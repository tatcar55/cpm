/*     */ package com.sun.xml.ws.client;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.Cancelable;
/*     */ import com.sun.xml.ws.util.CompletedFuture;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import javax.xml.ws.AsyncHandler;
/*     */ import javax.xml.ws.Response;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AsyncResponseImpl<T>
/*     */   extends FutureTask<T>
/*     */   implements Response<T>, ResponseContextReceiver
/*     */ {
/*     */   private final AsyncHandler<T> handler;
/*     */   private ResponseContext responseContext;
/*     */   private final Runnable callable;
/*     */   private Cancelable cancelable;
/*     */   
/*     */   public AsyncResponseImpl(Runnable runnable, @Nullable AsyncHandler<T> handler) {
/*  80 */     super(runnable, null);
/*  81 */     this.callable = runnable;
/*  82 */     this.handler = handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  90 */       this.callable.run();
/*  91 */     } catch (WebServiceException e) {
/*     */ 
/*     */       
/*  94 */       set(null, e);
/*  95 */     } catch (Throwable e) {
/*     */ 
/*     */       
/*  98 */       set(null, new WebServiceException(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ResponseContext getContext() {
/* 104 */     return this.responseContext;
/*     */   }
/*     */   
/*     */   public void setResponseContext(ResponseContext rc) {
/* 108 */     this.responseContext = rc;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(T v, Throwable t) {
/* 113 */     if (this.handler != null)
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 131 */         this.handler.handleResponse(new CallbackFuture<T>(v, t));
/* 132 */       } catch (Throwable e) {
/* 133 */         setException(e); return;
/*     */       }   class CallbackFuture<T> extends CompletedFuture<T> implements Response<T> {
/*     */       public CallbackFuture(T v, Throwable t) { super(v, t); }
/*     */       
/* 137 */       public Map<String, Object> getContext() { return AsyncResponseImpl.this.getContext(); } }; if (t != null) {
/* 138 */       setException(t);
/*     */     } else {
/* 140 */       set((V)v);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCancelable(Cancelable cancelable) {
/* 145 */     this.cancelable = cancelable;
/*     */   }
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 149 */     if (this.cancelable != null)
/* 150 */       this.cancelable.cancel(mayInterruptIfRunning); 
/* 151 */     return super.cancel(mayInterruptIfRunning);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\AsyncResponseImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */