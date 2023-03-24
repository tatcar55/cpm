/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.AbstractCreatorProcessor;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferException;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferMark;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.PolicyBuilder;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.TokenValidator;
/*     */ import com.sun.xml.ws.security.opt.api.tokens.Timestamp;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.TimestampProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.util.XMLStreamReaderFactory;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.StreamFilter;
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
/*     */ public class TimestampHeader
/*     */   implements Timestamp, SecurityHeaderElement, TokenValidator, PolicyBuilder, NamespaceContextInfo, SecurityElementWriter
/*     */ {
/*  77 */   private String localPart = null;
/*  78 */   private String namespaceURI = null;
/*  79 */   private String id = "";
/*     */   
/*  81 */   private XMLStreamBuffer mark = null;
/*  82 */   private TimestampProcessor filter = null;
/*     */   
/*  84 */   private TimestampPolicy tsPolicy = null;
/*     */ 
/*     */   
/*     */   private HashMap<String, String> nsDecls;
/*     */ 
/*     */ 
/*     */   
/*     */   public TimestampHeader(XMLStreamReader reader, StreamReaderBufferCreator creator, HashMap<String, String> nsDecls, JAXBFilterProcessingContext ctx) throws XMLStreamException, XMLStreamBufferException {
/*  92 */     this.localPart = reader.getLocalName();
/*  93 */     this.namespaceURI = reader.getNamespaceURI();
/*  94 */     this.id = reader.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*  95 */     this.filter = new TimestampProcessor(ctx);
/*  96 */     this.mark = (XMLStreamBuffer)new XMLStreamBufferMark(nsDecls, (AbstractCreatorProcessor)creator);
/*  97 */     XMLStreamReader tsReader = XMLStreamReaderFactory.createFilteredXMLStreamReader(reader, (StreamFilter)this.filter);
/*  98 */     creator.createElementFragment(tsReader, true);
/*     */     
/* 100 */     this.tsPolicy = new TimestampPolicy();
/* 101 */     this.tsPolicy.setUUID(this.id);
/* 102 */     this.tsPolicy.setCreationTime(this.filter.getCreated());
/* 103 */     this.tsPolicy.setExpirationTime(this.filter.getExpires());
/*     */     
/* 105 */     this.nsDecls = nsDecls;
/*     */   }
/*     */   
/*     */   public void validate(ProcessingContext context) throws XWSSecurityException {
/* 109 */     context.getSecurityEnvironment().validateTimestamp(context.getExtraneousProperties(), this.filter.getCreated(), this.filter.getExpires(), this.tsPolicy.getMaxClockSkew(), this.tsPolicy.getTimestampFreshness());
/*     */   }
/*     */ 
/*     */   
/*     */   public WSSPolicy getPolicy() {
/* 114 */     return (WSSPolicy)this.tsPolicy;
/*     */   }
/*     */   
/*     */   public void setCreated(String created) {
/* 118 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setExpires(String expires) {
/* 122 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getCreatedValue() {
/* 126 */     return this.filter.getCreated();
/*     */   }
/*     */   
/*     */   public String getExpiresValue() {
/* 130 */     return this.filter.getExpires();
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 134 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId() {
/* 138 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 142 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 146 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 150 */     return this.localPart;
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 154 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getAttribute(QName name) {
/* 158 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 162 */     return (XMLStreamReader)this.mark.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 166 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 170 */     this.mark.writeToXMLStreamWriter(streamWriter);
/*     */   }
/*     */   
/*     */   public byte[] canonicalize(String algorithm, List<AttributeNS> namespaceDecls) {
/* 174 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isCanonicalized() {
/* 178 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public HashMap<String, String> getInscopeNSContext() {
/* 182 */     return this.nsDecls;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 186 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\TimestampHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */