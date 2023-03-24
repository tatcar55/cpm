/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ResourceBundle;
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
/*     */ enum Messages
/*     */ {
/*  51 */   ID_MUST_BE_STRING,
/*     */   
/*  53 */   MUTUALLY_EXCLUSIVE_ANNOTATIONS,
/*  54 */   DUPLICATE_ANNOTATIONS,
/*  55 */   NO_DEFAULT_CONSTRUCTOR,
/*  56 */   CANT_HANDLE_INTERFACE,
/*  57 */   CANT_HANDLE_INNER_CLASS,
/*  58 */   ANNOTATION_ON_WRONG_METHOD,
/*  59 */   GETTER_SETTER_INCOMPATIBLE_TYPE,
/*  60 */   DUPLICATE_ENTRY_IN_PROP_ORDER,
/*  61 */   DUPLICATE_PROPERTIES,
/*     */   
/*  63 */   XML_ELEMENT_MAPPING_ON_NON_IXMLELEMENT_METHOD,
/*  64 */   SCOPE_IS_NOT_COMPLEXTYPE,
/*  65 */   CONFLICTING_XML_ELEMENT_MAPPING,
/*     */   
/*  67 */   REFERENCE_TO_NON_ELEMENT,
/*     */   
/*  69 */   NON_EXISTENT_ELEMENT_MAPPING,
/*     */   
/*  71 */   TWO_ATTRIBUTE_WILDCARDS,
/*  72 */   SUPER_CLASS_HAS_WILDCARD,
/*  73 */   INVALID_ATTRIBUTE_WILDCARD_TYPE,
/*  74 */   PROPERTY_MISSING_FROM_ORDER,
/*  75 */   PROPERTY_ORDER_CONTAINS_UNUSED_ENTRY,
/*     */   
/*  77 */   INVALID_XML_ENUM_VALUE,
/*  78 */   FAILED_TO_INITIALE_DATATYPE_FACTORY,
/*  79 */   NO_IMAGE_WRITER,
/*     */   
/*  81 */   ILLEGAL_MIME_TYPE,
/*  82 */   ILLEGAL_ANNOTATION,
/*     */   
/*  84 */   MULTIPLE_VALUE_PROPERTY,
/*  85 */   ELEMENT_AND_VALUE_PROPERTY,
/*  86 */   CONFLICTING_XML_TYPE_MAPPING,
/*  87 */   XMLVALUE_IN_DERIVED_TYPE,
/*  88 */   SIMPLE_TYPE_IS_REQUIRED,
/*  89 */   PROPERTY_COLLISION,
/*  90 */   INVALID_IDREF,
/*  91 */   INVALID_XML_ELEMENT_REF,
/*  92 */   NO_XML_ELEMENT_DECL,
/*  93 */   XML_ELEMENT_WRAPPER_ON_NON_COLLECTION,
/*     */   
/*  95 */   ANNOTATION_NOT_ALLOWED,
/*  96 */   XMLLIST_NEEDS_SIMPLETYPE,
/*  97 */   XMLLIST_ON_SINGLE_PROPERTY,
/*  98 */   NO_FACTORY_METHOD,
/*  99 */   FACTORY_CLASS_NEEDS_FACTORY_METHOD,
/*     */   
/* 101 */   INCOMPATIBLE_API_VERSION,
/* 102 */   INCOMPATIBLE_API_VERSION_MUSTANG,
/* 103 */   RUNNING_WITH_1_0_RUNTIME,
/*     */   
/* 105 */   MISSING_JAXB_PROPERTIES,
/* 106 */   TRANSIENT_FIELD_NOT_BINDABLE,
/* 107 */   THERE_MUST_BE_VALUE_IN_XMLVALUE,
/* 108 */   UNMATCHABLE_ADAPTER,
/* 109 */   ANONYMOUS_ARRAY_ITEM,
/*     */   
/* 111 */   ACCESSORFACTORY_INSTANTIATION_EXCEPTION,
/* 112 */   ACCESSORFACTORY_ACCESS_EXCEPTION,
/* 113 */   CUSTOM_ACCESSORFACTORY_PROPERTY_ERROR,
/* 114 */   CUSTOM_ACCESSORFACTORY_FIELD_ERROR,
/* 115 */   XMLGREGORIANCALENDAR_INVALID,
/* 116 */   XMLGREGORIANCALENDAR_SEC,
/* 117 */   XMLGREGORIANCALENDAR_MIN,
/* 118 */   XMLGREGORIANCALENDAR_HR,
/* 119 */   XMLGREGORIANCALENDAR_DAY,
/* 120 */   XMLGREGORIANCALENDAR_MONTH,
/* 121 */   XMLGREGORIANCALENDAR_YEAR,
/* 122 */   XMLGREGORIANCALENDAR_TIMEZONE;
/*     */   
/*     */   static {
/* 125 */     rb = ResourceBundle.getBundle(Messages.class.getName());
/*     */   }
/*     */   private static final ResourceBundle rb;
/*     */   public String toString() {
/* 129 */     return format(new Object[0]);
/*     */   }
/*     */   
/*     */   public String format(Object... args) {
/* 133 */     return MessageFormat.format(rb.getString(name()), args);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\Messages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */