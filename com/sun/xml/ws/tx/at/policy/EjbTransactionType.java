/*    */ package com.sun.xml.ws.tx.at.policy;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import javax.ejb.Stateful;
/*    */ import javax.ejb.Stateless;
/*    */ import javax.ejb.TransactionAttribute;
/*    */ import javax.ejb.TransactionManagement;
/*    */ import javax.ejb.TransactionManagementType;
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
/*    */ public enum EjbTransactionType
/*    */ {
/* 51 */   NOT_SUPPORTED,
/* 52 */   NEVER,
/* 53 */   MANDATORY,
/* 54 */   SUPPORTS,
/* 55 */   REQUIRES_NEW,
/* 56 */   REQUIRED,
/* 57 */   NOT_DEFINED;
/*    */   
/*    */   public static EjbTransactionType getDefaultFor(Class<?> seiClass) {
/* 60 */     EjbTransactionType result = NOT_DEFINED;
/*    */     
/* 62 */     TransactionAttribute txnAttr = seiClass.<TransactionAttribute>getAnnotation(TransactionAttribute.class);
/* 63 */     if (txnAttr != null) {
/* 64 */       result = (EjbTransactionType)valueOf(EjbTransactionType.class, txnAttr.value().name());
/*    */     }
/*    */     
/* 67 */     return result;
/*    */   }
/*    */   
/*    */   public EjbTransactionType getEffectiveType(Method method) {
/* 71 */     TransactionAttribute txnAttr = method.<TransactionAttribute>getAnnotation(TransactionAttribute.class);
/* 72 */     if (txnAttr != null) {
/* 73 */       return (EjbTransactionType)valueOf(EjbTransactionType.class, txnAttr.value().name());
/*    */     }
/* 75 */     return this;
/*    */   }
/*    */   
/*    */   public static boolean isContainerManagedEJB(Class c) {
/* 79 */     TransactionManagement tm = (TransactionManagement)c.getAnnotation(TransactionManagement.class);
/* 80 */     if (tm != null) {
/* 81 */       switch (tm.value()) {
/*    */         case BEAN:
/* 83 */           return false;
/*    */       } 
/*    */       
/* 86 */       return true;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 91 */     if (c.getAnnotation(Stateful.class) != null || c.getAnnotation(Stateless.class) != null)
/*    */     {
/* 93 */       return true;
/*    */     }
/*    */     
/* 96 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\policy\EjbTransactionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */