/*     */ package com.sun.xml.ws.rx.rm.protocol.wsrm200702;
/*     */ 
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
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
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "CreateSequenceType", propOrder = {"acksTo", "any", "expires", "offer", "securityTokenReference"})
/*     */ @XmlRootElement(name = "CreateSequence", namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */ public class CreateSequenceElement
/*     */ {
/*     */   @XmlElement(name = "AcksTo", required = true, namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */   protected EndpointReference acksTo;
/*     */   @XmlElement(name = "Expires", namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */   protected Expires expires;
/*     */   @XmlElement(name = "Offer", namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */   protected OfferType offer;
/*     */   @XmlAnyElement(lax = true)
/*     */   protected List<Object> any;
/*     */   @XmlElement(name = "SecurityTokenReference", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
/*     */   private SecurityTokenReferenceType securityTokenReference;
/*     */   @XmlAnyAttribute
/* 105 */   private Map<QName, String> otherAttributes = new HashMap<QName, String>();
/*     */ 
/*     */   
/*     */   public CreateSequenceElement() {}
/*     */ 
/*     */   
/*     */   public CreateSequenceElement(CreateSequenceData data) {
/* 112 */     this();
/*     */     
/* 114 */     this.acksTo = data.getAcksToEpr();
/* 115 */     if (!data.doesNotExpire()) {
/* 116 */       this.expires = new Expires(data.getDuration());
/*     */     }
/*     */     
/* 119 */     if (data.getOfferedSequenceId() != null) {
/* 120 */       this.offer = new OfferType();
/* 121 */       this.offer.setId(data.getOfferedSequenceId());
/*     */       
/* 123 */       this.offer.setEndpoint(data.getAcksToEpr());
/*     */       
/* 125 */       if (!data.offeredSequenceDoesNotExpire()) {
/* 126 */         this.offer.setExpires(new Expires(data.getOfferedSequenceExpiry()));
/*     */       }
/*     */       
/* 129 */       if (data.getOfferedSequenceIncompleteBehavior() != Sequence.IncompleteSequenceBehavior.getDefault()) {
/* 130 */         this.offer.setIncompleteSequenceBehavior(IncompleteSequenceBehaviorType.fromISB(data.getOfferedSequenceIncompleteBehavior()));
/*     */       }
/*     */     } 
/* 133 */     if (data.getStrType() != null) {
/* 134 */       this.securityTokenReference = data.getStrType();
/*     */     }
/*     */   }
/*     */   
/*     */   public CreateSequenceData.Builder toDataBuilder() {
/* 139 */     CreateSequenceData.Builder dataBuilder = CreateSequenceData.getBuilder(getAcksTo());
/* 140 */     dataBuilder.strType(this.securityTokenReference);
/*     */     
/* 142 */     if (this.expires != null) {
/* 143 */       dataBuilder.duration(this.expires.getDuration());
/*     */     }
/*     */     
/* 146 */     if (this.offer != null) {
/* 147 */       dataBuilder.offeredInboundSequenceId(this.offer.getId());
/* 148 */       if (this.offer.getExpires() != null) {
/* 149 */         dataBuilder.offeredSequenceExpiry(this.offer.getExpires().getDuration());
/*     */       }
/*     */       
/* 152 */       if (this.offer.getIncompleteSequenceBehavior() != null) {
/* 153 */         dataBuilder.offeredSequenceIncompleteBehavior(this.offer.getIncompleteSequenceBehavior().translate());
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return dataBuilder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndpointReference getAcksTo() {
/* 164 */     return this.acksTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAcksTo(EndpointReference value) {
/* 171 */     this.acksTo = value;
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
/* 183 */     return this.expires;
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
/* 195 */     this.expires = value;
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
/* 207 */     return this.offer;
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
/* 219 */     this.offer = value;
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
/* 246 */     if (this.any == null) {
/* 247 */       this.any = new ArrayList();
/*     */     }
/* 249 */     return this.any;
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
/* 267 */     return this.otherAttributes;
/*     */   }
/*     */   
/*     */   public SecurityTokenReferenceType getSecurityTokenReference() {
/* 271 */     return this.securityTokenReference;
/*     */   }
/*     */   
/*     */   public void setSecurityTokenReference(SecurityTokenReferenceType securityTokenReference) {
/* 275 */     this.securityTokenReference = securityTokenReference;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\wsrm200702\CreateSequenceElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */