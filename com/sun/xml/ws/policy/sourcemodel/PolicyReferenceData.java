/*     */ package com.sun.xml.ws.policy.sourcemodel;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PolicyReferenceData
/*     */ {
/*  54 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyReferenceData.class);
/*     */   private static final URI DEFAULT_DIGEST_ALGORITHM_URI;
/*     */   private static final URISyntaxException CLASS_INITIALIZATION_EXCEPTION;
/*     */   
/*     */   static {
/*  59 */     URISyntaxException tempEx = null;
/*  60 */     URI tempUri = null;
/*     */     try {
/*  62 */       tempUri = new URI("http://schemas.xmlsoap.org/ws/2004/09/policy/Sha1Exc");
/*  63 */     } catch (URISyntaxException e) {
/*  64 */       tempEx = e;
/*     */     } finally {
/*  66 */       DEFAULT_DIGEST_ALGORITHM_URI = tempUri;
/*  67 */       CLASS_INITIALIZATION_EXCEPTION = tempEx;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final URI referencedModelUri;
/*     */   private final String digest;
/*     */   private final URI digestAlgorithmUri;
/*     */   
/*     */   public PolicyReferenceData(URI referencedModelUri) {
/*  77 */     this.referencedModelUri = referencedModelUri;
/*  78 */     this.digest = null;
/*  79 */     this.digestAlgorithmUri = null;
/*     */   }
/*     */   
/*     */   public PolicyReferenceData(URI referencedModelUri, String expectedDigest, URI usedDigestAlgorithm) {
/*  83 */     if (CLASS_INITIALIZATION_EXCEPTION != null) {
/*  84 */       throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(LocalizationMessages.WSP_0015_UNABLE_TO_INSTANTIATE_DIGEST_ALG_URI_FIELD(), CLASS_INITIALIZATION_EXCEPTION));
/*     */     }
/*     */     
/*  87 */     if (usedDigestAlgorithm != null && expectedDigest == null) {
/*  88 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0072_DIGEST_MUST_NOT_BE_NULL_WHEN_ALG_DEFINED()));
/*     */     }
/*     */     
/*  91 */     this.referencedModelUri = referencedModelUri;
/*  92 */     if (expectedDigest == null) {
/*  93 */       this.digest = null;
/*  94 */       this.digestAlgorithmUri = null;
/*     */     } else {
/*  96 */       this.digest = expectedDigest;
/*     */       
/*  98 */       if (usedDigestAlgorithm == null) {
/*  99 */         this.digestAlgorithmUri = DEFAULT_DIGEST_ALGORITHM_URI;
/*     */       } else {
/* 101 */         this.digestAlgorithmUri = usedDigestAlgorithm;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public URI getReferencedModelUri() {
/* 107 */     return this.referencedModelUri;
/*     */   }
/*     */   
/*     */   public String getDigest() {
/* 111 */     return this.digest;
/*     */   }
/*     */   
/*     */   public URI getDigestAlgorithmUri() {
/* 115 */     return this.digestAlgorithmUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 123 */     return toString(0, new StringBuffer()).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer toString(int indentLevel, StringBuffer buffer) {
/* 134 */     String indent = PolicyUtils.Text.createIndent(indentLevel);
/* 135 */     String innerIndent = PolicyUtils.Text.createIndent(indentLevel + 1);
/*     */     
/* 137 */     buffer.append(indent).append("reference data {").append(PolicyUtils.Text.NEW_LINE);
/* 138 */     buffer.append(innerIndent).append("referenced policy model URI = '").append(this.referencedModelUri).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 139 */     if (this.digest == null) {
/* 140 */       buffer.append(innerIndent).append("no digest specified").append(PolicyUtils.Text.NEW_LINE);
/*     */     } else {
/* 142 */       buffer.append(innerIndent).append("digest algorith URI = '").append(this.digestAlgorithmUri).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 143 */       buffer.append(innerIndent).append("digest = '").append(this.digest).append('\'').append(PolicyUtils.Text.NEW_LINE);
/*     */     } 
/* 145 */     buffer.append(indent).append('}');
/*     */     
/* 147 */     return buffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\PolicyReferenceData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */