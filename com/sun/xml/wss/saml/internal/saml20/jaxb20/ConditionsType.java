/*     */ package com.sun.xml.wss.saml.internal.saml20.jaxb20;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElements;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.datatype.XMLGregorianCalendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "Conditions")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "ConditionsType", propOrder = {"conditionOrAudienceRestrictionOrOneTimeUse"})
/*     */ public class ConditionsType
/*     */ {
/*     */   @XmlElements({@XmlElement(name = "AudienceRestriction", type = AudienceRestrictionType.class), @XmlElement(name = "Condition"), @XmlElement(name = "OneTimeUse", type = OneTimeUseType.class), @XmlElement(name = "ProxyRestriction", type = ProxyRestrictionType.class)})
/*     */   protected List<ConditionAbstractType> conditionOrAudienceRestrictionOrOneTimeUse;
/*     */   @XmlAttribute(name = "NotBefore")
/*     */   protected XMLGregorianCalendar notBefore;
/*     */   @XmlAttribute(name = "NotOnOrAfter")
/*     */   protected XMLGregorianCalendar notOnOrAfter;
/*     */   
/*     */   public List<ConditionAbstractType> getConditionOrAudienceRestrictionOrOneTimeUse() {
/* 132 */     if (this.conditionOrAudienceRestrictionOrOneTimeUse == null) {
/* 133 */       this.conditionOrAudienceRestrictionOrOneTimeUse = new ArrayList<ConditionAbstractType>();
/*     */     }
/* 135 */     return this.conditionOrAudienceRestrictionOrOneTimeUse;
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
/*     */   public XMLGregorianCalendar getNotBefore() {
/* 147 */     return this.notBefore;
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
/*     */   public void setNotBefore(XMLGregorianCalendar value) {
/* 159 */     this.notBefore = value;
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
/*     */   public XMLGregorianCalendar getNotOnOrAfter() {
/* 171 */     return this.notOnOrAfter;
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
/*     */   public void setNotOnOrAfter(XMLGregorianCalendar value) {
/* 183 */     this.notOnOrAfter = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml20\jaxb20\ConditionsType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */