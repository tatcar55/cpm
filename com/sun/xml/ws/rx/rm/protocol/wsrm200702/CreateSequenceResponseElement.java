/*     */ package com.sun.xml.ws.rx.rm.protocol.wsrm200702;
/*     */ 
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlType(name = "CreateSequenceResponseType", propOrder = {"identifier", "expires", "incompleteSequenceBehavior", "accept", "any"})
/*     */ @XmlRootElement(name = "CreateSequenceResponse", namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */ public class CreateSequenceResponseElement
/*     */ {
/*     */   @XmlElement(name = "Identifier", required = true)
/*     */   protected Identifier identifier;
/*     */   @XmlElement(name = "Expires")
/*     */   protected Expires expires;
/*     */   @XmlElement(name = "IncompleteSequenceBehavior")
/*     */   protected IncompleteSequenceBehaviorType incompleteSequenceBehavior;
/*     */   @XmlElement(name = "Accept")
/*     */   protected AcceptType accept;
/*     */   @XmlAnyElement(lax = true)
/*     */   protected List<Object> any;
/*     */   @XmlAnyAttribute
/* 103 */   private Map<QName, String> otherAttributes = new HashMap<QName, String>();
/*     */ 
/*     */   
/*     */   public CreateSequenceResponseElement() {}
/*     */ 
/*     */   
/*     */   public CreateSequenceResponseElement(CreateSequenceResponseData data) {
/* 110 */     this();
/*     */     
/* 112 */     this.identifier = new Identifier(data.getSequenceId());
/*     */     
/* 114 */     if (data.getIncompleteSequenceBehavior() != Sequence.IncompleteSequenceBehavior.getDefault()) {
/* 115 */       this.incompleteSequenceBehavior = IncompleteSequenceBehaviorType.fromISB(data.getIncompleteSequenceBehavior());
/*     */     }
/*     */     
/* 118 */     if (!data.doesNotExpire()) {
/* 119 */       this.expires = new Expires(data.getDuration());
/*     */     }
/* 121 */     if (data.getAcceptedSequenceAcksTo() != null) {
/* 122 */       this.accept = new AcceptType();
/* 123 */       this.accept.setAcksTo(data.getAcceptedSequenceAcksTo());
/*     */     } 
/*     */   }
/*     */   
/*     */   public CreateSequenceResponseData.Builder toDataBuilder() {
/* 128 */     CreateSequenceResponseData.Builder dataBuilder = CreateSequenceResponseData.getBuilder(this.identifier.getValue());
/*     */     
/* 130 */     if (this.expires != null && this.expires.getDuration() != -1L) {
/* 131 */       dataBuilder.duration(this.expires.getDuration());
/*     */     }
/*     */     
/* 134 */     if (this.accept != null) {
/* 135 */       dataBuilder.acceptedSequenceAcksTo(this.accept.getAcksTo());
/*     */     }
/*     */     
/* 138 */     if (this.incompleteSequenceBehavior != null) {
/* 139 */       dataBuilder.incompleteSequenceBehavior(this.incompleteSequenceBehavior.translate());
/*     */     }
/*     */     
/* 142 */     return dataBuilder;
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
/*     */   public Identifier getIdentifier() {
/* 154 */     return this.identifier;
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
/*     */   public void setIdentifier(Identifier value) {
/* 166 */     this.identifier = value;
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
/* 178 */     return this.expires;
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
/* 190 */     this.expires = value;
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
/*     */   public IncompleteSequenceBehaviorType getIncompleteSequenceBehavior() {
/* 202 */     return this.incompleteSequenceBehavior;
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
/*     */   public void setIncompleteSequenceBehavior(IncompleteSequenceBehaviorType value) {
/* 214 */     this.incompleteSequenceBehavior = value;
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
/*     */   public AcceptType getAccept() {
/* 226 */     return this.accept;
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
/*     */   public void setAccept(AcceptType value) {
/* 238 */     this.accept = value;
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
/* 265 */     if (this.any == null) {
/* 266 */       this.any = new ArrayList();
/*     */     }
/* 268 */     return this.any;
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
/* 286 */     return this.otherAttributes;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\wsrm200702\CreateSequenceResponseElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */