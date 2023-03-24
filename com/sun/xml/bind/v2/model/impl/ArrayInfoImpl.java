/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.model.core.ArrayInfo;
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.model.util.ArrayInfoUtil;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.bind.v2.runtime.Location;
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
/*     */ public class ArrayInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   extends TypeInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   implements ArrayInfo<TypeT, ClassDeclT>, Location
/*     */ {
/*     */   private final NonElement<TypeT, ClassDeclT> itemType;
/*     */   private final QName typeName;
/*     */   private final TypeT arrayType;
/*     */   
/*     */   public ArrayInfoImpl(ModelBuilder<TypeT, ClassDeclT, FieldT, MethodT> builder, Locatable upstream, TypeT arrayType) {
/*  74 */     super(builder, upstream);
/*  75 */     this.arrayType = arrayType;
/*  76 */     TypeT componentType = (TypeT)nav().getComponentType(arrayType);
/*  77 */     this.itemType = builder.getTypeInfo(componentType, this);
/*     */     
/*  79 */     QName n = this.itemType.getTypeName();
/*  80 */     if (n == null) {
/*  81 */       builder.reportError(new IllegalAnnotationException(Messages.ANONYMOUS_ARRAY_ITEM.format(new Object[] { nav().getTypeName(componentType) }, ), this));
/*     */       
/*  83 */       n = new QName("#dummy");
/*     */     } 
/*  85 */     this.typeName = ArrayInfoUtil.calcArrayTypeName(n);
/*     */   }
/*     */   
/*     */   public NonElement<TypeT, ClassDeclT> getItemType() {
/*  89 */     return this.itemType;
/*     */   }
/*     */   
/*     */   public QName getTypeName() {
/*  93 */     return this.typeName;
/*     */   }
/*     */   
/*     */   public boolean isSimpleType() {
/*  97 */     return false;
/*     */   }
/*     */   
/*     */   public TypeT getType() {
/* 101 */     return this.arrayType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean canBeReferencedByIDREF() {
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 115 */     return this;
/*     */   }
/*     */   public String toString() {
/* 118 */     return nav().getTypeName(this.arrayType);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\ArrayInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */