/*     */ package com.sun.xml.rpc.wsdl.document;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.Kind;
/*     */ import com.sun.xml.rpc.wsdl.framework.QNameAction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessagePart
/*     */   extends Entity
/*     */ {
/*     */   public static final int SOAP_BODY_BINDING = 1;
/*     */   public static final int SOAP_HEADER_BINDING = 2;
/*     */   public static final int SOAP_HEADERFAULT_BINDING = 3;
/*     */   public static final int SOAP_FAULT_BINDING = 4;
/*     */   public static final int WSDL_MIME_BINDING = 5;
/*     */   private String _name;
/*     */   private QName _descriptor;
/*     */   private Kind _descriptorKind;
/*     */   private int _bindingKind;
/*     */   
/*     */   public String getName() {
/*  53 */     return this._name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  57 */     this._name = name;
/*     */   }
/*     */   
/*     */   public QName getDescriptor() {
/*  61 */     return this._descriptor;
/*     */   }
/*     */   
/*     */   public void setDescriptor(QName n) {
/*  65 */     this._descriptor = n;
/*     */   }
/*     */   
/*     */   public Kind getDescriptorKind() {
/*  69 */     return this._descriptorKind;
/*     */   }
/*     */   
/*     */   public void setDescriptorKind(Kind k) {
/*  73 */     this._descriptorKind = k;
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/*  77 */     return WSDLConstants.QNAME_PART;
/*     */   }
/*     */   
/*     */   public int getBindingExtensibilityElementKind() {
/*  81 */     return this._bindingKind;
/*     */   }
/*     */   
/*     */   public void setBindingExtensibilityElementKind(int kind) {
/*  85 */     this._bindingKind = kind;
/*     */   }
/*     */   
/*     */   public void withAllQNamesDo(QNameAction action) {
/*  89 */     if (this._descriptor != null) {
/*  90 */       action.perform(this._descriptor);
/*     */     }
/*     */   }
/*     */   
/*     */   public void withAllEntityReferencesDo(EntityReferenceAction action) {
/*  95 */     super.withAllEntityReferencesDo(action);
/*  96 */     if (this._descriptor != null && this._descriptorKind != null) {
/*  97 */       action.perform(this._descriptorKind, this._descriptor);
/*     */     }
/*     */   }
/*     */   
/*     */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 102 */     visitor.visit(this);
/*     */   }
/*     */   
/*     */   public void validateThis() {
/* 106 */     if (this._descriptorKind == null || this._descriptor == null)
/* 107 */       failValidation("validation.missingRequiredProperty", "descriptor"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\MessagePart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */