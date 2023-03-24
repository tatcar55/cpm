/*     */ package com.sun.xml.ws.dump;
/*     */ 
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
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
/*     */ @ManagedData
/*     */ public final class MessageDumpingFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "com.sun.xml.ws.messagedump.MessageDumpingFeature";
/*  61 */   private static final Level DEFAULT_MSG_LOG_LEVEL = Level.FINE;
/*     */   
/*     */   private final Queue<String> messageQueue;
/*     */   private final AtomicBoolean messageLoggingStatus;
/*     */   private final String messageLoggingRoot;
/*     */   private final Level messageLoggingLevel;
/*     */   
/*     */   public MessageDumpingFeature() {
/*  69 */     this(null, null, true);
/*     */   }
/*     */   
/*     */   public MessageDumpingFeature(String msgLogRoot, Level msgLogLevel, boolean storeMessages) {
/*  73 */     this.messageQueue = storeMessages ? new ConcurrentLinkedQueue<String>() : null;
/*  74 */     this.messageLoggingStatus = new AtomicBoolean(true);
/*  75 */     this.messageLoggingRoot = (msgLogRoot != null && msgLogRoot.length() > 0) ? msgLogRoot : "com.sun.xml.ws.messagedump";
/*  76 */     this.messageLoggingLevel = (msgLogLevel != null) ? msgLogLevel : DEFAULT_MSG_LOG_LEVEL;
/*     */     
/*  78 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageDumpingFeature(boolean enabled) {
/*  83 */     this();
/*  84 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   @FeatureConstructor({"enabled", "messageLoggingRoot", "messageLoggingLevel", "storeMessages"})
/*     */   public MessageDumpingFeature(boolean enabled, String msgLogRoot, String msgLogLevel, boolean storeMessages) {
/*  90 */     this(msgLogRoot, Level.parse(msgLogLevel), storeMessages);
/*     */     
/*  92 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/*  98 */     return "com.sun.xml.ws.messagedump.MessageDumpingFeature";
/*     */   }
/*     */   
/*     */   public String nextMessage() {
/* 102 */     return (this.messageQueue != null) ? this.messageQueue.poll() : null;
/*     */   }
/*     */   
/*     */   public void enableMessageLogging() {
/* 106 */     this.messageLoggingStatus.set(true);
/*     */   }
/*     */   
/*     */   public void disableMessageLogging() {
/* 110 */     this.messageLoggingStatus.set(false);
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public boolean getMessageLoggingStatus() {
/* 115 */     return this.messageLoggingStatus.get();
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getMessageLoggingRoot() {
/* 120 */     return this.messageLoggingRoot;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public Level getMessageLoggingLevel() {
/* 125 */     return this.messageLoggingLevel;
/*     */   }
/*     */   
/*     */   boolean offerMessage(String message) {
/* 129 */     return (this.messageQueue != null) ? this.messageQueue.offer(message) : false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\dump\MessageDumpingFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */