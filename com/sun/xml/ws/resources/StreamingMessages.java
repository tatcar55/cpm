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
/*     */ public final class StreamingMessages
/*     */ {
/*  54 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.ws.resources.streaming");
/*  55 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableFASTINFOSET_DECODING_NOT_ACCEPTED() {
/*  58 */     return messageFactory.getMessage("fastinfoset.decodingNotAccepted", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FASTINFOSET_DECODING_NOT_ACCEPTED() {
/*  66 */     return localizer.localize(localizableFASTINFOSET_DECODING_NOT_ACCEPTED());
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTAX_CANT_CREATE() {
/*  70 */     return messageFactory.getMessage("stax.cantCreate", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STAX_CANT_CREATE() {
/*  78 */     return localizer.localize(localizableSTAX_CANT_CREATE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTREAMING_IO_EXCEPTION(Object arg0) {
/*  82 */     return messageFactory.getMessage("streaming.ioException", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STREAMING_IO_EXCEPTION(Object arg0) {
/*  90 */     return localizer.localize(localizableSTREAMING_IO_EXCEPTION(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOURCEREADER_INVALID_SOURCE(Object arg0) {
/*  94 */     return messageFactory.getMessage("sourcereader.invalidSource", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOURCEREADER_INVALID_SOURCE(Object arg0) {
/* 102 */     return localizer.localize(localizableSOURCEREADER_INVALID_SOURCE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLREADER_UNEXPECTED_STATE(Object arg0, Object arg1) {
/* 106 */     return messageFactory.getMessage("xmlreader.unexpectedState", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLREADER_UNEXPECTED_STATE(Object arg0, Object arg1) {
/* 114 */     return localizer.localize(localizableXMLREADER_UNEXPECTED_STATE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLREADER_IO_EXCEPTION(Object arg0) {
/* 118 */     return messageFactory.getMessage("xmlreader.ioException", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLREADER_IO_EXCEPTION(Object arg0) {
/* 126 */     return localizer.localize(localizableXMLREADER_IO_EXCEPTION(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableFASTINFOSET_NO_IMPLEMENTATION() {
/* 130 */     return messageFactory.getMessage("fastinfoset.noImplementation", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FASTINFOSET_NO_IMPLEMENTATION() {
/* 138 */     return localizer.localize(localizableFASTINFOSET_NO_IMPLEMENTATION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLWRITER_IO_EXCEPTION(Object arg0) {
/* 142 */     return messageFactory.getMessage("xmlwriter.ioException", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLWRITER_IO_EXCEPTION(Object arg0) {
/* 150 */     return localizer.localize(localizableXMLWRITER_IO_EXCEPTION(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLREADER_UNEXPECTED_CHARACTER_CONTENT(Object arg0) {
/* 154 */     return messageFactory.getMessage("xmlreader.unexpectedCharacterContent", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLREADER_UNEXPECTED_CHARACTER_CONTENT(Object arg0) {
/* 162 */     return localizer.localize(localizableXMLREADER_UNEXPECTED_CHARACTER_CONTENT(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTREAMING_PARSE_EXCEPTION(Object arg0) {
/* 166 */     return messageFactory.getMessage("streaming.parseException", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STREAMING_PARSE_EXCEPTION(Object arg0) {
/* 174 */     return localizer.localize(localizableSTREAMING_PARSE_EXCEPTION(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLWRITER_NO_PREFIX_FOR_URI(Object arg0) {
/* 178 */     return messageFactory.getMessage("xmlwriter.noPrefixForURI", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLWRITER_NO_PREFIX_FOR_URI(Object arg0) {
/* 186 */     return localizer.localize(localizableXMLWRITER_NO_PREFIX_FOR_URI(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLREADER_NESTED_ERROR(Object arg0) {
/* 190 */     return messageFactory.getMessage("xmlreader.nestedError", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLREADER_NESTED_ERROR(Object arg0) {
/* 198 */     return localizer.localize(localizableXMLREADER_NESTED_ERROR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTAXREADER_XMLSTREAMEXCEPTION(Object arg0) {
/* 202 */     return messageFactory.getMessage("staxreader.xmlstreamexception", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STAXREADER_XMLSTREAMEXCEPTION(Object arg0) {
/* 210 */     return localizer.localize(localizableSTAXREADER_XMLSTREAMEXCEPTION(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLWRITER_NESTED_ERROR(Object arg0) {
/* 214 */     return messageFactory.getMessage("xmlwriter.nestedError", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLWRITER_NESTED_ERROR(Object arg0) {
/* 222 */     return localizer.localize(localizableXMLWRITER_NESTED_ERROR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLREADER_ILLEGAL_STATE_ENCOUNTERED(Object arg0) {
/* 226 */     return messageFactory.getMessage("xmlreader.illegalStateEncountered", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLREADER_ILLEGAL_STATE_ENCOUNTERED(Object arg0) {
/* 234 */     return localizer.localize(localizableXMLREADER_ILLEGAL_STATE_ENCOUNTERED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLREADER_UNEXPECTED_STATE_TAG(Object arg0, Object arg1) {
/* 238 */     return messageFactory.getMessage("xmlreader.unexpectedState.tag", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLREADER_UNEXPECTED_STATE_TAG(Object arg0, Object arg1) {
/* 246 */     return localizer.localize(localizableXMLREADER_UNEXPECTED_STATE_TAG(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLREADER_UNEXPECTED_STATE_MESSAGE(Object arg0, Object arg1, Object arg2) {
/* 250 */     return messageFactory.getMessage("xmlreader.unexpectedState.message", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLREADER_UNEXPECTED_STATE_MESSAGE(Object arg0, Object arg1, Object arg2) {
/* 258 */     return localizer.localize(localizableXMLREADER_UNEXPECTED_STATE_MESSAGE(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLREADER_PARSE_EXCEPTION(Object arg0) {
/* 262 */     return messageFactory.getMessage("xmlreader.parseException", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLREADER_PARSE_EXCEPTION(Object arg0) {
/* 270 */     return localizer.localize(localizableXMLREADER_PARSE_EXCEPTION(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXMLRECORDER_RECORDING_ENDED() {
/* 274 */     return messageFactory.getMessage("xmlrecorder.recording.ended", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XMLRECORDER_RECORDING_ENDED() {
/* 282 */     return localizer.localize(localizableXMLRECORDER_RECORDING_ENDED());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\resources\StreamingMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */