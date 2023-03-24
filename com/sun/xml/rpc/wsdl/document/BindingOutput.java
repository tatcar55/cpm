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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BindingOutput
/*    */   extends Entity
/*    */   implements Extensible
/*    */ {
/* 47 */   private ExtensibilityHelper _helper = new ExtensibilityHelper();
/*    */   private Documentation _documentation;
/*    */   
/*    */   public String getName() {
/* 51 */     return this._name;
/*    */   }
/*    */   private String _name;
/*    */   public void setName(String name) {
/* 55 */     this._name = name;
/*    */   }
/*    */   
/*    */   public QName getElementName() {
/* 59 */     return WSDLConstants.QNAME_OUTPUT;
/*    */   }
/*    */   
/*    */   public Documentation getDocumentation() {
/* 63 */     return this._documentation;
/*    */   }
/*    */   
/*    */   public void setDocumentation(Documentation d) {
/* 67 */     this._documentation = d;
/*    */   }
/*    */   
/*    */   public void addExtension(Extension e) {
/* 71 */     this._helper.addExtension(e);
/*    */   }
/*    */   
/*    */   public Iterator extensions() {
/* 75 */     return this._helper.extensions();
/*    */   }
/*    */   
/*    */   public void withAllSubEntitiesDo(EntityAction action) {
/* 79 */     this._helper.withAllSubEntitiesDo(action);
/*    */   }
/*    */   
/*    */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 83 */     visitor.preVisit(this);
/* 84 */     this._helper.accept(visitor);
/* 85 */     visitor.postVisit(this);
/*    */   }
/*    */   
/*    */   public void validateThis() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\BindingOutput.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */