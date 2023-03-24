/*    */ package com.sun.xml.ws.client.sei;
/*    */ 
/*    */ import com.sun.xml.ws.model.ParameterImpl;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ValueSetterFactory
/*    */ {
/* 56 */   public static final ValueSetterFactory SYNC = new ValueSetterFactory() {
/*    */       public ValueSetter get(ParameterImpl p) {
/* 58 */         return ValueSetter.getSync(p);
/*    */       }
/*    */     };
/*    */   
/* 62 */   public static final ValueSetterFactory NONE = new ValueSetterFactory() {
/*    */       public ValueSetter get(ParameterImpl p) {
/* 64 */         throw new WebServiceException("This shouldn't happen. No response parameters.");
/*    */       }
/*    */     };
/*    */   
/* 68 */   public static final ValueSetterFactory SINGLE = new ValueSetterFactory() {
/*    */       public ValueSetter get(ParameterImpl p) {
/* 70 */         return ValueSetter.SINGLE_VALUE;
/*    */       }
/*    */     };
/*    */   
/*    */   public abstract ValueSetter get(ParameterImpl paramParameterImpl);
/*    */   
/*    */   public static final class AsyncBeanValueSetterFactory extends ValueSetterFactory {
/*    */     public AsyncBeanValueSetterFactory(Class asyncBean) {
/* 78 */       this.asyncBean = asyncBean;
/*    */     }
/*    */     private Class asyncBean;
/*    */     public ValueSetter get(ParameterImpl p) {
/* 82 */       return new ValueSetter.AsyncBeanValueSetter(p, this.asyncBean);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\ValueSetterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */