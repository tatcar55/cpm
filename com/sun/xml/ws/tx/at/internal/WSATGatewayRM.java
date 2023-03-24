/*     */ package com.sun.xml.ws.tx.at.internal;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.at.WSATXAResource;
/*     */ import com.sun.xml.ws.tx.at.common.TransactionImportManager;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.tx.dev.WSATRuntimeConfig;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.transaction.RollbackException;
/*     */ import javax.transaction.SystemException;
/*     */ import javax.transaction.Transaction;
/*     */ import javax.transaction.xa.XAException;
/*     */ import javax.transaction.xa.XAResource;
/*     */ import javax.transaction.xa.Xid;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSATGatewayRM
/*     */   implements XAResource, WSATRuntimeConfig.RecoveryEventListener
/*     */ {
/*  70 */   private static final Logger LOGGER = Logger.getLogger(WSATGatewayRM.class);
/*     */   
/*     */   private static final String WSAT = "wsat";
/*     */   private static final String OUTBOUND = "outbound";
/*     */   private static final String INBOUND = "inbound";
/*     */   private static WSATGatewayRM singleton;
/*     */   private static String resourceRegistrationName;
/*     */   private static Map<Xid, BranchRecord> branches;
/*     */   static List<Xid> pendingXids;
/*  79 */   private final Object currentXidLock = new Object();
/*     */   private Xid currentXid;
/*     */   static boolean isReadyForRecovery = false;
/*     */   public static boolean isReadyForRuntime = false;
/*     */   public static String txlogdir;
/*     */   static String txlogdirInbound;
/*     */   private static String txlogdirOutbound;
/*     */   static boolean isStoreInit = false;
/*  87 */   private volatile int counter = 0;
/*  88 */   private Map<Xid, Xid> activityXidToInternalXidMap = new HashMap<Xid, Xid>();
/*  89 */   private Map<Xid, Xid> internalXidToActivityXidMap = new HashMap<Xid, Xid>();
/*     */ 
/*     */   
/*     */   WSATGatewayRM(String serverName) {
/*  93 */     resourceRegistrationName = "RM_NAME_PREFIX" + serverName;
/*  94 */     branches = Collections.synchronizedMap(new HashMap<Xid, BranchRecord>());
/*  95 */     pendingXids = Collections.synchronizedList(new ArrayList<Xid>());
/*  96 */     singleton = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized WSATGatewayRM getInstance() {
/* 104 */     if (singleton == null) {
/* 105 */       create("server");
/*     */     }
/* 107 */     return singleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized WSATGatewayRM create() {
/* 115 */     return create("server");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized WSATGatewayRM create(String serverName) {
/* 125 */     if (singleton == null) {
/* 126 */       new WSATGatewayRM(serverName);
/* 127 */       isReadyForRecovery = setupRecovery();
/*     */     } 
/* 129 */     return singleton;
/*     */   }
/*     */   
/*     */   private static boolean setupRecovery() {
/* 133 */     if (!WSATRuntimeConfig.getInstance().isWSATRecoveryEnabled()) return true; 
/* 134 */     TransactionImportManager.getInstance(); TransactionImportManager.registerRecoveryResourceHandler(singleton);
/* 135 */     WSATRuntimeConfig.getInstance().setWSATRecoveryEventListener(singleton);
/* 136 */     setTxLogDirs();
/*     */     try {
/* 138 */       initStore();
/* 139 */       return true;
/* 140 */     } catch (Exception e) {
/* 141 */       e.printStackTrace();
/* 142 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void initStore() throws Exception {
/* 151 */     if (isStoreInit)
/* 152 */       return;  if (WSATHelper.isDebugEnabled()) debug("WSATGatewayRM.initStore path:" + txlogdirInbound); 
/* 153 */     createFile(txlogdirInbound, true);
/* 154 */     if (WSATHelper.isDebugEnabled()) debug("WSATGatewayRM.initStore path:" + txlogdirOutbound); 
/* 155 */     createFile(txlogdirOutbound, true);
/* 156 */     isStoreInit = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static File createFile(String logFilePath, boolean isDir) throws Exception {
/* 161 */     File file = new File(logFilePath);
/* 162 */     if (!file.exists()) {
/* 163 */       if (isDir && !file.mkdirs())
/* 164 */         throw new Exception("Could not create directory : " + file.getAbsolutePath()); 
/* 165 */       if (!isDir) {
/*     */         try {
/* 167 */           file.createNewFile();
/* 168 */         } catch (IOException ioe) {
/* 169 */           Exception storeEx = new Exception("Could not create file : " + file.getAbsolutePath());
/* 170 */           storeEx.initCause(ioe);
/* 171 */           throw storeEx;
/*     */         } 
/*     */       }
/*     */     } 
/* 175 */     return file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void recoverPendingBranches(String outboundRecoveryDir, String inboundRecoveryDir) {
/* 182 */     if (WSATHelper.isDebugEnabled()) debug("recoverPendingBranches outbound directory:" + outboundRecoveryDir);
/*     */ 
/*     */     
/* 185 */     File[] files = (new File(outboundRecoveryDir)).listFiles();
/* 186 */     if (files != null) for (int i = 0; i < files.length; i++) {
/*     */         try {
/* 188 */           FileInputStream fileInputStream = new FileInputStream(files[i]);
/* 189 */           ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
/* 190 */           BranchRecord branch = (BranchRecord)objectInputStream.readObject();
/* 191 */           branch.setTxLogLocation(files[i].getCanonicalPath());
/* 192 */           branches.put(branch.getXid(), branch);
/* 193 */           pendingXids.addAll(branch.getAllXids());
/* 194 */           objectInputStream.close();
/* 195 */         } catch (Throwable e) {
/* 196 */           throw new WebServiceException("Failure while recovering WS-AT transaction logs outbound file:" + files[i], e);
/*     */         } 
/*     */       }  
/* 199 */     if (WSATHelper.isDebugEnabled()) debug("recoverPendingBranches inbound directory:" + inboundRecoveryDir); 
/* 200 */     FileInputStream fis = null;
/* 201 */     ObjectInputStream in = null;
/* 202 */     files = (new File(inboundRecoveryDir)).listFiles();
/* 203 */     if (files != null) for (int i = 0; i < files.length; i++) {
/*     */         try {
/* 205 */           fis = new FileInputStream(files[i]);
/* 206 */           in = new ObjectInputStream(fis);
/* 207 */           ForeignRecoveryContext frc = (ForeignRecoveryContext)in.readObject();
/* 208 */           frc.setTxLogLocation(files[i].getCanonicalPath());
/* 209 */           frc.setRecovered();
/* 210 */           ForeignRecoveryContextManager.getInstance().add(frc);
/* 211 */           in.close();
/* 212 */         } catch (Throwable e) {
/* 213 */           throw new WebServiceException("Failure while recovering WS-AT transaction logs inbound file:" + files[i], e);
/*     */         } 
/*     */       } 
/*     */   
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
/*     */   public Xid registerWSATResource(Xid xid, XAResource wsatResource, Transaction tx) throws IllegalStateException, RollbackException, SystemException {
/* 233 */     if (tx == null)
/* 234 */       throw new IllegalStateException("Transaction " + tx + " does not exist, wsatResource=" + wsatResource); 
/* 235 */     Xid xidFromActivityMap = this.activityXidToInternalXidMap.get(xid);
/*     */     
/* 237 */     if (xidFromActivityMap != null) {
/* 238 */       BranchRecord branch = getBranch(xidFromActivityMap);
/* 239 */       WSATXAResource resource = (WSATXAResource)branch.exists(wsatResource);
/* 240 */       if (resource != null) return resource.getXid();
/*     */     
/*     */     } 
/* 243 */     tx.enlistResource(new WSATNoOpXAResource());
/* 244 */     synchronized (this.currentXidLock) {
/* 245 */       tx.enlistResource(new WSATGatewayRMPeerRecoveryDelegate());
/*     */       
/* 247 */       ((WSATXAResource)wsatResource).setXid(this.currentXid);
/* 248 */       BranchRecord branch = getBranch(this.currentXid);
/* 249 */       branch.addSubordinate(this.currentXid, (WSATXAResource)wsatResource);
/* 250 */       this.activityXidToInternalXidMap.put(xid, this.currentXid);
/* 251 */       this.internalXidToActivityXidMap.put(this.currentXid, xid);
/* 252 */       if (WSATHelper.isDebugEnabled())
/* 253 */         debug("registerWSATResource() xid=" + this.currentXid); 
/*     */     } 
/* 255 */     return this.currentXid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(Xid xid, int flags) throws XAException {
/*     */     BranchRecord branch;
/* 267 */     this.currentXid = xid;
/* 268 */     debug("start currentXid:" + this.currentXid + " xid:" + xid);
/* 269 */     if (WSATHelper.isDebugEnabled())
/* 270 */       debug("start() xid=" + xid + ", flags=" + flags); 
/* 271 */     switch (flags) {
/*     */       case 0:
/* 273 */         getOrCreateBranch(xid);
/*     */         return;
/*     */       case 2097152:
/*     */       case 134217728:
/* 277 */         branch = getBranch(xid);
/* 278 */         if (branch == null) {
/* 279 */           JTAHelper.throwXAException(-4, "Attempt to resume xid " + xid + " that is not in SUSPENDED state.");
/*     */         }
/*     */         return;
/*     */ 
/*     */       
/*     */       case 536870912:
/* 285 */         JTAHelper.throwXAException(-3, "error while attempting to rollback branch" + resourceRegistrationName);
/*     */         return;
/*     */     } 
/*     */     
/* 289 */     throw new IllegalArgumentException("invalid flag:" + flags);
/*     */   }
/*     */ 
/*     */   
/*     */   public void end(Xid xid, int flags) throws XAException {
/* 294 */     if (WSATHelper.isDebugEnabled())
/* 295 */       debug("end() xid=" + xid + ", flags=" + flags); 
/* 296 */     BranchRecord branch = getBranch(xid);
/* 297 */     if (branch == null) {
/* 298 */       JTAHelper.throwXAException(-4, "end: no branch info for " + xid);
/*     */     }
/*     */   }
/*     */   
/*     */   public int prepare(Xid xid) throws XAException {
/*     */     int vote;
/* 304 */     if (WSATHelper.isDebugEnabled()) debug("prepare() xid=" + xid); 
/* 305 */     purgeActivityAndInternalXidMapEntries(xid);
/* 306 */     BranchRecord branch = getBranch(xid);
/* 307 */     if (WSATHelper.isDebugEnabled()) debug("prepare() xid=" + xid + " branch=" + branch); 
/* 308 */     if (branch == null) {
/* 309 */       JTAHelper.throwXAException(-4, "prepare: no branch info for " + xid);
/*     */     }
/* 311 */     if (WSATHelper.isDebugEnabled()) debug("prepare() xid=" + xid); 
/* 312 */     persistBranchIfNecessary(branch);
/*     */     
/*     */     try {
/* 315 */       vote = branch.prepare(xid);
/* 316 */     } catch (XAException xae) {
/* 317 */       deleteBranchIfNecessary(branch);
/* 318 */       throw xae;
/*     */     } 
/* 320 */     if (vote == 3) deleteBranchIfNecessary(branch); 
/* 321 */     return vote;
/*     */   }
/*     */   
/*     */   private void purgeActivityAndInternalXidMapEntries(Xid xid) {
/* 325 */     Xid activityXid = this.internalXidToActivityXidMap.remove(xid);
/* 326 */     if (activityXid != null) this.activityXidToInternalXidMap.remove(activityXid); 
/*     */   }
/*     */   
/*     */   public void commit(Xid xid, boolean onePhase) throws XAException {
/* 330 */     if (WSATHelper.isDebugEnabled()) debug("commit() xid=" + xid); 
/* 331 */     BranchRecord branch = getBranch(xid);
/* 332 */     if (branch == null) {
/* 333 */       JTAHelper.throwXAException(-4, "commit: no branch information for xid:" + xid);
/*     */     }
/*     */     try {
/* 336 */       branch.commit(xid, onePhase);
/*     */     } finally {
/* 338 */       deleteBranchIfNecessary(branch);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rollback(Xid xid) throws XAException {
/* 343 */     if (WSATHelper.isDebugEnabled()) debug("rollback() xid=" + xid); 
/* 344 */     purgeActivityAndInternalXidMapEntries(xid);
/* 345 */     BranchRecord branch = getBranch(xid);
/* 346 */     if (branch == null) {
/* 347 */       JTAHelper.throwXAException(-4, "rollback: no branch info for " + xid);
/*     */     }
/*     */     
/*     */     try {
/* 351 */       branch.rollback(xid);
/*     */     } finally {
/* 353 */       deleteBranchIfNecessary(branch);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recover() {
/*     */     try {
/* 362 */       recover(25165824);
/* 363 */     } catch (XAException e) {
/* 364 */       e.printStackTrace();
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
/*     */   public Xid[] recover(int flag) throws XAException {
/* 376 */     return recover(flag, null);
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
/*     */   public Xid[] recover(int flag, String instance) throws XAException {
/* 388 */     if (WSATHelper.isDebugEnabled()) debug("recover() flag=" + flag); 
/* 389 */     if (!isReadyForRecovery) throw new XAException("recover call on WS-AT gateway failed due to failed initialization"); 
/* 390 */     boolean isDelegated = (instance != null);
/* 391 */     if (isDelegated) {
/* 392 */       String delegatedtxlogdir = txlogdir + File.separator + ".." + File.separator + ".." + File.separator + instance + File.separator + "wsat" + File.separator;
/*     */       
/* 394 */       debug("delegatedtxlogdir in recover is" + delegatedtxlogdir);
/* 395 */       String delegatedtxlogdirOutbound = delegatedtxlogdir + "outbound" + File.separator;
/* 396 */       String delegatedtxlogdirInbound = delegatedtxlogdir + "inbound" + File.separator;
/* 397 */       if (WSATHelper.isDebugEnabled()) debug("recover() for delegate flag=" + flag + " delegatedtxlogdirOutbound:" + delegatedtxlogdirOutbound + ", delegatedtxlogdirInbound:" + delegatedtxlogdirInbound);
/*     */ 
/*     */       
/* 400 */       singleton.recoverPendingBranches(delegatedtxlogdirOutbound, delegatedtxlogdirInbound);
/* 401 */     } else if (!isReadyForRuntime) {
/*     */       try {
/* 403 */         initStore();
/* 404 */       } catch (Exception e) {
/* 405 */         XAException xaEx = new XAException("WSATGatewayRM recover call failed due to StoreException:" + e);
/* 406 */         xaEx.errorCode = -7;
/* 407 */         xaEx.initCause(e);
/* 408 */         throw xaEx;
/*     */       } 
/* 410 */       if (WSATHelper.isDebugEnabled()) debug("recover() for this server flag=" + flag + " txlogdirOutbound:" + txlogdirOutbound + ",txlogdirInbound:" + txlogdirInbound);
/*     */ 
/*     */       
/* 413 */       singleton.recoverPendingBranches(txlogdirOutbound, txlogdirInbound);
/* 414 */       isReadyForRuntime = true;
/*     */     } 
/*     */     
/* 417 */     if ((flag & 0x1000000) != 0) {
/* 418 */       if (WSATHelper.isDebugEnabled()) debug("WSAT recover(" + flag + ") returning " + pendingXids); 
/* 419 */       Xid[] xids = pendingXids.<Xid>toArray(new Xid[pendingXids.size()]);
/* 420 */       return xids;
/*     */     } 
/* 422 */     if (WSATHelper.isDebugEnabled()) debug("recover() returning empty array"); 
/* 423 */     return new Xid[0];
/*     */   }
/*     */   
/*     */   static void setTxLogDirs() {
/* 427 */     txlogdir = getTxLogDir();
/* 428 */     debug("txlogdir is" + txlogdir);
/* 429 */     String wstxlogdir = txlogdir;
/* 430 */     File f = new File(txlogdir);
/* 431 */     wstxlogdir = f.getParent();
/* 432 */     debug("wstxlogdir is" + wstxlogdir);
/* 433 */     txlogdirInbound = wstxlogdir + File.separator + "wsat" + File.separator + "inbound" + File.separator;
/*     */     
/* 435 */     txlogdirOutbound = wstxlogdir + File.separator + "wsat" + File.separator + "outbound" + File.separator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String getTxLogDir() {
/* 441 */     return WSATRuntimeConfig.getInstance().getTxLogLocation();
/*     */   }
/*     */   
/*     */   public void forget(Xid xid) throws XAException {
/* 445 */     if (WSATHelper.isDebugEnabled()) debug("forget() xid=" + xid); 
/* 446 */     BranchRecord branch = getBranch(xid);
/* 447 */     if (branch == null) JTAHelper.throwXAException(-4, "forget: no branch info for " + xid); 
/* 448 */     deleteBranchIfNecessary(branch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTransactionTimeout() throws XAException {
/* 457 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setTransactionTimeout(int seconds) throws XAException {
/* 467 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSameRM(XAResource xares) throws XAException {
/* 478 */     if (!(xares instanceof WSATGatewayRM)) return false; 
/* 479 */     WSATGatewayRM oxares = (WSATGatewayRM)xares;
/* 480 */     return equals(oxares);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean detectedUnavailable() {
/* 489 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDelistFlag() {
/* 497 */     return 67108864;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized BranchRecord getOrCreateBranch(Xid xid) {
/* 507 */     BranchRecord branch = getBranch(xid);
/* 508 */     if (branch == null) {
/* 509 */       branch = new BranchRecord(xid);
/* 510 */       branches.put(xid, branch);
/*     */     } 
/* 512 */     return branch;
/*     */   }
/*     */   
/*     */   private synchronized BranchRecord getBranch(Xid xid) {
/* 516 */     BranchRecord branch = branches.get(xid);
/* 517 */     if (branch != null && xid.getBranchQualifier() != null) branch.assignBranchXid(xid); 
/* 518 */     return branch;
/*     */   }
/*     */   
/*     */   private void delete(BranchRecord branch) {
/* 522 */     releaseBranchRecord(branch);
/* 523 */     branch.cleanup();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void persistBranchRecord(BranchRecord branch) throws IOException {
/* 532 */     if (!WSATRuntimeConfig.getInstance().isWSATRecoveryEnabled())
/* 533 */       return;  if (WSATHelper.isDebugEnabled()) debug("persist branch record " + branch);
/*     */ 
/*     */     
/* 536 */     String logLocation = txlogdirOutbound + File.separator + System.currentTimeMillis() + "-" + this.counter++;
/* 537 */     branch.setTxLogLocation(logLocation);
/* 538 */     FileOutputStream fos = new FileOutputStream(logLocation);
/* 539 */     ObjectOutputStream out = new ObjectOutputStream(fos);
/* 540 */     out.writeObject(branch);
/* 541 */     out.close();
/* 542 */     fos.flush();
/* 543 */     branch.setLogged(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void releaseBranchRecord(BranchRecord branch) {
/* 551 */     String logLocation = branch.getTxLogLocation();
/* 552 */     if (WSATHelper.isDebugEnabled()) debug("release branch record:" + branch + " logLocation:" + logLocation); 
/* 553 */     (new File(logLocation)).delete();
/* 554 */     branch.setLogged(false);
/*     */   }
/*     */   
/*     */   void persistBranchIfNecessary(BranchRecord branch) throws XAException {
/*     */     try {
/* 559 */       synchronized (branch) {
/* 560 */         if (!branch.isLogged()) {
/* 561 */           persistBranchRecord(branch);
/* 562 */           pendingXids.addAll(branch.getAllXids());
/*     */         } 
/*     */       } 
/* 565 */     } catch (IOException pse) {
/* 566 */       debug("error persisting branch " + branch + ": " + pse.toString());
/* 567 */       LOGGER.severe(LocalizationMessages.WSAT_4500_ERROR_PERSISTING_BRANCH_RECORD(branch.toString()), pse);
/* 568 */       JTAHelper.throwXAException(-3, "Error persisting branch " + branch, pse);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void deleteBranchIfNecessary(BranchRecord branch) throws XAException {
/* 573 */     boolean deleted = false;
/*     */     try {
/* 575 */       synchronized (branch) {
/* 576 */         branches.remove(branch.getXid());
/* 577 */         pendingXids.removeAll(branch.getAllXids());
/* 578 */         if (branch.isLogged()) {
/* 579 */           delete(branch);
/* 580 */           deleted = true;
/*     */         } 
/*     */       } 
/* 583 */     } catch (Exception pse) {
/* 584 */       debug("error deleting branch record " + branch + ": " + pse.toString());
/* 585 */       LOGGER.severe(LocalizationMessages.WSAT_4501_ERROR_DELETING_BRANCH_RECORD(branch.toString()), pse);
/* 586 */       JTAHelper.throwXAException(-3, "Error deleting branch record " + branch, pse);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void beforeRecovery(boolean delegated, String instance) {
/* 592 */     debug("afterRecovery called, delegated:" + delegated + " instance:" + instance);
/* 593 */     if (!delegated) {
/*     */       return;
/*     */     }
/* 596 */     TransactionImportManager.getInstance(); TransactionImportManager.registerRecoveryResourceHandler(new WSATGatewayRMPeerRecoveryDelegate(instance));
/*     */   }
/*     */ 
/*     */   
/*     */   public void afterRecovery(boolean success, boolean delegated, String instance) {
/* 601 */     debug("afterRecovery called, success:" + success + " delegated:" + delegated + " instance:" + instance);
/*     */   }
/*     */   
/*     */   private static void debug(String msg) {
/* 605 */     if (WSATHelper.isDebugEnabled()) {
/* 606 */       Logger.getLogger(WSATGatewayRM.class).log(Level.INFO, msg);
/*     */     }
/*     */   }
/*     */   
/*     */   private final class BranchObjectHandler
/*     */   {
/*     */     private static final int VERSION = 1;
/*     */     
/*     */     public Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
/* 615 */       int version = in.readInt();
/* 616 */       if (version != 1)
/* 617 */         throw new IOException("Stream corrupted.  Invalid WS-AT gateway branch version: " + version); 
/* 618 */       BranchRecord branch = new BranchRecord();
/* 619 */       branch.readExternal(in);
/* 620 */       if (WSATHelper.isDebugEnabled()) WSATGatewayRM.debug("read WS-AT branch " + branch); 
/* 621 */       return branch;
/*     */     }
/*     */     
/*     */     public void writeObject(ObjectOutput out, Object o) throws IOException {
/* 625 */       if (!(o instanceof BranchRecord))
/* 626 */         throw new IOException("Cannot serialize class of type: " + ((o == null) ? null : o.getClass().toString())); 
/* 627 */       out.writeInt(1);
/* 628 */       BranchRecord branch = (BranchRecord)o;
/* 629 */       branch.writeExternal(out);
/* 630 */       if (WSATHelper.isDebugEnabled()) WSATGatewayRM.debug("serialized WS-AT branch " + branch); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\internal\WSATGatewayRM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */