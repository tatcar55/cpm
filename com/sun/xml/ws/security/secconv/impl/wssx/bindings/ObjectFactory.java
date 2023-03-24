/*     */ package com.sun.xml.ws.security.secconv.impl.wssx.bindings;
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
/*  74 */   private static final QName _Nonce_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Nonce");
/*  75 */   private static final QName _Identifier_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Identifier");
/*  76 */   private static final QName _DerivedKeyToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "DerivedKeyToken");
/*  77 */   private static final QName _Name_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Name");
/*  78 */   private static final QName _SecurityContextToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "SecurityContextToken");
/*  79 */   private static final QName _Label_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Label");
/*  80 */   private static final QName _Instance_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Instance");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertiesType createPropertiesType() {
/*  94 */     return new PropertiesType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivedKeyTokenType createDerivedKeyTokenType() {
/* 102 */     return new DerivedKeyTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityContextTokenType createSecurityContextTokenType() {
/* 110 */     return new SecurityContextTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name = "Nonce")
/*     */   public JAXBElement<byte[]> createNonce(byte[] value) {
/* 119 */     return (JAXBElement)new JAXBElement<byte>(_Nonce_QNAME, (Class)byte[].class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name = "Identifier")
/*     */   public JAXBElement<String> createIdentifier(String value) {
/* 128 */     return new JAXBElement<String>(_Identifier_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name = "DerivedKeyToken")
/*     */   public JAXBElement<DerivedKeyTokenType> createDerivedKeyToken(DerivedKeyTokenType value) {
/* 137 */     return new JAXBElement<DerivedKeyTokenType>(_DerivedKeyToken_QNAME, DerivedKeyTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name = "Name")
/*     */   public JAXBElement<String> createName(String value) {
/* 146 */     return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name = "SecurityContextToken")
/*     */   public JAXBElement<SecurityContextTokenType> createSecurityContextToken(SecurityContextTokenType value) {
/* 155 */     return new JAXBElement<SecurityContextTokenType>(_SecurityContextToken_QNAME, SecurityContextTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name = "Label")
/*     */   public JAXBElement<String> createLabel(String value) {
/* 164 */     return new JAXBElement<String>(_Label_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name = "Instance")
/*     */   public JAXBElement<String> createInstance(String value) {
/* 173 */     return new JAXBElement<String>(_Instance_QNAME, String.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\impl\wssx\bindings\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */