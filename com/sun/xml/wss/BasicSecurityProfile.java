/*     */ package com.sun.xml.wss;
/*     */ 
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
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
/*     */ public final class BasicSecurityProfile
/*     */ {
/*     */   private boolean timeStampFound = false;
/*  55 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void log_bsp_3203() {
/*  65 */     log.log(Level.SEVERE, LogStringsMessages.BSP_3203_ONECREATED_TIMESTAMP());
/*  66 */     throw new XWSSecurityRuntimeException(LogStringsMessages.BSP_3203_ONECREATED_TIMESTAMP());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void log_bsp_3224() {
/*  73 */     log.log(Level.SEVERE, LogStringsMessages.BSP_3224_ONEEXPIRES_TIMESTAMP());
/*  74 */     throw new XWSSecurityRuntimeException(LogStringsMessages.BSP_3224_ONEEXPIRES_TIMESTAMP());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void log_bsp_3222(String elementName) {
/*  82 */     log.log(Level.SEVERE, LogStringsMessages.BSP_3222_ELEMENT_NOT_ALLOWED_UNDER_TIMESTMP(elementName));
/*  83 */     throw new XWSSecurityRuntimeException(LogStringsMessages.BSP_3222_ELEMENT_NOT_ALLOWED_UNDER_TIMESTMP(elementName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void log_bsp_3221() {
/*  90 */     log.log(Level.SEVERE, LogStringsMessages.BSP_3221_CREATED_BEFORE_EXPIRES_TIMESTAMP());
/*  91 */     throw new XWSSecurityRuntimeException(LogStringsMessages.BSP_3221_CREATED_BEFORE_EXPIRES_TIMESTAMP());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void log_bsp_3227() throws XWSSecurityException {
/*  99 */     log.log(Level.SEVERE, LogStringsMessages.BSP_3227_SINGLE_TIMESTAMP());
/* 100 */     throw new XWSSecurityException(LogStringsMessages.BSP_3227_SINGLE_TIMESTAMP());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void log_bsp_3225() {
/* 107 */     log.log(Level.SEVERE, LogStringsMessages.BSP_3225_CREATED_VALUE_TYPE_TIMESTAMP());
/* 108 */     throw new XWSSecurityRuntimeException(LogStringsMessages.BSP_3225_CREATED_VALUE_TYPE_TIMESTAMP());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void log_bsp_3226() {
/* 115 */     log.log(Level.SEVERE, LogStringsMessages.BSP_3226_EXPIRES_VALUE_TYPE_TIMESTAMP());
/* 116 */     throw new XWSSecurityRuntimeException(LogStringsMessages.BSP_3226_EXPIRES_VALUE_TYPE_TIMESTAMP());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void log_bsp_3104() throws XWSSecurityException {
/* 124 */     log.log(Level.WARNING, LogStringsMessages.BSP_3104_ENVELOPED_SIGNATURE_DISCORAGED());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeStampFound(boolean value) {
/* 132 */     this.timeStampFound = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimeStampFound() {
/* 139 */     return this.timeStampFound;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\BasicSecurityProfile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */