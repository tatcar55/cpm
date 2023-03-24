/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence.persistent;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.commons.AbstractMOMRegistrationAware;
/*     */ import com.sun.xml.ws.commons.DelayedTaskManager;
/*     */ import com.sun.xml.ws.commons.MaintenanceTaskExecutor;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RmConfiguration;
/*     */ import com.sun.xml.ws.rx.rm.runtime.delivery.DeliveryQueueBuilder;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.AbstractSequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateSequenceException;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.InboundSequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.OutboundSequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceMaintenanceTask;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceManager;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.UnknownSequenceException;
/*     */ import com.sun.xml.ws.rx.util.TimeSynchronizer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PersistentSequenceManager
/*     */   extends AbstractMOMRegistrationAware
/*     */   implements SequenceManager
/*     */ {
/*  73 */   private static final Logger LOGGER = Logger.getLogger(PersistentSequenceManager.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private ConnectionManager cm;
/*     */ 
/*     */ 
/*     */   
/*  81 */   private final ReadWriteLock dataLock = new ReentrantReadWriteLock();
/*     */ 
/*     */ 
/*     */   
/*  85 */   private final Map<String, AbstractSequence> sequences = new HashMap<String, AbstractSequence>();
/*     */ 
/*     */ 
/*     */   
/*  89 */   private final Map<String, String> boundSequences = new HashMap<String, String>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final DeliveryQueueBuilder inboundQueueBuilder;
/*     */ 
/*     */ 
/*     */   
/*     */   private final DeliveryQueueBuilder outboundQueueBuilder;
/*     */ 
/*     */ 
/*     */   
/*     */   private final long sequenceInactivityTimeout;
/*     */ 
/*     */ 
/*     */   
/*     */   private final long maxConcurrentInboundSequences;
/*     */ 
/*     */ 
/*     */   
/*     */   private final AtomicLong actualConcurrentInboundSequences;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String uniqueEndpointId;
/*     */ 
/*     */   
/*     */   private volatile boolean disposed = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentSequenceManager(String uniqueEndpointId, DeliveryQueueBuilder inboundQueueBuilder, DeliveryQueueBuilder outboundQueueBuilder, RmConfiguration configuration) {
/* 121 */     this.uniqueEndpointId = uniqueEndpointId;
/* 122 */     this.inboundQueueBuilder = inboundQueueBuilder;
/* 123 */     this.outboundQueueBuilder = outboundQueueBuilder;
/*     */     
/* 125 */     this.sequenceInactivityTimeout = configuration.getRmFeature().getSequenceInactivityTimeout();
/*     */     
/* 127 */     this.actualConcurrentInboundSequences = new AtomicLong(0L);
/* 128 */     this.maxConcurrentInboundSequences = configuration.getRmFeature().getMaxConcurrentSessions();
/*     */     
/* 130 */     this.cm = ConnectionManager.getInstance(new DefaultDataSourceProvider());
/*     */     
/* 132 */     MaintenanceTaskExecutor.INSTANCE.register((DelayedTaskManager.DelayedTask)new SequenceMaintenanceTask(this, configuration.getRmFeature().getSequenceManagerMaintenancePeriod(), TimeUnit.MILLISECONDS), configuration.getRmFeature().getSequenceManagerMaintenancePeriod(), TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean persistent() {
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String uniqueEndpointId() {
/* 149 */     return this.uniqueEndpointId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Sequence> sequences() {
/*     */     try {
/* 157 */       this.dataLock.readLock().lock();
/*     */       
/* 159 */       return (Map)new HashMap<String, AbstractSequence>(this.sequences);
/*     */     } finally {
/* 161 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> boundSequences() {
/*     */     try {
/* 170 */       this.dataLock.readLock().lock();
/*     */       
/* 172 */       return new HashMap<String, String>(this.boundSequences);
/*     */     } finally {
/* 174 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long concurrentlyOpenedInboundSequencesCount() {
/* 182 */     return this.actualConcurrentInboundSequences.longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence createOutboundSequence(String sequenceId, String strId, long expirationTime) throws DuplicateSequenceException {
/* 189 */     PersistentSequenceData data = PersistentSequenceData.newInstance((TimeSynchronizer)this, this.cm, this.uniqueEndpointId, sequenceId, PersistentSequenceData.SequenceType.Outbound, strId, expirationTime, Sequence.State.CREATED, false, 0L, currentTimeInMillis(), 0L);
/* 190 */     return (Sequence)registerSequence((AbstractSequence)new OutboundSequence(data, this.outboundQueueBuilder, (TimeSynchronizer)this), data.getBoundSequenceId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence createInboundSequence(String sequenceId, String strId, long expirationTime) throws DuplicateSequenceException {
/* 197 */     long actualSessions = this.actualConcurrentInboundSequences.incrementAndGet();
/* 198 */     if (this.maxConcurrentInboundSequences >= 0L && 
/* 199 */       this.maxConcurrentInboundSequences < actualSessions) {
/* 200 */       this.actualConcurrentInboundSequences.decrementAndGet();
/* 201 */       throw new RxRuntimeException(LocalizationMessages.WSRM_1156_MAX_CONCURRENT_SESSIONS_REACHED(Long.valueOf(this.maxConcurrentInboundSequences)));
/*     */     } 
/*     */ 
/*     */     
/* 205 */     PersistentSequenceData data = PersistentSequenceData.newInstance((TimeSynchronizer)this, this.cm, this.uniqueEndpointId, sequenceId, PersistentSequenceData.SequenceType.Inbound, strId, expirationTime, Sequence.State.CREATED, false, 0L, currentTimeInMillis(), 0L);
/* 206 */     return (Sequence)registerSequence((AbstractSequence)new InboundSequence(data, this.inboundQueueBuilder, (TimeSynchronizer)this), data.getBoundSequenceId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String generateSequenceUID() {
/* 213 */     return "uuid:" + UUID.randomUUID();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence closeSequence(String sequenceId) throws UnknownSequenceException {
/* 220 */     Sequence sequence = getSequence(sequenceId);
/* 221 */     sequence.close();
/* 222 */     return sequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence getSequence(String sequenceId) throws UnknownSequenceException {
/* 229 */     checkIfExist(new String[] { sequenceId });
/*     */     
/*     */     try {
/* 232 */       this.dataLock.readLock().lock();
/* 233 */       Sequence sequence = (Sequence)this.sequences.get(sequenceId);
/*     */       
/* 235 */       if (shouldTeminate(sequence)) {
/* 236 */         this.dataLock.readLock().unlock();
/* 237 */         tryTerminateSequence(sequenceId);
/* 238 */         this.dataLock.readLock().lock();
/*     */       } 
/*     */       
/* 241 */       return sequence;
/*     */     } finally {
/* 243 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence getInboundSequence(String sequenceId) throws UnknownSequenceException {
/* 251 */     Sequence sequence = getSequence(sequenceId);
/*     */     
/* 253 */     if (!(sequence instanceof InboundSequence)) {
/* 254 */       throw new UnknownSequenceException(sequenceId);
/*     */     }
/*     */     
/* 257 */     return sequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence getOutboundSequence(String sequenceId) throws UnknownSequenceException {
/* 264 */     Sequence sequence = getSequence(sequenceId);
/*     */     
/* 266 */     if (!(sequence instanceof OutboundSequence)) {
/* 267 */       throw new UnknownSequenceException(sequenceId);
/*     */     }
/*     */     
/* 270 */     return sequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String sequenceId) {
/*     */     Sequence s;
/*     */     try {
/* 279 */       this.dataLock.readLock().lock();
/* 280 */       s = (Sequence)this.sequences.get(sequenceId);
/*     */     } finally {
/* 282 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */     
/* 285 */     if (s == null) {
/* 286 */       s = fetch(sequenceId);
/*     */     }
/*     */     
/* 289 */     return (s != null && s.getState() != Sequence.State.TERMINATING);
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
/*     */   private void checkIfExist(String... sequenceIds) throws UnknownSequenceException {
/* 303 */     for (String sequenceId : sequenceIds) {
/*     */       Sequence s;
/*     */       try {
/* 306 */         this.dataLock.readLock().lock();
/* 307 */         s = (Sequence)this.sequences.get(sequenceId);
/*     */       } finally {
/* 309 */         this.dataLock.readLock().unlock();
/*     */       } 
/*     */       
/* 312 */       if (s == null) {
/* 313 */         s = fetch(sequenceId);
/*     */       }
/*     */       
/* 316 */       if (s == null) {
/* 317 */         throw new UnknownSequenceException(sequenceId);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private Sequence fetch(String sequenceId) {
/* 323 */     this.dataLock.writeLock().lock();
/*     */     
/* 325 */     try { if (this.sequences.containsKey(sequenceId)) {
/* 326 */         return (Sequence)this.sequences.get(sequenceId);
/*     */       }
/*     */       
/* 329 */       PersistentSequenceData sequenceData = PersistentSequenceData.loadInstance((TimeSynchronizer)this, this.cm, this.uniqueEndpointId, sequenceId);
/* 330 */       if (sequenceData != null)
/* 331 */       { AbstractSequence abstractSequence; switch (sequenceData.getType())
/*     */         { case Inbound:
/* 333 */             if (sequenceData.getState() != Sequence.State.TERMINATING) {
/* 334 */               this.actualConcurrentInboundSequences.incrementAndGet();
/*     */             }
/* 336 */             abstractSequence = registerSequence((AbstractSequence)new InboundSequence(sequenceData, this.inboundQueueBuilder, (TimeSynchronizer)this), sequenceData.getBoundSequenceId());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 345 */             return (Sequence)abstractSequence;case Outbound: abstractSequence = registerSequence((AbstractSequence)new OutboundSequence(sequenceData, this.outboundQueueBuilder, (TimeSynchronizer)this), sequenceData.getBoundSequenceId()); return (Sequence)abstractSequence; }  }  return null; } finally { this.dataLock.writeLock().unlock(); }
/*     */   
/*     */   }
/*     */   
/*     */   private Sequence tryTerminateSequence(String sequenceId) {
/*     */     try {
/* 351 */       this.dataLock.writeLock().lock();
/*     */       
/* 353 */       AbstractSequence sequence = this.sequences.get(sequenceId);
/*     */       
/* 355 */       if (sequence != null && sequence.getState() != Sequence.State.TERMINATING) {
/* 356 */         if (sequence instanceof InboundSequence) {
/* 357 */           this.actualConcurrentInboundSequences.decrementAndGet();
/*     */         }
/* 359 */         sequence.preDestroy();
/*     */       } 
/*     */       
/* 362 */       return (Sequence)sequence;
/*     */     } finally {
/* 364 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence terminateSequence(String sequenceId) throws UnknownSequenceException {
/*     */     try {
/* 373 */       this.dataLock.writeLock().lock();
/*     */       
/* 375 */       checkIfExist(new String[] { sequenceId });
/*     */       
/* 377 */       return tryTerminateSequence(sequenceId);
/*     */     } finally {
/* 379 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindSequences(String referenceSequenceId, String boundSequenceId) throws UnknownSequenceException {
/*     */     try {
/* 388 */       this.dataLock.writeLock().lock();
/* 389 */       checkIfExist(new String[] { referenceSequenceId, boundSequenceId });
/*     */       
/* 391 */       PersistentSequenceData.bind(this.cm, this.uniqueEndpointId, referenceSequenceId, boundSequenceId);
/* 392 */       this.boundSequences.put(referenceSequenceId, boundSequenceId);
/*     */     } finally {
/* 394 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence getBoundSequence(String referenceSequenceId) throws UnknownSequenceException {
/* 402 */     checkIfExist(new String[] { referenceSequenceId });
/*     */     
/*     */     try {
/* 405 */       this.dataLock.readLock().lock();
/* 406 */       return (Sequence)(this.boundSequences.containsKey(referenceSequenceId) ? this.sequences.get(this.boundSequences.get(referenceSequenceId)) : null);
/*     */     } finally {
/* 408 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AbstractSequence registerSequence(AbstractSequence sequence, String boundSequenceId) {
/*     */     try {
/* 419 */       this.dataLock.writeLock().lock();
/*     */ 
/*     */ 
/*     */       
/* 423 */       this.sequences.put(sequence.getId(), sequence);
/*     */       
/* 425 */       if (boundSequenceId != null) {
/* 426 */         checkIfExist(new String[] { boundSequenceId });
/*     */         
/* 428 */         this.boundSequences.put(sequence.getId(), boundSequenceId);
/*     */       } 
/*     */       
/* 431 */       return sequence;
/*     */     } finally {
/* 433 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentTimeInMillis() {
/* 442 */     return System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public boolean onMaintenance() {
/* 446 */     LOGGER.entering();
/* 447 */     boolean continueMaintenance = !this.disposed;
/*     */     
/*     */     try {
/* 450 */       this.dataLock.writeLock().lock();
/*     */       
/* 452 */       if (continueMaintenance) {
/*     */         
/* 454 */         Iterator<String> sequenceKeyIterator = this.sequences.keySet().iterator();
/* 455 */         while (sequenceKeyIterator.hasNext()) {
/* 456 */           String key = sequenceKeyIterator.next();
/*     */           
/* 458 */           AbstractSequence sequence = this.sequences.get(key);
/* 459 */           if (shouldRemove((Sequence)sequence)) {
/* 460 */             LOGGER.config(LocalizationMessages.WSRM_1152_REMOVING_SEQUENCE(sequence.getId()));
/* 461 */             sequenceKeyIterator.remove();
/* 462 */             PersistentSequenceData.remove(this.cm, this.uniqueEndpointId, sequence.getId());
/* 463 */             if (this.boundSequences.containsKey(sequence.getId()))
/* 464 */               this.boundSequences.remove(sequence.getId());  continue;
/*     */           } 
/* 466 */           if (shouldTeminate((Sequence)sequence)) {
/* 467 */             LOGGER.config(LocalizationMessages.WSRM_1153_TERMINATING_SEQUENCE(sequence.getId()));
/* 468 */             tryTerminateSequence(sequence.getId());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 473 */       return continueMaintenance;
/*     */     } finally {
/* 475 */       this.dataLock.writeLock().unlock();
/* 476 */       LOGGER.exiting(Boolean.valueOf(continueMaintenance));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shouldTeminate(Sequence sequence) {
/* 481 */     return (sequence.getState() != Sequence.State.TERMINATING && (sequence.isExpired() || sequence.getLastActivityTime() + this.sequenceInactivityTimeout < currentTimeInMillis()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldRemove(Sequence sequence) {
/* 488 */     return (sequence.getState() == Sequence.State.TERMINATING);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidateCache() {}
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 496 */     this.disposed = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\persistent\PersistentSequenceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */