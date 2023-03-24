/*     */ package com.sun.xml.ws.client.dispatch;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.AsyncInvoker;
/*     */ import com.sun.xml.ws.client.AsyncResponseImpl;
/*     */ import com.sun.xml.ws.client.RequestContext;
/*     */ import com.sun.xml.ws.client.ResponseContext;
/*     */ import com.sun.xml.ws.client.ResponseContextReceiver;
/*     */ import com.sun.xml.ws.client.Stub;
/*     */ import com.sun.xml.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.ws.encoding.soap.DeserializationException;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.message.AttachmentSetImpl;
/*     */ import com.sun.xml.ws.message.DataHandlerAttachment;
/*     */ import com.sun.xml.ws.resources.DispatchMessages;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.AsyncHandler;
/*     */ import javax.xml.ws.Dispatch;
/*     */ import javax.xml.ws.Response;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DispatchImpl<T>
/*     */   extends Stub
/*     */   implements Dispatch<T>
/*     */ {
/* 109 */   private static final Logger LOGGER = Logger.getLogger(DispatchImpl.class.getName());
/*     */   
/*     */   final Service.Mode mode;
/*     */   
/*     */   final SOAPVersion soapVersion;
/*     */   
/*     */   final boolean allowFaultResponseMsg;
/*     */   
/*     */   static final long AWAIT_TERMINATION_TIME = 800L;
/*     */   
/*     */   static final String HTTP_REQUEST_METHOD_GET = "GET";
/*     */   
/*     */   static final String HTTP_REQUEST_METHOD_POST = "POST";
/*     */   static final String HTTP_REQUEST_METHOD_PUT = "PUT";
/*     */   
/*     */   @Deprecated
/*     */   protected DispatchImpl(QName port, Service.Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, @Nullable WSEndpointReference epr) {
/* 126 */     super(port, owner, pipe, binding, (owner.getWsdlService() != null) ? (WSDLPort)owner.getWsdlService().get(port) : null, owner.getEndpointAddress(port), epr);
/* 127 */     this.mode = mode;
/* 128 */     this.soapVersion = binding.getSOAPVersion();
/* 129 */     this.allowFaultResponseMsg = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DispatchImpl(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, @Nullable WSEndpointReference epr) {
/* 138 */     this(portInfo, mode, binding, epr, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DispatchImpl(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, @Nullable WSEndpointReference epr, boolean allowFaultResponseMsg) {
/* 148 */     this(portInfo, mode, binding, (Tube)null, epr, allowFaultResponseMsg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DispatchImpl(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, Tube pipe, @Nullable WSEndpointReference epr, boolean allowFaultResponseMsg) {
/* 159 */     super(portInfo, binding, pipe, portInfo.getEndpointAddress(), epr);
/* 160 */     this.mode = mode;
/* 161 */     this.soapVersion = binding.getSOAPVersion();
/* 162 */     this.allowFaultResponseMsg = allowFaultResponseMsg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DispatchImpl(WSPortInfo portInfo, Service.Mode mode, Tube pipe, BindingImpl binding, @Nullable WSEndpointReference epr, boolean allowFaultResponseMsg) {
/* 173 */     super(portInfo, binding, pipe, portInfo.getEndpointAddress(), epr);
/* 174 */     this.mode = mode;
/* 175 */     this.soapVersion = binding.getSOAPVersion();
/* 176 */     this.allowFaultResponseMsg = allowFaultResponseMsg;
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
/*     */   public final Response<T> invokeAsync(T param) {
/* 193 */     Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*     */     try {
/* 195 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 196 */         dumpParam(param, "invokeAsync(T)");
/*     */       }
/* 198 */       AsyncInvoker invoker = new DispatchAsyncInvoker(param);
/* 199 */       AsyncResponseImpl<T> ft = new AsyncResponseImpl((Runnable)invoker, null);
/* 200 */       invoker.setReceiver(ft);
/* 201 */       ft.run();
/* 202 */       return (Response<T>)ft;
/*     */     } finally {
/* 204 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void dumpParam(T param, String method) {
/* 209 */     if (param instanceof Packet) {
/* 210 */       Packet message = (Packet)param;
/*     */ 
/*     */ 
/*     */       
/* 214 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 215 */         AddressingVersion av = getBinding().getAddressingVersion();
/* 216 */         SOAPVersion sv = getBinding().getSOAPVersion();
/* 217 */         String action = (av != null && message.getMessage() != null) ? AddressingUtils.getAction(message.getMessage().getMessageHeaders(), av, sv) : null;
/*     */ 
/*     */         
/* 220 */         String msgId = (av != null && message.getMessage() != null) ? AddressingUtils.getMessageID(message.getMessage().getMessageHeaders(), av, sv) : null;
/*     */ 
/*     */         
/* 223 */         LOGGER.fine("In DispatchImpl." + method + " for message with action: " + action + " and msg ID: " + msgId + " msg: " + message.getMessage());
/*     */         
/* 225 */         if (message.getMessage() == null)
/* 226 */           LOGGER.fine("Dispatching null message for action: " + action + " and msg ID: " + msgId); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final Future<?> invokeAsync(T param, AsyncHandler<T> asyncHandler) {
/* 232 */     Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*     */     try {
/* 234 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 235 */         dumpParam(param, "invokeAsync(T, AsyncHandler<T>)");
/*     */       }
/* 237 */       AsyncInvoker invoker = new DispatchAsyncInvoker(param);
/* 238 */       AsyncResponseImpl<T> ft = new AsyncResponseImpl((Runnable)invoker, asyncHandler);
/* 239 */       invoker.setReceiver(ft);
/* 240 */       invoker.setNonNullAsyncHandlerGiven((asyncHandler != null));
/*     */       
/* 242 */       ft.run();
/* 243 */       return (Future<?>)ft;
/*     */     } finally {
/* 245 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T doInvoke(T in, RequestContext rc, ResponseContextReceiver receiver) {
/* 256 */     Packet response = null;
/*     */     try {
/*     */       try {
/* 259 */         checkNullAllowed(in, rc, (WSBinding)this.binding, this.mode);
/*     */         
/* 261 */         Packet message = createPacket(in);
/* 262 */         message.setState(Packet.State.ClientRequest);
/* 263 */         resolveEndpointAddress(message, rc);
/* 264 */         setProperties(message, true);
/* 265 */         response = process(message, rc, receiver);
/* 266 */         Message msg = response.getMessage();
/*     */ 
/*     */         
/* 269 */         if (msg != null && msg.isFault() && !this.allowFaultResponseMsg) {
/*     */           
/* 271 */           SOAPFaultBuilder faultBuilder = SOAPFaultBuilder.create(msg);
/*     */ 
/*     */           
/* 274 */           throw (SOAPFaultException)faultBuilder.createException(null);
/*     */         } 
/* 276 */       } catch (JAXBException e) {
/*     */         
/* 278 */         throw new DeserializationException(DispatchMessages.INVALID_RESPONSE_DESERIALIZATION(), new Object[] { e });
/* 279 */       } catch (WebServiceException e) {
/*     */         
/* 281 */         throw e;
/* 282 */       } catch (Throwable e) {
/*     */ 
/*     */ 
/*     */         
/* 286 */         throw new WebServiceException(e);
/*     */       } 
/*     */       
/* 289 */       return toReturnValue(response);
/*     */     } finally {
/*     */       
/* 292 */       if (response != null && response.transportBackChannel != null)
/* 293 */         response.transportBackChannel.close(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final T invoke(T in) {
/* 298 */     Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*     */     try {
/* 300 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 301 */         dumpParam(in, "invoke(T)");
/*     */       }
/*     */       
/* 304 */       return doInvoke(in, this.requestContext, (ResponseContextReceiver)this);
/*     */     } finally {
/* 306 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void invokeOneWay(T in) {
/* 311 */     Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*     */     try {
/* 313 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 314 */         dumpParam(in, "invokeOneWay(T)");
/*     */       }
/*     */       
/*     */       try {
/* 318 */         checkNullAllowed(in, this.requestContext, (WSBinding)this.binding, this.mode);
/*     */         
/* 320 */         Packet request = createPacket(in);
/* 321 */         request.setState(Packet.State.ClientRequest);
/* 322 */         setProperties(request, false);
/* 323 */         process(request, this.requestContext, (ResponseContextReceiver)this);
/* 324 */       } catch (WebServiceException e) {
/*     */         
/* 326 */         throw e;
/* 327 */       } catch (Throwable e) {
/*     */ 
/*     */ 
/*     */         
/* 331 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } finally {
/* 334 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     } 
/*     */   }
/*     */   
/*     */   void setProperties(Packet packet, boolean expectReply) {
/* 339 */     packet.expectReply = Boolean.valueOf(expectReply);
/*     */   }
/*     */   
/*     */   static boolean isXMLHttp(@NotNull WSBinding binding) {
/* 343 */     return binding.getBindingId().equals(BindingID.XML_HTTP);
/*     */   }
/*     */   
/*     */   static boolean isPAYLOADMode(@NotNull Service.Mode mode) {
/* 347 */     return (mode == Service.Mode.PAYLOAD);
/*     */   }
/*     */ 
/*     */   
/*     */   static void checkNullAllowed(@Nullable Object in, RequestContext rc, WSBinding binding, Service.Mode mode) {
/* 352 */     if (in != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 357 */     if (isXMLHttp(binding)) {
/* 358 */       if (methodNotOk(rc)) {
/* 359 */         throw new WebServiceException(DispatchMessages.INVALID_NULLARG_XMLHTTP_REQUEST_METHOD("POST", "GET"));
/*     */       }
/* 361 */     } else if (mode == Service.Mode.MESSAGE) {
/* 362 */       throw new WebServiceException(DispatchMessages.INVALID_NULLARG_SOAP_MSGMODE(mode.name(), Service.Mode.PAYLOAD.toString()));
/*     */     } 
/*     */   }
/*     */   
/*     */   static boolean methodNotOk(@NotNull RequestContext rc) {
/* 367 */     String requestMethod = (String)rc.get("javax.xml.ws.http.request.method");
/* 368 */     String request = (requestMethod == null) ? "POST" : requestMethod;
/*     */     
/* 370 */     return ("POST".equalsIgnoreCase(request) || "PUT".equalsIgnoreCase(request));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void checkValidSOAPMessageDispatch(WSBinding binding, Service.Mode mode) {
/* 375 */     if (isXMLHttp(binding))
/* 376 */       throw new WebServiceException(DispatchMessages.INVALID_SOAPMESSAGE_DISPATCH_BINDING("http://www.w3.org/2004/08/wsdl/http", "http://schemas.xmlsoap.org/wsdl/soap/http or http://www.w3.org/2003/05/soap/bindings/HTTP/")); 
/* 377 */     if (isPAYLOADMode(mode)) {
/* 378 */       throw new WebServiceException(DispatchMessages.INVALID_SOAPMESSAGE_DISPATCH_MSGMODE(mode.name(), Service.Mode.MESSAGE.toString()));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void checkValidDataSourceDispatch(WSBinding binding, Service.Mode mode) {
/* 383 */     if (!isXMLHttp(binding))
/* 384 */       throw new WebServiceException(DispatchMessages.INVALID_DATASOURCE_DISPATCH_BINDING("SOAP/HTTP", "http://www.w3.org/2004/08/wsdl/http")); 
/* 385 */     if (isPAYLOADMode(mode))
/* 386 */       throw new WebServiceException(DispatchMessages.INVALID_DATASOURCE_DISPATCH_MSGMODE(mode.name(), Service.Mode.MESSAGE.toString())); 
/*     */   }
/*     */   @NotNull
/*     */   public final QName getPortName() {
/* 390 */     return this.portname;
/*     */   }
/*     */   void resolveEndpointAddress(@NotNull Packet message, @NotNull RequestContext requestContext) {
/*     */     String endpoint;
/* 394 */     boolean p = message.packetTakesPriorityOverRequestContext;
/*     */ 
/*     */ 
/*     */     
/* 398 */     if (p && message.endpointAddress != null) {
/* 399 */       endpoint = message.endpointAddress.toString();
/*     */     } else {
/* 401 */       endpoint = (String)requestContext.get("javax.xml.ws.service.endpoint.address");
/*     */     } 
/*     */     
/* 404 */     if (endpoint == null) {
/* 405 */       endpoint = message.endpointAddress.toString();
/*     */     }
/* 407 */     String pathInfo = null;
/* 408 */     String queryString = null;
/* 409 */     if (p && message.invocationProperties.get("javax.xml.ws.http.request.pathinfo") != null) {
/* 410 */       pathInfo = (String)message.invocationProperties.get("javax.xml.ws.http.request.pathinfo");
/* 411 */     } else if (requestContext.get("javax.xml.ws.http.request.pathinfo") != null) {
/* 412 */       pathInfo = (String)requestContext.get("javax.xml.ws.http.request.pathinfo");
/*     */     } 
/*     */     
/* 415 */     if (p && message.invocationProperties.get("javax.xml.ws.http.request.querystring") != null) {
/* 416 */       queryString = (String)message.invocationProperties.get("javax.xml.ws.http.request.querystring");
/* 417 */     } else if (requestContext.get("javax.xml.ws.http.request.querystring") != null) {
/* 418 */       queryString = (String)requestContext.get("javax.xml.ws.http.request.querystring");
/*     */     } 
/*     */     
/* 421 */     if (pathInfo != null || queryString != null) {
/* 422 */       pathInfo = checkPath(pathInfo);
/* 423 */       queryString = checkQuery(queryString);
/* 424 */       if (endpoint != null) {
/*     */         try {
/* 426 */           URI endpointURI = new URI(endpoint);
/* 427 */           endpoint = resolveURI(endpointURI, pathInfo, queryString);
/* 428 */         } catch (URISyntaxException e) {
/* 429 */           throw new WebServiceException(DispatchMessages.INVALID_URI(endpoint));
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 438 */     requestContext.put("javax.xml.ws.service.endpoint.address", endpoint);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected String resolveURI(@NotNull URI endpointURI, @Nullable String pathInfo, @Nullable String queryString) {
/* 444 */     String query = null;
/* 445 */     String fragment = null;
/* 446 */     if (queryString != null) {
/*     */       URI result;
/*     */       try {
/* 449 */         URI tp = new URI(null, null, endpointURI.getPath(), queryString, null);
/* 450 */         result = endpointURI.resolve(tp);
/* 451 */       } catch (URISyntaxException e) {
/* 452 */         throw new WebServiceException(DispatchMessages.INVALID_QUERY_STRING(queryString));
/*     */       } 
/* 454 */       query = result.getQuery();
/* 455 */       fragment = result.getFragment();
/*     */     } 
/*     */     
/* 458 */     String path = (pathInfo != null) ? pathInfo : endpointURI.getPath();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 467 */       StringBuilder spec = new StringBuilder();
/* 468 */       if (path != null) {
/* 469 */         spec.append(path);
/*     */       }
/* 471 */       if (query != null) {
/* 472 */         spec.append("?");
/* 473 */         spec.append(query);
/*     */       } 
/* 475 */       if (fragment != null) {
/* 476 */         spec.append("#");
/* 477 */         spec.append(fragment);
/*     */       } 
/* 479 */       return (new URL(endpointURI.toURL(), spec.toString())).toExternalForm();
/* 480 */     } catch (MalformedURLException e) {
/* 481 */       throw new WebServiceException(DispatchMessages.INVALID_URI_RESOLUTION(path));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String checkPath(@Nullable String path) {
/* 487 */     return (path == null || path.startsWith("/")) ? path : ("/" + path);
/*     */   }
/*     */   
/*     */   private static String checkQuery(@Nullable String query) {
/* 491 */     if (query == null) return null;
/*     */     
/* 493 */     if (query.indexOf('?') == 0)
/* 494 */       throw new WebServiceException(DispatchMessages.INVALID_QUERY_LEADING_CHAR(query)); 
/* 495 */     return query;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AttachmentSet setOutboundAttachments() {
/* 500 */     HashMap<String, DataHandler> attachments = (HashMap<String, DataHandler>)getRequestContext().get("javax.xml.ws.binding.attachments.outbound");
/*     */ 
/*     */     
/* 503 */     if (attachments != null) {
/* 504 */       List<Attachment> alist = new ArrayList<Attachment>();
/* 505 */       for (Map.Entry<String, DataHandler> att : attachments.entrySet()) {
/* 506 */         DataHandlerAttachment dha = new DataHandlerAttachment(att.getKey(), att.getValue());
/* 507 */         alist.add(dha);
/*     */       } 
/* 509 */       return (AttachmentSet)new AttachmentSetImpl(alist);
/*     */     } 
/* 511 */     return (AttachmentSet)new AttachmentSetImpl();
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
/*     */   private class Invoker
/*     */     implements Callable
/*     */   {
/*     */     private final T param;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 534 */     private final RequestContext rc = DispatchImpl.this.requestContext.copy();
/*     */ 
/*     */ 
/*     */     
/*     */     private ResponseContextReceiver receiver;
/*     */ 
/*     */ 
/*     */     
/*     */     Invoker(T param) {
/* 543 */       this.param = param;
/*     */     }
/*     */     
/*     */     public T call() throws Exception {
/* 547 */       if (DispatchImpl.LOGGER.isLoggable(Level.FINE)) {
/* 548 */         DispatchImpl.this.dumpParam(this.param, "call()");
/*     */       }
/* 550 */       return DispatchImpl.this.doInvoke(this.param, this.rc, this.receiver);
/*     */     }
/*     */     
/*     */     void setReceiver(ResponseContextReceiver receiver) {
/* 554 */       this.receiver = receiver;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class DispatchAsyncInvoker
/*     */     extends AsyncInvoker
/*     */   {
/*     */     private final T param;
/*     */     
/* 565 */     private final RequestContext rc = DispatchImpl.this.requestContext.copy();
/*     */     
/*     */     DispatchAsyncInvoker(T param) {
/* 568 */       this.param = param;
/*     */     }
/*     */     
/*     */     public void do_run() {
/* 572 */       DispatchImpl.checkNullAllowed(this.param, this.rc, (WSBinding)DispatchImpl.this.binding, DispatchImpl.this.mode);
/* 573 */       Packet message = DispatchImpl.this.createPacket(this.param);
/* 574 */       message.setState(Packet.State.ClientRequest);
/* 575 */       message.nonNullAsyncHandlerGiven = Boolean.valueOf(this.nonNullAsyncHandlerGiven);
/* 576 */       DispatchImpl.this.resolveEndpointAddress(message, this.rc);
/* 577 */       DispatchImpl.this.setProperties(message, true);
/*     */       
/* 579 */       String action = null;
/* 580 */       String msgId = null;
/* 581 */       if (DispatchImpl.LOGGER.isLoggable(Level.FINE)) {
/* 582 */         AddressingVersion av = DispatchImpl.this.getBinding().getAddressingVersion();
/* 583 */         SOAPVersion sv = DispatchImpl.this.getBinding().getSOAPVersion();
/* 584 */         action = (av != null && message.getMessage() != null) ? message.getMessage().getHeaders().getAction(av, sv) : null;
/*     */ 
/*     */         
/* 587 */         msgId = (av != null && message.getMessage() != null) ? message.getMessage().getHeaders().getMessageID(av, sv) : null;
/*     */ 
/*     */         
/* 590 */         DispatchImpl.LOGGER.fine("In DispatchAsyncInvoker.do_run for async message with action: " + action + " and msg ID: " + msgId);
/*     */       } 
/*     */       
/* 593 */       final String actionUse = action;
/* 594 */       final String msgIdUse = msgId;
/*     */       
/* 596 */       Fiber.CompletionCallback callback = new Fiber.CompletionCallback()
/*     */         {
/*     */           public void onCompletion(@NotNull Packet response) {
/* 599 */             if (DispatchImpl.LOGGER.isLoggable(Level.FINE)) {
/* 600 */               DispatchImpl.LOGGER.fine("Done with processAsync in DispatchAsyncInvoker.do_run, and setting response for async message with action: " + actionUse + " and msg ID: " + msgIdUse);
/*     */             }
/*     */             
/* 603 */             Message msg = response.getMessage();
/*     */             
/* 605 */             if (DispatchImpl.LOGGER.isLoggable(Level.FINE)) {
/* 606 */               DispatchImpl.LOGGER.fine("Done with processAsync in DispatchAsyncInvoker.do_run, and setting response for async message with action: " + actionUse + " and msg ID: " + msgIdUse + " msg: " + msg);
/*     */             }
/*     */             
/*     */             try {
/* 610 */               if (msg != null && msg.isFault() && !DispatchImpl.this.allowFaultResponseMsg) {
/*     */                 
/* 612 */                 SOAPFaultBuilder faultBuilder = SOAPFaultBuilder.create(msg);
/*     */ 
/*     */                 
/* 615 */                 throw (SOAPFaultException)faultBuilder.createException(null);
/*     */               } 
/* 617 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.setResponseContext(new ResponseContext(response));
/* 618 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(DispatchImpl.this.toReturnValue(response), null);
/* 619 */             } catch (JAXBException e) {
/*     */               
/* 621 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(null, (Throwable)new DeserializationException(DispatchMessages.INVALID_RESPONSE_DESERIALIZATION(), new Object[] { e }));
/* 622 */             } catch (WebServiceException e) {
/*     */               
/* 624 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(null, e);
/* 625 */             } catch (Throwable e) {
/*     */ 
/*     */ 
/*     */               
/* 629 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(null, new WebServiceException(e));
/*     */             } 
/*     */           }
/*     */           public void onCompletion(@NotNull Throwable error) {
/* 633 */             if (DispatchImpl.LOGGER.isLoggable(Level.FINE)) {
/* 634 */               DispatchImpl.LOGGER.fine("Done with processAsync in DispatchAsyncInvoker.do_run, and setting response for async message with action: " + actionUse + " and msg ID: " + msgIdUse + " Throwable: " + error.toString());
/*     */             }
/* 636 */             if (error instanceof WebServiceException) {
/* 637 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(null, error);
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 642 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(null, new WebServiceException(error));
/*     */             } 
/*     */           }
/*     */         };
/* 646 */       DispatchImpl.this.processAsync(this.responseImpl, message, this.rc, callback);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setOutboundHeaders(Object... headers) {
/* 651 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Dispatch<Source> createSourceDispatch(QName port, Service.Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/* 660 */     if (isXMLHttp((WSBinding)binding)) {
/* 661 */       return new RESTSourceDispatch(port, mode, owner, pipe, binding, epr);
/*     */     }
/* 663 */     return new SOAPSourceDispatch(port, mode, owner, pipe, binding, epr);
/*     */   }
/*     */   
/*     */   public static Dispatch<Source> createSourceDispatch(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/* 667 */     if (isXMLHttp((WSBinding)binding)) {
/* 668 */       return new RESTSourceDispatch(portInfo, mode, binding, epr);
/*     */     }
/* 670 */     return new SOAPSourceDispatch(portInfo, mode, binding, epr);
/*     */   }
/*     */   
/*     */   abstract Packet createPacket(T paramT);
/*     */   
/*     */   abstract T toReturnValue(Packet paramPacket);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\dispatch\DispatchImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */