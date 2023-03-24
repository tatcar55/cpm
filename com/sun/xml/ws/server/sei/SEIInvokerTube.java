/*     */ package com.sun.xml.ws.server.sei;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.JavaCallInfo;
/*     */ import com.oracle.webservices.api.message.MessageContext;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.server.Invoker;
/*     */ import com.sun.xml.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.ws.server.InvokerTube;
/*     */ import com.sun.xml.ws.wsdl.DispatchException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SEIInvokerTube
/*     */   extends InvokerTube
/*     */ {
/*     */   private final WSBinding binding;
/*     */   private final AbstractSEIModelImpl model;
/*     */   
/*     */   public SEIInvokerTube(AbstractSEIModelImpl model, Invoker invoker, WSBinding binding) {
/*  71 */     super(invoker);
/*  72 */     this.binding = binding;
/*  73 */     this.model = model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processRequest(@NotNull Packet req) {
/*  82 */     JavaCallInfo call = this.model.getDatabinding().deserializeRequest((MessageContext)req);
/*  83 */     if (call.getException() == null) {
/*     */       try {
/*  85 */         if (req.getMessage().isOneWay(this.model.getPort()) && req.transportBackChannel != null) {
/*  86 */           req.transportBackChannel.close();
/*     */         }
/*  88 */         Object ret = getInvoker(req).invoke(req, call.getMethod(), call.getParameters());
/*  89 */         call.setReturnValue(ret);
/*  90 */       } catch (InvocationTargetException e) {
/*  91 */         call.setException(e);
/*  92 */       } catch (Exception e) {
/*  93 */         call.setException(e);
/*     */       } 
/*  95 */     } else if (call.getException() instanceof DispatchException) {
/*  96 */       DispatchException e = (DispatchException)call.getException();
/*  97 */       return doReturnWith(req.createServerResponse(e.fault, this.model.getPort(), null, this.binding));
/*     */     } 
/*  99 */     Packet res = (Packet)this.model.getDatabinding().serializeResponse(call);
/* 100 */     res = req.relateServerResponse(res, req.endpoint.getPort(), (SEIModel)this.model, req.endpoint.getBinding());
/* 101 */     assert res != null;
/* 102 */     return doReturnWith(res);
/*     */   }
/*     */   @NotNull
/*     */   public NextAction processResponse(@NotNull Packet response) {
/* 106 */     return doReturnWith(response);
/*     */   }
/*     */   @NotNull
/*     */   public NextAction processException(@NotNull Throwable t) {
/* 110 */     return doThrow(t);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\sei\SEIInvokerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */