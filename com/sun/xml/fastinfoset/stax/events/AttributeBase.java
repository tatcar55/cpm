/*     */ package com.sun.xml.fastinfoset.stax.events;
/*     */ 
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.events.Attribute;
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
/*     */ public class AttributeBase
/*     */   extends EventBase
/*     */   implements Attribute
/*     */ {
/*     */   private QName _QName;
/*     */   private String _value;
/*  32 */   private String _attributeType = null;
/*     */   
/*     */   private boolean _specified = false;
/*     */ 
/*     */   
/*     */   public AttributeBase() {
/*  38 */     super(10);
/*     */   }
/*     */   
/*     */   public AttributeBase(String name, String value) {
/*  42 */     super(10);
/*  43 */     this._QName = new QName(name);
/*  44 */     this._value = value;
/*     */   }
/*     */   
/*     */   public AttributeBase(QName qname, String value) {
/*  48 */     this._QName = qname;
/*  49 */     this._value = value;
/*     */   }
/*     */   
/*     */   public AttributeBase(String prefix, String localName, String value) {
/*  53 */     this(prefix, null, localName, value, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeBase(String prefix, String namespaceURI, String localName, String value, String attributeType) {
/*  58 */     if (prefix == null) prefix = ""; 
/*  59 */     this._QName = new QName(namespaceURI, localName, prefix);
/*  60 */     this._value = value;
/*  61 */     this._attributeType = (attributeType == null) ? "CDATA" : attributeType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(QName name) {
/*  66 */     this._QName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/*  73 */     return this._QName;
/*     */   }
/*     */   
/*     */   public void setValue(String value) {
/*  77 */     this._value = value;
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/*  81 */     return this._QName.getLocalPart();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  87 */     return this._value;
/*     */   }
/*     */   
/*     */   public void setAttributeType(String attributeType) {
/*  91 */     this._attributeType = attributeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDTDType() {
/* 100 */     return this._attributeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpecified() {
/* 110 */     return this._specified;
/*     */   }
/*     */   
/*     */   public void setSpecified(boolean isSpecified) {
/* 114 */     this._specified = isSpecified;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 119 */     String prefix = this._QName.getPrefix();
/* 120 */     if (!Util.isEmptyString(prefix)) {
/* 121 */       return prefix + ":" + this._QName.getLocalPart() + "='" + this._value + "'";
/*     */     }
/* 123 */     return this._QName.getLocalPart() + "='" + this._value + "'";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\AttributeBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */