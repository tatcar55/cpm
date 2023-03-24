/*     */ package com.sun.xml.rpc.wsdl.document.schema;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceValidator;
/*     */ import com.sun.xml.rpc.wsdl.framework.GloballyKnown;
/*     */ import com.sun.xml.rpc.wsdl.framework.Kind;
/*     */ import com.sun.xml.rpc.wsdl.framework.NoSuchEntityException;
/*     */ import com.sun.xml.rpc.wsdl.framework.ValidationException;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchemaDocument
/*     */   extends AbstractDocument
/*     */ {
/*     */   private Schema _schema;
/*     */   
/*     */   public Schema getSchema() {
/*  54 */     return this._schema;
/*     */   }
/*     */   
/*     */   public void setSchema(Schema s) {
/*  58 */     this._schema = s;
/*     */   }
/*     */   
/*     */   public Set collectAllNamespaces() {
/*  62 */     Set<String> result = super.collectAllNamespaces();
/*  63 */     if (this._schema.getTargetNamespaceURI() != null) {
/*  64 */       result.add(this._schema.getTargetNamespaceURI());
/*     */     }
/*  66 */     return result;
/*     */   }
/*     */   
/*     */   public void validate(EntityReferenceValidator validator) {
/*  70 */     GloballyValidatingAction action = new GloballyValidatingAction(this, validator);
/*     */     
/*  72 */     withAllSubEntitiesDo(action);
/*  73 */     if (action.getException() != null) {
/*  74 */       throw action.getException();
/*     */     }
/*     */   }
/*     */   
/*     */   protected Entity getRoot() {
/*  79 */     return (Entity)this._schema;
/*     */   }
/*     */   
/*     */   private class GloballyValidatingAction
/*     */     implements EntityAction, EntityReferenceAction {
/*     */     private ValidationException _exception;
/*     */     private AbstractDocument _document;
/*     */     private EntityReferenceValidator _validator;
/*     */     
/*     */     public GloballyValidatingAction(AbstractDocument document, EntityReferenceValidator validator) {
/*  89 */       this._document = document;
/*  90 */       this._validator = validator;
/*     */     }
/*     */     
/*     */     public void perform(Entity entity) {
/*     */       try {
/*  95 */         entity.validateThis();
/*  96 */         entity.withAllEntityReferencesDo(this);
/*  97 */         entity.withAllSubEntitiesDo(this);
/*  98 */       } catch (ValidationException e) {
/*  99 */         if (this._exception == null) {
/* 100 */           this._exception = e;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public void perform(Kind kind, QName name) {
/*     */       try {
/* 107 */         GloballyKnown entity = this._document.find(kind, name);
/* 108 */       } catch (NoSuchEntityException e) {
/*     */         
/* 110 */         if (this._exception == null && (
/* 111 */           this._validator == null || !this._validator.isValid(kind, name)))
/*     */         {
/* 113 */           this._exception = (ValidationException)e;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ValidationException getException() {
/* 120 */       return this._exception;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\schema\SchemaDocument.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */