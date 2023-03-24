/*     */ package com.sun.xml.ws.security.opt.impl.reference;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.ObjectFactory;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.X509IssuerSerial;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509IssuerSerial
/*     */   extends X509IssuerSerial
/*     */   implements SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  79 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */ 
/*     */   
/*     */   public X509IssuerSerial(SOAPVersion sv) {
/*  83 */     this.soapVersion = sv;
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  91 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  95 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/*  99 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 103 */     return "X509IssuerSerial".intern();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 107 */     XMLStreamBufferResult xbr = new XMLStreamBufferResult();
/*     */     
/* 109 */     JAXBElement<X509IssuerSerial> issuerSerialElem = (new ObjectFactory()).createX509DataTypeX509IssuerSerial(this);
/*     */     try {
/* 111 */       getMarshaller().marshal(issuerSerialElem, (Result)xbr);
/*     */     }
/* 113 */     catch (JAXBException je) {
/* 114 */       throw new XMLStreamException(je);
/*     */     } 
/* 116 */     return (XMLStreamReader)xbr.getXMLStreamBuffer().readAsXMLStreamReader();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 121 */     JAXBElement<X509IssuerSerial> issuerSerialElem = (new ObjectFactory()).createX509DataTypeX509IssuerSerial(this);
/*     */     
/*     */     try {
/* 124 */       if (streamWriter instanceof Map) {
/* 125 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 126 */         if (os != null) {
/* 127 */           streamWriter.writeCharacters("");
/* 128 */           getMarshaller().marshal(issuerSerialElem, os);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 133 */       getMarshaller().marshal(issuerSerialElem, streamWriter);
/* 134 */     } catch (JAXBException e) {
/* 135 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap<Object, Object> props) throws XMLStreamException {
/*     */     try {
/* 141 */       Marshaller marshaller = getMarshaller();
/* 142 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 143 */       while (itr.hasNext()) {
/* 144 */         Map.Entry<Object, Object> entry = itr.next();
/* 145 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 147 */       writeTo(streamWriter);
/* 148 */     } catch (JAXBException jbe) {
/* 149 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {}
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 157 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\reference\X509IssuerSerial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */