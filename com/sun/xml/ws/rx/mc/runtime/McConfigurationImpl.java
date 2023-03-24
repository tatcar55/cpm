/*     */ package com.sun.xml.ws.rx.mc.runtime;
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
/*     */ 
/*     */ class McConfigurationImpl
/*     */   extends RxConfigurationBase
/*     */   implements McConfiguration
/*     */ {
/*     */   private final MakeConnectionSupportedFeature mcSupportedFeature;
/*     */   private final McRuntimeVersion runtimeVersion;
/*     */   private final String uniqueEndpointId;
/*     */   
/*     */   McConfigurationImpl(ReliableMessagingFeature rmFeature, MakeConnectionSupportedFeature mcSupportedFeature, String uniqueEndpointId, SOAPVersion soapVersion, AddressingVersion addressingVersion, boolean requestResponseDetected, ManagedObjectManager managedObjectManager, HighAvailabilityProvider haProvider) {
/*  69 */     super((rmFeature != null && rmFeature.isEnabled()), (mcSupportedFeature != null && mcSupportedFeature.isEnabled()), soapVersion, addressingVersion, requestResponseDetected, managedObjectManager, haProvider);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     this.mcSupportedFeature = mcSupportedFeature;
/*  79 */     this.runtimeVersion = (mcSupportedFeature != null) ? McRuntimeVersion.forProtocolVersion(mcSupportedFeature.getProtocolVersion()) : null;
/*  80 */     this.uniqueEndpointId = uniqueEndpointId;
/*     */   }
/*     */   
/*     */   public MakeConnectionSupportedFeature getFeature() {
/*  84 */     checkState();
/*     */     
/*  86 */     return this.mcSupportedFeature;
/*     */   }
/*     */   
/*     */   public McRuntimeVersion getRuntimeVersion() {
/*  90 */     checkState();
/*     */     
/*  92 */     return this.runtimeVersion;
/*     */   }
/*     */   
/*     */   public String getUniqueEndpointId() {
/*  96 */     checkState();
/*     */     
/*  98 */     return this.uniqueEndpointId;
/*     */   }
/*     */   
/*     */   private void checkState() {
/* 102 */     if (this.mcSupportedFeature == null || !this.mcSupportedFeature.isEnabled())
/* 103 */       throw new IllegalStateException("MakeConnectionSupportedFeature is not enabled"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\McConfigurationImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */