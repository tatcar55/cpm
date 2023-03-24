/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.saml.Conditions;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.ConditionsType;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import com.sun.xml.wss.util.DateUtils;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.datatype.DatatypeConfigurationException;
/*     */ import javax.xml.datatype.DatatypeFactory;
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
/*     */ public class Conditions
/*     */   extends ConditionsType
/*     */   implements Conditions
/*     */ {
/*  68 */   private Date notBeforeField = null;
/*  69 */   private Date notOnOrAfterField = null;
/*     */   
/*  71 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Conditions() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setaudienceRestrictionConditionOrDoNotCacheConditionOrCondition(List condition) {
/*  85 */     this.audienceRestrictionConditionOrDoNotCacheConditionOrCondition = condition;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Conditions(GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, List condition, List arc, List doNotCacheCnd) {
/* 110 */     DatatypeFactory factory = null;
/*     */     try {
/* 112 */       factory = DatatypeFactory.newInstance();
/* 113 */     } catch (DatatypeConfigurationException e) {
/* 114 */       factory = null;
/*     */     } 
/*     */     
/* 117 */     if (factory != null) {
/* 118 */       setNotBefore(factory.newXMLGregorianCalendar(notBefore));
/* 119 */       setNotOnOrAfter(factory.newXMLGregorianCalendar(notOnOrAfter));
/*     */     } 
/*     */     
/* 122 */     if (condition != null) {
/* 123 */       setaudienceRestrictionConditionOrDoNotCacheConditionOrCondition(condition);
/* 124 */     } else if (arc != null) {
/* 125 */       setaudienceRestrictionConditionOrDoNotCacheConditionOrCondition(arc);
/* 126 */     } else if (doNotCacheCnd != null) {
/* 127 */       setaudienceRestrictionConditionOrDoNotCacheConditionOrCondition(doNotCacheCnd);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Conditions(ConditionsType cType) {
/* 132 */     setNotBefore(cType.getNotBefore());
/* 133 */     setNotOnOrAfter(cType.getNotOnOrAfter());
/* 134 */     setaudienceRestrictionConditionOrDoNotCacheConditionOrCondition(cType.getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition());
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getNotBeforeDate() {
/*     */     try {
/* 140 */       if (this.notBeforeField != null) {
/* 141 */         return this.notBeforeField;
/*     */       }
/* 143 */       if (getNotBefore() != null) {
/* 144 */         this.notBeforeField = DateUtils.stringToDate(getNotBefore().toString());
/*     */       }
/* 146 */     } catch (ParseException ex) {
/* 147 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0430_SAML_GET_NOT_BEFORE_DATE_OR_GET_NOT_ON_OR_AFTER_DATE_PARSE_FAILED(), ex);
/*     */     } 
/* 149 */     return this.notBeforeField;
/*     */   }
/*     */   
/*     */   public Date getNotOnOrAfterDate() {
/*     */     try {
/* 154 */       if (this.notOnOrAfterField != null) {
/* 155 */         return this.notOnOrAfterField;
/*     */       }
/* 157 */       if (getNotOnOrAfter() != null) {
/* 158 */         this.notOnOrAfterField = DateUtils.stringToDate(getNotOnOrAfter().toString());
/*     */       }
/* 160 */     } catch (ParseException ex) {
/* 161 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0430_SAML_GET_NOT_BEFORE_DATE_OR_GET_NOT_ON_OR_AFTER_DATE_PARSE_FAILED(), ex);
/*     */     } 
/* 163 */     return this.notOnOrAfterField;
/*     */   }
/*     */   
/*     */   public List<Object> getConditions() {
/* 167 */     return getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition();
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
/*     */   public static ConditionsType fromElement(Element element) throws SAMLException {
/*     */     try {
/* 181 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 183 */       Unmarshaller u = jc.createUnmarshaller();
/* 184 */       return (ConditionsType)u.unmarshal(element);
/* 185 */     } catch (Exception ex) {
/* 186 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\Conditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */