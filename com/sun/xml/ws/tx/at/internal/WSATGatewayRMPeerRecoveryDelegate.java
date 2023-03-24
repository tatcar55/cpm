/*     */ package com.sun.xml.ws.tx.at.internal;
/*     */ 
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
/*     */ public class WSATGatewayRMPeerRecoveryDelegate
/*     */   implements XAResource
/*     */ {
/*     */   String peerLogLocation;
/*     */   
/*     */   public WSATGatewayRMPeerRecoveryDelegate() {}
/*     */   
/*     */   public WSATGatewayRMPeerRecoveryDelegate(String peerLogLocation) {
/*  63 */     this.peerLogLocation = peerLogLocation;
/*     */   }
/*     */   
/*     */   public void commit(Xid xid, boolean b) throws XAException {
/*  67 */     WSATGatewayRM.getInstance().commit(xid, b);
/*     */   }
/*     */   
/*     */   public void end(Xid xid, int i) throws XAException {
/*  71 */     WSATGatewayRM.getInstance().end(xid, i);
/*     */   }
/*     */   
/*     */   public void forget(Xid xid) throws XAException {
/*  75 */     WSATGatewayRM.getInstance().forget(xid);
/*     */   }
/*     */   
/*     */   public int getTransactionTimeout() throws XAException {
/*  79 */     return WSATGatewayRM.getInstance().getTransactionTimeout();
/*     */   }
/*     */   
/*     */   public boolean isSameRM(XAResource xaResource) throws XAException {
/*  83 */     return WSATGatewayRM.getInstance().isSameRM(xaResource);
/*     */   }
/*     */   
/*     */   public int prepare(Xid xid) throws XAException {
/*  87 */     return WSATGatewayRM.getInstance().prepare(xid);
/*     */   }
/*     */   
/*     */   public Xid[] recover(int i) throws XAException {
/*  91 */     return (this.peerLogLocation == null) ? WSATGatewayRM.getInstance().recover(i) : WSATGatewayRM.getInstance().recover(i, this.peerLogLocation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollback(Xid xid) throws XAException {
/*  97 */     WSATGatewayRM.getInstance().rollback(xid);
/*     */   }
/*     */   
/*     */   public boolean setTransactionTimeout(int i) throws XAException {
/* 101 */     return WSATGatewayRM.getInstance().setTransactionTimeout(i);
/*     */   }
/*     */   
/*     */   public void start(Xid xid, int i) throws XAException {
/* 105 */     WSATGatewayRM.getInstance().start(xid, i);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\internal\WSATGatewayRMPeerRecoveryDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */