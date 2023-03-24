/*    */ package com.sun.xml.rpc.streaming;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.security.AccessControlException;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import javax.xml.parsers.FactoryConfigurationError;
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
/*    */ public abstract class XMLWriterFactory
/*    */ {
/*    */   private static XMLWriterFactory _instance;
/*    */   
/*    */   public static XMLWriterFactory newInstance() {
/* 58 */     if (_instance == null) {
/* 59 */       String factoryImplName = getFactoryImplName();
/*    */       
/*    */       try {
/* 62 */         Class<?> clazz = Class.forName(factoryImplName);
/* 63 */         _instance = (XMLWriterFactory)clazz.newInstance();
/* 64 */       } catch (ClassNotFoundException e) {
/* 65 */         throw new FactoryConfigurationError(e);
/* 66 */       } catch (IllegalAccessException e) {
/* 67 */         throw new FactoryConfigurationError(e);
/* 68 */       } catch (InstantiationException e) {
/* 69 */         throw new FactoryConfigurationError(e);
/*    */       } 
/*    */     } 
/* 72 */     return _instance;
/*    */   }
/*    */   
/*    */   private static String getFactoryImplName() {
/*    */     String str;
/*    */     try {
/* 78 */       str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*    */           {
/*    */             public Object run()
/*    */             {
/* 82 */               return System.getProperty("com.sun.xml.rpc.streaming.XMLWriterFactory", "com.sun.xml.rpc.streaming.XMLWriterFactoryImpl");
/*    */             }
/*    */           });
/*    */     
/*    */     }
/* 87 */     catch (AccessControlException e) {
/* 88 */       str = "com.sun.xml.rpc.streaming.XMLWriterFactoryImpl";
/*    */     } 
/* 90 */     return str;
/*    */   }
/*    */   
/*    */   public abstract XMLWriter createXMLWriter(OutputStream paramOutputStream);
/*    */   
/*    */   public abstract XMLWriter createXMLWriter(OutputStream paramOutputStream, String paramString);
/*    */   
/*    */   public abstract XMLWriter createXMLWriter(OutputStream paramOutputStream, String paramString, boolean paramBoolean);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLWriterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */