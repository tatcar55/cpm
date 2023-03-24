/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferProcessor;
/*     */ import com.sun.xml.ws.security.impl.DerivedKeyTokenImpl;
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.SecurityTokenProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.token.LogStringsMessages;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Key;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.Base64Data;
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
/*     */ public class DerivedKeyToken
/*     */   implements SecurityHeaderElement, NamespaceContextInfo, SecurityElementWriter
/*     */ {
/*  77 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.token", "com.sun.xml.wss.logging.impl.opt.token.LogStrings");
/*     */   
/*  79 */   private static final String SECURITY_TOKEN_REFERENCE = "SecurityTokenReference".intern();
/*  80 */   private static final String LENGTH = "Length".intern();
/*  81 */   private static final String OFFSET = "Offset".intern();
/*  82 */   private static final String GENERATION = "Generation".intern();
/*  83 */   private static final String NONCE = "Nonce".intern();
/*  84 */   private static final String LABEL = "Label".intern();
/*     */   private static final int SECURITY_TOKEN_REFERENCE_ELEMENT = 3;
/*     */   private static final int LENGTH_ELEMENT = 4;
/*     */   private static final int OFFSET_ELEMENT = 5;
/*     */   private static final int GENERATION_ELEMENT = 6;
/*     */   private static final int NONCE_ELEMENT = 7;
/*     */   private static final int LABEL_ELEMENT = 8;
/*  91 */   private String id = "";
/*  92 */   private String namespaceURI = "";
/*  93 */   private String localName = "";
/*  94 */   private long offset = 0L;
/*  95 */   private long length = 32L;
/*  96 */   private String label = null;
/*  97 */   private String nonce = null;
/*  98 */   private byte[] decodedNonce = null;
/*  99 */   private long generation = -1L;
/*     */   private HashMap<String, String> nsDecls;
/* 101 */   private Key originalKey = null;
/* 102 */   private MutableXMLStreamBuffer buffer = null;
/* 103 */   private JAXBFilterProcessingContext pc = null;
/* 104 */   private WSSPolicy inferredKB = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivedKeyToken(XMLStreamReader reader, JAXBFilterProcessingContext pc, HashMap<String, String> nsDecls) throws XMLStreamException, XWSSecurityException {
/* 109 */     this.pc = pc;
/* 110 */     this.nsDecls = nsDecls;
/* 111 */     this.buffer = new MutableXMLStreamBuffer();
/* 112 */     process(reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public Key getKey() throws XWSSecurityException {
/* 117 */     String dataEncAlgo = null;
/* 118 */     if (this.originalKey == null) {
/* 119 */       SecurityTokenProcessor stp = new SecurityTokenProcessor(this.pc, null);
/*     */       try {
/* 121 */         StreamReaderBufferProcessor streamReaderBufferProcessor = this.buffer.readAsXMLStreamReader();
/* 122 */         if (streamReaderBufferProcessor.getEventType() != 1) {
/* 123 */           StreamUtil.moveToNextStartOREndElement((XMLStreamReader)streamReaderBufferProcessor);
/*     */         }
/* 125 */         this.pc.getSecurityContext().setInferredKB(null);
/* 126 */         this.originalKey = stp.resolveReference((XMLStreamReader)streamReaderBufferProcessor);
/* 127 */         this.inferredKB = (WSSPolicy)this.pc.getSecurityContext().getInferredKB();
/* 128 */         this.pc.getSecurityContext().setInferredKB(null);
/* 129 */       } catch (XMLStreamException ex) {
/* 130 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1855_XML_STREAM_READER_ERROR(), ex);
/*     */       } 
/*     */     } 
/* 133 */     if (this.pc.getAlgorithmSuite() != null) {
/* 134 */       dataEncAlgo = this.pc.getAlgorithmSuite().getEncryptionAlgorithm();
/*     */     } else {
/* 136 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1818_ALGORITHM_NOTSET_DERIVEDKEY());
/* 137 */       throw new XWSSecurityException(LogStringsMessages.WSS_1818_ALGORITHM_NOTSET_DERIVEDKEY());
/*     */     } 
/*     */     
/*     */     try {
/* 141 */       byte[] secret = this.originalKey.getEncoded();
/* 142 */       DerivedKeyTokenImpl derivedKeyTokenImpl = new DerivedKeyTokenImpl(this.offset, this.length, secret, this.decodedNonce, this.label);
/* 143 */       String jceAlgo = SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo);
/*     */       
/* 145 */       return derivedKeyTokenImpl.generateSymmetricKey(jceAlgo);
/*     */     }
/* 147 */     catch (Exception ex) {
/* 148 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1819_ERROR_SYMMKEY_DERIVEDKEY());
/* 149 */       throw new XWSSecurityException(LogStringsMessages.WSS_1819_ERROR_SYMMKEY_DERIVEDKEY(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 155 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId() {
/* 159 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 163 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 167 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 171 */     return this.localName;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 175 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 179 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 183 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private void process(XMLStreamReader reader) throws XMLStreamException, XWSSecurityException {
/* 187 */     this.id = reader.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/* 188 */     this.namespaceURI = reader.getNamespaceURI();
/* 189 */     this.localName = reader.getLocalName();
/*     */     
/* 191 */     boolean offsetSpecified = false;
/* 192 */     boolean genSpecified = false;
/* 193 */     boolean invalidToken = false;
/*     */     
/* 195 */     if (StreamUtil.moveToNextElement(reader)) {
/* 196 */       int refElement = getEventType(reader);
/* 197 */       while (reader.getEventType() != 8) {
/* 198 */         switch (refElement) {
/*     */           
/*     */           case 3:
/* 201 */             this.buffer.createFromXMLStreamReader(reader);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 4:
/* 206 */             this.length = Integer.parseInt(reader.getElementText());
/*     */             break;
/*     */           
/*     */           case 5:
/* 210 */             this.offset = Integer.parseInt(reader.getElementText());
/* 211 */             offsetSpecified = true;
/*     */             break;
/*     */           
/*     */           case 6:
/* 215 */             this.generation = Integer.parseInt(reader.getElementText());
/* 216 */             genSpecified = true;
/*     */             break;
/*     */           
/*     */           case 8:
/* 220 */             this.label = reader.getElementText();
/*     */             break;
/*     */           
/*     */           case 7:
/* 224 */             if (reader instanceof XMLStreamReaderEx) {
/* 225 */               reader.next();
/* 226 */               StringBuffer sb = null;
/* 227 */               if (reader.getEventType() == 4 && reader.getEventType() != 2) {
/*     */                 
/* 229 */                 CharSequence charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 230 */                 if (charSeq instanceof Base64Data) {
/* 231 */                   Base64Data bd = (Base64Data)((XMLStreamReaderEx)reader).getPCDATA();
/* 232 */                   this.decodedNonce = bd.getExact();
/*     */                 } else {
/* 234 */                   if (sb == null) {
/* 235 */                     sb = new StringBuffer();
/*     */                   }
/* 237 */                   for (int i = 0; i < charSeq.length(); i++) {
/* 238 */                     sb.append(charSeq.charAt(i));
/*     */                   }
/*     */                 } 
/* 241 */                 reader.next();
/*     */               } 
/* 243 */               if (sb != null) {
/* 244 */                 this.nonce = sb.toString();
/*     */                 try {
/* 246 */                   this.decodedNonce = Base64.decode(this.nonce);
/* 247 */                 } catch (Base64DecodingException dec) {
/* 248 */                   logger.log(Level.SEVERE, LogStringsMessages.WSS_1820_ERROR_NONCE_DERIVEDKEY(this.id));
/* 249 */                   throw new XWSSecurityException(LogStringsMessages.WSS_1820_ERROR_NONCE_DERIVEDKEY(this.id), dec);
/*     */                 } 
/*     */               } 
/*     */               
/*     */               break;
/*     */             } 
/* 255 */             this.nonce = reader.getElementText();
/*     */             try {
/* 257 */               this.decodedNonce = Base64.decode(this.nonce);
/* 258 */             } catch (Base64DecodingException ex) {
/* 259 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1820_ERROR_NONCE_DERIVEDKEY(this.id));
/* 260 */               throw new XWSSecurityException(LogStringsMessages.WSS_1820_ERROR_NONCE_DERIVEDKEY(this.id), ex);
/*     */             } 
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 267 */             throw new XWSSecurityException("Element name " + reader.getName() + " is not recognized under DerivedKeyToken");
/*     */         } 
/*     */ 
/*     */         
/* 271 */         if (!StreamUtil.isStartElement(reader) && StreamUtil.moveToNextStartOREndElement(reader) && StreamUtil._break(reader, "DerivedKeyToken", "http://schemas.xmlsoap.org/ws/2005/02/sc")) {
/*     */           
/* 273 */           StreamUtil.moveToNextStartOREndElement(reader);
/*     */           break;
/*     */         } 
/* 276 */         if (reader.getEventType() != 1) {
/* 277 */           StreamUtil.moveToNextStartOREndElement(reader);
/* 278 */           boolean isBreak = false;
/* 279 */           while (reader.getEventType() == 2) {
/* 280 */             if (StreamUtil._break(reader, "DerivedKeyToken", "http://schemas.xmlsoap.org/ws/2005/02/sc")) {
/* 281 */               isBreak = true;
/* 282 */               StreamUtil.moveToNextStartOREndElement(reader);
/*     */               break;
/*     */             } 
/* 285 */             StreamUtil.moveToNextStartOREndElement(reader);
/*     */           } 
/* 287 */           if (isBreak) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         
/* 292 */         refElement = getEventType(reader);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 297 */     if (offsetSpecified && genSpecified) {
/* 298 */       invalidToken = true;
/*     */     }
/*     */     
/* 301 */     if (invalidToken) {
/* 302 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1821_INVALID_DKT_TOKEN());
/* 303 */       throw new XWSSecurityException(LogStringsMessages.WSS_1821_INVALID_DKT_TOKEN());
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getEventType(XMLStreamReader reader) {
/* 308 */     if (reader.getEventType() == 1) {
/* 309 */       if (reader.getLocalName() == SECURITY_TOKEN_REFERENCE) {
/* 310 */         return 3;
/*     */       }
/*     */       
/* 313 */       if (reader.getLocalName() == LENGTH) {
/* 314 */         return 4;
/*     */       }
/*     */       
/* 317 */       if (reader.getLocalName() == OFFSET) {
/* 318 */         return 5;
/*     */       }
/*     */       
/* 321 */       if (reader.getLocalName() == GENERATION) {
/* 322 */         return 6;
/*     */       }
/*     */       
/* 325 */       if (reader.getLocalName() == NONCE) {
/* 326 */         return 7;
/*     */       }
/*     */       
/* 329 */       if (reader.getLocalName() == LABEL) {
/* 330 */         return 8;
/*     */       }
/*     */     } 
/* 333 */     return -1;
/*     */   }
/*     */   
/*     */   public HashMap<String, String> getInscopeNSContext() {
/* 337 */     return this.nsDecls;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 341 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public WSSPolicy getInferredKB() {
/* 345 */     return this.inferredKB;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\DerivedKeyToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */