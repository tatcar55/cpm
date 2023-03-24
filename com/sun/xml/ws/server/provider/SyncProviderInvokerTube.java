/*     */ package com.sun.xml.ws.server.provider;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.ThrowableContainerPropertySet;
/*     */ import com.sun.xml.ws.api.server.Invoker;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SyncProviderInvokerTube<T>
/*     */   extends ProviderInvokerTube<T>
/*     */ {
/*  63 */   private static final Logger LOGGER = Logger.getLogger("com.sun.xml.ws.server.SyncProviderInvokerTube");
/*     */ 
/*     */   
/*     */   public SyncProviderInvokerTube(Invoker invoker, ProviderArgumentsBuilder<T> argsBuilder) {
/*  67 */     super(invoker, argsBuilder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/*     */     T returnValue;
/*  77 */     WSDLPort port = getEndpoint().getPort();
/*  78 */     WSBinding binding = getEndpoint().getBinding();
/*  79 */     T param = this.argsBuilder.getParameter(request);
/*     */     
/*  81 */     LOGGER.fine("Invoking Provider Endpoint");
/*     */ 
/*     */     
/*     */     try {
/*  85 */       returnValue = (T)getInvoker(request).invokeProvider(request, param);
/*  86 */     } catch (Exception e) {
/*  87 */       LOGGER.log(Level.SEVERE, e.getMessage(), e);
/*  88 */       Packet packet = this.argsBuilder.getResponse(request, e, port, binding);
/*  89 */       return doReturnWith(packet);
/*     */     } 
/*  91 */     if (returnValue == null)
/*     */     {
/*     */       
/*  94 */       if (request.transportBackChannel != null) {
/*  95 */         request.transportBackChannel.close();
/*     */       }
/*     */     }
/*  98 */     Packet response = this.argsBuilder.getResponse(request, returnValue, port, binding);
/*     */ 
/*     */ 
/*     */     
/* 102 */     ThrowableContainerPropertySet tc = (ThrowableContainerPropertySet)response.getSatellite(ThrowableContainerPropertySet.class);
/* 103 */     Throwable t = (tc != null) ? tc.getThrowable() : null;
/*     */     
/* 105 */     return (t != null) ? doThrow(response, t) : doReturnWith(response);
/*     */   }
/*     */   @NotNull
/*     */   public NextAction processResponse(@NotNull Packet response) {
/* 109 */     return doReturnWith(response);
/*     */   }
/*     */   @NotNull
/*     */   public NextAction processException(@NotNull Throwable t) {
/* 113 */     return doThrow(t);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\provider\SyncProviderInvokerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */