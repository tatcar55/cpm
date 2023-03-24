/*    */ package org.glassfish.ha.store.criteria.spi;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.glassfish.ha.store.spi.AttributeMetadata;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AttributeAccessNode<V, T>
/*    */   extends ExpressionNode<T>
/*    */ {
/*    */   private AttributeMetadata<V, T> attr;
/*    */   
/*    */   public AttributeAccessNode(AttributeMetadata<V, T> attr) {
/* 62 */     super(Opcode.ATTR, attr.getAttributeType());
/* 63 */     this.attr = attr;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AttributeMetadata<V, T> getAttributeMetadata() {
/* 72 */     return this.attr;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LogicalExpressionNode in(Collection<? extends T> entries) {
/* 82 */     return new InExpressionNode<V>(this, entries);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\criteria\spi\AttributeAccessNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */