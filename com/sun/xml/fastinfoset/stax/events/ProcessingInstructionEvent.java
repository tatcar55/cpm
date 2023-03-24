/*    */ package com.sun.xml.fastinfoset.stax.events;
/*    */ 
/*    */ import javax.xml.stream.events.ProcessingInstruction;
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
/*    */ public class ProcessingInstructionEvent
/*    */   extends EventBase
/*    */   implements ProcessingInstruction
/*    */ {
/*    */   private String targetName;
/*    */   private String _data;
/*    */   
/*    */   public ProcessingInstructionEvent() {
/* 30 */     init();
/*    */   }
/*    */   
/*    */   public ProcessingInstructionEvent(String targetName, String data) {
/* 34 */     this.targetName = targetName;
/* 35 */     this._data = data;
/* 36 */     init();
/*    */   }
/*    */   
/*    */   protected void init() {
/* 40 */     setEventType(3);
/*    */   }
/*    */   
/*    */   public String getTarget() {
/* 44 */     return this.targetName;
/*    */   }
/*    */   
/*    */   public void setTarget(String targetName) {
/* 48 */     this.targetName = targetName;
/*    */   }
/*    */   
/*    */   public void setData(String data) {
/* 52 */     this._data = data;
/*    */   }
/*    */   
/*    */   public String getData() {
/* 56 */     return this._data;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 60 */     if (this._data != null && this.targetName != null)
/* 61 */       return "<?" + this.targetName + " " + this._data + "?>"; 
/* 62 */     if (this.targetName != null)
/* 63 */       return "<?" + this.targetName + "?>"; 
/* 64 */     if (this._data != null) {
/* 65 */       return "<?" + this._data + "?>";
/*    */     }
/* 67 */     return "<??>";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\ProcessingInstructionEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */