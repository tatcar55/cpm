/*     */ package com.sun.xml.rpc.processor.model;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.ImportedDocumentInfo;
/*     */ import com.sun.xml.rpc.spi.model.Model;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class Model
/*     */   extends ModelObject
/*     */   implements Model
/*     */ {
/*     */   private QName name;
/*     */   private String targetNamespace;
/*     */   
/*     */   public Model() {}
/*     */   
/*     */   public Model(QName name) {
/*  52 */     this.name = name;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  56 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(QName n) {
/*  60 */     this.name = n;
/*     */   }
/*     */   
/*     */   public String getTargetNamespaceURI() {
/*  64 */     return this.targetNamespace;
/*     */   }
/*     */   
/*     */   public void setTargetNamespaceURI(String s) {
/*  68 */     this.targetNamespace = s;
/*     */   }
/*     */   
/*     */   public void addService(Service service) {
/*  72 */     if (this.servicesByName.containsKey(service.getName())) {
/*  73 */       throw new ModelException("model.uniqueness");
/*     */     }
/*  75 */     this.services.add(service);
/*  76 */     this.servicesByName.put(service.getName(), service);
/*     */   }
/*     */   
/*     */   public Iterator getServices() {
/*  80 */     return this.services.iterator();
/*     */   }
/*     */   
/*     */   public Service getServiceByName(QName name) {
/*  84 */     if (this.servicesByName.size() != this.services.size()) {
/*  85 */       initializeServicesByName();
/*     */     }
/*  87 */     return (Service)this.servicesByName.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public List getServicesList() {
/*  92 */     return this.services;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServicesList(List l) {
/*  97 */     this.services = l;
/*     */   }
/*     */   
/*     */   private void initializeServicesByName() {
/* 101 */     this.servicesByName = new HashMap<Object, Object>();
/* 102 */     if (this.services != null) {
/* 103 */       for (Iterator<Service> iter = this.services.iterator(); iter.hasNext(); ) {
/* 104 */         Service service = iter.next();
/* 105 */         if (service.getName() != null && this.servicesByName.containsKey(service.getName()))
/*     */         {
/*     */           
/* 108 */           throw new ModelException("model.uniqueness");
/*     */         }
/* 110 */         this.servicesByName.put(service.getName(), service);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void addExtraType(AbstractType type) {
/* 116 */     this.extraTypes.add(type);
/*     */   }
/*     */   
/*     */   public Iterator getExtraTypes() {
/* 120 */     return this.extraTypes.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getExtraTypesSet() {
/* 125 */     return this.extraTypes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExtraTypesSet(Set s) {
/* 130 */     this.extraTypes = s;
/*     */   }
/*     */   
/*     */   public Iterator getImportedDocuments() {
/* 134 */     return this.importedDocuments.values().iterator();
/*     */   }
/*     */   
/*     */   public ImportedDocumentInfo getImportedDocument(String namespace) {
/* 138 */     return (ImportedDocumentInfo)this.importedDocuments.get(namespace);
/*     */   }
/*     */   
/*     */   public void addImportedDocument(ImportedDocumentInfo i) {
/* 142 */     this.importedDocuments.put(i.getNamespace(), i);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map getImportedDocumentsMap() {
/* 147 */     return this.importedDocuments;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setImportedDocumentsMap(Map m) {
/* 152 */     this.importedDocuments = m;
/*     */   }
/*     */   
/*     */   public void accept(ModelVisitor visitor) throws Exception {
/* 156 */     visitor.visit(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSource() {
/* 163 */     return this.source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSource(String string) {
/* 170 */     this.source = string;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 175 */   private List services = new ArrayList();
/* 176 */   private Map servicesByName = new HashMap<Object, Object>();
/* 177 */   private Set extraTypes = new HashSet();
/* 178 */   private Map importedDocuments = new HashMap<Object, Object>();
/*     */   private String source;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\Model.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */