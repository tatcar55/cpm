/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.EncryptedKey;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.BinarySecretType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.EntropyType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ObjectFactory;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
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
/*     */ public class EntropyImpl
/*     */   extends EntropyType
/*     */   implements Entropy
/*     */ {
/*     */   private String entropyType;
/*  70 */   private static final QName _EntropyType_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Type");
/*     */   
/*  72 */   private BinarySecret binarySecret = null;
/*     */   
/*  74 */   private EncryptedKey encryptedKey = null;
/*     */ 
/*     */   
/*     */   public EntropyImpl() {}
/*     */ 
/*     */   
/*     */   public EntropyImpl(BinarySecret binarySecret) {
/*  81 */     setBinarySecret(binarySecret);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntropyImpl(EncryptedKey encryptedKey) {
/*  86 */     setEncryptedKey(encryptedKey);
/*     */   }
/*     */   
/*     */   public EntropyImpl(EntropyType etype) {
/*  90 */     this.entropyType = (String)etype.getOtherAttributes().get(_EntropyType_QNAME);
/*  91 */     List<JAXBElement> list = etype.getAny();
/*  92 */     for (int i = 0; i < list.size(); i++) {
/*  93 */       JAXBElement<BinarySecretType> obj = list.get(i);
/*  94 */       String local = obj.getName().getLocalPart();
/*  95 */       if (local.equalsIgnoreCase("BinarySecret")) {
/*  96 */         BinarySecretType bst = obj.getValue();
/*  97 */         setBinarySecret(new BinarySecretImpl(bst));
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
/* 116 */       JAXBContext jc = JAXBContext.newInstance("com.sun.xml.ws.security.trust.impl.elements");
/*     */       
/* 118 */       Unmarshaller u = jc.createUnmarshaller();
/* 119 */       return (EntropyType)u.unmarshal(element);
/* 120 */     } catch (Exception ex) {
/* 121 */       throw new WSTrustException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEntropyType() {
/* 129 */     return this.entropyType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntropyType(String type) {
/* 136 */     this; this; this; if (!type.equalsIgnoreCase("BinarySecret") && !type.equalsIgnoreCase("Custom") && !type.equalsIgnoreCase("EncryptedKey"))
/*     */     {
/*     */       
/* 139 */       throw new RuntimeException("Invalid Entropy Type");
/*     */     }
/* 141 */     this.entropyType = type;
/* 142 */     getOtherAttributes().put(_EntropyType_QNAME, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinarySecret getBinarySecret() {
/* 150 */     return this.binarySecret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBinarySecret(BinarySecret binarySecret) {
/* 157 */     if (binarySecret != null) {
/* 158 */       this.binarySecret = binarySecret;
/* 159 */       JAXBElement<BinarySecretType> bsElement = (new ObjectFactory()).createBinarySecret((BinarySecretType)binarySecret);
/*     */       
/* 161 */       getAny().add(bsElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedKey getEncryptedKey() {
/* 170 */     return this.encryptedKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncryptedKey(EncryptedKey encryptedKey) {
/* 177 */     if (encryptedKey != null) {
/* 178 */       this.encryptedKey = encryptedKey;
/* 179 */       getAny().add(encryptedKey);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\EntropyImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */