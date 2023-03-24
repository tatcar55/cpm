/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.core.MapPropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.core.TypeInfo;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MapPropertyInfoImpl<T, C, F, M>
/*     */   extends PropertyInfoImpl<T, C, F, M>
/*     */   implements MapPropertyInfo<T, C>
/*     */ {
/*     */   private final QName xmlName;
/*     */   private boolean nil;
/*     */   private final T keyType;
/*     */   private final T valueType;
/*     */   private NonElement<T, C> keyTypeInfo;
/*     */   private NonElement<T, C> valueTypeInfo;
/*     */   
/*     */   public MapPropertyInfoImpl(ClassInfoImpl<T, C, F, M> ci, PropertySeed<T, C, F, M> seed) {
/*  71 */     super(ci, seed);
/*     */     
/*  73 */     XmlElementWrapper xe = (XmlElementWrapper)seed.readAnnotation(XmlElementWrapper.class);
/*  74 */     this.xmlName = calcXmlName(xe);
/*  75 */     this.nil = (xe != null && xe.nillable());
/*     */     
/*  77 */     T raw = getRawType();
/*  78 */     T bt = (T)nav().getBaseClass(raw, nav().asDecl(Map.class));
/*  79 */     assert bt != null;
/*     */     
/*  81 */     if (nav().isParameterizedType(bt)) {
/*  82 */       this.keyType = (T)nav().getTypeArgument(bt, 0);
/*  83 */       this.valueType = (T)nav().getTypeArgument(bt, 1);
/*     */     } else {
/*  85 */       this.keyType = this.valueType = (T)nav().ref(Object.class);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection<? extends TypeInfo<T, C>> ref() {
/*  90 */     return (Collection)Arrays.asList((Object[])new NonElement[] { getKeyType(), getValueType() });
/*     */   }
/*     */   
/*     */   public final PropertyKind kind() {
/*  94 */     return PropertyKind.MAP;
/*     */   }
/*     */   
/*     */   public QName getXmlName() {
/*  98 */     return this.xmlName;
/*     */   }
/*     */   
/*     */   public boolean isCollectionNillable() {
/* 102 */     return this.nil;
/*     */   }
/*     */   
/*     */   public NonElement<T, C> getKeyType() {
/* 106 */     if (this.keyTypeInfo == null)
/* 107 */       this.keyTypeInfo = getTarget(this.keyType); 
/* 108 */     return this.keyTypeInfo;
/*     */   }
/*     */   
/*     */   public NonElement<T, C> getValueType() {
/* 112 */     if (this.valueTypeInfo == null)
/* 113 */       this.valueTypeInfo = getTarget(this.valueType); 
/* 114 */     return this.valueTypeInfo;
/*     */   }
/*     */   
/*     */   public NonElement<T, C> getTarget(T type) {
/* 118 */     assert this.parent.builder != null : "this method must be called during the build stage";
/* 119 */     return this.parent.builder.getTypeInfo(type, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\MapPropertyInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */