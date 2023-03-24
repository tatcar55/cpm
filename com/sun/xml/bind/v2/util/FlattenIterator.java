/*    */ package com.sun.xml.bind.v2.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
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
/*    */ public final class FlattenIterator<T>
/*    */   implements Iterator<T>
/*    */ {
/*    */   private final Iterator<? extends Map<?, ? extends T>> parent;
/* 56 */   private Iterator<? extends T> child = null;
/*    */   private T next;
/*    */   
/*    */   public FlattenIterator(Iterable<? extends Map<?, ? extends T>> core) {
/* 60 */     this.parent = core.iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() {
/* 65 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 69 */     getNext();
/* 70 */     return (this.next != null);
/*    */   }
/*    */   
/*    */   public T next() {
/* 74 */     T r = this.next;
/* 75 */     this.next = null;
/* 76 */     if (r == null)
/* 77 */       throw new NoSuchElementException(); 
/* 78 */     return r;
/*    */   }
/*    */   
/*    */   private void getNext() {
/* 82 */     if (this.next != null)
/*    */       return; 
/* 84 */     if (this.child != null && this.child.hasNext()) {
/* 85 */       this.next = this.child.next();
/*    */       
/*    */       return;
/*    */     } 
/* 89 */     if (this.parent.hasNext()) {
/* 90 */       this.child = ((Map)this.parent.next()).values().iterator();
/* 91 */       getNext();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v\\util\FlattenIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */