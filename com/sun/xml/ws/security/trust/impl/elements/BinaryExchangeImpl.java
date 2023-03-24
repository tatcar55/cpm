/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.xml.ws.security.trust.elements.BinaryExchange;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.BinaryExchangeType;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinaryExchangeImpl
/*     */   extends BinaryExchangeType
/*     */   implements BinaryExchange
/*     */ {
/*  67 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinaryExchangeImpl(String encodingType, String valueType, byte[] rawText) {
/*  73 */     setEncodingType(encodingType);
/*  74 */     setValueType(valueType);
/*  75 */     setRawValue(rawText);
/*     */   }
/*     */   
/*     */   public BinaryExchangeImpl(BinaryExchangeType bcType) throws RuntimeException {
/*  79 */     setEncodingType(bcType.getEncodingType());
/*  80 */     setValueType(bcType.getValueType());
/*  81 */     setValue(bcType.getValue());
/*     */   }
/*     */   
/*     */   public byte[] getRawValue() {
/*     */     try {
/*  86 */       return Base64.decode(getTextValue());
/*  87 */     } catch (Base64DecodingException de) {
/*  88 */       log.log(Level.SEVERE, LogStringsMessages.WST_0020_ERROR_DECODING(getTextValue()), de);
/*     */       
/*  90 */       throw new RuntimeException(LogStringsMessages.WST_0020_ERROR_DECODING(getTextValue()), de);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getTextValue() {
/*  95 */     return getValue();
/*     */   }
/*     */   
/*     */   public void setTextValue(@NotNull String encodedText) {
/*  99 */     setValue(encodedText);
/*     */   }
/*     */   
/*     */   public final void setRawValue(@NotNull byte[] rawText) {
/* 103 */     setValue(Base64.encode(rawText));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\BinaryExchangeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */