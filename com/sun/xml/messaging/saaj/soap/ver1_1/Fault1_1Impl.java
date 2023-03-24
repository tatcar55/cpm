/*     */ package com.sun.xml.messaging.saaj.soap.ver1_1;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocument;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.DetailImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.FaultElementImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.FaultImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFaultElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Fault1_1Impl
/*     */   extends FaultImpl
/*     */ {
/*  70 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.ver1_1", "com.sun.xml.messaging.saaj.soap.ver1_1.LocalStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fault1_1Impl(SOAPDocumentImpl ownerDocument, String prefix) {
/*  76 */     super(ownerDocument, NameImpl.createFault1_1Name(prefix));
/*     */   }
/*     */   
/*     */   protected NameImpl getDetailName() {
/*  80 */     return NameImpl.createDetail1_1Name();
/*     */   }
/*     */   
/*     */   protected NameImpl getFaultCodeName() {
/*  84 */     return NameImpl.createFromUnqualifiedName("faultcode");
/*     */   }
/*     */   
/*     */   protected NameImpl getFaultStringName() {
/*  88 */     return NameImpl.createFromUnqualifiedName("faultstring");
/*     */   }
/*     */   
/*     */   protected NameImpl getFaultActorName() {
/*  92 */     return NameImpl.createFromUnqualifiedName("faultactor");
/*     */   }
/*     */   
/*     */   protected DetailImpl createDetail() {
/*  96 */     return new Detail1_1Impl(((SOAPDocument)getOwnerDocument()).getDocument());
/*     */   }
/*     */ 
/*     */   
/*     */   protected FaultElementImpl createSOAPFaultElement(String localName) {
/* 101 */     return new FaultElement1_1Impl(((SOAPDocument)getOwnerDocument()).getDocument(), localName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkIfStandardFaultCode(String faultCode, String uri) throws SOAPException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finallySetFaultCode(String faultcode) throws SOAPException {
/* 114 */     this.faultCodeElement.addTextNode(faultcode);
/*     */   }
/*     */   
/*     */   public String getFaultCode() {
/* 118 */     if (this.faultCodeElement == null)
/* 119 */       findFaultCodeElement(); 
/* 120 */     return this.faultCodeElement.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Name getFaultCodeAsName() {
/* 125 */     String faultcodeString = getFaultCode();
/* 126 */     if (faultcodeString == null) {
/* 127 */       return null;
/*     */     }
/* 129 */     int prefixIndex = faultcodeString.indexOf(':');
/* 130 */     if (prefixIndex == -1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       return (Name)NameImpl.createFromUnqualifiedName(faultcodeString);
/*     */     }
/*     */ 
/*     */     
/* 140 */     String prefix = faultcodeString.substring(0, prefixIndex);
/* 141 */     if (this.faultCodeElement == null)
/* 142 */       findFaultCodeElement(); 
/* 143 */     String nsName = this.faultCodeElement.getNamespaceURI(prefix);
/* 144 */     return NameImpl.createFromQualifiedName(faultcodeString, nsName);
/*     */   }
/*     */   
/*     */   public QName getFaultCodeAsQName() {
/* 148 */     String faultcodeString = getFaultCode();
/* 149 */     if (faultcodeString == null) {
/* 150 */       return null;
/*     */     }
/* 152 */     if (this.faultCodeElement == null)
/* 153 */       findFaultCodeElement(); 
/* 154 */     return convertCodeToQName(faultcodeString, this.faultCodeElement);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFaultString(String faultString) throws SOAPException {
/* 159 */     if (this.faultStringElement == null) {
/* 160 */       findFaultStringElement();
/*     */     }
/* 162 */     if (this.faultStringElement == null) {
/* 163 */       this.faultStringElement = (SOAPFaultElement)addSOAPFaultElement("faultstring");
/*     */     } else {
/* 165 */       this.faultStringElement.removeContents();
/*     */       
/* 167 */       this.faultStringElement.removeAttribute("xml:lang");
/*     */     } 
/*     */     
/* 170 */     this.faultStringElement.addTextNode(faultString);
/*     */   }
/*     */   
/*     */   public String getFaultString() {
/* 174 */     if (this.faultStringElement == null)
/* 175 */       findFaultStringElement(); 
/* 176 */     return this.faultStringElement.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Locale getFaultStringLocale() {
/* 181 */     if (this.faultStringElement == null)
/* 182 */       findFaultStringElement(); 
/* 183 */     if (this.faultStringElement != null) {
/* 184 */       String xmlLangAttr = this.faultStringElement.getAttributeValue((Name)NameImpl.createFromUnqualifiedName("xml:lang"));
/*     */ 
/*     */       
/* 187 */       if (xmlLangAttr != null)
/* 188 */         return xmlLangToLocale(xmlLangAttr); 
/*     */     } 
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFaultString(String faultString, Locale locale) throws SOAPException {
/* 195 */     setFaultString(faultString);
/* 196 */     this.faultStringElement.addAttribute(NameImpl.createFromTagName("xml:lang"), localeToXmlLang(locale));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isStandardFaultElement(String localName) {
/* 202 */     if (localName.equalsIgnoreCase("detail") || localName.equalsIgnoreCase("faultcode") || localName.equalsIgnoreCase("faultstring") || localName.equalsIgnoreCase("faultactor"))
/*     */     {
/*     */ 
/*     */       
/* 206 */       return true;
/*     */     }
/* 208 */     return false;
/*     */   }
/*     */   
/*     */   public void appendFaultSubcode(QName subcode) {
/* 212 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", "appendFaultSubcode");
/*     */ 
/*     */ 
/*     */     
/* 216 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*     */   }
/*     */   
/*     */   public void removeAllFaultSubcodes() {
/* 220 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", "removeAllFaultSubcodes");
/*     */ 
/*     */ 
/*     */     
/* 224 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*     */   }
/*     */   
/*     */   public Iterator getFaultSubcodes() {
/* 228 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", "getFaultSubcodes");
/*     */ 
/*     */ 
/*     */     
/* 232 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*     */   }
/*     */   
/*     */   public String getFaultReasonText(Locale locale) {
/* 236 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", "getFaultReasonText");
/*     */ 
/*     */ 
/*     */     
/* 240 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*     */   }
/*     */   
/*     */   public Iterator getFaultReasonTexts() {
/* 244 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", "getFaultReasonTexts");
/*     */ 
/*     */ 
/*     */     
/* 248 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*     */   }
/*     */   
/*     */   public Iterator getFaultReasonLocales() {
/* 252 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", "getFaultReasonLocales");
/*     */ 
/*     */ 
/*     */     
/* 256 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFaultReasonText(String text, Locale locale) throws SOAPException {
/* 261 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", "addFaultReasonText");
/*     */ 
/*     */ 
/*     */     
/* 265 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*     */   }
/*     */   
/*     */   public String getFaultRole() {
/* 269 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", "getFaultRole");
/*     */ 
/*     */ 
/*     */     
/* 273 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*     */   }
/*     */   
/*     */   public void setFaultRole(String uri) {
/* 277 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", "setFaultRole");
/*     */ 
/*     */ 
/*     */     
/* 281 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*     */   }
/*     */   
/*     */   public String getFaultNode() {
/* 285 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", "getFaultNode");
/*     */ 
/*     */ 
/*     */     
/* 289 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*     */   }
/*     */   
/*     */   public void setFaultNode(String uri) {
/* 293 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", "setFaultNode");
/*     */ 
/*     */ 
/*     */     
/* 297 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*     */   }
/*     */   
/*     */   protected QName getDefaultFaultCode() {
/* 301 */     return new QName("http://schemas.xmlsoap.org/soap/envelope/", "Server");
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addChildElement(SOAPElement element) throws SOAPException {
/* 306 */     String localName = element.getLocalName();
/* 307 */     if ("Detail".equalsIgnoreCase(localName) && 
/* 308 */       hasDetail()) {
/* 309 */       log.severe("SAAJ0305.ver1_2.detail.exists.error");
/* 310 */       throw new SOAPExceptionImpl("Cannot add Detail, Detail already exists");
/*     */     } 
/*     */     
/* 313 */     return super.addChildElement(element);
/*     */   }
/*     */   
/*     */   protected FaultElementImpl createSOAPFaultElement(QName qname) {
/* 317 */     return new FaultElement1_1Impl(((SOAPDocument)getOwnerDocument()).getDocument(), qname);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected FaultElementImpl createSOAPFaultElement(Name qname) {
/* 323 */     return new FaultElement1_1Impl(((SOAPDocument)getOwnerDocument()).getDocument(), (NameImpl)qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFaultCode(String faultCode, String prefix, String uri) throws SOAPException {
/* 330 */     if ((prefix == null || "".equals(prefix)) && 
/* 331 */       uri != null && !"".equals(uri)) {
/* 332 */       prefix = getNamespacePrefix(uri);
/* 333 */       if (prefix == null || "".equals(prefix)) {
/* 334 */         prefix = "ns0";
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 339 */     if (this.faultCodeElement == null) {
/* 340 */       findFaultCodeElement();
/*     */     }
/* 342 */     if (this.faultCodeElement == null) {
/* 343 */       this.faultCodeElement = addFaultCodeElement();
/*     */     } else {
/* 345 */       this.faultCodeElement.removeContents();
/*     */     } 
/* 347 */     if ((uri == null || "".equals(uri)) && 
/* 348 */       prefix != null && !"".equals("prefix")) {
/* 349 */       uri = this.faultCodeElement.getNamespaceURI(prefix);
/*     */     }
/*     */ 
/*     */     
/* 353 */     if (uri == null || "".equals(uri)) {
/* 354 */       if (prefix != null && !"".equals(prefix)) {
/*     */         
/* 356 */         log.log(Level.SEVERE, "SAAJ0307.impl.no.ns.URI", new Object[] { prefix + ":" + faultCode });
/* 357 */         throw new SOAPExceptionImpl("Empty/Null NamespaceURI specified for faultCode \"" + prefix + ":" + faultCode + "\"");
/*     */       } 
/* 359 */       uri = "";
/*     */     } 
/*     */ 
/*     */     
/* 363 */     checkIfStandardFaultCode(faultCode, uri);
/* 364 */     ((FaultElementImpl)this.faultCodeElement).ensureNamespaceIsDeclared(prefix, uri);
/*     */     
/* 366 */     if (prefix == null || "".equals(prefix)) {
/* 367 */       finallySetFaultCode(faultCode);
/*     */     } else {
/* 369 */       finallySetFaultCode(prefix + ":" + faultCode);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean standardFaultCode(String faultCode) {
/* 374 */     if (faultCode.equals("VersionMismatch") || faultCode.equals("MustUnderstand") || faultCode.equals("Client") || faultCode.equals("Server"))
/*     */     {
/* 376 */       return true;
/*     */     }
/* 378 */     if (faultCode.startsWith("VersionMismatch.") || faultCode.startsWith("MustUnderstand.") || faultCode.startsWith("Client.") || faultCode.startsWith("Server."))
/*     */     {
/* 380 */       return true;
/*     */     }
/* 382 */     return false;
/*     */   }
/*     */   
/*     */   public void setFaultActor(String faultActor) throws SOAPException {
/* 386 */     if (this.faultActorElement == null)
/* 387 */       findFaultActorElement(); 
/* 388 */     if (this.faultActorElement != null)
/* 389 */       this.faultActorElement.detachNode(); 
/* 390 */     if (faultActor == null)
/*     */       return; 
/* 392 */     this.faultActorElement = (SOAPFaultElement)createSOAPFaultElement((Name)getFaultActorName());
/*     */     
/* 394 */     this.faultActorElement.addTextNode(faultActor);
/* 395 */     if (hasDetail()) {
/* 396 */       insertBefore(this.faultActorElement, this.detail);
/*     */       return;
/*     */     } 
/* 399 */     addNode(this.faultActorElement);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_1\Fault1_1Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */