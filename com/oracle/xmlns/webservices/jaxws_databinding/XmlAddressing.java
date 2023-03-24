/*     */ package com.oracle.xmlns.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.ws.soap.Addressing;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "")
/*     */ @XmlRootElement(name = "addressing")
/*     */ public class XmlAddressing
/*     */   implements Addressing
/*     */ {
/*     */   @XmlAttribute(name = "enabled")
/*     */   protected Boolean enabled;
/*     */   @XmlAttribute(name = "required")
/*     */   protected Boolean required;
/*     */   
/*     */   public Boolean getEnabled() {
/*  82 */     return Boolean.valueOf(enabled());
/*     */   }
/*     */   
/*     */   public void setEnabled(Boolean enabled) {
/*  86 */     this.enabled = enabled;
/*     */   }
/*     */   
/*     */   public Boolean getRequired() {
/*  90 */     return Boolean.valueOf(required());
/*     */   }
/*     */   
/*     */   public void setRequired(Boolean required) {
/*  94 */     this.required = required;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean enabled() {
/*  99 */     return ((Boolean)Util.<Boolean>nullSafe(this.enabled, Boolean.valueOf(true))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean required() {
/* 104 */     return ((Boolean)Util.<Boolean>nullSafe(this.required, Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public AddressingFeature.Responses responses() {
/* 109 */     return AddressingFeature.Responses.ALL;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 114 */     return (Class)Addressing.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\xmlns\webservices\jaxws_databinding\XmlAddressing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */