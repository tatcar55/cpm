/*     */ package com.sun.xml.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocument;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.Node;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPBodyElement;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentFragment;
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
/*     */ public abstract class BodyImpl
/*     */   extends ElementImpl
/*     */   implements SOAPBody
/*     */ {
/*     */   private SOAPFault fault;
/*     */   
/*     */   protected BodyImpl(SOAPDocumentImpl ownerDoc, NameImpl bodyName) {
/*  68 */     super(ownerDoc, (Name)bodyName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract NameImpl getFaultName(String paramString);
/*     */   
/*     */   protected abstract boolean isFault(SOAPElement paramSOAPElement);
/*     */   
/*     */   protected abstract SOAPBodyElement createBodyElement(Name paramName);
/*     */   
/*     */   public SOAPFault addFault() throws SOAPException {
/*  79 */     if (hasFault()) {
/*  80 */       log.severe("SAAJ0110.impl.fault.already.exists");
/*  81 */       throw new SOAPExceptionImpl("Error: Fault already exists");
/*     */     } 
/*     */     
/*  84 */     this.fault = createFaultElement();
/*     */     
/*  86 */     addNode(this.fault);
/*     */     
/*  88 */     this.fault.setFaultCode(getDefaultFaultCode());
/*  89 */     this.fault.setFaultString("Fault string, and possibly fault code, not set");
/*     */     
/*  91 */     return this.fault;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract SOAPBodyElement createBodyElement(QName paramQName);
/*     */ 
/*     */   
/*     */   public SOAPFault addFault(Name faultCode, String faultString, Locale locale) throws SOAPException {
/* 100 */     SOAPFault fault = addFault();
/* 101 */     fault.setFaultCode(faultCode);
/* 102 */     fault.setFaultString(faultString, locale);
/* 103 */     return fault;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract SOAPFault createFaultElement();
/*     */   
/*     */   protected abstract QName getDefaultFaultCode();
/*     */   
/*     */   public SOAPFault addFault(QName faultCode, String faultString, Locale locale) throws SOAPException {
/* 112 */     SOAPFault fault = addFault();
/* 113 */     fault.setFaultCode(faultCode);
/* 114 */     fault.setFaultString(faultString, locale);
/* 115 */     return fault;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPFault addFault(Name faultCode, String faultString) throws SOAPException {
/* 121 */     SOAPFault fault = addFault();
/* 122 */     fault.setFaultCode(faultCode);
/* 123 */     fault.setFaultString(faultString);
/* 124 */     return fault;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPFault addFault(QName faultCode, String faultString) throws SOAPException {
/* 130 */     SOAPFault fault = addFault();
/* 131 */     fault.setFaultCode(faultCode);
/* 132 */     fault.setFaultString(faultString);
/* 133 */     return fault;
/*     */   }
/*     */   
/*     */   void initializeFault() {
/* 137 */     FaultImpl flt = (FaultImpl)findFault();
/* 138 */     this.fault = flt;
/*     */   }
/*     */   
/*     */   protected SOAPElement findFault() {
/* 142 */     Iterator<SOAPElement> eachChild = getChildElementNodes();
/* 143 */     while (eachChild.hasNext()) {
/* 144 */       SOAPElement child = eachChild.next();
/* 145 */       if (isFault(child)) {
/* 146 */         return child;
/*     */       }
/*     */     } 
/*     */     
/* 150 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasFault() {
/* 154 */     initializeFault();
/* 155 */     return (this.fault != null);
/*     */   }
/*     */   
/*     */   public SOAPFault getFault() {
/* 159 */     if (hasFault())
/* 160 */       return this.fault; 
/* 161 */     return null;
/*     */   }
/*     */   
/*     */   public SOAPBodyElement addBodyElement(Name name) throws SOAPException {
/* 165 */     SOAPBodyElement newBodyElement = (SOAPBodyElement)ElementFactory.createNamedElement(((SOAPDocument)getOwnerDocument()).getDocument(), name.getLocalName(), name.getPrefix(), name.getURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     if (newBodyElement == null) {
/* 172 */       newBodyElement = createBodyElement(name);
/*     */     }
/* 174 */     addNode(newBodyElement);
/* 175 */     return newBodyElement;
/*     */   }
/*     */   
/*     */   public SOAPBodyElement addBodyElement(QName qname) throws SOAPException {
/* 179 */     SOAPBodyElement newBodyElement = (SOAPBodyElement)ElementFactory.createNamedElement(((SOAPDocument)getOwnerDocument()).getDocument(), qname.getLocalPart(), qname.getPrefix(), qname.getNamespaceURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     if (newBodyElement == null) {
/* 186 */       newBodyElement = createBodyElement(qname);
/*     */     }
/* 188 */     addNode(newBodyElement);
/* 189 */     return newBodyElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParentElement(SOAPElement element) throws SOAPException {
/* 194 */     if (!(element instanceof javax.xml.soap.SOAPEnvelope)) {
/* 195 */       log.severe("SAAJ0111.impl.body.parent.must.be.envelope");
/* 196 */       throw new SOAPException("Parent of SOAPBody has to be a SOAPEnvelope");
/*     */     } 
/* 198 */     super.setParentElement(element);
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(Name name) throws SOAPException {
/* 202 */     return addBodyElement(name);
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(QName name) throws SOAPException {
/* 206 */     return addBodyElement(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPBodyElement addDocument(Document document) throws SOAPException {
/* 237 */     SOAPBodyElement newBodyElement = null;
/* 238 */     DocumentFragment docFrag = document.createDocumentFragment();
/* 239 */     Element rootElement = document.getDocumentElement();
/* 240 */     if (rootElement != null) {
/* 241 */       docFrag.appendChild(rootElement);
/*     */       
/* 243 */       Document ownerDoc = getOwnerDocument();
/*     */ 
/*     */       
/* 246 */       Node replacingNode = ownerDoc.importNode(docFrag, true);
/*     */       
/* 248 */       addNode(replacingNode);
/* 249 */       Iterator<SOAPBodyElement> i = getChildElements(NameImpl.copyElementName(rootElement));
/*     */ 
/*     */ 
/*     */       
/* 253 */       while (i.hasNext())
/* 254 */         newBodyElement = i.next(); 
/*     */     } 
/* 256 */     return newBodyElement;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SOAPElement convertToSoapElement(Element element) {
/* 261 */     if (element instanceof SOAPBodyElement && !element.getClass().equals(ElementImpl.class))
/*     */     {
/*     */ 
/*     */       
/* 265 */       return (SOAPElement)element;
/*     */     }
/* 267 */     return replaceElementWithSOAPElement(element, (ElementImpl)createBodyElement(NameImpl.copyElementName(element)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 275 */     log.log(Level.SEVERE, "SAAJ0146.impl.invalid.name.change.requested", new Object[] { this.elementQName.getLocalPart(), newName.getLocalPart() });
/*     */ 
/*     */ 
/*     */     
/* 279 */     throw new SOAPException("Cannot change name for " + this.elementQName.getLocalPart() + " to " + newName.getLocalPart());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document extractContentAsDocument() throws SOAPException {
/* 286 */     Iterator<Node> eachChild = getChildElements();
/* 287 */     Node firstBodyElement = null;
/*     */     
/* 289 */     while (eachChild.hasNext() && !(firstBodyElement instanceof SOAPElement))
/*     */     {
/* 291 */       firstBodyElement = eachChild.next();
/*     */     }
/* 293 */     boolean exactlyOneChildElement = true;
/* 294 */     if (firstBodyElement == null) {
/* 295 */       exactlyOneChildElement = false;
/*     */     } else {
/* 297 */       Node node = firstBodyElement.getNextSibling();
/* 298 */       for (; node != null; 
/* 299 */         node = node.getNextSibling()) {
/*     */         
/* 301 */         if (node instanceof Element) {
/* 302 */           exactlyOneChildElement = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 308 */     if (!exactlyOneChildElement) {
/* 309 */       log.log(Level.SEVERE, "SAAJ0250.impl.body.should.have.exactly.one.child");
/*     */       
/* 311 */       throw new SOAPException("Cannot extract Document from body");
/*     */     } 
/*     */     
/* 314 */     Document document = null;
/*     */     try {
/* 316 */       DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
/*     */       
/* 318 */       factory.setNamespaceAware(true);
/* 319 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 320 */       document = builder.newDocument();
/*     */       
/* 322 */       Element rootElement = (Element)document.importNode(firstBodyElement, true);
/*     */ 
/*     */ 
/*     */       
/* 326 */       document.appendChild(rootElement);
/*     */     }
/* 328 */     catch (Exception e) {
/* 329 */       log.log(Level.SEVERE, "SAAJ0251.impl.cannot.extract.document.from.body");
/*     */       
/* 331 */       throw new SOAPExceptionImpl("Unable to extract Document from body", e);
/*     */     } 
/*     */ 
/*     */     
/* 335 */     firstBodyElement.detachNode();
/*     */     
/* 337 */     return document;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\BodyImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */