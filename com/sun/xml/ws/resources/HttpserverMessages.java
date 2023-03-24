/*    */ package com.sun.xml.ws.resources;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class HttpserverMessages
/*    */ {
/* 54 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.ws.resources.httpserver");
/* 55 */   private static final Localizer localizer = new Localizer();
/*    */   
/*    */   public static Localizable localizableUNEXPECTED_HTTP_METHOD(Object arg0) {
/* 58 */     return messageFactory.getMessage("unexpected.http.method", new Object[] { arg0 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String UNEXPECTED_HTTP_METHOD(Object arg0) {
/* 66 */     return localizer.localize(localizableUNEXPECTED_HTTP_METHOD(arg0));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\resources\HttpserverMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */