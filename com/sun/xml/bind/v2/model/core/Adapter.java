/*     */ package com.sun.xml.bind.v2.model.core;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Adapter<TypeT, ClassDeclT>
/*     */ {
/*     */   public final ClassDeclT adapterType;
/*     */   public final TypeT defaultType;
/*     */   public final TypeT customType;
/*     */   
/*     */   public Adapter(XmlJavaTypeAdapter spec, AnnotationReader<TypeT, ClassDeclT, ?, ?> reader, Navigator<TypeT, ClassDeclT, ?, ?> nav) {
/*  83 */     this((ClassDeclT)nav.asDecl(reader.getClassValue(spec, "value")), nav);
/*     */   }
/*     */   
/*     */   public Adapter(ClassDeclT adapterType, Navigator<TypeT, ClassDeclT, ?, ?> nav) {
/*  87 */     this.adapterType = adapterType;
/*  88 */     TypeT baseClass = (TypeT)nav.getBaseClass(nav.use(adapterType), nav.asDecl(XmlAdapter.class));
/*     */ 
/*     */     
/*  91 */     assert baseClass != null;
/*     */     
/*  93 */     if (nav.isParameterizedType(baseClass)) {
/*  94 */       this.defaultType = (TypeT)nav.getTypeArgument(baseClass, 0);
/*     */     } else {
/*  96 */       this.defaultType = (TypeT)nav.ref(Object.class);
/*     */     } 
/*  98 */     if (nav.isParameterizedType(baseClass)) {
/*  99 */       this.customType = (TypeT)nav.getTypeArgument(baseClass, 1);
/*     */     } else {
/* 101 */       this.customType = (TypeT)nav.ref(Object.class);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\Adapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */