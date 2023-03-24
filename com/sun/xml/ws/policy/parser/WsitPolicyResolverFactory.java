/*    */ package com.sun.xml.ws.policy.parser;
/*    */ 
/*    */ import com.sun.xml.ws.api.policy.PolicyResolver;
/*    */ import com.sun.xml.ws.api.policy.PolicyResolverFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WsitPolicyResolverFactory
/*    */   extends PolicyResolverFactory
/*    */ {
/*    */   public PolicyResolver doCreate() {
/* 52 */     return new WsitPolicyResolver();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\parser\WsitPolicyResolverFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */