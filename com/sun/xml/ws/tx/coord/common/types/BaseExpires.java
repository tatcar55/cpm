/*    */ package com.sun.xml.ws.tx.coord.common.types;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BaseExpires<T>
/*    */ {
/*    */   protected T delegate;
/*    */   
/*    */   protected BaseExpires(T delegate) {
/* 68 */     this.delegate = delegate;
/*    */   }
/*    */   
/*    */   public T getDelegate() {
/* 72 */     return this.delegate;
/*    */   }
/*    */   
/*    */   public abstract long getValue();
/*    */   
/*    */   public abstract void setValue(long paramLong);
/*    */   
/*    */   public abstract Map<QName, String> getOtherAttributes();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\types\BaseExpires.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */