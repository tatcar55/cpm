/*     */ package com.sun.xml.ws.rx;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
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
/*     */ 
/*     */ public abstract class RxConfigurationBase
/*     */   implements RxConfiguration
/*     */ {
/*     */   private final boolean rmEnabled;
/*     */   private final boolean mcSupportEnabled;
/*     */   private final SOAPVersion soapVersion;
/*     */   private final AddressingVersion addressingVersion;
/*     */   private final boolean requestResponseDetected;
/*     */   private final ManagedObjectManager managedObjectManager;
/*     */   private final HighAvailabilityProvider haProvider;
/*     */   
/*     */   protected RxConfigurationBase(boolean isRmEnabled, boolean isMcEnabled, SOAPVersion soapVersion, AddressingVersion addressingVersion, boolean requestResponseDetected, ManagedObjectManager managedObjectManager, HighAvailabilityProvider haProvider) {
/*  70 */     this.rmEnabled = isRmEnabled;
/*  71 */     this.mcSupportEnabled = isMcEnabled;
/*     */     
/*  73 */     this.soapVersion = soapVersion;
/*  74 */     this.addressingVersion = addressingVersion;
/*  75 */     this.requestResponseDetected = requestResponseDetected;
/*  76 */     this.managedObjectManager = managedObjectManager;
/*  77 */     this.haProvider = haProvider;
/*     */   }
/*     */   
/*     */   public boolean isReliableMessagingEnabled() {
/*  81 */     return this.rmEnabled;
/*     */   }
/*     */   
/*     */   public boolean isMakeConnectionSupportEnabled() {
/*  85 */     return this.mcSupportEnabled;
/*     */   }
/*     */   
/*     */   public SOAPVersion getSoapVersion() {
/*  89 */     return this.soapVersion;
/*     */   }
/*     */   
/*     */   public AddressingVersion getAddressingVersion() {
/*  93 */     return this.addressingVersion;
/*     */   }
/*     */   
/*     */   public boolean requestResponseOperationsDetected() {
/*  97 */     return this.requestResponseDetected;
/*     */   }
/*     */   
/*     */   public ManagedObjectManager getManagedObjectManager() {
/* 101 */     return this.managedObjectManager;
/*     */   }
/*     */   
/*     */   public HighAvailabilityProvider getHighAvailabilityProvider() {
/* 105 */     return this.haProvider;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\RxConfigurationBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */