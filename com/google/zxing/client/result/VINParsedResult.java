/*     */ package com.google.zxing.client.result;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class VINParsedResult
/*     */   extends ParsedResult
/*     */ {
/*     */   private final String vin;
/*     */   private final String worldManufacturerID;
/*     */   private final String vehicleDescriptorSection;
/*     */   private final String vehicleIdentifierSection;
/*     */   private final String countryCode;
/*     */   private final String vehicleAttributes;
/*     */   private final int modelYear;
/*     */   private final char plantCode;
/*     */   private final String sequentialNumber;
/*     */   
/*     */   public VINParsedResult(String vin, String worldManufacturerID, String vehicleDescriptorSection, String vehicleIdentifierSection, String countryCode, String vehicleAttributes, int modelYear, char plantCode, String sequentialNumber) {
/*  42 */     super(ParsedResultType.VIN);
/*  43 */     this.vin = vin;
/*  44 */     this.worldManufacturerID = worldManufacturerID;
/*  45 */     this.vehicleDescriptorSection = vehicleDescriptorSection;
/*  46 */     this.vehicleIdentifierSection = vehicleIdentifierSection;
/*  47 */     this.countryCode = countryCode;
/*  48 */     this.vehicleAttributes = vehicleAttributes;
/*  49 */     this.modelYear = modelYear;
/*  50 */     this.plantCode = plantCode;
/*  51 */     this.sequentialNumber = sequentialNumber;
/*     */   }
/*     */   
/*     */   public String getVIN() {
/*  55 */     return this.vin;
/*     */   }
/*     */   
/*     */   public String getWorldManufacturerID() {
/*  59 */     return this.worldManufacturerID;
/*     */   }
/*     */   
/*     */   public String getVehicleDescriptorSection() {
/*  63 */     return this.vehicleDescriptorSection;
/*     */   }
/*     */   
/*     */   public String getVehicleIdentifierSection() {
/*  67 */     return this.vehicleIdentifierSection;
/*     */   }
/*     */   
/*     */   public String getCountryCode() {
/*  71 */     return this.countryCode;
/*     */   }
/*     */   
/*     */   public String getVehicleAttributes() {
/*  75 */     return this.vehicleAttributes;
/*     */   }
/*     */   
/*     */   public int getModelYear() {
/*  79 */     return this.modelYear;
/*     */   }
/*     */   
/*     */   public char getPlantCode() {
/*  83 */     return this.plantCode;
/*     */   }
/*     */   
/*     */   public String getSequentialNumber() {
/*  87 */     return this.sequentialNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayResult() {
/*  92 */     StringBuilder result = new StringBuilder(50);
/*  93 */     result.append(this.worldManufacturerID).append(' ');
/*  94 */     result.append(this.vehicleDescriptorSection).append(' ');
/*  95 */     result.append(this.vehicleIdentifierSection).append('\n');
/*  96 */     if (this.countryCode != null) {
/*  97 */       result.append(this.countryCode).append(' ');
/*     */     }
/*  99 */     result.append(this.modelYear).append(' ');
/* 100 */     result.append(this.plantCode).append(' ');
/* 101 */     result.append(this.sequentialNumber).append('\n');
/* 102 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\VINParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */