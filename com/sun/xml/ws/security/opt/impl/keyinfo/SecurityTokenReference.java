/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.security.core.dsig.ObjectFactory;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.X509Data;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.DirectReference;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.secext10.KeyIdentifierType;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.ReferenceType;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import java.io.OutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityTokenReference
/*     */   extends SecurityTokenReferenceType
/*     */   implements SecurityTokenReference, SecurityHeaderElement, SecurityElementWriter, Token
/*     */ {
/*     */   private boolean isCanonicalized = false;
/*  94 */   SOAPVersion sv = SOAPVersion.SOAP_11;
/*     */ 
/*     */   
/*     */   public SecurityTokenReference(SOAPVersion soapVersion) {
/*  98 */     this.sv = soapVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReference(Reference ref) {
/* 105 */     JAXBElement refElem = null;
/* 106 */     String type = ref.getType();
/* 107 */     ObjectFactory objFac = new ObjectFactory();
/* 108 */     if ("Identifier".equals(type)) {
/* 109 */       refElem = objFac.createKeyIdentifier((KeyIdentifierType)ref);
/* 110 */     } else if ("Direct".equals(type) || "Reference".equals(type)) {
/* 111 */       refElem = objFac.createReference((ReferenceType)ref);
/* 112 */     } else if ("X509Data".equals(type)) {
/* 113 */       refElem = (new ObjectFactory()).createX509Data((X509Data)ref);
/*     */     } 
/*     */     
/* 116 */     if (refElem != null) {
/* 117 */       List<Object> list = getAny();
/* 118 */       list.clear();
/* 119 */       list.add(refElem);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference getReference() {
/* 127 */     List<Object> list = getAny();
/* 128 */     JAXBElement<DirectReference> obj = (JAXBElement)list.get(0);
/* 129 */     String local = obj.getName().getLocalPart();
/* 130 */     if ("Direct".equals(local) || "Reference".equals(local))
/* 131 */       return (Reference)obj.getValue(); 
/* 132 */     if ("KeyIdentifier".equalsIgnoreCase(local))
/* 133 */       return (Reference)obj.getValue(); 
/* 134 */     if ("X509Data".equals(local)) {
/* 135 */       return (Reference)obj.getValue();
/*     */     }
/*     */     
/* 138 */     return null;
/*     */   }
/*     */   
/*     */   public void setTokenType(String tokenType) {
/* 142 */     QName qname = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "TokenType", "wsse11");
/*     */     
/* 144 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 145 */     otherAttributes.put(qname, tokenType);
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/* 149 */     QName qname = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "TokenType", "wsse11");
/*     */     
/* 151 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 152 */     return otherAttributes.get(qname);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 156 */     return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalPart() {
/* 161 */     return "SecurityTokenReference";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
/* 166 */     QName qname = new QName(nsUri, localName);
/* 167 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 168 */     return otherAttributes.get(qname);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAttribute(@NotNull QName name) {
/* 173 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 174 */     return otherAttributes.get(name);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 178 */     XMLStreamBufferResult xbr = new XMLStreamBufferResult();
/* 179 */     JAXBElement<SecurityTokenReferenceType> strElem = (new ObjectFactory()).createSecurityTokenReference(this);
/*     */     try {
/* 181 */       getMarshaller().marshal(strElem, (Result)xbr);
/*     */     }
/* 183 */     catch (JAXBException je) {
/* 184 */       throw new XMLStreamException(je);
/*     */     } 
/* 186 */     return (XMLStreamReader)xbr.getXMLStreamBuffer().readAsXMLStreamReader();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 196 */     JAXBElement<SecurityTokenReferenceType> strElem = (new ObjectFactory()).createSecurityTokenReference(this);
/*     */     
/*     */     try {
/* 199 */       if (streamWriter instanceof Map) {
/* 200 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 201 */         if (os != null) {
/* 202 */           streamWriter.writeCharacters("");
/* 203 */           getMarshaller().marshal(strElem, os);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 208 */       getMarshaller().marshal(strElem, streamWriter);
/* 209 */     } catch (JAXBException e) {
/* 210 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] canonicalize(String algorithm, List<AttributeNS> namespaceDecls) {
/* 216 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isCanonicalized() {
/* 220 */     return this.isCanonicalized;
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 224 */     return JAXBUtil.createMarshaller(this.sv);
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 228 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 236 */     List<JAXBElement> list = getAny();
/* 237 */     if (list.size() > 0) {
/* 238 */       JAXBElement je = list.get(0);
/* 239 */       Object obj = je.getValue();
/* 240 */       if (obj instanceof DirectReference) {
/* 241 */         StringBuffer sb = new StringBuffer();
/* 242 */         sb.append("#");
/* 243 */         sb.append(id);
/* 244 */         return ((DirectReference)obj).getURI().equals(sb.toString());
/* 245 */       }  if (obj instanceof KeyIdentifier) {
/* 246 */         return ((KeyIdentifier)obj).refersToSecHdrWithId(id);
/*     */       }
/*     */     } 
/* 249 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap<Object, Object> props) throws XMLStreamException {
/*     */     try {
/* 260 */       Marshaller marshaller = getMarshaller();
/* 261 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 262 */       while (itr.hasNext()) {
/* 263 */         Map.Entry<Object, Object> entry = itr.next();
/* 264 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 266 */       writeTo(streamWriter);
/* 267 */     } catch (JAXBException jbe) {
/* 268 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getType() {
/* 273 */     return "SecurityTokenReference";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/* 277 */     return getReference();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\SecurityTokenReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */