/*     */ package com.sun.xml.ws.transport.http;
/*     */ 
/*     */ import com.oracle.webservices.api.message.PropertySet;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.Component;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.NonAnonymousResponseProcessor;
/*     */ import com.sun.xml.ws.api.ha.HaInfo;
/*     */ import com.sun.xml.ws.api.message.ExceptionHasMessage;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.server.AbstractServerAsyncTransport;
/*     */ import com.sun.xml.ws.api.server.Adapter;
/*     */ import com.sun.xml.ws.api.server.BoundEndpoint;
/*     */ import com.sun.xml.ws.api.server.DocumentAddressResolver;
/*     */ import com.sun.xml.ws.api.server.Module;
/*     */ import com.sun.xml.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.ws.api.server.SDDocument;
/*     */ import com.sun.xml.ws.api.server.ServiceDefinition;
/*     */ import com.sun.xml.ws.api.server.TransportBackChannel;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WebServiceContextDelegate;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.ws.server.UnsupportedMediaException;
/*     */ import com.sun.xml.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.ws.util.Pool;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpAdapter
/*     */   extends Adapter<HttpAdapter.HttpToolkit>
/*     */ {
/*     */   protected Map<String, SDDocument> wsdls;
/*     */   private Map<SDDocument, String> revWsdls;
/* 122 */   private ServiceDefinition serviceDefinition = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final HttpAdapterList<? extends HttpAdapter> owner;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String urlPattern;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean stickyCookie;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disableJreplicaCookie = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpAdapter createAlone(WSEndpoint<?> endpoint) {
/* 146 */     return (new DummyList()).createAdapter("", "", endpoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpAdapter(WSEndpoint endpoint, HttpAdapterList<? extends HttpAdapter> owner) {
/* 156 */     this(endpoint, owner, (String)null);
/*     */   }
/*     */   
/*     */   protected HttpAdapter(WSEndpoint endpoint, HttpAdapterList<? extends HttpAdapter> owner, String urlPattern) {
/* 160 */     super(endpoint);
/* 161 */     this.owner = owner;
/* 162 */     this.urlPattern = urlPattern;
/*     */     
/* 164 */     initWSDLMap(endpoint.getServiceDefinition());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceDefinition getServiceDefinition() {
/* 173 */     return this.serviceDefinition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void initWSDLMap(ServiceDefinition sdef) {
/* 182 */     this.serviceDefinition = sdef;
/* 183 */     if (sdef == null) {
/* 184 */       this.wsdls = Collections.emptyMap();
/* 185 */       this.revWsdls = Collections.emptyMap();
/*     */     } else {
/* 187 */       this.wsdls = new HashMap<String, SDDocument>();
/*     */ 
/*     */       
/* 190 */       Map<String, SDDocument> systemIds = new TreeMap<String, SDDocument>();
/* 191 */       for (SDDocument sdd : sdef) {
/* 192 */         if (sdd == sdef.getPrimary()) {
/* 193 */           this.wsdls.put("wsdl", sdd);
/* 194 */           this.wsdls.put("WSDL", sdd); continue;
/*     */         } 
/* 196 */         systemIds.put(sdd.getURL().toString(), sdd);
/*     */       } 
/*     */ 
/*     */       
/* 200 */       int wsdlnum = 1;
/* 201 */       int xsdnum = 1;
/* 202 */       for (Map.Entry<String, SDDocument> e : systemIds.entrySet()) {
/* 203 */         SDDocument sdd = e.getValue();
/* 204 */         if (sdd.isWSDL()) {
/* 205 */           this.wsdls.put("wsdl=" + wsdlnum++, sdd);
/*     */         }
/* 207 */         if (sdd.isSchema()) {
/* 208 */           this.wsdls.put("xsd=" + xsdnum++, sdd);
/*     */         }
/*     */       } 
/*     */       
/* 212 */       this.revWsdls = new HashMap<SDDocument, String>();
/* 213 */       for (Map.Entry<String, SDDocument> e : this.wsdls.entrySet()) {
/* 214 */         if (!((String)e.getKey()).equals("WSDL")) {
/* 215 */           this.revWsdls.put(e.getValue(), e.getKey());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValidPath() {
/* 226 */     if (this.urlPattern.endsWith("/*")) {
/* 227 */       return this.urlPattern.substring(0, this.urlPattern.length() - 2);
/*     */     }
/* 229 */     return this.urlPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpToolkit createToolkit() {
/* 235 */     return new HttpToolkit();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@NotNull WSHTTPConnection connection) throws IOException {
/* 257 */     if (handleGet(connection)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 262 */     Pool<HttpToolkit> currentPool = getPool();
/*     */     
/* 264 */     HttpToolkit tk = (HttpToolkit)currentPool.take();
/*     */     try {
/* 266 */       tk.handle(connection);
/*     */     } finally {
/* 268 */       currentPool.recycle(tk);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean handleGet(@NotNull WSHTTPConnection connection) throws IOException {
/* 273 */     if (connection.getRequestMethod().equals("GET")) {
/*     */       
/* 275 */       for (Component c : this.endpoint.getComponents()) {
/* 276 */         HttpMetadataPublisher spi = (HttpMetadataPublisher)c.getSPI(HttpMetadataPublisher.class);
/* 277 */         if (spi != null && spi.handleMetadataRequest(this, connection)) {
/* 278 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 282 */       if (isMetadataQuery(connection.getQueryString())) {
/*     */         
/* 284 */         publishWSDL(connection);
/* 285 */         return true;
/*     */       } 
/*     */       
/* 288 */       WSBinding wSBinding = getEndpoint().getBinding();
/* 289 */       if (!(wSBinding instanceof javax.xml.ws.http.HTTPBinding)) {
/*     */         
/* 291 */         writeWebServicesHtmlPage(connection);
/* 292 */         return true;
/*     */       } 
/* 294 */     } else if (connection.getRequestMethod().equals("HEAD")) {
/* 295 */       connection.getInput().close();
/* 296 */       WSBinding wSBinding = getEndpoint().getBinding();
/* 297 */       if (isMetadataQuery(connection.getQueryString())) {
/* 298 */         SDDocument doc = this.wsdls.get(connection.getQueryString());
/* 299 */         connection.setStatus((doc != null) ? 200 : 404);
/*     */ 
/*     */         
/* 302 */         connection.getOutput().close();
/* 303 */         connection.close();
/* 304 */         return true;
/* 305 */       }  if (!(wSBinding instanceof javax.xml.ws.http.HTTPBinding)) {
/* 306 */         connection.setStatus(404);
/* 307 */         connection.getOutput().close();
/* 308 */         connection.close();
/* 309 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 314 */     return false;
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
/*     */   
/*     */   private Packet decodePacket(@NotNull WSHTTPConnection con, @NotNull Codec codec) throws IOException {
/* 327 */     String ct = con.getRequestHeader("Content-Type");
/* 328 */     InputStream in = con.getInput();
/* 329 */     Packet packet = new Packet();
/* 330 */     packet.soapAction = fixQuotesAroundSoapAction(con.getRequestHeader("SOAPAction"));
/* 331 */     packet.wasTransportSecure = con.isSecure();
/* 332 */     packet.acceptableMimeTypes = con.getRequestHeader("Accept");
/* 333 */     packet.addSatellite((PropertySet)con);
/* 334 */     addSatellites(packet);
/* 335 */     packet.isAdapterDeliversNonAnonymousResponse = true;
/* 336 */     packet.component = (Component)this;
/* 337 */     packet.transportBackChannel = new Oneway(con);
/* 338 */     packet.webServiceContextDelegate = con.getWebServiceContextDelegate();
/* 339 */     packet.setState(Packet.State.ServerRequest);
/* 340 */     if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 341 */       ByteArrayBuffer buf = new ByteArrayBuffer();
/* 342 */       buf.write(in);
/* 343 */       in.close();
/* 344 */       dump(buf, "HTTP request", con.getRequestHeaders());
/* 345 */       in = buf.newInputStream();
/*     */     } 
/* 347 */     codec.decode(in, ct, packet);
/* 348 */     return packet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addSatellites(Packet packet) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fixQuotesAroundSoapAction(String soapAction) {
/* 362 */     if (soapAction != null && (!soapAction.startsWith("\"") || !soapAction.endsWith("\""))) {
/* 363 */       if (LOGGER.isLoggable(Level.INFO)) {
/* 364 */         LOGGER.log(Level.INFO, "Received WS-I BP non-conformant Unquoted SoapAction HTTP header: {0}", soapAction);
/*     */       }
/* 366 */       String fixedSoapAction = soapAction;
/* 367 */       if (!soapAction.startsWith("\"")) {
/* 368 */         fixedSoapAction = "\"" + fixedSoapAction;
/*     */       }
/* 370 */       if (!soapAction.endsWith("\"")) {
/* 371 */         fixedSoapAction = fixedSoapAction + "\"";
/*     */       }
/* 373 */       return fixedSoapAction;
/*     */     } 
/* 375 */     return soapAction;
/*     */   }
/*     */   
/*     */   protected NonAnonymousResponseProcessor getNonAnonymousResponseProcessor() {
/* 379 */     return NonAnonymousResponseProcessor.getDefault();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeClientError(int connStatus, @NotNull OutputStream os, @NotNull Packet packet) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isClientErrorStatus(int connStatus) {
/* 393 */     return (connStatus == 403);
/*     */   }
/*     */   
/*     */   private boolean isNonAnonymousUri(EndpointAddress addr) {
/* 397 */     return (addr != null && !addr.toString().equals(AddressingVersion.W3C.anonymousUri) && !addr.toString().equals(AddressingVersion.MEMBER.anonymousUri));
/*     */   }
/*     */ 
/*     */   
/*     */   private void encodePacket(@NotNull Packet packet, @NotNull WSHTTPConnection con, @NotNull Codec codec) throws IOException {
/* 402 */     if (isNonAnonymousUri(packet.endpointAddress) && packet.getMessage() != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/* 408 */         packet = getNonAnonymousResponseProcessor().process(packet);
/* 409 */       } catch (RuntimeException re) {
/*     */ 
/*     */         
/* 412 */         SOAPVersion soapVersion = packet.getBinding().getSOAPVersion();
/* 413 */         Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, re);
/* 414 */         packet = packet.createServerResponse(faultMsg, packet.endpoint.getPort(), null, packet.endpoint.getBinding());
/*     */       } 
/*     */     }
/*     */     
/* 418 */     if (con.isClosed()) {
/*     */       return;
/*     */     }
/* 421 */     Message responseMessage = packet.getMessage();
/* 422 */     addStickyCookie(con);
/* 423 */     addReplicaCookie(con, packet);
/* 424 */     if (responseMessage == null) {
/* 425 */       if (!con.isClosed()) {
/*     */ 
/*     */         
/* 428 */         if (con.getStatus() == 0) {
/* 429 */           con.setStatus(202);
/*     */         }
/*     */         
/*     */         try {
/* 433 */           con.getOutput().close();
/* 434 */         } catch (IOException e) {
/* 435 */           throw new WebServiceException(e);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 439 */       if (con.getStatus() == 0)
/*     */       {
/*     */         
/* 442 */         con.setStatus(responseMessage.isFault() ? 500 : 200);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 447 */       if (isClientErrorStatus(con.getStatus())) {
/* 448 */         OutputStream os = con.getOutput();
/* 449 */         writeClientError(con.getStatus(), os, packet);
/* 450 */         os.close();
/*     */         
/*     */         return;
/*     */       } 
/* 454 */       ContentType contentType = codec.getStaticContentType(packet);
/* 455 */       if (contentType != null) {
/* 456 */         con.setContentTypeResponseHeader(contentType.getContentType());
/* 457 */         OutputStream os = con.getProtocol().contains("1.1") ? con.getOutput() : (OutputStream)new Http10OutputStream(con);
/* 458 */         if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 459 */           ByteArrayBuffer buf = new ByteArrayBuffer();
/* 460 */           codec.encode(packet, (OutputStream)buf);
/* 461 */           dump(buf, "HTTP response " + con.getStatus(), con.getResponseHeaders());
/* 462 */           buf.writeTo(os);
/*     */         } else {
/* 464 */           codec.encode(packet, os);
/*     */         } 
/* 466 */         os.close();
/*     */       } else {
/*     */         
/* 469 */         ByteArrayBuffer buf = new ByteArrayBuffer();
/* 470 */         contentType = codec.encode(packet, (OutputStream)buf);
/* 471 */         con.setContentTypeResponseHeader(contentType.getContentType());
/* 472 */         if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 473 */           dump(buf, "HTTP response " + con.getStatus(), con.getResponseHeaders());
/*     */         }
/* 475 */         OutputStream os = con.getOutput();
/* 476 */         buf.writeTo(os);
/* 477 */         os.close();
/*     */       } 
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addStickyCookie(WSHTTPConnection con) {
/* 495 */     if (this.stickyCookie) {
/* 496 */       String proxyJroute = con.getRequestHeader("proxy-jroute");
/* 497 */       if (proxyJroute == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 502 */       String jrouteId = con.getCookie("JROUTE");
/* 503 */       if (jrouteId == null || !jrouteId.equals(proxyJroute))
/*     */       {
/* 505 */         con.setCookie("JROUTE", proxyJroute);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addReplicaCookie(WSHTTPConnection con, Packet packet) {
/* 511 */     if (this.stickyCookie) {
/* 512 */       HaInfo haInfo = null;
/* 513 */       if (packet.supports("com.sun.xml.ws.api.message.packet.hainfo")) {
/* 514 */         haInfo = (HaInfo)packet.get("com.sun.xml.ws.api.message.packet.hainfo");
/*     */       }
/* 516 */       if (haInfo != null) {
/* 517 */         con.setCookie("METRO_KEY", haInfo.getKey());
/* 518 */         if (!this.disableJreplicaCookie) {
/* 519 */           con.setCookie("JREPLICA", haInfo.getReplicaInstance());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invokeAsync(WSHTTPConnection con) throws IOException {
/* 526 */     invokeAsync(con, NO_OP_COMPLETION_CALLBACK);
/*     */   }
/*     */   
/*     */   public void invokeAsync(final WSHTTPConnection con, final CompletionCallback callback) throws IOException {
/*     */     Packet request;
/* 531 */     if (handleGet(con)) {
/* 532 */       callback.onCompletion();
/*     */       return;
/*     */     } 
/* 535 */     final Pool<HttpToolkit> currentPool = getPool();
/* 536 */     final HttpToolkit tk = (HttpToolkit)currentPool.take();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 541 */       request = decodePacket(con, tk.codec);
/* 542 */     } catch (ExceptionHasMessage e) {
/* 543 */       LOGGER.log(Level.SEVERE, e.getMessage(), (Throwable)e);
/* 544 */       Packet response = new Packet();
/* 545 */       response.setMessage(e.getFaultMessage());
/* 546 */       encodePacket(response, con, tk.codec);
/* 547 */       currentPool.recycle(tk);
/* 548 */       con.close();
/* 549 */       callback.onCompletion();
/*     */       return;
/* 551 */     } catch (UnsupportedMediaException e) {
/* 552 */       LOGGER.log(Level.SEVERE, e.getMessage(), (Throwable)e);
/* 553 */       Packet response = new Packet();
/* 554 */       con.setStatus(415);
/* 555 */       encodePacket(response, con, tk.codec);
/* 556 */       currentPool.recycle(tk);
/* 557 */       con.close();
/* 558 */       callback.onCompletion();
/*     */       
/*     */       return;
/*     */     } 
/* 562 */     this.endpoint.process(request, new WSEndpoint.CompletionCallback()
/*     */         {
/*     */           public void onCompletion(@NotNull Packet response) {
/*     */             try {
/*     */               try {
/* 567 */                 HttpAdapter.this.encodePacket(response, con, tk.codec);
/* 568 */               } catch (IOException ioe) {
/* 569 */                 HttpAdapter.LOGGER.log(Level.SEVERE, ioe.getMessage(), ioe);
/*     */               } 
/* 571 */               currentPool.recycle(tk);
/*     */             } finally {
/* 573 */               con.close();
/* 574 */               callback.onCompletion();
/*     */             } 
/*     */           }
/*     */         }null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 582 */   public static final CompletionCallback NO_OP_COMPLETION_CALLBACK = new CompletionCallback()
/*     */     {
/*     */       public void onCompletion() {}
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final class AsyncTransport
/*     */     extends AbstractServerAsyncTransport<WSHTTPConnection>
/*     */   {
/*     */     public AsyncTransport() {
/* 597 */       super(HttpAdapter.this.endpoint);
/*     */     }
/*     */     
/*     */     public void handleAsync(WSHTTPConnection con) throws IOException {
/* 601 */       handle(con);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void encodePacket(WSHTTPConnection con, @NotNull Packet packet, @NotNull Codec codec) throws IOException {
/* 606 */       HttpAdapter.this.encodePacket(packet, con, codec);
/*     */     }
/*     */     @Nullable
/*     */     protected String getAcceptableMimeTypes(WSHTTPConnection con) {
/* 610 */       return null;
/*     */     }
/*     */     @Nullable
/*     */     protected TransportBackChannel getTransportBackChannel(WSHTTPConnection con) {
/* 614 */       return new HttpAdapter.Oneway(con);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     protected PropertySet getPropertySet(WSHTTPConnection con) {
/* 619 */       return (PropertySet)con;
/*     */     }
/*     */     @NotNull
/*     */     protected WebServiceContextDelegate getWebServiceContextDelegate(WSHTTPConnection con) {
/* 623 */       return con.getWebServiceContextDelegate();
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Oneway implements TransportBackChannel {
/*     */     WSHTTPConnection con;
/*     */     boolean closed;
/*     */     
/*     */     Oneway(WSHTTPConnection con) {
/* 632 */       this.con = con;
/*     */     }
/*     */     
/*     */     public void close() {
/* 636 */       if (!this.closed) {
/* 637 */         this.closed = true;
/*     */         
/* 639 */         if (this.con.getStatus() == 0)
/*     */         {
/*     */           
/* 642 */           this.con.setStatus(202);
/*     */         }
/*     */         
/* 645 */         OutputStream output = null;
/*     */         try {
/* 647 */           output = this.con.getOutput();
/* 648 */         } catch (IOException e) {}
/*     */ 
/*     */ 
/*     */         
/* 652 */         if (output != null) {
/*     */           try {
/* 654 */             output.close();
/* 655 */           } catch (IOException e) {
/* 656 */             throw new WebServiceException(e);
/*     */           } 
/*     */         }
/* 659 */         this.con.close();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 664 */   final class HttpToolkit extends Adapter.Toolkit { HttpToolkit() { super(HttpAdapter.this); } public void handle(WSHTTPConnection con) throws IOException {
/*     */       try {
/*     */         Packet packet;
/* 667 */         boolean invoke = false;
/*     */         
/*     */         try {
/* 670 */           packet = HttpAdapter.this.decodePacket(con, this.codec);
/* 671 */           invoke = true;
/* 672 */         } catch (Exception e) {
/* 673 */           packet = new Packet();
/* 674 */           if (e instanceof ExceptionHasMessage) {
/* 675 */             HttpAdapter.LOGGER.log(Level.SEVERE, e.getMessage(), e);
/* 676 */             packet.setMessage(((ExceptionHasMessage)e).getFaultMessage());
/* 677 */           } else if (e instanceof UnsupportedMediaException) {
/* 678 */             HttpAdapter.LOGGER.log(Level.SEVERE, e.getMessage(), e);
/* 679 */             con.setStatus(415);
/*     */           } else {
/* 681 */             HttpAdapter.LOGGER.log(Level.SEVERE, e.getMessage(), e);
/* 682 */             con.setStatus(500);
/*     */           } 
/*     */         } 
/* 685 */         if (invoke) {
/*     */           try {
/* 687 */             packet = this.head.process(packet, con.getWebServiceContextDelegate(), packet.transportBackChannel);
/*     */           }
/* 689 */           catch (Exception e) {
/* 690 */             HttpAdapter.LOGGER.log(Level.SEVERE, e.getMessage(), e);
/* 691 */             if (!con.isClosed()) {
/* 692 */               HttpAdapter.this.writeInternalServerError(con);
/*     */             }
/*     */             return;
/*     */           } 
/*     */         }
/* 697 */         HttpAdapter.this.encodePacket(packet, con, this.codec);
/*     */       } finally {
/* 699 */         if (!con.isClosed()) {
/* 700 */           con.close();
/*     */         }
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMetadataQuery(String query) {
/* 718 */     return (query != null && (query.equals("WSDL") || query.startsWith("wsdl") || query.startsWith("xsd=")));
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
/*     */   
/*     */   public void publishWSDL(@NotNull WSHTTPConnection con) throws IOException {
/* 731 */     con.getInput().close();
/*     */     
/* 733 */     SDDocument doc = this.wsdls.get(con.getQueryString());
/* 734 */     if (doc == null) {
/* 735 */       writeNotFoundErrorPage(con, "Invalid Request");
/*     */       
/*     */       return;
/*     */     } 
/* 739 */     con.setStatus(200);
/* 740 */     con.setContentTypeResponseHeader("text/xml;charset=utf-8");
/*     */     
/* 742 */     OutputStream os = con.getProtocol().contains("1.1") ? con.getOutput() : (OutputStream)new Http10OutputStream(con);
/*     */     
/* 744 */     PortAddressResolver portAddressResolver = getPortAddressResolver(con.getBaseAddress());
/* 745 */     DocumentAddressResolver resolver = getDocumentAddressResolver(portAddressResolver);
/*     */     
/* 747 */     doc.writeTo(portAddressResolver, resolver, os);
/* 748 */     os.close();
/*     */   }
/*     */   
/*     */   public PortAddressResolver getPortAddressResolver(String baseAddress) {
/* 752 */     return this.owner.createPortAddressResolver(baseAddress, this.endpoint.getImplementationClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public DocumentAddressResolver getDocumentAddressResolver(PortAddressResolver portAddressResolver) {
/* 757 */     final String address = portAddressResolver.getAddressFor(this.endpoint.getServiceName(), this.endpoint.getPortName().getLocalPart());
/* 758 */     assert address != null;
/* 759 */     return new DocumentAddressResolver()
/*     */       {
/*     */         public String getRelativeAddressFor(@NotNull SDDocument current, @NotNull SDDocument referenced)
/*     */         {
/* 763 */           assert HttpAdapter.this.revWsdls.containsKey(referenced);
/* 764 */           return address + '?' + (String)HttpAdapter.this.revWsdls.get(referenced);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Http10OutputStream
/*     */     extends ByteArrayBuffer
/*     */   {
/*     */     private final WSHTTPConnection con;
/*     */ 
/*     */     
/*     */     Http10OutputStream(WSHTTPConnection con) {
/* 777 */       this.con = con;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 782 */       super.close();
/* 783 */       this.con.setContentLengthResponseHeader(size());
/* 784 */       OutputStream os = this.con.getOutput();
/* 785 */       writeTo(os);
/* 786 */       os.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private void writeNotFoundErrorPage(WSHTTPConnection con, String message) throws IOException {
/* 791 */     con.setStatus(404);
/* 792 */     con.setContentTypeResponseHeader("text/html; charset=utf-8");
/*     */     
/* 794 */     PrintWriter out = new PrintWriter(new OutputStreamWriter(con.getOutput(), "UTF-8"));
/* 795 */     out.println("<html>");
/* 796 */     out.println("<head><title>");
/* 797 */     out.println(WsservletMessages.SERVLET_HTML_TITLE());
/* 798 */     out.println("</title></head>");
/* 799 */     out.println("<body>");
/* 800 */     out.println(WsservletMessages.SERVLET_HTML_NOT_FOUND(message));
/* 801 */     out.println("</body>");
/* 802 */     out.println("</html>");
/* 803 */     out.close();
/*     */   }
/*     */   
/*     */   private void writeInternalServerError(WSHTTPConnection con) throws IOException {
/* 807 */     con.setStatus(500);
/* 808 */     con.getOutput().close();
/*     */   }
/*     */   
/*     */   private static final class DummyList extends HttpAdapterList<HttpAdapter> { private DummyList() {}
/*     */     
/*     */     protected HttpAdapter createHttpAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
/* 814 */       return new HttpAdapter(endpoint, this, urlPattern);
/*     */     } }
/*     */ 
/*     */   
/*     */   private void dump(ByteArrayBuffer buf, String caption, Map<String, List<String>> headers) throws IOException {
/* 819 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 820 */     PrintWriter pw = new PrintWriter(baos, true);
/* 821 */     pw.println("---[" + caption + "]---");
/* 822 */     if (headers != null) {
/* 823 */       for (Map.Entry<String, List<String>> header : headers.entrySet()) {
/* 824 */         if (((List)header.getValue()).isEmpty()) {
/*     */ 
/*     */           
/* 827 */           pw.println(header.getValue()); continue;
/*     */         } 
/* 829 */         for (String value : header.getValue()) {
/* 830 */           pw.println((String)header.getKey() + ": " + value);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 835 */     buf.writeTo(baos);
/* 836 */     pw.println("--------------------");
/*     */     
/* 838 */     String msg = baos.toString();
/* 839 */     if (dump) {
/* 840 */       System.out.println(msg);
/*     */     }
/* 842 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 843 */       LOGGER.log(Level.FINER, msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeWebServicesHtmlPage(WSHTTPConnection con) throws IOException {
/* 851 */     if (!publishStatusPage) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 857 */     con.getInput().close();
/*     */ 
/*     */     
/* 860 */     con.setStatus(200);
/* 861 */     con.setContentTypeResponseHeader("text/html; charset=utf-8");
/*     */     
/* 863 */     PrintWriter out = new PrintWriter(new OutputStreamWriter(con.getOutput(), "UTF-8"));
/* 864 */     out.println("<html>");
/* 865 */     out.println("<head><title>");
/*     */     
/* 867 */     out.println(WsservletMessages.SERVLET_HTML_TITLE());
/* 868 */     out.println("</title></head>");
/* 869 */     out.println("<body>");
/*     */     
/* 871 */     out.println(WsservletMessages.SERVLET_HTML_TITLE_2());
/*     */ 
/*     */     
/* 874 */     Module module = (Module)getEndpoint().getContainer().getSPI(Module.class);
/* 875 */     List<BoundEndpoint> endpoints = Collections.emptyList();
/* 876 */     if (module != null) {
/* 877 */       endpoints = module.getBoundEndpoints();
/*     */     }
/*     */     
/* 880 */     if (endpoints.isEmpty()) {
/*     */       
/* 882 */       out.println(WsservletMessages.SERVLET_HTML_NO_INFO_AVAILABLE());
/*     */     } else {
/* 884 */       out.println("<table width='100%' border='1'>");
/* 885 */       out.println("<tr>");
/* 886 */       out.println("<td>");
/*     */       
/* 888 */       out.println(WsservletMessages.SERVLET_HTML_COLUMN_HEADER_PORT_NAME());
/* 889 */       out.println("</td>");
/*     */       
/* 891 */       out.println("<td>");
/*     */       
/* 893 */       out.println(WsservletMessages.SERVLET_HTML_COLUMN_HEADER_INFORMATION());
/* 894 */       out.println("</td>");
/* 895 */       out.println("</tr>");
/*     */       
/* 897 */       for (BoundEndpoint a : endpoints) {
/* 898 */         String endpointAddress = a.getAddress(con.getBaseAddress()).toString();
/* 899 */         out.println("<tr>");
/*     */         
/* 901 */         out.println("<td>");
/* 902 */         out.println(WsservletMessages.SERVLET_HTML_ENDPOINT_TABLE(a.getEndpoint().getServiceName(), a.getEndpoint().getPortName()));
/*     */ 
/*     */ 
/*     */         
/* 906 */         out.println("</td>");
/*     */         
/* 908 */         out.println("<td>");
/* 909 */         out.println(WsservletMessages.SERVLET_HTML_INFORMATION_TABLE(endpointAddress, a.getEndpoint().getImplementationClass().getName()));
/*     */ 
/*     */ 
/*     */         
/* 913 */         out.println("</td>");
/*     */         
/* 915 */         out.println("</tr>");
/*     */       } 
/* 917 */       out.println("</table>");
/*     */     } 
/* 919 */     out.println("</body>");
/* 920 */     out.println("</html>");
/* 921 */     out.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static volatile boolean dump = false;
/*     */   
/*     */   public static volatile boolean publishStatusPage = true;
/*     */ 
/*     */   
/*     */   public static synchronized void setPublishStatus(boolean publish) {
/* 932 */     publishStatusPage = publish;
/*     */   }
/*     */   
/*     */   static {
/*     */     try {
/* 937 */       dump = Boolean.getBoolean(HttpAdapter.class.getName() + ".dump");
/* 938 */     } catch (Throwable t) {}
/*     */ 
/*     */     
/*     */     try {
/* 942 */       setPublishStatus(System.getProperty(HttpAdapter.class.getName() + ".publishStatusPage").equals("true"));
/* 943 */     } catch (Throwable t) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDump(boolean dumpMessages) {
/* 949 */     dump = dumpMessages;
/*     */   }
/* 951 */   private static final Logger LOGGER = Logger.getLogger(HttpAdapter.class.getName());
/*     */   
/*     */   public static interface CompletionCallback {
/*     */     void onCompletion();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\HttpAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */