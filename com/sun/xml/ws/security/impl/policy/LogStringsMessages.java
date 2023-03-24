/*     */ package com.sun.xml.ws.security.impl.policy;
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
/*     */ public final class LogStringsMessages
/*     */ {
/*  15 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.ws.security.impl.policy.LogStrings");
/*  16 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableSP_0107_UNKNOWN_TOKEN_TYPE(Object arg0) {
/*  19 */     return messageFactory.getMessage("SP0107.unknown.token.type", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0107_UNKNOWN_TOKEN_TYPE(Object arg0) {
/*  27 */     return localizer.localize(localizableSP_0107_UNKNOWN_TOKEN_TYPE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0112_ERROR_INSTANTIATING(Object arg0) {
/*  31 */     return messageFactory.getMessage("SP0112.error.instantiating", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0112_ERROR_INSTANTIATING(Object arg0) {
/*  39 */     return localizer.localize(localizableSP_0112_ERROR_INSTANTIATING(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0104_ERROR_SIGNATURE_CONFIRMATION_ELEMENT(Object arg0) {
/*  43 */     return messageFactory.getMessage("SP0104.error.signature-confirmation-element", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0104_ERROR_SIGNATURE_CONFIRMATION_ELEMENT(Object arg0) {
/*  51 */     return localizer.localize(localizableSP_0104_ERROR_SIGNATURE_CONFIRMATION_ELEMENT(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0113_UNABLE_TO_DIGEST_POLICY(Object arg0) {
/*  55 */     return messageFactory.getMessage("SP0113.unable.to.digest.policy", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0113_UNABLE_TO_DIGEST_POLICY(Object arg0) {
/*  63 */     return localizer.localize(localizableSP_0113_UNABLE_TO_DIGEST_POLICY(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0105_ERROR_BINDING_ASSR_NOT_PRESENT() {
/*  67 */     return messageFactory.getMessage("SP0105.error.binding-assr-not-present", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0105_ERROR_BINDING_ASSR_NOT_PRESENT() {
/*  75 */     return localizer.localize(localizableSP_0105_ERROR_BINDING_ASSR_NOT_PRESENT());
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0103_ERROR_REQUIRED_ELEMENTS(Object arg0) {
/*  79 */     return messageFactory.getMessage("SP0103.error.required-elements", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0103_ERROR_REQUIRED_ELEMENTS(Object arg0) {
/*  87 */     return localizer.localize(localizableSP_0103_ERROR_REQUIRED_ELEMENTS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0111_ERROR_OBTAINING_CONSTRUCTOR(Object arg0) {
/*  91 */     return messageFactory.getMessage("SP0111.error.obtaining.constructor", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0111_ERROR_OBTAINING_CONSTRUCTOR(Object arg0) {
/*  99 */     return localizer.localize(localizableSP_0111_ERROR_OBTAINING_CONSTRUCTOR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0102_INVALID_URI_VALUE(Object arg0) {
/* 103 */     return messageFactory.getMessage("SP0102.invalid.uri-value", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0102_INVALID_URI_VALUE(Object arg0) {
/* 111 */     return localizer.localize(localizableSP_0102_INVALID_URI_VALUE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0110_ERROR_LOCATING_CLASS(Object arg0) {
/* 115 */     return messageFactory.getMessage("SP0110.error.locating.class", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0110_ERROR_LOCATING_CLASS(Object arg0) {
/* 123 */     return localizer.localize(localizableSP_0110_ERROR_LOCATING_CLASS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0108_SECURITY_POLICY_CREATOR_NF(Object arg0) {
/* 127 */     return messageFactory.getMessage("SP0108.security.policy.creator.nf", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0108_SECURITY_POLICY_CREATOR_NF(Object arg0) {
/* 135 */     return localizer.localize(localizableSP_0108_SECURITY_POLICY_CREATOR_NF(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0101_INVALID_EPR_ADDRESS() {
/* 139 */     return messageFactory.getMessage("SP0101.invalid.epr-address", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0101_INVALID_EPR_ADDRESS() {
/* 147 */     return localizer.localize(localizableSP_0101_INVALID_EPR_ADDRESS());
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0100_INVALID_SECURITY_ASSERTION(Object arg0, Object arg1) {
/* 151 */     return messageFactory.getMessage("SP0100.invalid.security.assertion", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0100_INVALID_SECURITY_ASSERTION(Object arg0, Object arg1) {
/* 159 */     return localizer.localize(localizableSP_0100_INVALID_SECURITY_ASSERTION(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0106_UNKNOWN_MESSAGE_LAYOUT(Object arg0) {
/* 163 */     return messageFactory.getMessage("SP0106.unknown.message.layout", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0106_UNKNOWN_MESSAGE_LAYOUT(Object arg0) {
/* 171 */     return localizer.localize(localizableSP_0106_UNKNOWN_MESSAGE_LAYOUT(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSP_0109_ERROR_DIGESTING_SP(Object arg0) {
/* 175 */     return messageFactory.getMessage("SP0109.error.digesting.sp", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SP_0109_ERROR_DIGESTING_SP(Object arg0) {
/* 183 */     return localizer.localize(localizableSP_0109_ERROR_DIGESTING_SP(arg0));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\LogStringsMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */