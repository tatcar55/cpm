/*     */ package com.sun.xml.rpc.encoding.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.CombinedSerializer;
/*     */ import com.sun.xml.rpc.encoding.Initializable;
/*     */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.ObjectSerializerBase;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationState;
/*     */ import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
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
/*     */ public final class JAXRpcMapEntrySerializer
/*     */   extends ObjectSerializerBase
/*     */   implements Initializable
/*     */ {
/*  47 */   private static final QName key_QNAME = InternalEncodingConstants.JAX_RPC_MAP_ENTRY_KEY_NAME;
/*     */   
/*  49 */   private static final QName anyType_TYPE_QNAME = SchemaConstants.QNAME_TYPE_URTYPE;
/*     */   
/*     */   private CombinedSerializer anyType_DynamicSerializer;
/*  52 */   private static final QName value_QNAME = InternalEncodingConstants.JAX_RPC_MAP_ENTRY_VALUE_NAME;
/*     */   
/*     */   private static final int KEY_INDEX = 0;
/*     */   
/*     */   private static final int VALUE_INDEX = 1;
/*  57 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  61 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXRpcMapEntrySerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  71 */     this(type, encodeType, isNullable, encodingStyle, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXRpcMapEntrySerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, SOAPVersion ver) {
/*  81 */     super(type, encodeType, isNullable, encodingStyle);
/*  82 */     init(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/*  88 */     this.anyType_DynamicSerializer = (CombinedSerializer)registry.getSerializer(this.soapEncodingConstants.getSOAPEncodingNamespace(), Object.class, anyType_TYPE_QNAME);
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
/*     */   public Object doDeserialize(SOAPDeserializationState state, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 101 */     JAXRpcMapEntry instance = new JAXRpcMapEntry();
/* 102 */     JAXRpcMapEntryBuilder builder = null;
/*     */     
/* 104 */     boolean isComplete = true;
/*     */ 
/*     */     
/* 107 */     reader.nextElementContent();
/* 108 */     QName elementName = reader.getName();
/* 109 */     if (reader.getState() == 1 && 
/* 110 */       elementName.equals(key_QNAME)) {
/* 111 */       Object member = this.anyType_DynamicSerializer.deserialize(key_QNAME, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       if (member instanceof SOAPDeserializationState) {
/* 117 */         if (builder == null) {
/* 118 */           builder = new JAXRpcMapEntryBuilder();
/*     */         }
/* 120 */         state = registerWithMemberState(instance, state, member, 0, builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 127 */         isComplete = false;
/*     */       } else {
/* 129 */         instance.setKey(member);
/*     */       } 
/* 131 */       reader.nextElementContent();
/*     */     } 
/*     */     
/* 134 */     elementName = reader.getName();
/* 135 */     if (reader.getState() == 1 && 
/* 136 */       elementName.equals(value_QNAME)) {
/* 137 */       Object member = this.anyType_DynamicSerializer.deserialize(value_QNAME, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       if (member instanceof SOAPDeserializationState) {
/* 143 */         if (builder == null) {
/* 144 */           builder = new JAXRpcMapEntryBuilder();
/*     */         }
/* 146 */         state = registerWithMemberState(instance, state, member, 1, builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         isComplete = false;
/*     */       } else {
/* 155 */         instance.setValue(member);
/*     */       } 
/* 157 */       reader.nextElementContent();
/*     */     } 
/*     */ 
/*     */     
/* 161 */     XMLReaderUtil.verifyReaderState(reader, 2);
/* 162 */     return isComplete ? instance : state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doSerializeInstance(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 171 */     JAXRpcMapEntry instance = (JAXRpcMapEntry)obj;
/*     */     
/* 173 */     this.anyType_DynamicSerializer.serialize(instance.getKey(), key_QNAME, null, writer, context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     this.anyType_DynamicSerializer.serialize(instance.getValue(), value_QNAME, null, writer, context);
/*     */   }
/*     */ 
/*     */   
/*     */   private class JAXRpcMapEntryBuilder
/*     */     implements SOAPInstanceBuilder
/*     */   {
/*     */     private JAXRpcMapEntry instance;
/*     */     private Object key;
/*     */     private Object value;
/*     */     private static final int KEY_INDEX = 0;
/*     */     private static final int VALUE_INDEX = 1;
/*     */     
/*     */     private JAXRpcMapEntryBuilder() {}
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 195 */       switch (memberIndex) {
/*     */         case 0:
/* 197 */           return 6;
/*     */         case 1:
/* 199 */           return 6;
/*     */       } 
/* 201 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void construct() {}
/*     */ 
/*     */     
/*     */     public void setMember(int index, Object memberValue) {
/* 209 */       switch (index) {
/*     */         case 0:
/* 211 */           this.instance.setKey(memberValue);
/*     */           return;
/*     */         case 1:
/* 214 */           this.instance.setValue(memberValue);
/*     */           return;
/*     */       } 
/* 217 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void initialize() {}
/*     */ 
/*     */     
/*     */     public void setInstance(Object instance) {
/* 225 */       this.instance = (JAXRpcMapEntry)instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 229 */       return this.instance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\JAXRpcMapEntrySerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */