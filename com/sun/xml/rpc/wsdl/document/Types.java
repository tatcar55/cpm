/*    */ package com.sun.xml.rpc.wsdl.document;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*    */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*    */ import com.sun.xml.rpc.wsdl.framework.ExtensibilityHelper;
/*    */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*    */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*    */ import com.sun.xml.rpc.wsdl.framework.ExtensionVisitor;
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
/*    */ public class Types
/*    */   extends Entity
/*    */   implements Extensible
/*    */ {
/* 48 */   private ExtensibilityHelper _helper = new ExtensibilityHelper();
/*    */   private Documentation _documentation;
/*    */   
/*    */   public QName getElementName() {
/* 52 */     return WSDLConstants.QNAME_TYPES;
/*    */   }
/*    */   
/*    */   public Documentation getDocumentation() {
/* 56 */     return this._documentation;
/*    */   }
/*    */   
/*    */   public void setDocumentation(Documentation d) {
/* 60 */     this._documentation = d;
/*    */   }
/*    */   
/*    */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 64 */     visitor.preVisit(this);
/* 65 */     this._helper.accept(visitor);
/* 66 */     visitor.postVisit(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validateThis() {}
/*    */   
/*    */   public void addExtension(Extension e) {
/* 73 */     this._helper.addExtension(e);
/*    */   }
/*    */   
/*    */   public Iterator extensions() {
/* 77 */     return this._helper.extensions();
/*    */   }
/*    */   
/*    */   public void withAllSubEntitiesDo(EntityAction action) {
/* 81 */     this._helper.withAllSubEntitiesDo(action);
/*    */   }
/*    */   
/*    */   public void accept(ExtensionVisitor visitor) throws Exception {
/* 85 */     this._helper.accept(visitor);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\Types.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */