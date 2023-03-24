/*     */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.internal.HmacSHA1;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.internal.MacOutputStream;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.internal.SignerOutputStream;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceAndPrefixMapper;
/*     */ import com.sun.xml.ws.security.opt.impl.util.WSSNamespacePrefixMapper;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import com.sun.xml.wss.impl.c14n.EXC14nStAXReaderBasedCanonicalizer;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
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
/*     */ public class SignatureProcessor
/*     */ {
/*  83 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */   
/*     */   private JAXBContext _jaxbContext;
/*     */   
/*  87 */   StAXEXC14nCanonicalizerImpl _exc14nCanonicalizer = new StAXEXC14nCanonicalizerImpl();
/*     */   EXC14nStAXReaderBasedCanonicalizer _exc14nSBCanonicalizer;
/*  89 */   XMLCryptoContext context = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private Signature _rsaSignature;
/*     */ 
/*     */ 
/*     */   
/*     */   private Signature _dsaSignature;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJAXBContext(JAXBContext _jaxbContext) {
/* 102 */     this._jaxbContext = _jaxbContext;
/*     */   }
/*     */   
/*     */   public JAXBContext getJAXBContext() {
/* 106 */     return this._jaxbContext;
/*     */   }
/*     */   
/*     */   public void setCryptoContext(XMLCryptoContext context) {
/* 110 */     this.context = context;
/*     */   }
/*     */   
/*     */   public byte[] performRSASign(Key privateKey, SignedInfo signedInfo, String signatureAlgo) throws InvalidKeyException {
/* 114 */     if (privateKey == null || signedInfo == null) {
/* 115 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 118 */     if (!(privateKey instanceof PrivateKey)) {
/* 119 */       throw new InvalidKeyException("key must be PrivateKey");
/*     */     }
/*     */     
/* 122 */     if (this._rsaSignature == null) {
/*     */       try {
/* 124 */         this._rsaSignature = Signature.getInstance(signatureAlgo);
/* 125 */       } catch (NoSuchAlgorithmException ex) {
/*     */         
/* 127 */         throw new XWSSecurityRuntimeException(ex);
/*     */       } 
/*     */     }
/*     */     
/* 131 */     this._rsaSignature.initSign((PrivateKey)privateKey);
/*     */     
/* 133 */     SignerOutputStream signerOutputStream = new SignerOutputStream(this._rsaSignature);
/*     */     
/*     */     try {
/* 136 */       Marshaller marshaller = getMarshaller();
/* 137 */       this._exc14nCanonicalizer.reset();
/*     */       
/* 139 */       setNamespaceAndPrefixList();
/*     */       
/* 141 */       this._exc14nCanonicalizer.setStream((OutputStream)signerOutputStream);
/* 142 */       marshaller.marshal(signedInfo, (XMLStreamWriter)this._exc14nCanonicalizer);
/* 143 */       if (logger.isLoggable(Level.FINEST)) {
/* 144 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 145 */         this._exc14nCanonicalizer.reset();
/* 146 */         this._exc14nCanonicalizer.setStream(baos);
/* 147 */         marshaller.marshal(signedInfo, (XMLStreamWriter)this._exc14nCanonicalizer);
/* 148 */         logger.log(Level.FINEST, LogStringsMessages.WSS_1756_CANONICALIZED_SIGNEDINFO_VALUE(baos.toString()));
/*     */       } 
/* 150 */     } catch (JAXBException ex) {
/* 151 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 156 */       return this._rsaSignature.sign();
/*     */     }
/* 158 */     catch (SignatureException se) {
/*     */       
/* 160 */       throw new RuntimeException(se.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] performHMACSign(Key key, SignedInfo signedInfo, int outputLength) throws InvalidKeyException {
/* 166 */     if (key == null || signedInfo == null) {
/* 167 */       throw new NullPointerException();
/*     */     }
/* 169 */     HmacSHA1 hmac = new HmacSHA1();
/* 170 */     hmac.init(key, outputLength);
/*     */     
/* 172 */     MacOutputStream macOutputStream = new MacOutputStream(hmac);
/*     */     
/*     */     try {
/* 175 */       Marshaller marshaller = getMarshaller();
/* 176 */       this._exc14nCanonicalizer.reset();
/* 177 */       setNamespaceAndPrefixList();
/* 178 */       this._exc14nCanonicalizer.setStream((OutputStream)macOutputStream);
/* 179 */       marshaller.marshal(signedInfo, (XMLStreamWriter)this._exc14nCanonicalizer);
/* 180 */       if (logger.isLoggable(Level.FINEST)) {
/* 181 */         marshaller = getMarshaller();
/* 182 */         this._exc14nCanonicalizer.reset();
/* 183 */         setNamespaceAndPrefixList();
/* 184 */         ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 185 */         this._exc14nCanonicalizer.setStream(bos);
/* 186 */         marshaller.marshal(signedInfo, (XMLStreamWriter)this._exc14nCanonicalizer);
/* 187 */         logger.log(Level.FINEST, LogStringsMessages.WSS_1756_CANONICALIZED_SIGNEDINFO_VALUE(bos.toString()));
/*     */       } 
/* 189 */     } catch (JAXBException ex) {
/* 190 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/*     */     try {
/* 193 */       return hmac.sign();
/*     */     }
/* 195 */     catch (SignatureException se) {
/*     */       
/* 197 */       throw new RuntimeException(se.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] performDSASign(Key privateKey, SignedInfo signedInfo) throws InvalidKeyException {
/* 202 */     if (privateKey == null || signedInfo == null) {
/* 203 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 206 */     if (!(privateKey instanceof PrivateKey)) {
/* 207 */       throw new InvalidKeyException("key must be PrivateKey");
/*     */     }
/*     */     
/* 210 */     if (this._dsaSignature == null) {
/*     */       try {
/* 212 */         this._dsaSignature = Signature.getInstance("SHA1withDSA");
/* 213 */       } catch (NoSuchAlgorithmException ex) {
/*     */         
/* 215 */         throw new XWSSecurityRuntimeException(ex);
/*     */       } 
/*     */     }
/*     */     
/* 219 */     this._dsaSignature.initSign((PrivateKey)privateKey);
/*     */     
/* 221 */     SignerOutputStream signerOutputStream = new SignerOutputStream(this._dsaSignature);
/*     */     
/*     */     try {
/* 224 */       Marshaller marshaller = getMarshaller();
/* 225 */       this._exc14nCanonicalizer.reset();
/* 226 */       setNamespaceAndPrefixList();
/* 227 */       this._exc14nCanonicalizer.setStream((OutputStream)signerOutputStream);
/* 228 */       marshaller.marshal(signedInfo, (XMLStreamWriter)this._exc14nCanonicalizer);
/* 229 */     } catch (JAXBException ex) {
/* 230 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/*     */     
/*     */     try {
/* 234 */       return convertASN1toXMLDSIG(this._dsaSignature.sign());
/*     */     }
/* 236 */     catch (SignatureException se) {
/*     */       
/* 238 */       throw new RuntimeException(se.getMessage());
/* 239 */     } catch (IOException ioex) {
/* 240 */       throw new RuntimeException(ioex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] convertASN1toXMLDSIG(byte[] asn1Bytes) throws IOException {
/* 249 */     byte rLength = asn1Bytes[3];
/*     */     
/*     */     int i;
/* 252 */     for (i = rLength; i > 0 && asn1Bytes[4 + rLength - i] == 0; i--);
/*     */     
/* 254 */     byte sLength = asn1Bytes[5 + rLength];
/*     */ 
/*     */     
/* 257 */     int j = sLength;
/* 258 */     for (; j > 0 && asn1Bytes[6 + rLength + sLength - j] == 0; j--);
/*     */     
/* 260 */     if (asn1Bytes[0] != 48 || asn1Bytes[1] != asn1Bytes.length - 2 || asn1Bytes[2] != 2 || i > 20 || asn1Bytes[4 + rLength] != 2 || j > 20)
/*     */     {
/*     */       
/* 263 */       throw new IOException("Invalid ASN.1 format of DSA signature");
/*     */     }
/* 265 */     byte[] xmldsigBytes = new byte[40];
/*     */     
/* 267 */     System.arraycopy(asn1Bytes, 4 + rLength - i, xmldsigBytes, 20 - i, i);
/* 268 */     System.arraycopy(asn1Bytes, 6 + rLength + sLength - j, xmldsigBytes, 40 - j, j);
/*     */ 
/*     */     
/* 271 */     return xmldsigBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verifyDSASignature(Key publicKey, SignedInfo si, byte[] signatureValue) throws InvalidKeyException, SignatureException {
/* 277 */     if (!(publicKey instanceof PublicKey)) {
/* 278 */       throw new InvalidKeyException("key must be PublicKey");
/*     */     }
/* 280 */     if (this._dsaSignature == null) {
/*     */       try {
/* 282 */         this._dsaSignature = Signature.getInstance("SHA1withDSA");
/* 283 */       } catch (NoSuchAlgorithmException nsae) {
/* 284 */         throw new SignatureException("SHA1withDSA Signature not found");
/*     */       } 
/*     */     }
/* 287 */     this._dsaSignature.initVerify((PublicKey)publicKey);
/* 288 */     SignerOutputStream sos = new SignerOutputStream(this._dsaSignature);
/* 289 */     if (si.getSignedInfo() != null) {
/* 290 */       XMLStreamReaderEx signedInfo = si.getSignedInfo();
/* 291 */       if (this._exc14nSBCanonicalizer == null) {
/* 292 */         this._exc14nSBCanonicalizer = new EXC14nStAXReaderBasedCanonicalizer();
/*     */       }
/*     */       
/* 295 */       NamespaceContextEx nsContext = signedInfo.getNamespaceContext();
/* 296 */       Iterator<NamespaceContextEx.Binding> itr = nsContext.iterator();
/* 297 */       ArrayList<AttributeNS> list = new ArrayList();
/* 298 */       while (itr.hasNext()) {
/* 299 */         NamespaceContextEx.Binding binding = itr.next();
/* 300 */         AttributeNS ans = new AttributeNS();
/* 301 */         ans.setPrefix(binding.getPrefix());
/* 302 */         ans.setUri(binding.getNamespaceURI());
/* 303 */         list.add(ans);
/*     */       } 
/*     */       
/* 306 */       this._exc14nSBCanonicalizer.addParentNamespaces(list);
/*     */       try {
/* 308 */         this._exc14nSBCanonicalizer.canonicalize((XMLStreamReader)signedInfo, (OutputStream)sos, null);
/* 309 */       } catch (XMLStreamException ex) {
/* 310 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1withDSA"));
/* 311 */         throw new SignatureException(LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1withDSA"), ex);
/* 312 */       } catch (IOException ex) {
/* 313 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1withDSA"));
/* 314 */         throw new SignatureException(LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1withDSA"), ex);
/*     */       } 
/*     */     } else {
/* 317 */       sos.write(si.getCanonicalizedSI());
/*     */     } 
/*     */     try {
/* 320 */       return this._dsaSignature.verify(convertXMLDSIGtoASN1(signatureValue));
/* 321 */     } catch (SignatureException ex) {
/* 322 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1withDSA"));
/* 323 */       throw new SignatureException(LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1withDSA"), ex);
/* 324 */     } catch (IOException ex) {
/* 325 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1withDSA"));
/* 326 */       throw new SignatureException(LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1withDSA"), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verifyHMACSignature(Key key, SignedInfo si, byte[] signatureValue, int outputLength) throws InvalidKeyException, SignatureException {
/* 333 */     if (key == null || si == null || signatureValue == null) {
/* 334 */       throw new NullPointerException("key, signedinfo or signature data can't be null");
/*     */     }
/* 336 */     HmacSHA1 hmac = new HmacSHA1();
/* 337 */     hmac.init(key, outputLength);
/*     */     
/* 339 */     MacOutputStream mos = new MacOutputStream(hmac);
/* 340 */     if (si.getSignedInfo() != null) {
/* 341 */       XMLStreamReaderEx signedInfo = si.getSignedInfo();
/* 342 */       if (this._exc14nSBCanonicalizer == null) {
/* 343 */         this._exc14nSBCanonicalizer = new EXC14nStAXReaderBasedCanonicalizer();
/*     */       }
/* 345 */       NamespaceContextEx nsContext = signedInfo.getNamespaceContext();
/* 346 */       Iterator<NamespaceContextEx.Binding> itr = nsContext.iterator();
/* 347 */       ArrayList<AttributeNS> list = new ArrayList();
/* 348 */       while (itr.hasNext()) {
/* 349 */         NamespaceContextEx.Binding binding = itr.next();
/* 350 */         AttributeNS ans = new AttributeNS();
/* 351 */         ans.setPrefix(binding.getPrefix());
/* 352 */         ans.setUri(binding.getNamespaceURI());
/* 353 */         list.add(ans);
/*     */       } 
/*     */       
/* 356 */       this._exc14nSBCanonicalizer.addParentNamespaces(list);
/* 357 */       this._exc14nSBCanonicalizer.addParentNamespaces(list);
/*     */       
/*     */       try {
/* 360 */         this._exc14nSBCanonicalizer.canonicalize((XMLStreamReader)signedInfo, (OutputStream)mos, null);
/* 361 */       } catch (XMLStreamException ex) {
/* 362 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("HMAC_SHA1"));
/* 363 */         throw new SignatureException(LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("HMAC_SHA1"), ex);
/* 364 */       } catch (IOException ex) {
/* 365 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("HMAC_SHA1"));
/* 366 */         throw new SignatureException(LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("HMAC_SHA1"), ex);
/*     */       } 
/*     */     } else {
/* 369 */       mos.write(si.getCanonicalizedSI());
/*     */     } 
/* 371 */     return hmac.verify(signatureValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean verifyRSASignature(Key publicKey, SignedInfo si, byte[] signatureValue, String signatureAlgo) throws InvalidKeyException, SignatureException {
/* 376 */     if (!(publicKey instanceof PublicKey)) {
/* 377 */       throw new InvalidKeyException("key must be PublicKey");
/*     */     }
/* 379 */     if (this._rsaSignature == null) {
/*     */       try {
/* 381 */         this._rsaSignature = Signature.getInstance(signatureAlgo);
/* 382 */       } catch (NoSuchAlgorithmException nsae) {
/* 383 */         throw new SignatureException("SHA1withRSA Signature not found");
/*     */       } 
/*     */     }
/* 386 */     this._rsaSignature.initVerify((PublicKey)publicKey);
/* 387 */     SignerOutputStream sos = new SignerOutputStream(this._rsaSignature);
/* 388 */     if (si.getSignedInfo() != null) {
/* 389 */       XMLStreamReaderEx signedInfo = si.getSignedInfo();
/* 390 */       if (this._exc14nSBCanonicalizer == null) {
/* 391 */         this._exc14nSBCanonicalizer = new EXC14nStAXReaderBasedCanonicalizer();
/*     */       }
/* 393 */       NamespaceContextEx nsContext = signedInfo.getNamespaceContext();
/* 394 */       Iterator<NamespaceContextEx.Binding> itr = nsContext.iterator();
/* 395 */       ArrayList<AttributeNS> list = new ArrayList();
/* 396 */       while (itr.hasNext()) {
/* 397 */         NamespaceContextEx.Binding binding = itr.next();
/* 398 */         AttributeNS ans = new AttributeNS();
/* 399 */         ans.setPrefix(binding.getPrefix());
/* 400 */         ans.setUri(binding.getNamespaceURI());
/* 401 */         list.add(ans);
/*     */       } 
/*     */ 
/*     */       
/* 405 */       this._exc14nSBCanonicalizer.addParentNamespaces(list);
/*     */       try {
/* 407 */         this._exc14nSBCanonicalizer.canonicalize((XMLStreamReader)signedInfo, (OutputStream)sos, null);
/* 408 */       } catch (XMLStreamException ex) {
/* 409 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1WithRSA"));
/* 410 */         throw new SignatureException(LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1WithRSA"), ex);
/* 411 */       } catch (IOException ex) {
/* 412 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1WithRSA"));
/* 413 */         throw new SignatureException(LogStringsMessages.WSS_1724_SIGTYPE_VERIFICATION_FAILED("SHA1WithRSA"), ex);
/*     */       } 
/*     */     } else {
/* 416 */       sos.write(si.getCanonicalizedSI());
/*     */     } 
/* 418 */     return this._rsaSignature.verify(signatureValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] convertXMLDSIGtoASN1(byte[] xmldsigBytes) throws IOException {
/* 425 */     if (xmldsigBytes.length != 40) {
/* 426 */       throw new IOException("Invalid XMLDSIG format of DSA signature");
/*     */     }
/*     */     
/*     */     int i;
/*     */     
/* 431 */     for (i = 20; i > 0 && xmldsigBytes[20 - i] == 0; i--);
/*     */     
/* 433 */     int j = i;
/*     */     
/* 435 */     if (xmldsigBytes[20 - i] < 0) {
/* 436 */       j++;
/*     */     }
/*     */     
/*     */     int k;
/*     */     
/* 441 */     for (k = 20; k > 0 && xmldsigBytes[40 - k] == 0; k--);
/*     */     
/* 443 */     int l = k;
/*     */     
/* 445 */     if (xmldsigBytes[40 - k] < 0) {
/* 446 */       l++;
/*     */     }
/*     */     
/* 449 */     byte[] asn1Bytes = new byte[6 + j + l];
/*     */     
/* 451 */     asn1Bytes[0] = 48;
/* 452 */     asn1Bytes[1] = (byte)(4 + j + l);
/* 453 */     asn1Bytes[2] = 2;
/* 454 */     asn1Bytes[3] = (byte)j;
/*     */     
/* 456 */     System.arraycopy(xmldsigBytes, 20 - i, asn1Bytes, 4 + j - i, i);
/*     */     
/* 458 */     asn1Bytes[4 + j] = 2;
/* 459 */     asn1Bytes[5 + j] = (byte)l;
/*     */     
/* 461 */     System.arraycopy(xmldsigBytes, 40 - k, asn1Bytes, 6 + j + l - k, k);
/*     */     
/* 463 */     return asn1Bytes;
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 467 */     JAXBFilterProcessingContext wssContext = (JAXBFilterProcessingContext)this.context.get("http://wss.sun.com#processingContext");
/* 468 */     Marshaller marshaller = this._jaxbContext.createMarshaller();
/* 469 */     marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.valueOf(false));
/* 470 */     if (wssContext != null) {
/* 471 */       marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new WSSNamespacePrefixMapper(wssContext.isSOAP12()));
/*     */     } else {
/* 473 */       marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new WSSNamespacePrefixMapper());
/* 474 */     }  marshaller.setProperty("jaxb.fragment", Boolean.valueOf(true));
/* 475 */     return marshaller;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setNamespaceAndPrefixList() {
/* 480 */     NamespaceAndPrefixMapper nsMapper = (NamespaceAndPrefixMapper)this.context.get("NS_And_Prefix_Mapper");
/* 481 */     if (nsMapper != null) {
/* 482 */       NamespaceContextEx nc = nsMapper.getNamespaceContext();
/* 483 */       Iterator<NamespaceContextEx.Binding> itr = nc.iterator();
/* 484 */       while (itr.hasNext()) {
/* 485 */         NamespaceContextEx.Binding nd = itr.next();
/*     */         try {
/* 487 */           this._exc14nCanonicalizer.writeNamespace(nd.getPrefix(), nd.getNamespaceURI());
/* 488 */         } catch (XMLStreamException ex) {
/* 489 */           throw new XWSSecurityRuntimeException(ex);
/*     */         } 
/*     */       } 
/* 492 */       List incList = nsMapper.getInlusivePrefixList();
/* 493 */       this._exc14nCanonicalizer.setInclusivePrefixList(incList);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\SignatureProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */