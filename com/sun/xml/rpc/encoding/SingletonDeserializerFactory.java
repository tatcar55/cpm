/*    */ package com.sun.xml.rpc.encoding;
/*    */ 
/*    */ import com.sun.xml.rpc.util.SingleElementIterator;
/*    */ import java.util.Iterator;
/*    */ import javax.xml.rpc.encoding.Deserializer;
/*    */ import javax.xml.rpc.encoding.DeserializerFactory;
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
/*    */ public class SingletonDeserializerFactory
/*    */   implements DeserializerFactory
/*    */ {
/*    */   protected Deserializer deserializer;
/*    */   
/*    */   public SingletonDeserializerFactory(Deserializer deserializer) {
/* 44 */     this.deserializer = deserializer;
/*    */   }
/*    */   
/*    */   public Deserializer getDeserializerAs(String mechanismType) {
/* 48 */     if (!"http://java.sun.com/jax-rpc-ri/1.0/streaming/".equals(mechanismType)) {
/* 49 */       throw new TypeMappingException("typemapping.mechanism.unsupported", mechanismType);
/*    */     }
/*    */ 
/*    */     
/* 53 */     return this.deserializer;
/*    */   }
/*    */   
/*    */   public Iterator getSupportedMechanismTypes() {
/* 57 */     return (Iterator)new SingleElementIterator("http://java.sun.com/jax-rpc-ri/1.0/streaming/");
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator getDeserializers() {
/* 62 */     return (Iterator)new SingleElementIterator(this.deserializer);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SingletonDeserializerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */