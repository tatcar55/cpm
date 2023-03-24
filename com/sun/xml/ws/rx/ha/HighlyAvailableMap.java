/*     */ package com.sun.xml.ws.rx.ha;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.ha.HaInfo;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.commons.ha.StickyKey;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.logging.Level;
/*     */ import org.glassfish.ha.store.api.BackingStore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HighlyAvailableMap<K extends Serializable, V>
/*     */   implements Map<K, V>
/*     */ {
/*     */   private final Map<K, V> localMap;
/*  66 */   private static final Logger LOGGER = Logger.getLogger(HighlyAvailableMap.class);
/*     */   
/*     */   public static final class NoopReplicationManager<K extends Serializable, V> implements ReplicationManager<K, V> {
/*     */     private final String loggerProlog;
/*     */     
/*     */     public NoopReplicationManager(String name) {
/*  72 */       this.loggerProlog = "[" + name + "]: ";
/*     */     }
/*     */     public V load(K key) {
/*  75 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/*  76 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "load() method invoked for key: " + key);
/*     */       }
/*  78 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void save(K key, V value, boolean isNew) {
/*  83 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/*  84 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "save() method invoked for [key=" + key + ", value=" + value + ", isNew=" + isNew + "]");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove(K key) {
/*  90 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/*  91 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "remove() method invoked for key: " + key);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {
/*  97 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/*  98 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "close() invoked");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void destroy() {
/* 104 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER))
/* 105 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "destroy() invoked"); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class SimpleReplicationManager<K extends Serializable, V extends Serializable>
/*     */     implements ReplicationManager<K, V>
/*     */   {
/*     */     private final BackingStore<K, V> backingStore;
/*     */     private final String loggerProlog;
/*     */     
/*     */     public SimpleReplicationManager(String name, BackingStore<K, V> backingStore) {
/* 116 */       this.backingStore = backingStore;
/* 117 */       this.loggerProlog = "[" + name + "]: ";
/*     */     }
/*     */     
/*     */     public V load(K key) {
/* 121 */       Serializable serializable = HighAvailabilityProvider.loadFrom(this.backingStore, (Serializable)key, null);
/* 122 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 123 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "loaded data for key [" + key + "]: " + serializable);
/*     */       }
/* 125 */       return (V)serializable;
/*     */     }
/*     */     
/*     */     public void save(K key, V value, boolean isNew) {
/* 129 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 130 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "sending for replication [key=" + key + ", value=" + value + ", isNew=" + isNew + "]");
/*     */       }
/* 132 */       HighAvailabilityProvider.saveTo(this.backingStore, (Serializable)key, (Serializable)value, isNew);
/*     */     }
/*     */     
/*     */     public void remove(K key) {
/* 136 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 137 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "removing data for key: " + key);
/*     */       }
/* 139 */       HighAvailabilityProvider.removeFrom(this.backingStore, (Serializable)key);
/*     */     }
/*     */     
/*     */     public void close() {
/* 143 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 144 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "closing backing store");
/*     */       }
/* 146 */       HighAvailabilityProvider.close(this.backingStore);
/*     */     }
/*     */     
/*     */     public void destroy() {
/* 150 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 151 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "destroying backing store");
/*     */       }
/* 153 */       HighAvailabilityProvider.destroy(this.backingStore);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class StickyReplicationManager<K extends Serializable, V extends Serializable>
/*     */     implements ReplicationManager<K, V> {
/*     */     private final BackingStore<StickyKey, V> backingStore;
/*     */     private final String loggerProlog;
/*     */     
/*     */     public StickyReplicationManager(String name, BackingStore<StickyKey, V> backingStore) {
/* 163 */       this.backingStore = backingStore;
/* 164 */       this.loggerProlog = "[" + name + "]: ";
/*     */     }
/*     */     
/*     */     public V load(K key) {
/* 168 */       Serializable serializable = HighAvailabilityProvider.loadFrom(this.backingStore, (Serializable)new StickyKey((Serializable)key), null);
/* 169 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 170 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "loaded data for key [" + key + "]: " + serializable);
/*     */       }
/* 172 */       return (V)serializable;
/*     */     }
/*     */     
/*     */     public void save(K key, V value, boolean isNew) {
/* 176 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 177 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "sending for replication [key=" + key + ", value=" + value + ", isNew=" + isNew + "]");
/*     */       }
/*     */       
/* 180 */       HaInfo haInfo = HaContext.currentHaInfo();
/* 181 */       if (haInfo != null) {
/* 182 */         if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 183 */           HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "Existing HaInfo found, using it for data replication: " + HaContext.asString(haInfo));
/*     */         }
/* 185 */         HaContext.udpateReplicaInstance(HighAvailabilityProvider.saveTo(this.backingStore, (Serializable)new StickyKey((Serializable)key, haInfo.getKey()), (Serializable)value, isNew));
/*     */       } else {
/* 187 */         StickyKey stickyKey = new StickyKey((Serializable)key);
/* 188 */         String replicaId = HighAvailabilityProvider.saveTo(this.backingStore, (Serializable)stickyKey, (Serializable)value, isNew);
/*     */         
/* 190 */         haInfo = new HaInfo(stickyKey.getHashKey(), replicaId, false);
/* 191 */         HaContext.updateHaInfo(haInfo);
/* 192 */         if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 193 */           HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "No HaInfo found, created new after data replication: " + HaContext.asString(haInfo));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove(K key) {
/* 199 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 200 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "removing data for key: " + key);
/*     */       }
/* 202 */       HighAvailabilityProvider.removeFrom(this.backingStore, (Serializable)new StickyKey((Serializable)key));
/*     */     }
/*     */     
/*     */     public void close() {
/* 206 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 207 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "closing backing store");
/*     */       }
/* 209 */       HighAvailabilityProvider.close(this.backingStore);
/*     */     }
/*     */     
/*     */     public void destroy() {
/* 213 */       if (HighlyAvailableMap.LOGGER.isLoggable(Level.FINER)) {
/* 214 */         HighlyAvailableMap.LOGGER.finer(this.loggerProlog + "destroying backing store");
/*     */       }
/* 216 */       HighAvailabilityProvider.destroy(this.backingStore);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 221 */   private final ReadWriteLock dataLock = new ReentrantReadWriteLock();
/*     */   private final ReplicationManager<K, V> replicationManager;
/*     */   private final String loggerProlog;
/*     */   
/*     */   public static <K extends Serializable, V extends Serializable> HighlyAvailableMap<K, V> create(String name, BackingStore<K, V> backingStore) {
/* 226 */     return new HighlyAvailableMap<K, V>(name, new HashMap<K, V>(), new SimpleReplicationManager<K, V>(name + "_MANAGER", backingStore));
/*     */   }
/*     */   
/*     */   public static <K extends Serializable, V extends Serializable> HighlyAvailableMap<K, V> createSticky(String name, BackingStore<StickyKey, V> backingStore) {
/* 230 */     return new HighlyAvailableMap<K, V>(name, new HashMap<K, V>(), new StickyReplicationManager<K, V>(name + "_MANAGER", backingStore));
/*     */   }
/*     */   
/*     */   public static <K extends Serializable, V> HighlyAvailableMap<K, V> create(String name, ReplicationManager<K, V> replicationManager) {
/* 234 */     if (replicationManager == null) {
/* 235 */       replicationManager = new NoopReplicationManager<K, V>(name + "_MANAGER");
/*     */     }
/*     */     
/* 238 */     return new HighlyAvailableMap<K, V>(name, new HashMap<K, V>(), replicationManager);
/*     */   }
/*     */   
/*     */   private HighlyAvailableMap(String name, Map<K, V> wrappedMap, ReplicationManager<K, V> replicationManager) {
/* 242 */     this.loggerProlog = "[" + name + "]: ";
/* 243 */     this.localMap = wrappedMap;
/* 244 */     this.replicationManager = replicationManager;
/*     */   }
/*     */   
/*     */   public int size() {
/* 248 */     this.dataLock.readLock().lock();
/*     */     
/*     */     try {
/* 251 */       return this.localMap.size();
/*     */     } finally {
/* 253 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 258 */     this.dataLock.readLock().lock();
/*     */     
/*     */     try {
/* 261 */       return this.localMap.isEmpty();
/*     */     } finally {
/* 263 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 269 */     Serializable serializable = (Serializable)key;
/*     */     
/* 271 */     this.dataLock.readLock().lock();
/*     */     
/*     */     try {
/* 274 */       if (this.localMap.containsKey(serializable)) {
/* 275 */         return true;
/*     */       }
/*     */       
/* 278 */       return (tryLoad((K)serializable) != null);
/*     */     } finally {
/* 280 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 286 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public V get(Object key) {
/* 290 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 291 */       LOGGER.finer(this.loggerProlog + "Retrieving data for key [" + key + "]");
/*     */     }
/*     */ 
/*     */     
/* 295 */     Serializable serializable = (Serializable)key;
/*     */     
/* 297 */     this.dataLock.readLock().lock();
/*     */     
/*     */     try {
/* 300 */       V value = this.localMap.get(serializable);
/* 301 */       if (value != null) {
/* 302 */         if (LOGGER.isLoggable(Level.FINER)) {
/* 303 */           LOGGER.finer(this.loggerProlog + "Data for key [" + key + "] found in a local cache: " + value);
/*     */         }
/* 305 */         return value;
/*     */       } 
/*     */       
/* 308 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 309 */         LOGGER.finer(this.loggerProlog + "Data for key [" + key + "] not found in the local cache - consulting replication manager");
/*     */       }
/* 311 */       return tryLoad((K)serializable);
/*     */     } finally {
/* 313 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public V put(K key, V value) {
/* 318 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 319 */       LOGGER.finer(this.loggerProlog + "Storing data for key [" + key + "]: " + value);
/*     */     }
/*     */     
/* 322 */     this.dataLock.writeLock().lock();
/*     */     try {
/* 324 */       V oldValue = this.localMap.put(key, value);
/* 325 */       this.replicationManager.save(key, value, (oldValue == null));
/* 326 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 327 */         LOGGER.finer(this.loggerProlog + "Old data replaced for key [" + key + "]: " + oldValue);
/*     */       }
/*     */       
/* 330 */       return oldValue;
/*     */     } finally {
/* 332 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(Object key) {
/* 338 */     Serializable serializable = (Serializable)key;
/*     */     
/* 340 */     V oldValue = get(serializable);
/*     */     
/* 342 */     this.dataLock.writeLock().lock();
/*     */     try {
/* 344 */       this.localMap.remove(serializable);
/* 345 */       this.replicationManager.remove((K)serializable);
/* 346 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 347 */         LOGGER.finer(this.loggerProlog + "Removing data for key [" + key + "]: " + oldValue);
/*     */       }
/*     */       
/* 350 */       return oldValue;
/*     */     } finally {
/* 352 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends V> m) {
/* 358 */     this.dataLock.writeLock().lock();
/*     */     try {
/* 360 */       for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
/* 361 */         put(e.getKey(), e.getValue());
/*     */       }
/*     */     } finally {
/* 364 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private V tryLoad(K key) {
/* 369 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 370 */       LOGGER.finer(this.loggerProlog + "Using replication manager to load data for key [" + key + "]");
/*     */     }
/*     */     
/* 373 */     this.dataLock.readLock().unlock();
/* 374 */     this.dataLock.writeLock().lock();
/*     */     try {
/* 376 */       V value = this.localMap.get(key);
/* 377 */       if (value != null) {
/* 378 */         return value;
/*     */       }
/*     */       
/* 381 */       value = this.replicationManager.load(key);
/* 382 */       if (value != null) {
/* 383 */         this.localMap.put(key, value);
/*     */       }
/*     */       
/* 386 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 387 */         LOGGER.finer(this.loggerProlog + "Replication manager returned data for key [" + key + "]: " + value);
/*     */       }
/*     */       
/* 390 */       return value;
/*     */     } finally {
/* 392 */       this.dataLock.readLock().lock();
/* 393 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 398 */     this.dataLock.writeLock().lock();
/*     */     try {
/* 400 */       for (Serializable serializable : this.localMap.keySet()) {
/* 401 */         this.replicationManager.remove((K)serializable);
/*     */       }
/*     */       
/* 404 */       this.localMap.clear();
/* 405 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 406 */         LOGGER.finer(this.loggerProlog + "HA map cleared");
/*     */       }
/*     */     } finally {
/* 409 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<K> keySet() {
/* 414 */     this.dataLock.readLock().lock();
/*     */     
/*     */     try {
/* 417 */       return this.localMap.keySet();
/*     */     } finally {
/* 419 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection<V> values() {
/* 424 */     this.dataLock.readLock().lock();
/*     */     
/*     */     try {
/* 427 */       return this.localMap.values();
/*     */     } finally {
/* 429 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 434 */     this.dataLock.readLock().lock();
/*     */     
/*     */     try {
/* 437 */       return this.localMap.entrySet();
/*     */     } finally {
/* 439 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<K, V> getLocalMapCopy() {
/* 444 */     this.dataLock.readLock().lock();
/*     */     
/*     */     try {
/* 447 */       return new HashMap<K, V>(this.localMap);
/*     */     } finally {
/* 449 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invalidateCache() {
/* 454 */     this.dataLock.writeLock().lock();
/*     */     
/*     */     try {
/* 457 */       this.localMap.clear();
/* 458 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 459 */         LOGGER.finer(this.loggerProlog + "local cache invalidated");
/*     */       }
/*     */     } finally {
/* 462 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ReplicationManager<K, V> getReplicationManager() {
/* 467 */     return this.replicationManager;
/*     */   }
/*     */   
/*     */   public void close() {
/* 471 */     this.replicationManager.close();
/* 472 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 473 */       LOGGER.finer(this.loggerProlog + "HA map closed");
/*     */     }
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 478 */     this.replicationManager.destroy();
/* 479 */     if (LOGGER.isLoggable(Level.FINER))
/* 480 */       LOGGER.finer(this.loggerProlog + "HA map destroyed"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\ha\HighlyAvailableMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */