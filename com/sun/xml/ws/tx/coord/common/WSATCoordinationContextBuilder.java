/*     */ package com.sun.xml.ws.tx.coord.common;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import com.sun.xml.ws.tx.coord.common.types.CoordinationContextIF;
/*     */ import com.sun.xml.ws.tx.coord.v10.WSATCoordinationContextBuilderImpl;
/*     */ import com.sun.xml.ws.tx.coord.v11.WSATCoordinationContextBuilderImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSATCoordinationContextBuilder
/*     */ {
/*     */   protected String coordinationType;
/*     */   protected String identifier;
/*     */   protected long expires;
/*     */   protected String registrationCoordinatorAddress;
/*     */   protected String txId;
/*     */   protected boolean mustUnderstand = false;
/*  55 */   protected SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */   
/*     */   public static WSATCoordinationContextBuilder newInstance(Transactional.Version version) {
/*  58 */     if (Transactional.Version.WSAT10 == version)
/*  59 */       return (WSATCoordinationContextBuilder)new WSATCoordinationContextBuilderImpl(); 
/*  60 */     if (Transactional.Version.WSAT11 == version || Transactional.Version.WSAT12 == version) {
/*  61 */       return (WSATCoordinationContextBuilder)new WSATCoordinationContextBuilderImpl();
/*     */     }
/*  63 */     throw new IllegalArgumentException(version + "is not a supported ws-at version");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WSATCoordinationContextBuilder txId(String txId) {
/*  69 */     this.txId = txId;
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public WSATCoordinationContextBuilder registrationCoordinatorAddress(String registrationCoordinatorAddress) {
/*  74 */     this.registrationCoordinatorAddress = registrationCoordinatorAddress;
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public WSATCoordinationContextBuilder soapVersion(SOAPVersion soapVersion) {
/*  79 */     if (soapVersion == null)
/*  80 */       throw new IllegalArgumentException("SOAP version can't null!"); 
/*  81 */     this.soapVersion = soapVersion;
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   public WSATCoordinationContextBuilder mustUnderstand(boolean mustUnderstand) {
/*  86 */     this.mustUnderstand = mustUnderstand;
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   public WSATCoordinationContextBuilder expires(long expires) {
/*  91 */     this.expires = expires;
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public CoordinationContextIF build() {
/*  97 */     CoordinationContextBuilder builder = configBuilder();
/*  98 */     return builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   private CoordinationContextBuilder configBuilder() {
/* 103 */     if (this.registrationCoordinatorAddress == null)
/* 104 */       this.registrationCoordinatorAddress = getDefaultRegistrationCoordinatorAddress(); 
/* 105 */     CoordinationContextBuilder builder = newCoordinationContextBuilder();
/* 106 */     builder.coordinationType(getCoordinationType()).address(this.registrationCoordinatorAddress).identifier("urn:uuid:" + this.txId).txId(this.txId).expires(this.expires).soapVersion(this.soapVersion).mustUnderstand(this.mustUnderstand);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     return builder;
/*     */   }
/*     */   
/*     */   protected abstract CoordinationContextBuilder newCoordinationContextBuilder();
/*     */   
/*     */   protected abstract String getCoordinationType();
/*     */   
/*     */   protected abstract String getDefaultRegistrationCoordinatorAddress();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\WSATCoordinationContextBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */