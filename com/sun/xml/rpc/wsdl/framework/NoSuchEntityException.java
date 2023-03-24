/*    */ package com.sun.xml.rpc.wsdl.framework;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoSuchEntityException
/*    */   extends ValidationException
/*    */ {
/*    */   public NoSuchEntityException(QName name) {
/* 39 */     super("entity.notFoundByQName", new Object[] { name.getLocalPart(), name.getNamespaceURI() });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NoSuchEntityException(String id) {
/* 45 */     super("entity.notFoundByID", id);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 49 */     return "com.sun.xml.rpc.resources.wsdl";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\NoSuchEntityException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */