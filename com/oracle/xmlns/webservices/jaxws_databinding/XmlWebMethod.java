/*     */ package com.oracle.xmlns.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.jws.WebMethod;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlRootElement(name = "web-method")
/*     */ public class XmlWebMethod
/*     */   implements WebMethod
/*     */ {
/*     */   @XmlAttribute(name = "action")
/*     */   protected String action;
/*     */   @XmlAttribute(name = "exclude")
/*     */   protected Boolean exclude;
/*     */   @XmlAttribute(name = "operation-name")
/*     */   protected String operationName;
/*     */   
/*     */   public String getAction() {
/*  93 */     if (this.action == null) {
/*  94 */       return "";
/*     */     }
/*  96 */     return this.action;
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
/*     */   public void setAction(String value) {
/* 109 */     this.action = value;
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
/*     */   public boolean isExclude() {
/* 121 */     if (this.exclude == null) {
/* 122 */       return false;
/*     */     }
/* 124 */     return this.exclude.booleanValue();
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
/*     */   public void setExclude(Boolean value) {
/* 137 */     this.exclude = value;
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
/*     */   public String getOperationName() {
/* 149 */     if (this.operationName == null) {
/* 150 */       return "";
/*     */     }
/* 152 */     return this.operationName;
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
/*     */   public void setOperationName(String value) {
/* 165 */     this.operationName = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String operationName() {
/* 170 */     return Util.nullSafe(this.operationName);
/*     */   }
/*     */ 
/*     */   
/*     */   public String action() {
/* 175 */     return Util.nullSafe(this.action);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean exclude() {
/* 180 */     return ((Boolean)Util.<Boolean>nullSafe(this.exclude, Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 185 */     return (Class)WebMethod.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\xmlns\webservices\jaxws_databinding\XmlWebMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */