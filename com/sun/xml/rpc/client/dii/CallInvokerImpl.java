/*     */ package com.sun.xml.rpc.client.dii;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.soap.MessageImpl;
/*     */ import com.sun.xml.rpc.client.ClientTransport;
/*     */ import com.sun.xml.rpc.client.ClientTransportFactory;
/*     */ import com.sun.xml.rpc.client.HandlerChainImpl;
/*     */ import com.sun.xml.rpc.client.StreamingSender;
/*     */ import com.sun.xml.rpc.client.StreamingSenderState;
/*     */ import com.sun.xml.rpc.client.http.HttpClientTransportFactory;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationState;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPResponseStructure;
/*     */ import com.sun.xml.rpc.soap.message.InternalSOAPMessage;
/*     */ import com.sun.xml.rpc.soap.message.SOAPBlockInfo;
/*     */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.streaming.FastInfosetWriterFactoryImpl;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterFactory;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterFactoryImpl;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.handler.HandlerChain;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CallInvokerImpl
/*     */   extends StreamingSender
/*     */   implements CallInvoker, CallPropertyConstants
/*     */ {
/*  67 */   private static ClientTransportFactory defaultTransportFactory = null;
/*     */   
/*     */   private static final String BASIC_CALL_PROPERTY = "com.sun.xml.rpc.client.dii.BasicCall";
/*     */   
/*     */   protected JAXRPCDeserializer faultDeserializer;
/*     */   
/*     */   protected JAXRPCDeserializer responseDeserializer;
/*     */   
/*     */   protected ClientTransportFactory transportFactory;
/*     */   protected ClientTransport clientTransport;
/*  77 */   protected String defaultEnvEncodingStyle = "http://schemas.xmlsoap.org/soap/encoding/";
/*  78 */   protected String implicitEnvEncodingStyle = null;
/*  79 */   protected String[] additionalNamespaces = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean useFastInfoset;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean acceptFastInfoset;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultTransportFactory(ClientTransportFactory factory) {
/*  94 */     defaultTransportFactory = factory;
/*     */   }
/*     */   
/*     */   public CallInvokerImpl() {
/*  98 */     this.transportFactory = defaultTransportFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPResponseStructure doInvoke(CallRequest callInfo, JAXRPCSerializer requestSerializer, JAXRPCDeserializer responseDeserializer, JAXRPCDeserializer faultDeserializer) throws Exception {
/* 107 */     this.responseDeserializer = responseDeserializer;
/* 108 */     this.faultDeserializer = faultDeserializer;
/*     */     
/* 110 */     BasicCall call = callInfo.call;
/*     */     
/* 112 */     initContentNegotiationState(call);
/*     */ 
/*     */ 
/*     */     
/* 116 */     StreamingSenderState state = setupRequest(callInfo, requestSerializer);
/*     */ 
/*     */ 
/*     */     
/* 120 */     _send(call.getTargetEndpointAddress(), state);
/*     */ 
/*     */ 
/*     */     
/* 124 */     SOAPResponseStructure responseStruct = null;
/* 125 */     Object responseObject = state.getResponse().getBody().getValue();
/*     */ 
/*     */ 
/*     */     
/* 129 */     if (responseObject instanceof SOAPDeserializationState) {
/* 130 */       responseStruct = (SOAPResponseStructure)((SOAPDeserializationState)responseObject).getInstance();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 135 */       responseStruct = (SOAPResponseStructure)responseObject;
/*     */     } 
/*     */     
/* 138 */     return responseStruct;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doInvokeOneWay(CallRequest callInfo, JAXRPCSerializer requestSerializer) throws Exception {
/* 146 */     BasicCall call = callInfo.call;
/* 147 */     initContentNegotiationState(call);
/* 148 */     StreamingSenderState state = setupRequest(callInfo, requestSerializer);
/* 149 */     _sendOneWay(call.getTargetEndpointAddress(), state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initContentNegotiationState(BasicCall call) {
/* 156 */     String value = (String)call.getProperty("com.sun.xml.rpc.client.ContentNegotiation");
/*     */     
/* 158 */     this.useFastInfoset = (value == "optimistic");
/* 159 */     this.acceptFastInfoset = (this.useFastInfoset || value == "pessimistic");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StreamingSenderState _start(HandlerChain handlerChain) {
/* 168 */     SOAPMessageContext messageContext = new SOAPMessageContext();
/* 169 */     ((HandlerChainImpl)handlerChain).addUnderstoodHeaders(_getUnderstoodHeaders());
/*     */ 
/*     */ 
/*     */     
/* 173 */     return new StreamingSenderState(messageContext, handlerChain, this.useFastInfoset, this.acceptFastInfoset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _handleRuntimeExceptionInSend(RuntimeException rex) throws Exception {
/* 180 */     if (rex instanceof javax.xml.rpc.JAXRPCException) {
/* 181 */       throw rex;
/*     */     }
/* 183 */     super._handleRuntimeExceptionInSend(rex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StreamingSenderState setupRequest(CallRequest callInfo, JAXRPCSerializer requestSerializer) throws Exception {
/* 191 */     BasicCall call = callInfo.call;
/*     */     
/* 193 */     String encodingStyle = (String)call.getProperty("javax.xml.rpc.encodingstyle.namespace.uri");
/*     */     
/* 195 */     String operationStyle = (String)call.getProperty("javax.xml.rpc.soap.operation.style");
/*     */ 
/*     */     
/* 198 */     StreamingSenderState state = _start(call.getHandlerChain());
/*     */ 
/*     */     
/* 201 */     state.setCall(call);
/*     */ 
/*     */     
/* 204 */     InternalSOAPMessage request = state.getRequest();
/*     */ 
/*     */ 
/*     */     
/* 208 */     if (isRPCLiteral(operationStyle, encodingStyle)) {
/* 209 */       setNamespaceDeclarations("ns0", call.getOperationName().getNamespaceURI());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     if ("".equals(encodingStyle)) {
/* 216 */       setImplicitEnvelopeEncodingStyle("");
/* 217 */       setDefaultEnvelopeEncodingStyle((String)null);
/*     */     } 
/*     */     
/* 220 */     SOAPBlockInfo bodyBlock = null;
/* 221 */     if (isRPC(operationStyle, encodingStyle)) {
/* 222 */       bodyBlock = new SOAPBlockInfo(call.getOperationName());
/*     */     } else {
/* 224 */       bodyBlock = new SOAPBlockInfo(null);
/*     */     } 
/*     */     
/* 227 */     bodyBlock.setValue(callInfo.request);
/* 228 */     bodyBlock.setSerializer(requestSerializer);
/*     */     
/* 230 */     request.setBody(bodyBlock);
/*     */     
/* 232 */     SOAPMessageContext messageContext = state.getMessageContext();
/* 233 */     messageContext.setProperty("com.sun.xml.rpc.client.dii.BasicCall", call);
/*     */ 
/*     */     
/* 236 */     if (isRPCLiteral(operationStyle, encodingStyle)) {
/* 237 */       messageContext.setProperty("com.sun.xml.rpc.client.responseQName", new QName(call.getOperationName().getNamespaceURI(), call.getOperationName().getLocalPart() + "Response"));
/*     */     }
/*     */ 
/*     */     
/* 241 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _preSendingHook(StreamingSenderState state) throws Exception {
/* 249 */     BasicCall call = (BasicCall)state.getMessageContext().getProperty("com.sun.xml.rpc.client.dii.BasicCall");
/*     */     
/* 251 */     SOAPMessageContext messageContext = state.getMessageContext();
/* 252 */     Object username = call.getProperty("javax.xml.rpc.security.auth.username");
/* 253 */     if (username != null) {
/* 254 */       messageContext.setProperty("javax.xml.rpc.security.auth.username", username);
/*     */     }
/* 256 */     Object password = call.getProperty("javax.xml.rpc.security.auth.password");
/* 257 */     if (password != null) {
/* 258 */       messageContext.setProperty("javax.xml.rpc.security.auth.password", password);
/*     */     }
/* 260 */     Object endpoint = call.getProperty("javax.xml.rpc.endpoint");
/* 261 */     if (endpoint != null) {
/* 262 */       messageContext.setProperty("javax.xml.rpc.endpoint", endpoint);
/*     */     }
/* 264 */     Object operation = call.getProperty("javax.xml.rpc.soap.operation.style");
/* 265 */     if (operation != null) {
/* 266 */       messageContext.setProperty("javax.xml.rpc.soap.operation.style", operation);
/*     */     }
/* 268 */     Boolean isSOAPActionUsed = (Boolean)call.getRequiredProperty("javax.xml.rpc.soap.http.soapaction.use");
/*     */     
/* 270 */     if (isSOAPActionUsed.booleanValue()) {
/* 271 */       messageContext.setProperty("http.soap.action", call.getRequiredProperty("javax.xml.rpc.soap.http.soapaction.uri"));
/*     */     }
/*     */     
/* 274 */     Object encoding = call.getProperty("javax.xml.rpc.encodingstyle.namespace.uri");
/* 275 */     if (encoding != null) {
/* 276 */       messageContext.setProperty("javax.xml.rpc.encodingstyle.namespace.uri", encoding);
/*     */     }
/*     */     
/* 279 */     Object verification = call.getProperty("com.sun.xml.rpc.client.http.HostnameVerificationProperty");
/* 280 */     if (verification != null) {
/* 281 */       messageContext.setProperty("com.sun.xml.rpc.client.http.HostnameVerificationProperty", verification);
/*     */     }
/*     */ 
/*     */     
/* 285 */     Object maintainSession = call.getProperty("javax.xml.rpc.session.maintain");
/* 286 */     if (maintainSession != null) {
/* 287 */       messageContext.setProperty("javax.xml.rpc.session.maintain", maintainSession);
/*     */     }
/*     */     
/* 290 */     if (maintainSession != null && maintainSession.equals(Boolean.TRUE)) {
/* 291 */       Object cookieJar = call.getProperty("com.sun.xml.rpc.client.http.CookieJar");
/*     */       
/* 293 */       if (cookieJar != null) {
/* 294 */         messageContext.setProperty("com.sun.xml.rpc.client.http.CookieJar", cookieJar);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _postSendingHook(StreamingSenderState state) throws Exception {
/* 303 */     BasicCall call = (BasicCall)state.getMessageContext().getProperty("com.sun.xml.rpc.client.dii.BasicCall");
/*     */     
/* 305 */     Object maintainSession = call.getProperty("javax.xml.rpc.session.maintain");
/* 306 */     if (maintainSession != null && maintainSession.equals(Boolean.TRUE)) {
/* 307 */       Object cookieJar = call.getProperty("com.sun.xml.rpc.client.http.CookieJar");
/*     */       
/* 309 */       if (cookieJar == null) {
/* 310 */         SOAPMessageContext messageContext = state.getMessageContext();
/* 311 */         cookieJar = messageContext.getProperty("com.sun.xml.rpc.client.http.CookieJar");
/*     */         
/* 313 */         call.setProperty("com.sun.xml.rpc.client.http.CookieJar", cookieJar);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     MessageImpl response = (MessageImpl)state.getResponse().getMessage();
/* 323 */     if (!this.useFastInfoset && response.isFastInfoset()) {
/* 324 */       state.getCall().setProperty("com.sun.xml.rpc.client.ContentNegotiation", "optimistic");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientTransportFactory _getTransportFactory() {
/* 331 */     if (this.transportFactory == null) {
/* 332 */       this.transportFactory = (ClientTransportFactory)new HttpClientTransportFactory();
/*     */     }
/*     */     
/* 335 */     return this.transportFactory;
/*     */   }
/*     */   
/*     */   public void _setTransportFactory(ClientTransportFactory factory) {
/* 339 */     this.transportFactory = factory;
/* 340 */     this.clientTransport = null;
/*     */   }
/*     */   
/*     */   public ClientTransport _getTransport() {
/* 344 */     if (this.clientTransport == null) {
/* 345 */       this.clientTransport = _getTransportFactory().create();
/*     */     }
/*     */     
/* 348 */     return this.clientTransport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _readFirstBodyElement(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
/* 357 */     String operationStyle = (String)state.getMessageContext().getProperty("javax.xml.rpc.soap.operation.style");
/*     */     
/* 359 */     String encoding = (String)state.getMessageContext().getProperty("javax.xml.rpc.encodingstyle.namespace.uri");
/*     */     
/* 361 */     QName responseQName = null;
/* 362 */     if (isRPCLiteral(operationStyle, encoding)) {
/* 363 */       responseQName = (QName)state.getMessageContext().getProperty("com.sun.xml.rpc.client.responseQName");
/*     */     }
/*     */ 
/*     */     
/* 367 */     Object responseStructObj = getResponseDeserializer().deserialize(responseQName, bodyReader, deserializationContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 373 */     SOAPBlockInfo bodyBlock = new SOAPBlockInfo(responseQName);
/* 374 */     bodyBlock.setValue(responseStructObj);
/* 375 */     state.getResponse().setBody(bodyBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JAXRPCDeserializer getFaultDeserializer() {
/* 380 */     return this.faultDeserializer;
/*     */   }
/*     */   
/*     */   protected JAXRPCDeserializer getResponseDeserializer() {
/* 384 */     return this.responseDeserializer;
/*     */   }
/*     */ 
/*     */   
/*     */   public String _getDefaultEnvelopeEncodingStyle() {
/* 389 */     return this.defaultEnvEncodingStyle;
/*     */   }
/*     */   
/*     */   void setDefaultEnvelopeEncodingStyle(String style) {
/* 393 */     this.defaultEnvEncodingStyle = style;
/*     */   }
/*     */   
/*     */   public void setImplicitEnvelopeEncodingStyle(String style) {
/* 397 */     this.implicitEnvEncodingStyle = style;
/*     */   }
/*     */   
/*     */   public String _getImplicitEnvelopeEncodingStyle() {
/* 401 */     return this.implicitEnvEncodingStyle;
/*     */   }
/*     */   
/*     */   protected String[] _getNamespaceDeclarations() {
/* 405 */     return this.additionalNamespaces;
/*     */   }
/*     */   
/*     */   protected void setNamespaceDeclarations(String pre, String name) {
/* 409 */     this.additionalNamespaces = new String[] { pre, name };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDocumentLiteral(String operationStyle, String encodingStyle) {
/* 415 */     return ("document".equalsIgnoreCase(operationStyle) && "".equals(encodingStyle));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRPCLiteral(String operationStyle, String encodingStyle) {
/* 421 */     return ("rpc".equalsIgnoreCase(operationStyle) && "".equals(encodingStyle));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRPC(String operationStyle, String encodingStyle) {
/* 427 */     return "rpc".equalsIgnoreCase(operationStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLWriterFactory _getXMLWriterFactory() {
/* 438 */     return this.useFastInfoset ? FastInfosetWriterFactoryImpl.newInstance() : XMLWriterFactoryImpl.newInstance();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\CallInvokerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */