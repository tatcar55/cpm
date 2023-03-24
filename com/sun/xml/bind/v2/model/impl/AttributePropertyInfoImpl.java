/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.api.impl.NameConverter;
/*     */ import com.sun.xml.bind.v2.model.core.AttributePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlNsForm;
/*     */ import javax.xml.bind.annotation.XmlSchema;
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
/*     */ class AttributePropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   extends SingleTypePropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   implements AttributePropertyInfo<TypeT, ClassDeclT>
/*     */ {
/*     */   private final QName xmlName;
/*     */   private final boolean isRequired;
/*     */   
/*     */   AttributePropertyInfoImpl(ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> parent, PropertySeed<TypeT, ClassDeclT, FieldT, MethodT> seed) {
/*  63 */     super(parent, seed);
/*  64 */     XmlAttribute att = (XmlAttribute)seed.readAnnotation(XmlAttribute.class);
/*  65 */     assert att != null;
/*     */     
/*  67 */     if (att.required())
/*  68 */     { this.isRequired = true; }
/*  69 */     else { this.isRequired = nav().isPrimitive(getIndividualType()); }
/*     */     
/*  71 */     this.xmlName = calcXmlName(att);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private QName calcXmlName(XmlAttribute att) {
/*  78 */     String uri = att.namespace();
/*  79 */     String local = att.name();
/*     */ 
/*     */     
/*  82 */     if (local.equals("##default"))
/*  83 */       local = NameConverter.standard.toVariableName(getName()); 
/*  84 */     if (uri.equals("##default")) {
/*  85 */       XmlSchema xs = (XmlSchema)reader().getPackageAnnotation(XmlSchema.class, this.parent.getClazz(), this);
/*     */ 
/*     */       
/*  88 */       if (xs != null) {
/*  89 */         switch (xs.attributeFormDefault()) {
/*     */           case QUALIFIED:
/*  91 */             uri = this.parent.getTypeName().getNamespaceURI();
/*  92 */             if (uri.length() == 0)
/*  93 */               uri = this.parent.builder.defaultNsUri; 
/*     */             break;
/*     */           case UNQUALIFIED:
/*     */           case UNSET:
/*  97 */             uri = ""; break;
/*     */         } 
/*     */       } else {
/* 100 */         uri = "";
/*     */       } 
/*     */     } 
/* 103 */     return new QName(uri.intern(), local.intern());
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/* 107 */     return this.isRequired;
/*     */   }
/*     */   
/*     */   public final QName getXmlName() {
/* 111 */     return this.xmlName;
/*     */   }
/*     */   
/*     */   public final PropertyKind kind() {
/* 115 */     return PropertyKind.ATTRIBUTE;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\AttributePropertyInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */