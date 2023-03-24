/*     */ package com.sun.xml.ws.client.sei;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.JavaCallInfo;
/*     */ import com.oracle.webservices.api.message.MessageContext;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.client.AsyncInvoker;
/*     */ import com.sun.xml.ws.client.AsyncResponseImpl;
/*     */ import com.sun.xml.ws.client.RequestContext;
/*     */ import com.sun.xml.ws.client.ResponseContext;
/*     */ import java.lang.reflect.Method;
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
/*     */ abstract class AsyncMethodHandler
/*     */   extends MethodHandler
/*     */ {
/*     */   AsyncMethodHandler(SEIStub owner, Method m) {
/*  70 */     super(owner, m);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Response<Object> doInvoke(Object proxy, Object[] args, AsyncHandler handler) {
/* 143 */     AsyncInvoker invoker = new SEIAsyncInvoker(proxy, args);
/* 144 */     invoker.setNonNullAsyncHandlerGiven((handler != null));
/* 145 */     AsyncResponseImpl<Object> ft = new AsyncResponseImpl((Runnable)invoker, handler);
/* 146 */     invoker.setReceiver(ft);
/* 147 */     ft.run();
/* 148 */     return (Response<Object>)ft;
/*     */   }
/*     */   
/*     */   private class SEIAsyncInvoker
/*     */     extends AsyncInvoker
/*     */   {
/* 154 */     private final RequestContext rc = AsyncMethodHandler.this.owner.requestContext.copy();
/*     */     private final Object[] args;
/*     */     
/*     */     SEIAsyncInvoker(Object proxy, Object[] args) {
/* 158 */       this.args = args;
/*     */     }
/*     */     
/*     */     public void do_run() {
/* 162 */       JavaCallInfo call = AsyncMethodHandler.this.owner.databinding.createJavaCallInfo(AsyncMethodHandler.this.method, this.args);
/* 163 */       Packet req = (Packet)AsyncMethodHandler.this.owner.databinding.serializeRequest(call);
/*     */       
/* 165 */       Fiber.CompletionCallback callback = new Fiber.CompletionCallback()
/*     */         {
/*     */           public void onCompletion(@NotNull Packet response) {
/* 168 */             AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.setResponseContext(new ResponseContext(response));
/* 169 */             Message msg = response.getMessage();
/* 170 */             if (msg == null) {
/*     */               return;
/*     */             }
/*     */             try {
/* 174 */               Object[] rargs = new Object[1];
/* 175 */               JavaCallInfo call = AsyncMethodHandler.this.owner.databinding.createJavaCallInfo(AsyncMethodHandler.this.method, rargs);
/* 176 */               call = AsyncMethodHandler.this.owner.databinding.deserializeResponse((MessageContext)response, call);
/* 177 */               if (call.getException() != null) {
/* 178 */                 throw call.getException();
/*     */               }
/* 180 */               AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(rargs[0], null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 195 */             catch (Throwable t) {
/* 196 */               if (t instanceof RuntimeException) {
/* 197 */                 if (t instanceof WebServiceException) {
/* 198 */                   AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(null, t);
/*     */                   return;
/*     */                 } 
/* 201 */               } else if (t instanceof Exception) {
/* 202 */                 AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(null, t);
/*     */                 
/*     */                 return;
/*     */               } 
/*     */               
/* 207 */               AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(null, new WebServiceException(t));
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onCompletion(@NotNull Throwable error) {
/* 213 */             if (error instanceof WebServiceException) {
/* 214 */               AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(null, error);
/*     */             }
/*     */             else {
/*     */               
/* 218 */               AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(null, new WebServiceException(error));
/*     */             } 
/*     */           }
/*     */         };
/* 222 */       AsyncMethodHandler.this.owner.doProcessAsync(this.responseImpl, req, this.rc, callback);
/*     */     }
/*     */   }
/*     */   
/*     */   ValueGetterFactory getValueGetterFactory() {
/* 227 */     return ValueGetterFactory.ASYNC;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\AsyncMethodHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */