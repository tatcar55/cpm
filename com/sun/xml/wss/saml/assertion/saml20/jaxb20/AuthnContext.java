/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.AuthnContext;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AuthnContextType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.ObjectFactory;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ public class AuthnContext
/*     */   extends AuthnContextType
/*     */   implements AuthnContext
/*     */ {
/*  69 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthnContextType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  84 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/*  86 */       Unmarshaller u = jc.createUnmarshaller();
/*  87 */       return (AuthnContextType)u.unmarshal(element);
/*  88 */     } catch (Exception ex) {
/*  89 */       throw new SAMLException(ex.getMessage());
/*     */     } 
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
/*     */   public AuthnContext() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthnContext(String authContextClassref, String authenticatingAuthority) {
/* 115 */     ObjectFactory factory = new ObjectFactory();
/* 116 */     if (authContextClassref != null) {
/* 117 */       getContent().add(factory.createAuthnContextClassRef(authContextClassref));
/*     */     }
/* 119 */     if (authenticatingAuthority != null)
/* 120 */       getContent().add(factory.createAuthenticatingAuthority(authenticatingAuthority)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\AuthnContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */