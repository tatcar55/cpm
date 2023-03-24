/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.model.annotation.MethodLocatable;
/*     */ import com.sun.xml.bind.v2.model.core.RegistryInfo;
/*     */ import com.sun.xml.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.bind.v2.runtime.Location;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RegistryInfoImpl<T, C, F, M>
/*     */   implements Locatable, RegistryInfo<T, C>
/*     */ {
/*     */   final C registryClass;
/*     */   private final Locatable upstream;
/*     */   private final Navigator<T, C, F, M> nav;
/*  74 */   private final Set<TypeInfo<T, C>> references = new LinkedHashSet<TypeInfo<T, C>>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RegistryInfoImpl(ModelBuilder<T, C, F, M> builder, Locatable upstream, C registryClass) {
/*  80 */     this.nav = builder.nav;
/*  81 */     this.registryClass = registryClass;
/*  82 */     this.upstream = upstream;
/*  83 */     builder.registries.put(getPackageName(), this);
/*     */     
/*  85 */     if (this.nav.getDeclaredField(registryClass, "_useJAXBProperties") != null) {
/*     */ 
/*     */       
/*  88 */       builder.reportError(new IllegalAnnotationException(Messages.MISSING_JAXB_PROPERTIES.format(new Object[] { getPackageName() }, ), this));
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  96 */     for (M m : this.nav.getDeclaredMethods(registryClass)) {
/*  97 */       ElementInfoImpl<T, C, F, M> ei; XmlElementDecl em = (XmlElementDecl)builder.reader.getMethodAnnotation(XmlElementDecl.class, m, this);
/*     */ 
/*     */       
/* 100 */       if (em == null) {
/* 101 */         if (this.nav.getMethodName(m).startsWith("create"))
/*     */         {
/* 103 */           this.references.add((TypeInfo<T, C>)builder.getTypeInfo((T)this.nav.getReturnType(m), (Locatable)new MethodLocatable(this, m, this.nav)));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 113 */         ei = builder.createElementInfo(this, m);
/* 114 */       } catch (IllegalAnnotationException e) {
/* 115 */         builder.reportError(e);
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 121 */       builder.typeInfoSet.add(ei, builder);
/* 122 */       this.references.add(ei);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Locatable getUpstream() {
/* 127 */     return this.upstream;
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 131 */     return this.nav.getClassLocation(this.registryClass);
/*     */   }
/*     */   
/*     */   public Set<TypeInfo<T, C>> getReferences() {
/* 135 */     return this.references;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPackageName() {
/* 142 */     return this.nav.getPackageName(this.registryClass);
/*     */   }
/*     */   
/*     */   public C getClazz() {
/* 146 */     return this.registryClass;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RegistryInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */