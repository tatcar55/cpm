/*     */ package com.sun.xml.ws.security.opt.impl.enc;
/*     */ 
/*     */ import com.sun.xml.security.core.xenc.CVAdapter;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.SSEData;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.secext11.EncryptedHeaderType;
/*     */ import com.sun.xml.ws.security.secext11.ObjectFactory;
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
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedHeader
/*     */   implements SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  83 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.crypto", "com.sun.xml.wss.logging.impl.opt.crypto.LogStrings");
/*     */ 
/*     */   
/*  86 */   private EncryptedHeaderType eht = null;
/*     */   
/*     */   private boolean isCanonicalized = false;
/*  89 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*  90 */   private Data data = null;
/*  91 */   private Key key = null;
/*  92 */   private CryptoProcessor dep = null;
/*     */ 
/*     */   
/*     */   public EncryptedHeader(EncryptedHeaderType eht, Data data, Key key, SOAPVersion soapVersion) {
/*  96 */     this.eht = eht;
/*  97 */     this.key = key;
/*  98 */     this.data = data;
/*  99 */     this.soapVersion = soapVersion;
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 103 */     KeyInfo ki = (KeyInfo)this.eht.getEncryptedData().getKeyInfo();
/* 104 */     if (ki != null) {
/* 105 */       List list = ki.getContent();
/* 106 */       if (list.size() > 0) {
/* 107 */         Object data = list.get(0);
/* 108 */         if (data instanceof SecurityHeaderElement) {
/* 109 */           return ((SecurityHeaderElement)data).refersToSecHdrWithId(id);
/*     */         }
/*     */       } 
/*     */     } 
/* 113 */     if (this.data instanceof SSEData) {
/* 114 */       SecurityElement se = ((SSEData)this.data).getSecurityElement();
/* 115 */       if (se instanceof SecurityHeaderElement) {
/* 116 */         return ((SecurityHeaderElement)se).refersToSecHdrWithId(id);
/*     */       }
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 123 */     return this.eht.getId();
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 127 */     this.eht.setId(id);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 131 */     return "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd";
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 135 */     return "EncryptedHeader";
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 139 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public byte[] canonicalize(String algorithm, List<AttributeNS> namespaceDecls) {
/* 143 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isCanonicalized() {
/* 147 */     return this.isCanonicalized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/*     */     try {
/* 156 */       if (streamWriter instanceof Map && this.dep == null) {
/* 157 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 158 */         if (os != null) {
/* 159 */           streamWriter.writeCharacters("");
/* 160 */           writeTo(os);
/*     */           return;
/*     */         } 
/*     */       } 
/* 164 */       Marshaller writer = getMarshaller();
/* 165 */       if (this.dep == null) {
/* 166 */         this.dep = new CryptoProcessor(1, this.eht.getEncryptedData().getEncryptionMethod().getAlgorithm(), this.data, this.key);
/*     */         
/* 168 */         if (streamWriter instanceof com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl) {
/* 169 */           ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*     */           try {
/* 171 */             this.dep.encryptData(bos);
/*     */           }
/* 173 */           catch (IOException ie) {
/* 174 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1920_ERROR_CALCULATING_CIPHERVALUE(), ie);
/* 175 */             throw new XMLStreamException("Error occurred while calculating Cipher Value");
/*     */           } 
/* 177 */           this.dep.setEncryptedDataCV(bos.toByteArray());
/*     */         } 
/*     */       } 
/* 180 */       CVAdapter adapter = new CVAdapter(this.dep);
/* 181 */       writer.setAdapter(CVAdapter.class, adapter);
/*     */       
/* 183 */       ObjectFactory obj = new ObjectFactory();
/* 184 */       JAXBElement eh = obj.createEncryptedHeader(this.eht);
/* 185 */       writer.marshal(eh, streamWriter);
/* 186 */     } catch (JAXBException ex) {
/* 187 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1916_ERROR_WRITING_ECRYPTEDHEADER(ex.getMessage()), ex);
/* 188 */     } catch (XWSSecurityException ex) {
/* 189 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1916_ERROR_WRITING_ECRYPTEDHEADER(ex.getMessage()), (Throwable)ex);
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
/* 201 */       Marshaller marshaller = getMarshaller();
/* 202 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 203 */       while (itr.hasNext()) {
/* 204 */         Map.Entry<Object, Object> entry = itr.next();
/* 205 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 207 */       writeTo(streamWriter);
/* 208 */     } catch (JAXBException jbe) {
/* 209 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1916_ERROR_WRITING_ECRYPTEDHEADER(jbe.getMessage()), jbe);
/* 210 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {
/*     */     try {
/* 219 */       Marshaller writer = getMarshaller();
/*     */ 
/*     */       
/* 222 */       CryptoProcessor dep = new CryptoProcessor(1, this.eht.getEncryptedData().getEncryptionMethod().getAlgorithm(), this.data, this.key);
/* 223 */       CVAdapter adapter = new CVAdapter(dep);
/* 224 */       writer.setAdapter(CVAdapter.class, adapter);
/* 225 */       ObjectFactory obj = new ObjectFactory();
/* 226 */       JAXBElement eh = obj.createEncryptedHeader(this.eht);
/* 227 */       writer.marshal(eh, os);
/* 228 */     } catch (XWSSecurityException ex) {
/* 229 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1916_ERROR_WRITING_ECRYPTEDHEADER(ex.getMessage()), (Throwable)ex);
/* 230 */     } catch (JAXBException ex) {
/* 231 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1916_ERROR_WRITING_ECRYPTEDHEADER(ex.getMessage()), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 236 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\enc\EncryptedHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */