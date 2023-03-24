/*    */ package com.sun.xml.wss.impl.policy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DynamicSecurityPolicy
/*    */   implements SecurityPolicy
/*    */ {
/*    */   StaticPolicyContext ctx;
/*    */   
/*    */   public DynamicSecurityPolicy() {}
/*    */   
/*    */   public DynamicSecurityPolicy(StaticPolicyContext ctx) {
/* 70 */     this.ctx = ctx;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StaticPolicyContext getStaticPolicyContext() {
/* 77 */     return this.ctx;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setStaticPolicyContext(StaticPolicyContext ctx) {
/* 85 */     this.ctx = ctx;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract SecurityPolicyGenerator policyGenerator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getType() {
/* 99 */     return "DynamicSecurityPolicy";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\DynamicSecurityPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */