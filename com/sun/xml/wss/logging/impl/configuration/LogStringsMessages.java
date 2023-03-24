/*    */ package com.sun.xml.wss.logging.impl.configuration;
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
/* 15 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.wss.logging.impl.configuration.LogStrings");
/* 16 */   private static final Localizer localizer = new Localizer();
/*    */   
/*    */   public static Localizable localizableWSS_1100_CLASSCAST_TARGET(Object arg0) {
/* 19 */     return messageFactory.getMessage("WSS1100.classcast.target", new Object[] { arg0 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String WSS_1100_CLASSCAST_TARGET(Object arg0) {
/* 27 */     return localizer.localize(localizableWSS_1100_CLASSCAST_TARGET(arg0));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\logging\impl\configuration\LogStringsMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */