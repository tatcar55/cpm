/*     */ package com.sun.xml.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.Locale;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.soap.SOAPFaultElement;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FaultImpl
/*     */   extends ElementImpl
/*     */   implements SOAPFault
/*     */ {
/*     */   protected SOAPFaultElement faultStringElement;
/*     */   protected SOAPFaultElement faultActorElement;
/*     */   protected SOAPFaultElement faultCodeElement;
/*     */   protected Detail detail;
/*     */   
/*     */   protected FaultImpl(SOAPDocumentImpl ownerDoc, NameImpl name) {
/*  68 */     super(ownerDoc, (Name)name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract NameImpl getDetailName();
/*     */ 
/*     */   
/*     */   protected abstract NameImpl getFaultCodeName();
/*     */ 
/*     */   
/*     */   protected abstract NameImpl getFaultStringName();
/*     */   
/*     */   protected abstract NameImpl getFaultActorName();
/*     */   
/*     */   protected abstract DetailImpl createDetail();
/*     */   
/*     */   protected abstract FaultElementImpl createSOAPFaultElement(String paramString);
/*     */   
/*     */   protected void findFaultCodeElement() {
/*  87 */     this.faultCodeElement = (SOAPFaultElement)findChild(getFaultCodeName());
/*     */   } protected abstract FaultElementImpl createSOAPFaultElement(QName paramQName); protected abstract FaultElementImpl createSOAPFaultElement(Name paramName); protected abstract void checkIfStandardFaultCode(String paramString1, String paramString2) throws SOAPException; protected abstract void finallySetFaultCode(String paramString) throws SOAPException;
/*     */   protected abstract boolean isStandardFaultElement(String paramString);
/*     */   protected abstract QName getDefaultFaultCode();
/*     */   protected void findFaultActorElement() {
/*  92 */     this.faultActorElement = (SOAPFaultElement)findChild(getFaultActorName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void findFaultStringElement() {
/*  97 */     this.faultStringElement = (SOAPFaultElement)findChild(getFaultStringName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFaultCode(String faultCode) throws SOAPException {
/* 102 */     setFaultCode(NameImpl.getLocalNameFromTagName(faultCode), NameImpl.getPrefixFromTagName(faultCode), (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFaultCode(String faultCode, String prefix, String uri) throws SOAPException {
/* 111 */     if ((prefix == null || "".equals(prefix)) && 
/* 112 */       uri != null && !"".equals(uri)) {
/* 113 */       prefix = getNamespacePrefix(uri);
/* 114 */       if (prefix == null || "".equals(prefix)) {
/* 115 */         prefix = "ns0";
/*     */       }
/*     */     } 
/*     */     
/* 119 */     if (this.faultCodeElement == null) {
/* 120 */       findFaultCodeElement();
/*     */     }
/* 122 */     if (this.faultCodeElement == null) {
/* 123 */       this.faultCodeElement = addFaultCodeElement();
/*     */     } else {
/* 125 */       this.faultCodeElement.removeContents();
/*     */     } 
/* 127 */     if (uri == null || "".equals(uri)) {
/* 128 */       uri = this.faultCodeElement.getNamespaceURI(prefix);
/*     */     }
/* 130 */     if (uri == null || "".equals(uri)) {
/* 131 */       if (prefix != null && !"".equals(prefix)) {
/*     */         
/* 133 */         log.log(Level.SEVERE, "SAAJ0140.impl.no.ns.URI", new Object[] { prefix + ":" + faultCode });
/* 134 */         throw new SOAPExceptionImpl("Empty/Null NamespaceURI specified for faultCode \"" + prefix + ":" + faultCode + "\"");
/*     */       } 
/* 136 */       uri = "";
/*     */     } 
/*     */     
/* 139 */     checkIfStandardFaultCode(faultCode, uri);
/* 140 */     ((FaultElementImpl)this.faultCodeElement).ensureNamespaceIsDeclared(prefix, uri);
/*     */     
/* 142 */     if (prefix == null || "".equals(prefix)) {
/* 143 */       finallySetFaultCode(faultCode);
/*     */     } else {
/* 145 */       finallySetFaultCode(prefix + ":" + faultCode);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFaultCode(Name faultCodeQName) throws SOAPException {
/* 150 */     setFaultCode(faultCodeQName.getLocalName(), faultCodeQName.getPrefix(), faultCodeQName.getURI());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFaultCode(QName faultCodeQName) throws SOAPException {
/* 157 */     setFaultCode(faultCodeQName.getLocalPart(), faultCodeQName.getPrefix(), faultCodeQName.getNamespaceURI());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static QName convertCodeToQName(String code, SOAPElement codeContainingElement) {
/* 167 */     int prefixIndex = code.indexOf(':');
/* 168 */     if (prefixIndex == -1) {
/* 169 */       return new QName(code);
/*     */     }
/*     */     
/* 172 */     String prefix = code.substring(0, prefixIndex);
/* 173 */     String nsName = ((ElementImpl)codeContainingElement).lookupNamespaceURI(prefix);
/*     */     
/* 175 */     return new QName(nsName, getLocalPart(code), prefix);
/*     */   }
/*     */   
/*     */   protected void initializeDetail() {
/* 179 */     NameImpl detailName = getDetailName();
/* 180 */     this.detail = (Detail)findChild(detailName);
/*     */   }
/*     */   
/*     */   public Detail getDetail() {
/* 184 */     if (this.detail == null)
/* 185 */       initializeDetail(); 
/* 186 */     if (this.detail != null && this.detail.getParentNode() == null)
/*     */     {
/* 188 */       this.detail = null;
/*     */     }
/* 190 */     return this.detail;
/*     */   }
/*     */   
/*     */   public Detail addDetail() throws SOAPException {
/* 194 */     if (this.detail == null)
/* 195 */       initializeDetail(); 
/* 196 */     if (this.detail == null) {
/* 197 */       this.detail = createDetail();
/* 198 */       addNode(this.detail);
/* 199 */       return this.detail;
/*     */     } 
/*     */     
/* 202 */     throw new SOAPExceptionImpl("Error: Detail already exists");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasDetail() {
/* 207 */     return (getDetail() != null);
/*     */   }
/*     */   
/*     */   public abstract void setFaultActor(String paramString) throws SOAPException;
/*     */   
/*     */   public String getFaultActor() {
/* 213 */     if (this.faultActorElement == null)
/* 214 */       findFaultActorElement(); 
/* 215 */     if (this.faultActorElement != null) {
/* 216 */       return this.faultActorElement.getValue();
/*     */     }
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 223 */     log.log(Level.SEVERE, "SAAJ0146.impl.invalid.name.change.requested", new Object[] { this.elementQName.getLocalPart(), newName.getLocalPart() });
/*     */ 
/*     */ 
/*     */     
/* 227 */     throw new SOAPException("Cannot change name for " + this.elementQName.getLocalPart() + " to " + newName.getLocalPart());
/*     */   }
/*     */   
/*     */   protected SOAPElement convertToSoapElement(Element element) {
/*     */     ElementImpl newElement;
/* 232 */     if (element instanceof SOAPFaultElement)
/* 233 */       return (SOAPElement)element; 
/* 234 */     if (element instanceof SOAPElement) {
/* 235 */       SOAPElement soapElement = (SOAPElement)element;
/* 236 */       if (getDetailName().equals(soapElement.getElementName())) {
/* 237 */         return replaceElementWithSOAPElement(element, createDetail());
/*     */       }
/* 239 */       String localName = soapElement.getElementName().getLocalName();
/*     */       
/* 241 */       if (isStandardFaultElement(localName)) {
/* 242 */         return replaceElementWithSOAPElement(element, createSOAPFaultElement(soapElement.getElementQName()));
/*     */       }
/*     */       
/* 245 */       return soapElement;
/*     */     } 
/*     */     
/* 248 */     Name elementName = NameImpl.copyElementName(element);
/*     */     
/* 250 */     if (getDetailName().equals(elementName)) {
/* 251 */       newElement = createDetail();
/*     */     } else {
/* 253 */       String localName = elementName.getLocalName();
/* 254 */       if (isStandardFaultElement(localName)) {
/* 255 */         newElement = createSOAPFaultElement(elementName);
/*     */       } else {
/*     */         
/* 258 */         newElement = (ElementImpl)createElement(elementName);
/*     */       } 
/* 260 */     }  return replaceElementWithSOAPElement(element, newElement);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SOAPFaultElement addFaultCodeElement() throws SOAPException {
/* 265 */     if (this.faultCodeElement == null)
/* 266 */       findFaultCodeElement(); 
/* 267 */     if (this.faultCodeElement == null) {
/* 268 */       this.faultCodeElement = addSOAPFaultElement(getFaultCodeName().getLocalName());
/*     */       
/* 270 */       return this.faultCodeElement;
/*     */     } 
/* 272 */     throw new SOAPExceptionImpl("Error: Faultcode already exists");
/*     */   }
/*     */ 
/*     */   
/*     */   private SOAPFaultElement addFaultStringElement() throws SOAPException {
/* 277 */     if (this.faultStringElement == null)
/* 278 */       findFaultStringElement(); 
/* 279 */     if (this.faultStringElement == null) {
/* 280 */       this.faultStringElement = addSOAPFaultElement(getFaultStringName().getLocalName());
/*     */       
/* 282 */       return this.faultStringElement;
/*     */     } 
/*     */     
/* 285 */     throw new SOAPExceptionImpl("Error: Faultstring already exists");
/*     */   }
/*     */ 
/*     */   
/*     */   private SOAPFaultElement addFaultActorElement() throws SOAPException {
/* 290 */     if (this.faultActorElement == null)
/* 291 */       findFaultActorElement(); 
/* 292 */     if (this.faultActorElement == null) {
/* 293 */       this.faultActorElement = addSOAPFaultElement(getFaultActorName().getLocalName());
/*     */       
/* 295 */       return this.faultActorElement;
/*     */     } 
/*     */     
/* 298 */     throw new SOAPExceptionImpl("Error: Faultactor already exists");
/*     */   }
/*     */ 
/*     */   
/*     */   protected SOAPElement addElement(Name name) throws SOAPException {
/* 303 */     if (getDetailName().equals(name))
/* 304 */       return addDetail(); 
/* 305 */     if (getFaultCodeName().equals(name))
/* 306 */       return addFaultCodeElement(); 
/* 307 */     if (getFaultStringName().equals(name))
/* 308 */       return addFaultStringElement(); 
/* 309 */     if (getFaultActorName().equals(name)) {
/* 310 */       return addFaultActorElement();
/*     */     }
/* 312 */     return super.addElement(name);
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(QName name) throws SOAPException {
/* 316 */     return addElement(NameImpl.convertToName(name));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected FaultElementImpl addSOAPFaultElement(String localName) throws SOAPException {
/* 322 */     FaultElementImpl faultElem = createSOAPFaultElement(localName);
/* 323 */     addNode(faultElem);
/* 324 */     return faultElem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Locale xmlLangToLocale(String xmlLang) {
/* 331 */     if (xmlLang == null) {
/* 332 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 336 */     int index = xmlLang.indexOf("-");
/*     */ 
/*     */     
/* 339 */     if (index == -1) {
/* 340 */       index = xmlLang.indexOf("_");
/*     */     }
/*     */     
/* 343 */     if (index == -1)
/*     */     {
/* 345 */       return new Locale(xmlLang, "");
/*     */     }
/*     */     
/* 348 */     String language = xmlLang.substring(0, index);
/* 349 */     String country = xmlLang.substring(index + 1);
/* 350 */     return new Locale(language, country);
/*     */   }
/*     */   
/*     */   protected static String localeToXmlLang(Locale locale) {
/* 354 */     String xmlLang = locale.getLanguage();
/* 355 */     String country = locale.getCountry();
/* 356 */     if (!"".equals(country)) {
/* 357 */       xmlLang = xmlLang + "-" + country;
/*     */     }
/* 359 */     return xmlLang;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\FaultImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */