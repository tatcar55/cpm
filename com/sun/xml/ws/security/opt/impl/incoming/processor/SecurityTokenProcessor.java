/*     */ package com.sun.xml.ws.security.opt.impl.incoming.processor;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.AbstractCreatorProcessor;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferMark;
/*     */ import com.sun.xml.ws.security.opt.crypto.jaxb.JAXBValidateContext;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.KeySelectorImpl;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.StreamWriterData;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.URIResolver;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.logging.impl.opt.token.LogStringsMessages;
/*     */ import java.math.BigInteger;
/*     */ import java.security.Key;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.crypto.KeySelectorException;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.XMLCryptoContext;
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
/*     */ public class SecurityTokenProcessor
/*     */ {
/*  74 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.token", "com.sun.xml.wss.logging.impl.opt.token.LogStrings");
/*     */ 
/*     */   
/*  77 */   private static String SECURITY_TOKEN_REF = "SecurityTokenReference";
/*  78 */   private static String DIRECT_REFERENCE_ELEMENT = "Reference";
/*  79 */   private static String KEYIDENTIFIER_ELEMENT = "KeyIdentifier";
/*  80 */   private static String THUMBPRINT_ELEMENT = "Thumbprint";
/*     */   private static final String KEY_VALUE = "KeyValue";
/*     */   private static final String X509DATA_ELEMENT = "X509Data";
/*     */   private static final String X509ISSUERSERIAL_ELEMENT = "X509IssuerSerial";
/*     */   private static final String X509ISSUERNAME_ELEMENT = "X509IssuerName";
/*     */   private static final String X509SERIALNUMBER_ELEMENT = "X509SerialNumber";
/*     */   private static final String KEY_NAME = "KeyName";
/*     */   private static final int DIRECT_REFERENCE = 1;
/*     */   private static final int KEYIDENTIFIER = 2;
/*     */   private static final int THUMBPRINT = 3;
/*     */   private static final int KEY_VALUE_ELEMENT = 4;
/*     */   private static final int X509DATA = 5;
/*     */   private static final int X509ISSUERSERIAL = 6;
/*     */   private static final int X509ISSUERNAME = 7;
/*     */   private static final int X509SERIALNUMBER = 8;
/*     */   private static final int SECURITY_TOKEN_REFERENCE = 9;
/*     */   private static final int KEY_NAME_ELEMENT = 10;
/*  97 */   private JAXBFilterProcessingContext pc = null;
/*  98 */   private XMLStreamWriter canonWriter = null;
/*  99 */   private KeySelector.Purpose purpose = null;
/* 100 */   private String id = "";
/*     */ 
/*     */   
/*     */   public SecurityTokenProcessor(JAXBFilterProcessingContext context, KeySelector.Purpose purpose) {
/* 104 */     this.pc = context;
/* 105 */     this.purpose = purpose;
/*     */   }
/*     */   
/*     */   public SecurityTokenProcessor(JAXBFilterProcessingContext context, XMLStreamWriter canonWriter, KeySelector.Purpose purpose) {
/* 109 */     this.pc = context;
/* 110 */     this.canonWriter = canonWriter;
/* 111 */     this.purpose = purpose;
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
/*     */   public Key resolveReference(XMLStreamReader reader) throws XWSSecurityException {
/* 123 */     Key resolvedKey = null;
/*     */     try {
/* 125 */       if (this.canonWriter != null)
/* 126 */         StreamUtil.writeStartElement(reader, this.canonWriter); 
/* 127 */       this.id = reader.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*     */       
/* 129 */       if (this.id != null && this.id.length() > 0)
/*     */       {
/* 131 */         if (reader instanceof AbstractCreatorProcessor) {
/* 132 */           Map<String, String> emptyMap = Collections.emptyMap();
/* 133 */           XMLStreamBufferMark marker = new XMLStreamBufferMark(emptyMap, (AbstractCreatorProcessor)reader);
/* 134 */           this.pc.getElementCache().put(this.id, new StreamWriterData((XMLStreamBuffer)marker));
/*     */         } 
/*     */       }
/* 137 */       if (reader.getLocalName() == SECURITY_TOKEN_REF && reader.getNamespaceURI() == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") {
/* 138 */         while (reader.hasNext() && !StreamUtil._break(reader, SECURITY_TOKEN_REF, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")) {
/* 139 */           reader.next();
/* 140 */           int refType = getReferenceType(reader);
/* 141 */           switch (refType) {
/*     */             case 1:
/* 143 */               resolvedKey = processDirectReference(reader);
/*     */ 
/*     */             
/*     */             case 2:
/* 147 */               resolvedKey = processKeyIdentifier(reader);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case 4:
/* 154 */               if (this.canonWriter != null) {
/* 155 */                 StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */               }
/* 157 */               resolvedKey = (new KeyValueProcessor(this.pc, this.canonWriter)).processKeyValue(reader);
/*     */ 
/*     */             
/*     */             case 5:
/* 161 */               resolvedKey = processX509Data(reader);
/*     */ 
/*     */             
/*     */             case 9:
/* 165 */               if (this.pc.isBSP()) {
/* 166 */                 logger.log(Level.SEVERE, LogStringsMessages.BSP_3057_STR_NOT_REF_STR());
/* 167 */                 throw new XWSSecurityException(LogStringsMessages.BSP_3057_STR_NOT_REF_STR());
/*     */               } 
/*     */ 
/*     */             
/*     */             case 10:
/* 172 */               if (this.pc.isBSP()) {
/* 173 */                 logger.log(Level.SEVERE, LogStringsMessages.BSP_3058_STR_VALUE_TYPE_NOTEMPTY());
/* 174 */                 throw new XWSSecurityException(LogStringsMessages.BSP_3058_STR_VALUE_TYPE_NOTEMPTY());
/*     */               } 
/*     */           } 
/*     */ 
/*     */         
/*     */         } 
/*     */       }
/* 181 */       if (this.canonWriter != null) {
/* 182 */         this.canonWriter.writeEndElement();
/*     */       }
/* 184 */       if (reader.hasNext()) {
/* 185 */         reader.next();
/*     */       }
/*     */     }
/* 188 */     catch (XMLStreamException xe) {
/* 189 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1815_ERROR_PROCESSING_STR(), xe);
/* 190 */       throw new XWSSecurityException(LogStringsMessages.WSS_1815_ERROR_PROCESSING_STR(), xe);
/*     */     } 
/* 192 */     return resolvedKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getReferenceType(XMLStreamReader reader) {
/* 200 */     if (reader.getEventType() == 1) {
/* 201 */       if (reader.getLocalName() == DIRECT_REFERENCE_ELEMENT)
/* 202 */         return 1; 
/* 203 */       if (reader.getLocalName() == KEYIDENTIFIER_ELEMENT)
/* 204 */         return 2; 
/* 205 */       if (reader.getLocalName() == THUMBPRINT_ELEMENT)
/* 206 */         return 3; 
/* 207 */       if (reader.getLocalName() == "KeyValue")
/* 208 */         return 4; 
/* 209 */       if (reader.getLocalName() == "X509Data")
/* 210 */         return 5; 
/* 211 */       if (reader.getLocalName() == "KeyName")
/* 212 */         return 10; 
/* 213 */       if (reader.getLocalName() == SECURITY_TOKEN_REF) {
/* 214 */         return 9;
/*     */       }
/*     */     } 
/*     */     
/* 218 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean moveToNextElement(XMLStreamReader reader) throws XMLStreamException {
/* 223 */     if (reader.hasNext()) {
/* 224 */       reader.next();
/* 225 */       return true;
/*     */     } 
/* 227 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Key processDirectReference(XMLStreamReader reader) throws XWSSecurityException {
/*     */     try {
/* 237 */       if (this.canonWriter != null) {
/* 238 */         StreamUtil.writeStartElement(reader, this.canonWriter);
/*     */       }
/*     */       
/* 241 */       String uri = reader.getAttributeValue(null, "URI");
/*     */       
/* 243 */       if (this.pc.isBSP() && uri == null) {
/* 244 */         logger.log(Level.SEVERE, LogStringsMessages.BSP_3062_STR_URIATTRIBUTE());
/* 245 */         throw new XWSSecurityException(LogStringsMessages.BSP_3062_STR_URIATTRIBUTE());
/*     */       } 
/* 247 */       String vt = reader.getAttributeValue(null, "ValueType");
/* 248 */       if (this.pc.isBSP() && (vt == null || vt.length() == 0)) {
/* 249 */         logger.log(Level.SEVERE, LogStringsMessages.BSP_3058_STR_VALUE_TYPE_NOTEMPTY());
/* 250 */         throw new XWSSecurityException(LogStringsMessages.BSP_3058_STR_VALUE_TYPE_NOTEMPTY());
/*     */       } 
/*     */       
/* 253 */       String wscInstance = reader.getAttributeValue(this.pc.getWSSCVersion(this.pc.getSecurityPolicyVersion()), "Instance");
/* 254 */       if (wscInstance != null) {
/* 255 */         this.pc.setWSCInstance(wscInstance);
/*     */       }
/* 257 */       if (this.canonWriter != null) {
/* 258 */         this.canonWriter.writeEndElement();
/*     */       }
/*     */       
/* 261 */       URIResolver resolver = new URIResolver(this.pc);
/* 262 */       JAXBValidateContext validateContext = new JAXBValidateContext();
/* 263 */       validateContext.setURIDereferencer((URIDereferencer)resolver);
/* 264 */       validateContext.put("http://wss.sun.com#processingContext", this.pc);
/* 265 */       reader.next();
/* 266 */       reader.next();
/* 267 */       return KeySelectorImpl.resolveDirectReference((XMLCryptoContext)validateContext, vt, uri, this.purpose);
/* 268 */     } catch (KeySelectorException kse) {
/* 269 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1816_ERROR_REFERENCE_MECHANISM("Direct Reference"), kse);
/* 270 */       throw new XWSSecurityException(LogStringsMessages.WSS_1816_ERROR_REFERENCE_MECHANISM("Direct Reference"), kse);
/* 271 */     } catch (XMLStreamException xse) {
/* 272 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1817_ERROR_REFERENCE_CANWRITER("Direct Reference"), xse);
/* 273 */       throw new XWSSecurityException(LogStringsMessages.WSS_1817_ERROR_REFERENCE_CANWRITER("Direct Reference"), xse);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Key processX509Data(XMLStreamReader reader) throws XWSSecurityException {
/*     */     try {
/* 284 */       Key returnKey = null;
/* 285 */       if (this.canonWriter != null)
/* 286 */         StreamUtil.writeStartElement(reader, this.canonWriter); 
/* 287 */       while (reader.hasNext() && !StreamUtil._break(reader, "X509Data", "http://www.w3.org/2000/09/xmldsig#")) {
/* 288 */         reader.next();
/* 289 */         int eventType = getEventTypeForX509Data(reader);
/* 290 */         switch (eventType) {
/*     */           case 6:
/* 292 */             returnKey = processX509IssuerSerial(reader);
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 297 */       return returnKey;
/* 298 */     } catch (XMLStreamException xse) {
/* 299 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1817_ERROR_REFERENCE_CANWRITER("Issuer Serial"), xse);
/* 300 */       throw new XWSSecurityException(LogStringsMessages.WSS_1817_ERROR_REFERENCE_CANWRITER("Issuer Serial"), xse);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getEventTypeForX509Data(XMLStreamReader reader) throws XMLStreamException {
/* 310 */     if (reader.getEventType() == 1 && 
/* 311 */       reader.getLocalName() == "X509IssuerSerial") {
/* 312 */       return 6;
/*     */     }
/*     */     
/* 315 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Key processX509IssuerSerial(XMLStreamReader reader) throws XWSSecurityException {
/*     */     try {
/* 325 */       Key returnKey = null;
/* 326 */       if (this.canonWriter != null)
/* 327 */         StreamUtil.writeStartElement(reader, this.canonWriter); 
/* 328 */       BigInteger serialNumber = null;
/* 329 */       String issuerName = null;
/* 330 */       while (reader.hasNext() && !StreamUtil._break(reader, "X509IssuerSerial", "http://www.w3.org/2000/09/xmldsig#")) {
/* 331 */         String tmp; reader.next();
/* 332 */         int eventType = getEventTypeForX509IssuerSerial(reader);
/* 333 */         switch (eventType) {
/*     */           case 7:
/* 335 */             if (this.canonWriter != null)
/* 336 */               StreamUtil.writeStartElement(reader, this.canonWriter); 
/* 337 */             reader.next();
/*     */             
/* 339 */             issuerName = StreamUtil.getCV(reader);
/* 340 */             if (this.canonWriter != null) {
/* 341 */               this.canonWriter.writeCharacters(issuerName);
/*     */             }
/*     */             continue;
/*     */           
/*     */           case 8:
/* 346 */             if (this.canonWriter != null)
/* 347 */               StreamUtil.writeStartElement(reader, this.canonWriter); 
/* 348 */             reader.next();
/* 349 */             tmp = StreamUtil.getCV(reader);
/* 350 */             serialNumber = new BigInteger(tmp);
/* 351 */             if (this.canonWriter != null) {
/* 352 */               this.canonWriter.writeCharacters(tmp);
/*     */             }
/*     */             continue;
/*     */         } 
/*     */         
/* 357 */         if (this.canonWriter != null) {
/* 358 */           StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 364 */       if (issuerName != null && serialNumber != null) {
/* 365 */         URIResolver resolver = new URIResolver(this.pc);
/* 366 */         JAXBValidateContext validateContext = new JAXBValidateContext();
/* 367 */         validateContext.setURIDereferencer((URIDereferencer)resolver);
/* 368 */         validateContext.put("http://wss.sun.com#processingContext", this.pc);
/* 369 */         returnKey = KeySelectorImpl.resolveIssuerSerial((XMLCryptoContext)validateContext, issuerName, serialNumber, this.id, this.purpose);
/*     */       } 
/* 371 */       return returnKey;
/* 372 */     } catch (KeySelectorException kse) {
/* 373 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1816_ERROR_REFERENCE_MECHANISM("Issuer Serial"), kse);
/* 374 */       throw new XWSSecurityException(LogStringsMessages.WSS_1816_ERROR_REFERENCE_MECHANISM("Issuer Serial"), kse);
/* 375 */     } catch (XMLStreamException xse) {
/* 376 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1817_ERROR_REFERENCE_CANWRITER("Issuer Serial"), xse);
/* 377 */       throw new XWSSecurityException(LogStringsMessages.WSS_1817_ERROR_REFERENCE_CANWRITER("Issuer Serial"), xse);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getEventTypeForX509IssuerSerial(XMLStreamReader reader) throws XMLStreamException {
/* 387 */     if (reader.getEventType() == 1) {
/* 388 */       if (reader.getLocalName() == "X509IssuerName")
/* 389 */         return 7; 
/* 390 */       if (reader.getLocalName() == "X509SerialNumber") {
/* 391 */         return 8;
/*     */       }
/*     */     } 
/* 394 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Key processKeyIdentifier(XMLStreamReader reader) throws XWSSecurityException {
/*     */     try {
/* 404 */       if (this.canonWriter != null)
/* 405 */         StreamUtil.writeStartElement(reader, this.canonWriter); 
/* 406 */       String valueType = reader.getAttributeValue(null, "ValueType");
/*     */       
/* 408 */       if (this.pc.isBSP()) {
/* 409 */         String et = reader.getAttributeValue(null, "EncodingType");
/* 410 */         if (et == null || et.length() == 0) {
/* 411 */           logger.log(Level.SEVERE, LogStringsMessages.BSP_3071_STR_ENCODING_TYPE());
/* 412 */           throw new XWSSecurityException(LogStringsMessages.BSP_3071_STR_ENCODING_TYPE());
/*     */         } 
/*     */       } 
/* 415 */       String keyIdentifier = null;
/* 416 */       if (reader instanceof XMLStreamReaderEx) {
/* 417 */         reader.next();
/* 418 */         if (reader.getEventType() == 4) {
/* 419 */           CharSequence charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 420 */           if (charSeq instanceof Base64Data) {
/* 421 */             Base64Data bd = (Base64Data)charSeq;
/* 422 */             keyIdentifier = bd.toString();
/*     */           } else {
/* 424 */             keyIdentifier = StreamUtil.getCV((XMLStreamReaderEx)reader);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 428 */         keyIdentifier = StreamUtil.getCV(reader);
/*     */       } 
/*     */       
/* 431 */       if (this.canonWriter != null) {
/*     */         
/* 433 */         this.canonWriter.writeCharacters(keyIdentifier);
/*     */         
/* 435 */         this.canonWriter.writeEndElement();
/*     */       } 
/* 437 */       reader.next();
/*     */       
/* 439 */       URIResolver resolver = new URIResolver(this.pc);
/* 440 */       JAXBValidateContext validateContext = new JAXBValidateContext();
/* 441 */       validateContext.setURIDereferencer((URIDereferencer)resolver);
/* 442 */       validateContext.put("http://wss.sun.com#processingContext", this.pc);
/* 443 */       return KeySelectorImpl.resolveKeyIdentifier((XMLCryptoContext)validateContext, valueType, keyIdentifier, this.id, this.purpose);
/* 444 */     } catch (KeySelectorException kse) {
/* 445 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1816_ERROR_REFERENCE_MECHANISM("KeyIdentifier"), kse);
/* 446 */       throw new XWSSecurityException(LogStringsMessages.WSS_1816_ERROR_REFERENCE_MECHANISM("KeyIdentifier"), kse);
/* 447 */     } catch (XMLStreamException xe) {
/* 448 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1817_ERROR_REFERENCE_CANWRITER("KeyIdentifier"), xe);
/* 449 */       throw new XWSSecurityException(LogStringsMessages.WSS_1817_ERROR_REFERENCE_CANWRITER("KeyIdentifier"), xe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\processor\SecurityTokenProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */