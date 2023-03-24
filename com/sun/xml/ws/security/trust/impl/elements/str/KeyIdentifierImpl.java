/*    */ package com.sun.xml.ws.security.trust.impl.elements.str;
/*    */ 
/*    */ import com.sun.xml.ws.security.secext10.KeyIdentifierType;
/*    */ import com.sun.xml.ws.security.trust.elements.str.KeyIdentifier;
/*    */ import java.net.URI;
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
/*    */ 
/*    */ 
/*    */ public class KeyIdentifierImpl
/*    */   extends KeyIdentifierType
/*    */   implements KeyIdentifier
/*    */ {
/*    */   public KeyIdentifierImpl() {}
/*    */   
/*    */   public KeyIdentifierImpl(String valueType, String encodingType) {
/* 63 */     setValueType(valueType);
/* 64 */     setEncodingType(encodingType);
/*    */   }
/*    */   
/*    */   public KeyIdentifierImpl(KeyIdentifierType kidType) {
/* 68 */     this(kidType.getValueType(), kidType.getEncodingType());
/* 69 */     setValue(kidType.getValue());
/*    */   }
/*    */   
/*    */   public URI getValueTypeURI() {
/* 73 */     return URI.create(getValueType());
/*    */   }
/*    */   
/*    */   public URI getEncodingTypeURI() {
/* 77 */     return URI.create(getEncodingType());
/*    */   }
/*    */   
/*    */   public String getType() {
/* 81 */     return "KeyIdentifier";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\str\KeyIdentifierImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */