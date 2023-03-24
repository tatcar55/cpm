/*     */ package com.sun.xml.ws.server.provider;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.ThrowableContainerPropertySet;
/*     */ import com.sun.xml.ws.api.server.AsyncProviderCallback;
/*     */ import com.sun.xml.ws.api.server.Invoker;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.server.AbstractWebServiceContext;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsyncProviderInvokerTube<T>
/*     */   extends ProviderInvokerTube<T>
/*     */ {
/*  67 */   private static final Logger LOGGER = Logger.getLogger("com.sun.xml.ws.server.AsyncProviderInvokerTube");
/*     */ 
/*     */   
/*     */   public AsyncProviderInvokerTube(Invoker invoker, ProviderArgumentsBuilder<T> argsBuilder) {
/*  71 */     super(invoker, argsBuilder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processRequest(@NotNull Packet request) {
/*  81 */     T param = this.argsBuilder.getParameter(request);
/*  82 */     NoSuspendResumer resumer = new NoSuspendResumer();
/*     */     
/*  84 */     AsyncProviderCallbackImpl callback = new AsyncProviderCallbackImpl(request, resumer);
/*  85 */     AsyncWebServiceContext ctxt = new AsyncWebServiceContext(getEndpoint(), request);
/*     */     
/*  87 */     LOGGER.fine("Invoking AsyncProvider Endpoint");
/*     */     try {
/*  89 */       getInvoker(request).invokeAsyncProvider(request, param, callback, (WebServiceContext)ctxt);
/*  90 */     } catch (Throwable e) {
/*  91 */       LOGGER.log(Level.SEVERE, e.getMessage(), e);
/*  92 */       return doThrow(e);
/*     */     } 
/*     */     
/*  95 */     synchronized (callback) {
/*  96 */       if (resumer.response != null) {
/*     */ 
/*     */         
/*  99 */         ThrowableContainerPropertySet tc = (ThrowableContainerPropertySet)resumer.response.getSatellite(ThrowableContainerPropertySet.class);
/* 100 */         Throwable t = (tc != null) ? tc.getThrowable() : null;
/*     */         
/* 102 */         return (t != null) ? doThrow(resumer.response, t) : doReturnWith(resumer.response);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 107 */       callback.resumer = new FiberResumer();
/* 108 */       return doSuspend();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static interface Resumer
/*     */   {
/*     */     void onResume(Packet param1Packet);
/*     */   }
/*     */   
/*     */   public class FiberResumer
/*     */     implements Resumer
/*     */   {
/* 120 */     private final Fiber fiber = Fiber.current();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onResume(Packet response) {
/* 126 */       ThrowableContainerPropertySet tc = (ThrowableContainerPropertySet)response.getSatellite(ThrowableContainerPropertySet.class);
/* 127 */       Throwable t = (tc != null) ? tc.getThrowable() : null;
/* 128 */       this.fiber.resume(t, response);
/*     */     }
/*     */   }
/*     */   
/*     */   private class NoSuspendResumer implements Resumer {
/* 133 */     protected Packet response = null;
/*     */     
/*     */     public void onResume(Packet response) {
/* 136 */       this.response = response;
/*     */     }
/*     */     
/*     */     private NoSuspendResumer() {} }
/*     */   
/*     */   public class AsyncProviderCallbackImpl implements AsyncProviderCallback<T> {
/*     */     private final Packet request;
/*     */     
/*     */     public AsyncProviderCallbackImpl(Packet request, AsyncProviderInvokerTube.Resumer resumer) {
/* 145 */       this.request = request;
/* 146 */       this.resumer = resumer;
/*     */     }
/*     */     private AsyncProviderInvokerTube.Resumer resumer;
/*     */     public void send(@Nullable T param) {
/* 150 */       if (param == null && 
/* 151 */         this.request.transportBackChannel != null) {
/* 152 */         this.request.transportBackChannel.close();
/*     */       }
/*     */       
/* 155 */       Packet packet = AsyncProviderInvokerTube.this.argsBuilder.getResponse(this.request, param, AsyncProviderInvokerTube.this.getEndpoint().getPort(), AsyncProviderInvokerTube.this.getEndpoint().getBinding());
/* 156 */       synchronized (this) {
/* 157 */         this.resumer.onResume(packet);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void sendError(@NotNull Throwable t) {
/*     */       Exception e;
/* 163 */       if (t instanceof Exception) {
/* 164 */         e = (Exception)t;
/*     */       } else {
/* 166 */         e = new RuntimeException(t);
/*     */       } 
/* 168 */       Packet packet = AsyncProviderInvokerTube.this.argsBuilder.getResponse(this.request, e, AsyncProviderInvokerTube.this.getEndpoint().getPort(), AsyncProviderInvokerTube.this.getEndpoint().getBinding());
/* 169 */       synchronized (this) {
/* 170 */         this.resumer.onResume(packet);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class AsyncWebServiceContext
/*     */     extends AbstractWebServiceContext
/*     */   {
/*     */     final Packet packet;
/*     */     
/*     */     public AsyncWebServiceContext(WSEndpoint endpoint, Packet packet) {
/* 182 */       super(endpoint);
/* 183 */       this.packet = packet;
/*     */     }
/*     */     @NotNull
/*     */     public Packet getRequestPacket() {
/* 187 */       return this.packet;
/*     */     } }
/*     */   
/*     */   @NotNull
/*     */   public NextAction processResponse(@NotNull Packet response) {
/* 192 */     return doReturnWith(response);
/*     */   }
/*     */   @NotNull
/*     */   public NextAction processException(@NotNull Throwable t) {
/* 196 */     return doThrow(t);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\provider\AsyncProviderInvokerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */