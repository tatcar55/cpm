/*     */ package com.sun.xml.ws.rx.rm.api;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReliableMessagingFeatureBuilder
/*     */ {
/*     */   private final RmProtocolVersion protocolVersion;
/*     */   private boolean enabled = true;
/*  54 */   private long inactivityTimeout = 600000L;
/*  55 */   private long destinationBufferQuota = 32L;
/*     */   private boolean orderedDelivery = false;
/*  57 */   private ReliableMessagingFeature.DeliveryAssurance deliveryAssurance = ReliableMessagingFeature.DeliveryAssurance.getDefault();
/*  58 */   private ReliableMessagingFeature.SecurityBinding securityBinding = ReliableMessagingFeature.SecurityBinding.getDefault();
/*     */   
/*  60 */   private long messageRetransmissionInterval = 2000L;
/*  61 */   private ReliableMessagingFeature.BackoffAlgorithm retransmissionBackoffAlgorithm = ReliableMessagingFeature.BackoffAlgorithm.getDefault();
/*  62 */   private long maxMessageRetransmissionCount = -1L;
/*     */   
/*  64 */   private long maxRmSessionControlMessageResendAttempts = 3L;
/*     */   
/*  66 */   private long ackTransmissionInterval = -1L;
/*  67 */   private long ackRequestTransmissionInterval = 2000L;
/*  68 */   private long closeSequenceOperationTimeout = 3000L;
/*     */   private boolean persistenceEnabled = false;
/*  70 */   private long sequenceMaintenancePeriod = 60000L;
/*  71 */   private long maxConcurrentSessions = -1L;
/*     */   
/*     */   private boolean offerElementGenerationDisabled = false;
/*     */   
/*     */   public ReliableMessagingFeatureBuilder(RmProtocolVersion version) {
/*  76 */     this.protocolVersion = version;
/*     */   }
/*     */   
/*     */   public ReliableMessagingFeature build() {
/*  80 */     return new ReliableMessagingFeature(this.enabled, this.protocolVersion, this.inactivityTimeout, this.destinationBufferQuota, this.orderedDelivery, this.deliveryAssurance, this.securityBinding, this.messageRetransmissionInterval, this.retransmissionBackoffAlgorithm, this.maxMessageRetransmissionCount, this.maxRmSessionControlMessageResendAttempts, this.ackTransmissionInterval, this.ackRequestTransmissionInterval, this.closeSequenceOperationTimeout, this.persistenceEnabled, this.sequenceMaintenancePeriod, this.maxConcurrentSessions, this.offerElementGenerationDisabled);
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
/*     */   
/*     */   public ReliableMessagingFeatureBuilder acknowledgementTransmissionInterval(long value) {
/* 105 */     this.ackTransmissionInterval = value;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder ackRequestTransmissionInterval(long value) {
/* 113 */     this.ackRequestTransmissionInterval = value;
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder messageRetransmissionInterval(long value) {
/* 121 */     this.messageRetransmissionInterval = value;
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder retransmissionBackoffAlgorithm(ReliableMessagingFeature.BackoffAlgorithm value) {
/* 129 */     this.retransmissionBackoffAlgorithm = value;
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder maxMessageRetransmissionCount(long value) {
/* 137 */     this.maxMessageRetransmissionCount = value;
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder maxRmSessionControlMessageResendAttempts(long value) {
/* 145 */     this.maxRmSessionControlMessageResendAttempts = value;
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder destinationBufferQuota(long value) {
/* 153 */     this.destinationBufferQuota = value;
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder closeSequenceOperationTimeout(long value) {
/* 161 */     this.closeSequenceOperationTimeout = value;
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder deliveryAssurance(ReliableMessagingFeature.DeliveryAssurance value) {
/* 169 */     this.deliveryAssurance = value;
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder sequenceInactivityTimeout(long value) {
/* 177 */     this.inactivityTimeout = value;
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder enableOrderedDelivery() {
/* 185 */     this.orderedDelivery = true;
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RmProtocolVersion getProtocolVersion() {
/* 193 */     return this.protocolVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder securityBinding(ReliableMessagingFeature.SecurityBinding value) {
/* 200 */     this.securityBinding = value;
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder enablePersistence() {
/* 208 */     this.persistenceEnabled = true;
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder disablePersistence() {
/* 216 */     this.persistenceEnabled = false;
/* 217 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder sequenceMaintenancePeriod(long value) {
/* 224 */     this.sequenceMaintenancePeriod = value;
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder maxConcurrentSessions(long value) {
/* 232 */     this.maxConcurrentSessions = value;
/*     */     
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder disableOfferElementGeneration() {
/* 241 */     this.offerElementGenerationDisabled = true;
/*     */     
/* 243 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\api\ReliableMessagingFeatureBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */