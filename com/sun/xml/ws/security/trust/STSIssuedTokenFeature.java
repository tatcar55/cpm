/*    */ package com.sun.xml.ws.security.trust;
/*    */ 
/*    */ import com.sun.xml.ws.api.FeatureConstructor;
/*    */ import com.sun.xml.ws.api.security.trust.client.STSIssuedTokenConfiguration;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ import org.glassfish.gmbal.ManagedAttribute;
/*    */ import org.glassfish.gmbal.ManagedData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ManagedData
/*    */ public class STSIssuedTokenFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   public static final String ID = "com.sun.xml.ws.security.trust.STSIssuedTokenFeature";
/*    */   private final STSIssuedTokenConfiguration stsIssuedTokenConfig;
/*    */   
/*    */   @FeatureConstructor({"stsIssuedTokenConfig"})
/*    */   public STSIssuedTokenFeature(STSIssuedTokenConfiguration stsIssuedTokenConfig) {
/* 61 */     this.enabled = true;
/* 62 */     this.stsIssuedTokenConfig = stsIssuedTokenConfig;
/*    */   }
/*    */ 
/*    */   
/*    */   @ManagedAttribute
/*    */   public String getID() {
/* 68 */     return "com.sun.xml.ws.security.trust.STSIssuedTokenFeature";
/*    */   }
/*    */   
/*    */   @ManagedAttribute
/*    */   public STSIssuedTokenConfiguration getSTSIssuedTokenConfiguration() {
/* 73 */     return this.stsIssuedTokenConfig;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\STSIssuedTokenFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */