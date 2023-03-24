/*     */ package com.sun.xml.ws.tx.at.common;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.logging.Level;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingException;
/*     */ import javax.transaction.HeuristicMixedException;
/*     */ import javax.transaction.HeuristicRollbackException;
/*     */ import javax.transaction.InvalidTransactionException;
/*     */ import javax.transaction.NotSupportedException;
/*     */ import javax.transaction.RollbackException;
/*     */ import javax.transaction.Synchronization;
/*     */ import javax.transaction.SystemException;
/*     */ import javax.transaction.Transaction;
/*     */ import javax.transaction.TransactionManager;
/*     */ import javax.transaction.TransactionSynchronizationRegistry;
/*     */ import javax.transaction.UserTransaction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransactionManagerImpl
/*     */   implements TransactionManager, TransactionSynchronizationRegistry
/*     */ {
/*  61 */   private static final TxLogger logger = TxLogger.getATLogger(TransactionManagerImpl.class);
/*     */   
/*     */   private static final String AS_TXN_MGR_JNDI_NAME = "java:appserver/TransactionManager";
/*  64 */   private static final String TXN_MGR_JNDI_NAME = System.getProperty("com.sun.xml.ws.tx.txnMgrJndiName", "java:appserver/TransactionManager");
/*     */   
/*     */   private static final String TXN_SYNC_REG_JNDI_NAME = "java:comp/TransactionSynchronizationRegistry";
/*     */   
/*     */   private static final String USER_TRANSACTION_JNDI_NAME = "java:comp/UserTransaction";
/*  69 */   private static final TransactionManagerImpl singleton = new TransactionManagerImpl(); private final TransactionManager javaeeTM;
/*     */   
/*     */   public static TransactionManagerImpl getInstance() {
/*  72 */     return singleton;
/*     */   }
/*     */   private final TransactionSynchronizationRegistry javaeeSynchReg;
/*     */   private static Object jndiLookup(String jndiName) {
/*  76 */     Object result = null;
/*     */     try {
/*  78 */       Context ctx = new InitialContext();
/*  79 */       result = ctx.lookup(jndiName);
/*  80 */     } catch (NamingException e) {}
/*     */ 
/*     */     
/*  83 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TransactionManagerImpl() {
/*  93 */     this.javaeeTM = (TransactionManager)jndiLookup(TXN_MGR_JNDI_NAME);
/*  94 */     this.javaeeSynchReg = (TransactionSynchronizationRegistry)jndiLookup("java:comp/TransactionSynchronizationRegistry");
/*     */   }
/*     */   
/*     */   public TransactionManager getTransactionManager() {
/*  98 */     return this.javaeeTM;
/*     */   }
/*     */   
/*     */   public UserTransaction getUserTransaction() {
/* 102 */     return (UserTransaction)jndiLookup("java:comp/UserTransaction");
/*     */   }
/*     */   
/*     */   public boolean isTransactionManagerAvailable() {
/* 106 */     return (this.javaeeTM != null);
/*     */   }
/*     */   
/*     */   public void begin() throws NotSupportedException, SystemException {
/* 110 */     this.javaeeTM.begin();
/*     */   }
/*     */   
/*     */   public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
/* 114 */     this.javaeeTM.commit();
/*     */   }
/*     */   
/*     */   public int getStatus() throws SystemException {
/* 118 */     return this.javaeeTM.getStatus();
/*     */   }
/*     */   
/*     */   public Transaction getTransaction() throws SystemException {
/* 122 */     return this.javaeeTM.getTransaction();
/*     */   }
/*     */   
/*     */   public void resume(Transaction transaction) throws InvalidTransactionException, IllegalStateException, SystemException {
/* 126 */     this.javaeeTM.resume(transaction);
/* 127 */     servletPreInvokeTx();
/*     */   }
/*     */   
/*     */   public void rollback() throws IllegalStateException, SecurityException, SystemException {
/* 131 */     this.javaeeTM.rollback();
/*     */   }
/*     */   
/*     */   public void setRollbackOnly() throws IllegalStateException {
/* 135 */     this.javaeeSynchReg.setRollbackOnly();
/*     */   }
/*     */   
/*     */   public void setTransactionTimeout(int seconds) throws SystemException {
/* 139 */     this.javaeeTM.setTransactionTimeout(seconds);
/*     */   }
/*     */   
/*     */   public Transaction suspend() throws SystemException {
/* 143 */     servletPostInvokeTx(Boolean.valueOf(true));
/* 144 */     return this.javaeeTM.suspend();
/*     */   }
/*     */   
/*     */   public Object getTransactionKey() {
/* 148 */     return this.javaeeSynchReg.getTransactionKey();
/*     */   }
/*     */   
/*     */   public void putResource(Object object, Object object0) {
/* 152 */     this.javaeeSynchReg.putResource(object, object0);
/*     */   }
/*     */   
/*     */   public Object getResource(Object object) {
/* 156 */     return this.javaeeSynchReg.getResource(object);
/*     */   }
/*     */   
/*     */   public void registerInterposedSynchronization(Synchronization synchronization) {
/* 160 */     this.javaeeSynchReg.registerInterposedSynchronization(synchronization);
/*     */   }
/*     */   
/*     */   public void registerSynchronization(Synchronization sync) {
/* 164 */     if (sync == null) {
/*     */       return;
/*     */     }
/*     */     
/* 168 */     Transaction txn = null;
/*     */     try {
/* 170 */       txn = this.javaeeTM.getTransaction();
/* 171 */     } catch (SystemException ex) {}
/*     */ 
/*     */     
/* 174 */     if (txn != null) {
/*     */       
/*     */       try {
/*     */         
/* 178 */         txn.registerSynchronization(sync);
/* 179 */       } catch (IllegalStateException ex) {
/*     */       
/* 181 */       } catch (RollbackException ex) {
/*     */       
/* 183 */       } catch (SystemException ex) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTransactionStatus() {
/* 190 */     return this.javaeeSynchReg.getTransactionStatus();
/*     */   }
/*     */   
/*     */   public boolean getRollbackOnly() {
/* 194 */     return this.javaeeSynchReg.getRollbackOnly();
/*     */   }
/*     */   
/*     */   private static Method getMethod(Class<?> theClass, String methodName, Class<?> param) {
/* 198 */     Method method = null;
/*     */     try {
/* 200 */       if (param == null) {
/* 201 */         method = theClass.getMethod(methodName, new Class[0]);
/*     */       } else {
/* 203 */         method = theClass.getMethod(methodName, new Class[] { param });
/*     */       } 
/* 205 */       logger.finest("getMethod", "found Sun App Server 9.1 container specific method via reflection " + theClass.getName() + "." + methodName);
/* 206 */     } catch (Exception e) {
/* 207 */       logger.finest("getMethod", "reflection lookup of  " + theClass.getName() + "." + methodName + "(" + ((param == null) ? "" : param.getName()) + ") failed with handled exception ", e);
/*     */     } 
/* 209 */     return method;
/*     */   }
/*     */   private static boolean initialized = false;
/* 212 */   private static Method servletPreInvokeTxMethod = null;
/* 213 */   private static Method servletPostInvokeTxMethod = null;
/*     */   
/*     */   private void initServletMethods() {
/* 216 */     if (!initialized) {
/* 217 */       initialized = true;
/* 218 */       servletPreInvokeTxMethod = getMethod(this.javaeeTM.getClass(), "servletPreInvokeTx", null);
/* 219 */       servletPostInvokeTxMethod = getMethod(this.javaeeTM.getClass(), "servletPostInvokeTx", boolean.class);
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
/*     */ 
/*     */   
/*     */   public void servletPreInvokeTx() {
/* 236 */     initServletMethods();
/* 237 */     if (servletPreInvokeTxMethod != null) {
/*     */       try {
/* 239 */         servletPreInvokeTxMethod.invoke(this.javaeeTM, new Object[0]);
/* 240 */       } catch (Throwable ex) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void servletPostInvokeTx(Boolean suspend) {
/* 259 */     initServletMethods();
/* 260 */     if (servletPostInvokeTxMethod != null) {
/*     */       try {
/* 262 */         servletPostInvokeTxMethod.invoke(this.javaeeTM, new Object[] { suspend });
/* 263 */       } catch (Throwable ex) {}
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
/*     */   public int getRemainingTimeout() {
/* 278 */     String METHOD = "getRemainingTimeout";
/*     */     try {
/* 280 */       return TransactionImportManager.getInstance().getTransactionRemainingTimeout();
/* 281 */     } catch (SystemException se) {
/* 282 */       if (logger.isLogging(Level.FINEST)) {
/* 283 */         logger.finest("getRemainingTimeout", "getRemainingTimeout stack trace", (Throwable)se);
/*     */       
/*     */       }
/*     */     }
/* 287 */     catch (Throwable t) {
/* 288 */       if (logger.isLogging(Level.FINEST)) {
/* 289 */         logger.finest("getRemainingTimeout", "getTransactionRemainingTimeout() failed, default to no timeout", t);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 294 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\TransactionManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */