/*     */ package com.sun.xml.ws.security.opt.impl.tokens;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.secext11.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext11.SignatureConfirmationType;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends SignatureConfirmationType
/*     */   implements SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  74 */   private final ObjectFactory objFac = new ObjectFactory();
/*  75 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */ 
/*     */   
/*     */   public SignatureConfirmation(String id, SOAPVersion sv) {
/*  79 */     setId(id);
/*  80 */     this.soapVersion = sv;
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/*  84 */     return "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd";
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/*  88 */     return "SignatureConfirmation";
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/*  92 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getAttribute(QName name) {
/*  96 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 100 */     XMLStreamBufferResult xbr = new XMLStreamBufferResult();
/* 101 */     JAXBElement<SignatureConfirmationType> scElem = this.objFac.createSignatureConfirmation(this);
/*     */     try {
/* 103 */       getMarshaller().marshal(scElem, (Result)xbr);
/*     */     }
/* 105 */     catch (JAXBException je) {
/* 106 */       throw new XMLStreamException(je);
/*     */     } 
/* 108 */     return (XMLStreamReader)xbr.getXMLStreamBuffer().readAsXMLStreamReader();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {}
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 115 */     JAXBElement<SignatureConfirmationType> scElem = this.objFac.createSignatureConfirmation(this);
/*     */     
/*     */     try {
/* 118 */       if (streamWriter instanceof Map) {
/* 119 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 120 */         if (os != null) {
/* 121 */           streamWriter.writeCharacters("");
/* 122 */           getMarshaller().marshal(scElem, os);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 127 */       getMarshaller().marshal(scElem, streamWriter);
/* 128 */     } catch (JAXBException e) {
/* 129 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 134 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 143 */     return false;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap<Object, Object> props) throws XMLStreamException {
/*     */     try {
/* 148 */       Marshaller marshaller = getMarshaller();
/* 149 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 150 */       while (itr.hasNext()) {
/* 151 */         Map.Entry<Object, Object> entry = itr.next();
/* 152 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 154 */       writeTo(streamWriter);
/* 155 */     } catch (JAXBException jbe) {
/* 156 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\tokens\SignatureConfirmation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */