/*    */ package com.sun.xml.rpc.wsdl.document;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*    */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*    */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceAction;
/*    */ import com.sun.xml.rpc.wsdl.framework.QNameAction;
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
/*    */ public class Input
/*    */   extends Entity
/*    */ {
/*    */   private Documentation _documentation;
/*    */   private String _name;
/*    */   private QName _message;
/*    */   
/*    */   public String getName() {
/* 47 */     return this._name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 51 */     this._name = name;
/*    */   }
/*    */   
/*    */   public QName getMessage() {
/* 55 */     return this._message;
/*    */   }
/*    */   
/*    */   public void setMessage(QName n) {
/* 59 */     this._message = n;
/*    */   }
/*    */   
/*    */   public Message resolveMessage(AbstractDocument document) {
/* 63 */     return (Message)document.find(Kinds.MESSAGE, this._message);
/*    */   }
/*    */   
/*    */   public QName getElementName() {
/* 67 */     return WSDLConstants.QNAME_INPUT;
/*    */   }
/*    */   
/*    */   public Documentation getDocumentation() {
/* 71 */     return this._documentation;
/*    */   }
/*    */   
/*    */   public void setDocumentation(Documentation d) {
/* 75 */     this._documentation = d;
/*    */   }
/*    */   
/*    */   public void withAllQNamesDo(QNameAction action) {
/* 79 */     if (this._message != null) {
/* 80 */       action.perform(this._message);
/*    */     }
/*    */   }
/*    */   
/*    */   public void withAllEntityReferencesDo(EntityReferenceAction action) {
/* 85 */     super.withAllEntityReferencesDo(action);
/* 86 */     if (this._message != null) {
/* 87 */       action.perform(Kinds.MESSAGE, this._message);
/*    */     }
/*    */   }
/*    */   
/*    */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 92 */     visitor.preVisit(this);
/* 93 */     visitor.postVisit(this);
/*    */   }
/*    */   
/*    */   public void validateThis() {
/* 97 */     if (this._message == null)
/* 98 */       failValidation("validation.missingRequiredAttribute", "message"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\Input.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */