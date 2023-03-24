/*     */ package com.sun.xml.ws.spi.db;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ public final class TypeInfo
/*     */ {
/*     */   public final QName tagName;
/*     */   public Type type;
/*     */   public final Annotation[] annotations;
/*  87 */   private Map<String, Object> properties = new HashMap<String, Object>();
/*     */   
/*     */   private boolean isGlobalElement = true;
/*     */   
/*     */   private TypeInfo parentCollectionType;
/*     */   
/*     */   private Type genericType;
/*     */   
/*     */   private boolean nillable = true;
/*     */   
/*     */   public TypeInfo(QName tagName, Type type, Annotation... annotations) {
/*  98 */     if (tagName == null || type == null || annotations == null) {
/*  99 */       String nullArgs = "";
/*     */       
/* 101 */       if (tagName == null) nullArgs = "tagName"; 
/* 102 */       if (type == null) nullArgs = nullArgs + ((nullArgs.length() > 0) ? ", type" : "type"); 
/* 103 */       if (annotations == null) nullArgs = nullArgs + ((nullArgs.length() > 0) ? ", annotations" : "annotations");
/*     */ 
/*     */ 
/*     */       
/* 107 */       throw new IllegalArgumentException("Argument(s) \"" + nullArgs + "\" can''t be null.)");
/*     */     } 
/*     */     
/* 110 */     this.tagName = new QName(tagName.getNamespaceURI().intern(), tagName.getLocalPart().intern(), tagName.getPrefix());
/* 111 */     this.type = type;
/* 112 */     if (type instanceof Class && ((Class)type).isPrimitive()) this.nillable = false; 
/* 113 */     this.annotations = annotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends Annotation> A get(Class<A> annotationType) {
/* 121 */     for (Annotation a : this.annotations) {
/* 122 */       if (a.annotationType() == annotationType)
/* 123 */         return annotationType.cast(a); 
/*     */     } 
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeInfo toItemType() {
/* 136 */     Type t = (this.genericType != null) ? this.genericType : this.type;
/* 137 */     Type base = Navigator.REFLECTION.getBaseClass(t, Collection.class);
/* 138 */     if (base == null) {
/* 139 */       return this;
/*     */     }
/* 141 */     return new TypeInfo(this.tagName, Navigator.REFLECTION.getTypeArgument(base, 0), new Annotation[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Object> properties() {
/* 146 */     return this.properties;
/*     */   }
/*     */   
/*     */   public boolean isGlobalElement() {
/* 150 */     return this.isGlobalElement;
/*     */   }
/*     */   
/*     */   public void setGlobalElement(boolean isGlobalElement) {
/* 154 */     this.isGlobalElement = isGlobalElement;
/*     */   }
/*     */   
/*     */   public TypeInfo getParentCollectionType() {
/* 158 */     return this.parentCollectionType;
/*     */   }
/*     */   
/*     */   public void setParentCollectionType(TypeInfo parentCollectionType) {
/* 162 */     this.parentCollectionType = parentCollectionType;
/*     */   }
/*     */   
/*     */   public boolean isRepeatedElement() {
/* 166 */     return (this.parentCollectionType != null);
/*     */   }
/*     */   
/*     */   public Type getGenericType() {
/* 170 */     return this.genericType;
/*     */   }
/*     */   
/*     */   public void setGenericType(Type genericType) {
/* 174 */     this.genericType = genericType;
/*     */   }
/*     */   
/*     */   public boolean isNillable() {
/* 178 */     return this.nillable;
/*     */   }
/*     */   
/*     */   public void setNillable(boolean nillable) {
/* 182 */     this.nillable = nillable;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 186 */     return "TypeInfo: Type = " + this.type + ", tag = " + this.tagName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeInfo getItemType() {
/* 192 */     if (this.type instanceof Class && ((Class)this.type).isArray() && !byte[].class.equals(this.type)) {
/* 193 */       Type<?> componentType = ((Class)this.type).getComponentType();
/* 194 */       Type genericComponentType = null;
/* 195 */       if (this.genericType != null && this.genericType instanceof GenericArrayType) {
/* 196 */         GenericArrayType arrayType = (GenericArrayType)this.type;
/* 197 */         genericComponentType = arrayType.getGenericComponentType();
/* 198 */         componentType = arrayType.getGenericComponentType();
/*     */       } 
/* 200 */       TypeInfo ti = new TypeInfo(this.tagName, componentType, this.annotations);
/* 201 */       if (genericComponentType != null) ti.setGenericType(genericComponentType); 
/* 202 */       return ti;
/*     */     } 
/*     */     
/* 205 */     Type t = (this.genericType != null) ? this.genericType : this.type;
/* 206 */     Type base = Navigator.REFLECTION.getBaseClass(t, Collection.class);
/* 207 */     if (base != null) {
/* 208 */       return new TypeInfo(this.tagName, Navigator.REFLECTION.getTypeArgument(base, 0), this.annotations);
/*     */     }
/* 210 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\TypeInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */