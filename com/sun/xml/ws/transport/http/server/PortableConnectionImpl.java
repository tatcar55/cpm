/*     */ package com.sun.xml.ws.transport.http.server;
/*     */ 
/*     */ import com.oracle.webservices.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.api.message.PropertySet.Property;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WebServiceContextDelegate;
/*     */ import com.sun.xml.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.ws.transport.http.WSHTTPConnection;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Principal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PortableConnectionImpl
/*     */   extends WSHTTPConnection
/*     */   implements WebServiceContextDelegate
/*     */ {
/*     */   private final HttpExchange httpExchange;
/*     */   private int status;
/*     */   private final HttpAdapter adapter;
/*     */   private boolean outputWritten;
/*     */   
/*     */   public PortableConnectionImpl(@NotNull HttpAdapter adapter, @NotNull HttpExchange httpExchange) {
/*  79 */     this.adapter = adapter;
/*  80 */     this.httpExchange = httpExchange;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.headers", "com.sun.xml.ws.api.message.packet.inbound.transport.headers"})
/*     */   @NotNull
/*     */   public Map<String, List<String>> getRequestHeaders() {
/*  86 */     return this.httpExchange.getRequestHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestHeader(String headerName) {
/*  91 */     return this.httpExchange.getRequestHeader(headerName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseHeaders(Map<String, List<String>> headers) {
/*  96 */     Map<String, List<String>> r = this.httpExchange.getResponseHeaders();
/*  97 */     r.clear();
/*  98 */     for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
/*  99 */       String name = entry.getKey();
/* 100 */       List<String> values = entry.getValue();
/*     */       
/* 102 */       if (!name.equalsIgnoreCase("Content-Length") && !name.equalsIgnoreCase("Content-Type")) {
/* 103 */         r.put(name, new ArrayList<String>(values));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseHeader(String key, List<String> value) {
/* 110 */     this.httpExchange.getResponseHeaders().put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getRequestHeaderNames() {
/* 115 */     return this.httpExchange.getRequestHeaders().keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getRequestHeaderValues(String headerName) {
/* 120 */     return this.httpExchange.getRequestHeaders().get(headerName);
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.response.headers", "com.sun.xml.ws.api.message.packet.outbound.transport.headers"})
/*     */   public Map<String, List<String>> getResponseHeaders() {
/* 126 */     return this.httpExchange.getResponseHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentTypeResponseHeader(@NotNull String value) {
/* 131 */     this.httpExchange.addResponseHeader("Content-Type", value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(int status) {
/* 136 */     this.status = status;
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.response.code"})
/*     */   public int getStatus() {
/* 142 */     return this.status;
/*     */   }
/*     */   @NotNull
/*     */   public InputStream getInput() throws IOException {
/* 146 */     return this.httpExchange.getRequestBody();
/*     */   }
/*     */   @NotNull
/*     */   public OutputStream getOutput() throws IOException {
/* 150 */     assert !this.outputWritten;
/* 151 */     this.outputWritten = true;
/*     */     
/* 153 */     this.httpExchange.setStatus(getStatus());
/* 154 */     return this.httpExchange.getResponseBody();
/*     */   }
/*     */   @NotNull
/*     */   public WebServiceContextDelegate getWebServiceContextDelegate() {
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Principal getUserPrincipal(Packet request) {
/* 163 */     return this.httpExchange.getUserPrincipal();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUserInRole(Packet request, String role) {
/* 168 */     return this.httpExchange.isUserInRole(role);
/*     */   }
/*     */   @NotNull
/*     */   public String getEPRAddress(Packet request, WSEndpoint endpoint) {
/* 172 */     PortAddressResolver resolver = this.adapter.owner.createPortAddressResolver(getBaseAddress(), endpoint.getImplementationClass());
/* 173 */     String address = resolver.getAddressFor(endpoint.getServiceName(), endpoint.getPortName().getLocalPart());
/* 174 */     if (address == null) {
/* 175 */       throw new WebServiceException(WsservletMessages.SERVLET_NO_ADDRESS_AVAILABLE(endpoint.getPortName()));
/*     */     }
/* 177 */     return address;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.servlet.context"})
/*     */   public Object getServletContext() {
/* 182 */     return this.httpExchange.getAttribute("javax.xml.ws.servlet.context");
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.servlet.response"})
/*     */   public Object getServletResponse() {
/* 187 */     return this.httpExchange.getAttribute("javax.xml.ws.servlet.response");
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.servlet.request"})
/*     */   public Object getServletRequest() {
/* 192 */     return this.httpExchange.getAttribute("javax.xml.ws.servlet.request");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWSDLAddress(@NotNull Packet request, @NotNull WSEndpoint endpoint) {
/* 197 */     String eprAddress = getEPRAddress(request, endpoint);
/* 198 */     if (this.adapter.getEndpoint().getPort() != null) {
/* 199 */       return eprAddress + "?wsdl";
/*     */     }
/* 201 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSecure() {
/* 207 */     return this.httpExchange.getScheme().equals("https");
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.method"})
/*     */   @NotNull
/*     */   public String getRequestMethod() {
/* 213 */     return this.httpExchange.getRequestMethod();
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.querystring"})
/*     */   public String getQueryString() {
/* 219 */     return this.httpExchange.getQueryString();
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.pathinfo"})
/*     */   public String getPathInfo() {
/* 225 */     return this.httpExchange.getPathInfo();
/*     */   }
/*     */   
/*     */   @Property({"com.sun.xml.ws.http.exchange"})
/*     */   public HttpExchange getExchange() {
/* 230 */     return this.httpExchange;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getBaseAddress() {
/* 235 */     StringBuilder sb = new StringBuilder();
/* 236 */     sb.append(this.httpExchange.getScheme());
/* 237 */     sb.append("://");
/* 238 */     sb.append(this.httpExchange.getLocalAddress().getHostName());
/* 239 */     sb.append(":");
/* 240 */     sb.append(this.httpExchange.getLocalAddress().getPort());
/* 241 */     sb.append(this.httpExchange.getContextPath());
/* 242 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getProtocol() {
/* 247 */     return this.httpExchange.getProtocol();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentLengthResponseHeader(int value) {
/* 252 */     this.httpExchange.addResponseHeader("Content-Length", "" + value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestURI() {
/* 257 */     return this.httpExchange.getRequestURI().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestScheme() {
/* 262 */     return this.httpExchange.getScheme();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getServerName() {
/* 267 */     return this.httpExchange.getLocalAddress().getHostName();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getServerPort() {
/* 272 */     return this.httpExchange.getLocalAddress().getPort();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 277 */     return model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 283 */   private static final BasePropertySet.PropertyMap model = parse(PortableConnectionImpl.class);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\server\PortableConnectionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */