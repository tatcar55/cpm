/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.secconv.impl.wssx.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secconv.impl.wssx.bindings.SecurityContextTokenType;
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
/*     */ 
/*     */ 
/*     */ public class SecurityContextToken13
/*     */   extends SecurityContextTokenType
/*     */   implements SecurityHeaderElement, SecurityElementWriter, SecurityContextToken
/*     */ {
/*  79 */   public final String SECURITY_CONTEXT_TOKEN = "SecurityContextToken";
/*     */   
/*  81 */   private String instance = null;
/*  82 */   private URI identifier = null;
/*  83 */   private List extElements = null;
/*  84 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */   
/*     */   public SecurityContextToken13(URI identifier, String instance, String wsuId, SOAPVersion sv) {
/*  87 */     if (identifier != null) {
/*  88 */       setIdentifier(identifier);
/*     */     }
/*  90 */     if (instance != null) {
/*  91 */       setInstance(instance);
/*     */     }
/*     */     
/*  94 */     if (wsuId != null) {
/*  95 */       setWsuId(wsuId);
/*     */     }
/*  97 */     this.soapVersion = sv;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityContextToken13(SecurityContextTokenType sTokenType, SOAPVersion sv) {
/* 103 */     List<Object> list = sTokenType.getAny();
/* 104 */     for (int i = 0; i < list.size(); i++) {
/* 105 */       Object object = list.get(i);
/* 106 */       if (object instanceof JAXBElement) {
/* 107 */         JAXBElement<String> obj = (JAXBElement)object;
/*     */         
/* 109 */         String local = obj.getName().getLocalPart();
/* 110 */         if (local.equalsIgnoreCase("Instance")) {
/* 111 */           setInstance(obj.getValue());
/* 112 */         } else if (local.equalsIgnoreCase("Identifier")) {
/*     */           try {
/* 114 */             setIdentifier(new URI(obj.getValue()));
/* 115 */           } catch (URISyntaxException ex) {
/* 116 */             throw new RuntimeException(ex);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 120 */         getAny().add(object);
/* 121 */         if (this.extElements == null) {
/* 122 */           this.extElements = new ArrayList();
/* 123 */           this.extElements.add(object);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     setWsuId(sTokenType.getId());
/* 129 */     this.soapVersion = sv;
/*     */   }
/*     */   
/*     */   public URI getIdentifier() {
/* 133 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public void setIdentifier(URI identifier) {
/* 137 */     this.identifier = identifier;
/* 138 */     JAXBElement<String> iElement = (new ObjectFactory()).createIdentifier(identifier.toString());
/*     */     
/* 140 */     getAny().add(iElement);
/*     */   }
/*     */   
/*     */   public String getInstance() {
/* 144 */     return this.instance;
/*     */   }
/*     */   
/*     */   public void setInstance(String instance) {
/* 148 */     this.instance = instance;
/* 149 */     JAXBElement<String> iElement = (new ObjectFactory()).createInstance(instance);
/*     */     
/* 151 */     getAny().add(iElement);
/*     */   }
/*     */   
/*     */   public void setWsuId(String wsuId) {
/* 155 */     setId(wsuId);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWsuId() {
/* 160 */     return getId();
/*     */   }
/*     */   
/*     */   public String getType() {
/* 164 */     return "SecurityContextToken";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/*     */     try {
/* 169 */       DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 170 */       dbf.setNamespaceAware(true);
/* 171 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 172 */       Document doc = db.newDocument();
/*     */       
/* 174 */       Marshaller marshaller = WSTrustElementFactory.getContext().createMarshaller();
/* 175 */       JAXBElement<SecurityContextTokenType> tElement = (new ObjectFactory()).createSecurityContextToken(this);
/* 176 */       marshaller.marshal(tElement, doc);
/* 177 */       return doc.getDocumentElement();
/*     */     }
/* 179 */     catch (Exception ex) {
/* 180 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getExtElements() {
/* 185 */     return this.extElements;
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 189 */     return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 193 */     return "SecurityContextToken";
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 197 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getAttribute(QName name) {
/* 201 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 205 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {
/*     */     try {
/* 213 */       JAXBElement<SecurityContextTokenType> sct = (new ObjectFactory()).createSecurityContextToken(this);
/*     */       
/* 215 */       Marshaller writer = getMarshaller();
/* 216 */       writer.marshal(sct, os);
/* 217 */     } catch (JAXBException ex) {
/* 218 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 227 */     JAXBElement<SecurityContextTokenType> sct = (new ObjectFactory()).createSecurityContextToken(this);
/*     */ 
/*     */     
/*     */     try {
/* 231 */       Marshaller writer = getMarshaller();
/* 232 */       if (streamWriter instanceof Map) {
/* 233 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 234 */         if (os != null) {
/* 235 */           streamWriter.writeCharacters("");
/*     */           
/* 237 */           writer.marshal(sct, os);
/*     */           return;
/*     */         } 
/*     */       } 
/* 241 */       writer.marshal(sct, streamWriter);
/* 242 */     } catch (JAXBException e) {
/* 243 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] canonicalize(String algorithm, List<AttributeNS> namespaceDecls) {
/* 248 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isCanonicalized() {
/* 252 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 257 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 261 */     return false;
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
/* 272 */       Marshaller marshaller = getMarshaller();
/* 273 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 274 */       while (itr.hasNext()) {
/* 275 */         Map.Entry<Object, Object> entry = itr.next();
/* 276 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 278 */       writeTo(streamWriter);
/* 279 */     } catch (JAXBException jbe) {
/* 280 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\SecurityContextToken13.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */