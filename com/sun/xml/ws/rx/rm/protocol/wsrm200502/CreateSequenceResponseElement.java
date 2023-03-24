/*     */ package com.sun.xml.ws.rx.rm.protocol.wsrm200502;
/*     */ 
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceResponseData;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "CreateSequenceResponseType", propOrder = {"identifier", "expires", "accept", "any"})
/*     */ @XmlRootElement(name = "CreateSequenceResponse", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */ public class CreateSequenceResponseElement
/*     */ {
/*     */   @XmlElement(name = "Identifier", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */   protected Identifier identifier;
/*     */   @XmlElement(name = "Expires", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */   protected Expires expires;
/*     */   @XmlElement(name = "Accept", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */   protected AcceptType accept;
/*     */   @XmlAnyElement(lax = true)
/*     */   protected List<Object> any;
/*     */   @XmlAnyAttribute
/*  98 */   private Map<QName, String> otherAttributes = new HashMap<QName, String>();
/*     */ 
/*     */   
/*     */   public CreateSequenceResponseElement() {}
/*     */ 
/*     */   
/*     */   public CreateSequenceResponseElement(CreateSequenceResponseData data) {
/* 105 */     this();
/*     */     
/* 107 */     this.identifier = new Identifier(data.getSequenceId());
/* 108 */     if (!data.doesNotExpire()) {
/* 109 */       this.expires = new Expires(data.getDuration());
/*     */     }
/* 111 */     if (data.getAcceptedSequenceAcksTo() != null) {
/* 112 */       this.accept = new AcceptType();
/* 113 */       this.accept.setAcksTo(data.getAcceptedSequenceAcksTo());
/*     */     } 
/*     */   }
/*     */   
/*     */   public CreateSequenceResponseData.Builder toDataBuilder() {
/* 118 */     CreateSequenceResponseData.Builder dataBuilder = CreateSequenceResponseData.getBuilder(this.identifier.getValue());
/*     */     
/* 120 */     if (this.expires != null && this.expires.getDuration() != -1L) {
/* 121 */       dataBuilder.duration(this.expires.getDuration());
/*     */     }
/*     */     
/* 124 */     if (this.accept != null) {
/* 125 */       dataBuilder.acceptedSequenceAcksTo(this.accept.getAcksTo());
/*     */     }
/*     */     
/* 128 */     return dataBuilder;
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
/* 140 */     return this.identifier;
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
/* 152 */     this.identifier = value;
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
/* 164 */     return this.expires;
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
/* 176 */     this.expires = value;
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
/* 188 */     return this.accept;
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
/* 200 */     this.accept = value;
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
/* 227 */     if (this.any == null) {
/* 228 */       this.any = new ArrayList();
/*     */     }
/* 230 */     return this.any;
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
/* 248 */     return this.otherAttributes;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\wsrm200502\CreateSequenceResponseElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */