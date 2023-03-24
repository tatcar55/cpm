/*     */ package com.sun.xml.ws.tx.at.internal;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.at.WSATXAResource;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public class BranchRecord
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = -8663994789749988958L;
/*     */   private static final int VERSION = 1;
/*     */   private Xid globalXid;
/*     */   private Map<Xid, RegisteredResource> registeredResources;
/*  72 */   private String branchAliasSuffix = "BI_WSATGatewayRM";
/*     */   private boolean logged;
/*     */   private String txLogLocation;
/*  75 */   private static final Logger LOGGER = Logger.getLogger(BranchRecord.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BranchRecord() {
/*  81 */     this.registeredResources = new HashMap<Xid, RegisteredResource>();
/*     */   }
/*     */   
/*     */   BranchRecord(Xid xid) {
/*  85 */     this.globalXid = xid;
/*  86 */     this.registeredResources = new HashMap<Xid, RegisteredResource>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized RegisteredResource addSubordinate(Xid xid, WSATXAResource wsatResource) {
/*  96 */     debug("addSubordindate xid:" + xid + " wsatResource:" + wsatResource);
/*  97 */     RegisteredResource rr = new RegisteredResource(wsatResource);
/*  98 */     this.registeredResources.put(xid, rr);
/*  99 */     return rr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized String getBranchName(XAResource wsatResource) {
/* 110 */     int index = getResourceIndex(wsatResource);
/* 111 */     if (index == -1) {
/* 112 */       throw new IllegalStateException("WS-AT resource not associated with transaction branch " + this.globalXid);
/*     */     }
/*     */     
/* 115 */     return index + this.branchAliasSuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setLogged(boolean b) {
/* 124 */     this.logged = b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isLogged() {
/* 132 */     return this.logged;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int prepare(Xid xid) throws XAException {
/* 143 */     RegisteredResource rr = getRegisteredResource(xid);
/* 144 */     int vote = 0;
/*     */     
/* 146 */     try { vote = rr.prepare(xid); }
/* 147 */     catch (XAException xae)
/* 148 */     { switch (xae.errorCode)
/*     */       
/*     */       { case 100:
/* 151 */           throw xae;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case -4:
/* 158 */           JTAHelper.throwXAException(106, "Subordinate resource timeout.", xae);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 165 */           return vote; }  throw xae; }  return vote;
/*     */   }
/*     */   
/*     */   void rollback(Xid xid) throws XAException {
/* 169 */     if (isPrimaryBranch(xid)) {
/* 170 */       debug("rollback() xid=" + xid + " ignoring primary branch ");
/*     */     }
/* 172 */     RegisteredResource rr = getRegisteredResource(xid);
/*     */     try {
/* 174 */       rr.rollback(xid);
/* 175 */     } catch (XAException e) {
/* 176 */       switch (e.errorCode) {
/*     */         case 5:
/*     */         case 7:
/*     */         case 8:
/* 180 */           throw e;
/*     */         
/*     */         case -4:
/*     */           return;
/*     */       } 
/* 185 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void commit(Xid xid, boolean onePhase) throws XAException {
/* 191 */     if (isPrimaryBranch(xid)) {
/* 192 */       debug("commit() xid=" + xid + " ignoring primary branch");
/*     */     }
/*     */ 
/*     */     
/* 196 */     RegisteredResource rr = getRegisteredResource(xid);
/*     */     try {
/* 198 */       rr.commit(xid, onePhase);
/* 199 */     } catch (XAException e) {
/* 200 */       switch (e.errorCode) {
/*     */         case 5:
/*     */         case 6:
/*     */         case 8:
/* 204 */           throw e;
/*     */         
/*     */         case -4:
/*     */           return;
/*     */       } 
/* 209 */       throw e;
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
/*     */   private synchronized int getResourceIndex(XAResource wsatResource) {
/* 221 */     for (int i = 0; i < this.registeredResources.size(); i++) {
/* 222 */       RegisteredResource rr = this.registeredResources.get(Integer.valueOf(i));
/* 223 */       if (wsatResource.equals(rr.resource)) return i; 
/*     */     } 
/* 225 */     return -1;
/*     */   }
/*     */   
/*     */   XAResource exists(XAResource wsatResource) {
/* 229 */     int resourceIndex = getResourceIndex(wsatResource);
/* 230 */     return (resourceIndex == -1) ? null : (XAResource)(this.registeredResources.get(Integer.valueOf(resourceIndex))).resource;
/*     */   }
/*     */   
/*     */   private boolean isPrimaryBranch(Xid xid) {
/* 234 */     return Arrays.equals(xid.getBranchQualifier(), this.globalXid.getBranchQualifier());
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
/*     */   private int getResourceIndex(Xid xid) {
/* 246 */     String bqual = new String(xid.getBranchQualifier());
/* 247 */     int endPos = bqual.indexOf(this.branchAliasSuffix);
/* 248 */     if (endPos == -1) return -1; 
/* 249 */     String s = bqual.substring(0, endPos);
/* 250 */     return Integer.parseInt(s);
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
/*     */   private synchronized RegisteredResource getRegisteredResource(Xid xid) throws XAException {
/* 262 */     RegisteredResource registeredResource = this.registeredResources.get(xid);
/* 263 */     if (registeredResource == null) {
/* 264 */       JTAHelper.throwXAException(-4, "Xid=" + xid);
/*     */     }
/* 266 */     if (registeredResource.getBranchXid() != null) {
/* 267 */       boolean isRegisteredBranch = Arrays.equals(registeredResource.getBranchXid().getBranchQualifier(), xid.getBranchQualifier());
/*     */       
/* 269 */       if (!isRegisteredBranch) {
/* 270 */         WSATHelper.getInstance(); if (WSATHelper.isDebugEnabled()) {
/* 271 */           byte[] branchQualifier = registeredResource.getBranchXid().getBranchQualifier();
/* 272 */           if (branchQualifier == null) branchQualifier = new byte[0]; 
/* 273 */           debug("WS-AT Branch registered branchId:\t[" + new String(branchQualifier) + "] ");
/* 274 */           branchQualifier = xid.getBranchQualifier();
/* 275 */           if (branchQualifier == null) branchQualifier = new byte[0]; 
/* 276 */           debug("WS-AT Branch branchId used to identify a registered resource, branchQual:" + new String(branchQualifier));
/*     */         } 
/* 278 */         debug("prepare() xid=" + xid + " returning XA_RDONLY");
/* 279 */         JTAHelper.throwXAException(-4, "xid=" + xid);
/*     */       } 
/*     */     } 
/* 282 */     return registeredResource;
/*     */   }
/*     */   
/*     */   Xid getXid() {
/* 286 */     return this.globalXid;
/*     */   }
/*     */   
/*     */   synchronized Collection<Xid> getAllXids() {
/* 290 */     Collection<Xid> xids = new ArrayList<Xid>();
/* 291 */     Iterator<RegisteredResource> resourceIterator = this.registeredResources.values().iterator();
/* 292 */     while (resourceIterator.hasNext()) {
/* 293 */       xids.add(((RegisteredResource)resourceIterator.next()).getBranchXid());
/*     */     }
/* 295 */     return xids;
/*     */   }
/*     */ 
/*     */   
/*     */   void assignBranchXid(Xid xid) {
/* 300 */     int index = getResourceIndex(xid);
/* 301 */     if (index == -1)
/* 302 */       return;  RegisteredResource rr = this.registeredResources.get(Integer.valueOf(index));
/* 303 */     if (rr == null)
/* 304 */       return;  if (rr.getBranchXid() == null) rr.setBranchXid(xid);
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanup() {}
/*     */ 
/*     */   
/*     */   synchronized boolean allResourcesCompleted() {
/* 313 */     for (int i = 0, num = this.registeredResources.size(); i < num; i++) {
/* 314 */       RegisteredResource rr = this.registeredResources.get(Integer.valueOf(i));
/* 315 */       if (!rr.isCompleted()) return false; 
/*     */     } 
/* 317 */     return true;
/*     */   }
/*     */   
/*     */   public void setTxLogLocation(String logLocation) {
/* 321 */     this.txLogLocation = logLocation;
/*     */   }
/*     */   
/*     */   public String getTxLogLocation() {
/* 325 */     return this.txLogLocation;
/*     */   }
/*     */   
/*     */   private void debug(String msg) {
/* 329 */     LOGGER.info(msg);
/*     */   }
/*     */   
/*     */   class RegisteredResource
/*     */     implements Externalizable {
/*     */     private static final long serialVersionUID = 601688150453719976L;
/*     */     private static final int STATE_ACTIVE = 1;
/*     */     private static final int STATE_PREPARED = 2;
/*     */     private static final int STATE_READONLY = 3;
/*     */     private static final int STATE_COMPLETED = 4;
/*     */     private WSATXAResource resource;
/* 340 */     private int vote = -1;
/*     */ 
/*     */     
/*     */     private int state;
/*     */ 
/*     */     
/*     */     private BranchXidImpl branchXid;
/*     */ 
/*     */ 
/*     */     
/*     */     RegisteredResource(WSATXAResource wsatResource) {
/* 351 */       this.resource = wsatResource;
/* 352 */       this.state = 1;
/*     */     }
/*     */     
/*     */     private Xid getBranchXid() {
/* 356 */       if (this.branchXid == null) return null; 
/* 357 */       return this.branchXid.getDelegate();
/*     */     }
/*     */     
/*     */     private void setBranchXid(Xid xid) {
/* 361 */       this.branchXid = new BranchXidImpl(new XidImpl(xid));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void setPrepared() {
/* 368 */       this.state = 2;
/*     */     }
/*     */     
/*     */     XAResource getResource() {
/* 372 */       return (XAResource)this.resource;
/*     */     }
/*     */     
/*     */     boolean isCompleted() {
/* 376 */       return (this.state == 4 || this.state == 3);
/*     */     }
/*     */ 
/*     */     
/*     */     int prepare(Xid xid) throws XAException {
/* 381 */       switch (this.state) {
/*     */         
/*     */         case 2:
/*     */         case 3:
/* 385 */           return this.vote;
/*     */         case 4:
/* 387 */           JTAHelper.throwXAException(-5, "Resource completed.");
/*     */           break;
/*     */       } 
/*     */       
/*     */       try {
/* 392 */         this.vote = this.resource.prepare(xid);
/* 393 */         switch (this.vote) {
/*     */           case 0:
/* 395 */             this.state = 2;
/*     */             break;
/*     */           case 3:
/* 398 */             this.state = 3;
/*     */             break;
/*     */         } 
/* 401 */       } catch (XAException xae) {
/* 402 */         switch (xae.errorCode) {
/*     */           
/*     */           case 100:
/*     */           case 101:
/*     */           case 102:
/*     */           case 103:
/*     */           case 104:
/*     */           case 105:
/*     */           case 106:
/*     */           case 107:
/* 412 */             this.state = 4;
/* 413 */             throw xae;
/*     */           
/*     */           case -7:
/*     */           case -6:
/*     */           case -5:
/*     */           case -4:
/*     */           case -3:
/* 420 */             throw xae;
/*     */         } 
/* 422 */         throw xae;
/*     */       } 
/*     */ 
/*     */       
/* 426 */       return this.vote;
/*     */     }
/*     */     
/*     */     void commit(Xid xid, boolean onePhase) throws XAException {
/* 430 */       switch (this.state) {
/*     */         case 3:
/*     */         case 4:
/*     */           return;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 438 */         this.resource.commit(xid, onePhase);
/* 439 */         this.state = 4;
/* 440 */       } catch (XAException xae) {
/* 441 */         switch (xae.errorCode) {
/*     */           case 7:
/* 443 */             this.state = 4;
/*     */             return;
/*     */           case 5:
/*     */           case 6:
/*     */           case 8:
/* 448 */             this.state = 4;
/* 449 */             throw xae;
/*     */           case 100:
/*     */           case 101:
/*     */           case 102:
/*     */           case 103:
/*     */           case 104:
/*     */           case 105:
/*     */           case 106:
/*     */           case 107:
/* 458 */             if (onePhase) {
/*     */               
/* 460 */               this.state = 4;
/* 461 */               throw xae;
/*     */             } 
/*     */             
/* 464 */             JTAHelper.throwXAException(8, "Invalid rollback error code thrown for 2PC commit. ", xae);
/*     */             return;
/*     */ 
/*     */           
/*     */           case -7:
/*     */           case -6:
/*     */           case -5:
/*     */           case -3:
/* 472 */             throw xae;
/*     */           
/*     */           case -4:
/* 475 */             this.state = 4;
/*     */             return;
/*     */         } 
/* 478 */         throw xae;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void rollback(Xid xid) throws XAException {
/* 485 */       switch (this.state) {
/*     */         case 3:
/*     */         case 4:
/*     */           return;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 493 */         this.resource.rollback(xid);
/* 494 */         this.state = 4;
/* 495 */       } catch (XAException xae) {
/* 496 */         switch (xae.errorCode) {
/*     */           case 6:
/* 498 */             this.state = 4;
/*     */             return;
/*     */           case 5:
/*     */           case 7:
/*     */           case 8:
/* 503 */             this.state = 4;
/* 504 */             throw xae;
/*     */           
/*     */           case -7:
/*     */           case -6:
/*     */           case -5:
/*     */           case -3:
/* 510 */             throw xae;
/*     */           
/*     */           case -4:
/* 513 */             this.state = 4;
/*     */             return;
/*     */         } 
/* 516 */         throw xae;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 522 */       this.branchXid = new BranchXidImpl();
/* 523 */       this.branchXid.readExternal(in);
/* 524 */       this.resource = (WSATXAResource)in.readObject();
/*     */     }
/*     */     
/*     */     public void writeExternal(ObjectOutput out) throws IOException {
/* 528 */       this.resource.setXid(new XidImpl(this.resource.getXid()));
/* 529 */       (new BranchXidImpl(this.resource.getXid())).writeExternal(out);
/*     */       try {
/* 531 */         out.writeObject(this.resource);
/* 532 */       } catch (Exception e) {
/* 533 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     RegisteredResource() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 547 */     out.writeInt(1);
/*     */ 
/*     */     
/* 550 */     out.writeInt(this.globalXid.getFormatId());
/*     */     
/* 552 */     byte[] gtrid = this.globalXid.getGlobalTransactionId();
/* 553 */     out.writeByte((byte)gtrid.length);
/* 554 */     out.write(gtrid);
/*     */     
/* 556 */     byte[] bqual = this.globalXid.getBranchQualifier();
/* 557 */     if (bqual == null) {
/* 558 */       out.writeByte(-1);
/*     */     } else {
/* 560 */       out.writeByte((byte)bqual.length);
/* 561 */       out.write(bqual);
/*     */     } 
/*     */ 
/*     */     
/* 565 */     out.writeInt(this.registeredResources.size());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 572 */     Iterator<RegisteredResource> resourceIterator = this.registeredResources.values().iterator();
/* 573 */     while (resourceIterator.hasNext()) {
/* 574 */       ((RegisteredResource)resourceIterator.next()).writeExternal(out);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 581 */     int version = in.readInt();
/* 582 */     if (version != 1) {
/* 583 */       throw new IOException("invalid OTSBranch version " + version);
/*     */     }
/*     */ 
/*     */     
/* 587 */     int formatId = in.readInt();
/*     */     
/* 589 */     int len = in.readByte();
/* 590 */     byte[] gtrid = new byte[len];
/* 591 */     in.readFully(gtrid);
/*     */     
/* 593 */     len = in.readByte();
/* 594 */     byte[] bqual = null;
/* 595 */     if (len > -1) {
/* 596 */       bqual = new byte[len];
/* 597 */       in.readFully(bqual);
/*     */     } 
/*     */ 
/*     */     
/* 601 */     this.globalXid = new XidImpl(formatId, gtrid, bqual);
/*     */ 
/*     */     
/* 604 */     int resourceNum = in.readInt();
/* 605 */     for (int i = 0; i < resourceNum; i++) {
/*     */       
/* 607 */       RegisteredResource rr = new RegisteredResource();
/* 608 */       rr.readExternal(in);
/* 609 */       rr.setPrepared();
/* 610 */       this.registeredResources.put(this.globalXid, rr);
/*     */     } 
/*     */     
/* 613 */     this.logged = true;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 617 */     return "BranchRecord:globalXid=" + this.globalXid;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\internal\BranchRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */