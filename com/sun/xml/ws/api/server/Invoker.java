/*     */ package com.sun.xml.ws.api.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.server.sei.Invoker;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.xml.ws.Provider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Invoker
/*     */   extends Invoker
/*     */ {
/*     */   private static final Method invokeMethod;
/*     */   private static final Method asyncInvokeMethod;
/*     */   
/*     */   public void start(@NotNull WSWebServiceContext wsc, @NotNull WSEndpoint endpoint) {
/*  79 */     start(wsc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(@NotNull WebServiceContext wsc) {
/*  87 */     throw new IllegalStateException("deprecated version called");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T invokeProvider(@NotNull Packet p, T arg) throws IllegalAccessException, InvocationTargetException {
/* 106 */     return (T)invoke(p, invokeMethod, new Object[] { arg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void invokeAsyncProvider(@NotNull Packet p, T arg, AsyncProviderCallback cbak, WebServiceContext ctxt) throws IllegalAccessException, InvocationTargetException {
/* 114 */     invoke(p, asyncInvokeMethod, new Object[] { arg, cbak, ctxt });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 121 */       invokeMethod = Provider.class.getMethod("invoke", new Class[] { Object.class });
/* 122 */     } catch (NoSuchMethodException e) {
/* 123 */       throw new AssertionError(e);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 131 */       asyncInvokeMethod = AsyncProvider.class.getMethod("invoke", new Class[] { Object.class, AsyncProviderCallback.class, WebServiceContext.class });
/* 132 */     } catch (NoSuchMethodException e) {
/* 133 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\Invoker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */