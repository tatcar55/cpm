/*     */ package com.sun.xml.rpc.wsdl.document.schema;
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
/*     */ public interface SchemaConstants
/*     */ {
/*     */   public static final String NS_XMLNS = "http://www.w3.org/2000/xmlns/";
/*     */   public static final String NS_XSD = "http://www.w3.org/2001/XMLSchema";
/*     */   public static final String NS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
/*  43 */   public static final QName QNAME_ALL = new QName("http://www.w3.org/2001/XMLSchema", "all");
/*  44 */   public static final QName QNAME_ANNOTATION = new QName("http://www.w3.org/2001/XMLSchema", "annotation");
/*  45 */   public static final QName QNAME_ANY = new QName("http://www.w3.org/2001/XMLSchema", "any");
/*  46 */   public static final QName QNAME_ANY_ATTRIBUTE = new QName("http://www.w3.org/2001/XMLSchema", "anyAttribute");
/*  47 */   public static final QName QNAME_ATTRIBUTE = new QName("http://www.w3.org/2001/XMLSchema", "attribute");
/*  48 */   public static final QName QNAME_ATTRIBUTE_GROUP = new QName("http://www.w3.org/2001/XMLSchema", "attributeGroup");
/*     */   
/*  50 */   public static final QName QNAME_CHOICE = new QName("http://www.w3.org/2001/XMLSchema", "choice");
/*  51 */   public static final QName QNAME_COMPLEX_CONTENT = new QName("http://www.w3.org/2001/XMLSchema", "complexContent");
/*     */   
/*  53 */   public static final QName QNAME_COMPLEX_TYPE = new QName("http://www.w3.org/2001/XMLSchema", "complexType");
/*  54 */   public static final QName QNAME_ELEMENT = new QName("http://www.w3.org/2001/XMLSchema", "element");
/*  55 */   public static final QName QNAME_ENUMERATION = new QName("http://www.w3.org/2001/XMLSchema", "enumeration");
/*  56 */   public static final QName QNAME_EXTENSION = new QName("http://www.w3.org/2001/XMLSchema", "extension");
/*  57 */   public static final QName QNAME_FIELD = new QName("http://www.w3.org/2001/XMLSchema", "field");
/*  58 */   public static final QName QNAME_FRACTION_DIGITS = new QName("http://www.w3.org/2001/XMLSchema", "fractionDigits");
/*     */   
/*  60 */   public static final QName QNAME_GROUP = new QName("http://www.w3.org/2001/XMLSchema", "group");
/*  61 */   public static final QName QNAME_IMPORT = new QName("http://www.w3.org/2001/XMLSchema", "import");
/*  62 */   public static final QName QNAME_INCLUDE = new QName("http://www.w3.org/2001/XMLSchema", "include");
/*  63 */   public static final QName QNAME_KEY = new QName("http://www.w3.org/2001/XMLSchema", "key");
/*  64 */   public static final QName QNAME_KEYREF = new QName("http://www.w3.org/2001/XMLSchema", "keyref");
/*  65 */   public static final QName QNAME_LENGTH = new QName("http://www.w3.org/2001/XMLSchema", "length");
/*  66 */   public static final QName QNAME_LIST = new QName("http://www.w3.org/2001/XMLSchema", "list");
/*  67 */   public static final QName QNAME_MAX_EXCLUSIVE = new QName("http://www.w3.org/2001/XMLSchema", "maxExclusive");
/*  68 */   public static final QName QNAME_MAX_INCLUSIVE = new QName("http://www.w3.org/2001/XMLSchema", "maxInclusive");
/*  69 */   public static final QName QNAME_MAX_LENGTH = new QName("http://www.w3.org/2001/XMLSchema", "maxLength");
/*  70 */   public static final QName QNAME_MIN_EXCLUSIVE = new QName("http://www.w3.org/2001/XMLSchema", "minExclusive");
/*  71 */   public static final QName QNAME_MIN_INCLUSIVE = new QName("http://www.w3.org/2001/XMLSchema", "minInclusive");
/*  72 */   public static final QName QNAME_MIN_LENGTH = new QName("http://www.w3.org/2001/XMLSchema", "minLength");
/*  73 */   public static final QName QNAME_NOTATION = new QName("http://www.w3.org/2001/XMLSchema", "notation");
/*  74 */   public static final QName QNAME_RESTRICTION = new QName("http://www.w3.org/2001/XMLSchema", "restriction");
/*  75 */   public static final QName QNAME_PATTERN = new QName("http://www.w3.org/2001/XMLSchema", "pattern");
/*  76 */   public static final QName QNAME_PRECISION = new QName("http://www.w3.org/2001/XMLSchema", "precision");
/*  77 */   public static final QName QNAME_REDEFINE = new QName("http://www.w3.org/2001/XMLSchema", "redefine");
/*  78 */   public static final QName QNAME_SCALE = new QName("http://www.w3.org/2001/XMLSchema", "scale");
/*  79 */   public static final QName QNAME_SCHEMA = new QName("http://www.w3.org/2001/XMLSchema", "schema");
/*  80 */   public static final QName QNAME_SELECTOR = new QName("http://www.w3.org/2001/XMLSchema", "selector");
/*  81 */   public static final QName QNAME_SEQUENCE = new QName("http://www.w3.org/2001/XMLSchema", "sequence");
/*  82 */   public static final QName QNAME_SIMPLE_CONTENT = new QName("http://www.w3.org/2001/XMLSchema", "simpleContent");
/*     */   
/*  84 */   public static final QName QNAME_SIMPLE_TYPE = new QName("http://www.w3.org/2001/XMLSchema", "simpleType");
/*  85 */   public static final QName QNAME_TOTAL_DIGITS = new QName("http://www.w3.org/2001/XMLSchema", "totalDigits");
/*  86 */   public static final QName QNAME_UNIQUE = new QName("http://www.w3.org/2001/XMLSchema", "unique");
/*  87 */   public static final QName QNAME_UNION = new QName("http://www.w3.org/2001/XMLSchema", "union");
/*  88 */   public static final QName QNAME_WHITE_SPACE = new QName("http://www.w3.org/2001/XMLSchema", "whiteSpace");
/*     */ 
/*     */   
/*  91 */   public static final QName QNAME_TYPE_STRING = new QName("http://www.w3.org/2001/XMLSchema", "string");
/*  92 */   public static final QName QNAME_TYPE_NORMALIZED_STRING = new QName("http://www.w3.org/2001/XMLSchema", "normalizedString");
/*     */   
/*  94 */   public static final QName QNAME_TYPE_TOKEN = new QName("http://www.w3.org/2001/XMLSchema", "token");
/*  95 */   public static final QName QNAME_TYPE_BYTE = new QName("http://www.w3.org/2001/XMLSchema", "byte");
/*  96 */   public static final QName QNAME_TYPE_UNSIGNED_BYTE = new QName("http://www.w3.org/2001/XMLSchema", "unsignedByte");
/*     */   
/*  98 */   public static final QName QNAME_TYPE_BASE64_BINARY = new QName("http://www.w3.org/2001/XMLSchema", "base64Binary");
/*     */   
/* 100 */   public static final QName QNAME_TYPE_HEX_BINARY = new QName("http://www.w3.org/2001/XMLSchema", "hexBinary");
/* 101 */   public static final QName QNAME_TYPE_INTEGER = new QName("http://www.w3.org/2001/XMLSchema", "integer");
/* 102 */   public static final QName QNAME_TYPE_POSITIVE_INTEGER = new QName("http://www.w3.org/2001/XMLSchema", "positiveInteger");
/*     */   
/* 104 */   public static final QName QNAME_TYPE_NEGATIVE_INTEGER = new QName("http://www.w3.org/2001/XMLSchema", "negativeInteger");
/*     */   
/* 106 */   public static final QName QNAME_TYPE_NON_NEGATIVE_INTEGER = new QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger");
/*     */   
/* 108 */   public static final QName QNAME_TYPE_NON_POSITIVE_INTEGER = new QName("http://www.w3.org/2001/XMLSchema", "nonPositiveInteger");
/*     */   
/* 110 */   public static final QName QNAME_TYPE_INT = new QName("http://www.w3.org/2001/XMLSchema", "int");
/* 111 */   public static final QName QNAME_TYPE_UNSIGNED_INT = new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt");
/*     */   
/* 113 */   public static final QName QNAME_TYPE_LONG = new QName("http://www.w3.org/2001/XMLSchema", "long");
/* 114 */   public static final QName QNAME_TYPE_UNSIGNED_LONG = new QName("http://www.w3.org/2001/XMLSchema", "unsignedLong");
/*     */   
/* 116 */   public static final QName QNAME_TYPE_SHORT = new QName("http://www.w3.org/2001/XMLSchema", "short");
/* 117 */   public static final QName QNAME_TYPE_UNSIGNED_SHORT = new QName("http://www.w3.org/2001/XMLSchema", "unsignedShort");
/*     */   
/* 119 */   public static final QName QNAME_TYPE_DECIMAL = new QName("http://www.w3.org/2001/XMLSchema", "decimal");
/* 120 */   public static final QName QNAME_TYPE_FLOAT = new QName("http://www.w3.org/2001/XMLSchema", "float");
/* 121 */   public static final QName QNAME_TYPE_DOUBLE = new QName("http://www.w3.org/2001/XMLSchema", "double");
/* 122 */   public static final QName QNAME_TYPE_BOOLEAN = new QName("http://www.w3.org/2001/XMLSchema", "boolean");
/* 123 */   public static final QName QNAME_TYPE_TIME = new QName("http://www.w3.org/2001/XMLSchema", "time");
/* 124 */   public static final QName QNAME_TYPE_DATE_TIME = new QName("http://www.w3.org/2001/XMLSchema", "dateTime");
/* 125 */   public static final QName QNAME_TYPE_DURATION = new QName("http://www.w3.org/2001/XMLSchema", "duration");
/* 126 */   public static final QName QNAME_TYPE_DATE = new QName("http://www.w3.org/2001/XMLSchema", "date");
/* 127 */   public static final QName QNAME_TYPE_G_MONTH = new QName("http://www.w3.org/2001/XMLSchema", "gMonth");
/* 128 */   public static final QName QNAME_TYPE_G_YEAR = new QName("http://www.w3.org/2001/XMLSchema", "gYear");
/* 129 */   public static final QName QNAME_TYPE_G_YEAR_MONTH = new QName("http://www.w3.org/2001/XMLSchema", "gYearMonth");
/*     */   
/* 131 */   public static final QName QNAME_TYPE_G_DAY = new QName("http://www.w3.org/2001/XMLSchema", "gDay");
/* 132 */   public static final QName QNAME_TYPE_G_MONTH_DAY = new QName("http://www.w3.org/2001/XMLSchema", "gMonthDay");
/* 133 */   public static final QName QNAME_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "Name");
/* 134 */   public static final QName QNAME_TYPE_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "QName");
/* 135 */   public static final QName QNAME_TYPE_NCNAME = new QName("http://www.w3.org/2001/XMLSchema", "NCName");
/* 136 */   public static final QName QNAME_TYPE_ANY_URI = new QName("http://www.w3.org/2001/XMLSchema", "anyURI");
/* 137 */   public static final QName QNAME_TYPE_ID = new QName("http://www.w3.org/2001/XMLSchema", "ID");
/* 138 */   public static final QName QNAME_TYPE_IDREF = new QName("http://www.w3.org/2001/XMLSchema", "IDREF");
/* 139 */   public static final QName QNAME_TYPE_IDREFS = new QName("http://www.w3.org/2001/XMLSchema", "IDREFS");
/* 140 */   public static final QName QNAME_TYPE_ENTITY = new QName("http://www.w3.org/2001/XMLSchema", "ENTITY");
/* 141 */   public static final QName QNAME_TYPE_ENTITIES = new QName("http://www.w3.org/2001/XMLSchema", "ENTITIES");
/* 142 */   public static final QName QNAME_TYPE_NOTATION = new QName("http://www.w3.org/2001/XMLSchema", "NOTATION");
/* 143 */   public static final QName QNAME_TYPE_NMTOKEN = new QName("http://www.w3.org/2001/XMLSchema", "NMTOKEN");
/* 144 */   public static final QName QNAME_TYPE_NMTOKENS = new QName("http://www.w3.org/2001/XMLSchema", "NMTOKENS");
/*     */   
/* 146 */   public static final QName QNAME_TYPE_LANGUAGE = new QName("http://www.w3.org/2001/XMLSchema", "language");
/*     */ 
/*     */   
/* 149 */   public static final QName QNAME_TYPE_URTYPE = new QName("http://www.w3.org/2001/XMLSchema", "anyType");
/* 150 */   public static final QName QNAME_TYPE_SIMPLE_URTYPE = new QName("http://www.w3.org/2001/XMLSchema", "anySimpleType");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\schema\SchemaConstants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */