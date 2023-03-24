/*     */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.SignatureType;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.SignatureException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.crypto.KeySelectorException;
/*     */ import javax.xml.crypto.KeySelectorResult;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.dsig.SignatureMethod;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.XMLSignContext;
/*     */ import javax.xml.crypto.dsig.XMLSignature;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ import javax.xml.crypto.dsig.spec.HMACParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
/*     */ import org.jvnet.staxex.XMLStreamReaderEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*     */ public class Signature
/*     */   extends SignatureType
/*     */   implements XMLSignature
/*     */ {
/*     */   @XmlTransient
/*  86 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */   
/*     */   @XmlTransient
/*  89 */   private XMLStreamReaderEx _streamSI = null; @XmlTransient
/*  90 */   private String type = null; @XmlTransient
/*  91 */   private List<XMLObject> objects = null;
/*     */   @XmlTransient
/*  93 */   private Key verificationKey = null; @XmlTransient private SignatureProcessor _sp; @XmlTransient
/*  94 */   private byte[] signedInfoBytes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSignedInfo(XMLStreamReaderEx streamReader) {
/* 104 */     this._streamSI = streamReader;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSignedInfo(byte[] si) {
/* 109 */     this.signedInfoBytes = si;
/*     */   }
/*     */   
/*     */   public void setVerificationKey(Key key) {
/* 113 */     this.verificationKey = key;
/*     */   }
/*     */ 
/*     */   
/*     */   public Key getVerificationKey() {
/* 118 */     return this.verificationKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate(XMLValidateContext xMLValidateContext) throws XMLSignatureException {
/* 125 */     if (xMLValidateContext == null) {
/* 126 */       throw new NullPointerException("validateContext cannot be null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     SignedInfo si = getSignedInfo();
/* 134 */     List<Reference> refList = si.getReferences();
/* 135 */     for (int i = 0, size = refList.size(); i < size; i++) {
/* 136 */       Reference ref = refList.get(i);
/* 137 */       byte[] originalDigest = ref.getDigestValue();
/* 138 */       ref.digest(xMLValidateContext);
/* 139 */       byte[] calculatedDigest = ref.getDigestValue();
/* 140 */       if (!Arrays.equals(originalDigest, calculatedDigest)) {
/* 141 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1713_SIGNATURE_VERIFICATION_EXCEPTION("Signature digest values mismatch"));
/* 142 */         throw new XMLSignatureException(LogStringsMessages.WSS_1713_SIGNATURE_VERIFICATION_EXCEPTION("Signature digest values mismatch"));
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     si.setSignedInfo(this._streamSI);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     KeySelectorResult keySelectoResult = null;
/*     */     try {
/* 153 */       SignatureMethod sm = si.getSignatureMethod();
/* 154 */       if (this.verificationKey == null) {
/* 155 */         keySelectoResult = xMLValidateContext.getKeySelector().select((KeyInfo)getKeyInfo(), KeySelector.Purpose.VERIFY, sm, xMLValidateContext);
/* 156 */         this.verificationKey = keySelectoResult.getKey();
/*     */       } 
/* 158 */       if (this.verificationKey == null) {
/* 159 */         throw new XMLSignatureException("The KeySelector" + xMLValidateContext.getKeySelector() + " did not " + "find the key used for signature verification");
/*     */       }
/*     */ 
/*     */       
/* 163 */       if (this._sp == null) {
/* 164 */         this._sp = new SignatureProcessor();
/*     */       }
/*     */       
/*     */       try {
/* 168 */         String signatureAlgo = sm.getAlgorithm();
/* 169 */         String algo = getRSASignatureAlgorithm(signatureAlgo);
/* 170 */         if (algo != null)
/* 171 */           return this._sp.verifyRSASignature(this.verificationKey, si, getSignatureValue().getValue(), algo); 
/* 172 */         if (signatureAlgo.equals("http://www.w3.org/2000/09/xmldsig#dsa-sha1"))
/* 173 */           return this._sp.verifyDSASignature(this.verificationKey, si, getSignatureValue().getValue()); 
/* 174 */         if (signatureAlgo.equals("http://www.w3.org/2000/09/xmldsig#hmac-sha1")) {
/* 175 */           SignatureMethodParameterSpec params = (SignatureMethodParameterSpec)sm.getParameterSpec();
/*     */           
/* 177 */           int outputLength = -1;
/*     */           
/* 179 */           if (params != null) {
/* 180 */             if (!(params instanceof HMACParameterSpec)) {
/* 181 */               throw new XMLSignatureException("SignatureMethodParameterSpec must be of type HMACParameterSpec");
/*     */             }
/*     */             
/* 184 */             outputLength = ((HMACParameterSpec)params).getOutputLength();
/*     */           } 
/* 186 */           return this._sp.verifyHMACSignature(this.verificationKey, si, getSignatureValue().getValue(), outputLength);
/*     */         } 
/* 188 */         throw new XMLSignatureException("Unsupported signature algorithm found");
/*     */       }
/* 190 */       catch (InvalidKeyException ex) {
/* 191 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1713_SIGNATURE_VERIFICATION_EXCEPTION(ex));
/* 192 */         throw new XMLSignatureException(LogStringsMessages.WSS_1713_SIGNATURE_VERIFICATION_EXCEPTION(ex));
/* 193 */       } catch (SignatureException ex) {
/* 194 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1713_SIGNATURE_VERIFICATION_EXCEPTION("Signature digest values mismatch"));
/* 195 */         throw new XMLSignatureException(LogStringsMessages.WSS_1713_SIGNATURE_VERIFICATION_EXCEPTION(ex));
/*     */       } 
/* 197 */     } catch (KeySelectorException kse) {
/* 198 */       throw new XMLSignatureException("Cannot find verification key", kse);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List getObjects() {
/* 204 */     return null;
/*     */   }
/*     */   
/*     */   public void sign(XMLSignContext xMLSignContext) throws MarshalException, XMLSignatureException {
/*     */     SignatureMethod sm;
/* 209 */     if (xMLSignContext == null) {
/* 210 */       throw new NullPointerException("signContext cannot be null");
/*     */     }
/*     */ 
/*     */     
/* 214 */     SignedInfo si = getSignedInfo();
/* 215 */     List<Reference> refList = si.getReferences();
/* 216 */     for (int i = 0, size = refList.size(); i < size; i++) {
/* 217 */       Reference ref = refList.get(i);
/* 218 */       ref.digest(xMLSignContext);
/*     */     } 
/*     */ 
/*     */     
/* 222 */     Key signingKey = null;
/* 223 */     KeySelectorResult keySelectoResult = null;
/*     */     try {
/* 225 */       sm = si.getSignatureMethod();
/* 226 */       keySelectoResult = xMLSignContext.getKeySelector().select((KeyInfo)getKeyInfo(), KeySelector.Purpose.SIGN, sm, xMLSignContext);
/* 227 */       signingKey = keySelectoResult.getKey();
/* 228 */       if (signingKey == null) {
/* 229 */         throw new XMLSignatureException("The KeySelector" + xMLSignContext.getKeySelector() + " did not " + "find the key used for signing");
/*     */       }
/*     */     }
/* 232 */     catch (KeySelectorException kse) {
/* 233 */       throw new XMLSignatureException("Cannot find signing key", kse);
/*     */     } 
/*     */     
/* 236 */     if (this._sp == null) {
/*     */       try {
/* 238 */         JAXBContext jc = JAXBUtil.getJAXBContext();
/* 239 */         this._sp = new SignatureProcessor();
/* 240 */         this._sp.setJAXBContext(jc);
/* 241 */         this._sp.setCryptoContext(xMLSignContext);
/* 242 */       } catch (Exception ex) {
/* 243 */         throw new XMLSignatureException(ex);
/*     */       } 
/*     */     }
/*     */     
/* 247 */     String signatureAlgo = sm.getAlgorithm();
/*     */     
/* 249 */     String algo = getRSASignatureAlgorithm(signatureAlgo);
/* 250 */     if (algo != null) {
/*     */       try {
/* 252 */         SignatureValue sigValue = new SignatureValue();
/* 253 */         sigValue.setValue(this._sp.performRSASign(signingKey, this.signedInfo, algo));
/* 254 */         setSignatureValue(sigValue);
/*     */       
/*     */       }
/* 257 */       catch (InvalidKeyException ex) {
/* 258 */         throw new XMLSignatureException(ex);
/*     */       } 
/* 260 */     } else if (signatureAlgo.equals("http://www.w3.org/2000/09/xmldsig#dsa-sha1")) {
/*     */       try {
/* 262 */         SignatureValue sigValue = new SignatureValue();
/* 263 */         sigValue.setValue(this._sp.performDSASign(signingKey, this.signedInfo));
/* 264 */         setSignatureValue(sigValue);
/*     */ 
/*     */       
/*     */       }
/* 268 */       catch (InvalidKeyException ex) {
/* 269 */         throw new XMLSignatureException(ex);
/*     */       }
/*     */     
/* 272 */     } else if (signatureAlgo.equals("http://www.w3.org/2000/09/xmldsig#hmac-sha1")) {
/* 273 */       SignatureMethodParameterSpec params = (SignatureMethodParameterSpec)sm.getParameterSpec();
/* 274 */       int outputLength = -1;
/* 275 */       if (params != null) {
/* 276 */         if (!(params instanceof HMACParameterSpec)) {
/* 277 */           throw new XMLSignatureException("SignatureMethodParameterSpec must be of type HMACParameterSpec");
/*     */         }
/*     */         
/* 280 */         outputLength = ((HMACParameterSpec)params).getOutputLength();
/*     */       } 
/*     */       
/*     */       try {
/* 284 */         SignatureValue sigValue = new SignatureValue();
/* 285 */         sigValue.setValue(this._sp.performHMACSign(signingKey, this.signedInfo, outputLength));
/* 286 */         setSignatureValue(sigValue);
/* 287 */       } catch (InvalidKeyException ex) {
/* 288 */         throw new XMLSignatureException(ex);
/*     */       } 
/*     */     } else {
/* 291 */       throw new XMLSignatureException("Unsupported signature algorithm found");
/*     */     } 
/*     */   }
/*     */   
/*     */   public KeySelectorResult getKeySelectorResult() {
/* 296 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFeatureSupported(String string) {
/* 302 */     return false;
/*     */   }
/*     */   
/*     */   public XMLSignature.SignatureValue getSignatureValue() {
/* 306 */     return this.signatureValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignedInfo getSignedInfo() {
/* 311 */     return this.signedInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyInfo getKeyInfo() {
/* 316 */     return this.keyInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setObjects(List<XMLObject> objects) {
/* 321 */     this.objects = objects;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/* 326 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setType(String type) {
/* 331 */     this.type = type;
/*     */   }
/*     */   
/*     */   private String getRSASignatureAlgorithm(String signatureAlgo) {
/* 335 */     if (signatureAlgo.equals("http://www.w3.org/2000/09/xmldsig#rsa-sha1"))
/* 336 */       return "SHA1withRSA"; 
/* 337 */     if (signatureAlgo.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"))
/* 338 */       return "SHA256withRSA"; 
/* 339 */     if (signatureAlgo.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384"))
/* 340 */       return "SHA384withRSA"; 
/* 341 */     if (signatureAlgo.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512")) {
/* 342 */       return "SHA512withRSA";
/*     */     }
/* 344 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\Signature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */