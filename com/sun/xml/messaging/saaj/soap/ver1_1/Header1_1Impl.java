/*     */ package com.sun.xml.messaging.saaj.soap.ver1_1;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocument;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.HeaderImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeaderElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Header1_1Impl
/*     */   extends HeaderImpl
/*     */ {
/*  63 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.ver1_1", "com.sun.xml.messaging.saaj.soap.ver1_1.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public Header1_1Impl(SOAPDocumentImpl ownerDocument, String prefix) {
/*  68 */     super(ownerDocument, NameImpl.createHeader1_1Name(prefix));
/*     */   }
/*     */   
/*     */   protected NameImpl getNotUnderstoodName() {
/*  72 */     log.log(Level.SEVERE, "SAAJ0301.ver1_1.hdr.op.unsupported.in.SOAP1.1", (Object[])new String[] { "getNotUnderstoodName" });
/*     */ 
/*     */ 
/*     */     
/*  76 */     throw new UnsupportedOperationException("Not supported by SOAP 1.1");
/*     */   }
/*     */   
/*     */   protected NameImpl getUpgradeName() {
/*  80 */     return NameImpl.create("Upgrade", getPrefix(), "http://schemas.xmlsoap.org/soap/envelope/");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NameImpl getSupportedEnvelopeName() {
/*  87 */     return NameImpl.create("SupportedEnvelope", getPrefix(), "http://schemas.xmlsoap.org/soap/envelope/");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPHeaderElement addNotUnderstoodHeaderElement(QName name) throws SOAPException {
/*  95 */     log.log(Level.SEVERE, "SAAJ0301.ver1_1.hdr.op.unsupported.in.SOAP1.1", (Object[])new String[] { "addNotUnderstoodHeaderElement" });
/*     */ 
/*     */ 
/*     */     
/*  99 */     throw new UnsupportedOperationException("Not supported by SOAP 1.1");
/*     */   }
/*     */   
/*     */   protected SOAPHeaderElement createHeaderElement(Name name) {
/* 103 */     return (SOAPHeaderElement)new HeaderElement1_1Impl(((SOAPDocument)getOwnerDocument()).getDocument(), name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SOAPHeaderElement createHeaderElement(QName name) {
/* 108 */     return (SOAPHeaderElement)new HeaderElement1_1Impl(((SOAPDocument)getOwnerDocument()).getDocument(), name);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_1\Header1_1Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */