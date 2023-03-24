/*     */ package com.oracle.xmlns.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.jws.soap.SOAPBinding;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "")
/*     */ @XmlRootElement(name = "soap-binding")
/*     */ public class XmlSOAPBinding
/*     */   implements SOAPBinding
/*     */ {
/*     */   @XmlAttribute(name = "style")
/*     */   protected SoapBindingStyle style;
/*     */   @XmlAttribute(name = "use")
/*     */   protected SoapBindingUse use;
/*     */   @XmlAttribute(name = "parameter-style")
/*     */   protected SoapBindingParameterStyle parameterStyle;
/*     */   
/*     */   public SoapBindingStyle getStyle() {
/*  92 */     if (this.style == null) {
/*  93 */       return SoapBindingStyle.DOCUMENT;
/*     */     }
/*  95 */     return this.style;
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
/*     */   public void setStyle(SoapBindingStyle value) {
/* 108 */     this.style = value;
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
/*     */   public SoapBindingUse getUse() {
/* 120 */     if (this.use == null) {
/* 121 */       return SoapBindingUse.LITERAL;
/*     */     }
/* 123 */     return this.use;
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
/*     */   public void setUse(SoapBindingUse value) {
/* 136 */     this.use = value;
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
/*     */   public SoapBindingParameterStyle getParameterStyle() {
/* 148 */     if (this.parameterStyle == null) {
/* 149 */       return SoapBindingParameterStyle.WRAPPED;
/*     */     }
/* 151 */     return this.parameterStyle;
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
/*     */   public void setParameterStyle(SoapBindingParameterStyle value) {
/* 164 */     this.parameterStyle = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPBinding.Style style() {
/* 169 */     return Util.<SOAPBinding.Style>nullSafe(this.style, SOAPBinding.Style.DOCUMENT);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPBinding.Use use() {
/* 174 */     return Util.<SOAPBinding.Use>nullSafe(this.use, SOAPBinding.Use.LITERAL);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPBinding.ParameterStyle parameterStyle() {
/* 179 */     return Util.<SOAPBinding.ParameterStyle>nullSafe(this.parameterStyle, SOAPBinding.ParameterStyle.WRAPPED);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 184 */     return (Class)SOAPBinding.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\xmlns\webservices\jaxws_databinding\XmlSOAPBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */