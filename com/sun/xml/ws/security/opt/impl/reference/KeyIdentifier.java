/*     */ package com.sun.xml.ws.security.opt.impl.reference;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.reference.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.secext10.KeyIdentifierType;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import java.io.OutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyIdentifier
/*     */   extends KeyIdentifierType
/*     */   implements KeyIdentifier, SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  88 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */ 
/*     */   
/*     */   public KeyIdentifier(SOAPVersion sv) {
/*  92 */     this.soapVersion = sv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueType() {
/* 100 */     return super.getValueType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValueType(String valueType) {
/* 108 */     super.setValueType(valueType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncodingType() {
/* 116 */     return super.getEncodingType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncodingType(String value) {
/* 124 */     super.setEncodingType(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReferenceValue() {
/* 132 */     return getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReferenceValue(String referenceValue) {
/* 140 */     setValue(referenceValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 148 */     return "Identifier";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 156 */     QName qname = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", "wsu");
/* 157 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 158 */     return otherAttributes.get(qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/* 166 */     QName qname = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", "wsu");
/* 167 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 168 */     otherAttributes.put(qname, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/* 176 */     return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalPart() {
/* 186 */     return "KeyIdentifier".intern();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
/* 191 */     QName qname = new QName(nsUri, localName);
/* 192 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 193 */     return otherAttributes.get(qname);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAttribute(@NotNull QName name) {
/* 198 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 199 */     return otherAttributes.get(name);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 203 */     XMLStreamBufferResult xbr = new XMLStreamBufferResult();
/* 204 */     JAXBElement<KeyIdentifierType> keyIdentifierElem = (new ObjectFactory()).createKeyIdentifier(this);
/*     */     try {
/* 206 */       getMarshaller().marshal(keyIdentifierElem, (Result)xbr);
/*     */     }
/* 208 */     catch (JAXBException je) {
/* 209 */       throw new XMLStreamException(je);
/*     */     } 
/* 211 */     return (XMLStreamReader)xbr.getXMLStreamBuffer().readAsXMLStreamReader();
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
/* 222 */     JAXBElement<KeyIdentifierType> keyIdentifierElem = (new ObjectFactory()).createKeyIdentifier(this);
/*     */     
/*     */     try {
/* 225 */       if (streamWriter instanceof Map) {
/* 226 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 227 */         if (os != null) {
/* 228 */           streamWriter.writeCharacters("");
/* 229 */           getMarshaller().marshal(keyIdentifierElem, os);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 234 */       getMarshaller().marshal(keyIdentifierElem, streamWriter);
/* 235 */     } catch (JAXBException e) {
/* 236 */       throw new XMLStreamException(e);
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
/* 249 */       Marshaller marshaller = getMarshaller();
/* 250 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 251 */       while (itr.hasNext()) {
/* 252 */         Map.Entry<Object, Object> entry = itr.next();
/* 253 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 255 */       writeTo(streamWriter);
/* 256 */     } catch (JAXBException jbe) {
/* 257 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 262 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateReferenceValue(byte[] kerberosToken) throws XWSSecurityException {
/* 273 */     if (getValueType() == "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1") {
/*     */       try {
/* 275 */         setReferenceValue(Base64.encode(MessageDigest.getInstance("SHA-1").digest(kerberosToken)));
/* 276 */       } catch (NoSuchAlgorithmException ex) {
/* 277 */         throw new XWSSecurityException("Digest algorithm SHA-1 not found");
/*     */       } 
/*     */     } else {
/* 280 */       throw new XWSSecurityException(getValueType() + " ValueType not supported for kerberos tokens");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateReferenceValue(X509Certificate cert) throws XWSSecurityException {
/* 285 */     if (getValueType() == "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1") {
/*     */       try {
/* 287 */         setReferenceValue(Base64.encode(MessageDigest.getInstance("SHA-1").digest(cert.getEncoded())));
/* 288 */       } catch (NoSuchAlgorithmException ex) {
/* 289 */         throw new XWSSecurityException("Digest algorithm SHA-1 not found");
/* 290 */       } catch (CertificateEncodingException ex) {
/* 291 */         throw new XWSSecurityException("Error while getting certificate's raw content");
/*     */       } 
/* 293 */     } else if (getValueType() == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier") {
/* 294 */       byte[] keyId = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(cert);
/* 295 */       if (keyId == null) {
/*     */         return;
/*     */       }
/* 298 */       setReferenceValue(Base64.encode(keyId));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 308 */     String valueType = getValueType();
/* 309 */     if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(valueType))
/*     */     {
/* 311 */       if (getValue().equals(id)) {
/* 312 */         return true;
/*     */       }
/*     */     }
/* 315 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\reference\KeyIdentifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */