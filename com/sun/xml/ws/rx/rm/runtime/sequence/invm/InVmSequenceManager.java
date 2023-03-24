/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence.invm;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.ha.HaInfo;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.commons.AbstractMOMRegistrationAware;
/*     */ import com.sun.xml.ws.commons.DelayedTaskManager;
/*     */ import com.sun.xml.ws.commons.MaintenanceTaskExecutor;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.commons.ha.StickyKey;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.ha.HighlyAvailableMap;
/*     */ import com.sun.xml.ws.rx.ha.ReplicationManager;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RmConfiguration;
/*     */ import com.sun.xml.ws.rx.rm.runtime.delivery.DeliveryQueueBuilder;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.AbstractSequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateSequenceException;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.InboundSequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.OutboundSequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceData;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceMaintenanceTask;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceManager;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.UnknownSequenceException;
/*     */ import com.sun.xml.ws.rx.util.TimeSynchronizer;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.logging.Level;
/*     */ import org.glassfish.ha.store.api.BackingStore;
/*     */ import org.glassfish.ha.store.api.BackingStoreConfiguration;
/*     */ import org.glassfish.ha.store.api.BackingStoreException;
/*     */ import org.glassfish.ha.store.api.BackingStoreFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InVmSequenceManager
/*     */   extends AbstractMOMRegistrationAware
/*     */   implements SequenceManager, ReplicationManager<String, AbstractSequence>
/*     */ {
/*     */   private final ReadWriteLock dataLock;
/*     */   private final HighlyAvailableMap<String, AbstractSequence> sequences;
/*     */   private final BackingStore<StickyKey, SequenceDataPojo> sequenceDataBs;
/*     */   private final HighlyAvailableMap<String, String> boundSequences;
/*     */   private final HighlyAvailableMap<String, ApplicationMessage> unackedMessageStore;
/*  87 */   private static final Logger LOGGER = Logger.getLogger(InVmSequenceManager.class); private final DeliveryQueueBuilder inboundQueueBuilder;
/*     */   
/*     */   public InVmSequenceManager(String uniqueEndpointId, DeliveryQueueBuilder inboundQueueBuilder, DeliveryQueueBuilder outboundQueueBuilder, RmConfiguration configuration) {
/*     */     BackingStore<StickyKey, String> boundSequencesBs;
/*  91 */     this.dataLock = new ReentrantReadWriteLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     this.disposed = new AtomicBoolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     this.loggerProlog = "[" + uniqueEndpointId + "_SEQUENCE_MANAGER]: ";
/* 143 */     this.uniqueEndpointId = uniqueEndpointId;
/* 144 */     this.inboundQueueBuilder = inboundQueueBuilder;
/* 145 */     this.outboundQueueBuilder = outboundQueueBuilder;
/*     */     
/* 147 */     this.sequenceInactivityTimeout = configuration.getRmFeature().getSequenceInactivityTimeout();
/*     */     
/* 149 */     this.actualConcurrentInboundSequences = new AtomicLong(0L);
/* 150 */     this.maxConcurrentInboundSequences = configuration.getRmFeature().getMaxConcurrentSessions();
/*     */     
/* 152 */     BackingStoreFactory bsFactory = HighAvailabilityProvider.INSTANCE.getBackingStoreFactory(HighAvailabilityProvider.StoreType.IN_MEMORY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     String boundSequencesBsName = uniqueEndpointId + "_BOUND_SEQUENCE_BS";
/* 160 */     BackingStoreConfiguration<StickyKey, String> boundSequencesBsConfig = HighAvailabilityProvider.INSTANCE.initBackingStoreConfiguration(boundSequencesBsName, StickyKey.class, String.class);
/*     */ 
/*     */ 
/*     */     
/* 164 */     boundSequencesBsConfig.setClassLoader(getClass().getClassLoader());
/*     */     
/*     */     try {
/* 167 */       boundSequencesBs = bsFactory.createBackingStore(boundSequencesBsConfig);
/* 168 */     } catch (BackingStoreException ex) {
/* 169 */       throw new RxRuntimeException(LocalizationMessages.WSRM_1142_ERROR_CREATING_HA_BACKING_STORE(boundSequencesBsName), ex);
/*     */     } 
/* 171 */     this.boundSequences = HighlyAvailableMap.createSticky(uniqueEndpointId + "_BOUND_SEQUENCE_MAP", boundSequencesBs);
/*     */ 
/*     */     
/* 174 */     this.sequenceDataBs = HighAvailabilityProvider.INSTANCE.createBackingStore(bsFactory, uniqueEndpointId + "_SEQUENCE_DATA_BS", StickyKey.class, SequenceDataPojo.class);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     this.sequences = HighlyAvailableMap.create(uniqueEndpointId + "_SEQUENCE_DATA_MAP", this);
/*     */     
/* 181 */     UnackedMessageReplicationManager unackedMsgRM = null;
/* 182 */     if (HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured()) {
/* 183 */       unackedMsgRM = new UnackedMessageReplicationManager(uniqueEndpointId);
/*     */     }
/*     */     
/* 186 */     this.unackedMessageStore = HighlyAvailableMap.create(uniqueEndpointId + "_UNACKED_MESSAGES_MAP", unackedMsgRM);
/*     */     
/* 188 */     MaintenanceTaskExecutor.INSTANCE.register((DelayedTaskManager.DelayedTask)new SequenceMaintenanceTask(this, configuration.getRmFeature().getSequenceManagerMaintenancePeriod(), TimeUnit.MILLISECONDS), configuration.getRmFeature().getSequenceManagerMaintenancePeriod(), TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   private final DeliveryQueueBuilder outboundQueueBuilder;
/*     */   
/*     */   private final String uniqueEndpointId;
/*     */   private final long sequenceInactivityTimeout;
/*     */   
/*     */   public boolean persistent() {
/* 198 */     return false;
/*     */   }
/*     */   private final long maxConcurrentInboundSequences; private final AtomicLong actualConcurrentInboundSequences;
/*     */   private final AtomicBoolean disposed;
/*     */   private final String loggerProlog;
/*     */   
/*     */   public String uniqueEndpointId() {
/* 205 */     return this.uniqueEndpointId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Sequence> sequences() {
/*     */     try {
/* 213 */       this.dataLock.readLock().lock();
/*     */       
/* 215 */       return (Map)new HashMap<String, AbstractSequence>((Map<? extends String, ? extends AbstractSequence>)this.sequences);
/*     */     } finally {
/* 217 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> boundSequences() {
/*     */     try {
/* 226 */       this.dataLock.readLock().lock();
/*     */       
/* 228 */       return this.boundSequences.getLocalMapCopy();
/*     */     } finally {
/* 230 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long concurrentlyOpenedInboundSequencesCount() {
/* 238 */     return this.actualConcurrentInboundSequences.longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence createOutboundSequence(String sequenceId, String strId, long expirationTime) throws DuplicateSequenceException {
/* 245 */     SequenceDataPojo sequenceDataPojo = new SequenceDataPojo(sequenceId, strId, expirationTime, false, this.sequenceDataBs);
/* 246 */     sequenceDataPojo.setState(Sequence.State.CREATED);
/* 247 */     sequenceDataPojo.setAckRequestedFlag(false);
/* 248 */     sequenceDataPojo.setLastMessageNumber(0L);
/* 249 */     sequenceDataPojo.setLastActivityTime(currentTimeInMillis());
/* 250 */     sequenceDataPojo.setLastAcknowledgementRequestTime(0L);
/*     */     
/* 252 */     SequenceData data = InVmSequenceData.newInstace(sequenceDataPojo, (TimeSynchronizer)this, (Map<String, ApplicationMessage>)this.unackedMessageStore);
/* 253 */     return registerSequence((AbstractSequence)new OutboundSequence(data, this.outboundQueueBuilder, (TimeSynchronizer)this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence createInboundSequence(String sequenceId, String strId, long expirationTime) throws DuplicateSequenceException {
/* 260 */     long actualSessions = this.actualConcurrentInboundSequences.incrementAndGet();
/* 261 */     if (this.maxConcurrentInboundSequences >= 0L && 
/* 262 */       this.maxConcurrentInboundSequences < actualSessions) {
/* 263 */       this.actualConcurrentInboundSequences.decrementAndGet();
/* 264 */       throw new RxRuntimeException(LocalizationMessages.WSRM_1156_MAX_CONCURRENT_SESSIONS_REACHED(Long.valueOf(this.maxConcurrentInboundSequences)));
/*     */     } 
/*     */ 
/*     */     
/* 268 */     SequenceDataPojo sequenceDataPojo = new SequenceDataPojo(sequenceId, strId, expirationTime, true, this.sequenceDataBs);
/* 269 */     sequenceDataPojo.setState(Sequence.State.CREATED);
/* 270 */     sequenceDataPojo.setAckRequestedFlag(false);
/* 271 */     sequenceDataPojo.setLastMessageNumber(0L);
/* 272 */     sequenceDataPojo.setLastActivityTime(currentTimeInMillis());
/* 273 */     sequenceDataPojo.setLastAcknowledgementRequestTime(0L);
/*     */     
/* 275 */     SequenceData data = InVmSequenceData.newInstace(sequenceDataPojo, (TimeSynchronizer)this, (Map<String, ApplicationMessage>)this.unackedMessageStore);
/* 276 */     return registerSequence((AbstractSequence)new InboundSequence(data, this.inboundQueueBuilder, (TimeSynchronizer)this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String generateSequenceUID() {
/* 283 */     return "uuid:" + UUID.randomUUID();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence closeSequence(String sequenceId) throws UnknownSequenceException {
/* 290 */     Sequence sequence = getSequence(sequenceId);
/* 291 */     sequence.close();
/* 292 */     return sequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence getSequence(String sequenceId) throws UnknownSequenceException {
/* 299 */     if (sequenceId == null) {
/* 300 */       throw new UnknownSequenceException("[null-sequence-identifier]");
/*     */     }
/*     */     
/*     */     try {
/* 304 */       this.dataLock.readLock().lock();
/* 305 */       Sequence sequence = (Sequence)this.sequences.get(sequenceId);
/* 306 */       if (sequence == null) {
/* 307 */         throw new UnknownSequenceException(sequenceId);
/*     */       }
/*     */       
/* 310 */       if (shouldTeminate(sequence)) {
/* 311 */         this.dataLock.readLock().unlock();
/* 312 */         tryTerminateSequence(sequenceId);
/* 313 */         this.dataLock.readLock().lock();
/*     */       } 
/*     */       
/* 316 */       return sequence;
/*     */     } finally {
/* 318 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence getInboundSequence(String sequenceId) throws UnknownSequenceException {
/* 326 */     Sequence sequence = getSequence(sequenceId);
/*     */     
/* 328 */     if (!(sequence instanceof InboundSequence)) {
/* 329 */       throw new UnknownSequenceException(sequenceId);
/*     */     }
/*     */     
/* 332 */     return sequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence getOutboundSequence(String sequenceId) throws UnknownSequenceException {
/* 339 */     Sequence sequence = getSequence(sequenceId);
/*     */     
/* 341 */     if (!(sequence instanceof OutboundSequence)) {
/* 342 */       throw new UnknownSequenceException(sequenceId);
/*     */     }
/*     */     
/* 345 */     return sequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String sequenceId) {
/* 352 */     if (sequenceId == null) {
/* 353 */       return false;
/*     */     }
/*     */     try {
/* 356 */       this.dataLock.readLock().lock();
/* 357 */       Sequence s = (Sequence)this.sequences.get(sequenceId);
/* 358 */       return (s != null && s.getState() != Sequence.State.TERMINATING);
/*     */     } finally {
/* 360 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Sequence tryTerminateSequence(String sequenceId) {
/* 365 */     if (sequenceId == null) {
/* 366 */       return null;
/*     */     }
/*     */     try {
/* 369 */       this.dataLock.writeLock().lock();
/* 370 */       Sequence sequence = (Sequence)this.sequences.get(sequenceId);
/* 371 */       if (sequence == null) {
/* 372 */         return null;
/*     */       }
/*     */       
/* 375 */       if (sequence.getState() != Sequence.State.TERMINATING) {
/* 376 */         if (sequence instanceof InboundSequence) {
/* 377 */           this.actualConcurrentInboundSequences.decrementAndGet();
/*     */         }
/* 379 */         sequence.preDestroy();
/*     */       } 
/*     */       
/* 382 */       return sequence;
/*     */     } finally {
/* 384 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence terminateSequence(String sequenceId) throws UnknownSequenceException {
/* 392 */     Sequence sequence = tryTerminateSequence(sequenceId);
/* 393 */     if (sequence == null) {
/* 394 */       throw new UnknownSequenceException(sequenceId);
/*     */     }
/*     */     
/* 397 */     return sequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindSequences(String referenceSequenceId, String boundSequenceId) throws UnknownSequenceException {
/*     */     try {
/* 405 */       this.dataLock.writeLock().lock();
/* 406 */       if (!this.sequences.containsKey(referenceSequenceId)) {
/* 407 */         throw new UnknownSequenceException(referenceSequenceId);
/*     */       }
/*     */       
/* 410 */       if (!this.sequences.containsKey(boundSequenceId)) {
/* 411 */         throw new UnknownSequenceException(boundSequenceId);
/*     */       }
/*     */       
/* 414 */       this.boundSequences.put(referenceSequenceId, boundSequenceId);
/*     */     } finally {
/* 416 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence getBoundSequence(String referenceSequenceId) throws UnknownSequenceException {
/*     */     try {
/* 425 */       this.dataLock.readLock().lock();
/* 426 */       if (!isValid(referenceSequenceId)) {
/* 427 */         throw new UnknownSequenceException(referenceSequenceId);
/*     */       }
/*     */       
/* 430 */       return (Sequence)(this.boundSequences.containsKey(referenceSequenceId) ? (AbstractSequence)this.sequences.get(this.boundSequences.get(referenceSequenceId)) : null);
/*     */     } finally {
/* 432 */       this.dataLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Sequence registerSequence(AbstractSequence sequence) throws DuplicateSequenceException {
/*     */     try {
/* 443 */       this.dataLock.writeLock().lock();
/* 444 */       if (this.sequences.containsKey(sequence.getId())) {
/* 445 */         throw new DuplicateSequenceException(sequence.getId());
/*     */       }
/* 447 */       this.sequences.put(sequence.getId(), sequence);
/*     */ 
/*     */       
/* 450 */       return (Sequence)sequence;
/*     */     } finally {
/* 452 */       this.dataLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentTimeInMillis() {
/* 460 */     return System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public boolean onMaintenance() {
/* 464 */     LOGGER.entering();
/*     */     
/* 466 */     boolean continueMaintenance = !this.disposed.get();
/*     */     
/*     */     try {
/* 469 */       this.dataLock.writeLock().lock();
/* 470 */       if (continueMaintenance) {
/* 471 */         Iterator<String> sequenceKeyIterator = this.sequences.keySet().iterator();
/* 472 */         while (sequenceKeyIterator.hasNext()) {
/* 473 */           String key = sequenceKeyIterator.next();
/*     */           
/* 475 */           Sequence sequence = (Sequence)this.sequences.get(key);
/* 476 */           if (shouldRemove(sequence)) {
/* 477 */             LOGGER.config(LocalizationMessages.WSRM_1152_REMOVING_SEQUENCE(sequence.getId()));
/* 478 */             sequenceKeyIterator.remove();
/* 479 */             this.sequences.getReplicationManager().remove(key);
/*     */             
/* 481 */             if (this.boundSequences.containsKey(sequence.getId()))
/* 482 */               this.boundSequences.remove(sequence.getId());  continue;
/*     */           } 
/* 484 */           if (shouldTeminate(sequence)) {
/* 485 */             LOGGER.config(LocalizationMessages.WSRM_1153_TERMINATING_SEQUENCE(sequence.getId()));
/* 486 */             tryTerminateSequence(sequence.getId());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 491 */       return continueMaintenance;
/*     */     } finally {
/*     */       
/* 494 */       this.dataLock.writeLock().unlock();
/* 495 */       LOGGER.exiting(Boolean.valueOf(continueMaintenance));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shouldTeminate(Sequence sequence) {
/* 500 */     return (sequence.getState() != Sequence.State.TERMINATING && (sequence.isExpired() || sequence.getLastActivityTime() + this.sequenceInactivityTimeout < currentTimeInMillis()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldRemove(Sequence sequence) {
/* 507 */     return (sequence.getState() == Sequence.State.TERMINATING);
/*     */   }
/*     */   
/*     */   public void invalidateCache() {
/* 511 */     this.sequences.invalidateCache();
/* 512 */     this.boundSequences.invalidateCache();
/* 513 */     this.unackedMessageStore.invalidateCache();
/* 514 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 515 */       LOGGER.finer(this.loggerProlog + "Local cache invalidated");
/*     */     }
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 520 */     if (this.disposed.compareAndSet(false, true)) {
/* 521 */       this.sequences.close();
/* 522 */       this.sequences.destroy();
/*     */       
/* 524 */       this.boundSequences.close();
/* 525 */       this.boundSequences.destroy();
/*     */       
/* 527 */       this.unackedMessageStore.close();
/* 528 */       this.unackedMessageStore.destroy();
/*     */     } 
/*     */   }
/*     */   public AbstractSequence load(String key) {
/*     */     OutboundSequence outboundSequence;
/* 533 */     SequenceDataPojo state = (SequenceDataPojo)HighAvailabilityProvider.loadFrom(this.sequenceDataBs, (Serializable)new StickyKey(key), null);
/* 534 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 535 */       LOGGER.finer(this.loggerProlog + "Sequence state data loaded from backing store for key [" + key + "]: " + ((state == null) ? null : state.toString()));
/*     */     }
/* 537 */     if (state == null) {
/* 538 */       return null;
/*     */     }
/*     */     
/* 541 */     state.setBackingStore(this.sequenceDataBs);
/* 542 */     InVmSequenceData data = InVmSequenceData.loadReplica(state, (TimeSynchronizer)this, (Map<String, ApplicationMessage>)this.unackedMessageStore);
/*     */ 
/*     */     
/* 545 */     if (state.isInbound()) {
/* 546 */       if (HaContext.failoverDetected() && !data.getUnackedMessageNumbers().isEmpty()) {
/* 547 */         if (LOGGER.isLoggable(Level.FINE)) {
/* 548 */           LOGGER.fine(this.loggerProlog + "Unacked messages detected during failover of an inbound sequence data [" + data.getSequenceId() + "]: Registering as failed-over");
/*     */         }
/* 550 */         data.markUnackedAsFailedOver();
/*     */       } 
/*     */       
/* 553 */       InboundSequence inboundSequence = new InboundSequence(data, this.inboundQueueBuilder, (TimeSynchronizer)this);
/*     */     } else {
/* 555 */       outboundSequence = new OutboundSequence(data, this.outboundQueueBuilder, (TimeSynchronizer)this);
/*     */     } 
/*     */     
/* 558 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 559 */       LOGGER.finer(this.loggerProlog + "Sequence state data for key [" + key + "] converted into sequence of class: " + outboundSequence.getClass());
/*     */     }
/* 561 */     return (AbstractSequence)outboundSequence;
/*     */   }
/*     */   
/*     */   public void save(String key, AbstractSequence sequence, boolean isNew) {
/* 565 */     SequenceData _data = sequence.getData();
/* 566 */     if (!(_data instanceof InVmSequenceData)) {
/* 567 */       throw new IllegalArgumentException("Unsupported sequence data class: " + _data.getClass().getName());
/*     */     }
/*     */     
/* 570 */     SequenceDataPojo value = ((InVmSequenceData)_data).getSequenceStatePojo();
/* 571 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 572 */       LOGGER.finer(this.loggerProlog + "Sending for replication sequence data with a key [" + key + "]: " + value.toString() + ", isNew=" + isNew);
/*     */     }
/*     */     
/* 575 */     HaInfo haInfo = HaContext.currentHaInfo();
/* 576 */     if (haInfo != null) {
/* 577 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 578 */         LOGGER.finer(this.loggerProlog + "Existing HaInfo found, using it for sequence data replication: " + HaContext.asString(haInfo));
/*     */       }
/* 580 */       HaContext.udpateReplicaInstance(HighAvailabilityProvider.saveTo(this.sequenceDataBs, (Serializable)new StickyKey(key, haInfo.getKey()), value, isNew));
/*     */     } else {
/* 582 */       StickyKey stickyKey = new StickyKey(key);
/* 583 */       String replicaId = HighAvailabilityProvider.saveTo(this.sequenceDataBs, (Serializable)stickyKey, value, isNew);
/*     */       
/* 585 */       haInfo = new HaInfo(stickyKey.getHashKey(), replicaId, false);
/* 586 */       HaContext.updateHaInfo(haInfo);
/* 587 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 588 */         LOGGER.finer(this.loggerProlog + "No HaInfo found, created new after sequence data replication: " + HaContext.asString(haInfo));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(String key) {
/* 594 */     HighAvailabilityProvider.removeFrom(this.sequenceDataBs, (Serializable)new StickyKey(key));
/* 595 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 596 */       LOGGER.finer(this.loggerProlog + "Removed sequence data from the backing store for key [" + key + "]");
/*     */     }
/*     */   }
/*     */   
/*     */   public void close() {
/* 601 */     HighAvailabilityProvider.close(this.sequenceDataBs);
/* 602 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 603 */       LOGGER.finer(this.loggerProlog + "Closed sequence data backing store");
/*     */     }
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 608 */     HighAvailabilityProvider.destroy(this.sequenceDataBs);
/* 609 */     if (LOGGER.isLoggable(Level.FINER))
/* 610 */       LOGGER.finer(this.loggerProlog + "Destroyed sequence data backing store"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\invm\InVmSequenceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */