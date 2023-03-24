/*     */ package com.sun.xml.rpc.server.http.ea;
/*     */ 
/*     */ import com.sun.xml.rpc.server.http.JAXRPCServletException;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImplementorRegistry
/*     */ {
/*     */   public ImplementorInfo getImplementorInfo(String name) {
/*  51 */     ImplementorInfo info = (ImplementorInfo)this._implementors.get(name);
/*     */     
/*  53 */     if (info == null) {
/*  54 */       throw new JAXRPCServletException("error.implementorRegistry.unknownName", name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  59 */     return info;
/*     */   }
/*     */   
/*     */   public boolean containsName(String name) {
/*  63 */     return this._implementors.containsKey(name);
/*     */   }
/*     */   
/*     */   public Iterator names() {
/*  67 */     return this._implementors.keySet().iterator();
/*     */   }
/*     */   
/*     */   public void readFrom(String filename) {
/*     */     try {
/*  72 */       readFrom(new FileInputStream(filename));
/*  73 */     } catch (FileNotFoundException e) {
/*  74 */       throw new JAXRPCServletException("error.implementorRegistry.fileNotFound", filename);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFrom(InputStream inputStream) {
/*     */     try {
/*  83 */       Properties properties = new Properties();
/*  84 */       properties.load(inputStream);
/*  85 */       inputStream.close();
/*     */       
/*  87 */       int portCount = Integer.parseInt(properties.getProperty("portcount"));
/*     */       
/*  89 */       for (int i = 0; i < portCount; i++) {
/*  90 */         String portPrefix = "port" + Integer.toString(i) + ".";
/*  91 */         String name = properties.getProperty(portPrefix + "name");
/*     */         
/*  93 */         String tieClassName = properties.getProperty(portPrefix + "tie");
/*     */         
/*  95 */         String servantClassName = properties.getProperty(portPrefix + "servant");
/*     */         
/*  97 */         if (name == null || tieClassName == null || servantClassName == null)
/*     */         {
/*     */           
/* 100 */           throw new JAXRPCServletException("error.implementorRegistry.incompleteInformation");
/*     */         }
/* 102 */         register(name, tieClassName, servantClassName);
/*     */       } 
/* 104 */     } catch (IOException e) {
/* 105 */       throw new JAXRPCServletException("error.implementorRegistry.cannotReadConfiguration");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(String name, String tieClassName, String servantClassName) {
/* 113 */     Class<?> tieClass = null;
/* 114 */     Class<?> servantClass = null;
/*     */     try {
/* 116 */       tieClass = Thread.currentThread().getContextClassLoader().loadClass(tieClassName);
/*     */     
/*     */     }
/* 119 */     catch (ClassNotFoundException e) {
/* 120 */       throw new JAXRPCServletException("error.implementorRegistry.classNotFound", tieClassName);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 126 */       servantClass = Thread.currentThread().getContextClassLoader().loadClass(servantClassName);
/*     */     
/*     */     }
/* 129 */     catch (ClassNotFoundException e) {
/* 130 */       throw new JAXRPCServletException("error.implementorRegistry.classNotFound", servantClassName);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 135 */     register(name, new ImplementorInfo(tieClass, servantClass));
/*     */   }
/*     */   
/*     */   public void register(String name, ImplementorInfo info) {
/* 139 */     if (this._implementors.containsKey(name)) {
/* 140 */       throw new JAXRPCServletException("error.implementorRegistry.duplicateName", name);
/*     */     }
/*     */ 
/*     */     
/* 144 */     this._implementors.put(name, info);
/*     */   }
/*     */ 
/*     */   
/* 148 */   private Map _implementors = new HashMap<Object, Object>();
/*     */   private static final String PROPERTY_PORT_COUNT = "portcount";
/*     */   private static final String PROPERTY_PORT = "port";
/*     */   private static final String PROPERTY_NAME = "name";
/*     */   private static final String PROPERTY_TIE = "tie";
/*     */   private static final String PROPERTY_SERVANT = "servant";
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\ea\ImplementorRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */