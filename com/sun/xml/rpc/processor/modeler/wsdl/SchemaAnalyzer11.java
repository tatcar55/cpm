/*     */ package com.sun.xml.rpc.processor.modeler.wsdl;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDAnyURIEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDBase64BinaryEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDBooleanEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDByteEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDateTimeCalendarEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDecimalEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDoubleEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDFloatEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDHexBinaryEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDIntEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDIntegerEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDListTypeEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDLongEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDQNameEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDShortEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDStringEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDTimeEncoder;
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.BuiltInTypes;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ public class SchemaAnalyzer11
/*     */   extends SchemaAnalyzerBase
/*     */ {
/*     */   public SchemaAnalyzer11(AbstractDocument document, ModelInfo modelInfo, Properties options, Set conflictingClassNames, JavaSimpleTypeCreator javaTypes) {
/*  85 */     super(document, modelInfo, options, conflictingClassNames, javaTypes);
/*     */   }
/*     */   
/*     */   protected void initializeMaps() {
/*  89 */     this._builtinSchemaTypeToJavaTypeMap = new HashMap<Object, Object>();
/*  90 */     if (this._useDataHandlerOnly) {
/*  91 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_IMAGE, this.javaTypes.DATA_HANDLER_JAVATYPE);
/*     */ 
/*     */       
/*  94 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_MIME_MULTIPART, this.javaTypes.DATA_HANDLER_JAVATYPE);
/*     */ 
/*     */       
/*  97 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_SOURCE, this.javaTypes.DATA_HANDLER_JAVATYPE);
/*     */     }
/*     */     else {
/*     */       
/* 101 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_IMAGE, this.javaTypes.IMAGE_JAVATYPE);
/*     */ 
/*     */       
/* 104 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_MIME_MULTIPART, this.javaTypes.MIME_MULTIPART_JAVATYPE);
/*     */ 
/*     */       
/* 107 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_SOURCE, this.javaTypes.SOURCE_JAVATYPE);
/*     */     } 
/*     */ 
/*     */     
/* 111 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_DATA_HANDLER, this.javaTypes.DATA_HANDLER_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 115 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.STRING, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 118 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.INTEGER, this.javaTypes.BIG_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 121 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.INT, this.javaTypes.INT_JAVATYPE);
/*     */ 
/*     */     
/* 124 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.LONG, this.javaTypes.LONG_JAVATYPE);
/*     */ 
/*     */     
/* 127 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.SHORT, this.javaTypes.SHORT_JAVATYPE);
/*     */ 
/*     */     
/* 130 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.DECIMAL, this.javaTypes.DECIMAL_JAVATYPE);
/*     */ 
/*     */     
/* 133 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.FLOAT, this.javaTypes.FLOAT_JAVATYPE);
/*     */ 
/*     */     
/* 136 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.DOUBLE, this.javaTypes.DOUBLE_JAVATYPE);
/*     */ 
/*     */     
/* 139 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.BOOLEAN, this.javaTypes.BOOLEAN_JAVATYPE);
/*     */ 
/*     */     
/* 142 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.BYTE, this.javaTypes.BYTE_JAVATYPE);
/*     */ 
/*     */     
/* 145 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.QNAME, this.javaTypes.QNAME_JAVATYPE);
/*     */ 
/*     */     
/* 148 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.DATE_TIME, this.javaTypes.CALENDAR_JAVATYPE);
/*     */ 
/*     */     
/* 151 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.BASE64_BINARY, this.javaTypes.BYTE_ARRAY_JAVATYPE);
/*     */ 
/*     */     
/* 154 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.HEX_BINARY, this.javaTypes.BYTE_ARRAY_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 158 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.NORMALIZED_STRING, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 161 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.TOKEN, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 164 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.LANGUAGE, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 167 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.NAME, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 170 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.NMTOKEN, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 173 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.NCNAME, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 176 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.ID, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.POSITIVE_INTEGER, this.javaTypes.BIG_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 184 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.NON_POSITIVE_INTEGER, this.javaTypes.BIG_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 187 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.NEGATIVE_INTEGER, this.javaTypes.BIG_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 190 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.NON_NEGATIVE_INTEGER, this.javaTypes.BIG_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 193 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.UNSIGNED_LONG, this.javaTypes.BIG_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 196 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.UNSIGNED_INT, this.javaTypes.LONG_JAVATYPE);
/*     */ 
/*     */     
/* 199 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.UNSIGNED_SHORT, this.javaTypes.INT_JAVATYPE);
/*     */ 
/*     */     
/* 202 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.UNSIGNED_BYTE, this.javaTypes.SHORT_JAVATYPE);
/*     */ 
/*     */     
/* 205 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.DURATION, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 208 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.DATE, this.javaTypes.CALENDAR_JAVATYPE);
/*     */ 
/*     */     
/* 211 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.TIME, this.javaTypes.CALENDAR_JAVATYPE);
/*     */ 
/*     */     
/* 214 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.G_YEAR_MONTH, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 217 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.G_YEAR, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 220 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.G_MONTH_DAY, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 223 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.G_DAY, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 226 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.G_MONTH, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     if (!VersionUtil.isJavaVersionGreaterThan1_3()) {
/* 232 */       this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.ANY_URI, this.javaTypes.STRING_JAVATYPE);
/*     */     }
/*     */     else {
/*     */       
/* 236 */       this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.ANY_URI, this.javaTypes.URI_JAVATYPE);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     if (this._resolveIDREF) {
/* 243 */       this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.IDREF, this.javaTypes.OBJECT_JAVATYPE);
/*     */     }
/*     */     else {
/*     */       
/* 247 */       this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.IDREF, this.javaTypes.STRING_JAVATYPE);
/*     */     } 
/*     */ 
/*     */     
/* 251 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.IDREFS, this.javaTypes.STRING_ARRAY_JAVATYPE);
/*     */ 
/*     */     
/* 254 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.NMTOKENS, this.javaTypes.STRING_ARRAY_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 258 */     this._builtinSchemaTypeToJavaTypeMap.put(SchemaConstants.QNAME_TYPE_URTYPE, this.javaTypes.OBJECT_JAVATYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.ANY_SIMPLE_URTYPE, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 267 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeString(), this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 270 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeInteger(), this.javaTypes.BIG_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 273 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeInt(), this.javaTypes.BOXED_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 276 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeLong(), this.javaTypes.BOXED_LONG_JAVATYPE);
/*     */ 
/*     */     
/* 279 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeShort(), this.javaTypes.BOXED_SHORT_JAVATYPE);
/*     */ 
/*     */     
/* 282 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeDecimal(), this.javaTypes.DECIMAL_JAVATYPE);
/*     */ 
/*     */     
/* 285 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeFloat(), this.javaTypes.BOXED_FLOAT_JAVATYPE);
/*     */ 
/*     */     
/* 288 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeDouble(), this.javaTypes.BOXED_DOUBLE_JAVATYPE);
/*     */ 
/*     */     
/* 291 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeBoolean(), this.javaTypes.BOXED_BOOLEAN_JAVATYPE);
/*     */ 
/*     */     
/* 294 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeByte(), this.javaTypes.BOXED_BYTE_JAVATYPE);
/*     */ 
/*     */     
/* 297 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeQName(), this.javaTypes.QNAME_JAVATYPE);
/*     */ 
/*     */     
/* 300 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeDateTime(), this.javaTypes.CALENDAR_JAVATYPE);
/*     */ 
/*     */     
/* 303 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeBase64Binary(), this.javaTypes.BYTE_ARRAY_JAVATYPE);
/*     */ 
/*     */     
/* 306 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeHexBinary(), this.javaTypes.BYTE_ARRAY_JAVATYPE);
/*     */ 
/*     */     
/* 309 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeBase64(), this.javaTypes.BYTE_ARRAY_JAVATYPE);
/*     */ 
/*     */     
/* 312 */     if (!this._strictCompliance) {
/* 313 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_COLLECTION, this.javaTypes.COLLECTION_JAVATYPE);
/*     */ 
/*     */       
/* 316 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_LIST, this.javaTypes.LIST_JAVATYPE);
/*     */ 
/*     */       
/* 319 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_SET, this.javaTypes.SET_JAVATYPE);
/*     */ 
/*     */       
/* 322 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_ARRAY_LIST, this.javaTypes.ARRAY_LIST_JAVATYPE);
/*     */ 
/*     */       
/* 325 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_VECTOR, this.javaTypes.VECTOR_JAVATYPE);
/*     */ 
/*     */       
/* 328 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_STACK, this.javaTypes.STACK_JAVATYPE);
/*     */ 
/*     */       
/* 331 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_LINKED_LIST, this.javaTypes.LINKED_LIST_JAVATYPE);
/*     */ 
/*     */       
/* 334 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_HASH_SET, this.javaTypes.HASH_SET_JAVATYPE);
/*     */ 
/*     */       
/* 337 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_TREE_SET, this.javaTypes.TREE_SET_JAVATYPE);
/*     */ 
/*     */ 
/*     */       
/* 341 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_MAP, this.javaTypes.MAP_JAVATYPE);
/*     */ 
/*     */       
/* 344 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_JAX_RPC_MAP_ENTRY, this.javaTypes.JAX_RPC_MAP_ENTRY_JAVATYPE);
/*     */ 
/*     */       
/* 347 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_HASH_MAP, this.javaTypes.HASH_MAP_JAVATYPE);
/*     */ 
/*     */       
/* 350 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_TREE_MAP, this.javaTypes.TREE_MAP_JAVATYPE);
/*     */ 
/*     */       
/* 353 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_HASHTABLE, this.javaTypes.HASHTABLE_JAVATYPE);
/*     */ 
/*     */       
/* 356 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_PROPERTIES, this.javaTypes.PROPERTIES_JAVATYPE);
/*     */     } 
/*     */ 
/*     */     
/* 360 */     this._builtinSchemaTypeToJavaWrapperTypeMap = new HashMap<Object, Object>();
/* 361 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.INT, this.javaTypes.BOXED_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 364 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.LONG, this.javaTypes.BOXED_LONG_JAVATYPE);
/*     */ 
/*     */     
/* 367 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.SHORT, this.javaTypes.BOXED_SHORT_JAVATYPE);
/*     */ 
/*     */     
/* 370 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.FLOAT, this.javaTypes.BOXED_FLOAT_JAVATYPE);
/*     */ 
/*     */     
/* 373 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.DOUBLE, this.javaTypes.BOXED_DOUBLE_JAVATYPE);
/*     */ 
/*     */     
/* 376 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.BOOLEAN, this.javaTypes.BOXED_BOOLEAN_JAVATYPE);
/*     */ 
/*     */     
/* 379 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.BYTE, this.javaTypes.BOXED_BYTE_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 383 */     this._simpleTypeEncoderMap = new HashMap<Object, Object>();
/* 384 */     this._simpleTypeEncoderMap.put(BuiltInTypes.STRING, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 387 */     this._simpleTypeEncoderMap.put(BuiltInTypes.INTEGER, XSDIntegerEncoder.getInstance());
/*     */ 
/*     */     
/* 390 */     this._simpleTypeEncoderMap.put(BuiltInTypes.INT, XSDIntEncoder.getInstance());
/*     */ 
/*     */     
/* 393 */     this._simpleTypeEncoderMap.put(BuiltInTypes.LONG, XSDLongEncoder.getInstance());
/*     */ 
/*     */     
/* 396 */     this._simpleTypeEncoderMap.put(BuiltInTypes.SHORT, XSDShortEncoder.getInstance());
/*     */ 
/*     */     
/* 399 */     this._simpleTypeEncoderMap.put(BuiltInTypes.DECIMAL, XSDDecimalEncoder.getInstance());
/*     */ 
/*     */     
/* 402 */     this._simpleTypeEncoderMap.put(BuiltInTypes.FLOAT, XSDFloatEncoder.getInstance());
/*     */ 
/*     */     
/* 405 */     this._simpleTypeEncoderMap.put(BuiltInTypes.DOUBLE, XSDDoubleEncoder.getInstance());
/*     */ 
/*     */     
/* 408 */     this._simpleTypeEncoderMap.put(BuiltInTypes.BYTE, XSDByteEncoder.getInstance());
/*     */ 
/*     */ 
/*     */     
/* 412 */     this._simpleTypeEncoderMap.put(BuiltInTypes.NORMALIZED_STRING, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 415 */     this._simpleTypeEncoderMap.put(BuiltInTypes.TOKEN, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 418 */     this._simpleTypeEncoderMap.put(BuiltInTypes.LANGUAGE, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 421 */     this._simpleTypeEncoderMap.put(BuiltInTypes.NAME, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 424 */     this._simpleTypeEncoderMap.put(BuiltInTypes.NMTOKEN, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 427 */     this._simpleTypeEncoderMap.put(BuiltInTypes.NCNAME, XSDStringEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 434 */     this._simpleTypeEncoderMap.put(BuiltInTypes.UNSIGNED_INT, XSDLongEncoder.getInstance());
/*     */ 
/*     */     
/* 437 */     this._simpleTypeEncoderMap.put(BuiltInTypes.UNSIGNED_SHORT, XSDIntEncoder.getInstance());
/*     */ 
/*     */     
/* 440 */     this._simpleTypeEncoderMap.put(BuiltInTypes.UNSIGNED_BYTE, XSDShortEncoder.getInstance());
/*     */ 
/*     */     
/* 443 */     if (!VersionUtil.isJavaVersionGreaterThan1_3()) {
/* 444 */       this._simpleTypeEncoderMap.put(BuiltInTypes.ANY_URI, XSDStringEncoder.getInstance());
/*     */     }
/*     */     else {
/*     */       
/* 448 */       this._simpleTypeEncoderMap.put(BuiltInTypes.ANY_URI, XSDAnyURIEncoder.getInstance());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 454 */     this._simpleTypeEncoderMap.put(BuiltInTypes.NMTOKENS, XSDListTypeEncoder.getInstance(XSDStringEncoder.getInstance(), String.class));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 459 */     this._simpleTypeEncoderMap.put(BuiltInTypes.DURATION, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 462 */     this._simpleTypeEncoderMap.put(BuiltInTypes.G_YEAR_MONTH, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 465 */     this._simpleTypeEncoderMap.put(BuiltInTypes.G_YEAR, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 468 */     this._simpleTypeEncoderMap.put(BuiltInTypes.G_MONTH_DAY, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 471 */     this._simpleTypeEncoderMap.put(BuiltInTypes.G_DAY, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 474 */     this._simpleTypeEncoderMap.put(BuiltInTypes.G_MONTH, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 477 */     this._simpleTypeEncoderMap.put(BuiltInTypes.ID, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 480 */     this._simpleTypeEncoderMap.put(BuiltInTypes.NON_POSITIVE_INTEGER, XSDIntegerEncoder.getInstance());
/*     */ 
/*     */     
/* 483 */     this._simpleTypeEncoderMap.put(BuiltInTypes.NEGATIVE_INTEGER, XSDIntegerEncoder.getInstance());
/*     */ 
/*     */     
/* 486 */     this._simpleTypeEncoderMap.put(BuiltInTypes.NON_NEGATIVE_INTEGER, XSDIntegerEncoder.getInstance());
/*     */ 
/*     */     
/* 489 */     this._simpleTypeEncoderMap.put(BuiltInTypes.UNSIGNED_LONG, XSDIntegerEncoder.getInstance());
/*     */ 
/*     */     
/* 492 */     this._simpleTypeEncoderMap.put(BuiltInTypes.POSITIVE_INTEGER, XSDIntegerEncoder.getInstance());
/*     */ 
/*     */     
/* 495 */     this._simpleTypeEncoderMap.put(BuiltInTypes.BASE64_BINARY, XSDBase64BinaryEncoder.getInstance());
/*     */ 
/*     */     
/* 498 */     this._simpleTypeEncoderMap.put(BuiltInTypes.HEX_BINARY, XSDHexBinaryEncoder.getInstance());
/*     */ 
/*     */     
/* 501 */     this._simpleTypeEncoderMap.put(BuiltInTypes.TIME, XSDTimeEncoder.getInstance());
/*     */ 
/*     */     
/* 504 */     this._simpleTypeEncoderMap.put(BuiltInTypes.DATE, XSDDateTimeCalendarEncoder.getInstance());
/*     */ 
/*     */     
/* 507 */     this._simpleTypeEncoderMap.put(BuiltInTypes.DATE_TIME, XSDDateTimeCalendarEncoder.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 513 */     this._simpleTypeEncoderMap.put(BuiltInTypes.BOOLEAN, XSDBooleanEncoder.getInstance());
/*     */ 
/*     */     
/* 516 */     this._simpleTypeEncoderMap.put(BuiltInTypes.QNAME, XSDQNameEncoder.getInstance());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\wsdl\SchemaAnalyzer11.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */