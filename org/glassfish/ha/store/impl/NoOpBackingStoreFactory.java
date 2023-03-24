/*    */ package org.glassfish.ha.store.impl;
/*    */ 
/*    */ import org.glassfish.ha.store.api.BackingStore;
/*    */ import org.glassfish.ha.store.api.BackingStoreConfiguration;
/*    */ import org.glassfish.ha.store.api.BackingStoreException;
/*    */ import org.glassfish.ha.store.api.BackingStoreFactory;
/*    */ import org.glassfish.ha.store.api.BackingStoreTransaction;
/*    */ import org.jvnet.hk2.annotations.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service(name = "noop")
/*    */ public class NoOpBackingStoreFactory
/*    */   implements BackingStoreFactory
/*    */ {
/* 55 */   private static BackingStoreTransaction _noOpTransaction = new BackingStoreTransaction()
/*    */     {
/*    */       public void commit() {}
/*    */     };
/*    */ 
/*    */   
/*    */   public <K extends java.io.Serializable, V extends java.io.Serializable> BackingStore<K, V> createBackingStore(BackingStoreConfiguration<K, V> conf) throws BackingStoreException {
/* 62 */     NoOpBackingStore<K, V> store = new NoOpBackingStore<K, V>();
/* 63 */     store.initialize(conf);
/*    */     
/* 65 */     return store;
/*    */   }
/*    */   
/*    */   public BackingStoreTransaction createBackingStoreTransaction() {
/* 69 */     return _noOpTransaction;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\impl\NoOpBackingStoreFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */