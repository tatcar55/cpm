/*     */ package com.sun.xml.ws.policy.privateutil;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import java.lang.reflect.Field;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PolicyLogger
/*     */   extends Logger
/*     */ {
/*     */   private static final String POLICY_PACKAGE_ROOT = "com.sun.xml.ws.policy";
/*     */   
/*     */   private PolicyLogger(String policyLoggerName, String className) {
/*  68 */     super(policyLoggerName, className);
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
/*     */   public static PolicyLogger getLogger(Class<?> componentClass) {
/*  81 */     String componentClassName = componentClass.getName();
/*     */     
/*  83 */     if (componentClassName.startsWith("com.sun.xml.ws.policy")) {
/*  84 */       return new PolicyLogger(getLoggingSubsystemName() + componentClassName.substring("com.sun.xml.ws.policy".length()), componentClassName);
/*     */     }
/*     */     
/*  87 */     return new PolicyLogger(getLoggingSubsystemName() + "." + componentClassName, componentClassName);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getLoggingSubsystemName() {
/*  92 */     String loggingSubsystemName = "wspolicy";
/*     */ 
/*     */     
/*     */     try {
/*  96 */       Class<?> jaxwsConstants = Class.forName("com.sun.xml.ws.util.Constants");
/*  97 */       Field loggingDomainField = jaxwsConstants.getField("LoggingDomain");
/*  98 */       Object loggingDomain = loggingDomainField.get(null);
/*  99 */       loggingSubsystemName = loggingDomain.toString().concat(".wspolicy");
/* 100 */     } catch (RuntimeException e) {
/*     */ 
/*     */     
/* 103 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 106 */     return loggingSubsystemName;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\privateutil\PolicyLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */