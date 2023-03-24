/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.security.spi.SecurityContext;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.security.auth.Subject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityContextImpl
/*     */   implements SecurityContext
/*     */ {
/*     */   private static final String GF_SEC_CONTEXT = "com.sun.enterprise.security.SecurityContext";
/*  56 */   private Class c = null;
/*  57 */   private Method getCurrent = null;
/*  58 */   private Method serverGenCred = null;
/*  59 */   private Method getSubject = null;
/*  60 */   private Constructor ctor = null;
/*     */   
/*     */   public SecurityContextImpl() {
/*     */     try {
/*  64 */       Class[] params = new Class[0];
/*  65 */       this.c = Class.forName("com.sun.enterprise.security.SecurityContext", true, Thread.currentThread().getContextClassLoader());
/*  66 */       this.getCurrent = this.c.getMethod("getCurrent", params);
/*  67 */       this.serverGenCred = this.c.getMethod("didServerGenerateCredentials", params);
/*  68 */       this.getSubject = this.c.getMethod("getSubject", params);
/*  69 */       params = new Class[] { Subject.class };
/*  70 */       this.ctor = this.c.getConstructor(params);
/*  71 */     } catch (NoSuchMethodException ex) {
/*     */     
/*  73 */     } catch (SecurityException ex) {
/*     */     
/*  75 */     } catch (ClassNotFoundException ex) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Subject getSubject() {
/*  81 */     Subject s = null;
/*  82 */     Object[] args = new Object[0];
/*     */     
/*     */     try {
/*  85 */       if (this.getCurrent == null || this.serverGenCred == null || this.getSubject == null) {
/*  86 */         return null;
/*     */       }
/*     */       
/*  89 */       Object currentSC = this.getCurrent.invoke(null, args);
/*  90 */       if (currentSC == null) {
/*  91 */         return null;
/*     */       }
/*  93 */       Boolean didServerGenerateCredentials = (Boolean)this.serverGenCred.invoke(currentSC, args);
/*  94 */       if (!didServerGenerateCredentials.booleanValue()) {
/*  95 */         s = (Subject)this.getSubject.invoke(currentSC, args);
/*     */       }
/*  97 */       return s;
/*     */     }
/*  99 */     catch (IllegalAccessException ex) {
/* 100 */       return null;
/* 101 */     } catch (IllegalArgumentException ex) {
/* 102 */       return null;
/* 103 */     } catch (InvocationTargetException ex) {
/* 104 */       return null;
/* 105 */     } catch (SecurityException ex) {
/* 106 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubject(Subject subject) {
/* 113 */     Class[] params = null;
/* 114 */     Object[] args = null;
/*     */     try {
/* 116 */       args = new Object[] { subject };
/* 117 */       if (this.ctor == null) {
/*     */         return;
/*     */       }
/*     */       
/* 121 */       Object secContext = this.ctor.newInstance(args);
/* 122 */       params = new Class[] { secContext.getClass() };
/*     */       
/* 124 */       Method setCurrent = this.c.getMethod("setCurrent", params);
/* 125 */       args = new Object[] { secContext };
/* 126 */       if (setCurrent == null) {
/*     */         return;
/*     */       }
/*     */       
/* 130 */       setCurrent.invoke(null, args);
/* 131 */     } catch (InstantiationException ex) {
/*     */     
/* 133 */     } catch (IllegalAccessException ex) {
/*     */     
/* 135 */     } catch (IllegalArgumentException ex) {
/*     */     
/* 137 */     } catch (InvocationTargetException ex) {
/*     */     
/* 139 */     } catch (NoSuchMethodException ex) {
/*     */     
/* 141 */     } catch (SecurityException ex) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\SecurityContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */