/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.wss.saml.AuthorityBinding;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorityBindingType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorityBindingImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorityBindingTypeImpl;
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
/*     */ public class AuthorityBinding
/*     */   extends AuthorityBindingImpl
/*     */   implements AuthorityBinding
/*     */ {
/*  70 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthorityBindingTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/*  85 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  87 */       Unmarshaller u = jc.createUnmarshaller();
/*  88 */       return (AuthorityBindingTypeImpl)u.unmarshal(element);
/*  89 */     } catch (Exception ex) {
/*  90 */       throw new SAMLException(ex.getMessage());
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
/* 110 */     setAuthorityKind(authKind);
/* 111 */     setLocation(location);
/* 112 */     setBinding(binding);
/*     */   }
/*     */   
/*     */   public AuthorityBinding(AuthorityBindingType authBinType) {
/* 116 */     setAuthorityKind(authBinType.getAuthorityKind());
/* 117 */     setLocation(authBinType.getLocation());
/* 118 */     setBinding(authBinType.getBinding());
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getAuthorityKind() {
/* 123 */     return super.getAuthorityKind();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBinding() {
/* 128 */     return super.getBinding();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocation() {
/* 133 */     return super.getLocation();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\AuthorityBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */