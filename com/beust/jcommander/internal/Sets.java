/*    */ package com.beust.jcommander.internal;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
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
/*    */ public class Sets
/*    */ {
/*    */   public static <K> Set<K> newHashSet() {
/* 28 */     return new HashSet<K>();
/*    */   }
/*    */   
/*    */   public static <K> Set<K> newLinkedHashSet() {
/* 32 */     return new LinkedHashSet<K>();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\beust\jcommander\internal\Sets.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */