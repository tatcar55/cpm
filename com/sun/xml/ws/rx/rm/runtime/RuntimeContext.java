/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.commons.ScheduledTaskManager;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceManager;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceManagerFactory;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.UnknownSequenceException;
/*     */ import com.sun.xml.ws.rx.util.Communicator;
/*     */ import com.sun.xml.ws.rx.util.SuspendedFiberStorage;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RuntimeContext
/*     */ {
/*     */   public final RmConfiguration configuration;
/*     */   public final AddressingVersion addressingVersion;
/*     */   public final SOAPVersion soapVersion;
/*     */   public final RmRuntimeVersion rmVersion;
/*     */   private volatile SequenceManager sequenceManager;
/*     */   public final Communicator communicator;
/*     */   public final SuspendedFiberStorage suspendedFiberStorage;
/*     */   public final WsrmProtocolHandler protocolHandler;
/*     */   public final ScheduledTaskManager scheduledTaskManager;
/*     */   final SourceMessageHandler sourceMessageHandler;
/*     */   final DestinationMessageHandler destinationMessageHandler;
/*     */   
/*     */   public static Builder builder(@NotNull RmConfiguration configuration, @NotNull Communicator communicator) {
/*  62 */     return new Builder(configuration, communicator);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     @NotNull
/*     */     private final RmConfiguration configuration;
/*     */     
/*     */     @NotNull
/*     */     private final Communicator communicator;
/*     */     
/*     */     @Nullable
/*     */     private SequenceManager sequenceManager;
/*     */     
/*     */     @Nullable
/*     */     private SourceMessageHandler sourceMessageHandler;
/*     */     
/*     */     @Nullable
/*     */     private DestinationMessageHandler destinationMessageHandler;
/*     */     
/*     */     public Builder(@NotNull RmConfiguration configuration, @NotNull Communicator communicator) {
/*  84 */       assert configuration != null;
/*  85 */       assert communicator != null;
/*     */       
/*  87 */       this.configuration = configuration;
/*  88 */       this.communicator = communicator;
/*     */       
/*  90 */       this.sourceMessageHandler = new SourceMessageHandler(null);
/*  91 */       this.destinationMessageHandler = new DestinationMessageHandler(null);
/*     */     }
/*     */     
/*     */     public Builder sequenceManager(SequenceManager sequenceManager) {
/*  95 */       this.sequenceManager = sequenceManager;
/*     */       
/*  97 */       this.sourceMessageHandler.setSequenceManager(sequenceManager);
/*  98 */       this.destinationMessageHandler.setSequenceManager(sequenceManager);
/*     */       
/* 100 */       return this;
/*     */     }
/*     */     
/*     */     public RuntimeContext build() {
/* 104 */       return new RuntimeContext(this.configuration, this.sequenceManager, this.communicator, new SuspendedFiberStorage(), new ScheduledTaskManager("RM Runtime Context"), this.sourceMessageHandler, this.destinationMessageHandler);
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
/* 125 */   private final AtomicBoolean closed = new AtomicBoolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RuntimeContext(RmConfiguration configuration, SequenceManager sequenceManager, Communicator communicator, SuspendedFiberStorage suspendedFiberStorage, ScheduledTaskManager scheduledTaskManager, SourceMessageHandler srcMsgHandler, DestinationMessageHandler dstMsgHandler) {
/* 137 */     this.configuration = configuration;
/* 138 */     this.sequenceManager = sequenceManager;
/* 139 */     this.communicator = communicator;
/* 140 */     this.suspendedFiberStorage = suspendedFiberStorage;
/* 141 */     this.scheduledTaskManager = scheduledTaskManager;
/* 142 */     this.sourceMessageHandler = srcMsgHandler;
/* 143 */     this.destinationMessageHandler = dstMsgHandler;
/*     */     
/* 145 */     this.addressingVersion = configuration.getAddressingVersion();
/* 146 */     this.soapVersion = configuration.getSoapVersion();
/* 147 */     this.rmVersion = configuration.getRuntimeVersion();
/*     */     
/* 149 */     this.protocolHandler = WsrmProtocolHandler.getInstance(configuration, communicator, this);
/*     */   }
/*     */   
/*     */   public void close() {
/* 153 */     if (this.closed.compareAndSet(false, true)) {
/* 154 */       this.scheduledTaskManager.shutdown();
/* 155 */       this.communicator.close();
/*     */       
/* 157 */       if (this.sequenceManager != null) {
/* 158 */         SequenceManagerFactory.INSTANCE.dispose(this.sequenceManager, this.configuration);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getBoundSequenceId(String sequenceId) throws UnknownSequenceException {
/* 164 */     assert this.sequenceManager != null;
/*     */     
/* 166 */     Sequence boundSequence = this.sequenceManager.getBoundSequence(sequenceId);
/* 167 */     return (boundSequence != null) ? boundSequence.getId() : null;
/*     */   }
/*     */   
/*     */   public SequenceManager sequenceManager() {
/* 171 */     assert this.sequenceManager != null;
/*     */     
/* 173 */     return this.sequenceManager;
/*     */   }
/*     */   
/*     */   public void setSequenceManager(@NotNull SequenceManager newValue) {
/* 177 */     assert newValue != null;
/*     */     
/* 179 */     this.sequenceManager = newValue;
/*     */     
/* 181 */     this.sourceMessageHandler.setSequenceManager(newValue);
/* 182 */     this.destinationMessageHandler.setSequenceManager(newValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\RuntimeContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */