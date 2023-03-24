/*     */ package com.sun.xml.rpc.wsdl.document.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.spi.tools.SOAPConstants;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface SOAPConstants
/*     */   extends SOAPConstants
/*     */ {
/*     */   public static final String NS_WSDL_SOAP = "http://schemas.xmlsoap.org/wsdl/soap/";
/*     */   public static final String URI_SOAP_TRANSPORT_HTTP = "http://schemas.xmlsoap.org/soap/http";
/*  50 */   public static final QName QNAME_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "address");
/*     */   
/*  52 */   public static final QName QNAME_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "binding");
/*     */   
/*  54 */   public static final QName QNAME_BODY = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "body");
/*  55 */   public static final QName QNAME_FAULT = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "fault");
/*  56 */   public static final QName QNAME_HEADER = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "header");
/*  57 */   public static final QName QNAME_HEADERFAULT = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "headerfault");
/*     */   
/*  59 */   public static final QName QNAME_OPERATION = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "operation");
/*     */   
/*  61 */   public static final QName QNAME_MUSTUNDERSTAND = new QName("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand");
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final QName QNAME_TYPE_ARRAY = new QName("http://schemas.xmlsoap.org/soap/encoding/", "Array");
/*     */   
/*  67 */   public static final QName QNAME_ATTR_GROUP_COMMON_ATTRIBUTES = new QName("http://schemas.xmlsoap.org/soap/encoding/", "commonAttributes");
/*     */   
/*  69 */   public static final QName QNAME_ATTR_ARRAY_TYPE = new QName("http://schemas.xmlsoap.org/soap/encoding/", "arrayType");
/*     */   
/*  71 */   public static final QName QNAME_ATTR_OFFSET = new QName("http://schemas.xmlsoap.org/soap/encoding/", "offset");
/*     */   
/*  73 */   public static final QName QNAME_ATTR_POSITION = new QName("http://schemas.xmlsoap.org/soap/encoding/", "position");
/*     */ 
/*     */   
/*  76 */   public static final QName QNAME_TYPE_BASE64 = new QName("http://schemas.xmlsoap.org/soap/encoding/", "base64");
/*     */ 
/*     */   
/*  79 */   public static final QName QNAME_ELEMENT_STRING = new QName("http://schemas.xmlsoap.org/soap/encoding/", "string");
/*     */   
/*  81 */   public static final QName QNAME_ELEMENT_NORMALIZED_STRING = new QName("http://schemas.xmlsoap.org/soap/encoding/", "normalizedString");
/*     */   
/*  83 */   public static final QName QNAME_ELEMENT_TOKEN = new QName("http://schemas.xmlsoap.org/soap/encoding/", "token");
/*     */   
/*  85 */   public static final QName QNAME_ELEMENT_BYTE = new QName("http://schemas.xmlsoap.org/soap/encoding/", "byte");
/*     */   
/*  87 */   public static final QName QNAME_ELEMENT_UNSIGNED_BYTE = new QName("http://schemas.xmlsoap.org/soap/encoding/", "unsignedByte");
/*     */   
/*  89 */   public static final QName QNAME_ELEMENT_BASE64_BINARY = new QName("http://schemas.xmlsoap.org/soap/encoding/", "base64Binary");
/*     */   
/*  91 */   public static final QName QNAME_ELEMENT_HEX_BINARY = new QName("http://schemas.xmlsoap.org/soap/encoding/", "hexBinary");
/*     */   
/*  93 */   public static final QName QNAME_ELEMENT_INTEGER = new QName("http://schemas.xmlsoap.org/soap/encoding/", "integer");
/*     */   
/*  95 */   public static final QName QNAME_ELEMENT_POSITIVE_INTEGER = new QName("http://schemas.xmlsoap.org/soap/encoding/", "positiveInteger");
/*     */   
/*  97 */   public static final QName QNAME_ELEMENT_NEGATIVE_INTEGER = new QName("http://schemas.xmlsoap.org/soap/encoding/", "negativeInteger");
/*     */   
/*  99 */   public static final QName QNAME_ELEMENT_NON_NEGATIVE_INTEGER = new QName("http://schemas.xmlsoap.org/soap/encoding/", "nonNegativeInteger");
/*     */   
/* 101 */   public static final QName QNAME_ELEMENT_NON_POSITIVE_INTEGER = new QName("http://schemas.xmlsoap.org/soap/encoding/", "nonPositiveInteger");
/*     */   
/* 103 */   public static final QName QNAME_ELEMENT_INT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "int");
/*     */   
/* 105 */   public static final QName QNAME_ELEMENT_UNSIGNED_INT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "unsignedInt");
/*     */   
/* 107 */   public static final QName QNAME_ELEMENT_LONG = new QName("http://schemas.xmlsoap.org/soap/encoding/", "long");
/*     */   
/* 109 */   public static final QName QNAME_ELEMENT_UNSIGNED_LONG = new QName("http://schemas.xmlsoap.org/soap/encoding/", "unsignedLong");
/*     */   
/* 111 */   public static final QName QNAME_ELEMENT_SHORT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "short");
/*     */   
/* 113 */   public static final QName QNAME_ELEMENT_UNSIGNED_SHORT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "unsignedShort");
/*     */   
/* 115 */   public static final QName QNAME_ELEMENT_DECIMAL = new QName("http://schemas.xmlsoap.org/soap/encoding/", "decimal");
/*     */   
/* 117 */   public static final QName QNAME_ELEMENT_FLOAT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "float");
/*     */   
/* 119 */   public static final QName QNAME_ELEMENT_DOUBLE = new QName("http://schemas.xmlsoap.org/soap/encoding/", "double");
/*     */   
/* 121 */   public static final QName QNAME_ELEMENT_BOOLEAN = new QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean");
/*     */   
/* 123 */   public static final QName QNAME_ELEMENT_TIME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "time");
/*     */   
/* 125 */   public static final QName QNAME_ELEMENT_DATE_TIME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "dateTime");
/*     */   
/* 127 */   public static final QName QNAME_ELEMENT_DURATION = new QName("http://schemas.xmlsoap.org/soap/encoding/", "duration");
/*     */   
/* 129 */   public static final QName QNAME_ELEMENT_DATE = new QName("http://schemas.xmlsoap.org/soap/encoding/", "date");
/*     */   
/* 131 */   public static final QName QNAME_ELEMENT_G_MONTH = new QName("http://schemas.xmlsoap.org/soap/encoding/", "gMonth");
/*     */   
/* 133 */   public static final QName QNAME_ELEMENT_G_YEAR = new QName("http://schemas.xmlsoap.org/soap/encoding/", "gYear");
/*     */   
/* 135 */   public static final QName QNAME_ELEMENT_G_YEAR_MONTH = new QName("http://schemas.xmlsoap.org/soap/encoding/", "gYearMonth");
/*     */   
/* 137 */   public static final QName QNAME_ELEMENT_G_DAY = new QName("http://schemas.xmlsoap.org/soap/encoding/", "gDay");
/*     */   
/* 139 */   public static final QName QNAME_ELEMENT_G_MONTH_DAY = new QName("http://schemas.xmlsoap.org/soap/encoding/", "gMonthDay");
/*     */   
/* 141 */   public static final QName QNAME_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "Name");
/*     */   
/* 143 */   public static final QName QNAME_ELEMENT_QNAME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "QName");
/*     */   
/* 145 */   public static final QName QNAME_ELEMENT_NCNAME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "NCName");
/*     */   
/* 147 */   public static final QName QNAME_ELEMENT_ANY_URI = new QName("http://schemas.xmlsoap.org/soap/encoding/", "anyURI");
/*     */   
/* 149 */   public static final QName QNAME_ELEMENT_ID = new QName("http://schemas.xmlsoap.org/soap/encoding/", "ID");
/*     */   
/* 151 */   public static final QName QNAME_ELEMENT_IDREF = new QName("http://schemas.xmlsoap.org/soap/encoding/", "IDREF");
/*     */   
/* 153 */   public static final QName QNAME_ELEMENT_IDREFS = new QName("http://schemas.xmlsoap.org/soap/encoding/", "IDREFS");
/*     */   
/* 155 */   public static final QName QNAME_ELEMENT_ENTITY = new QName("http://schemas.xmlsoap.org/soap/encoding/", "ENTITY");
/*     */   
/* 157 */   public static final QName QNAME_ELEMENT_ENTITIES = new QName("http://schemas.xmlsoap.org/soap/encoding/", "ENTITIES");
/*     */   
/* 159 */   public static final QName QNAME_ELEMENT_NOTATION = new QName("http://schemas.xmlsoap.org/soap/encoding/", "NOTATION");
/*     */   
/* 161 */   public static final QName QNAME_ELEMENT_NMTOKEN = new QName("http://schemas.xmlsoap.org/soap/encoding/", "NMTOKEN");
/*     */   
/* 163 */   public static final QName QNAME_ELEMENT_NMTOKENS = new QName("http://schemas.xmlsoap.org/soap/encoding/", "NMTOKENS");
/*     */ 
/*     */   
/* 166 */   public static final QName QNAME_TYPE_STRING = new QName("http://schemas.xmlsoap.org/soap/encoding/", "string");
/*     */   
/* 168 */   public static final QName QNAME_TYPE_NORMALIZED_STRING = new QName("http://schemas.xmlsoap.org/soap/encoding/", "normalizedString");
/*     */   
/* 170 */   public static final QName QNAME_TYPE_TOKEN = new QName("http://schemas.xmlsoap.org/soap/encoding/", "token");
/*     */   
/* 172 */   public static final QName QNAME_TYPE_BYTE = new QName("http://schemas.xmlsoap.org/soap/encoding/", "byte");
/*     */   
/* 174 */   public static final QName QNAME_TYPE_UNSIGNED_BYTE = new QName("http://schemas.xmlsoap.org/soap/encoding/", "unsignedByte");
/*     */   
/* 176 */   public static final QName QNAME_TYPE_BASE64_BINARY = new QName("http://schemas.xmlsoap.org/soap/encoding/", "base64Binary");
/*     */   
/* 178 */   public static final QName QNAME_TYPE_HEX_BINARY = new QName("http://schemas.xmlsoap.org/soap/encoding/", "hexBinary");
/*     */   
/* 180 */   public static final QName QNAME_TYPE_INTEGER = new QName("http://schemas.xmlsoap.org/soap/encoding/", "integer");
/*     */   
/* 182 */   public static final QName QNAME_TYPE_POSITIVE_INTEGER = new QName("http://schemas.xmlsoap.org/soap/encoding/", "positiveInteger");
/*     */   
/* 184 */   public static final QName QNAME_TYPE_NEGATIVE_INTEGER = new QName("http://schemas.xmlsoap.org/soap/encoding/", "negativeInteger");
/*     */   
/* 186 */   public static final QName QNAME_TYPE_NON_NEGATIVE_INTEGER = new QName("http://schemas.xmlsoap.org/soap/encoding/", "nonNegativeInteger");
/*     */   
/* 188 */   public static final QName QNAME_TYPE_NON_POSITIVE_INTEGER = new QName("http://schemas.xmlsoap.org/soap/encoding/", "nonPositiveInteger");
/*     */   
/* 190 */   public static final QName QNAME_TYPE_INT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "int");
/*     */   
/* 192 */   public static final QName QNAME_TYPE_UNSIGNED_INT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "unsignedInt");
/*     */   
/* 194 */   public static final QName QNAME_TYPE_LONG = new QName("http://schemas.xmlsoap.org/soap/encoding/", "long");
/*     */   
/* 196 */   public static final QName QNAME_TYPE_UNSIGNED_LONG = new QName("http://schemas.xmlsoap.org/soap/encoding/", "unsignedLong");
/*     */   
/* 198 */   public static final QName QNAME_TYPE_SHORT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "short");
/*     */   
/* 200 */   public static final QName QNAME_TYPE_UNSIGNED_SHORT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "unsignedShort");
/*     */   
/* 202 */   public static final QName QNAME_TYPE_DECIMAL = new QName("http://schemas.xmlsoap.org/soap/encoding/", "decimal");
/*     */   
/* 204 */   public static final QName QNAME_TYPE_FLOAT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "float");
/*     */   
/* 206 */   public static final QName QNAME_TYPE_DOUBLE = new QName("http://schemas.xmlsoap.org/soap/encoding/", "double");
/*     */   
/* 208 */   public static final QName QNAME_TYPE_BOOLEAN = new QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean");
/*     */   
/* 210 */   public static final QName QNAME_TYPE_TIME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "time");
/*     */   
/* 212 */   public static final QName QNAME_TYPE_DATE_TIME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "dateTime");
/*     */   
/* 214 */   public static final QName QNAME_TYPE_DURATION = new QName("http://schemas.xmlsoap.org/soap/encoding/", "duration");
/*     */   
/* 216 */   public static final QName QNAME_TYPE_DATE = new QName("http://schemas.xmlsoap.org/soap/encoding/", "date");
/*     */   
/* 218 */   public static final QName QNAME_TYPE_G_MONTH = new QName("http://schemas.xmlsoap.org/soap/encoding/", "gMonth");
/*     */   
/* 220 */   public static final QName QNAME_TYPE_G_YEAR = new QName("http://schemas.xmlsoap.org/soap/encoding/", "gYear");
/*     */   
/* 222 */   public static final QName QNAME_TYPE_G_YEAR_MONTH = new QName("http://schemas.xmlsoap.org/soap/encoding/", "gYearMonth");
/*     */   
/* 224 */   public static final QName QNAME_TYPE_G_DAY = new QName("http://schemas.xmlsoap.org/soap/encoding/", "gDay");
/*     */   
/* 226 */   public static final QName QNAME_TYPE_G_MONTH_DAY = new QName("http://schemas.xmlsoap.org/soap/encoding/", "gMonthDay");
/*     */   
/* 228 */   public static final QName QNAME_TYPE_NAME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "Name");
/*     */   
/* 230 */   public static final QName QNAME_TYPE_QNAME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "QName");
/*     */   
/* 232 */   public static final QName QNAME_TYPE_NCNAME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "NCName");
/*     */   
/* 234 */   public static final QName QNAME_TYPE_ANY_URI = new QName("http://schemas.xmlsoap.org/soap/encoding/", "anyURI");
/*     */   
/* 236 */   public static final QName QNAME_TYPE_ID = new QName("http://schemas.xmlsoap.org/soap/encoding/", "ID");
/* 237 */   public static final QName QNAME_TYPE_IDREF = new QName("http://schemas.xmlsoap.org/soap/encoding/", "IDREF");
/*     */   
/* 239 */   public static final QName QNAME_TYPE_IDREFS = new QName("http://schemas.xmlsoap.org/soap/encoding/", "IDREFS");
/*     */   
/* 241 */   public static final QName QNAME_TYPE_ENTITY = new QName("http://schemas.xmlsoap.org/soap/encoding/", "ENTITY");
/*     */   
/* 243 */   public static final QName QNAME_TYPE_ENTITIES = new QName("http://schemas.xmlsoap.org/soap/encoding/", "ENTITIES");
/*     */   
/* 245 */   public static final QName QNAME_TYPE_NOTATION = new QName("http://schemas.xmlsoap.org/soap/encoding/", "NOTATION");
/*     */   
/* 247 */   public static final QName QNAME_TYPE_NMTOKEN = new QName("http://schemas.xmlsoap.org/soap/encoding/", "NMTOKEN");
/*     */   
/* 249 */   public static final QName QNAME_TYPE_NMTOKENS = new QName("http://schemas.xmlsoap.org/soap/encoding/", "NMTOKENS");
/*     */   
/* 251 */   public static final QName QNAME_TYPE_LANGUAGE = new QName("http://schemas.xmlsoap.org/soap/encoding/", "LANGUAGE");
/*     */ 
/*     */ 
/*     */   
/* 255 */   public static final QName QNAME_ATTR_ID = new QName("", "id");
/* 256 */   public static final QName QNAME_ATTR_HREF = new QName("", "href");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\soap\SOAPConstants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */