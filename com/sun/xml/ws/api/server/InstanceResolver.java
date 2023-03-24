/*     */ package com.sun.xml.ws.api.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import com.sun.xml.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.ws.server.ServerRtException;
/*     */ import com.sun.xml.ws.server.SingletonResolver;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.Provider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InstanceResolver<T>
/*     */ {
/*     */   @NotNull
/*     */   public abstract T resolve(@NotNull Packet paramPacket);
/*     */   
/*     */   public void postInvoke(@NotNull Packet request, @NotNull T servant) {}
/*     */   
/*     */   public void start(@NotNull WSWebServiceContext wsc, @NotNull WSEndpoint endpoint) {
/* 135 */     start(wsc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(@NotNull WebServiceContext wsc) {}
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
/*     */   public static <T> InstanceResolver<T> createSingleton(T singleton) {
/*     */     SingletonResolver singletonResolver;
/* 161 */     assert singleton != null;
/* 162 */     InstanceResolver<?> ir = createFromInstanceResolverAnnotation(singleton.getClass());
/* 163 */     if (ir == null)
/* 164 */       singletonResolver = new SingletonResolver(singleton); 
/* 165 */     return (InstanceResolver<T>)singletonResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> InstanceResolver<T> createDefault(@NotNull Class<T> clazz, boolean bool) {
/* 175 */     return createDefault(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> InstanceResolver<T> createDefault(@NotNull Class<T> clazz) {
/*     */     SingletonResolver singletonResolver;
/* 182 */     InstanceResolver<T> ir = createFromInstanceResolverAnnotation(clazz);
/* 183 */     if (ir == null)
/* 184 */       singletonResolver = new SingletonResolver(createNewInstance(clazz)); 
/* 185 */     return (InstanceResolver<T>)singletonResolver;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> InstanceResolver<T> createFromInstanceResolverAnnotation(@NotNull Class<T> clazz) {
/*     */     Annotation[] arr$;
/*     */     int len$;
/*     */     int i$;
/* 193 */     for (arr$ = clazz.getAnnotations(), len$ = arr$.length, i$ = 0; i$ < len$; ) { Annotation a = arr$[i$];
/* 194 */       InstanceResolverAnnotation ira = a.annotationType().<InstanceResolverAnnotation>getAnnotation(InstanceResolverAnnotation.class);
/* 195 */       if (ira == null) { i$++; continue; }
/* 196 */        Class<? extends InstanceResolver> ir = ira.value();
/*     */       try {
/* 198 */         return ir.getConstructor(new Class[] { Class.class }).newInstance(new Object[] { clazz });
/* 199 */       } catch (InstantiationException e) {
/* 200 */         throw new WebServiceException(ServerMessages.FAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(ir.getName(), a.annotationType(), clazz.getName()));
/*     */       }
/* 202 */       catch (IllegalAccessException e) {
/* 203 */         throw new WebServiceException(ServerMessages.FAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(ir.getName(), a.annotationType(), clazz.getName()));
/*     */       }
/* 205 */       catch (InvocationTargetException e) {
/* 206 */         throw new WebServiceException(ServerMessages.FAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(ir.getName(), a.annotationType(), clazz.getName()));
/*     */       }
/* 208 */       catch (NoSuchMethodException e) {
/* 209 */         throw new WebServiceException(ServerMessages.FAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(ir.getName(), a.annotationType(), clazz.getName()));
/*     */       }  }
/*     */ 
/*     */ 
/*     */     
/* 214 */     return null;
/*     */   }
/*     */   
/*     */   protected static <T> T createNewInstance(Class<T> cl) {
/*     */     try {
/* 219 */       return cl.newInstance();
/* 220 */     } catch (InstantiationException e) {
/* 221 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 222 */       throw new ServerRtException(WsservletMessages.ERROR_IMPLEMENTOR_FACTORY_NEW_INSTANCE_FAILED(cl), new Object[0]);
/*     */     }
/* 224 */     catch (IllegalAccessException e) {
/* 225 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 226 */       throw new ServerRtException(WsservletMessages.ERROR_IMPLEMENTOR_FACTORY_NEW_INSTANCE_FAILED(cl), new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Invoker createInvoker() {
/* 235 */     return new Invoker()
/*     */       {
/*     */         public void start(@NotNull WSWebServiceContext wsc, @NotNull WSEndpoint endpoint) {
/* 238 */           InstanceResolver.this.start(wsc, endpoint);
/*     */         }
/*     */ 
/*     */         
/*     */         public void dispose() {
/* 243 */           InstanceResolver.this.dispose();
/*     */         }
/*     */ 
/*     */         
/*     */         public Object invoke(Packet p, Method m, Object... args) throws InvocationTargetException, IllegalAccessException {
/* 248 */           T t = InstanceResolver.this.resolve(p);
/*     */           try {
/* 250 */             return m.invoke(t, args);
/*     */           } finally {
/* 252 */             InstanceResolver.this.postInvoke(p, t);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public <U> U invokeProvider(@NotNull Packet p, U arg) {
/* 258 */           T t = InstanceResolver.this.resolve(p);
/*     */           try {
/* 260 */             return (U)((Provider<Object>)t).invoke((Object)arg);
/*     */           } finally {
/* 262 */             InstanceResolver.this.postInvoke(p, t);
/*     */           } 
/*     */         }
/*     */         
/*     */         public String toString() {
/* 267 */           return "Default Invoker over " + InstanceResolver.this.toString();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/* 272 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.server");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\InstanceResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */