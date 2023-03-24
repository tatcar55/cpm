/*     */ package com.sun.xml.ws.security.secext11;
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
/*  74 */   private static final QName _Iteration_QNAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "Iteration");
/*  75 */   private static final QName _EncryptedHeader_QNAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "EncryptedHeader");
/*  76 */   private static final QName _SignatureConfirmation_QNAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "SignatureConfirmation");
/*  77 */   private static final QName _Salt_QNAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "Salt");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureConfirmationType createSignatureConfirmationType() {
/*  91 */     return new SignatureConfirmationType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedHeaderType createEncryptedHeaderType() {
/*  99 */     return new EncryptedHeaderType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", name = "Iteration")
/*     */   public JAXBElement<Long> createIteration(Long value) {
/* 108 */     return new JAXBElement<Long>(_Iteration_QNAME, Long.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", name = "EncryptedHeader")
/*     */   public JAXBElement<EncryptedHeaderType> createEncryptedHeader(EncryptedHeaderType value) {
/* 117 */     return new JAXBElement<EncryptedHeaderType>(_EncryptedHeader_QNAME, EncryptedHeaderType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", name = "SignatureConfirmation")
/*     */   public JAXBElement<SignatureConfirmationType> createSignatureConfirmation(SignatureConfirmationType value) {
/* 126 */     return new JAXBElement<SignatureConfirmationType>(_SignatureConfirmation_QNAME, SignatureConfirmationType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", name = "Salt")
/*     */   public JAXBElement<byte[]> createSalt(byte[] value) {
/* 135 */     return (JAXBElement)new JAXBElement<byte>(_Salt_QNAME, (Class)byte[].class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secext11\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */