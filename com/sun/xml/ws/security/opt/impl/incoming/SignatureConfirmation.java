/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.AbstractCreatorProcessor;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferMark;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.PolicyBuilder;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.TokenValidator;
/*     */ import com.sun.xml.ws.security.opt.impl.util.XMLStreamReaderFactory;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureConfirmationPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.filter.LogStringsMessages;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.StreamFilter;
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
/*     */ public class SignatureConfirmation
/*     */   implements SecurityHeaderElement, TokenValidator, PolicyBuilder, NamespaceContextInfo, SecurityElementWriter
/*     */ {
/*  75 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*  79 */   private String id = "";
/*  80 */   private String namespaceURI = "";
/*  81 */   private String localName = "";
/*  82 */   private String signatureValue = null;
/*     */   
/*  84 */   private SignatureConfirmationPolicy scPolicy = null;
/*     */   private HashMap<String, String> nsDecls;
/*  86 */   private XMLStreamBuffer mark = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureConfirmation(XMLStreamReader reader, StreamReaderBufferCreator creator, HashMap<String, String> nsDecls, XMLInputFactory staxIF) throws XMLStreamException {
/*  94 */     this.namespaceURI = reader.getNamespaceURI();
/*  95 */     this.localName = reader.getLocalName();
/*  96 */     this.id = reader.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*     */     
/*  98 */     this.mark = (XMLStreamBuffer)new XMLStreamBufferMark(nsDecls, (AbstractCreatorProcessor)creator);
/*  99 */     creator.createElementFragment(XMLStreamReaderFactory.createFilteredXMLStreamReader(reader, new SCProcessor()), false);
/*     */     
/* 101 */     this.nsDecls = nsDecls;
/*     */     
/* 103 */     this.scPolicy = new SignatureConfirmationPolicy();
/* 104 */     this.scPolicy.setSignatureValue(this.signatureValue);
/*     */   }
/*     */   
/*     */   public String getSignatureValue() {
/* 108 */     return this.signatureValue;
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 112 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId() {
/* 116 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 120 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 124 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 128 */     return this.localName;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 132 */     return (XMLStreamReader)this.mark.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 136 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 140 */     this.mark.writeToXMLStreamWriter(streamWriter);
/*     */   }
/*     */   
/*     */   public void validate(ProcessingContext context) throws XWSSecurityException {
/* 144 */     Object temp = context.getExtraneousProperty("SignatureConfirmation");
/* 145 */     List scList = null;
/* 146 */     if (temp != null && temp instanceof ArrayList)
/* 147 */       scList = (ArrayList)temp; 
/* 148 */     if (scList != null) {
/* 149 */       if (this.signatureValue == null) {
/* 150 */         if (!scList.isEmpty()) {
/* 151 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1435_SIGNATURE_CONFIRMATION_VALIDATION_FAILURE());
/* 152 */           throw new XWSSecurityException("Failure in SignatureConfirmation Validation");
/*     */         } 
/* 154 */       } else if (scList.contains(this.signatureValue)) {
/*     */         
/* 156 */         scList.remove(this.signatureValue);
/*     */       } else {
/* 158 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1435_SIGNATURE_CONFIRMATION_VALIDATION_FAILURE());
/* 159 */         throw new XWSSecurityException("Mismatch in SignatureConfirmation Element");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public WSSPolicy getPolicy() {
/* 165 */     return (WSSPolicy)this.scPolicy;
/*     */   }
/*     */   
/*     */   public HashMap<String, String> getInscopeNSContext() {
/* 169 */     return this.nsDecls;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 173 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   class SCProcessor
/*     */     implements StreamFilter {
/*     */     public boolean accept(XMLStreamReader reader) {
/* 179 */       if (reader.getEventType() == 2 && 
/* 180 */         reader.getLocalName() == SignatureConfirmation.this.localName && reader.getNamespaceURI() == SignatureConfirmation.this.namespaceURI) {
/* 181 */         this.elementRead = true;
/*     */       }
/*     */       
/* 184 */       if (!this.elementRead && reader.getEventType() == 1) {
/* 185 */         SignatureConfirmation.this.signatureValue = reader.getAttributeValue(null, "Value");
/*     */       }
/* 187 */       return true;
/*     */     }
/*     */     
/*     */     boolean elementRead = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\SignatureConfirmation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */