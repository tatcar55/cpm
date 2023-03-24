/*     */ package com.sun.xml.ws.rx.mc.runtime;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.commons.ScheduledTaskManager;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.mc.dev.ProtocolMessageHandler;
/*     */ import com.sun.xml.ws.rx.mc.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.mc.protocol.wsmc200702.MakeConnectionElement;
/*     */ import com.sun.xml.ws.rx.util.Communicator;
/*     */ import com.sun.xml.ws.rx.util.SuspendedFiberStorage;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MakeConnectionSenderTask
/*     */   implements Runnable
/*     */ {
/*  64 */   private static final Logger LOGGER = Logger.getLogger(MakeConnectionSenderTask.class);
/*     */   
/*     */   private final String wsmcAnonymousAddress;
/*     */   
/*     */   private final Header wsmcAnnonymousReplyToHeader;
/*     */   
/*     */   private final Header wsmcAnnonymousFaultToHeader;
/*     */   
/*     */   private long lastMcMessageTimestamp;
/*     */   
/*     */   private final AtomicBoolean isMcRequestPending;
/*     */   
/*     */   private int scheduledMcRequestCounter;
/*     */   
/*     */   private final McConfiguration configuration;
/*     */   
/*     */   private final Communicator communicator;
/*     */   private final SuspendedFiberStorage suspendedFiberStorage;
/*     */   private final Map<String, ProtocolMessageHandler> mapOfRegisteredProtocolMessageHandlers;
/*     */   private final ScheduledTaskManager scheduler;
/*     */   private final AtomicBoolean isRunning;
/*     */   private final AtomicBoolean wasShutdown;
/*     */   
/*     */   MakeConnectionSenderTask(Communicator communicator, SuspendedFiberStorage suspendedFiberStorage, String wsmcAnonymousAddress, Header wsmcAnnonymousReplyToHeader, Header wsmcAnnonymousFaultToHeader, McConfiguration configuration) {
/*  88 */     this.communicator = communicator;
/*  89 */     this.suspendedFiberStorage = suspendedFiberStorage;
/*  90 */     this.wsmcAnonymousAddress = wsmcAnonymousAddress;
/*  91 */     this.wsmcAnnonymousReplyToHeader = wsmcAnnonymousReplyToHeader;
/*  92 */     this.wsmcAnnonymousFaultToHeader = wsmcAnnonymousFaultToHeader;
/*  93 */     this.configuration = configuration;
/*  94 */     this.mapOfRegisteredProtocolMessageHandlers = new HashMap<String, ProtocolMessageHandler>();
/*     */     
/*  96 */     this.lastMcMessageTimestamp = System.currentTimeMillis();
/*  97 */     this.isMcRequestPending = new AtomicBoolean(false);
/*  98 */     this.scheduledMcRequestCounter = 0;
/*     */     
/* 100 */     this.scheduler = new ScheduledTaskManager("MakeConnectionSenderTask");
/* 101 */     this.isRunning = new AtomicBoolean(false);
/* 102 */     this.wasShutdown = new AtomicBoolean(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 109 */     if (this.wasShutdown.get()) {
/* 110 */       throw new IllegalStateException("This task instance has already been shut down in the past.");
/*     */     }
/*     */     
/* 113 */     if (this.isRunning.compareAndSet(false, true))
/*     */     {
/* 115 */       this.scheduler.startTask(this, 2000L, 500L);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/* 120 */     return this.isRunning.get();
/*     */   }
/*     */   
/*     */   public boolean wasShutdown() {
/* 124 */     return this.wasShutdown.get();
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 128 */     if (this.isRunning.compareAndSet(true, false) && this.wasShutdown.compareAndSet(false, true)) {
/* 129 */       this.scheduler.shutdown();
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
/*     */   public synchronized void run() {
/* 146 */     if (!this.isMcRequestPending.get() && resendMakeConnectionIntervalPassed() && (this.scheduledMcRequestCounter > 0 || suspendedFibersReadyForResend())) {
/* 147 */       sendMcRequest();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean suspendedFibersReadyForResend() {
/* 153 */     while (!this.suspendedFiberStorage.isEmpty()) {
/* 154 */       long oldestRegistrationAge = System.currentTimeMillis() - this.suspendedFiberStorage.getOldestRegistrationTimestamp();
/*     */       
/* 156 */       if (oldestRegistrationAge > this.configuration.getFeature().getResponseRetrievalTimeout()) {
/* 157 */         ((Fiber)this.suspendedFiberStorage.removeOldest()).resume((Throwable)new RxRuntimeException(LocalizationMessages.WSMC_0123_RESPONSE_RETRIEVAL_TIMED_OUT())); continue;
/*     */       } 
/* 159 */       return (oldestRegistrationAge > this.configuration.getFeature().getBaseMakeConnectionRequetsInterval());
/*     */     } 
/*     */ 
/*     */     
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized boolean resendMakeConnectionIntervalPassed() {
/* 168 */     return (System.currentTimeMillis() - this.lastMcMessageTimestamp > this.configuration.getFeature().getBaseMakeConnectionRequetsInterval());
/*     */   }
/*     */   
/*     */   synchronized void register(ProtocolMessageHandler handler) {
/* 172 */     for (String wsaAction : handler.getSuportedWsaActions()) {
/* 173 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 174 */         LOGGER.finer(String.format("Registering ProtocolMessageHandler of class [ %s ] to process WS-A action [ %s ]", new Object[] { handler.getClass().getName(), wsaAction }));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 180 */       ProtocolMessageHandler oldHandler = this.mapOfRegisteredProtocolMessageHandlers.put(wsaAction, handler);
/*     */       
/* 182 */       if (oldHandler != null && LOGGER.isLoggable(Level.WARNING)) {
/* 183 */         LOGGER.warning(LocalizationMessages.WSMC_0101_DUPLICATE_PROTOCOL_MESSAGE_HANDLER(wsaAction, oldHandler.getClass().getName(), handler.getClass().getName()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void scheduleMcRequest() {
/* 192 */     this.scheduledMcRequestCounter++;
/*     */   }
/*     */   
/*     */   private void sendMcRequest() {
/* 196 */     Packet mcRequest = this.communicator.createRequestPacket(new MakeConnectionElement(this.wsmcAnonymousAddress), (this.configuration.getRuntimeVersion()).protocolVersion.wsmcAction, true);
/* 197 */     McClientTube.setMcAnnonymousHeaders(mcRequest.getMessage().getHeaders(), this.configuration.getAddressingVersion(), this.wsmcAnnonymousReplyToHeader, this.wsmcAnnonymousFaultToHeader);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     this.isMcRequestPending.set(true);
/*     */     try {
/* 205 */       this.communicator.sendAsync(mcRequest, new WsMcResponseHandler(this.configuration, this, this.suspendedFiberStorage, this.mapOfRegisteredProtocolMessageHandlers));
/*     */     } finally {
/* 207 */       this.lastMcMessageTimestamp = System.currentTimeMillis();
/* 208 */       if (--this.scheduledMcRequestCounter < 0) {
/* 209 */         this.scheduledMcRequestCounter = 0;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized void clearMcRequestPendingFlag() {
/* 215 */     this.isMcRequestPending.set(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\MakeConnectionSenderTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */