/*    */ package com.sun.xml.wss.logging.saml;
/*    */ 
/*    */ import com.sun.istack.localization.Localizable;
/*    */ import com.sun.istack.localization.LocalizableMessageFactory;
/*    */ import com.sun.istack.localization.Localizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LogStringsMessages
/*    */ {
/* 15 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.wss.logging.saml.LogStrings");
/* 16 */   private static final Localizer localizer = new Localizer();
/*    */   
/*    */   public static Localizable localizableWSS_001_SAML_ASSERTION_NOT_FOUND(Object arg0) {
/* 19 */     return messageFactory.getMessage("WSS001.SAML_ASSERTION_NOT_FOUND", new Object[] { arg0 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String WSS_001_SAML_ASSERTION_NOT_FOUND(Object arg0) {
/* 27 */     return localizer.localize(localizableWSS_001_SAML_ASSERTION_NOT_FOUND(arg0));
/*    */   }
/*    */   
/*    */   public static Localizable localizableWSS_003_FAILEDTO_MARSHAL() {
/* 31 */     return messageFactory.getMessage("WSS003.failedto.marshal", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String WSS_003_FAILEDTO_MARSHAL() {
/* 39 */     return localizer.localize(localizableWSS_003_FAILEDTO_MARSHAL());
/*    */   }
/*    */   
/*    */   public static Localizable localizableWSS_004_SAML_VERSION_NOT_SET() {
/* 43 */     return messageFactory.getMessage("WSS004.SAML_VERSION_NOT_SET", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String WSS_004_SAML_VERSION_NOT_SET() {
/* 51 */     return localizer.localize(localizableWSS_004_SAML_VERSION_NOT_SET());
/*    */   }
/*    */   
/*    */   public static Localizable localizableWSS_002_FAILED_CREATE_DOCUMENT() {
/* 55 */     return messageFactory.getMessage("WSS002.failed.create.document", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String WSS_002_FAILED_CREATE_DOCUMENT() {
/* 63 */     return localizer.localize(localizableWSS_002_FAILED_CREATE_DOCUMENT());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\logging\saml\LogStringsMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */