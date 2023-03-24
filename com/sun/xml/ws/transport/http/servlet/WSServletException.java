/*    */ package com.sun.xml.ws.transport.http.servlet;
/*    */ 
/*    */ import com.sun.xml.ws.util.exception.JAXWSExceptionBase;
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
/*    */ final class WSServletException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public WSServletException(String key, Object... args) {
/* 51 */     super(key, args);
/*    */   }
/*    */   
/*    */   public WSServletException(Throwable throwable) {
/* 55 */     super(throwable);
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 59 */     return "com.sun.xml.ws.resources.wsservlet";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\WSServletException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */