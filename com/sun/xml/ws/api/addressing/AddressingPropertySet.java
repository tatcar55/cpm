/*    */ package com.sun.xml.ws.api.addressing;
/*    */ 
/*    */ import com.oracle.webservices.api.message.BasePropertySet;
/*    */ import com.oracle.webservices.api.message.PropertySet.Property;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AddressingPropertySet
/*    */   extends BasePropertySet
/*    */ {
/*    */   public static final String ADDRESSING_FAULT_TO = "com.sun.xml.ws.api.addressing.fault.to";
/*    */   private String faultTo;
/*    */   public static final String ADDRESSING_MESSAGE_ID = "com.sun.xml.ws.api.addressing.message.id";
/*    */   private String messageId;
/*    */   public static final String ADDRESSING_RELATES_TO = "com.sun.xml.ws.api.addressing.relates.to";
/*    */   @Property({"com.sun.xml.ws.api.addressing.relates.to"})
/*    */   private String relatesTo;
/*    */   public static final String ADDRESSING_REPLY_TO = "com.sun.xml.ws.api.addressing.reply.to";
/*    */   @Property({"com.sun.xml.ws.api.addressing.reply.to"})
/*    */   private String replyTo;
/*    */   
/*    */   @Property({"com.sun.xml.ws.api.addressing.fault.to"})
/*    */   public String getFaultTo() {
/* 62 */     return this.faultTo; } public void setFaultTo(String x) {
/* 63 */     this.faultTo = x;
/*    */   }
/*    */   
/*    */   public String getMessageId() {
/* 67 */     return this.messageId; } public void setMessageId(String x) {
/* 68 */     this.messageId = x;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRelatesTo() {
/* 73 */     return this.relatesTo; } public void setRelatesTo(String x) {
/* 74 */     this.relatesTo = x;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getReplyTo() {
/* 79 */     return this.replyTo; } public void setReplyTo(String x) {
/* 80 */     this.replyTo = x;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 90 */   private static final BasePropertySet.PropertyMap model = parse(AddressingPropertySet.class);
/*    */ 
/*    */ 
/*    */   
/*    */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 95 */     return model;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\addressing\AddressingPropertySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */