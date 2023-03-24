/*     */ package org.glassfish.ha.store.api;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.glassfish.ha.store.spi.ObjectInputOutputStreamFactory;
/*     */ import org.glassfish.ha.store.spi.ObjectInputOutputStreamFactoryRegistry;
/*     */ import org.glassfish.ha.store.spi.ObjectInputStreamWithLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BackingStore<K extends Serializable, V extends Serializable>
/*     */ {
/*     */   BackingStoreConfiguration<K, V> conf;
/*     */   
/*     */   protected void initialize(BackingStoreConfiguration<K, V> conf) throws BackingStoreException {
/*  77 */     this.conf = conf;
/*     */   }
/*     */   
/*     */   protected BackingStoreConfiguration<K, V> getBackingStoreConfiguration() {
/*  81 */     return this.conf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract BackingStoreFactory getBackingStoreFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract V load(K paramK, String paramString) throws BackingStoreException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String save(K paramK, V paramV, boolean paramBoolean) throws BackingStoreException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void remove(K paramK) throws BackingStoreException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTimestamp(K key, long time) throws BackingStoreException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int removeExpired(long idleForMillis) throws BackingStoreException {
/* 133 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String updateTimestamp(K key, String version, Long accessTime) throws BackingStoreException {
/* 144 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int removeExpired() throws BackingStoreException {
/* 151 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int size() throws BackingStoreException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws BackingStoreException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() throws BackingStoreException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectOutputStream createObjectOutputStream(OutputStream os) throws IOException {
/* 186 */     ObjectInputOutputStreamFactory oosf = ObjectInputOutputStreamFactoryRegistry.getObjectInputOutputStreamFactory();
/* 187 */     return (oosf == null) ? new ObjectOutputStream(os) : oosf.createObjectOutputStream(os);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectInputStream createObjectInputStream(InputStream is) throws IOException {
/* 197 */     return (ObjectInputStream)new ObjectInputStreamWithLoader(is, this.conf.getValueClazz().getClassLoader());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\api\BackingStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */