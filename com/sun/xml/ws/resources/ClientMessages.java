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
/*     */ public final class ClientMessages
/*     */ {
/*  54 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.ws.resources.client");
/*  55 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableFAILED_TO_PARSE(Object arg0, Object arg1) {
/*  58 */     return messageFactory.getMessage("failed.to.parse", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAILED_TO_PARSE(Object arg0, Object arg1) {
/*  67 */     return localizer.localize(localizableFAILED_TO_PARSE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_BINDING_ID(Object arg0, Object arg1) {
/*  71 */     return messageFactory.getMessage("invalid.binding.id", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_BINDING_ID(Object arg0, Object arg1) {
/*  79 */     return localizer.localize(localizableINVALID_BINDING_ID(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableEPR_WITHOUT_ADDRESSING_ON() {
/*  83 */     return messageFactory.getMessage("epr.without.addressing.on", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String EPR_WITHOUT_ADDRESSING_ON() {
/*  91 */     return localizer.localize(localizableEPR_WITHOUT_ADDRESSING_ON());
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_SERVICE_NO_WSDL(Object arg0) {
/*  95 */     return messageFactory.getMessage("invalid.service.no.wsdl", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_SERVICE_NO_WSDL(Object arg0) {
/* 103 */     return localizer.localize(localizableINVALID_SERVICE_NO_WSDL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_SOAP_ROLE_NONE() {
/* 107 */     return messageFactory.getMessage("invalid.soap.role.none", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_SOAP_ROLE_NONE() {
/* 115 */     return localizer.localize(localizableINVALID_SOAP_ROLE_NONE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNDEFINED_BINDING(Object arg0) {
/* 119 */     return messageFactory.getMessage("undefined.binding", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNDEFINED_BINDING(Object arg0) {
/* 127 */     return localizer.localize(localizableUNDEFINED_BINDING(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableHTTP_NOT_FOUND(Object arg0) {
/* 131 */     return messageFactory.getMessage("http.not.found", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String HTTP_NOT_FOUND(Object arg0) {
/* 139 */     return localizer.localize(localizableHTTP_NOT_FOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_EPR_PORT_NAME(Object arg0, Object arg1) {
/* 143 */     return messageFactory.getMessage("invalid.epr.port.name", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_EPR_PORT_NAME(Object arg0, Object arg1) {
/* 151 */     return localizer.localize(localizableINVALID_EPR_PORT_NAME(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableFAILED_TO_PARSE_WITH_MEX(Object arg0, Object arg1, Object arg2) {
/* 155 */     return messageFactory.getMessage("failed.to.parseWithMEX", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAILED_TO_PARSE_WITH_MEX(Object arg0, Object arg1, Object arg2) {
/* 166 */     return localizer.localize(localizableFAILED_TO_PARSE_WITH_MEX(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableHTTP_STATUS_CODE(Object arg0, Object arg1) {
/* 170 */     return messageFactory.getMessage("http.status.code", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String HTTP_STATUS_CODE(Object arg0, Object arg1) {
/* 178 */     return localizer.localize(localizableHTTP_STATUS_CODE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_ADDRESS(Object arg0) {
/* 182 */     return messageFactory.getMessage("invalid.address", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_ADDRESS(Object arg0) {
/* 190 */     return localizer.localize(localizableINVALID_ADDRESS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNDEFINED_PORT_TYPE(Object arg0) {
/* 194 */     return messageFactory.getMessage("undefined.portType", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNDEFINED_PORT_TYPE(Object arg0) {
/* 202 */     return localizer.localize(localizableUNDEFINED_PORT_TYPE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSDL_CONTAINS_NO_SERVICE(Object arg0) {
/* 206 */     return messageFactory.getMessage("wsdl.contains.no.service", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSDL_CONTAINS_NO_SERVICE(Object arg0) {
/* 214 */     return localizer.localize(localizableWSDL_CONTAINS_NO_SERVICE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_SOAP_ACTION() {
/* 218 */     return messageFactory.getMessage("invalid.soap.action", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_SOAP_ACTION() {
/* 226 */     return localizer.localize(localizableINVALID_SOAP_ACTION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_LOGICAL_HANDLER_SET(Object arg0) {
/* 230 */     return messageFactory.getMessage("non.logical.handler.set", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_LOGICAL_HANDLER_SET(Object arg0) {
/* 238 */     return localizer.localize(localizableNON_LOGICAL_HANDLER_SET(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableLOCAL_CLIENT_FAILED(Object arg0) {
/* 242 */     return messageFactory.getMessage("local.client.failed", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String LOCAL_CLIENT_FAILED(Object arg0) {
/* 250 */     return localizer.localize(localizableLOCAL_CLIENT_FAILED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_WSDLPARSER_INVALID_WSDL(Object arg0, Object arg1, Object arg2, Object arg3) {
/* 254 */     return messageFactory.getMessage("runtime.wsdlparser.invalidWSDL", new Object[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_WSDLPARSER_INVALID_WSDL(Object arg0, Object arg1, Object arg2, Object arg3) {
/* 262 */     return localizer.localize(localizableRUNTIME_WSDLPARSER_INVALID_WSDL(arg0, arg1, arg2, arg3));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSDL_NOT_FOUND(Object arg0) {
/* 266 */     return messageFactory.getMessage("wsdl.not.found", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSDL_NOT_FOUND(Object arg0) {
/* 274 */     return localizer.localize(localizableWSDL_NOT_FOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableHTTP_CLIENT_FAILED(Object arg0) {
/* 278 */     return messageFactory.getMessage("http.client.failed", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String HTTP_CLIENT_FAILED(Object arg0) {
/* 286 */     return localizer.localize(localizableHTTP_CLIENT_FAILED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_SERVICE_NAME_NULL(Object arg0) {
/* 290 */     return messageFactory.getMessage("invalid.service.name.null", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_SERVICE_NAME_NULL(Object arg0) {
/* 298 */     return localizer.localize(localizableINVALID_SERVICE_NAME_NULL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_WSDL_URL(Object arg0) {
/* 302 */     return messageFactory.getMessage("invalid.wsdl.url", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_WSDL_URL(Object arg0) {
/* 310 */     return localizer.localize(localizableINVALID_WSDL_URL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_PORT_NAME(Object arg0, Object arg1) {
/* 314 */     return messageFactory.getMessage("invalid.port.name", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_PORT_NAME(Object arg0, Object arg1) {
/* 322 */     return localizer.localize(localizableINVALID_PORT_NAME(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_SERVICE_NAME(Object arg0, Object arg1) {
/* 326 */     return messageFactory.getMessage("invalid.service.name", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_SERVICE_NAME(Object arg0, Object arg1) {
/* 334 */     return localizer.localize(localizableINVALID_SERVICE_NAME(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNSUPPORTED_OPERATION(Object arg0, Object arg1, Object arg2) {
/* 338 */     return messageFactory.getMessage("unsupported.operation", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNSUPPORTED_OPERATION(Object arg0, Object arg1, Object arg2) {
/* 346 */     return localizer.localize(localizableUNSUPPORTED_OPERATION(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableFAILED_TO_PARSE_EPR(Object arg0) {
/* 350 */     return messageFactory.getMessage("failed.to.parse.epr", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAILED_TO_PARSE_EPR(Object arg0) {
/* 358 */     return localizer.localize(localizableFAILED_TO_PARSE_EPR(arg0));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\resources\ClientMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */