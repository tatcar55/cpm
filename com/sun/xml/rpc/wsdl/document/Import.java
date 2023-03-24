/*    */ package com.sun.xml.rpc.wsdl.document;
/*    */ 
/*    */ import com.sun.xml.rpc.spi.tools.Import;
/*    */ import com.sun.xml.rpc.wsdl.framework.Entity;
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
/*    */ public class Import
/*    */   extends Entity
/*    */   implements Import
/*    */ {
/*    */   private Documentation _documentation;
/*    */   private String _location;
/*    */   private String _namespace;
/*    */   
/*    */   public String getNamespace() {
/* 46 */     return this._namespace;
/*    */   }
/*    */   
/*    */   public void setNamespace(String s) {
/* 50 */     this._namespace = s;
/*    */   }
/*    */   
/*    */   public String getLocation() {
/* 54 */     return this._location;
/*    */   }
/*    */   
/*    */   public void setLocation(String s) {
/* 58 */     this._location = s;
/*    */   }
/*    */   
/*    */   public QName getElementName() {
/* 62 */     return WSDLConstants.QNAME_IMPORT;
/*    */   }
/*    */   
/*    */   public Documentation getDocumentation() {
/* 66 */     return this._documentation;
/*    */   }
/*    */   
/*    */   public void setDocumentation(Documentation d) {
/* 70 */     this._documentation = d;
/*    */   }
/*    */   
/*    */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 74 */     visitor.visit(this);
/*    */   }
/*    */   
/*    */   public void validateThis() {
/* 78 */     if (this._location == null) {
/* 79 */       failValidation("validation.missingRequiredAttribute", "location");
/*    */     }
/* 81 */     if (this._namespace == null)
/* 82 */       failValidation("validation.missingRequiredAttribute", "namespace"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\Import.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */