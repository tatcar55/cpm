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
/*     */ public final class SenderMessages
/*     */ {
/*  54 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.ws.resources.sender");
/*  55 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableSENDER_REQUEST_ILLEGAL_VALUE_FOR_CONTENT_NEGOTIATION(Object arg0) {
/*  58 */     return messageFactory.getMessage("sender.request.illegalValueForContentNegotiation", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SENDER_REQUEST_ILLEGAL_VALUE_FOR_CONTENT_NEGOTIATION(Object arg0) {
/*  66 */     return localizer.localize(localizableSENDER_REQUEST_ILLEGAL_VALUE_FOR_CONTENT_NEGOTIATION(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSENDER_RESPONSE_CANNOT_DECODE_FAULT_DETAIL() {
/*  70 */     return messageFactory.getMessage("sender.response.cannotDecodeFaultDetail", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SENDER_RESPONSE_CANNOT_DECODE_FAULT_DETAIL() {
/*  78 */     return localizer.localize(localizableSENDER_RESPONSE_CANNOT_DECODE_FAULT_DETAIL());
/*     */   }
/*     */   
/*     */   public static Localizable localizableSENDER_NESTED_ERROR(Object arg0) {
/*  82 */     return messageFactory.getMessage("sender.nestedError", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SENDER_NESTED_ERROR(Object arg0) {
/*  90 */     return localizer.localize(localizableSENDER_NESTED_ERROR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSENDER_REQUEST_MESSAGE_NOT_READY() {
/*  94 */     return messageFactory.getMessage("sender.request.messageNotReady", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SENDER_REQUEST_MESSAGE_NOT_READY() {
/* 102 */     return localizer.localize(localizableSENDER_REQUEST_MESSAGE_NOT_READY());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\resources\SenderMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */