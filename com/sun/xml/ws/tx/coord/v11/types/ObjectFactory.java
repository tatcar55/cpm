/*     */ package com.sun.xml.ws.tx.coord.v11.types;
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
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  66 */   private static final QName _Register_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "Register");
/*  67 */   private static final QName _RegisterResponse_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegisterResponse");
/*  68 */   private static final QName _CreateCoordinationContext_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "CreateCoordinationContext");
/*  69 */   private static final QName _CreateCoordinationContextResponse_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "CreateCoordinationContextResponse");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoordinationContextType createCoordinationContextType() {
/*  83 */     return new CoordinationContextType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreateCoordinationContextType createCreateCoordinationContextType() {
/*  91 */     return new CreateCoordinationContextType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Expires createExpires() {
/*  99 */     return new Expires();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegisterType createRegisterType() {
/* 107 */     return new RegisterType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegisterResponseType createRegisterResponseType() {
/* 115 */     return new RegisterResponseType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreateCoordinationContextResponseType createCreateCoordinationContextResponseType() {
/* 123 */     return new CreateCoordinationContextResponseType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoordinationContext createCoordinationContext() {
/* 131 */     return new CoordinationContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoordinationContextType.Identifier createCoordinationContextTypeIdentifier() {
/* 139 */     return new CoordinationContextType.Identifier();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreateCoordinationContextType.CurrentContext createCreateCoordinationContextTypeCurrentContext() {
/* 147 */     return new CreateCoordinationContextType.CurrentContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", name = "Register")
/*     */   public JAXBElement<RegisterType> createRegister(RegisterType value) {
/* 156 */     return new JAXBElement<RegisterType>(_Register_QNAME, RegisterType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", name = "RegisterResponse")
/*     */   public JAXBElement<RegisterResponseType> createRegisterResponse(RegisterResponseType value) {
/* 165 */     return new JAXBElement<RegisterResponseType>(_RegisterResponse_QNAME, RegisterResponseType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", name = "CreateCoordinationContext")
/*     */   public JAXBElement<CreateCoordinationContextType> createCreateCoordinationContext(CreateCoordinationContextType value) {
/* 174 */     return new JAXBElement<CreateCoordinationContextType>(_CreateCoordinationContext_QNAME, CreateCoordinationContextType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", name = "CreateCoordinationContextResponse")
/*     */   public JAXBElement<CreateCoordinationContextResponseType> createCreateCoordinationContextResponse(CreateCoordinationContextResponseType value) {
/* 183 */     return new JAXBElement<CreateCoordinationContextResponseType>(_CreateCoordinationContextResponse_QNAME, CreateCoordinationContextResponseType.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\types\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */