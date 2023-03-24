/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.core.ID;
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class SingleTypePropertyInfoImpl<T, C, F, M>
/*     */   extends PropertyInfoImpl<T, C, F, M>
/*     */ {
/*     */   private NonElement<T, C> type;
/*     */   private final Accessor acc;
/*     */   private Transducer xducer;
/*     */   
/*     */   public SingleTypePropertyInfoImpl(ClassInfoImpl<T, C, F, M> classInfo, PropertySeed<T, C, F, M> seed) {
/*  75 */     super(classInfo, seed);
/*  76 */     if (this instanceof RuntimePropertyInfo) {
/*  77 */       Accessor rawAcc = ((RuntimeClassInfoImpl.RuntimePropertySeed)seed).getAccessor();
/*  78 */       if (getAdapter() != null && !isCollection())
/*     */       {
/*     */         
/*  81 */         rawAcc = rawAcc.adapt(((RuntimePropertyInfo)this).getAdapter()); } 
/*  82 */       this.acc = rawAcc;
/*     */     } else {
/*  84 */       this.acc = null;
/*     */     } 
/*     */   }
/*     */   public List<? extends NonElement<T, C>> ref() {
/*  88 */     return Collections.singletonList(getTarget());
/*     */   }
/*     */   
/*     */   public NonElement<T, C> getTarget() {
/*  92 */     if (this.type == null) {
/*  93 */       assert this.parent.builder != null : "this method must be called during the build stage";
/*  94 */       this.type = this.parent.builder.getTypeInfo(getIndividualType(), this);
/*     */     } 
/*  96 */     return this.type;
/*     */   }
/*     */   
/*     */   public PropertyInfo<T, C> getSource() {
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public void link() {
/* 104 */     super.link();
/*     */     
/* 106 */     if (!NonElement.ANYTYPE_NAME.equals(this.type.getTypeName()) && !this.type.isSimpleType() && id() != ID.IDREF) {
/* 107 */       this.parent.builder.reportError(new IllegalAnnotationException(Messages.SIMPLE_TYPE_IS_REQUIRED.format(new Object[0]), this.seed));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (!isCollection() && this.seed.hasAnnotation(XmlList.class)) {
/* 114 */       this.parent.builder.reportError(new IllegalAnnotationException(Messages.XMLLIST_ON_SINGLE_PROPERTY.format(new Object[0]), this));
/*     */     }
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
/*     */   public Accessor getAccessor() {
/* 134 */     return this.acc;
/*     */   }
/*     */ 
/*     */   
/*     */   public Transducer getTransducer() {
/* 139 */     if (this.xducer == null) {
/* 140 */       this.xducer = RuntimeModelBuilder.createTransducer((RuntimeNonElementRef)this);
/* 141 */       if (this.xducer == null)
/*     */       {
/*     */         
/* 144 */         this.xducer = RuntimeBuiltinLeafInfoImpl.STRING;
/*     */       }
/*     */     } 
/* 147 */     return this.xducer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\SingleTypePropertyInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */