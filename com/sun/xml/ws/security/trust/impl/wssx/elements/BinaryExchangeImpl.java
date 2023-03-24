/*    */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*    */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*    */ import com.sun.xml.ws.security.trust.elements.BinaryExchange;
/*    */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.BinaryExchangeType;
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
/*    */ 
/*    */ 
/*    */ public class BinaryExchangeImpl
/*    */   extends BinaryExchangeType
/*    */   implements BinaryExchange
/*    */ {
/*    */   public BinaryExchangeImpl(String encodingType, String valueType, byte[] rawText) {
/* 60 */     setEncodingType(encodingType);
/* 61 */     setValueType(valueType);
/* 62 */     setRawValue(rawText);
/*    */   }
/*    */   
/*    */   public BinaryExchangeImpl(BinaryExchangeType bcType) throws Exception {
/* 66 */     setEncodingType(bcType.getEncodingType());
/* 67 */     setValueType(bcType.getValueType());
/* 68 */     setValue(bcType.getValue());
/*    */   }
/*    */   
/*    */   public byte[] getRawValue() {
/*    */     try {
/* 73 */       return Base64.decode(getTextValue());
/* 74 */     } catch (Base64DecodingException de) {
/* 75 */       throw new RuntimeException("Error while decoding " + de.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTextValue() {
/* 81 */     return getValue();
/*    */   }
/*    */   
/*    */   public void setTextValue(String encodedText) {
/* 85 */     setValue(encodedText);
/*    */   }
/*    */   
/*    */   public void setRawValue(byte[] rawText) {
/* 89 */     setValue(Base64.encode(rawText));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\BinaryExchangeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */