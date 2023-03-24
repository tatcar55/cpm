/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.xml.bind.v2.model.core.ElementPropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.core.ID;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElements;
/*     */ import javax.xml.bind.annotation.XmlList;
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
/*     */ class ElementPropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   extends ERPropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   implements ElementPropertyInfo<TypeT, ClassDeclT>
/*     */ {
/*     */   private List<TypeRefImpl<TypeT, ClassDeclT>> types;
/*     */   
/*  76 */   private final List<TypeInfo<TypeT, ClassDeclT>> ref = new AbstractList<TypeInfo<TypeT, ClassDeclT>>() {
/*     */       public TypeInfo<TypeT, ClassDeclT> get(int index) {
/*  78 */         return (TypeInfo<TypeT, ClassDeclT>)((TypeRefImpl)ElementPropertyInfoImpl.this.getTypes().get(index)).getTarget();
/*     */       }
/*     */       
/*     */       public int size() {
/*  82 */         return ElementPropertyInfoImpl.this.getTypes().size();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Boolean isRequired;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isValueList;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ElementPropertyInfoImpl(ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> parent, PropertySeed<TypeT, ClassDeclT, FieldT, MethodT> propertySeed) {
/* 100 */     super(parent, propertySeed);
/*     */     
/* 102 */     this.isValueList = this.seed.hasAnnotation(XmlList.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends TypeRefImpl<TypeT, ClassDeclT>> getTypes() {
/* 107 */     if (this.types == null) {
/* 108 */       this.types = (List<TypeRefImpl<TypeT, ClassDeclT>>)new FinalArrayList();
/* 109 */       XmlElement[] ann = null;
/*     */       
/* 111 */       XmlElement xe = (XmlElement)this.seed.readAnnotation(XmlElement.class);
/* 112 */       XmlElements xes = (XmlElements)this.seed.readAnnotation(XmlElements.class);
/*     */       
/* 114 */       if (xe != null && xes != null) {
/* 115 */         this.parent.builder.reportError(new IllegalAnnotationException(Messages.MUTUALLY_EXCLUSIVE_ANNOTATIONS.format(new Object[] { nav().getClassName(this.parent.getClazz()) + '#' + this.seed.getName(), xe.annotationType().getName(), xes.annotationType().getName() }, ), xe, xes));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       this.isRequired = Boolean.valueOf(true);
/*     */       
/* 124 */       if (xe != null) {
/* 125 */         ann = new XmlElement[] { xe };
/*     */       }
/* 127 */       else if (xes != null) {
/* 128 */         ann = xes.value();
/*     */       } 
/* 130 */       if (ann == null) {
/*     */         
/* 132 */         TypeT t = getIndividualType();
/* 133 */         if (!nav().isPrimitive(t) || isCollection()) {
/* 134 */           this.isRequired = Boolean.valueOf(false);
/*     */         }
/* 136 */         this.types.add(createTypeRef(calcXmlName((XmlElement)null), t, isCollection(), (String)null));
/*     */       } else {
/* 138 */         for (XmlElement item : ann) {
/*     */           
/* 140 */           QName name = calcXmlName(item);
/* 141 */           TypeT type = (TypeT)reader().getClassValue(item, "type");
/* 142 */           if (nav().isSameType(type, nav().ref(XmlElement.DEFAULT.class)))
/* 143 */             type = getIndividualType(); 
/* 144 */           if ((!nav().isPrimitive(type) || isCollection()) && !item.required())
/* 145 */             this.isRequired = Boolean.valueOf(false); 
/* 146 */           this.types.add(createTypeRef(name, type, item.nillable(), getDefaultValue(item.defaultValue())));
/*     */         } 
/*     */       } 
/* 149 */       this.types = Collections.unmodifiableList(this.types);
/* 150 */       assert !this.types.contains(null);
/*     */     } 
/* 152 */     return this.types;
/*     */   }
/*     */   
/*     */   private String getDefaultValue(String value) {
/* 156 */     if (value.equals("\000")) {
/* 157 */       return null;
/*     */     }
/* 159 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TypeRefImpl<TypeT, ClassDeclT> createTypeRef(QName name, TypeT type, boolean isNillable, String defaultValue) {
/* 166 */     return new TypeRefImpl<TypeT, ClassDeclT>(this, name, type, isNillable, defaultValue);
/*     */   }
/*     */   
/*     */   public boolean isValueList() {
/* 170 */     return this.isValueList;
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/* 174 */     if (this.isRequired == null)
/* 175 */       getTypes(); 
/* 176 */     return this.isRequired.booleanValue();
/*     */   }
/*     */   
/*     */   public List<? extends TypeInfo<TypeT, ClassDeclT>> ref() {
/* 180 */     return this.ref;
/*     */   }
/*     */   
/*     */   public final PropertyKind kind() {
/* 184 */     return PropertyKind.ELEMENT;
/*     */   }
/*     */   
/*     */   protected void link() {
/* 188 */     super.link();
/* 189 */     for (TypeRefImpl<TypeT, ClassDeclT> ref : getTypes()) {
/* 190 */       ref.link();
/*     */     }
/*     */     
/* 193 */     if (isValueList()) {
/*     */ 
/*     */       
/* 196 */       if (id() != ID.IDREF)
/*     */       {
/*     */ 
/*     */         
/* 200 */         for (TypeRefImpl<TypeT, ClassDeclT> ref : this.types) {
/* 201 */           if (!ref.getTarget().isSimpleType()) {
/* 202 */             this.parent.builder.reportError(new IllegalAnnotationException(Messages.XMLLIST_NEEDS_SIMPLETYPE.format(new Object[] { nav().getTypeName(ref.getTarget().getType()) }, ), this));
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 210 */       if (!isCollection())
/* 211 */         this.parent.builder.reportError(new IllegalAnnotationException(Messages.XMLLIST_ON_SINGLE_PROPERTY.format(new Object[0]), this)); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\ElementPropertyInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */