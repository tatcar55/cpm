/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.BinarySecretType;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ public class BinarySecretImpl
/*     */   extends BinarySecretType
/*     */   implements BinarySecret
/*     */ {
/*  77 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinarySecretImpl(@NotNull byte[] rawValue, String type) {
/*  83 */     setRawValue(rawValue);
/*  84 */     setType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public BinarySecretImpl(@NotNull BinarySecretType bsType) {
/*  89 */     this(bsType.getValue(), bsType.getType());
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
/*     */   public static BinarySecretType fromElement(@NotNull Element element) throws WSTrustException {
/*     */     try {
/* 107 */       Unmarshaller u = WSTrustElementFactory.getContext().createUnmarshaller();
/* 108 */       return ((JAXBElement<BinarySecretType>)u.unmarshal(element)).getValue();
/* 109 */     } catch (JAXBException ex) {
/* 110 */       log.log(Level.SEVERE, LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT(), ex);
/*     */       
/* 112 */       throw new WSTrustException(LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public byte[] getRawValue() {
/* 118 */     return getValue();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getTextValue() {
/* 123 */     return Base64.encode(getRawValue());
/*     */   }
/*     */   
/*     */   public final void setRawValue(@NotNull byte[] rawText) {
/* 127 */     setValue(rawText);
/*     */   }
/*     */   
/*     */   public void setTextValue(@NotNull String encodedText) {
/*     */     try {
/* 132 */       setValue(Base64.decode(encodedText));
/* 133 */     } catch (Base64DecodingException de) {
/* 134 */       log.log(Level.SEVERE, LogStringsMessages.WST_0020_ERROR_DECODING(encodedText), de);
/*     */       
/* 136 */       throw new RuntimeException(LogStringsMessages.WST_0020_ERROR_DECODING(encodedText), de);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\BinarySecretImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */