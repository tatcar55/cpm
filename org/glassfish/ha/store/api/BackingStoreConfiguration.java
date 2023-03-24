/*     */ package org.glassfish.ha.store.api;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
/*     */ import org.glassfish.ha.store.util.KeyTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BackingStoreConfiguration<K extends Serializable, V extends Serializable>
/*     */ {
/*     */   public static final String BASE_DIRECTORY_NAME = "base.directory.name";
/*     */   public static final String NO_OP_PERSISTENCE_TYPE = "noop";
/*     */   public static final String START_GMS = "start.gms";
/*     */   private String clusterName;
/*     */   private String instanceName;
/*     */   private String storeName;
/*     */   private String shortUniqueName;
/*     */   private String storeType;
/*  73 */   private long maxIdleTimeInSeconds = -1L;
/*     */   
/*     */   private String relaxVersionCheck;
/*     */   
/*     */   private long maxLoadWaitTimeInSeconds;
/*     */   
/*     */   private File baseDirectory;
/*     */   
/*     */   private Class<K> keyClazz;
/*     */   
/*     */   private Class<V> valueClazz;
/*     */   
/*     */   private boolean synchronousSave;
/*     */   
/*     */   private long typicalPayloadSizeInKiloBytes;
/*     */   
/*     */   private Logger logger;
/*     */   
/*  91 */   private Map<String, Object> vendorSpecificSettings = new HashMap<String, Object>();
/*     */   
/*     */   private ClassLoader classLoader;
/*     */   
/*     */   private boolean startGroupService;
/*     */   
/*     */   private KeyTransformer<K> keyTransformer;
/*     */   
/*     */   public String getClusterName() {
/* 100 */     return this.clusterName;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setClusterName(String clusterName) {
/* 104 */     this.clusterName = clusterName;
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public String getInstanceName() {
/* 109 */     return this.instanceName;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setInstanceName(String instanceName) {
/* 113 */     this.instanceName = instanceName;
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public String getStoreName() {
/* 118 */     return this.storeName;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setStoreName(String storeName) {
/* 122 */     this.storeName = storeName;
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   public String getShortUniqueName() {
/* 127 */     return this.shortUniqueName;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setShortUniqueName(String shortUniqueName) {
/* 131 */     this.shortUniqueName = shortUniqueName;
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   public String getStoreType() {
/* 136 */     return this.storeType;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setStoreType(String storeType) {
/* 140 */     this.storeType = storeType;
/* 141 */     return this;
/*     */   }
/*     */   
/*     */   public long getMaxIdleTimeInSeconds() {
/* 145 */     return this.maxIdleTimeInSeconds;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setMaxIdleTimeInSeconds(long maxIdleTimeInSeconds) {
/* 149 */     this.maxIdleTimeInSeconds = maxIdleTimeInSeconds;
/* 150 */     return this;
/*     */   }
/*     */   
/*     */   public String getRelaxVersionCheck() {
/* 154 */     return this.relaxVersionCheck;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setRelaxVersionCheck(String relaxVersionCheck) {
/* 158 */     this.relaxVersionCheck = relaxVersionCheck;
/* 159 */     return this;
/*     */   }
/*     */   
/*     */   public long getMaxLoadWaitTimeInSeconds() {
/* 163 */     return this.maxLoadWaitTimeInSeconds;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setMaxLoadWaitTimeInSeconds(long maxLoadWaitTimeInSeconds) {
/* 167 */     this.maxLoadWaitTimeInSeconds = maxLoadWaitTimeInSeconds;
/* 168 */     return this;
/*     */   }
/*     */   
/*     */   public File getBaseDirectory() {
/* 172 */     return this.baseDirectory;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setBaseDirectory(File baseDirectory) {
/* 176 */     this.baseDirectory = baseDirectory;
/* 177 */     return this;
/*     */   }
/*     */   
/*     */   public Class<K> getKeyClazz() {
/* 181 */     return this.keyClazz;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setKeyClazz(Class<K> kClazz) {
/* 185 */     this.keyClazz = kClazz;
/* 186 */     return this;
/*     */   }
/*     */   
/*     */   public Class<V> getValueClazz() {
/* 190 */     return this.valueClazz;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setValueClazz(Class<V> vClazz) {
/* 194 */     this.valueClazz = vClazz;
/* 195 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isSynchronousSave() {
/* 199 */     return this.synchronousSave;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setSynchronousSave(boolean synchronousSave) {
/* 203 */     this.synchronousSave = synchronousSave;
/* 204 */     return this;
/*     */   }
/*     */   
/*     */   public long getTypicalPayloadSizeInKiloBytes() {
/* 208 */     return this.typicalPayloadSizeInKiloBytes;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setTypicalPayloadSizeInKiloBytes(long typicalPayloadSizeInKiloBytes) {
/* 212 */     this.typicalPayloadSizeInKiloBytes = typicalPayloadSizeInKiloBytes;
/* 213 */     return this;
/*     */   }
/*     */   
/*     */   public Logger getLogger() {
/* 217 */     return this.logger;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setLogger(Logger logger) {
/* 221 */     this.logger = logger;
/* 222 */     return this;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getVendorSpecificSettings() {
/* 226 */     return this.vendorSpecificSettings;
/*     */   }
/*     */   
/*     */   public ClassLoader getClassLoader() {
/* 230 */     return this.classLoader;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setClassLoader(ClassLoader classLoader) {
/* 234 */     this.classLoader = classLoader;
/* 235 */     return this;
/*     */   }
/*     */   
/*     */   public boolean getStartGroupService() {
/* 239 */     return this.startGroupService;
/*     */   }
/*     */   
/*     */   public BackingStoreConfiguration<K, V> setStartGroupService(boolean startGroupService) {
/* 243 */     this.startGroupService = startGroupService;
/* 244 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 249 */     return "BackingStoreConfiguration{clusterName='" + this.clusterName + '\'' + ", instanceName='" + this.instanceName + '\'' + ", storeName='" + this.storeName + '\'' + ", shortUniqueName='" + this.shortUniqueName + '\'' + ", storeType='" + this.storeType + '\'' + ", maxIdleTimeInSeconds=" + this.maxIdleTimeInSeconds + ", relaxVersionCheck='" + this.relaxVersionCheck + '\'' + ", maxLoadWaitTimeInSeconds=" + this.maxLoadWaitTimeInSeconds + ", baseDirectoryName='" + this.baseDirectory + '\'' + ", keyClazz=" + this.keyClazz + ", valueClazz=" + this.valueClazz + ", synchronousSave=" + this.synchronousSave + ", typicalPayloadSizeInKiloBytes=" + this.typicalPayloadSizeInKiloBytes + ", vendorSpecificSettings=" + this.vendorSpecificSettings + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\api\BackingStoreConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */