/*     */ package com.sun.xml.ws.transport.tcp.server.glassfish;
/*     */ 
/*     */ import com.oracle.webservices.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.api.message.PropertySet.Property;
/*     */ import com.sun.xml.ws.api.DistributedPropertySet;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.Principal;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletInputStream;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
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
/*     */ public final class ServletFakeArtifactSet
/*     */   extends DistributedPropertySet
/*     */ {
/*  74 */   private static final BasePropertySet.PropertyMap model = parse(ServletFakeArtifactSet.class);
/*     */   private final HttpServletRequest request;
/*     */   
/*     */   public BasePropertySet.PropertyMap getPropertyMap() {
/*  78 */     return model;
/*     */   }
/*     */   private final HttpServletResponse response;
/*     */   public ServletFakeArtifactSet(String requestURL, String servletPath) {
/*  82 */     this.request = createRequest(requestURL, servletPath);
/*  83 */     this.response = createResponse();
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.servlet.response"})
/*     */   public HttpServletResponse getResponse() {
/*  88 */     return this.response;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.servlet.request"})
/*     */   public HttpServletRequest getRequest() {
/*  93 */     return this.request;
/*     */   }
/*     */   
/*     */   private static HttpServletRequest createRequest(String requestURL, String servletPath) {
/*  97 */     return new FakeServletHttpRequest(requestURL, servletPath);
/*     */   }
/*     */   
/*     */   private static HttpServletResponse createResponse() {
/* 101 */     return new FakeServletHttpResponse();
/*     */   }
/*     */   
/*     */   public static final class FakeServletHttpRequest implements HttpServletRequest {
/*     */     private final StringBuffer requestURL;
/*     */     private final String requestURI;
/*     */     private final String servletPath;
/*     */     
/*     */     public FakeServletHttpRequest(String requestURL, String servletPath) {
/* 110 */       this.requestURI = requestURL;
/* 111 */       this.requestURL = new StringBuffer(requestURL);
/* 112 */       this.servletPath = servletPath;
/*     */     }
/*     */     
/*     */     public String getAuthType() {
/* 116 */       return null;
/*     */     }
/*     */     
/*     */     public Cookie[] getCookies() {
/* 120 */       return null;
/*     */     }
/*     */     
/*     */     public long getDateHeader(String string) {
/* 124 */       return 0L;
/*     */     }
/*     */     
/*     */     public String getHeader(String string) {
/* 128 */       return null;
/*     */     }
/*     */     
/*     */     public Enumeration getHeaders(String string) {
/* 132 */       return null;
/*     */     }
/*     */     
/*     */     public Enumeration getHeaderNames() {
/* 136 */       return null;
/*     */     }
/*     */     
/*     */     public int getIntHeader(String string) {
/* 140 */       return -1;
/*     */     }
/*     */     
/*     */     public String getMethod() {
/* 144 */       return "POST";
/*     */     }
/*     */     
/*     */     public String getPathInfo() {
/* 148 */       return null;
/*     */     }
/*     */     
/*     */     public String getPathTranslated() {
/* 152 */       return null;
/*     */     }
/*     */     
/*     */     public String getContextPath() {
/* 156 */       return null;
/*     */     }
/*     */     
/*     */     public String getQueryString() {
/* 160 */       return null;
/*     */     }
/*     */     
/*     */     public String getRemoteUser() {
/* 164 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isUserInRole(String string) {
/* 168 */       return true;
/*     */     }
/*     */     
/*     */     public Principal getUserPrincipal() {
/* 172 */       return null;
/*     */     }
/*     */     
/*     */     public String getRequestedSessionId() {
/* 176 */       return null;
/*     */     }
/*     */     
/*     */     public String getRequestURI() {
/* 180 */       return this.requestURI;
/*     */     }
/*     */     
/*     */     public StringBuffer getRequestURL() {
/* 184 */       return this.requestURL;
/*     */     }
/*     */     
/*     */     public String getServletPath() {
/* 188 */       return this.servletPath;
/*     */     }
/*     */     
/*     */     public HttpSession getSession(boolean b) {
/* 192 */       return null;
/*     */     }
/*     */     
/*     */     public HttpSession getSession() {
/* 196 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isRequestedSessionIdValid() {
/* 200 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isRequestedSessionIdFromCookie() {
/* 204 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isRequestedSessionIdFromURL() {
/* 208 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isRequestedSessionIdFromUrl() {
/* 212 */       return true;
/*     */     }
/*     */     
/*     */     public Object getAttribute(String string) {
/* 216 */       return null;
/*     */     }
/*     */     
/*     */     public Enumeration getAttributeNames() {
/* 220 */       return null;
/*     */     }
/*     */     
/*     */     public String getCharacterEncoding() {
/* 224 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setCharacterEncoding(String string) throws UnsupportedEncodingException {}
/*     */     
/*     */     public int getContentLength() {
/* 231 */       return 0;
/*     */     }
/*     */     
/*     */     public String getContentType() {
/* 235 */       return null;
/*     */     }
/*     */     
/*     */     public ServletInputStream getInputStream() throws IOException {
/* 239 */       return null;
/*     */     }
/*     */     
/*     */     public String getParameter(String string) {
/* 243 */       return null;
/*     */     }
/*     */     
/*     */     public Enumeration getParameterNames() {
/* 247 */       return null;
/*     */     }
/*     */     
/*     */     public String[] getParameterValues(String string) {
/* 251 */       return null;
/*     */     }
/*     */     
/*     */     public Map getParameterMap() {
/* 255 */       return null;
/*     */     }
/*     */     
/*     */     public String getProtocol() {
/* 259 */       return null;
/*     */     }
/*     */     
/*     */     public String getScheme() {
/* 263 */       return null;
/*     */     }
/*     */     
/*     */     public String getServerName() {
/* 267 */       return null;
/*     */     }
/*     */     
/*     */     public int getServerPort() {
/* 271 */       return 0;
/*     */     }
/*     */     
/*     */     public BufferedReader getReader() throws IOException {
/* 275 */       return null;
/*     */     }
/*     */     
/*     */     public String getRemoteAddr() {
/* 279 */       return null;
/*     */     }
/*     */     
/*     */     public String getRemoteHost() {
/* 283 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setAttribute(String string, Object object) {}
/*     */ 
/*     */     
/*     */     public void removeAttribute(String string) {}
/*     */     
/*     */     public Locale getLocale() {
/* 293 */       return null;
/*     */     }
/*     */     
/*     */     public Enumeration getLocales() {
/* 297 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isSecure() {
/* 301 */       return false;
/*     */     }
/*     */     
/*     */     public RequestDispatcher getRequestDispatcher(String string) {
/* 305 */       return null;
/*     */     }
/*     */     
/*     */     public String getRealPath(String string) {
/* 309 */       return null;
/*     */     }
/*     */     
/*     */     public int getRemotePort() {
/* 313 */       return 0;
/*     */     }
/*     */     
/*     */     public String getLocalName() {
/* 317 */       return null;
/*     */     }
/*     */     
/*     */     public String getLocalAddr() {
/* 321 */       return null;
/*     */     }
/*     */     
/*     */     public int getLocalPort() {
/* 325 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class FakeServletHttpResponse
/*     */     implements HttpServletResponse {
/*     */     public void addCookie(Cookie cookie) {}
/*     */     
/*     */     public boolean containsHeader(String string) {
/* 334 */       return true;
/*     */     }
/*     */     
/*     */     public String encodeURL(String string) {
/* 338 */       return null;
/*     */     }
/*     */     
/*     */     public String encodeRedirectURL(String string) {
/* 342 */       return null;
/*     */     }
/*     */     
/*     */     public String encodeUrl(String string) {
/* 346 */       return null;
/*     */     }
/*     */     
/*     */     public String encodeRedirectUrl(String string) {
/* 350 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void sendError(int i, String string) throws IOException {}
/*     */ 
/*     */     
/*     */     public void sendError(int i) throws IOException {}
/*     */ 
/*     */     
/*     */     public void sendRedirect(String string) throws IOException {}
/*     */ 
/*     */     
/*     */     public void setDateHeader(String string, long l) {}
/*     */ 
/*     */     
/*     */     public void addDateHeader(String string, long l) {}
/*     */ 
/*     */     
/*     */     public void setHeader(String string, String string0) {}
/*     */ 
/*     */     
/*     */     public void addHeader(String string, String string0) {}
/*     */ 
/*     */     
/*     */     public void setIntHeader(String string, int i) {}
/*     */ 
/*     */     
/*     */     public void addIntHeader(String string, int i) {}
/*     */ 
/*     */     
/*     */     public void setStatus(int i) {}
/*     */ 
/*     */     
/*     */     public void setStatus(int i, String string) {}
/*     */     
/*     */     public String getCharacterEncoding() {
/* 387 */       return null;
/*     */     }
/*     */     
/*     */     public String getContentType() {
/* 391 */       return null;
/*     */     }
/*     */     
/*     */     public ServletOutputStream getOutputStream() throws IOException {
/* 395 */       return null;
/*     */     }
/*     */     
/*     */     public PrintWriter getWriter() throws IOException {
/* 399 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setCharacterEncoding(String string) {}
/*     */ 
/*     */     
/*     */     public void setContentLength(int i) {}
/*     */ 
/*     */     
/*     */     public void setContentType(String string) {}
/*     */ 
/*     */     
/*     */     public void setBufferSize(int i) {}
/*     */     
/*     */     public int getBufferSize() {
/* 415 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void flushBuffer() throws IOException {}
/*     */ 
/*     */     
/*     */     public void resetBuffer() {}
/*     */     
/*     */     public boolean isCommitted() {
/* 425 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void reset() {}
/*     */ 
/*     */     
/*     */     public void setLocale(Locale locale) {}
/*     */     
/*     */     public Locale getLocale() {
/* 435 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage getSOAPMessage() throws SOAPException {
/* 442 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setSOAPMessage(SOAPMessage soap) {
/* 446 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\glassfish\ServletFakeArtifactSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */