/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secconv.impl.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secconv.impl.bindings.SecurityContextTokenType;
/*     */ import com.sun.xml.ws.security.secconv.impl.elements.SecurityContextTokenImpl;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.impl.WSTrustElementFactoryImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestedSecurityTokenType;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ public class RequestedSecurityTokenImpl
/*     */   extends RequestedSecurityTokenType
/*     */   implements RequestedSecurityToken
/*     */ {
/*  80 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   Token containedToken = null;
/*     */   
/*  87 */   private static final QName SCT_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "SecurityContextToken");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedSecurityTokenImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedSecurityTokenImpl(@NotNull RequestedSecurityTokenType rdstType) {
/* 103 */     Object rdst = rdstType.getAny();
/* 104 */     if (rdst instanceof JAXBElement) {
/* 105 */       JAXBElement<SecurityContextTokenType> rdstEle = (JAXBElement)rdst;
/* 106 */       QName name = rdstEle.getName();
/* 107 */       if (SCT_QNAME.equals(name)) {
/* 108 */         SecurityContextTokenType sctType = rdstEle.getValue();
/* 109 */         setToken((Token)new SecurityContextTokenImpl(sctType));
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 118 */         setAny(rdstEle);
/* 119 */         Element token = (new WSTrustElementFactoryImpl()).toElement(rdstEle);
/* 120 */         this.containedToken = (Token)new GenericToken(token);
/*     */       } 
/*     */     } else {
/*     */       
/* 124 */       setToken((Token)new GenericToken((Element)rdst));
/*     */     } 
/*     */   }
/*     */   
/*     */   public RequestedSecurityTokenImpl(Token token) {
/* 129 */     setToken(token);
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
/*     */   public static RequestedSecurityTokenType fromElement(@NotNull Element element) throws WSTrustException {
/*     */     try {
/* 146 */       JAXBContext context = WSTrustElementFactory.getContext();
/*     */       
/* 148 */       Unmarshaller unmarshaller = context.createUnmarshaller();
/*     */       
/* 150 */       return unmarshaller.<RequestedSecurityTokenType>unmarshal(element, RequestedSecurityTokenType.class).getValue();
/* 151 */     } catch (JAXBException ex) {
/* 152 */       log.log(Level.SEVERE, LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT(), ex);
/*     */       
/* 154 */       throw new WSTrustException(LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Token getToken() {
/* 162 */     return this.containedToken;
/*     */   }
/*     */   
/*     */   public final void setToken(Token token) {
/* 166 */     if (token != null) {
/* 167 */       String tokenType = token.getType();
/* 168 */       if ("SecurityContextToken".equals(tokenType)) {
/* 169 */         JAXBElement<SecurityContextTokenType> sctElement = (new ObjectFactory()).createSecurityContextToken((SecurityContextTokenType)token);
/*     */         
/* 171 */         setAny(sctElement);
/*     */       } else {
/* 173 */         Element element = (Element)token.getTokenValue();
/* 174 */         setAny(element);
/*     */       } 
/*     */     } 
/* 177 */     this.containedToken = token;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\RequestedSecurityTokenImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */