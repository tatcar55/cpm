/*     */ package com.sun.xml.ws.transport.httpspi.servlet;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.security.Principal;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.ws.spi.http.HttpContext;
/*     */ import javax.xml.ws.spi.http.HttpExchange;
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
/*     */ final class EndpointHttpExchange
/*     */   extends HttpExchange
/*     */ {
/*     */   private final HttpServletRequest req;
/*     */   private final HttpServletResponse res;
/*     */   private final ExchangeRequestHeaders reqHeaders;
/*     */   private final ExchangeResponseHeaders resHeaders;
/*     */   private final ServletContext servletContext;
/*     */   private final HttpContext httpContext;
/*  66 */   private static final Set<String> attributes = new HashSet<String>();
/*     */   static {
/*  68 */     attributes.add("javax.xml.ws.servlet.context");
/*  69 */     attributes.add("javax.xml.ws.servlet.request");
/*  70 */     attributes.add("javax.xml.ws.servlet.response");
/*     */   }
/*     */ 
/*     */   
/*     */   EndpointHttpExchange(HttpServletRequest req, HttpServletResponse res, ServletContext servletContext, HttpContext httpContext) {
/*  75 */     this.req = req;
/*  76 */     this.res = res;
/*  77 */     this.servletContext = servletContext;
/*  78 */     this.httpContext = httpContext;
/*  79 */     this.reqHeaders = new ExchangeRequestHeaders(req);
/*  80 */     this.resHeaders = new ExchangeResponseHeaders(res);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, List<String>> getRequestHeaders() {
/*  85 */     return this.reqHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, List<String>> getResponseHeaders() {
/*  90 */     return this.resHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestURI() {
/*  95 */     return this.req.getRequestURI();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContextPath() {
/* 100 */     return this.req.getContextPath();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestMethod() {
/* 105 */     return this.req.getMethod();
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpContext getHttpContext() {
/* 110 */     return this.httpContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {}
/*     */ 
/*     */   
/*     */   public String getRequestHeader(String name) {
/* 119 */     return this.reqHeaders.getFirst(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addResponseHeader(String name, String value) {
/* 124 */     this.resHeaders.add(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getRequestBody() throws IOException {
/* 129 */     return (InputStream)this.req.getInputStream();
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStream getResponseBody() throws IOException {
/* 134 */     return (OutputStream)this.res.getOutputStream();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(int rCode) {
/* 139 */     this.res.setStatus(rCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress getRemoteAddress() {
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetSocketAddress getLocalAddress() {
/* 151 */     return InetSocketAddress.createUnresolved(this.req.getServerName(), this.req.getServerPort());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getProtocol() {
/* 156 */     return this.req.getProtocol();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getAttribute(String name) {
/* 161 */     if (name.equals("javax.xml.ws.servlet.context"))
/* 162 */       return this.servletContext; 
/* 163 */     if (name.equals("javax.xml.ws.servlet.request"))
/* 164 */       return this.req; 
/* 165 */     if (name.equals("javax.xml.ws.servlet.response")) {
/* 166 */       return this.res;
/*     */     }
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getAttributeNames() {
/* 173 */     return attributes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Principal getUserPrincipal() {
/* 178 */     return this.req.getUserPrincipal();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUserInRole(String role) {
/* 183 */     return this.req.isUserInRole(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getScheme() {
/* 188 */     return this.req.getScheme();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPathInfo() {
/* 193 */     return this.req.getPathInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getQueryString() {
/* 198 */     return this.req.getQueryString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\EndpointHttpExchange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */