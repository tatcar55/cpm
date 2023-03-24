/*     */ package com.sun.xml.ws.api.server;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import com.sun.xml.ws.server.ServerRtException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractInstanceResolver<T>
/*     */   extends InstanceResolver<T>
/*     */ {
/*     */   protected static ResourceInjector getResourceInjector(WSEndpoint endpoint) {
/*  67 */     ResourceInjector ri = endpoint.getContainer().<ResourceInjector>getSPI(ResourceInjector.class);
/*  68 */     if (ri == null)
/*  69 */       ri = ResourceInjector.STANDALONE; 
/*  70 */     return ri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void invokeMethod(@Nullable final Method method, final Object instance, Object... args) {
/*  77 */     if (method == null)
/*  78 */       return;  AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*     */             try {
/*  81 */               if (!method.isAccessible()) {
/*  82 */                 method.setAccessible(true);
/*     */               }
/*  84 */               method.invoke(instance, args);
/*  85 */             } catch (IllegalAccessException e) {
/*  86 */               throw new ServerRtException("server.rt.err", new Object[] { e });
/*  87 */             } catch (InvocationTargetException e) {
/*  88 */               throw new ServerRtException("server.rt.err", new Object[] { e });
/*     */             } 
/*  90 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected final Method findAnnotatedMethod(Class clazz, Class<? extends Annotation> annType) {
/* 100 */     boolean once = false;
/* 101 */     Method r = null;
/* 102 */     for (Method method : clazz.getDeclaredMethods()) {
/* 103 */       if (method.getAnnotation(annType) != null) {
/* 104 */         if (once)
/* 105 */           throw new ServerRtException(ServerMessages.ANNOTATION_ONLY_ONCE(annType), new Object[0]); 
/* 106 */         if ((method.getParameterTypes()).length != 0)
/* 107 */           throw new ServerRtException(ServerMessages.NOT_ZERO_PARAMETERS(method), new Object[0]); 
/* 108 */         r = method;
/* 109 */         once = true;
/*     */       } 
/*     */     } 
/* 112 */     return r;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\AbstractInstanceResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */