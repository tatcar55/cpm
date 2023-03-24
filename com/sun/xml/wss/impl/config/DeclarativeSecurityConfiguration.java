/*     */ package com.sun.xml.wss.impl.config;
/*     */ 
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
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
/*     */ public class DeclarativeSecurityConfiguration
/*     */   implements SecurityPolicy
/*     */ {
/*  59 */   private MessagePolicy senderSettings = new MessagePolicy();
/*  60 */   private MessagePolicy receiverSettings = new MessagePolicy();
/*     */ 
/*     */   
/*     */   private boolean retainSecHeader = false;
/*     */   
/*     */   private boolean resetMU = false;
/*     */ 
/*     */   
/*     */   public void setDumpMessages(boolean doDumpMessages) {
/*  69 */     this.senderSettings.dumpMessages(doDumpMessages);
/*  70 */     this.receiverSettings.dumpMessages(doDumpMessages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableDynamicPolicy(boolean flag) {
/*  77 */     this.senderSettings.enableDynamicPolicy(flag);
/*  78 */     this.receiverSettings.enableDynamicPolicy(flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessagePolicy senderSettings() {
/*  85 */     return this.senderSettings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessagePolicy receiverSettings() {
/*  92 */     return this.receiverSettings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isBSP(boolean bspFlag) {
/* 103 */     this.senderSettings.isBSP(bspFlag);
/* 104 */     this.receiverSettings.isBSP(bspFlag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainSecurityHeader() {
/* 111 */     return this.retainSecHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void retainSecurityHeader(boolean arg) {
/* 118 */     this.retainSecHeader = arg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 125 */     return "DeclarativeSecurityConfiguration";
/*     */   }
/*     */   
/*     */   public void resetMustUnderstand(boolean value) {
/* 129 */     this.resetMU = value;
/*     */   }
/*     */   
/*     */   public boolean resetMustUnderstand() {
/* 133 */     return this.resetMU;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\config\DeclarativeSecurityConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */