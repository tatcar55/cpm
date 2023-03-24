/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferProcessor;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URI;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public class SecurityContextToken
/*     */   implements SecurityHeaderElement, NamespaceContextInfo, SecurityElementWriter, SecurityContextToken
/*     */ {
/*  66 */   private static final String IDENTIFIER = "Identifier".intern();
/*  67 */   private static final String INSTANCE = "Instance".intern();
/*     */   
/*     */   private static final int IDENTIFIER_ELEMENT = 1;
/*     */   
/*     */   private static final int INSTANCE_ELEMENT = 2;
/*  72 */   private String id = "";
/*  73 */   private String namespaceURI = "";
/*  74 */   private String localName = "";
/*  75 */   private String identifier = null;
/*  76 */   private String instance = null;
/*  77 */   private List extElements = null;
/*     */   private JAXBFilterProcessingContext pc;
/*  79 */   private MutableXMLStreamBuffer buffer = null;
/*     */ 
/*     */   
/*     */   private HashMap<String, String> nsDecls;
/*     */ 
/*     */   
/*     */   public SecurityContextToken(XMLStreamReader reader, JAXBFilterProcessingContext pc, HashMap<String, String> nsDecls) throws XMLStreamException, XWSSecurityException {
/*  86 */     this.pc = pc;
/*  87 */     this.nsDecls = nsDecls;
/*  88 */     this.id = reader.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*  89 */     this.namespaceURI = reader.getNamespaceURI();
/*  90 */     this.localName = reader.getLocalName();
/*  91 */     this.buffer = new MutableXMLStreamBuffer();
/*  92 */     this.buffer.createFromXMLStreamReader(reader);
/*  93 */     StreamReaderBufferProcessor streamReaderBufferProcessor = this.buffer.readAsXMLStreamReader();
/*  94 */     streamReaderBufferProcessor.next();
/*  95 */     process((XMLStreamReader)streamReaderBufferProcessor);
/*     */   }
/*     */   
/*     */   public String getSCId() {
/*  99 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public URI getIdentifier() {
/*     */     try {
/* 104 */       return new URI(this.identifier);
/* 105 */     } catch (Exception e) {
/* 106 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getInstance() {
/* 111 */     return this.instance;
/*     */   }
/*     */   
/*     */   public List getExtElements() {
/* 115 */     return this.extElements;
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 119 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId() {
/* 123 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 127 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 131 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 135 */     return this.localName;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 139 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 143 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 147 */     this.buffer.writeToXMLStreamWriter(streamWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public HashMap<String, String> getInscopeNSContext() {
/* 152 */     return this.nsDecls;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void process(XMLStreamReader reader) throws XMLStreamException, XWSSecurityException {
/* 158 */     if (StreamUtil.moveToNextElement(reader)) {
/* 159 */       int refElement = getEventType(reader);
/* 160 */       while (reader.getEventType() != 8) {
/* 161 */         switch (refElement) {
/*     */           case 1:
/* 163 */             this.identifier = reader.getElementText();
/*     */             break;
/*     */           
/*     */           case 2:
/* 167 */             this.instance = reader.getElementText();
/*     */             break;
/*     */ 
/*     */           
/*     */           default:
/* 172 */             throw new XWSSecurityException("Element name " + reader.getName() + " is not recognized under SecurityContextToken");
/*     */         } 
/*     */         
/* 175 */         if (StreamUtil.moveToNextStartOREndElement(reader) && StreamUtil._break(reader, "SecurityContextToken", "http://schemas.xmlsoap.org/ws/2005/02/sc")) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 180 */         if (reader.getEventType() != 1) {
/* 181 */           StreamUtil.moveToNextElement(reader);
/*     */         }
/*     */         
/* 184 */         refElement = getEventType(reader);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getEventType(XMLStreamReader reader) {
/* 191 */     if (reader.getEventType() == 1) {
/* 192 */       if (reader.getLocalName() == IDENTIFIER) {
/* 193 */         return 1;
/*     */       }
/* 195 */       if (reader.getLocalName() == INSTANCE) {
/* 196 */         return 2;
/*     */       }
/*     */     } 
/* 199 */     return -1;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 203 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getWsuId() {
/* 207 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 211 */     return "http://schemas.xmlsoap.org/ws/2005/02/sc/sct";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/* 215 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\SecurityContextToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */