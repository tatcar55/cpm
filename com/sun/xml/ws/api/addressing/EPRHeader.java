/*     */ package com.sun.xml.ws.api.addressing;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.message.AbstractHeaderImpl;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.dom.DOMResult;
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
/*     */ final class EPRHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   private final String nsUri;
/*     */   private final String localName;
/*     */   private final WSEndpointReference epr;
/*     */   
/*     */   EPRHeader(QName tagName, WSEndpointReference epr) {
/*  76 */     this.nsUri = tagName.getNamespaceURI();
/*  77 */     this.localName = tagName.getLocalPart();
/*  78 */     this.epr = epr;
/*     */   }
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/*  82 */     return this.nsUri;
/*     */   }
/*     */   @NotNull
/*     */   public String getLocalPart() {
/*  86 */     return this.localName;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
/*     */     try {
/*  92 */       XMLStreamReader sr = this.epr.read("EndpointReference");
/*  93 */       while (sr.getEventType() != 1) {
/*  94 */         sr.next();
/*     */       }
/*  96 */       return sr.getAttributeValue(nsUri, localName);
/*  97 */     } catch (XMLStreamException e) {
/*     */       
/*  99 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 104 */     return this.epr.read(this.localName);
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 108 */     this.epr.writeTo(this.localName, w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/*     */     try {
/* 116 */       Transformer t = XmlUtil.newTransformer();
/* 117 */       SOAPHeader header = saaj.getSOAPHeader();
/* 118 */       if (header == null)
/* 119 */         header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 120 */       t.transform(this.epr.asSource(this.localName), new DOMResult(header));
/* 121 */     } catch (Exception e) {
/* 122 */       throw new SOAPException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 127 */     this.epr.writeTo(this.localName, contentHandler, errorHandler, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\addressing\EPRHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */