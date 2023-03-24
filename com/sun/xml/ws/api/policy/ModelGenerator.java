/*    */ package com.sun.xml.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.Policy;
/*    */ import com.sun.xml.ws.policy.sourcemodel.PolicyModelGenerator;
/*    */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ModelGenerator
/*    */   extends PolicyModelGenerator
/*    */ {
/* 53 */   private static final SourceModelCreator CREATOR = new SourceModelCreator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PolicyModelGenerator getGenerator() {
/* 68 */     return PolicyModelGenerator.getCompactGenerator(CREATOR);
/*    */   }
/*    */ 
/*    */   
/*    */   protected static class SourceModelCreator
/*    */     extends PolicyModelGenerator.PolicySourceModelCreator
/*    */   {
/*    */     protected PolicySourceModel create(Policy policy) {
/* 76 */       return SourceModel.createPolicySourceModel(policy.getNamespaceVersion(), policy.getId(), policy.getName());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\policy\ModelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */