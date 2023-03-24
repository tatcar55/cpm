/*    */ package com.sun.xml.rpc.streaming;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.security.AccessControlException;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import javax.xml.parsers.FactoryConfigurationError;
/*    */ import org.xml.sax.InputSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class XMLReaderFactory
/*    */ {
/*    */   private static XMLReaderFactory _instance;
/*    */   
/*    */   public static XMLReaderFactory newInstance() {
/* 61 */     if (_instance == null) {
/* 62 */       String factoryImplName = getFactoryImplName();
/*    */       
/*    */       try {
/* 65 */         Class<?> clazz = Class.forName(factoryImplName);
/* 66 */         _instance = (XMLReaderFactory)clazz.newInstance();
/* 67 */       } catch (ClassNotFoundException e) {
/* 68 */         throw new FactoryConfigurationError(e);
/* 69 */       } catch (IllegalAccessException e) {
/* 70 */         throw new FactoryConfigurationError(e);
/* 71 */       } catch (InstantiationException e) {
/* 72 */         throw new FactoryConfigurationError(e);
/*    */       } 
/*    */     } 
/* 75 */     return _instance;
/*    */   }
/*    */   
/*    */   private static String getFactoryImplName() {
/*    */     String str;
/*    */     try {
/* 81 */       str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*    */           {
/*    */             public Object run()
/*    */             {
/* 85 */               return System.getProperty("com.sun.xml.rpc.streaming.XMLReaderFactory", "com.sun.xml.rpc.streaming.XMLReaderFactoryImpl");
/*    */             }
/*    */           });
/*    */     
/*    */     }
/* 90 */     catch (AccessControlException e) {
/* 91 */       str = "com.sun.xml.rpc.streaming.XMLReaderFactoryImpl";
/*    */     } 
/* 93 */     return str;
/*    */   }
/*    */   
/*    */   public abstract XMLReader createXMLReader(InputStream paramInputStream);
/*    */   
/*    */   public abstract XMLReader createXMLReader(InputSource paramInputSource);
/*    */   
/*    */   public abstract XMLReader createXMLReader(InputStream paramInputStream, boolean paramBoolean);
/*    */   
/*    */   public abstract XMLReader createXMLReader(InputSource paramInputSource, boolean paramBoolean);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLReaderFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */