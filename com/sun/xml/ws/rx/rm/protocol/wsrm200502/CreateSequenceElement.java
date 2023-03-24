/*     */ package com.sun.xml.ws.rx.rm.protocol.wsrm200502;
/*     */ 
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceData;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ import javax.xml.ws.EndpointReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlType(name = "CreateSequenceType", propOrder = {"acksTo", "any", "expires", "offer", "securityTokenReference"})
/*     */ @XmlRootElement(name = "CreateSequence", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */ public class CreateSequenceElement
/*     */ {
/*     */   @XmlElement(name = "AcksTo", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */   protected EndpointReference acksTo;
/*     */   @XmlAnyElement(lax = true)
/*  95 */   protected List<Object> any = new ArrayList();
/*     */   @XmlElement(name = "Expires", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */   protected Expires expires;
/*     */   @XmlElement(name = "Offer", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */   protected OfferType offer;
/*     */   @XmlElement(name = "SecurityTokenReference", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
/*     */   private SecurityTokenReferenceType securityTokenReference;
/*     */   @XmlAnyAttribute
/* 103 */   private Map<QName, String> otherAttributes = new HashMap<QName, String>();
/*     */ 
/*     */   
/*     */   public CreateSequenceElement() {}
/*     */ 
/*     */   
/*     */   public CreateSequenceElement(CreateSequenceData data) {
/* 110 */     this();
/*     */     
/* 112 */     this.acksTo = data.getAcksToEpr();
/* 113 */     if (!data.doesNotExpire()) {
/* 114 */       this.expires = new Expires(data.getDuration());
/*     */     }
/*     */     
/* 117 */     if (data.getOfferedSequenceId() != null) {
/* 118 */       this.offer = new OfferType();
/* 119 */       this.offer.setId(data.getOfferedSequenceId());
/* 120 */       if (!data.offeredSequenceDoesNotExpire()) {
/* 121 */         this.offer.setExpires(new Expires(data.getOfferedSequenceExpiry()));
/*     */       }
/*     */     } 
/* 124 */     if (data.getStrType() != null) {
/* 125 */       this.securityTokenReference = data.getStrType();
/*     */     }
/*     */   }
/*     */   
/*     */   public CreateSequenceData.Builder toDataBuilder() {
/* 130 */     CreateSequenceData.Builder dataBuilder = CreateSequenceData.getBuilder(getAcksTo());
/* 131 */     dataBuilder.strType(this.securityTokenReference);
/*     */     
/* 133 */     if (this.expires != null) {
/* 134 */       dataBuilder.duration(this.expires.getDuration());
/*     */     }
/*     */     
/* 137 */     if (this.offer != null) {
/* 138 */       dataBuilder.offeredInboundSequenceId(this.offer.getId());
/* 139 */       if (this.offer.getExpires() != null) {
/* 140 */         dataBuilder.offeredSequenceExpiry(this.offer.getExpires().getDuration());
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return dataBuilder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndpointReference getAcksTo() {
/* 151 */     return this.acksTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAcksTo(EndpointReference value) {
/* 158 */     this.acksTo = value;
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
/*     */   public Expires getExpires() {
/* 170 */     return this.expires;
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
/*     */   public void setExpires(Expires value) {
/* 182 */     this.expires = value;
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
/*     */   public OfferType getOffer() {
/* 194 */     return this.offer;
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
/*     */   public void setOffer(OfferType value) {
/* 206 */     this.offer = value;
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
/* 233 */     if (this.any == null) {
/* 234 */       this.any = new ArrayList();
/*     */     }
/* 236 */     return this.any;
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
/* 254 */     return this.otherAttributes;
/*     */   }
/*     */   
/*     */   public SecurityTokenReferenceType getSecurityTokenReference() {
/* 258 */     return this.securityTokenReference;
/*     */   }
/*     */   
/*     */   public void setSecurityTokenReference(SecurityTokenReferenceType securityTokenReference) {
/* 262 */     this.securityTokenReference = securityTokenReference;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\wsrm200502\CreateSequenceElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */