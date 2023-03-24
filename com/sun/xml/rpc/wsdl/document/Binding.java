/*     */ package com.sun.xml.rpc.wsdl.document;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.Defining;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.ExtensibilityHelper;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.GlobalEntity;
/*     */ import com.sun.xml.rpc.wsdl.framework.Kind;
/*     */ import com.sun.xml.rpc.wsdl.framework.QNameAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class Binding
/*     */   extends GlobalEntity
/*     */   implements Extensible
/*     */ {
/*     */   private ExtensibilityHelper _helper;
/*     */   private Documentation _documentation;
/*     */   private QName _portType;
/*     */   private List _operations;
/*     */   
/*     */   public Binding(Defining defining) {
/*  55 */     super(defining);
/*  56 */     this._operations = new ArrayList();
/*  57 */     this._helper = new ExtensibilityHelper();
/*     */   }
/*     */   
/*     */   public void add(BindingOperation operation) {
/*  61 */     this._operations.add(operation);
/*     */   }
/*     */   
/*     */   public Iterator operations() {
/*  65 */     return this._operations.iterator();
/*     */   }
/*     */   
/*     */   public QName getPortType() {
/*  69 */     return this._portType;
/*     */   }
/*     */   
/*     */   public void setPortType(QName n) {
/*  73 */     this._portType = n;
/*     */   }
/*     */   
/*     */   public PortType resolvePortType(AbstractDocument document) {
/*  77 */     return (PortType)document.find(Kinds.PORT_TYPE, this._portType);
/*     */   }
/*     */   
/*     */   public Kind getKind() {
/*  81 */     return Kinds.BINDING;
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/*  85 */     return WSDLConstants.QNAME_BINDING;
/*     */   }
/*     */   
/*     */   public Documentation getDocumentation() {
/*  89 */     return this._documentation;
/*     */   }
/*     */   
/*     */   public void setDocumentation(Documentation d) {
/*  93 */     this._documentation = d;
/*     */   }
/*     */   
/*     */   public void withAllSubEntitiesDo(EntityAction action) {
/*  97 */     for (Iterator<Entity> iter = this._operations.iterator(); iter.hasNext();) {
/*  98 */       action.perform(iter.next());
/*     */     }
/* 100 */     this._helper.withAllSubEntitiesDo(action);
/*     */   }
/*     */   
/*     */   public void withAllQNamesDo(QNameAction action) {
/* 104 */     super.withAllQNamesDo(action);
/*     */     
/* 106 */     if (this._portType != null) {
/* 107 */       action.perform(this._portType);
/*     */     }
/*     */   }
/*     */   
/*     */   public void withAllEntityReferencesDo(EntityReferenceAction action) {
/* 112 */     super.withAllEntityReferencesDo(action);
/* 113 */     if (this._portType != null) {
/* 114 */       action.perform(Kinds.PORT_TYPE, this._portType);
/*     */     }
/*     */   }
/*     */   
/*     */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 119 */     visitor.preVisit(this);
/*     */     
/* 121 */     this._helper.accept(visitor);
/* 122 */     for (Iterator<BindingOperation> iter = this._operations.iterator(); iter.hasNext();) {
/* 123 */       ((BindingOperation)iter.next()).accept(visitor);
/*     */     }
/* 125 */     visitor.postVisit(this);
/*     */   }
/*     */   
/*     */   public void validateThis() {
/* 129 */     if (getName() == null) {
/* 130 */       failValidation("validation.missingRequiredAttribute", "name");
/*     */     }
/* 132 */     if (this._portType == null) {
/* 133 */       failValidation("validation.missingRequiredAttribute", "type");
/*     */     }
/*     */   }
/*     */   
/*     */   public void addExtension(Extension e) {
/* 138 */     this._helper.addExtension(e);
/*     */   }
/*     */   
/*     */   public Iterator extensions() {
/* 142 */     return this._helper.extensions();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\Binding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */