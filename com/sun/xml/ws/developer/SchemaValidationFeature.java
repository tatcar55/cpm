/*     */ package com.sun.xml.ws.developer;
/*     */ 
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import com.sun.xml.ws.server.DraconianValidationErrorHandler;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class SchemaValidationFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "http://jax-ws.dev.java.net/features/schema-validation";
/*     */   private final Class<? extends ValidationErrorHandler> clazz;
/*     */   private final boolean inbound;
/*     */   private final boolean outbound;
/*     */   
/*     */   public SchemaValidationFeature() {
/*  70 */     this(true, true, (Class)DraconianValidationErrorHandler.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SchemaValidationFeature(Class<? extends ValidationErrorHandler> clazz) {
/*  78 */     this(true, true, clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SchemaValidationFeature(boolean inbound, boolean outbound) {
/*  85 */     this(inbound, outbound, (Class)DraconianValidationErrorHandler.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FeatureConstructor({"inbound", "outbound", "handler"})
/*     */   public SchemaValidationFeature(boolean inbound, boolean outbound, Class<? extends ValidationErrorHandler> clazz) {
/*  93 */     this.enabled = true;
/*  94 */     this.inbound = inbound;
/*  95 */     this.outbound = outbound;
/*  96 */     this.clazz = clazz;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/* 102 */     return "http://jax-ws.dev.java.net/features/schema-validation";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public Class<? extends ValidationErrorHandler> getErrorHandler() {
/* 112 */     return this.clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInbound() {
/* 121 */     return this.inbound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOutbound() {
/* 130 */     return this.outbound;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\SchemaValidationFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */