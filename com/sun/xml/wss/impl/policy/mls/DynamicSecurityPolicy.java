/*    */ package com.sun.xml.wss.impl.policy.mls;
/*    */ 
/*    */ import com.sun.xml.wss.impl.policy.DynamicSecurityPolicy;
/*    */ import com.sun.xml.wss.impl.policy.SecurityPolicyGenerator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynamicSecurityPolicy
/*    */   extends DynamicSecurityPolicy
/*    */ {
/*    */   public SecurityPolicyGenerator policyGenerator() {
/* 63 */     return new WSSPolicyGenerator();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\DynamicSecurityPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */