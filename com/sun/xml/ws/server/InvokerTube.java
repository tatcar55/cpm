/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.server.AsyncProviderCallback;
/*     */ import com.sun.xml.ws.api.server.Invoker;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WSWebServiceContext;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import com.sun.xml.ws.server.sei.Invoker;
/*     */ import com.sun.xml.ws.server.sei.InvokerTube;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InvokerTube<T>
/*     */   extends InvokerTube<Invoker>
/*     */   implements EndpointAwareTube
/*     */ {
/*     */   private WSEndpoint endpoint;
/*     */   
/*     */   protected InvokerTube(Invoker invoker) {
/*  72 */     super((Invoker)invoker);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     this.wrapper = new Invoker()
/*     */       {
/*     */         public Object invoke(Packet p, Method m, Object... args) throws InvocationTargetException, IllegalAccessException {
/* 147 */           Packet old = set(p);
/*     */           try {
/* 149 */             return ((Invoker)InvokerTube.this.invoker).invoke(p, m, args);
/*     */           } finally {
/* 151 */             set(old);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public <T> T invokeProvider(Packet p, T arg) throws IllegalAccessException, InvocationTargetException {
/* 157 */           Packet old = set(p);
/*     */           try {
/* 159 */             return (T)((Invoker)InvokerTube.this.invoker).invokeProvider(p, arg);
/*     */           } finally {
/* 161 */             set(old);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public <T> void invokeAsyncProvider(Packet p, T arg, AsyncProviderCallback cbak, WebServiceContext ctxt) throws IllegalAccessException, InvocationTargetException {
/* 167 */           Packet old = set(p);
/*     */           try {
/* 169 */             ((Invoker)InvokerTube.this.invoker).invokeAsyncProvider(p, arg, cbak, ctxt);
/*     */           } finally {
/* 171 */             set(old);
/*     */           } 
/*     */         }
/*     */         
/*     */         private Packet set(Packet p) {
/* 176 */           Packet old = InvokerTube.packets.get();
/* 177 */           InvokerTube.packets.set(p);
/* 178 */           return old;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void setEndpoint(WSEndpoint endpoint) {
/*     */     this.endpoint = endpoint;
/*     */     WSWebServiceContext webServiceContext = new AbstractWebServiceContext(endpoint) {
/*     */         @Nullable
/*     */         public Packet getRequestPacket() {
/*     */           Packet p = InvokerTube.packets.get();
/*     */           return p;
/*     */         }
/*     */       };
/*     */     ((Invoker)this.invoker).start(webServiceContext, endpoint);
/*     */   }
/*     */   
/*     */   protected WSEndpoint getEndpoint() {
/*     */     return this.endpoint;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public final Invoker getInvoker(Packet request) {
/*     */     return this.wrapper;
/*     */   }
/*     */   
/*     */   public final AbstractTubeImpl copy(TubeCloner cloner) {
/*     */     cloner.add(this, this);
/*     */     return (AbstractTubeImpl)this;
/*     */   }
/*     */   
/*     */   public void preDestroy() {
/*     */     ((Invoker)this.invoker).dispose();
/*     */   }
/*     */   
/*     */   private static final ThreadLocal<Packet> packets = new ThreadLocal<Packet>();
/*     */   private final Invoker wrapper;
/*     */   
/*     */   @NotNull
/*     */   public static Packet getCurrentPacket() {
/*     */     Packet packet = packets.get();
/*     */     if (packet == null)
/*     */       throw new WebServiceException(ServerMessages.NO_CURRENT_PACKET()); 
/*     */     return packet;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\InvokerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */