/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.saml.Conditions;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.ConditionsType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import com.sun.xml.wss.util.DateUtils;
/*     */ import java.text.ParseException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
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
/*     */ public class Conditions
/*     */   extends ConditionsImpl
/*     */   implements Conditions
/*     */ {
/*  75 */   private Date notBeforeField = null;
/*  76 */   private Date notOnOrAfterField = null;
/*     */   
/*  78 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
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
/*  91 */     this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition = new ListImpl(condition);
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
/*     */   public Conditions(Calendar notBefore, Calendar notOnOrAfter, List condition, List arc, List doNotCacheCnd) {
/* 115 */     setNotBefore(notBefore);
/* 116 */     setNotOnOrAfter(notOnOrAfter);
/*     */     
/* 118 */     if (condition != null) {
/* 119 */       setaudienceRestrictionConditionOrDoNotCacheConditionOrCondition(condition);
/* 120 */     } else if (arc != null) {
/* 121 */       setaudienceRestrictionConditionOrDoNotCacheConditionOrCondition(arc);
/* 122 */     } else if (doNotCacheCnd != null) {
/* 123 */       setaudienceRestrictionConditionOrDoNotCacheConditionOrCondition(doNotCacheCnd);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Conditions(ConditionsType cType) {
/* 128 */     setNotBefore(cType.getNotBefore());
/* 129 */     setNotOnOrAfter(cType.getNotOnOrAfter());
/* 130 */     setaudienceRestrictionConditionOrDoNotCacheConditionOrCondition(cType.getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition());
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getNotBeforeDate() {
/*     */     try {
/* 136 */       if (this.notBeforeField != null) {
/* 137 */         return this.notBeforeField;
/*     */       }
/* 139 */       if (getNotBefore() != null) {
/* 140 */         this.notBeforeField = DateUtils.stringToDate(getNotBefore().toString());
/*     */       }
/* 142 */     } catch (ParseException ex) {
/* 143 */       log.log(Level.SEVERE, (String)null, ex);
/*     */     } 
/* 145 */     return this.notBeforeField;
/*     */   }
/*     */   
/*     */   public Date getNotOnOrAfterDate() {
/*     */     try {
/* 150 */       if (this.notOnOrAfterField != null) {
/* 151 */         return this.notOnOrAfterField;
/*     */       }
/* 153 */       if (getNotOnOrAfter() != null) {
/* 154 */         this.notOnOrAfterField = DateUtils.stringToDate(getNotOnOrAfter().toString());
/*     */       }
/* 156 */     } catch (ParseException ex) {
/* 157 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0430_SAML_GET_NOT_BEFORE_DATE_OR_GET_NOT_ON_OR_AFTER_DATE_PARSE_FAILED(), ex);
/*     */     } 
/* 159 */     return this.notOnOrAfterField;
/*     */   }
/*     */   
/*     */   public List<Object> getConditions() {
/* 163 */     return getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition();
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
/*     */   public static ConditionsTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/* 178 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 180 */       Unmarshaller u = jc.createUnmarshaller();
/* 181 */       return (ConditionsTypeImpl)u.unmarshal(element);
/* 182 */     } catch (Exception ex) {
/* 183 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\Conditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */