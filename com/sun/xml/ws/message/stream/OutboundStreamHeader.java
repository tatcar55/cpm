/*     */ package com.sun.xml.ws.message.stream;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferException;
/*     */ import com.sun.xml.ws.message.AbstractHeaderImpl;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OutboundStreamHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   private final XMLStreamBuffer infoset;
/*     */   private final String nsUri;
/*     */   private final String localName;
/*     */   private FinalArrayList<Attribute> attributes;
/*     */   private static final String TRUE_VALUE = "1";
/*     */   private static final String IS_REFERENCE_PARAMETER = "IsReferenceParameter";
/*     */   
/*     */   public OutboundStreamHeader(XMLStreamBuffer infoset, String nsUri, String localName) {
/*  82 */     this.infoset = infoset;
/*  83 */     this.nsUri = nsUri;
/*  84 */     this.localName = localName;
/*     */   }
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/*  88 */     return this.nsUri;
/*     */   }
/*     */   @NotNull
/*     */   public String getLocalPart() {
/*  92 */     return this.localName;
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/*  96 */     if (this.attributes == null)
/*  97 */       parseAttributes(); 
/*  98 */     for (int i = this.attributes.size() - 1; i >= 0; i--) {
/*  99 */       Attribute a = (Attribute)this.attributes.get(i);
/* 100 */       if (a.localName.equals(localName) && a.nsUri.equals(nsUri))
/* 101 */         return a.value; 
/*     */     } 
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseAttributes() {
/*     */     try {
/* 114 */       XMLStreamReader reader = readHeader();
/*     */       
/* 116 */       this.attributes = new FinalArrayList();
/*     */       
/* 118 */       for (int i = 0; i < reader.getAttributeCount(); i++) {
/* 119 */         String localName = reader.getAttributeLocalName(i);
/* 120 */         String namespaceURI = reader.getAttributeNamespace(i);
/* 121 */         String value = reader.getAttributeValue(i);
/*     */         
/* 123 */         this.attributes.add(new Attribute(namespaceURI, localName, value));
/*     */       } 
/* 125 */     } catch (XMLStreamException e) {
/* 126 */       throw new WebServiceException("Unable to read the attributes for {" + this.nsUri + "}" + this.localName + " header", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 131 */     return (XMLStreamReader)this.infoset.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 135 */     this.infoset.writeToXMLStreamWriter(w, true);
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/*     */     try {
/* 140 */       SOAPHeader header = saaj.getSOAPHeader();
/* 141 */       if (header == null)
/* 142 */         header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 143 */       this.infoset.writeTo(header);
/* 144 */     } catch (XMLStreamBufferException e) {
/* 145 */       throw new SOAPException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 150 */     this.infoset.writeTo(contentHandler, errorHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Attribute
/*     */   {
/*     */     final String nsUri;
/*     */ 
/*     */     
/*     */     final String localName;
/*     */     
/*     */     final String value;
/*     */ 
/*     */     
/*     */     public Attribute(String nsUri, String localName, String value) {
/* 166 */       this.nsUri = fixNull(nsUri);
/* 167 */       this.localName = localName;
/* 168 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static String fixNull(String s) {
/* 175 */       if (s == null) return ""; 
/* 176 */       return s;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\stream\OutboundStreamHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */