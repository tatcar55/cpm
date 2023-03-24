/*    */ package org.glassfish.gmbal.generic;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface NullaryFunction<T>
/*    */ {
/*    */   T evaluate();
/*    */   
/*    */   public static class Factory
/*    */   {
/*    */     public static <T> NullaryFunction<T> makeConstant(final T value) {
/* 50 */       return new NullaryFunction<T>() {
/*    */           public T evaluate() {
/* 52 */             return (T)value;
/*    */           }
/*    */         };
/*    */     }
/*    */ 
/*    */     
/*    */     public static <T> NullaryFunction<T> makeFuture(final NullaryFunction<T> closure) {
/* 59 */       return new NullaryFunction<T>()
/*    */         {
/*    */           private boolean evaluated = false;
/*    */           private T value;
/*    */           
/*    */           public synchronized T evaluate() {
/* 65 */             if (!this.evaluated) {
/* 66 */               this.evaluated = true;
/* 67 */               this.value = closure.evaluate();
/*    */             } 
/*    */             
/* 70 */             return this.value;
/*    */           }
/*    */         };
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\NullaryFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */