/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.SignedData;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.opt.impl.util.XMLStreamFilterWithId;
/*     */ import java.io.OutputStream;
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
/*     */ public class SignedMessageHeader
/*     */   extends SignedMessagePart
/*     */   implements SecurityHeaderElement, SignedData, SecurityElementWriter
/*     */ {
/*  66 */   private Header header = null;
/*  67 */   private SecurityHeaderElement she = null;
/*     */   
/*     */   private byte[] digestValue;
/*     */   
/*     */   private String id;
/*     */   
/*  73 */   JAXBFilterProcessingContext context = null;
/*  74 */   private MutableXMLStreamBuffer buffer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignedMessageHeader(Header header, String id, JAXBFilterProcessingContext context) {
/*  83 */     this.header = header;
/*  84 */     this.id = id;
/*  85 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignedMessageHeader(SecurityHeaderElement she) {
/*  94 */     this.she = she;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 102 */     if (this.header != null) {
/* 103 */       return this.id;
/*     */     }
/* 105 */     return this.she.getId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/* 113 */     if (this.header != null) {
/* 114 */       this.id = id;
/*     */     } else {
/* 116 */       this.she.setId(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/* 126 */     if (this.header != null) {
/* 127 */       return this.header.getNamespaceURI();
/*     */     }
/* 129 */     return this.she.getNamespaceURI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getLocalPart() {
/* 139 */     if (this.header != null) {
/* 140 */       return this.header.getLocalPart();
/*     */     }
/* 142 */     return this.she.getLocalPart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 151 */     if (this.buffer == null) {
/* 152 */       this.buffer = new MutableXMLStreamBuffer();
/* 153 */       XMLStreamWriter writer = this.buffer.createFromXMLStreamWriter();
/* 154 */       writeTo(writer);
/*     */     } 
/* 156 */     return (XMLStreamReader)this.buffer.readAsXMLStreamReader();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 163 */     throw new UnsupportedOperationException("Not implemented yet");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 170 */     if (this.header != null) {
/* 171 */       XMLStreamFilterWithId xmlStreamFilterWithId = new XMLStreamFilterWithId(streamWriter, (NamespaceContextEx)this.context.getNamespaceContext(), this.id);
/* 172 */       this.header.writeTo((XMLStreamWriter)xmlStreamFilterWithId);
/*     */     } else {
/* 174 */       ((SecurityElementWriter)this.she).writeTo(streamWriter);
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
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 190 */     if (this.header != null) {
/* 191 */       XMLStreamFilterWithId xmlStreamFilterWithId = new XMLStreamFilterWithId(streamWriter, (NamespaceContextEx)this.context.getNamespaceContext(), this.id);
/* 192 */       this.header.writeTo((XMLStreamWriter)xmlStreamFilterWithId);
/*     */     } else {
/* 194 */       ((SecurityElementWriter)this.she).writeTo(streamWriter, props);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDigestValue(byte[] digestValue) {
/* 199 */     this.digestValue = digestValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getDigestValue() {
/* 207 */     return this.digestValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 217 */     return this.she.refersToSecHdrWithId(id);
/*     */   }
/*     */   
/*     */   public Header getSignedHeader() {
/* 221 */     return this.header;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\SignedMessageHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */