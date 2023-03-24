/*     */ package com.sun.xml.ws.rx.rm.protocol.wsrm200502;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "", propOrder = {"identifier", "acknowledgementRange", "bufferRemaining", "nack", "any"})
/*     */ @XmlRootElement(name = "SequenceAcknowledgement", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */ public class SequenceAcknowledgementElement
/*     */ {
/*     */   @XmlElement(name = "Identifier", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */   protected Identifier identifier;
/*     */   @XmlElement(name = "AcknowledgementRange", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */   protected List<AcknowledgementRange> acknowledgementRange;
/*     */   @XmlElement(name = "Nack", namespace = "http://schemas.xmlsoap.org/ws/2005/02/rm")
/*     */   protected List<BigInteger> nack;
/*     */   @XmlElement(name = "BufferRemaining", namespace = "http://schemas.microsoft.com/ws/2006/05/rm")
/*     */   protected Integer bufferRemaining;
/*     */   @XmlAnyElement(lax = true)
/*     */   protected List<Object> any;
/*     */   @XmlAnyAttribute
/*  81 */   private Map<QName, String> otherAttributes = new HashMap<QName, String>();
/*     */ 
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
/*  93 */     return this.identifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIdentifier(Identifier value) {
/* 102 */     this.identifier = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AcknowledgementRange> getAcknowledgementRange() {
/* 112 */     if (this.acknowledgementRange == null) {
/* 113 */       this.acknowledgementRange = new ArrayList<AcknowledgementRange>();
/*     */     }
/* 115 */     return this.acknowledgementRange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BigInteger> getNack() {
/* 126 */     if (this.nack == null) {
/* 127 */       this.nack = new ArrayList<BigInteger>();
/*     */     }
/* 129 */     return this.nack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Object> getAny() {
/* 139 */     if (this.any == null) {
/* 140 */       this.any = new ArrayList();
/*     */     }
/* 142 */     return this.any;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<QName, String> getOtherAttributes() {
/* 151 */     return this.otherAttributes;
/*     */   }
/*     */   
/*     */   public void setId(String idString) {
/* 155 */     Identifier newId = new Identifier();
/* 156 */     newId.setValue(idString);
/* 157 */     setIdentifier(newId);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 161 */     return getIdentifier().getValue();
/*     */   }
/*     */   
/*     */   public int getBufferRemaining() {
/* 165 */     if (this.bufferRemaining == null) {
/* 166 */       return -1;
/*     */     }
/* 168 */     return this.bufferRemaining.intValue();
/*     */   }
/*     */   
/*     */   public void setBufferRemaining(int value) {
/* 172 */     this.bufferRemaining = Integer.valueOf(value);
/*     */   }
/*     */   
/*     */   public void addAckRange(long lower, long upper) {
/* 176 */     if (this.nack != null) {
/* 177 */       throw new IllegalArgumentException(LocalizationMessages.WSRM_4002_BOTH_ACKS_AND_NACKS_MESSAGE());
/*     */     }
/*     */     
/* 180 */     if (lower > upper) {
/* 181 */       throw new IllegalArgumentException(LocalizationMessages.WSRM_4003_UPPERBOUND_LESSTHAN_LOWERBOUND_MESSAGE());
/*     */     }
/*     */ 
/*     */     
/* 185 */     AcknowledgementRange range = new AcknowledgementRange();
/* 186 */     range.setLower(BigInteger.valueOf(lower));
/* 187 */     range.setUpper(BigInteger.valueOf(upper));
/* 188 */     getAcknowledgementRange().add(range);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addNack(long index) {
/* 193 */     if (this.acknowledgementRange != null) {
/* 194 */       throw new IllegalArgumentException(LocalizationMessages.WSRM_4002_BOTH_ACKS_AND_NACKS_MESSAGE());
/*     */     }
/*     */     
/* 197 */     getNack().add(BigInteger.valueOf(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 202 */     StringBuffer buffer = new StringBuffer();
/* 203 */     buffer.append(LocalizationMessages.WSRM_4004_SEQUENCE_ACKNOWLEDGEMENT_TOSTRING_STRING(getId(), Integer.valueOf(getBufferRemaining())));
/* 204 */     List<AcknowledgementRange> ranges = getAcknowledgementRange();
/* 205 */     if (ranges != null) {
/* 206 */       for (AcknowledgementRange range : ranges) {
/* 207 */         buffer.append("\t\t").append(range.toString()).append('\n');
/*     */       }
/*     */     }
/* 210 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   @XmlAccessorType(XmlAccessType.FIELD)
/*     */   @XmlType(name = "")
/*     */   public static class AcknowledgementRange {
/*     */     @XmlAttribute(name = "Lower", required = true)
/*     */     protected BigInteger lower;
/*     */     @XmlAttribute(name = "Upper", required = true)
/*     */     protected BigInteger upper;
/*     */     @XmlAnyAttribute
/* 221 */     private Map<QName, String> otherAttributes = new HashMap<QName, String>();
/*     */ 
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
/* 233 */       return this.lower;
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
/* 245 */       this.lower = value;
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
/*     */     public BigInteger getUpper() {
/* 257 */       return this.upper;
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
/* 269 */       this.upper = value;
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
/* 287 */       return this.otherAttributes;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 292 */       return "AcknowledgementRange (" + this.lower.intValue() + "," + this.upper.intValue() + ")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\wsrm200502\SequenceAcknowledgementElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */