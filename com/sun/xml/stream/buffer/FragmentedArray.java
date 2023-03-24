/*    */ package com.sun.xml.stream.buffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class FragmentedArray<T>
/*    */ {
/*    */   protected T _item;
/*    */   protected FragmentedArray<T> _next;
/*    */   protected FragmentedArray<T> _previous;
/*    */   
/*    */   FragmentedArray(T item) {
/* 49 */     this(item, null);
/*    */   }
/*    */   
/*    */   FragmentedArray(T item, FragmentedArray<T> previous) {
/* 53 */     setArray(item);
/* 54 */     if (previous != null) {
/* 55 */       previous._next = this;
/* 56 */       this._previous = previous;
/*    */     } 
/*    */   }
/*    */   
/*    */   T getArray() {
/* 61 */     return this._item;
/*    */   }
/*    */   
/*    */   void setArray(T item) {
/* 65 */     assert item.getClass().isArray();
/*    */     
/* 67 */     this._item = item;
/*    */   }
/*    */   
/*    */   FragmentedArray<T> getNext() {
/* 71 */     return this._next;
/*    */   }
/*    */   
/*    */   void setNext(FragmentedArray<T> next) {
/* 75 */     this._next = next;
/* 76 */     if (next != null) {
/* 77 */       next._previous = this;
/*    */     }
/*    */   }
/*    */   
/*    */   FragmentedArray<T> getPrevious() {
/* 82 */     return this._previous;
/*    */   }
/*    */   
/*    */   void setPrevious(FragmentedArray<T> previous) {
/* 86 */     this._previous = previous;
/* 87 */     if (previous != null)
/* 88 */       previous._next = this; 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\FragmentedArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */