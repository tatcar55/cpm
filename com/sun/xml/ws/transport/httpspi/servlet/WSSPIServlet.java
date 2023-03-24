/*    */ package com.sun.xml.ws.transport.httpspi.servlet;
/*    */ 
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
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
/*    */ public class WSSPIServlet
/*    */   extends HttpServlet
/*    */ {
/* 58 */   private WSServletDelegate delegate = null;
/*    */   
/*    */   public void init(ServletConfig servletConfig) throws ServletException {
/* 61 */     super.init(servletConfig);
/* 62 */     this.delegate = getDelegate(servletConfig);
/*    */   }
/*    */ 
/*    */   
/*    */   public static final String JAXWS_RI_RUNTIME_INFO = "com.sun.xml.ws.server.http.servletDelegate";
/*    */   
/*    */   public static final String JAXWS_RI_PROPERTY_PUBLISH_WSDL = "com.sun.xml.ws.server.http.publishWSDL";
/*    */   public static final String JAXWS_RI_PROPERTY_PUBLISH_STATUS_PAGE = "com.sun.xml.ws.server.http.publishStatusPage";
/*    */   
/*    */   protected WSServletDelegate getDelegate(ServletConfig servletConfig) {
/* 72 */     return (WSServletDelegate)servletConfig.getServletContext().getAttribute("com.sun.xml.ws.server.http.servletDelegate");
/*    */   }
/*    */   
/*    */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/* 76 */     if (this.delegate != null) {
/* 77 */       this.delegate.doPost(request, response, getServletContext());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/* 83 */     if (this.delegate != null) {
/* 84 */       this.delegate.doGet(request, response, getServletContext());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/* 90 */     if (this.delegate != null) {
/* 91 */       this.delegate.doPut(request, response, getServletContext());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/* 97 */     if (this.delegate != null)
/* 98 */       this.delegate.doDelete(request, response, getServletContext()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\WSSPIServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */