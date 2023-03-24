/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedProofToken;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.BinarySecretType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestedProofTokenType;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
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
/*     */ public class RequestedProofTokenImpl
/*     */   extends RequestedProofTokenType
/*     */   implements RequestedProofToken
/*     */ {
/*     */   private String tokenType;
/*     */   private URI computedKey;
/*     */   private BinarySecret secret;
/*     */   private SecurityTokenReference str;
/*     */   
/*     */   public RequestedProofTokenImpl() {}
/*     */   
/*     */   public RequestedProofTokenImpl(String proofTokenType) {
/*  81 */     setProofTokenType(proofTokenType);
/*     */   }
/*     */   
/*     */   public RequestedProofTokenImpl(RequestedProofTokenType rptType) {
/*  85 */     JAXBElement<String> obj = (JAXBElement)rptType.getAny();
/*  86 */     String local = obj.getName().getLocalPart();
/*  87 */     if (local.equalsIgnoreCase("ComputedKey")) {
/*     */       try {
/*  89 */         setComputedKey(new URI(obj.getValue()));
/*  90 */       } catch (URISyntaxException ex) {
/*  91 */         throw new RuntimeException(ex);
/*     */       } 
/*  93 */     } else if (local.equalsIgnoreCase("BinarySecret")) {
/*  94 */       BinarySecretType bsType = (BinarySecretType)obj.getValue();
/*  95 */       setBinarySecret(new BinarySecretImpl(bsType));
/*     */     } else {
/*  97 */       throw new UnsupportedOperationException("Unsupported requested proof token: " + local);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getProofTokenType() {
/* 102 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public void setProofTokenType(String proofTokenType) {
/* 106 */     if (!proofTokenType.equalsIgnoreCase("BinarySecret") && !proofTokenType.equalsIgnoreCase("ComputedKey") && !proofTokenType.equalsIgnoreCase("EncryptedKey") && !proofTokenType.equalsIgnoreCase("Custom") && !proofTokenType.equalsIgnoreCase("SecurityTokenReference"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       throw new RuntimeException("Invalid tokenType"); } 
/* 114 */     this.tokenType = proofTokenType;
/*     */   }
/*     */   
/*     */   public void setSecurityTokenReference(SecurityTokenReference reference) {
/* 118 */     if (reference != null) {
/* 119 */       this.str = reference;
/* 120 */       JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)reference);
/*     */       
/* 122 */       setAny(strElement);
/*     */     } 
/* 124 */     setProofTokenType("SecurityTokenReference");
/*     */   }
/*     */   
/*     */   public SecurityTokenReference getSecurityTokenReference() {
/* 128 */     return this.str;
/*     */   }
/*     */   
/*     */   public void setComputedKey(URI computedKey) {
/* 132 */     if (computedKey != null) {
/* 133 */       String ckString = computedKey.toString();
/* 134 */       if (!ckString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_13.getCKHASHalgorithmURI()) && !ckString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_13.getCKPSHA1algorithmURI()))
/*     */       {
/* 136 */         throw new RuntimeException("Invalid computedKeyURI");
/*     */       }
/* 138 */       this.computedKey = computedKey;
/* 139 */       JAXBElement<String> ckElement = (new ObjectFactory()).createComputedKey(computedKey.toString());
/*     */       
/* 141 */       setAny(ckElement);
/*     */     } 
/* 143 */     setProofTokenType("ComputedKey");
/*     */   }
/*     */   
/*     */   public URI getComputedKey() {
/* 147 */     return this.computedKey;
/*     */   }
/*     */   
/*     */   public void setBinarySecret(BinarySecret secret) {
/* 151 */     if (secret != null) {
/* 152 */       this.secret = secret;
/* 153 */       JAXBElement<BinarySecretType> bsElement = (new ObjectFactory()).createBinarySecret((BinarySecretType)secret);
/*     */       
/* 155 */       setAny(bsElement);
/*     */     } 
/* 157 */     setProofTokenType("BinarySecret");
/*     */   }
/*     */   
/*     */   public BinarySecret getBinarySecret() {
/* 161 */     return this.secret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static RequestedProofTokenType fromElement(Element element) throws WSTrustException {
/*     */     try {
/* 167 */       JAXBContext jc = JAXBContext.newInstance("com.sun.xml.ws.security.trust.impl.wssx.elements");
/*     */       
/* 169 */       Unmarshaller u = jc.createUnmarshaller();
/* 170 */       return (RequestedProofTokenType)u.unmarshal(element);
/* 171 */     } catch (Exception ex) {
/* 172 */       throw new WSTrustException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\RequestedProofTokenImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */