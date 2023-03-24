/*     */ package com.sun.xml.rpc.processor.model.java;
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
/*     */ public abstract class JavaType
/*     */ {
/*     */   private String name;
/*     */   private String realName;
/*     */   private boolean present;
/*     */   private boolean holder;
/*     */   private boolean holderPresent;
/*     */   private String initString;
/*     */   private String holderName;
/*     */   
/*     */   public JavaType() {}
/*     */   
/*     */   public JavaType(String name, boolean present, String initString) {
/*  46 */     init(name, present, initString, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType(String name, boolean present, String initString, String holderName) {
/*  52 */     init(name, present, initString, holderName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(String name, boolean present, String initString, String holderName) {
/*  58 */     this.realName = name;
/*  59 */     this.name = name.replace('$', '.');
/*  60 */     this.present = present;
/*  61 */     this.initString = initString;
/*  62 */     this.holderName = holderName;
/*  63 */     this.holder = (holderName != null);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  67 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void doSetName(String name) {
/*  73 */     this.realName = name;
/*  74 */     this.name = name.replace('$', '.');
/*     */   }
/*     */   
/*     */   public String getRealName() {
/*  78 */     return this.realName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRealName(String s) {
/*  83 */     this.realName = s;
/*     */   }
/*     */   
/*     */   public String getFormalName() {
/*  87 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setFormalName(String s) {
/*  91 */     this.name = s;
/*     */   }
/*     */   
/*     */   public boolean isPresent() {
/*  95 */     return this.present;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPresent(boolean b) {
/* 100 */     this.present = b;
/*     */   }
/*     */   
/*     */   public boolean isHolder() {
/* 104 */     return this.holder;
/*     */   }
/*     */   
/*     */   public void setHolder(boolean holder) {
/* 108 */     this.holder = holder;
/*     */   }
/*     */   
/*     */   public boolean isHolderPresent() {
/* 112 */     return this.holderPresent;
/*     */   }
/*     */   public void setHolderPresent(boolean holderPresent) {
/* 115 */     this.holderPresent = holderPresent;
/*     */   }
/*     */   
/*     */   public String getInitString() {
/* 119 */     return this.initString;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInitString(String s) {
/* 124 */     this.initString = s;
/*     */   }
/*     */   
/*     */   public String getHolderName() {
/* 128 */     return this.holderName;
/*     */   }
/*     */   
/*     */   public void setHolderName(String holderName) {
/* 132 */     this.holderName = holderName;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\java\JavaType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */