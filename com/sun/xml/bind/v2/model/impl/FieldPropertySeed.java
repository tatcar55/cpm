/*    */ package com.sun.xml.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*    */ import com.sun.xml.bind.v2.runtime.Location;
/*    */ import java.lang.annotation.Annotation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class FieldPropertySeed<TypeT, ClassDeclT, FieldT, MethodT>
/*    */   implements PropertySeed<TypeT, ClassDeclT, FieldT, MethodT>
/*    */ {
/*    */   protected final FieldT field;
/*    */   private ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> parent;
/*    */   
/*    */   FieldPropertySeed(ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> classInfo, FieldT field) {
/* 59 */     this.parent = classInfo;
/* 60 */     this.field = field;
/*    */   }
/*    */   
/*    */   public <A extends Annotation> A readAnnotation(Class<A> a) {
/* 64 */     return (A)this.parent.reader().getFieldAnnotation(a, this.field, this);
/*    */   }
/*    */   
/*    */   public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
/* 68 */     return this.parent.reader().hasFieldAnnotation(annotationType, this.field);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 74 */     return this.parent.nav().getFieldName(this.field);
/*    */   }
/*    */   
/*    */   public TypeT getRawType() {
/* 78 */     return (TypeT)this.parent.nav().getFieldType(this.field);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Locatable getUpstream() {
/* 85 */     return this.parent;
/*    */   }
/*    */   
/*    */   public Location getLocation() {
/* 89 */     return this.parent.nav().getFieldLocation(this.field);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\FieldPropertySeed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */