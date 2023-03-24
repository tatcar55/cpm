/*     */ package com.sun.xml.ws.developer;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedData;
/*     */ import org.jvnet.mimepull.MIMEConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedData
/*     */ public final class StreamingAttachmentFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "http://jax-ws.dev.java.net/features/mime";
/*     */   private MIMEConfig config;
/*     */   private String dir;
/*     */   private boolean parseEagerly;
/*     */   private long memoryThreshold;
/*     */   
/*     */   public StreamingAttachmentFeature() {}
/*     */   
/*     */   @FeatureConstructor({"dir", "parseEagerly", "memoryThreshold"})
/*     */   public StreamingAttachmentFeature(@Nullable String dir, boolean parseEagerly, long memoryThreshold) {
/*  88 */     this.enabled = true;
/*  89 */     this.dir = dir;
/*  90 */     this.parseEagerly = parseEagerly;
/*  91 */     this.memoryThreshold = memoryThreshold;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/*  96 */     return "http://jax-ws.dev.java.net/features/mime";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public MIMEConfig getConfig() {
/* 107 */     if (this.config == null) {
/* 108 */       this.config = new MIMEConfig();
/* 109 */       this.config.setDir(this.dir);
/* 110 */       this.config.setParseEagerly(this.parseEagerly);
/* 111 */       this.config.setMemoryThreshold(this.memoryThreshold);
/* 112 */       this.config.validate();
/*     */     } 
/* 114 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDir(String dir) {
/* 121 */     this.dir = dir;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParseEagerly(boolean parseEagerly) {
/* 128 */     this.parseEagerly = parseEagerly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMemoryThreshold(long memoryThreshold) {
/* 136 */     this.memoryThreshold = memoryThreshold;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\StreamingAttachmentFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */