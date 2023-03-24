/*    */ package com.beust.jcommander.converters;
/*    */ 
/*    */ import com.beust.jcommander.ParameterException;
/*    */ import java.math.BigDecimal;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BigDecimalConverter
/*    */   extends BaseConverter<BigDecimal>
/*    */ {
/*    */   public BigDecimalConverter(String optionName) {
/* 33 */     super(optionName);
/*    */   }
/*    */   
/*    */   public BigDecimal convert(String value) {
/*    */     try {
/* 38 */       return new BigDecimal(value);
/* 39 */     } catch (NumberFormatException nfe) {
/* 40 */       throw new ParameterException(getErrorString(value, "a BigDecimal"));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\beust\jcommander\converters\BigDecimalConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */