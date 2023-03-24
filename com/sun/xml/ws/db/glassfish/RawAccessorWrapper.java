/*    */ package com.sun.xml.ws.db.glassfish;
/*    */ 
/*    */ import com.sun.xml.bind.api.AccessorException;
/*    */ import com.sun.xml.bind.api.RawAccessor;
/*    */ import com.sun.xml.ws.spi.db.DatabindingException;
/*    */ import com.sun.xml.ws.spi.db.PropertyAccessor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RawAccessorWrapper
/*    */   implements PropertyAccessor
/*    */ {
/*    */   private RawAccessor accessor;
/*    */   
/*    */   public RawAccessorWrapper(RawAccessor a) {
/* 54 */     this.accessor = a;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 59 */     return this.accessor.equals(obj);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object get(Object bean) throws DatabindingException {
/*    */     try {
/* 65 */       return this.accessor.get(bean);
/* 66 */     } catch (AccessorException e) {
/* 67 */       throw new DatabindingException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 73 */     return this.accessor.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(Object bean, Object value) throws DatabindingException {
/*    */     try {
/* 79 */       this.accessor.set(bean, value);
/* 80 */     } catch (AccessorException e) {
/* 81 */       throw new DatabindingException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return this.accessor.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\db\glassfish\RawAccessorWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */