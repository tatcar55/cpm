/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.core.ID;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeAttributePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElement;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeValuePropertyInfo;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PropertyFactory
/*     */ {
/*     */   private static final Constructor<? extends Property>[] propImpls;
/*     */   
/*     */   static {
/*  72 */     Class[] arrayOfClass = { SingleElementLeafProperty.class, null, null, ArrayElementLeafProperty.class, null, null, SingleElementNodeProperty.class, SingleReferenceNodeProperty.class, SingleMapNodeProperty.class, ArrayElementNodeProperty.class, ArrayReferenceNodeProperty.class, null };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     propImpls = (Constructor<? extends Property>[])new Constructor[arrayOfClass.length];
/*  91 */     for (int i = 0; i < propImpls.length; i++) {
/*  92 */       if (arrayOfClass[i] != null)
/*     */       {
/*  94 */         propImpls[i] = (Constructor)arrayOfClass[i].getConstructors()[0];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Property create(JAXBContextImpl grammar, RuntimePropertyInfo info) {
/* 104 */     PropertyKind kind = info.kind();
/*     */     
/* 106 */     switch (kind) {
/*     */       case ATTRIBUTE:
/* 108 */         return new AttributeProperty(grammar, (RuntimeAttributePropertyInfo)info);
/*     */       case VALUE:
/* 110 */         return new ValueProperty(grammar, (RuntimeValuePropertyInfo)info);
/*     */       case ELEMENT:
/* 112 */         if (((RuntimeElementPropertyInfo)info).isValueList()) {
/* 113 */           return new ListElementProperty<Object, Object, Object>(grammar, (RuntimeElementPropertyInfo)info);
/*     */         }
/*     */         break;
/*     */       case REFERENCE:
/*     */       case MAP:
/*     */         break;
/*     */       default:
/*     */         assert false;
/*     */         break;
/*     */     } 
/* 123 */     boolean isCollection = info.isCollection();
/* 124 */     boolean isLeaf = isLeaf(info);
/*     */     
/* 126 */     Constructor<? extends Property> c = propImpls[(isLeaf ? 0 : 6) + (isCollection ? 3 : 0) + kind.propertyIndex];
/*     */     try {
/* 128 */       return c.newInstance(new Object[] { grammar, info });
/* 129 */     } catch (InstantiationException e) {
/* 130 */       throw new InstantiationError(e.getMessage());
/* 131 */     } catch (IllegalAccessException e) {
/* 132 */       throw new IllegalAccessError(e.getMessage());
/* 133 */     } catch (InvocationTargetException e) {
/* 134 */       Throwable t = e.getCause();
/* 135 */       if (t instanceof Error)
/* 136 */         throw (Error)t; 
/* 137 */       if (t instanceof RuntimeException) {
/* 138 */         throw (RuntimeException)t;
/*     */       }
/* 140 */       throw new AssertionError(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isLeaf(RuntimePropertyInfo info) {
/* 149 */     Collection<? extends RuntimeTypeInfo> types = info.ref();
/* 150 */     if (types.size() != 1) return false;
/*     */     
/* 152 */     RuntimeTypeInfo rti = types.iterator().next();
/* 153 */     if (!(rti instanceof RuntimeNonElement)) return false;
/*     */     
/* 155 */     if (info.id() == ID.IDREF)
/*     */     {
/* 157 */       return true;
/*     */     }
/* 159 */     if (((RuntimeNonElement)rti).getTransducer() == null)
/*     */     {
/*     */ 
/*     */       
/* 163 */       return false;
/*     */     }
/* 165 */     if (!info.getIndividualType().equals(rti.getType())) {
/* 166 */       return false;
/*     */     }
/* 168 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\PropertyFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */