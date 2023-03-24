/*    */ package com.sun.xml.ws.policy.sourcemodel;
/*    */ 
/*    */ import java.net.URI;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PolicySourceModelContext
/*    */ {
/*    */   Map<URI, PolicySourceModel> policyModels;
/*    */   
/*    */   private Map<URI, PolicySourceModel> getModels() {
/* 63 */     if (null == this.policyModels) {
/* 64 */       this.policyModels = new HashMap<URI, PolicySourceModel>();
/*    */     }
/* 66 */     return this.policyModels;
/*    */   }
/*    */   
/*    */   public void addModel(URI modelUri, PolicySourceModel model) {
/* 70 */     getModels().put(modelUri, model);
/*    */   }
/*    */   
/*    */   public static PolicySourceModelContext createContext() {
/* 74 */     return new PolicySourceModelContext();
/*    */   }
/*    */   
/*    */   public boolean containsModel(URI modelUri) {
/* 78 */     return getModels().containsKey(modelUri);
/*    */   }
/*    */   
/*    */   PolicySourceModel retrieveModel(URI modelUri) {
/* 82 */     return getModels().get(modelUri);
/*    */   }
/*    */ 
/*    */   
/*    */   PolicySourceModel retrieveModel(URI modelUri, URI digestAlgorithm, String digest) {
/* 87 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 92 */     return "PolicySourceModelContext: policyModels = " + this.policyModels;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\PolicySourceModelContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */