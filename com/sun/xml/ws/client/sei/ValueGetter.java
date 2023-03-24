/*    */ package com.sun.xml.ws.client.sei;
/*    */ 
/*    */ import javax.xml.ws.Holder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ enum ValueGetter
/*    */ {
/* 66 */   PLAIN {
/*    */     Object get(Object parameter) {
/* 68 */       return parameter;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   HOLDER {
/*    */     Object get(Object parameter) {
/* 81 */       if (parameter == null)
/*    */       {
/* 83 */         return null; } 
/* 84 */       return ((Holder)parameter).value;
/*    */     }
/*    */   };
/*    */   
/*    */   abstract Object get(Object paramObject);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\ValueGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */