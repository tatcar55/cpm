/*    */ package com.sun.xml.rpc.server.http;
/*    */ 
/*    */ import java.security.Principal;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import javax.xml.rpc.handler.MessageContext;
/*    */ import javax.xml.rpc.server.ServletEndpointContext;
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
/*    */ public class ServletEndpointContextImpl
/*    */   implements ServletEndpointContext
/*    */ {
/*    */   private ServletContext servletContext;
/*    */   private Implementor implementor;
/*    */   
/*    */   public ServletEndpointContextImpl(ServletContext c) {
/* 45 */     this(c, null);
/*    */   }
/*    */   
/*    */   public ServletEndpointContextImpl(ServletContext c, Implementor i) {
/* 49 */     this.servletContext = c;
/* 50 */     this.implementor = i;
/*    */   }
/*    */   
/*    */   public ServletContext getServletContext() {
/* 54 */     return this.servletContext;
/*    */   }
/*    */   
/*    */   public Principal getUserPrincipal() {
/* 58 */     return getHttpServletRequest().getUserPrincipal();
/*    */   }
/*    */   
/*    */   public HttpSession getHttpSession() {
/* 62 */     return getHttpServletRequest().getSession();
/*    */   }
/*    */   
/*    */   public MessageContext getMessageContext() {
/* 66 */     return messageContext.get();
/*    */   }
/*    */   
/*    */   public void setMessageContext(MessageContext c) {
/* 70 */     messageContext.set(c);
/*    */   }
/*    */   
/*    */   public HttpServletRequest getHttpServletRequest() {
/* 74 */     return httpRequest.get();
/*    */   }
/*    */   
/*    */   public void setHttpServletRequest(HttpServletRequest r) {
/* 78 */     httpRequest.set(r);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 82 */     setMessageContext(null);
/* 83 */     setHttpServletRequest(null);
/*    */   }
/*    */   
/*    */   public boolean isUserInRole(String role) {
/* 87 */     return ((HttpServletRequest)httpRequest.get()).isUserInRole(role);
/*    */   }
/*    */   
/*    */   public Implementor getImplementor() {
/* 91 */     return this.implementor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 98 */   private static ThreadLocal messageContext = new ThreadLocal();
/* 99 */   private static ThreadLocal httpRequest = new ThreadLocal();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\ServletEndpointContextImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */