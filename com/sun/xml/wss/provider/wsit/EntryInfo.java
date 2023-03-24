/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.message.config.AuthConfigFactory;
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
/*     */ 
/*     */ final class EntryInfo
/*     */ {
/*     */   private final String className;
/*     */   private final Map<String, String> properties;
/*     */   private List<AuthConfigFactory.RegistrationContext> regContexts;
/*     */   
/*     */   EntryInfo(String className, Map<String, String> properties) {
/*  68 */     if (className == null) {
/*  69 */       throw new IllegalArgumentException("Class name for registration entry cannot be null");
/*     */     }
/*     */     
/*  72 */     this.className = className;
/*  73 */     this.properties = properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   EntryInfo(String className, Map<String, String> properties, List<AuthConfigFactory.RegistrationContext> ctxs) {
/*  84 */     if (ctxs == null || ctxs.isEmpty()) {
/*  85 */       throw new IllegalArgumentException("Registration entry must contain one ormore registration contexts");
/*     */     }
/*     */ 
/*     */     
/*  89 */     this.className = className;
/*  90 */     this.properties = properties;
/*  91 */     this.regContexts = ctxs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   EntryInfo(String className, Map<String, String> properties, AuthConfigFactory.RegistrationContext ctx) {
/* 102 */     this.className = className;
/* 103 */     this.properties = properties;
/* 104 */     if (ctx != null) {
/* 105 */       AuthConfigFactory.RegistrationContext ctxImpl = new RegistrationContextImpl(ctx.getMessageLayer(), ctx.getAppContext(), ctx.getDescription(), ctx.isPersistent());
/*     */ 
/*     */       
/* 108 */       List<AuthConfigFactory.RegistrationContext> newList = new ArrayList<AuthConfigFactory.RegistrationContext>(1);
/*     */       
/* 110 */       newList.add(ctxImpl);
/* 111 */       this.regContexts = newList;
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isConstructorEntry() {
/* 116 */     return (this.regContexts == null);
/*     */   }
/*     */   
/*     */   String getClassName() {
/* 120 */     return this.className;
/*     */   }
/*     */   
/*     */   Map<String, String> getProperties() {
/* 124 */     return this.properties;
/*     */   }
/*     */   
/*     */   List<AuthConfigFactory.RegistrationContext> getRegContexts() {
/* 128 */     return this.regContexts;
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
/*     */   
/*     */   boolean equals(EntryInfo target) {
/* 146 */     if (target == null) {
/* 147 */       return false;
/*     */     }
/* 149 */     return ((isConstructorEntry() ^ target.isConstructorEntry()) == 0 && matchStrings(this.className, target.getClassName()) && matchMaps(this.properties, target.getProperties()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean matchStrings(String s1, String s2) {
/* 159 */     if (s1 == null && s2 == null) {
/* 160 */       return true;
/*     */     }
/* 162 */     if (s1 == null || s2 == null) {
/* 163 */       return false;
/*     */     }
/* 165 */     return s1.equals(s2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean matchMaps(Map m1, Map m2) {
/* 173 */     if (m1 == null && m2 == null) {
/* 174 */       return true;
/*     */     }
/* 176 */     if (m1 == null || m2 == null) {
/* 177 */       return false;
/*     */     }
/* 179 */     return m1.equals(m2);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\EntryInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */