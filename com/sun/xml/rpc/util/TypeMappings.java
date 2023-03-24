/*     */ package com.sun.xml.rpc.util;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.CombinedSerializer;
/*     */ import com.sun.xml.rpc.encoding.EncodingException;
/*     */ import com.sun.xml.rpc.encoding.ObjectSerializerBase;
/*     */ import com.sun.xml.rpc.encoding.ReferenceableSerializerImpl;
/*     */ import com.sun.xml.rpc.encoding.SerializerConstants;
/*     */ import com.sun.xml.rpc.encoding.SingletonDeserializerFactory;
/*     */ import com.sun.xml.rpc.encoding.SingletonSerializerFactory;
/*     */ import com.sun.xml.rpc.encoding.ValueTypeSerializer;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.lang.reflect.Constructor;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.Service;
/*     */ import javax.xml.rpc.encoding.Deserializer;
/*     */ import javax.xml.rpc.encoding.DeserializerFactory;
/*     */ import javax.xml.rpc.encoding.Serializer;
/*     */ import javax.xml.rpc.encoding.SerializerFactory;
/*     */ import javax.xml.rpc.encoding.TypeMapping;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeMappings
/*     */   implements SerializerConstants
/*     */ {
/*     */   public static void createMapping(Class<?> serializerType, QName xmlType, Class javaType, TypeMapping mappings) {
/*  56 */     if (!ObjectSerializerBase.class.isAssignableFrom(serializerType)) {
/*  57 */       throw new IllegalArgumentException("serializerType must be a derivitive of com.sun.xml.rpc.encoding.ObjectSerializerBase");
/*     */     }
/*     */     
/*     */     try {
/*  61 */       Constructor<?> serializerConstructor = serializerType.getConstructor(new Class[] { QName.class, boolean.class, boolean.class, String.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  69 */       CombinedSerializer serializer = (CombinedSerializer)serializerConstructor.newInstance(new Object[] { xmlType, new Boolean(true), new Boolean(true), "http://schemas.xmlsoap.org/soap/encoding/" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  77 */       ReferenceableSerializerImpl referenceableSerializerImpl = new ReferenceableSerializerImpl(true, serializer);
/*     */ 
/*     */       
/*  80 */       SingletonSerializerFactory serializerFactory = new SingletonSerializerFactory((Serializer)referenceableSerializerImpl);
/*     */       
/*  82 */       SingletonDeserializerFactory deserializerFactory = new SingletonDeserializerFactory((Deserializer)referenceableSerializerImpl);
/*     */ 
/*     */       
/*  85 */       mappings.register(javaType, xmlType, (SerializerFactory)serializerFactory, (DeserializerFactory)deserializerFactory);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  90 */     catch (Exception e) {
/*  91 */       throw new EncodingException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createMapping(Class serializerType, QName xmlType, Class javaType, Service service) {
/* 100 */     TypeMapping mappings = service.getTypeMappingRegistry().getTypeMapping("http://schemas.xmlsoap.org/soap/encoding/");
/*     */ 
/*     */     
/* 103 */     createMapping(serializerType, xmlType, javaType, mappings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createValueTypeMapping(QName xmlType, Class javaType, Service service) {
/* 110 */     ValueTypeSerializer valueTypeSerializer = new ValueTypeSerializer(xmlType, true, true, "http://schemas.xmlsoap.org/soap/encoding/", javaType);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     ReferenceableSerializerImpl referenceableSerializerImpl = new ReferenceableSerializerImpl(true, (CombinedSerializer)valueTypeSerializer);
/*     */ 
/*     */     
/* 120 */     SingletonSerializerFactory serializerFactory = new SingletonSerializerFactory((Serializer)referenceableSerializerImpl);
/*     */     
/* 122 */     SingletonDeserializerFactory deserializerFactory = new SingletonDeserializerFactory((Deserializer)referenceableSerializerImpl);
/*     */ 
/*     */     
/* 125 */     TypeMapping mappings = service.getTypeMappingRegistry().getTypeMapping("http://schemas.xmlsoap.org/soap/encoding/");
/*     */ 
/*     */     
/* 128 */     mappings.register(javaType, xmlType, (SerializerFactory)serializerFactory, (DeserializerFactory)deserializerFactory);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\TypeMappings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */