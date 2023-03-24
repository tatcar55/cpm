/*      */ package com.sun.xml.rpc.encoding.soap;
/*      */ 
/*      */ import com.sun.xml.rpc.encoding.AttachmentSerializer;
/*      */ import com.sun.xml.rpc.encoding.CombinedSerializer;
/*      */ import com.sun.xml.rpc.encoding.DynamicSerializer;
/*      */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*      */ import com.sun.xml.rpc.encoding.ReferenceableSerializerImpl;
/*      */ import com.sun.xml.rpc.encoding.SerializerConstants;
/*      */ import com.sun.xml.rpc.encoding.SimpleMultiTypeSerializer;
/*      */ import com.sun.xml.rpc.encoding.SimpleTypeSerializer;
/*      */ import com.sun.xml.rpc.encoding.SingletonDeserializerFactory;
/*      */ import com.sun.xml.rpc.encoding.SingletonSerializerFactory;
/*      */ import com.sun.xml.rpc.encoding.TypeMappingImpl;
/*      */ import com.sun.xml.rpc.encoding.simpletype.DataHandlerAttachmentEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.ImageAttachmentEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.MimeMultipartAttachmentEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.SourceAttachmentEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDBase64BinaryEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDBooleanEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDByteEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDDateEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDDateTimeCalendarEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDDateTimeDateEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDDecimalEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDDoubleEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDFloatEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDHexBinaryEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDIntEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDIntegerEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDListTypeEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDLongEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDNegativeIntegerEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDNonNegativeIntegerEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDNonPositiveIntegerEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDPositiveIntegerEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDQNameEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDShortEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDStringEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDTimeEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedByteEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedIntEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedLongEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedShortEncoder;
/*      */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*      */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*      */ import java.awt.Image;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
/*      */ import java.util.TreeMap;
/*      */ import java.util.TreeSet;
/*      */ import java.util.Vector;
/*      */ import javax.activation.DataHandler;
/*      */ import javax.mail.internet.MimeMultipart;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.rpc.encoding.Deserializer;
/*      */ import javax.xml.rpc.encoding.DeserializerFactory;
/*      */ import javax.xml.rpc.encoding.Serializer;
/*      */ import javax.xml.rpc.encoding.SerializerFactory;
/*      */ import javax.xml.transform.Source;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StandardSOAPTypeMappings
/*      */   extends TypeMappingImpl
/*      */   implements SerializerConstants, InternalEncodingConstants
/*      */ {
/*  102 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*      */ 
/*      */   
/*      */   private void init(SOAPVersion ver) {
/*  106 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*      */   }
/*      */ 
/*      */   
/*      */   public StandardSOAPTypeMappings() throws Exception {
/*  111 */     this(SOAPVersion.SOAP_11);
/*      */   }
/*      */ 
/*      */   
/*      */   public StandardSOAPTypeMappings(SOAPVersion ver) throws Exception {
/*  116 */     init(ver);
/*  117 */     setSupportedEncodings(new String[] { this.soapEncodingConstants.getSOAPEncodingNamespace() });
/*      */     
/*  119 */     QName[] base64Types = { SchemaConstants.QNAME_TYPE_BASE64_BINARY, this.soapEncodingConstants.getQNameTypeBase64Binary(), this.soapEncodingConstants.getQNameTypeBase64() };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  125 */     QName type = SchemaConstants.QNAME_TYPE_BOOLEAN;
/*  126 */     SimpleTypeSerializer simpleTypeSerializer98 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDBooleanEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  133 */     registerSerializer(boolean.class, type, (CombinedSerializer)simpleTypeSerializer98);
/*      */ 
/*      */     
/*  136 */     type = this.soapEncodingConstants.getQNameTypeBoolean();
/*  137 */     SimpleTypeSerializer simpleTypeSerializer97 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDBooleanEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  144 */     registerReferenceableSerializer(boolean.class, type, (CombinedSerializer)simpleTypeSerializer97);
/*      */ 
/*      */     
/*  147 */     type = SchemaConstants.QNAME_TYPE_BOOLEAN;
/*  148 */     SimpleTypeSerializer simpleTypeSerializer96 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDBooleanEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  155 */     registerSerializer(Boolean.class, type, (CombinedSerializer)simpleTypeSerializer96);
/*      */ 
/*      */     
/*  158 */     type = this.soapEncodingConstants.getQNameTypeBoolean();
/*  159 */     SimpleTypeSerializer simpleTypeSerializer95 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDBooleanEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  166 */     registerReferenceableSerializer(Boolean.class, type, (CombinedSerializer)simpleTypeSerializer95);
/*      */ 
/*      */     
/*  169 */     type = SchemaConstants.QNAME_TYPE_BYTE;
/*  170 */     SimpleTypeSerializer simpleTypeSerializer94 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDByteEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  177 */     registerSerializer(byte.class, type, (CombinedSerializer)simpleTypeSerializer94);
/*      */ 
/*      */     
/*  180 */     type = this.soapEncodingConstants.getQNameTypeByte();
/*  181 */     SimpleTypeSerializer simpleTypeSerializer93 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDByteEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  188 */     registerReferenceableSerializer(byte.class, type, (CombinedSerializer)simpleTypeSerializer93);
/*      */ 
/*      */     
/*  191 */     type = SchemaConstants.QNAME_TYPE_BYTE;
/*  192 */     SimpleTypeSerializer simpleTypeSerializer92 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDByteEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  199 */     registerSerializer(Byte.class, type, (CombinedSerializer)simpleTypeSerializer92);
/*      */ 
/*      */     
/*  202 */     type = this.soapEncodingConstants.getQNameTypeByte();
/*  203 */     SimpleTypeSerializer simpleTypeSerializer91 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDByteEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  210 */     registerReferenceableSerializer(Byte.class, type, (CombinedSerializer)simpleTypeSerializer91);
/*      */ 
/*      */     
/*  213 */     type = SchemaConstants.QNAME_TYPE_BASE64_BINARY;
/*  214 */     SimpleMultiTypeSerializer simpleMultiTypeSerializer3 = new SimpleMultiTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDBase64BinaryEncoder.getInstance(), base64Types);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  222 */     registerReferenceableSerializer(byte[].class, type, (CombinedSerializer)simpleMultiTypeSerializer3);
/*      */ 
/*      */     
/*  225 */     type = this.soapEncodingConstants.getQNameTypeBase64Binary();
/*  226 */     SimpleMultiTypeSerializer simpleMultiTypeSerializer2 = new SimpleMultiTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDBase64BinaryEncoder.getInstance(), base64Types);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  234 */     registerReferenceableSerializer(byte[].class, type, (CombinedSerializer)simpleMultiTypeSerializer2);
/*      */ 
/*      */     
/*  237 */     type = this.soapEncodingConstants.getQNameTypeBase64();
/*  238 */     SimpleMultiTypeSerializer simpleMultiTypeSerializer1 = new SimpleMultiTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDBase64BinaryEncoder.getInstance(), base64Types);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  246 */     registerReferenceableSerializer(byte[].class, type, (CombinedSerializer)simpleMultiTypeSerializer1);
/*      */ 
/*      */     
/*  249 */     type = SchemaConstants.QNAME_TYPE_HEX_BINARY;
/*  250 */     SimpleTypeSerializer simpleTypeSerializer90 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDHexBinaryEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  257 */     registerReferenceableSerializer(byte[].class, type, (CombinedSerializer)simpleTypeSerializer90);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  273 */     type = SchemaConstants.QNAME_TYPE_DECIMAL;
/*  274 */     SimpleTypeSerializer simpleTypeSerializer89 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDecimalEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  281 */     registerSerializer(BigDecimal.class, type, (CombinedSerializer)simpleTypeSerializer89);
/*      */ 
/*      */     
/*  284 */     type = this.soapEncodingConstants.getQNameTypeDecimal();
/*  285 */     SimpleTypeSerializer simpleTypeSerializer88 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDecimalEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  292 */     registerReferenceableSerializer(BigDecimal.class, type, (CombinedSerializer)simpleTypeSerializer88);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  298 */     type = SchemaConstants.QNAME_TYPE_DOUBLE;
/*  299 */     SimpleTypeSerializer simpleTypeSerializer87 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDoubleEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  306 */     registerSerializer(double.class, type, (CombinedSerializer)simpleTypeSerializer87);
/*      */ 
/*      */     
/*  309 */     type = this.soapEncodingConstants.getQNameTypeDouble();
/*  310 */     SimpleTypeSerializer simpleTypeSerializer86 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDoubleEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  317 */     registerReferenceableSerializer(double.class, type, (CombinedSerializer)simpleTypeSerializer86);
/*      */ 
/*      */     
/*  320 */     type = SchemaConstants.QNAME_TYPE_DOUBLE;
/*  321 */     SimpleTypeSerializer simpleTypeSerializer85 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDoubleEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  328 */     registerSerializer(Double.class, type, (CombinedSerializer)simpleTypeSerializer85);
/*      */ 
/*      */     
/*  331 */     type = this.soapEncodingConstants.getQNameTypeDouble();
/*  332 */     SimpleTypeSerializer simpleTypeSerializer84 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDoubleEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  339 */     registerReferenceableSerializer(Double.class, type, (CombinedSerializer)simpleTypeSerializer84);
/*      */ 
/*      */     
/*  342 */     type = SchemaConstants.QNAME_TYPE_FLOAT;
/*  343 */     SimpleTypeSerializer simpleTypeSerializer83 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDFloatEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  350 */     registerSerializer(float.class, type, (CombinedSerializer)simpleTypeSerializer83);
/*      */ 
/*      */     
/*  353 */     type = this.soapEncodingConstants.getQNameTypeFloat();
/*  354 */     SimpleTypeSerializer simpleTypeSerializer82 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDFloatEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  361 */     registerReferenceableSerializer(float.class, type, (CombinedSerializer)simpleTypeSerializer82);
/*      */ 
/*      */     
/*  364 */     type = SchemaConstants.QNAME_TYPE_FLOAT;
/*  365 */     SimpleTypeSerializer simpleTypeSerializer81 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDFloatEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  372 */     registerSerializer(Float.class, type, (CombinedSerializer)simpleTypeSerializer81);
/*      */ 
/*      */     
/*  375 */     type = this.soapEncodingConstants.getQNameTypeFloat();
/*  376 */     SimpleTypeSerializer simpleTypeSerializer80 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDFloatEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  383 */     registerReferenceableSerializer(Float.class, type, (CombinedSerializer)simpleTypeSerializer80);
/*      */ 
/*      */     
/*  386 */     type = SchemaConstants.QNAME_TYPE_INT;
/*  387 */     SimpleTypeSerializer simpleTypeSerializer79 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDIntEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  394 */     registerSerializer(int.class, type, (CombinedSerializer)simpleTypeSerializer79);
/*      */ 
/*      */     
/*  397 */     type = this.soapEncodingConstants.getQNameTypeInt();
/*  398 */     SimpleTypeSerializer simpleTypeSerializer78 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDIntEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  405 */     registerReferenceableSerializer(int.class, type, (CombinedSerializer)simpleTypeSerializer78);
/*      */ 
/*      */     
/*  408 */     type = SchemaConstants.QNAME_TYPE_INT;
/*  409 */     SimpleTypeSerializer simpleTypeSerializer77 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDIntEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  416 */     registerSerializer(Integer.class, type, (CombinedSerializer)simpleTypeSerializer77);
/*      */ 
/*      */     
/*  419 */     type = this.soapEncodingConstants.getQNameTypeInt();
/*  420 */     SimpleTypeSerializer simpleTypeSerializer76 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDIntEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  427 */     registerReferenceableSerializer(Integer.class, type, (CombinedSerializer)simpleTypeSerializer76);
/*      */ 
/*      */     
/*  430 */     type = SchemaConstants.QNAME_TYPE_INTEGER;
/*  431 */     SimpleTypeSerializer simpleTypeSerializer75 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDIntegerEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  438 */     registerSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer75);
/*      */ 
/*      */     
/*  441 */     type = this.soapEncodingConstants.getQNameTypeInteger();
/*  442 */     SimpleTypeSerializer simpleTypeSerializer74 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDIntegerEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  449 */     registerReferenceableSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer74);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  455 */     type = SchemaConstants.QNAME_TYPE_LONG;
/*  456 */     SimpleTypeSerializer simpleTypeSerializer73 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDLongEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  463 */     registerSerializer(long.class, type, (CombinedSerializer)simpleTypeSerializer73);
/*      */ 
/*      */     
/*  466 */     type = this.soapEncodingConstants.getQNameTypeLong();
/*  467 */     SimpleTypeSerializer simpleTypeSerializer72 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDLongEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  474 */     registerReferenceableSerializer(long.class, type, (CombinedSerializer)simpleTypeSerializer72);
/*      */ 
/*      */     
/*  477 */     type = SchemaConstants.QNAME_TYPE_LONG;
/*  478 */     SimpleTypeSerializer simpleTypeSerializer71 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDLongEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  485 */     registerSerializer(Long.class, type, (CombinedSerializer)simpleTypeSerializer71);
/*      */ 
/*      */     
/*  488 */     type = this.soapEncodingConstants.getQNameTypeLong();
/*  489 */     SimpleTypeSerializer simpleTypeSerializer70 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDLongEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  496 */     registerReferenceableSerializer(Long.class, type, (CombinedSerializer)simpleTypeSerializer70);
/*      */ 
/*      */     
/*  499 */     type = SchemaConstants.QNAME_TYPE_SHORT;
/*  500 */     SimpleTypeSerializer simpleTypeSerializer69 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDShortEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  507 */     registerSerializer(short.class, type, (CombinedSerializer)simpleTypeSerializer69);
/*      */ 
/*      */     
/*  510 */     type = this.soapEncodingConstants.getQNameTypeShort();
/*  511 */     SimpleTypeSerializer simpleTypeSerializer68 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDShortEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  518 */     registerReferenceableSerializer(short.class, type, (CombinedSerializer)simpleTypeSerializer68);
/*      */ 
/*      */     
/*  521 */     type = SchemaConstants.QNAME_TYPE_SHORT;
/*  522 */     SimpleTypeSerializer simpleTypeSerializer67 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDShortEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  529 */     registerSerializer(Short.class, type, (CombinedSerializer)simpleTypeSerializer67);
/*      */ 
/*      */     
/*  532 */     type = this.soapEncodingConstants.getQNameTypeShort();
/*  533 */     SimpleTypeSerializer simpleTypeSerializer66 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDShortEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  540 */     registerReferenceableSerializer(Short.class, type, (CombinedSerializer)simpleTypeSerializer66);
/*      */ 
/*      */     
/*  543 */     type = SchemaConstants.QNAME_TYPE_STRING;
/*  544 */     AttachmentSerializer attachmentSerializer9 = new AttachmentSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), false, XSDStringEncoder.getInstance(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  554 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)attachmentSerializer9, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  561 */     type = this.soapEncodingConstants.getQNameTypeString();
/*  562 */     AttachmentSerializer attachmentSerializer8 = new AttachmentSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), false, XSDStringEncoder.getInstance(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  572 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)attachmentSerializer8, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  579 */     type = SchemaConstants.QNAME_TYPE_DATE_TIME;
/*  580 */     SimpleTypeSerializer simpleTypeSerializer65 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDateTimeCalendarEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  587 */     registerSerializer(Calendar.class, type, (CombinedSerializer)simpleTypeSerializer65);
/*      */ 
/*      */     
/*  590 */     type = this.soapEncodingConstants.getQNameTypeDateTime();
/*  591 */     SimpleTypeSerializer simpleTypeSerializer64 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDateTimeCalendarEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  598 */     registerReferenceableSerializer(Calendar.class, type, (CombinedSerializer)simpleTypeSerializer64);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  604 */     type = SchemaConstants.QNAME_TYPE_DATE_TIME;
/*  605 */     SimpleTypeSerializer simpleTypeSerializer63 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDateTimeDateEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  612 */     registerSerializer(Date.class, type, (CombinedSerializer)simpleTypeSerializer63);
/*      */ 
/*      */     
/*  615 */     type = this.soapEncodingConstants.getQNameTypeDateTime();
/*  616 */     SimpleTypeSerializer simpleTypeSerializer62 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDateTimeDateEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  623 */     registerReferenceableSerializer(Date.class, type, (CombinedSerializer)simpleTypeSerializer62);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  629 */     type = SchemaConstants.QNAME_TYPE_QNAME;
/*  630 */     SimpleTypeSerializer simpleTypeSerializer61 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDQNameEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  637 */     registerSerializer(QName.class, type, (CombinedSerializer)simpleTypeSerializer61);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  643 */     type = this.soapEncodingConstants.getQNameTypeQName();
/*  644 */     SimpleTypeSerializer simpleTypeSerializer60 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDQNameEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  651 */     registerReferenceableSerializer(QName.class, type, (CombinedSerializer)simpleTypeSerializer60);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  657 */     type = SchemaConstants.QNAME_TYPE_URTYPE;
/*  658 */     DynamicSerializer dynamicSerializer = new DynamicSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  665 */     registerReferenceableSerializer(Object.class, type, (CombinedSerializer)dynamicSerializer);
/*      */ 
/*      */ 
/*      */     
/*  669 */     type = QNAME_TYPE_COLLECTION;
/*  670 */     CollectionInterfaceSerializer collectionInterfaceSerializer3 = new CollectionInterfaceSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  677 */     registerReferenceableSerializer(Collection.class, type, (CombinedSerializer)collectionInterfaceSerializer3);
/*      */ 
/*      */     
/*  680 */     type = QNAME_TYPE_LIST;
/*  681 */     CollectionInterfaceSerializer collectionInterfaceSerializer2 = new CollectionInterfaceSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  688 */     registerReferenceableSerializer(List.class, type, (CombinedSerializer)collectionInterfaceSerializer2);
/*      */ 
/*      */     
/*  691 */     type = QNAME_TYPE_ARRAY_LIST;
/*  692 */     CollectionSerializer collectionSerializer6 = new CollectionSerializer(type, ArrayList.class, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), COLLECTION_ELEMENT_NAME, SchemaConstants.QNAME_TYPE_URTYPE, Object.class, ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  703 */     registerReferenceableSerializer(ArrayList.class, type, (CombinedSerializer)collectionSerializer6);
/*      */ 
/*      */     
/*  706 */     type = QNAME_TYPE_VECTOR;
/*  707 */     CollectionSerializer collectionSerializer5 = new CollectionSerializer(type, Vector.class, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), COLLECTION_ELEMENT_NAME, SchemaConstants.QNAME_TYPE_URTYPE, Object.class, ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  718 */     registerReferenceableSerializer(Vector.class, type, (CombinedSerializer)collectionSerializer5);
/*      */ 
/*      */     
/*  721 */     type = QNAME_TYPE_LINKED_LIST;
/*  722 */     CollectionSerializer collectionSerializer4 = new CollectionSerializer(type, LinkedList.class, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), COLLECTION_ELEMENT_NAME, SchemaConstants.QNAME_TYPE_URTYPE, Object.class, ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  733 */     registerReferenceableSerializer(LinkedList.class, type, (CombinedSerializer)collectionSerializer4);
/*      */ 
/*      */     
/*  736 */     type = QNAME_TYPE_STACK;
/*  737 */     CollectionSerializer collectionSerializer3 = new CollectionSerializer(type, Stack.class, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), COLLECTION_ELEMENT_NAME, SchemaConstants.QNAME_TYPE_URTYPE, Object.class, ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  748 */     registerReferenceableSerializer(Stack.class, type, (CombinedSerializer)collectionSerializer3);
/*      */ 
/*      */     
/*  751 */     type = QNAME_TYPE_SET;
/*  752 */     CollectionInterfaceSerializer collectionInterfaceSerializer1 = new CollectionInterfaceSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  759 */     registerReferenceableSerializer(Set.class, type, (CombinedSerializer)collectionInterfaceSerializer1);
/*      */ 
/*      */     
/*  762 */     type = QNAME_TYPE_HASH_SET;
/*  763 */     CollectionSerializer collectionSerializer2 = new CollectionSerializer(type, HashSet.class, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), COLLECTION_ELEMENT_NAME, SchemaConstants.QNAME_TYPE_URTYPE, Object.class, ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  774 */     registerReferenceableSerializer(HashSet.class, type, (CombinedSerializer)collectionSerializer2);
/*      */ 
/*      */     
/*  777 */     type = QNAME_TYPE_TREE_SET;
/*  778 */     CollectionSerializer collectionSerializer1 = new CollectionSerializer(type, TreeSet.class, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), COLLECTION_ELEMENT_NAME, SchemaConstants.QNAME_TYPE_URTYPE, Object.class, ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  789 */     registerReferenceableSerializer(TreeSet.class, type, (CombinedSerializer)collectionSerializer1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  794 */     type = QNAME_TYPE_MAP;
/*  795 */     MapInterfaceSerializer mapInterfaceSerializer = new MapInterfaceSerializer(type, true, true, this.soapEncodingConstants.getURIEncoding(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  802 */     registerReferenceableSerializer(Map.class, type, (CombinedSerializer)mapInterfaceSerializer);
/*      */ 
/*      */     
/*  805 */     type = QNAME_TYPE_JAX_RPC_MAP_ENTRY;
/*  806 */     JAXRpcMapEntrySerializer jAXRpcMapEntrySerializer = new JAXRpcMapEntrySerializer(type, true, true, this.soapEncodingConstants.getURIEncoding(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  813 */     registerReferenceableSerializer(JAXRpcMapEntry.class, type, (CombinedSerializer)jAXRpcMapEntrySerializer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  819 */     type = QNAME_TYPE_HASH_MAP;
/*  820 */     MapSerializer mapSerializer4 = new MapSerializer(type, HashMap.class, true, true, this.soapEncodingConstants.getURIEncoding(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  828 */     registerReferenceableSerializer(HashMap.class, type, (CombinedSerializer)mapSerializer4);
/*      */ 
/*      */     
/*  831 */     type = QNAME_TYPE_TREE_MAP;
/*  832 */     MapSerializer mapSerializer3 = new MapSerializer(type, TreeMap.class, true, true, this.soapEncodingConstants.getURIEncoding(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  840 */     registerReferenceableSerializer(TreeMap.class, type, (CombinedSerializer)mapSerializer3);
/*      */ 
/*      */     
/*  843 */     type = QNAME_TYPE_HASHTABLE;
/*  844 */     MapSerializer mapSerializer2 = new MapSerializer(type, Hashtable.class, true, true, this.soapEncodingConstants.getURIEncoding(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  852 */     registerReferenceableSerializer(Hashtable.class, type, (CombinedSerializer)mapSerializer2);
/*      */ 
/*      */     
/*  855 */     type = QNAME_TYPE_PROPERTIES;
/*  856 */     MapSerializer mapSerializer1 = new MapSerializer(type, Properties.class, true, true, this.soapEncodingConstants.getURIEncoding(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  864 */     registerReferenceableSerializer(Properties.class, type, (CombinedSerializer)mapSerializer1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  878 */     type = new QName("http://java.sun.com/jax-rpc-ri/internal", "image");
/*      */     
/*  880 */     AttachmentSerializer attachmentSerializer7 = new AttachmentSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), true, ImageAttachmentEncoder.getInstance(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  889 */     registerReferenceableSerializer(Image.class, type, (CombinedSerializer)attachmentSerializer7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  895 */     type = new QName("http://java.sun.com/jax-rpc-ri/internal", "datahandler");
/*      */ 
/*      */ 
/*      */     
/*  899 */     AttachmentSerializer attachmentSerializer6 = new AttachmentSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), true, DataHandlerAttachmentEncoder.getInstance(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  908 */     registerReferenceableSerializer(DataHandler.class, type, (CombinedSerializer)attachmentSerializer6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  914 */     type = new QName("http://java.sun.com/jax-rpc-ri/internal", "multipart");
/*      */ 
/*      */ 
/*      */     
/*  918 */     AttachmentSerializer attachmentSerializer5 = new AttachmentSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), true, MimeMultipartAttachmentEncoder.getInstance(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  927 */     registerReferenceableSerializer(MimeMultipart.class, type, (CombinedSerializer)attachmentSerializer5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  933 */     type = new QName("http://java.sun.com/jax-rpc-ri/internal", "text_xml");
/*      */ 
/*      */ 
/*      */     
/*  937 */     AttachmentSerializer attachmentSerializer4 = new AttachmentSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), true, SourceAttachmentEncoder.getInstance(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  946 */     registerReferenceableSerializer(Source.class, type, (CombinedSerializer)attachmentSerializer4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  952 */     type = new QName("http://java.sun.com/jax-rpc-ri/internal", "image");
/*      */     
/*  954 */     AttachmentSerializer attachmentSerializer3 = new AttachmentSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), true, DataHandlerAttachmentEncoder.getInstance(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  963 */     registerReferenceableSerializer(DataHandler.class, type, (CombinedSerializer)attachmentSerializer3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  969 */     type = new QName("http://java.sun.com/jax-rpc-ri/internal", "multipart");
/*      */ 
/*      */ 
/*      */     
/*  973 */     AttachmentSerializer attachmentSerializer2 = new AttachmentSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), true, DataHandlerAttachmentEncoder.getInstance(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  982 */     registerReferenceableSerializer(DataHandler.class, type, (CombinedSerializer)attachmentSerializer2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  988 */     type = new QName("http://java.sun.com/jax-rpc-ri/internal", "text_xml");
/*      */ 
/*      */ 
/*      */     
/*  992 */     AttachmentSerializer attachmentSerializer1 = new AttachmentSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), true, DataHandlerAttachmentEncoder.getInstance(), ver);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1001 */     registerReferenceableSerializer(DataHandler.class, type, (CombinedSerializer)attachmentSerializer1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1009 */     type = SchemaConstants.QNAME_TYPE_LANGUAGE;
/* 1010 */     SimpleTypeSerializer simpleTypeSerializer59 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1017 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer59);
/*      */ 
/*      */     
/* 1020 */     type = this.soapEncodingConstants.getQNameTypeLanguage();
/* 1021 */     SimpleTypeSerializer simpleTypeSerializer58 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1028 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer58);
/*      */ 
/*      */     
/* 1031 */     type = SchemaConstants.QNAME_TYPE_NORMALIZED_STRING;
/* 1032 */     SimpleTypeSerializer simpleTypeSerializer57 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1039 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer57);
/*      */ 
/*      */     
/* 1042 */     type = this.soapEncodingConstants.getQNameTypeNormalizedString();
/* 1043 */     SimpleTypeSerializer simpleTypeSerializer56 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1050 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer56);
/*      */ 
/*      */     
/* 1053 */     type = SchemaConstants.QNAME_TYPE_TOKEN;
/* 1054 */     SimpleTypeSerializer simpleTypeSerializer55 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1061 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer55);
/*      */ 
/*      */     
/* 1064 */     type = this.soapEncodingConstants.getQNameTypeToken();
/* 1065 */     SimpleTypeSerializer simpleTypeSerializer54 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1072 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer54);
/*      */ 
/*      */     
/* 1075 */     type = SchemaConstants.QNAME_TYPE_NMTOKEN;
/* 1076 */     SimpleTypeSerializer simpleTypeSerializer53 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1083 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer53);
/*      */ 
/*      */     
/* 1086 */     type = this.soapEncodingConstants.getQNameTypeNMTOKEN();
/* 1087 */     SimpleTypeSerializer simpleTypeSerializer52 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1094 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer52);
/*      */ 
/*      */     
/* 1097 */     type = SchemaConstants.QNAME_TYPE_NAME;
/* 1098 */     SimpleTypeSerializer simpleTypeSerializer51 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1105 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer51);
/*      */ 
/*      */     
/* 1108 */     type = this.soapEncodingConstants.getQNameTypeName();
/* 1109 */     SimpleTypeSerializer simpleTypeSerializer50 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1116 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer50);
/*      */ 
/*      */     
/* 1119 */     type = SchemaConstants.QNAME_TYPE_NCNAME;
/* 1120 */     SimpleTypeSerializer simpleTypeSerializer49 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1127 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer49);
/*      */ 
/*      */     
/* 1130 */     type = this.soapEncodingConstants.getQNameTypeNCNAME();
/* 1131 */     SimpleTypeSerializer simpleTypeSerializer48 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1138 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer48);
/*      */ 
/*      */     
/* 1141 */     type = SchemaConstants.QNAME_TYPE_ID;
/* 1142 */     SimpleTypeSerializer simpleTypeSerializer47 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1149 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer47);
/*      */ 
/*      */     
/* 1152 */     type = this.soapEncodingConstants.getQNameTypeID();
/* 1153 */     SimpleTypeSerializer simpleTypeSerializer46 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1160 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer46);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1165 */     type = SchemaConstants.QNAME_TYPE_POSITIVE_INTEGER;
/* 1166 */     SimpleTypeSerializer simpleTypeSerializer45 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDPositiveIntegerEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1173 */     registerSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer45);
/*      */ 
/*      */     
/* 1176 */     type = this.soapEncodingConstants.getQNameTypePositiveInteger();
/* 1177 */     SimpleTypeSerializer simpleTypeSerializer44 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDPositiveIntegerEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1184 */     registerReferenceableSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer44);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1192 */     type = SchemaConstants.QNAME_TYPE_NON_POSITIVE_INTEGER;
/* 1193 */     SimpleTypeSerializer simpleTypeSerializer43 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDNonPositiveIntegerEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1200 */     registerSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer43);
/*      */ 
/*      */     
/* 1203 */     type = this.soapEncodingConstants.getQNameTypeNonPositiveInteger();
/* 1204 */     SimpleTypeSerializer simpleTypeSerializer42 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDNonPositiveIntegerEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1211 */     registerReferenceableSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer42);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1219 */     type = SchemaConstants.QNAME_TYPE_NEGATIVE_INTEGER;
/* 1220 */     SimpleTypeSerializer simpleTypeSerializer41 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDNegativeIntegerEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1227 */     registerSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer41);
/*      */ 
/*      */     
/* 1230 */     type = this.soapEncodingConstants.getQNameTypeNegativeInteger();
/* 1231 */     SimpleTypeSerializer simpleTypeSerializer40 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDNegativeIntegerEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1238 */     registerReferenceableSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer40);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1246 */     type = SchemaConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER;
/* 1247 */     SimpleTypeSerializer simpleTypeSerializer39 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDNonNegativeIntegerEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1254 */     registerSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer39);
/*      */ 
/*      */     
/* 1257 */     type = this.soapEncodingConstants.getQNameTypeNonNegativeInteger();
/* 1258 */     SimpleTypeSerializer simpleTypeSerializer38 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDNonNegativeIntegerEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1265 */     registerReferenceableSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer38);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1273 */     type = SchemaConstants.QNAME_TYPE_UNSIGNED_LONG;
/* 1274 */     SimpleTypeSerializer simpleTypeSerializer37 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedLongEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1281 */     registerSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer37);
/*      */ 
/*      */     
/* 1284 */     type = this.soapEncodingConstants.getQNameTypeUnsignedLong();
/* 1285 */     SimpleTypeSerializer simpleTypeSerializer36 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedLongEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1292 */     registerReferenceableSerializer(BigInteger.class, type, (CombinedSerializer)simpleTypeSerializer36);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1300 */     type = SchemaConstants.QNAME_TYPE_UNSIGNED_INT;
/* 1301 */     SimpleTypeSerializer simpleTypeSerializer35 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedIntEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1308 */     registerSerializer(long.class, type, (CombinedSerializer)simpleTypeSerializer35);
/*      */ 
/*      */     
/* 1311 */     type = this.soapEncodingConstants.getQNameTypeUnsignedInt();
/* 1312 */     SimpleTypeSerializer simpleTypeSerializer34 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDLongEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1319 */     registerReferenceableSerializer(long.class, type, (CombinedSerializer)simpleTypeSerializer34);
/*      */ 
/*      */     
/* 1322 */     type = SchemaConstants.QNAME_TYPE_UNSIGNED_INT;
/* 1323 */     SimpleTypeSerializer simpleTypeSerializer33 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedIntEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1330 */     registerSerializer(Long.class, type, (CombinedSerializer)simpleTypeSerializer33);
/*      */ 
/*      */     
/* 1333 */     type = this.soapEncodingConstants.getQNameTypeUnsignedInt();
/* 1334 */     SimpleTypeSerializer simpleTypeSerializer32 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedIntEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1341 */     registerReferenceableSerializer(Long.class, type, (CombinedSerializer)simpleTypeSerializer32);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1346 */     type = SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT;
/* 1347 */     SimpleTypeSerializer simpleTypeSerializer31 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedShortEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1354 */     registerSerializer(int.class, type, (CombinedSerializer)simpleTypeSerializer31);
/*      */ 
/*      */     
/* 1357 */     type = this.soapEncodingConstants.getQNameTypeUnsignedShort();
/* 1358 */     SimpleTypeSerializer simpleTypeSerializer30 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedShortEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1365 */     registerReferenceableSerializer(int.class, type, (CombinedSerializer)simpleTypeSerializer30);
/*      */ 
/*      */     
/* 1368 */     type = SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT;
/* 1369 */     SimpleTypeSerializer simpleTypeSerializer29 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedShortEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1376 */     registerSerializer(Integer.class, type, (CombinedSerializer)simpleTypeSerializer29);
/*      */ 
/*      */     
/* 1379 */     type = this.soapEncodingConstants.getQNameTypeUnsignedShort();
/* 1380 */     SimpleTypeSerializer simpleTypeSerializer28 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedShortEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1387 */     registerReferenceableSerializer(Integer.class, type, (CombinedSerializer)simpleTypeSerializer28);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1392 */     type = SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE;
/* 1393 */     SimpleTypeSerializer simpleTypeSerializer27 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedByteEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1400 */     registerSerializer(short.class, type, (CombinedSerializer)simpleTypeSerializer27);
/*      */ 
/*      */     
/* 1403 */     type = this.soapEncodingConstants.getQNameTypeUnsignedByte();
/* 1404 */     SimpleTypeSerializer simpleTypeSerializer26 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedByteEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1411 */     registerReferenceableSerializer(short.class, type, (CombinedSerializer)simpleTypeSerializer26);
/*      */ 
/*      */     
/* 1414 */     type = SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE;
/* 1415 */     SimpleTypeSerializer simpleTypeSerializer25 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedByteEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1422 */     registerSerializer(Short.class, type, (CombinedSerializer)simpleTypeSerializer25);
/*      */ 
/*      */     
/* 1425 */     type = this.soapEncodingConstants.getQNameTypeUnsignedByte();
/* 1426 */     SimpleTypeSerializer simpleTypeSerializer24 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDUnsignedByteEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1433 */     registerReferenceableSerializer(Short.class, type, (CombinedSerializer)simpleTypeSerializer24);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1438 */     type = SchemaConstants.QNAME_TYPE_DURATION;
/* 1439 */     SimpleTypeSerializer simpleTypeSerializer23 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1446 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer23);
/*      */ 
/*      */     
/* 1449 */     type = this.soapEncodingConstants.getQNameTypeDuration();
/* 1450 */     SimpleTypeSerializer simpleTypeSerializer22 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1457 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer22);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1462 */     type = SchemaConstants.QNAME_TYPE_TIME;
/* 1463 */     SimpleTypeSerializer simpleTypeSerializer21 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDTimeEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1470 */     registerSerializer(Calendar.class, type, (CombinedSerializer)simpleTypeSerializer21);
/*      */ 
/*      */     
/* 1473 */     type = this.soapEncodingConstants.getQNameTypeTime();
/* 1474 */     SimpleTypeSerializer simpleTypeSerializer20 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDTimeEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1481 */     registerReferenceableSerializer(Calendar.class, type, (CombinedSerializer)simpleTypeSerializer20);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1486 */     type = SchemaConstants.QNAME_TYPE_DATE;
/* 1487 */     SimpleTypeSerializer simpleTypeSerializer19 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDateEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1494 */     registerSerializer(Calendar.class, type, (CombinedSerializer)simpleTypeSerializer19);
/*      */ 
/*      */     
/* 1497 */     type = this.soapEncodingConstants.getQNameTypeDate();
/* 1498 */     SimpleTypeSerializer simpleTypeSerializer18 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDDateTimeCalendarEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1505 */     registerReferenceableSerializer(Calendar.class, type, (CombinedSerializer)simpleTypeSerializer18);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1510 */     type = SchemaConstants.QNAME_TYPE_G_YEAR_MONTH;
/* 1511 */     SimpleTypeSerializer simpleTypeSerializer17 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1518 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer17);
/*      */ 
/*      */     
/* 1521 */     type = this.soapEncodingConstants.getQNameTypeGYearMonth();
/* 1522 */     SimpleTypeSerializer simpleTypeSerializer16 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1529 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer16);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1534 */     type = SchemaConstants.QNAME_TYPE_G_YEAR;
/* 1535 */     SimpleTypeSerializer simpleTypeSerializer15 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1542 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer15);
/*      */ 
/*      */     
/* 1545 */     type = this.soapEncodingConstants.getQNameTypeGYear();
/* 1546 */     SimpleTypeSerializer simpleTypeSerializer14 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1553 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer14);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1558 */     type = SchemaConstants.QNAME_TYPE_G_MONTH_DAY;
/* 1559 */     SimpleTypeSerializer simpleTypeSerializer13 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1566 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer13);
/*      */ 
/*      */     
/* 1569 */     type = this.soapEncodingConstants.getQNameTypeGMonthDay();
/* 1570 */     SimpleTypeSerializer simpleTypeSerializer12 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1577 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer12);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1582 */     type = SchemaConstants.QNAME_TYPE_G_DAY;
/* 1583 */     SimpleTypeSerializer simpleTypeSerializer11 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1590 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer11);
/*      */ 
/*      */     
/* 1593 */     type = this.soapEncodingConstants.getQNameTypeGDay();
/* 1594 */     SimpleTypeSerializer simpleTypeSerializer10 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1601 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer10);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1606 */     type = SchemaConstants.QNAME_TYPE_G_MONTH;
/* 1607 */     SimpleTypeSerializer simpleTypeSerializer9 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1614 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer9);
/*      */ 
/*      */     
/* 1617 */     type = this.soapEncodingConstants.getQNameTypeGMonth();
/* 1618 */     SimpleTypeSerializer simpleTypeSerializer8 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1625 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer8);
/*      */ 
/*      */ 
/*      */     
/* 1629 */     type = SchemaConstants.QNAME_TYPE_IDREF;
/* 1630 */     SimpleTypeSerializer simpleTypeSerializer7 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1637 */     registerSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer7);
/*      */ 
/*      */     
/* 1640 */     type = this.soapEncodingConstants.getQNameTypeIDREF();
/* 1641 */     SimpleTypeSerializer simpleTypeSerializer6 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1648 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1654 */     type = SchemaConstants.QNAME_TYPE_IDREFS;
/* 1655 */     SimpleTypeSerializer simpleTypeSerializer5 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDListTypeEncoder.getInstance(XSDStringEncoder.getInstance(), String.class));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1664 */     registerSerializer(String[].class, type, (CombinedSerializer)simpleTypeSerializer5);
/*      */ 
/*      */     
/* 1667 */     type = this.soapEncodingConstants.getQNameTypeIDREFS();
/* 1668 */     SimpleTypeSerializer simpleTypeSerializer4 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDListTypeEncoder.getInstance(XSDStringEncoder.getInstance(), String.class));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1677 */     registerReferenceableSerializer(String[].class, type, (CombinedSerializer)simpleTypeSerializer4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1683 */     type = SchemaConstants.QNAME_TYPE_NMTOKENS;
/* 1684 */     SimpleTypeSerializer simpleTypeSerializer3 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDListTypeEncoder.getInstance(XSDStringEncoder.getInstance(), String.class));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1693 */     registerSerializer(String[].class, type, (CombinedSerializer)simpleTypeSerializer3);
/*      */ 
/*      */     
/* 1696 */     type = this.soapEncodingConstants.getQNameTypeNMTOKENS();
/* 1697 */     SimpleTypeSerializer simpleTypeSerializer2 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDListTypeEncoder.getInstance(XSDStringEncoder.getInstance(), String.class));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1706 */     registerReferenceableSerializer(String[].class, type, (CombinedSerializer)simpleTypeSerializer2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1711 */     type = SchemaConstants.QNAME_TYPE_SIMPLE_URTYPE;
/* 1712 */     SimpleTypeSerializer simpleTypeSerializer1 = new SimpleTypeSerializer(type, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), XSDStringEncoder.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1719 */     registerReferenceableSerializer(String.class, type, (CombinedSerializer)simpleTypeSerializer1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void registerReferenceableSerializer(Class javaType, QName xmlType, CombinedSerializer ser) throws Exception {
/* 1729 */     registerReferenceableSerializer(javaType, xmlType, ser, SOAPVersion.SOAP_11);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void registerReferenceableSerializer(Class javaType, QName xmlType, CombinedSerializer ser, SOAPVersion version) throws Exception {
/* 1743 */     registerReferenceableSerializer(javaType, xmlType, ser, false, version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void registerReferenceableSerializer(Class javaType, QName xmlType, CombinedSerializer ser, boolean serializeAsRef) throws Exception {
/* 1758 */     registerReferenceableSerializer(javaType, xmlType, ser, serializeAsRef, SOAPVersion.SOAP_11);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void registerReferenceableSerializer(Class javaType, QName xmlType, CombinedSerializer ser, boolean serializeAsRef, SOAPVersion version) throws Exception {
/*      */     ReferenceableSerializerImpl referenceableSerializerImpl;
/* 1774 */     if (!(ser instanceof ReferenceableSerializerImpl))
/* 1775 */       referenceableSerializerImpl = new ReferenceableSerializerImpl(serializeAsRef, ser, version); 
/* 1776 */     registerSerializer(javaType, xmlType, (CombinedSerializer)referenceableSerializerImpl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void registerSerializer(Class javaType, QName xmlType, CombinedSerializer ser) throws Exception {
/* 1785 */     register(javaType, xmlType, (SerializerFactory)new SingletonSerializerFactory((Serializer)ser), (DeserializerFactory)new SingletonDeserializerFactory((Deserializer)ser));
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\StandardSOAPTypeMappings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */