/*    */ package com.sun.xml.fastinfoset.stax.events;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.CommonResourceBundle;
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
/*    */ public class EmptyIterator
/*    */   implements Iterator
/*    */ {
/* 25 */   public static final EmptyIterator instance = new EmptyIterator();
/*    */ 
/*    */ 
/*    */   
/*    */   public static EmptyIterator getInstance() {
/* 30 */     return instance;
/*    */   }
/*    */   public boolean hasNext() {
/* 33 */     return false;
/*    */   }
/*    */   public Object next() throws NoSuchElementException {
/* 36 */     throw new NoSuchElementException();
/*    */   }
/*    */   public void remove() {
/* 39 */     throw new UnsupportedOperationException(CommonResourceBundle.getInstance().getString("message.emptyIterator"));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\EmptyIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */