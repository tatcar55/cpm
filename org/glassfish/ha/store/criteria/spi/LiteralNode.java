/*    */ package org.glassfish.ha.store.criteria.spi;
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
/*    */ 
/*    */ public class LiteralNode<T>
/*    */   extends ExpressionNode<T>
/*    */ {
/*    */   private T value;
/*    */   
/*    */   public LiteralNode(Class<T> clazz, T t) {
/* 55 */     super(Opcode.LITERAL, clazz);
/* 56 */     this.value = t;
/*    */   }
/*    */   
/*    */   public T getValue() {
/* 60 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\criteria\spi\LiteralNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */