/*     */ package org.glassfish.ha.store.criteria;
/*     */ 
/*     */ import org.glassfish.ha.store.criteria.spi.AttributeAccessNode;
/*     */ import org.glassfish.ha.store.criteria.spi.ExpressionNode;
/*     */ import org.glassfish.ha.store.criteria.spi.LiteralNode;
/*     */ import org.glassfish.ha.store.criteria.spi.LogicalExpressionNode;
/*     */ import org.glassfish.ha.store.criteria.spi.Opcode;
/*     */ import org.glassfish.ha.store.spi.AttributeMetadata;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExpressionBuilder<V>
/*     */ {
/*     */   Class<V> entryClazz;
/*     */   
/*     */   public ExpressionBuilder(Class<V> entryClazz) {
/*  57 */     this.entryClazz = entryClazz;
/*     */   }
/*     */   
/*     */   public Criteria<V> setCriteria(Expression<Boolean> expr) {
/*  61 */     Criteria<V> c = new Criteria<V>(this.entryClazz);
/*  62 */     c.setExpression(expr);
/*     */     
/*  64 */     return c;
/*     */   }
/*     */   
/*     */   public <T> AttributeAccessNode<V, T> attr(AttributeMetadata<V, T> meta) {
/*  68 */     return new AttributeAccessNode(meta);
/*     */   }
/*     */   
/*     */   public <T> LiteralNode<T> literal(Class<T> type, T value) {
/*  72 */     return new LiteralNode(type, value);
/*     */   }
/*     */   
/*     */   public <T> LogicalExpressionNode eq(T value, AttributeMetadata<V, T> meta) {
/*  76 */     return new LogicalExpressionNode(Opcode.EQ, (ExpressionNode)new LiteralNode(meta.getAttributeType(), value), (ExpressionNode)new AttributeAccessNode(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> LogicalExpressionNode eq(AttributeMetadata<V, T> meta, T value) {
/*  82 */     return new LogicalExpressionNode(Opcode.EQ, (ExpressionNode)new AttributeAccessNode(meta), (ExpressionNode)new LiteralNode(meta.getAttributeType(), value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> LogicalExpressionNode eq(AttributeMetadata<V, T> meta1, AttributeMetadata<V, T> meta2) {
/*  89 */     return new LogicalExpressionNode(Opcode.EQ, (ExpressionNode)new AttributeAccessNode(meta1), (ExpressionNode)new AttributeAccessNode(meta2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> LogicalExpressionNode eq(ExpressionNode<T> expr1, ExpressionNode<T> expr2) {
/*  95 */     return new LogicalExpressionNode(Opcode.EQ, expr1, expr2);
/*     */   }
/*     */   
/*     */   public <T extends Number> LogicalExpressionNode eq(LiteralNode<T> value, AttributeMetadata<V, T> meta) {
/*  99 */     return new LogicalExpressionNode(Opcode.EQ, (ExpressionNode)value, (ExpressionNode)new AttributeAccessNode(meta));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Number> LogicalExpressionNode eq(AttributeMetadata<V, T> meta, LiteralNode<T> value) {
/* 104 */     return new LogicalExpressionNode(Opcode.EQ, (ExpressionNode)new AttributeAccessNode(meta), (ExpressionNode)value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\criteria\ExpressionBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */