/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.saml.Conditions;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.ConditionsType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Conditions
/*     */   extends ConditionsType
/*     */   implements Conditions
/*     */ {
/*  72 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private Date notBeforeField = null;
/*  78 */   private Date notOnOrAfterField = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Conditions() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void setConditionOrAudienceRestrictionOrOneTimeUse(List condition) {
/*  88 */     this.conditionOrAudienceRestrictionOrOneTimeUse = condition;
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
/*     */   public Conditions(GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, List condition, List ar, List oneTimeUse, List proxyRestriction) {
/* 113 */     DatatypeFactory factory = null;
/*     */     try {
/* 115 */       factory = DatatypeFactory.newInstance();
/* 116 */     } catch (DatatypeConfigurationException e) {
/* 117 */       factory = null;
/*     */     } 
/*     */     
/* 120 */     if (factory != null) {
/* 121 */       setNotBefore(factory.newXMLGregorianCalendar(notBefore));
/* 122 */       setNotOnOrAfter(factory.newXMLGregorianCalendar(notOnOrAfter));
/*     */     } 
/*     */     
/* 125 */     if (condition != null) {
/* 126 */       setConditionOrAudienceRestrictionOrOneTimeUse(condition);
/*     */     }
/* 128 */     if (ar != null) {
/* 129 */       setConditionOrAudienceRestrictionOrOneTimeUse(ar);
/*     */     }
/* 131 */     if (oneTimeUse != null) {
/* 132 */       setConditionOrAudienceRestrictionOrOneTimeUse(oneTimeUse);
/*     */     }
/* 134 */     if (proxyRestriction != null) {
/* 135 */       setConditionOrAudienceRestrictionOrOneTimeUse(proxyRestriction);
/*     */     }
/*     */   }
/*     */   
/*     */   public Conditions(ConditionsType cType) {
/* 140 */     setNotBefore(cType.getNotBefore());
/* 141 */     setNotOnOrAfter(cType.getNotOnOrAfter());
/* 142 */     setConditionOrAudienceRestrictionOrOneTimeUse(cType.getConditionOrAudienceRestrictionOrOneTimeUse());
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getNotBeforeDate() {
/*     */     try {
/* 148 */       if (this.notBeforeField != null) {
/* 149 */         return this.notBeforeField;
/*     */       }
/* 151 */       if (getNotBefore() != null) {
/* 152 */         this.notBeforeField = DateUtils.stringToDate(getNotBefore().toString());
/*     */       }
/* 154 */     } catch (ParseException ex) {
/* 155 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0430_SAML_GET_NOT_BEFORE_DATE_OR_GET_NOT_ON_OR_AFTER_DATE_PARSE_FAILED(), ex);
/*     */     } 
/* 157 */     return this.notBeforeField;
/*     */   }
/*     */   
/*     */   public Date getNotOnOrAfterDate() {
/*     */     try {
/* 162 */       if (this.notOnOrAfterField != null) {
/* 163 */         return this.notOnOrAfterField;
/*     */       }
/* 165 */       if (getNotOnOrAfter() != null) {
/* 166 */         this.notOnOrAfterField = DateUtils.stringToDate(getNotOnOrAfter().toString());
/*     */       }
/* 168 */     } catch (ParseException ex) {
/* 169 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0430_SAML_GET_NOT_BEFORE_DATE_OR_GET_NOT_ON_OR_AFTER_DATE_PARSE_FAILED(), ex);
/*     */     } 
/* 171 */     return this.notOnOrAfterField;
/*     */   }
/*     */   
/*     */   public List<Object> getConditions() {
/* 175 */     return getConditionOrAudienceRestrictionOrOneTimeUse();
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
/*     */   public static ConditionsType fromElement(Element element) throws SAMLException {
/*     */     try {
/* 190 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/* 192 */       Unmarshaller u = jc.createUnmarshaller();
/* 193 */       return (ConditionsType)u.unmarshal(element);
/* 194 */     } catch (Exception ex) {
/* 195 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\Conditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */