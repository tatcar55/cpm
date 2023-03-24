/*     */ package com.sun.xml.ws.rx.mc.api;
/*     */ 
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import com.sun.xml.ws.api.ha.StickyFeature;
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
/*     */ 
/*     */ @ManagedData
/*     */ public class MakeConnectionSupportedFeature
/*     */   extends WebServiceFeature
/*     */   implements StickyFeature
/*     */ {
/*     */   public static final String ID = "http://docs.oasis-open.org/ws-rx/wsmc/";
/*     */   public static final long DEFAULT_RESPONSE_RETRIEVAL_TIMEOUT = 600000L;
/*     */   public static final long DEFAULT_MAKE_CONNECTION_REQUEST_INTERVAL = 2000L;
/*     */   private final long responseRetrievalTimeout;
/*     */   private final long mcRequestBaseInterval;
/*     */   
/*     */   public MakeConnectionSupportedFeature() {
/*  73 */     this(true, 2000L, 600000L);
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
/*     */   @FeatureConstructor({"enabled"})
/*     */   public MakeConnectionSupportedFeature(boolean enabled) {
/*  86 */     this(enabled, 2000L, 600000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MakeConnectionSupportedFeature(boolean enabled, long mcRequestBaseInterval, long responseRetrievalTimeout) {
/*  97 */     this.enabled = enabled;
/*     */     
/*  99 */     this.mcRequestBaseInterval = mcRequestBaseInterval;
/* 100 */     this.responseRetrievalTimeout = responseRetrievalTimeout;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/* 106 */     return "http://docs.oasis-open.org/ws-rx/wsmc/";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public McProtocolVersion getProtocolVersion() {
/* 116 */     return McProtocolVersion.WSMC200702;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getResponseRetrievalTimeout() {
/* 127 */     return this.responseRetrievalTimeout;
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
/*     */   public long getBaseMakeConnectionRequetsInterval() {
/* 139 */     return this.mcRequestBaseInterval;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\api\MakeConnectionSupportedFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */