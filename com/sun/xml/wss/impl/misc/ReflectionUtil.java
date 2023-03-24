/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
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
/*     */ public class ReflectionUtil
/*     */ {
/*  64 */   private static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T invoke(Object target, String methodName, Class<T> resultClass, Object... parameters) throws XWSSecurityRuntimeException {
/*     */     Class[] parameterTypes;
/*  75 */     if (parameters != null && parameters.length > 0) {
/*  76 */       parameterTypes = new Class[parameters.length];
/*  77 */       int i = 0;
/*  78 */       for (Object parameter : parameters) {
/*  79 */         parameterTypes[i++] = parameter.getClass();
/*     */       }
/*     */     } else {
/*  82 */       parameterTypes = null;
/*     */     } 
/*     */     
/*  85 */     return invoke(target, methodName, resultClass, parameters, parameterTypes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T invoke(Object target, String methodName, Class<T> resultClass, Object[] parameters, Class[] parameterTypes) throws XWSSecurityRuntimeException {
/*     */     try {
/*  94 */       Method method = target.getClass().getMethod(methodName, parameterTypes);
/*  95 */       Object result = method.invoke(target, parameters);
/*     */       
/*  97 */       return resultClass.cast(result);
/*  98 */     } catch (IllegalArgumentException e) {
/*  99 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0810_METHOD_INVOCATION_FAILED(), e);
/* 100 */       throw e;
/* 101 */     } catch (InvocationTargetException e) {
/* 102 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0810_METHOD_INVOCATION_FAILED(), e);
/* 103 */       throw new XWSSecurityRuntimeException(e);
/* 104 */     } catch (IllegalAccessException e) {
/* 105 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0810_METHOD_INVOCATION_FAILED(), e);
/* 106 */       throw new XWSSecurityRuntimeException(e);
/* 107 */     } catch (SecurityException e) {
/* 108 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0810_METHOD_INVOCATION_FAILED(), e);
/* 109 */       throw e;
/* 110 */     } catch (NoSuchMethodException e) {
/* 111 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0810_METHOD_INVOCATION_FAILED(), e);
/* 112 */       throw new XWSSecurityRuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\ReflectionUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */