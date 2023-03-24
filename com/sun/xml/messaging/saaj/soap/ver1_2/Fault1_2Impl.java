/*     */ package com.sun.xml.messaging.saaj.soap.ver1_2;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocument;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.DetailImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.ElementImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.FaultElementImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.FaultImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFaultElement;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Fault1_2Impl
/*     */   extends FaultImpl
/*     */ {
/*  64 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.ver1_2", "com.sun.xml.messaging.saaj.soap.ver1_2.LocalStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static final QName textName = new QName("http://www.w3.org/2003/05/soap-envelope", "Text");
/*     */   
/*  71 */   private final QName valueName = new QName("http://www.w3.org/2003/05/soap-envelope", "Value", getPrefix());
/*     */   
/*  73 */   private final QName subcodeName = new QName("http://www.w3.org/2003/05/soap-envelope", "Subcode", getPrefix());
/*     */ 
/*     */   
/*  76 */   private SOAPElement innermostSubCodeElement = null;
/*     */   
/*     */   public Fault1_2Impl(SOAPDocumentImpl ownerDoc, String name, String prefix) {
/*  79 */     super(ownerDoc, NameImpl.createFault1_2Name(name, prefix));
/*     */   }
/*     */   
/*     */   public Fault1_2Impl(SOAPDocumentImpl ownerDocument, String prefix) {
/*  83 */     super(ownerDocument, NameImpl.createFault1_2Name(null, prefix));
/*     */   }
/*     */   
/*     */   protected NameImpl getDetailName() {
/*  87 */     return NameImpl.createSOAP12Name("Detail", getPrefix());
/*     */   }
/*     */   
/*     */   protected NameImpl getFaultCodeName() {
/*  91 */     return NameImpl.createSOAP12Name("Code", getPrefix());
/*     */   }
/*     */   
/*     */   protected NameImpl getFaultStringName() {
/*  95 */     return getFaultReasonName();
/*     */   }
/*     */   
/*     */   protected NameImpl getFaultActorName() {
/*  99 */     return getFaultRoleName();
/*     */   }
/*     */   
/*     */   private NameImpl getFaultRoleName() {
/* 103 */     return NameImpl.createSOAP12Name("Role", getPrefix());
/*     */   }
/*     */   
/*     */   private NameImpl getFaultReasonName() {
/* 107 */     return NameImpl.createSOAP12Name("Reason", getPrefix());
/*     */   }
/*     */   
/*     */   private NameImpl getFaultReasonTextName() {
/* 111 */     return NameImpl.createSOAP12Name("Text", getPrefix());
/*     */   }
/*     */   
/*     */   private NameImpl getFaultNodeName() {
/* 115 */     return NameImpl.createSOAP12Name("Node", getPrefix());
/*     */   }
/*     */   
/*     */   private static NameImpl getXmlLangName() {
/* 119 */     return NameImpl.createXmlName("lang");
/*     */   }
/*     */   
/*     */   protected DetailImpl createDetail() {
/* 123 */     return new Detail1_2Impl(((SOAPDocument)getOwnerDocument()).getDocument());
/*     */   }
/*     */ 
/*     */   
/*     */   protected FaultElementImpl createSOAPFaultElement(String localName) {
/* 128 */     return new FaultElement1_2Impl(((SOAPDocument)getOwnerDocument()).getDocument(), localName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkIfStandardFaultCode(String faultCode, String uri) throws SOAPException {
/* 135 */     QName qname = new QName(uri, faultCode);
/* 136 */     if (SOAPConstants.SOAP_DATAENCODINGUNKNOWN_FAULT.equals(qname) || SOAPConstants.SOAP_MUSTUNDERSTAND_FAULT.equals(qname) || SOAPConstants.SOAP_RECEIVER_FAULT.equals(qname) || SOAPConstants.SOAP_SENDER_FAULT.equals(qname) || SOAPConstants.SOAP_VERSIONMISMATCH_FAULT.equals(qname)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 142 */     log.log(Level.SEVERE, "SAAJ0435.ver1_2.code.not.standard", qname);
/*     */ 
/*     */ 
/*     */     
/* 146 */     throw new SOAPExceptionImpl(qname + " is not a standard Code value");
/*     */   }
/*     */   
/*     */   protected void finallySetFaultCode(String faultcode) throws SOAPException {
/* 150 */     SOAPElement value = this.faultCodeElement.addChildElement(this.valueName);
/* 151 */     value.addTextNode(faultcode);
/*     */   }
/*     */   
/*     */   private void findReasonElement() {
/* 155 */     findFaultStringElement();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator getFaultReasonTexts() throws SOAPException {
/* 160 */     if (this.faultStringElement == null)
/* 161 */       findReasonElement(); 
/* 162 */     Iterator<SOAPElement> eachTextElement = this.faultStringElement.getChildElements(textName);
/*     */     
/* 164 */     List<String> texts = new ArrayList();
/* 165 */     while (eachTextElement.hasNext()) {
/* 166 */       SOAPElement textElement = eachTextElement.next();
/* 167 */       Locale thisLocale = getLocale(textElement);
/* 168 */       if (thisLocale == null) {
/* 169 */         log.severe("SAAJ0431.ver1_2.xml.lang.missing");
/* 170 */         throw new SOAPExceptionImpl("\"xml:lang\" attribute is not present on the Text element");
/*     */       } 
/* 172 */       texts.add(textElement.getValue());
/*     */     } 
/* 174 */     if (texts.isEmpty()) {
/* 175 */       log.severe("SAAJ0434.ver1_2.text.element.not.present");
/* 176 */       throw new SOAPExceptionImpl("env:Text must be present inside env:Reason");
/*     */     } 
/* 178 */     return texts.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFaultReasonText(String text, Locale locale) throws SOAPException {
/*     */     SOAPElement reasonText;
/* 184 */     if (locale == null) {
/* 185 */       log.severe("SAAJ0430.ver1_2.locale.required");
/* 186 */       throw new SOAPException("locale is required and must not be null");
/*     */     } 
/*     */ 
/*     */     
/* 190 */     if (this.faultStringElement == null) {
/* 191 */       findReasonElement();
/*     */     }
/*     */     
/* 194 */     if (this.faultStringElement == null) {
/* 195 */       this.faultStringElement = (SOAPFaultElement)addSOAPFaultElement("Reason");
/* 196 */       reasonText = this.faultStringElement.addChildElement((Name)getFaultReasonTextName());
/*     */     }
/*     */     else {
/*     */       
/* 200 */       removeDefaultFaultString();
/* 201 */       reasonText = getFaultReasonTextElement(locale);
/* 202 */       if (reasonText != null) {
/* 203 */         reasonText.removeContents();
/*     */       } else {
/* 205 */         reasonText = this.faultStringElement.addChildElement((Name)getFaultReasonTextName());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 211 */     String xmlLang = localeToXmlLang(locale);
/* 212 */     reasonText.addAttribute((Name)getXmlLangName(), xmlLang);
/* 213 */     reasonText.addTextNode(text);
/*     */   }
/*     */   
/*     */   private void removeDefaultFaultString() throws SOAPException {
/* 217 */     SOAPElement reasonText = getFaultReasonTextElement(Locale.getDefault());
/* 218 */     if (reasonText != null) {
/* 219 */       String defaultFaultString = "Fault string, and possibly fault code, not set";
/*     */       
/* 221 */       if (defaultFaultString.equals(reasonText.getValue())) {
/* 222 */         reasonText.detachNode();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFaultReasonText(Locale locale) throws SOAPException {
/* 229 */     if (locale == null) {
/* 230 */       return null;
/*     */     }
/*     */     
/* 233 */     if (this.faultStringElement == null) {
/* 234 */       findReasonElement();
/*     */     }
/* 236 */     if (this.faultStringElement != null) {
/* 237 */       SOAPElement textElement = getFaultReasonTextElement(locale);
/* 238 */       if (textElement != null) {
/* 239 */         textElement.normalize();
/* 240 */         return textElement.getFirstChild().getNodeValue();
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator getFaultReasonLocales() throws SOAPException {
/* 249 */     if (this.faultStringElement == null)
/* 250 */       findReasonElement(); 
/* 251 */     Iterator<SOAPElement> eachTextElement = this.faultStringElement.getChildElements(textName);
/*     */     
/* 253 */     List<Locale> localeSet = new ArrayList();
/* 254 */     while (eachTextElement.hasNext()) {
/* 255 */       SOAPElement textElement = eachTextElement.next();
/* 256 */       Locale thisLocale = getLocale(textElement);
/* 257 */       if (thisLocale == null) {
/* 258 */         log.severe("SAAJ0431.ver1_2.xml.lang.missing");
/* 259 */         throw new SOAPExceptionImpl("\"xml:lang\" attribute is not present on the Text element");
/*     */       } 
/* 261 */       localeSet.add(thisLocale);
/*     */     } 
/* 263 */     if (localeSet.isEmpty()) {
/* 264 */       log.severe("SAAJ0434.ver1_2.text.element.not.present");
/* 265 */       throw new SOAPExceptionImpl("env:Text elements with mandatory xml:lang attributes must be present inside env:Reason");
/*     */     } 
/* 267 */     return localeSet.iterator();
/*     */   }
/*     */   
/*     */   public Locale getFaultStringLocale() {
/* 271 */     Locale locale = null;
/*     */     try {
/* 273 */       locale = getFaultReasonLocales().next();
/* 274 */     } catch (SOAPException e) {}
/* 275 */     return locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPElement getFaultReasonTextElement(Locale locale) throws SOAPException {
/* 285 */     Iterator<SOAPElement> eachTextElement = this.faultStringElement.getChildElements(textName);
/*     */     
/* 287 */     while (eachTextElement.hasNext()) {
/* 288 */       SOAPElement textElement = eachTextElement.next();
/* 289 */       Locale thisLocale = getLocale(textElement);
/* 290 */       if (thisLocale == null) {
/* 291 */         log.severe("SAAJ0431.ver1_2.xml.lang.missing");
/* 292 */         throw new SOAPExceptionImpl("\"xml:lang\" attribute is not present on the Text element");
/*     */       } 
/* 294 */       if (thisLocale.equals(locale)) {
/* 295 */         return textElement;
/*     */       }
/*     */     } 
/* 298 */     return null;
/*     */   }
/*     */   
/*     */   public String getFaultNode() {
/* 302 */     SOAPElement faultNode = findChild(getFaultNodeName());
/* 303 */     if (faultNode == null) {
/* 304 */       return null;
/*     */     }
/* 306 */     return faultNode.getValue();
/*     */   }
/*     */   
/*     */   public void setFaultNode(String uri) throws SOAPException {
/* 310 */     SOAPElement faultNode = findChild(getFaultNodeName());
/* 311 */     if (faultNode != null) {
/* 312 */       faultNode.detachNode();
/*     */     }
/* 314 */     FaultElementImpl faultElementImpl = createSOAPFaultElement((Name)getFaultNodeName());
/* 315 */     SOAPElement sOAPElement1 = faultElementImpl.addTextNode(uri);
/* 316 */     if (getFaultRole() != null) {
/* 317 */       insertBefore(sOAPElement1, this.faultActorElement);
/*     */       return;
/*     */     } 
/* 320 */     if (hasDetail()) {
/* 321 */       insertBefore(sOAPElement1, this.detail);
/*     */       return;
/*     */     } 
/* 324 */     addNode(sOAPElement1);
/*     */   }
/*     */   
/*     */   public String getFaultRole() {
/* 328 */     return getFaultActor();
/*     */   }
/*     */   
/*     */   public void setFaultRole(String uri) throws SOAPException {
/* 332 */     if (this.faultActorElement == null)
/* 333 */       findFaultActorElement(); 
/* 334 */     if (this.faultActorElement != null)
/* 335 */       this.faultActorElement.detachNode(); 
/* 336 */     this.faultActorElement = (SOAPFaultElement)createSOAPFaultElement((Name)getFaultActorName());
/*     */     
/* 338 */     this.faultActorElement.addTextNode(uri);
/* 339 */     if (hasDetail()) {
/* 340 */       insertBefore(this.faultActorElement, this.detail);
/*     */       return;
/*     */     } 
/* 343 */     addNode(this.faultActorElement);
/*     */   }
/*     */   
/*     */   public String getFaultCode() {
/* 347 */     if (this.faultCodeElement == null)
/* 348 */       findFaultCodeElement(); 
/* 349 */     Iterator<SOAPElement> codeValues = this.faultCodeElement.getChildElements(this.valueName);
/*     */     
/* 351 */     return ((SOAPElement)codeValues.next()).getValue();
/*     */   }
/*     */   
/*     */   public QName getFaultCodeAsQName() {
/* 355 */     String faultcode = getFaultCode();
/* 356 */     if (faultcode == null) {
/* 357 */       return null;
/*     */     }
/* 359 */     if (this.faultCodeElement == null)
/* 360 */       findFaultCodeElement(); 
/* 361 */     Iterator<SOAPElement> valueElements = this.faultCodeElement.getChildElements(this.valueName);
/*     */     
/* 363 */     return convertCodeToQName(faultcode, valueElements.next());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Name getFaultCodeAsName() {
/* 369 */     String faultcode = getFaultCode();
/* 370 */     if (faultcode == null) {
/* 371 */       return null;
/*     */     }
/* 373 */     if (this.faultCodeElement == null)
/* 374 */       findFaultCodeElement(); 
/* 375 */     Iterator<SOAPElement> valueElements = this.faultCodeElement.getChildElements(this.valueName);
/*     */     
/* 377 */     return NameImpl.convertToName(convertCodeToQName(faultcode, valueElements.next()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFaultString() {
/* 384 */     String reason = null;
/*     */ 
/*     */     
/*     */     try {
/* 388 */       reason = getFaultReasonTexts().next();
/* 389 */     } catch (SOAPException e) {}
/* 390 */     return reason;
/*     */   }
/*     */   
/*     */   public void setFaultString(String faultString) throws SOAPException {
/* 394 */     addFaultReasonText(faultString, Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFaultString(String faultString, Locale locale) throws SOAPException {
/* 401 */     addFaultReasonText(faultString, locale);
/*     */   }
/*     */   
/*     */   public void appendFaultSubcode(QName subcode) throws SOAPException {
/* 405 */     if (subcode == null) {
/*     */       return;
/*     */     }
/* 408 */     if (subcode.getNamespaceURI() == null || "".equals(subcode.getNamespaceURI())) {
/*     */ 
/*     */       
/* 411 */       log.severe("SAAJ0432.ver1_2.subcode.not.ns.qualified");
/* 412 */       throw new SOAPExceptionImpl("A Subcode must be namespace-qualified");
/*     */     } 
/* 414 */     if (this.innermostSubCodeElement == null) {
/* 415 */       if (this.faultCodeElement == null)
/* 416 */         findFaultCodeElement(); 
/* 417 */       this.innermostSubCodeElement = this.faultCodeElement;
/*     */     } 
/* 419 */     String prefix = null;
/* 420 */     if (subcode.getPrefix() == null || "".equals(subcode.getPrefix())) {
/* 421 */       prefix = ((ElementImpl)this.innermostSubCodeElement).getNamespacePrefix(subcode.getNamespaceURI());
/*     */     }
/*     */     else {
/*     */       
/* 425 */       prefix = subcode.getPrefix();
/* 426 */     }  if (prefix == null || "".equals(prefix)) {
/* 427 */       prefix = "ns1";
/*     */     }
/* 429 */     this.innermostSubCodeElement = this.innermostSubCodeElement.addChildElement(this.subcodeName);
/*     */     
/* 431 */     SOAPElement subcodeValueElement = this.innermostSubCodeElement.addChildElement(this.valueName);
/*     */     
/* 433 */     ((ElementImpl)subcodeValueElement).ensureNamespaceIsDeclared(prefix, subcode.getNamespaceURI());
/*     */ 
/*     */     
/* 436 */     subcodeValueElement.addTextNode(prefix + ":" + subcode.getLocalPart());
/*     */   }
/*     */   
/*     */   public void removeAllFaultSubcodes() {
/* 440 */     if (this.faultCodeElement == null)
/* 441 */       findFaultCodeElement(); 
/* 442 */     Iterator<SOAPElement> subcodeElements = this.faultCodeElement.getChildElements(this.subcodeName);
/*     */     
/* 444 */     if (subcodeElements.hasNext()) {
/* 445 */       SOAPElement subcode = subcodeElements.next();
/* 446 */       subcode.detachNode();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Iterator getFaultSubcodes() {
/* 451 */     if (this.faultCodeElement == null)
/* 452 */       findFaultCodeElement(); 
/* 453 */     final List<QName> subcodeList = new ArrayList();
/* 454 */     SOAPElement currentCodeElement = this.faultCodeElement;
/* 455 */     Iterator<ElementImpl> subcodeElements = currentCodeElement.getChildElements(this.subcodeName);
/*     */     
/* 457 */     while (subcodeElements.hasNext()) {
/* 458 */       ElementImpl elementImpl = subcodeElements.next();
/* 459 */       Iterator<SOAPElement> valueElements = elementImpl.getChildElements(this.valueName);
/*     */       
/* 461 */       SOAPElement valueElement = valueElements.next();
/* 462 */       String code = valueElement.getValue();
/* 463 */       subcodeList.add(convertCodeToQName(code, valueElement));
/* 464 */       subcodeElements = elementImpl.getChildElements(this.subcodeName);
/*     */     } 
/*     */     
/* 467 */     return new Iterator() {
/* 468 */         Iterator subCodeIter = subcodeList.iterator();
/*     */         
/*     */         public boolean hasNext() {
/* 471 */           return this.subCodeIter.hasNext();
/*     */         }
/*     */         
/*     */         public Object next() {
/* 475 */           return this.subCodeIter.next();
/*     */         }
/*     */         
/*     */         public void remove() {
/* 479 */           throw new UnsupportedOperationException("Method remove() not supported on SubCodes Iterator");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static Locale getLocale(SOAPElement reasonText) {
/* 486 */     return xmlLangToLocale(reasonText.getAttributeValue((Name)getXmlLangName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncodingStyle(String encodingStyle) throws SOAPException {
/* 494 */     log.severe("SAAJ0407.ver1_2.no.encodingStyle.in.fault");
/* 495 */     throw new SOAPExceptionImpl("encodingStyle attribute cannot appear on Fault");
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
/* 500 */     if (name.getLocalName().equals("encodingStyle") && name.getURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/* 502 */       setEncodingStyle(value);
/*     */     }
/* 504 */     return super.addAttribute(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(QName name, String value) throws SOAPException {
/* 509 */     if (name.getLocalPart().equals("encodingStyle") && name.getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/* 511 */       setEncodingStyle(value);
/*     */     }
/* 513 */     return super.addAttribute(name, value);
/*     */   }
/*     */   
/*     */   public SOAPElement addTextNode(String text) throws SOAPException {
/* 517 */     log.log(Level.SEVERE, "SAAJ0416.ver1_2.adding.text.not.legal", getElementQName());
/*     */ 
/*     */ 
/*     */     
/* 521 */     throw new SOAPExceptionImpl("Adding text to SOAP 1.2 Fault is not legal");
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addChildElement(SOAPElement element) throws SOAPException {
/* 526 */     String localName = element.getLocalName();
/* 527 */     if ("Detail".equalsIgnoreCase(localName)) {
/* 528 */       if (hasDetail()) {
/* 529 */         log.severe("SAAJ0436.ver1_2.detail.exists.error");
/* 530 */         throw new SOAPExceptionImpl("Cannot add Detail, Detail already exists");
/*     */       } 
/* 532 */       String uri = element.getElementQName().getNamespaceURI();
/* 533 */       if (!uri.equals("http://www.w3.org/2003/05/soap-envelope")) {
/* 534 */         log.severe("SAAJ0437.ver1_2.version.mismatch.error");
/* 535 */         throw new SOAPExceptionImpl("Cannot add Detail, Incorrect SOAP version specified for Detail element");
/*     */       } 
/*     */     } 
/* 538 */     if (element instanceof Detail1_2Impl) {
/* 539 */       ElementImpl importedElement = (ElementImpl)importElement(element);
/* 540 */       addNode((Node)importedElement);
/* 541 */       return convertToSoapElement((Element)importedElement);
/*     */     } 
/* 543 */     return super.addChildElement(element);
/*     */   }
/*     */   
/*     */   protected boolean isStandardFaultElement(String localName) {
/* 547 */     if (localName.equalsIgnoreCase("code") || localName.equalsIgnoreCase("reason") || localName.equalsIgnoreCase("node") || localName.equalsIgnoreCase("role") || localName.equalsIgnoreCase("detail"))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 552 */       return true;
/*     */     }
/* 554 */     return false;
/*     */   }
/*     */   
/*     */   protected QName getDefaultFaultCode() {
/* 558 */     return SOAPConstants.SOAP_SENDER_FAULT;
/*     */   }
/*     */   
/*     */   protected FaultElementImpl createSOAPFaultElement(QName qname) {
/* 562 */     return new FaultElement1_2Impl(((SOAPDocument)getOwnerDocument()).getDocument(), qname);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected FaultElementImpl createSOAPFaultElement(Name qname) {
/* 568 */     return new FaultElement1_2Impl(((SOAPDocument)getOwnerDocument()).getDocument(), (NameImpl)qname);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFaultActor(String faultActor) throws SOAPException {
/* 574 */     setFaultRole(faultActor);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_2\Fault1_2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */