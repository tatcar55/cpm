/*     */ package com.sun.xml.rpc.wsdl.document.schema;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.Defining;
/*     */ import com.sun.xml.rpc.wsdl.framework.DuplicateEntityException;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.Kind;
/*     */ import com.sun.xml.rpc.wsdl.framework.ValidationException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class Schema
/*     */   extends Extension
/*     */   implements Defining
/*     */ {
/*     */   private AbstractDocument _document;
/*     */   private String _targetNamespaceURI;
/*     */   private SchemaElement _content;
/*     */   private List _definedEntities;
/*     */   private Map _nsPrefixes;
/*     */   
/*     */   public Schema(AbstractDocument document) {
/*  52 */     this._document = document;
/*  53 */     this._nsPrefixes = new HashMap<Object, Object>();
/*  54 */     this._definedEntities = new ArrayList();
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/*  58 */     return SchemaConstants.QNAME_SCHEMA;
/*     */   }
/*     */   
/*     */   public SchemaElement getContent() {
/*  62 */     return this._content;
/*     */   }
/*     */   
/*     */   public void setContent(SchemaElement entity) {
/*  66 */     this._content = entity;
/*  67 */     this._content.setSchema(this);
/*     */   }
/*     */   
/*     */   public void setTargetNamespaceURI(String uri) {
/*  71 */     this._targetNamespaceURI = uri;
/*     */   }
/*     */   
/*     */   public String getTargetNamespaceURI() {
/*  75 */     return this._targetNamespaceURI;
/*     */   }
/*     */   
/*     */   public void addPrefix(String prefix, String uri) {
/*  79 */     this._nsPrefixes.put(prefix, uri);
/*     */   }
/*     */   
/*     */   public String getURIForPrefix(String prefix) {
/*  83 */     return (String)this._nsPrefixes.get(prefix);
/*     */   }
/*     */   
/*     */   public Iterator prefixes() {
/*  87 */     return this._nsPrefixes.keySet().iterator();
/*     */   }
/*     */   
/*     */   public void defineAllEntities() {
/*  91 */     if (this._content == null) {
/*  92 */       throw new ValidationException("validation.shouldNotHappen", "missing schema content");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  97 */     for (Iterator<SchemaElement> iter = this._content.children(); iter.hasNext(); ) {
/*  98 */       SchemaElement child = iter.next();
/*  99 */       if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE)) {
/* 100 */         QName name = new QName(this._targetNamespaceURI, child.getValueOfMandatoryAttribute("name"));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 105 */         defineEntity(child, SchemaKinds.XSD_ATTRIBUTE, name); continue;
/* 106 */       }  if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE_GROUP)) {
/*     */ 
/*     */         
/* 109 */         QName name = new QName(this._targetNamespaceURI, child.getValueOfMandatoryAttribute("name"));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 114 */         defineEntity(child, SchemaKinds.XSD_ATTRIBUTE_GROUP, name); continue;
/* 115 */       }  if (child.getQName().equals(SchemaConstants.QNAME_ELEMENT)) {
/*     */         
/* 117 */         QName name = new QName(this._targetNamespaceURI, child.getValueOfMandatoryAttribute("name"));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 122 */         defineEntity(child, SchemaKinds.XSD_ELEMENT, name); continue;
/* 123 */       }  if (child.getQName().equals(SchemaConstants.QNAME_GROUP)) {
/* 124 */         QName name = new QName(this._targetNamespaceURI, child.getValueOfMandatoryAttribute("name"));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 129 */         defineEntity(child, SchemaKinds.XSD_GROUP, name); continue;
/* 130 */       }  if (child.getQName().equals(SchemaConstants.QNAME_COMPLEX_TYPE)) {
/*     */         
/* 132 */         QName name = new QName(this._targetNamespaceURI, child.getValueOfMandatoryAttribute("name"));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 137 */         defineEntity(child, SchemaKinds.XSD_TYPE, name); continue;
/* 138 */       }  if (child.getQName().equals(SchemaConstants.QNAME_SIMPLE_TYPE)) {
/*     */         
/* 140 */         QName name = new QName(this._targetNamespaceURI, child.getValueOfMandatoryAttribute("name"));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 145 */         defineEntity(child, SchemaKinds.XSD_TYPE, name);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void defineEntity(SchemaElement element, Kind kind, QName name) {
/* 154 */     SchemaEntity entity = new SchemaEntity(this, element, kind, name);
/*     */     try {
/* 156 */       this._document.define(entity);
/* 157 */     } catch (DuplicateEntityException e) {
/*     */       return;
/*     */     } 
/* 160 */     this._definedEntities.add(entity);
/*     */   }
/*     */   
/*     */   public Iterator definedEntities() {
/* 164 */     return this._definedEntities.iterator();
/*     */   }
/*     */   
/*     */   public void validateThis() {
/* 168 */     if (this._content == null) {
/* 169 */       throw new ValidationException("validation.shouldNotHappen", "missing schema content");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String asString(QName name) {
/* 176 */     if (name.getNamespaceURI().equals("")) {
/* 177 */       return name.getLocalPart();
/*     */     }
/*     */     
/* 180 */     for (Iterator<String> iter = prefixes(); iter.hasNext(); ) {
/* 181 */       String prefix = iter.next();
/* 182 */       if (prefix.equals(name.getNamespaceURI())) {
/* 183 */         return prefix + ":" + name.getLocalPart();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 188 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\schema\Schema.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */