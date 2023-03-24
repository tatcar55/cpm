/*    */ package com.sun.xml.rpc.encoding;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.rpc.encoding.Deserializer;
/*    */ import javax.xml.rpc.encoding.DeserializerFactory;
/*    */ import javax.xml.rpc.encoding.Serializer;
/*    */ import javax.xml.rpc.encoding.SerializerFactory;
/*    */ import javax.xml.rpc.encoding.TypeMapping;
/*    */ import javax.xml.rpc.encoding.TypeMappingRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TypeMappingUtil
/*    */ {
/*    */   public static TypeMapping getTypeMapping(TypeMappingRegistry registry, String encodingStyle) throws Exception {
/* 48 */     TypeMapping mapping = registry.getTypeMapping(encodingStyle);
/*    */     
/* 50 */     if (mapping == null) {
/* 51 */       throw new TypeMappingException("typemapping.noMappingForEncoding", encodingStyle);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 56 */     return mapping;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Serializer getSerializer(TypeMapping mapping, Class javaType, QName xmlType) throws Exception {
/* 65 */     SerializerFactory sf = mapping.getSerializer(javaType, xmlType);
/*    */     
/* 67 */     if (sf == null) {
/* 68 */       throw new TypeMappingException("typemapping.serializerNotRegistered", new Object[] { javaType, xmlType });
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 73 */     return sf.getSerializerAs("http://java.sun.com/jax-rpc-ri/1.0/streaming/");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Deserializer getDeserializer(TypeMapping mapping, Class javaType, QName xmlType) throws Exception {
/* 82 */     DeserializerFactory df = mapping.getDeserializer(javaType, xmlType);
/*    */     
/* 84 */     if (df == null) {
/* 85 */       throw new TypeMappingException("typemapping.deserializerNotRegistered", new Object[] { javaType, xmlType });
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 90 */     return df.getDeserializerAs("http://java.sun.com/jax-rpc-ri/1.0/streaming/");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\TypeMappingUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */