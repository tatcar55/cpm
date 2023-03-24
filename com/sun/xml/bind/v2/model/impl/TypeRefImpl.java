/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.core.TypeRef;
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
/*     */ class TypeRefImpl<TypeT, ClassDeclT>
/*     */   implements TypeRef<TypeT, ClassDeclT>
/*     */ {
/*     */   private final QName elementName;
/*     */   private final TypeT type;
/*     */   protected final ElementPropertyInfoImpl<TypeT, ClassDeclT, ?, ?> owner;
/*     */   private NonElement<TypeT, ClassDeclT> ref;
/*     */   private final boolean isNillable;
/*     */   private String defaultValue;
/*     */   
/*     */   public TypeRefImpl(ElementPropertyInfoImpl<TypeT, ClassDeclT, ?, ?> owner, QName elementName, TypeT type, boolean isNillable, String defaultValue) {
/*  61 */     this.owner = owner;
/*  62 */     this.elementName = elementName;
/*  63 */     this.type = type;
/*  64 */     this.isNillable = isNillable;
/*  65 */     this.defaultValue = defaultValue;
/*  66 */     assert owner != null;
/*  67 */     assert elementName != null;
/*  68 */     assert type != null;
/*     */   }
/*     */   
/*     */   public NonElement<TypeT, ClassDeclT> getTarget() {
/*  72 */     if (this.ref == null)
/*  73 */       calcRef(); 
/*  74 */     return this.ref;
/*     */   }
/*     */   
/*     */   public QName getTagName() {
/*  78 */     return this.elementName;
/*     */   }
/*     */   
/*     */   public boolean isNillable() {
/*  82 */     return this.isNillable;
/*     */   }
/*     */   
/*     */   public String getDefaultValue() {
/*  86 */     return this.defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void link() {
/*  91 */     calcRef();
/*     */   }
/*     */ 
/*     */   
/*     */   private void calcRef() {
/*  96 */     this.ref = this.owner.parent.builder.getTypeInfo(this.type, this.owner);
/*  97 */     assert this.ref != null;
/*     */   }
/*     */   
/*     */   public PropertyInfo<TypeT, ClassDeclT> getSource() {
/* 101 */     return this.owner;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\TypeRefImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */