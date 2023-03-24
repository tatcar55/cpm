/*    */ package com.sun.xml.ws.developer;
/*    */ 
/*    */ import com.sun.xml.ws.api.FeatureConstructor;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SerializationFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   public static final String ID = "http://jax-ws.java.net/features/serialization";
/*    */   private final String encoding;
/*    */   
/*    */   public SerializationFeature() {
/* 62 */     this("");
/*    */   }
/*    */   
/*    */   @FeatureConstructor({"encoding"})
/*    */   public SerializationFeature(String encoding) {
/* 67 */     this.encoding = encoding;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 71 */     return "http://jax-ws.java.net/features/serialization";
/*    */   }
/*    */   
/*    */   public String getEncoding() {
/* 75 */     return this.encoding;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\SerializationFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */