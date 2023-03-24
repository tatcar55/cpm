/*     */ package com.sun.xml.ws.transport.http.client;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.client.ClientTransportException;
/*     */ import com.sun.xml.ws.resources.ClientMessages;
/*     */ import com.sun.xml.ws.transport.Headers;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ 
/*     */ public class HttpClientTransport
/*     */ {
/*  79 */   private static final byte[] THROW_AWAY_BUFFER = new byte[8192];
/*     */   int statusCode;
/*     */   
/*     */   static {
/*     */     try {
/*  84 */       JAXBContext.newInstance(new Class[0]).createUnmarshaller();
/*  85 */     } catch (JAXBException je) {}
/*     */   }
/*     */ 
/*     */   
/*     */   String statusMessage;
/*     */   
/*     */   int contentLength;
/*     */   
/*     */   private final Map<String, List<String>> reqHeaders;
/*  94 */   private Map<String, List<String>> respHeaders = null;
/*     */   
/*     */   private OutputStream outputStream;
/*     */   private boolean https;
/*  98 */   private HttpURLConnection httpConnection = null;
/*     */   
/*     */   private final EndpointAddress endpoint;
/*     */   private final Packet context;
/*     */   private final Integer chunkSize;
/*     */   
/*     */   public HttpClientTransport(@NotNull Packet packet, @NotNull Map<String, List<String>> reqHeaders) {
/* 105 */     this.endpoint = packet.endpointAddress;
/* 106 */     this.context = packet;
/* 107 */     this.reqHeaders = reqHeaders;
/* 108 */     this.chunkSize = (Integer)this.context.invocationProperties.get("com.sun.xml.ws.transport.http.client.streaming.chunk.size");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   OutputStream getOutput() {
/*     */     try {
/* 116 */       createHttpConnection();
/*     */       
/* 118 */       if (requiresOutputStream()) {
/* 119 */         this.outputStream = this.httpConnection.getOutputStream();
/* 120 */         if (this.chunkSize != null) {
/* 121 */           this.outputStream = new WSChunkedOuputStream(this.outputStream, this.chunkSize.intValue());
/*     */         }
/* 123 */         List<String> contentEncoding = this.reqHeaders.get("Content-Encoding");
/*     */         
/* 125 */         if (contentEncoding != null && ((String)contentEncoding.get(0)).contains("gzip")) {
/* 126 */           this.outputStream = new GZIPOutputStream(this.outputStream);
/*     */         }
/*     */       } 
/* 129 */       this.httpConnection.connect();
/* 130 */     } catch (Exception ex) {
/* 131 */       throw new ClientTransportException(ClientMessages.localizableHTTP_CLIENT_FAILED(ex), ex);
/*     */     } 
/*     */ 
/*     */     
/* 135 */     return this.outputStream;
/*     */   }
/*     */   
/*     */   void closeOutput() throws IOException {
/* 139 */     if (this.outputStream != null) {
/* 140 */       this.outputStream.close();
/* 141 */       this.outputStream = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   InputStream getInput() {
/*     */     InputStream in;
/*     */     try {
/* 153 */       in = readResponse();
/* 154 */       if (in != null) {
/* 155 */         String contentEncoding = this.httpConnection.getContentEncoding();
/* 156 */         if (contentEncoding != null && contentEncoding.contains("gzip")) {
/* 157 */           in = new GZIPInputStream(in);
/*     */         }
/*     */       } 
/* 160 */     } catch (IOException e) {
/* 161 */       throw new ClientTransportException(ClientMessages.localizableHTTP_STATUS_CODE(Integer.valueOf(this.statusCode), this.statusMessage), e);
/*     */     } 
/* 163 */     return in;
/*     */   }
/*     */   
/*     */   public Map<String, List<String>> getHeaders() {
/* 167 */     if (this.respHeaders != null) {
/* 168 */       return this.respHeaders;
/*     */     }
/* 170 */     this.respHeaders = (Map<String, List<String>>)new Headers();
/* 171 */     this.respHeaders.putAll(this.httpConnection.getHeaderFields());
/* 172 */     return this.respHeaders;
/*     */   }
/*     */   @Nullable
/*     */   protected InputStream readResponse() {
/*     */     InputStream inputStream1;
/*     */     try {
/* 178 */       inputStream1 = this.httpConnection.getInputStream();
/* 179 */     } catch (IOException ioe) {
/* 180 */       inputStream1 = this.httpConnection.getErrorStream();
/*     */     } 
/* 182 */     if (inputStream1 == null) {
/* 183 */       return inputStream1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 188 */     final InputStream temp = inputStream1;
/* 189 */     return new FilterInputStream(temp)
/*     */       {
/*     */         boolean closed;
/*     */ 
/*     */         
/*     */         public void close() throws IOException {
/* 195 */           if (!this.closed) {
/* 196 */             this.closed = true;
/* 197 */             while (temp.read(HttpClientTransport.THROW_AWAY_BUFFER) != -1);
/* 198 */             super.close();
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected void readResponseCodeAndMessage() {
/*     */     try {
/* 206 */       this.statusCode = this.httpConnection.getResponseCode();
/* 207 */       this.statusMessage = this.httpConnection.getResponseMessage();
/* 208 */       this.contentLength = this.httpConnection.getContentLength();
/* 209 */     } catch (IOException ioe) {
/* 210 */       throw new WebServiceException(ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected HttpURLConnection openConnection(Packet packet) {
/* 216 */     return null;
/*     */   }
/*     */   
/*     */   protected boolean checkHTTPS(HttpURLConnection connection) {
/* 220 */     if (connection instanceof HttpsURLConnection) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 226 */       String verificationProperty = (String)this.context.invocationProperties.get("com.sun.xml.ws.client.http.HostnameVerificationProperty");
/*     */       
/* 228 */       if (verificationProperty != null && 
/* 229 */         verificationProperty.equalsIgnoreCase("true")) {
/* 230 */         ((HttpsURLConnection)connection).setHostnameVerifier(new HttpClientVerifier());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 235 */       HostnameVerifier verifier = (HostnameVerifier)this.context.invocationProperties.get("com.sun.xml.ws.transport.https.client.hostname.verifier");
/*     */       
/* 237 */       if (verifier != null) {
/* 238 */         ((HttpsURLConnection)connection).setHostnameVerifier(verifier);
/*     */       }
/*     */ 
/*     */       
/* 242 */       SSLSocketFactory sslSocketFactory = (SSLSocketFactory)this.context.invocationProperties.get("com.sun.xml.ws.transport.https.client.SSLSocketFactory");
/*     */       
/* 244 */       if (sslSocketFactory != null) {
/* 245 */         ((HttpsURLConnection)connection).setSSLSocketFactory(sslSocketFactory);
/*     */       }
/*     */       
/* 248 */       return true;
/*     */     } 
/* 250 */     return false;
/*     */   }
/*     */   
/*     */   private void createHttpConnection() throws IOException {
/* 254 */     this.httpConnection = openConnection(this.context);
/*     */     
/* 256 */     if (this.httpConnection == null) {
/* 257 */       this.httpConnection = (HttpURLConnection)this.endpoint.openConnection();
/*     */     }
/* 259 */     String scheme = this.endpoint.getURI().getScheme();
/* 260 */     if (scheme.equals("https")) {
/* 261 */       this.https = true;
/*     */     }
/* 263 */     if (checkHTTPS(this.httpConnection)) {
/* 264 */       this.https = true;
/*     */     }
/*     */ 
/*     */     
/* 268 */     this.httpConnection.setAllowUserInteraction(true);
/*     */ 
/*     */     
/* 271 */     this.httpConnection.setDoOutput(true);
/* 272 */     this.httpConnection.setDoInput(true);
/*     */     
/* 274 */     String requestMethod = (String)this.context.invocationProperties.get("javax.xml.ws.http.request.method");
/* 275 */     String method = (requestMethod != null) ? requestMethod : "POST";
/* 276 */     this.httpConnection.setRequestMethod(method);
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
/* 287 */     Integer reqTimeout = (Integer)this.context.invocationProperties.get("com.sun.xml.ws.request.timeout");
/* 288 */     if (reqTimeout != null) {
/* 289 */       this.httpConnection.setReadTimeout(reqTimeout.intValue());
/*     */     }
/*     */     
/* 292 */     Integer connectTimeout = (Integer)this.context.invocationProperties.get("com.sun.xml.ws.connect.timeout");
/* 293 */     if (connectTimeout != null) {
/* 294 */       this.httpConnection.setConnectTimeout(connectTimeout.intValue());
/*     */     }
/*     */     
/* 297 */     Integer chunkSize = (Integer)this.context.invocationProperties.get("com.sun.xml.ws.transport.http.client.streaming.chunk.size");
/* 298 */     if (chunkSize != null) {
/* 299 */       this.httpConnection.setChunkedStreamingMode(chunkSize.intValue());
/*     */     }
/*     */ 
/*     */     
/* 303 */     for (Map.Entry<String, List<String>> entry : this.reqHeaders.entrySet()) {
/* 304 */       if ("Content-Length".equals(entry.getKey()))
/* 305 */         continue;  for (String value : entry.getValue()) {
/* 306 */         this.httpConnection.addRequestProperty(entry.getKey(), value);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isSecure() {
/* 312 */     return this.https;
/*     */   }
/*     */   
/*     */   protected void setStatusCode(int statusCode) {
/* 316 */     this.statusCode = statusCode;
/*     */   }
/*     */   
/*     */   private boolean requiresOutputStream() {
/* 320 */     return (!this.httpConnection.getRequestMethod().equalsIgnoreCase("GET") && !this.httpConnection.getRequestMethod().equalsIgnoreCase("HEAD") && !this.httpConnection.getRequestMethod().equalsIgnoreCase("DELETE"));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   String getContentType() {
/* 326 */     return this.httpConnection.getContentType();
/*     */   }
/*     */   
/*     */   public int getContentLength() {
/* 330 */     return this.httpConnection.getContentLength();
/*     */   }
/*     */   
/*     */   private static class HttpClientVerifier implements HostnameVerifier {
/*     */     private HttpClientVerifier() {}
/*     */     
/*     */     public boolean verify(String s, SSLSession sslSession) {
/* 337 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class LocalhostHttpClientVerifier implements HostnameVerifier {
/*     */     public boolean verify(String s, SSLSession sslSession) {
/* 343 */       return ("localhost".equalsIgnoreCase(s) || "127.0.0.1".equals(s));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class WSChunkedOuputStream
/*     */     extends FilterOutputStream
/*     */   {
/*     */     final int chunkSize;
/*     */ 
/*     */ 
/*     */     
/*     */     WSChunkedOuputStream(OutputStream actual, int chunkSize) {
/* 357 */       super(actual);
/* 358 */       this.chunkSize = chunkSize;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(byte[] b, int off, int len) throws IOException {
/* 363 */       while (len > 0) {
/* 364 */         int sent = (len > this.chunkSize) ? this.chunkSize : len;
/* 365 */         this.out.write(b, off, sent);
/* 366 */         len -= sent;
/* 367 */         off += sent;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\client\HttpClientTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */