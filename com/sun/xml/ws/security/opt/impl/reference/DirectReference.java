/*     */ package com.sun.xml.ws.security.opt.impl.reference;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.reference.DirectReference;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.ReferenceType;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DirectReference
/*     */   extends ReferenceType
/*     */   implements DirectReference, SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  82 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */ 
/*     */   
/*     */   public DirectReference(SOAPVersion sv) {
/*  86 */     this.soapVersion = sv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueType() {
/*  94 */     return super.getValueType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValueType(String valueType) {
/* 102 */     super.setValueType(valueType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI() {
/* 110 */     return super.getURI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURI(String uri) {
/* 118 */     super.setURI(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 126 */     return "Direct";
/*     */   }
/*     */   
/*     */   public String getId() {
/* 130 */     QName qname = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", "wsu");
/* 131 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 132 */     return otherAttributes.get(qname);
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 136 */     QName qname = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", "wsu");
/* 137 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 138 */     otherAttributes.put(qname, id);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/* 143 */     return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalPart() {
/* 148 */     return "Reference";
/*     */   }
/*     */   
/*     */   public String setAttribute(@NotNull String nsUri, @NotNull String localName, @NotNull String value) {
/* 152 */     QName qname = new QName(nsUri, localName);
/* 153 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 154 */     return otherAttributes.put(qname, value);
/*     */   }
/*     */   
/*     */   public String setAttribute(@NotNull QName name, @NotNull String value) {
/* 158 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 159 */     return otherAttributes.put(name, value);
/*     */   }
/*     */   
/*     */   public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
/* 163 */     QName qname = new QName(nsUri, localName);
/* 164 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 165 */     return otherAttributes.get(qname);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAttribute(@NotNull QName name) {
/* 170 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 171 */     return otherAttributes.get(name);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 175 */     XMLStreamBufferResult xbr = new XMLStreamBufferResult();
/* 176 */     JAXBElement<ReferenceType> deirectRefElem = (new ObjectFactory()).createReference(this);
/*     */     try {
/* 178 */       getMarshaller().marshal(deirectRefElem, (Result)xbr);
/*     */     }
/* 180 */     catch (JAXBException je) {
/* 181 */       throw new XMLStreamException(je);
/*     */     } 
/* 183 */     return (XMLStreamReader)xbr.getXMLStreamBuffer().readAsXMLStreamReader();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 194 */     JAXBElement<ReferenceType> deirectRefElem = (new ObjectFactory()).createReference(this);
/*     */     
/*     */     try {
/* 197 */       if (streamWriter instanceof Map) {
/* 198 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 199 */         if (os != null) {
/* 200 */           streamWriter.writeCharacters("");
/* 201 */           getMarshaller().marshal(deirectRefElem, os);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 206 */       getMarshaller().marshal(deirectRefElem, streamWriter);
/* 207 */     } catch (JAXBException e) {
/* 208 */       throw new XMLStreamException(e);
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
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap<Object, Object> props) throws XMLStreamException {
/*     */     try {
/* 221 */       Marshaller marshaller = getMarshaller();
/* 222 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 223 */       while (itr.hasNext()) {
/* 224 */         Map.Entry<Object, Object> entry = itr.next();
/* 225 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 227 */       writeTo(streamWriter);
/* 228 */     } catch (JAXBException jbe) {
/* 229 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 234 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getReferencedSecHeaderElements() {
/* 245 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addReferencedSecHeaderElement(String id) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 257 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\reference\DirectReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */