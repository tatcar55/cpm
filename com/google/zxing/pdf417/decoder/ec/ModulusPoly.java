/*     */ package com.google.zxing.pdf417.decoder.ec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ModulusPoly
/*     */ {
/*     */   private final ModulusGF field;
/*     */   private final int[] coefficients;
/*     */   
/*     */   ModulusPoly(ModulusGF field, int[] coefficients) {
/*  29 */     if (coefficients.length == 0) {
/*  30 */       throw new IllegalArgumentException();
/*     */     }
/*  32 */     this.field = field;
/*  33 */     int coefficientsLength = coefficients.length;
/*  34 */     if (coefficientsLength > 1 && coefficients[0] == 0) {
/*     */       
/*  36 */       int firstNonZero = 1;
/*  37 */       while (firstNonZero < coefficientsLength && coefficients[firstNonZero] == 0) {
/*  38 */         firstNonZero++;
/*     */       }
/*  40 */       if (firstNonZero == coefficientsLength) {
/*  41 */         this.coefficients = new int[] { 0 };
/*     */       } else {
/*  43 */         this.coefficients = new int[coefficientsLength - firstNonZero];
/*  44 */         System.arraycopy(coefficients, firstNonZero, this.coefficients, 0, this.coefficients.length);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  51 */       this.coefficients = coefficients;
/*     */     } 
/*     */   }
/*     */   
/*     */   int[] getCoefficients() {
/*  56 */     return this.coefficients;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDegree() {
/*  63 */     return this.coefficients.length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isZero() {
/*  70 */     return (this.coefficients[0] == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getCoefficient(int degree) {
/*  77 */     return this.coefficients[this.coefficients.length - 1 - degree];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int evaluateAt(int a) {
/*  84 */     if (a == 0)
/*     */     {
/*  86 */       return getCoefficient(0);
/*     */     }
/*  88 */     int size = this.coefficients.length;
/*  89 */     if (a == 1) {
/*     */       
/*  91 */       int j = 0;
/*  92 */       for (int coefficient : this.coefficients) {
/*  93 */         j = this.field.add(j, coefficient);
/*     */       }
/*  95 */       return j;
/*     */     } 
/*  97 */     int result = this.coefficients[0];
/*  98 */     for (int i = 1; i < size; i++) {
/*  99 */       result = this.field.add(this.field.multiply(a, result), this.coefficients[i]);
/*     */     }
/* 101 */     return result;
/*     */   }
/*     */   
/*     */   ModulusPoly add(ModulusPoly other) {
/* 105 */     if (!this.field.equals(other.field)) {
/* 106 */       throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
/*     */     }
/* 108 */     if (isZero()) {
/* 109 */       return other;
/*     */     }
/* 111 */     if (other.isZero()) {
/* 112 */       return this;
/*     */     }
/*     */     
/* 115 */     int[] smallerCoefficients = this.coefficients;
/* 116 */     int[] largerCoefficients = other.coefficients;
/* 117 */     if (smallerCoefficients.length > largerCoefficients.length) {
/* 118 */       int[] temp = smallerCoefficients;
/* 119 */       smallerCoefficients = largerCoefficients;
/* 120 */       largerCoefficients = temp;
/*     */     } 
/* 122 */     int[] sumDiff = new int[largerCoefficients.length];
/* 123 */     int lengthDiff = largerCoefficients.length - smallerCoefficients.length;
/*     */     
/* 125 */     System.arraycopy(largerCoefficients, 0, sumDiff, 0, lengthDiff);
/*     */     
/* 127 */     for (int i = lengthDiff; i < largerCoefficients.length; i++) {
/* 128 */       sumDiff[i] = this.field.add(smallerCoefficients[i - lengthDiff], largerCoefficients[i]);
/*     */     }
/*     */     
/* 131 */     return new ModulusPoly(this.field, sumDiff);
/*     */   }
/*     */   
/*     */   ModulusPoly subtract(ModulusPoly other) {
/* 135 */     if (!this.field.equals(other.field)) {
/* 136 */       throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
/*     */     }
/* 138 */     if (other.isZero()) {
/* 139 */       return this;
/*     */     }
/* 141 */     return add(other.negative());
/*     */   }
/*     */   
/*     */   ModulusPoly multiply(ModulusPoly other) {
/* 145 */     if (!this.field.equals(other.field)) {
/* 146 */       throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
/*     */     }
/* 148 */     if (isZero() || other.isZero()) {
/* 149 */       return this.field.getZero();
/*     */     }
/* 151 */     int[] aCoefficients = this.coefficients;
/* 152 */     int aLength = aCoefficients.length;
/* 153 */     int[] bCoefficients = other.coefficients;
/* 154 */     int bLength = bCoefficients.length;
/* 155 */     int[] product = new int[aLength + bLength - 1];
/* 156 */     for (int i = 0; i < aLength; i++) {
/* 157 */       int aCoeff = aCoefficients[i];
/* 158 */       for (int j = 0; j < bLength; j++) {
/* 159 */         product[i + j] = this.field.add(product[i + j], this.field.multiply(aCoeff, bCoefficients[j]));
/*     */       }
/*     */     } 
/* 162 */     return new ModulusPoly(this.field, product);
/*     */   }
/*     */   
/*     */   ModulusPoly negative() {
/* 166 */     int size = this.coefficients.length;
/* 167 */     int[] negativeCoefficients = new int[size];
/* 168 */     for (int i = 0; i < size; i++) {
/* 169 */       negativeCoefficients[i] = this.field.subtract(0, this.coefficients[i]);
/*     */     }
/* 171 */     return new ModulusPoly(this.field, negativeCoefficients);
/*     */   }
/*     */   
/*     */   ModulusPoly multiply(int scalar) {
/* 175 */     if (scalar == 0) {
/* 176 */       return this.field.getZero();
/*     */     }
/* 178 */     if (scalar == 1) {
/* 179 */       return this;
/*     */     }
/* 181 */     int size = this.coefficients.length;
/* 182 */     int[] product = new int[size];
/* 183 */     for (int i = 0; i < size; i++) {
/* 184 */       product[i] = this.field.multiply(this.coefficients[i], scalar);
/*     */     }
/* 186 */     return new ModulusPoly(this.field, product);
/*     */   }
/*     */   
/*     */   ModulusPoly multiplyByMonomial(int degree, int coefficient) {
/* 190 */     if (degree < 0) {
/* 191 */       throw new IllegalArgumentException();
/*     */     }
/* 193 */     if (coefficient == 0) {
/* 194 */       return this.field.getZero();
/*     */     }
/* 196 */     int size = this.coefficients.length;
/* 197 */     int[] product = new int[size + degree];
/* 198 */     for (int i = 0; i < size; i++) {
/* 199 */       product[i] = this.field.multiply(this.coefficients[i], coefficient);
/*     */     }
/* 201 */     return new ModulusPoly(this.field, product);
/*     */   }
/*     */   
/*     */   ModulusPoly[] divide(ModulusPoly other) {
/* 205 */     if (!this.field.equals(other.field)) {
/* 206 */       throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
/*     */     }
/* 208 */     if (other.isZero()) {
/* 209 */       throw new IllegalArgumentException("Divide by 0");
/*     */     }
/*     */     
/* 212 */     ModulusPoly quotient = this.field.getZero();
/* 213 */     ModulusPoly remainder = this;
/*     */     
/* 215 */     int denominatorLeadingTerm = other.getCoefficient(other.getDegree());
/* 216 */     int inverseDenominatorLeadingTerm = this.field.inverse(denominatorLeadingTerm);
/*     */     
/* 218 */     while (remainder.getDegree() >= other.getDegree() && !remainder.isZero()) {
/* 219 */       int degreeDifference = remainder.getDegree() - other.getDegree();
/* 220 */       int scale = this.field.multiply(remainder.getCoefficient(remainder.getDegree()), inverseDenominatorLeadingTerm);
/* 221 */       ModulusPoly term = other.multiplyByMonomial(degreeDifference, scale);
/* 222 */       ModulusPoly iterationQuotient = this.field.buildMonomial(degreeDifference, scale);
/* 223 */       quotient = quotient.add(iterationQuotient);
/* 224 */       remainder = remainder.subtract(term);
/*     */     } 
/*     */     
/* 227 */     return new ModulusPoly[] { quotient, remainder };
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 232 */     StringBuilder result = new StringBuilder(8 * getDegree());
/* 233 */     for (int degree = getDegree(); degree >= 0; degree--) {
/* 234 */       int coefficient = getCoefficient(degree);
/* 235 */       if (coefficient != 0) {
/* 236 */         if (coefficient < 0) {
/* 237 */           result.append(" - ");
/* 238 */           coefficient = -coefficient;
/*     */         }
/* 240 */         else if (result.length() > 0) {
/* 241 */           result.append(" + ");
/*     */         } 
/*     */         
/* 244 */         if (degree == 0 || coefficient != 1) {
/* 245 */           result.append(coefficient);
/*     */         }
/* 247 */         if (degree != 0) {
/* 248 */           if (degree == 1) {
/* 249 */             result.append('x');
/*     */           } else {
/* 251 */             result.append("x^");
/* 252 */             result.append(degree);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 257 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\pdf417\decoder\ec\ModulusPoly.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */