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
/*     */ public final class SoapMessages
/*     */ {
/*  54 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.ws.resources.soap");
/*  55 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableSOAP_FAULT_CREATE_ERR(Object arg0) {
/*  58 */     return messageFactory.getMessage("soap.fault.create.err", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_FAULT_CREATE_ERR(Object arg0) {
/*  66 */     return localizer.localize(localizableSOAP_FAULT_CREATE_ERR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAP_MSG_FACTORY_CREATE_ERR(Object arg0) {
/*  70 */     return messageFactory.getMessage("soap.msg.factory.create.err", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_MSG_FACTORY_CREATE_ERR(Object arg0) {
/*  78 */     return localizer.localize(localizableSOAP_MSG_FACTORY_CREATE_ERR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAP_MSG_CREATE_ERR(Object arg0) {
/*  82 */     return messageFactory.getMessage("soap.msg.create.err", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_MSG_CREATE_ERR(Object arg0) {
/*  90 */     return localizer.localize(localizableSOAP_MSG_CREATE_ERR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAP_FACTORY_CREATE_ERR(Object arg0) {
/*  94 */     return messageFactory.getMessage("soap.factory.create.err", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_FACTORY_CREATE_ERR(Object arg0) {
/* 102 */     return localizer.localize(localizableSOAP_FACTORY_CREATE_ERR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAP_PROTOCOL_INVALID_FAULT_CODE(Object arg0) {
/* 106 */     return messageFactory.getMessage("soap.protocol.invalidFaultCode", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_PROTOCOL_INVALID_FAULT_CODE(Object arg0) {
/* 114 */     return localizer.localize(localizableSOAP_PROTOCOL_INVALID_FAULT_CODE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAP_VERSION_MISMATCH_ERR(Object arg0, Object arg1) {
/* 118 */     return messageFactory.getMessage("soap.version.mismatch.err", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_VERSION_MISMATCH_ERR(Object arg0, Object arg1) {
/* 126 */     return localizer.localize(localizableSOAP_VERSION_MISMATCH_ERR(arg0, arg1));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\resources\SoapMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */