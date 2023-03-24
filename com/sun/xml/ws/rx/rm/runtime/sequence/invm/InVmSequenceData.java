/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence.invm;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateMessageRegistrationException;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceData;
/*     */ import com.sun.xml.ws.rx.util.TimeSynchronizer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import org.glassfish.ha.store.annotations.StoreEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @StoreEntry
/*     */ final class InVmSequenceData
/*     */   implements SequenceData
/*     */ {
/*  60 */   private final ReadWriteLock dataLock = new ReentrantReadWriteLock();
/*     */   
/*     */   private final Map<String, ApplicationMessage> messageStore;
/*     */   private final SequenceDataPojo data;
/*     */   private final TimeSynchronizer timeSynchronizer;
/*     */   
/*     */   public static InVmSequenceData newInstace(@NotNull SequenceDataPojo data, @NotNull TimeSynchronizer timeSynchronizer, Map<String, ApplicationMessage> messageStore) {
/*  67 */     return new InVmSequenceData(data, timeSynchronizer, messageStore);
/*     */   }
/*     */ 
/*     */   
/*     */   public static InVmSequenceData loadReplica(@NotNull SequenceDataPojo data, @NotNull TimeSynchronizer timeSynchronizer, Map<String, ApplicationMessage> messageStore) {
/*  72 */     InVmSequenceData replica = new InVmSequenceData(data, timeSynchronizer, messageStore);
/*  73 */     replica.initLocalCache();
/*     */     
/*  75 */     return replica;
/*     */   }
/*     */   
/*     */   private InVmSequenceData(@NotNull SequenceDataPojo data, @NotNull TimeSynchronizer timeSynchronizer, @NotNull Map<String, ApplicationMessage> messageStore) {
/*  79 */     assert timeSynchronizer != null;
/*  80 */     assert data != null;
/*     */     
/*  82 */     this.timeSynchronizer = timeSynchronizer;
/*  83 */     this.data = data;
/*     */     
/*  85 */     this.messageStore = messageStore;
/*     */   }
/*     */   
/*     */   private void lockRead() {
/*  89 */     this.dataLock.readLock().lock();
/*     */   }
/*     */   
/*     */   private void unlockRead() {
/*  93 */     this.dataLock.readLock().unlock();
/*     */   }
/*     */   
/*     */   private void lockWrite() {
/*  97 */     this.dataLock.writeLock().lock();
/*     */   }
/*     */   
/*     */   private void unlockWrite() {
/* 101 */     this.dataLock.writeLock().unlock();
/*     */   }
/*     */   
/*     */   public String getSequenceId() {
/* 105 */     return this.data.getSequenceId();
/*     */   }
/*     */   
/*     */   public String getBoundSecurityTokenReferenceId() {
/* 109 */     return this.data.getBoundSecurityTokenReferenceId();
/*     */   }
/*     */   
/*     */   public long getLastMessageNumber() {
/*     */     try {
/* 114 */       lockRead();
/* 115 */       return this.data.getLastMessageNumber();
/*     */     } finally {
/* 117 */       unlockRead();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Sequence.State getState() {
/* 122 */     return this.data.getState();
/*     */   }
/*     */   
/*     */   public void setState(Sequence.State newState) {
/* 126 */     updateLastActivityTime();
/*     */     
/* 128 */     this.data.setState(newState);
/* 129 */     this.data.replicate();
/*     */   }
/*     */   
/*     */   public boolean getAckRequestedFlag() {
/* 133 */     return this.data.getAckRequestedFlag();
/*     */   }
/*     */   
/*     */   public void setAckRequestedFlag(boolean newValue) {
/* 137 */     updateLastActivityTime();
/*     */     
/* 139 */     this.data.setAckRequestedFlag(newValue);
/* 140 */     this.data.replicate();
/*     */   }
/*     */   
/*     */   public long getLastAcknowledgementRequestTime() {
/* 144 */     return this.data.getLastAcknowledgementRequestTime();
/*     */   }
/*     */   
/*     */   public void setLastAcknowledgementRequestTime(long newTime) {
/* 148 */     updateLastActivityTime();
/*     */     
/* 150 */     this.data.setLastAcknowledgementRequestTime(newTime);
/* 151 */     this.data.replicate();
/*     */   }
/*     */   
/*     */   public long getLastActivityTime() {
/* 155 */     return this.data.getLastActivityTime();
/*     */   }
/*     */   
/*     */   private void updateLastActivityTime() {
/* 159 */     this.data.setLastActivityTime(this.timeSynchronizer.currentTimeInMillis());
/*     */   }
/*     */   
/*     */   public long getExpirationTime() {
/* 163 */     return this.data.getExpirationTime();
/*     */   }
/*     */   
/*     */   public final void attachMessageToUnackedMessageNumber(ApplicationMessage message) {
/* 167 */     updateLastActivityTime();
/*     */     
/*     */     try {
/* 170 */       lockWrite();
/* 171 */       Long msgNumberKey = getUnackedMessageIdentifierKey(message.getMessageNumber());
/*     */       
/* 173 */       this.data.getUnackedNumberToCorrelationIdMap().put(msgNumberKey, message.getCorrelationId());
/* 174 */       this.data.replicate();
/*     */       
/* 176 */       this.messageStore.put(decorateForSequence(message.getCorrelationId()), message);
/*     */     } finally {
/* 178 */       unlockWrite();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long incrementAndGetLastMessageNumber(boolean received) {
/* 186 */     updateLastActivityTime();
/*     */     
/*     */     try {
/* 189 */       this.dataLock.writeLock().lock();
/*     */       
/* 191 */       this.data.setLastMessageNumber(this.data.getLastMessageNumber() + 1L);
/* 192 */       addUnackedMessageNumber(this.data.getLastMessageNumber(), received);
/* 193 */       this.data.replicate();
/*     */       
/* 195 */       return this.data.getLastMessageNumber();
/*     */     } finally {
/* 197 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerReceivedUnackedMessageNumber(long messageNumber) throws DuplicateMessageRegistrationException {
/* 205 */     updateLastActivityTime();
/*     */     
/*     */     try {
/* 208 */       lockWrite();
/*     */       
/* 210 */       if (messageNumber > this.data.getLastMessageNumber()) {
/* 211 */         while (messageNumber > this.data.getLastMessageNumber() + 1L)
/*     */         {
/*     */           
/* 214 */           incrementAndGetLastMessageNumber(false);
/*     */         }
/*     */         
/* 217 */         incrementAndGetLastMessageNumber(true);
/*     */       } else {
/* 219 */         if (this.data.getReceivedUnackedMessageNumbers().contains(Long.valueOf(messageNumber)) || !this.data.getAllUnackedMessageNumbers().contains(Long.valueOf(messageNumber)))
/*     */         {
/* 221 */           throw new DuplicateMessageRegistrationException(this.data.getSequenceId(), messageNumber);
/*     */         }
/*     */         
/* 224 */         addUnackedMessageNumber(messageNumber, true);
/*     */       } 
/*     */       
/* 227 */       this.data.replicate();
/*     */     } finally {
/* 229 */       unlockWrite();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addUnackedMessageNumber(long messageNumber, boolean received) {
/* 237 */     Long newUnackedInstance = Long.valueOf(messageNumber);
/*     */     
/* 239 */     this.data.getAllUnackedMessageNumbers().add(newUnackedInstance);
/* 240 */     if (received) {
/* 241 */       this.data.getReceivedUnackedMessageNumbers().add(newUnackedInstance);
/*     */     }
/*     */   }
/*     */   
/*     */   void markUnackedAsFailedOver() {
/* 246 */     lockWrite();
/*     */     try {
/* 248 */       for (Long unackedNumber : this.data.getReceivedUnackedMessageNumbers()) {
/* 249 */         this.data.getFailedOverUnackedMessageNumbers().add(unackedNumber);
/*     */       }
/*     */     } finally {
/* 252 */       unlockWrite();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFailedOver(long messageNumber) {
/* 258 */     Long value = Long.valueOf(messageNumber);
/* 259 */     lockRead();
/*     */     try {
/* 261 */       return this.data.getFailedOverUnackedMessageNumbers().contains(value);
/*     */     } finally {
/* 263 */       unlockRead();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void markAsAcknowledged(long messageNumber) {
/* 268 */     updateLastActivityTime();
/*     */     
/*     */     try {
/* 271 */       lockWrite();
/* 272 */       this.data.getFailedOverUnackedMessageNumbers().remove(Long.valueOf(messageNumber));
/* 273 */       this.data.getReceivedUnackedMessageNumbers().remove(Long.valueOf(messageNumber));
/* 274 */       this.data.getAllUnackedMessageNumbers().remove(Long.valueOf(messageNumber));
/* 275 */       String correlationId = this.data.getUnackedNumberToCorrelationIdMap().remove(Long.valueOf(messageNumber));
/* 276 */       this.data.replicate();
/*     */       
/* 278 */       this.messageStore.remove(decorateForSequence(correlationId));
/*     */     } finally {
/* 280 */       unlockWrite();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ApplicationMessage retrieveMessage(String correlationId) {
/* 285 */     updateLastActivityTime();
/*     */     
/*     */     try {
/* 288 */       lockRead();
/* 289 */       return this.messageStore.get(decorateForSequence(correlationId));
/*     */     } finally {
/* 291 */       unlockRead();
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Long> getUnackedMessageNumbers() {
/*     */     try {
/* 297 */       lockRead();
/* 298 */       return new ArrayList<Long>(this.data.getAllUnackedMessageNumbers());
/*     */     } finally {
/* 300 */       unlockRead();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Long> getLastMessageNumberWithUnackedMessageNumbers() {
/*     */     try {
/* 307 */       lockRead();
/*     */       
/* 309 */       LinkedList<Long> result = new LinkedList<Long>(this.data.getAllUnackedMessageNumbers());
/* 310 */       result.addFirst(Long.valueOf(this.data.getLastMessageNumber()));
/*     */       
/* 312 */       return result;
/*     */     } finally {
/* 314 */       unlockRead();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Long getUnackedMessageIdentifierKey(long messageNumber) {
/*     */     try {
/* 320 */       lockRead();
/* 321 */       Long msgNumberKey = null;
/* 322 */       Iterator<Long> iterator = this.data.getReceivedUnackedMessageNumbers().iterator();
/* 323 */       while (iterator.hasNext()) {
/* 324 */         msgNumberKey = iterator.next();
/* 325 */         if (msgNumberKey.longValue() == messageNumber) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 330 */       return msgNumberKey;
/*     */     } finally {
/* 332 */       unlockRead();
/*     */     } 
/*     */   }
/*     */   
/*     */   SequenceDataPojo getSequenceStatePojo() {
/* 337 */     return this.data;
/*     */   }
/*     */   
/*     */   private void initLocalCache() {
/* 341 */     for (Long unackedMessageNumber : this.data.getReceivedUnackedMessageNumbers()) {
/* 342 */       String correlationId = this.data.getUnackedNumberToCorrelationIdMap().get(unackedMessageNumber);
/* 343 */       this.messageStore.get(decorateForSequence(correlationId));
/*     */     } 
/*     */   }
/*     */   
/*     */   private String decorateForSequence(String correlationId) {
/* 348 */     return this.data.getSequenceId() + "_" + correlationId;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\invm\InVmSequenceData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */