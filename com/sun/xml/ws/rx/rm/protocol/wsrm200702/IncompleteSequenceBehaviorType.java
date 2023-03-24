/*     */ package com.sun.xml.ws.rx.rm.protocol.wsrm200702;
/*     */ 
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import javax.xml.bind.annotation.XmlEnum;
/*     */ import javax.xml.bind.annotation.XmlEnumValue;
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
/*     */ @XmlType(name = "IncompleteSequenceBehaviorType")
/*     */ @XmlEnum
/*     */ public enum IncompleteSequenceBehaviorType
/*     */ {
/*  68 */   DISCARD_ENTIRE_SEQUENCE("DiscardEntireSequence", Sequence.IncompleteSequenceBehavior.DISCARD_ENTIRE_SEQUENCE),
/*     */ 
/*     */   
/*  71 */   DISCARD_FOLLOWING_FIRST_GAP("DiscardFollowingFirstGap", Sequence.IncompleteSequenceBehavior.DISCARD_FOLLOWING_FIRST_GAP),
/*     */ 
/*     */   
/*  74 */   NO_DISCARD("NoDiscard", Sequence.IncompleteSequenceBehavior.NO_DISCARD);
/*     */   
/*     */   private final String value;
/*     */   
/*     */   private final Sequence.IncompleteSequenceBehavior translation;
/*     */   
/*     */   IncompleteSequenceBehaviorType(String v, Sequence.IncompleteSequenceBehavior translation) {
/*  81 */     this.value = v;
/*  82 */     this.translation = translation;
/*     */   }
/*     */   
/*     */   public String value() {
/*  86 */     return this.value;
/*     */   }
/*     */   
/*     */   public static IncompleteSequenceBehaviorType fromValue(String v) {
/*  90 */     for (IncompleteSequenceBehaviorType c : values()) {
/*  91 */       if (c.value.equals(v)) {
/*  92 */         return c;
/*     */       }
/*     */     } 
/*  95 */     throw new IllegalArgumentException(v);
/*     */   }
/*     */   
/*     */   public static IncompleteSequenceBehaviorType fromISB(Sequence.IncompleteSequenceBehavior v) {
/*  99 */     for (IncompleteSequenceBehaviorType c : values()) {
/* 100 */       if (c.translation == v) {
/* 101 */         return c;
/*     */       }
/*     */     } 
/* 104 */     throw new IllegalArgumentException(v.toString());
/*     */   }
/*     */   
/*     */   public Sequence.IncompleteSequenceBehavior translate() {
/* 108 */     return this.translation;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\wsrm200702\IncompleteSequenceBehaviorType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */