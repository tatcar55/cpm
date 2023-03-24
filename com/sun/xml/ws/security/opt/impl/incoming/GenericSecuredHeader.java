/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.stream.buffer.AbstractCreatorProcessor;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferException;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferMark;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferSource;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.message.AbstractHeaderImpl;
/*     */ import com.sun.xml.ws.message.Util;
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.SecuredHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.XMLStreamReaderFactory;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.StreamFilter;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class GenericSecuredHeader
/*     */   extends AbstractHeaderImpl
/*     */   implements SecuredHeader, NamespaceContextInfo
/*     */ {
/*     */   private static final String SOAP_1_1_MUST_UNDERSTAND = "mustUnderstand";
/*     */   private static final String SOAP_1_2_MUST_UNDERSTAND = "mustUnderstand";
/*     */   private static final String SOAP_1_1_ROLE = "actor";
/*     */   private static final String SOAP_1_2_ROLE = "role";
/*     */   private static final String SOAP_1_2_RELAY = "relay";
/*     */   private XMLStreamBuffer completeHeader;
/*     */   private boolean isMustUnderstand;
/*  96 */   private SOAPVersion soapVersion = null;
/*     */   
/*  98 */   private Vector idValues = new Vector(2);
/*  99 */   private HashMap<String, String> shNSDecls = new HashMap<String, String>();
/* 100 */   private HashMap<String, String> nsDecls = null;
/*     */   
/*     */   private String role;
/*     */   
/*     */   private boolean isRelay;
/*     */   private String localName;
/* 106 */   private String namespaceURI = "";
/* 107 */   private String id = "";
/*     */   
/*     */   private final FinalArrayList<Attribute> attributes;
/*     */   
/*     */   private boolean hasED = false;
/*     */   
/*     */   public GenericSecuredHeader(XMLStreamReader reader, SOAPVersion soapVersion, StreamReaderBufferCreator creator, HashMap<String, String> nsDecl, XMLInputFactory staxIF, boolean encHeaderContent) throws XMLStreamBufferException, XMLStreamException {
/* 114 */     this.shNSDecls = nsDecl;
/* 115 */     this.soapVersion = soapVersion;
/* 116 */     if (reader.getNamespaceURI() != null) {
/* 117 */       this.namespaceURI = reader.getNamespaceURI();
/*     */     }
/* 119 */     this.localName = reader.getLocalName();
/* 120 */     this.attributes = processHeaderAttributes(reader);
/* 121 */     this.completeHeader = (XMLStreamBuffer)new XMLStreamBufferMark(this.nsDecls, (AbstractCreatorProcessor)creator);
/* 122 */     creator.createElementFragment(XMLStreamReaderFactory.createFilteredXMLStreamReader(reader, new IDProcessor()), true);
/* 123 */     this.nsDecls.putAll(this.shNSDecls);
/* 124 */     if (this.id.length() > 0) {
/* 125 */       this.idValues.add(this.id);
/*     */     }
/*     */     
/* 128 */     if (encHeaderContent) {
/* 129 */       checkEncryptedData();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasEncData() {
/* 134 */     return this.hasED;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkEncryptedData() throws XMLStreamException {
/* 139 */     XMLStreamReader reader = readHeader();
/* 140 */     while (StreamUtil.moveToNextElement(reader)) {
/* 141 */       if ("EncryptedData".equals(reader.getLocalName()) && "http://www.w3.org/2001/04/xmlenc#".equals(reader.getNamespaceURI())) {
/*     */         
/* 143 */         this.hasED = true;
/* 144 */         String encId = reader.getAttributeValue(null, "Id");
/* 145 */         if (encId != null && encId.length() > 0) {
/* 146 */           this.idValues.add(encId);
/*     */         }
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private FinalArrayList<Attribute> processHeaderAttributes(XMLStreamReader reader) {
/* 154 */     if (this.soapVersion == SOAPVersion.SOAP_11) {
/* 155 */       return process11Header(reader);
/*     */     }
/* 157 */     return process12Header(reader);
/*     */   }
/*     */ 
/*     */   
/*     */   private FinalArrayList<Attribute> process12Header(XMLStreamReader reader) {
/* 162 */     FinalArrayList<Attribute> atts = null;
/* 163 */     this.role = this.soapVersion.implicitRole;
/* 164 */     this.nsDecls = new HashMap<String, String>();
/*     */     
/* 166 */     this.nsDecls = new HashMap<String, String>();
/* 167 */     if (reader.getNamespaceCount() > 0) {
/* 168 */       for (int j = 0; j < reader.getNamespaceCount(); j++) {
/* 169 */         this.nsDecls.put(reader.getNamespacePrefix(j), reader.getNamespaceURI(j));
/*     */       }
/*     */     }
/*     */     
/* 173 */     for (int i = 0; i < reader.getAttributeCount(); i++) {
/* 174 */       String lName = reader.getAttributeLocalName(i);
/* 175 */       String nsURI = reader.getAttributeNamespace(i);
/* 176 */       String value = reader.getAttributeValue(i);
/* 177 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(nsURI) && "Id".intern().equals(lName)) {
/*     */         
/* 179 */         this.id = value;
/*     */       }
/* 181 */       else if (nsURI == null && "Id".intern().equals(lName)) {
/*     */         
/* 183 */         this.id = value;
/*     */       } 
/*     */       
/* 186 */       if ("http://schemas.xmlsoap.org/soap/envelope/".equals(nsURI)) {
/* 187 */         if ("mustUnderstand".equals(lName)) {
/* 188 */           this.isMustUnderstand = Util.parseBool(value);
/* 189 */         } else if ("actor".equals(lName) && 
/* 190 */           value != null && value.length() > 0) {
/* 191 */           this.role = value;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 196 */       if (atts == null) {
/* 197 */         atts = new FinalArrayList();
/*     */       }
/* 199 */       atts.add(new Attribute(nsURI, lName, value));
/*     */     } 
/* 201 */     return atts;
/*     */   }
/*     */   
/*     */   private final FinalArrayList<Attribute> process11Header(XMLStreamReader reader) {
/* 205 */     FinalArrayList<Attribute> atts = null;
/*     */     
/* 207 */     this.role = this.soapVersion.implicitRole;
/*     */     
/* 209 */     this.nsDecls = new HashMap<String, String>();
/* 210 */     if (reader.getNamespaceCount() > 0)
/*     */     {
/* 212 */       for (int j = 0; j < reader.getNamespaceCount(); j++) {
/* 213 */         this.nsDecls.put(reader.getNamespacePrefix(j), reader.getNamespaceURI(j));
/*     */       }
/*     */     }
/*     */     
/* 217 */     for (int i = 0; i < reader.getAttributeCount(); i++) {
/* 218 */       String lName = reader.getAttributeLocalName(i);
/* 219 */       String nsURI = reader.getAttributeNamespace(i);
/* 220 */       String value = reader.getAttributeValue(i);
/*     */       
/* 222 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(nsURI) && "Id".intern().equals(lName)) {
/*     */         
/* 224 */         this.id = value;
/* 225 */       } else if (nsURI == null && "Id".intern().equals(lName)) {
/*     */         
/* 227 */         this.id = value;
/*     */       } 
/*     */       
/* 230 */       if ("http://www.w3.org/2003/05/soap-envelope".equals(nsURI)) {
/* 231 */         if ("mustUnderstand".equals(lName)) {
/* 232 */           this.isMustUnderstand = Util.parseBool(value);
/* 233 */         } else if ("role".equals(lName)) {
/* 234 */           if (value != null && value.length() > 0) {
/* 235 */             this.role = value;
/*     */           }
/* 237 */         } else if ("relay".equals(lName)) {
/* 238 */           this.isRelay = Util.parseBool(value);
/*     */         } 
/*     */       }
/*     */       
/* 242 */       if (atts == null) {
/* 243 */         atts = new FinalArrayList();
/*     */       }
/* 245 */       atts.add(new Attribute(nsURI, lName, value));
/*     */     } 
/*     */     
/* 248 */     return atts;
/*     */   }
/*     */   
/*     */   public boolean hasID(String id) {
/* 252 */     return this.idValues.contains(id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isIgnorable(SOAPVersion soapVersion, Set<String> roles) {
/* 258 */     if (!this.isMustUnderstand) {
/* 259 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 263 */     return !roles.contains(this.role);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRole(SOAPVersion soapVersion) {
/* 268 */     assert this.role != null;
/* 269 */     return this.role;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRelay() {
/* 274 */     return this.isRelay;
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 278 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 282 */     return this.localName;
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 286 */     if (this.attributes != null) {
/* 287 */       for (int i = this.attributes.size() - 1; i >= 0; i--) {
/* 288 */         Attribute a = (Attribute)this.attributes.get(i);
/* 289 */         if (a.localName.equals(localName) && a.nsUri.equals(nsUri)) {
/* 290 */           return a.value;
/*     */         }
/*     */       } 
/*     */     }
/* 294 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 301 */     return (XMLStreamReader)this.completeHeader.readAsXMLStreamReader();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/*     */     try {
/* 307 */       this.completeHeader.writeToXMLStreamWriter(w);
/* 308 */     } catch (Exception e) {
/* 309 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/*     */     try {
/* 318 */       TransformerFactory tf = WSITXMLFactory.createTransformerFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 319 */       Transformer t = tf.newTransformer();
/* 320 */       XMLStreamBufferSource source = new XMLStreamBufferSource(this.completeHeader);
/* 321 */       DOMResult result = new DOMResult();
/* 322 */       t.transform((Source)source, result);
/* 323 */       Node d = result.getNode();
/* 324 */       if (d.getNodeType() == 9) {
/* 325 */         d = d.getFirstChild();
/*     */       }
/* 327 */       SOAPHeader header = saaj.getSOAPHeader();
/* 328 */       Node node = header.getOwnerDocument().importNode(d, true);
/* 329 */       header.appendChild(node);
/* 330 */     } catch (Exception e) {
/* 331 */       throw new SOAPException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 336 */     this.completeHeader.writeTo(contentHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStringContent() {
/*     */     try {
/* 342 */       XMLStreamReader xsr = readHeader();
/* 343 */       xsr.nextTag();
/* 344 */       return xsr.getElementText();
/* 345 */     } catch (XMLStreamException e) {
/* 346 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String fixNull(String s) {
/* 351 */     if (s == null) {
/* 352 */       return "";
/*     */     }
/* 354 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T readAsJAXB(Unmarshaller um) throws JAXBException {
/*     */     try {
/* 362 */       return (T)um.unmarshal((XMLStreamReader)this.completeHeader.readAsXMLStreamReader());
/* 363 */     } catch (XMLStreamException e) {
/* 364 */       throw new JAXBException(e);
/* 365 */     } catch (Exception e) {
/* 366 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge) throws JAXBException {
/*     */     try {
/* 373 */       return (T)bridge.unmarshal((XMLStreamReader)this.completeHeader.readAsXMLStreamReader());
/* 374 */     } catch (XMLStreamException e) {
/* 375 */       throw new JAXBException(e);
/* 376 */     } catch (Exception e) {
/* 377 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public HashMap<String, String> getInscopeNSContext() {
/* 382 */     return this.nsDecls;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static final class Attribute
/*     */   {
/*     */     final String nsUri;
/*     */     
/*     */     final String localName;
/*     */     
/*     */     final String value;
/*     */     
/*     */     public Attribute(String nsUri, String localName, String value) {
/* 395 */       this.nsUri = GenericSecuredHeader.fixNull(nsUri);
/* 396 */       this.localName = localName;
/* 397 */       this.value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   class IDProcessor
/*     */     implements StreamFilter
/*     */   {
/*     */     boolean elementRead = false;
/*     */     
/*     */     public boolean accept(XMLStreamReader reader) {
/* 407 */       if (reader.getEventType() == 2 && 
/* 408 */         reader.getLocalName().equals(GenericSecuredHeader.this.localName) && reader.getNamespaceURI().equals(GenericSecuredHeader.this.namespaceURI)) {
/* 409 */         this.elementRead = true;
/*     */       }
/*     */       
/* 412 */       if (!this.elementRead && reader.getEventType() == 1) {
/* 413 */         String id = reader.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/* 414 */         if (id != null && id.length() > 0) {
/* 415 */           GenericSecuredHeader.this.idValues.add(id);
/*     */         }
/*     */       } 
/*     */       
/* 419 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\GenericSecuredHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */