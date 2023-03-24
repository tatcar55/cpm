/*     */ package com.sun.xml.rpc.encoding.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.Initializable;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*     */ import com.sun.xml.rpc.encoding.ObjectSerializerBase;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationState;
/*     */ import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.util.StructMap;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StructMapSerializer
/*     */   extends ObjectSerializerBase
/*     */   implements Initializable
/*     */ {
/*     */   protected InternalTypeMappingRegistry registry;
/*     */   
/*     */   public StructMapSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  66 */     super(type, encodeType, isNullable, encodingStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/*  72 */     this.registry = registry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSerializeInstance(Object instance, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/*  81 */     StructMap struct = (StructMap)instance;
/*  82 */     Iterator<QName> eachKey = struct.keys().iterator();
/*  83 */     Iterator eachValue = struct.values().iterator();
/*     */     
/*  85 */     while (eachKey.hasNext()) {
/*  86 */       Object value = eachValue.next();
/*  87 */       QName key = eachKey.next();
/*     */       
/*  89 */       if (value != null) {
/*  90 */         JAXRPCSerializer serializer = (JAXRPCSerializer)this.registry.getSerializer(this.encodingStyle, value.getClass());
/*     */ 
/*     */ 
/*     */         
/*  94 */         serializer.serialize(value, key, null, writer, context); continue;
/*     */       } 
/*  96 */       serializeNull(key, writer, context);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object doDeserialize(SOAPDeserializationState state, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 107 */     StructMap instance = new StructMap();
/*     */     
/* 109 */     StructMapBuilder builder = null;
/* 110 */     boolean isComplete = true;
/*     */     
/* 112 */     int memberIndex = 0;
/*     */     
/* 114 */     while (reader.nextElementContent() != 2) {
/* 115 */       QName key = reader.getName();
/* 116 */       if (!getNullStatus(reader)) {
/* 117 */         JAXRPCDeserializer deserializer = (JAXRPCDeserializer)this.registry.getDeserializer(this.encodingStyle, getType(reader));
/*     */ 
/*     */ 
/*     */         
/* 121 */         Object member = deserializer.deserialize(key, reader, context);
/* 122 */         if (member instanceof SOAPDeserializationState) {
/* 123 */           if (builder == null) {
/* 124 */             builder = new StructMapBuilder(instance);
/*     */           }
/* 126 */           state = registerWithMemberState(instance, state, member, memberIndex, builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 133 */           isComplete = false;
/*     */         } 
/*     */ 
/*     */         
/* 137 */         instance.put(key, member); continue;
/*     */       } 
/* 139 */       instance.put(key, null);
/*     */     } 
/*     */ 
/*     */     
/* 143 */     return isComplete ? instance : state;
/*     */   }
/*     */   
/*     */   protected class StructMapBuilder
/*     */     implements SOAPInstanceBuilder {
/*     */     StructMap instance;
/*     */     
/*     */     StructMapBuilder(StructMap instance) {
/* 151 */       this.instance = instance;
/*     */     }
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 155 */       return 6;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMember(int index, Object memberValue) {
/*     */       try {
/* 166 */         this.instance.set(index, memberValue);
/* 167 */       } catch (Exception e) {
/* 168 */         throw new DeserializationException("nestedSerializationError", new LocalizableExceptionAdapter(e));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void initialize() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void setInstance(Object instance) {
/* 179 */       instance = instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 183 */       return this.instance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\StructMapSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */