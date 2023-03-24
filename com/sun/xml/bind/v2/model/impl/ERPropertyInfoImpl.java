/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import javax.xml.bind.annotation.XmlElementWrapper;
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
/*     */ abstract class ERPropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   extends PropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */ {
/*     */   private final QName xmlName;
/*     */   private final boolean wrapperNillable;
/*     */   private final boolean wrapperRequired;
/*     */   
/*     */   public ERPropertyInfoImpl(ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> classInfo, PropertySeed<TypeT, ClassDeclT, FieldT, MethodT> propertySeed) {
/*  57 */     super(classInfo, propertySeed);
/*     */     
/*  59 */     XmlElementWrapper e = (XmlElementWrapper)this.seed.readAnnotation(XmlElementWrapper.class);
/*     */     
/*  61 */     boolean nil = false;
/*  62 */     boolean required = false;
/*  63 */     if (!isCollection()) {
/*  64 */       this.xmlName = null;
/*  65 */       if (e != null) {
/*  66 */         classInfo.builder.reportError(new IllegalAnnotationException(Messages.XML_ELEMENT_WRAPPER_ON_NON_COLLECTION.format(new Object[] { nav().getClassName(this.parent.getClazz()) + '.' + this.seed.getName() }, ), e));
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/*  72 */     else if (e != null) {
/*  73 */       this.xmlName = calcXmlName(e);
/*  74 */       nil = e.nillable();
/*  75 */       required = e.required();
/*     */     } else {
/*  77 */       this.xmlName = null;
/*     */     } 
/*     */     
/*  80 */     this.wrapperNillable = nil;
/*  81 */     this.wrapperRequired = required;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName getXmlName() {
/* 100 */     return this.xmlName;
/*     */   }
/*     */   
/*     */   public final boolean isCollectionNillable() {
/* 104 */     return this.wrapperNillable;
/*     */   }
/*     */   
/*     */   public final boolean isCollectionRequired() {
/* 108 */     return this.wrapperRequired;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\ERPropertyInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */