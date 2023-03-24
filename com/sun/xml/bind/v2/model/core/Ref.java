/*     */ package com.sun.xml.bind.v2.model.core;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.impl.ModelBuilderI;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import javax.xml.bind.annotation.XmlList;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Ref<T, C>
/*     */ {
/*     */   public final T type;
/*     */   public final Adapter<T, C> adapter;
/*     */   public final boolean valueList;
/*     */   
/*     */   public Ref(T type) {
/*  75 */     this(type, null, false);
/*     */   }
/*     */   public Ref(T type, Adapter<T, C> adapter, boolean valueList) {
/*     */     TypeT typeT;
/*  79 */     this.adapter = adapter;
/*  80 */     if (adapter != null)
/*  81 */       typeT = adapter.defaultType; 
/*  82 */     this.type = (T)typeT;
/*  83 */     this.valueList = valueList;
/*     */   }
/*     */   
/*     */   public Ref(ModelBuilderI<T, C, ?, ?> builder, T type, XmlJavaTypeAdapter xjta, XmlList xl) {
/*  87 */     this(builder.getReader(), builder.getNavigator(), type, xjta, xl);
/*     */   }
/*     */ 
/*     */   
/*     */   public Ref(AnnotationReader<T, C, ?, ?> reader, Navigator<T, C, ?, ?> nav, T type, XmlJavaTypeAdapter xjta, XmlList xl) {
/*     */     TypeT typeT;
/*  93 */     Adapter<T, C> adapter = null;
/*  94 */     if (xjta != null) {
/*  95 */       adapter = new Adapter<T, C>(xjta, reader, nav);
/*  96 */       typeT = adapter.defaultType;
/*     */     } 
/*     */     
/*  99 */     this.type = (T)typeT;
/* 100 */     this.adapter = adapter;
/* 101 */     this.valueList = (xl != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\Ref.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */