/*     */ package com.sun.xml.ws.rx.rm.protocol.wsrm200702;
/*     */ 
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyAttribute;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlType(name = "", propOrder = {"identifier", "acknowledgementRange", "none", "_final", "bufferRemaining", "nack", "any"})
/*     */ @XmlRootElement(name = "SequenceAcknowledgement", namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */ public class SequenceAcknowledgementElement
/*     */ {
/*     */   @XmlElement(name = "Identifier", required = true, namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */   protected Identifier identifier;
/*     */   @XmlElement(name = "AcknowledgementRange", namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */   protected List<AcknowledgementRange> acknowledgementRange;
/*     */   @XmlElement(name = "None", namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */   protected None none;
/*     */   @XmlElement(name = "Final", namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */   protected Final _final;
/*     */   @XmlElement(name = "BufferRemaining", namespace = "http://schemas.microsoft.com/ws/2006/05/rm")
/*     */   protected Integer bufferRemaining;
/*     */   @XmlElement(name = "Nack", namespace = "http://docs.oasis-open.org/ws-rx/wsrm/200702")
/*     */   @XmlSchemaType(name = "unsignedLong")
/*     */   protected List<BigInteger> nack;
/*     */   @XmlAnyElement(lax = true)
/*     */   protected List<Object> any;
/*     */   @XmlAnyAttribute
/* 148 */   private Map<QName, String> otherAttributes = new HashMap<QName, String>();
/*     */ 
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
/* 160 */     return this.identifier;
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
/* 172 */     this.identifier = value;
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
/*     */   public List<AcknowledgementRange> getAcknowledgementRange() {
/* 198 */     if (this.acknowledgementRange == null) {
/* 199 */       this.acknowledgementRange = new ArrayList<AcknowledgementRange>();
/*     */     }
/* 201 */     return this.acknowledgementRange;
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
/*     */   public None getNone() {
/* 213 */     return this.none;
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
/*     */   public void setNone(None value) {
/* 225 */     this.none = value;
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
/*     */   public Final getFinal() {
/* 237 */     return this._final;
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
/*     */   public void setFinal(Final value) {
/* 249 */     this._final = value;
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
/*     */   public List<BigInteger> getNack() {
/* 275 */     if (this.nack == null) {
/* 276 */       this.nack = new ArrayList<BigInteger>();
/*     */     }
/* 278 */     return this.nack;
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
/* 305 */     if (this.any == null) {
/* 306 */       this.any = new ArrayList();
/*     */     }
/* 308 */     return this.any;
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
/* 326 */     return this.otherAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAccessorType(XmlAccessType.FIELD)
/*     */   @XmlType(name = "")
/*     */   public static class AcknowledgementRange
/*     */   {
/*     */     @XmlAttribute(name = "Upper", required = true)
/*     */     @XmlSchemaType(name = "unsignedLong")
/*     */     protected BigInteger upper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @XmlAttribute(name = "Lower", required = true)
/*     */     @XmlSchemaType(name = "unsignedLong")
/*     */     protected BigInteger lower;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @XmlAnyAttribute
/* 359 */     private Map<QName, String> otherAttributes = new HashMap<QName, String>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BigInteger getUpper() {
/* 371 */       return this.upper;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setUpper(BigInteger value) {
/* 383 */       this.upper = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BigInteger getLower() {
/* 395 */       return this.lower;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setLower(BigInteger value) {
/* 407 */       this.lower = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<QName, String> getOtherAttributes() {
/* 425 */       return this.otherAttributes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAccessorType(XmlAccessType.FIELD)
/*     */   @XmlType(name = "")
/*     */   public static class Final {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAccessorType(XmlAccessType.FIELD)
/*     */   @XmlType(name = "")
/*     */   public static class None {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String idString) {
/* 476 */     Identifier newId = new Identifier();
/* 477 */     newId.setValue(idString);
/* 478 */     setIdentifier(newId);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 482 */     return getIdentifier().getValue();
/*     */   }
/*     */   
/*     */   public int getBufferRemaining() {
/* 486 */     if (this.bufferRemaining == null) {
/* 487 */       return -1;
/*     */     }
/* 489 */     return this.bufferRemaining.intValue();
/*     */   }
/*     */   
/*     */   public void setBufferRemaining(int value) {
/* 493 */     this.bufferRemaining = Integer.valueOf(value);
/*     */   }
/*     */   
/*     */   public void addAckRange(long lower, long upper) {
/* 497 */     if (this.nack != null) {
/* 498 */       throw new IllegalArgumentException(LocalizationMessages.WSRM_4002_BOTH_ACKS_AND_NACKS_MESSAGE());
/*     */     }
/*     */     
/* 501 */     if (lower > upper) {
/* 502 */       throw new IllegalArgumentException(LocalizationMessages.WSRM_4003_UPPERBOUND_LESSTHAN_LOWERBOUND_MESSAGE());
/*     */     }
/*     */ 
/*     */     
/* 506 */     AcknowledgementRange range = new AcknowledgementRange();
/* 507 */     range.setLower(BigInteger.valueOf(lower));
/* 508 */     range.setUpper(BigInteger.valueOf(upper));
/* 509 */     getAcknowledgementRange().add(range);
/*     */   }
/*     */   
/*     */   public void addNack(long index) {
/* 513 */     if (this.acknowledgementRange != null) {
/* 514 */       throw new IllegalArgumentException(LocalizationMessages.WSRM_4002_BOTH_ACKS_AND_NACKS_MESSAGE());
/*     */     }
/*     */     
/* 517 */     getNack().add(BigInteger.valueOf(index));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\wsrm200702\SequenceAcknowledgementElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */