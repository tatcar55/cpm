/*     */ package com.sun.xml.ws.transport.http.servlet;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
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
/*     */ public class WSServlet
/*     */   extends HttpServlet
/*     */ {
/*  60 */   private WSServletDelegate delegate = null;
/*     */   public static final String JAXWS_RI_RUNTIME_INFO = "com.sun.xml.ws.server.http.servletDelegate";
/*     */   
/*     */   public void init(ServletConfig servletConfig) throws ServletException {
/*  64 */     super.init(servletConfig);
/*  65 */     this.delegate = getDelegate(servletConfig);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final String JAXWS_RI_PROPERTY_PUBLISH_WSDL = "com.sun.xml.ws.server.http.publishWSDL";
/*     */   
/*     */   public static final String JAXWS_RI_PROPERTY_PUBLISH_STATUS_PAGE = "com.sun.xml.ws.server.http.publishStatusPage";
/*     */   
/*     */   @Nullable
/*     */   protected WSServletDelegate getDelegate(ServletConfig servletConfig) {
/*  75 */     return (WSServletDelegate)servletConfig.getServletContext().getAttribute("com.sun.xml.ws.server.http.servletDelegate");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/*  80 */     if (this.delegate != null) {
/*  81 */       this.delegate.doPost(request, response, getServletContext());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/*  88 */     if (this.delegate != null) {
/*  89 */       this.delegate.doGet(request, response, getServletContext());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/*  96 */     if (this.delegate != null) {
/*  97 */       this.delegate.doPut(request, response, getServletContext());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/* 104 */     if (this.delegate != null) {
/* 105 */       this.delegate.doDelete(request, response, getServletContext());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/* 111 */     if (this.delegate != null)
/* 112 */       this.delegate.doHead(request, response, getServletContext()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\WSServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */