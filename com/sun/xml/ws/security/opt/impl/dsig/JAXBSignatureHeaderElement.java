/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.CustomStreamWriterImpl;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Signature;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.dsig.XMLSignContext;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
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
/*     */ public class JAXBSignatureHeaderElement
/*     */   implements SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*     */   private boolean isCanonicalized = false;
/*  86 */   private byte[] cs = null;
/*     */   
/*  88 */   private Signature signature = null;
/*  89 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*  90 */   private Marshaller marshaller = null;
/*  91 */   private XMLSignContext signContext = null;
/*     */   
/*     */   public JAXBSignatureHeaderElement(Signature signature, SOAPVersion soapVersion) {
/*  94 */     this.signature = signature;
/*  95 */     this.soapVersion = soapVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public JAXBSignatureHeaderElement(Signature signature, SOAPVersion soapVersion, XMLSignContext signctx) {
/* 100 */     this.signature = signature;
/* 101 */     this.soapVersion = soapVersion;
/* 102 */     this.signContext = signctx;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 106 */     return this.signature.getId();
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 110 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/* 115 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalPart() {
/* 120 */     return "Signature";
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 124 */     XMLStreamBufferResult xbr = new XMLStreamBufferResult();
/*     */     try {
/* 126 */       getMarshaller().marshal(this.signature, (Result)xbr);
/* 127 */     } catch (JAXBException je) {
/*     */       
/* 129 */       throw new XMLStreamException(je);
/*     */     } 
/* 131 */     return (XMLStreamReader)xbr.getXMLStreamBuffer().readAsXMLStreamReader();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/*     */     try {
/* 142 */       if (streamWriter instanceof Map) {
/* 143 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 144 */         if (os != null) {
/* 145 */           streamWriter.writeCharacters("");
/* 146 */           getMarshaller().marshal(this.signature, os);
/*     */           return;
/*     */         } 
/* 149 */       } else if (streamWriter instanceof org.jvnet.staxex.XMLStreamWriterEx) {
/* 150 */         CustomStreamWriterImpl swi = new CustomStreamWriterImpl(streamWriter);
/* 151 */         getMarshaller().marshal(this.signature, (XMLStreamWriter)swi);
/*     */       } else {
/* 153 */         getMarshaller().marshal(this.signature, streamWriter);
/*     */       }
/*     */     
/* 156 */     } catch (JAXBException e) {
/* 157 */       throw new XMLStreamException(e);
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
/* 170 */       Marshaller marshaller = getMarshaller();
/* 171 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 172 */       while (itr.hasNext()) {
/* 173 */         Map.Entry<Object, Object> entry = itr.next();
/* 174 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/*     */ 
/*     */       
/* 178 */       marshaller.marshal(this.signature, streamWriter);
/* 179 */     } catch (JAXBException jbe) {
/*     */       
/* 181 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] canonicalize(String algorithm, List<AttributeNS> namespaceDecls) {
/* 187 */     if (!isCanonicalized()) {
/* 188 */       canonicalizeSignature();
/*     */     }
/* 190 */     return this.cs;
/*     */   }
/*     */   
/*     */   public boolean isCanonicalized() {
/* 194 */     return this.isCanonicalized;
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 198 */     if (this.marshaller == null) {
/* 199 */       this.marshaller = JAXBUtil.createMarshaller(this.soapVersion);
/*     */     }
/* 201 */     return this.marshaller;
/*     */   }
/*     */   
/*     */   private void canonicalizeSignature() {
/* 205 */     throw new UnsupportedOperationException("Not yet implemented");
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 209 */     throw new UnsupportedOperationException("Not yet implemented");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 218 */     StringBuffer sb = new StringBuffer();
/* 219 */     sb.append("#");
/* 220 */     sb.append(id);
/* 221 */     String refId = sb.toString();
/* 222 */     KeyInfo ki = this.signature.getKeyInfo();
/* 223 */     if (ki != null) {
/* 224 */       List<JAXBElement> list = ki.getContent();
/* 225 */       if (list.size() > 0) {
/* 226 */         JAXBElement je = list.get(0);
/* 227 */         Object data = je.getValue();
/*     */         
/* 229 */         if (data instanceof SecurityHeaderElement && (
/* 230 */           (SecurityHeaderElement)data).refersToSecHdrWithId(id)) {
/* 231 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 236 */     List<Reference> refList = this.signature.getSignedInfo().getReferences();
/* 237 */     for (int i = 0; i < refList.size(); i++) {
/* 238 */       Reference ref = refList.get(i);
/* 239 */       if (ref.getURI().equals(refId)) {
/* 240 */         return true;
/*     */       }
/*     */     } 
/* 243 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sign() throws XMLStreamException {
/*     */     try {
/* 251 */       this.signature.sign(this.signContext);
/* 252 */     } catch (MarshalException me) {
/* 253 */       throw new XMLStreamException(me);
/* 254 */     } catch (XMLSignatureException xse) {
/* 255 */       throw new XMLStreamException(xse);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\JAXBSignatureHeaderElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */