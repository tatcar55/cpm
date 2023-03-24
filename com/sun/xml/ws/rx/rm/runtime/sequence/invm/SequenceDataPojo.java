/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence.invm;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.ha.HaInfo;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.commons.ha.StickyKey;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ class SequenceDataPojo
/*     */   implements Serializable
/*     */ {
/*  65 */   private static final Logger LOGGER = Logger.getLogger(SequenceDataPojo.class);
/*     */   
/*     */   static final long serialVersionUID = -5024744406713321676L;
/*     */   
/*     */   private transient BackingStore<StickyKey, SequenceDataPojo> backingStore;
/*     */   
/*     */   private String sequenceId;
/*     */   
/*     */   private String boundSecurityTokenReferenceId;
/*     */   
/*     */   private long expirationTime;
/*     */   
/*     */   private volatile Sequence.State state;
/*     */   
/*     */   private volatile boolean ackRequestedFlag;
/*     */   
/*     */   private volatile long lastMessageNumber;
/*     */   
/*     */   private volatile long lastActivityTime;
/*     */   
/*     */   private volatile long lastAcknowledgementRequestTime;
/*     */   
/*     */   private Set<Long> allUnackedMessageNumbers;
/*     */   
/*     */   private Set<Long> receivedUnackedMessageNumbers;
/*     */   
/*     */   private Set<Long> failedOverUnackedMessageNumbers;
/*     */   private Map<Long, String> unackedNumberToCorrelationIdMap;
/*     */   private boolean inbound;
/*     */   
/*     */   protected SequenceDataPojo() {}
/*     */   
/*     */   public SequenceDataPojo(String sequenceId, String boundSecurityTokenReferenceId, long expirationTime, boolean isInbound, BackingStore<StickyKey, SequenceDataPojo> bs) {
/*  98 */     this.sequenceId = sequenceId;
/*  99 */     this.boundSecurityTokenReferenceId = boundSecurityTokenReferenceId;
/* 100 */     this.expirationTime = expirationTime;
/* 101 */     this.inbound = isInbound;
/*     */     
/* 103 */     this.allUnackedMessageNumbers = new TreeSet<Long>();
/* 104 */     this.receivedUnackedMessageNumbers = new HashSet<Long>();
/* 105 */     this.failedOverUnackedMessageNumbers = new HashSet<Long>();
/*     */     
/* 107 */     this.unackedNumberToCorrelationIdMap = new HashMap<Long, String>();
/*     */     
/* 109 */     this.backingStore = bs;
/*     */   }
/*     */   
/*     */   public String getBoundSecurityTokenReferenceId() {
/* 113 */     return this.boundSecurityTokenReferenceId;
/*     */   }
/*     */   
/*     */   public long getExpirationTime() {
/* 117 */     return this.expirationTime;
/*     */   }
/*     */   
/*     */   public String getSequenceId() {
/* 121 */     return this.sequenceId;
/*     */   }
/*     */   
/*     */   public boolean getAckRequestedFlag() {
/* 125 */     return this.ackRequestedFlag;
/*     */   }
/*     */   
/*     */   public void setAckRequestedFlag(boolean ackRequestedFlag) {
/* 129 */     this.ackRequestedFlag = ackRequestedFlag;
/* 130 */     dirty(Parameter.ackRequestedFlag);
/*     */   }
/*     */   
/*     */   public long getLastAcknowledgementRequestTime() {
/* 134 */     return this.lastAcknowledgementRequestTime;
/*     */   }
/*     */   
/*     */   public void setLastAcknowledgementRequestTime(long lastAcknowledgementRequestTime) {
/* 138 */     this.lastAcknowledgementRequestTime = lastAcknowledgementRequestTime;
/* 139 */     dirty(Parameter.lastAcknowledgementRequestTime);
/*     */   }
/*     */   
/*     */   public long getLastActivityTime() {
/* 143 */     return this.lastActivityTime;
/*     */   }
/*     */   
/*     */   public void setLastActivityTime(long lastActivityTime) {
/* 147 */     this.lastActivityTime = lastActivityTime;
/* 148 */     dirty(Parameter.lastActivityTime);
/*     */   }
/*     */   
/*     */   public long getLastMessageNumber() {
/* 152 */     return this.lastMessageNumber;
/*     */   }
/*     */   
/*     */   public void setLastMessageNumber(long lastMessageNumber) {
/* 156 */     this.lastMessageNumber = lastMessageNumber;
/* 157 */     dirty(Parameter.lastMessageNumber);
/*     */   }
/*     */   
/*     */   public Sequence.State getState() {
/* 161 */     return this.state;
/*     */   }
/*     */   
/*     */   public void setState(Sequence.State state) {
/* 165 */     this.state = state;
/* 166 */     dirty(Parameter.state);
/*     */   }
/*     */   
/*     */   public Set<Long> getAllUnackedMessageNumbers() {
/* 170 */     return this.allUnackedMessageNumbers;
/*     */   }
/*     */   
/*     */   public Set<Long> getReceivedUnackedMessageNumbers() {
/* 174 */     return this.receivedUnackedMessageNumbers;
/*     */   }
/*     */   
/*     */   public Set<Long> getFailedOverUnackedMessageNumbers() {
/* 178 */     return this.failedOverUnackedMessageNumbers;
/*     */   }
/*     */   
/*     */   public Map<Long, String> getUnackedNumberToCorrelationIdMap() {
/* 182 */     return this.unackedNumberToCorrelationIdMap;
/*     */   }
/*     */   
/*     */   public boolean isInbound() {
/* 186 */     return this.inbound;
/*     */   }
/*     */   
/*     */   public void setBackingStore(BackingStore<StickyKey, SequenceDataPojo> backingStore) {
/* 190 */     this.backingStore = backingStore;
/*     */   }
/*     */   
/*     */   public void replicate() {
/* 194 */     if (this.backingStore != null && this.dirty) {
/* 195 */       HaInfo haInfo = HaContext.currentHaInfo();
/* 196 */       if (haInfo != null) {
/* 197 */         if (LOGGER.isLoggable(Level.FINER)) {
/* 198 */           LOGGER.finer("Sequence " + this.sequenceId + "]: Existing HaInfo found, using it for sequence state data replication: " + HaContext.asString(haInfo));
/*     */         }
/*     */         
/* 201 */         HaContext.udpateReplicaInstance(HighAvailabilityProvider.saveTo(this.backingStore, (Serializable)new StickyKey(this.sequenceId, haInfo.getKey()), this, false));
/*     */       } else {
/* 203 */         StickyKey stickyKey = new StickyKey(this.sequenceId);
/* 204 */         String replicaId = HighAvailabilityProvider.saveTo(this.backingStore, (Serializable)stickyKey, this, false);
/*     */         
/* 206 */         haInfo = new HaInfo(stickyKey.getHashKey(), replicaId, false);
/* 207 */         HaContext.updateHaInfo(haInfo);
/* 208 */         if (LOGGER.isLoggable(Level.FINER)) {
/* 209 */           LOGGER.finer("Sequence " + this.sequenceId + "]: No HaInfo found, created new after sequence state data replication: " + HaContext.asString(haInfo));
/*     */         }
/*     */       } 
/*     */     } 
/* 213 */     resetDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 218 */     if (obj == null) {
/* 219 */       return false;
/*     */     }
/* 221 */     if (getClass() != obj.getClass()) {
/* 222 */       return false;
/*     */     }
/* 224 */     SequenceDataPojo other = (SequenceDataPojo)obj;
/* 225 */     if ((this.sequenceId == null) ? (other.sequenceId != null) : !this.sequenceId.equals(other.sequenceId)) {
/* 226 */       return false;
/*     */     }
/* 228 */     if ((this.boundSecurityTokenReferenceId == null) ? (other.boundSecurityTokenReferenceId != null) : !this.boundSecurityTokenReferenceId.equals(other.boundSecurityTokenReferenceId)) {
/* 229 */       return false;
/*     */     }
/* 231 */     if (this.expirationTime != other.expirationTime) {
/* 232 */       return false;
/*     */     }
/* 234 */     if (this.state != other.state) {
/* 235 */       return false;
/*     */     }
/* 237 */     if (this.ackRequestedFlag != other.ackRequestedFlag) {
/* 238 */       return false;
/*     */     }
/* 240 */     if (this.lastMessageNumber != other.lastMessageNumber) {
/* 241 */       return false;
/*     */     }
/* 243 */     if (this.lastActivityTime != other.lastActivityTime) {
/* 244 */       return false;
/*     */     }
/* 246 */     if (this.lastAcknowledgementRequestTime != other.lastAcknowledgementRequestTime) {
/* 247 */       return false;
/*     */     }
/* 249 */     if (this.allUnackedMessageNumbers != other.allUnackedMessageNumbers && (this.allUnackedMessageNumbers == null || !this.allUnackedMessageNumbers.equals(other.allUnackedMessageNumbers))) {
/* 250 */       return false;
/*     */     }
/* 252 */     if (this.receivedUnackedMessageNumbers != other.receivedUnackedMessageNumbers && (this.receivedUnackedMessageNumbers == null || !this.receivedUnackedMessageNumbers.equals(other.receivedUnackedMessageNumbers))) {
/* 253 */       return false;
/*     */     }
/* 255 */     if (this.unackedNumberToCorrelationIdMap != other.unackedNumberToCorrelationIdMap && (this.unackedNumberToCorrelationIdMap == null || !this.unackedNumberToCorrelationIdMap.equals(other.unackedNumberToCorrelationIdMap))) {
/* 256 */       return false;
/*     */     }
/* 258 */     if (this.inbound != other.inbound) {
/* 259 */       return false;
/*     */     }
/* 261 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 266 */     int hash = 7;
/* 267 */     hash = 61 * hash + ((this.sequenceId != null) ? this.sequenceId.hashCode() : 0);
/* 268 */     hash = 61 * hash + ((this.boundSecurityTokenReferenceId != null) ? this.boundSecurityTokenReferenceId.hashCode() : 0);
/* 269 */     hash = 61 * hash + (int)(this.expirationTime ^ this.expirationTime >>> 32L);
/* 270 */     hash = 61 * hash + ((this.state != null) ? this.state.hashCode() : 0);
/* 271 */     hash = 61 * hash + (this.ackRequestedFlag ? 1 : 0);
/* 272 */     hash = 61 * hash + (int)(this.lastMessageNumber ^ this.lastMessageNumber >>> 32L);
/* 273 */     hash = 61 * hash + (int)(this.lastActivityTime ^ this.lastActivityTime >>> 32L);
/* 274 */     hash = 61 * hash + (int)(this.lastAcknowledgementRequestTime ^ this.lastAcknowledgementRequestTime >>> 32L);
/* 275 */     hash = 61 * hash + ((this.allUnackedMessageNumbers != null) ? this.allUnackedMessageNumbers.hashCode() : 0);
/* 276 */     hash = 61 * hash + ((this.receivedUnackedMessageNumbers != null) ? this.receivedUnackedMessageNumbers.hashCode() : 0);
/* 277 */     hash = 61 * hash + ((this.failedOverUnackedMessageNumbers != null) ? this.failedOverUnackedMessageNumbers.hashCode() : 0);
/* 278 */     hash = 61 * hash + ((this.unackedNumberToCorrelationIdMap != null) ? this.unackedNumberToCorrelationIdMap.hashCode() : 0);
/* 279 */     hash = 61 * hash + (this.inbound ? 1 : 0);
/* 280 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 285 */     return "SequenceDataPojo{\n\tbackingStore=" + this.backingStore + ",\n\tsequenceId=" + this.sequenceId + ",\n\tboundSecurityTokenReferenceId=" + this.boundSecurityTokenReferenceId + ",\n\texpirationTime=" + this.expirationTime + ",\n\tstate=" + this.state + ",\n\tackRequestedFlag=" + this.ackRequestedFlag + ",\n\tlastMessageNumber=" + this.lastMessageNumber + ",\n\tlastActivityTime=" + this.lastActivityTime + ",\n\tlastAcknowledgementRequestTime=" + this.lastAcknowledgementRequestTime + ",\n\tallUnackedMessageNumbers=" + this.allUnackedMessageNumbers + ",\n\treceivedUnackedMessageNumbers=" + this.receivedUnackedMessageNumbers + ",\n\tfailedOverUnackedMessageNumbers=" + this.failedOverUnackedMessageNumbers + ",\n\tunackedNumberToCorrelationIdMap=" + this.unackedNumberToCorrelationIdMap + ",\n\tinbound=" + this.inbound + ",\n\tdirty=" + this.dirty + "\n}";
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
/*     */   private enum Parameter
/*     */   {
/* 306 */     sequenceId("sequenceId", 0),
/* 307 */     boundSecurityTokenReferenceId("boundSecurityTokenReferenceId", 1),
/* 308 */     expirationTime("expirationTime", 2),
/*     */     
/* 310 */     state("state", 3),
/* 311 */     ackRequestedFlag("ackRequestedFlag", 4),
/* 312 */     lastMessageNumber("lastMessageNumber", 5),
/* 313 */     lastActivityTime("lastActivityTime", 6),
/* 314 */     lastAcknowledgementRequestTime("lastAcknowledgementRequestTime", 7),
/*     */     
/* 316 */     allUnackedMessageNumbers("allUnackedMessageNumbers", 8),
/* 317 */     receivedUnackedMessageNumbers("receivedUnackedMessageNumbers", 9),
/* 318 */     failedOverUnackedMessageNumbers("failedOverUnackedMessageNumbers", 10),
/*     */     
/* 320 */     unackedNumberToCorrelationIdMap("unackedNumberToCorrelationIdMap", 11),
/* 321 */     inbound("inbound", 12);
/*     */     
/*     */     public final String name;
/*     */     public final int index;
/*     */     
/*     */     Parameter(String name, int index) {
/* 327 */       this.name = name;
/* 328 */       this.index = index;
/*     */     }
/*     */   }
/*     */   
/*     */   private volatile boolean dirty = false;
/*     */   
/*     */   private void dirty(Parameter p) {
/* 335 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   public void resetDirty() {
/* 339 */     this.dirty = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\invm\SequenceDataPojo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */