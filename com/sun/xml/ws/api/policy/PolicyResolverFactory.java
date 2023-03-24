/*    */ package com.sun.xml.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.jaxws.DefaultPolicyResolver;
/*    */ import com.sun.xml.ws.util.ServiceFinder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PolicyResolverFactory
/*    */ {
/*    */   public abstract PolicyResolver doCreate();
/*    */   
/*    */   public static PolicyResolver create() {
/* 61 */     for (PolicyResolverFactory factory : ServiceFinder.find(PolicyResolverFactory.class)) {
/* 62 */       PolicyResolver policyResolver = factory.doCreate();
/* 63 */       if (policyResolver != null) {
/* 64 */         return policyResolver;
/*    */       }
/*    */     } 
/*    */     
/* 68 */     return DEFAULT_POLICY_RESOLVER;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 76 */   public static final PolicyResolver DEFAULT_POLICY_RESOLVER = (PolicyResolver)new DefaultPolicyResolver();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\policy\PolicyResolverFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */