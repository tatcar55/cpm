/*     */ package com.sun.xml.messaging.saaj.soap.ver1_2;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocument;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.BodyImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPBodyElement;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Body1_2Impl
/*     */   extends BodyImpl
/*     */ {
/*  63 */   protected static final Logger log = Logger.getLogger(Body1_2Impl.class.getName(), "com.sun.xml.messaging.saaj.soap.ver1_2.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public Body1_2Impl(SOAPDocumentImpl ownerDocument, String prefix) {
/*  68 */     super(ownerDocument, NameImpl.createBody1_2Name(prefix));
/*     */   }
/*     */   
/*     */   protected NameImpl getFaultName(String name) {
/*  72 */     return NameImpl.createFault1_2Name(name, null);
/*     */   }
/*     */   
/*     */   protected SOAPBodyElement createBodyElement(Name name) {
/*  76 */     return (SOAPBodyElement)new BodyElement1_2Impl(((SOAPDocument)getOwnerDocument()).getDocument(), name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SOAPBodyElement createBodyElement(QName name) {
/*  81 */     return (SOAPBodyElement)new BodyElement1_2Impl(((SOAPDocument)getOwnerDocument()).getDocument(), name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected QName getDefaultFaultCode() {
/*  87 */     return SOAPConstants.SOAP_RECEIVER_FAULT;
/*     */   }
/*     */   
/*     */   public SOAPFault addFault() throws SOAPException {
/*  91 */     if (hasAnyChildElement()) {
/*  92 */       log.severe("SAAJ0402.ver1_2.only.fault.allowed.in.body");
/*  93 */       throw new SOAPExceptionImpl("No other element except Fault allowed in SOAPBody");
/*     */     } 
/*     */     
/*  96 */     return super.addFault();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncodingStyle(String encodingStyle) throws SOAPException {
/* 104 */     log.severe("SAAJ0401.ver1_2.no.encodingstyle.in.body");
/* 105 */     throw new SOAPExceptionImpl("encodingStyle attribute cannot appear on Body");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
/* 114 */     if (name.getLocalName().equals("encodingStyle") && name.getURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/*     */       
/* 117 */       setEncodingStyle(value);
/*     */     }
/* 119 */     return super.addAttribute(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(QName name, String value) throws SOAPException {
/* 124 */     if (name.getLocalPart().equals("encodingStyle") && name.getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/*     */       
/* 127 */       setEncodingStyle(value);
/*     */     }
/* 129 */     return super.addAttribute(name, value);
/*     */   }
/*     */   
/*     */   protected boolean isFault(SOAPElement child) {
/* 133 */     return (child.getElementName().getURI().equals("http://www.w3.org/2003/05/soap-envelope") && child.getElementName().getLocalName().equals("Fault"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SOAPFault createFaultElement() {
/* 140 */     return (SOAPFault)new Fault1_2Impl(((SOAPDocument)getOwnerDocument()).getDocument(), getPrefix());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPBodyElement addBodyElement(Name name) throws SOAPException {
/* 150 */     if (hasFault()) {
/* 151 */       log.severe("SAAJ0402.ver1_2.only.fault.allowed.in.body");
/* 152 */       throw new SOAPExceptionImpl("No other element except Fault allowed in SOAPBody");
/*     */     } 
/*     */     
/* 155 */     return super.addBodyElement(name);
/*     */   }
/*     */   
/*     */   public SOAPBodyElement addBodyElement(QName name) throws SOAPException {
/* 159 */     if (hasFault()) {
/* 160 */       log.severe("SAAJ0402.ver1_2.only.fault.allowed.in.body");
/* 161 */       throw new SOAPExceptionImpl("No other element except Fault allowed in SOAPBody");
/*     */     } 
/*     */     
/* 164 */     return super.addBodyElement(name);
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(Name name) throws SOAPException {
/* 168 */     if (hasFault()) {
/* 169 */       log.severe("SAAJ0402.ver1_2.only.fault.allowed.in.body");
/* 170 */       throw new SOAPExceptionImpl("No other element except Fault allowed in SOAPBody");
/*     */     } 
/*     */     
/* 173 */     return super.addElement(name);
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(QName name) throws SOAPException {
/* 177 */     if (hasFault()) {
/* 178 */       log.severe("SAAJ0402.ver1_2.only.fault.allowed.in.body");
/* 179 */       throw new SOAPExceptionImpl("No other element except Fault allowed in SOAPBody");
/*     */     } 
/*     */     
/* 182 */     return super.addElement(name);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(Name name) throws SOAPException {
/* 186 */     if (hasFault()) {
/* 187 */       log.severe("SAAJ0402.ver1_2.only.fault.allowed.in.body");
/* 188 */       throw new SOAPExceptionImpl("No other element except Fault allowed in SOAPBody");
/*     */     } 
/*     */     
/* 191 */     return super.addChildElement(name);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(QName name) throws SOAPException {
/* 195 */     if (hasFault()) {
/* 196 */       log.severe("SAAJ0402.ver1_2.only.fault.allowed.in.body");
/* 197 */       throw new SOAPExceptionImpl("No other element except Fault allowed in SOAPBody");
/*     */     } 
/*     */     
/* 200 */     return super.addChildElement(name);
/*     */   }
/*     */   
/*     */   private boolean hasAnyChildElement() {
/* 204 */     Node currentNode = getFirstChild();
/* 205 */     while (currentNode != null) {
/* 206 */       if (currentNode.getNodeType() == 1)
/* 207 */         return true; 
/* 208 */       currentNode = currentNode.getNextSibling();
/*     */     } 
/* 210 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_2\Body1_2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */