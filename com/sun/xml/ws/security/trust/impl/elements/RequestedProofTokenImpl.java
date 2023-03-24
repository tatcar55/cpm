/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedProofToken;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.BinarySecretType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestedProofTokenType;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import java.net.URI;
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
/*     */ public class RequestedProofTokenImpl
/*     */   extends RequestedProofTokenType
/*     */   implements RequestedProofToken
/*     */ {
/*  79 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */   
/*     */   private String tokenType;
/*     */   
/*     */   private URI computedKey;
/*     */   
/*     */   private BinarySecret secret;
/*     */   
/*     */   private SecurityTokenReference str;
/*     */ 
/*     */   
/*     */   public RequestedProofTokenImpl() {}
/*     */ 
/*     */   
/*     */   public RequestedProofTokenImpl(String proofTokenType) {
/*  94 */     setProofTokenType(proofTokenType);
/*     */   }
/*     */   
/*     */   public RequestedProofTokenImpl(RequestedProofTokenType rptType) {
/*  98 */     JAXBElement<String> obj = (JAXBElement)rptType.getAny();
/*  99 */     String local = obj.getName().getLocalPart();
/* 100 */     if (local.equalsIgnoreCase("ComputedKey")) {
/* 101 */       setComputedKey(URI.create(obj.getValue()));
/* 102 */     } else if (local.equalsIgnoreCase("BinarySecret")) {
/* 103 */       BinarySecretType bsType = (BinarySecretType)obj.getValue();
/* 104 */       setBinarySecret(new BinarySecretImpl(bsType));
/*     */     } else {
/* 106 */       log.log(Level.SEVERE, LogStringsMessages.WST_0019_INVALID_PROOF_TOKEN_TYPE(local, null));
/*     */       
/* 108 */       throw new RuntimeException(LogStringsMessages.WST_0019_INVALID_PROOF_TOKEN_TYPE(local, null));
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getProofTokenType() {
/* 113 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public final void setProofTokenType(@NotNull String proofTokenType) {
/* 117 */     if (!proofTokenType.equalsIgnoreCase("BinarySecret") && !proofTokenType.equalsIgnoreCase("ComputedKey") && !proofTokenType.equalsIgnoreCase("EncryptedKey") && !proofTokenType.equalsIgnoreCase("Custom") && !proofTokenType.equalsIgnoreCase("SecurityTokenReference")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       log.log(Level.SEVERE, LogStringsMessages.WST_0019_INVALID_PROOF_TOKEN_TYPE(proofTokenType, null));
/*     */       
/* 125 */       throw new RuntimeException(LogStringsMessages.WST_0019_INVALID_PROOF_TOKEN_TYPE(proofTokenType, null));
/*     */     } 
/* 127 */     this.tokenType = proofTokenType;
/*     */   }
/*     */   
/*     */   public void setSecurityTokenReference(SecurityTokenReference reference) {
/* 131 */     if (reference != null) {
/* 132 */       this.str = reference;
/* 133 */       JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)reference);
/*     */       
/* 135 */       setAny(strElement);
/*     */     } 
/* 137 */     setProofTokenType("SecurityTokenReference");
/*     */   }
/*     */   
/*     */   public SecurityTokenReference getSecurityTokenReference() {
/* 141 */     return this.str;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setComputedKey(@NotNull URI computedKey) {
/* 146 */     if (computedKey != null) {
/* 147 */       String ckString = computedKey.toString();
/* 148 */       if (!ckString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getCKHASHalgorithmURI()) && !ckString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getCKPSHA1algorithmURI())) {
/*     */         
/* 150 */         log.log(Level.SEVERE, LogStringsMessages.WST_0028_INVALID_CK(ckString));
/*     */         
/* 152 */         throw new RuntimeException(LogStringsMessages.WST_0028_INVALID_CK(ckString));
/*     */       } 
/* 154 */       this.computedKey = computedKey;
/* 155 */       JAXBElement<String> ckElement = (new ObjectFactory()).createComputedKey(computedKey.toString());
/*     */       
/* 157 */       setAny(ckElement);
/*     */     } 
/* 159 */     setProofTokenType("ComputedKey");
/*     */   }
/*     */   
/*     */   public URI getComputedKey() {
/* 163 */     return this.computedKey;
/*     */   }
/*     */   
/*     */   public final void setBinarySecret(BinarySecret secret) {
/* 167 */     if (secret != null) {
/* 168 */       this.secret = secret;
/* 169 */       JAXBElement<BinarySecretType> bsElement = (new ObjectFactory()).createBinarySecret((BinarySecretType)secret);
/*     */       
/* 171 */       setAny(bsElement);
/*     */     } 
/* 173 */     setProofTokenType("BinarySecret");
/*     */   }
/*     */   
/*     */   public BinarySecret getBinarySecret() {
/* 177 */     return this.secret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static RequestedProofTokenType fromElement(Element element) throws WSTrustException {
/*     */     try {
/* 183 */       Unmarshaller unmarshaller = WSTrustElementFactory.getContext().createUnmarshaller();
/* 184 */       return (RequestedProofTokenType)unmarshaller.unmarshal(element);
/* 185 */     } catch (JAXBException ex) {
/* 186 */       log.log(Level.SEVERE, LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT(), ex);
/*     */       
/* 188 */       throw new WSTrustException(LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\RequestedProofTokenImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */