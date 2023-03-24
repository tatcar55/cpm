/*     */ package com.sun.xml.ws.transport.http.servlet;
/*     */ 
/*     */ import com.oracle.webservices.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.api.message.PropertySet.Property;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.ha.HaInfo;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WebServiceContextDelegate;
/*     */ import com.sun.xml.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.ws.transport.Headers;
/*     */ import com.sun.xml.ws.transport.http.WSHTTPConnection;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Principal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class ServletConnectionImpl
/*     */   extends WSHTTPConnection
/*     */   implements WebServiceContextDelegate
/*     */ {
/*     */   private final HttpServletRequest request;
/*     */   private final HttpServletResponse response;
/*     */   private final ServletContext context;
/*     */   private int status;
/*     */   private Headers requestHeaders;
/*     */   private final ServletAdapter adapter;
/*     */   private Headers responseHeaders;
/*     */   private HaInfo haInfo;
/*     */   
/*     */   public ServletConnectionImpl(@NotNull ServletAdapter adapter, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
/*  90 */     this.adapter = adapter;
/*  91 */     this.context = context;
/*  92 */     this.request = request;
/*  93 */     this.response = response;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.headers", "com.sun.xml.ws.api.message.packet.inbound.transport.headers"})
/*     */   @NotNull
/*     */   public Map<String, List<String>> getRequestHeaders() {
/*  99 */     if (this.requestHeaders == null) {
/* 100 */       this.requestHeaders = new Headers();
/* 101 */       Enumeration<String> enums = this.request.getHeaderNames();
/* 102 */       while (enums.hasMoreElements()) {
/* 103 */         String headerName = enums.nextElement();
/* 104 */         Enumeration<String> e = this.request.getHeaders(headerName);
/* 105 */         if (e != null) {
/* 106 */           List<String> values = (List<String>)this.requestHeaders.get(headerName);
/* 107 */           while (e.hasMoreElements()) {
/* 108 */             String headerValue = e.nextElement();
/* 109 */             if (values == null) {
/* 110 */               values = new ArrayList<String>();
/* 111 */               this.requestHeaders.put(headerName, values);
/*     */             } 
/* 113 */             values.add(headerValue);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 118 */     return (Map<String, List<String>>)this.requestHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getRequestHeaderNames() {
/* 123 */     return getRequestHeaders().keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getRequestHeaderValues(@NotNull String headerName) {
/* 128 */     if (this.requestHeaders != null) {
/* 129 */       return (List<String>)this.requestHeaders.get(headerName);
/*     */     }
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResponseHeaders(Map<String, List<String>> headers) {
/* 139 */     if (headers == null) {
/* 140 */       this.responseHeaders = null;
/*     */     } else {
/* 142 */       if (this.responseHeaders == null) {
/* 143 */         this.responseHeaders = new Headers();
/*     */       } else {
/* 145 */         this.responseHeaders.clear();
/*     */       } 
/* 147 */       this.responseHeaders.putAll(headers);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseHeader(String key, String value) {
/* 153 */     setResponseHeader(key, Collections.singletonList(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseHeader(String key, List<String> value) {
/* 158 */     if (this.responseHeaders == null) {
/* 159 */       this.responseHeaders = new Headers();
/*     */     }
/*     */     
/* 162 */     this.responseHeaders.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.response.headers", "com.sun.xml.ws.api.message.packet.outbound.transport.headers"})
/*     */   public Map<String, List<String>> getResponseHeaders() {
/* 168 */     return (Map<String, List<String>>)this.responseHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(int status) {
/* 173 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.response.code"})
/*     */   public int getStatus() {
/* 182 */     return this.status;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentTypeResponseHeader(@NotNull String value) {
/* 187 */     this.response.setContentType(value);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public InputStream getInput() throws IOException {
/* 192 */     return (InputStream)this.request.getInputStream();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public OutputStream getOutput() throws IOException {
/* 197 */     this.response.setStatus(this.status);
/* 198 */     if (this.responseHeaders != null) {
/* 199 */       for (Map.Entry<String, List<String>> entry : (Iterable<Map.Entry<String, List<String>>>)this.responseHeaders.entrySet()) {
/* 200 */         String name = entry.getKey();
/* 201 */         if (name == null) {
/*     */           continue;
/*     */         }
/* 204 */         if (name.equalsIgnoreCase("Content-Type") || name.equalsIgnoreCase("Content-Length"))
/*     */           continue; 
/* 206 */         for (String value : entry.getValue()) {
/* 207 */           this.response.addHeader(name, value);
/*     */         }
/*     */       } 
/*     */     }
/* 211 */     return (OutputStream)this.response.getOutputStream();
/*     */   }
/*     */   @NotNull
/*     */   public WebServiceContextDelegate getWebServiceContextDelegate() {
/* 215 */     return this;
/*     */   }
/*     */   
/*     */   public Principal getUserPrincipal(Packet p) {
/* 219 */     return this.request.getUserPrincipal();
/*     */   }
/*     */   
/*     */   public boolean isUserInRole(Packet p, String role) {
/* 223 */     return this.request.isUserInRole(role);
/*     */   }
/*     */   @NotNull
/*     */   public String getEPRAddress(Packet p, WSEndpoint endpoint) {
/* 227 */     PortAddressResolver resolver = this.adapter.owner.createPortAddressResolver(getBaseAddress(), endpoint.getImplementationClass());
/* 228 */     String address = resolver.getAddressFor(endpoint.getServiceName(), endpoint.getPortName().getLocalPart());
/* 229 */     if (address == null)
/* 230 */       throw new WebServiceException(WsservletMessages.SERVLET_NO_ADDRESS_AVAILABLE(endpoint.getPortName())); 
/* 231 */     return address;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWSDLAddress(@NotNull Packet request, @NotNull WSEndpoint endpoint) {
/* 236 */     String eprAddress = getEPRAddress(request, endpoint);
/* 237 */     if (this.adapter.getEndpoint().getPort() != null) {
/* 238 */       return eprAddress + "?wsdl";
/*     */     }
/* 240 */     return null;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.method"})
/*     */   @NotNull
/*     */   public String getRequestMethod() {
/* 246 */     return this.request.getMethod();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSecure() {
/* 251 */     return this.request.isSecure();
/*     */   }
/*     */ 
/*     */   
/*     */   public Principal getUserPrincipal() {
/* 256 */     return this.request.getUserPrincipal();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUserInRole(String role) {
/* 261 */     return this.request.isUserInRole(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestHeader(String headerName) {
/* 266 */     return this.request.getHeader(headerName);
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.querystring"})
/*     */   public String getQueryString() {
/* 272 */     return this.request.getQueryString();
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.pathinfo"})
/*     */   @NotNull
/*     */   public String getPathInfo() {
/* 278 */     return this.request.getPathInfo();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getRequestURI() {
/* 283 */     return this.request.getRequestURI();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getRequestScheme() {
/* 288 */     return this.request.getScheme();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getServerName() {
/* 293 */     return this.request.getServerName();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public int getServerPort() {
/* 298 */     return this.request.getServerPort();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getContextPath() {
/* 303 */     return this.request.getContextPath();
/*     */   }
/*     */   @NotNull
/*     */   public String getBaseAddress() {
/* 307 */     return getBaseAddress(this.request);
/*     */   }
/*     */   @NotNull
/*     */   static String getBaseAddress(HttpServletRequest request) {
/* 311 */     StringBuilder buf = new StringBuilder();
/* 312 */     buf.append(request.getScheme());
/* 313 */     buf.append("://");
/* 314 */     buf.append(request.getServerName());
/* 315 */     buf.append(':');
/* 316 */     buf.append(request.getServerPort());
/* 317 */     buf.append(request.getContextPath());
/*     */     
/* 319 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getRequestAttribute(String key) {
/* 324 */     return this.request.getAttribute(key);
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.servlet.context"})
/*     */   public ServletContext getContext() {
/* 329 */     return this.context;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.servlet.response"})
/*     */   public HttpServletResponse getResponse() {
/* 334 */     return this.response;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.servlet.request"})
/*     */   public HttpServletRequest getRequest() {
/* 339 */     return this.request;
/*     */   }
/*     */   
/*     */   @Property({"com.sun.xml.ws.transport.http.servlet.requestURL"})
/*     */   public String getRequestURL() {
/* 344 */     return this.request.getRequestURL().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getProtocol() {
/* 349 */     return this.request.getProtocol();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentLengthResponseHeader(int value) {
/* 354 */     this.response.setContentLength(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCookie(String name) {
/* 359 */     Cookie[] cookies = this.request.getCookies();
/* 360 */     if (cookies != null) {
/* 361 */       for (Cookie cookie : cookies) {
/* 362 */         if (cookie.getName().equals(name)) {
/* 363 */           return cookie.getValue();
/*     */         }
/*     */       } 
/*     */     }
/* 367 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCookie(String name, String value) {
/* 372 */     Cookie cookie = new Cookie(name, value);
/* 373 */     this.response.addCookie(cookie);
/*     */   }
/*     */   
/*     */   @Property({"com.sun.xml.ws.api.message.packet.hainfo"})
/*     */   public HaInfo getHaInfo() {
/* 378 */     if (this.haInfo == null) {
/* 379 */       String replicaInstance = null;
/* 380 */       String key = null;
/* 381 */       String jrouteId = null;
/* 382 */       Cookie[] cookies = this.request.getCookies();
/* 383 */       if (cookies != null) {
/* 384 */         for (Cookie cookie : cookies) {
/* 385 */           if (replicaInstance == null && cookie.getName().equals("JREPLICA")) {
/* 386 */             replicaInstance = cookie.getValue();
/* 387 */           } else if (key == null && cookie.getName().equals("METRO_KEY")) {
/* 388 */             key = cookie.getValue();
/* 389 */           } else if (jrouteId == null && cookie.getName().equals("JROUTE")) {
/* 390 */             jrouteId = cookie.getValue();
/*     */           } 
/*     */         } 
/* 393 */         if (replicaInstance != null && key != null) {
/* 394 */           String proxyJroute = this.request.getHeader("proxy-jroute");
/* 395 */           boolean failOver = (jrouteId != null && proxyJroute != null && !jrouteId.equals(proxyJroute));
/* 396 */           this.haInfo = new HaInfo(key, replicaInstance, failOver);
/*     */         } 
/*     */       } 
/*     */     } 
/* 400 */     return this.haInfo;
/*     */   }
/*     */   
/*     */   public void setHaInfo(HaInfo replicaInfo) {
/* 404 */     this.haInfo = replicaInfo;
/*     */   }
/*     */   
/*     */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 408 */     return model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 414 */   private static final BasePropertySet.PropertyMap model = parse(ServletConnectionImpl.class);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\ServletConnectionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */