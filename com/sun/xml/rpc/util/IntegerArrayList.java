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
/*    */ public final class IntegerArrayList
/*    */ {
/* 33 */   private int[] values = null;
/* 34 */   private int length = 0;
/*    */   
/*    */   public IntegerArrayList() {
/* 37 */     this(8);
/*    */   }
/*    */   
/*    */   public IntegerArrayList(int size) {
/* 41 */     this.values = new int[size];
/*    */   }
/*    */   
/*    */   public boolean add(int value) {
/* 45 */     resize();
/* 46 */     this.values[this.length++] = value;
/*    */     
/* 48 */     return true;
/*    */   }
/*    */   
/*    */   public int get(int index) {
/* 52 */     return this.values[index];
/*    */   }
/*    */   
/*    */   public void clear() {
/* 56 */     this.values = new int[this.length];
/* 57 */     this.length = 0;
/*    */   }
/*    */   
/*    */   public int[] toArray() {
/* 61 */     int[] array = new int[this.length];
/* 62 */     System.arraycopy(this.values, 0, array, 0, this.length);
/* 63 */     return array;
/*    */   }
/*    */   
/*    */   public int size() {
/* 67 */     return this.length;
/*    */   }
/*    */   
/*    */   private void resize() {
/* 71 */     if (this.length >= this.values.length) {
/* 72 */       int[] newValues = new int[this.values.length * 2];
/* 73 */       System.arraycopy(this.values, 0, newValues, 0, this.values.length);
/* 74 */       this.values = newValues;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\IntegerArrayList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */