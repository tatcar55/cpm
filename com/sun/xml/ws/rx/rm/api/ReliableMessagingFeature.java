/*     */ package com.sun.xml.ws.rx.rm.api;
/*     */ 
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import com.sun.xml.ws.api.ha.StickyFeature;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedData
/*     */ public class ReliableMessagingFeature
/*     */   extends WebServiceFeature
/*     */   implements StickyFeature
/*     */ {
/*     */   public static final String ID = "http://docs.oasis-open.org/ws-rx/wsrm/";
/*     */   public static final long DEFAULT_SEQUENCE_INACTIVITY_TIMEOUT = 600000L;
/*     */   public static final long DEFAULT_DESTINATION_BUFFER_QUOTA = 32L;
/*     */   public static final long DEFAULT_MESSAGE_RETRANSMISSION_INTERVAL = 2000L;
/*     */   public static final long DEFAULT_MAX_MESSAGE_RETRANSMISSION_COUNT = -1L;
/*     */   public static final long DEFAULT_MAX_RM_SESSION_CONTROL_MESSAGE_RESEND_ATTEMPTS = 3L;
/*     */   public static final long DEFAULT_ACKNOWLEDGEMENT_TRANSMISSION_INTERVAL = -1L;
/*     */   public static final long DEFAULT_ACK_REQUEST_TRANSMISSION_INTERVAL = 2000L;
/*     */   public static final long DEFAULT_CLOSE_SEQUENCE_OPERATION_TIMEOUT = 3000L;
/*     */   public static final long DEFAULT_SEQUENCE_MANAGER_MAINTENANCE_PERIOD = 60000L;
/*     */   public static final long DEFAULT_MAX_CONCURRENT_SESSIONS = -1L;
/*     */   public static final boolean DEFAULT_OFFER_ELEMENT_GENERATION_DISABLED = false;
/*     */   private final RmProtocolVersion version;
/*     */   private final long sequenceInactivityTimeout;
/*     */   private final long destinationBufferQuota;
/*     */   private final boolean orderedDelivery;
/*     */   private final DeliveryAssurance deliveryAssurance;
/*     */   private final SecurityBinding securityBinding;
/*     */   private final long messageRetransmissionInterval;
/*     */   private final BackoffAlgorithm retransmissionBackoffAlgorithm;
/*     */   private final long maxMessageRetransmissionCount;
/*     */   private final long maxRmSessionControlMessageResendAttempts;
/*     */   private final long acknowledgementTransmissionInterval;
/*     */   private final long ackRequestTransmissionInterval;
/*     */   private final long closeSequenceOperationTimeout;
/*     */   private final boolean persistenceEnabled;
/*     */   private final long sequenceManagerMaintenancePeriod;
/*     */   private final long maxConcurrentSessions;
/*     */   private final boolean offerElementGenerationDisabled;
/*     */   
/*     */   public enum SecurityBinding
/*     */   {
/* 144 */     STR,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     TRANSPORT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static SecurityBinding getDefault() {
/* 175 */       return NONE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum DeliveryAssurance
/*     */   {
/* 202 */     EXACTLY_ONCE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     AT_LEAST_ONCE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 226 */     AT_MOST_ONCE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static DeliveryAssurance getDefault() {
/* 236 */       return EXACTLY_ONCE;
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
/*     */   public enum BackoffAlgorithm
/*     */   {
/* 255 */     CONSTANT("Constant")
/*     */     {
/*     */       public long getDelayInMillis(int resendAttemptNumber, long baseRate) {
/* 258 */         return baseRate;
/*     */       }
/*     */     },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     EXPONENTIAL("Exponential")
/*     */     {
/*     */       public long getDelayInMillis(int resendAttemptNumber, long baseRate) {
/* 270 */         return resendAttemptNumber * baseRate;
/*     */       }
/*     */     };
/*     */     
/*     */     private final String name;
/*     */     
/*     */     BackoffAlgorithm(String name) {
/* 277 */       this.name = name;
/*     */     }
/*     */     
/*     */     public static BackoffAlgorithm parse(String name) {
/* 281 */       for (BackoffAlgorithm value : values()) {
/* 282 */         if (value.name.equals(name)) {
/* 283 */           return value;
/*     */         }
/*     */       } 
/*     */       
/* 287 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static BackoffAlgorithm getDefault() {
/* 298 */       return CONSTANT;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract long getDelayInMillis(int param1Int, long param1Long);
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
/*     */   public ReliableMessagingFeature() {
/* 345 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeature(boolean enabled) {
/* 352 */     this(enabled, RmProtocolVersion.getDefault(), 600000L, 32L, false, DeliveryAssurance.getDefault(), SecurityBinding.getDefault(), 2000L, BackoffAlgorithm.getDefault(), -1L, 3L, -1L, 2000L, 3000L, false, 60000L, -1L, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FeatureConstructor({"enabled", "version", "sequenceInactivityTimeout", "destinationBufferQuota", "orderedDeliveryEnabled", "deliveryAssurance", "securityBinding", "persistenceEnabled", "sequenceManagerMaintenancePeriod", "maxConcurrentSessions"})
/*     */   public ReliableMessagingFeature(boolean enabled, RmProtocolVersion version, long inactivityTimeout, long bufferQuota, boolean orderedDelivery, DeliveryAssurance deliveryAssurance, SecurityBinding securityBinding, boolean persistenceEnabled, long sequenceManagerMaintenancePeriod, long maxConcurrentSessions) {
/* 397 */     this(enabled, version, inactivityTimeout, bufferQuota, orderedDelivery, deliveryAssurance, securityBinding, 2000L, BackoffAlgorithm.getDefault(), -1L, 3L, -1L, 2000L, 3000L, persistenceEnabled, sequenceManagerMaintenancePeriod, maxConcurrentSessions, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ReliableMessagingFeature(boolean enabled, RmProtocolVersion version, long inactivityTimeout, long bufferQuota, boolean orderedDelivery, DeliveryAssurance deliveryAssurance, SecurityBinding securityBinding, long messageRetransmissionInterval, BackoffAlgorithm retransmissionBackoffAlgorithm, long maxMessageRetransmissionCount, long maxRmSessionControlMessageResendAttempts, long acknowledgementTransmissionInterval, long ackRequestTransmissionInterval, long closeSequenceOperationTimeout, boolean persistenceEnabled, long sequenceManagerMaintenancePeriod, long maxConcurrentRmSessions, boolean offerElementGenerationDisabled) {
/* 441 */     this.enabled = enabled;
/* 442 */     this.version = version;
/* 443 */     this.sequenceInactivityTimeout = inactivityTimeout;
/* 444 */     this.destinationBufferQuota = bufferQuota;
/* 445 */     this.orderedDelivery = orderedDelivery;
/* 446 */     this.deliveryAssurance = deliveryAssurance;
/* 447 */     this.securityBinding = securityBinding;
/* 448 */     this.messageRetransmissionInterval = messageRetransmissionInterval;
/* 449 */     this.retransmissionBackoffAlgorithm = retransmissionBackoffAlgorithm;
/* 450 */     this.maxMessageRetransmissionCount = maxMessageRetransmissionCount;
/* 451 */     this.maxRmSessionControlMessageResendAttempts = maxRmSessionControlMessageResendAttempts;
/* 452 */     this.acknowledgementTransmissionInterval = acknowledgementTransmissionInterval;
/* 453 */     this.ackRequestTransmissionInterval = ackRequestTransmissionInterval;
/* 454 */     this.closeSequenceOperationTimeout = closeSequenceOperationTimeout;
/* 455 */     this.persistenceEnabled = persistenceEnabled;
/* 456 */     this.sequenceManagerMaintenancePeriod = sequenceManagerMaintenancePeriod;
/* 457 */     this.maxConcurrentSessions = maxConcurrentRmSessions;
/* 458 */     this.offerElementGenerationDisabled = offerElementGenerationDisabled;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/* 464 */     return "http://docs.oasis-open.org/ws-rx/wsrm/";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public RmProtocolVersion getProtocolVersion() {
/* 476 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public long getSequenceInactivityTimeout() {
/* 488 */     return this.sequenceInactivityTimeout;
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
/*     */   @ManagedAttribute
/*     */   public SecurityBinding getSecurityBinding() {
/* 502 */     return this.securityBinding;
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
/*     */   @ManagedAttribute
/*     */   public DeliveryAssurance getDeliveryAssurance() {
/* 523 */     return this.deliveryAssurance;
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
/*     */   
/*     */   @ManagedAttribute
/*     */   public boolean isOrderedDeliveryEnabled() {
/* 550 */     return this.orderedDelivery;
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
/*     */   @ManagedAttribute
/*     */   public long getDestinationBufferQuota() {
/* 565 */     return this.destinationBufferQuota;
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
/*     */   @ManagedAttribute
/*     */   public long getMessageRetransmissionInterval() {
/* 578 */     return this.messageRetransmissionInterval;
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
/*     */   @ManagedAttribute
/*     */   public BackoffAlgorithm getRetransmissionBackoffAlgorithm() {
/* 593 */     return this.retransmissionBackoffAlgorithm;
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
/*     */   @ManagedAttribute
/*     */   public long getMaxMessageRetransmissionCount() {
/* 618 */     return this.maxMessageRetransmissionCount;
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
/*     */   public boolean canRetransmitMessage(long nextRetransmissionCount) {
/* 634 */     if (this.maxMessageRetransmissionCount < 0L) {
/* 635 */       return true;
/*     */     }
/*     */     
/* 638 */     return (nextRetransmissionCount <= this.maxMessageRetransmissionCount);
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
/*     */   @ManagedAttribute
/*     */   public long getMaxRmSessionControlMessageResendAttempts() {
/* 652 */     return this.maxRmSessionControlMessageResendAttempts;
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
/*     */   @ManagedAttribute
/*     */   public long getAcknowledgementTransmissionInterval() {
/* 665 */     return this.acknowledgementTransmissionInterval;
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
/*     */   @ManagedAttribute
/*     */   public long getAckRequestTransmissionInterval() {
/* 678 */     return this.ackRequestTransmissionInterval;
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
/*     */   @ManagedAttribute
/*     */   public long getCloseSequenceOperationTimeout() {
/* 692 */     return this.closeSequenceOperationTimeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public boolean isPersistenceEnabled() {
/* 702 */     return this.persistenceEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public long getSequenceManagerMaintenancePeriod() {
/* 712 */     return this.sequenceManagerMaintenancePeriod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public long getMaxConcurrentSessions() {
/* 724 */     return this.maxConcurrentSessions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOfferElementGenerationDisabled() {
/* 735 */     return this.offerElementGenerationDisabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 740 */     return "ReliableMessagingFeature{\n\tversion=" + this.version + ",\n\tdeliveryAssurance=" + this.deliveryAssurance + ",\n\torderedDelivery=" + this.orderedDelivery + ",\n\tsecurityBinding=" + this.securityBinding + ",\n\tdestinationBufferQuota=" + this.destinationBufferQuota + ",\n\tpersistenceEnabled=" + this.persistenceEnabled + ",\n\tmessageRetransmissionInterval=" + this.messageRetransmissionInterval + ",\n\tretransmissionBackoffAlgorithm=" + this.retransmissionBackoffAlgorithm + ",\n\tmaxMessageRetransmissionCount=" + this.maxMessageRetransmissionCount + ",\n\tmaxRmSessionControlMessageResendAttempts=" + this.maxRmSessionControlMessageResendAttempts + ",\n\tacknowledgementTransmissionInterval=" + this.acknowledgementTransmissionInterval + ",\n\tackRequestTransmissionInterval=" + this.ackRequestTransmissionInterval + ",\n\tsequenceInactivityTimeout=" + this.sequenceInactivityTimeout + ",\n\tcloseSequenceOperationTimeout=" + this.closeSequenceOperationTimeout + ",\n\tsequenceManagerMaintenancePeriod=" + this.sequenceManagerMaintenancePeriod + ",\n\tmaxConcurrentSessions=" + this.maxConcurrentSessions + ",\n\tofferElementGenerationDisabled=" + this.offerElementGenerationDisabled + "\n}";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\api\ReliableMessagingFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */