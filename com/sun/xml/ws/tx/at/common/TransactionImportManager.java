/*     */ package com.sun.xml.ws.tx.at.common;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import javax.resource.spi.XATerminator;
/*     */ import javax.transaction.SystemException;
/*     */ import javax.transaction.TransactionManager;
/*     */ import javax.transaction.xa.XAResource;
/*     */ import javax.transaction.xa.Xid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransactionImportManager
/*     */   implements TransactionImportWrapper
/*     */ {
/*     */   private static final class MethodInfo<T>
/*     */   {
/*     */     final String methodName;
/*     */     final Class<?>[] parameterTypes;
/*     */     final Class<?> returnType;
/*     */     final Class<T> returnTypeCaster;
/*     */     Method method;
/*     */     
/*     */     public MethodInfo(String methodName, Class<?>[] parameterTypes, Class<T> returnType) {
/*  76 */       this(methodName, parameterTypes, returnType, returnType);
/*     */     }
/*     */     
/*     */     public MethodInfo(String methodName, Class<?>[] parameterTypes, Class<?> returnType, Class<T> returnTypeCaster) {
/*  80 */       this.methodName = methodName;
/*  81 */       this.parameterTypes = parameterTypes;
/*  82 */       this.returnType = returnType;
/*  83 */       this.returnTypeCaster = returnTypeCaster;
/*     */     }
/*     */     
/*     */     public boolean isCompatibleWith(Method m) {
/*  87 */       if (!this.methodName.equals(m.getName())) {
/*  88 */         return false;
/*     */       }
/*     */       
/*  91 */       if (!Modifier.isPublic(m.getModifiers())) {
/*  92 */         return false;
/*     */       }
/*     */       
/*  95 */       if (!this.returnType.isAssignableFrom(m.getReturnType())) {
/*  96 */         return false;
/*     */       }
/*     */       
/*  99 */       Class<?>[] otherParamTypes = m.getParameterTypes();
/* 100 */       if (this.parameterTypes.length != otherParamTypes.length) {
/* 101 */         return false;
/*     */       }
/* 103 */       for (int i = 0; i < this.parameterTypes.length; i++) {
/* 104 */         if (!this.parameterTypes[i].isAssignableFrom(otherParamTypes[i])) {
/* 105 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 109 */       return true;
/*     */     }
/*     */     
/*     */     public T invoke(TransactionManager tmInstance, Object... args) {
/*     */       try {
/* 114 */         Object result = this.method.invoke(tmInstance, args);
/* 115 */         return this.returnTypeCaster.cast(result);
/* 116 */       } catch (IllegalAccessException ex) {
/* 117 */         throw new RuntimeException(ex);
/* 118 */       } catch (IllegalArgumentException ex) {
/* 119 */         throw new RuntimeException(ex);
/* 120 */       } catch (InvocationTargetException ex) {
/* 121 */         throw new RuntimeException(ex);
/*     */       } 
/*     */     }
/*     */   }
/* 125 */   private static final Logger LOGGER = Logger.getLogger(TransactionImportManager.class);
/*     */   private static TransactionImportManager INSTANCE;
/*     */   
/*     */   public static TransactionImportManager getInstance() {
/* 129 */     if (INSTANCE == null) INSTANCE = new TransactionImportManager(); 
/* 130 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   private static TransactionManager javaeeTM;
/*     */   private final MethodInfo<?> recreate;
/*     */   private final MethodInfo<?> release;
/*     */   private final MethodInfo<XATerminator> getXATerminator;
/*     */   private final MethodInfo<Integer> getTransactionRemainingTimeout;
/*     */   private final MethodInfo<String> getTxLogLocation;
/*     */   private static MethodInfo<?> registerRecoveryResourceHandler;
/*     */   
/*     */   private TransactionImportManager() {
/* 143 */     this(TransactionManagerImpl.getInstance().getTransactionManager());
/*     */   }
/*     */   
/*     */   private TransactionImportManager(TransactionManager tm) {
/* 147 */     javaeeTM = tm;
/*     */     
/* 149 */     this.recreate = new MethodInfo("recreate", new Class[] { Xid.class, long.class }, void.class);
/*     */ 
/*     */ 
/*     */     
/* 153 */     this.release = new MethodInfo("release", new Class[] { Xid.class }, void.class);
/*     */ 
/*     */ 
/*     */     
/* 157 */     this.getXATerminator = new MethodInfo<XATerminator>("getXATerminator", new Class[0], XATerminator.class);
/*     */ 
/*     */ 
/*     */     
/* 161 */     this.getTransactionRemainingTimeout = new MethodInfo<Integer>("getTransactionRemainingTimeout", new Class[0], int.class, Integer.class);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     this.getTxLogLocation = new MethodInfo<String>("getTxLogLocation", new Class[0], String.class);
/*     */ 
/*     */ 
/*     */     
/* 170 */     registerRecoveryResourceHandler = new MethodInfo("registerRecoveryResourceHandler", new Class[] { XAResource.class }, void.class);
/*     */ 
/*     */ 
/*     */     
/* 174 */     MethodInfo[] arrayOfMethodInfo = { this.recreate, this.release, this.getXATerminator, this.getTransactionRemainingTimeout, this.getTxLogLocation, registerRecoveryResourceHandler };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     int remainingMethodsToFind = arrayOfMethodInfo.length;
/*     */     
/* 185 */     if (javaeeTM != null) {
/* 186 */       for (Method m : javaeeTM.getClass().getDeclaredMethods()) {
/* 187 */         for (MethodInfo mi : arrayOfMethodInfo) {
/* 188 */           if (mi.isCompatibleWith(m)) {
/* 189 */             mi.method = m;
/* 190 */             remainingMethodsToFind--;
/*     */           } 
/*     */         } 
/*     */         
/* 194 */         if (remainingMethodsToFind == 0) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 200 */     if (remainingMethodsToFind != 0) {
/* 201 */       StringBuilder sb = new StringBuilder("Missing required extension methods detected on '" + TransactionManager.class.getName() + "' implementation '" + javaeeTM.getClass().getName() + "':\n");
/*     */       
/* 203 */       for (MethodInfo mi : arrayOfMethodInfo) {
/* 204 */         if (mi.method == null) {
/* 205 */           sb.append(mi.methodName).append("\n");
/*     */         }
/*     */       } 
/* 208 */       LOGGER.info(sb.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recreate(Xid xid, long timeout) {
/* 216 */     this.recreate.invoke(javaeeTM, new Object[] { xid, Long.valueOf(timeout) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release(Xid xid) {
/* 223 */     this.release.invoke(javaeeTM, new Object[] { xid });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XATerminator getXATerminator() {
/* 230 */     return this.getXATerminator.invoke(javaeeTM, new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTransactionRemainingTimeout() throws SystemException {
/* 237 */     String METHOD = "getTransactionRemainingTimeout";
/* 238 */     int result = 0;
/*     */     try {
/* 240 */       result = ((Integer)this.getTransactionRemainingTimeout.invoke(javaeeTM, new Object[0])).intValue();
/* 241 */     } catch (IllegalStateException ise) {
/* 242 */       LOGGER.finest("getTransactionRemainingTimeout " + LocalizationMessages.WSAT_4617_TXN_MGR_LOOKUP_TXN_TIMEOUT(), ise);
/*     */     } 
/* 244 */     return result;
/*     */   }
/*     */   
/*     */   public String getTxLogLocation() {
/* 248 */     return this.getTxLogLocation.invoke(javaeeTM, new Object[0]);
/*     */   }
/*     */   
/*     */   public static void registerRecoveryResourceHandler(XAResource xaResource) {
/* 252 */     registerRecoveryResourceHandler.invoke(javaeeTM, new Object[] { xaResource });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\TransactionImportManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */