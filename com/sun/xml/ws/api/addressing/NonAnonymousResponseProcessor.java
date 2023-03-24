/*     */ package com.sun.xml.ws.api.addressing;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.TransportTubeFactory;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NonAnonymousResponseProcessor
/*     */ {
/*  60 */   private static final NonAnonymousResponseProcessor DEFAULT = new NonAnonymousResponseProcessor();
/*     */   
/*     */   public static NonAnonymousResponseProcessor getDefault() {
/*  63 */     return DEFAULT;
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
/*     */   public Packet process(Packet packet) {
/*  77 */     Fiber.CompletionCallback fiberCallback = null;
/*  78 */     Fiber currentFiber = Fiber.getCurrentIfSet();
/*  79 */     if (currentFiber != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  85 */       final Fiber.CompletionCallback currentFiberCallback = currentFiber.getCompletionCallback();
/*     */ 
/*     */       
/*  88 */       if (currentFiberCallback != null) {
/*  89 */         fiberCallback = new Fiber.CompletionCallback() {
/*     */             public void onCompletion(@NotNull Packet response) {
/*  91 */               currentFiberCallback.onCompletion(response);
/*     */             }
/*     */             
/*     */             public void onCompletion(@NotNull Throwable error) {
/*  95 */               currentFiberCallback.onCompletion(error);
/*     */             }
/*     */           };
/*  98 */         currentFiber.setCompletionCallback(null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 103 */     WSEndpoint<?> endpoint = packet.endpoint;
/* 104 */     WSBinding binding = endpoint.getBinding();
/* 105 */     Tube transport = TransportTubeFactory.create(Thread.currentThread().getContextClassLoader(), new ClientTubeAssemblerContext(packet.endpointAddress, endpoint.getPort(), (WSService)null, binding, endpoint.getContainer(), ((BindingImpl)binding).createCodec(), null, null));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     Fiber fiber = endpoint.getEngine().createFiber();
/* 111 */     fiber.start(transport, packet, fiberCallback);
/*     */ 
/*     */     
/* 114 */     Packet copy = packet.copy(false);
/* 115 */     copy.endpointAddress = null;
/*     */     
/* 117 */     return copy;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\addressing\NonAnonymousResponseProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */