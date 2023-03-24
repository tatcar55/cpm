/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalStringManagerImpl
/*     */   implements LocalStringManager
/*     */ {
/*  59 */   private static Logger _logger = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class defaultClass;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalStringManagerImpl(Class defaultClass) {
/*  70 */     this.defaultClass = defaultClass;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalString(Class callerClass, String key, String defaultValue) {
/*  98 */     Class stopClass = this.defaultClass.getSuperclass();
/*  99 */     Class startClass = (callerClass != null) ? callerClass : this.defaultClass;
/*     */     
/* 101 */     ResourceBundle resources = null;
/* 102 */     boolean globalDone = false;
/* 103 */     Class c = startClass;
/* 104 */     for (; c != stopClass && c != null; 
/* 105 */       c = c.getSuperclass()) {
/* 106 */       globalDone = (c == this.defaultClass);
/*     */ 
/*     */       
/*     */       try {
/* 110 */         StringBuilder resFileName = new StringBuilder(c.getName().substring(0, c.getName().lastIndexOf(".")));
/*     */         
/* 112 */         resFileName.append(".LocalStrings");
/*     */         
/* 114 */         resources = ResourceBundle.getBundle(resFileName.toString(), Locale.getDefault(), c.getClassLoader());
/* 115 */         if (resources != null) {
/* 116 */           String value = resources.getString(key);
/* 117 */           if (value != null)
/* 118 */             return value; 
/*     */         } 
/* 120 */       } catch (Exception ex) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 125 */     if (!globalDone) {
/* 126 */       return getLocalString((Class)null, key, defaultValue);
/*     */     }
/* 128 */     if (_logger.isLoggable(Level.FINE))
/* 129 */       _logger.log(Level.FINE, "No local string for " + key); 
/* 130 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalString(String key, String defaultValue) {
/* 141 */     return getLocalString((Class)null, key, defaultValue);
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
/*     */   public String getLocalString(Class callerClass, String key, String defaultFormat, Object... arguments) {
/* 159 */     MessageFormat f = new MessageFormat(getLocalString(callerClass, key, defaultFormat));
/*     */     
/* 161 */     for (int i = 0; i < arguments.length; i++) {
/* 162 */       if (arguments[i] == null) {
/* 163 */         arguments[i] = "null";
/* 164 */       } else if (!(arguments[i] instanceof String) && !(arguments[i] instanceof Number) && !(arguments[i] instanceof java.util.Date)) {
/*     */ 
/*     */         
/* 167 */         arguments[i] = arguments[i].toString();
/*     */       } 
/*     */     } 
/* 170 */     return f.format(arguments);
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
/*     */   public String getLocalString(String key, String defaultFormat, Object... arguments) {
/* 186 */     return getLocalString(null, key, defaultFormat, arguments);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\LocalStringManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */