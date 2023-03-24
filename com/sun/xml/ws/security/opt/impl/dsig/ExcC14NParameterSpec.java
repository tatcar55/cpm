/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExcC14NParameterSpec
/*     */   implements AlgorithmParameterSpec, C14NMethodParameterSpec, TransformParameterSpec
/*     */ {
/*     */   private List preList;
/*     */   public static final String DEFAULT = "#default";
/*     */   
/*     */   public ExcC14NParameterSpec() {
/*  68 */     this.preList = new ArrayList();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExcC14NParameterSpec(List<?> prefixList) {
/*  85 */     if (prefixList == null) {
/*  86 */       throw new NullPointerException("prefixList cannot be null");
/*     */     }
/*  88 */     this.preList = new ArrayList(prefixList);
/*  89 */     for (int i = 0, size = this.preList.size(); i < size; i++) {
/*  90 */       if (!(this.preList.get(i) instanceof String)) {
/*  91 */         throw new ClassCastException("not a String");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getPrefixList() {
/* 104 */     return this.preList;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\ExcC14NParameterSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */