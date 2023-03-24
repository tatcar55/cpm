/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.xsd.XSDConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterUtil;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
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
/*     */ public class DynamicSerializer
/*     */   extends SerializerBase
/*     */   implements SchemaConstants, Initializable
/*     */ {
/*  51 */   InternalTypeMappingRegistry registry = null;
/*     */   
/*  53 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  57 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicSerializer(QName xmlType, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  67 */     this(xmlType, encodeType, isNullable, encodingStyle, SOAPVersion.SOAP_11);
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
/*     */   
/*     */   public DynamicSerializer(QName xmlType, boolean encodeType, boolean isNullable, String encodingStyle, SOAPVersion ver) {
/*  82 */     super(xmlType, encodeType, isNullable, encodingStyle);
/*  83 */     init(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/*  89 */     this.registry = registry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Object obj, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) {
/*  99 */     if (obj == null) {
/* 100 */       serializeNull(name, writer);
/*     */       
/*     */       return;
/*     */     } 
/* 104 */     JAXRPCSerializer serializer = getSerializerForObject(obj);
/*     */     
/* 106 */     if (serializer != null) {
/* 107 */       serializer.serialize(obj, name, callback, writer, context);
/*     */     }
/*     */   }
/*     */   
/*     */   protected JAXRPCSerializer getSerializerForObject(Object obj) {
/* 112 */     JAXRPCSerializer serializer = null;
/*     */     try {
/* 114 */       serializer = (JAXRPCSerializer)this.registry.getSerializer(this.soapEncodingConstants.getURIEncoding(), obj.getClass());
/*     */ 
/*     */ 
/*     */       
/* 118 */       if (serializer instanceof DynamicSerializer) {
/* 119 */         throw new SerializationException("typemapping.serializer.is.dynamic", new Object[] { obj.getClass() });
/*     */       
/*     */       }
/*     */     }
/* 123 */     catch (SerializationException e) {
/* 124 */       throw e;
/* 125 */     } catch (Exception e) {
/* 126 */       throw new SerializationException("nestedSerializationError", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 131 */     return serializer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void serializeNull(QName name, XMLWriter writer) {
/*     */     try {
/* 137 */       writer.startElement((name != null) ? name : QNAME_ANY);
/*     */       
/* 139 */       String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/* 140 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */       
/* 142 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
/* 143 */       writer.endElement();
/* 144 */     } catch (JAXRPCExceptionBase e) {
/* 145 */       throw new SerializationException("nestedSerializationError", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) {
/*     */     try {
/* 155 */       JAXRPCDeserializer deserializer = getDeserializerForElement(reader, context);
/*     */ 
/*     */       
/* 158 */       if (deserializer == null) {
/* 159 */         return null;
/*     */       }
/* 161 */       return deserializer.deserialize(name, reader, context);
/*     */     }
/* 163 */     catch (DeserializationException e) {
/* 164 */       throw e;
/* 165 */     } catch (Exception e) {
/* 166 */       throw new DeserializationException("nestedDeserializationError", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCDeserializer getDeserializerForElement(XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 177 */     if (getNullStatus(reader) == true) {
/* 178 */       skipEmptyContent(reader);
/* 179 */       return null;
/*     */     } 
/*     */     
/* 182 */     QName objectXMLType = getType(reader);
/* 183 */     return (JAXRPCDeserializer)this.registry.getDeserializer(this.soapEncodingConstants.getURIEncoding(), objectXMLType);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\DynamicSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */