/*    */ package com.sun.xml.rpc.wsdl.framework;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuplicateEntityException
/*    */   extends ValidationException
/*    */ {
/*    */   public DuplicateEntityException(GloballyKnown entity) {
/* 37 */     super("entity.duplicateWithType", new Object[] { entity.getElementName().getLocalPart(), entity.getName() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DuplicateEntityException(Identifiable entity) {
/* 45 */     super("entity.duplicateWithType", new Object[] { entity.getElementName().getLocalPart(), entity.getID() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DuplicateEntityException(Entity entity, String name) {
/* 53 */     super("entity.duplicateWithType", new Object[] { entity.getElementName().getLocalPart(), name });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getResourceBundleName() {
/* 59 */     return "com.sun.xml.rpc.resources.wsdl";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\DuplicateEntityException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */