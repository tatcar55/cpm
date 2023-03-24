/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.runtime.Location;
/*     */ import java.beans.Introspector;
/*     */ import java.lang.annotation.Annotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GetterSetterPropertySeed<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   implements PropertySeed<TypeT, ClassDeclT, FieldT, MethodT>
/*     */ {
/*     */   protected final MethodT getter;
/*     */   protected final MethodT setter;
/*     */   private ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> parent;
/*     */   
/*     */   GetterSetterPropertySeed(ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> parent, MethodT getter, MethodT setter) {
/*  64 */     this.parent = parent;
/*  65 */     this.getter = getter;
/*  66 */     this.setter = setter;
/*     */     
/*  68 */     if (getter == null && setter == null)
/*  69 */       throw new IllegalArgumentException(); 
/*     */   }
/*     */   
/*     */   public TypeT getRawType() {
/*  73 */     if (this.getter != null) {
/*  74 */       return (TypeT)this.parent.nav().getReturnType(this.getter);
/*     */     }
/*  76 */     return (TypeT)this.parent.nav().getMethodParameters(this.setter)[0];
/*     */   }
/*     */   
/*     */   public <A extends Annotation> A readAnnotation(Class<A> annotation) {
/*  80 */     return (A)this.parent.reader().getMethodAnnotation(annotation, this.getter, this.setter, this);
/*     */   }
/*     */   
/*     */   public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
/*  84 */     return this.parent.reader().hasMethodAnnotation(annotationType, getName(), this.getter, this.setter, this);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  88 */     if (this.getter != null) {
/*  89 */       return getName(this.getter);
/*     */     }
/*  91 */     return getName(this.setter);
/*     */   }
/*     */   
/*     */   private String getName(MethodT m) {
/*  95 */     String seed = this.parent.nav().getMethodName(m);
/*  96 */     String lseed = seed.toLowerCase();
/*  97 */     if (lseed.startsWith("get") || lseed.startsWith("set"))
/*  98 */       return camelize(seed.substring(3)); 
/*  99 */     if (lseed.startsWith("is"))
/* 100 */       return camelize(seed.substring(2)); 
/* 101 */     return seed;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String camelize(String s) {
/* 106 */     return Introspector.decapitalize(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locatable getUpstream() {
/* 113 */     return this.parent;
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 117 */     if (this.getter != null) {
/* 118 */       return this.parent.nav().getMethodLocation(this.getter);
/*     */     }
/* 120 */     return this.parent.nav().getMethodLocation(this.setter);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\GetterSetterPropertySeed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */