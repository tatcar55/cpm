/*    */ package com.sun.xml.fastinfoset.alphabet;
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
/*    */ public final class BuiltInRestrictedAlphabets
/*    */ {
/* 24 */   public static final char[][] table = new char[2][];
/*    */ 
/*    */   
/*    */   static {
/* 28 */     table[0] = "0123456789-+.E ".toCharArray();
/* 29 */     table[1] = "0123456789-:TZ ".toCharArray();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\alphabet\BuiltInRestrictedAlphabets.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */