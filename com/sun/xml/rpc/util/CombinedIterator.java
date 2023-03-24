/*    */ package com.sun.xml.rpc.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CombinedIterator
/*    */   implements Iterator
/*    */ {
/*    */   protected Iterator currentIterator;
/*    */   protected Iterator secondIterator;
/*    */   
/*    */   public CombinedIterator(Iterator firstIterator, Iterator secondIterator) {
/* 42 */     this.currentIterator = firstIterator;
/* 43 */     this.secondIterator = secondIterator;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 47 */     if (!this.currentIterator.hasNext()) {
/* 48 */       this.currentIterator = this.secondIterator;
/*    */     }
/* 50 */     return this.currentIterator.hasNext();
/*    */   }
/*    */   
/*    */   public Object next() {
/* 54 */     if (!this.currentIterator.hasNext()) {
/* 55 */       this.currentIterator = this.secondIterator;
/*    */     }
/* 57 */     return this.currentIterator.next();
/*    */   }
/*    */   
/*    */   public void remove() {
/* 61 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\CombinedIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */