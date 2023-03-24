/*     */ package com.sun.xml.rpc.encoding.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.DynamicInternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.beans.BeanInfo;
/*     */ import java.beans.Introspector;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ public class ValueTypeLiteralSerializer
/*     */   extends GenericLiteralObjectSerializer
/*     */ {
/*  55 */   protected String memberNamespace = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueTypeLiteralSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  66 */     super((type != null) ? type : new QName(""), encodeType, isNullable, encodingStyle);
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
/*     */ 
/*     */   
/*     */   public ValueTypeLiteralSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, Collection params) {
/*  80 */     super((type != null) ? type : new QName(""), encodeType, isNullable, encodingStyle);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.memberOrder = params;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueTypeLiteralSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, Class targetClass) {
/*  95 */     this(type, encodeType, isNullable, encodingStyle);
/*  96 */     setTargetClass(targetClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueTypeLiteralSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, Class targetClass, String memberNamespace) {
/* 107 */     this(type, encodeType, isNullable, encodingStyle, targetClass);
/* 108 */     this.memberNamespace = memberNamespace;
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
/*     */   public ValueTypeLiteralSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, Class targetClass, Collection paramOrder) {
/* 120 */     this(type, encodeType, isNullable, encodingStyle, paramOrder);
/* 121 */     setTargetClass(targetClass);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSetTargetClass(Class targetClass) {
/*     */     try {
/* 127 */       introspectTargetClass(targetClass);
/* 128 */       reflectTargetClass(targetClass);
/* 129 */     } catch (Exception e) {
/* 130 */       throw new DeserializationException("nestedSerializationError", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void introspectTargetClass(Class<?> targetClass) throws Exception {
/* 137 */     BeanInfo beanInfoForTarget = Introspector.getBeanInfo(targetClass);
/* 138 */     PropertyDescriptor[] targetProperties = beanInfoForTarget.getPropertyDescriptors();
/*     */ 
/*     */     
/* 141 */     targetProperties = membersByParameterOrder(targetProperties);
/*     */     
/* 143 */     for (int i = 0; i < targetProperties.length; i++) {
/* 144 */       final Method getterMethod = targetProperties[i].getReadMethod();
/* 145 */       final Method setterMethod = targetProperties[i].getWriteMethod();
/*     */       
/* 147 */       if (getterMethod != null && setterMethod != null) {
/*     */ 
/*     */         
/* 150 */         GenericLiteralObjectSerializer.MemberInfo member = new GenericLiteralObjectSerializer.MemberInfo();
/*     */ 
/*     */         
/* 153 */         member.name = new QName(this.memberNamespace, targetProperties[i].getName());
/*     */         
/* 155 */         Class<?> baseJavaType = targetProperties[i].getPropertyType();
/* 156 */         member.javaType = getBoxedClassFor(baseJavaType);
/* 157 */         member.xmlType = (QName)this.javaToXmlType.get(baseJavaType);
/*     */ 
/*     */         
/* 160 */         if (DynamicInternalTypeMappingRegistry.isValueType(member.javaType))
/*     */         {
/* 162 */           if (member.xmlType == null) {
/* 163 */             member.xmlType = member.name;
/*     */           }
/*     */         }
/* 166 */         member.getter = new GenericLiteralObjectSerializer.GetterMethod() {
/*     */             public Object get(Object instance) throws Exception {
/* 168 */               return getterMethod.invoke(instance, new Object[0]);
/*     */             }
/*     */           };
/* 171 */         member.setter = new GenericLiteralObjectSerializer.SetterMethod()
/*     */           {
/*     */             public void set(Object instance, Object value) throws Exception {
/* 174 */               setterMethod.invoke(instance, new Object[] { value });
/*     */             }
/*     */           };
/*     */         
/* 178 */         addMember(member);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected void reflectTargetClass(Class targetClass) throws Exception {
/* 183 */     Field[] targetFields = targetClass.getFields();
/* 184 */     for (int i = 0; i < targetFields.length; i++) {
/* 185 */       final Field currentField = targetFields[i];
/*     */       
/* 187 */       int fieldModifiers = currentField.getModifiers();
/* 188 */       if (Modifier.isPublic(fieldModifiers))
/*     */       {
/*     */         
/* 191 */         if (!Modifier.isTransient(fieldModifiers))
/*     */         {
/*     */           
/* 194 */           if (!Modifier.isFinal(fieldModifiers)) {
/*     */ 
/*     */ 
/*     */             
/* 198 */             GenericLiteralObjectSerializer.MemberInfo member = new GenericLiteralObjectSerializer.MemberInfo();
/*     */             
/* 200 */             member.name = new QName(this.memberNamespace, currentField.getName());
/* 201 */             Class<?> baseJavaType = targetFields[i].getType();
/* 202 */             member.javaType = getBoxedClassFor(baseJavaType);
/* 203 */             member.xmlType = (QName)this.javaToXmlType.get(baseJavaType);
/*     */             
/* 205 */             member.getter = new GenericLiteralObjectSerializer.GetterMethod() {
/*     */                 public Object get(Object instance) throws Exception {
/* 207 */                   Field field = currentField;
/* 208 */                   return field.get(instance);
/*     */                 }
/*     */               };
/* 211 */             member.setter = new GenericLiteralObjectSerializer.SetterMethod()
/*     */               {
/*     */                 public void set(Object instance, Object value) throws Exception {
/* 214 */                   Field field = currentField;
/* 215 */                   field.set(instance, value);
/*     */                 }
/*     */               };
/*     */             
/* 219 */             addMember(member);
/*     */           }  } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private PropertyDescriptor[] membersByParameterOrder(PropertyDescriptor[] targetProperties) {
/* 226 */     if (targetProperties == null)
/* 227 */       return null; 
/* 228 */     if (this.memberOrder == null || this.memberOrder.size() == 0) {
/* 229 */       return targetProperties;
/*     */     }
/* 231 */     Collection<PropertyDescriptor> members = new ArrayList();
/* 232 */     Iterator<String> namesIter = this.memberOrder.iterator();
/* 233 */     while (namesIter.hasNext()) {
/* 234 */       String name = namesIter.next();
/*     */       
/* 236 */       for (int j = 0; j < targetProperties.length; j++) {
/* 237 */         String propertyName = targetProperties[j].getName();
/* 238 */         if (propertyName.equalsIgnoreCase(name)) {
/* 239 */           members.add(targetProperties[j]);
/*     */         }
/*     */       } 
/*     */     } 
/* 243 */     if (members.size() == 0) {
/* 244 */       return targetProperties;
/*     */     }
/* 246 */     return members.<PropertyDescriptor>toArray(new PropertyDescriptor[members.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class getBoxedClassFor(Class<boolean> possiblePrimitiveType) {
/* 251 */     if (!possiblePrimitiveType.isPrimitive()) {
/* 252 */       return possiblePrimitiveType;
/*     */     }
/* 254 */     if (possiblePrimitiveType == boolean.class)
/* 255 */       return Boolean.class; 
/* 256 */     if (possiblePrimitiveType == byte.class)
/* 257 */       return Byte.class; 
/* 258 */     if (possiblePrimitiveType == short.class)
/* 259 */       return Short.class; 
/* 260 */     if (possiblePrimitiveType == int.class)
/* 261 */       return Integer.class; 
/* 262 */     if (possiblePrimitiveType == long.class)
/* 263 */       return Long.class; 
/* 264 */     if (possiblePrimitiveType == char.class)
/* 265 */       return Character.class; 
/* 266 */     if (possiblePrimitiveType == float.class)
/* 267 */       return Float.class; 
/* 268 */     if (possiblePrimitiveType == double.class) {
/* 269 */       return Double.class;
/*     */     }
/*     */     
/* 272 */     return possiblePrimitiveType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\literal\ValueTypeLiteralSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */