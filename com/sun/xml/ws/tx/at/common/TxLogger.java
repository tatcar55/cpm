/*     */ package com.sun.xml.ws.tx.at.common;
/*     */ 
/*     */ import java.lang.reflect.Field;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TxLogger
/*     */ {
/*     */   private static final String LOGGING_SUBSYSTEM_NAME;
/*     */   private final String componentClassName;
/*     */   private final Logger logger;
/*     */   
/*     */   static {
/*  67 */     String loggingSubsystemName = null;
/*     */ 
/*     */     
/*     */     try {
/*  71 */       Class<?> jaxwsConstants = Class.forName("com.sun.xml.ws.util.Constants");
/*  72 */       Field loggingDomainField = jaxwsConstants.getField("LoggingDomain");
/*  73 */       Object loggingDomain = loggingDomainField.get(null);
/*  74 */       loggingSubsystemName = loggingDomain.toString().concat(".wstx");
/*  75 */     } catch (Exception e) {
/*     */ 
/*     */       
/*  78 */       loggingSubsystemName = "wstx";
/*     */     } finally {
/*  80 */       LOGGING_SUBSYSTEM_NAME = loggingSubsystemName;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TxLogger(String componentName) {
/*  91 */     this.componentClassName = "[" + componentName + "] ";
/*  92 */     this.logger = Logger.getLogger(LOGGING_SUBSYSTEM_NAME);
/*     */   }
/*     */   
/*     */   private TxLogger(String componentName, String subsystem) {
/*  96 */     this.componentClassName = "[" + componentName + "] ";
/*     */     
/*  98 */     this.logger = Logger.getLogger(LOGGING_SUBSYSTEM_NAME + subsystem);
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
/*     */   public static TxLogger getLogger(Class componentClass) {
/* 111 */     return new TxLogger(componentClass.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TxLogger getATLogger(Class componentClass) {
/* 118 */     return new TxLogger(componentClass.getName(), ".wsat");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TxLogger getCoordLogger(Class componentClass) {
/* 125 */     return new TxLogger(componentClass.getName(), ".wscoord");
/*     */   }
/*     */   
/*     */   public void log(Level level, String methodName, String message) {
/* 129 */     this.logger.logp(level, this.componentClassName, methodName, message);
/*     */   }
/*     */   
/*     */   public void log(Level level, String methodName, String message, Throwable thrown) {
/* 133 */     this.logger.logp(level, this.componentClassName, methodName, message, thrown);
/*     */   }
/*     */   
/*     */   public void finest(String methodName, String message) {
/* 137 */     this.logger.logp(Level.FINEST, this.componentClassName, methodName, message);
/*     */   }
/*     */   
/*     */   public void finest(String methodName, String message, Throwable thrown) {
/* 141 */     this.logger.logp(Level.FINEST, this.componentClassName, methodName, message, thrown);
/*     */   }
/*     */   
/*     */   public void finer(String methodName, String message) {
/* 145 */     this.logger.logp(Level.FINER, this.componentClassName, methodName, message);
/*     */   }
/*     */   
/*     */   public void finer(String methodName, String message, Throwable thrown) {
/* 149 */     this.logger.logp(Level.FINER, this.componentClassName, methodName, message, thrown);
/*     */   }
/*     */   
/*     */   public void fine(String methodName, String message) {
/* 153 */     this.logger.logp(Level.FINE, this.componentClassName, methodName, message);
/*     */   }
/*     */   
/*     */   public void fine(String methodName, String message, Throwable thrown) {
/* 157 */     this.logger.logp(Level.FINE, this.componentClassName, methodName, message, thrown);
/*     */   }
/*     */   
/*     */   public void info(String methodName, String message) {
/* 161 */     this.logger.logp(Level.INFO, this.componentClassName, methodName, message);
/*     */   }
/*     */   
/*     */   public void info(String methodName, String message, Throwable thrown) {
/* 165 */     this.logger.logp(Level.INFO, this.componentClassName, methodName, message, thrown);
/*     */   }
/*     */   
/*     */   public void config(String methodName, String message) {
/* 169 */     this.logger.logp(Level.CONFIG, this.componentClassName, methodName, message);
/*     */   }
/*     */   
/*     */   public void config(String methodName, String message, Throwable thrown) {
/* 173 */     this.logger.logp(Level.CONFIG, this.componentClassName, methodName, message, thrown);
/*     */   }
/*     */   
/*     */   public void warning(String methodName, String message) {
/* 177 */     this.logger.logp(Level.WARNING, this.componentClassName, methodName, message);
/*     */   }
/*     */   
/*     */   public void warning(String methodName, String message, Throwable thrown) {
/* 181 */     this.logger.logp(Level.WARNING, this.componentClassName, methodName, message, thrown);
/*     */   }
/*     */   
/*     */   public void severe(String methodName, String message) {
/* 185 */     this.logger.logp(Level.SEVERE, this.componentClassName, methodName, message);
/*     */   }
/*     */   
/*     */   public void severe(String methodName, String message, Throwable thrown) {
/* 189 */     this.logger.logp(Level.SEVERE, this.componentClassName, methodName, message, thrown);
/*     */   }
/*     */   
/*     */   public void entering(String methodName) {
/* 193 */     this.logger.entering(this.componentClassName, methodName);
/*     */   }
/*     */   
/*     */   public void entering(String methodName, Object parameter) {
/* 197 */     this.logger.entering(this.componentClassName, methodName, parameter);
/*     */   }
/*     */   
/*     */   public void entering(String methodName, Object[] parameters) {
/* 201 */     this.logger.entering(this.componentClassName, methodName, parameters);
/*     */   }
/*     */   
/*     */   public void exiting(String methodName) {
/* 205 */     this.logger.exiting(this.componentClassName, methodName);
/*     */   }
/*     */   
/*     */   public void exiting(String methodName, Object result) {
/* 209 */     this.logger.exiting(this.componentClassName, methodName, result);
/*     */   }
/*     */   
/*     */   public boolean isLogging(Level level) {
/* 213 */     return this.logger.isLoggable(level);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\TxLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */