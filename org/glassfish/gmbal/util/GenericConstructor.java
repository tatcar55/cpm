/*     */ package org.glassfish.gmbal.util;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
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
/*     */ public class GenericConstructor<T>
/*     */ {
/*  53 */   private final Object lock = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   private String typeName;
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<T> resultType;
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> type;
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?>[] signature;
/*     */ 
/*     */ 
/*     */   
/*     */   private Constructor constructor;
/*     */ 
/*     */ 
/*     */   
/*     */   public GenericConstructor(Class<T> type, String className, Class<?>... signature) {
/*  78 */     this.resultType = type;
/*  79 */     this.typeName = className;
/*  80 */     this.signature = (Class[])signature.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void getConstructor() {
/*  85 */     synchronized (this.lock) {
/*  86 */       if (this.type == null || this.constructor == null) {
/*     */         try {
/*  88 */           this.type = Class.forName(this.typeName);
/*  89 */           this.constructor = AccessController.<Constructor>doPrivileged(new PrivilegedExceptionAction<Constructor>()
/*     */               {
/*     */                 public Constructor run() throws Exception {
/*  92 */                   synchronized (GenericConstructor.this.lock) {
/*  93 */                     return GenericConstructor.this.type.getDeclaredConstructor(GenericConstructor.this.signature);
/*     */                   } 
/*     */                 }
/*     */               });
/*  97 */         } catch (Exception exc) {
/*     */           
/*  99 */           Logger.getLogger("org.glassfish.gmbal.util").log(Level.FINE, "Failure in getConstructor", exc);
/*     */         } 
/*     */       }
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
/*     */   
/*     */   public synchronized T create(Object... args) {
/* 116 */     synchronized (this.lock) {
/* 117 */       T result = null;
/*     */       
/* 119 */       for (int ctr = 0; ctr <= 1; ctr++) {
/* 120 */         getConstructor();
/* 121 */         if (this.constructor == null) {
/*     */           break;
/*     */         }
/*     */         
/*     */         try {
/* 126 */           result = this.resultType.cast(this.constructor.newInstance(args));
/*     */           break;
/* 128 */         } catch (Exception exc) {
/*     */ 
/*     */           
/* 131 */           this.constructor = null;
/* 132 */           Logger.getLogger("org.glassfish.gmbal.util").log(Level.WARNING, "Error invoking constructor", exc);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 137 */       return result;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmba\\util\GenericConstructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */