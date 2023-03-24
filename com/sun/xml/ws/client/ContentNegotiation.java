/*    */ package com.sun.xml.ws.client;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum ContentNegotiation
/*    */ {
/* 64 */   none,
/* 65 */   pessimistic,
/* 66 */   optimistic;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final String PROPERTY = "com.sun.xml.ws.client.ContentNegotiation";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ContentNegotiation obtainFromSystemProperty() {
/*    */     try {
/* 82 */       String value = System.getProperty("com.sun.xml.ws.client.ContentNegotiation");
/*    */       
/* 84 */       if (value == null) {
/* 85 */         return none;
/*    */       }
/*    */       
/* 88 */       return valueOf(value);
/* 89 */     } catch (Exception e) {
/*    */ 
/*    */       
/* 92 */       return none;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\ContentNegotiation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */