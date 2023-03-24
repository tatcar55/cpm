/*     */ package com.sun.xml.rpc.processor.generator.writer;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPConstants;
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorException;
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorUtil;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPListType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.BuiltInTypes;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class SimpleTypeSerializerWriter
/*     */   extends SerializerWriterBase
/*     */   implements GeneratorConstants
/*     */ {
/*  59 */   private String encoder = null;
/*     */   private String serializerMemberName;
/*     */   private SOAPType dataType;
/*     */   
/*     */   public SimpleTypeSerializerWriter(SOAPType type, Names names) {
/*  64 */     super((AbstractType)type, names);
/*  65 */     this.encoder = getTypeEncoder((AbstractType)type);
/*  66 */     this.dataType = type;
/*     */     
/*  68 */     if (this.encoder == null) {
/*  69 */       throw new GeneratorException("generator.simpleTypeSerializerWriter.no.encoder.for.type", new Object[] { type.getName().toString(), type.getJavaType().getName() });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     String partialSerializerName = this.encoder.substring(0, this.encoder.lastIndexOf("Encoder"));
/*     */     
/*  77 */     if (partialSerializerName.startsWith("XSD"))
/*  78 */       partialSerializerName = partialSerializerName.substring(3); 
/*  79 */     String serializerName = partialSerializerName + "_Serializer";
/*  80 */     this.serializerMemberName = names.getClassMemberName(partialSerializerName, (AbstractType)type, "_Serializer");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private QName getQNameTypeString() {
/*  88 */     if (this.soapVer.equals(SOAPVersion.SOAP_12.toString())) {
/*  89 */       return SOAP12Constants.QNAME_TYPE_STRING;
/*     */     }
/*  91 */     return SOAPConstants.QNAME_TYPE_STRING;
/*     */   }
/*     */   
/*     */   private QName getQNameTypeBase64Binary() {
/*  95 */     if (this.soapVer.equals(SOAPVersion.SOAP_12.toString())) {
/*  96 */       return SOAP12Constants.QNAME_TYPE_BASE64_BINARY;
/*     */     }
/*  98 */     return SOAPConstants.QNAME_TYPE_BASE64_BINARY;
/*     */   }
/*     */   
/*     */   private QName getQNameTypeBase64() {
/* 102 */     if (this.soapVer.equals(SOAPVersion.SOAP_12.toString())) {
/* 103 */       return SOAP12Constants.QNAME_TYPE_BASE64;
/*     */     }
/* 105 */     return SOAPConstants.QNAME_TYPE_BASE64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createSerializer(IndentingWriter p, StringBuffer typeName, String serName, boolean encodeTypes, boolean multiRefEncoding, String typeMapping) throws IOException {
/* 116 */     SOAPType type = (SOAPType)this.type;
/* 117 */     String nillable = type.isNillable() ? "NULLABLE" : "NOT_NULLABLE";
/* 118 */     String referenceable = type.isReferenceable() ? "REFERENCEABLE" : "NOT_REFERENCEABLE";
/*     */ 
/*     */ 
/*     */     
/* 122 */     String multiRef = (multiRefEncoding && type.isReferenceable()) ? "SERIALIZE_AS_REF" : "DONT_SERIALIZE_AS_REF";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     String encodeType = encodeTypes ? "ENCODE_TYPE" : "DONT_ENCODE_TYPE";
/*     */     
/* 129 */     declareType(p, typeName, type.getName(), false, false);
/* 130 */     QName typeQName = type.getName();
/*     */     
/* 132 */     if (attachmentTypes.contains(typeQName)) {
/* 133 */       boolean serAsAttachment = (!typeQName.equals(getQNameTypeString()) && !typeQName.equals(BuiltInTypes.STRING));
/*     */ 
/*     */       
/* 136 */       p.plnI(serializerName() + " " + serName + " = new " + "AttachmentSerializer" + "(" + typeName + ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       p.pln(encodeType + ", " + nillable + ", " + getEncodingStyleString() + ", " + serAsAttachment + ", " + this.encoder + ".getInstance()," + getSOAPVersionString() + ");");
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
/* 158 */       multiRef = "DONT_SERIALIZE_AS_REF";
/* 159 */     } else if (typeQName.equals(BuiltInTypes.BASE64_BINARY) || typeQName.equals(getQNameTypeBase64Binary()) || typeQName.equals(getQNameTypeBase64())) {
/*     */ 
/*     */ 
/*     */       
/* 163 */       p.plnI(serializerName() + " " + serName + " = new " + "SimpleMultiTypeSerializer" + "(" + typeName + ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       p.pln(encodeType + ", " + nillable + ", " + getEncodingStyleString() + ", " + this.encoder + ".getInstance(),");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 181 */       p.plnI("new QName[] {");
/* 182 */       GeneratorUtil.writeNewQName(p, BuiltInTypes.BASE64_BINARY);
/* 183 */       p.pln(",");
/* 184 */       GeneratorUtil.writeNewQName(p, getQNameTypeBase64Binary());
/* 185 */       p.pln(",");
/* 186 */       GeneratorUtil.writeNewQName(p, getQNameTypeBase64());
/* 187 */       p.pOln("});");
/*     */     } else {
/* 189 */       p.plnI(serializerName() + " " + serName + " = new " + "SimpleTypeSerializer" + "(" + typeName + ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 198 */       if (type instanceof SOAPListType) {
/* 199 */         p.pln(encodeType + ", " + nillable + ", " + getEncodingStyleString() + ", " + this.encoder + ".getInstance(" + getItemType() + "));");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 211 */         p.pln(encodeType + ", " + nillable + ", " + getEncodingStyleString() + ", " + this.encoder + ".getInstance());");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     p.pO();
/* 222 */     if (type.isReferenceable()) {
/* 223 */       p.plnI(serName + " = new " + "ReferenceableSerializerImpl" + "(" + multiRef + ", " + serName + ", " + getSOAPVersionString() + ");");
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
/* 234 */       p.pO();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void declareSerializer(IndentingWriter p, boolean isStatic, boolean isFinal) throws IOException {
/* 243 */     String modifier = getPrivateModifier(isStatic, isFinal);
/* 244 */     p.pln(modifier + serializerName() + " " + serializerMemberName() + ";");
/*     */   }
/*     */   
/*     */   public String serializerMemberName() {
/* 248 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   public String deserializerMemberName() {
/* 252 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
/* 256 */     return "private " + getModifier(isStatic, isFinal);
/*     */   }
/*     */   
/*     */   public static String getTypeEncoder(AbstractType type) {
/* 260 */     QName name = type.getName();
/* 261 */     String encoder = null;
/* 262 */     if (type instanceof SOAPListType) {
/* 263 */       encoder = "XSDListTypeEncoder";
/*     */     } else {
/* 265 */       encoder = (String)encoderMap.get(name);
/*     */     } 
/*     */     
/* 268 */     if (encoder == null) {
/* 269 */       String javaName = type.getJavaType().getName();
/* 270 */       if (name.equals(BuiltInTypes.DATE_TIME) || name.equals(SOAP12Constants.QNAME_TYPE_DATE_TIME) || name.equals(SOAPConstants.QNAME_TYPE_DATE_TIME)) {
/*     */ 
/*     */         
/* 273 */         if (javaName.equals("java.util.Date")) {
/* 274 */           encoder = "XSDDateTimeDateEncoder";
/* 275 */         } else if (javaName.equals("java.util.Calendar")) {
/* 276 */           encoder = "XSDDateTimeCalendarEncoder";
/*     */         } 
/* 278 */       } else if (name.equals(BuiltInTypes.BASE64_BINARY) || name.equals(SOAPConstants.QNAME_TYPE_BASE64_BINARY) || name.equals(SOAP12Constants.QNAME_TYPE_BASE64_BINARY) || name.equals(SOAPConstants.QNAME_TYPE_BASE64) || name.equals(SOAP12Constants.QNAME_TYPE_BASE64)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 284 */         if (javaName.equals("byte[]")) {
/* 285 */           encoder = "XSDBase64BinaryEncoder";
/*     */         }
/* 287 */       } else if (name.equals(BuiltInTypes.HEX_BINARY) || name.equals(SOAP12Constants.QNAME_TYPE_HEX_BINARY) || name.equals(SOAPConstants.QNAME_TYPE_HEX_BINARY)) {
/*     */ 
/*     */ 
/*     */         
/* 291 */         if (javaName.equals("byte[]")) {
/* 292 */           encoder = "XSDHexBinaryEncoder";
/*     */         }
/*     */       } 
/*     */     } 
/* 296 */     return encoder;
/*     */   }
/*     */   
/*     */   public static String getTypeEncoder(QName typeName) {
/* 300 */     return (String)encoderMap.get(typeName);
/*     */   }
/*     */   
/*     */   protected String getEncoder() {
/* 304 */     return getTypeEncoder(this.type);
/*     */   }
/*     */   
/*     */   protected String getItemType() {
/* 308 */     String strType = null;
/* 309 */     SOAPListType lt = (SOAPListType)this.type;
/*     */ 
/*     */     
/* 312 */     if (lt.getItemType() instanceof com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType) {
/*     */ 
/*     */ 
/*     */       
/* 316 */       strType = new String(lt.getItemType().getJavaType().getName() + "_Encoder.getInstance(), " + lt.getItemType().getJavaType().getName() + ".class");
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 324 */       strType = getTypeEncoder((AbstractType)lt.getItemType()) + ".getInstance(), " + lt.getItemType().getJavaType().getName() + ".class";
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 331 */     if (strType == null) {
/* 332 */       throw new GeneratorException("generator.simpleTypeSerializerWriter.invalidType", new Object[] { lt.getItemType().getName().toString(), lt.getItemType().getJavaType().getName() });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 338 */     return strType;
/*     */   }
/*     */   
/* 341 */   private static Map encoderMap = null;
/* 342 */   private static Set attachmentTypes = null;
/*     */   
/*     */   static {
/* 345 */     attachmentTypes = new HashSet();
/* 346 */     attachmentTypes.add(QNAME_TYPE_IMAGE);
/* 347 */     attachmentTypes.add(QNAME_TYPE_MIME_MULTIPART);
/* 348 */     attachmentTypes.add(QNAME_TYPE_SOURCE);
/* 349 */     attachmentTypes.add(QNAME_TYPE_DATA_HANDLER);
/* 350 */     attachmentTypes.add(BuiltInTypes.STRING);
/* 351 */     attachmentTypes.add(SOAPConstants.QNAME_TYPE_STRING);
/* 352 */     attachmentTypes.add(SOAP12Constants.QNAME_TYPE_STRING);
/*     */     
/* 354 */     encoderMap = new HashMap<Object, Object>();
/*     */     
/* 356 */     encoderMap.put(QNAME_TYPE_IMAGE, "ImageAttachmentEncoder");
/* 357 */     encoderMap.put(QNAME_TYPE_MIME_MULTIPART, "MimeMultipartAttachmentEncoder");
/* 358 */     encoderMap.put(QNAME_TYPE_SOURCE, "SourceAttachmentEncoder");
/* 359 */     encoderMap.put(QNAME_TYPE_DATA_HANDLER, "DataHandlerAttachmentEncoder");
/*     */ 
/*     */     
/* 362 */     encoderMap.put(BuiltInTypes.BOOLEAN, "XSDBooleanEncoder");
/* 363 */     encoderMap.put(BuiltInTypes.BYTE, "XSDByteEncoder");
/* 364 */     encoderMap.put(BuiltInTypes.BASE64_BINARY, null);
/* 365 */     encoderMap.put(BuiltInTypes.HEX_BINARY, null);
/* 366 */     encoderMap.put(BuiltInTypes.DOUBLE, "XSDDoubleEncoder");
/* 367 */     encoderMap.put(BuiltInTypes.FLOAT, "XSDFloatEncoder");
/* 368 */     encoderMap.put(BuiltInTypes.INT, "XSDIntEncoder");
/* 369 */     encoderMap.put(BuiltInTypes.INTEGER, "XSDIntegerEncoder");
/* 370 */     encoderMap.put(BuiltInTypes.LONG, "XSDLongEncoder");
/* 371 */     encoderMap.put(BuiltInTypes.SHORT, "XSDShortEncoder");
/* 372 */     encoderMap.put(BuiltInTypes.DECIMAL, "XSDDecimalEncoder");
/* 373 */     encoderMap.put(BuiltInTypes.DATE_TIME, null);
/*     */     
/* 375 */     encoderMap.put(BuiltInTypes.STRING, "XSDStringEncoder");
/* 376 */     encoderMap.put(BuiltInTypes.QNAME, "XSDQNameEncoder");
/*     */     
/* 378 */     encoderMap.put(BuiltInTypes.LANGUAGE, "XSDStringEncoder");
/* 379 */     encoderMap.put(BuiltInTypes.NORMALIZED_STRING, "XSDStringEncoder");
/* 380 */     encoderMap.put(BuiltInTypes.TOKEN, "XSDStringEncoder");
/* 381 */     encoderMap.put(BuiltInTypes.NMTOKEN, "XSDStringEncoder");
/* 382 */     encoderMap.put(BuiltInTypes.NAME, "XSDStringEncoder");
/* 383 */     encoderMap.put(BuiltInTypes.ID, "XSDStringEncoder");
/* 384 */     encoderMap.put(BuiltInTypes.NCNAME, "XSDStringEncoder");
/*     */     
/* 386 */     encoderMap.put(BuiltInTypes.POSITIVE_INTEGER, "XSDPositiveIntegerEncoder");
/*     */ 
/*     */     
/* 389 */     encoderMap.put(BuiltInTypes.NON_POSITIVE_INTEGER, "XSDNonPositiveIntegerEncoder");
/*     */ 
/*     */     
/* 392 */     encoderMap.put(BuiltInTypes.NEGATIVE_INTEGER, "XSDNegativeIntegerEncoder");
/*     */ 
/*     */     
/* 395 */     encoderMap.put(BuiltInTypes.NON_NEGATIVE_INTEGER, "XSDNonNegativeIntegerEncoder");
/*     */ 
/*     */     
/* 398 */     encoderMap.put(BuiltInTypes.UNSIGNED_LONG, "XSDUnsignedLongEncoder");
/*     */ 
/*     */     
/* 401 */     encoderMap.put(BuiltInTypes.UNSIGNED_INT, "XSDUnsignedIntEncoder");
/*     */ 
/*     */     
/* 404 */     encoderMap.put(BuiltInTypes.UNSIGNED_SHORT, "XSDUnsignedShortEncoder");
/*     */ 
/*     */     
/* 407 */     encoderMap.put(BuiltInTypes.UNSIGNED_BYTE, "XSDUnsignedByteEncoder");
/*     */ 
/*     */     
/* 410 */     encoderMap.put(BuiltInTypes.DURATION, "XSDStringEncoder");
/* 411 */     encoderMap.put(BuiltInTypes.TIME, "XSDTimeEncoder");
/* 412 */     encoderMap.put(BuiltInTypes.DATE, "XSDDateEncoder");
/* 413 */     encoderMap.put(BuiltInTypes.G_YEAR_MONTH, "XSDStringEncoder");
/* 414 */     encoderMap.put(BuiltInTypes.G_YEAR, "XSDStringEncoder");
/* 415 */     encoderMap.put(BuiltInTypes.G_MONTH_DAY, "XSDStringEncoder");
/* 416 */     encoderMap.put(BuiltInTypes.G_DAY, "XSDStringEncoder");
/* 417 */     encoderMap.put(BuiltInTypes.G_MONTH, "XSDStringEncoder");
/*     */     
/* 419 */     if (!VersionUtil.isJavaVersionGreaterThan1_3()) {
/* 420 */       encoderMap.put(BuiltInTypes.ANY_URI, "XSDStringEncoder");
/*     */     } else {
/* 422 */       encoderMap.put(BuiltInTypes.ANY_URI, "XSDAnyURIEncoder");
/* 423 */     }  encoderMap.put(BuiltInTypes.IDREF, "XSDStringEncoder");
/*     */     
/* 425 */     encoderMap.put(BuiltInTypes.IDREFS, "XSDListTypeEncoder");
/*     */     
/* 427 */     encoderMap.put(BuiltInTypes.NMTOKENS, "XSDListTypeEncoder");
/*     */ 
/*     */ 
/*     */     
/* 431 */     encoderMap.put(SOAPConstants.QNAME_TYPE_BOOLEAN, "XSDBooleanEncoder");
/*     */ 
/*     */     
/* 434 */     encoderMap.put(SOAPConstants.QNAME_TYPE_BYTE, "XSDByteEncoder");
/* 435 */     encoderMap.put(SOAPConstants.QNAME_TYPE_BASE64_BINARY, null);
/* 436 */     encoderMap.put(SOAPConstants.QNAME_TYPE_DOUBLE, "XSDDoubleEncoder");
/*     */ 
/*     */     
/* 439 */     encoderMap.put(SOAPConstants.QNAME_TYPE_FLOAT, "XSDFloatEncoder");
/* 440 */     encoderMap.put(SOAPConstants.QNAME_TYPE_INT, "XSDIntEncoder");
/* 441 */     encoderMap.put(SOAPConstants.QNAME_TYPE_LONG, "XSDLongEncoder");
/* 442 */     encoderMap.put(SOAPConstants.QNAME_TYPE_SHORT, "XSDShortEncoder");
/* 443 */     encoderMap.put(SOAPConstants.QNAME_TYPE_DECIMAL, "XSDDecimalEncoder");
/*     */ 
/*     */     
/* 446 */     encoderMap.put(SOAPConstants.QNAME_TYPE_DATE_TIME, null);
/*     */     
/* 448 */     encoderMap.put(SOAPConstants.QNAME_TYPE_STRING, "XSDStringEncoder");
/*     */ 
/*     */     
/* 451 */     encoderMap.put(SOAPConstants.QNAME_TYPE_QNAME, "XSDQNameEncoder");
/*     */ 
/*     */     
/* 454 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_BOOLEAN, "XSDBooleanEncoder");
/*     */ 
/*     */     
/* 457 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_BYTE, "XSDByteEncoder");
/* 458 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_BASE64_BINARY, null);
/* 459 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_DOUBLE, "XSDDoubleEncoder");
/*     */ 
/*     */     
/* 462 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_FLOAT, "XSDFloatEncoder");
/*     */ 
/*     */     
/* 465 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_INT, "XSDIntEncoder");
/* 466 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_LONG, "XSDLongEncoder");
/* 467 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_SHORT, "XSDShortEncoder");
/*     */ 
/*     */     
/* 470 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_DECIMAL, "XSDDecimalEncoder");
/*     */ 
/*     */     
/* 473 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_DATE_TIME, null);
/*     */     
/* 475 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_STRING, "XSDStringEncoder");
/*     */ 
/*     */     
/* 478 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_QNAME, "XSDQNameEncoder");
/*     */ 
/*     */     
/* 481 */     encoderMap.put(SOAPConstants.QNAME_TYPE_DATE, "XSDDateEncoder");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 486 */     encoderMap.put(BuiltInTypes.ANY_SIMPLE_URTYPE, "XSDStringEncoder");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\SimpleTypeSerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */