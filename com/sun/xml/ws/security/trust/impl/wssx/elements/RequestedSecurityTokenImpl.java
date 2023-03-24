/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secconv.impl.wssx.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secconv.impl.wssx.bindings.SecurityContextTokenType;
/*     */ import com.sun.xml.ws.security.secconv.impl.wssx.elements.SecurityContextTokenImpl;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.WSTrustElementFactoryImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestedSecurityTokenType;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RequestedSecurityTokenImpl
/*     */   extends RequestedSecurityTokenType
/*     */   implements RequestedSecurityToken
/*     */ {
/*  79 */   Token containedToken = null;
/*     */   
/*  81 */   private static final QName SecurityContextToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "SecurityContextToken");
/*     */ 
/*     */   
/*  84 */   private static final QName SAML11_Assertion_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
/*     */ 
/*     */   
/*  87 */   private static final QName EncryptedData_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedData");
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedSecurityTokenImpl() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedSecurityTokenImpl(RequestedSecurityTokenType rdstType) {
/*  96 */     Object rdst = rdstType.getAny();
/*  97 */     if (rdst instanceof JAXBElement) {
/*  98 */       JAXBElement<SecurityContextTokenType> rdstEle = (JAXBElement)rdst;
/*  99 */       QName name = rdstEle.getName();
/* 100 */       if (SecurityContextToken_QNAME.equals(name)) {
/* 101 */         SecurityContextTokenType sctType = rdstEle.getValue();
/* 102 */         setToken((Token)new SecurityContextTokenImpl(sctType));
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 111 */         setAny(rdstEle);
/* 112 */         Element token = (new WSTrustElementFactoryImpl()).toElement(rdstEle);
/* 113 */         this.containedToken = (Token)new GenericToken(token);
/*     */       } 
/*     */     } else {
/*     */       
/* 117 */       setToken((Token)new GenericToken((Element)rdst));
/*     */     } 
/*     */   }
/*     */   
/*     */   public RequestedSecurityTokenImpl(Token token) {
/* 122 */     setToken(token);
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
/*     */ 
/*     */   
/*     */   public static RequestedSecurityTokenType fromElement(Element element) throws WSTrustException {
/*     */     try {
/* 139 */       JAXBContext context = WSTrustElementFactory.getContext(WSTrustVersion.WS_TRUST_13);
/*     */       
/* 141 */       Unmarshaller unmarshaller = context.createUnmarshaller();
/*     */       
/* 143 */       return unmarshaller.<RequestedSecurityTokenType>unmarshal(element, RequestedSecurityTokenType.class).getValue();
/* 144 */     } catch (Exception ex) {
/* 145 */       throw new WSTrustException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Token getToken() {
/* 153 */     return this.containedToken;
/*     */   }
/*     */   
/*     */   public void setToken(Token token) {
/* 157 */     if (token != null) {
/* 158 */       String tokenType = token.getType();
/* 159 */       if ("SecurityContextToken".equals(tokenType)) {
/* 160 */         JAXBElement<SecurityContextTokenType> sctElement = (new ObjectFactory()).createSecurityContextToken((SecurityContextTokenType)token);
/*     */         
/* 162 */         setAny(sctElement);
/*     */       } else {
/* 164 */         Element element = (Element)token.getTokenValue();
/* 165 */         setAny(element);
/*     */       } 
/*     */     } 
/* 168 */     this.containedToken = token;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\RequestedSecurityTokenImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */