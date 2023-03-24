/*     */ package com.sun.xml.messaging.saaj.soap.ver1_2;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.ElementImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.HeaderElementImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
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
/*     */ public class HeaderElement1_2Impl
/*     */   extends HeaderElementImpl
/*     */ {
/*  60 */   private static final Logger log = Logger.getLogger(HeaderElement1_2Impl.class.getName(), "com.sun.xml.messaging.saaj.soap.ver1_2.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderElement1_2Impl(SOAPDocumentImpl ownerDoc, Name qname) {
/*  65 */     super(ownerDoc, qname);
/*     */   }
/*     */   public HeaderElement1_2Impl(SOAPDocumentImpl ownerDoc, QName qname) {
/*  68 */     super(ownerDoc, qname);
/*     */   }
/*     */   
/*     */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/*  72 */     HeaderElementImpl copy = new HeaderElement1_2Impl((SOAPDocumentImpl)getOwnerDocument(), newName);
/*     */     
/*  74 */     return replaceElementWithSOAPElement((Element)this, (ElementImpl)copy);
/*     */   }
/*     */   
/*     */   protected NameImpl getRoleAttributeName() {
/*  78 */     return NameImpl.create("role", null, "http://www.w3.org/2003/05/soap-envelope");
/*     */   }
/*     */ 
/*     */   
/*     */   protected NameImpl getActorAttributeName() {
/*  83 */     return getRoleAttributeName();
/*     */   }
/*     */   
/*     */   protected NameImpl getMustunderstandAttributeName() {
/*  87 */     return NameImpl.create("mustUnderstand", null, "http://www.w3.org/2003/05/soap-envelope");
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getMustunderstandLiteralValue(boolean mustUnderstand) {
/*  92 */     return (mustUnderstand == true) ? "true" : "false";
/*     */   }
/*     */   
/*     */   protected boolean getMustunderstandAttributeValue(String mu) {
/*  96 */     if (mu.equals("true") || mu.equals("1"))
/*  97 */       return true; 
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   protected NameImpl getRelayAttributeName() {
/* 102 */     return NameImpl.create("relay", null, "http://www.w3.org/2003/05/soap-envelope");
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getRelayLiteralValue(boolean relay) {
/* 107 */     return (relay == true) ? "true" : "false";
/*     */   }
/*     */   
/*     */   protected boolean getRelayAttributeValue(String relay) {
/* 111 */     if (relay.equals("true") || relay.equals("1"))
/* 112 */       return true; 
/* 113 */     return false;
/*     */   }
/*     */   
/*     */   protected String getActorOrRole() {
/* 117 */     return getRole();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_2\HeaderElement1_2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */