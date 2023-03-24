/*     */ package com.sun.xml.ws.security.secconv.impl.bindings;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  78 */   private static final QName _Instance_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Instance");
/*  79 */   private static final QName _DerivedKeyToken_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "DerivedKeyToken");
/*  80 */   private static final QName _Identifier_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Identifier");
/*  81 */   private static final QName _Nonce_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Nonce");
/*  82 */   private static final QName _Name_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Name");
/*  83 */   private static final QName _SecurityContextToken_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "SecurityContextToken");
/*  84 */   private static final QName _Label_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Label");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityContextTokenType createSecurityContextTokenType() {
/*  98 */     return new SecurityContextTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivedKeyTokenType createDerivedKeyTokenType() {
/* 106 */     return new DerivedKeyTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertiesType createPropertiesType() {
/* 114 */     return new PropertiesType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/sc", name = "Instance")
/*     */   public JAXBElement<String> createInstance(String value) {
/* 123 */     return new JAXBElement<String>(_Instance_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/sc", name = "DerivedKeyToken")
/*     */   public JAXBElement<DerivedKeyTokenType> createDerivedKeyToken(DerivedKeyTokenType value) {
/* 132 */     return new JAXBElement<DerivedKeyTokenType>(_DerivedKeyToken_QNAME, DerivedKeyTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/sc", name = "Identifier")
/*     */   public JAXBElement<String> createIdentifier(String value) {
/* 141 */     return new JAXBElement<String>(_Identifier_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/sc", name = "Nonce")
/*     */   public JAXBElement<byte[]> createNonce(byte[] value) {
/* 150 */     return (JAXBElement)new JAXBElement<byte>(_Nonce_QNAME, (Class)byte[].class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/sc", name = "Name")
/*     */   public JAXBElement<String> createName(String value) {
/* 159 */     return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/sc", name = "SecurityContextToken")
/*     */   public JAXBElement<SecurityContextTokenType> createSecurityContextToken(SecurityContextTokenType value) {
/* 168 */     return new JAXBElement<SecurityContextTokenType>(_SecurityContextToken_QNAME, SecurityContextTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/sc", name = "Label")
/*     */   public JAXBElement<String> createLabel(String value) {
/* 177 */     return new JAXBElement<String>(_Label_QNAME, String.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\impl\bindings\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */