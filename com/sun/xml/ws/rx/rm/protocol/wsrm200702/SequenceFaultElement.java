/*     */ package com.sun.xml.ws.rx.rm.protocol.wsrm200702;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyAttribute;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlType(name = "SequenceFaultType", propOrder = {"faultCode", "detail", "any"})
/*     */ @XmlRootElement(name = "SequenceFault", namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */ public class SequenceFaultElement
/*     */ {
/*     */   @XmlElement(name = "FaultCode", required = true)
/*     */   protected QName faultCode;
/*     */   @XmlElement(name = "Detail")
/*     */   protected DetailType detail;
/*     */   @XmlAnyElement(lax = true)
/*     */   protected List<Object> any;
/*     */   @XmlAnyAttribute
/*  94 */   private Map<QName, String> otherAttributes = new HashMap<QName, String>();
/*     */ 
/*     */ 
/*     */   
/*     */   public SequenceFaultElement() {}
/*     */ 
/*     */   
/*     */   public SequenceFaultElement(QName faultCode) {
/* 102 */     this.faultCode = faultCode;
/*     */   }
/*     */   
/*     */   public SequenceFaultElement(QName faultCode, Detail soapFaultDetail) {
/* 106 */     this.faultCode = faultCode;
/*     */     
/* 108 */     if (soapFaultDetail != null && soapFaultDetail.hasChildNodes()) {
/* 109 */       this.detail = new DetailType();
/*     */       
/* 111 */       Iterator detailEntries = soapFaultDetail.getDetailEntries();
/* 112 */       while (detailEntries.hasNext()) {
/* 113 */         this.detail.getAny().add(detailEntries.next());
/*     */       }
/*     */     } 
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
/*     */   public QName getFaultCode() {
/* 127 */     return this.faultCode;
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
/*     */   public void setFaultCode(QName value) {
/* 139 */     this.faultCode = value;
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
/*     */   public DetailType getDetail() {
/* 151 */     return this.detail;
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
/*     */   public void setDetail(DetailType value) {
/* 163 */     this.detail = value;
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
/*     */ 
/*     */   
/*     */   public List<Object> getAny() {
/* 190 */     if (this.any == null) {
/* 191 */       this.any = new ArrayList();
/*     */     }
/* 193 */     return this.any;
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
/* 211 */     return this.otherAttributes;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\wsrm200702\SequenceFaultElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */