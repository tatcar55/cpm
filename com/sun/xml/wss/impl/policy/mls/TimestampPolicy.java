/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import java.text.SimpleDateFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimestampPolicy
/*     */   extends WSSPolicy
/*     */ {
/*  65 */   private String creationTime = MessageConstants._EMPTY;
/*  66 */   private String expirationTime = MessageConstants._EMPTY;
/*     */   
/*  68 */   private long timeout = 300000L;
/*  69 */   private long maxClockSkew = 300000L;
/*  70 */   private long timestampFreshness = 300000L;
/*     */   
/*  72 */   static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
/*  73 */   static SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimestampPolicy() {
/*  79 */     setPolicyIdentifier("TimestampPolicy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreationTime(String creationTime) {
/*  87 */     this.creationTime = creationTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeout(long timeout) {
/*  97 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxClockSkew(long maxClockSkew) {
/* 106 */     this.maxClockSkew = maxClockSkew;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimestampFreshness(long timestampFreshness) {
/* 116 */     this.timestampFreshness = timestampFreshness;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCreationTime() {
/* 124 */     return this.creationTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimeout() {
/* 131 */     return this.timeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpirationTime() throws Exception {
/* 138 */     if (this.expirationTime.equals("") && this.timeout != 0L && !this.creationTime.equals("")) {
/*     */       try {
/* 140 */         synchronized (formatter) {
/* 141 */           this.expirationTime = Long.toString(formatter.parse(this.creationTime).getTime() + this.timeout);
/*     */         } 
/* 143 */       } catch (Exception e) {
/* 144 */         synchronized (formatter1) {
/* 145 */           this.expirationTime = Long.toString(formatter1.parse(this.creationTime).getTime() + this.timeout);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 151 */     return this.expirationTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpirationTime(String expirationTime) {
/* 158 */     this.expirationTime = expirationTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxClockSkew() {
/* 165 */     return this.maxClockSkew;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimestampFreshness() {
/* 172 */     return this.timestampFreshness;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(WSSPolicy policy) {
/* 181 */     boolean assrt = false;
/*     */     
/*     */     try {
/* 184 */       TimestampPolicy tPolicy = (TimestampPolicy)policy;
/* 185 */       boolean b1 = this.creationTime.equals("") ? true : this.creationTime.equalsIgnoreCase(tPolicy.getCreationTime());
/* 186 */       boolean b2 = getExpirationTime().equals("") ? true : getExpirationTime().equalsIgnoreCase(tPolicy.getExpirationTime());
/* 187 */       assrt = (b1 && b2);
/* 188 */     } catch (Exception e) {}
/*     */     
/* 190 */     return assrt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsIgnoreTargets(WSSPolicy policy) {
/* 199 */     return equals(policy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 207 */     TimestampPolicy tPolicy = new TimestampPolicy();
/*     */     
/*     */     try {
/* 210 */       tPolicy.setTimeout(this.timeout);
/* 211 */       tPolicy.setCreationTime(this.creationTime);
/* 212 */       tPolicy.setExpirationTime(this.expirationTime);
/* 213 */       tPolicy.setMaxClockSkew(this.maxClockSkew);
/* 214 */       tPolicy.setTimestampFreshness(this.timestampFreshness);
/* 215 */     } catch (Exception e) {}
/*     */     
/* 217 */     return tPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 224 */     return "TimestampPolicy";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\TimestampPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */