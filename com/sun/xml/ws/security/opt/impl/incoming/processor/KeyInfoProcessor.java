/*     */ package com.sun.xml.ws.security.opt.impl.incoming.processor;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Base64Data;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.EncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.LogStringsMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.security.Key;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class KeyInfoProcessor
/*     */ {
/*  78 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt", "com.sun.xml.wss.logging.impl.opt.LogStrings");
/*     */ 
/*     */   
/*  81 */   private static String KEYINFO = "KeyInfo".intern();
/*  82 */   private static String SECURITY_TOKEN_REFERENCE = "SecurityTokenReference".intern();
/*     */   private static final int SECURITY_TOKEN_REFERENCE_ELEMENT = 3;
/*     */   private static final int ENCRYPTED_KEY_ELEMENT = 4;
/*     */   private static final int KEY_VALUE_ELEMENT = 5;
/*     */   private static final int RSA_KEY_VALUE_ELEMENT = 6;
/*     */   private static final int DSA_KEY_VALUE_ELEMENT = 7;
/*     */   private static final int MODULUS_ELEMENT = 8;
/*     */   private static final int EXPONENT_ELEMENT = 9;
/*     */   private static final int X509_DATA_ELEMENT = 10;
/*     */   private static final int BINARY_SECRET_ELEMENT = 11;
/*     */   private static final String RSA_KEY_VALUE = "RSAKeyValue";
/*     */   private static final String DSA_KEY_VALUE = "DSAKeyValue";
/*     */   private static final String ENCRYPTED_KEY = "EncryptedKey";
/*     */   private static final String KEY_VALUE = "KeyValue";
/*     */   private static final String EXPONENT = "Exponent";
/*     */   private static final String MODULUS = "Modulus";
/*     */   private static final String X509_DATA = "X509Data";
/*     */   private static final String X509Certificate = "X509Certificate";
/*     */   private static final String BINARY_SECRET = "BinarySecret";
/*     */   private boolean strPresent = false;
/* 102 */   private JAXBFilterProcessingContext pc = null;
/* 103 */   private XMLStreamWriter canonWriter = null;
/*     */   
/*     */   private boolean isSAMLSubjectConfirmationKeyInfo = false;
/*     */   
/* 107 */   private KeySelector.Purpose purpose = null;
/*     */   
/*     */   public KeyInfoProcessor(JAXBFilterProcessingContext pc) {
/* 110 */     this.pc = pc;
/* 111 */     ((NamespaceContextEx)pc.getNamespaceContext()).addSignatureNS();
/*     */   }
/*     */   
/*     */   public KeyInfoProcessor(JAXBFilterProcessingContext pc, XMLStreamWriter canonWriter, KeySelector.Purpose purpose) {
/* 115 */     this.pc = pc;
/* 116 */     this.canonWriter = canonWriter;
/* 117 */     this.purpose = purpose;
/*     */   }
/*     */   
/*     */   public KeyInfoProcessor(JAXBFilterProcessingContext pc, KeySelector.Purpose purpose) {
/* 121 */     this.pc = pc;
/* 122 */     this.purpose = purpose;
/*     */   }
/*     */   
/*     */   public KeyInfoProcessor(JAXBFilterProcessingContext pc, KeySelector.Purpose purpose, boolean isSAMLSCKey) {
/* 126 */     this.pc = pc;
/* 127 */     this.purpose = purpose;
/* 128 */     this.isSAMLSubjectConfirmationKeyInfo = isSAMLSCKey;
/*     */   }
/*     */   
/*     */   public Key getKey(XMLStreamReader reader) throws XMLStreamException, XWSSecurityException {
/* 132 */     return processKeyInfo(reader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Key processKeyInfo(XMLStreamReader reader) throws XMLStreamException, XWSSecurityException {
/* 143 */     Key retKey = null;
/* 144 */     if (this.canonWriter != null)
/* 145 */       StreamUtil.writeStartElement(reader, this.canonWriter); 
/* 146 */     while (reader.hasNext() && !StreamUtil._break(reader, KEYINFO, "http://www.w3.org/2000/09/xmldsig#")) {
/* 147 */       SecurityTokenProcessor stp; EncryptedKey ek; String dataEncAlgo; reader.next();
/* 148 */       int eventType = getEventType(reader);
/* 149 */       switch (eventType) {
/*     */         case 3:
/* 151 */           stp = new SecurityTokenProcessor(this.pc, this.canonWriter, this.purpose);
/* 152 */           retKey = stp.resolveReference(reader);
/* 153 */           this.strPresent = true;
/*     */ 
/*     */         
/*     */         case 4:
/* 157 */           ek = new EncryptedKey(reader, this.pc, null, true);
/* 158 */           dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
/* 159 */           if (this.pc.getAlgorithmSuite() != null) {
/* 160 */             dataEncAlgo = this.pc.getAlgorithmSuite().getEncryptionAlgorithm();
/*     */           }
/* 162 */           retKey = ek.getKey(dataEncAlgo);
/*     */ 
/*     */         
/*     */         case 5:
/* 166 */           if (this.canonWriter != null) {
/* 167 */             StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */           }
/* 169 */           retKey = (new KeyValueProcessor(this.pc, this.canonWriter)).processKeyValue(reader);
/*     */ 
/*     */           
/* 172 */           if (!this.isSAMLSubjectConfirmationKeyInfo && this.purpose == KeySelector.Purpose.VERIFY) {
/* 173 */             X509Certificate cer = null;
/*     */             try {
/* 175 */               cer = this.pc.getSecurityEnvironment().getCertificate(this.pc.getExtraneousProperties(), (PublicKey)retKey, false);
/* 176 */             } catch (XWSSecurityException ex) {}
/*     */ 
/*     */ 
/*     */             
/* 180 */             if (cer != null) {
/* 181 */               this.pc.getSecurityEnvironment().validateCertificate(cer, this.pc.getExtraneousProperties());
/*     */             }
/* 183 */             this.pc.getSecurityContext().setInferredKB((MLSPolicy)new AuthenticationTokenPolicy.KeyValueTokenBinding());
/*     */           } 
/*     */ 
/*     */         
/*     */         case 11:
/* 188 */           reader.next();
/* 189 */           retKey = buildBinarySecret(reader);
/*     */ 
/*     */         
/*     */         case 10:
/* 193 */           if (this.canonWriter != null) {
/* 194 */             StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */           }
/* 196 */           StreamUtil.moveToNextStartOREndElement(reader, this.canonWriter);
/* 197 */           if (reader.getLocalName() == "X509Certificate" && reader.getNamespaceURI() == "http://www.w3.org/2000/09/xmldsig#") {
/* 198 */             reader.next();
/* 199 */             StringBuffer sb = null;
/* 200 */             byte[] value = null;
/* 201 */             CharSequence charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 202 */             if (charSeq instanceof Base64Data) {
/* 203 */               Base64Data bd = (Base64Data)((XMLStreamReaderEx)reader).getPCDATA();
/* 204 */               value = bd.getExact();
/* 205 */               if (this.canonWriter != null) {
/* 206 */                 String ev = Base64.encode(value);
/* 207 */                 this.canonWriter.writeCharacters(ev);
/*     */               }  continue;
/*     */             } 
/* 210 */             sb = new StringBuffer();
/* 211 */             while (reader.getEventType() == 4 && reader.getEventType() != 2) {
/* 212 */               charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 213 */               for (int i = 0; i < charSeq.length(); i++) {
/* 214 */                 sb.append(charSeq.charAt(i));
/*     */               }
/* 216 */               reader.next();
/*     */             } 
/* 218 */             String dv = sb.toString();
/* 219 */             if (this.canonWriter != null) {
/* 220 */               this.canonWriter.writeCharacters(dv);
/*     */             }
/*     */             try {
/* 223 */               value = Base64.decode(dv);
/* 224 */             } catch (Base64DecodingException dec) {
/* 225 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1606_ERROR_RSAKEYINFO_BASE_64_DECODING("MODULUS"), dec);
/* 226 */               throw new XWSSecurityException(LogStringsMessages.WSS_1606_ERROR_RSAKEYINFO_BASE_64_DECODING("MODULUS"));
/*     */             } 
/* 228 */             X509Certificate cert = buildCertificate(new ByteArrayInputStream(value));
/* 229 */             if (this.purpose == KeySelector.Purpose.DECRYPT) {
/* 230 */               retKey = this.pc.getSecurityEnvironment().getPrivateKey(this.pc.getExtraneousProperties(), cert);
/*     */             }
/* 232 */             else if (this.purpose == KeySelector.Purpose.VERIFY) {
/* 233 */               retKey = cert.getPublicKey();
/*     */             } 
/*     */             
/* 236 */             if (!this.isSAMLSubjectConfirmationKeyInfo && this.purpose == KeySelector.Purpose.VERIFY)
/*     */             {
/*     */               
/* 239 */               this.pc.getSecurityEnvironment().validateCertificate(cert, this.pc.getExtraneousProperties());
/*     */             }
/*     */           } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 248 */     if (reader.hasNext() && 
/* 249 */       this.canonWriter != null) {
/* 250 */       StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */     }
/*     */     
/* 253 */     reader.next();
/* 254 */     return retKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Key buildBinarySecret(XMLStreamReader reader) throws XWSSecurityException, XMLStreamException {
/* 264 */     byte[] value = null;
/* 265 */     if (reader.getEventType() == 4) {
/* 266 */       if (reader instanceof XMLStreamReaderEx) {
/* 267 */         CharSequence charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 268 */         if (charSeq instanceof Base64Data) {
/* 269 */           Base64Data bd = (Base64Data)((XMLStreamReaderEx)reader).getPCDATA();
/* 270 */           value = bd.getExact();
/* 271 */           if (this.canonWriter != null) {
/* 272 */             String ev = Base64.encode(value);
/* 273 */             this.canonWriter.writeCharacters(ev);
/*     */           } 
/*     */         } else {
/* 276 */           String dv = readCharacters(reader);
/*     */           try {
/* 278 */             value = Base64.decode(dv);
/* 279 */           } catch (Base64DecodingException dec) {
/* 280 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1606_ERROR_RSAKEYINFO_BASE_64_DECODING("MODULUS"), dec);
/* 281 */             throw new XWSSecurityException(LogStringsMessages.WSS_1606_ERROR_RSAKEYINFO_BASE_64_DECODING("MODULUS"));
/*     */           } 
/*     */         } 
/*     */       } else {
/* 285 */         String dv = readCharacters(reader);
/*     */         try {
/* 287 */           value = Base64.decode(dv);
/* 288 */         } catch (Base64DecodingException dec) {
/* 289 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1606_ERROR_RSAKEYINFO_BASE_64_DECODING("MODULUS"), dec);
/* 290 */           throw new XWSSecurityException(LogStringsMessages.WSS_1606_ERROR_RSAKEYINFO_BASE_64_DECODING("MODULUS"));
/*     */         } 
/*     */       } 
/*     */     }
/* 294 */     String algorithm = "AES";
/* 295 */     if (this.pc.getAlgorithmSuite() != null) {
/* 296 */       algorithm = SecurityUtil.getSecretKeyAlgorithm(this.pc.getAlgorithmSuite().getEncryptionAlgorithm());
/*     */     }
/* 298 */     return new SecretKeySpec(value, algorithm);
/*     */   }
/*     */   
/*     */   private String readCharacters(XMLStreamReader reader) throws XMLStreamException {
/* 302 */     StringBuffer sb = new StringBuffer();
/* 303 */     while (reader.getEventType() == 4 && reader.getEventType() != 2) {
/* 304 */       CharSequence charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 305 */       for (int i = 0; i < charSeq.length(); i++) {
/* 306 */         sb.append(charSeq.charAt(i));
/*     */       }
/* 308 */       reader.next();
/*     */     } 
/* 310 */     String dv = sb.toString();
/* 311 */     if (this.canonWriter != null) {
/* 312 */       this.canonWriter.writeCharacters(dv);
/*     */     }
/* 314 */     return dv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private X509Certificate buildCertificate(InputStream certValue) throws XWSSecurityException {
/*     */     try {
/* 325 */       CertificateFactory certFact = CertificateFactory.getInstance("X.509");
/* 326 */       return (X509Certificate)certFact.generateCertificate(certValue);
/* 327 */     } catch (CertificateException ex) {
/* 328 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1605_ERROR_GENERATING_CERTIFICATE(ex), ex);
/* 329 */       throw new XWSSecurityException(LogStringsMessages.WSS_1605_ERROR_GENERATING_CERTIFICATE(ex));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getEventType(XMLStreamReader reader) throws XMLStreamException {
/* 340 */     if (reader.getEventType() == 1) {
/* 341 */       if (reader.getLocalName() == SECURITY_TOKEN_REFERENCE) {
/* 342 */         return 3;
/*     */       }
/* 344 */       if (reader.getLocalName() == "EncryptedKey") {
/* 345 */         return 4;
/*     */       }
/* 347 */       if (reader.getLocalName() == "KeyValue") {
/* 348 */         return 5;
/*     */       }
/* 350 */       if (reader.getLocalName() == "X509Data") {
/* 351 */         return 10;
/*     */       }
/* 353 */       if (reader.getLocalName() == "BinarySecret") {
/* 354 */         return 11;
/*     */       }
/*     */     } 
/* 357 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean hasSTR() {
/* 361 */     return this.strPresent;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\processor\KeyInfoProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */