/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
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
/*     */ public final class NestedPolicy
/*     */   extends Policy
/*     */ {
/*     */   private static final String NESTED_POLICY_TOSTRING_NAME = "nested policy";
/*     */   
/*     */   private NestedPolicy(AssertionSet set) {
/*  55 */     super("nested policy", Arrays.asList(new AssertionSet[] { set }));
/*     */   }
/*     */   
/*     */   private NestedPolicy(String name, String policyId, AssertionSet set) {
/*  59 */     super("nested policy", name, policyId, Arrays.asList(new AssertionSet[] { set }));
/*     */   }
/*     */   
/*     */   static NestedPolicy createNestedPolicy(AssertionSet set) {
/*  63 */     return new NestedPolicy(set);
/*     */   }
/*     */   
/*     */   static NestedPolicy createNestedPolicy(String name, String policyId, AssertionSet set) {
/*  67 */     return new NestedPolicy(name, policyId, set);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssertionSet getAssertionSet() {
/*  78 */     Iterator<AssertionSet> iterator = iterator();
/*  79 */     if (iterator.hasNext()) {
/*  80 */       return iterator.next();
/*     */     }
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  91 */     if (this == obj) {
/*  92 */       return true;
/*     */     }
/*     */     
/*  95 */     if (!(obj instanceof NestedPolicy)) {
/*  96 */       return false;
/*     */     }
/*     */     
/*  99 */     NestedPolicy that = (NestedPolicy)obj;
/*     */     
/* 101 */     return super.equals(that);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 114 */     return toString(0, new StringBuffer()).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   StringBuffer toString(int indentLevel, StringBuffer buffer) {
/* 126 */     return super.toString(indentLevel, buffer);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\NestedPolicy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */