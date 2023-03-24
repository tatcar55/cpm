/*     */ package com.sun.xml.ws.rx.rm.protocol.wsrm200702;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.XmlValue;
/*     */ import javax.xml.datatype.Duration;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlType(name = "", propOrder = {"value"})
/*     */ @XmlRootElement(name = "Expires")
/*     */ public class Expires
/*     */ {
/*     */   @XmlValue
/*     */   protected Duration value;
/*     */   @XmlAnyAttribute
/*  81 */   private Map<QName, String> otherAttributes = new HashMap<QName, String>();
/*     */ 
/*     */   
/*     */   public Expires() {}
/*     */ 
/*     */   
/*     */   public Expires(Duration value) {
/*  88 */     this.value = value;
/*     */   }
/*     */   
/*     */   public Expires(long duration) {
/*  92 */     setDuration(duration);
/*     */   }
/*     */   
/*     */   public long getDuration() {
/*  96 */     if (this.value == null || "PT0S".equals(this.value.toString())) {
/*  97 */       return -1L;
/*     */     }
/*     */     
/* 100 */     return getValue().getTimeInMillis(Calendar.getInstance());
/*     */   }
/*     */   
/*     */   public void setDuration(long value) {
/* 104 */     if (value == -1L);
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
/*     */   public Duration getValue() {
/* 120 */     return this.value;
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
/*     */   public void setValue(Duration value) {
/* 132 */     this.value = value;
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
/*     */   public Map<QName, String> getOtherAttributes() {
/* 150 */     return this.otherAttributes;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\wsrm200702\Expires.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */