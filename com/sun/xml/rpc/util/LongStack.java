/*    */ package com.sun.xml.rpc.util;
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
/*    */ public final class LongStack
/*    */ {
/* 33 */   private long[] values = null;
/* 34 */   private int topOfStack = 0;
/*    */   
/*    */   public LongStack() {
/* 37 */     this(32);
/*    */   }
/*    */   
/*    */   public LongStack(int size) {
/* 41 */     this.values = new long[size];
/*    */   }
/*    */   
/*    */   public void push(long newValue) {
/* 45 */     resize();
/* 46 */     this.values[this.topOfStack] = newValue;
/* 47 */     this.topOfStack++;
/*    */   }
/*    */   
/*    */   public long pop() {
/* 51 */     this.topOfStack--;
/* 52 */     return this.values[this.topOfStack];
/*    */   }
/*    */   
/*    */   public long peek() {
/* 56 */     return this.values[this.topOfStack - 1];
/*    */   }
/*    */   
/*    */   private void resize() {
/* 60 */     if (this.topOfStack >= this.values.length) {
/* 61 */       long[] newValues = new long[this.values.length * 2];
/* 62 */       System.arraycopy(this.values, 0, newValues, 0, this.values.length);
/* 63 */       this.values = newValues;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\LongStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */