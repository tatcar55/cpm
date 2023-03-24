/*     */ package com.sun.xml.rpc.encoding.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.CombinedSerializer;
/*     */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*     */ import com.sun.xml.rpc.encoding.SerializerConstants;
/*     */ import com.sun.xml.rpc.encoding.SingletonDeserializerFactory;
/*     */ import com.sun.xml.rpc.encoding.SingletonSerializerFactory;
/*     */ import com.sun.xml.rpc.encoding.TypeMappingImpl;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDBase64BinaryEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDBooleanEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDByteEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDateEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDateTimeCalendarEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDateTimeDateEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDecimalEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDoubleEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDFloatEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDHexBinaryEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDIntEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDIntegerEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDListTypeEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDLongEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDNegativeIntegerEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDNonNegativeIntegerEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDNonPositiveIntegerEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDPositiveIntegerEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDQNameEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDShortEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDStringEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDTimeEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedByteEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedIntEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedLongEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedShortEncoder;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class StandardLiteralTypeMappings
/*     */   extends TypeMappingImpl
/*     */   implements SerializerConstants, InternalEncodingConstants
/*     */ {
/*     */   public StandardLiteralTypeMappings() throws Exception {
/*  81 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer51 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_BOOLEAN, "", XSDBooleanEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     registerSerializer(boolean.class, SchemaConstants.QNAME_TYPE_BOOLEAN, (CombinedSerializer)literalSimpleTypeSerializer51);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer50 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_BOOLEAN, "", XSDBooleanEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     registerSerializer(Boolean.class, SchemaConstants.QNAME_TYPE_BOOLEAN, (CombinedSerializer)literalSimpleTypeSerializer50);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer49 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_BYTE, "", XSDByteEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     registerSerializer(byte.class, SchemaConstants.QNAME_TYPE_BYTE, (CombinedSerializer)literalSimpleTypeSerializer49);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer48 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_BYTE, "", XSDByteEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     registerSerializer(Byte.class, SchemaConstants.QNAME_TYPE_BYTE, (CombinedSerializer)literalSimpleTypeSerializer48);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer47 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_BASE64_BINARY, "", XSDBase64BinaryEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     registerSerializer(byte[].class, SchemaConstants.QNAME_TYPE_BASE64_BINARY, (CombinedSerializer)literalSimpleTypeSerializer47);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer46 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_BASE64_BINARY, "", XSDBase64BinaryEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     registerSerializer(Byte[].class, SchemaConstants.QNAME_TYPE_BASE64_BINARY, (CombinedSerializer)literalSimpleTypeSerializer46);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer45 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_HEX_BINARY, "", XSDHexBinaryEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     registerSerializer(byte[].class, SchemaConstants.QNAME_TYPE_HEX_BINARY, (CombinedSerializer)literalSimpleTypeSerializer45);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer44 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_HEX_BINARY, "", XSDHexBinaryEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     registerSerializer(Byte[].class, SchemaConstants.QNAME_TYPE_HEX_BINARY, (CombinedSerializer)literalSimpleTypeSerializer44);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer43 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_DOUBLE, "", XSDDoubleEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     registerSerializer(double.class, SchemaConstants.QNAME_TYPE_DOUBLE, (CombinedSerializer)literalSimpleTypeSerializer43);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer42 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_DOUBLE, "", XSDDoubleEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     registerSerializer(Double.class, SchemaConstants.QNAME_TYPE_DOUBLE, (CombinedSerializer)literalSimpleTypeSerializer42);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer41 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_DECIMAL, "", XSDDecimalEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     registerSerializer(BigDecimal.class, SchemaConstants.QNAME_TYPE_DECIMAL, (CombinedSerializer)literalSimpleTypeSerializer41);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     QName qName1 = SchemaConstants.QNAME_TYPE_FLOAT;
/* 203 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer54 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_FLOAT, "", XSDFloatEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     registerSerializer(float.class, SchemaConstants.QNAME_TYPE_FLOAT, (CombinedSerializer)literalSimpleTypeSerializer54);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     qName1 = SchemaConstants.QNAME_TYPE_FLOAT;
/* 215 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer53 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_FLOAT, "", XSDFloatEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 220 */     registerSerializer(Float.class, SchemaConstants.QNAME_TYPE_FLOAT, (CombinedSerializer)literalSimpleTypeSerializer53);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 226 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer40 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_INT, "", XSDIntEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     registerSerializer(int.class, SchemaConstants.QNAME_TYPE_INT, (CombinedSerializer)literalSimpleTypeSerializer40);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer39 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_INT, "", XSDIntEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     registerSerializer(Integer.class, SchemaConstants.QNAME_TYPE_INT, (CombinedSerializer)literalSimpleTypeSerializer39);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 248 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer38 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_INTEGER, "", XSDIntegerEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     registerSerializer(BigInteger.class, SchemaConstants.QNAME_TYPE_INTEGER, (CombinedSerializer)literalSimpleTypeSerializer38);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer37 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_LONG, "", XSDLongEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 264 */     registerSerializer(long.class, SchemaConstants.QNAME_TYPE_LONG, (CombinedSerializer)literalSimpleTypeSerializer37);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer36 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_LONG, "", XSDLongEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     registerSerializer(Long.class, SchemaConstants.QNAME_TYPE_LONG, (CombinedSerializer)literalSimpleTypeSerializer36);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer35 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_SHORT, "", XSDShortEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     registerSerializer(short.class, SchemaConstants.QNAME_TYPE_SHORT, (CombinedSerializer)literalSimpleTypeSerializer35);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 292 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer34 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_SHORT, "", XSDShortEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 297 */     registerSerializer(Short.class, SchemaConstants.QNAME_TYPE_SHORT, (CombinedSerializer)literalSimpleTypeSerializer34);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer33 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_STRING, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 308 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_STRING, (CombinedSerializer)literalSimpleTypeSerializer33);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer32 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_DATE_TIME, "", XSDDateTimeCalendarEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     registerSerializer(Calendar.class, SchemaConstants.QNAME_TYPE_DATE_TIME, (CombinedSerializer)literalSimpleTypeSerializer32);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer31 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_DATE_TIME, "", XSDDateTimeDateEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     registerSerializer(Date.class, SchemaConstants.QNAME_TYPE_DATE_TIME, (CombinedSerializer)literalSimpleTypeSerializer31);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer30 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_DATE, "", XSDDateEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 341 */     registerSerializer(Calendar.class, SchemaConstants.QNAME_TYPE_DATE, (CombinedSerializer)literalSimpleTypeSerializer30);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 347 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer29 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_QNAME, "", XSDQNameEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     registerSerializer(QName.class, SchemaConstants.QNAME_TYPE_QNAME, (CombinedSerializer)literalSimpleTypeSerializer29);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer28 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_LANGUAGE, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 363 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_LANGUAGE, (CombinedSerializer)literalSimpleTypeSerializer28);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 369 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer27 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_NORMALIZED_STRING, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 374 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_NORMALIZED_STRING, (CombinedSerializer)literalSimpleTypeSerializer27);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 380 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer26 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_TOKEN, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_TOKEN, (CombinedSerializer)literalSimpleTypeSerializer26);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 391 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer25 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_NMTOKEN, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 396 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_NMTOKEN, (CombinedSerializer)literalSimpleTypeSerializer25);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 402 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer24 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_NAME, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_NAME, (CombinedSerializer)literalSimpleTypeSerializer24);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 413 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer23 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_NCNAME, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 418 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_NCNAME, (CombinedSerializer)literalSimpleTypeSerializer23);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 424 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer22 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_ID, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 429 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_ID, (CombinedSerializer)literalSimpleTypeSerializer22);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer21 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_POSITIVE_INTEGER, "", XSDPositiveIntegerEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 441 */     registerSerializer(BigInteger.class, SchemaConstants.QNAME_TYPE_POSITIVE_INTEGER, (CombinedSerializer)literalSimpleTypeSerializer21);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 449 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer20 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_NON_POSITIVE_INTEGER, "", XSDNonPositiveIntegerEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 454 */     registerSerializer(BigInteger.class, SchemaConstants.QNAME_TYPE_NON_POSITIVE_INTEGER, (CombinedSerializer)literalSimpleTypeSerializer20);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 462 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer19 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_NEGATIVE_INTEGER, "", XSDNegativeIntegerEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 467 */     registerSerializer(BigInteger.class, SchemaConstants.QNAME_TYPE_NEGATIVE_INTEGER, (CombinedSerializer)literalSimpleTypeSerializer19);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 474 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer18 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER, "", XSDNonNegativeIntegerEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 479 */     registerSerializer(BigInteger.class, SchemaConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER, (CombinedSerializer)literalSimpleTypeSerializer18);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 486 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer17 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_UNSIGNED_LONG, "", XSDUnsignedLongEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 491 */     registerSerializer(BigInteger.class, SchemaConstants.QNAME_TYPE_UNSIGNED_LONG, (CombinedSerializer)literalSimpleTypeSerializer17);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 498 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer16 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_UNSIGNED_INT, "", XSDUnsignedIntEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 503 */     registerSerializer(long.class, SchemaConstants.QNAME_TYPE_UNSIGNED_INT, (CombinedSerializer)literalSimpleTypeSerializer16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 509 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer15 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_UNSIGNED_INT, "", XSDUnsignedIntEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 514 */     registerSerializer(Long.class, SchemaConstants.QNAME_TYPE_UNSIGNED_INT, (CombinedSerializer)literalSimpleTypeSerializer15);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 521 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer14 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT, "", XSDUnsignedShortEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 526 */     registerSerializer(int.class, SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT, (CombinedSerializer)literalSimpleTypeSerializer14);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 532 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer13 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT, "", XSDUnsignedShortEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 537 */     registerSerializer(Integer.class, SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT, (CombinedSerializer)literalSimpleTypeSerializer13);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 544 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer12 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE, "", XSDUnsignedByteEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 549 */     registerSerializer(short.class, SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE, (CombinedSerializer)literalSimpleTypeSerializer12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 555 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer11 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE, "", XSDUnsignedByteEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 560 */     registerSerializer(Short.class, SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE, (CombinedSerializer)literalSimpleTypeSerializer11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 567 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer10 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_DURATION, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 572 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_DURATION, (CombinedSerializer)literalSimpleTypeSerializer10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 579 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer9 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_TIME, "", XSDTimeEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 584 */     registerSerializer(Calendar.class, SchemaConstants.QNAME_TYPE_TIME, (CombinedSerializer)literalSimpleTypeSerializer9);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 591 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer8 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_G_YEAR_MONTH, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 596 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_G_YEAR_MONTH, (CombinedSerializer)literalSimpleTypeSerializer8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 603 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer7 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_G_YEAR, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 608 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_G_YEAR_MONTH, (CombinedSerializer)literalSimpleTypeSerializer7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 615 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer6 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_G_MONTH_DAY, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 620 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_G_MONTH_DAY, (CombinedSerializer)literalSimpleTypeSerializer6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 627 */     QName type = SchemaConstants.QNAME_TYPE_G_DAY;
/* 628 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer52 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_G_DAY, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 633 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_G_DAY, (CombinedSerializer)literalSimpleTypeSerializer52);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 640 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer5 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_G_MONTH, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 645 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_G_MONTH, (CombinedSerializer)literalSimpleTypeSerializer5);
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
/* 680 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer4 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_IDREF, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 685 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_IDREF, (CombinedSerializer)literalSimpleTypeSerializer4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 693 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer3 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_IDREFS, "", XSDListTypeEncoder.getInstance(XSDStringEncoder.getInstance(), String.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 700 */     registerSerializer(String[].class, SchemaConstants.QNAME_TYPE_IDREFS, (CombinedSerializer)literalSimpleTypeSerializer3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 708 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer2 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_NMTOKENS, "", XSDListTypeEncoder.getInstance(XSDStringEncoder.getInstance(), String.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 715 */     registerSerializer(String[].class, SchemaConstants.QNAME_TYPE_NMTOKENS, (CombinedSerializer)literalSimpleTypeSerializer2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 723 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer1 = new LiteralSimpleTypeSerializer(SchemaConstants.QNAME_TYPE_SIMPLE_URTYPE, "", XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 728 */     registerSerializer(String.class, SchemaConstants.QNAME_TYPE_SIMPLE_URTYPE, (CombinedSerializer)literalSimpleTypeSerializer1);
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
/*     */   private void registerSerializer(TypeMapping mapping, Class javaType, QName xmlType, CombinedSerializer ser) throws Exception {
/* 742 */     register(javaType, xmlType, (SerializerFactory)new SingletonSerializerFactory((Serializer)ser), (DeserializerFactory)new SingletonDeserializerFactory((Deserializer)ser));
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
/*     */   private void registerSerializer(Class javaType, QName xmlType, CombinedSerializer ser) throws Exception {
/* 755 */     register(javaType, xmlType, (SerializerFactory)new SingletonSerializerFactory((Serializer)ser), (DeserializerFactory)new SingletonDeserializerFactory((Deserializer)ser));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\literal\StandardLiteralTypeMappings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */