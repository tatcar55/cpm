/*    */ package org.glassfish.ha.store.criteria.spi;
/*    */ 
/*    */ import org.glassfish.ha.store.criteria.Expression;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ExpressionNode<T>
/*    */   implements Expression<T>
/*    */ {
/*    */   private Opcode opcode;
/*    */   protected Class<T> returnType;
/*    */   
/*    */   public ExpressionNode(Opcode opcode, Class<T> returnType) {
/* 60 */     this.opcode = opcode;
/* 61 */     this.returnType = returnType;
/*    */   }
/*    */   
/*    */   public Opcode getOpcode() {
/* 65 */     return this.opcode;
/*    */   }
/*    */   
/*    */   public Class<T> getReturnType() {
/* 69 */     return this.returnType;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\criteria\spi\ExpressionNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */