/*     */ package com.sun.xml.ws.api.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.Component;
/*     */ import com.sun.xml.ws.server.provider.AsyncProviderInvokerTube;
/*     */ import com.sun.xml.ws.server.provider.ProviderArgumentsBuilder;
/*     */ import com.sun.xml.ws.server.provider.ProviderInvokerTube;
/*     */ import com.sun.xml.ws.server.provider.SyncProviderInvokerTube;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ProviderInvokerTubeFactory<T>
/*     */ {
/*  69 */   private static final ProviderInvokerTubeFactory DEFAULT = new DefaultProviderInvokerTubeFactory();
/*     */   
/*     */   protected abstract ProviderInvokerTube<T> doCreate(@NotNull Class<T> paramClass, @NotNull Invoker paramInvoker, @NotNull ProviderArgumentsBuilder<?> paramProviderArgumentsBuilder, boolean paramBoolean);
/*     */   
/*     */   private static class DefaultProviderInvokerTubeFactory<T>
/*     */     extends ProviderInvokerTubeFactory<T> {
/*     */     private DefaultProviderInvokerTubeFactory() {}
/*     */     
/*     */     public ProviderInvokerTube<T> doCreate(@NotNull Class<T> implType, @NotNull Invoker invoker, @NotNull ProviderArgumentsBuilder<?> argsBuilder, boolean isAsync) {
/*  78 */       return createDefault(implType, invoker, argsBuilder, isAsync);
/*     */     }
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
/*     */   public static <T> ProviderInvokerTube<T> create(@Nullable ClassLoader classLoader, @NotNull Container container, @NotNull Class<T> implType, @NotNull Invoker invoker, @NotNull ProviderArgumentsBuilder<?> argsBuilder, boolean isAsync) {
/*  99 */     for (ProviderInvokerTubeFactory<T> factory : (Iterable<ProviderInvokerTubeFactory<T>>)ServiceFinder.find(ProviderInvokerTubeFactory.class, classLoader, (Component)container)) {
/*     */ 
/*     */       
/* 102 */       ProviderInvokerTube<T> tube = factory.doCreate(implType, invoker, argsBuilder, isAsync);
/* 103 */       if (tube != null) {
/* 104 */         if (logger.isLoggable(Level.FINE)) {
/* 105 */           logger.log(Level.FINE, "{0} successfully created {1}", new Object[] { factory.getClass(), tube });
/*     */         }
/* 107 */         return tube;
/*     */       } 
/*     */     } 
/* 110 */     return DEFAULT.createDefault(implType, invoker, argsBuilder, isAsync);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ProviderInvokerTube<T> createDefault(@NotNull Class<T> implType, @NotNull Invoker invoker, @NotNull ProviderArgumentsBuilder<?> argsBuilder, boolean isAsync) {
/* 118 */     return isAsync ? (ProviderInvokerTube<T>)new AsyncProviderInvokerTube(invoker, argsBuilder) : (ProviderInvokerTube<T>)new SyncProviderInvokerTube(invoker, argsBuilder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   private static final Logger logger = Logger.getLogger(ProviderInvokerTubeFactory.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\ProviderInvokerTubeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */