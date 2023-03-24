/*     */ package com.sun.xml.ws.security.opt.impl.message;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.stax.StreamWriterBufferCreator;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.opt.impl.util.XMLStreamFilterWithId;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPBody
/*     */ {
/*     */   private static final String BODY = "Body";
/*     */   private static final String BODY_PREFIX = "S";
/*     */   private Message message;
/*     */   private SOAPVersion soapVersion;
/*     */   private SecurityElement bodyContent;
/*     */   private String wsuId;
/*     */   private String contentId;
/*  76 */   private MutableXMLStreamBuffer buffer = null;
/*  77 */   private List attributeValuePrefixes = null;
/*     */   
/*     */   public SOAPBody(Message message) {
/*  80 */     this.message = message;
/*  81 */     this.soapVersion = SOAPVersion.SOAP_11;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPBody(Message message, SOAPVersion soapVersion) {
/*  91 */     this.message = message;
/*  92 */     this.soapVersion = soapVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPBody(byte[] payLoad, SOAPVersion soapVersion) {
/*  97 */     this.soapVersion = soapVersion;
/*     */   }
/*     */   
/*     */   public SOAPBody(SecurityElement se, SOAPVersion soapVersion) {
/* 101 */     this.bodyContent = se;
/* 102 */     this.soapVersion = soapVersion;
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 106 */     return this.soapVersion;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 110 */     return this.wsuId;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 114 */     this.wsuId = id;
/*     */   }
/*     */   
/*     */   public String getBodyContentId() {
/* 118 */     if (this.contentId != null)
/* 119 */       return this.contentId; 
/* 120 */     if (this.bodyContent != null)
/* 121 */       return this.bodyContent.getId(); 
/* 122 */     return null;
/*     */   }
/*     */   
/*     */   public void setBodyContentId(String id) {
/* 126 */     this.contentId = id;
/*     */   }
/*     */   
/*     */   public void writePayload(XMLStreamWriter writer) throws XMLStreamException {
/* 130 */     if (this.message != null) {
/* 131 */       if (getBodyContentId() == null) {
/* 132 */         this.message.writePayloadTo(writer);
/*     */       } else {
/* 134 */         boolean isSOAP12 = (this.soapVersion == SOAPVersion.SOAP_12);
/* 135 */         XMLStreamFilterWithId xmlStreamFilterWithId = new XMLStreamFilterWithId(writer, new NamespaceContextEx(isSOAP12), getBodyContentId());
/* 136 */         this.message.writePayloadTo((XMLStreamWriter)xmlStreamFilterWithId);
/*     */       } 
/* 138 */     } else if (this.bodyContent != null) {
/* 139 */       ((SecurityElementWriter)this.bodyContent).writeTo(writer);
/* 140 */     } else if (this.buffer != null) {
/* 141 */       if (writer instanceof StAXEXC14nCanonicalizerImpl && 
/* 142 */         this.attributeValuePrefixes != null && !this.attributeValuePrefixes.isEmpty()) {
/* 143 */         List<?> prefixList = ((StAXEXC14nCanonicalizerImpl)writer).getInclusivePrefixList();
/* 144 */         if (prefixList == null) {
/* 145 */           prefixList = new ArrayList();
/*     */         }
/* 147 */         prefixList.addAll(this.attributeValuePrefixes);
/*     */         
/* 149 */         HashSet<?> set = new HashSet(prefixList);
/* 150 */         prefixList = new ArrayList(set);
/* 151 */         ((StAXEXC14nCanonicalizerImpl)writer).setInclusivePrefixList(prefixList);
/*     */       } 
/*     */       
/* 154 */       this.buffer.writeToXMLStreamWriter(writer);
/*     */     } else {
/* 156 */       throw new UnsupportedOperationException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter writer) throws XMLStreamException {
/* 162 */     writer.writeStartElement("S", "Body", this.soapVersion.nsUri);
/* 163 */     if (this.wsuId != null) {
/* 164 */       writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.wsuId);
/*     */     }
/* 166 */     writePayload(writer);
/* 167 */     writer.writeEndElement();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 172 */     if (this.message != null) {
/* 173 */       return this.message.getPayloadNamespaceURI();
/*     */     }
/* 175 */     if (this.bodyContent != null) {
/* 176 */       return this.bodyContent.getNamespaceURI();
/*     */     }
/* 178 */     return null;
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 182 */     if (this.message != null) {
/* 183 */       return this.message.getPayloadLocalPart();
/*     */     }
/* 185 */     if (this.bodyContent != null) {
/* 186 */       return this.bodyContent.getLocalPart();
/*     */     }
/* 188 */     return null;
/*     */   }
/*     */   
/*     */   public XMLStreamReader read() throws XMLStreamException {
/* 192 */     if (this.message != null)
/* 193 */       return this.message.readPayload(); 
/* 194 */     if (this.bodyContent != null) {
/* 195 */       return this.bodyContent.readHeader();
/*     */     }
/* 197 */     throw new XMLStreamException("Invalid SOAPBody");
/*     */   }
/*     */   
/*     */   public void cachePayLoad() throws XMLStreamException {
/* 201 */     if (this.message != null && (
/* 202 */       this.message instanceof com.sun.xml.ws.message.stream.StreamMessage || this.message instanceof com.sun.xml.ws.message.source.PayloadSourceMessage || this.message instanceof com.sun.xml.ws.message.jaxb.JAXBMessage))
/*     */     {
/* 204 */       if (this.buffer == null) {
/* 205 */         this.buffer = new MutableXMLStreamBuffer();
/* 206 */         StreamWriterBufferCreator creator = new StreamWriterBufferCreator(this.buffer);
/*     */         
/* 208 */         creator.setCheckAttributeValue(true);
/* 209 */         writePayload((XMLStreamWriter)creator);
/* 210 */         this.attributeValuePrefixes = creator.getAttributeValuePrefixes();
/* 211 */         this.message = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List getAttributeValuePrefixes() {
/* 218 */     return this.attributeValuePrefixes;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\message\SOAPBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */