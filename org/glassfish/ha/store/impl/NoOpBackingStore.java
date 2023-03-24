/*    */ package org.glassfish.ha.store.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.glassfish.ha.store.api.BackingStore;
/*    */ import org.glassfish.ha.store.api.BackingStoreConfiguration;
/*    */ import org.glassfish.ha.store.api.BackingStoreException;
/*    */ import org.glassfish.ha.store.api.BackingStoreFactory;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoOpBackingStore<K extends Serializable, V extends Serializable>
/*    */   extends BackingStore<K, V>
/*    */ {
/*    */   private String myName;
/*    */   
/*    */   protected void initialize(BackingStoreConfiguration<K, V> conf) throws BackingStoreException {
/* 62 */     super.initialize(conf);
/*    */     
/* 64 */     this.myName = (conf == null) ? null : conf.getInstanceName();
/*    */   }
/*    */ 
/*    */   
/*    */   public BackingStoreFactory getBackingStoreFactory() {
/* 69 */     return new NoOpBackingStoreFactory();
/*    */   }
/*    */ 
/*    */   
/*    */   public V load(K key, String version) throws BackingStoreException {
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String save(K key, V value, boolean isNew) throws BackingStoreException {
/* 79 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove(K key) throws BackingStoreException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public String updateTimestamp(K key, String version, Long accessTime) throws BackingStoreException {
/* 89 */     return this.myName;
/*    */   }
/*    */ 
/*    */   
/*    */   public int removeExpired() throws BackingStoreException {
/* 94 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() throws BackingStoreException {
/* 99 */     return 0;
/*    */   }
/*    */   
/*    */   public void destroy() throws BackingStoreException {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\impl\NoOpBackingStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */