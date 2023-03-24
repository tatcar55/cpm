/*     */ package com.sun.xml.wss.impl.callback;
/*     */ 
/*     */ import javax.security.auth.callback.Callback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertyCallback
/*     */   extends XWSSCallback
/*     */   implements Callback
/*     */ {
/*     */   public static final long MAX_NONCE_AGE = 900000L;
/*     */   public static final long MAX_CLOCK_SKEW = 60000L;
/*     */   public static final long TIMESTAMP_FRESHNESS_LIMIT = 300000L;
/*  66 */   long maxSkew = 60000L;
/*  67 */   long freshnessLimit = 300000L;
/*  68 */   long maxNonceAge = 900000L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxClockSkew(long skew) {
/*  74 */     this.maxSkew = skew;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxClockSkew() {
/*  81 */     return this.maxSkew;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimestampFreshnessLimit(long freshnessLimit) {
/*  88 */     this.freshnessLimit = freshnessLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimestampFreshnessLimit() {
/*  95 */     return this.freshnessLimit;
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
/*     */   public void setMaxNonceAge(long maxNonceAge) {
/* 107 */     this.maxNonceAge = maxNonceAge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxNonceAge() {
/* 114 */     return this.maxNonceAge;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\PropertyCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */