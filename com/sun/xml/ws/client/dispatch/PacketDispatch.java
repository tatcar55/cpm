/*     */ package com.sun.xml.ws.client.dispatch;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.client.ThrowableInPacketCompletionFeature;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.WSServiceDelegate;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketDispatch
/*     */   extends DispatchImpl<Packet>
/*     */ {
/*     */   private final boolean isDeliverThrowableInPacket;
/*     */   
/*     */   @Deprecated
/*     */   public PacketDispatch(QName port, WSServiceDelegate owner, Tube pipe, BindingImpl binding, @Nullable WSEndpointReference epr) {
/*  67 */     super(port, Service.Mode.MESSAGE, owner, pipe, binding, epr);
/*  68 */     this.isDeliverThrowableInPacket = calculateIsDeliverThrowableInPacket(binding);
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketDispatch(WSPortInfo portInfo, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/*  73 */     this(portInfo, pipe, binding, epr, true);
/*     */   }
/*     */   
/*     */   public PacketDispatch(WSPortInfo portInfo, Tube pipe, BindingImpl binding, WSEndpointReference epr, boolean allowFaultResponseMsg) {
/*  77 */     super(portInfo, Service.Mode.MESSAGE, pipe, binding, epr, allowFaultResponseMsg);
/*  78 */     this.isDeliverThrowableInPacket = calculateIsDeliverThrowableInPacket(binding);
/*     */   }
/*     */   
/*     */   public PacketDispatch(WSPortInfo portInfo, BindingImpl binding, WSEndpointReference epr) {
/*  82 */     super(portInfo, Service.Mode.MESSAGE, binding, epr, true);
/*  83 */     this.isDeliverThrowableInPacket = calculateIsDeliverThrowableInPacket(binding);
/*     */   }
/*     */   
/*     */   private boolean calculateIsDeliverThrowableInPacket(BindingImpl binding) {
/*  87 */     return binding.isFeatureEnabled(ThrowableInPacketCompletionFeature.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureFiber(Fiber fiber) {
/*  92 */     fiber.setDeliverThrowableInPacket(this.isDeliverThrowableInPacket);
/*     */   }
/*     */ 
/*     */   
/*     */   Packet toReturnValue(Packet response) {
/*  97 */     return response;
/*     */   }
/*     */ 
/*     */   
/*     */   Packet createPacket(Packet request) {
/* 102 */     return request;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\dispatch\PacketDispatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */