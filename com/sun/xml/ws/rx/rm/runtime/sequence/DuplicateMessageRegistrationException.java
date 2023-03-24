/*    */ package com.sun.xml.ws.rx.rm.runtime.sequence;
/*    */ 
/*    */ import com.sun.xml.ws.rx.RxException;
/*    */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuplicateMessageRegistrationException
/*    */   extends RxException
/*    */ {
/*    */   private static final long serialVersionUID = 8605938716798458482L;
/*    */   private final String sequenceId;
/*    */   private final long messageNumber;
/*    */   
/*    */   public DuplicateMessageRegistrationException(String sequenceId, long messageNumber) {
/* 59 */     super(LocalizationMessages.WSRM_1148_DUPLICATE_MSG_NUMBER_REGISTRATION_ATTEMPTED(Long.valueOf(messageNumber), sequenceId));
/*    */ 
/*    */ 
/*    */     
/* 63 */     this.sequenceId = sequenceId;
/* 64 */     this.messageNumber = messageNumber;
/*    */   }
/*    */   
/*    */   public long getMessageNumber() {
/* 68 */     return this.messageNumber;
/*    */   }
/*    */   
/*    */   public String getSequenceId() {
/* 72 */     return this.sequenceId;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\DuplicateMessageRegistrationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */