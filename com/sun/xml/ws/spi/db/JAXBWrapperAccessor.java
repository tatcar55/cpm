/*     */ package com.sun.xml.ws.spi.db;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
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
/*     */ public class JAXBWrapperAccessor
/*     */   extends WrapperAccessor
/*     */ {
/*     */   protected Class<?> contentClass;
/*     */   protected HashMap<Object, Class> elementDeclaredTypes;
/*     */   
/*     */   public JAXBWrapperAccessor(Class<?> wrapperBean) {
/*  75 */     this.contentClass = wrapperBean;
/*     */     
/*  77 */     HashMap<Object, PropertySetter> setByQName = new HashMap<Object, PropertySetter>();
/*  78 */     HashMap<Object, PropertySetter> setByLocalpart = new HashMap<Object, PropertySetter>();
/*  79 */     HashMap<String, Method> publicSetters = new HashMap<String, Method>();
/*     */     
/*  81 */     HashMap<Object, PropertyGetter> getByQName = new HashMap<Object, PropertyGetter>();
/*  82 */     HashMap<Object, PropertyGetter> getByLocalpart = new HashMap<Object, PropertyGetter>();
/*  83 */     HashMap<String, Method> publicGetters = new HashMap<String, Method>();
/*     */     
/*  85 */     HashMap<Object, Class<?>> elementDeclaredTypesByQName = new HashMap<Object, Class<?>>();
/*  86 */     HashMap<Object, Class<?>> elementDeclaredTypesByLocalpart = new HashMap<Object, Class<?>>();
/*     */     
/*  88 */     for (Method method : this.contentClass.getMethods()) {
/*  89 */       if (PropertySetterBase.setterPattern(method)) {
/*  90 */         String key = method.getName().substring(3, method.getName().length()).toLowerCase();
/*     */         
/*  92 */         publicSetters.put(key, method);
/*     */       } 
/*  94 */       if (PropertyGetterBase.getterPattern(method)) {
/*  95 */         String methodName = method.getName();
/*  96 */         String key = methodName.startsWith("is") ? methodName.substring(2, method.getName().length()).toLowerCase() : methodName.substring(3, method.getName().length()).toLowerCase();
/*     */ 
/*     */ 
/*     */         
/* 100 */         publicGetters.put(key, method);
/*     */       } 
/*     */     } 
/* 103 */     HashSet<String> elementLocalNames = new HashSet<String>();
/* 104 */     for (Field field : getAllFields(this.contentClass)) {
/* 105 */       XmlElement xmlElem = field.<XmlElement>getAnnotation(XmlElement.class);
/* 106 */       XmlElementRef xmlElemRef = field.<XmlElementRef>getAnnotation(XmlElementRef.class);
/* 107 */       String fieldName = field.getName().toLowerCase();
/* 108 */       String namespace = "";
/* 109 */       String localName = field.getName();
/* 110 */       if (xmlElem != null) {
/* 111 */         namespace = xmlElem.namespace();
/* 112 */         if (xmlElem.name() != null && !xmlElem.name().equals("") && !xmlElem.name().equals("##default"))
/*     */         {
/* 114 */           localName = xmlElem.name();
/*     */         }
/* 116 */       } else if (xmlElemRef != null) {
/* 117 */         namespace = xmlElemRef.namespace();
/* 118 */         if (xmlElemRef.name() != null && !xmlElemRef.name().equals("") && !xmlElemRef.name().equals("##default"))
/*     */         {
/* 120 */           localName = xmlElemRef.name();
/*     */         }
/*     */       } 
/* 123 */       if (elementLocalNames.contains(localName)) {
/* 124 */         this.elementLocalNameCollision = true;
/*     */       } else {
/* 126 */         elementLocalNames.add(localName);
/*     */       } 
/*     */       
/* 129 */       QName qname = new QName(namespace, localName);
/* 130 */       if (field.getType().equals(JAXBElement.class) && 
/* 131 */         field.getGenericType() instanceof ParameterizedType) {
/* 132 */         Type arg = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
/*     */         
/* 134 */         if (arg instanceof Class) {
/* 135 */           elementDeclaredTypesByQName.put(qname, (Class)arg);
/* 136 */           elementDeclaredTypesByLocalpart.put(localName, (Class)arg);
/*     */         }
/* 138 */         else if (arg instanceof GenericArrayType) {
/* 139 */           Type componentType = ((GenericArrayType)arg).getGenericComponentType();
/*     */           
/* 141 */           if (componentType instanceof Class) {
/* 142 */             Class<?> arrayClass = Array.newInstance((Class)componentType, 0).getClass();
/*     */             
/* 144 */             elementDeclaredTypesByQName.put(qname, arrayClass);
/* 145 */             elementDeclaredTypesByLocalpart.put(localName, arrayClass);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       if (fieldName.startsWith("_") && !localName.startsWith("_")) {
/* 154 */         fieldName = fieldName.substring(1);
/*     */       }
/* 156 */       Method setMethod = publicSetters.get(fieldName);
/* 157 */       Method getMethod = publicGetters.get(fieldName);
/* 158 */       PropertySetter setter = createPropertySetter(field, setMethod);
/* 159 */       PropertyGetter getter = createPropertyGetter(field, getMethod);
/* 160 */       setByQName.put(qname, setter);
/* 161 */       setByLocalpart.put(localName, setter);
/* 162 */       getByQName.put(qname, getter);
/* 163 */       getByLocalpart.put(localName, getter);
/*     */     } 
/* 165 */     if (this.elementLocalNameCollision) {
/* 166 */       this.propertySetters = setByQName;
/* 167 */       this.propertyGetters = getByQName;
/* 168 */       this.elementDeclaredTypes = elementDeclaredTypesByQName;
/*     */     } else {
/* 170 */       this.propertySetters = setByLocalpart;
/* 171 */       this.propertyGetters = getByLocalpart;
/* 172 */       this.elementDeclaredTypes = elementDeclaredTypesByLocalpart;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static List<Field> getAllFields(Class<?> clz) {
/* 177 */     List<Field> list = new ArrayList<Field>();
/* 178 */     while (!Object.class.equals(clz)) {
/* 179 */       list.addAll(Arrays.asList(getDeclaredFields(clz)));
/* 180 */       clz = clz.getSuperclass();
/*     */     } 
/* 182 */     return list;
/*     */   }
/*     */   
/*     */   protected static Field[] getDeclaredFields(final Class<?> clz) {
/*     */     try {
/* 187 */       return (System.getSecurityManager() == null) ? clz.getDeclaredFields() : AccessController.<Field[]>doPrivileged((PrivilegedExceptionAction)new PrivilegedExceptionAction<Field[]>()
/*     */           {
/*     */             public Field[] run() throws IllegalAccessException
/*     */             {
/* 191 */               return clz.getDeclaredFields();
/*     */             }
/*     */           });
/* 194 */     } catch (PrivilegedActionException e) {
/*     */       
/* 196 */       e.printStackTrace();
/* 197 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static PropertyGetter createPropertyGetter(Field field, Method getMethod) {
/* 202 */     if (!field.isAccessible() && 
/* 203 */       getMethod != null) {
/* 204 */       MethodGetter methodGetter = new MethodGetter(getMethod);
/* 205 */       if (methodGetter.getType().toString().equals(field.getType().toString())) {
/* 206 */         return methodGetter;
/*     */       }
/*     */     } 
/*     */     
/* 210 */     return new FieldGetter(field);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static PropertySetter createPropertySetter(Field field, Method setter) {
/* 215 */     if (!field.isAccessible() && 
/* 216 */       setter != null) {
/* 217 */       MethodSetter injection = new MethodSetter(setter);
/* 218 */       if (injection.getType().toString().equals(field.getType().toString())) {
/* 219 */         return injection;
/*     */       }
/*     */     } 
/*     */     
/* 223 */     return new FieldSetter(field);
/*     */   }
/*     */   
/*     */   private Class getElementDeclaredType(QName name) {
/* 227 */     Object key = this.elementLocalNameCollision ? name : name.getLocalPart();
/*     */     
/* 229 */     return this.elementDeclaredTypes.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyAccessor getPropertyAccessor(String ns, String name) {
/* 234 */     final QName n = new QName(ns, name);
/* 235 */     final PropertySetter setter = getPropertySetter(n);
/* 236 */     final PropertyGetter getter = getPropertyGetter(n);
/* 237 */     final boolean isJAXBElement = setter.getType().equals(JAXBElement.class);
/*     */     
/* 239 */     final boolean isListType = List.class.isAssignableFrom(setter.getType());
/*     */     
/* 241 */     final Class elementDeclaredType = isJAXBElement ? getElementDeclaredType(n) : null;
/*     */     
/* 243 */     return new PropertyAccessor<Object, Object>()
/*     */       {
/*     */         public Object get(Object bean) throws DatabindingException {
/*     */           Object val;
/* 247 */           if (isJAXBElement) {
/* 248 */             JAXBElement<Object> jaxbElement = (JAXBElement<Object>)getter.get(bean);
/* 249 */             val = (jaxbElement == null) ? null : jaxbElement.getValue();
/*     */           } else {
/* 251 */             val = getter.get(bean);
/*     */           } 
/* 253 */           if (val == null && isListType) {
/* 254 */             val = new ArrayList();
/* 255 */             set(bean, val);
/*     */           } 
/* 257 */           return val;
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(Object bean, Object value) throws DatabindingException {
/* 262 */           if (isJAXBElement) {
/* 263 */             JAXBElement<Object> jaxbElement = new JAXBElement(n, elementDeclaredType, JAXBWrapperAccessor.this.contentClass, value);
/*     */             
/* 265 */             setter.set(bean, jaxbElement);
/*     */           } else {
/* 267 */             setter.set(bean, value);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\JAXBWrapperAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */