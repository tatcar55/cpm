/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb20;
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
/*     */ @XmlRootElement(name = "Conditions")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "ConditionsType", propOrder = {"audienceRestrictionConditionOrDoNotCacheConditionOrCondition"})
/*     */ public class ConditionsType
/*     */ {
/*     */   @XmlElements({@XmlElement(name = "DoNotCacheCondition", type = DoNotCacheConditionType.class), @XmlElement(name = "AudienceRestrictionCondition", type = AudienceRestrictionConditionType.class), @XmlElement(name = "Condition")})
/*     */   protected List<ConditionAbstractType> audienceRestrictionConditionOrDoNotCacheConditionOrCondition;
/*     */   @XmlAttribute(name = "NotBefore")
/*     */   protected XMLGregorianCalendar notBefore;
/*     */   @XmlAttribute(name = "NotOnOrAfter")
/*     */   protected XMLGregorianCalendar notOnOrAfter;
/*     */   
/*     */   public List<ConditionAbstractType> getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition() {
/* 129 */     if (this.audienceRestrictionConditionOrDoNotCacheConditionOrCondition == null) {
/* 130 */       this.audienceRestrictionConditionOrDoNotCacheConditionOrCondition = new ArrayList<ConditionAbstractType>();
/*     */     }
/* 132 */     return this.audienceRestrictionConditionOrDoNotCacheConditionOrCondition;
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
/* 144 */     return this.notBefore;
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
/* 156 */     this.notBefore = value;
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
/* 168 */     return this.notOnOrAfter;
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
/* 180 */     this.notOnOrAfter = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb20\ConditionsType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */