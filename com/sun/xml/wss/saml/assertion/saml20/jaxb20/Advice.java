/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Advice;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AdviceType;
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
/*     */ public class Advice
/*     */   extends AdviceType
/*     */   implements Advice
/*     */ {
/*  61 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public static AdviceType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  67 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*  68 */       Unmarshaller u = jc.createUnmarshaller();
/*  69 */       return (AdviceType)u.unmarshal(element);
/*  70 */     } catch (Exception ex) {
/*  71 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAssertionIDRefOrAssertionURIRefOrAssertion(List assertionIDRefOrAssertionURIRefOrAssertion) {
/*  77 */     this.assertionIDRefOrAssertionURIRefOrAssertion = assertionIDRefOrAssertionURIRefOrAssertion;
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
/*     */   public Advice(List assertionidreference, List assertion, List otherelement) {
/*  90 */     if (null != assertionidreference) {
/*  91 */       setAssertionIDRefOrAssertionURIRefOrAssertion(assertionidreference);
/*  92 */     } else if (null != assertion) {
/*  93 */       setAssertionIDRefOrAssertionURIRefOrAssertion(assertion);
/*  94 */     } else if (null != otherelement) {
/*  95 */       setAssertionIDRefOrAssertionURIRefOrAssertion(otherelement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Advice(AdviceType adviceType) {
/* 100 */     if (adviceType != null) {
/* 101 */       setAssertionIDRefOrAssertionURIRefOrAssertion(adviceType.getAssertionIDRefOrAssertionURIRefOrAssertion());
/*     */     }
/*     */   }
/*     */   
/*     */   public List<Object> getAdvice() {
/* 106 */     return getAssertionIDRefOrAssertionURIRefOrAssertion();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\Advice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */