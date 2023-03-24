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
/*     */ public final class ServerMessages
/*     */ {
/*  54 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.ws.resources.server");
/*  55 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICE(Object arg0, Object arg1) {
/*  58 */     return messageFactory.getMessage("runtime.parser.wsdl.incorrectservice", new Object[] { arg0, arg1 });
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
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_WSDL_INCORRECTSERVICE(Object arg0, Object arg1) {
/*  75 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_MISSING_ATTRIBUTE_NO_LINE() {
/*  79 */     return messageFactory.getMessage("runtime.parser.missing.attribute.no.line", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_MISSING_ATTRIBUTE_NO_LINE() {
/*  87 */     return localizer.localize(localizableRUNTIME_PARSER_MISSING_ATTRIBUTE_NO_LINE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTATEFUL_COOKIE_HEADER_INCORRECT(Object arg0, Object arg1) {
/*  91 */     return messageFactory.getMessage("stateful.cookie.header.incorrect", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STATEFUL_COOKIE_HEADER_INCORRECT(Object arg0, Object arg1) {
/*  99 */     return localizer.localize(localizableSTATEFUL_COOKIE_HEADER_INCORRECT(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNOT_IMPLEMENT_PROVIDER(Object arg0) {
/* 103 */     return messageFactory.getMessage("not.implement.provider", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NOT_IMPLEMENT_PROVIDER(Object arg0) {
/* 111 */     return localizer.localize(localizableNOT_IMPLEMENT_PROVIDER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTATEFUL_REQURES_ADDRESSING(Object arg0) {
/* 115 */     return messageFactory.getMessage("stateful.requres.addressing", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STATEFUL_REQURES_ADDRESSING(Object arg0) {
/* 123 */     return localizer.localize(localizableSTATEFUL_REQURES_ADDRESSING(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAPDECODER_ERR() {
/* 127 */     return messageFactory.getMessage("soapdecoder.err", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAPDECODER_ERR() {
/* 135 */     return localizer.localize(localizableSOAPDECODER_ERR());
/*     */   }
/*     */   
/*     */   public static Localizable localizableGENERATE_NON_STANDARD_WSDL() {
/* 139 */     return messageFactory.getMessage("generate.non.standard.wsdl", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String GENERATE_NON_STANDARD_WSDL() {
/* 147 */     return localizer.localize(localizableGENERATE_NON_STANDARD_WSDL());
/*     */   }
/*     */   
/*     */   public static Localizable localizableDISPATCH_CANNOT_FIND_METHOD(Object arg0) {
/* 151 */     return messageFactory.getMessage("dispatch.cannotFindMethod", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String DISPATCH_CANNOT_FIND_METHOD(Object arg0) {
/* 159 */     return localizer.localize(localizableDISPATCH_CANNOT_FIND_METHOD(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNO_CONTENT_TYPE() {
/* 163 */     return messageFactory.getMessage("no.contentType", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NO_CONTENT_TYPE() {
/* 171 */     return localizer.localize(localizableNO_CONTENT_TYPE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_INVALID_VERSION_NUMBER() {
/* 175 */     return messageFactory.getMessage("runtime.parser.invalidVersionNumber", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_INVALID_VERSION_NUMBER() {
/* 183 */     return localizer.localize(localizableRUNTIME_PARSER_INVALID_VERSION_NUMBER());
/*     */   }
/*     */   
/*     */   public static Localizable localizablePROVIDER_INVALID_PARAMETER_TYPE(Object arg0, Object arg1) {
/* 187 */     return messageFactory.getMessage("provider.invalid.parameterType", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String PROVIDER_INVALID_PARAMETER_TYPE(Object arg0, Object arg1) {
/* 195 */     return localizer.localize(localizablePROVIDER_INVALID_PARAMETER_TYPE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWRONG_NO_PARAMETERS(Object arg0) {
/* 199 */     return messageFactory.getMessage("wrong.no.parameters", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WRONG_NO_PARAMETERS(Object arg0) {
/* 207 */     return localizer.localize(localizableWRONG_NO_PARAMETERS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableANNOTATION_ONLY_ONCE(Object arg0) {
/* 211 */     return messageFactory.getMessage("annotation.only.once", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ANNOTATION_ONLY_ONCE(Object arg0) {
/* 219 */     return localizer.localize(localizableANNOTATION_ONLY_ONCE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableALREADY_HTTPS_SERVER(Object arg0) {
/* 223 */     return messageFactory.getMessage("already.https.server", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ALREADY_HTTPS_SERVER(Object arg0) {
/* 231 */     return localizer.localize(localizableALREADY_HTTPS_SERVER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_XML_READER(Object arg0) {
/* 235 */     return messageFactory.getMessage("runtime.parser.xmlReader", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_XML_READER(Object arg0) {
/* 243 */     return localizer.localize(localizableRUNTIME_PARSER_XML_READER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICEPORT(Object arg0, Object arg1, Object arg2) {
/* 247 */     return messageFactory.getMessage("runtime.parser.wsdl.incorrectserviceport", new Object[] { arg0, arg1, arg2 });
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
/*     */   public static String RUNTIME_PARSER_WSDL_INCORRECTSERVICEPORT(Object arg0, Object arg1, Object arg2) {
/* 262 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICEPORT(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSERVER_RT_ERR(Object arg0) {
/* 266 */     return messageFactory.getMessage("server.rt.err", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SERVER_RT_ERR(Object arg0) {
/* 274 */     return localizer.localize(localizableSERVER_RT_ERR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(Object arg0, Object arg1, Object arg2) {
/* 278 */     return messageFactory.getMessage("runtime.parser.invalidAttributeValue", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(Object arg0, Object arg1, Object arg2) {
/* 286 */     return localizer.localize(localizableRUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNO_CURRENT_PACKET() {
/* 290 */     return messageFactory.getMessage("no.current.packet", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NO_CURRENT_PACKET() {
/* 298 */     return localizer.localize(localizableNO_CURRENT_PACKET());
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_UNEXPECTED_CONTENT(Object arg0) {
/* 302 */     return messageFactory.getMessage("runtime.parser.unexpectedContent", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_UNEXPECTED_CONTENT(Object arg0) {
/* 310 */     return localizer.localize(localizableRUNTIME_PARSER_UNEXPECTED_CONTENT(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTATEFUL_COOKIE_HEADER_REQUIRED(Object arg0) {
/* 314 */     return messageFactory.getMessage("stateful.cookie.header.required", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STATEFUL_COOKIE_HEADER_REQUIRED(Object arg0) {
/* 322 */     return localizer.localize(localizableSTATEFUL_COOKIE_HEADER_REQUIRED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_IMPLEMENTOR() {
/* 326 */     return messageFactory.getMessage("null.implementor", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_IMPLEMENTOR() {
/* 334 */     return localizer.localize(localizableNULL_IMPLEMENTOR());
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL(Object arg0) {
/* 338 */     return messageFactory.getMessage("runtime.parser.wsdl", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_WSDL(Object arg0) {
/* 346 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAPENCODER_ERR() {
/* 350 */     return messageFactory.getMessage("soapencoder.err", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAPENCODER_ERR() {
/* 358 */     return localizer.localize(localizableSOAPENCODER_ERR());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSDL_REQUIRED() {
/* 362 */     return messageFactory.getMessage("wsdl.required", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSDL_REQUIRED() {
/* 370 */     return localizer.localize(localizableWSDL_REQUIRED());
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL_NOSERVICE_IN_WSDLMODEL(Object arg0) {
/* 374 */     return messageFactory.getMessage("runtime.parser.wsdl.noservice.in.wsdlmodel", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_WSDL_NOSERVICE_IN_WSDLMODEL(Object arg0) {
/* 382 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL_NOSERVICE_IN_WSDLMODEL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizablePORT_NAME_REQUIRED() {
/* 386 */     return messageFactory.getMessage("port.name.required", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String PORT_NAME_REQUIRED() {
/* 394 */     return localizer.localize(localizablePORT_NAME_REQUIRED());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWRONG_TNS_FOR_PORT(Object arg0) {
/* 398 */     return messageFactory.getMessage("wrong.tns.for.port", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WRONG_TNS_FOR_PORT(Object arg0) {
/* 406 */     return localizer.localize(localizableWRONG_TNS_FOR_PORT(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL_MULTIPLEBINDING(Object arg0, Object arg1, Object arg2) {
/* 410 */     return messageFactory.getMessage("runtime.parser.wsdl.multiplebinding", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_WSDL_MULTIPLEBINDING(Object arg0, Object arg1, Object arg2) {
/* 418 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL_MULTIPLEBINDING(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNOT_KNOW_HTTP_CONTEXT_TYPE(Object arg0, Object arg1, Object arg2) {
/* 422 */     return messageFactory.getMessage("not.know.HttpContext.type", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NOT_KNOW_HTTP_CONTEXT_TYPE(Object arg0, Object arg1, Object arg2) {
/* 430 */     return localizer.localize(localizableNOT_KNOW_HTTP_CONTEXT_TYPE(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_UNIQUE_DISPATCH_QNAME(Object arg0, Object arg1) {
/* 434 */     return messageFactory.getMessage("non.unique.dispatch.qname", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_UNIQUE_DISPATCH_QNAME(Object arg0, Object arg1) {
/* 442 */     return localizer.localize(localizableNON_UNIQUE_DISPATCH_QNAME(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableALREADY_HTTP_SERVER(Object arg0) {
/* 446 */     return messageFactory.getMessage("already.http.server", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ALREADY_HTTP_SERVER(Object arg0) {
/* 454 */     return localizer.localize(localizableALREADY_HTTP_SERVER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableCAN_NOT_GENERATE_WSDL(Object arg0) {
/* 458 */     return messageFactory.getMessage("can.not.generate.wsdl", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String CAN_NOT_GENERATE_WSDL(Object arg0) {
/* 466 */     return localizer.localize(localizableCAN_NOT_GENERATE_WSDL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(Object arg0, Object arg1) {
/* 470 */     return messageFactory.getMessage("runtime.parser.invalid.attribute.value", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(Object arg0, Object arg1) {
/* 478 */     return localizer.localize(localizableRUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WRONG_ELEMENT(Object arg0, Object arg1, Object arg2) {
/* 482 */     return messageFactory.getMessage("runtime.parser.wrong.element", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_WRONG_ELEMENT(Object arg0, Object arg1, Object arg2) {
/* 490 */     return localizer.localize(localizableRUNTIME_PARSER_WRONG_ELEMENT(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIMEMODELER_INVALIDANNOTATION_ON_IMPL(Object arg0, Object arg1, Object arg2) {
/* 494 */     return messageFactory.getMessage("runtimemodeler.invalidannotationOnImpl", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIMEMODELER_INVALIDANNOTATION_ON_IMPL(Object arg0, Object arg1, Object arg2) {
/* 502 */     return localizer.localize(localizableRUNTIMEMODELER_INVALIDANNOTATION_ON_IMPL(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSERVICE_NAME_REQUIRED() {
/* 506 */     return messageFactory.getMessage("service.name.required", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SERVICE_NAME_REQUIRED() {
/* 514 */     return localizer.localize(localizableSERVICE_NAME_REQUIRED());
/*     */   }
/*     */   
/*     */   public static Localizable localizablePROVIDER_NOT_PARAMETERIZED(Object arg0) {
/* 518 */     return messageFactory.getMessage("provider.not.parameterized", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String PROVIDER_NOT_PARAMETERIZED(Object arg0) {
/* 526 */     return localizer.localize(localizablePROVIDER_NOT_PARAMETERIZED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_WSDL_PATCHER() {
/* 530 */     return messageFactory.getMessage("runtime.wsdl.patcher", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_WSDL_PATCHER() {
/* 538 */     return localizer.localize(localizableRUNTIME_WSDL_PATCHER());
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_SAXPARSER_EXCEPTION(Object arg0, Object arg1) {
/* 542 */     return messageFactory.getMessage("runtime.saxparser.exception", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_SAXPARSER_EXCEPTION(Object arg0, Object arg1) {
/* 551 */     return localizer.localize(localizableRUNTIME_SAXPARSER_EXCEPTION(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWRONG_PARAMETER_TYPE(Object arg0) {
/* 555 */     return messageFactory.getMessage("wrong.parameter.type", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WRONG_PARAMETER_TYPE(Object arg0) {
/* 563 */     return localizer.localize(localizableWRONG_PARAMETER_TYPE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL_NOT_FOUND(Object arg0) {
/* 567 */     return messageFactory.getMessage("runtime.parser.wsdl.not.found", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_WSDL_NOT_FOUND(Object arg0) {
/* 575 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL_NOT_FOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_CLASS_NOT_FOUND(Object arg0) {
/* 579 */     return messageFactory.getMessage("runtime.parser.classNotFound", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_CLASS_NOT_FOUND(Object arg0) {
/* 587 */     return localizer.localize(localizableRUNTIME_PARSER_CLASS_NOT_FOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNSUPPORTED_CHARSET(Object arg0) {
/* 591 */     return messageFactory.getMessage("unsupported.charset", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNSUPPORTED_CHARSET(Object arg0) {
/* 599 */     return localizer.localize(localizableUNSUPPORTED_CHARSET(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTATIC_RESOURCE_INJECTION_ONLY(Object arg0, Object arg1) {
/* 603 */     return messageFactory.getMessage("static.resource.injection.only", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STATIC_RESOURCE_INJECTION_ONLY(Object arg0, Object arg1) {
/* 611 */     return localizer.localize(localizableSTATIC_RESOURCE_INJECTION_ONLY(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNOT_ZERO_PARAMETERS(Object arg0) {
/* 615 */     return messageFactory.getMessage("not.zero.parameters", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NOT_ZERO_PARAMETERS(Object arg0) {
/* 623 */     return localizer.localize(localizableNOT_ZERO_PARAMETERS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableDUPLICATE_PRIMARY_WSDL(Object arg0) {
/* 627 */     return messageFactory.getMessage("duplicate.primary.wsdl", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String DUPLICATE_PRIMARY_WSDL(Object arg0) {
/* 635 */     return localizer.localize(localizableDUPLICATE_PRIMARY_WSDL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableDUPLICATE_ABSTRACT_WSDL(Object arg0) {
/* 639 */     return messageFactory.getMessage("duplicate.abstract.wsdl", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String DUPLICATE_ABSTRACT_WSDL(Object arg0) {
/* 647 */     return localizer.localize(localizableDUPLICATE_ABSTRACT_WSDL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTATEFUL_INVALID_WEBSERVICE_CONTEXT(Object arg0) {
/* 651 */     return messageFactory.getMessage("stateful.invalid.webservice.context", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STATEFUL_INVALID_WEBSERVICE_CONTEXT(Object arg0) {
/* 659 */     return localizer.localize(localizableSTATEFUL_INVALID_WEBSERVICE_CONTEXT(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_INVALID_ELEMENT(Object arg0, Object arg1) {
/* 663 */     return messageFactory.getMessage("runtime.parser.invalidElement", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_INVALID_ELEMENT(Object arg0, Object arg1) {
/* 671 */     return localizer.localize(localizableRUNTIME_PARSER_INVALID_ELEMENT(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_MISSING_ATTRIBUTE(Object arg0, Object arg1, Object arg2) {
/* 675 */     return messageFactory.getMessage("runtime.parser.missing.attribute", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_MISSING_ATTRIBUTE(Object arg0, Object arg1, Object arg2) {
/* 683 */     return localizer.localize(localizableRUNTIME_PARSER_MISSING_ATTRIBUTE(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWRONG_FIELD_TYPE(Object arg0) {
/* 687 */     return messageFactory.getMessage("wrong.field.type", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WRONG_FIELD_TYPE(Object arg0) {
/* 695 */     return localizer.localize(localizableWRONG_FIELD_TYPE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableDUPLICATE_PORT_KNOWN_HEADER(Object arg0) {
/* 699 */     return messageFactory.getMessage("duplicate.portKnownHeader", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String DUPLICATE_PORT_KNOWN_HEADER(Object arg0) {
/* 707 */     return localizer.localize(localizableDUPLICATE_PORT_KNOWN_HEADER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNSUPPORTED_CONTENT_TYPE(Object arg0, Object arg1) {
/* 711 */     return messageFactory.getMessage("unsupported.contentType", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNSUPPORTED_CONTENT_TYPE(Object arg0, Object arg1) {
/* 719 */     return localizer.localize(localizableUNSUPPORTED_CONTENT_TYPE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableFAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(Object arg0, Object arg1, Object arg2) {
/* 723 */     return messageFactory.getMessage("failed.to.instantiate.instanceResolver", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(Object arg0, Object arg1, Object arg2) {
/* 731 */     return localizer.localize(localizableFAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableDD_MTOM_CONFLICT(Object arg0, Object arg1) {
/* 735 */     return messageFactory.getMessage("dd.mtom.conflict", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String DD_MTOM_CONFLICT(Object arg0, Object arg1) {
/* 743 */     return localizer.localize(localizableDD_MTOM_CONFLICT(arg0, arg1));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\resources\ServerMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */