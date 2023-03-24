/*     */ package com.sun.xml.messaging.saaj.soap.ver1_2;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.EnvelopeImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Envelope1_2Impl
/*     */   extends EnvelopeImpl
/*     */ {
/*  60 */   protected static final Logger log = Logger.getLogger(Envelope1_2Impl.class.getName(), "com.sun.xml.messaging.saaj.soap.ver1_2.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public Envelope1_2Impl(SOAPDocumentImpl ownerDoc, String prefix) {
/*  65 */     super(ownerDoc, (Name)NameImpl.createEnvelope1_2Name(prefix));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Envelope1_2Impl(SOAPDocumentImpl ownerDoc, String prefix, boolean createHeader, boolean createBody) throws SOAPException {
/*  74 */     super(ownerDoc, NameImpl.createEnvelope1_2Name(prefix), createHeader, createBody);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NameImpl getBodyName(String prefix) {
/*  82 */     return NameImpl.createBody1_2Name(prefix);
/*     */   }
/*     */   
/*     */   protected NameImpl getHeaderName(String prefix) {
/*  86 */     return NameImpl.createHeader1_2Name(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncodingStyle(String encodingStyle) throws SOAPException {
/*  94 */     log.severe("SAAJ0404.ver1_2.no.encodingStyle.in.envelope");
/*  95 */     throw new SOAPExceptionImpl("encodingStyle attribute cannot appear on Envelope");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
/* 104 */     if (name.getLocalName().equals("encodingStyle") && name.getURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/* 106 */       setEncodingStyle(value);
/*     */     }
/* 108 */     return super.addAttribute(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(QName name, String value) throws SOAPException {
/* 113 */     if (name.getLocalPart().equals("encodingStyle") && name.getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/* 115 */       setEncodingStyle(value);
/*     */     }
/* 117 */     return super.addAttribute(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement addChildElement(Name name) throws SOAPException {
/* 127 */     if (getBody() != null) {
/* 128 */       log.severe("SAAJ0405.ver1_2.body.must.last.in.envelope");
/* 129 */       throw new SOAPExceptionImpl("Body must be the last element in SOAP Envelope");
/*     */     } 
/*     */     
/* 132 */     return super.addChildElement(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addChildElement(QName name) throws SOAPException {
/* 137 */     if (getBody() != null) {
/* 138 */       log.severe("SAAJ0405.ver1_2.body.must.last.in.envelope");
/* 139 */       throw new SOAPExceptionImpl("Body must be the last element in SOAP Envelope");
/*     */     } 
/*     */     
/* 142 */     return super.addChildElement(name);
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
/*     */   public SOAPElement addTextNode(String text) throws SOAPException {
/* 156 */     log.log(Level.SEVERE, "SAAJ0416.ver1_2.adding.text.not.legal", getElementQName());
/*     */ 
/*     */ 
/*     */     
/* 160 */     throw new SOAPExceptionImpl("Adding text to SOAP 1.2 Envelope is not legal");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_2\Envelope1_2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */