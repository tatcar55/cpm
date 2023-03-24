/*    */ package com.sun.xml.ws.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public final class RuntimeVersion
/*    */ {
/*    */   public static final Version VERSION;
/*    */   
/*    */   static {
/* 57 */     Version version = null;
/* 58 */     InputStream in = RuntimeVersion.class.getResourceAsStream("version.properties");
/*    */     try {
/* 60 */       version = Version.create(in);
/*    */     } finally {
/* 62 */       if (in != null) {
/*    */         try {
/* 64 */           in.close();
/* 65 */         } catch (IOException ioe) {}
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 70 */     VERSION = (version == null) ? Version.create(null) : version;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 77 */     return VERSION.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\RuntimeVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */