/*     */ package com.sun.xml.messaging.saaj.soap.ver1_1;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.ElementImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.HeaderElementImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ public class HeaderElement1_1Impl
/*     */   extends HeaderElementImpl
/*     */ {
/*  62 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.ver1_1", "com.sun.xml.messaging.saaj.soap.ver1_1.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderElement1_1Impl(SOAPDocumentImpl ownerDoc, Name qname) {
/*  67 */     super(ownerDoc, qname);
/*     */   }
/*     */   public HeaderElement1_1Impl(SOAPDocumentImpl ownerDoc, QName qname) {
/*  70 */     super(ownerDoc, qname);
/*     */   }
/*     */   
/*     */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/*  74 */     HeaderElementImpl copy = new HeaderElement1_1Impl((SOAPDocumentImpl)getOwnerDocument(), newName);
/*     */     
/*  76 */     return replaceElementWithSOAPElement((Element)this, (ElementImpl)copy);
/*     */   }
/*     */   
/*     */   protected NameImpl getActorAttributeName() {
/*  80 */     return NameImpl.create("actor", null, "http://schemas.xmlsoap.org/soap/envelope/");
/*     */   }
/*     */ 
/*     */   
/*     */   protected NameImpl getRoleAttributeName() {
/*  85 */     log.log(Level.SEVERE, "SAAJ0302.ver1_1.hdr.attr.unsupported.in.SOAP1.1", (Object[])new String[] { "Role" });
/*     */ 
/*     */ 
/*     */     
/*  89 */     throw new UnsupportedOperationException("Role not supported by SOAP 1.1");
/*     */   }
/*     */   
/*     */   protected NameImpl getMustunderstandAttributeName() {
/*  93 */     return NameImpl.create("mustUnderstand", null, "http://schemas.xmlsoap.org/soap/envelope/");
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getMustunderstandLiteralValue(boolean mustUnderstand) {
/*  98 */     return (mustUnderstand == true) ? "1" : "0";
/*     */   }
/*     */   
/*     */   protected boolean getMustunderstandAttributeValue(String mu) {
/* 102 */     if ("1".equals(mu) || "true".equalsIgnoreCase(mu))
/* 103 */       return true; 
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NameImpl getRelayAttributeName() {
/* 109 */     log.log(Level.SEVERE, "SAAJ0302.ver1_1.hdr.attr.unsupported.in.SOAP1.1", (Object[])new String[] { "Relay" });
/*     */ 
/*     */ 
/*     */     
/* 113 */     throw new UnsupportedOperationException("Relay not supported by SOAP 1.1");
/*     */   }
/*     */   
/*     */   protected String getRelayLiteralValue(boolean relayAttr) {
/* 117 */     log.log(Level.SEVERE, "SAAJ0302.ver1_1.hdr.attr.unsupported.in.SOAP1.1", (Object[])new String[] { "Relay" });
/*     */ 
/*     */ 
/*     */     
/* 121 */     throw new UnsupportedOperationException("Relay not supported by SOAP 1.1");
/*     */   }
/*     */   
/*     */   protected boolean getRelayAttributeValue(String mu) {
/* 125 */     log.log(Level.SEVERE, "SAAJ0302.ver1_1.hdr.attr.unsupported.in.SOAP1.1", (Object[])new String[] { "Relay" });
/*     */ 
/*     */ 
/*     */     
/* 129 */     throw new UnsupportedOperationException("Relay not supported by SOAP 1.1");
/*     */   }
/*     */   
/*     */   protected String getActorOrRole() {
/* 133 */     return getActor();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_1\HeaderElement1_1Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */