/*     */ package com.google.zxing.pdf417.decoder.ec;
/*     */ 
/*     */ import com.google.zxing.ChecksumException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ErrorCorrection
/*     */ {
/*  35 */   private final ModulusGF field = ModulusGF.PDF417_GF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int decode(int[] received, int numECCodewords, int[] erasures) throws ChecksumException {
/*  49 */     ModulusPoly poly = new ModulusPoly(this.field, received);
/*  50 */     int[] S = new int[numECCodewords];
/*  51 */     boolean error = false;
/*  52 */     for (int i = numECCodewords; i > 0; i--) {
/*  53 */       int eval = poly.evaluateAt(this.field.exp(i));
/*  54 */       S[numECCodewords - i] = eval;
/*  55 */       if (eval != 0) {
/*  56 */         error = true;
/*     */       }
/*     */     } 
/*     */     
/*  60 */     if (!error) {
/*  61 */       return 0;
/*     */     }
/*     */     
/*  64 */     ModulusPoly knownErrors = this.field.getOne();
/*  65 */     if (erasures != null) {
/*  66 */       for (int erasure : erasures) {
/*  67 */         int b = this.field.exp(received.length - 1 - erasure);
/*     */         
/*  69 */         ModulusPoly term = new ModulusPoly(this.field, new int[] { this.field.subtract(0, b), 1 });
/*  70 */         knownErrors = knownErrors.multiply(term);
/*     */       } 
/*     */     }
/*     */     
/*  74 */     ModulusPoly syndrome = new ModulusPoly(this.field, S);
/*     */ 
/*     */ 
/*     */     
/*  78 */     ModulusPoly[] sigmaOmega = runEuclideanAlgorithm(this.field.buildMonomial(numECCodewords, 1), syndrome, numECCodewords);
/*  79 */     ModulusPoly sigma = sigmaOmega[0];
/*  80 */     ModulusPoly omega = sigmaOmega[1];
/*     */ 
/*     */ 
/*     */     
/*  84 */     int[] errorLocations = findErrorLocations(sigma);
/*  85 */     int[] errorMagnitudes = findErrorMagnitudes(omega, sigma, errorLocations);
/*     */     
/*  87 */     for (int j = 0; j < errorLocations.length; j++) {
/*  88 */       int position = received.length - 1 - this.field.log(errorLocations[j]);
/*  89 */       if (position < 0) {
/*  90 */         throw ChecksumException.getChecksumInstance();
/*     */       }
/*  92 */       received[position] = this.field.subtract(received[position], errorMagnitudes[j]);
/*     */     } 
/*  94 */     return errorLocations.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ModulusPoly[] runEuclideanAlgorithm(ModulusPoly a, ModulusPoly b, int R) throws ChecksumException {
/* 100 */     if (a.getDegree() < b.getDegree()) {
/* 101 */       ModulusPoly temp = a;
/* 102 */       a = b;
/* 103 */       b = temp;
/*     */     } 
/*     */     
/* 106 */     ModulusPoly rLast = a;
/* 107 */     ModulusPoly r = b;
/* 108 */     ModulusPoly tLast = this.field.getZero();
/* 109 */     ModulusPoly t = this.field.getOne();
/*     */ 
/*     */     
/* 112 */     while (r.getDegree() >= R / 2) {
/* 113 */       ModulusPoly rLastLast = rLast;
/* 114 */       ModulusPoly tLastLast = tLast;
/* 115 */       rLast = r;
/* 116 */       tLast = t;
/*     */ 
/*     */       
/* 119 */       if (rLast.isZero())
/*     */       {
/* 121 */         throw ChecksumException.getChecksumInstance();
/*     */       }
/* 123 */       r = rLastLast;
/* 124 */       ModulusPoly q = this.field.getZero();
/* 125 */       int denominatorLeadingTerm = rLast.getCoefficient(rLast.getDegree());
/* 126 */       int dltInverse = this.field.inverse(denominatorLeadingTerm);
/* 127 */       while (r.getDegree() >= rLast.getDegree() && !r.isZero()) {
/* 128 */         int degreeDiff = r.getDegree() - rLast.getDegree();
/* 129 */         int scale = this.field.multiply(r.getCoefficient(r.getDegree()), dltInverse);
/* 130 */         q = q.add(this.field.buildMonomial(degreeDiff, scale));
/* 131 */         r = r.subtract(rLast.multiplyByMonomial(degreeDiff, scale));
/*     */       } 
/*     */       
/* 134 */       t = q.multiply(tLast).subtract(tLastLast).negative();
/*     */     } 
/*     */     
/* 137 */     int sigmaTildeAtZero = t.getCoefficient(0);
/* 138 */     if (sigmaTildeAtZero == 0) {
/* 139 */       throw ChecksumException.getChecksumInstance();
/*     */     }
/*     */     
/* 142 */     int inverse = this.field.inverse(sigmaTildeAtZero);
/* 143 */     ModulusPoly sigma = t.multiply(inverse);
/* 144 */     ModulusPoly omega = r.multiply(inverse);
/* 145 */     return new ModulusPoly[] { sigma, omega };
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] findErrorLocations(ModulusPoly errorLocator) throws ChecksumException {
/* 150 */     int numErrors = errorLocator.getDegree();
/* 151 */     int[] result = new int[numErrors];
/* 152 */     int e = 0;
/* 153 */     for (int i = 1; i < this.field.getSize() && e < numErrors; i++) {
/* 154 */       if (errorLocator.evaluateAt(i) == 0) {
/* 155 */         result[e] = this.field.inverse(i);
/* 156 */         e++;
/*     */       } 
/*     */     } 
/* 159 */     if (e != numErrors) {
/* 160 */       throw ChecksumException.getChecksumInstance();
/*     */     }
/* 162 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] findErrorMagnitudes(ModulusPoly errorEvaluator, ModulusPoly errorLocator, int[] errorLocations) {
/* 168 */     int errorLocatorDegree = errorLocator.getDegree();
/* 169 */     int[] formalDerivativeCoefficients = new int[errorLocatorDegree];
/* 170 */     for (int i = 1; i <= errorLocatorDegree; i++) {
/* 171 */       formalDerivativeCoefficients[errorLocatorDegree - i] = this.field
/* 172 */         .multiply(i, errorLocator.getCoefficient(i));
/*     */     }
/* 174 */     ModulusPoly formalDerivative = new ModulusPoly(this.field, formalDerivativeCoefficients);
/*     */ 
/*     */     
/* 177 */     int s = errorLocations.length;
/* 178 */     int[] result = new int[s];
/* 179 */     for (int j = 0; j < s; j++) {
/* 180 */       int xiInverse = this.field.inverse(errorLocations[j]);
/* 181 */       int numerator = this.field.subtract(0, errorEvaluator.evaluateAt(xiInverse));
/* 182 */       int denominator = this.field.inverse(formalDerivative.evaluateAt(xiInverse));
/* 183 */       result[j] = this.field.multiply(numerator, denominator);
/*     */     } 
/* 185 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\pdf417\decoder\ec\ErrorCorrection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */