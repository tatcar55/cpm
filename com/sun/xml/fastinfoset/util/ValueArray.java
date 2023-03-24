/*    */ package com.sun.xml.fastinfoset.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ValueArray
/*    */ {
/*    */   public static final int DEFAULT_CAPACITY = 10;
/*    */   public static final int MAXIMUM_CAPACITY = 2147483647;
/*    */   protected int _size;
/*    */   protected int _readOnlyArraySize;
/*    */   protected int _maximumCapacity;
/*    */   
/*    */   public int getSize() {
/* 31 */     return this._size;
/*    */   }
/*    */   
/*    */   public int getMaximumCapacity() {
/* 35 */     return this._maximumCapacity;
/*    */   }
/*    */   
/*    */   public void setMaximumCapacity(int maximumCapacity) {
/* 39 */     this._maximumCapacity = maximumCapacity;
/*    */   }
/*    */   
/*    */   public abstract void setReadOnlyArray(ValueArray paramValueArray, boolean paramBoolean);
/*    */   
/*    */   public abstract void clear();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\ValueArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */