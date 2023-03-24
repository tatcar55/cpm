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
/*     */ 
/*     */ public class TimestampValidationCallback
/*     */   extends XWSSCallback
/*     */   implements Callback
/*     */ {
/*     */   private Request request;
/*     */   private TimestampValidator validator;
/*     */   
/*     */   public TimestampValidationCallback(Request request) {
/*  68 */     this.request = request;
/*     */   }
/*     */   
/*     */   public void getResult() throws TimestampValidationException {
/*  72 */     if (this.validator == null) {
/*  73 */       throw new TimestampValidationException("A Required TimestampValidator object was not set by the CallbackHandler");
/*     */     }
/*  75 */     this.validator.validate(this.request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValidator(TimestampValidator validator) {
/*  83 */     this.validator = validator;
/*  84 */     if (this.validator instanceof ValidatorExtension) {
/*  85 */       ((ValidatorExtension)this.validator).setRuntimeProperties(getRuntimeProperties());
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Request {}
/*     */   
/*     */   public static class UTCTimestampRequest
/*     */     implements Request
/*     */   {
/*     */     private String created;
/*     */     private String expired;
/*  96 */     private long maxClockSkew = 0L;
/*  97 */     private long timestampFreshnessLimit = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isUsernameToken = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void isUsernameToken(boolean isUsernameToken) {
/* 108 */       this.isUsernameToken = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isUsernameToken() {
/* 117 */       return this.isUsernameToken;
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
/*     */     
/*     */     public UTCTimestampRequest(String created, String expired, long maxClockSkew, long timestampFreshnessLimit) {
/* 136 */       this.created = created;
/* 137 */       this.expired = expired;
/* 138 */       this.maxClockSkew = maxClockSkew;
/* 139 */       this.timestampFreshnessLimit = timestampFreshnessLimit;
/*     */     }
/*     */     
/*     */     public String getCreated() {
/* 143 */       return this.created;
/*     */     }
/*     */     
/*     */     public String getExpired() {
/* 147 */       return this.expired;
/*     */     }
/*     */     
/*     */     public long getMaxClockSkew() {
/* 151 */       return this.maxClockSkew;
/*     */     }
/*     */     
/*     */     public long getTimestampFreshnessLimit() {
/* 155 */       return this.timestampFreshnessLimit;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class TimestampValidationException
/*     */     extends Exception {
/*     */     public TimestampValidationException(String message) {
/* 162 */       super(message);
/*     */     }
/*     */     
/*     */     public TimestampValidationException(String message, Throwable cause) {
/* 166 */       super(message, cause);
/*     */     }
/*     */     
/*     */     public TimestampValidationException(Throwable cause) {
/* 170 */       super(cause);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface TimestampValidator {
/*     */     void validate(TimestampValidationCallback.Request param1Request) throws TimestampValidationCallback.TimestampValidationException;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\TimestampValidationCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */