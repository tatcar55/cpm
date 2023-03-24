/*     */ package com.sun.xml.rpc.wsdl.document;
/*     */ 
/*     */ import com.sun.xml.rpc.spi.tools.WSDLDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceValidator;
/*     */ import com.sun.xml.rpc.wsdl.framework.GloballyKnown;
/*     */ import com.sun.xml.rpc.wsdl.framework.Kind;
/*     */ import com.sun.xml.rpc.wsdl.framework.NoSuchEntityException;
/*     */ import com.sun.xml.rpc.wsdl.framework.ValidationException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class WSDLDocument
/*     */   extends AbstractDocument
/*     */   implements WSDLDocument
/*     */ {
/*     */   private Definitions _definitions;
/*     */   
/*     */   public Definitions getDefinitions() {
/*  58 */     return this._definitions;
/*     */   }
/*     */   
/*     */   public void setDefinitions(Definitions d) {
/*  62 */     this._definitions = d;
/*     */   }
/*     */   
/*     */   public Set collectAllNamespaces() {
/*  66 */     Set<String> result = super.collectAllNamespaces();
/*  67 */     if (this._definitions.getTargetNamespaceURI() != null) {
/*  68 */       result.add(this._definitions.getTargetNamespaceURI());
/*     */     }
/*  70 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName[] getAllServiceQNames() {
/*  75 */     ArrayList<QName> serviceQNames = new ArrayList();
/*     */     
/*  77 */     for (Iterator<Service> iter = getDefinitions().services(); iter.hasNext(); ) {
/*  78 */       Service next = iter.next();
/*  79 */       String targetNamespace = next.getDefining().getTargetNamespaceURI();
/*  80 */       String localName = next.getName();
/*  81 */       QName serviceQName = new QName(targetNamespace, localName);
/*  82 */       serviceQNames.add(serviceQName);
/*     */     } 
/*  84 */     return serviceQNames.<QName>toArray(new QName[serviceQNames.size()]);
/*     */   }
/*     */   
/*     */   public QName[] getAllPortQNames() {
/*  88 */     ArrayList<QName> portQNames = new ArrayList();
/*     */     
/*  90 */     for (Iterator<Service> iter = getDefinitions().services(); iter.hasNext(); ) {
/*  91 */       Service next = iter.next();
/*     */       
/*  93 */       for (Iterator<Port> piter = next.ports(); piter.hasNext(); ) {
/*     */         
/*  95 */         Port pnext = piter.next();
/*  96 */         String targetNamespace = pnext.getDefining().getTargetNamespaceURI();
/*     */         
/*  98 */         String localName = pnext.getName();
/*  99 */         QName portQName = new QName(targetNamespace, localName);
/* 100 */         portQNames.add(portQName);
/*     */       } 
/*     */     } 
/* 103 */     return portQNames.<QName>toArray(new QName[portQNames.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public QName[] getPortQNames(String serviceNameLocalPart) {
/* 108 */     ArrayList<QName> portQNames = new ArrayList();
/*     */     
/* 110 */     for (Iterator<Service> iter = getDefinitions().services(); iter.hasNext(); ) {
/* 111 */       Service next = iter.next();
/* 112 */       if (next.getName().equals(serviceNameLocalPart)) {
/* 113 */         for (Iterator<Port> piter = next.ports(); piter.hasNext(); ) {
/* 114 */           Port pnext = piter.next();
/* 115 */           String targetNamespace = pnext.getDefining().getTargetNamespaceURI();
/*     */           
/* 117 */           String localName = pnext.getName();
/* 118 */           QName portQName = new QName(targetNamespace, localName);
/* 119 */           portQNames.add(portQName);
/*     */         } 
/*     */       }
/*     */     } 
/* 123 */     return portQNames.<QName>toArray(new QName[portQNames.size()]);
/*     */   }
/*     */   
/*     */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 127 */     this._definitions.accept(visitor);
/*     */   }
/*     */   
/*     */   public void validate(EntityReferenceValidator validator) {
/* 131 */     GloballyValidatingAction action = new GloballyValidatingAction(this, validator);
/*     */     
/* 133 */     withAllSubEntitiesDo(action);
/* 134 */     if (action.getException() != null) {
/* 135 */       throw action.getException();
/*     */     }
/*     */   }
/*     */   
/*     */   protected Entity getRoot() {
/* 140 */     return this._definitions;
/*     */   }
/*     */   
/*     */   private class GloballyValidatingAction
/*     */     implements EntityAction, EntityReferenceAction {
/*     */     private ValidationException _exception;
/*     */     private AbstractDocument _document;
/*     */     private EntityReferenceValidator _validator;
/*     */     
/*     */     public GloballyValidatingAction(AbstractDocument document, EntityReferenceValidator validator) {
/* 150 */       this._document = document;
/* 151 */       this._validator = validator;
/*     */     }
/*     */     
/*     */     public void perform(Entity entity) {
/*     */       try {
/* 156 */         entity.validateThis();
/* 157 */         entity.withAllEntityReferencesDo(this);
/* 158 */         entity.withAllSubEntitiesDo(this);
/* 159 */       } catch (ValidationException e) {
/* 160 */         if (this._exception == null) {
/* 161 */           this._exception = e;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public void perform(Kind kind, QName name) {
/*     */       try {
/* 168 */         GloballyKnown entity = this._document.find(kind, name);
/* 169 */       } catch (NoSuchEntityException e) {
/*     */         
/* 171 */         if (this._exception == null && (
/* 172 */           this._validator == null || !this._validator.isValid(kind, name)))
/*     */         {
/* 174 */           this._exception = (ValidationException)e;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ValidationException getException() {
/* 181 */       return this._exception;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\WSDLDocument.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */