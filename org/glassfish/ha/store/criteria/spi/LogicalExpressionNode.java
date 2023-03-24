/*    */ package org.glassfish.ha.store.criteria.spi;
/*    */ 
/*    */ import java.util.Collection;
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
/*    */ public class LogicalExpressionNode
/*    */   extends BinaryExpressionNode<Boolean>
/*    */ {
/*    */   Collection entries;
/*    */   
/*    */   public LogicalExpressionNode(Opcode opcode, ExpressionNode<Boolean> left, ExpressionNode<Boolean> right) {
/* 58 */     super(opcode, Boolean.class, left, right);
/*    */   }
/*    */   
/*    */   public LogicalExpressionNode and(LogicalExpressionNode expr) {
/* 62 */     return new LogicalExpressionNode(Opcode.AND, this, expr);
/*    */   }
/*    */   
/*    */   public LogicalExpressionNode or(LogicalExpressionNode expr) {
/* 66 */     return new LogicalExpressionNode(Opcode.OR, this, expr);
/*    */   }
/*    */   
/*    */   public LogicalExpressionNode isTrue() {
/* 70 */     return new LogicalExpressionNode(Opcode.EQ, this, new LiteralNode<Boolean>(Boolean.class, Boolean.valueOf(true)));
/*    */   }
/*    */   
/*    */   public LogicalExpressionNode eq(boolean value) {
/* 74 */     return new LogicalExpressionNode(Opcode.EQ, this, new LiteralNode<Boolean>(Boolean.class, Boolean.valueOf(value)));
/*    */   }
/*    */   
/*    */   public LogicalExpressionNode isNotTrue() {
/* 78 */     return new LogicalExpressionNode(Opcode.EQ, this, new LiteralNode<Boolean>(Boolean.class, Boolean.valueOf(true)));
/*    */   }
/*    */   
/*    */   public LogicalExpressionNode neq(boolean value) {
/* 82 */     return new LogicalExpressionNode(Opcode.NEQ, this, new LiteralNode<Boolean>(Boolean.class, Boolean.valueOf(value)));
/*    */   }
/*    */   
/*    */   public Class<Boolean> getReturnType() {
/* 86 */     return Boolean.class;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\criteria\spi\LogicalExpressionNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */