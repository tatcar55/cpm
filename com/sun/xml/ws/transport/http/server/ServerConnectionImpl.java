/*     */ package com.sun.xml.ws.transport.http.server;
/*     */ 
/*     */ import com.oracle.webservices.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.api.message.PropertySet.Property;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.net.httpserver.Headers;
/*     */ import com.sun.net.httpserver.HttpExchange;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WebServiceContextDelegate;
/*     */ import com.sun.xml.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.ws.transport.http.WSHTTPConnection;
/*     */ import com.sun.xml.ws.util.ReadAllStream;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URI;
/*     */ import java.security.Principal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ final class ServerConnectionImpl
/*     */   extends WSHTTPConnection
/*     */   implements WebServiceContextDelegate
/*     */ {
/*     */   private final HttpExchange httpExchange;
/*     */   private int status;
/*     */   private final HttpAdapter adapter;
/*     */   private LWHSInputStream in;
/*     */   private OutputStream out;
/*     */   
/*     */   public ServerConnectionImpl(@NotNull HttpAdapter adapter, @NotNull HttpExchange httpExchange) {
/*  88 */     this.adapter = adapter;
/*  89 */     this.httpExchange = httpExchange;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.headers", "com.sun.xml.ws.api.message.packet.inbound.transport.headers"})
/*     */   @NotNull
/*     */   public Map<String, List<String>> getRequestHeaders() {
/*  95 */     return this.httpExchange.getRequestHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestHeader(String headerName) {
/* 100 */     return this.httpExchange.getRequestHeaders().getFirst(headerName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseHeaders(Map<String, List<String>> headers) {
/* 105 */     Headers r = this.httpExchange.getResponseHeaders();
/* 106 */     r.clear();
/* 107 */     for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
/* 108 */       String name = entry.getKey();
/* 109 */       List<String> values = entry.getValue();
/*     */       
/* 111 */       if (!"Content-Length".equalsIgnoreCase(name) && !"Content-Type".equalsIgnoreCase(name)) {
/* 112 */         r.put(name, new ArrayList<String>(values));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseHeader(String key, List<String> value) {
/* 119 */     this.httpExchange.getResponseHeaders().put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getRequestHeaderNames() {
/* 124 */     return this.httpExchange.getRequestHeaders().keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getRequestHeaderValues(String headerName) {
/* 129 */     return this.httpExchange.getRequestHeaders().get(headerName);
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.response.headers", "com.sun.xml.ws.api.message.packet.outbound.transport.headers"})
/*     */   public Map<String, List<String>> getResponseHeaders() {
/* 135 */     return this.httpExchange.getResponseHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentTypeResponseHeader(@NotNull String value) {
/* 140 */     this.httpExchange.getResponseHeaders().set("Content-Type", value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(int status) {
/* 145 */     this.status = status;
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.response.code"})
/*     */   public int getStatus() {
/* 151 */     return this.status;
/*     */   }
/*     */   @NotNull
/*     */   public InputStream getInput() {
/* 155 */     if (this.in == null) {
/* 156 */       this.in = new LWHSInputStream(this.httpExchange.getRequestBody());
/*     */     }
/* 158 */     return this.in;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LWHSInputStream
/*     */     extends FilterInputStream
/*     */   {
/*     */     boolean closed;
/*     */     boolean readAll;
/*     */     
/*     */     LWHSInputStream(InputStream in) {
/* 169 */       super(in);
/*     */     }
/*     */     
/*     */     void readAll() throws IOException {
/* 173 */       if (!this.closed && !this.readAll) {
/* 174 */         ReadAllStream all = new ReadAllStream();
/* 175 */         all.readAll(this.in, 4000000L);
/* 176 */         this.in.close();
/* 177 */         this.in = (InputStream)all;
/* 178 */         this.readAll = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 184 */       if (!this.closed) {
/* 185 */         readAll();
/* 186 */         super.close();
/* 187 */         this.closed = true;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public OutputStream getOutput() throws IOException {
/* 195 */     if (this.out == null) {
/* 196 */       String lenHeader = this.httpExchange.getResponseHeaders().getFirst("Content-Length");
/* 197 */       int length = (lenHeader != null) ? Integer.parseInt(lenHeader) : 0;
/* 198 */       this.httpExchange.sendResponseHeaders(getStatus(), length);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 203 */       this.out = new FilterOutputStream(this.httpExchange.getResponseBody()) {
/*     */           boolean closed;
/*     */           
/*     */           public void close() throws IOException {
/* 207 */             if (!this.closed) {
/* 208 */               this.closed = true;
/*     */ 
/*     */               
/* 211 */               ServerConnectionImpl.this.in.readAll();
/*     */               try {
/* 213 */                 super.close();
/* 214 */               } catch (IOException ioe) {}
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void write(byte[] buf, int start, int len) throws IOException {
/* 223 */             this.out.write(buf, start, len);
/*     */           }
/*     */         };
/*     */     } 
/* 227 */     return this.out;
/*     */   }
/*     */   @NotNull
/*     */   public WebServiceContextDelegate getWebServiceContextDelegate() {
/* 231 */     return this;
/*     */   }
/*     */   
/*     */   public Principal getUserPrincipal(Packet request) {
/* 235 */     return this.httpExchange.getPrincipal();
/*     */   }
/*     */   
/*     */   public boolean isUserInRole(Packet request, String role) {
/* 239 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getEPRAddress(Packet request, WSEndpoint endpoint) {
/* 245 */     PortAddressResolver resolver = this.adapter.owner.createPortAddressResolver(getBaseAddress(), endpoint.getImplementationClass());
/* 246 */     String address = resolver.getAddressFor(endpoint.getServiceName(), endpoint.getPortName().getLocalPart());
/* 247 */     if (address == null)
/* 248 */       throw new WebServiceException(WsservletMessages.SERVLET_NO_ADDRESS_AVAILABLE(endpoint.getPortName())); 
/* 249 */     return address;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWSDLAddress(@NotNull Packet request, @NotNull WSEndpoint endpoint) {
/* 254 */     String eprAddress = getEPRAddress(request, endpoint);
/* 255 */     if (this.adapter.getEndpoint().getPort() != null) {
/* 256 */       return eprAddress + "?wsdl";
/*     */     }
/* 258 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSecure() {
/* 263 */     return this.httpExchange instanceof com.sun.net.httpserver.HttpsExchange;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.method"})
/*     */   @NotNull
/*     */   public String getRequestMethod() {
/* 269 */     return this.httpExchange.getRequestMethod();
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.querystring"})
/*     */   public String getQueryString() {
/* 275 */     URI requestUri = this.httpExchange.getRequestURI();
/* 276 */     String query = requestUri.getQuery();
/* 277 */     if (query != null)
/* 278 */       return query; 
/* 279 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.pathinfo"})
/*     */   public String getPathInfo() {
/* 285 */     URI requestUri = this.httpExchange.getRequestURI();
/* 286 */     String reqPath = requestUri.getPath();
/* 287 */     String ctxtPath = this.httpExchange.getHttpContext().getPath();
/* 288 */     if (reqPath.length() > ctxtPath.length()) {
/* 289 */       return reqPath.substring(ctxtPath.length());
/*     */     }
/* 291 */     return null;
/*     */   }
/*     */   
/*     */   @Property({"com.sun.xml.ws.http.exchange"})
/*     */   public HttpExchange getExchange() {
/* 296 */     return this.httpExchange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getBaseAddress() {
/* 307 */     StringBuilder strBuf = new StringBuilder();
/* 308 */     strBuf.append((this.httpExchange instanceof com.sun.net.httpserver.HttpsExchange) ? "https" : "http");
/* 309 */     strBuf.append("://");
/*     */     
/* 311 */     String hostHeader = this.httpExchange.getRequestHeaders().getFirst("Host");
/* 312 */     if (hostHeader != null) {
/* 313 */       strBuf.append(hostHeader);
/*     */     } else {
/* 315 */       strBuf.append(this.httpExchange.getLocalAddress().getHostName());
/* 316 */       strBuf.append(":");
/* 317 */       strBuf.append(this.httpExchange.getLocalAddress().getPort());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 322 */     return strBuf.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getProtocol() {
/* 327 */     return this.httpExchange.getProtocol();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentLengthResponseHeader(int value) {
/* 332 */     this.httpExchange.getResponseHeaders().set("Content-Length", "" + value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestURI() {
/* 337 */     return this.httpExchange.getRequestURI().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestScheme() {
/* 342 */     return (this.httpExchange instanceof com.sun.net.httpserver.HttpsExchange) ? "https" : "http";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getServerName() {
/* 347 */     return this.httpExchange.getLocalAddress().getHostName();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getServerPort() {
/* 352 */     return this.httpExchange.getLocalAddress().getPort();
/*     */   }
/*     */   
/*     */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 356 */     return model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 362 */   private static final BasePropertySet.PropertyMap model = parse(ServerConnectionImpl.class);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\server\ServerConnectionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */