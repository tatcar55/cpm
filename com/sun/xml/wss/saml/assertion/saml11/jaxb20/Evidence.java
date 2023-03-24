/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Evidence;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.EvidenceType;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.util.List;
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
/*     */ public class Evidence
/*     */   extends EvidenceType
/*     */   implements Evidence
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
/*     */ 
/*     */   
/*     */   public static EvidenceType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  86 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  88 */       Unmarshaller u = jc.createUnmarshaller();
/*  89 */       return (EvidenceType)u.unmarshal(element);
/*  90 */     } catch (Exception ex) {
/*  91 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAssertionIDReferenceOrAssertion(List evidence) {
/*  97 */     this.assertionIDReferenceOrAssertion = evidence;
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
/*     */   public Evidence(List assertionIDRef, List assertion) {
/* 112 */     if (assertionIDRef != null) {
/* 113 */       setAssertionIDReferenceOrAssertion(assertionIDRef);
/* 114 */     } else if (assertion != null) {
/* 115 */       setAssertionIDReferenceOrAssertion(assertion);
/*     */     } 
/*     */   }
/*     */   public Evidence(EvidenceType eveType) {
/* 119 */     setAssertionIDReferenceOrAssertion(eveType.getAssertionIDReferenceOrAssertion());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\Evidence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */