/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.core.Adapter;
/*     */ import com.sun.xml.bind.v2.model.core.ClassInfo;
/*     */ import com.sun.xml.bind.v2.model.core.ElementPropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeClassInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElement;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeRef;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RuntimeElementInfoImpl
/*     */   extends ElementInfoImpl<Type, Class, Field, Method>
/*     */   implements RuntimeElementInfo
/*     */ {
/*     */   private final Class<? extends XmlAdapter> adapterType;
/*     */   
/*     */   public RuntimeElementInfoImpl(RuntimeModelBuilder modelBuilder, RegistryInfoImpl<Type, Class, Field, Method> registry, Method method) throws IllegalAnnotationException {
/*  72 */     super(modelBuilder, registry, method);
/*     */     
/*  74 */     Adapter<Type, Class<?>> a = getProperty().getAdapter();
/*     */     
/*  76 */     if (a != null) {
/*  77 */       this.adapterType = (Class<? extends XmlAdapter>)a.adapterType;
/*     */     } else {
/*  79 */       this.adapterType = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ElementInfoImpl<Type, Class, Field, Method>.PropertyImpl createPropertyImpl() {
/*  84 */     return new RuntimePropertyImpl();
/*     */   }
/*     */   
/*     */   class RuntimePropertyImpl extends ElementInfoImpl.PropertyImpl implements RuntimeElementPropertyInfo, RuntimeTypeRef {
/*     */     public Accessor getAccessor() {
/*  89 */       if (RuntimeElementInfoImpl.this.adapterType == null) {
/*  90 */         return Accessor.JAXB_ELEMENT_VALUE;
/*     */       }
/*  92 */       return Accessor.JAXB_ELEMENT_VALUE.adapt((Class)(getAdapter()).defaultType, RuntimeElementInfoImpl.this.adapterType);
/*     */     }
/*     */ 
/*     */     
/*     */     public Type getRawType() {
/*  97 */       return Collection.class;
/*     */     }
/*     */     
/*     */     public Type getIndividualType() {
/* 101 */       return (Type)RuntimeElementInfoImpl.this.getContentType().getType();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean elementOnlyContent() {
/* 106 */       return false;
/*     */     }
/*     */     
/*     */     public List<? extends RuntimeTypeRef> getTypes() {
/* 110 */       return Collections.singletonList(this);
/*     */     }
/*     */     
/*     */     public List<? extends RuntimeNonElement> ref() {
/* 114 */       return (List)super.ref();
/*     */     }
/*     */     
/*     */     public RuntimeNonElement getTarget() {
/* 118 */       return (RuntimeNonElement)super.getTarget();
/*     */     }
/*     */     
/*     */     public RuntimePropertyInfo getSource() {
/* 122 */       return (RuntimePropertyInfo)this;
/*     */     }
/*     */     
/*     */     public Transducer getTransducer() {
/* 126 */       return RuntimeModelBuilder.createTransducer((RuntimeNonElementRef)this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RuntimeElementPropertyInfo getProperty() {
/* 136 */     return (RuntimeElementPropertyInfo)super.getProperty();
/*     */   }
/*     */   
/*     */   public Class<? extends JAXBElement> getType() {
/* 140 */     return Navigator.REFLECTION.erasure(super.getType());
/*     */   }
/*     */   
/*     */   public RuntimeClassInfo getScope() {
/* 144 */     return (RuntimeClassInfo)super.getScope();
/*     */   }
/*     */   
/*     */   public RuntimeNonElement getContentType() {
/* 148 */     return (RuntimeNonElement)super.getContentType();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeElementInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */