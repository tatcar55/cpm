/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.sun.xml.ws.api.server.AbstractInstanceResolver;
/*     */ import com.sun.xml.ws.api.server.ResourceInjector;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WSWebServiceContext;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractMultiInstanceResolver<T>
/*     */   extends AbstractInstanceResolver<T>
/*     */ {
/*     */   protected final Class<T> clazz;
/*     */   private WSWebServiceContext webServiceContext;
/*     */   protected WSEndpoint owner;
/*     */   private final Method postConstructMethod;
/*     */   private final Method preDestroyMethod;
/*     */   private ResourceInjector resourceInjector;
/*     */   
/*     */   public AbstractMultiInstanceResolver(Class<T> clazz) {
/*  70 */     this.clazz = clazz;
/*     */     
/*  72 */     this.postConstructMethod = findAnnotatedMethod(clazz, PostConstruct.class);
/*  73 */     this.preDestroyMethod = findAnnotatedMethod(clazz, PreDestroy.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void prepare(T t) {
/*  81 */     assert this.webServiceContext != null;
/*     */     
/*  83 */     this.resourceInjector.inject(this.webServiceContext, t);
/*  84 */     invokeMethod(this.postConstructMethod, t, new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final T create() {
/*  91 */     T t = (T)createNewInstance(this.clazz);
/*  92 */     prepare(t);
/*  93 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start(WSWebServiceContext wsc, WSEndpoint endpoint) {
/*  98 */     this.resourceInjector = getResourceInjector(endpoint);
/*  99 */     this.webServiceContext = wsc;
/* 100 */     this.owner = endpoint;
/*     */   }
/*     */   
/*     */   protected final void dispose(T instance) {
/* 104 */     invokeMethod(this.preDestroyMethod, instance, new Object[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\AbstractMultiInstanceResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */