/*    */ package com.sun.xml.rpc.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public abstract class CachedStack
/*    */ {
/* 36 */   protected List elements = new ArrayList();
/* 37 */   protected int topOfStack = -1;
/*    */   
/*    */   protected abstract Object createObject() throws Exception;
/*    */   
/*    */   public void push() throws Exception {
/* 42 */     this.topOfStack++;
/* 43 */     if (this.elements.size() == this.topOfStack) {
/* 44 */       this.elements.add(this.topOfStack, createObject());
/*    */     }
/*    */   }
/*    */   
/*    */   public void pop() {
/* 49 */     if (this.topOfStack < 0) {
/* 50 */       throw new ArrayIndexOutOfBoundsException(this.topOfStack);
/*    */     }
/* 52 */     this.topOfStack--;
/*    */   }
/*    */   
/*    */   public Object peek() {
/* 56 */     if (this.topOfStack == -1) {
/* 57 */       return null;
/*    */     }
/* 59 */     return this.elements.get(this.topOfStack);
/*    */   }
/*    */   
/*    */   public int depth() {
/* 63 */     return this.topOfStack + 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\CachedStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */