/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.rx.RxConfigurationBase;
/*     */ import com.sun.xml.ws.rx.mc.api.MakeConnectionSupportedFeature;
/*     */ import com.sun.xml.ws.rx.rm.api.ReliableMessagingFeature;
/*     */ import org.glassfish.gmbal.ManagedObjectManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RmConfigurationImpl
/*     */   extends RxConfigurationBase
/*     */   implements RmConfiguration
/*     */ {
/*     */   private final ReliableMessagingFeature rmFeature;
/*     */   private final RmRuntimeVersion runtimeVersion;
/*     */   
/*     */   RmConfigurationImpl(ReliableMessagingFeature rmFeature, MakeConnectionSupportedFeature mcSupportedFeature, SOAPVersion soapVersion, AddressingVersion addressingVersion, boolean requestResponseDetected, ManagedObjectManager managedObjectManager, HighAvailabilityProvider haProvider) {
/*  67 */     super((rmFeature != null && rmFeature.isEnabled()), (mcSupportedFeature != null && mcSupportedFeature.isEnabled()), soapVersion, addressingVersion, requestResponseDetected, managedObjectManager, haProvider);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     this.rmFeature = rmFeature;
/*  77 */     this.runtimeVersion = (rmFeature != null) ? RmRuntimeVersion.forProtocolVersion(rmFeature.getProtocolVersion()) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeature getRmFeature() {
/*  82 */     checkState();
/*     */     
/*  84 */     return this.rmFeature;
/*     */   }
/*     */   
/*     */   public RmRuntimeVersion getRuntimeVersion() {
/*  88 */     checkState();
/*     */     
/*  90 */     return this.runtimeVersion;
/*     */   }
/*     */   
/*     */   private void checkState() {
/*  94 */     if (this.rmFeature == null || !this.rmFeature.isEnabled()) {
/*  95 */       throw new IllegalStateException("Reliable messaging feature is not enabled");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "RmConfigurationImpl{\nrmFeature=" + this.rmFeature + ",\nruntimeVersion=" + this.runtimeVersion + "\n}";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\RmConfigurationImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */