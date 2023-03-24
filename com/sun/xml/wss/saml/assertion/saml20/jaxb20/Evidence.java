/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Evidence;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.EvidenceType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
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
/*     */ public class Evidence
/*     */   extends EvidenceType
/*     */   implements Evidence
/*     */ {
/*  67 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  84 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/*  86 */       Unmarshaller u = jc.createUnmarshaller();
/*  87 */       return (EvidenceType)u.unmarshal(element);
/*  88 */     } catch (Exception ex) {
/*  89 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAssertionIDReferenceOrAssertion(List evidence) {
/*  95 */     this.assertionIDRefOrAssertionURIRefOrAssertion = evidence;
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
/* 110 */     if (assertionIDRef != null) {
/* 111 */       setAssertionIDReferenceOrAssertion(assertionIDRef);
/* 112 */     } else if (assertion != null) {
/* 113 */       setAssertionIDReferenceOrAssertion(assertion);
/*     */     } 
/*     */   }
/*     */   public Evidence(EvidenceType eveType) {
/* 117 */     setAssertionIDReferenceOrAssertion(eveType.getAssertionIDRefOrAssertionURIRefOrAssertion());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\Evidence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */