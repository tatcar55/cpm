/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.DerivedKeyToken;
/*     */ import com.sun.xml.ws.security.opt.api.reference.DirectReference;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.secconv.impl.bindings.DerivedKeyTokenType;
/*     */ import com.sun.xml.ws.security.secconv.impl.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secconv.impl.wssx.bindings.DerivedKeyTokenType;
/*     */ import com.sun.xml.ws.security.secconv.impl.wssx.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.KeyIdentifierType;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
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
/*     */ public class DerivedKey
/*     */   implements DerivedKeyToken, SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  71 */   private DerivedKeyTokenType derivedKey = null;
/*  72 */   private DerivedKeyTokenType derivedKey13 = null;
/*  73 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*  74 */   private String refId = "";
/*  75 */   private String spVersion = "";
/*     */   
/*     */   public DerivedKey(DerivedKeyTokenType dkt, SOAPVersion soapVersion, String spVersion) {
/*  78 */     this.derivedKey = dkt;
/*  79 */     this.soapVersion = soapVersion;
/*  80 */     this.spVersion = spVersion;
/*     */   }
/*     */   
/*     */   public DerivedKey(DerivedKeyTokenType dkt, SOAPVersion soapVersion, String refId, String spVersion) {
/*  84 */     this.derivedKey = dkt;
/*  85 */     this.soapVersion = soapVersion;
/*  86 */     this.refId = refId;
/*  87 */     this.spVersion = spVersion;
/*     */   }
/*     */   
/*     */   public DerivedKey(DerivedKeyTokenType dkt, SOAPVersion soapVersion, String spVersion) {
/*  91 */     this.derivedKey13 = dkt;
/*  92 */     this.soapVersion = soapVersion;
/*  93 */     this.spVersion = spVersion;
/*     */   }
/*     */   
/*     */   public DerivedKey(DerivedKeyTokenType dkt, SOAPVersion soapVersion, String refId, String spVersion) {
/*  97 */     this.derivedKey13 = dkt;
/*  98 */     this.soapVersion = soapVersion;
/*  99 */     this.refId = refId;
/* 100 */     this.spVersion = spVersion;
/*     */   }
/*     */   
/*     */   public String getAlgorithm() {
/* 104 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 105 */       return this.derivedKey13.getAlgorithm();
/*     */     }
/* 107 */     return this.derivedKey.getAlgorithm();
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getGeneration() {
/* 112 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 113 */       return this.derivedKey13.getGeneration();
/*     */     }
/* 115 */     return this.derivedKey.getGeneration();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 120 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 121 */       return this.derivedKey13.getId();
/*     */     }
/* 123 */     return this.derivedKey.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLabel() {
/* 128 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 129 */       return this.derivedKey13.getLabel();
/*     */     }
/* 131 */     return this.derivedKey.getLabel();
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getLength() {
/* 136 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 137 */       return this.derivedKey13.getLength();
/*     */     }
/* 139 */     return this.derivedKey.getLength();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getNonce() {
/* 144 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 145 */       return this.derivedKey13.getNonce();
/*     */     }
/* 147 */     return this.derivedKey.getNonce();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getOffset() {
/* 153 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 154 */       return this.derivedKey13.getOffset();
/*     */     }
/* 156 */     return this.derivedKey.getOffset();
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityTokenReferenceType getSecurityTokenReference() {
/* 161 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 162 */       return this.derivedKey13.getSecurityTokenReference();
/*     */     }
/* 164 */     return this.derivedKey.getSecurityTokenReference();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAlgorithm(String value) {
/* 169 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 170 */       this.derivedKey13.setAlgorithm(value);
/*     */     } else {
/* 172 */       this.derivedKey.setAlgorithm(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setGeneration(BigInteger value) {
/* 177 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 178 */       this.derivedKey13.setGeneration(value);
/*     */     } else {
/* 180 */       this.derivedKey.setGeneration(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setId(String value) {
/* 185 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 186 */       this.derivedKey13.setId(value);
/*     */     } else {
/* 188 */       this.derivedKey.setId(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setLabel(String value) {
/* 193 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 194 */       this.derivedKey13.setLabel(value);
/*     */     } else {
/* 196 */       this.derivedKey.setLabel(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setLength(BigInteger value) {
/* 201 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 202 */       this.derivedKey13.setLength(value);
/*     */     } else {
/* 204 */       this.derivedKey.setLength(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setNonce(byte[] value) {
/* 209 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 210 */       this.derivedKey13.setNonce(value);
/*     */     } else {
/* 212 */       this.derivedKey.setNonce(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setOffset(BigInteger value) {
/* 217 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 218 */       this.derivedKey13.setOffset(value);
/*     */     } else {
/* 220 */       this.derivedKey.setOffset(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSecurityTokenReference(SecurityTokenReferenceType value) {
/* 225 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 226 */       this.derivedKey13.setSecurityTokenReference(value);
/*     */     } else {
/* 228 */       this.derivedKey.setSecurityTokenReference(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 233 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 234 */       return "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512";
/*     */     }
/* 236 */     return "http://schemas.xmlsoap.org/ws/2005/02/sc";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalPart() {
/* 241 */     return "DerivedKeyToken";
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 245 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {
/*     */     try {
/* 253 */       JAXBElement<DerivedKeyTokenType> dkt = null;
/* 254 */       JAXBElement<DerivedKeyTokenType> dkt13 = null;
/* 255 */       Marshaller writer = getMarshaller();
/* 256 */       if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 257 */         dkt13 = (new ObjectFactory()).createDerivedKeyToken(this.derivedKey13);
/* 258 */         writer.marshal(dkt13, os);
/*     */       } else {
/* 260 */         dkt = (new ObjectFactory()).createDerivedKeyToken(this.derivedKey);
/* 261 */         writer.marshal(dkt, os);
/*     */       } 
/* 263 */     } catch (JAXBException ex) {
/* 264 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 273 */     JAXBElement<DerivedKeyTokenType> dkt = null;
/* 274 */     JAXBElement<DerivedKeyTokenType> dkt13 = null;
/* 275 */     if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 276 */       dkt13 = (new ObjectFactory()).createDerivedKeyToken(this.derivedKey13);
/*     */     } else {
/* 278 */       dkt = (new ObjectFactory()).createDerivedKeyToken(this.derivedKey);
/*     */     } 
/*     */     
/*     */     try {
/* 282 */       Marshaller writer = getMarshaller();
/* 283 */       if (streamWriter instanceof Map) {
/* 284 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 285 */         if (os != null) {
/* 286 */           streamWriter.writeCharacters("");
/* 287 */           if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 288 */             writer.marshal(dkt13, os);
/*     */           } else {
/* 290 */             writer.marshal(dkt, os);
/*     */           } 
/*     */           return;
/*     */         } 
/*     */       } 
/* 295 */       if (this.spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 296 */         writer.marshal(dkt13, streamWriter);
/*     */       } else {
/* 298 */         writer.marshal(dkt, streamWriter);
/*     */       } 
/* 300 */     } catch (JAXBException e) {
/*     */       
/* 302 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 308 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 316 */     if (this.refId != null && this.refId.length() > 0 && 
/* 317 */       this.refId.equals(id)) {
/* 318 */       return true;
/*     */     }
/*     */     
/* 321 */     if (getSecurityTokenReference() != null) {
/* 322 */       SecurityTokenReferenceType ref = getSecurityTokenReference();
/* 323 */       List<JAXBElement> list = ref.getAny();
/* 324 */       if (list.size() > 0) {
/* 325 */         JAXBElement je = list.get(0);
/* 326 */         Object obj = je.getValue();
/* 327 */         if (obj instanceof DirectReference) {
/* 328 */           StringBuffer sb = new StringBuffer();
/* 329 */           sb.append("#");
/* 330 */           sb.append(id);
/* 331 */           return ((DirectReference)obj).getURI().equals(sb.toString());
/* 332 */         }  if (obj instanceof KeyIdentifierType) {
/* 333 */           KeyIdentifierType ki = (KeyIdentifierType)obj;
/* 334 */           String valueType = ki.getValueType();
/* 335 */           if (valueType.equals("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID") || valueType.equals("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID"))
/*     */           {
/* 337 */             if (id.equals(ki.getValue())) {
/* 338 */               return true;
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 344 */     return false;
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
/* 355 */       Marshaller marshaller = getMarshaller();
/* 356 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 357 */       while (itr.hasNext()) {
/* 358 */         Map.Entry<Object, Object> entry = itr.next();
/* 359 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 361 */       writeTo(streamWriter);
/* 362 */     } catch (JAXBException jbe) {
/*     */       
/* 364 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\DerivedKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */