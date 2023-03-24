/*    */ package com.sun.xml.fastinfoset.stax.events;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.CommonResourceBundle;
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
/*    */ public class ReadIterator
/*    */   implements Iterator
/*    */ {
/* 25 */   Iterator iterator = EmptyIterator.getInstance();
/*    */ 
/*    */   
/*    */   public ReadIterator() {}
/*    */   
/*    */   public ReadIterator(Iterator iterator) {
/* 31 */     if (iterator != null) {
/* 32 */       this.iterator = iterator;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 37 */     return this.iterator.hasNext();
/*    */   }
/*    */   
/*    */   public Object next() {
/* 41 */     return this.iterator.next();
/*    */   }
/*    */   
/*    */   public void remove() {
/* 45 */     throw new UnsupportedOperationException(CommonResourceBundle.getInstance().getString("message.readonlyList"));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\ReadIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */