/*    */ package com.sun.xml.rpc.client;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.xml.rpc.JAXRPCException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContentNegotiationProperties
/*    */ {
/*    */   public static void initFromSystemProperties(Map<String, String> props) throws JAXRPCException {
/* 38 */     String value = System.getProperty("com.sun.xml.rpc.client.ContentNegotiation");
/*    */ 
/*    */     
/* 41 */     if (value == null) {
/* 42 */       props.put("com.sun.xml.rpc.client.ContentNegotiation", "none");
/*    */     
/*    */     }
/* 45 */     else if (value.equals("none") || value.equals("pessimistic") || value.equals("optimistic")) {
/*    */       
/* 47 */       props.put("com.sun.xml.rpc.client.ContentNegotiation", value.intern());
/*    */     }
/*    */     else {
/*    */       
/* 51 */       throw new JAXRPCException("Illegal value '" + value + "' for property " + "com.sun.xml.rpc.client.ContentNegotiation");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\ContentNegotiationProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */