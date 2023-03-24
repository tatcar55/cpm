/*     */ package com.sun.xml.security.core.ai;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  74 */   private static final QName _Identity_QNAME = new QName("http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", "Identity");
/*  75 */   private static final QName _Upn_QNAME = new QName("http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", "Upn");
/*  76 */   private static final QName _Dns_QNAME = new QName("http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", "Dns");
/*  77 */   private static final QName _Spn_QNAME = new QName("http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", "Spn");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IdentityType createIdentityType() {
/*  91 */     return new IdentityType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", name = "Identity")
/*     */   public JAXBElement<IdentityType> createIdentity(IdentityType value) {
/* 100 */     return new JAXBElement<IdentityType>(_Identity_QNAME, IdentityType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", name = "Upn")
/*     */   public JAXBElement<String> createUpn(String value) {
/* 109 */     return new JAXBElement<String>(_Upn_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", name = "Dns")
/*     */   public JAXBElement<String> createDns(String value) {
/* 118 */     return new JAXBElement<String>(_Dns_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", name = "Spn")
/*     */   public JAXBElement<String> createSpn(String value) {
/* 127 */     return new JAXBElement<String>(_Spn_QNAME, String.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\security\core\ai\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */