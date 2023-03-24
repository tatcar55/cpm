/*     */ package com.google.zxing.client.result;
/*     */ 
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ExpandedProductParsedResult
/*     */   extends ParsedResult
/*     */ {
/*     */   public static final String KILOGRAM = "KG";
/*     */   public static final String POUND = "LB";
/*     */   private final String rawText;
/*     */   private final String productID;
/*     */   private final String sscc;
/*     */   private final String lotNumber;
/*     */   private final String productionDate;
/*     */   private final String packagingDate;
/*     */   private final String bestBeforeDate;
/*     */   private final String expirationDate;
/*     */   private final String weight;
/*     */   private final String weightType;
/*     */   private final String weightIncrement;
/*     */   private final String price;
/*     */   private final String priceIncrement;
/*     */   private final String priceCurrency;
/*     */   private final Map<String, String> uncommonAIs;
/*     */   
/*     */   public ExpandedProductParsedResult(String rawText, String productID, String sscc, String lotNumber, String productionDate, String packagingDate, String bestBeforeDate, String expirationDate, String weight, String weightType, String weightIncrement, String price, String priceIncrement, String priceCurrency, Map<String, String> uncommonAIs) {
/*  72 */     super(ParsedResultType.PRODUCT);
/*  73 */     this.rawText = rawText;
/*  74 */     this.productID = productID;
/*  75 */     this.sscc = sscc;
/*  76 */     this.lotNumber = lotNumber;
/*  77 */     this.productionDate = productionDate;
/*  78 */     this.packagingDate = packagingDate;
/*  79 */     this.bestBeforeDate = bestBeforeDate;
/*  80 */     this.expirationDate = expirationDate;
/*  81 */     this.weight = weight;
/*  82 */     this.weightType = weightType;
/*  83 */     this.weightIncrement = weightIncrement;
/*  84 */     this.price = price;
/*  85 */     this.priceIncrement = priceIncrement;
/*  86 */     this.priceCurrency = priceCurrency;
/*  87 */     this.uncommonAIs = uncommonAIs;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  92 */     if (!(o instanceof ExpandedProductParsedResult)) {
/*  93 */       return false;
/*     */     }
/*     */     
/*  96 */     ExpandedProductParsedResult other = (ExpandedProductParsedResult)o;
/*     */     
/*  98 */     return (equalsOrNull(this.productID, other.productID) && 
/*  99 */       equalsOrNull(this.sscc, other.sscc) && 
/* 100 */       equalsOrNull(this.lotNumber, other.lotNumber) && 
/* 101 */       equalsOrNull(this.productionDate, other.productionDate) && 
/* 102 */       equalsOrNull(this.bestBeforeDate, other.bestBeforeDate) && 
/* 103 */       equalsOrNull(this.expirationDate, other.expirationDate) && 
/* 104 */       equalsOrNull(this.weight, other.weight) && 
/* 105 */       equalsOrNull(this.weightType, other.weightType) && 
/* 106 */       equalsOrNull(this.weightIncrement, other.weightIncrement) && 
/* 107 */       equalsOrNull(this.price, other.price) && 
/* 108 */       equalsOrNull(this.priceIncrement, other.priceIncrement) && 
/* 109 */       equalsOrNull(this.priceCurrency, other.priceCurrency) && 
/* 110 */       equalsOrNull(this.uncommonAIs, other.uncommonAIs));
/*     */   }
/*     */   
/*     */   private static boolean equalsOrNull(Object o1, Object o2) {
/* 114 */     return (o1 == null) ? ((o2 == null)) : o1.equals(o2);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 119 */     int hash = 0;
/* 120 */     hash ^= hashNotNull(this.productID);
/* 121 */     hash ^= hashNotNull(this.sscc);
/* 122 */     hash ^= hashNotNull(this.lotNumber);
/* 123 */     hash ^= hashNotNull(this.productionDate);
/* 124 */     hash ^= hashNotNull(this.bestBeforeDate);
/* 125 */     hash ^= hashNotNull(this.expirationDate);
/* 126 */     hash ^= hashNotNull(this.weight);
/* 127 */     hash ^= hashNotNull(this.weightType);
/* 128 */     hash ^= hashNotNull(this.weightIncrement);
/* 129 */     hash ^= hashNotNull(this.price);
/* 130 */     hash ^= hashNotNull(this.priceIncrement);
/* 131 */     hash ^= hashNotNull(this.priceCurrency);
/* 132 */     hash ^= hashNotNull(this.uncommonAIs);
/* 133 */     return hash;
/*     */   }
/*     */   
/*     */   private static int hashNotNull(Object o) {
/* 137 */     return (o == null) ? 0 : o.hashCode();
/*     */   }
/*     */   
/*     */   public String getRawText() {
/* 141 */     return this.rawText;
/*     */   }
/*     */   
/*     */   public String getProductID() {
/* 145 */     return this.productID;
/*     */   }
/*     */   
/*     */   public String getSscc() {
/* 149 */     return this.sscc;
/*     */   }
/*     */   
/*     */   public String getLotNumber() {
/* 153 */     return this.lotNumber;
/*     */   }
/*     */   
/*     */   public String getProductionDate() {
/* 157 */     return this.productionDate;
/*     */   }
/*     */   
/*     */   public String getPackagingDate() {
/* 161 */     return this.packagingDate;
/*     */   }
/*     */   
/*     */   public String getBestBeforeDate() {
/* 165 */     return this.bestBeforeDate;
/*     */   }
/*     */   
/*     */   public String getExpirationDate() {
/* 169 */     return this.expirationDate;
/*     */   }
/*     */   
/*     */   public String getWeight() {
/* 173 */     return this.weight;
/*     */   }
/*     */   
/*     */   public String getWeightType() {
/* 177 */     return this.weightType;
/*     */   }
/*     */   
/*     */   public String getWeightIncrement() {
/* 181 */     return this.weightIncrement;
/*     */   }
/*     */   
/*     */   public String getPrice() {
/* 185 */     return this.price;
/*     */   }
/*     */   
/*     */   public String getPriceIncrement() {
/* 189 */     return this.priceIncrement;
/*     */   }
/*     */   
/*     */   public String getPriceCurrency() {
/* 193 */     return this.priceCurrency;
/*     */   }
/*     */   
/*     */   public Map<String, String> getUncommonAIs() {
/* 197 */     return this.uncommonAIs;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayResult() {
/* 202 */     return String.valueOf(this.rawText);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\ExpandedProductParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */