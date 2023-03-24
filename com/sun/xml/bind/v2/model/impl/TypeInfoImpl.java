/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.api.impl.NameConverter;
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlSchema;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ abstract class TypeInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   implements TypeInfo<TypeT, ClassDeclT>, Locatable
/*     */ {
/*     */   private final Locatable upstream;
/*     */   protected final TypeInfoSetImpl<TypeT, ClassDeclT, FieldT, MethodT> owner;
/*     */   protected ModelBuilder<TypeT, ClassDeclT, FieldT, MethodT> builder;
/*     */   
/*     */   protected TypeInfoImpl(ModelBuilder<TypeT, ClassDeclT, FieldT, MethodT> builder, Locatable upstream) {
/*  85 */     this.builder = builder;
/*  86 */     this.owner = builder.typeInfoSet;
/*  87 */     this.upstream = upstream;
/*     */   }
/*     */   
/*     */   public Locatable getUpstream() {
/*  91 */     return this.upstream;
/*     */   }
/*     */   
/*     */   void link() {
/*  95 */     this.builder = null;
/*     */   }
/*     */   
/*     */   protected final Navigator<TypeT, ClassDeclT, FieldT, MethodT> nav() {
/*  99 */     return this.owner.nav;
/*     */   }
/*     */   
/*     */   protected final AnnotationReader<TypeT, ClassDeclT, FieldT, MethodT> reader() {
/* 103 */     return this.owner.reader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final QName parseElementName(ClassDeclT clazz) {
/* 114 */     XmlRootElement e = (XmlRootElement)reader().getClassAnnotation(XmlRootElement.class, clazz, this);
/* 115 */     if (e == null) {
/* 116 */       return null;
/*     */     }
/* 118 */     String local = e.name();
/* 119 */     if (local.equals("##default"))
/*     */     {
/* 121 */       local = NameConverter.standard.toVariableName(nav().getClassShortName(clazz));
/*     */     }
/* 123 */     String nsUri = e.namespace();
/* 124 */     if (nsUri.equals("##default")) {
/*     */       
/* 126 */       XmlSchema xs = (XmlSchema)reader().getPackageAnnotation(XmlSchema.class, clazz, this);
/* 127 */       if (xs != null) {
/* 128 */         nsUri = xs.namespace();
/*     */       } else {
/* 130 */         nsUri = this.builder.defaultNsUri;
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     return new QName(nsUri.intern(), local.intern());
/*     */   }
/*     */   
/*     */   protected final QName parseTypeName(ClassDeclT clazz) {
/* 138 */     return parseTypeName(clazz, (XmlType)reader().getClassAnnotation(XmlType.class, clazz, this));
/*     */   }
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
/*     */   protected final QName parseTypeName(ClassDeclT clazz, XmlType t) {
/* 153 */     String nsUri = "##default";
/* 154 */     String local = "##default";
/* 155 */     if (t != null) {
/* 156 */       nsUri = t.namespace();
/* 157 */       local = t.name();
/*     */     } 
/*     */     
/* 160 */     if (local.length() == 0) {
/* 161 */       return null;
/*     */     }
/*     */     
/* 164 */     if (local.equals("##default"))
/*     */     {
/* 166 */       local = NameConverter.standard.toVariableName(nav().getClassShortName(clazz));
/*     */     }
/* 168 */     if (nsUri.equals("##default")) {
/*     */       
/* 170 */       XmlSchema xs = (XmlSchema)reader().getPackageAnnotation(XmlSchema.class, clazz, this);
/* 171 */       if (xs != null) {
/* 172 */         nsUri = xs.namespace();
/*     */       } else {
/* 174 */         nsUri = this.builder.defaultNsUri;
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     return new QName(nsUri.intern(), local.intern());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\TypeInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */