/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.beans.BeanInfo;
/*     */ import java.beans.Introspector;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
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
/*     */ public class ValueTypeSerializer
/*     */   extends GenericObjectSerializer
/*     */ {
/*  49 */   protected String memberNamespace = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueTypeSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  57 */     super((type != null) ? type : new QName(""), encodeType, isNullable, encodingStyle);
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
/*     */   public ValueTypeSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, Class targetClass) {
/*  71 */     this(type, encodeType, isNullable, encodingStyle);
/*     */     
/*  73 */     setTargetClass(targetClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueTypeSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, Class targetClass, String memberNamespace) {
/*  84 */     this(type, encodeType, isNullable, encodingStyle, targetClass);
/*     */     
/*  86 */     this.memberNamespace = memberNamespace;
/*     */   }
/*     */   
/*     */   protected void doSetTargetClass(Class targetClass) {
/*     */     try {
/*  91 */       introspectTargetClass(targetClass);
/*  92 */       reflectTargetClass(targetClass);
/*  93 */     } catch (Exception e) {
/*  94 */       throw new DeserializationException("nestedSerializationError", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void introspectTargetClass(Class<?> targetClass) throws Exception {
/* 101 */     BeanInfo beanInfoForTarget = Introspector.getBeanInfo(targetClass);
/* 102 */     PropertyDescriptor[] targetProperties = beanInfoForTarget.getPropertyDescriptors();
/*     */     
/* 104 */     for (int i = 0; i < targetProperties.length; i++) {
/* 105 */       final Method getterMethod = targetProperties[i].getReadMethod();
/* 106 */       final Method setterMethod = targetProperties[i].getWriteMethod();
/*     */       
/* 108 */       if (getterMethod != null && setterMethod != null) {
/*     */ 
/*     */         
/* 111 */         GenericObjectSerializer.MemberInfo member = new GenericObjectSerializer.MemberInfo();
/*     */         
/* 113 */         member.name = new QName(this.memberNamespace, targetProperties[i].getName());
/*     */         
/* 115 */         Class<?> baseJavaType = targetProperties[i].getPropertyType();
/* 116 */         member.javaType = getBoxedClassFor(baseJavaType);
/* 117 */         member.xmlType = (QName)this.javaToXmlType.get(baseJavaType);
/*     */         
/* 119 */         member.getter = new GenericObjectSerializer.GetterMethod() {
/*     */             public Object get(Object instance) throws Exception {
/* 121 */               return getterMethod.invoke(instance, new Object[0]);
/*     */             }
/*     */           };
/* 124 */         member.setter = new GenericObjectSerializer.SetterMethod()
/*     */           {
/*     */             public void set(Object instance, Object value) throws Exception {
/* 127 */               setterMethod.invoke(instance, new Object[] { value });
/*     */             }
/*     */           };
/*     */         
/* 131 */         addMember(member);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected void reflectTargetClass(Class targetClass) throws Exception {
/* 136 */     Field[] targetFields = targetClass.getFields();
/* 137 */     for (int i = 0; i < targetFields.length; i++) {
/* 138 */       final Field currentField = targetFields[i];
/*     */       
/* 140 */       int fieldModifiers = currentField.getModifiers();
/* 141 */       if (Modifier.isPublic(fieldModifiers))
/*     */       {
/*     */         
/* 144 */         if (!Modifier.isTransient(fieldModifiers))
/*     */         {
/*     */           
/* 147 */           if (!Modifier.isFinal(fieldModifiers)) {
/*     */ 
/*     */ 
/*     */             
/* 151 */             GenericObjectSerializer.MemberInfo member = new GenericObjectSerializer.MemberInfo();
/*     */             
/* 153 */             member.name = new QName(this.memberNamespace, currentField.getName());
/* 154 */             Class<?> baseJavaType = targetFields[i].getType();
/* 155 */             member.javaType = getBoxedClassFor(baseJavaType);
/* 156 */             member.xmlType = (QName)this.javaToXmlType.get(baseJavaType);
/*     */             
/* 158 */             member.getter = new GenericObjectSerializer.GetterMethod() {
/*     */                 public Object get(Object instance) throws Exception {
/* 160 */                   Field field = currentField;
/* 161 */                   return field.get(instance);
/*     */                 }
/*     */               };
/* 164 */             member.setter = new GenericObjectSerializer.SetterMethod()
/*     */               {
/*     */                 public void set(Object instance, Object value) throws Exception {
/* 167 */                   Field field = currentField;
/* 168 */                   field.set(instance, value);
/*     */                 }
/*     */               };
/*     */             
/* 172 */             addMember(member);
/*     */           }  }  } 
/*     */     } 
/*     */   }
/*     */   private static Class getBoxedClassFor(Class<boolean> possiblePrimitiveType) {
/* 177 */     if (!possiblePrimitiveType.isPrimitive()) {
/* 178 */       return possiblePrimitiveType;
/*     */     }
/* 180 */     if (possiblePrimitiveType == boolean.class)
/* 181 */       return Boolean.class; 
/* 182 */     if (possiblePrimitiveType == byte.class)
/* 183 */       return Byte.class; 
/* 184 */     if (possiblePrimitiveType == short.class)
/* 185 */       return Short.class; 
/* 186 */     if (possiblePrimitiveType == int.class)
/* 187 */       return Integer.class; 
/* 188 */     if (possiblePrimitiveType == long.class)
/* 189 */       return Long.class; 
/* 190 */     if (possiblePrimitiveType == char.class)
/* 191 */       return Character.class; 
/* 192 */     if (possiblePrimitiveType == float.class)
/* 193 */       return Float.class; 
/* 194 */     if (possiblePrimitiveType == double.class) {
/* 195 */       return Double.class;
/*     */     }
/*     */     
/* 198 */     return possiblePrimitiveType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\ValueTypeSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */