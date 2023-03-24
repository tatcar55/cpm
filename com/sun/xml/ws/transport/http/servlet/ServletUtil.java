/*    */ package com.sun.xml.ws.transport.http.servlet;
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
/*    */ public class ServletUtil
/*    */ {
/*    */   public static boolean isServlet30Based() {
/*    */     try {
/* 49 */       Class<?> servletRequestClazz = Class.forName("javax.servlet.ServletRequest");
/* 50 */       servletRequestClazz.getDeclaredMethod("getServletContext", new Class[0]);
/*    */       
/* 52 */       return true;
/* 53 */     } catch (Throwable t) {
/*    */ 
/*    */       
/* 56 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\ServletUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */