/*     */ package com.sun.xml.ws.security.soap12;
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
/*  74 */   private static final QName _Upgrade_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Upgrade");
/*  75 */   private static final QName _NotUnderstood_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "NotUnderstood");
/*  76 */   private static final QName _Body_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Body");
/*  77 */   private static final QName _Header_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Header");
/*  78 */   private static final QName _Envelope_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Envelope");
/*  79 */   private static final QName _Fault_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Fault");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Faultcode createFaultcode() {
/*  93 */     return new Faultcode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Envelope createEnvelope() {
/* 101 */     return new Envelope();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Faultreason createFaultreason() {
/* 109 */     return new Faultreason();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SupportedEnvType createSupportedEnvType() {
/* 117 */     return new SupportedEnvType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NotUnderstoodType createNotUnderstoodType() {
/* 125 */     return new NotUnderstoodType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Body createBody() {
/* 133 */     return new Body();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpgradeType createUpgradeType() {
/* 141 */     return new UpgradeType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Header createHeader() {
/* 149 */     return new Header();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fault createFault() {
/* 157 */     return new Fault();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Detail createDetail() {
/* 165 */     return new Detail();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reasontext createReasontext() {
/* 173 */     return new Reasontext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Subcode createSubcode() {
/* 181 */     return new Subcode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2003/05/soap-envelope", name = "Upgrade")
/*     */   public JAXBElement<UpgradeType> createUpgrade(UpgradeType value) {
/* 190 */     return new JAXBElement<UpgradeType>(_Upgrade_QNAME, UpgradeType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2003/05/soap-envelope", name = "NotUnderstood")
/*     */   public JAXBElement<NotUnderstoodType> createNotUnderstood(NotUnderstoodType value) {
/* 199 */     return new JAXBElement<NotUnderstoodType>(_NotUnderstood_QNAME, NotUnderstoodType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2003/05/soap-envelope", name = "Body")
/*     */   public JAXBElement<Body> createBody(Body value) {
/* 208 */     return new JAXBElement<Body>(_Body_QNAME, Body.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2003/05/soap-envelope", name = "Header")
/*     */   public JAXBElement<Header> createHeader(Header value) {
/* 217 */     return new JAXBElement<Header>(_Header_QNAME, Header.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2003/05/soap-envelope", name = "Envelope")
/*     */   public JAXBElement<Envelope> createEnvelope(Envelope value) {
/* 226 */     return new JAXBElement<Envelope>(_Envelope_QNAME, Envelope.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2003/05/soap-envelope", name = "Fault")
/*     */   public JAXBElement<Fault> createFault(Fault value) {
/* 235 */     return new JAXBElement<Fault>(_Fault_QNAME, Fault.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\soap12\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */