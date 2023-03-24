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
/*    */ 
/*    */ 
/*    */ public class BinaryExpressionNode<T>
/*    */   extends ExpressionNode<T>
/*    */ {
/*    */   private ExpressionNode<T> left;
/*    */   private ExpressionNode<T> right;
/*    */   
/*    */   public BinaryExpressionNode(Opcode opcode, Class<T> returnType, ExpressionNode<T> left) {
/* 58 */     this(opcode, returnType, left, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public BinaryExpressionNode(Opcode opcode, Class<T> returnType, ExpressionNode<T> left, ExpressionNode<T> right) {
/* 63 */     super(opcode, returnType);
/* 64 */     this.left = left;
/* 65 */     this.right = right;
/*    */   }
/*    */   
/*    */   public ExpressionNode<T> getLeft() {
/* 69 */     return this.left;
/*    */   }
/*    */   
/*    */   public ExpressionNode<T> getRight() {
/* 73 */     return this.right;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\criteria\spi\BinaryExpressionNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */