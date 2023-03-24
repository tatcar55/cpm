/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.PolicyBuilder;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.CryptoProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.CipherDataProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.KeyInfoProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.ReferenceListProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.logging.impl.opt.crypto.LogStringsMessages;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Key;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedKey
/*     */   implements SecurityHeaderElement, NamespaceContextInfo, SecurityElementWriter, PolicyBuilder
/*     */ {
/*  82 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.crypto", "com.sun.xml.wss.logging.impl.opt.crypto.LogStrings");
/*     */ 
/*     */   
/*  85 */   private static final String ENCRYPTION_METHOD = "EncryptionMethod".intern();
/*  86 */   private static final String REFERENCE_LIST = "ReferenceList".intern();
/*  87 */   private static final String CIPHER_DATA = "CipherData".intern();
/*  88 */   private static final String KEYINFO = "KeyInfo".intern();
/*  89 */   private static final String DIGEST_METHOD = "DigestMethod".intern();
/*  90 */   private static final String KEY_SIZE = "KeySize".intern();
/*     */   
/*     */   private static final int KEYINFO_ELEMENT = 1;
/*     */   
/*     */   private static final int ENCRYPTIONMETHOD_ELEMENT = 2;
/*     */   
/*     */   private static final int REFERENCE_LIST_ELEMENT = 4;
/*     */   private static final int CIPHER_DATA_ELEMENT = 5;
/*     */   private static final int KEY_SIZE_ELEMENT = 6;
/*     */   private static final int DIGEST_METHOD_ELEMENT = 7;
/* 100 */   private String id = "";
/* 101 */   private String namespaceURI = "";
/* 102 */   private String localName = "";
/* 103 */   private String encryptionMethod = "";
/* 104 */   private Key keyEncKey = null;
/* 105 */   private ArrayList<String> referenceList = null;
/* 106 */   private JAXBFilterProcessingContext pc = null;
/* 107 */   private Key dataEncKey = null;
/* 108 */   private ArrayList<String> pendingRefList = null;
/*     */   
/*     */   private HashMap<String, String> nsDecls;
/*     */   
/* 112 */   private CryptoProcessor cp = null;
/* 113 */   private CipherDataProcessor cdp = null;
/*     */   
/* 115 */   private EncryptionPolicy encPolicy = null;
/* 116 */   private WSSPolicy inferredKB = null;
/*     */   
/*     */   private boolean ignoreEKSHA1 = false;
/*     */   private boolean emPresent = false;
/*     */   
/*     */   public EncryptedKey(XMLStreamReader reader, JAXBFilterProcessingContext pc, HashMap<String, String> nsDecls) throws XMLStreamException, XWSSecurityException {
/* 122 */     this.pc = pc;
/* 123 */     this.nsDecls = nsDecls;
/* 124 */     process(reader);
/*     */   }
/*     */   
/*     */   public EncryptedKey(XMLStreamReader reader, JAXBFilterProcessingContext pc, HashMap<String, String> nsDecls, boolean ignoreEKSHA1) throws XMLStreamException, XWSSecurityException {
/* 128 */     this.pc = pc;
/* 129 */     this.ignoreEKSHA1 = ignoreEKSHA1;
/* 130 */     this.nsDecls = nsDecls;
/* 131 */     process(reader);
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 135 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId() {
/* 139 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 143 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 147 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 151 */     return this.localName;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 155 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 159 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 163 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private void process(XMLStreamReader reader) throws XMLStreamException, XWSSecurityException {
/* 167 */     this.id = reader.getAttributeValue(null, "Id");
/*     */     
/* 169 */     if (this.pc.isBSP()) {
/* 170 */       String tmp = reader.getAttributeValue(null, "Recipient");
/* 171 */       if (tmp != null) {
/*     */         
/* 173 */         logger.log(Level.SEVERE, LogStringsMessages.BSP_5602_ENCRYPTEDKEY_RECIPIENT(this.id));
/* 174 */         throw new XWSSecurityException(LogStringsMessages.BSP_5602_ENCRYPTEDKEY_RECIPIENT(this.id));
/*     */       } 
/*     */       
/* 177 */       String mt = reader.getAttributeValue(null, "MimeType");
/* 178 */       if (mt != null) {
/*     */         
/* 180 */         logger.log(Level.SEVERE, LogStringsMessages.BSP_5622_ENCRYPTEDKEY_MIMETYPE(this.id));
/* 181 */         throw new XWSSecurityException(LogStringsMessages.BSP_5622_ENCRYPTEDKEY_MIMETYPE(this.id));
/*     */       } 
/*     */       
/* 184 */       String et = reader.getAttributeValue(null, "Encoding");
/* 185 */       if (et != null) {
/*     */         
/* 187 */         logger.log(Level.SEVERE, LogStringsMessages.BSP_5623_ENCRYPTEDKEY_ENCODING(this.id));
/* 188 */         throw new XWSSecurityException(LogStringsMessages.BSP_5623_ENCRYPTEDKEY_ENCODING(this.id));
/*     */       } 
/*     */     } 
/* 191 */     this.namespaceURI = reader.getNamespaceURI();
/* 192 */     this.localName = reader.getLocalName();
/*     */     
/* 194 */     if (StreamUtil.moveToNextElement(reader)) {
/* 195 */       int refElement = getEventType(reader);
/* 196 */       while (reader.getEventType() != 8) {
/* 197 */         KeyInfoProcessor kip; ReferenceListProcessor rlp; switch (refElement) {
/*     */           case 2:
/* 199 */             processEncryptionMethod(reader);
/* 200 */             reader.next();
/* 201 */             this.emPresent = true;
/*     */             break;
/*     */           
/*     */           case 1:
/* 205 */             this.pc.getSecurityContext().setInferredKB(null);
/* 206 */             this.pc.setExtraneousProperty("EncryptedKey", "true");
/* 207 */             kip = new KeyInfoProcessor(this.pc, KeySelector.Purpose.DECRYPT);
/* 208 */             this.keyEncKey = kip.getKey(reader);
/* 209 */             this.pc.removeExtraneousProperty("EncryptedKey");
/* 210 */             this.inferredKB = (WSSPolicy)this.pc.getSecurityContext().getInferredKB();
/* 211 */             this.pc.getSecurityContext().setInferredKB(null);
/*     */             break;
/*     */           
/*     */           case 4:
/* 215 */             this.encPolicy = new EncryptionPolicy();
/* 216 */             this.encPolicy.setFeatureBinding((MLSPolicy)new EncryptionPolicy.FeatureBinding());
/* 217 */             rlp = new ReferenceListProcessor(this.encPolicy);
/* 218 */             rlp.process(reader);
/* 219 */             this.referenceList = rlp.getReferences();
/* 220 */             this.pendingRefList = (ArrayList<String>)this.referenceList.clone();
/*     */             break;
/*     */           
/*     */           case 5:
/* 224 */             this.cdp = new CipherDataProcessor(this.pc);
/* 225 */             this.cdp.process(reader);
/* 226 */             if (!this.ignoreEKSHA1) {
/*     */               try {
/* 228 */                 byte[] decodedCipher = this.cdp.readAsBytes();
/* 229 */                 byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(decodedCipher);
/* 230 */                 String encEkSha1 = Base64.encode(ekSha1);
/* 231 */                 if (!this.pc.isSAMLEK())
/*     */                 {
/* 233 */                   if (this.pc.getExtraneousProperty("EKSHA1Value") == null) {
/* 234 */                     this.pc.setExtraneousProperty("EKSHA1Value", encEkSha1);
/*     */                   }
/*     */                 }
/* 237 */               } catch (NoSuchAlgorithmException nsae) {
/* 238 */                 throw new XWSSecurityException(nsae);
/*     */               } 
/*     */             }
/* 241 */             this.cp = new CryptoProcessor(4, this.encryptionMethod, this.keyEncKey);
/*     */             break;
/*     */ 
/*     */           
/*     */           default:
/* 246 */             if (StreamUtil.isStartElement(reader)) {
/* 247 */               throw new XWSSecurityException("Element name " + reader.getName() + " is not recognized under EncryptedKey");
/*     */             }
/*     */             break;
/*     */         } 
/*     */         
/* 252 */         if (shouldBreak(reader)) {
/*     */           break;
/*     */         }
/* 255 */         if (reader.getEventType() == 1) {
/* 256 */           if (getEventType(reader) == -1) {
/* 257 */             reader.next();
/*     */           }
/*     */         } else {
/* 260 */           reader.next();
/*     */         } 
/* 262 */         refElement = getEventType(reader);
/*     */       } 
/*     */     } 
/*     */     
/* 266 */     if (this.pc.isBSP() && 
/* 267 */       this.emPresent) {
/* 268 */       logger.log(Level.SEVERE, LogStringsMessages.BSP_5603_ENCRYPTEDKEY_ENCRYPTIONMEHOD(this.id));
/* 269 */       throw new XWSSecurityException(LogStringsMessages.BSP_5603_ENCRYPTEDKEY_ENCRYPTIONMEHOD(this.id));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldBreak(XMLStreamReader reader) throws XMLStreamException {
/* 275 */     if (StreamUtil._break(reader, "EncryptedKey", "http://www.w3.org/2001/04/xmlenc#")) {
/* 276 */       return true;
/*     */     }
/* 278 */     if (reader.getEventType() == 8) {
/* 279 */       return true;
/*     */     }
/* 281 */     return false;
/*     */   }
/*     */   
/*     */   private void processEncryptionMethod(XMLStreamReader reader) throws XMLStreamException, XWSSecurityException {
/* 285 */     this.encryptionMethod = reader.getAttributeValue(null, "Algorithm");
/* 286 */     if (this.encryptionMethod == null || this.encryptionMethod.length() == 0) {
/* 287 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1925_EMPTY_ENCMETHOD_ED());
/* 288 */       throw new XWSSecurityException(LogStringsMessages.WSS_1925_EMPTY_ENCMETHOD_ED());
/*     */     } 
/*     */     
/* 291 */     while (reader.getEventType() != 8) {
/* 292 */       int eventType = getEventType(reader);
/* 293 */       switch (eventType) {
/*     */       
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       if (reader.getEventType() == 2 && reader.getLocalName() == ENCRYPTION_METHOD) {
/*     */         break;
/*     */       }
/* 309 */       reader.next();
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getEventType(XMLStreamReader reader) {
/* 314 */     if (reader.getEventType() == 1) {
/* 315 */       if (reader.getLocalName() == ENCRYPTION_METHOD) {
/* 316 */         return 2;
/*     */       }
/*     */       
/* 319 */       if (reader.getLocalName() == CIPHER_DATA) {
/* 320 */         return 5;
/*     */       }
/* 322 */       if (reader.getLocalName() == REFERENCE_LIST) {
/* 323 */         return 4;
/*     */       }
/*     */       
/* 326 */       if (reader.getLocalName() == KEYINFO) {
/* 327 */         return 1;
/*     */       }
/*     */       
/* 330 */       if (reader.getLocalName() == DIGEST_METHOD) {
/* 331 */         return 7;
/*     */       }
/* 333 */       if (reader.getLocalName() == KEY_SIZE) {
/* 334 */         return 6;
/*     */       }
/*     */     } 
/* 337 */     return -1;
/*     */   }
/*     */   
/*     */   public List<String> getReferenceList() {
/* 341 */     return this.referenceList;
/*     */   }
/*     */   
/*     */   public List<String> getPendingReferenceList() {
/* 345 */     return this.pendingRefList;
/*     */   }
/*     */   
/*     */   public Key getKey(String encAlgo) throws XWSSecurityException {
/* 349 */     if (this.dataEncKey == null) {
/*     */       try {
/* 351 */         this.dataEncKey = this.cp.decryptKey(this.cdp.readAsBytes(), encAlgo);
/* 352 */       } catch (IOException ex) {
/* 353 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1927_ERROR_DECRYPT_ED("EncryptedKey"));
/* 354 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1927_ERROR_DECRYPT_ED("EncryptedKey"), ex);
/*     */       } 
/* 356 */       if (!this.ignoreEKSHA1)
/*     */       {
/* 358 */         if (this.pc.getExtraneousProperty("SecretKeyValue") == null) {
/* 359 */           this.pc.setExtraneousProperty("SecretKeyValue", this.dataEncKey);
/*     */         }
/*     */       }
/*     */     } 
/* 363 */     return this.dataEncKey;
/*     */   }
/*     */   
/*     */   public HashMap<String, String> getInscopeNSContext() {
/* 367 */     return this.nsDecls;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 371 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public WSSPolicy getPolicy() {
/* 375 */     return (WSSPolicy)this.encPolicy;
/*     */   }
/*     */   
/*     */   public WSSPolicy getInferredKB() {
/* 379 */     return this.inferredKB;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\EncryptedKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */