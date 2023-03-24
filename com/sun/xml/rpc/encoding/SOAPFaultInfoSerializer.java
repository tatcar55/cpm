/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
/*     */ import com.sun.xml.rpc.encoding.literal.LiteralFragmentSerializer;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDQNameEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDStringEncoder;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPConstants;
/*     */ import com.sun.xml.rpc.soap.message.SOAPFaultInfo;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.DetailEntry;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
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
/*     */ public class SOAPFaultInfoSerializer
/*     */   extends ObjectSerializerBase
/*     */   implements Initializable
/*     */ {
/*  55 */   protected static final QName FAULTACTOR_QNAME = new QName("", "faultactor");
/*  56 */   protected static final QName XSD_STRING_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
/*     */   
/*  58 */   protected static final QName XSD_QNAME_TYPE_QNAME = SchemaConstants.QNAME_TYPE_QNAME;
/*     */   
/*     */   private static final int DETAIL_INDEX = 0;
/*     */   
/*  62 */   protected static final CombinedSerializer _XSDStringSerializer = new SimpleTypeSerializer(XSD_STRING_TYPE_QNAME, false, true, null, XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   protected static final CombinedSerializer _XSDQNameSerializer = new SimpleTypeSerializer(XSD_QNAME_TYPE_QNAME, false, true, null, XSDQNameEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   protected static final QName FAULTCODE_QNAME = new QName("", "faultcode");
/*  77 */   protected static final QName FAULTSTRING_QNAME = new QName("", "faultstring");
/*     */   
/*  79 */   protected static final QName DETAIL_QNAME = new QName("", "detail");
/*  80 */   protected static final QName SOAPELEMENT_QNAME = new QName("", "element");
/*     */   
/*     */   public SOAPFaultInfoSerializer(boolean encodeType, boolean isNullable) {
/*  83 */     super(SOAPConstants.QNAME_SOAP_FAULT, encodeType, isNullable, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPFaultInfoSerializer(boolean encodeType, boolean isNullable, String encodingStyle) {
/*  91 */     super(SOAPConstants.QNAME_SOAP_FAULT, encodeType, isNullable, encodingStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object doDeserialize(SOAPDeserializationState state, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 108 */     SOAPFaultInfo instance = null;
/* 109 */     boolean isComplete = true;
/*     */     
/* 111 */     QName code = null;
/* 112 */     String string = null;
/* 113 */     String actor = null;
/* 114 */     Object detail = null;
/* 115 */     SOAPInstanceBuilder builder = null;
/*     */     
/* 117 */     reader.nextElementContent();
/* 118 */     XMLReaderUtil.verifyReaderState(reader, 1);
/* 119 */     QName elementName = reader.getName();
/* 120 */     if (elementName.equals(FAULTCODE_QNAME)) {
/* 121 */       code = (QName)_XSDQNameSerializer.deserialize(FAULTCODE_QNAME, reader, context);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     reader.nextElementContent();
/* 128 */     XMLReaderUtil.verifyReaderState(reader, 1);
/* 129 */     elementName = reader.getName();
/* 130 */     if (elementName.equals(FAULTSTRING_QNAME)) {
/* 131 */       string = (String)_XSDStringSerializer.deserialize(FAULTSTRING_QNAME, reader, context);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     if (reader.nextElementContent() == 1) {
/* 138 */       elementName = reader.getName();
/* 139 */       if (elementName.equals(FAULTACTOR_QNAME)) {
/* 140 */         actor = (String)_XSDStringSerializer.deserialize(FAULTACTOR_QNAME, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 145 */         if (reader.nextElementContent() == 1) {
/* 146 */           elementName = reader.getName();
/*     */         }
/*     */       } 
/* 149 */       instance = new SOAPFaultInfo(code, string, actor, detail);
/* 150 */       if (elementName.equals(DETAIL_QNAME)) {
/* 151 */         detail = deserializeDetail(state, reader, context, instance);
/*     */         
/* 153 */         if (detail instanceof SOAPDeserializationState) {
/* 154 */           state = (SOAPDeserializationState)detail;
/* 155 */           isComplete = false;
/*     */         } else {
/* 157 */           instance.setDetail(detail);
/*     */         } 
/* 159 */         reader.nextElementContent();
/*     */       } 
/*     */     } 
/* 162 */     if (instance == null) {
/* 163 */       instance = new SOAPFaultInfo(code, string, actor, detail);
/*     */     }
/*     */     
/* 166 */     XMLReaderUtil.verifyReaderState(reader, 2);
/* 167 */     return isComplete ? instance : state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doSerializeInstance(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 176 */     SOAPFaultInfo instance = (SOAPFaultInfo)obj;
/*     */     
/* 178 */     _XSDQNameSerializer.serialize(instance.getCode(), FAULTCODE_QNAME, (SerializerCallback)null, writer, context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     _XSDStringSerializer.serialize(instance.getString(), FAULTSTRING_QNAME, (SerializerCallback)null, writer, context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     if (instance.getActor() != null) {
/* 191 */       _XSDStringSerializer.serialize(instance.getActor(), FAULTACTOR_QNAME, (SerializerCallback)null, writer, context);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     serializeDetail(instance.getDetail(), writer, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object deserializeDetail(SOAPDeserializationState state, XMLReader reader, SOAPDeserializationContext context, SOAPFaultInfo instance) throws Exception {
/* 208 */     reader.nextElementContent();
/* 209 */     return deserializeDetail(reader, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeDetail(Object detail, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 218 */     if (detail instanceof Detail) {
/* 219 */       writer.startElement(DETAIL_QNAME);
/* 220 */       Iterator<DetailEntry> iter = ((Detail)detail).getDetailEntries();
/* 221 */       while (iter.hasNext()) {
/* 222 */         DetailEntry entry = iter.next();
/* 223 */         Name elementName = entry.getElementName();
/* 224 */         QName elementQName = new QName(elementName.getURI(), elementName.getLocalName());
/*     */         
/* 226 */         LiteralFragmentSerializer serializer = new LiteralFragmentSerializer(DETAIL_QNAME, this.isNullable, this.encodingStyle);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 231 */         serializer.serialize(entry, elementQName, null, writer, context);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 238 */       writer.endElement();
/* 239 */     } else if (detail instanceof SOAPElement) {
/* 240 */       Iterator<SOAPElement> iter = ((SOAPElement)detail).getChildElements();
/* 241 */       if (iter.hasNext()) {
/* 242 */         SOAPElement entry = iter.next();
/* 243 */         Name elementName = entry.getElementName();
/* 244 */         QName elementQName = new QName(elementName.getURI(), elementName.getLocalName());
/*     */         
/* 246 */         LiteralFragmentSerializer serializer = new LiteralFragmentSerializer(SOAPELEMENT_QNAME, this.isNullable, this.encodingStyle);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 251 */         serializer.serialize(entry, elementQName, null, writer, context);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 258 */       writer.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object deserializeDetail(XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 267 */     DetailFragmentDeserializer detailDeserializer = new DetailFragmentDeserializer(DETAIL_QNAME, this.isNullable, this.encodingStyle);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     Object detail = detailDeserializer.deserialize(reader.getName(), reader, context);
/*     */     
/* 274 */     return detail;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void skipRemainingDetailEntries(XMLReader reader) throws Exception {
/* 280 */     while (reader.getState() != 2) {
/* 281 */       reader.skipElement();
/* 282 */       reader.nextElementContent();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SOAPFaultInfoSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */