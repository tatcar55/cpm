/*     */ package com.sun.xml.rpc.client;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.soap.MessageImpl;
/*     */ import com.sun.xml.rpc.client.http.HttpClientTransportFactory;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.SerializerConstants;
/*     */ import com.sun.xml.rpc.encoding._Initializable;
/*     */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.spi.runtime.ClientTransportFactory;
/*     */ import com.sun.xml.rpc.spi.runtime.StubBase;
/*     */ import com.sun.xml.rpc.streaming.FastInfosetWriterFactoryImpl;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterFactory;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterFactoryImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.rpc.JAXRPCException;
/*     */ import javax.xml.rpc.Stub;
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
/*     */ public abstract class StubBase
/*     */   extends StreamingSender
/*     */   implements Stub, SerializerConstants, _Initializable, StubBase
/*     */ {
/*     */   protected static final Set _recognizedProperties;
/*  68 */   private Map _properties = new HashMap<Object, Object>();
/*     */   
/*     */   private boolean _mustInitialize = true;
/*     */   private ClientTransport _transport;
/*     */   private ClientTransportFactory _transportFactory;
/*     */   protected HandlerChain _handlerChain;
/*     */   
/*     */   static {
/*  76 */     Set<String> temp = new HashSet();
/*  77 */     temp.add("javax.xml.rpc.security.auth.username");
/*  78 */     temp.add("javax.xml.rpc.security.auth.password");
/*  79 */     temp.add("javax.xml.rpc.service.endpoint.address");
/*  80 */     temp.add("javax.xml.rpc.session.maintain");
/*  81 */     temp.add("com.sun.client.OperationStyleProperty");
/*  82 */     temp.add(" com.sun.client.EncodingStyleProperty");
/*  83 */     temp.add("com.sun.xml.rpc.client.http.CookieJar");
/*  84 */     temp.add("com.sun.xml.rpc.client.http.HostnameVerificationProperty");
/*  85 */     temp.add("com.sun.xml.rpc.client.http.RedirectRequestProperty");
/*  86 */     temp.add("com.sun.xml.rpc.security.context");
/*     */     
/*  88 */     temp.add("com.sun.xml.rpc.attachment.SetAttachmentContext");
/*  89 */     temp.add("com.sun.xml.rpc.attachment.GetAttachmentContext");
/*  90 */     temp.add("com.sun.xml.rpc.client.ContentNegotiation");
/*  91 */     _recognizedProperties = Collections.unmodifiableSet(temp);
/*     */   }
/*     */ 
/*     */   
/*     */   protected StubBase(HandlerChain handlerChain) {
/*  96 */     this._handlerChain = handlerChain;
/*  97 */     ContentNegotiationProperties.initFromSystemProperties(this._properties);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StreamingSenderState _start(HandlerChain handlerChain) {
/* 103 */     SOAPMessageContext messageContext = new SOAPMessageContext();
/*     */     
/* 105 */     ((HandlerChainImpl)handlerChain).addUnderstoodHeaders(_getUnderstoodHeaders());
/*     */ 
/*     */ 
/*     */     
/* 109 */     return new StreamingSenderState(messageContext, handlerChain, useFastInfoset(), acceptFastInfoset());
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerChain _getHandlerChain() {
/* 114 */     if (this._handlerChain == null)
/*     */     {
/*     */       
/* 117 */       this._handlerChain = new HandlerChainImpl(new ArrayList());
/*     */     }
/* 119 */     return this._handlerChain;
/*     */   }
/*     */   
/*     */   public boolean useFastInfoset() {
/* 123 */     Object value = this._properties.get("com.sun.xml.rpc.client.ContentNegotiation");
/*     */     
/* 125 */     return (value == "optimistic");
/*     */   }
/*     */   
/*     */   public boolean acceptFastInfoset() {
/* 129 */     Object value = this._properties.get("com.sun.xml.rpc.client.ContentNegotiation");
/*     */     
/* 131 */     return (value != "none");
/*     */   }
/*     */   
/*     */   public void _setProperty(String name, Object value) {
/* 135 */     if (!_recognizedProperties.contains(name)) {
/* 136 */       throw new JAXRPCException("Stub does not recognize property: " + name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (name.equals("com.sun.xml.rpc.client.ContentNegotiation")) {
/* 142 */       this._properties.put(name, ((String)value).intern());
/*     */     } else {
/*     */       
/* 145 */       this._properties.put(name, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object _getProperty(String name) {
/* 150 */     if (!_recognizedProperties.contains(name)) {
/* 151 */       throw new JAXRPCException("Stub does not recognize property: " + name);
/*     */     }
/*     */ 
/*     */     
/* 155 */     return this._properties.get(name);
/*     */   }
/*     */   
/*     */   public Iterator _getPropertyNames() {
/* 159 */     return this._properties.keySet().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void _initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 164 */     this._mustInitialize = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _preSendingHook(StreamingSenderState state) throws Exception {
/* 169 */     if (this._mustInitialize) {
/* 170 */       throw new SenderException("sender.stub.notInitialized");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 175 */     SOAPMessageContext messageContext = state.getMessageContext();
/* 176 */     Object userName = _getProperty("javax.xml.rpc.security.auth.username");
/* 177 */     if (userName != null)
/* 178 */       messageContext.setProperty("javax.xml.rpc.security.auth.username", userName); 
/* 179 */     Object password = _getProperty("javax.xml.rpc.security.auth.password");
/* 180 */     if (password != null)
/* 181 */       messageContext.setProperty("javax.xml.rpc.security.auth.password", password); 
/* 182 */     Object address = _getProperty("javax.xml.rpc.service.endpoint.address");
/* 183 */     if (address != null)
/* 184 */       messageContext.setProperty("javax.xml.rpc.service.endpoint.address", address); 
/* 185 */     Object verification = _getProperty("com.sun.xml.rpc.client.http.HostnameVerificationProperty");
/*     */     
/* 187 */     if (verification != null) {
/* 188 */       messageContext.setProperty("com.sun.xml.rpc.client.http.HostnameVerificationProperty", verification);
/*     */     }
/*     */     
/* 191 */     Object style = _getProperty("com.sun.client.OperationStyleProperty");
/*     */     
/* 193 */     if (style != null) {
/* 194 */       messageContext.setProperty("com.sun.client.OperationStyleProperty", style);
/*     */     }
/*     */     
/* 197 */     Object encoding = _getProperty(" com.sun.client.EncodingStyleProperty");
/*     */     
/* 199 */     if (encoding != null) {
/* 200 */       messageContext.setProperty(" com.sun.client.EncodingStyleProperty", encoding);
/*     */     }
/*     */     
/* 203 */     Object maintainSession = _getProperty("javax.xml.rpc.session.maintain");
/* 204 */     if (maintainSession != null) {
/* 205 */       messageContext.setProperty("javax.xml.rpc.session.maintain", maintainSession);
/*     */     }
/*     */     
/* 208 */     if (maintainSession != null && maintainSession.equals(Boolean.TRUE)) {
/* 209 */       Object cookieJar = _getProperty("com.sun.xml.rpc.client.http.CookieJar");
/*     */       
/* 211 */       if (cookieJar != null) {
/* 212 */         messageContext.setProperty("com.sun.xml.rpc.client.http.CookieJar", cookieJar);
/*     */       }
/*     */     } 
/*     */     
/* 216 */     Object securityContext = _getProperty("com.sun.xml.rpc.security.context");
/*     */     
/* 218 */     if (securityContext != null) {
/* 219 */       messageContext.setProperty("com.sun.xml.rpc.security.context", securityContext);
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
/*     */   protected void _postSendingHook(StreamingSenderState state) throws Exception {
/* 231 */     Object maintainSession = _getProperty("javax.xml.rpc.session.maintain");
/* 232 */     if (maintainSession != null && maintainSession.equals(Boolean.TRUE)) {
/* 233 */       Object cookieJar = _getProperty("com.sun.xml.rpc.client.http.CookieJar");
/*     */       
/* 235 */       if (cookieJar == null) {
/* 236 */         SOAPMessageContext messageContext = state.getMessageContext();
/* 237 */         cookieJar = messageContext.getProperty("com.sun.xml.rpc.client.http.CookieJar");
/*     */ 
/*     */         
/* 240 */         _setProperty("com.sun.xml.rpc.client.http.CookieJar", cookieJar);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 248 */     MessageImpl response = (MessageImpl)state.getResponse().getMessage();
/* 249 */     if (!useFastInfoset() && response.isFastInfoset()) {
/* 250 */       this._properties.put("com.sun.xml.rpc.client.ContentNegotiation", "optimistic");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClientTransport _getTransport() {
/* 256 */     if (this._transport == null) {
/* 257 */       this._transport = _getTransportFactory().create();
/*     */     }
/* 259 */     return this._transport;
/*     */   }
/*     */   
/*     */   public ClientTransportFactory _getTransportFactory() {
/* 263 */     if (this._transportFactory == null) {
/* 264 */       this._transportFactory = (ClientTransportFactory)new HttpClientTransportFactory();
/*     */     }
/*     */     
/* 267 */     return this._transportFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   public void _setTransportFactory(ClientTransportFactory f) {
/* 272 */     _setTransportFactory((ClientTransportFactory)f);
/*     */   }
/*     */   
/*     */   public void _setTransportFactory(ClientTransportFactory f) {
/* 276 */     this._transportFactory = f;
/* 277 */     this._transport = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLWriterFactory _getXMLWriterFactory() {
/* 287 */     Object value = _getProperty("com.sun.xml.rpc.client.ContentNegotiation");
/*     */     
/* 289 */     return (value == "optimistic") ? FastInfosetWriterFactoryImpl.newInstance() : XMLWriterFactoryImpl.newInstance();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\StubBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */