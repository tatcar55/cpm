/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.annotation.FieldLocatable;
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeEnumLeafInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElement;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RuntimeEnumLeafInfoImpl<T extends Enum<T>, B>
/*     */   extends EnumLeafInfoImpl<Type, Class, Field, Method>
/*     */   implements RuntimeEnumLeafInfo, Transducer<T>
/*     */ {
/*     */   private final Transducer<B> baseXducer;
/*     */   
/*     */   public Transducer<T> getTransducer() {
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private final Map<B, T> parseMap = new HashMap<B, T>();
/*     */   private final Map<T, B> printMap;
/*     */   
/*     */   RuntimeEnumLeafInfoImpl(RuntimeModelBuilder builder, Locatable upstream, Class<T> enumType) {
/*  86 */     super(builder, upstream, enumType, enumType);
/*  87 */     this.printMap = new EnumMap<T, B>(enumType);
/*     */     
/*  89 */     this.baseXducer = ((RuntimeNonElement)this.baseType).getTransducer();
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeEnumConstantImpl createEnumConstant(String name, String literal, Field constant, EnumConstantImpl<Type, Class<?>, Field, Method> last) {
/*     */     Enum enum_;
/*     */     try {
/*     */       try {
/*  97 */         constant.setAccessible(true);
/*  98 */       } catch (SecurityException e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       enum_ = (Enum)constant.get((Object)null);
/* 104 */     } catch (IllegalAccessException e) {
/*     */       
/* 106 */       throw new IllegalAccessError(e.getMessage());
/*     */     } 
/*     */     
/* 109 */     B b = null;
/*     */     try {
/* 111 */       b = (B)this.baseXducer.parse(literal);
/* 112 */     } catch (Exception e) {
/* 113 */       this.builder.reportError(new IllegalAnnotationException(Messages.INVALID_XML_ENUM_VALUE.format(new Object[] { literal, ((Type)this.baseType.getType()).toString() }, ), e, (Locatable)new FieldLocatable(this, constant, nav())));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 118 */     this.parseMap.put(b, (T)enum_);
/* 119 */     this.printMap.put((T)enum_, b);
/*     */     
/* 121 */     return new RuntimeEnumConstantImpl(this, name, literal, last);
/*     */   }
/*     */   
/*     */   public QName[] getTypeNames() {
/* 125 */     return new QName[] { getTypeName() };
/*     */   }
/*     */   
/*     */   public boolean isDefault() {
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getClazz() {
/* 134 */     return this.clazz;
/*     */   }
/*     */   
/*     */   public boolean useNamespace() {
/* 138 */     return this.baseXducer.useNamespace();
/*     */   }
/*     */   
/*     */   public void declareNamespace(T t, XMLSerializer w) throws AccessorException {
/* 142 */     this.baseXducer.declareNamespace(this.printMap.get(t), w);
/*     */   }
/*     */   
/*     */   public CharSequence print(T t) throws AccessorException {
/* 146 */     return this.baseXducer.print(this.printMap.get(t));
/*     */   }
/*     */ 
/*     */   
/*     */   public T parse(CharSequence lexical) throws AccessorException, SAXException {
/*     */     String str;
/* 152 */     B b = (B)this.baseXducer.parse(lexical);
/*     */     
/* 154 */     if (this.tokenStringType) {
/* 155 */       str = ((String)b).trim();
/*     */     }
/*     */     
/* 158 */     return this.parseMap.get(str);
/*     */   }
/*     */   
/*     */   public void writeText(XMLSerializer w, T t, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 162 */     this.baseXducer.writeText(w, this.printMap.get(t), fieldName);
/*     */   }
/*     */   
/*     */   public void writeLeafElement(XMLSerializer w, Name tagName, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 166 */     this.baseXducer.writeLeafElement(w, tagName, this.printMap.get(o), fieldName);
/*     */   }
/*     */   
/*     */   public QName getTypeName(T instance) {
/* 170 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeEnumLeafInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */