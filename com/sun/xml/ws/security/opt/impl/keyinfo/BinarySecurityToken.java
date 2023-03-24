/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.api.BridgeContext;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BinarySecurityToken;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.secext10.BinarySecurityTokenType;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.wss.logging.LogDomainConstants;
/*     */ import com.sun.xml.wss.logging.impl.crypto.LogStringsMessages;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public class BinarySecurityToken
/*     */   implements BinarySecurityToken, SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  92 */   private BinarySecurityTokenType bst = null;
/*     */   
/*  94 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */   
/*     */   public BinarySecurityToken(BinarySecurityTokenType token, SOAPVersion sv) {
/*  97 */     this.bst = token;
/*  98 */     this.soapVersion = sv;
/*     */   }
/*     */   
/*     */   public String getValueType() {
/* 102 */     return this.bst.getValueType();
/*     */   }
/*     */   
/*     */   public String getEncodingType() {
/* 106 */     return this.bst.getEncodingType();
/*     */   }
/*     */   
/*     */   public String getId() {
/* 110 */     return this.bst.getId();
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 114 */     this.bst.setId(id);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/* 119 */     return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getLocalPart() {
/* 124 */     return "BinarySecurityToken";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 132 */     XMLStreamBufferResult xbr = new XMLStreamBufferResult();
/* 133 */     JAXBElement<BinarySecurityTokenType> bstElem = (new ObjectFactory()).createBinarySecurityToken(this.bst);
/*     */     
/*     */     try {
/* 136 */       getMarshaller().marshal(bstElem, (Result)xbr);
/* 137 */     } catch (JAXBException je) {
/*     */       
/* 139 */       throw new XMLStreamException(je);
/*     */     } 
/* 141 */     return (XMLStreamReader)xbr.getXMLStreamBuffer().readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 145 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge, BridgeContext context) throws JAXBException {
/* 149 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 153 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 161 */     JAXBElement<BinarySecurityTokenType> bstElem = (new ObjectFactory()).createBinarySecurityToken(this.bst);
/*     */ 
/*     */     
/*     */     try {
/* 165 */       if (streamWriter instanceof Map) {
/* 166 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 167 */         if (os != null) {
/* 168 */           streamWriter.writeCharacters("");
/* 169 */           Marshaller writer = getMarshaller();
/*     */           
/* 171 */           writer.marshal(bstElem, os);
/*     */           return;
/*     */         } 
/*     */       } 
/* 175 */       getMarshaller().marshal(bstElem, streamWriter);
/* 176 */     } catch (JAXBException e) {
/*     */       
/* 178 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 183 */     NodeList nl = saaj.getSOAPHeader().getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
/*     */     try {
/* 185 */       Marshaller writer = getMarshaller();
/*     */       
/* 187 */       writer.marshal(this.bst, nl.item(0));
/* 188 */     } catch (JAXBException ex) {
/* 189 */       throw new SOAPException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 194 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getTokenValue() {
/*     */     try {
/* 202 */       return Base64.decode(this.bst.getValue());
/* 203 */     } catch (Base64DecodingException ex) {
/* 204 */       LogDomainConstants.CRYPTO_IMPL_LOGGER.log(Level.SEVERE, LogStringsMessages.WSS_1243_BST_DECODING_ERROR(), ex);
/* 205 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 210 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {}
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 217 */     return false;
/*     */   }
/*     */   
/*     */   public X509Certificate getCertificate() {
/* 221 */     return null;
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
/* 232 */       Marshaller marshaller = getMarshaller();
/* 233 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 234 */       while (itr.hasNext()) {
/* 235 */         Map.Entry<Object, Object> entry = itr.next();
/* 236 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 238 */       writeTo(streamWriter);
/* 239 */     } catch (JAXBException jbe) {
/*     */       
/* 241 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\BinarySecurityToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */