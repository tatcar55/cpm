/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.model.core.ClassInfo;
/*     */ import com.sun.xml.bind.v2.model.core.Element;
/*     */ import com.sun.xml.bind.v2.model.core.EnumLeafInfo;
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.runtime.Location;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.bind.annotation.XmlEnum;
/*     */ import javax.xml.bind.annotation.XmlEnumValue;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EnumLeafInfoImpl<T, C, F, M>
/*     */   extends TypeInfoImpl<T, C, F, M>
/*     */   implements EnumLeafInfo<T, C>, Element<T, C>, Iterable<EnumConstantImpl<T, C, F, M>>
/*     */ {
/*     */   final C clazz;
/*     */   NonElement<T, C> baseType;
/*     */   private final T type;
/*     */   private final QName typeName;
/*     */   private EnumConstantImpl<T, C, F, M> firstConstant;
/*     */   private QName elementName;
/*     */   protected boolean tokenStringType;
/*     */   
/*     */   public EnumLeafInfoImpl(ModelBuilder<T, C, F, M> builder, Locatable upstream, C clazz, T type) {
/* 107 */     super(builder, upstream);
/* 108 */     this.clazz = clazz;
/* 109 */     this.type = type;
/*     */     
/* 111 */     this.elementName = parseElementName(clazz);
/*     */ 
/*     */ 
/*     */     
/* 115 */     this.typeName = parseTypeName(clazz);
/*     */ 
/*     */ 
/*     */     
/* 119 */     XmlEnum xe = (XmlEnum)builder.reader.getClassAnnotation(XmlEnum.class, clazz, this);
/* 120 */     if (xe != null) {
/* 121 */       T base = (T)builder.reader.getClassValue(xe, "value");
/* 122 */       this.baseType = builder.getTypeInfo(base, this);
/*     */     } else {
/* 124 */       this.baseType = builder.getTypeInfo((T)builder.nav.ref(String.class), this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calcConstants() {
/* 132 */     EnumConstantImpl<T, C, F, M> last = null;
/*     */ 
/*     */     
/* 135 */     Collection<? extends F> fields = nav().getDeclaredFields(this.clazz);
/* 136 */     for (F f : fields) {
/* 137 */       if (nav().isSameType(nav().getFieldType(f), nav().ref(String.class))) {
/* 138 */         XmlSchemaType schemaTypeAnnotation = (XmlSchemaType)this.builder.reader.getFieldAnnotation(XmlSchemaType.class, f, this);
/* 139 */         if (schemaTypeAnnotation != null && 
/* 140 */           "token".equals(schemaTypeAnnotation.name())) {
/* 141 */           this.tokenStringType = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 147 */     F[] constants = (F[])nav().getEnumConstants(this.clazz);
/* 148 */     for (int i = constants.length - 1; i >= 0; i--) {
/* 149 */       String literal; F constant = constants[i];
/* 150 */       String name = nav().getFieldName(constant);
/* 151 */       XmlEnumValue xev = (XmlEnumValue)this.builder.reader.getFieldAnnotation(XmlEnumValue.class, constant, this);
/*     */ 
/*     */       
/* 154 */       if (xev == null) { literal = name; }
/* 155 */       else { literal = xev.value(); }
/*     */       
/* 157 */       last = createEnumConstant(name, literal, constant, last);
/*     */     } 
/* 159 */     this.firstConstant = last;
/*     */   }
/*     */   
/*     */   protected EnumConstantImpl<T, C, F, M> createEnumConstant(String name, String literal, F constant, EnumConstantImpl<T, C, F, M> last) {
/* 163 */     return new EnumConstantImpl<T, C, F, M>(this, name, literal, last);
/*     */   }
/*     */ 
/*     */   
/*     */   public T getType() {
/* 168 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isToken() {
/* 176 */     return this.tokenStringType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean canBeReferencedByIDREF() {
/* 186 */     return false;
/*     */   }
/*     */   
/*     */   public QName getTypeName() {
/* 190 */     return this.typeName;
/*     */   }
/*     */   
/*     */   public C getClazz() {
/* 194 */     return this.clazz;
/*     */   }
/*     */   
/*     */   public NonElement<T, C> getBaseType() {
/* 198 */     return this.baseType;
/*     */   }
/*     */   
/*     */   public boolean isSimpleType() {
/* 202 */     return true;
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 206 */     return nav().getClassLocation(this.clazz);
/*     */   }
/*     */   
/*     */   public Iterable<? extends EnumConstantImpl<T, C, F, M>> getConstants() {
/* 210 */     if (this.firstConstant == null)
/* 211 */       calcConstants(); 
/* 212 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void link() {
/* 218 */     getConstants();
/* 219 */     super.link();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element<T, C> getSubstitutionHead() {
/* 228 */     return null;
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/* 232 */     return this.elementName;
/*     */   }
/*     */   
/*     */   public boolean isElement() {
/* 236 */     return (this.elementName != null);
/*     */   }
/*     */   
/*     */   public Element<T, C> asElement() {
/* 240 */     if (isElement()) {
/* 241 */       return this;
/*     */     }
/* 243 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassInfo<T, C> getScope() {
/* 254 */     return null;
/*     */   }
/*     */   
/*     */   public Iterator<EnumConstantImpl<T, C, F, M>> iterator() {
/* 258 */     return new Iterator<EnumConstantImpl<T, C, F, M>>() {
/* 259 */         private EnumConstantImpl<T, C, F, M> next = EnumLeafInfoImpl.this.firstConstant;
/*     */         public boolean hasNext() {
/* 261 */           return (this.next != null);
/*     */         }
/*     */         
/*     */         public EnumConstantImpl<T, C, F, M> next() {
/* 265 */           EnumConstantImpl<T, C, F, M> r = this.next;
/* 266 */           this.next = this.next.next;
/* 267 */           return r;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 271 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\EnumLeafInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */