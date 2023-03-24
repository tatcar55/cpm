/*     */ package com.sun.xml.wss.impl.configuration;
/*     */ 
/*     */ import com.sun.xml.wss.impl.policy.DynamicPolicyContext;
/*     */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*     */ import java.util.HashMap;
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
/*     */ public class DynamicApplicationContext
/*     */   extends DynamicPolicyContext
/*     */ {
/*  61 */   private String messageIdentifier = "";
/*     */   
/*     */   private boolean inBoundMessage = false;
/*  64 */   private StaticPolicyContext context = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicApplicationContext() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicApplicationContext(StaticPolicyContext context) {
/*  77 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessageIdentifier(String messageIdentifier) {
/*  85 */     this.messageIdentifier = messageIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessageIdentifier() {
/*  92 */     return this.messageIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inBoundMessage(boolean inBound) {
/* 100 */     this.inBoundMessage = inBound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inBoundMessage() {
/* 107 */     return this.inBoundMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStaticPolicyContext(StaticPolicyContext context) {
/* 115 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StaticPolicyContext getStaticPolicyContext() {
/* 122 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap getRuntimeProperties() {
/* 129 */     return this.properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(DynamicApplicationContext ctx) {
/* 138 */     boolean b1 = getStaticPolicyContext().equals(ctx.getStaticPolicyContext());
/*     */     
/* 140 */     if (!b1) return false;
/*     */     
/* 142 */     boolean b2 = (this.messageIdentifier.equalsIgnoreCase(ctx.getMessageIdentifier()) && this.inBoundMessage == ctx.inBoundMessage);
/*     */ 
/*     */     
/* 145 */     if (!b2) return false;
/*     */     
/* 147 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\configuration\DynamicApplicationContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */