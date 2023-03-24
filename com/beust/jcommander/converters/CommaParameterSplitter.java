/*   */ package com.beust.jcommander.converters;
/*   */ 
/*   */ import java.util.Arrays;
/*   */ import java.util.List;
/*   */ 
/*   */ public class CommaParameterSplitter
/*   */   implements IParameterSplitter {
/*   */   public List<String> split(String value) {
/* 9 */     return Arrays.asList(value.split(","));
/*   */   }
/*   */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\beust\jcommander\converters\CommaParameterSplitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */