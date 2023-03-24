/*     */ package com.sun.xml.rpc.processor.generator.writer;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPConstants;
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorException;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralListType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.BuiltInTypes;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class LiteralSimpleSerializerWriter
/*     */   extends LiteralSerializerWriterBase
/*     */   implements GeneratorConstants
/*     */ {
/*  55 */   private String encoder = null;
/*     */   private String serializerMemberName;
/*     */   private LiteralType dataType;
/*     */   
/*     */   public LiteralSimpleSerializerWriter(LiteralType type, Names names) {
/*  60 */     super((AbstractType)type, names);
/*  61 */     this.dataType = type;
/*  62 */     this.encoder = getTypeEncoder((AbstractType)type);
/*  63 */     if (this.encoder == null) {
/*  64 */       throw new GeneratorException("generator.simpleTypeSerializerWriter.no.encoder.for.type", new Object[] { type.getName().toString(), type.getJavaType().getName() });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     String partialSerializerName = this.encoder.substring(3, this.encoder.lastIndexOf("Encoder"));
/*     */     
/*  72 */     String serializerName = partialSerializerName + "_Serializer";
/*  73 */     this.serializerMemberName = names.getClassMemberName(partialSerializerName, (AbstractType)type, "_Serializer");
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
/*     */   public void createSerializer(IndentingWriter p, StringBuffer typeName, String serName, boolean encodeTypes, boolean multiRefEncoding, String typeMapping) throws IOException {
/*  88 */     LiteralType type = (LiteralType)this.type;
/*  89 */     declareType(p, typeName, type.getName(), false, false);
/*  90 */     QName typeQName = type.getName();
/*  91 */     p.plnI(serializerName() + " " + serName + " = new " + "LiteralSimpleTypeSerializer" + "(" + typeName + ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     if (type instanceof LiteralListType) {
/* 101 */       p.pln("\"\", " + this.encoder + ".getInstance(" + getItemType() + "));");
/*     */     } else {
/* 103 */       p.pln("\"\", " + this.encoder + ".getInstance());");
/* 104 */     }  p.pO();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void declareSerializer(IndentingWriter p, boolean isStatic, boolean isFinal) throws IOException {
/* 112 */     String modifier = getPrivateModifier(isStatic, isFinal);
/* 113 */     p.pln(modifier + serializerName() + " " + serializerMemberName() + ";");
/*     */   }
/*     */   
/*     */   public String serializerMemberName() {
/* 117 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   public String deserializerMemberName() {
/* 121 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
/* 125 */     return "private " + getModifier(isStatic, isFinal);
/*     */   }
/*     */   
/*     */   public static String getTypeEncoder(AbstractType type) {
/* 129 */     QName name = type.getName();
/* 130 */     String encoder = null;
/* 131 */     if (type instanceof LiteralListType) {
/* 132 */       encoder = "XSDListTypeEncoder";
/*     */     } else {
/* 134 */       encoder = (String)encoderMap.get(name);
/*     */     } 
/*     */     
/* 137 */     if (encoder == null) {
/* 138 */       String javaName = type.getJavaType().getName();
/* 139 */       if (name.equals(BuiltInTypes.DATE_TIME) || name.equals(SOAP12Constants.QNAME_TYPE_DATE_TIME) || name.equals(SOAPConstants.QNAME_TYPE_DATE_TIME)) {
/*     */ 
/*     */         
/* 142 */         if (javaName.equals("java.util.Date")) {
/* 143 */           encoder = "XSDDateTimeDateEncoder";
/* 144 */         } else if (javaName.equals("java.util.Calendar")) {
/* 145 */           encoder = "XSDDateTimeCalendarEncoder";
/*     */         } 
/* 147 */       } else if (name.equals(BuiltInTypes.BASE64_BINARY) || name.equals(SOAPConstants.QNAME_TYPE_BASE64_BINARY) || name.equals(SOAP12Constants.QNAME_TYPE_BASE64_BINARY) || name.equals(SOAPConstants.QNAME_TYPE_BASE64) || name.equals(SOAP12Constants.QNAME_TYPE_BASE64)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         if (javaName.equals("byte[]")) {
/* 154 */           encoder = "XSDBase64BinaryEncoder";
/*     */         }
/* 156 */       } else if (name.equals(BuiltInTypes.HEX_BINARY) || name.equals(SOAPConstants.QNAME_TYPE_HEX_BINARY) || name.equals(SOAP12Constants.QNAME_TYPE_HEX_BINARY)) {
/*     */ 
/*     */ 
/*     */         
/* 160 */         if (javaName.equals("byte[]")) {
/* 161 */           encoder = "XSDHexBinaryEncoder";
/*     */         }
/* 163 */       } else if (javaName.equals("java.lang.String")) {
/* 164 */         encoder = "XSDStringEncoder";
/* 165 */       } else if (name.equals(BuiltInTypes.IDREF)) {
/*     */ 
/*     */         
/* 168 */         encoder = "XSDStringEncoder";
/*     */       } 
/*     */     } 
/* 171 */     return encoder;
/*     */   }
/*     */   
/*     */   protected String getEncoder() {
/* 175 */     return getTypeEncoder(this.type);
/*     */   }
/*     */   
/*     */   protected String getItemType() {
/* 179 */     String strType = null;
/* 180 */     LiteralListType lt = (LiteralListType)this.type;
/*     */ 
/*     */     
/* 183 */     if (lt.getItemType() instanceof com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType) {
/*     */ 
/*     */ 
/*     */       
/* 187 */       strType = new String(lt.getItemType().getJavaType().getName() + "_Encoder.getInstance(), " + lt.getItemType().getJavaType().getName() + ".class");
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 195 */       strType = getTypeEncoder((AbstractType)lt.getItemType()) + ".getInstance(), " + lt.getItemType().getJavaType().getName() + ".class";
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     if (strType == null) {
/* 203 */       throw new GeneratorException("generator.simpleTypeSerializerWriter.invalidType", new Object[] { lt.getItemType().getName().toString(), lt.getItemType().getJavaType().getName() });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     return strType;
/*     */   }
/*     */   
/* 212 */   private static Map encoderMap = null;
/*     */   
/*     */   static {
/* 215 */     encoderMap = new HashMap<Object, Object>();
/*     */     
/* 217 */     encoderMap.put(BuiltInTypes.BOOLEAN, "XSDBooleanEncoder");
/* 218 */     encoderMap.put(BuiltInTypes.BYTE, "XSDByteEncoder");
/* 219 */     encoderMap.put(BuiltInTypes.BASE64_BINARY, null);
/* 220 */     encoderMap.put(BuiltInTypes.HEX_BINARY, null);
/* 221 */     encoderMap.put(BuiltInTypes.DOUBLE, "XSDDoubleEncoder");
/* 222 */     encoderMap.put(BuiltInTypes.FLOAT, "XSDFloatEncoder");
/* 223 */     encoderMap.put(BuiltInTypes.INT, "XSDIntEncoder");
/* 224 */     encoderMap.put(BuiltInTypes.INTEGER, "XSDIntegerEncoder");
/* 225 */     encoderMap.put(BuiltInTypes.LONG, "XSDLongEncoder");
/* 226 */     encoderMap.put(BuiltInTypes.SHORT, "XSDShortEncoder");
/* 227 */     encoderMap.put(BuiltInTypes.DECIMAL, "XSDDecimalEncoder");
/* 228 */     encoderMap.put(BuiltInTypes.DATE_TIME, null);
/*     */     
/* 230 */     encoderMap.put(BuiltInTypes.STRING, "XSDStringEncoder");
/* 231 */     encoderMap.put(BuiltInTypes.QNAME, "XSDQNameEncoder");
/*     */ 
/*     */     
/* 234 */     encoderMap.put(BuiltInTypes.TIME, "XSDTimeEncoder");
/*     */     
/* 236 */     encoderMap.put(BuiltInTypes.NMTOKENS, "XSDListTypeEncoder");
/* 237 */     encoderMap.put(BuiltInTypes.IDREFS, "XSDListTypeEncoder");
/* 238 */     encoderMap.put(BuiltInTypes.POSITIVE_INTEGER, "XSDPositiveIntegerEncoder");
/*     */ 
/*     */     
/* 241 */     encoderMap.put(BuiltInTypes.NON_POSITIVE_INTEGER, "XSDNonPositiveIntegerEncoder");
/*     */ 
/*     */     
/* 244 */     encoderMap.put(BuiltInTypes.NEGATIVE_INTEGER, "XSDNegativeIntegerEncoder");
/*     */ 
/*     */     
/* 247 */     encoderMap.put(BuiltInTypes.NON_NEGATIVE_INTEGER, "XSDNonNegativeIntegerEncoder");
/*     */ 
/*     */     
/* 250 */     encoderMap.put(BuiltInTypes.UNSIGNED_LONG, "XSDUnsignedLongEncoder");
/*     */ 
/*     */     
/* 253 */     encoderMap.put(BuiltInTypes.UNSIGNED_INT, "XSDUnsignedIntEncoder");
/*     */ 
/*     */     
/* 256 */     encoderMap.put(BuiltInTypes.UNSIGNED_SHORT, "XSDUnsignedShortEncoder");
/*     */ 
/*     */     
/* 259 */     encoderMap.put(BuiltInTypes.UNSIGNED_BYTE, "XSDUnsignedByteEncoder");
/*     */ 
/*     */     
/* 262 */     encoderMap.put(BuiltInTypes.DATE, "XSDDateEncoder");
/*     */     
/* 264 */     encoderMap.put(BuiltInTypes.LIST, "XSDListTypeEncoder");
/*     */ 
/*     */     
/* 267 */     encoderMap.put(BuiltInTypes.ANY_SIMPLE_URTYPE, "XSDStringEncoder");
/*     */ 
/*     */     
/* 270 */     if (!VersionUtil.isJavaVersionGreaterThan1_3()) {
/* 271 */       encoderMap.put(BuiltInTypes.ANY_URI, "XSDStringEncoder");
/*     */     } else {
/* 273 */       encoderMap.put(BuiltInTypes.ANY_URI, "XSDAnyURIEncoder");
/*     */     } 
/*     */     
/* 276 */     encoderMap.put(SOAPConstants.QNAME_TYPE_BOOLEAN, "XSDBooleanEncoder");
/*     */ 
/*     */     
/* 279 */     encoderMap.put(SOAPConstants.QNAME_TYPE_BYTE, "XSDByteEncoder");
/* 280 */     encoderMap.put(SOAPConstants.QNAME_TYPE_BASE64_BINARY, null);
/* 281 */     encoderMap.put(SOAPConstants.QNAME_TYPE_DOUBLE, "XSDDoubleEncoder");
/*     */ 
/*     */     
/* 284 */     encoderMap.put(SOAPConstants.QNAME_TYPE_FLOAT, "XSDFloatEncoder");
/* 285 */     encoderMap.put(SOAPConstants.QNAME_TYPE_INT, "XSDIntEncoder");
/* 286 */     encoderMap.put(SOAPConstants.QNAME_TYPE_LONG, "XSDLongEncoder");
/* 287 */     encoderMap.put(SOAPConstants.QNAME_TYPE_SHORT, "XSDShortEncoder");
/* 288 */     encoderMap.put(SOAPConstants.QNAME_TYPE_DECIMAL, "XSDDecimalEncoder");
/*     */ 
/*     */     
/* 291 */     encoderMap.put(SOAPConstants.QNAME_TYPE_DATE_TIME, null);
/*     */     
/* 293 */     encoderMap.put(SOAPConstants.QNAME_TYPE_STRING, "XSDStringEncoder");
/*     */ 
/*     */     
/* 296 */     encoderMap.put(SOAPConstants.QNAME_TYPE_QNAME, "XSDQNameEncoder");
/*     */ 
/*     */     
/* 299 */     encoderMap.put(SOAPConstants.QNAME_TYPE_TIME, "XSDTimeEncoder");
/*     */     
/* 301 */     encoderMap.put(SOAPConstants.QNAME_TYPE_NMTOKENS, "XSDListTypeEncoder");
/*     */ 
/*     */     
/* 304 */     encoderMap.put(SOAPConstants.QNAME_TYPE_IDREFS, "XSDListTypeEncoder");
/*     */ 
/*     */     
/* 307 */     encoderMap.put(SOAPConstants.QNAME_TYPE_POSITIVE_INTEGER, "XSDPositiveIntegerEncoder");
/*     */ 
/*     */     
/* 310 */     encoderMap.put(SOAPConstants.QNAME_TYPE_NON_POSITIVE_INTEGER, "XSDNonPositiveIntegerEncoder");
/*     */ 
/*     */     
/* 313 */     encoderMap.put(SOAPConstants.QNAME_TYPE_NEGATIVE_INTEGER, "XSDNegativeIntegerEncoder");
/*     */ 
/*     */     
/* 316 */     encoderMap.put(SOAPConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER, "XSDNonNegativeIntegerEncoder");
/*     */ 
/*     */     
/* 319 */     encoderMap.put(SOAPConstants.QNAME_TYPE_UNSIGNED_LONG, "XSDUnsignedLongEncoder");
/*     */ 
/*     */     
/* 322 */     encoderMap.put(SOAPConstants.QNAME_TYPE_UNSIGNED_INT, "XSDUnsignedIntEncoder");
/*     */ 
/*     */     
/* 325 */     encoderMap.put(SOAPConstants.QNAME_TYPE_UNSIGNED_SHORT, "XSDUnsignedShortEncoder");
/*     */ 
/*     */     
/* 328 */     encoderMap.put(SOAPConstants.QNAME_TYPE_UNSIGNED_BYTE, "XSDUnsignedByteEncoder");
/*     */ 
/*     */     
/* 331 */     encoderMap.put(SOAPConstants.QNAME_TYPE_DATE, "XSDDateEncoder");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     if (!VersionUtil.isJavaVersionGreaterThan1_3()) {
/* 337 */       encoderMap.put(SOAPConstants.QNAME_TYPE_ANY_URI, "XSDStringEncoder");
/*     */     }
/*     */     else {
/*     */       
/* 341 */       encoderMap.put(SOAPConstants.QNAME_TYPE_ANY_URI, "XSDAnyURIEncoder");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 346 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_BOOLEAN, "XSDBooleanEncoder");
/*     */ 
/*     */     
/* 349 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_BYTE, "XSDByteEncoder");
/* 350 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_BASE64_BINARY, null);
/* 351 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_DOUBLE, "XSDDoubleEncoder");
/*     */ 
/*     */     
/* 354 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_FLOAT, "XSDFloatEncoder");
/*     */ 
/*     */     
/* 357 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_INT, "XSDIntEncoder");
/* 358 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_LONG, "XSDLongEncoder");
/* 359 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_SHORT, "XSDShortEncoder");
/*     */ 
/*     */     
/* 362 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_DECIMAL, "XSDDecimalEncoder");
/*     */ 
/*     */     
/* 365 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_DATE_TIME, null);
/*     */     
/* 367 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_STRING, "XSDStringEncoder");
/*     */ 
/*     */     
/* 370 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_QNAME, "XSDQNameEncoder");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_TIME, "XSDTimeEncoder");
/*     */     
/* 377 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_NMTOKENS, "XSDListTypeEncoder");
/*     */ 
/*     */     
/* 380 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_IDREFS, "XSDListTypeEncoder");
/*     */ 
/*     */     
/* 383 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_POSITIVE_INTEGER, "XSDPositiveIntegerEncoder");
/*     */ 
/*     */     
/* 386 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_NON_POSITIVE_INTEGER, "XSDNonPositiveIntegerEncoder");
/*     */ 
/*     */     
/* 389 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_NEGATIVE_INTEGER, "XSDNegativeIntegerEncoder");
/*     */ 
/*     */     
/* 392 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_NON_NEGATIVE_INTEGER, "XSDNonNegativeIntegerEncoder");
/*     */ 
/*     */     
/* 395 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_UNSIGNED_LONG, "XSDUnsignedLongEncoder");
/*     */ 
/*     */     
/* 398 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_UNSIGNED_INT, "XSDUnsignedIntEncoder");
/*     */ 
/*     */     
/* 401 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_UNSIGNED_SHORT, "XSDUnsignedShortEncoder");
/*     */ 
/*     */     
/* 404 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_UNSIGNED_BYTE, "XSDUnsignedByteEncoder");
/*     */ 
/*     */     
/* 407 */     encoderMap.put(SOAP12Constants.QNAME_TYPE_DATE, "XSDDateEncoder");
/*     */ 
/*     */ 
/*     */     
/* 411 */     if (!VersionUtil.isJavaVersionGreaterThan1_3()) {
/* 412 */       encoderMap.put(SOAP12Constants.QNAME_TYPE_ANY_URI, "XSDStringEncoder");
/*     */     }
/*     */     else {
/*     */       
/* 416 */       encoderMap.put(SOAP12Constants.QNAME_TYPE_ANY_URI, "XSDAnyURIEncoder");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\LiteralSimpleSerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */