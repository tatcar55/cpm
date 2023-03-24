/*    */ package com.sun.xml.ws.rx.rm.runtime.sequence;
/*    */ 
/*    */ import com.sun.xml.ws.rx.RxRuntimeException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DuplicateSequenceException
/*    */   extends RxRuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -4888405115401229826L;
/*    */   private final String sequenceId;
/*    */   
/*    */   public DuplicateSequenceException(String sequenceId) {
/* 66 */     super(createErrorMessage(sequenceId));
/* 67 */     this.sequenceId = sequenceId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSequenceId() {
/* 75 */     return this.sequenceId;
/*    */   }
/*    */   
/*    */   private static String createErrorMessage(String sequenceId) {
/* 79 */     return LocalizationMessages.WSRM_1126_DUPLICATE_SEQUENCE_ID(sequenceId);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\DuplicateSequenceException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */