/*    */ package com.sun.xml.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*    */ import com.sun.xml.ws.policy.sourcemodel.XmlPolicyModelUnmarshaller;
/*    */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelUnmarshaller
/*    */   extends XmlPolicyModelUnmarshaller
/*    */ {
/* 54 */   private static final ModelUnmarshaller INSTANCE = new ModelUnmarshaller();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ModelUnmarshaller getUnmarshaller() {
/* 69 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected PolicySourceModel createSourceModel(NamespaceVersion nsVersion, String id, String name) {
/* 74 */     return SourceModel.createSourceModel(nsVersion, id, name);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\policy\ModelUnmarshaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */