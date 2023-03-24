/*     */ package com.sun.xml.ws.tx.at.internal;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import java.util.logging.Level;
/*     */ import javax.transaction.xa.XAException;
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
/*     */ 
/*     */ 
/*     */ class WSATNoOpXAResource
/*     */   implements XAResource
/*     */ {
/*     */   public void commit(Xid xid, boolean bln) throws XAException {
/*  65 */     debug("commit");
/*     */   }
/*     */   
/*     */   public void end(Xid xid, int i) throws XAException {
/*  69 */     debug("end");
/*     */   }
/*     */ 
/*     */   
/*     */   public void forget(Xid xid) throws XAException {}
/*     */   
/*     */   public int getTransactionTimeout() throws XAException {
/*  76 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean isSameRM(XAResource xar) throws XAException {
/*  80 */     return false;
/*     */   }
/*     */   
/*     */   public int prepare(Xid xid) throws XAException {
/*  84 */     debug("prepare");
/*  85 */     return 0;
/*     */   }
/*     */   
/*     */   public Xid[] recover(int i) throws XAException {
/*  89 */     return new Xid[0];
/*     */   }
/*     */   
/*     */   public void rollback(Xid xid) throws XAException {
/*  93 */     debug("rollback");
/*     */   }
/*     */   
/*     */   public boolean setTransactionTimeout(int i) throws XAException {
/*  97 */     return true;
/*     */   }
/*     */   
/*     */   public void start(Xid xid, int i) throws XAException {
/* 101 */     debug("start");
/*     */   }
/*     */ 
/*     */   
/*     */   private void debug(String msg) {
/* 106 */     Logger.getLogger(WSATNoOpXAResource.class).log(Level.INFO, msg);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\internal\WSATNoOpXAResource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */