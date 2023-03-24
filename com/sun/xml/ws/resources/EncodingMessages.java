/*     */ package com.sun.xml.ws.resources;
/*     */ 
/*     */ import com.sun.istack.localization.Localizable;
/*     */ import com.sun.istack.localization.LocalizableMessageFactory;
/*     */ import com.sun.istack.localization.Localizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EncodingMessages
/*     */ {
/*  54 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.ws.resources.encoding");
/*  55 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableFAILED_TO_READ_RESPONSE(Object arg0) {
/*  58 */     return messageFactory.getMessage("failed.to.read.response", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAILED_TO_READ_RESPONSE(Object arg0) {
/*  66 */     return localizer.localize(localizableFAILED_TO_READ_RESPONSE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableEXCEPTION_INCORRECT_TYPE(Object arg0) {
/*  70 */     return messageFactory.getMessage("exception.incorrectType", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String EXCEPTION_INCORRECT_TYPE(Object arg0) {
/*  78 */     return localizer.localize(localizableEXCEPTION_INCORRECT_TYPE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableEXCEPTION_NOTFOUND(Object arg0) {
/*  82 */     return messageFactory.getMessage("exception.notfound", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String EXCEPTION_NOTFOUND(Object arg0) {
/*  90 */     return localizer.localize(localizableEXCEPTION_NOTFOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXSD_UNEXPECTED_ELEMENT_NAME(Object arg0, Object arg1) {
/*  94 */     return messageFactory.getMessage("xsd.unexpectedElementName", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XSD_UNEXPECTED_ELEMENT_NAME(Object arg0, Object arg1) {
/* 102 */     return localizer.localize(localizableXSD_UNEXPECTED_ELEMENT_NAME(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNESTED_DESERIALIZATION_ERROR(Object arg0) {
/* 106 */     return messageFactory.getMessage("nestedDeserializationError", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NESTED_DESERIALIZATION_ERROR(Object arg0) {
/* 114 */     return localizer.localize(localizableNESTED_DESERIALIZATION_ERROR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNESTED_ENCODING_ERROR(Object arg0) {
/* 118 */     return messageFactory.getMessage("nestedEncodingError", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NESTED_ENCODING_ERROR(Object arg0) {
/* 126 */     return localizer.localize(localizableNESTED_ENCODING_ERROR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXSD_UNKNOWN_PREFIX(Object arg0) {
/* 130 */     return messageFactory.getMessage("xsd.unknownPrefix", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XSD_UNKNOWN_PREFIX(Object arg0) {
/* 138 */     return localizer.localize(localizableXSD_UNKNOWN_PREFIX(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNESTED_SERIALIZATION_ERROR(Object arg0) {
/* 142 */     return messageFactory.getMessage("nestedSerializationError", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NESTED_SERIALIZATION_ERROR(Object arg0) {
/* 150 */     return localizer.localize(localizableNESTED_SERIALIZATION_ERROR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNO_SUCH_CONTENT_ID(Object arg0) {
/* 154 */     return messageFactory.getMessage("noSuchContentId", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NO_SUCH_CONTENT_ID(Object arg0) {
/* 162 */     return localizer.localize(localizableNO_SUCH_CONTENT_ID(arg0));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\resources\EncodingMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */