/*     */ package com.sun.xml.rpc.wsdl.document;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.Defining;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.ExtensibilityHelper;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.GlobalEntity;
/*     */ import com.sun.xml.rpc.wsdl.framework.Kind;
/*     */ import com.sun.xml.rpc.wsdl.framework.QNameAction;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Port
/*     */   extends GlobalEntity
/*     */   implements Extensible
/*     */ {
/*     */   private ExtensibilityHelper _helper;
/*     */   private Documentation _documentation;
/*     */   private Service _service;
/*     */   private QName _binding;
/*     */   
/*     */   public Port(Defining defining) {
/*  52 */     super(defining);
/*  53 */     this._helper = new ExtensibilityHelper();
/*     */   }
/*     */   
/*     */   public Service getService() {
/*  57 */     return this._service;
/*     */   }
/*     */   
/*     */   public void setService(Service s) {
/*  61 */     this._service = s;
/*     */   }
/*     */   
/*     */   public QName getBinding() {
/*  65 */     return this._binding;
/*     */   }
/*     */   
/*     */   public void setBinding(QName n) {
/*  69 */     this._binding = n;
/*     */   }
/*     */   
/*     */   public Binding resolveBinding(AbstractDocument document) {
/*  73 */     return (Binding)document.find(Kinds.BINDING, this._binding);
/*     */   }
/*     */   
/*     */   public Kind getKind() {
/*  77 */     return Kinds.PORT;
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/*  81 */     return WSDLConstants.QNAME_PORT;
/*     */   }
/*     */   
/*     */   public Documentation getDocumentation() {
/*  85 */     return this._documentation;
/*     */   }
/*     */   
/*     */   public void setDocumentation(Documentation d) {
/*  89 */     this._documentation = d;
/*     */   }
/*     */   
/*     */   public void withAllQNamesDo(QNameAction action) {
/*  93 */     super.withAllQNamesDo(action);
/*     */     
/*  95 */     if (this._binding != null) {
/*  96 */       action.perform(this._binding);
/*     */     }
/*     */   }
/*     */   
/*     */   public void withAllEntityReferencesDo(EntityReferenceAction action) {
/* 101 */     super.withAllEntityReferencesDo(action);
/* 102 */     if (this._binding != null) {
/* 103 */       action.perform(Kinds.BINDING, this._binding);
/*     */     }
/*     */   }
/*     */   
/*     */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 108 */     visitor.preVisit(this);
/* 109 */     this._helper.accept(visitor);
/* 110 */     visitor.postVisit(this);
/*     */   }
/*     */   
/*     */   public void validateThis() {
/* 114 */     if (getName() == null) {
/* 115 */       failValidation("validation.missingRequiredAttribute", "name");
/*     */     }
/* 117 */     if (this._binding == null) {
/* 118 */       failValidation("validation.missingRequiredAttribute", "binding");
/*     */     }
/*     */   }
/*     */   
/*     */   public void addExtension(Extension e) {
/* 123 */     this._helper.addExtension(e);
/*     */   }
/*     */   
/*     */   public Iterator extensions() {
/* 127 */     return this._helper.extensions();
/*     */   }
/*     */   
/*     */   public void withAllSubEntitiesDo(EntityAction action) {
/* 131 */     this._helper.withAllSubEntitiesDo(action);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\Port.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */