/*     */ package org.glassfish.gmbal;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import javax.management.ObjectName;
/*     */ import org.glassfish.gmbal.util.GenericConstructor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ManagedObjectManagerFactory
/*     */ {
/*  59 */   private static GenericConstructor<ManagedObjectManager> objectNameCons = new GenericConstructor(ManagedObjectManager.class, "org.glassfish.gmbal.impl.ManagedObjectManagerImpl", new Class[] { ObjectName.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static GenericConstructor<ManagedObjectManager> stringCons = new GenericConstructor(ManagedObjectManager.class, "org.glassfish.gmbal.impl.ManagedObjectManagerImpl", new Class[] { String.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method getMethod(final Class<?> cls, final String name, Class<?>... types) {
/*     */     try {
/*  84 */       return AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/*  87 */               return cls.getDeclaredMethod(name, types);
/*     */             }
/*     */           });
/*  90 */     } catch (PrivilegedActionException ex) {
/*  91 */       throw new GmbalException("Unexpected exception", ex);
/*  92 */     } catch (SecurityException exc) {
/*  93 */       throw new GmbalException("Unexpected exception", exc);
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
/*     */   public static ManagedObjectManager createStandalone(String domain) {
/* 107 */     ManagedObjectManager result = (ManagedObjectManager)stringCons.create(new Object[] { domain });
/* 108 */     if (result == null) {
/* 109 */       return ManagedObjectManagerNOPImpl.self;
/*     */     }
/* 111 */     return result;
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
/*     */   public static ManagedObjectManager createFederated(ObjectName rootParentName) {
/* 129 */     ManagedObjectManager result = (ManagedObjectManager)objectNameCons.create(new Object[] { rootParentName });
/* 130 */     if (result == null) {
/* 131 */       return ManagedObjectManagerNOPImpl.self;
/*     */     }
/* 133 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ManagedObjectManager createNOOP() {
/* 143 */     return ManagedObjectManagerNOPImpl.self;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\ManagedObjectManagerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */