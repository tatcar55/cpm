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
/*    */ public class CloseSequenceException
/*    */   extends RxException
/*    */ {
/*    */   private static final long serialVersionUID = 6938882497563905280L;
/*    */   private String sequenceId;
/*    */   
/*    */   public CloseSequenceException(String message) {
/* 57 */     super(message);
/*    */   }
/*    */   
/*    */   public CloseSequenceException(String message, String id) {
/* 61 */     super(message);
/* 62 */     this.sequenceId = id;
/*    */   }
/*    */   
/*    */   public String getSequenceId() {
/* 66 */     return this.sequenceId;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\CloseSequenceException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */