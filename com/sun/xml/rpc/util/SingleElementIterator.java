/*    */ package com.sun.xml.rpc.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
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
/*    */ public class SingleElementIterator
/*    */   implements Iterator
/*    */ {
/*    */   protected boolean hasNext = false;
/*    */   protected Object element;
/*    */   
/*    */   public SingleElementIterator() {}
/*    */   
/*    */   public SingleElementIterator(Object element) {
/* 47 */     this.element = element;
/* 48 */     this.hasNext = true;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 52 */     return this.hasNext;
/*    */   }
/*    */   
/*    */   public Object next() throws NoSuchElementException {
/* 56 */     if (!this.hasNext) {
/* 57 */       throw new NoSuchElementException("No elements left in SingleElementIterator next()");
/*    */     }
/* 59 */     this.hasNext = false;
/* 60 */     return this.element;
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() throws UnsupportedOperationException, IllegalStateException {
/* 65 */     throw new UnsupportedOperationException("SingleElementIterator does not support remove()");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\SingleElementIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */