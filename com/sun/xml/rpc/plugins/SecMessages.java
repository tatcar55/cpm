/*    */ package com.sun.xml.rpc.plugins;
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
/*    */ public final class SecMessages
/*    */ {
/* 15 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.plugins.sec");
/* 16 */   private static final Localizer localizer = new Localizer();
/*    */   
/*    */   public static Localizable localizableSEC_USAGE_OPTIONS() {
/* 19 */     return messageFactory.getMessage("sec.usage.options", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String SEC_USAGE_OPTIONS() {
/* 27 */     return localizer.localize(localizableSEC_USAGE_OPTIONS());
/*    */   }
/*    */   
/*    */   public static Localizable localizableSEC_MISSING_OPTION_ARGUMENT(Object arg0) {
/* 31 */     return messageFactory.getMessage("sec.missingOptionArgument", new Object[] { arg0 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String SEC_MISSING_OPTION_ARGUMENT(Object arg0) {
/* 39 */     return localizer.localize(localizableSEC_MISSING_OPTION_ARGUMENT(arg0));
/*    */   }
/*    */   
/*    */   public static Localizable localizableSEC_DUPLICATE_OPTION(Object arg0) {
/* 43 */     return messageFactory.getMessage("sec.duplicateOption", new Object[] { arg0 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String SEC_DUPLICATE_OPTION(Object arg0) {
/* 51 */     return localizer.localize(localizableSEC_DUPLICATE_OPTION(arg0));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\plugins\SecMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */