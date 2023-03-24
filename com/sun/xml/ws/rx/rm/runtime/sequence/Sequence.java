/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence;@ManagedData
/*     */ @Description("Reliable Messaging Sequence")
/*     */ public interface Sequence { public static final long UNSPECIFIED_MESSAGE_ID = 0L;
/*     */   public static final long MIN_MESSAGE_ID = 1L;
/*     */   public static final long MAX_MESSAGE_ID = 9223372036854775807L;
/*     */   public static final long NO_EXPIRY = -1L;
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Unique sequence identifier")
/*     */   String getId();
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Last message identifier register on this sequence")
/*     */   long getLastMessageNumber();
/*     */   
/*     */   void registerMessage(@NotNull ApplicationMessage paramApplicationMessage, boolean paramBoolean) throws DuplicateMessageRegistrationException, AbstractSoapFaultException;
/*     */   
/*     */   @Nullable
/*     */   ApplicationMessage retrieveMessage(@NotNull String paramString);
/*     */   
/*     */   DeliveryQueue getDeliveryQueue();
/*     */   
/*     */   void acknowledgeMessageNumbers(List<AckRange> paramList) throws InvalidAcknowledgementException, AbstractSoapFaultException;
/*     */   
/*     */   void acknowledgeMessageNumber(long paramLong) throws AbstractSoapFaultException;
/*     */   
/*     */   boolean isFailedOver(long paramLong);
/*     */   
/*     */   List<AckRange> getAcknowledgedMessageNumbers();
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("True if the sequence has unacknowledged message identifiers")
/*     */   boolean hasUnacknowledgedMessages();
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Runtime state of the sequence")
/*     */   State getState();
/*     */   
/*     */   void setAckRequestedFlag();
/*     */   
/*     */   void clearAckRequestedFlag();
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("True if AckRequested flag set")
/*     */   boolean isAckRequested();
/*     */   
/*     */   void updateLastAcknowledgementRequestTime();
/*     */   
/*     */   boolean isStandaloneAcknowledgementRequestSchedulable(long paramLong);
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("The security token reference identifier to which this sequence is bound")
/*     */   String getBoundSecurityTokenReferenceId();
/*     */   
/*     */   void close();
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("True if the sequence has been closed")
/*     */   boolean isClosed();
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("True if the sequence has expired")
/*     */   boolean isExpired();
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Last activity time on the sequence in milliseconds")
/*     */   long getLastActivityTime();
/*     */   
/*     */   void preDestroy();
/*     */   
/*  71 */   public enum State { CREATED(15)
/*     */     {
/*     */       void verifyAcceptingAcknowledgement(String sequenceId, AbstractSoapFaultException.Code code) throws AbstractSoapFaultException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       void verifyAcceptingMessageRegistration(String sequenceId, AbstractSoapFaultException.Code code) throws AbstractSoapFaultException {}
/*     */     },
/*  83 */     CLOSING(20)
/*     */     {
/*     */       void verifyAcceptingAcknowledgement(String sequenceId, AbstractSoapFaultException.Code code) throws AbstractSoapFaultException
/*     */       {
/*  87 */         throw new SequenceClosedException(sequenceId, LocalizationMessages.WSRM_1135_WRONG_SEQUENCE_STATE_ACKNOWLEDGEMENT_REJECTED(sequenceId, this));
/*     */       }
/*     */ 
/*     */       
/*     */       void verifyAcceptingMessageRegistration(String sequenceId, AbstractSoapFaultException.Code code) throws AbstractSoapFaultException {
/*  92 */         throw new SequenceClosedException(sequenceId, LocalizationMessages.WSRM_1136_WRONG_SEQUENCE_STATE_MESSAGE_REGISTRATION(sequenceId, this));
/*     */       }
/*     */     },
/*  95 */     CLOSED(25)
/*     */     {
/*     */       void verifyAcceptingAcknowledgement(String sequenceId, AbstractSoapFaultException.Code code) throws AbstractSoapFaultException
/*     */       {
/*  99 */         throw new SequenceClosedException(sequenceId, LocalizationMessages.WSRM_1135_WRONG_SEQUENCE_STATE_ACKNOWLEDGEMENT_REJECTED(sequenceId, this));
/*     */       }
/*     */ 
/*     */       
/*     */       void verifyAcceptingMessageRegistration(String sequenceId, AbstractSoapFaultException.Code code) throws AbstractSoapFaultException {
/* 104 */         throw new SequenceClosedException(sequenceId, LocalizationMessages.WSRM_1136_WRONG_SEQUENCE_STATE_MESSAGE_REGISTRATION(sequenceId, this));
/*     */       }
/*     */     },
/* 107 */     TERMINATING(30)
/*     */     {
/*     */       void verifyAcceptingAcknowledgement(String sequenceId, AbstractSoapFaultException.Code code) throws AbstractSoapFaultException
/*     */       {
/* 111 */         throw new SequenceTerminatedException(sequenceId, LocalizationMessages.WSRM_1135_WRONG_SEQUENCE_STATE_ACKNOWLEDGEMENT_REJECTED(sequenceId, this), code);
/*     */       }
/*     */ 
/*     */       
/*     */       void verifyAcceptingMessageRegistration(String sequenceId, AbstractSoapFaultException.Code code) throws AbstractSoapFaultException {
/* 116 */         throw new SequenceTerminatedException(sequenceId, LocalizationMessages.WSRM_1136_WRONG_SEQUENCE_STATE_MESSAGE_REGISTRATION(sequenceId, this), code);
/*     */       } };
/*     */     
/*     */     private int value;
/*     */     
/*     */     State(int value) {
/* 122 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int asInt() {
/* 126 */       return this.value;
/*     */     }
/*     */     
/*     */     public static State asState(int value) {
/* 130 */       for (State status : values()) {
/* 131 */         if (status.value == value) {
/* 132 */           return status;
/*     */         }
/*     */       } 
/*     */       
/* 136 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     abstract void verifyAcceptingMessageRegistration(String param1String, AbstractSoapFaultException.Code param1Code);
/*     */ 
/*     */     
/*     */     abstract void verifyAcceptingAcknowledgement(String param1String, AbstractSoapFaultException.Code param1Code); }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum IncompleteSequenceBehavior
/*     */   {
/* 150 */     NO_DISCARD,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     DISCARD_ENTIRE_SEQUENCE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     DISCARD_FOLLOWING_FIRST_GAP;
/*     */     
/*     */     public static IncompleteSequenceBehavior getDefault() {
/* 164 */       return NO_DISCARD;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class AckRange
/*     */   {
/* 170 */     private static final Comparator<AckRange> COMPARATOR = new Comparator<AckRange>()
/*     */       {
/*     */         public int compare(Sequence.AckRange range1, Sequence.AckRange range2) {
/* 173 */           if (range1.lower <= range2.lower) {
/* 174 */             return -1;
/*     */           }
/* 176 */           return 1;
/*     */         }
/*     */       };
/*     */     public final long lower; public final long upper;
/*     */     
/*     */     public static void sort(@NotNull List<AckRange> ranges) {
/* 182 */       if (ranges.size() > 1) {
/* 183 */         Collections.sort(ranges, COMPARATOR);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AckRange(long lower, long upper) {
/* 192 */       this.lower = lower;
/* 193 */       this.upper = upper;
/*     */     }
/*     */   } }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\Sequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */