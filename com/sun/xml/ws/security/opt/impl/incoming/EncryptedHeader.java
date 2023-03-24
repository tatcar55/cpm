/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Key;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedHeader
/*     */   implements SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  89 */   private JAXBFilterProcessingContext pc = null;
/*     */   
/*  91 */   private String id = "";
/*     */   
/*  93 */   private String namespaceURI = "";
/*     */   
/*  95 */   private String localName = "";
/*     */   
/*  97 */   private EncryptedData ed = null;
/*     */   
/*  99 */   private HashMap<String, String> parentNS = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedHeader(XMLStreamReader reader, JAXBFilterProcessingContext pc, HashMap<String, String> parentNS) throws XMLStreamException, XWSSecurityException {
/* 107 */     this.pc = pc;
/*     */     
/* 109 */     this.parentNS = parentNS;
/*     */     
/* 111 */     process(reader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedData getEncryptedData() {
/* 119 */     return this.ed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncryptionAlgorithm() {
/* 127 */     return this.ed.getEncryptionAlgorithm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Key getKey() {
/* 135 */     return this.ed.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getCipherInputStream() throws XWSSecurityException {
/* 143 */     return this.ed.getCipherInputStream();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getCipherInputStream(Key key) throws XWSSecurityException {
/* 151 */     return this.ed.getCipherInputStream(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader getDecryptedData() throws XMLStreamException, XWSSecurityException {
/* 159 */     return this.ed.getDecryptedData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader getDecryptedData(Key key) throws XMLStreamException, XWSSecurityException {
/* 167 */     return this.ed.getDecryptedData(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 175 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 183 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/* 191 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/* 199 */     return this.namespaceURI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalPart() {
/* 207 */     return this.localName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 215 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 223 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 231 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 239 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void process(XMLStreamReader reader) throws XMLStreamException, XWSSecurityException {
/* 247 */     this.id = reader.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*     */     
/* 249 */     this.namespaceURI = reader.getNamespaceURI();
/*     */     
/* 251 */     this.localName = reader.getLocalName();
/*     */ 
/*     */ 
/*     */     
/* 255 */     while (reader.hasNext()) {
/*     */       
/* 257 */       reader.next();
/*     */       
/* 259 */       if (reader.getEventType() == 1)
/*     */       {
/* 261 */         if ("EncryptedData".equals(reader.getLocalName()) && "http://www.w3.org/2001/04/xmlenc#".equals(reader.getNamespaceURI()))
/*     */         {
/* 263 */           this.ed = new EncryptedData(reader, this.pc, this.parentNS);
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 271 */       if (reader.getEventType() == 2)
/*     */       {
/* 273 */         if ("EncryptedHeader".equals(reader.getLocalName()) && "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd".equals(reader.getNamespaceURI())) {
/*     */           break;
/*     */         }
/*     */       }
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
/*     */   public WSSPolicy getInferredKB() {
/* 289 */     return this.ed.getInferredKB();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\EncryptedHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */