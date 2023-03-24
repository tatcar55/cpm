/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.oracle.webservices.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.api.message.PropertySet.Property;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThrowableContainerPropertySet
/*     */   extends BasePropertySet
/*     */ {
/*     */   public static final String FIBER_COMPLETION_THROWABLE = "com.sun.xml.ws.api.pipe.fiber-completion-throwable";
/*     */   private Throwable throwable;
/*     */   public static final String FAULT_MESSAGE = "com.sun.xml.ws.api.pipe.fiber-completion-fault-message";
/*     */   private Message faultMessage;
/*     */   public static final String RESPONSE_PACKET = "com.sun.xml.ws.api.pipe.fiber-completion-response-packet";
/*     */   private Packet responsePacket;
/*     */   public static final String IS_FAULT_CREATED = "com.sun.xml.ws.api.pipe.fiber-completion-is-fault-created";
/*     */   private boolean isFaultCreated;
/*     */   
/*     */   @Property({"com.sun.xml.ws.api.pipe.fiber-completion-throwable"})
/*     */   public Throwable getThrowable() {
/*     */     return this.throwable;
/*     */   }
/*     */   
/*     */   public void setThrowable(Throwable throwable) {
/*     */     this.throwable = throwable;
/*     */   }
/*     */   
/*     */   @Property({"com.sun.xml.ws.api.pipe.fiber-completion-fault-message"})
/*     */   public Message getFaultMessage() {
/*     */     return this.faultMessage;
/*     */   }
/*     */   
/*     */   public void setFaultMessage(Message faultMessage) {
/*     */     this.faultMessage = faultMessage;
/*     */   }
/*     */   
/*     */   @Property({"com.sun.xml.ws.api.pipe.fiber-completion-response-packet"})
/*     */   public Packet getResponsePacket() {
/*     */     return this.responsePacket;
/*     */   }
/*     */   
/*     */   public void setResponsePacket(Packet responsePacket) {
/*     */     this.responsePacket = responsePacket;
/*     */   }
/*     */   
/*     */   public ThrowableContainerPropertySet(Throwable throwable) {
/* 113 */     this.isFaultCreated = false;
/*     */     this.throwable = throwable; } @Property({"com.sun.xml.ws.api.pipe.fiber-completion-is-fault-created"})
/*     */   public boolean isFaultCreated() {
/* 116 */     return this.isFaultCreated;
/*     */   }
/*     */   public void setFaultCreated(boolean isFaultCreated) {
/* 119 */     this.isFaultCreated = isFaultCreated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 128 */     return model;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 133 */   private static final BasePropertySet.PropertyMap model = parse(ThrowableContainerPropertySet.class);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\ThrowableContainerPropertySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */