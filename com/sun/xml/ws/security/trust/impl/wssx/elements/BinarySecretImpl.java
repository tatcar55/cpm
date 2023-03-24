/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.BinarySecretType;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import org.w3c.dom.Element;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinarySecretImpl
/*     */   extends BinarySecretType
/*     */   implements BinarySecret
/*     */ {
/*     */   public BinarySecretImpl(byte[] rawValue, String type) {
/*  77 */     setRawValue(rawValue);
/*  78 */     setType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public BinarySecretImpl(BinarySecretType bsType) {
/*  83 */     this(bsType.getValue(), bsType.getType());
/*     */   }
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
/*     */   public static BinarySecretType fromElement(Element element) throws WSTrustException {
/*     */     try {
/* 101 */       JAXBContext jc = JAXBContext.newInstance("com.sun.xml.ws.security.trust.impl.bindings");
/*     */       
/* 103 */       Unmarshaller u = jc.createUnmarshaller();
/* 104 */       return ((JAXBElement<BinarySecretType>)u.unmarshal(element)).getValue();
/* 105 */     } catch (Exception ex) {
/* 106 */       throw new WSTrustException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] getRawValue() {
/* 111 */     return getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextValue() {
/* 116 */     return Base64.encode(getRawValue());
/*     */   }
/*     */   
/*     */   public void setRawValue(byte[] rawText) {
/* 120 */     setValue(rawText);
/*     */   }
/*     */   
/*     */   public void setTextValue(String encodedText) {
/*     */     try {
/* 125 */       setValue(Base64.decode(encodedText));
/* 126 */     } catch (Base64DecodingException de) {
/* 127 */       throw new RuntimeException("Error while decoding " + de.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\BinarySecretImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */