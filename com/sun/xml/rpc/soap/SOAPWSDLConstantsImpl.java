/*     */ package com.sun.xml.rpc.soap;class SOAPWSDLConstantsImpl implements SOAPWSDLConstants { private SOAPVersion ver; private String NS_WSDL_SOAP; private String NS_SOAP_ENCODING; private String URI_SOAP_TRANSPORT_HTTP; private QName QNAME_ADDRESS; private QName QNAME_BINDING; private QName QNAME_BODY; private QName QNAME_FAULT; private QName QNAME_HEADER; private QName QNAME_HEADERFAULT; private QName QNAME_OPERATION; private QName QNAME_TYPE_ARRAY; private QName QNAME_ATTR_GROUP_COMMON_ATTRIBUTES; private QName QNAME_ATTR_ARRAY_TYPE; private QName QNAME_ATTR_ITEM_TYPE; private QName QNAME_ATTR_ARRAY_SIZE; private QName QNAME_ATTR_OFFSET;
/*     */   private QName QNAME_ATTR_POSITION;
/*     */   private QName QNAME_TYPE_BASE64;
/*     */   private QName QNAME_ELEMENT_STRING;
/*     */   private QName QNAME_ELEMENT_NORMALIZED_STRING;
/*     */   private QName QNAME_ELEMENT_TOKEN;
/*     */   private QName QNAME_ELEMENT_BYTE;
/*     */   private QName QNAME_ELEMENT_UNSIGNED_BYTE;
/*     */   private QName QNAME_ELEMENT_BASE64_BINARY;
/*     */   private QName QNAME_ELEMENT_HEX_BINARY;
/*     */   private QName QNAME_ELEMENT_INTEGER;
/*     */   private QName QNAME_ELEMENT_POSITIVE_INTEGER;
/*     */   private QName QNAME_ELEMENT_NEGATIVE_INTEGER;
/*     */   private QName QNAME_ELEMENT_NON_NEGATIVE_INTEGER;
/*     */   private QName QNAME_ELEMENT_NON_POSITIVE_INTEGER;
/*     */   private QName QNAME_ELEMENT_INT;
/*     */   private QName QNAME_ELEMENT_UNSIGNED_INT;
/*     */   private QName QNAME_ELEMENT_LONG;
/*     */   private QName QNAME_ELEMENT_UNSIGNED_LONG;
/*     */   private QName QNAME_ELEMENT_SHORT;
/*     */   private QName QNAME_ELEMENT_UNSIGNED_SHORT;
/*     */   private QName QNAME_ELEMENT_DECIMAL;
/*     */   private QName QNAME_ELEMENT_FLOAT;
/*     */   private QName QNAME_ELEMENT_DOUBLE;
/*     */   private QName QNAME_ELEMENT_BOOLEAN;
/*     */   private QName QNAME_ELEMENT_TIME;
/*     */   private QName QNAME_ELEMENT_DATE_TIME;
/*     */   private QName QNAME_ELEMENT_DURATION;
/*     */   private QName QNAME_ELEMENT_DATE;
/*     */   private QName QNAME_ELEMENT_G_MONTH;
/*     */   private QName QNAME_ELEMENT_G_YEAR;
/*     */   private QName QNAME_ELEMENT_G_YEAR_MONTH;
/*     */   private QName QNAME_ELEMENT_G_DAY;
/*     */   private QName QNAME_ELEMENT_G_MONTH_DAY;
/*     */   private QName QNAME_ELEMENT_NAME;
/*     */   private QName QNAME_ELEMENT_QNAME;
/*     */   private QName QNAME_ELEMENT_NCNAME;
/*     */   private QName QNAME_ELEMENT_ANY_URI;
/*     */   
/*     */   SOAPWSDLConstantsImpl(SOAPVersion ver) {
/*  41 */     this.ver = ver;
/*  42 */     if (ver == SOAPVersion.SOAP_11) {
/*  43 */       initSOAP11();
/*  44 */     } else if (ver == SOAPVersion.SOAP_12) {
/*  45 */       initSOAP12();
/*     */     } 
/*     */   }
/*     */   private QName QNAME_ELEMENT_ID; private QName QNAME_ELEMENT_IDREF; private QName QNAME_ELEMENT_IDREFS; private QName QNAME_ELEMENT_ENTITY; private QName QNAME_ELEMENT_ENTITIES; private QName QNAME_ELEMENT_NOTATION; private QName QNAME_ELEMENT_NMTOKEN; private QName QNAME_ELEMENT_NMTOKENS; private QName QNAME_TYPE_STRING; private QName QNAME_TYPE_NORMALIZED_STRING; private QName QNAME_TYPE_TOKEN; private QName QNAME_TYPE_BYTE; private QName QNAME_TYPE_UNSIGNED_BYTE; private QName QNAME_TYPE_BASE64_BINARY; private QName QNAME_TYPE_HEX_BINARY; private QName QNAME_TYPE_INTEGER; private QName QNAME_TYPE_POSITIVE_INTEGER; private QName QNAME_TYPE_NEGATIVE_INTEGER; private QName QNAME_TYPE_NON_NEGATIVE_INTEGER; private QName QNAME_TYPE_NON_POSITIVE_INTEGER; private QName QNAME_TYPE_INT; private QName QNAME_TYPE_UNSIGNED_INT; private QName QNAME_TYPE_LONG; private QName QNAME_TYPE_UNSIGNED_LONG; private QName QNAME_TYPE_SHORT; private QName QNAME_TYPE_UNSIGNED_SHORT; private QName QNAME_TYPE_DECIMAL; private QName QNAME_TYPE_FLOAT; private QName QNAME_TYPE_DOUBLE; private QName QNAME_TYPE_BOOLEAN; private QName QNAME_TYPE_TIME; private QName QNAME_TYPE_DATE_TIME; private QName QNAME_TYPE_DURATION; private QName QNAME_TYPE_DATE; private QName QNAME_TYPE_G_MONTH; private QName QNAME_TYPE_G_YEAR; private QName QNAME_TYPE_G_YEAR_MONTH; private QName QNAME_TYPE_G_DAY; private QName QNAME_TYPE_G_MONTH_DAY; private QName QNAME_TYPE_NAME; private QName QNAME_TYPE_QNAME; private QName QNAME_TYPE_NCNAME; private QName QNAME_TYPE_ANY_URI; private QName QNAME_TYPE_ID; private QName QNAME_TYPE_IDREF; private QName QNAME_TYPE_IDREFS; private QName QNAME_TYPE_ENTITY; private QName QNAME_TYPE_ENTITIES; private QName QNAME_TYPE_NOTATION; private QName QNAME_TYPE_NMTOKEN; private QName QNAME_TYPE_NMTOKENS; private QName QNAME_TYPE_LANGUAGE; private QName QNAME_ATTR_ID; private QName QNAME_ATTR_HREF;
/*     */   public String getWSDLSOAPNamespace() {
/*  50 */     return this.NS_WSDL_SOAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSOAPEncodingNamespace() {
/*  55 */     return this.NS_SOAP_ENCODING;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSOAPTransportHttpURI() {
/*  60 */     return this.URI_SOAP_TRANSPORT_HTTP;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getQNameAddress() {
/*  65 */     return this.QNAME_ADDRESS;
/*     */   }
/*     */   
/*     */   public QName getQNameBinding() {
/*  69 */     return this.QNAME_BINDING;
/*     */   }
/*     */   
/*     */   public QName getQNameBody() {
/*  73 */     return this.QNAME_BODY;
/*     */   }
/*     */   
/*     */   public QName getQNameFault() {
/*  77 */     return this.QNAME_FAULT;
/*     */   }
/*     */   
/*     */   public QName getQNameHeader() {
/*  81 */     return this.QNAME_HEADER;
/*     */   }
/*     */   
/*     */   public QName getQNameHeaderFault() {
/*  85 */     return this.QNAME_HEADERFAULT;
/*     */   }
/*     */   
/*     */   public QName getQNameOperation() {
/*  89 */     return this.QNAME_OPERATION;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getQNameTypeArray() {
/*  94 */     return this.QNAME_TYPE_ARRAY;
/*     */   }
/*     */   
/*     */   public QName getQNameAttrGroupCommonAttributes() {
/*  98 */     return this.QNAME_ATTR_GROUP_COMMON_ATTRIBUTES;
/*     */   }
/*     */   
/*     */   public QName getQNameAttrArrayType() {
/* 102 */     return this.QNAME_ATTR_ARRAY_TYPE;
/*     */   }
/*     */   
/*     */   public QName getQNameAttrOffset() {
/* 106 */     return this.QNAME_ATTR_OFFSET;
/*     */   }
/*     */   
/*     */   public QName getQNameAttrPosition() {
/* 110 */     return this.QNAME_ATTR_POSITION;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeBase64() {
/* 114 */     return this.QNAME_TYPE_BASE64;
/*     */   }
/*     */   
/*     */   public QName getQNameElementString() {
/* 118 */     return this.QNAME_ELEMENT_STRING;
/*     */   }
/*     */   
/*     */   public QName getQNameElementNormalizedString() {
/* 122 */     return this.QNAME_ELEMENT_NORMALIZED_STRING;
/*     */   }
/*     */   
/*     */   public QName getQNameElementToken() {
/* 126 */     return this.QNAME_ELEMENT_TOKEN;
/*     */   }
/*     */   
/*     */   public QName getQNameElementByte() {
/* 130 */     return this.QNAME_ELEMENT_BYTE;
/*     */   }
/*     */   
/*     */   public QName getQNameElementUnsignedByte() {
/* 134 */     return this.QNAME_ELEMENT_UNSIGNED_BYTE;
/*     */   }
/*     */   
/*     */   public QName getQNameElementBase64Binary() {
/* 138 */     return this.QNAME_ELEMENT_BASE64_BINARY;
/*     */   }
/*     */   
/*     */   public QName getQNameElementHexBinary() {
/* 142 */     return this.QNAME_ELEMENT_HEX_BINARY;
/*     */   }
/*     */   
/*     */   public QName getQNameElementInteger() {
/* 146 */     return this.QNAME_ELEMENT_INTEGER;
/*     */   }
/*     */   
/*     */   public QName getQNameElementPositiveInteger() {
/* 150 */     return this.QNAME_ELEMENT_POSITIVE_INTEGER;
/*     */   }
/*     */   
/*     */   public QName getQNameElementNegativeInteger() {
/* 154 */     return this.QNAME_ELEMENT_NEGATIVE_INTEGER;
/*     */   }
/*     */   
/*     */   public QName getQNameElementNonNegativeInteger() {
/* 158 */     return this.QNAME_ELEMENT_NON_NEGATIVE_INTEGER;
/*     */   }
/*     */   
/*     */   public QName getQNameElementNonPositiveInteger() {
/* 162 */     return this.QNAME_ELEMENT_NON_POSITIVE_INTEGER;
/*     */   }
/*     */   
/*     */   public QName getQNameElementInt() {
/* 166 */     return this.QNAME_ELEMENT_INT;
/*     */   }
/*     */   
/*     */   public QName getQNameElementUnsignedInt() {
/* 170 */     return this.QNAME_ELEMENT_UNSIGNED_INT;
/*     */   }
/*     */   
/*     */   public QName getQNameElementLong() {
/* 174 */     return this.QNAME_ELEMENT_LONG;
/*     */   }
/*     */   
/*     */   public QName getQNameElementUnsignedLong() {
/* 178 */     return this.QNAME_ELEMENT_UNSIGNED_LONG;
/*     */   }
/*     */   
/*     */   public QName getQNameElementShort() {
/* 182 */     return this.QNAME_ELEMENT_SHORT;
/*     */   }
/*     */   
/*     */   public QName getQNameElementUnsignedShort() {
/* 186 */     return this.QNAME_ELEMENT_UNSIGNED_SHORT;
/*     */   }
/*     */   
/*     */   public QName getQNameElementDecimal() {
/* 190 */     return this.QNAME_ELEMENT_DECIMAL;
/*     */   }
/*     */   
/*     */   public QName getQNameElementFloat() {
/* 194 */     return this.QNAME_ELEMENT_FLOAT;
/*     */   }
/*     */   
/*     */   public QName getQNameElementDouble() {
/* 198 */     return this.QNAME_ELEMENT_DOUBLE;
/*     */   }
/*     */   
/*     */   public QName getQNameElementBoolean() {
/* 202 */     return this.QNAME_ELEMENT_BOOLEAN;
/*     */   }
/*     */   
/*     */   public QName getQNameElementTime() {
/* 206 */     return this.QNAME_ELEMENT_TIME;
/*     */   }
/*     */   
/*     */   public QName getQNameElementDateTime() {
/* 210 */     return this.QNAME_ELEMENT_DATE_TIME;
/*     */   }
/*     */   
/*     */   public QName getQNameElementDuration() {
/* 214 */     return this.QNAME_ELEMENT_DURATION;
/*     */   }
/*     */   
/*     */   public QName getQNameElementDate() {
/* 218 */     return this.QNAME_ELEMENT_DATE;
/*     */   }
/*     */   
/*     */   public QName getQNameElementGMonth() {
/* 222 */     return this.QNAME_ELEMENT_G_MONTH;
/*     */   }
/*     */   
/*     */   public QName getQNameElementGYear() {
/* 226 */     return this.QNAME_ELEMENT_G_YEAR;
/*     */   }
/*     */   
/*     */   public QName getQNameElementGYearMonth() {
/* 230 */     return this.QNAME_ELEMENT_G_YEAR_MONTH;
/*     */   }
/*     */   
/*     */   public QName getQNameElementGDay() {
/* 234 */     return this.QNAME_ELEMENT_G_DAY;
/*     */   }
/*     */   
/*     */   public QName getQNameElementGMonthDay() {
/* 238 */     return this.QNAME_ELEMENT_G_MONTH_DAY;
/*     */   }
/*     */   
/*     */   public QName getQNameElementName() {
/* 242 */     return this.QNAME_ELEMENT_NAME;
/*     */   }
/*     */   
/*     */   public QName getQNameElementQName() {
/* 246 */     return this.QNAME_ELEMENT_QNAME;
/*     */   }
/*     */   
/*     */   public QName getQNameElementNCNAME() {
/* 250 */     return this.QNAME_ELEMENT_NCNAME;
/*     */   }
/*     */   
/*     */   public QName getQNameElementAnyURI() {
/* 254 */     return this.QNAME_ELEMENT_ANY_URI;
/*     */   }
/*     */   
/*     */   public QName getQNameElementID() {
/* 258 */     return this.QNAME_ELEMENT_ID;
/*     */   }
/*     */   
/*     */   public QName getQNameElementIDREF() {
/* 262 */     return this.QNAME_ELEMENT_IDREF;
/*     */   }
/*     */   
/*     */   public QName getQNameElementIDREFS() {
/* 266 */     return this.QNAME_ELEMENT_IDREFS;
/*     */   }
/*     */   
/*     */   public QName getQNameElementEntity() {
/* 270 */     return this.QNAME_ELEMENT_ENTITY;
/*     */   }
/*     */   
/*     */   public QName getQNameElementEntities() {
/* 274 */     return this.QNAME_ELEMENT_ENTITIES;
/*     */   }
/*     */   
/*     */   public QName getQNameElementNotation() {
/* 278 */     return this.QNAME_ELEMENT_ENTITIES;
/*     */   }
/*     */   
/*     */   public QName getQNameElementNMTOKEN() {
/* 282 */     return this.QNAME_ELEMENT_NMTOKEN;
/*     */   }
/*     */   
/*     */   public QName getQNameElementNMTOKENS() {
/* 286 */     return this.QNAME_ELEMENT_NMTOKENS;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeString() {
/* 290 */     return this.QNAME_TYPE_STRING;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeNormalizedString() {
/* 294 */     return this.QNAME_TYPE_NORMALIZED_STRING;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeToken() {
/* 298 */     return this.QNAME_TYPE_TOKEN;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeByte() {
/* 302 */     return this.QNAME_TYPE_BYTE;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeUnsignedByte() {
/* 306 */     return this.QNAME_TYPE_UNSIGNED_BYTE;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeBase64Binary() {
/* 310 */     return this.QNAME_TYPE_BASE64_BINARY;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeHexBinary() {
/* 314 */     return this.QNAME_TYPE_HEX_BINARY;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeInteger() {
/* 318 */     return this.QNAME_TYPE_INTEGER;
/*     */   }
/*     */   
/*     */   public QName getQNameTypePositiveInteger() {
/* 322 */     return this.QNAME_TYPE_POSITIVE_INTEGER;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeNegativeInteger() {
/* 326 */     return this.QNAME_TYPE_NEGATIVE_INTEGER;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeNonNegativeInteger() {
/* 330 */     return this.QNAME_TYPE_NON_NEGATIVE_INTEGER;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeNonPositiveInteger() {
/* 334 */     return this.QNAME_TYPE_NON_POSITIVE_INTEGER;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeInt() {
/* 338 */     return this.QNAME_TYPE_INT;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeUnsignedInt() {
/* 342 */     return this.QNAME_TYPE_UNSIGNED_INT;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeLong() {
/* 346 */     return this.QNAME_TYPE_LONG;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeUnsignedLong() {
/* 350 */     return this.QNAME_TYPE_UNSIGNED_LONG;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeShort() {
/* 354 */     return this.QNAME_TYPE_SHORT;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeUnsignedShort() {
/* 358 */     return this.QNAME_TYPE_UNSIGNED_SHORT;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeDecimal() {
/* 362 */     return this.QNAME_TYPE_DECIMAL;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeFloat() {
/* 366 */     return this.QNAME_TYPE_FLOAT;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeDouble() {
/* 370 */     return this.QNAME_TYPE_DOUBLE;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeBoolean() {
/* 374 */     return this.QNAME_TYPE_BOOLEAN;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeTime() {
/* 378 */     return this.QNAME_TYPE_TIME;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeDateTime() {
/* 382 */     return this.QNAME_TYPE_DATE_TIME;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeDuration() {
/* 386 */     return this.QNAME_TYPE_DURATION;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeDate() {
/* 390 */     return this.QNAME_TYPE_DATE;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeGMonth() {
/* 394 */     return this.QNAME_TYPE_G_MONTH;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeGYear() {
/* 398 */     return this.QNAME_TYPE_G_YEAR;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeGYearMonth() {
/* 402 */     return this.QNAME_TYPE_G_YEAR_MONTH;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeGDay() {
/* 406 */     return this.QNAME_TYPE_G_DAY;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeGMonthDay() {
/* 410 */     return this.QNAME_TYPE_G_MONTH_DAY;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeName() {
/* 414 */     return this.QNAME_TYPE_NAME;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeQName() {
/* 418 */     return this.QNAME_TYPE_QNAME;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeNCNAME() {
/* 422 */     return this.QNAME_TYPE_NCNAME;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeAnyURI() {
/* 426 */     return this.QNAME_TYPE_ANY_URI;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeID() {
/* 430 */     return this.QNAME_TYPE_ID;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeIDREF() {
/* 434 */     return this.QNAME_TYPE_IDREF;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeIDREFS() {
/* 438 */     return this.QNAME_TYPE_IDREFS;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeENTITY() {
/* 442 */     return this.QNAME_TYPE_ENTITY;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeENTITIES() {
/* 446 */     return this.QNAME_TYPE_ENTITIES;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeNotation() {
/* 450 */     return this.QNAME_TYPE_NOTATION;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeNMTOKEN() {
/* 454 */     return this.QNAME_TYPE_NMTOKEN;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeNMTOKENS() {
/* 458 */     return this.QNAME_TYPE_NMTOKENS;
/*     */   }
/*     */   
/*     */   public QName getQNameTypeLanguage() {
/* 462 */     return this.QNAME_TYPE_LANGUAGE;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getQNameAttrID() {
/* 467 */     return this.QNAME_ATTR_ID;
/*     */   }
/*     */   
/*     */   public QName getQNameAttrHREF() {
/* 471 */     return this.QNAME_ATTR_HREF;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 476 */     return this.ver;
/*     */   }
/*     */   
/*     */   private void initSOAP11() {
/* 480 */     this.NS_WSDL_SOAP = "http://schemas.xmlsoap.org/wsdl/soap/";
/* 481 */     this.NS_SOAP_ENCODING = "http://schemas.xmlsoap.org/soap/encoding/";
/*     */ 
/*     */     
/* 484 */     this.URI_SOAP_TRANSPORT_HTTP = "http://schemas.xmlsoap.org/soap/http";
/*     */ 
/*     */     
/* 487 */     this.QNAME_ADDRESS = SOAPConstants.QNAME_ADDRESS;
/* 488 */     this.QNAME_BINDING = SOAPConstants.QNAME_BINDING;
/* 489 */     this.QNAME_BODY = SOAPConstants.QNAME_BODY;
/* 490 */     this.QNAME_FAULT = SOAPConstants.QNAME_FAULT;
/* 491 */     this.QNAME_HEADER = SOAPConstants.QNAME_HEADER;
/* 492 */     this.QNAME_HEADERFAULT = SOAPConstants.QNAME_HEADERFAULT;
/* 493 */     this.QNAME_OPERATION = SOAPConstants.QNAME_OPERATION;
/*     */ 
/*     */     
/* 496 */     this.QNAME_TYPE_ARRAY = SOAPConstants.QNAME_TYPE_ARRAY;
/* 497 */     this.QNAME_ATTR_GROUP_COMMON_ATTRIBUTES = SOAPConstants.QNAME_ATTR_GROUP_COMMON_ATTRIBUTES;
/*     */     
/* 499 */     this.QNAME_ATTR_ARRAY_TYPE = SOAPConstants.QNAME_ATTR_ARRAY_TYPE;
/*     */ 
/*     */     
/* 502 */     this.QNAME_ATTR_OFFSET = SOAPConstants.QNAME_ATTR_OFFSET;
/* 503 */     this.QNAME_ATTR_POSITION = SOAPConstants.QNAME_ATTR_POSITION;
/*     */     
/* 505 */     this.QNAME_TYPE_BASE64 = SOAPConstants.QNAME_TYPE_BASE64;
/*     */     
/* 507 */     this.QNAME_ELEMENT_STRING = SOAPConstants.QNAME_ELEMENT_STRING;
/* 508 */     this.QNAME_ELEMENT_NORMALIZED_STRING = SOAPConstants.QNAME_ELEMENT_NORMALIZED_STRING;
/*     */     
/* 510 */     this.QNAME_ELEMENT_TOKEN = SOAPConstants.QNAME_ELEMENT_TOKEN;
/* 511 */     this.QNAME_ELEMENT_BYTE = SOAPConstants.QNAME_ELEMENT_TOKEN;
/* 512 */     this.QNAME_ELEMENT_UNSIGNED_BYTE = SOAPConstants.QNAME_ELEMENT_UNSIGNED_BYTE;
/* 513 */     this.QNAME_ELEMENT_BASE64_BINARY = SOAPConstants.QNAME_ELEMENT_BASE64_BINARY;
/* 514 */     this.QNAME_ELEMENT_HEX_BINARY = SOAPConstants.QNAME_ELEMENT_HEX_BINARY;
/* 515 */     this.QNAME_ELEMENT_INTEGER = SOAPConstants.QNAME_ELEMENT_INTEGER;
/* 516 */     this.QNAME_ELEMENT_POSITIVE_INTEGER = SOAPConstants.QNAME_ELEMENT_POSITIVE_INTEGER;
/*     */     
/* 518 */     this.QNAME_ELEMENT_NEGATIVE_INTEGER = SOAPConstants.QNAME_ELEMENT_NEGATIVE_INTEGER;
/*     */     
/* 520 */     this.QNAME_ELEMENT_NON_NEGATIVE_INTEGER = SOAPConstants.QNAME_ELEMENT_NON_NEGATIVE_INTEGER;
/*     */     
/* 522 */     this.QNAME_ELEMENT_NON_POSITIVE_INTEGER = SOAPConstants.QNAME_ELEMENT_NON_POSITIVE_INTEGER;
/*     */     
/* 524 */     this.QNAME_ELEMENT_INT = SOAPConstants.QNAME_ELEMENT_INT;
/* 525 */     this.QNAME_ELEMENT_UNSIGNED_INT = SOAPConstants.QNAME_ELEMENT_UNSIGNED_INT;
/* 526 */     this.QNAME_ELEMENT_LONG = SOAPConstants.QNAME_ELEMENT_LONG;
/* 527 */     this.QNAME_ELEMENT_UNSIGNED_LONG = SOAPConstants.QNAME_ELEMENT_UNSIGNED_LONG;
/* 528 */     this.QNAME_ELEMENT_SHORT = SOAPConstants.QNAME_ELEMENT_SHORT;
/* 529 */     this.QNAME_ELEMENT_UNSIGNED_SHORT = SOAPConstants.QNAME_ELEMENT_UNSIGNED_SHORT;
/*     */     
/* 531 */     this.QNAME_ELEMENT_DECIMAL = SOAPConstants.QNAME_ELEMENT_DECIMAL;
/* 532 */     this.QNAME_ELEMENT_FLOAT = SOAPConstants.QNAME_ELEMENT_FLOAT;
/* 533 */     this.QNAME_ELEMENT_DOUBLE = SOAPConstants.QNAME_ELEMENT_DOUBLE;
/* 534 */     this.QNAME_ELEMENT_BOOLEAN = SOAPConstants.QNAME_ELEMENT_BOOLEAN;
/* 535 */     this.QNAME_ELEMENT_TIME = SOAPConstants.QNAME_ELEMENT_TIME;
/* 536 */     this.QNAME_ELEMENT_DATE_TIME = SOAPConstants.QNAME_ELEMENT_DATE_TIME;
/* 537 */     this.QNAME_ELEMENT_DURATION = SOAPConstants.QNAME_ELEMENT_DURATION;
/* 538 */     this.QNAME_ELEMENT_DATE = SOAPConstants.QNAME_ELEMENT_DATE;
/* 539 */     this.QNAME_ELEMENT_G_MONTH = SOAPConstants.QNAME_ELEMENT_G_MONTH;
/* 540 */     this.QNAME_ELEMENT_G_YEAR = SOAPConstants.QNAME_ELEMENT_G_YEAR;
/* 541 */     this.QNAME_ELEMENT_G_YEAR_MONTH = SOAPConstants.QNAME_ELEMENT_G_YEAR_MONTH;
/* 542 */     this.QNAME_ELEMENT_G_DAY = SOAPConstants.QNAME_ELEMENT_G_DAY;
/* 543 */     this.QNAME_ELEMENT_G_MONTH_DAY = SOAPConstants.QNAME_ELEMENT_G_MONTH_DAY;
/* 544 */     this.QNAME_ELEMENT_NAME = SOAPConstants.QNAME_ELEMENT_NAME;
/* 545 */     this.QNAME_ELEMENT_QNAME = SOAPConstants.QNAME_ELEMENT_QNAME;
/* 546 */     this.QNAME_ELEMENT_NCNAME = SOAPConstants.QNAME_ELEMENT_NCNAME;
/* 547 */     this.QNAME_ELEMENT_ANY_URI = SOAPConstants.QNAME_ELEMENT_ANY_URI;
/* 548 */     this.QNAME_ELEMENT_ID = SOAPConstants.QNAME_ELEMENT_ID;
/* 549 */     this.QNAME_ELEMENT_IDREF = SOAPConstants.QNAME_ELEMENT_IDREF;
/* 550 */     this.QNAME_ELEMENT_IDREFS = SOAPConstants.QNAME_ELEMENT_IDREFS;
/* 551 */     this.QNAME_ELEMENT_ENTITY = SOAPConstants.QNAME_ELEMENT_ENTITY;
/* 552 */     this.QNAME_ELEMENT_ENTITIES = SOAPConstants.QNAME_ELEMENT_ENTITIES;
/* 553 */     this.QNAME_ELEMENT_NOTATION = SOAPConstants.QNAME_ELEMENT_NOTATION;
/* 554 */     this.QNAME_ELEMENT_NMTOKEN = SOAPConstants.QNAME_ELEMENT_NMTOKEN;
/* 555 */     this.QNAME_ELEMENT_NMTOKENS = SOAPConstants.QNAME_ELEMENT_NMTOKENS;
/*     */     
/* 557 */     this.QNAME_TYPE_STRING = SOAPConstants.QNAME_TYPE_STRING;
/* 558 */     this.QNAME_TYPE_NORMALIZED_STRING = SOAPConstants.QNAME_TYPE_NORMALIZED_STRING;
/*     */     
/* 560 */     this.QNAME_TYPE_TOKEN = SOAPConstants.QNAME_TYPE_TOKEN;
/* 561 */     this.QNAME_TYPE_BYTE = SOAPConstants.QNAME_TYPE_BYTE;
/* 562 */     this.QNAME_TYPE_UNSIGNED_BYTE = SOAPConstants.QNAME_TYPE_UNSIGNED_BYTE;
/* 563 */     this.QNAME_TYPE_BASE64_BINARY = SOAPConstants.QNAME_TYPE_BASE64_BINARY;
/* 564 */     this.QNAME_TYPE_HEX_BINARY = SOAPConstants.QNAME_TYPE_HEX_BINARY;
/* 565 */     this.QNAME_TYPE_INTEGER = SOAPConstants.QNAME_TYPE_INTEGER;
/* 566 */     this.QNAME_TYPE_POSITIVE_INTEGER = SOAPConstants.QNAME_TYPE_POSITIVE_INTEGER;
/* 567 */     this.QNAME_TYPE_NEGATIVE_INTEGER = SOAPConstants.QNAME_TYPE_NEGATIVE_INTEGER;
/* 568 */     this.QNAME_TYPE_NON_NEGATIVE_INTEGER = SOAPConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER;
/*     */     
/* 570 */     this.QNAME_TYPE_NON_POSITIVE_INTEGER = SOAPConstants.QNAME_TYPE_NON_POSITIVE_INTEGER;
/*     */     
/* 572 */     this.QNAME_TYPE_INT = SOAPConstants.QNAME_TYPE_INT;
/* 573 */     this.QNAME_TYPE_UNSIGNED_INT = SOAPConstants.QNAME_TYPE_UNSIGNED_INT;
/* 574 */     this.QNAME_TYPE_LONG = SOAPConstants.QNAME_TYPE_LONG;
/* 575 */     this.QNAME_TYPE_UNSIGNED_LONG = SOAPConstants.QNAME_TYPE_UNSIGNED_LONG;
/* 576 */     this.QNAME_TYPE_SHORT = SOAPConstants.QNAME_TYPE_SHORT;
/* 577 */     this.QNAME_TYPE_UNSIGNED_SHORT = SOAPConstants.QNAME_TYPE_UNSIGNED_SHORT;
/* 578 */     this.QNAME_TYPE_DECIMAL = SOAPConstants.QNAME_TYPE_DECIMAL;
/* 579 */     this.QNAME_TYPE_FLOAT = SOAPConstants.QNAME_TYPE_FLOAT;
/* 580 */     this.QNAME_TYPE_DOUBLE = SOAPConstants.QNAME_TYPE_DOUBLE;
/* 581 */     this.QNAME_TYPE_BOOLEAN = SOAPConstants.QNAME_TYPE_BOOLEAN;
/* 582 */     this.QNAME_TYPE_TIME = SOAPConstants.QNAME_TYPE_TIME;
/* 583 */     this.QNAME_TYPE_DATE_TIME = SOAPConstants.QNAME_TYPE_DATE_TIME;
/* 584 */     this.QNAME_TYPE_DURATION = SOAPConstants.QNAME_TYPE_DURATION;
/* 585 */     this.QNAME_TYPE_DATE = SOAPConstants.QNAME_TYPE_DATE;
/* 586 */     this.QNAME_TYPE_G_MONTH = SOAPConstants.QNAME_TYPE_G_MONTH;
/* 587 */     this.QNAME_TYPE_G_YEAR = SOAPConstants.QNAME_TYPE_G_YEAR;
/* 588 */     this.QNAME_TYPE_G_YEAR_MONTH = SOAPConstants.QNAME_TYPE_G_YEAR_MONTH;
/* 589 */     this.QNAME_TYPE_G_DAY = SOAPConstants.QNAME_TYPE_G_DAY;
/* 590 */     this.QNAME_TYPE_G_MONTH_DAY = SOAPConstants.QNAME_TYPE_G_MONTH_DAY;
/* 591 */     this.QNAME_TYPE_NAME = SOAPConstants.QNAME_TYPE_NAME;
/* 592 */     this.QNAME_TYPE_QNAME = SOAPConstants.QNAME_TYPE_QNAME;
/* 593 */     this.QNAME_TYPE_NCNAME = SOAPConstants.QNAME_TYPE_NCNAME;
/* 594 */     this.QNAME_TYPE_ANY_URI = SOAPConstants.QNAME_TYPE_ANY_URI;
/* 595 */     this.QNAME_TYPE_ID = SOAPConstants.QNAME_TYPE_ID;
/* 596 */     this.QNAME_TYPE_IDREF = SOAPConstants.QNAME_TYPE_IDREF;
/* 597 */     this.QNAME_TYPE_IDREFS = SOAPConstants.QNAME_TYPE_IDREFS;
/* 598 */     this.QNAME_TYPE_ENTITY = SOAPConstants.QNAME_TYPE_ENTITY;
/* 599 */     this.QNAME_TYPE_ENTITIES = SOAPConstants.QNAME_TYPE_ENTITIES;
/* 600 */     this.QNAME_TYPE_NOTATION = SOAPConstants.QNAME_TYPE_NOTATION;
/* 601 */     this.QNAME_TYPE_NMTOKEN = SOAPConstants.QNAME_TYPE_NMTOKEN;
/* 602 */     this.QNAME_TYPE_NMTOKENS = SOAPConstants.QNAME_TYPE_NMTOKENS;
/* 603 */     this.QNAME_TYPE_LANGUAGE = SOAPConstants.QNAME_TYPE_LANGUAGE;
/*     */ 
/*     */     
/* 606 */     this.QNAME_ATTR_ID = SOAPConstants.QNAME_ATTR_ID;
/* 607 */     this.QNAME_ATTR_HREF = SOAPConstants.QNAME_ATTR_HREF;
/*     */   }
/*     */   
/*     */   private void initSOAP12() {
/* 611 */     this.NS_WSDL_SOAP = "http://schemas.xmlsoap.org/wsdl/soap/";
/* 612 */     this.NS_SOAP_ENCODING = "http://www.w3.org/2002/06/soap-encoding";
/*     */ 
/*     */     
/* 615 */     this.URI_SOAP_TRANSPORT_HTTP = "http://www.w3.org/2002/06/soap/bindings/HTTP/";
/*     */ 
/*     */     
/* 618 */     this.QNAME_ADDRESS = SOAP12Constants.QNAME_ADDRESS;
/* 619 */     this.QNAME_BINDING = SOAP12Constants.QNAME_BINDING;
/* 620 */     this.QNAME_BODY = SOAP12Constants.QNAME_BODY;
/* 621 */     this.QNAME_FAULT = SOAP12Constants.QNAME_FAULT;
/* 622 */     this.QNAME_HEADER = SOAP12Constants.QNAME_HEADER;
/* 623 */     this.QNAME_HEADERFAULT = SOAP12Constants.QNAME_HEADERFAULT;
/* 624 */     this.QNAME_OPERATION = SOAP12Constants.QNAME_OPERATION;
/*     */ 
/*     */     
/* 627 */     this.QNAME_TYPE_ARRAY = SOAP12Constants.QNAME_TYPE_ARRAY;
/* 628 */     this.QNAME_ATTR_GROUP_COMMON_ATTRIBUTES = SOAP12Constants.QNAME_ATTR_GROUP_COMMON_ATTRIBUTES;
/*     */     
/* 630 */     this.QNAME_ATTR_ARRAY_TYPE = SOAP12Constants.QNAME_ATTR_ARRAY_TYPE;
/* 631 */     this.QNAME_ATTR_ITEM_TYPE = SOAP12Constants.QNAME_ATTR_ITEM_TYPE;
/* 632 */     this.QNAME_ATTR_ARRAY_SIZE = SOAP12Constants.QNAME_ATTR_ARRAY_SIZE;
/* 633 */     this.QNAME_ATTR_OFFSET = SOAP12Constants.QNAME_ATTR_OFFSET;
/* 634 */     this.QNAME_ATTR_POSITION = SOAP12Constants.QNAME_ATTR_POSITION;
/*     */     
/* 636 */     this.QNAME_TYPE_BASE64 = SOAP12Constants.QNAME_TYPE_BASE64;
/*     */     
/* 638 */     this.QNAME_ELEMENT_STRING = SOAP12Constants.QNAME_ELEMENT_STRING;
/* 639 */     this.QNAME_ELEMENT_NORMALIZED_STRING = SOAP12Constants.QNAME_ELEMENT_NORMALIZED_STRING;
/*     */     
/* 641 */     this.QNAME_ELEMENT_TOKEN = SOAP12Constants.QNAME_ELEMENT_TOKEN;
/* 642 */     this.QNAME_ELEMENT_BYTE = SOAP12Constants.QNAME_ELEMENT_TOKEN;
/* 643 */     this.QNAME_ELEMENT_UNSIGNED_BYTE = SOAP12Constants.QNAME_ELEMENT_UNSIGNED_BYTE;
/*     */     
/* 645 */     this.QNAME_ELEMENT_BASE64_BINARY = SOAP12Constants.QNAME_ELEMENT_BASE64_BINARY;
/*     */     
/* 647 */     this.QNAME_ELEMENT_HEX_BINARY = SOAP12Constants.QNAME_ELEMENT_HEX_BINARY;
/* 648 */     this.QNAME_ELEMENT_INTEGER = SOAP12Constants.QNAME_ELEMENT_INTEGER;
/* 649 */     this.QNAME_ELEMENT_POSITIVE_INTEGER = SOAP12Constants.QNAME_ELEMENT_POSITIVE_INTEGER;
/*     */     
/* 651 */     this.QNAME_ELEMENT_NEGATIVE_INTEGER = SOAP12Constants.QNAME_ELEMENT_NEGATIVE_INTEGER;
/*     */     
/* 653 */     this.QNAME_ELEMENT_NON_NEGATIVE_INTEGER = SOAP12Constants.QNAME_ELEMENT_NON_NEGATIVE_INTEGER;
/*     */     
/* 655 */     this.QNAME_ELEMENT_NON_POSITIVE_INTEGER = SOAP12Constants.QNAME_ELEMENT_NON_POSITIVE_INTEGER;
/*     */     
/* 657 */     this.QNAME_ELEMENT_INT = SOAP12Constants.QNAME_ELEMENT_INT;
/* 658 */     this.QNAME_ELEMENT_UNSIGNED_INT = SOAP12Constants.QNAME_ELEMENT_UNSIGNED_INT;
/* 659 */     this.QNAME_ELEMENT_LONG = SOAP12Constants.QNAME_ELEMENT_LONG;
/* 660 */     this.QNAME_ELEMENT_UNSIGNED_LONG = SOAP12Constants.QNAME_ELEMENT_UNSIGNED_LONG;
/*     */     
/* 662 */     this.QNAME_ELEMENT_SHORT = SOAP12Constants.QNAME_ELEMENT_SHORT;
/* 663 */     this.QNAME_ELEMENT_UNSIGNED_SHORT = SOAP12Constants.QNAME_ELEMENT_UNSIGNED_SHORT;
/*     */     
/* 665 */     this.QNAME_ELEMENT_DECIMAL = SOAP12Constants.QNAME_ELEMENT_DECIMAL;
/* 666 */     this.QNAME_ELEMENT_FLOAT = SOAP12Constants.QNAME_ELEMENT_FLOAT;
/* 667 */     this.QNAME_ELEMENT_DOUBLE = SOAP12Constants.QNAME_ELEMENT_DOUBLE;
/* 668 */     this.QNAME_ELEMENT_BOOLEAN = SOAP12Constants.QNAME_ELEMENT_BOOLEAN;
/* 669 */     this.QNAME_ELEMENT_TIME = SOAP12Constants.QNAME_ELEMENT_TIME;
/* 670 */     this.QNAME_ELEMENT_DATE_TIME = SOAP12Constants.QNAME_ELEMENT_DATE_TIME;
/* 671 */     this.QNAME_ELEMENT_DURATION = SOAP12Constants.QNAME_ELEMENT_DURATION;
/* 672 */     this.QNAME_ELEMENT_DATE = SOAP12Constants.QNAME_ELEMENT_DATE;
/* 673 */     this.QNAME_ELEMENT_G_MONTH = SOAP12Constants.QNAME_ELEMENT_G_MONTH;
/* 674 */     this.QNAME_ELEMENT_G_YEAR = SOAP12Constants.QNAME_ELEMENT_G_YEAR;
/* 675 */     this.QNAME_ELEMENT_G_YEAR_MONTH = SOAP12Constants.QNAME_ELEMENT_G_YEAR_MONTH;
/* 676 */     this.QNAME_ELEMENT_G_DAY = SOAP12Constants.QNAME_ELEMENT_G_DAY;
/* 677 */     this.QNAME_ELEMENT_G_MONTH_DAY = SOAP12Constants.QNAME_ELEMENT_G_MONTH_DAY;
/* 678 */     this.QNAME_ELEMENT_NAME = SOAP12Constants.QNAME_ELEMENT_NAME;
/* 679 */     this.QNAME_ELEMENT_QNAME = SOAP12Constants.QNAME_ELEMENT_QNAME;
/* 680 */     this.QNAME_ELEMENT_NCNAME = SOAP12Constants.QNAME_ELEMENT_NCNAME;
/* 681 */     this.QNAME_ELEMENT_ANY_URI = SOAP12Constants.QNAME_ELEMENT_ANY_URI;
/* 682 */     this.QNAME_ELEMENT_ID = SOAP12Constants.QNAME_ELEMENT_ID;
/* 683 */     this.QNAME_ELEMENT_IDREF = SOAP12Constants.QNAME_ELEMENT_IDREF;
/* 684 */     this.QNAME_ELEMENT_IDREFS = SOAP12Constants.QNAME_ELEMENT_IDREFS;
/* 685 */     this.QNAME_ELEMENT_ENTITY = SOAP12Constants.QNAME_ELEMENT_ENTITY;
/* 686 */     this.QNAME_ELEMENT_ENTITIES = SOAP12Constants.QNAME_ELEMENT_ENTITIES;
/* 687 */     this.QNAME_ELEMENT_NOTATION = SOAP12Constants.QNAME_ELEMENT_NOTATION;
/* 688 */     this.QNAME_ELEMENT_NMTOKEN = SOAP12Constants.QNAME_ELEMENT_NMTOKEN;
/* 689 */     this.QNAME_ELEMENT_NMTOKENS = SOAP12Constants.QNAME_ELEMENT_NMTOKENS;
/*     */     
/* 691 */     this.QNAME_TYPE_STRING = SOAP12Constants.QNAME_TYPE_STRING;
/* 692 */     this.QNAME_TYPE_NORMALIZED_STRING = SOAP12Constants.QNAME_TYPE_NORMALIZED_STRING;
/*     */     
/* 694 */     this.QNAME_TYPE_TOKEN = SOAP12Constants.QNAME_TYPE_TOKEN;
/* 695 */     this.QNAME_TYPE_BYTE = SOAP12Constants.QNAME_TYPE_BYTE;
/* 696 */     this.QNAME_TYPE_UNSIGNED_BYTE = SOAP12Constants.QNAME_TYPE_UNSIGNED_BYTE;
/* 697 */     this.QNAME_TYPE_BASE64_BINARY = SOAP12Constants.QNAME_TYPE_BASE64_BINARY;
/* 698 */     this.QNAME_TYPE_HEX_BINARY = SOAP12Constants.QNAME_TYPE_HEX_BINARY;
/* 699 */     this.QNAME_TYPE_INTEGER = SOAP12Constants.QNAME_TYPE_INTEGER;
/* 700 */     this.QNAME_TYPE_POSITIVE_INTEGER = SOAP12Constants.QNAME_TYPE_POSITIVE_INTEGER;
/*     */     
/* 702 */     this.QNAME_TYPE_NEGATIVE_INTEGER = SOAP12Constants.QNAME_TYPE_NEGATIVE_INTEGER;
/*     */     
/* 704 */     this.QNAME_TYPE_NON_NEGATIVE_INTEGER = SOAP12Constants.QNAME_TYPE_NON_NEGATIVE_INTEGER;
/*     */     
/* 706 */     this.QNAME_TYPE_NON_POSITIVE_INTEGER = SOAP12Constants.QNAME_TYPE_NON_POSITIVE_INTEGER;
/*     */     
/* 708 */     this.QNAME_TYPE_INT = SOAP12Constants.QNAME_TYPE_INT;
/* 709 */     this.QNAME_TYPE_UNSIGNED_INT = SOAP12Constants.QNAME_TYPE_UNSIGNED_INT;
/* 710 */     this.QNAME_TYPE_LONG = SOAP12Constants.QNAME_TYPE_LONG;
/* 711 */     this.QNAME_TYPE_UNSIGNED_LONG = SOAP12Constants.QNAME_TYPE_UNSIGNED_LONG;
/* 712 */     this.QNAME_TYPE_SHORT = SOAP12Constants.QNAME_TYPE_SHORT;
/* 713 */     this.QNAME_TYPE_UNSIGNED_SHORT = SOAP12Constants.QNAME_TYPE_UNSIGNED_SHORT;
/* 714 */     this.QNAME_TYPE_DECIMAL = SOAP12Constants.QNAME_TYPE_DECIMAL;
/* 715 */     this.QNAME_TYPE_FLOAT = SOAP12Constants.QNAME_TYPE_FLOAT;
/* 716 */     this.QNAME_TYPE_DOUBLE = SOAP12Constants.QNAME_TYPE_DOUBLE;
/* 717 */     this.QNAME_TYPE_BOOLEAN = SOAP12Constants.QNAME_TYPE_BOOLEAN;
/* 718 */     this.QNAME_TYPE_TIME = SOAP12Constants.QNAME_TYPE_TIME;
/* 719 */     this.QNAME_TYPE_DATE_TIME = SOAP12Constants.QNAME_TYPE_DATE_TIME;
/* 720 */     this.QNAME_TYPE_DURATION = SOAP12Constants.QNAME_TYPE_DURATION;
/* 721 */     this.QNAME_TYPE_DATE = SOAP12Constants.QNAME_TYPE_DATE;
/* 722 */     this.QNAME_TYPE_G_MONTH = SOAP12Constants.QNAME_TYPE_G_MONTH;
/* 723 */     this.QNAME_TYPE_G_YEAR = SOAP12Constants.QNAME_TYPE_G_YEAR;
/* 724 */     this.QNAME_TYPE_G_YEAR_MONTH = SOAP12Constants.QNAME_TYPE_G_YEAR_MONTH;
/* 725 */     this.QNAME_TYPE_G_DAY = SOAP12Constants.QNAME_TYPE_G_DAY;
/* 726 */     this.QNAME_TYPE_G_MONTH_DAY = SOAP12Constants.QNAME_TYPE_G_MONTH_DAY;
/* 727 */     this.QNAME_TYPE_NAME = SOAP12Constants.QNAME_TYPE_NAME;
/* 728 */     this.QNAME_TYPE_QNAME = SOAP12Constants.QNAME_TYPE_QNAME;
/* 729 */     this.QNAME_TYPE_NCNAME = SOAP12Constants.QNAME_TYPE_NCNAME;
/* 730 */     this.QNAME_TYPE_ANY_URI = SOAP12Constants.QNAME_TYPE_ANY_URI;
/* 731 */     this.QNAME_TYPE_ID = SOAP12Constants.QNAME_TYPE_ID;
/* 732 */     this.QNAME_TYPE_IDREF = SOAP12Constants.QNAME_TYPE_IDREF;
/* 733 */     this.QNAME_TYPE_IDREFS = SOAP12Constants.QNAME_TYPE_IDREFS;
/* 734 */     this.QNAME_TYPE_ENTITY = SOAP12Constants.QNAME_TYPE_ENTITY;
/* 735 */     this.QNAME_TYPE_ENTITIES = SOAP12Constants.QNAME_TYPE_ENTITIES;
/* 736 */     this.QNAME_TYPE_NOTATION = SOAP12Constants.QNAME_TYPE_NOTATION;
/* 737 */     this.QNAME_TYPE_NMTOKEN = SOAP12Constants.QNAME_TYPE_NMTOKEN;
/* 738 */     this.QNAME_TYPE_NMTOKENS = SOAP12Constants.QNAME_TYPE_NMTOKENS;
/* 739 */     this.QNAME_TYPE_LANGUAGE = SOAP12Constants.QNAME_TYPE_LANGUAGE;
/*     */ 
/*     */     
/* 742 */     this.QNAME_ATTR_ID = SOAP12Constants.QNAME_ATTR_ID;
/* 743 */     this.QNAME_ATTR_HREF = SOAP12Constants.QNAME_ATTR_HREF;
/*     */   }
/*     */   
/*     */   public QName getQNameAttrItemType() {
/* 747 */     return this.QNAME_ATTR_ITEM_TYPE;
/*     */   }
/*     */   
/*     */   public QName getQNameAttrArraySize() {
/* 751 */     return this.QNAME_ATTR_ARRAY_SIZE;
/*     */   } }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\SOAPWSDLConstantsImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */