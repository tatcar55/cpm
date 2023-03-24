/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.Advice;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AdviceType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceTypeImpl;
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
/*     */ public class Advice
/*     */   extends AdviceImpl
/*     */   implements Advice
/*     */ {
/*  72 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public static AdviceTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/*  78 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  80 */       Unmarshaller u = jc.createUnmarshaller();
/*  81 */       return (AdviceTypeImpl)u.unmarshal(element);
/*  82 */     } catch (Exception ex) {
/*  83 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setAssertionIDReferenceOrAssertionOrAny(List assertionIDReferenceOrAssertionOrAny) {
/*  90 */     this._AssertionIDReferenceOrAssertionOrAny = new ListImpl(assertionIDReferenceOrAssertionOrAny);
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
/*     */   public Advice(List assertionidreference, List assertion, List otherelement) {
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\Advice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */