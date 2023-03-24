/*     */ package com.sun.xml.ws.developer;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.transform.Source;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EPRRecipe
/*     */ {
/*  83 */   private final List<Header> referenceParameters = new ArrayList<Header>();
/*  84 */   private final List<Source> metadata = new ArrayList<Source>();
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<Header> getReferenceParameters() {
/*  90 */     return this.referenceParameters;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<Source> getMetadata() {
/*  97 */     return this.metadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EPRRecipe addReferenceParameter(Header h) {
/* 104 */     if (h == null) throw new IllegalArgumentException(); 
/* 105 */     this.referenceParameters.add(h);
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EPRRecipe addReferenceParameters(Header... headers) {
/* 113 */     for (Header h : headers)
/* 114 */       addReferenceParameter(h); 
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EPRRecipe addReferenceParameters(Iterable<? extends Header> headers) {
/* 122 */     for (Header h : headers)
/* 123 */       addReferenceParameter(h); 
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EPRRecipe addMetadata(Source source) {
/* 131 */     if (source == null) throw new IllegalArgumentException(); 
/* 132 */     this.metadata.add(source);
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   public EPRRecipe addMetadata(Source... sources) {
/* 137 */     for (Source s : sources)
/* 138 */       addMetadata(s); 
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   public EPRRecipe addMetadata(Iterable<? extends Source> sources) {
/* 143 */     for (Source s : sources)
/* 144 */       addMetadata(s); 
/* 145 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\EPRRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */