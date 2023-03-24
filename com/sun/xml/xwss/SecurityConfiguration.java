/*     */ package com.sun.xml.xwss;
/*     */ 
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.config.ApplicationSecurityConfiguration;
/*     */ import com.sun.xml.wss.impl.config.SecurityConfigurationXmlReader;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.security.auth.callback.CallbackHandler;
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
/*     */ public class SecurityConfiguration
/*     */   implements XWSSecurityConfiguration
/*     */ {
/*  65 */   private ApplicationSecurityConfiguration configuration = null;
/*  66 */   private CallbackHandler callbackhandler = null;
/*  67 */   private SecurityEnvironment securityEnvironment = null;
/*     */   
/*     */   private boolean configEmpty = false;
/*     */   
/*     */   public SecurityConfiguration(URL configUrl) throws XWSSecurityException {
/*  72 */     if (configUrl == null) {
/*  73 */       this.configEmpty = true;
/*     */       
/*     */       return;
/*     */     } 
/*  77 */     InputStream config = null;
/*     */     try {
/*  79 */       config = configUrl.openStream();
/*     */       
/*  81 */       if (config == null) {
/*  82 */         this.configEmpty = true;
/*     */         
/*     */         return;
/*     */       } 
/*  86 */       this.configuration = SecurityConfigurationXmlReader.createApplicationSecurityConfiguration(config);
/*     */       
/*  88 */       this.callbackhandler = (CallbackHandler)Class.forName(this.configuration.getSecurityEnvironmentHandler(), true, Thread.currentThread().getContextClassLoader()).newInstance();
/*     */ 
/*     */       
/*  91 */       this.securityEnvironment = (SecurityEnvironment)new DefaultSecurityEnvironmentImpl(this.callbackhandler);
/*     */     
/*     */     }
/*  94 */     catch (IOException e) {
/*  95 */       throw new XWSSecurityException(e);
/*  96 */     } catch (Exception e) {
/*  97 */       throw new XWSSecurityException(e);
/*     */     } finally {
/*     */       try {
/* 100 */         if (config != null) {
/* 101 */           config.close();
/*     */         }
/* 103 */       } catch (IOException e) {}
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
/*     */ 
/*     */   
/*     */   public SecurityConfiguration(InputStream config) throws XWSSecurityException {
/* 117 */     if (config == null) {
/* 118 */       this.configEmpty = true;
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 123 */       this.configuration = SecurityConfigurationXmlReader.createApplicationSecurityConfiguration(config);
/*     */       
/* 125 */       this.callbackhandler = (CallbackHandler)Class.forName(this.configuration.getSecurityEnvironmentHandler(), true, Thread.currentThread().getContextClassLoader()).newInstance();
/*     */ 
/*     */       
/* 128 */       this.securityEnvironment = (SecurityEnvironment)new DefaultSecurityEnvironmentImpl(this.callbackhandler);
/*     */     }
/* 130 */     catch (Exception e) {
/* 131 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplicationSecurityConfiguration getSecurityConfiguration() {
/* 140 */     return this.configuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityEnvironment getSecurityEnvironment() {
/* 149 */     return this.securityEnvironment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 157 */     return this.configEmpty;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\xwss\SecurityConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */