/*     */ package org.glassfish.ha.store.spi;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.glassfish.ha.store.api.BackingStoreException;
/*     */ import org.glassfish.ha.store.api.BackingStoreFactory;
/*     */ import org.glassfish.ha.store.impl.NoOpBackingStoreFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BackingStoreFactoryRegistry
/*     */ {
/*  67 */   private static final ConcurrentHashMap<String, BackingStoreFactory> factories = new ConcurrentHashMap<String, BackingStoreFactory>();
/*     */ 
/*     */   
/*     */   static {
/*  71 */     factories.put("noop", new NoOpBackingStoreFactory());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  76 */   private static final List<String> predefinedPersistenceTypes = Arrays.asList(new String[] { "memory", "file" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized BackingStoreFactory register(String type, BackingStoreFactory factory) {
/*  86 */     BackingStoreFactory oldFactory = factories.put(type, factory);
/*  87 */     Logger.getLogger(BackingStoreFactoryRegistry.class.getName()).log(Level.INFO, "Registered " + factory.getClass().getName() + " for persistence-type = " + type + " in BackingStoreFactoryRegistry");
/*     */ 
/*     */ 
/*     */     
/*  91 */     return oldFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized BackingStoreFactory getFactoryInstance(String type) throws BackingStoreException {
/* 102 */     BackingStoreFactory factory = factories.get(type);
/* 103 */     if (factory == null) {
/* 104 */       throw new BackingStoreException("Backing store for persistence-type " + type + " is not registered.");
/*     */     }
/*     */ 
/*     */     
/* 108 */     return factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void unregister(String type) {
/* 116 */     factories.remove(type);
/*     */   }
/*     */   
/*     */   public static synchronized Set<String> getRegisteredTypes() {
/* 120 */     Set<String> result = new HashSet<String>(factories.keySet());
/* 121 */     result.addAll(predefinedPersistenceTypes);
/* 122 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\spi\BackingStoreFactoryRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */