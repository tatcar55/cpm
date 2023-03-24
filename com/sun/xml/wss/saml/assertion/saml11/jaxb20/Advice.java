/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Advice;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AdviceType;
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
/*     */ 
/*     */ public class Advice
/*     */   extends AdviceType
/*     */   implements Advice
/*     */ {
/*  70 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AdviceType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  77 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  79 */       Unmarshaller u = jc.createUnmarshaller();
/*  80 */       return (AdviceType)u.unmarshal(element);
/*  81 */     } catch (Exception ex) {
/*  82 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setAssertionIDReferenceOrAssertionOrAny(List<Object> assertionIDReferenceOrAssertionOrAny) {
/*  89 */     this.assertionIDReferenceOrAssertionOrAny = assertionIDReferenceOrAssertionOrAny;
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
/*     */   public Advice(List<Object> assertionidreference, List<Object> assertion, List<Object> otherelement) {
/* 102 */     if (null != assertionidreference) {
/* 103 */       setAssertionIDReferenceOrAssertionOrAny(assertionidreference);
/* 104 */     } else if (null != assertion) {
/* 105 */       setAssertionIDReferenceOrAssertionOrAny(assertion);
/* 106 */     } else if (null != otherelement) {
/* 107 */       setAssertionIDReferenceOrAssertionOrAny(otherelement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Advice(AdviceType adviceType) {
/* 112 */     if (adviceType != null) {
/* 113 */       setAssertionIDReferenceOrAssertionOrAny(adviceType.getAssertionIDReferenceOrAssertionOrAny());
/*     */     }
/*     */   }
/*     */   
/*     */   public List<Object> getAdvice() {
/* 118 */     return getAssertionIDReferenceOrAssertionOrAny();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\Advice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */