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
/*    */ public class InExpressionNode<T>
/*    */   extends LogicalExpressionNode
/*    */ {
/*    */   Collection<? extends T> entries;
/*    */   
/*    */   public InExpressionNode(ExpressionNode<T> value, Collection<? extends T> entries) {
/* 58 */     super(Opcode.IN, value, null);
/* 59 */     this.entries = entries;
/*    */   }
/*    */   
/*    */   public Collection<? extends T> getEntries() {
/* 63 */     return this.entries;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\criteria\spi\InExpressionNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */