/*     */ package com.sun.xml.ws.dump;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.commons.xmlutil.Converter;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MessageDumpingTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   static final String DEFAULT_MSGDUMP_LOGGING_ROOT = "com.sun.xml.ws.messagedump";
/*  61 */   private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MessageDumper messageDumper;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int tubeId;
/*     */ 
/*     */ 
/*     */   
/*     */   private final MessageDumpingFeature messageDumpingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MessageDumpingTube(Tube next, MessageDumpingFeature feature) {
/*  80 */     super(next);
/*     */     
/*  82 */     this.messageDumpingFeature = feature;
/*  83 */     this.tubeId = ID_GENERATOR.incrementAndGet();
/*  84 */     this.messageDumper = new MessageDumper("MesageDumpingTube", Logger.getLogger(feature.getMessageLoggingRoot()), feature.getMessageLoggingLevel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MessageDumpingTube(MessageDumpingTube that, TubeCloner cloner) {
/*  94 */     super(that, cloner);
/*     */ 
/*     */     
/*  97 */     this.messageDumpingFeature = that.messageDumpingFeature;
/*  98 */     this.tubeId = ID_GENERATOR.incrementAndGet();
/*  99 */     this.messageDumper = that.messageDumper;
/*     */   }
/*     */   
/*     */   public MessageDumpingTube copy(TubeCloner cloner) {
/* 103 */     return new MessageDumpingTube(this, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 108 */     dump(MessageDumper.MessageType.Request, Converter.toString(request), (Fiber.current()).owner.id);
/* 109 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 114 */     dump(MessageDumper.MessageType.Response, Converter.toString(response), (Fiber.current()).owner.id);
/* 115 */     return super.processResponse(response);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/* 120 */     dump(MessageDumper.MessageType.Exception, Converter.toString(t), (Fiber.current()).owner.id);
/*     */     
/* 122 */     return super.processException(t);
/*     */   }
/*     */   
/*     */   protected final void dump(MessageDumper.MessageType messageType, String message, String engineId) {
/*     */     String logMessage;
/* 127 */     if (this.messageDumpingFeature.getMessageLoggingStatus()) {
/* 128 */       this.messageDumper.setLoggingLevel(this.messageDumpingFeature.getMessageLoggingLevel());
/* 129 */       logMessage = this.messageDumper.dump(messageType, MessageDumper.ProcessingState.Received, message, this.tubeId, engineId);
/*     */     } else {
/* 131 */       logMessage = this.messageDumper.createLogMessage(messageType, MessageDumper.ProcessingState.Received, this.tubeId, engineId, message);
/*     */     } 
/* 133 */     this.messageDumpingFeature.offerMessage(logMessage);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\dump\MessageDumpingTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */