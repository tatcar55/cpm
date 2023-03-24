/*     */ package com.sun.xml.ws.db;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.JavaCallInfo;
/*     */ import com.oracle.webservices.api.message.MessageContext;
/*     */ import com.sun.xml.ws.api.databinding.ClientCallBridge;
/*     */ import com.sun.xml.ws.api.databinding.Databinding;
/*     */ import com.sun.xml.ws.api.databinding.DatabindingConfig;
/*     */ import com.sun.xml.ws.api.databinding.EndpointCallBridge;
/*     */ import com.sun.xml.ws.api.databinding.JavaCallInfo;
/*     */ import com.sun.xml.ws.api.databinding.WSDLGenInfo;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.MessageContextFactory;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.MEP;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.sei.StubAsyncHandler;
/*     */ import com.sun.xml.ws.client.sei.StubHandler;
/*     */ import com.sun.xml.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.ws.model.RuntimeModeler;
/*     */ import com.sun.xml.ws.server.sei.TieHandler;
/*     */ import com.sun.xml.ws.wsdl.ActionBasedOperationSignature;
/*     */ import com.sun.xml.ws.wsdl.DispatchException;
/*     */ import com.sun.xml.ws.wsdl.OperationDispatcher;
/*     */ import com.sun.xml.ws.wsdl.writer.WSDLGenerator;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabindingImpl
/*     */   implements Databinding
/*     */ {
/*     */   AbstractSEIModelImpl seiModel;
/*     */   Map<Method, StubHandler> stubHandlers;
/*  89 */   Map<JavaMethodImpl, TieHandler> wsdlOpMap = new HashMap<JavaMethodImpl, TieHandler>();
/*  90 */   Map<Method, TieHandler> tieHandlers = new HashMap<Method, TieHandler>();
/*     */   OperationDispatcher operationDispatcher;
/*     */   OperationDispatcher operationDispatcherNoWsdl;
/*     */   boolean clientConfig = false;
/*     */   Codec codec;
/*  95 */   MessageContextFactory packetFactory = null;
/*     */   
/*     */   public DatabindingImpl(DatabindingProviderImpl p, DatabindingConfig config) {
/*  98 */     RuntimeModeler modeler = new RuntimeModeler(config);
/*  99 */     modeler.setClassLoader(config.getClassLoader());
/* 100 */     this.seiModel = modeler.buildRuntimeModel();
/* 101 */     WSDLPort wsdlport = config.getWsdlPort();
/* 102 */     this.packetFactory = new MessageContextFactory(this.seiModel.getWSBinding().getFeatures());
/* 103 */     this.clientConfig = isClientConfig(config);
/* 104 */     if (this.clientConfig) initStubHandlers(); 
/* 105 */     this.seiModel.setDatabinding(this);
/* 106 */     if (wsdlport != null) freeze(wsdlport); 
/* 107 */     if (this.operationDispatcher == null) this.operationDispatcherNoWsdl = new OperationDispatcher(null, this.seiModel.getWSBinding(), (SEIModel)this.seiModel);
/*     */     
/* 109 */     for (JavaMethodImpl jm : this.seiModel.getJavaMethods()) { if (!jm.isAsync()) {
/* 110 */         TieHandler th = new TieHandler(jm, this.seiModel.getWSBinding(), this.packetFactory);
/* 111 */         this.wsdlOpMap.put(jm, th);
/* 112 */         this.tieHandlers.put(th.getMethod(), th);
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isClientConfig(DatabindingConfig config) {
/* 119 */     if (config.getContractClass() == null) return false; 
/* 120 */     if (!config.getContractClass().isInterface()) return false; 
/* 121 */     return (config.getEndpointClass() == null || config.getEndpointClass().isInterface());
/*     */   }
/*     */   
/*     */   public synchronized void freeze(WSDLPort port) {
/* 125 */     if (this.clientConfig)
/* 126 */       return;  if (this.operationDispatcher != null)
/* 127 */       return;  this.operationDispatcher = (port == null) ? null : new OperationDispatcher(port, this.seiModel.getWSBinding(), (SEIModel)this.seiModel);
/*     */   }
/*     */   
/*     */   public SEIModel getModel() {
/* 131 */     return (SEIModel)this.seiModel;
/*     */   }
/*     */   
/*     */   private void initStubHandlers() {
/* 135 */     this.stubHandlers = new HashMap<Method, StubHandler>();
/* 136 */     Map<ActionBasedOperationSignature, JavaMethodImpl> syncs = new HashMap<ActionBasedOperationSignature, JavaMethodImpl>();
/*     */ 
/*     */     
/* 139 */     for (JavaMethodImpl m : this.seiModel.getJavaMethods()) {
/* 140 */       if (!(m.getMEP()).isAsync) {
/* 141 */         StubHandler handler = new StubHandler(m, this.packetFactory);
/* 142 */         syncs.put(m.getOperationSignature(), m);
/* 143 */         this.stubHandlers.put(m.getMethod(), handler);
/*     */       } 
/*     */     } 
/* 146 */     for (JavaMethodImpl jm : this.seiModel.getJavaMethods()) {
/* 147 */       JavaMethodImpl sync = syncs.get(jm.getOperationSignature());
/* 148 */       if (jm.getMEP() == MEP.ASYNC_CALLBACK || jm.getMEP() == MEP.ASYNC_POLL) {
/* 149 */         Method m = jm.getMethod();
/* 150 */         StubAsyncHandler handler = new StubAsyncHandler(jm, sync, this.packetFactory);
/* 151 */         this.stubHandlers.put(m, handler);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public JavaMethodImpl resolveJavaMethod(Packet req) throws DispatchException {
/* 157 */     WSDLOperationMapping m = req.getWSDLOperationMapping();
/* 158 */     if (m == null) m = (this.operationDispatcher != null) ? this.operationDispatcher.getWSDLOperationMapping(req) : this.operationDispatcherNoWsdl.getWSDLOperationMapping(req);
/*     */ 
/*     */     
/* 161 */     return (JavaMethodImpl)m.getJavaMethod();
/*     */   }
/*     */   
/*     */   public JavaCallInfo deserializeRequest(Packet req) {
/* 165 */     JavaCallInfo call = new JavaCallInfo();
/*     */     try {
/* 167 */       JavaMethodImpl wsdlOp = resolveJavaMethod(req);
/* 168 */       TieHandler tie = this.wsdlOpMap.get(wsdlOp);
/* 169 */       call.setMethod(tie.getMethod());
/* 170 */       Object[] args = tie.readRequest(req.getMessage());
/* 171 */       call.setParameters(args);
/* 172 */     } catch (DispatchException e) {
/* 173 */       call.setException((Throwable)e);
/*     */     } 
/* 175 */     return (JavaCallInfo)call;
/*     */   }
/*     */   
/*     */   public JavaCallInfo deserializeResponse(Packet res, JavaCallInfo call) {
/* 179 */     StubHandler stubHandler = this.stubHandlers.get(call.getMethod());
/*     */     try {
/* 181 */       return stubHandler.readResponse(res, call);
/* 182 */     } catch (Throwable e) {
/* 183 */       call.setException(e);
/* 184 */       return call;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public WebServiceFeature[] getFeatures() {
/* 190 */     return null;
/*     */   }
/*     */   
/*     */   public Packet serializeRequest(JavaCallInfo call) {
/* 194 */     StubHandler stubHandler = this.stubHandlers.get(call.getMethod());
/* 195 */     Packet p = stubHandler.createRequestPacket(call);
/* 196 */     p.setState(Packet.State.ClientRequest);
/* 197 */     return p;
/*     */   }
/*     */   
/*     */   public Packet serializeResponse(JavaCallInfo call) {
/* 201 */     Method method = call.getMethod();
/* 202 */     Message message = null;
/* 203 */     if (method != null) {
/* 204 */       TieHandler th = this.tieHandlers.get(method);
/* 205 */       if (th != null) {
/* 206 */         return th.serializeResponse(call);
/*     */       }
/*     */     } 
/* 209 */     if (call.getException() instanceof DispatchException) {
/* 210 */       message = ((DispatchException)call.getException()).fault;
/*     */     }
/* 212 */     Packet p = (Packet)this.packetFactory.createContext(message);
/* 213 */     p.setState(Packet.State.ServerResponse);
/* 214 */     return p;
/*     */   }
/*     */   
/*     */   public ClientCallBridge getClientBridge(Method method) {
/* 218 */     return (ClientCallBridge)this.stubHandlers.get(method);
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateWSDL(WSDLGenInfo info) {
/* 223 */     WSDLGenerator wsdlGen = new WSDLGenerator(this.seiModel, info.getWsdlResolver(), this.seiModel.getWSBinding(), info.getContainer(), this.seiModel.getEndpointClass(), info.isInlineSchemas(), info.getExtensions());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     wsdlGen.doGeneration();
/*     */   }
/*     */   
/*     */   public EndpointCallBridge getEndpointBridge(Packet req) throws DispatchException {
/* 234 */     JavaMethodImpl wsdlOp = resolveJavaMethod(req);
/* 235 */     return (EndpointCallBridge)this.wsdlOpMap.get(wsdlOp);
/*     */   }
/*     */ 
/*     */   
/*     */   Codec getCodec() {
/* 240 */     if (this.codec == null) this.codec = ((BindingImpl)this.seiModel.getWSBinding()).createCodec(); 
/* 241 */     return this.codec;
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) throws IOException {
/* 245 */     return getCodec().encode(packet, out);
/*     */   }
/*     */   
/*     */   public void decode(InputStream in, String ct, Packet p) throws IOException {
/* 249 */     getCodec().decode(in, ct, p);
/*     */   }
/*     */   
/*     */   public JavaCallInfo createJavaCallInfo(Method method, Object[] args) {
/* 253 */     return (JavaCallInfo)new JavaCallInfo(method, args);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaCallInfo deserializeResponse(MessageContext message, JavaCallInfo call) {
/* 258 */     return deserializeResponse((Packet)message, call);
/*     */   }
/*     */   
/*     */   public JavaCallInfo deserializeRequest(MessageContext message) {
/* 262 */     return deserializeRequest((Packet)message);
/*     */   }
/*     */   
/*     */   public MessageContextFactory getMessageContextFactory() {
/* 266 */     return this.packetFactory;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\db\DatabindingImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */