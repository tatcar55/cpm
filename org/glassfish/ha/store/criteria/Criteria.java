/*    */ package org.glassfish.ha.store.criteria;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Criteria<V>
/*    */ {
/*    */   private Class<V> entryClazz;
/*    */   private Expression<Boolean> expression;
/*    */   
/*    */   Criteria(Class<V> entryClazz) {
/* 60 */     this.entryClazz = entryClazz;
/*    */   }
/*    */   
/*    */   public Expression<Boolean> getExpression() {
/* 64 */     return this.expression;
/*    */   }
/*    */   
/*    */   public void setExpression(Expression<Boolean> expression) {
/* 68 */     this.expression = expression;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\criteria\Criteria.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */