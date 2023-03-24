/*    */ package com.sun.xml.rpc.encoding;
/*    */ 
/*    */ import com.sun.xml.rpc.util.SingleElementIterator;
/*    */ import java.util.Iterator;
/*    */ import javax.xml.rpc.encoding.Serializer;
/*    */ import javax.xml.rpc.encoding.SerializerFactory;
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
/*    */ public class SingletonSerializerFactory
/*    */   implements SerializerFactory
/*    */ {
/*    */   protected Serializer serializer;
/*    */   
/*    */   public SingletonSerializerFactory(Serializer serializer) {
/* 44 */     this.serializer = serializer;
/*    */   }
/*    */   
/*    */   public Serializer getSerializerAs(String mechanismType) {
/* 48 */     if (!"http://java.sun.com/jax-rpc-ri/1.0/streaming/".equals(mechanismType)) {
/* 49 */       throw new TypeMappingException("typemapping.mechanism.unsupported", mechanismType);
/*    */     }
/*    */ 
/*    */     
/* 53 */     return this.serializer;
/*    */   }
/*    */   
/*    */   public Iterator getSupportedMechanismTypes() {
/* 57 */     return (Iterator)new SingleElementIterator("http://java.sun.com/jax-rpc-ri/1.0/streaming/");
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator getSerializers() {
/* 62 */     return (Iterator)new SingleElementIterator(this.serializer);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SingletonSerializerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */