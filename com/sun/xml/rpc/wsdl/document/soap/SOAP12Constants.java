/*     */ package com.sun.xml.rpc.wsdl.document.soap;
/*     */ 
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
/*     */ public interface SOAP12Constants
/*     */ {
/*     */   public static final String NS_WSDL_SOAP = "http://schemas.xmlsoap.org/wsdl/soap/";
/*     */   public static final String NS_SOAP_ENCODING = "http://www.w3.org/2002/06/soap-encoding";
/*     */   public static final String URI_SOAP_TRANSPORT_HTTP = "http://www.w3.org/2002/06/soap/bindings/HTTP/";
/*  45 */   public static final QName QNAME_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "address");
/*  46 */   public static final QName QNAME_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "binding");
/*  47 */   public static final QName QNAME_BODY = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "body");
/*  48 */   public static final QName QNAME_FAULT = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "fault");
/*  49 */   public static final QName QNAME_HEADER = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "header");
/*  50 */   public static final QName QNAME_HEADERFAULT = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "headerfault");
/*     */   
/*  52 */   public static final QName QNAME_OPERATION = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "operation");
/*     */ 
/*     */   
/*  55 */   public static final QName QNAME_TYPE_ARRAY = new QName("http://www.w3.org/2002/06/soap-encoding", "Array");
/*  56 */   public static final QName QNAME_ATTR_GROUP_COMMON_ATTRIBUTES = new QName("http://www.w3.org/2002/06/soap-encoding", "commonAttributes");
/*     */   
/*  58 */   public static final QName QNAME_ATTR_ARRAY_TYPE = new QName("http://www.w3.org/2002/06/soap-encoding", "arrayType");
/*     */   
/*  60 */   public static final QName QNAME_ATTR_ITEM_TYPE = new QName("http://www.w3.org/2002/06/soap-encoding", "itemType");
/*     */   
/*  62 */   public static final QName QNAME_ATTR_ARRAY_SIZE = new QName("http://www.w3.org/2002/06/soap-encoding", "arraySize");
/*     */   
/*  64 */   public static final QName QNAME_ATTR_OFFSET = new QName("http://www.w3.org/2002/06/soap-encoding", "offset");
/*     */   
/*  66 */   public static final QName QNAME_ATTR_POSITION = new QName("http://www.w3.org/2002/06/soap-encoding", "position");
/*     */ 
/*     */   
/*  69 */   public static final QName QNAME_TYPE_BASE64 = new QName("http://www.w3.org/2002/06/soap-encoding", "base64");
/*     */ 
/*     */   
/*  72 */   public static final QName QNAME_ELEMENT_STRING = new QName("http://www.w3.org/2002/06/soap-encoding", "string");
/*     */   
/*  74 */   public static final QName QNAME_ELEMENT_NORMALIZED_STRING = new QName("http://www.w3.org/2002/06/soap-encoding", "normalizedString");
/*     */   
/*  76 */   public static final QName QNAME_ELEMENT_TOKEN = new QName("http://www.w3.org/2002/06/soap-encoding", "token");
/*     */   
/*  78 */   public static final QName QNAME_ELEMENT_BYTE = new QName("http://www.w3.org/2002/06/soap-encoding", "byte");
/*     */   
/*  80 */   public static final QName QNAME_ELEMENT_UNSIGNED_BYTE = new QName("http://www.w3.org/2002/06/soap-encoding", "unsignedByte");
/*     */   
/*  82 */   public static final QName QNAME_ELEMENT_BASE64_BINARY = new QName("http://www.w3.org/2002/06/soap-encoding", "base64Binary");
/*     */   
/*  84 */   public static final QName QNAME_ELEMENT_HEX_BINARY = new QName("http://www.w3.org/2002/06/soap-encoding", "hexBinary");
/*     */   
/*  86 */   public static final QName QNAME_ELEMENT_INTEGER = new QName("http://www.w3.org/2002/06/soap-encoding", "integer");
/*     */   
/*  88 */   public static final QName QNAME_ELEMENT_POSITIVE_INTEGER = new QName("http://www.w3.org/2002/06/soap-encoding", "positiveInteger");
/*     */   
/*  90 */   public static final QName QNAME_ELEMENT_NEGATIVE_INTEGER = new QName("http://www.w3.org/2002/06/soap-encoding", "negativeInteger");
/*     */   
/*  92 */   public static final QName QNAME_ELEMENT_NON_NEGATIVE_INTEGER = new QName("http://www.w3.org/2002/06/soap-encoding", "nonNegativeInteger");
/*     */   
/*  94 */   public static final QName QNAME_ELEMENT_NON_POSITIVE_INTEGER = new QName("http://www.w3.org/2002/06/soap-encoding", "nonPositiveInteger");
/*     */   
/*  96 */   public static final QName QNAME_ELEMENT_INT = new QName("http://www.w3.org/2002/06/soap-encoding", "int");
/*  97 */   public static final QName QNAME_ELEMENT_UNSIGNED_INT = new QName("http://www.w3.org/2002/06/soap-encoding", "unsignedInt");
/*     */   
/*  99 */   public static final QName QNAME_ELEMENT_LONG = new QName("http://www.w3.org/2002/06/soap-encoding", "long");
/*     */   
/* 101 */   public static final QName QNAME_ELEMENT_UNSIGNED_LONG = new QName("http://www.w3.org/2002/06/soap-encoding", "unsignedLong");
/*     */   
/* 103 */   public static final QName QNAME_ELEMENT_SHORT = new QName("http://www.w3.org/2002/06/soap-encoding", "short");
/*     */   
/* 105 */   public static final QName QNAME_ELEMENT_UNSIGNED_SHORT = new QName("http://www.w3.org/2002/06/soap-encoding", "unsignedShort");
/*     */   
/* 107 */   public static final QName QNAME_ELEMENT_DECIMAL = new QName("http://www.w3.org/2002/06/soap-encoding", "decimal");
/*     */   
/* 109 */   public static final QName QNAME_ELEMENT_FLOAT = new QName("http://www.w3.org/2002/06/soap-encoding", "float");
/*     */   
/* 111 */   public static final QName QNAME_ELEMENT_DOUBLE = new QName("http://www.w3.org/2002/06/soap-encoding", "double");
/*     */   
/* 113 */   public static final QName QNAME_ELEMENT_BOOLEAN = new QName("http://www.w3.org/2002/06/soap-encoding", "boolean");
/*     */   
/* 115 */   public static final QName QNAME_ELEMENT_TIME = new QName("http://www.w3.org/2002/06/soap-encoding", "time");
/*     */   
/* 117 */   public static final QName QNAME_ELEMENT_DATE_TIME = new QName("http://www.w3.org/2002/06/soap-encoding", "dateTime");
/*     */   
/* 119 */   public static final QName QNAME_ELEMENT_DURATION = new QName("http://www.w3.org/2002/06/soap-encoding", "duration");
/*     */   
/* 121 */   public static final QName QNAME_ELEMENT_DATE = new QName("http://www.w3.org/2002/06/soap-encoding", "date");
/*     */   
/* 123 */   public static final QName QNAME_ELEMENT_G_MONTH = new QName("http://www.w3.org/2002/06/soap-encoding", "gMonth");
/*     */   
/* 125 */   public static final QName QNAME_ELEMENT_G_YEAR = new QName("http://www.w3.org/2002/06/soap-encoding", "gYear");
/*     */   
/* 127 */   public static final QName QNAME_ELEMENT_G_YEAR_MONTH = new QName("http://www.w3.org/2002/06/soap-encoding", "gYearMonth");
/*     */   
/* 129 */   public static final QName QNAME_ELEMENT_G_DAY = new QName("http://www.w3.org/2002/06/soap-encoding", "gDay");
/*     */   
/* 131 */   public static final QName QNAME_ELEMENT_G_MONTH_DAY = new QName("http://www.w3.org/2002/06/soap-encoding", "gMonthDay");
/*     */   
/* 133 */   public static final QName QNAME_ELEMENT_NAME = new QName("http://www.w3.org/2002/06/soap-encoding", "Name");
/*     */   
/* 135 */   public static final QName QNAME_ELEMENT_QNAME = new QName("http://www.w3.org/2002/06/soap-encoding", "QName");
/*     */   
/* 137 */   public static final QName QNAME_ELEMENT_NCNAME = new QName("http://www.w3.org/2002/06/soap-encoding", "NCName");
/*     */   
/* 139 */   public static final QName QNAME_ELEMENT_ANY_URI = new QName("http://www.w3.org/2002/06/soap-encoding", "anyURI");
/*     */   
/* 141 */   public static final QName QNAME_ELEMENT_ID = new QName("http://www.w3.org/2002/06/soap-encoding", "ID");
/* 142 */   public static final QName QNAME_ELEMENT_IDREF = new QName("http://www.w3.org/2002/06/soap-encoding", "IDREF");
/*     */   
/* 144 */   public static final QName QNAME_ELEMENT_IDREFS = new QName("http://www.w3.org/2002/06/soap-encoding", "IDREFS");
/*     */   
/* 146 */   public static final QName QNAME_ELEMENT_ENTITY = new QName("http://www.w3.org/2002/06/soap-encoding", "ENTITY");
/*     */   
/* 148 */   public static final QName QNAME_ELEMENT_ENTITIES = new QName("http://www.w3.org/2002/06/soap-encoding", "ENTITIES");
/*     */   
/* 150 */   public static final QName QNAME_ELEMENT_NOTATION = new QName("http://www.w3.org/2002/06/soap-encoding", "NOTATION");
/*     */   
/* 152 */   public static final QName QNAME_ELEMENT_NMTOKEN = new QName("http://www.w3.org/2002/06/soap-encoding", "NMTOKEN");
/*     */   
/* 154 */   public static final QName QNAME_ELEMENT_NMTOKENS = new QName("http://www.w3.org/2002/06/soap-encoding", "NMTOKENS");
/*     */ 
/*     */   
/* 157 */   public static final QName QNAME_TYPE_STRING = new QName("http://www.w3.org/2002/06/soap-encoding", "string");
/*     */   
/* 159 */   public static final QName QNAME_TYPE_NORMALIZED_STRING = new QName("http://www.w3.org/2002/06/soap-encoding", "normalizedString");
/*     */   
/* 161 */   public static final QName QNAME_TYPE_TOKEN = new QName("http://www.w3.org/2002/06/soap-encoding", "token");
/* 162 */   public static final QName QNAME_TYPE_BYTE = new QName("http://www.w3.org/2002/06/soap-encoding", "byte");
/* 163 */   public static final QName QNAME_TYPE_UNSIGNED_BYTE = new QName("http://www.w3.org/2002/06/soap-encoding", "unsignedByte");
/*     */   
/* 165 */   public static final QName QNAME_TYPE_BASE64_BINARY = new QName("http://www.w3.org/2002/06/soap-encoding", "base64Binary");
/*     */   
/* 167 */   public static final QName QNAME_TYPE_HEX_BINARY = new QName("http://www.w3.org/2002/06/soap-encoding", "hexBinary");
/*     */   
/* 169 */   public static final QName QNAME_TYPE_INTEGER = new QName("http://www.w3.org/2002/06/soap-encoding", "integer");
/*     */   
/* 171 */   public static final QName QNAME_TYPE_POSITIVE_INTEGER = new QName("http://www.w3.org/2002/06/soap-encoding", "positiveInteger");
/*     */   
/* 173 */   public static final QName QNAME_TYPE_NEGATIVE_INTEGER = new QName("http://www.w3.org/2002/06/soap-encoding", "negativeInteger");
/*     */   
/* 175 */   public static final QName QNAME_TYPE_NON_NEGATIVE_INTEGER = new QName("http://www.w3.org/2002/06/soap-encoding", "nonNegativeInteger");
/*     */   
/* 177 */   public static final QName QNAME_TYPE_NON_POSITIVE_INTEGER = new QName("http://www.w3.org/2002/06/soap-encoding", "nonPositiveInteger");
/*     */   
/* 179 */   public static final QName QNAME_TYPE_INT = new QName("http://www.w3.org/2002/06/soap-encoding", "int");
/* 180 */   public static final QName QNAME_TYPE_UNSIGNED_INT = new QName("http://www.w3.org/2002/06/soap-encoding", "unsignedInt");
/*     */   
/* 182 */   public static final QName QNAME_TYPE_LONG = new QName("http://www.w3.org/2002/06/soap-encoding", "long");
/* 183 */   public static final QName QNAME_TYPE_UNSIGNED_LONG = new QName("http://www.w3.org/2002/06/soap-encoding", "unsignedLong");
/*     */   
/* 185 */   public static final QName QNAME_TYPE_SHORT = new QName("http://www.w3.org/2002/06/soap-encoding", "short");
/* 186 */   public static final QName QNAME_TYPE_UNSIGNED_SHORT = new QName("http://www.w3.org/2002/06/soap-encoding", "unsignedShort");
/*     */   
/* 188 */   public static final QName QNAME_TYPE_DECIMAL = new QName("http://www.w3.org/2002/06/soap-encoding", "decimal");
/*     */   
/* 190 */   public static final QName QNAME_TYPE_FLOAT = new QName("http://www.w3.org/2002/06/soap-encoding", "float");
/* 191 */   public static final QName QNAME_TYPE_DOUBLE = new QName("http://www.w3.org/2002/06/soap-encoding", "double");
/*     */   
/* 193 */   public static final QName QNAME_TYPE_BOOLEAN = new QName("http://www.w3.org/2002/06/soap-encoding", "boolean");
/*     */   
/* 195 */   public static final QName QNAME_TYPE_TIME = new QName("http://www.w3.org/2002/06/soap-encoding", "time");
/* 196 */   public static final QName QNAME_TYPE_DATE_TIME = new QName("http://www.w3.org/2002/06/soap-encoding", "dateTime");
/*     */   
/* 198 */   public static final QName QNAME_TYPE_DURATION = new QName("http://www.w3.org/2002/06/soap-encoding", "duration");
/*     */   
/* 200 */   public static final QName QNAME_TYPE_DATE = new QName("http://www.w3.org/2002/06/soap-encoding", "date");
/* 201 */   public static final QName QNAME_TYPE_G_MONTH = new QName("http://www.w3.org/2002/06/soap-encoding", "gMonth");
/*     */   
/* 203 */   public static final QName QNAME_TYPE_G_YEAR = new QName("http://www.w3.org/2002/06/soap-encoding", "gYear");
/*     */   
/* 205 */   public static final QName QNAME_TYPE_G_YEAR_MONTH = new QName("http://www.w3.org/2002/06/soap-encoding", "gYearMonth");
/*     */   
/* 207 */   public static final QName QNAME_TYPE_G_DAY = new QName("http://www.w3.org/2002/06/soap-encoding", "gDay");
/* 208 */   public static final QName QNAME_TYPE_G_MONTH_DAY = new QName("http://www.w3.org/2002/06/soap-encoding", "gMonthDay");
/*     */   
/* 210 */   public static final QName QNAME_TYPE_NAME = new QName("http://www.w3.org/2002/06/soap-encoding", "Name");
/* 211 */   public static final QName QNAME_TYPE_QNAME = new QName("http://www.w3.org/2002/06/soap-encoding", "QName");
/* 212 */   public static final QName QNAME_TYPE_NCNAME = new QName("http://www.w3.org/2002/06/soap-encoding", "NCName");
/*     */   
/* 214 */   public static final QName QNAME_TYPE_ANY_URI = new QName("http://www.w3.org/2002/06/soap-encoding", "anyURI");
/*     */   
/* 216 */   public static final QName QNAME_TYPE_ID = new QName("http://www.w3.org/2002/06/soap-encoding", "ID");
/* 217 */   public static final QName QNAME_TYPE_IDREF = new QName("http://www.w3.org/2002/06/soap-encoding", "IDREF");
/* 218 */   public static final QName QNAME_TYPE_IDREFS = new QName("http://www.w3.org/2002/06/soap-encoding", "IDREFS");
/*     */   
/* 220 */   public static final QName QNAME_TYPE_ENTITY = new QName("http://www.w3.org/2002/06/soap-encoding", "ENTITY");
/*     */   
/* 222 */   public static final QName QNAME_TYPE_ENTITIES = new QName("http://www.w3.org/2002/06/soap-encoding", "ENTITIES");
/*     */   
/* 224 */   public static final QName QNAME_TYPE_NOTATION = new QName("http://www.w3.org/2002/06/soap-encoding", "NOTATION");
/*     */   
/* 226 */   public static final QName QNAME_TYPE_NMTOKEN = new QName("http://www.w3.org/2002/06/soap-encoding", "NMTOKEN");
/*     */   
/* 228 */   public static final QName QNAME_TYPE_NMTOKENS = new QName("http://www.w3.org/2002/06/soap-encoding", "NMTOKENS");
/*     */   
/* 230 */   public static final QName QNAME_TYPE_LANGUAGE = new QName("http://www.w3.org/2002/06/soap-encoding", "LANGUAGE");
/*     */ 
/*     */ 
/*     */   
/* 234 */   public static final QName QNAME_ATTR_ID = new QName("", "id");
/* 235 */   public static final QName QNAME_ATTR_HREF = new QName("", "ref");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\soap\SOAP12Constants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */