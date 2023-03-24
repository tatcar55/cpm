/*     */ package com.sun.xml.ws.security.opt.impl.enc;
/*     */ 
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.api.BridgeContext;
/*     */ import com.sun.xml.security.core.xenc.CVAdapter;
/*     */ import com.sun.xml.security.core.xenc.EncryptedKeyType;
/*     */ import com.sun.xml.security.core.xenc.ObjectFactory;
/*     */ import com.sun.xml.security.core.xenc.ReferenceList;
/*     */ import com.sun.xml.security.core.xenc.ReferenceType;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.EncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import com.sun.xml.wss.logging.impl.opt.crypto.LogStringsMessages;
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
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class JAXBEncryptedKey
/*     */   implements EncryptedKey, SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  81 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.crypto", "com.sun.xml.wss.logging.impl.opt.crypto.LogStrings");
/*     */ 
/*     */   
/*  84 */   private EncryptedKeyType ekt = null;
/*     */   
/*  86 */   private Key dataEnckey = null;
/*  87 */   private Key dkEK = null;
/*  88 */   CryptoProcessor dep = null;
/*  89 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */ 
/*     */   
/*     */   public JAXBEncryptedKey(EncryptedKeyType ekt, Key kk, Key dk, SOAPVersion soapVersion) throws XWSSecurityException {
/*  93 */     this.ekt = ekt;
/*  94 */     this.dkEK = kk;
/*  95 */     this.dataEnckey = dk;
/*  96 */     this.soapVersion = soapVersion;
/*  97 */     this.dep = new CryptoProcessor(3, ekt.getEncryptionMethod().getAlgorithm(), this.dataEnckey, this.dkEK);
/*     */   }
/*     */ 
/*     */   
/*     */   public void encrypt() {}
/*     */ 
/*     */   
/*     */   public void decrypt() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/* 108 */     return this.ekt.getId();
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 112 */     this.ekt.setId(id);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 116 */     return "http://www.w3.org/2001/04/xmlenc#";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalPart() {
/* 121 */     return "EncryptedKey";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 126 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAttribute(QName name) {
/* 131 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 135 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 139 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge, BridgeContext context) throws JAXBException {
/* 143 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 147 */     throw new UnsupportedOperationException();
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
/* 158 */       if (streamWriter instanceof Map) {
/* 159 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 160 */         if (os != null) {
/* 161 */           streamWriter.writeCharacters("");
/* 162 */           writeTo(os);
/*     */           return;
/*     */         } 
/*     */       } 
/* 166 */       Marshaller writer = getMarshaller();
/* 167 */       JAXBElement ed = getEK(writer);
/* 168 */       writer.marshal(ed, streamWriter);
/* 169 */     } catch (JAXBException ex) {
/* 170 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1921_ERROR_WRITING_ENCRYPTEDKEY(ex.getMessage()), ex);
/* 171 */     } catch (XWSSecurityException ex) {
/* 172 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1921_ERROR_WRITING_ENCRYPTEDKEY(ex.getMessage()), (Throwable)ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {
/*     */     try {
/* 183 */       Marshaller writer = getMarshaller();
/*     */       
/* 185 */       JAXBElement ed = getEK(writer);
/* 186 */       writer.marshal(ed, os);
/* 187 */     } catch (JAXBException ex) {
/* 188 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1921_ERROR_WRITING_ENCRYPTEDKEY(ex.getMessage()), ex);
/* 189 */     } catch (XWSSecurityException ex) {
/* 190 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1921_ERROR_WRITING_ENCRYPTEDKEY(ex.getMessage()), (Throwable)ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private JAXBElement getEK(Marshaller writer) throws JAXBException, XWSSecurityException {
/* 196 */     CVAdapter adapter = new CVAdapter(this.dep);
/* 197 */     writer.setAdapter(CVAdapter.class, adapter);
/* 198 */     ObjectFactory obj = new ObjectFactory();
/* 199 */     return obj.createEncryptedKey(this.ekt);
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 203 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 207 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public byte[] canonicalize(String algorithm, List<AttributeNS> namespaceDecls) {
/* 211 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isCanonicalized() {
/* 215 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 219 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */   
/*     */   public ReferenceList getReferenceList() {
/* 223 */     return this.ekt.getReferenceList();
/*     */   }
/*     */   
/*     */   public boolean hasReferenceList() {
/* 227 */     return (this.ekt.getReferenceList() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 236 */     KeyInfo ki = (KeyInfo)this.ekt.getKeyInfo();
/* 237 */     if (ki != null) {
/* 238 */       List<JAXBElement> list1 = ki.getContent();
/* 239 */       if (list1.size() > 0) {
/* 240 */         Object data = ((JAXBElement)list1.get(0)).getValue();
/* 241 */         if (data instanceof SecurityHeaderElement && (
/* 242 */           (SecurityHeaderElement)data).refersToSecHdrWithId(id)) {
/* 243 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     List<JAXBElement<ReferenceType>> list = null;
/* 249 */     if (getReferenceList() != null) {
/* 250 */       list = getReferenceList().getDataReferenceOrKeyReference();
/*     */     }
/* 252 */     if (list == null) {
/* 253 */       return false;
/*     */     }
/* 255 */     StringBuffer sb = new StringBuffer();
/* 256 */     sb.append("#");
/* 257 */     sb.append(id);
/* 258 */     String idref = sb.toString();
/* 259 */     for (int i = 0; i < list.size(); i++) {
/* 260 */       JAXBElement<ReferenceType> rt = list.get(i);
/* 261 */       ReferenceType ref = rt.getValue();
/* 262 */       if (ref.getURI().equals(idref)) {
/* 263 */         return true;
/*     */       }
/*     */     } 
/* 266 */     return false;
/*     */   }
/*     */   
/*     */   public void setReferenceList(ReferenceList list) {
/* 270 */     this.ekt.setReferenceList(list);
/*     */   }
/*     */   
/*     */   public Key getKey() {
/* 274 */     return this.dataEnckey;
/*     */   }
/*     */   
/*     */   public byte[] getCipherValue() {
/* 278 */     return this.dep.getCipherValueOfEK();
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
/* 290 */       Marshaller marshaller = getMarshaller();
/* 291 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 292 */       while (itr.hasNext()) {
/* 293 */         Map.Entry<Object, Object> entry = itr.next();
/* 294 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 296 */       writeTo(streamWriter);
/* 297 */     } catch (JAXBException jbe) {
/* 298 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1921_ERROR_WRITING_ENCRYPTEDKEY(jbe.getMessage()), jbe);
/* 299 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\enc\JAXBEncryptedKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */