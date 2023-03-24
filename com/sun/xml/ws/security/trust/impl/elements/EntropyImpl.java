/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.EncryptedKey;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.BinarySecretType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.EntropyType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class EntropyImpl
/*     */   extends EntropyType
/*     */   implements Entropy
/*     */ {
/*  78 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */   
/*     */   private String entropyType;
/*     */ 
/*     */   
/*  84 */   private static final QName _EntropyType_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Type");
/*     */   
/*  86 */   private BinarySecret binarySecret = null;
/*     */   
/*  88 */   private EncryptedKey encryptedKey = null;
/*     */ 
/*     */   
/*     */   public EntropyImpl() {}
/*     */ 
/*     */   
/*     */   public EntropyImpl(BinarySecret binarySecret) {
/*  95 */     setEntropyType("BinarySecret");
/*  96 */     setBinarySecret(binarySecret);
/*     */   }
/*     */   
/*     */   public EntropyImpl(EncryptedKey encryptedKey) {
/* 100 */     setEntropyType("EncryptedKey");
/* 101 */     setEncryptedKey(encryptedKey);
/*     */   }
/*     */   
/*     */   public EntropyImpl(@NotNull EntropyType etype) {
/* 105 */     this.entropyType = (String)etype.getOtherAttributes().get(_EntropyType_QNAME);
/* 106 */     List<JAXBElement> list = etype.getAny();
/* 107 */     for (int i = 0; i < list.size(); i++) {
/* 108 */       JAXBElement<BinarySecretType> obj = list.get(i);
/* 109 */       String local = obj.getName().getLocalPart();
/* 110 */       if (local.equalsIgnoreCase("BinarySecret")) {
/* 111 */         BinarySecretType bst = obj.getValue();
/* 112 */         setBinarySecret(new BinarySecretImpl(bst));
/*     */       } 
/*     */     } 
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
/*     */   public static EntropyType fromElement(Element element) throws WSTrustException {
/*     */     try {
/* 131 */       Unmarshaller unmarshaller = WSTrustElementFactory.getContext().createUnmarshaller();
/* 132 */       return (EntropyType)unmarshaller.unmarshal(element);
/* 133 */     } catch (JAXBException ex) {
/* 134 */       log.log(Level.SEVERE, LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT(), ex);
/*     */       
/* 136 */       throw new WSTrustException(LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEntropyType() {
/* 144 */     return this.entropyType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setEntropyType(@NotNull String type) {
/* 151 */     this; this; this; if (!type.equalsIgnoreCase("BinarySecret") && !type.equalsIgnoreCase("Custom") && !type.equalsIgnoreCase("EncryptedKey")) {
/*     */ 
/*     */       
/* 154 */       log.log(Level.SEVERE, LogStringsMessages.WST_0022_INVALID_ENTROPY(type));
/*     */       
/* 156 */       throw new RuntimeException(LogStringsMessages.WST_0022_INVALID_ENTROPY(type));
/*     */     } 
/* 158 */     this.entropyType = type;
/* 159 */     getOtherAttributes().put(_EntropyType_QNAME, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinarySecret getBinarySecret() {
/* 167 */     return this.binarySecret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setBinarySecret(BinarySecret binarySecret) {
/* 174 */     if (binarySecret != null) {
/* 175 */       this.binarySecret = binarySecret;
/* 176 */       JAXBElement<BinarySecretType> bsElement = (new ObjectFactory()).createBinarySecret((BinarySecretType)binarySecret);
/*     */       
/* 178 */       getAny().add(bsElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedKey getEncryptedKey() {
/* 187 */     return this.encryptedKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setEncryptedKey(EncryptedKey encryptedKey) {
/* 194 */     if (encryptedKey != null) {
/* 195 */       this.encryptedKey = encryptedKey;
/* 196 */       getAny().add(encryptedKey);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\EntropyImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */