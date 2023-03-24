/*    */ package com.sun.xml.ws.policy.config;
/*    */ 
/*    */ import com.sun.xml.ws.api.FeatureConstructor;
/*    */ import com.sun.xml.ws.policy.PolicyMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ManagedData
/*    */ public class PolicyFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   public static final String ID = "com.sun.xml.ws.policy.PolicyFeature";
/*    */   private final PolicyMap policyMap;
/*    */   
/*    */   @FeatureConstructor({"policyMap"})
/*    */   public PolicyFeature(PolicyMap policyMap) {
/* 67 */     this.enabled = true;
/* 68 */     this.policyMap = policyMap;
/*    */   }
/*    */ 
/*    */   
/*    */   @ManagedAttribute
/*    */   public String getID() {
/* 74 */     return "com.sun.xml.ws.policy.PolicyFeature";
/*    */   }
/*    */   
/*    */   @ManagedAttribute
/*    */   public PolicyMap getPolicyMap() {
/* 79 */     return this.policyMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\config\PolicyFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */