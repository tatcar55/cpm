/*     */ package com.sun.xml.ws.transport.http.client;
/*     */ 
/*     */ import com.oracle.webservices.api.message.PropertySet;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.client.ClientTransportException;
/*     */ import com.sun.xml.ws.developer.HttpConfigFeature;
/*     */ import com.sun.xml.ws.resources.ClientMessages;
/*     */ import com.sun.xml.ws.transport.Headers;
/*     */ import com.sun.xml.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.ws.util.RuntimeVersion;
/*     */ import com.sun.xml.ws.util.StreamUtils;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.CookieHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.DatatypeConverter;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpTransportPipe
/*     */   extends AbstractTubeImpl
/*     */ {
/*     */   static {
/*     */     boolean bool;
/*     */   }
/*     */   
/*  82 */   private static final List<String> USER_AGENT = Collections.singletonList(RuntimeVersion.VERSION.toString());
/*  83 */   private static final Logger LOGGER = Logger.getLogger(HttpTransportPipe.class.getName());
/*     */   
/*     */   public static boolean dump;
/*     */   
/*     */   private final Codec codec;
/*     */   
/*     */   private final WSBinding binding;
/*     */   
/*     */   private final CookieHandler cookieJar;
/*     */   
/*     */   private final boolean sticky;
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  98 */       bool = Boolean.getBoolean(HttpTransportPipe.class.getName() + ".dump");
/*  99 */     } catch (Throwable t) {
/* 100 */       bool = false;
/*     */     } 
/* 102 */     dump = bool;
/*     */   }
/*     */   
/*     */   public HttpTransportPipe(Codec codec, WSBinding binding) {
/* 106 */     this.codec = codec;
/* 107 */     this.binding = binding;
/* 108 */     this.sticky = isSticky(binding);
/* 109 */     HttpConfigFeature configFeature = (HttpConfigFeature)binding.getFeature(HttpConfigFeature.class);
/* 110 */     if (configFeature == null) {
/* 111 */       configFeature = new HttpConfigFeature();
/*     */     }
/* 113 */     this.cookieJar = configFeature.getCookieHandler();
/*     */   }
/*     */   
/*     */   private static boolean isSticky(WSBinding binding) {
/* 117 */     boolean tSticky = false;
/* 118 */     WebServiceFeature[] features = binding.getFeatures().toArray();
/* 119 */     for (WebServiceFeature f : features) {
/* 120 */       if (f instanceof com.sun.xml.ws.api.ha.StickyFeature) {
/* 121 */         tSticky = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 125 */     return tSticky;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HttpTransportPipe(HttpTransportPipe that, TubeCloner cloner) {
/* 132 */     this(that.codec.copy(), that.binding);
/* 133 */     cloner.add((Tube)that, (Tube)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(@NotNull Throwable t) {
/* 138 */     return doThrow(t);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(@NotNull Packet request) {
/* 143 */     return doReturnWith(process(request));
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(@NotNull Packet response) {
/* 148 */     return doReturnWith(response);
/*     */   }
/*     */   
/*     */   protected HttpClientTransport getTransport(Packet request, Map<String, List<String>> reqHeaders) {
/* 152 */     return new HttpClientTransport(request, reqHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet process(Packet request) {
/*     */     try {
/* 160 */       Headers<String, List<String>> headers = new Headers();
/*     */       
/* 162 */       Map<String, List<String>> userHeaders = (Map<String, List<String>>)request.invocationProperties.get("javax.xml.ws.http.request.headers");
/* 163 */       boolean addUserAgent = true;
/* 164 */       if (userHeaders != null) {
/*     */         
/* 166 */         headers.putAll(userHeaders);
/*     */         
/* 168 */         if (userHeaders.get("User-Agent") != null) {
/* 169 */           addUserAgent = false;
/*     */         }
/*     */       } 
/* 172 */       if (addUserAgent) {
/* 173 */         headers.put("User-Agent", USER_AGENT);
/*     */       }
/*     */       
/* 176 */       addBasicAuth(request, (Map<String, List<String>>)headers);
/* 177 */       addCookies(request, (Map<String, List<String>>)headers);
/*     */       
/* 179 */       HttpClientTransport con = getTransport(request, (Map<String, List<String>>)headers);
/* 180 */       request.addSatellite((PropertySet)new HttpResponseProperties(con));
/*     */       
/* 182 */       ContentType ct = this.codec.getStaticContentType(request);
/* 183 */       if (ct == null) {
/* 184 */         ByteArrayBuffer buf = new ByteArrayBuffer();
/*     */         
/* 186 */         ct = this.codec.encode(request, (OutputStream)buf);
/*     */         
/* 188 */         headers.put("Content-Length", Collections.singletonList(Integer.toString(buf.size())));
/* 189 */         headers.put("Content-Type", Collections.singletonList(ct.getContentType()));
/* 190 */         if (ct.getAcceptHeader() != null) {
/* 191 */           headers.put("Accept", Collections.singletonList(ct.getAcceptHeader()));
/*     */         }
/* 193 */         if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/* 194 */           writeSOAPAction((Map<String, List<String>>)headers, ct.getSOAPActionHeader());
/*     */         }
/*     */         
/* 197 */         if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 198 */           dump(buf, "HTTP request", (Map<String, List<String>>)headers);
/*     */         }
/*     */         
/* 201 */         buf.writeTo(con.getOutput());
/*     */       } else {
/*     */         
/* 204 */         headers.put("Content-Type", Collections.singletonList(ct.getContentType()));
/* 205 */         if (ct.getAcceptHeader() != null) {
/* 206 */           headers.put("Accept", Collections.singletonList(ct.getAcceptHeader()));
/*     */         }
/* 208 */         if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/* 209 */           writeSOAPAction((Map<String, List<String>>)headers, ct.getSOAPActionHeader());
/*     */         }
/*     */         
/* 212 */         if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 213 */           ByteArrayBuffer buf = new ByteArrayBuffer();
/* 214 */           this.codec.encode(request, (OutputStream)buf);
/* 215 */           dump(buf, "HTTP request - " + request.endpointAddress, (Map<String, List<String>>)headers);
/* 216 */           OutputStream out = con.getOutput();
/* 217 */           if (out != null) {
/* 218 */             buf.writeTo(out);
/*     */           }
/*     */         } else {
/* 221 */           OutputStream os = con.getOutput();
/* 222 */           if (os != null) {
/* 223 */             this.codec.encode(request, os);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 228 */       con.closeOutput();
/*     */       
/* 230 */       return createResponsePacket(request, con);
/* 231 */     } catch (WebServiceException wex) {
/* 232 */       throw wex;
/* 233 */     } catch (Exception ex) {
/* 234 */       throw new WebServiceException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Packet createResponsePacket(Packet request, HttpClientTransport con) throws IOException {
/* 239 */     con.readResponseCodeAndMessage();
/* 240 */     recordCookies(request, con);
/*     */     
/* 242 */     InputStream responseStream = con.getInput();
/* 243 */     if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 244 */       ByteArrayBuffer buf = new ByteArrayBuffer();
/* 245 */       if (responseStream != null) {
/* 246 */         buf.write(responseStream);
/* 247 */         responseStream.close();
/*     */       } 
/* 249 */       dump(buf, "HTTP response - " + request.endpointAddress + " - " + con.statusCode, con.getHeaders());
/* 250 */       responseStream = buf.newInputStream();
/*     */     } 
/*     */ 
/*     */     
/* 254 */     int cl = con.contentLength;
/* 255 */     InputStream tempIn = null;
/* 256 */     if (cl == -1) {
/* 257 */       tempIn = StreamUtils.hasSomeData(responseStream);
/* 258 */       if (tempIn != null) {
/* 259 */         responseStream = tempIn;
/*     */       }
/*     */     } 
/* 262 */     if ((cl == 0 || (cl == -1 && tempIn == null)) && 
/* 263 */       responseStream != null) {
/* 264 */       responseStream.close();
/* 265 */       responseStream = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     checkStatusCode(responseStream, con);
/*     */     
/* 274 */     Packet reply = request.createClientResponse(null);
/* 275 */     reply.wasTransportSecure = con.isSecure();
/* 276 */     if (responseStream != null) {
/* 277 */       String contentType = con.getContentType();
/* 278 */       if (contentType != null && contentType.contains("text/html") && this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/* 279 */         throw new ClientTransportException(ClientMessages.localizableHTTP_STATUS_CODE(Integer.valueOf(con.statusCode), con.statusMessage));
/*     */       }
/* 281 */       this.codec.decode(responseStream, contentType, reply);
/*     */     } 
/* 283 */     return reply;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkStatusCode(InputStream in, HttpClientTransport con) throws IOException {
/* 295 */     int statusCode = con.statusCode;
/* 296 */     String statusMessage = con.statusMessage;
/*     */     
/* 298 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/* 299 */       if (this.binding.getSOAPVersion() == SOAPVersion.SOAP_12) {
/*     */         
/* 301 */         if (statusCode == 200 || statusCode == 202 || isErrorCode(statusCode)) {
/*     */           
/* 303 */           if (isErrorCode(statusCode) && in == null)
/*     */           {
/* 305 */             throw new ClientTransportException(ClientMessages.localizableHTTP_STATUS_CODE(Integer.valueOf(statusCode), statusMessage));
/*     */           }
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/* 311 */       } else if (statusCode == 200 || statusCode == 202 || statusCode == 500) {
/*     */         
/* 313 */         if (statusCode == 500 && in == null)
/*     */         {
/* 315 */           throw new ClientTransportException(ClientMessages.localizableHTTP_STATUS_CODE(Integer.valueOf(statusCode), statusMessage));
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 320 */       if (in != null) {
/* 321 */         in.close();
/*     */       }
/* 323 */       throw new ClientTransportException(ClientMessages.localizableHTTP_STATUS_CODE(Integer.valueOf(statusCode), statusMessage));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isErrorCode(int code) {
/* 330 */     return (code == 500 || code == 400);
/*     */   }
/*     */   
/*     */   private void addCookies(Packet context, Map<String, List<String>> reqHeaders) throws IOException {
/* 334 */     Boolean shouldMaintainSessionProperty = (Boolean)context.invocationProperties.get("javax.xml.ws.session.maintain");
/*     */     
/* 336 */     if (shouldMaintainSessionProperty != null && !shouldMaintainSessionProperty.booleanValue()) {
/*     */       return;
/*     */     }
/* 339 */     if (this.sticky || (shouldMaintainSessionProperty != null && shouldMaintainSessionProperty.booleanValue())) {
/* 340 */       Map<String, List<String>> rememberedCookies = this.cookieJar.get(context.endpointAddress.getURI(), reqHeaders);
/* 341 */       processCookieHeaders(reqHeaders, rememberedCookies, "Cookie");
/* 342 */       processCookieHeaders(reqHeaders, rememberedCookies, "Cookie2");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processCookieHeaders(Map<String, List<String>> requestHeaders, Map<String, List<String>> rememberedCookies, String cookieHeader) {
/* 347 */     List<String> jarCookies = rememberedCookies.get(cookieHeader);
/* 348 */     if (jarCookies != null && !jarCookies.isEmpty()) {
/* 349 */       List<String> resultCookies = mergeUserCookies(jarCookies, requestHeaders.get(cookieHeader));
/* 350 */       requestHeaders.put(cookieHeader, resultCookies);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> mergeUserCookies(List<String> rememberedCookies, List<String> userCookies) {
/* 357 */     if (userCookies == null || userCookies.isEmpty()) {
/* 358 */       return rememberedCookies;
/*     */     }
/*     */     
/* 361 */     Map<String, String> map = new HashMap<String, String>();
/* 362 */     cookieListToMap(rememberedCookies, map);
/* 363 */     cookieListToMap(userCookies, map);
/*     */     
/* 365 */     return new ArrayList<String>(map.values());
/*     */   }
/*     */   
/*     */   private void cookieListToMap(List<String> cookieList, Map<String, String> targetMap) {
/* 369 */     for (String cookie : cookieList) {
/* 370 */       int index = cookie.indexOf("=");
/* 371 */       String cookieName = cookie.substring(0, index);
/* 372 */       targetMap.put(cookieName, cookie);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void recordCookies(Packet context, HttpClientTransport con) throws IOException {
/* 377 */     Boolean shouldMaintainSessionProperty = (Boolean)context.invocationProperties.get("javax.xml.ws.session.maintain");
/*     */     
/* 379 */     if (shouldMaintainSessionProperty != null && !shouldMaintainSessionProperty.booleanValue()) {
/*     */       return;
/*     */     }
/* 382 */     if (this.sticky || (shouldMaintainSessionProperty != null && shouldMaintainSessionProperty.booleanValue())) {
/* 383 */       this.cookieJar.put(context.endpointAddress.getURI(), con.getHeaders());
/*     */     }
/*     */   }
/*     */   
/*     */   private void addBasicAuth(Packet context, Map<String, List<String>> reqHeaders) {
/* 388 */     String user = (String)context.invocationProperties.get("javax.xml.ws.security.auth.username");
/* 389 */     if (user != null) {
/* 390 */       String pw = (String)context.invocationProperties.get("javax.xml.ws.security.auth.password");
/* 391 */       if (pw != null) {
/* 392 */         StringBuilder buf = new StringBuilder(user);
/* 393 */         buf.append(":");
/* 394 */         buf.append(pw);
/* 395 */         String creds = DatatypeConverter.printBase64Binary(buf.toString().getBytes());
/* 396 */         reqHeaders.put("Authorization", Collections.singletonList("Basic " + creds));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeSOAPAction(Map<String, List<String>> reqHeaders, String soapAction) {
/* 407 */     if (SOAPVersion.SOAP_12.equals(this.binding.getSOAPVersion())) {
/*     */       return;
/*     */     }
/* 410 */     if (soapAction != null) {
/* 411 */       reqHeaders.put("SOAPAction", Collections.singletonList(soapAction));
/*     */     } else {
/* 413 */       reqHeaders.put("SOAPAction", Collections.singletonList("\"\""));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void preDestroy() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpTransportPipe copy(TubeCloner cloner) {
/* 424 */     return new HttpTransportPipe(this, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   private void dump(ByteArrayBuffer buf, String caption, Map<String, List<String>> headers) throws IOException {
/* 429 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 430 */     PrintWriter pw = new PrintWriter(baos, true);
/* 431 */     pw.println("---[" + caption + "]---");
/* 432 */     for (Map.Entry<String, List<String>> header : headers.entrySet()) {
/* 433 */       if (((List)header.getValue()).isEmpty()) {
/*     */ 
/*     */         
/* 436 */         pw.println(header.getValue()); continue;
/*     */       } 
/* 438 */       for (String value : header.getValue()) {
/* 439 */         pw.println((String)header.getKey() + ": " + value);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 444 */     buf.writeTo(baos);
/* 445 */     pw.println("--------------------");
/*     */     
/* 447 */     String msg = baos.toString();
/* 448 */     if (dump) {
/* 449 */       System.out.println(msg);
/*     */     }
/* 451 */     if (LOGGER.isLoggable(Level.FINER))
/* 452 */       LOGGER.log(Level.FINER, msg); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\client\HttpTransportPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */