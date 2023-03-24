/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.PolicyBuilder;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.TokenValidator;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.KeyInfoProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Key;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLOutputFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAMLAssertion
/*     */   implements SecurityHeaderElement, PolicyBuilder, TokenValidator, NamespaceContextInfo, SecurityElementWriter
/*     */ {
/*  85 */   private String id = "";
/*  86 */   private String localName = "";
/*  87 */   private String namespaceURI = "";
/*  88 */   private Key key = null;
/*  89 */   private JAXBFilterProcessingContext jpc = null;
/*  90 */   private HashMap<String, String> samlHeaderNSContext = null;
/*  91 */   private StreamReaderBufferCreator creator = null;
/*  92 */   private Signature sig = null;
/*  93 */   private MutableXMLStreamBuffer buffer = null;
/*     */   
/*     */   private boolean signatureNSinReader = false;
/*  96 */   private AuthenticationTokenPolicy.SAMLAssertionBinding samlPolicy = null;
/*     */   
/*     */   private static final String KEYINFO_ELEMENT = "KeyInfo";
/*     */   
/*     */   private static final String SUBJECT_CONFIRMATION_ELEMENT = "SubjectConfirmation";
/*     */ 
/*     */   
/*     */   public SAMLAssertion(XMLStreamReader reader, JAXBFilterProcessingContext jpc, StreamReaderBufferCreator creator, HashMap<? extends String, ? extends String> nsDecl) throws XWSSecurityException {
/* 104 */     this.jpc = jpc;
/* 105 */     this.creator = creator;
/* 106 */     this.id = reader.getAttributeValue(null, "AssertionID");
/* 107 */     if (this.id == null) {
/* 108 */       this.id = reader.getAttributeValue(null, "ID");
/*     */     }
/* 110 */     this.namespaceURI = reader.getNamespaceURI();
/* 111 */     this.localName = reader.getLocalName();
/* 112 */     this.samlHeaderNSContext = new HashMap<String, String>();
/* 113 */     this.samlHeaderNSContext.putAll(nsDecl);
/* 114 */     if (reader.getNamespaceCount() > 0) {
/* 115 */       for (int i = 0; i < reader.getNamespaceCount(); i++) {
/* 116 */         this.samlHeaderNSContext.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
/* 117 */         if ("ds".equals(reader.getNamespacePrefix(i)) && "http://www.w3.org/2000/09/xmldsig#".equals(reader.getNamespaceURI(i))) {
/* 118 */           this.signatureNSinReader = true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 123 */     this.samlPolicy = new AuthenticationTokenPolicy.SAMLAssertionBinding();
/* 124 */     this.samlPolicy.setUUID(this.id);
/*     */ 
/*     */     
/* 127 */     this.buffer = new MutableXMLStreamBuffer();
/*     */     
/*     */     try {
/* 130 */       this.buffer.createFromXMLStreamReader(reader);
/* 131 */       process((XMLStreamReader)this.buffer.readAsXMLStreamReader());
/* 132 */     } catch (XMLStreamException xe) {
/* 133 */       throw new XWSSecurityException("Error occurred while reading SAMLAssertion", xe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader getSamlReader() throws XMLStreamException, XWSSecurityException {
/* 139 */     XMLStreamReader samlReader = readHeader();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 144 */       XMLOutputFactory xof = XMLOutputFactory.newInstance();
/* 145 */       XMLInputFactory xif = XMLInputFactory.newInstance();
/* 146 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 147 */       XMLStreamWriter writer = xof.createXMLStreamWriter(baos);
/* 148 */       boolean samlElementProcessed = false;
/* 149 */       while (8 != samlReader.getEventType()) {
/* 150 */         if (1 == samlReader.getEventType() && samlReader.getLocalName().equals("Assertion") && !samlElementProcessed) {
/*     */           
/* 152 */           writer.writeStartElement(samlReader.getPrefix(), samlReader.getLocalName(), samlReader.getNamespaceURI());
/*     */           
/* 154 */           Set<String> samlNSKeySet = this.samlHeaderNSContext.keySet();
/* 155 */           Iterator<String> it = samlNSKeySet.iterator();
/* 156 */           while (it.hasNext()) {
/* 157 */             String prefix = it.next();
/* 158 */             writer.writeNamespace(prefix, this.samlHeaderNSContext.get(prefix));
/*     */           } 
/*     */           
/* 161 */           int atCount = samlReader.getAttributeCount();
/* 162 */           for (int i = 0; i < atCount; i++) {
/* 163 */             if (samlReader.getAttributePrefix(i) == "" || samlReader.getAttributePrefix(i) == null) {
/* 164 */               writer.writeAttribute(samlReader.getAttributeLocalName(i), samlReader.getAttributeValue(i));
/*     */             } else {
/* 166 */               writer.writeAttribute(samlReader.getAttributePrefix(i), samlReader.getAttributeNamespace(i), samlReader.getAttributeLocalName(i), samlReader.getAttributeValue(i));
/*     */             } 
/*     */           } 
/* 169 */           samlElementProcessed = true;
/*     */         } else {
/* 171 */           StreamUtil.writeCurrentEvent(samlReader, writer);
/*     */         } 
/* 173 */         samlReader.next();
/*     */       } 
/* 175 */       writer.close();
/*     */       try {
/* 177 */         baos.close();
/* 178 */       } catch (IOException ex) {
/* 179 */         throw new XWSSecurityException("Error occurred while processing SAMLAssertion of type XMLSreamReader", ex);
/*     */       } 
/* 181 */       samlReader = xif.createXMLStreamReader(new ByteArrayInputStream(baos.toByteArray()));
/* 182 */     } catch (XMLStreamException ex) {
/* 183 */       throw new XWSSecurityException("Error occurred while processing SAMLAssertion of type XMLSreamReader", ex);
/*     */     } 
/* 185 */     return samlReader;
/*     */   }
/*     */   
/*     */   private boolean isSignatureNSinReader() {
/* 189 */     return this.signatureNSinReader;
/*     */   }
/*     */ 
/*     */   
/*     */   public SAMLAssertion() {}
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 196 */     return false;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 200 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 204 */     throw new UnsupportedOperationException("not implemented");
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 208 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 212 */     return this.localName;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 216 */     return (XMLStreamReader)this.buffer.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public WSSPolicy getPolicy() {
/* 220 */     return (WSSPolicy)this.samlPolicy;
/*     */   }
/*     */   
/*     */   public void validate(ProcessingContext context) throws XWSSecurityException {
/*     */     try {
/* 225 */       XMLStreamReader samlReader = getSamlReader();
/* 226 */       context.getSecurityEnvironment().validateSAMLAssertion(context.getExtraneousProperties(), samlReader);
/* 227 */       context.getSecurityEnvironment().updateOtherPartySubject((Subject)context.getExtraneousProperties().get("javax.security.auth.Subject"), samlReader);
/* 228 */     } catch (XMLStreamException xe) {
/* 229 */       throw new XWSSecurityException("Error occurred while trying to validate SAMLAssertion", xe);
/*     */     } 
/*     */   }
/*     */   
/*     */   public HashMap<String, String> getInscopeNSContext() {
/* 234 */     return this.samlHeaderNSContext;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 238 */     this.buffer.writeToXMLStreamWriter(streamWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 243 */     writeTo(streamWriter);
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 247 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isHOK() {
/* 251 */     if (this.sig != null) {
/* 252 */       return true;
/*     */     }
/* 254 */     return false;
/*     */   }
/*     */   
/*     */   public boolean validateSignature() throws XWSSecurityException {
/* 258 */     if (isHOK()) {
/* 259 */       return this.sig.validate();
/*     */     }
/* 261 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void processNoValidation(XMLStreamReader reader, XMLStreamWriter buffer) throws XWSSecurityException {
/*     */     try {
/* 267 */       StreamUtil.writeCurrentEvent(reader, buffer);
/* 268 */       while (reader.hasNext()) {
/* 269 */         reader.next();
/* 270 */         if (_break(reader)) {
/* 271 */           StreamUtil.writeCurrentEvent(reader, buffer);
/* 272 */           reader.next();
/*     */           break;
/*     */         } 
/* 275 */         StreamUtil.writeCurrentEvent(reader, buffer);
/*     */       }
/*     */     
/* 278 */     } catch (XMLStreamException xe) {
/* 279 */       throw new XWSSecurityException("Error occurred while reading SAMLAssertion", xe);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(XMLStreamReader reader) throws XWSSecurityException {
/*     */     try {
/* 321 */       while (reader.hasNext()) {
/* 322 */         reader.next();
/* 323 */         switch (reader.getEventType()) {
/*     */           case 1:
/* 325 */             if (reader.getLocalName() == "Signature" && reader.getNamespaceURI() == "http://www.w3.org/2000/09/xmldsig#") {
/* 326 */               this.sig = new Signature(this.jpc, this.samlHeaderNSContext, this.creator, false);
/* 327 */               this.jpc.isSamlSignatureKey(true);
/* 328 */               this.sig.process(reader, false);
/* 329 */               this.jpc.isSamlSignatureKey(false);
/*     */             } 
/*     */             break;
/*     */         } 
/*     */         
/* 334 */         if (_break(reader)) {
/* 335 */           reader.next();
/*     */           break;
/*     */         } 
/* 338 */         if (reader.getEventType() == 1 && reader.getLocalName().equals("Advice")) {
/* 339 */           skipAdviceValidation(reader);
/*     */         }
/*     */       }
/*     */     
/* 343 */     } catch (XMLStreamException xe) {
/* 344 */       throw new XWSSecurityException("Error occurred while reading SAMLAssertion", xe);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skipAdviceValidation(XMLStreamReader reader) throws XWSSecurityException {
/* 369 */     int adviceElementCount = 1;
/*     */     try {
/* 371 */       while (!reader.getLocalName().equals("Advice") || reader.getEventType() != 2 || adviceElementCount != 0) {
/*     */ 
/*     */         
/* 374 */         reader.next();
/* 375 */         if (reader.getEventType() == 1 && reader.getLocalName().equals("Advice")) {
/* 376 */           adviceElementCount++;
/*     */         }
/* 378 */         if (reader.getEventType() == 2 && reader.getLocalName().equals("Advice")) {
/* 379 */           adviceElementCount--;
/*     */         }
/*     */       } 
/* 382 */     } catch (XMLStreamException xe) {
/* 383 */       throw new XWSSecurityException("Error occurred while reading SAMLAssertion", xe);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Key getKey() throws XWSSecurityException {
/* 388 */     if (this.key == null) {
/*     */       try {
/* 390 */         XMLStreamReader reader = readHeader();
/* 391 */         while (reader.getEventType() != 8) {
/* 392 */           switch (reader.getEventType()) {
/*     */             case 1:
/* 394 */               if (reader.getLocalName() == "SubjectConfirmation") {
/* 395 */                 reader.next();
/* 396 */                 while (reader.getLocalName() != "SubjectConfirmation" || reader.getEventType() != 2) {
/*     */                   
/* 398 */                   reader.next();
/* 399 */                   if (reader.getEventType() == 1 && reader.getLocalName() == "KeyInfo" && reader.getNamespaceURI() == "http://www.w3.org/2000/09/xmldsig#") {
/* 400 */                     this.jpc.isSAMLEK(true);
/* 401 */                     KeyInfoProcessor kip = new KeyInfoProcessor(this.jpc, KeySelector.Purpose.VERIFY, true);
/* 402 */                     this.key = kip.getKey(reader);
/* 403 */                     this.jpc.isSAMLEK(false);
/* 404 */                     return this.key;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 414 */           if (reader.hasNext()) {
/* 415 */             if (reader.getEventType() == 1 && reader.getLocalName().equals("Advice")) {
/* 416 */               int adviceElementCount = 1;
/* 417 */               while (!reader.getLocalName().equals("Advice") || reader.getEventType() != 2 || adviceElementCount != 0) {
/*     */ 
/*     */                 
/* 420 */                 reader.next();
/* 421 */                 if (reader.getEventType() == 1 && reader.getLocalName().equals("Advice")) {
/* 422 */                   adviceElementCount++;
/*     */                 }
/* 424 */                 if (reader.getEventType() == 2 && reader.getLocalName().equals("Advice")) {
/* 425 */                   adviceElementCount--;
/*     */                 }
/*     */               } 
/* 428 */               reader.next(); continue;
/*     */             } 
/* 430 */             reader.next();
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 436 */       catch (XMLStreamException xe) {
/* 437 */         throw new XWSSecurityException("Error occurred while obtaining Key from SAMLAssertion", xe);
/*     */       } 
/*     */     }
/* 440 */     return this.key;
/*     */   }
/*     */   
/*     */   private boolean _break(XMLStreamReader reader) {
/* 444 */     if (reader.getEventType() == 2 && 
/* 445 */       reader.getLocalName() == "Assertion") {
/* 446 */       String uri = reader.getNamespaceURI();
/* 447 */       if (uri == "urn:oasis:names:tc:SAML:2.0:assertion" || uri == "urn:oasis:names:tc:SAML:1.0:assertion" || uri == "urn:oasis:names:tc:SAML:1.0:assertion") {
/* 448 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 452 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\SAMLAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */