/*     */ package com.sun.xml.messaging.saaj.soap.ver1_2;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.ElementImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.FaultElementImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
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
/*     */ 
/*     */ public class FaultElement1_2Impl
/*     */   extends FaultElementImpl
/*     */ {
/*     */   public FaultElement1_2Impl(SOAPDocumentImpl ownerDoc, NameImpl qname) {
/*  60 */     super(ownerDoc, qname);
/*     */   }
/*     */   
/*     */   public FaultElement1_2Impl(SOAPDocumentImpl ownerDoc, QName qname) {
/*  64 */     super(ownerDoc, qname);
/*     */   }
/*     */   
/*     */   public FaultElement1_2Impl(SOAPDocumentImpl ownerDoc, String localName) {
/*  68 */     super(ownerDoc, NameImpl.createSOAP12Name(localName));
/*     */   }
/*     */   
/*     */   protected boolean isStandardFaultElement() {
/*  72 */     String localName = this.elementQName.getLocalPart();
/*  73 */     if (localName.equalsIgnoreCase("code") || localName.equalsIgnoreCase("reason") || localName.equalsIgnoreCase("node") || localName.equalsIgnoreCase("role"))
/*     */     {
/*     */ 
/*     */       
/*  77 */       return true;
/*     */     }
/*  79 */     return false;
/*     */   }
/*     */   
/*     */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/*  83 */     if (!isStandardFaultElement()) {
/*  84 */       FaultElement1_2Impl copy = new FaultElement1_2Impl((SOAPDocumentImpl)getOwnerDocument(), newName);
/*     */       
/*  86 */       return replaceElementWithSOAPElement((Element)this, (ElementImpl)copy);
/*     */     } 
/*  88 */     return super.setElementQName(newName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEncodingStyle(String encodingStyle) throws SOAPException {
/*  93 */     log.severe("SAAJ0408.ver1_2.no.encodingStyle.in.fault.child");
/*  94 */     throw new SOAPExceptionImpl("encodingStyle attribute cannot appear on a Fault child element");
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
/*  99 */     if (name.getLocalName().equals("encodingStyle") && name.getURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/* 101 */       setEncodingStyle(value);
/*     */     }
/* 103 */     return super.addAttribute(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(QName name, String value) throws SOAPException {
/* 108 */     if (name.getLocalPart().equals("encodingStyle") && name.getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/* 110 */       setEncodingStyle(value);
/*     */     }
/* 112 */     return super.addAttribute(name, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_2\FaultElement1_2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */