/*     */ package com.sun.xml.rpc.wsdl.document;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.Defining;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.GlobalEntity;
/*     */ import com.sun.xml.rpc.wsdl.framework.Kind;
/*     */ import com.sun.xml.rpc.wsdl.framework.ValidationException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class PortType
/*     */   extends GlobalEntity
/*     */ {
/*     */   private Documentation _documentation;
/*     */   private List _operations;
/*     */   private Set _operationKeys;
/*     */   
/*     */   public PortType(Defining defining) {
/*  52 */     super(defining);
/*  53 */     this._operations = new ArrayList();
/*  54 */     this._operationKeys = new HashSet();
/*     */   }
/*     */   
/*     */   public void add(Operation operation) {
/*  58 */     String key = operation.getUniqueKey();
/*  59 */     if (this._operationKeys.contains(key)) {
/*  60 */       throw new ValidationException("validation.ambiguousName", operation.getName());
/*     */     }
/*     */     
/*  63 */     this._operationKeys.add(key);
/*  64 */     this._operations.add(operation);
/*     */   }
/*     */   
/*     */   public Iterator operations() {
/*  68 */     return this._operations.iterator();
/*     */   }
/*     */   
/*     */   public Set getOperationsNamed(String s) {
/*  72 */     Set<Operation> result = new HashSet();
/*  73 */     for (Iterator<Operation> iter = this._operations.iterator(); iter.hasNext(); ) {
/*  74 */       Operation operation = iter.next();
/*  75 */       if (operation.getName().equals(s)) {
/*  76 */         result.add(operation);
/*     */       }
/*     */     } 
/*  79 */     return result;
/*     */   }
/*     */   
/*     */   public Kind getKind() {
/*  83 */     return Kinds.PORT_TYPE;
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/*  87 */     return WSDLConstants.QNAME_PORT_TYPE;
/*     */   }
/*     */   
/*     */   public Documentation getDocumentation() {
/*  91 */     return this._documentation;
/*     */   }
/*     */   
/*     */   public void setDocumentation(Documentation d) {
/*  95 */     this._documentation = d;
/*     */   }
/*     */   
/*     */   public void withAllSubEntitiesDo(EntityAction action) {
/*  99 */     super.withAllSubEntitiesDo(action);
/*     */     
/* 101 */     for (Iterator<Entity> iter = this._operations.iterator(); iter.hasNext();) {
/* 102 */       action.perform(iter.next());
/*     */     }
/*     */   }
/*     */   
/*     */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 107 */     visitor.preVisit(this);
/* 108 */     for (Iterator<Operation> iter = this._operations.iterator(); iter.hasNext();) {
/* 109 */       ((Operation)iter.next()).accept(visitor);
/*     */     }
/* 111 */     visitor.postVisit(this);
/*     */   }
/*     */   
/*     */   public void validateThis() {
/* 115 */     if (getName() == null)
/* 116 */       failValidation("validation.missingRequiredAttribute", "name"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\PortType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */