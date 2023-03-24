/*     */ package com.sun.xml.ws.api.client;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ServiceInterceptorFactory
/*     */ {
/*     */   public abstract ServiceInterceptor create(@NotNull WSService paramWSService);
/*     */   
/*     */   @NotNull
/*     */   public static ServiceInterceptor load(@NotNull WSService service, @Nullable ClassLoader cl) {
/*  81 */     List<ServiceInterceptor> l = new ArrayList<ServiceInterceptor>();
/*     */ 
/*     */     
/*  84 */     for (ServiceInterceptorFactory f : ServiceFinder.find(ServiceInterceptorFactory.class)) {
/*  85 */       l.add(f.create(service));
/*     */     }
/*     */     
/*  88 */     for (ServiceInterceptorFactory f : threadLocalFactories.get()) {
/*  89 */       l.add(f.create(service));
/*     */     }
/*  91 */     return ServiceInterceptor.aggregate(l.<ServiceInterceptor>toArray(new ServiceInterceptor[l.size()]));
/*     */   }
/*     */   
/*  94 */   private static ThreadLocal<Set<ServiceInterceptorFactory>> threadLocalFactories = new ThreadLocal<Set<ServiceInterceptorFactory>>() {
/*     */       protected Set<ServiceInterceptorFactory> initialValue() {
/*  96 */         return new HashSet<ServiceInterceptorFactory>();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean registerForThread(ServiceInterceptorFactory factory) {
/* 108 */     return ((Set<ServiceInterceptorFactory>)threadLocalFactories.get()).add(factory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean unregisterForThread(ServiceInterceptorFactory factory) {
/* 115 */     return ((Set)threadLocalFactories.get()).remove(factory);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\client\ServiceInterceptorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */