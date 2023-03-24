/*     */ package com.sun.xml.ws.dump;
/*     */ 
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
/*     */ final class MessageDumper
/*     */ {
/*     */   private final String tubeName;
/*     */   private final Logger logger;
/*     */   private Level loggingLevel;
/*     */   
/*     */   enum MessageType
/*     */   {
/*  53 */     Request("Request message"),
/*  54 */     Response("Response message"),
/*  55 */     Exception("Response exception");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     MessageType(String name) {
/*  60 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  65 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   enum ProcessingState {
/*  70 */     Received("received"),
/*  71 */     Processed("processed");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     ProcessingState(String name) {
/*  76 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  81 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageDumper(String tubeName, Logger logger, Level loggingLevel) {
/*  92 */     this.tubeName = tubeName;
/*  93 */     this.logger = logger;
/*  94 */     this.loggingLevel = loggingLevel;
/*     */   }
/*     */   
/*     */   final boolean isLoggable() {
/*  98 */     return this.logger.isLoggable(this.loggingLevel);
/*     */   }
/*     */   
/*     */   final void setLoggingLevel(Level level) {
/* 102 */     this.loggingLevel = level;
/*     */   }
/*     */   
/*     */   final String createLogMessage(MessageType messageType, ProcessingState processingState, int tubeId, String engineId, String message) {
/* 106 */     return String.format("%s %s in Tube [ %s ] Instance [ %d ] Engine [ %s ] Thread [ %s ]:%n%s", new Object[] { messageType, processingState, this.tubeName, Integer.valueOf(tubeId), engineId, Thread.currentThread().getName(), message });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String dump(MessageType messageType, ProcessingState processingState, String message, int tubeId, String engineId) {
/* 117 */     String logMessage = createLogMessage(messageType, processingState, tubeId, engineId, message);
/* 118 */     this.logger.log(this.loggingLevel, logMessage);
/*     */     
/* 120 */     return logMessage;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\dump\MessageDumper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */