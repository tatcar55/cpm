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
/*    */ public final class NullIterator
/*    */   implements Iterator
/*    */ {
/*    */   public static NullIterator getInstance() {
/* 40 */     return _instance;
/*    */   }
/*    */   
/* 43 */   private static final NullIterator _instance = new NullIterator();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 49 */     return false;
/*    */   }
/*    */   
/*    */   public Object next() {
/* 53 */     throw new NoSuchElementException();
/*    */   }
/*    */   
/*    */   public void remove() {
/* 57 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\NullIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */