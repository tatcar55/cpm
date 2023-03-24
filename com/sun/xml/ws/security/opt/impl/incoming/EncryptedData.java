/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.CryptoProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.CipherDataProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.KeyInfoProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.util.CheckedInputStream;
/*     */ import com.sun.xml.ws.security.opt.impl.util.DecryptedInputStream;
/*     */ import com.sun.xml.ws.security.opt.impl.util.FilteredXMLStreamReader;
/*     */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.logging.impl.opt.crypto.LogStringsMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Key;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.stream.XMLInputFactory;
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
/*     */ 
/*     */ public class EncryptedData
/*     */   implements SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  80 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.crypto", "com.sun.xml.wss.logging.impl.opt.crypto.LogStrings");
/*     */ 
/*     */   
/*  83 */   private static final String ENCRYPTION_METHOD = "EncryptionMethod".intern();
/*  84 */   private static final String CIPHER_DATA = "CipherData".intern();
/*  85 */   private static final String KEY_INFO = "KeyInfo".intern();
/*     */   
/*     */   private static final int KEYINFO_ELEMENT = 1;
/*     */   
/*     */   private static final int ENCRYPTIONMETHOD_ELEMENT = 2;
/*     */   private static final int CIPHER_DATA_ELEMENT = 5;
/*  91 */   private JAXBFilterProcessingContext pc = null;
/*     */   
/*  93 */   private String id = "";
/*  94 */   private String namespaceURI = "";
/*  95 */   private String localName = "";
/*  96 */   private String encryptionMethod = "";
/*  97 */   private Key dataEncKey = null;
/*  98 */   private InputStream cin = null;
/*  99 */   private CryptoProcessor cp = null;
/* 100 */   CipherDataProcessor cdp = null;
/*     */   private boolean hasCipherReference = false;
/* 102 */   private byte[] decryptedMimeData = null;
/* 103 */   private String attachmentContentId = null;
/* 104 */   private String attachmentContentType = null;
/* 105 */   private String mimeType = null;
/* 106 */   private WSSPolicy inferredKB = null;
/* 107 */   HashMap<String, String> parentNS = null;
/*     */ 
/*     */   
/*     */   public EncryptedData(XMLStreamReader reader, JAXBFilterProcessingContext pc, HashMap<String, String> parentNS) throws XMLStreamException, XWSSecurityException {
/* 111 */     this.pc = pc;
/* 112 */     this.parentNS = parentNS;
/* 113 */     process(reader);
/*     */   }
/*     */   
/*     */   public EncryptedData(XMLStreamReader reader, Key dataEncKey, JAXBFilterProcessingContext pc, HashMap<String, String> parentNS) throws XMLStreamException, XWSSecurityException {
/* 117 */     this.dataEncKey = dataEncKey;
/* 118 */     this.pc = pc;
/* 119 */     this.parentNS = parentNS;
/* 120 */     process(reader);
/*     */   }
/*     */   
/*     */   private void process(XMLStreamReader reader) throws XMLStreamException, XWSSecurityException {
/* 124 */     this.id = reader.getAttributeValue(null, "Id");
/* 125 */     this.namespaceURI = reader.getNamespaceURI();
/* 126 */     this.localName = reader.getLocalName();
/* 127 */     this.mimeType = reader.getAttributeValue(null, "MimeType");
/*     */     
/* 129 */     if (StreamUtil.moveToNextElement(reader)) {
/* 130 */       int refElement = getEventType(reader);
/* 131 */       while (reader.getEventType() != 8) {
/* 132 */         KeyInfoProcessor kip; switch (refElement) {
/*     */           case 2:
/* 134 */             this.encryptionMethod = reader.getAttributeValue(null, "Algorithm");
/* 135 */             if (this.encryptionMethod == null || this.encryptionMethod.length() == 0) {
/* 136 */               if (this.pc.isBSP()) {
/* 137 */                 logger.log(Level.SEVERE, LogStringsMessages.BSP_5601_ENCRYPTEDDATA_ENCRYPTIONMETHOD(this.id));
/* 138 */                 throw new XWSSecurityException(LogStringsMessages.BSP_5601_ENCRYPTEDDATA_ENCRYPTIONMETHOD(this.id));
/*     */               } 
/* 140 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1925_EMPTY_ENCMETHOD_ED());
/* 141 */               throw new XWSSecurityException(LogStringsMessages.WSS_1925_EMPTY_ENCMETHOD_ED());
/*     */             } 
/*     */             
/* 144 */             if (this.pc.isBSP() && !this.encryptionMethod.equals("http://www.w3.org/2001/04/xmlenc#tripledes-cbc") && !this.encryptionMethod.equals("http://www.w3.org/2001/04/xmlenc#aes128-cbc") && !this.encryptionMethod.equals("http://www.w3.org/2001/04/xmlenc#aes256-cbc")) {
/*     */ 
/*     */               
/* 147 */               logger.log(Level.SEVERE, LogStringsMessages.BSP_5626_KEYENCRYPTIONALGO());
/* 148 */               throw new XWSSecurityException(LogStringsMessages.BSP_5626_KEYENCRYPTIONALGO());
/*     */             } 
/* 150 */             reader.next();
/*     */             break;
/*     */           
/*     */           case 1:
/* 154 */             this.pc.getSecurityContext().setInferredKB(null);
/* 155 */             kip = new KeyInfoProcessor(this.pc, KeySelector.Purpose.DECRYPT);
/* 156 */             this.dataEncKey = kip.getKey(reader);
/* 157 */             this.inferredKB = (WSSPolicy)this.pc.getSecurityContext().getInferredKB();
/* 158 */             this.pc.getSecurityContext().setInferredKB(null);
/* 159 */             if (!kip.hasSTR() && this.pc.isBSP()) {
/* 160 */               logger.log(Level.SEVERE, LogStringsMessages.BSP_5426_ENCRYPTEDKEYINFO(this.id));
/* 161 */               throw new XWSSecurityException(LogStringsMessages.BSP_5426_ENCRYPTEDKEYINFO(this.id));
/*     */             } 
/*     */             break;
/*     */           
/*     */           case 5:
/* 166 */             this.cdp = new CipherDataProcessor(this.pc);
/* 167 */             this.cdp.process(reader);
/* 168 */             this.hasCipherReference = this.cdp.hasCipherReference();
/* 169 */             if (this.hasCipherReference) {
/* 170 */               this.attachmentContentId = this.cdp.getAttachmentContentId();
/* 171 */               this.attachmentContentType = this.cdp.getAttachmentContentType();
/*     */             } 
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 180 */         if (shouldBreak(reader)) {
/*     */           break;
/*     */         }
/* 183 */         if (reader.getEventType() == 1) {
/* 184 */           if (getEventType(reader) == -1) {
/* 185 */             reader.next();
/*     */           }
/*     */         } else {
/* 188 */           reader.next();
/*     */         } 
/*     */         
/* 191 */         refElement = getEventType(reader);
/*     */       } 
/*     */     } 
/* 194 */     if (reader.hasNext()) {
/* 195 */       reader.next();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean shouldBreak(XMLStreamReader reader) throws XMLStreamException {
/* 200 */     if (StreamUtil._break(reader, "EncryptedData", "http://www.w3.org/2001/04/xmlenc#")) {
/* 201 */       return true;
/*     */     }
/* 203 */     if (reader.getEventType() == 8) {
/* 204 */       return true;
/*     */     }
/* 206 */     return false;
/*     */   }
/*     */   
/*     */   public String getEncryptionAlgorithm() {
/* 210 */     return this.encryptionMethod;
/*     */   }
/*     */   
/*     */   public Key getKey() {
/* 214 */     return this.dataEncKey;
/*     */   }
/*     */   
/*     */   public InputStream getCipherInputStream() throws XWSSecurityException {
/* 218 */     if (this.dataEncKey == null) {
/* 219 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1926_ED_KEY_NOTSET());
/* 220 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1926_ED_KEY_NOTSET(), null);
/*     */     } 
/* 222 */     this.cp = new CryptoProcessor(2, this.encryptionMethod, this.dataEncKey);
/*     */     try {
/* 224 */       this.cin = this.cp.decryptData(this.cdp.readAsStream());
/* 225 */     } catch (IOException ex) {
/* 226 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1927_ERROR_DECRYPT_ED("EncryptedData"));
/* 227 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1927_ERROR_DECRYPT_ED("EncryptedData"), ex);
/*     */     } 
/*     */     
/* 230 */     return this.cin;
/*     */   }
/*     */   
/*     */   public InputStream getCipherInputStream(Key key) throws XWSSecurityException {
/* 234 */     this.dataEncKey = key;
/* 235 */     return getCipherInputStream();
/*     */   }
/*     */   
/*     */   public XMLStreamReader getDecryptedData() throws XMLStreamException, XWSSecurityException {
/* 239 */     if (this.cin == null) {
/* 240 */       this.cin = getCipherInputStream();
/*     */     }
/* 242 */     if (logger.isLoggable(Level.FINEST)) {
/* 243 */       ByteArrayOutputStream decryptedContent = new ByteArrayOutputStream();
/* 244 */       byte[] buf = new byte[4096];
/*     */       try {
/* 246 */         for (int len = -1; (len = this.cin.read(buf)) != -1;) {
/* 247 */           decryptedContent.write(buf, 0, len);
/*     */         }
/* 249 */         logger.log(Level.FINEST, LogStringsMessages.WSS_1951_ENCRYPTED_DATA_VALUE(new String(decryptedContent.toByteArray())));
/* 250 */         this.cin = new ByteArrayInputStream(decryptedContent.toByteArray());
/* 251 */       } catch (IOException ex) {
/* 252 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/* 255 */     CheckedInputStream ccin = null;
/*     */     try {
/* 257 */       ccin = new CheckedInputStream(this.cin);
/* 258 */       if (ccin.isEmpty()) {
/* 259 */         return null;
/*     */       }
/* 261 */     } catch (IOException ioe) {
/* 262 */       throw new XWSSecurityException(ioe);
/*     */     } 
/*     */     
/* 265 */     DecryptedInputStream decryptedStream = new DecryptedInputStream((InputStream)ccin, this.parentNS);
/* 266 */     XMLInputFactory xif = XMLInputFactory.newInstance();
/* 267 */     XMLStreamReader reader = xif.createXMLStreamReader((InputStream)decryptedStream);
/*     */     
/* 269 */     return (XMLStreamReader)new FilteredXMLStreamReader(reader);
/*     */   }
/*     */   
/*     */   public XMLStreamReader getDecryptedData(Key key) throws XMLStreamException, XWSSecurityException {
/* 273 */     if (this.cin == null) {
/* 274 */       this.cin = getCipherInputStream(key);
/*     */     }
/* 276 */     return getDecryptedData();
/*     */   }
/*     */   
/*     */   public byte[] getDecryptedMimeData(Key key) throws XWSSecurityException {
/* 280 */     this.dataEncKey = key;
/* 281 */     return getDecryptedMimeData();
/*     */   }
/*     */   
/*     */   public byte[] getDecryptedMimeData() throws XWSSecurityException {
/* 285 */     if (this.decryptedMimeData == null) {
/* 286 */       if (this.dataEncKey == null) {
/* 287 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1926_ED_KEY_NOTSET());
/* 288 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1926_ED_KEY_NOTSET(), null);
/*     */       } 
/* 290 */       this.cp = new CryptoProcessor(2, this.encryptionMethod, this.dataEncKey);
/*     */       try {
/* 292 */         this.decryptedMimeData = this.cp.decryptData(this.cdp.readAsBytes());
/* 293 */       } catch (IOException ex) {
/* 294 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1927_ERROR_DECRYPT_ED("EncryptedData"));
/* 295 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1927_ERROR_DECRYPT_ED("EncryptedData"), ex);
/*     */       } 
/*     */     } 
/* 298 */     return this.decryptedMimeData;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 302 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 306 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 310 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 314 */     return this.localName;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 318 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 322 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 326 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private int getEventType(XMLStreamReader reader) {
/* 330 */     if (reader.getEventType() == 1) {
/* 331 */       if (reader.getLocalName() == ENCRYPTION_METHOD) {
/* 332 */         return 2;
/*     */       }
/*     */       
/* 335 */       if (reader.getLocalName() == KEY_INFO) {
/* 336 */         return 1;
/*     */       }
/*     */       
/* 339 */       if (reader.getLocalName() == CIPHER_DATA) {
/* 340 */         return 5;
/*     */       }
/*     */     } 
/* 343 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 347 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 351 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public WSSPolicy getInferredKB() {
/* 355 */     return this.inferredKB;
/*     */   }
/*     */   
/*     */   public boolean hasCipherReference() {
/* 359 */     return this.hasCipherReference;
/*     */   }
/*     */   
/*     */   public String getAttachmentContentId() {
/* 363 */     return this.attachmentContentId;
/*     */   }
/*     */   
/*     */   public String getAttachmentContentType() {
/* 367 */     return this.attachmentContentType;
/*     */   }
/*     */   
/*     */   public String getAttachmentMimeType() {
/* 371 */     return this.mimeType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\EncryptedData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */