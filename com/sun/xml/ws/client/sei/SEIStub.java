/*     */ package com.sun.xml.ws.client.sei;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.databinding.Databinding;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Headers;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.MEP;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.AsyncResponseImpl;
/*     */ import com.sun.xml.ws.client.RequestContext;
/*     */ import com.sun.xml.ws.client.ResponseContextReceiver;
/*     */ import com.sun.xml.ws.client.Stub;
/*     */ import com.sun.xml.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.ws.model.SOAPSEIModel;
/*     */ import com.sun.xml.ws.wsdl.OperationDispatcher;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SEIStub
/*     */   extends Stub
/*     */   implements InvocationHandler
/*     */ {
/*     */   Databinding databinding;
/*     */   public final SOAPSEIModel seiModel;
/*     */   public final SOAPVersion soapVersion;
/*     */   private final Map<Method, MethodHandler> methodHandlers;
/*     */   
/*     */   @Deprecated
/*     */   public SEIStub(WSServiceDelegate owner, BindingImpl binding, SOAPSEIModel seiModel, Tube master, WSEndpointReference epr) {
/*  83 */     super(owner, master, binding, seiModel.getPort(), seiModel.getPort().getAddress(), epr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     this.methodHandlers = new HashMap<Method, MethodHandler>(); this.seiModel = seiModel; this.soapVersion = binding.getSOAPVersion(); this.databinding = seiModel.getDatabinding(); initMethodHandlers(); } public SEIStub(WSPortInfo portInfo, BindingImpl binding, SOAPSEIModel seiModel, WSEndpointReference epr) { super(portInfo, binding, seiModel.getPort().getAddress(), epr); this.methodHandlers = new HashMap<Method, MethodHandler>(); this.seiModel = seiModel; this.soapVersion = binding.getSOAPVersion(); this.databinding = seiModel.getDatabinding(); initMethodHandlers(); }
/*     */   private void initMethodHandlers() { Map<WSDLBoundOperation, JavaMethodImpl> syncs = new HashMap<WSDLBoundOperation, JavaMethodImpl>(); for (JavaMethodImpl m : this.seiModel.getJavaMethods()) { if (!(m.getMEP()).isAsync) { SyncMethodHandler handler = new SyncMethodHandler(this, m.getMethod()); syncs.put(m.getOperation(), m); this.methodHandlers.put(m.getMethod(), handler); }  }  for (JavaMethodImpl jm : this.seiModel.getJavaMethods()) { JavaMethodImpl sync = syncs.get(jm.getOperation()); if (jm.getMEP() == MEP.ASYNC_CALLBACK) { Method m = jm.getMethod(); CallbackMethodHandler handler = new CallbackMethodHandler(this, m, (m.getParameterTypes()).length - 1); this.methodHandlers.put(m, handler); }  if (jm.getMEP() == MEP.ASYNC_POLL) { Method m = jm.getMethod(); PollingMethodHandler handler = new PollingMethodHandler(this, m); this.methodHandlers.put(m, handler); }
/*     */        }
/* 150 */      } public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*     */     try {
/* 152 */       MethodHandler handler = this.methodHandlers.get(method);
/* 153 */       if (handler != null) {
/* 154 */         return handler.invoke(proxy, args);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 169 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     }  }
/*     */   @Nullable
/*     */   public OperationDispatcher getOperationDispatcher() { if (this.operationDispatcher == null && this.wsdlPort != null)
/*     */       this.operationDispatcher = new OperationDispatcher(this.wsdlPort, (WSBinding)this.binding, (SEIModel)this.seiModel); 
/* 174 */     return this.operationDispatcher; } public final Packet doProcess(Packet request, RequestContext rc, ResponseContextReceiver receiver) { return process(request, rc, receiver); }
/*     */ 
/*     */   
/*     */   public final void doProcessAsync(AsyncResponseImpl<?> receiver, Packet request, RequestContext rc, Fiber.CompletionCallback callback) {
/* 178 */     processAsync(receiver, request, rc, callback);
/*     */   }
/*     */   @NotNull
/*     */   protected final QName getPortName() {
/* 182 */     return this.wsdlPort.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOutboundHeaders(Object... headers) {
/* 187 */     if (headers == null)
/* 188 */       throw new IllegalArgumentException(); 
/* 189 */     Header[] hl = new Header[headers.length];
/* 190 */     for (int i = 0; i < hl.length; i++) {
/* 191 */       if (headers[i] == null)
/* 192 */         throw new IllegalArgumentException(); 
/* 193 */       hl[i] = Headers.create(this.seiModel.getBindingContext(), headers[i]);
/*     */     } 
/* 195 */     setOutboundHeaders(hl);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\SEIStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */