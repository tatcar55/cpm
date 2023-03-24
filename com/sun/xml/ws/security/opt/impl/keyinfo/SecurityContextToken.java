/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.secconv.impl.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secconv.impl.bindings.SecurityContextTokenType;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityContextToken
/*     */   extends SecurityContextTokenType
/*     */   implements SecurityHeaderElement, SecurityElementWriter, SecurityContextToken
/*     */ {
/*  77 */   public final String SECURITY_CONTEXT_TOKEN = "SecurityContextToken";
/*     */   
/*  79 */   private String instance = null;
/*  80 */   private URI identifier = null;
/*  81 */   private List extElements = null;
/*  82 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */   
/*     */   public SecurityContextToken(URI identifier, String instance, String wsuId, SOAPVersion sv) {
/*  85 */     if (identifier != null) {
/*  86 */       setIdentifier(identifier);
/*     */     }
/*  88 */     if (instance != null) {
/*  89 */       setInstance(instance);
/*     */     }
/*     */     
/*  92 */     if (wsuId != null) {
/*  93 */       setWsuId(wsuId);
/*     */     }
/*  95 */     this.soapVersion = sv;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityContextToken(SecurityContextTokenType sTokenType, SOAPVersion sv) {
/* 101 */     List<Object> list = sTokenType.getAny();
/* 102 */     for (int i = 0; i < list.size(); i++) {
/* 103 */       Object object = list.get(i);
/* 104 */       if (object instanceof JAXBElement) {
/* 105 */         JAXBElement<String> obj = (JAXBElement)object;
/*     */         
/* 107 */         String local = obj.getName().getLocalPart();
/* 108 */         if (local.equalsIgnoreCase("Instance")) {
/* 109 */           setInstance(obj.getValue());
/* 110 */         } else if (local.equalsIgnoreCase("Identifier")) {
/*     */           try {
/* 112 */             setIdentifier(new URI(obj.getValue()));
/* 113 */           } catch (URISyntaxException ex) {
/* 114 */             throw new RuntimeException(ex);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 118 */         getAny().add(object);
/* 119 */         if (this.extElements == null) {
/* 120 */           this.extElements = new ArrayList();
/* 121 */           this.extElements.add(object);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     setWsuId(sTokenType.getId());
/* 127 */     this.soapVersion = sv;
/*     */   }
/*     */   
/*     */   public URI getIdentifier() {
/* 131 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public void setIdentifier(URI identifier) {
/* 135 */     this.identifier = identifier;
/* 136 */     JAXBElement<String> iElement = (new ObjectFactory()).createIdentifier(identifier.toString());
/*     */     
/* 138 */     getAny().add(iElement);
/*     */   }
/*     */   
/*     */   public String getInstance() {
/* 142 */     return this.instance;
/*     */   }
/*     */   
/*     */   public void setInstance(String instance) {
/* 146 */     this.instance = instance;
/* 147 */     JAXBElement<String> iElement = (new ObjectFactory()).createInstance(instance);
/*     */     
/* 149 */     getAny().add(iElement);
/*     */   }
/*     */   
/*     */   public void setWsuId(String wsuId) {
/* 153 */     setId(wsuId);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWsuId() {
/* 158 */     return getId();
/*     */   }
/*     */   
/*     */   public String getType() {
/* 162 */     return "SecurityContextToken";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/*     */     try {
/* 167 */       DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 168 */       dbf.setNamespaceAware(true);
/* 169 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 170 */       Document doc = db.newDocument();
/*     */       
/* 172 */       Marshaller marshaller = WSTrustElementFactory.getContext().createMarshaller();
/* 173 */       JAXBElement<SecurityContextTokenType> tElement = (new ObjectFactory()).createSecurityContextToken(this);
/* 174 */       marshaller.marshal(tElement, doc);
/* 175 */       return doc.getDocumentElement();
/*     */     }
/* 177 */     catch (Exception ex) {
/* 178 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getExtElements() {
/* 183 */     return this.extElements;
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 187 */     return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 191 */     return "SecurityContextToken";
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 195 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getAttribute(QName name) {
/* 199 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 203 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/*     */     try {
/* 208 */       JAXBElement<SecurityContextTokenType> sct = (new ObjectFactory()).createSecurityContextToken(this);
/*     */       
/* 210 */       Marshaller writer = getMarshaller();
/* 211 */       writer.marshal(sct, os);
/* 212 */     } catch (JAXBException ex) {
/* 213 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 222 */     JAXBElement<SecurityContextTokenType> sct = (new ObjectFactory()).createSecurityContextToken(this);
/*     */ 
/*     */     
/*     */     try {
/* 226 */       Marshaller writer = getMarshaller();
/* 227 */       if (streamWriter instanceof Map) {
/* 228 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 229 */         if (os != null) {
/* 230 */           streamWriter.writeCharacters("");
/*     */           
/* 232 */           writer.marshal(sct, os);
/*     */           return;
/*     */         } 
/*     */       } 
/* 236 */       writer.marshal(sct, streamWriter);
/* 237 */     } catch (JAXBException e) {
/* 238 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] canonicalize(String algorithm, List<AttributeNS> namespaceDecls) {
/* 243 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isCanonicalized() {
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 252 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 256 */     return false;
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
/* 267 */       Marshaller marshaller = getMarshaller();
/* 268 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 269 */       while (itr.hasNext()) {
/* 270 */         Map.Entry<Object, Object> entry = itr.next();
/* 271 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 273 */       writeTo(streamWriter);
/* 274 */     } catch (JAXBException jbe) {
/* 275 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\SecurityContextToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */