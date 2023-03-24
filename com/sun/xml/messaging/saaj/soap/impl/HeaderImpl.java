/*     */ package com.sun.xml.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocument;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPHeaderElement;
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
/*     */ public abstract class HeaderImpl
/*     */   extends ElementImpl
/*     */   implements SOAPHeader
/*     */ {
/*     */   protected static final boolean MUST_UNDERSTAND_ONLY = false;
/*     */   
/*     */   protected HeaderImpl(SOAPDocumentImpl ownerDoc, NameImpl name) {
/*  60 */     super(ownerDoc, (Name)name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract SOAPHeaderElement createHeaderElement(Name paramName) throws SOAPException;
/*     */ 
/*     */   
/*     */   protected abstract SOAPHeaderElement createHeaderElement(QName paramQName) throws SOAPException;
/*     */ 
/*     */   
/*     */   public SOAPHeaderElement addHeaderElement(Name name) throws SOAPException {
/*  72 */     SOAPElement newHeaderElement = ElementFactory.createNamedElement(((SOAPDocument)getOwnerDocument()).getDocument(), name.getLocalName(), name.getPrefix(), name.getURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     if (newHeaderElement == null || !(newHeaderElement instanceof SOAPHeaderElement))
/*     */     {
/*  80 */       newHeaderElement = createHeaderElement(name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  85 */     String uri = newHeaderElement.getElementQName().getNamespaceURI();
/*  86 */     if (uri == null || "".equals(uri)) {
/*  87 */       log.severe("SAAJ0131.impl.header.elems.ns.qualified");
/*  88 */       throw new SOAPExceptionImpl("HeaderElements must be namespace qualified");
/*     */     } 
/*  90 */     addNode(newHeaderElement);
/*  91 */     return (SOAPHeaderElement)newHeaderElement;
/*     */   } protected abstract NameImpl getNotUnderstoodName(); protected abstract NameImpl getUpgradeName();
/*     */   protected abstract NameImpl getSupportedEnvelopeName();
/*     */   public SOAPHeaderElement addHeaderElement(QName name) throws SOAPException {
/*  95 */     SOAPElement newHeaderElement = ElementFactory.createNamedElement(((SOAPDocument)getOwnerDocument()).getDocument(), name.getLocalPart(), name.getPrefix(), name.getNamespaceURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     if (newHeaderElement == null || !(newHeaderElement instanceof SOAPHeaderElement))
/*     */     {
/* 103 */       newHeaderElement = createHeaderElement(name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 108 */     String uri = newHeaderElement.getElementQName().getNamespaceURI();
/* 109 */     if (uri == null || "".equals(uri)) {
/* 110 */       log.severe("SAAJ0131.impl.header.elems.ns.qualified");
/* 111 */       throw new SOAPExceptionImpl("HeaderElements must be namespace qualified");
/*     */     } 
/* 113 */     addNode(newHeaderElement);
/* 114 */     return (SOAPHeaderElement)newHeaderElement;
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(Name name) throws SOAPException {
/* 118 */     return addHeaderElement(name);
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(QName name) throws SOAPException {
/* 122 */     return addHeaderElement(name);
/*     */   }
/*     */   
/*     */   public Iterator examineHeaderElements(String actor) {
/* 126 */     return getHeaderElementsForActor(actor, false, false);
/*     */   }
/*     */   
/*     */   public Iterator extractHeaderElements(String actor) {
/* 130 */     return getHeaderElementsForActor(actor, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Iterator getHeaderElementsForActor(String actor, boolean detach, boolean mustUnderstand) {
/* 137 */     if (actor == null || actor.equals("")) {
/* 138 */       log.severe("SAAJ0132.impl.invalid.value.for.actor.or.role");
/* 139 */       throw new IllegalArgumentException("Invalid value for actor or role");
/*     */     } 
/* 141 */     return getHeaderElements(actor, detach, mustUnderstand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Iterator getHeaderElements(String actor, boolean detach, boolean mustUnderstand) {
/* 148 */     List<HeaderElementImpl> elementList = new ArrayList();
/*     */     
/* 150 */     Iterator eachChild = getChildElements();
/*     */     
/* 152 */     Object currentChild = iterate(eachChild);
/* 153 */     while (currentChild != null) {
/* 154 */       if (!(currentChild instanceof SOAPHeaderElement)) {
/* 155 */         currentChild = iterate(eachChild); continue;
/*     */       } 
/* 157 */       HeaderElementImpl currentElement = (HeaderElementImpl)currentChild;
/*     */       
/* 159 */       currentChild = iterate(eachChild);
/*     */       
/* 161 */       boolean isMustUnderstandMatching = (!mustUnderstand || currentElement.getMustUnderstand());
/*     */       
/* 163 */       boolean doAdd = false;
/* 164 */       if (actor == null && isMustUnderstandMatching) {
/* 165 */         doAdd = true;
/*     */       } else {
/* 167 */         String currentActor = currentElement.getActorOrRole();
/* 168 */         if (currentActor == null) {
/* 169 */           currentActor = "";
/*     */         }
/*     */         
/* 172 */         if (currentActor.equalsIgnoreCase(actor) && isMustUnderstandMatching)
/*     */         {
/* 174 */           doAdd = true;
/*     */         }
/*     */       } 
/*     */       
/* 178 */       if (doAdd) {
/* 179 */         elementList.add(currentElement);
/* 180 */         if (detach) {
/* 181 */           currentElement.detachNode();
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 187 */     return elementList.listIterator();
/*     */   }
/*     */   
/*     */   private Object iterate(Iterator each) {
/* 191 */     return each.hasNext() ? each.next() : null;
/*     */   }
/*     */   
/*     */   public void setParentElement(SOAPElement element) throws SOAPException {
/* 195 */     if (!(element instanceof javax.xml.soap.SOAPEnvelope)) {
/* 196 */       log.severe("SAAJ0133.impl.header.parent.mustbe.envelope");
/* 197 */       throw new SOAPException("Parent of SOAPHeader has to be a SOAPEnvelope");
/*     */     } 
/* 199 */     super.setParentElement(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement addChildElement(String localName) throws SOAPException {
/* 208 */     SOAPElement element = super.addChildElement(localName);
/*     */     
/* 210 */     String uri = element.getElementName().getURI();
/* 211 */     if (uri == null || "".equals(uri)) {
/* 212 */       log.severe("SAAJ0134.impl.header.elems.ns.qualified");
/* 213 */       throw new SOAPExceptionImpl("HeaderElements must be namespace qualified");
/*     */     } 
/* 215 */     return element;
/*     */   }
/*     */   
/*     */   public Iterator examineAllHeaderElements() {
/* 219 */     return getHeaderElements((String)null, false, false);
/*     */   }
/*     */   
/*     */   public Iterator examineMustUnderstandHeaderElements(String actor) {
/* 223 */     return getHeaderElements(actor, false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator extractAllHeaderElements() {
/* 228 */     return getHeaderElements((String)null, true, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPHeaderElement addUpgradeHeaderElement(Iterator<String> supportedSoapUris) throws SOAPException {
/* 233 */     if (supportedSoapUris == null) {
/* 234 */       log.severe("SAAJ0411.ver1_2.no.null.supportedURIs");
/* 235 */       throw new SOAPException("Argument cannot be null; iterator of supportedURIs cannot be null");
/*     */     } 
/* 237 */     if (!supportedSoapUris.hasNext()) {
/* 238 */       log.severe("SAAJ0412.ver1_2.no.empty.list.of.supportedURIs");
/* 239 */       throw new SOAPException("List of supported URIs cannot be empty");
/*     */     } 
/* 241 */     NameImpl nameImpl1 = getUpgradeName();
/* 242 */     SOAPHeaderElement upgradeHeaderElement = (SOAPHeaderElement)addChildElement((Name)nameImpl1);
/*     */     
/* 244 */     NameImpl nameImpl2 = getSupportedEnvelopeName();
/* 245 */     int i = 0;
/* 246 */     while (supportedSoapUris.hasNext()) {
/* 247 */       SOAPElement subElement = upgradeHeaderElement.addChildElement((Name)nameImpl2);
/*     */       
/* 249 */       String ns = "ns" + Integer.toString(i);
/* 250 */       subElement.addAttribute((Name)NameImpl.createFromUnqualifiedName("qname"), ns + ":Envelope");
/*     */ 
/*     */       
/* 253 */       subElement.addNamespaceDeclaration(ns, supportedSoapUris.next());
/*     */ 
/*     */       
/* 256 */       i++;
/*     */     } 
/* 258 */     return upgradeHeaderElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPHeaderElement addUpgradeHeaderElement(String supportedSoapUri) throws SOAPException {
/* 263 */     return addUpgradeHeaderElement(new String[] { supportedSoapUri });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPHeaderElement addUpgradeHeaderElement(String[] supportedSoapUris) throws SOAPException {
/* 269 */     if (supportedSoapUris == null) {
/* 270 */       log.severe("SAAJ0411.ver1_2.no.null.supportedURIs");
/* 271 */       throw new SOAPException("Argument cannot be null; array of supportedURIs cannot be null");
/*     */     } 
/* 273 */     if (supportedSoapUris.length == 0) {
/* 274 */       log.severe("SAAJ0412.ver1_2.no.empty.list.of.supportedURIs");
/* 275 */       throw new SOAPException("List of supported URIs cannot be empty");
/*     */     } 
/* 277 */     NameImpl nameImpl1 = getUpgradeName();
/* 278 */     SOAPHeaderElement upgradeHeaderElement = (SOAPHeaderElement)addChildElement((Name)nameImpl1);
/*     */     
/* 280 */     NameImpl nameImpl2 = getSupportedEnvelopeName();
/* 281 */     for (int i = 0; i < supportedSoapUris.length; i++) {
/* 282 */       SOAPElement subElement = upgradeHeaderElement.addChildElement((Name)nameImpl2);
/*     */       
/* 284 */       String ns = "ns" + Integer.toString(i);
/* 285 */       subElement.addAttribute((Name)NameImpl.createFromUnqualifiedName("qname"), ns + ":Envelope");
/*     */ 
/*     */       
/* 288 */       subElement.addNamespaceDeclaration(ns, supportedSoapUris[i]);
/*     */     } 
/* 290 */     return upgradeHeaderElement;
/*     */   }
/*     */   protected SOAPElement convertToSoapElement(Element element) {
/*     */     SOAPHeaderElement headerElement;
/* 294 */     if (element instanceof SOAPHeaderElement) {
/* 295 */       return (SOAPElement)element;
/*     */     }
/*     */     
/*     */     try {
/* 299 */       headerElement = createHeaderElement(NameImpl.copyElementName(element));
/*     */     }
/* 301 */     catch (SOAPException e) {
/* 302 */       throw new ClassCastException("Could not convert Element to SOAPHeaderElement: " + e.getMessage());
/*     */     } 
/* 304 */     return replaceElementWithSOAPElement(element, (ElementImpl)headerElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 311 */     log.log(Level.SEVERE, "SAAJ0146.impl.invalid.name.change.requested", new Object[] { this.elementQName.getLocalPart(), newName.getLocalPart() });
/*     */ 
/*     */ 
/*     */     
/* 315 */     throw new SOAPException("Cannot change name for " + this.elementQName.getLocalPart() + " to " + newName.getLocalPart());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\HeaderImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */