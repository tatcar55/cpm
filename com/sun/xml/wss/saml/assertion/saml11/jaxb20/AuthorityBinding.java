/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.AuthorityBinding;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AuthorityBindingType;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
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
/*     */ public class AuthorityBinding
/*     */   extends AuthorityBindingType
/*     */   implements AuthorityBinding
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
/*     */   public static AuthorityBindingType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  84 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  86 */       Unmarshaller u = jc.createUnmarshaller();
/*  87 */       return (AuthorityBindingType)u.unmarshal(element);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthorityBinding(QName authKind, String location, String binding) {
/* 109 */     setAuthorityKind(authKind);
/* 110 */     setLocation(location);
/* 111 */     setBinding(binding);
/*     */   }
/*     */   
/*     */   public AuthorityBinding(AuthorityBindingType authBinType) {
/* 115 */     setAuthorityKind(authBinType.getAuthorityKind());
/* 116 */     setLocation(authBinType.getLocation());
/* 117 */     setBinding(authBinType.getBinding());
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getAuthorityKind() {
/* 122 */     return super.getAuthorityKind();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBinding() {
/* 127 */     return super.getBinding();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocation() {
/* 132 */     return super.getLocation();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\AuthorityBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */