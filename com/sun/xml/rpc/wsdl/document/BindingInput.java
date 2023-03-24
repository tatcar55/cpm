/*    */ package com.sun.xml.rpc.wsdl.document;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*    */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*    */ import com.sun.xml.rpc.wsdl.framework.ExtensibilityHelper;
/*    */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*    */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*    */ import java.util.Iterator;
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
/*    */ public class BindingInput
/*    */   extends Entity
/*    */   implements Extensible
/*    */ {
/* 41 */   private ExtensibilityHelper _helper = new ExtensibilityHelper();
/*    */   private Documentation _documentation;
/*    */   
/*    */   public String getName() {
/* 45 */     return this._name;
/*    */   }
/*    */   private String _name;
/*    */   public void setName(String name) {
/* 49 */     this._name = name;
/*    */   }
/*    */   
/*    */   public QName getElementName() {
/* 53 */     return WSDLConstants.QNAME_INPUT;
/*    */   }
/*    */   
/*    */   public Documentation getDocumentation() {
/* 57 */     return this._documentation;
/*    */   }
/*    */   
/*    */   public void setDocumentation(Documentation d) {
/* 61 */     this._documentation = d;
/*    */   }
/*    */   
/*    */   public void addExtension(Extension e) {
/* 65 */     this._helper.addExtension(e);
/*    */   }
/*    */   
/*    */   public Iterator extensions() {
/* 69 */     return this._helper.extensions();
/*    */   }
/*    */   
/*    */   public void withAllSubEntitiesDo(EntityAction action) {
/* 73 */     this._helper.withAllSubEntitiesDo(action);
/*    */   }
/*    */   
/*    */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 77 */     visitor.preVisit(this);
/* 78 */     this._helper.accept(visitor);
/* 79 */     visitor.postVisit(this);
/*    */   }
/*    */   
/*    */   public void validateThis() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\BindingInput.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */