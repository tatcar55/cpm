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
/*     */ import java.util.logging.Level;
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
/*     */ public class LoggingDumpTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   public enum Position
/*     */   {
/*  62 */     Before((String)MessageDumper.ProcessingState.Received, MessageDumper.ProcessingState.Processed),
/*  63 */     After((String)MessageDumper.ProcessingState.Processed, MessageDumper.ProcessingState.Received);
/*     */     
/*     */     private final MessageDumper.ProcessingState requestState;
/*     */     private final MessageDumper.ProcessingState responseState;
/*     */     
/*     */     Position(MessageDumper.ProcessingState requestState, MessageDumper.ProcessingState responseState) {
/*  69 */       this.requestState = requestState;
/*  70 */       this.responseState = responseState;
/*     */     }
/*     */   }
/*     */   
/*  74 */   private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
/*     */   
/*     */   private MessageDumper messageDumper;
/*     */   private final Level loggingLevel;
/*     */   private final Position position;
/*     */   private final int tubeId;
/*     */   
/*     */   public LoggingDumpTube(Level loggingLevel, Position position, Tube tubelineHead) {
/*  82 */     super(tubelineHead);
/*     */     
/*  84 */     this.position = position;
/*  85 */     this.loggingLevel = loggingLevel;
/*     */     
/*  87 */     this.tubeId = ID_GENERATOR.incrementAndGet();
/*     */   }
/*     */   
/*     */   public void setLoggedTubeName(String loggedTubeName) {
/*  91 */     assert this.messageDumper == null;
/*  92 */     this.messageDumper = new MessageDumper(loggedTubeName, Logger.getLogger(loggedTubeName), this.loggingLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LoggingDumpTube(LoggingDumpTube original, TubeCloner cloner) {
/*  99 */     super(original, cloner);
/*     */     
/* 101 */     this.messageDumper = original.messageDumper;
/* 102 */     this.loggingLevel = original.loggingLevel;
/* 103 */     this.position = original.position;
/*     */     
/* 105 */     this.tubeId = ID_GENERATOR.incrementAndGet();
/*     */   }
/*     */   
/*     */   public LoggingDumpTube copy(TubeCloner cloner) {
/* 109 */     return new LoggingDumpTube(this, cloner);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 115 */     if (this.messageDumper.isLoggable()) {
/* 116 */       Packet dumpPacket = (request != null) ? request.copy(true) : null;
/* 117 */       this.messageDumper.dump(MessageDumper.MessageType.Request, this.position.requestState, Converter.toString(dumpPacket), this.tubeId, (Fiber.current()).owner.id);
/*     */     } 
/*     */     
/* 120 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 125 */     if (this.messageDumper.isLoggable()) {
/* 126 */       Packet dumpPacket = (response != null) ? response.copy(true) : null;
/* 127 */       this.messageDumper.dump(MessageDumper.MessageType.Response, this.position.responseState, Converter.toString(dumpPacket), this.tubeId, (Fiber.current()).owner.id);
/*     */     } 
/*     */     
/* 130 */     return super.processResponse(response);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/* 135 */     if (this.messageDumper.isLoggable()) {
/* 136 */       this.messageDumper.dump(MessageDumper.MessageType.Exception, this.position.responseState, Converter.toString(t), this.tubeId, (Fiber.current()).owner.id);
/*     */     }
/*     */     
/* 139 */     return super.processException(t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 144 */     super.preDestroy();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\dump\LoggingDumpTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */