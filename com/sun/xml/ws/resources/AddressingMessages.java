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
/*     */ public final class AddressingMessages
/*     */ {
/*  54 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.ws.resources.addressing");
/*  55 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableNON_ANONYMOUS_RESPONSE_ONEWAY() {
/*  58 */     return messageFactory.getMessage("nonAnonymous.response.oneway", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_ANONYMOUS_RESPONSE_ONEWAY() {
/*  66 */     return localizer.localize(localizableNON_ANONYMOUS_RESPONSE_ONEWAY());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_WSA_HEADERS() {
/*  70 */     return messageFactory.getMessage("null.wsa.headers", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_WSA_HEADERS() {
/*  78 */     return localizer.localize(localizableNULL_WSA_HEADERS());
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNKNOWN_WSA_HEADER() {
/*  82 */     return messageFactory.getMessage("unknown.wsa.header", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNKNOWN_WSA_HEADER() {
/*  90 */     return localizer.localize(localizableUNKNOWN_WSA_HEADER());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_ACTION() {
/*  94 */     return messageFactory.getMessage("null.action", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_ACTION() {
/* 102 */     return localizer.localize(localizableNULL_ACTION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_WSAW_ANONYMOUS(Object arg0) {
/* 106 */     return messageFactory.getMessage("invalid.wsaw.anonymous", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_WSAW_ANONYMOUS(Object arg0) {
/* 114 */     return localizer.localize(localizableINVALID_WSAW_ANONYMOUS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_SOAP_VERSION() {
/* 118 */     return messageFactory.getMessage("null.soap.version", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_SOAP_VERSION() {
/* 126 */     return localizer.localize(localizableNULL_SOAP_VERSION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSDL_BOUND_OPERATION_NOT_FOUND(Object arg0) {
/* 130 */     return messageFactory.getMessage("wsdlBoundOperation.notFound", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSDL_BOUND_OPERATION_NOT_FOUND(Object arg0) {
/* 138 */     return localizer.localize(localizableWSDL_BOUND_OPERATION_NOT_FOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_UNIQUE_OPERATION_SIGNATURE(Object arg0, Object arg1, Object arg2, Object arg3) {
/* 142 */     return messageFactory.getMessage("non.unique.operation.signature", new Object[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_UNIQUE_OPERATION_SIGNATURE(Object arg0, Object arg1, Object arg2, Object arg3) {
/* 150 */     return localizer.localize(localizableNON_UNIQUE_OPERATION_SIGNATURE(arg0, arg1, arg2, arg3));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_ANONYMOUS_RESPONSE() {
/* 154 */     return messageFactory.getMessage("nonAnonymous.response", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_ANONYMOUS_RESPONSE() {
/* 162 */     return localizer.localize(localizableNON_ANONYMOUS_RESPONSE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableVALIDATION_SERVER_NULL_ACTION() {
/* 166 */     return messageFactory.getMessage("validation.server.nullAction", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String VALIDATION_SERVER_NULL_ACTION() {
/* 174 */     return localizer.localize(localizableVALIDATION_SERVER_NULL_ACTION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableFAULT_TO_CANNOT_PARSE() {
/* 178 */     return messageFactory.getMessage("faultTo.cannot.parse", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAULT_TO_CANNOT_PARSE() {
/* 186 */     return localizer.localize(localizableFAULT_TO_CANNOT_PARSE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableVALIDATION_CLIENT_NULL_ACTION() {
/* 190 */     return messageFactory.getMessage("validation.client.nullAction", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String VALIDATION_CLIENT_NULL_ACTION() {
/* 198 */     return localizer.localize(localizableVALIDATION_CLIENT_NULL_ACTION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_MESSAGE() {
/* 202 */     return messageFactory.getMessage("null.message", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_MESSAGE() {
/* 210 */     return localizer.localize(localizableNULL_MESSAGE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableACTION_NOT_SUPPORTED_EXCEPTION(Object arg0) {
/* 214 */     return messageFactory.getMessage("action.not.supported.exception", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ACTION_NOT_SUPPORTED_EXCEPTION(Object arg0) {
/* 222 */     return localizer.localize(localizableACTION_NOT_SUPPORTED_EXCEPTION(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_ANONYMOUS_RESPONSE_NULL_HEADERS(Object arg0) {
/* 226 */     return messageFactory.getMessage("nonAnonymous.response.nullHeaders", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_ANONYMOUS_RESPONSE_NULL_HEADERS(Object arg0) {
/* 234 */     return localizer.localize(localizableNON_ANONYMOUS_RESPONSE_NULL_HEADERS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_ANONYMOUS_RESPONSE_SENDING(Object arg0) {
/* 238 */     return messageFactory.getMessage("nonAnonymous.response.sending", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_ANONYMOUS_RESPONSE_SENDING(Object arg0) {
/* 246 */     return localizer.localize(localizableNON_ANONYMOUS_RESPONSE_SENDING(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableREPLY_TO_CANNOT_PARSE() {
/* 250 */     return messageFactory.getMessage("replyTo.cannot.parse", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String REPLY_TO_CANNOT_PARSE() {
/* 258 */     return localizer.localize(localizableREPLY_TO_CANNOT_PARSE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_ADDRESSING_HEADER_EXCEPTION(Object arg0, Object arg1) {
/* 262 */     return messageFactory.getMessage("invalid.addressing.header.exception", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_ADDRESSING_HEADER_EXCEPTION(Object arg0, Object arg1) {
/* 270 */     return localizer.localize(localizableINVALID_ADDRESSING_HEADER_EXCEPTION(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSAW_ANONYMOUS_PROHIBITED() {
/* 274 */     return messageFactory.getMessage("wsaw.anonymousProhibited", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSAW_ANONYMOUS_PROHIBITED() {
/* 282 */     return localizer.localize(localizableWSAW_ANONYMOUS_PROHIBITED());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_WSDL_PORT() {
/* 286 */     return messageFactory.getMessage("null.wsdlPort", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_WSDL_PORT() {
/* 294 */     return localizer.localize(localizableNULL_WSDL_PORT());
/*     */   }
/*     */   
/*     */   public static Localizable localizableADDRESSING_SHOULD_BE_ENABLED() {
/* 298 */     return messageFactory.getMessage("addressing.should.be.enabled.", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ADDRESSING_SHOULD_BE_ENABLED() {
/* 306 */     return localizer.localize(localizableADDRESSING_SHOULD_BE_ENABLED());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_ADDRESSING_VERSION() {
/* 310 */     return messageFactory.getMessage("null.addressing.version", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_ADDRESSING_VERSION() {
/* 318 */     return localizer.localize(localizableNULL_ADDRESSING_VERSION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableMISSING_HEADER_EXCEPTION(Object arg0) {
/* 322 */     return messageFactory.getMessage("missing.header.exception", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String MISSING_HEADER_EXCEPTION(Object arg0) {
/* 330 */     return localizer.localize(localizableMISSING_HEADER_EXCEPTION(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_PACKET() {
/* 334 */     return messageFactory.getMessage("null.packet", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_PACKET() {
/* 342 */     return localizer.localize(localizableNULL_PACKET());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWRONG_ADDRESSING_VERSION(Object arg0, Object arg1) {
/* 346 */     return messageFactory.getMessage("wrong.addressing.version", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WRONG_ADDRESSING_VERSION(Object arg0, Object arg1) {
/* 354 */     return localizer.localize(localizableWRONG_ADDRESSING_VERSION(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableADDRESSING_NOT_ENABLED(Object arg0) {
/* 358 */     return messageFactory.getMessage("addressing.notEnabled", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ADDRESSING_NOT_ENABLED(Object arg0) {
/* 366 */     return localizer.localize(localizableADDRESSING_NOT_ENABLED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_ANONYMOUS_UNKNOWN_PROTOCOL(Object arg0) {
/* 370 */     return messageFactory.getMessage("nonAnonymous.unknown.protocol", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_ANONYMOUS_UNKNOWN_PROTOCOL(Object arg0) {
/* 378 */     return localizer.localize(localizableNON_ANONYMOUS_UNKNOWN_PROTOCOL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_HEADERS() {
/* 382 */     return messageFactory.getMessage("null.headers", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_HEADERS() {
/* 390 */     return localizer.localize(localizableNULL_HEADERS());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_BINDING() {
/* 394 */     return messageFactory.getMessage("null.binding", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_BINDING() {
/* 402 */     return localizer.localize(localizableNULL_BINDING());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\resources\AddressingMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */