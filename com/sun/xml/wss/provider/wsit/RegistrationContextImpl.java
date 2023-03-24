/*    */ package com.sun.xml.wss.provider.wsit;
/*    */ 
/*    */ import javax.security.auth.message.config.AuthConfigFactory;
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
/*    */ 
/*    */ 
/*    */ final class RegistrationContextImpl
/*    */   implements AuthConfigFactory.RegistrationContext
/*    */ {
/*    */   private final String messageLayer;
/*    */   private final String appContext;
/*    */   private final String description;
/*    */   private final boolean isPersistent;
/*    */   
/*    */   RegistrationContextImpl(String messageLayer, String appContext, String description, boolean persistent) {
/* 59 */     this.messageLayer = messageLayer;
/* 60 */     this.appContext = appContext;
/* 61 */     this.description = description;
/* 62 */     this.isPersistent = persistent;
/*    */   }
/*    */ 
/*    */   
/*    */   RegistrationContextImpl(AuthConfigFactory.RegistrationContext ctx) {
/* 67 */     this.messageLayer = ctx.getMessageLayer();
/* 68 */     this.appContext = ctx.getAppContext();
/* 69 */     this.description = ctx.getDescription();
/* 70 */     this.isPersistent = ctx.isPersistent();
/*    */   }
/*    */   
/*    */   public String getMessageLayer() {
/* 74 */     return this.messageLayer;
/*    */   }
/*    */   
/*    */   public String getAppContext() {
/* 78 */     return this.appContext;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 82 */     return this.description;
/*    */   }
/*    */   
/*    */   public boolean isPersistent() {
/* 86 */     return this.isPersistent;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 90 */     if (o == null || !(o instanceof AuthConfigFactory.RegistrationContext)) {
/* 91 */       return false;
/*    */     }
/* 93 */     AuthConfigFactory.RegistrationContext target = (AuthConfigFactory.RegistrationContext)o;
/* 94 */     return (EntryInfo.matchStrings(this.messageLayer, target.getMessageLayer()) && EntryInfo.matchStrings(this.appContext, target.getAppContext()) && EntryInfo.matchStrings(this.description, target.getDescription()));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\RegistrationContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */