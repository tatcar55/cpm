/*     */ package com.sun.xml.ws.rx.rm.protocol;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AcknowledgementData
/*     */ {
/*     */   private final String ackedSequenceId;
/*     */   private final List<Sequence.AckRange> ackedRanges;
/*     */   private final String ackRequestedSequenceId;
/*     */   private final boolean isFinalAcknowledgement;
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private String ackedSequenceId;
/*     */     private List<Sequence.AckRange> ackedRanges;
/*     */     private String ackRequestedSequenceId;
/*     */     private boolean isFinalAcknowledgement;
/*     */     
/*     */     private Builder() {}
/*     */     
/*     */     private Builder(AcknowledgementData data) {
/*  63 */       this.ackRequestedSequenceId = data.ackRequestedSequenceId;
/*  64 */       this.ackedRanges = data.ackedRanges;
/*  65 */       this.ackedSequenceId = data.ackedSequenceId;
/*  66 */       this.isFinalAcknowledgement = data.isFinalAcknowledgement;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder acknowledgements(@NotNull String ackedSequenceId, List<Sequence.AckRange> acknowledgedMessageIds, boolean isFinal) {
/*  77 */       assert ackedSequenceId != null;
/*     */       
/*  79 */       this.ackedSequenceId = ackedSequenceId;
/*  80 */       this.ackedRanges = acknowledgedMessageIds;
/*  81 */       this.isFinalAcknowledgement = isFinal;
/*     */       
/*  83 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder ackReqestedSequenceId(@NotNull String ackRequestedSequenceId) {
/*  92 */       assert ackRequestedSequenceId != null;
/*     */       
/*  94 */       this.ackRequestedSequenceId = ackRequestedSequenceId;
/*     */       
/*  96 */       return this;
/*     */     }
/*     */     
/*     */     public AcknowledgementData build() {
/* 100 */       return new AcknowledgementData(this.ackedSequenceId, this.ackedRanges, this.ackRequestedSequenceId, this.isFinalAcknowledgement);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder getBuilder() {
/* 105 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static Builder getBuilder(AcknowledgementData data) {
/* 109 */     return new Builder(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AcknowledgementData(String ackedSequenceId, List<Sequence.AckRange> ackedRanges, String ackRequestedSequenceId, boolean isFinal) {
/* 118 */     this.ackedSequenceId = ackedSequenceId;
/* 119 */     this.ackedRanges = ackedRanges;
/* 120 */     this.ackRequestedSequenceId = ackRequestedSequenceId;
/* 121 */     this.isFinalAcknowledgement = isFinal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAcknowledgedSequenceId() {
/* 129 */     return this.ackedSequenceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<Sequence.AckRange> getAcknowledgedRanges() {
/* 138 */     if (this.ackedRanges != null) {
/* 139 */       return this.ackedRanges;
/*     */     }
/* 141 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAckReqestedSequenceId() {
/* 151 */     return this.ackRequestedSequenceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFinalAcknowledgement() {
/* 160 */     return this.isFinalAcknowledgement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsSequenceAcknowledgementData() {
/* 171 */     return (this.ackedSequenceId != null && this.ackedRanges != null && !this.ackedRanges.isEmpty());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\AcknowledgementData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */