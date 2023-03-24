/*    */ package com.sun.xml.rpc.processor.util;
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
/*    */ public class StringUtils
/*    */ {
/*    */   public static String decapitalize(String name) {
/* 49 */     if (name == null || name.length() == 0) {
/* 50 */       return name;
/*    */     }
/* 52 */     if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0)))
/*    */     {
/*    */ 
/*    */       
/* 56 */       return name;
/*    */     }
/* 58 */     char[] chars = name.toCharArray();
/* 59 */     chars[0] = Character.toLowerCase(chars[0]);
/* 60 */     return new String(chars);
/*    */   }
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
/*    */   public static String capitalize(String name) {
/* 73 */     if (name == null || name.length() == 0) {
/* 74 */       return name;
/*    */     }
/* 76 */     char[] chars = name.toCharArray();
/* 77 */     chars[0] = Character.toUpperCase(chars[0]);
/* 78 */     return new String(chars);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\StringUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */