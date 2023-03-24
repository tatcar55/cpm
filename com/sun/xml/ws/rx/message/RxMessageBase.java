/*    */ package com.sun.xml.ws.rx.message;
/*    */ 
/*    */ import com.sun.istack.NotNull;
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
/*    */ public abstract class RxMessageBase
/*    */   implements RxMessage
/*    */ {
/*    */   @NotNull
/*    */   private final String correlationId;
/*    */   
/*    */   public RxMessageBase(@NotNull String correlationId) {
/* 56 */     if (correlationId == null) {
/* 57 */       throw new NullPointerException("correlationId initialization parameter must not be 'null'");
/*    */     }
/* 59 */     this.correlationId = correlationId;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String getCorrelationId() {
/* 64 */     return this.correlationId;
/*    */   }
/*    */   
/*    */   public byte[] toBytes() {
/* 68 */     return new byte[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\message\RxMessageBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */