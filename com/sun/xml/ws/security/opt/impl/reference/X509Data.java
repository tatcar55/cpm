/*     */ package com.sun.xml.ws.security.opt.impl.reference;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.ObjectFactory;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.X509Data;
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
/*     */ public class X509Data
/*     */   extends X509Data
/*     */   implements Reference, SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  70 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */ 
/*     */   
/*     */   public X509Data(SOAPVersion sv) {
/*  74 */     this.soapVersion = sv;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  78 */     return "X509Data";
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  86 */     throw new UnsupportedOperationException("Id attribute not allowed for X509Data");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  90 */     throw new UnsupportedOperationException("Id attribute not allowed for X509Data");
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/*  94 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/*  98 */     return "X509Data".intern();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 102 */     XMLStreamBufferResult xbr = new XMLStreamBufferResult();
/*     */     
/* 104 */     JAXBElement<X509Data> x509DataElem = (new ObjectFactory()).createX509Data(this);
/*     */     try {
/* 106 */       getMarshaller().marshal(x509DataElem, (Result)xbr);
/*     */     }
/* 108 */     catch (JAXBException je) {
/* 109 */       throw new XMLStreamException(je);
/*     */     } 
/* 111 */     return (XMLStreamReader)xbr.getXMLStreamBuffer().readAsXMLStreamReader();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 116 */     JAXBElement<X509Data> x509DataElem = (new ObjectFactory()).createX509Data(this);
/*     */     
/*     */     try {
/* 119 */       if (streamWriter instanceof Map) {
/* 120 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 121 */         if (os != null) {
/* 122 */           streamWriter.writeCharacters("");
/* 123 */           getMarshaller().marshal(x509DataElem, os);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 128 */       getMarshaller().marshal(x509DataElem, streamWriter);
/* 129 */     } catch (JAXBException e) {
/* 130 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap<Object, Object> props) throws XMLStreamException {
/*     */     try {
/* 136 */       Marshaller marshaller = getMarshaller();
/* 137 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 138 */       while (itr.hasNext()) {
/* 139 */         Map.Entry<Object, Object> entry = itr.next();
/* 140 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 142 */       writeTo(streamWriter);
/* 143 */     } catch (JAXBException jbe) {
/* 144 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {}
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 152 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\reference\X509Data.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */