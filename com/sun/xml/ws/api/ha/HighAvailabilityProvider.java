/*     */ package com.sun.xml.ws.api.ha;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import java.io.Serializable;
/*     */ import org.glassfish.ha.store.api.BackingStore;
/*     */ import org.glassfish.ha.store.api.BackingStoreConfiguration;
/*     */ import org.glassfish.ha.store.api.BackingStoreException;
/*     */ import org.glassfish.ha.store.api.BackingStoreFactory;
/*     */ import org.glassfish.ha.store.spi.BackingStoreFactoryRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum HighAvailabilityProvider
/*     */ {
/*  58 */   INSTANCE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile HaEnvironment haEnvironment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final Logger LOGGER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HighAvailabilityProvider() {
/* 145 */     this.haEnvironment = HaEnvironment.NO_HA_ENVIRONMENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     LOGGER = Logger.getLogger(HighAvailabilityProvider.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initHaEnvironment(String clusterName, String instanceName) {
/* 160 */     initHaEnvironment(clusterName, instanceName, false);
/*     */   }
/*     */   public enum StoreType {
/*     */     IN_MEMORY("replicated"), NOOP("noop"); private final String storeTypeId; StoreType(String storeTypeId) { this.storeTypeId = storeTypeId; } } private static class HaEnvironment {
/* 164 */     public static final HaEnvironment NO_HA_ENVIRONMENT = new HaEnvironment(null, null, false); private final String clusterName; private final String instanceName; private final boolean disableJreplica; private HaEnvironment(String clusterName, String instanceName, boolean disableJreplica) { this.clusterName = clusterName; this.instanceName = instanceName; this.disableJreplica = disableJreplica; } public static HaEnvironment getInstance(String clusterName, String instanceName, boolean disableJreplica) { if (clusterName == null && instanceName == null) return NO_HA_ENVIRONMENT;  return new HaEnvironment(clusterName, instanceName, disableJreplica); } public String getClusterName() { return this.clusterName; } public String getInstanceName() { return this.instanceName; } public boolean isDisabledJreplica() { return this.disableJreplica; } public boolean equals(Object obj) { if (this == obj) return true;  if (obj == null) return false;  if (getClass() != obj.getClass()) return false;  HaEnvironment other = (HaEnvironment)obj; if ((this.clusterName == null) ? (other.clusterName != null) : !this.clusterName.equals(other.clusterName)) return false;  if ((this.instanceName == null) ? (other.instanceName != null) : !this.instanceName.equals(other.instanceName)) return false;  return true; } public int hashCode() { int hash = 7; hash = 89 * hash + ((this.clusterName != null) ? this.clusterName.hashCode() : 0); hash = 89 * hash + ((this.instanceName != null) ? this.instanceName.hashCode() : 0); return hash; } } public void initHaEnvironment(String clusterName, String instanceName, boolean disableJreplica) { System.out.println("initHaEnvironment is called: " + clusterName + " " + instanceName);
/* 165 */     this.haEnvironment = HaEnvironment.getInstance(clusterName, instanceName, disableJreplica); }
/*     */ 
/*     */   
/*     */   public boolean isDisabledJreplica() {
/* 169 */     return this.haEnvironment.isDisabledJreplica();
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
/*     */   
/*     */   public <K extends Serializable, V extends Serializable> BackingStoreConfiguration<K, V> initBackingStoreConfiguration(String storeName, Class<K> keyClass, Class<V> valueClass) {
/* 190 */     HaEnvironment env = this.haEnvironment;
/*     */     
/* 192 */     return (new BackingStoreConfiguration()).setClusterName(env.clusterName).setInstanceName(env.getInstanceName()).setStoreName(storeName).setKeyClazz(keyClass).setValueClazz(valueClass);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackingStoreFactory getBackingStoreFactory(StoreType type) throws HighAvailabilityProviderException {
/* 216 */     if (!isHaEnvironmentConfigured()) {
/* 217 */       return getSafeBackingStoreFactory(StoreType.NOOP);
/*     */     }
/*     */     
/* 220 */     return getSafeBackingStoreFactory(type);
/*     */   }
/*     */   
/*     */   private BackingStoreFactory getSafeBackingStoreFactory(StoreType type) throws HighAvailabilityProviderException {
/*     */     try {
/* 225 */       return BackingStoreFactoryRegistry.getFactoryInstance(type.storeTypeId);
/* 226 */     } catch (BackingStoreException ex) {
/* 227 */       throw (HighAvailabilityProviderException)LOGGER.logSevereException(new HighAvailabilityProviderException("", ex));
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
/*     */   public boolean isHaEnvironmentConfigured() {
/* 239 */     return !HaEnvironment.NO_HA_ENVIRONMENT.equals(this.haEnvironment);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <K extends Serializable, V extends Serializable> BackingStore<K, V> createBackingStore(BackingStoreFactory factory, String backingStoreName, Class<K> keyClass, Class<V> valueClass) {
/* 263 */     BackingStoreConfiguration<K, V> bsConfig = initBackingStoreConfiguration(backingStoreName, keyClass, valueClass);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 268 */       return factory.createBackingStore(bsConfig);
/* 269 */     } catch (BackingStoreException ex) {
/* 270 */       throw (HighAvailabilityProviderException)LOGGER.logSevereException(new HighAvailabilityProviderException("", ex));
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
/*     */   public static <K extends Serializable, V extends Serializable> V loadFrom(BackingStore<K, V> backingStore, K key, String version) {
/*     */     try {
/* 290 */       return (V)backingStore.load((Serializable)key, version);
/* 291 */     } catch (BackingStoreException ex) {
/* 292 */       throw (HighAvailabilityProviderException)LOGGER.logSevereException(new HighAvailabilityProviderException("", ex));
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
/*     */   public static <K extends Serializable, V extends Serializable> String saveTo(BackingStore<K, V> backingStore, K key, V value, boolean isNew) {
/*     */     try {
/* 313 */       return backingStore.save((Serializable)key, (Serializable)value, isNew);
/* 314 */     } catch (BackingStoreException ex) {
/* 315 */       throw (HighAvailabilityProviderException)LOGGER.logSevereException(new HighAvailabilityProviderException("", ex));
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
/*     */   public static <K extends Serializable, V extends Serializable> void removeFrom(BackingStore<K, V> backingStore, K key) {
/*     */     try {
/* 332 */       backingStore.remove((Serializable)key);
/* 333 */     } catch (BackingStoreException ex) {
/* 334 */       throw (HighAvailabilityProviderException)LOGGER.logSevereException(new HighAvailabilityProviderException("", ex));
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
/*     */   public static void close(BackingStore<?, ?> backingStore) {
/*     */     try {
/* 349 */       backingStore.close();
/* 350 */     } catch (BackingStoreException ex) {
/* 351 */       throw (HighAvailabilityProviderException)LOGGER.logSevereException(new HighAvailabilityProviderException("", ex));
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
/*     */   public static void destroy(BackingStore<?, ?> backingStore) {
/*     */     try {
/* 365 */       backingStore.destroy();
/* 366 */     } catch (BackingStoreException ex) {
/* 367 */       throw (HighAvailabilityProviderException)LOGGER.logSevereException(new HighAvailabilityProviderException("", ex));
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
/*     */   public static <K extends Serializable> void removeExpired(BackingStore<K, ?> backingStore) {
/*     */     try {
/* 381 */       backingStore.removeExpired();
/* 382 */     } catch (BackingStoreException ex) {
/* 383 */       throw (HighAvailabilityProviderException)LOGGER.logSevereException(new HighAvailabilityProviderException("", ex));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\ha\HighAvailabilityProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */