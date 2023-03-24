/*     */ package com.sun.xml.ws.tx.at.validation;
/*     */ 
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TXAttributesValidator
/*     */ {
/*     */   public static final short TX_NOT_SET = -1;
/*     */   public static final short TX_NOT_SUPPORTED = 0;
/*     */   public static final short TX_REQUIRED = 1;
/*     */   public static final short TX_SUPPORTS = 2;
/*     */   public static final short TX_REQUIRES_NEW = 3;
/*     */   public static final short TX_MANDATORY = 4;
/*     */   public static final short TX_NEVER = 5;
/*  59 */   Set<InvalidCombination> inValidateCombinations = new HashSet<InvalidCombination>();
/*  60 */   static Set<Combination> validateCombinations = new HashSet<Combination>();
/*     */   
/*     */   static {
/*  63 */     validateCombinations.add(new Combination(TransactionAttributeType.REQUIRED, Transactional.TransactionFlowType.MANDATORY));
/*  64 */     validateCombinations.add(new Combination(TransactionAttributeType.REQUIRED, Transactional.TransactionFlowType.NEVER));
/*  65 */     validateCombinations.add(new Combination(TransactionAttributeType.MANDATORY, Transactional.TransactionFlowType.MANDATORY));
/*  66 */     validateCombinations.add(new Combination(TransactionAttributeType.REQUIRED, Transactional.TransactionFlowType.SUPPORTS));
/*  67 */     validateCombinations.add(new Combination(TransactionAttributeType.SUPPORTS, Transactional.TransactionFlowType.SUPPORTS));
/*  68 */     validateCombinations.add(new Combination(TransactionAttributeType.REQUIRES_NEW, Transactional.TransactionFlowType.NEVER));
/*  69 */     validateCombinations.add(new Combination(TransactionAttributeType.NEVER, Transactional.TransactionFlowType.NEVER));
/*  70 */     validateCombinations.add(new Combination(TransactionAttributeType.NOT_SUPPORTED, Transactional.TransactionFlowType.NEVER));
/*     */     
/*  72 */     validateCombinations.add(new Combination(TransactionAttributeType.SUPPORTS, Transactional.TransactionFlowType.NEVER));
/*  73 */     validateCombinations.add(new Combination(TransactionAttributeType.SUPPORTS, Transactional.TransactionFlowType.MANDATORY));
/*     */   }
/*     */   
/*     */   public void visitOperation(String operationName, short attribute, Transactional.TransactionFlowType wsatType) {
/*  77 */     TransactionAttributeType ejbTx = fromIndex(Short.valueOf(attribute));
/*  78 */     visitOperation(operationName, ejbTx, wsatType);
/*     */   }
/*     */   
/*     */   public void validate() throws WebServiceException {
/*  82 */     StringBuilder sb = new StringBuilder();
/*  83 */     for (InvalidCombination combination : this.inValidateCombinations) {
/*  84 */       sb.append("The effective TransactionAttributeType " + combination.ejbTx).append(" and WS-AT Transaction flowType ").append(combination.wsat).append(" on WebService operation ").append(combination.operationName).append(" is not a valid combination! ");
/*     */     }
/*  86 */     if (sb.length() > 0)
/*  87 */       throw new WebServiceException(sb.toString()); 
/*     */   }
/*     */   
/*     */   public void visitOperation(String operationName, TransactionAttributeType ejbTx, Transactional.TransactionFlowType wsatType) {
/*  91 */     if (wsatType == null) wsatType = Transactional.TransactionFlowType.NEVER; 
/*  92 */     Combination combination = new Combination(ejbTx, wsatType);
/*  93 */     if (!validateCombinations.contains(combination)) {
/*  94 */       this.inValidateCombinations.add(new InvalidCombination(ejbTx, wsatType, operationName));
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isValid(TransactionAttributeType ejbTx, Transactional.TransactionFlowType wsatType) {
/*  99 */     return validateCombinations.contains(new Combination(ejbTx, wsatType));
/*     */   }
/*     */   
/*     */   private static TransactionAttributeType fromIndex(Short index) {
/* 103 */     switch (index.shortValue()) {
/*     */       case 0:
/* 105 */         return TransactionAttributeType.NOT_SUPPORTED;
/*     */       case 1:
/* 107 */         return TransactionAttributeType.REQUIRED;
/*     */       case 2:
/* 109 */         return TransactionAttributeType.SUPPORTS;
/*     */       case 3:
/* 111 */         return TransactionAttributeType.REQUIRES_NEW;
/*     */       case 4:
/* 113 */         return TransactionAttributeType.MANDATORY;
/*     */       case 5:
/* 115 */         return TransactionAttributeType.NEVER;
/*     */     } 
/* 117 */     return TransactionAttributeType.SUPPORTS;
/*     */   }
/*     */   
/*     */   static class Combination
/*     */   {
/*     */     TXAttributesValidator.TransactionAttributeType ejbTx;
/*     */     Transactional.TransactionFlowType wsat;
/*     */     
/*     */     Combination(TXAttributesValidator.TransactionAttributeType ejbTx, Transactional.TransactionFlowType wsat) {
/* 126 */       this.ejbTx = ejbTx;
/* 127 */       this.wsat = wsat;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 131 */       if (this == o) return true; 
/* 132 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 134 */       Combination that = (Combination)o;
/*     */       
/* 136 */       if (this.ejbTx != that.ejbTx) return false; 
/* 137 */       if (this.wsat != that.wsat) return false;
/*     */       
/* 139 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 144 */       int result = this.ejbTx.hashCode();
/* 145 */       result = 31 * result + this.wsat.hashCode();
/* 146 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   static class InvalidCombination {
/*     */     TXAttributesValidator.TransactionAttributeType ejbTx;
/*     */     Transactional.TransactionFlowType wsat;
/*     */     String operationName;
/*     */     
/*     */     InvalidCombination(TXAttributesValidator.TransactionAttributeType ejbTx, Transactional.TransactionFlowType wsat, String operationName) {
/* 156 */       this.ejbTx = ejbTx;
/* 157 */       this.wsat = wsat;
/* 158 */       this.operationName = operationName;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 162 */       if (this == o) return true; 
/* 163 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 165 */       InvalidCombination that = (InvalidCombination)o;
/*     */       
/* 167 */       if (this.ejbTx != that.ejbTx) return false; 
/* 168 */       if (!this.operationName.equals(that.operationName)) return false; 
/* 169 */       if (this.wsat != that.wsat) return false;
/*     */       
/* 171 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 176 */       int result = this.ejbTx.hashCode();
/* 177 */       result = 31 * result + this.wsat.hashCode();
/* 178 */       result = 31 * result + this.operationName.hashCode();
/* 179 */       return result;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public enum TransactionAttributeType
/*     */   {
/* 186 */     MANDATORY,
/* 187 */     REQUIRED,
/* 188 */     REQUIRES_NEW,
/* 189 */     SUPPORTS,
/* 190 */     NOT_SUPPORTED,
/* 191 */     NEVER;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\validation\TXAttributesValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */