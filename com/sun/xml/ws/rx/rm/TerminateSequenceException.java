/*    */ package com.sun.xml.ws.rx.rm;
/*    */ 
/*    */ import com.sun.xml.ws.rx.RxException;
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
/*    */ public class TerminateSequenceException
/*    */   extends RxException
/*    */ {
/*    */   private static final long serialVersionUID = -6941741820196934499L;
/*    */   private String sequenceId;
/*    */   
/*    */   public TerminateSequenceException(String message) {
/* 59 */     super(message);
/*    */   }
/*    */   
/*    */   public TerminateSequenceException(String message, String id) {
/* 63 */     super(message);
/* 64 */     this.sequenceId = id;
/*    */   }
/*    */   
/*    */   public TerminateSequenceException(String message, Throwable cause) {
/* 68 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public String getSequenceId() {
/* 72 */     return this.sequenceId;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\TerminateSequenceException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */