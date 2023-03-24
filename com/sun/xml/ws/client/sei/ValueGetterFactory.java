/*    */ package com.sun.xml.ws.client.sei;
/*    */ 
/*    */ import com.sun.xml.ws.model.ParameterImpl;
/*    */ import javax.jws.WebParam;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class ValueGetterFactory
/*    */ {
/* 56 */   static final ValueGetterFactory SYNC = new ValueGetterFactory() {
/*    */       ValueGetter get(ParameterImpl p) {
/* 58 */         return (p.getMode() == WebParam.Mode.IN || p.getIndex() == -1) ? ValueGetter.PLAIN : ValueGetter.HOLDER;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   static final ValueGetterFactory ASYNC = new ValueGetterFactory() {
/*    */       ValueGetter get(ParameterImpl p) {
/* 69 */         return ValueGetter.PLAIN;
/*    */       }
/*    */     };
/*    */   
/*    */   abstract ValueGetter get(ParameterImpl paramParameterImpl);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\ValueGetterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */