/*    */ package com.sun.xml.rpc.streaming;
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
/*    */ public final class ElementIdStack
/*    */ {
/*    */   private int[] _values;
/*    */   private int _tos;
/*    */   private int _nextElementId;
/*    */   private static final int INITIAL_SIZE = 32;
/*    */   
/*    */   public ElementIdStack() {
/* 37 */     this(32);
/*    */   }
/*    */   
/*    */   public ElementIdStack(int size) {
/* 41 */     this._values = new int[size];
/* 42 */     reset();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 46 */     this._tos = 0;
/* 47 */     this._nextElementId = 1;
/*    */   }
/*    */   
/*    */   public int getCurrent() {
/* 51 */     return this._values[this._tos - 1];
/*    */   }
/*    */   
/*    */   public int pushNext() {
/* 55 */     ensureCapacity();
/* 56 */     this._values[this._tos++] = this._nextElementId;
/* 57 */     return this._nextElementId++;
/*    */   }
/*    */   
/*    */   public int pop() {
/* 61 */     this._tos--;
/* 62 */     return this._values[this._tos];
/*    */   }
/*    */   
/*    */   private void ensureCapacity() {
/* 66 */     if (this._tos >= this._values.length) {
/* 67 */       int[] newValues = new int[this._values.length * 2];
/* 68 */       System.arraycopy(this._values, 0, newValues, 0, this._values.length);
/* 69 */       this._values = newValues;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\ElementIdStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */