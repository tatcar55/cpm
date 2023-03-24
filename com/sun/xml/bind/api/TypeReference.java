/*     */ package com.sun.xml.bind.api;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TypeReference
/*     */ {
/*     */   public final QName tagName;
/*     */   public final Type type;
/*     */   public final Annotation[] annotations;
/*     */   
/*     */   public TypeReference(QName tagName, Type type, Annotation... annotations) {
/*  85 */     if (tagName == null || type == null || annotations == null) {
/*  86 */       String nullArgs = "";
/*     */       
/*  88 */       if (tagName == null) nullArgs = "tagName"; 
/*  89 */       if (type == null) nullArgs = nullArgs + ((nullArgs.length() > 0) ? ", type" : "type"); 
/*  90 */       if (annotations == null) nullArgs = nullArgs + ((nullArgs.length() > 0) ? ", annotations" : "annotations");
/*     */       
/*  92 */       Messages.ARGUMENT_CANT_BE_NULL.format(new Object[] { nullArgs });
/*     */       
/*  94 */       throw new IllegalArgumentException(Messages.ARGUMENT_CANT_BE_NULL.format(new Object[] { nullArgs }));
/*     */     } 
/*     */     
/*  97 */     this.tagName = new QName(tagName.getNamespaceURI().intern(), tagName.getLocalPart().intern(), tagName.getPrefix());
/*  98 */     this.type = type;
/*  99 */     this.annotations = annotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends Annotation> A get(Class<A> annotationType) {
/* 107 */     for (Annotation a : this.annotations) {
/* 108 */       if (a.annotationType() == annotationType)
/* 109 */         return annotationType.cast(a); 
/*     */     } 
/* 111 */     return null;
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
/*     */   public TypeReference toItemType() {
/* 123 */     Type base = Navigator.REFLECTION.getBaseClass(this.type, Collection.class);
/* 124 */     if (base == null) {
/* 125 */       return this;
/*     */     }
/* 127 */     return new TypeReference(this.tagName, Navigator.REFLECTION.getTypeArgument(base, 0), new Annotation[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 133 */     if (this == o) return true; 
/* 134 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 136 */     TypeReference that = (TypeReference)o;
/*     */     
/* 138 */     if (!Arrays.equals((Object[])this.annotations, (Object[])that.annotations)) return false; 
/* 139 */     if (!this.tagName.equals(that.tagName)) return false; 
/* 140 */     if (!this.type.equals(that.type)) return false;
/*     */     
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 147 */     int result = this.tagName.hashCode();
/* 148 */     result = 31 * result + this.type.hashCode();
/* 149 */     result = 31 * result + Arrays.hashCode((Object[])this.annotations);
/* 150 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\api\TypeReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */