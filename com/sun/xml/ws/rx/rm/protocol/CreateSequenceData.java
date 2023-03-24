/*     */ package com.sun.xml.ws.rx.rm.protocol;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
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
/*     */ public class CreateSequenceData
/*     */ {
/*     */   @NotNull
/*     */   private final EndpointReference acksToEpr;
/*     */   private final long duration;
/*     */   @Nullable
/*     */   private final String offeredSequenceId;
/*     */   private final long offeredSequenceExpiry;
/*     */   @Nullable
/*     */   private final SecurityTokenReferenceType strType;
/*     */   @NotNull
/*     */   private final Sequence.IncompleteSequenceBehavior offeredSequenceIncompleteBehavior;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @NotNull
/*     */     private final EndpointReference acksToEpr;
/*     */     private long duration;
/*     */     @Nullable
/*     */     private SecurityTokenReferenceType strType;
/*     */     @Nullable
/*     */     private String offeredSequenceId;
/*     */     private long offeredSequenceExpiry;
/*     */     private Sequence.IncompleteSequenceBehavior offeredSequenceIncompleteBehavior;
/*     */     
/*     */     private Builder(EndpointReference acksToEpr) {
/*  64 */       this.acksToEpr = acksToEpr;
/*  65 */       this.duration = -1L;
/*  66 */       this.offeredSequenceExpiry = -1L;
/*  67 */       this.offeredSequenceIncompleteBehavior = Sequence.IncompleteSequenceBehavior.getDefault();
/*     */     }
/*     */     
/*     */     public void duration(long expiry) {
/*  71 */       this.duration = expiry;
/*     */     }
/*     */     
/*     */     public Builder strType(SecurityTokenReferenceType value) {
/*  75 */       this.strType = value;
/*     */       
/*  77 */       return this;
/*     */     }
/*     */     
/*     */     public void offeredSequenceExpiry(long offeredSequenceExpiry) {
/*  81 */       this.offeredSequenceExpiry = offeredSequenceExpiry;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder offeredInboundSequenceId(String value) {
/*  86 */       this.offeredSequenceId = value;
/*     */       
/*  88 */       return this;
/*     */     }
/*     */     
/*     */     public void offeredSequenceIncompleteBehavior(Sequence.IncompleteSequenceBehavior value) {
/*  92 */       this.offeredSequenceIncompleteBehavior = value;
/*     */     }
/*     */     
/*     */     public CreateSequenceData build() {
/*  96 */       return new CreateSequenceData(this.acksToEpr, this.duration, this.strType, this.offeredSequenceId, this.offeredSequenceExpiry, this.offeredSequenceIncompleteBehavior);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder getBuilder(EndpointReference acksToEpr) {
/* 101 */     return new Builder(acksToEpr);
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
/*     */   private CreateSequenceData(@NotNull EndpointReference acksToEpr, @Nullable long exipry, @Nullable SecurityTokenReferenceType strType, @Nullable String offeredSequenceId, @Nullable long offeredSequenceExpiry, @NotNull Sequence.IncompleteSequenceBehavior offeredSequenceIncompleteBehavior) {
/* 118 */     this.acksToEpr = acksToEpr;
/* 119 */     this.duration = exipry;
/* 120 */     this.offeredSequenceId = offeredSequenceId;
/* 121 */     this.offeredSequenceExpiry = offeredSequenceExpiry;
/* 122 */     this.strType = strType;
/* 123 */     this.offeredSequenceIncompleteBehavior = offeredSequenceIncompleteBehavior;
/*     */   }
/*     */   @NotNull
/*     */   public EndpointReference getAcksToEpr() {
/* 127 */     return this.acksToEpr;
/*     */   }
/*     */   
/*     */   public long getDuration() {
/* 131 */     return this.duration;
/*     */   }
/*     */   
/*     */   public boolean doesNotExpire() {
/* 135 */     return (this.duration == -1L);
/*     */   }
/*     */   @Nullable
/*     */   public SecurityTokenReferenceType getStrType() {
/* 139 */     return this.strType;
/*     */   }
/*     */   @Nullable
/*     */   public String getOfferedSequenceId() {
/* 143 */     return this.offeredSequenceId;
/*     */   }
/*     */   
/*     */   public long getOfferedSequenceExpiry() {
/* 147 */     return this.offeredSequenceExpiry;
/*     */   }
/*     */   
/*     */   public boolean offeredSequenceDoesNotExpire() {
/* 151 */     return (this.offeredSequenceExpiry == -1L);
/*     */   }
/*     */   
/*     */   public Sequence.IncompleteSequenceBehavior getOfferedSequenceIncompleteBehavior() {
/* 155 */     return this.offeredSequenceIncompleteBehavior;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\CreateSequenceData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */