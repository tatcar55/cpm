/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.Evidence;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.EvidenceType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.EvidenceImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.EvidenceTypeImpl;
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
/*     */ public class Evidence
/*     */   extends EvidenceImpl
/*     */   implements Evidence
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
/*     */ 
/*     */   
/*     */   public static EvidenceTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/*  87 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  89 */       Unmarshaller u = jc.createUnmarshaller();
/*  90 */       return (EvidenceTypeImpl)u.unmarshal(element);
/*  91 */     } catch (Exception ex) {
/*  92 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAssertionIDReferenceOrAssertion(List evidence) {
/*  98 */     this._AssertionIDReferenceOrAssertion = new ListImpl(evidence);
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
/* 113 */     if (assertionIDRef != null) {
/* 114 */       setAssertionIDReferenceOrAssertion(assertionIDRef);
/* 115 */     } else if (assertion != null) {
/* 116 */       setAssertionIDReferenceOrAssertion(assertion);
/*     */     } 
/*     */   }
/*     */   public Evidence(EvidenceType eveType) {
/* 120 */     setAssertionIDReferenceOrAssertion(eveType.getAssertionIDReferenceOrAssertion());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\Evidence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */