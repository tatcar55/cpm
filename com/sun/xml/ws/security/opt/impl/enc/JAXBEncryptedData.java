/*     */ package com.sun.xml.ws.security.opt.impl.enc;
/*     */ 
/*     */ import com.sun.xml.security.core.xenc.CVAdapter;
/*     */ import com.sun.xml.security.core.xenc.EncryptedDataType;
/*     */ import com.sun.xml.security.core.xenc.ObjectFactory;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.EncryptedData;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.SSEData;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import com.sun.xml.wss.logging.impl.opt.crypto.LogStringsMessages;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Key;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class JAXBEncryptedData
/*     */   implements EncryptedData, SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  91 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.crypto", "com.sun.xml.wss.logging.impl.opt.crypto.LogStrings");
/*     */ 
/*     */   
/*  94 */   private EncryptedDataType edt = null;
/*  95 */   private Data data = null;
/*  96 */   private Key key = null;
/*  97 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*  98 */   private CryptoProcessor dep = null;
/*     */   
/*     */   public JAXBEncryptedData(EncryptedDataType edt, Data data, Key key, SOAPVersion soapVersion) {
/* 101 */     this.edt = edt;
/* 102 */     this.key = key;
/* 103 */     this.data = data;
/* 104 */     this.soapVersion = soapVersion;
/*     */   }
/*     */   
/*     */   public JAXBEncryptedData(EncryptedDataType edt, Data data, SOAPVersion soapVersion) {
/* 108 */     this.edt = edt;
/* 109 */     this.data = data;
/* 110 */     this.soapVersion = soapVersion;
/*     */   }
/*     */   
/*     */   public String getEncryptedLocalName() {
/* 114 */     if (this.data instanceof SSEData) {
/* 115 */       SecurityElement se = ((SSEData)this.data).getSecurityElement();
/* 116 */       return se.getLocalPart();
/*     */     } 
/* 118 */     return "";
/*     */   }
/*     */   
/*     */   public String getEncryptedId() {
/* 122 */     if (this.data instanceof SSEData) {
/* 123 */       SecurityElement se = ((SSEData)this.data).getSecurityElement();
/* 124 */       return se.getId();
/*     */     } 
/* 126 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void encrypt() {}
/*     */ 
/*     */   
/*     */   public void decrypt() {}
/*     */   
/*     */   public String getId() {
/* 136 */     return this.edt.getId();
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 140 */     if (this.edt.getId() == null || this.edt.getId().length() == 0) {
/* 141 */       this.edt.setId(id);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 146 */     return "http://www.w3.org/2001/04/xmlenc#";
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 150 */     return "EncryptedData";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/*     */     try {
/* 160 */       if (streamWriter instanceof Map && this.dep == null) {
/* 161 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 162 */         if (os != null) {
/* 163 */           streamWriter.writeCharacters("");
/* 164 */           writeTo(os);
/*     */           return;
/*     */         } 
/*     */       } 
/* 168 */       Marshaller writer = getMarshaller();
/*     */       
/* 170 */       if (this.dep == null) {
/* 171 */         this.dep = new CryptoProcessor(1, this.edt.getEncryptionMethod().getAlgorithm(), this.data, this.key);
/*     */         
/* 173 */         if (streamWriter instanceof com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl) {
/* 174 */           ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*     */           try {
/* 176 */             this.dep.encryptData(bos);
/*     */           }
/* 178 */           catch (IOException ie) {
/* 179 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1920_ERROR_CALCULATING_CIPHERVALUE(), ie);
/* 180 */             throw new XMLStreamException("Error occurred while calculating Cipher Value");
/*     */           } 
/* 182 */           this.dep.setEncryptedDataCV(bos.toByteArray());
/*     */         } 
/*     */       } 
/* 185 */       CVAdapter adapter = new CVAdapter(this.dep);
/* 186 */       writer.setAdapter(CVAdapter.class, adapter);
/*     */       
/* 188 */       ObjectFactory obj = new ObjectFactory();
/* 189 */       JAXBElement ed = obj.createEncryptedData(this.edt);
/* 190 */       writer.marshal(ed, streamWriter);
/* 191 */     } catch (XWSSecurityException ex) {
/* 192 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1919_ERROR_WRITING_ENCRYPTEDDATA(ex.getMessage()), (Throwable)ex);
/* 193 */     } catch (JAXBException ex) {
/* 194 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1919_ERROR_WRITING_ENCRYPTEDDATA(ex.getMessage()), ex);
/*     */     } 
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
/* 206 */       Marshaller marshaller = getMarshaller();
/* 207 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 208 */       while (itr.hasNext()) {
/* 209 */         Map.Entry<Object, Object> entry = itr.next();
/* 210 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 212 */       writeTo(streamWriter);
/* 213 */     } catch (JAXBException jbe) {
/* 214 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1919_ERROR_WRITING_ENCRYPTEDDATA(jbe.getMessage()), jbe);
/* 215 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {
/*     */     try {
/* 224 */       Marshaller writer = getMarshaller();
/*     */ 
/*     */       
/* 227 */       CryptoProcessor dep = new CryptoProcessor(1, this.edt.getEncryptionMethod().getAlgorithm(), this.data, this.key);
/*     */       
/* 229 */       CVAdapter adapter = new CVAdapter(dep);
/* 230 */       writer.setAdapter(CVAdapter.class, adapter);
/* 231 */       ObjectFactory obj = new ObjectFactory();
/* 232 */       JAXBElement ed = obj.createEncryptedData(this.edt);
/* 233 */       writer.marshal(ed, os);
/* 234 */     } catch (XWSSecurityException ex) {
/* 235 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1919_ERROR_WRITING_ENCRYPTEDDATA(ex.getMessage()), (Throwable)ex);
/* 236 */     } catch (JAXBException ex) {
/* 237 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1919_ERROR_WRITING_ENCRYPTEDDATA(ex.getMessage()), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 242 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 246 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public byte[] canonicalize(String algorithm, List<AttributeNS> namespaceDecls) {
/* 250 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isCanonicalized() {
/* 254 */     return false;
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 258 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 262 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 270 */     KeyInfo ki = (KeyInfo)this.edt.getKeyInfo();
/* 271 */     if (ki != null) {
/* 272 */       List<JAXBElement> list = ki.getContent();
/* 273 */       if (list.size() > 0) {
/* 274 */         Object data = ((JAXBElement)list.get(0)).getValue();
/* 275 */         if (data instanceof SecurityHeaderElement && (
/* 276 */           (SecurityHeaderElement)data).refersToSecHdrWithId(id)) {
/* 277 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 282 */     if (this.data instanceof SSEData) {
/* 283 */       SecurityElement se = ((SSEData)this.data).getSecurityElement();
/* 284 */       if (se instanceof SecurityHeaderElement) {
/* 285 */         return ((SecurityHeaderElement)se).refersToSecHdrWithId(id);
/*     */       }
/*     */     } 
/* 288 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\enc\JAXBEncryptedData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */