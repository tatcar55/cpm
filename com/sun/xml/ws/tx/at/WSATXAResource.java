/*     */ package com.sun.xml.ws.tx.at;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import javax.transaction.xa.XAException;
/*     */ import javax.transaction.xa.XAResource;
/*     */ import javax.transaction.xa.Xid;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSATXAResource
/*     */   implements WSATConstants, XAResource, Serializable
/*     */ {
/*  57 */   private static final Logger LOGGER = Logger.getLogger(WSATXAResource.class);
/*     */   static final long serialVersionUID = -5827137400010343968L;
/*     */   private Xid m_xid;
/*     */   static final String ACTIVE = "ACTIVE";
/*  61 */   private volatile String m_status = "ACTIVE";
/*     */ 
/*     */   
/*     */   private Transactional.Version m_version;
/*     */   
/*     */   private boolean m_isRemovedFromMap = false;
/*     */   
/*     */   private transient EndpointReference m_epr;
/*     */ 
/*     */   
/*     */   public WSATXAResource(EndpointReference epr, Xid xid) {
/*  72 */     this(Transactional.Version.WSAT10, epr, xid, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSATXAResource(Transactional.Version version, EndpointReference epr, Xid xid) {
/*  82 */     this(version, epr, xid, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSATXAResource(Transactional.Version version, EndpointReference epr, Xid xid, boolean isRecovery) {
/*  93 */     this.m_version = version;
/*  94 */     if (epr == null)
/*  95 */       throw new IllegalArgumentException("endpoint reference can't be null"); 
/*  96 */     this.m_epr = epr;
/*  97 */     this.m_xid = xid;
/*  98 */     if (WSATHelper.isDebugEnabled())
/*  99 */       LOGGER.info(LocalizationMessages.WSAT_4538_WSAT_XARESOURCE(this.m_epr.toString(), this.m_xid, "")); 
/* 100 */     if (isRecovery) this.m_status = "Prepared";
/*     */   
/*     */   }
/*     */   
/*     */   WSATHelper getWSATHelper() {
/* 105 */     return WSATHelper.getInstance(this.m_version);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(String status) {
/* 113 */     this.m_status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int prepare(Xid xid) throws XAException {
/* 124 */     debug("prepare xid:" + xid);
/* 125 */     if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4539_PREPARE(this.m_epr.toString(), this.m_xid)); 
/* 126 */     getWSATHelper().prepare(this.m_epr, this.m_xid, this);
/*     */     try {
/* 128 */       synchronized (this) {
/*     */         
/* 130 */         if (this.m_status.equals("ReadOnly"))
/* 131 */           return 3; 
/* 132 */         if (this.m_status.equals("Prepared")) {
/* 133 */           if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4540_PREPARED_BEFORE_WAIT(this.m_epr.toString(), this.m_xid)); 
/* 134 */           return 0;
/* 135 */         }  if (this.m_status.equals("Aborted")) {
/* 136 */           throw newFailedStateXAExceptionForMethodNameAndErrorcode("prepare", 100);
/*     */         }
/* 138 */         if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4541_PREPARE_WAITING_FOR_REPLY(this.m_epr.toString(), this.m_xid));
/*     */         
/* 140 */         wait(getWaitForReplyTimeout());
/* 141 */         if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4542_PREPARE_FINISHED_WAITING_FOR_REPLY(this.m_epr.toString(), this.m_xid));
/*     */       
/*     */       } 
/* 144 */       if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4543_PREPARE_RECEIVED_REPLY_STATUS(this.m_status, this.m_epr.toString(), this.m_xid));
/*     */       
/* 146 */       if (this.m_status.equals("ReadOnly")) {
/* 147 */         logSuccess("preparereadonly");
/* 148 */         return 3;
/* 149 */       }  if (this.m_status.equals("Prepared")) {
/* 150 */         logSuccess("prepareprepared");
/* 151 */         return 0;
/* 152 */       }  if (this.m_status.equals("Aborted")) {
/* 153 */         throw newFailedStateXAExceptionForMethodNameAndErrorcode("prepare", 100);
/*     */       }
/* 155 */       LOGGER.severe(LocalizationMessages.WSAT_4544_FAILED_STATE_FOR_PREPARE(this.m_status, this.m_epr.toString(), this.m_xid));
/* 156 */       throw newFailedStateXAExceptionForMethodNameAndErrorcode("prepare", -7);
/* 157 */     } catch (InterruptedException e) {
/* 158 */       LOGGER.info(LocalizationMessages.WSAT_4545_INTERRUPTED_EXCEPTION_DURING_PREPARE(e, this.m_epr.toString(), this.m_xid));
/* 159 */       XAException xaException = new XAException("InterruptedException during WS-AT XAResource prepare");
/* 160 */       xaException.errorCode = -7;
/* 161 */       xaException.initCause(e);
/* 162 */       throw xaException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private XAException newFailedStateXAExceptionForMethodNameAndErrorcode(String method, int errorcode) {
/* 168 */     XAException xaException = new XAException("Failed state during " + method + " of WS-AT XAResource:" + this);
/* 169 */     xaException.errorCode = errorcode;
/* 170 */     return xaException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 180 */     super.finalize();
/* 181 */     if (!this.m_isRemovedFromMap) getWSATHelper().removeDurableParticipant(this);
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
/*     */   public void commit(Xid xid, boolean onePhase) throws XAException {
/* 194 */     debug("commit xid:" + xid + " onePhase:" + onePhase);
/* 195 */     if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4546_COMMIT(this.m_epr.toString(), this.m_xid)); 
/* 196 */     getWSATHelper().commit(this.m_epr, this.m_xid, this);
/*     */     try {
/* 198 */       synchronized (this) {
/* 199 */         if (this.m_status.equals("Committed")) {
/* 200 */           if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4547_COMMIT_BEFORE_WAIT(this.m_epr.toString(), this.m_xid)); 
/* 201 */           getWSATHelper().removeDurableParticipant(this);
/* 202 */           this.m_isRemovedFromMap = true;
/*     */           return;
/*     */         } 
/* 205 */         if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4548_COMMIT_WAITING_FOR_REPLY(this.m_epr.toString(), this.m_xid)); 
/* 206 */         wait(getWaitForReplyTimeout());
/* 207 */         if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4549_COMMIT_FINISHED_WAITING_FOR_REPLY(this.m_epr.toString(), this.m_xid));
/*     */       
/*     */       } 
/* 210 */       if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4550_COMMIT_RECEIVED_REPLY_STATUS(this.m_status, this.m_epr.toString(), this.m_xid));
/*     */       
/* 212 */       if (this.m_status.equals("Committed"))
/* 213 */       { logSuccess("preparecommitted");
/* 214 */         getWSATHelper().removeDurableParticipant(this);
/* 215 */         this.m_isRemovedFromMap = true; }
/* 216 */       else { if (this.m_status.equals("Prepared")) {
/* 217 */           LOGGER.severe(LocalizationMessages.WSAT_4551_FAILED_STATE_FOR_COMMIT(this.m_status, this.m_epr.toString(), this.m_xid));
/*     */           
/* 219 */           XAException xAException = newFailedStateXAExceptionForMethodNameAndErrorcode("commit", -7);
/* 220 */           log("Failed state during WS-AT XAResource commit:" + this.m_status, xAException);
/* 221 */           throw xAException;
/*     */         } 
/* 223 */         LOGGER.severe(LocalizationMessages.WSAT_4551_FAILED_STATE_FOR_COMMIT(this.m_status, this.m_epr.toString(), this.m_xid));
/*     */         
/* 225 */         XAException xaException = newFailedStateXAExceptionForMethodNameAndErrorcode("commit", -6);
/* 226 */         log("Failed state during WS-AT XAResource commit:" + this.m_status, xaException);
/* 227 */         throw xaException; }
/*     */     
/* 229 */     } catch (InterruptedException e) {
/* 230 */       LOGGER.severe(LocalizationMessages.WSAT_4552_INTERRUPTED_EXCEPTION_DURING_COMMIT(this.m_epr.toString(), this.m_xid), e);
/* 231 */       XAException xaException = new XAException("InterruptedException during WS-AT XAResource commit:" + e);
/* 232 */       xaException.errorCode = -7;
/* 233 */       xaException.initCause(e);
/* 234 */       throw xaException;
/*     */     } finally {
/* 236 */       getWSATHelper().removeDurableParticipant(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getWaitForReplyTimeout() {
/* 245 */     return getWSATHelper().getWaitForReplyTimeout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollback(Xid xid) throws XAException {
/* 254 */     debug("rollback xid:" + xid);
/* 255 */     if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4553_ROLLBACK(this.m_epr.toString(), this.m_xid)); 
/* 256 */     getWSATHelper().rollback(this.m_epr, this.m_xid, this);
/*     */     try {
/* 258 */       synchronized (this) {
/* 259 */         if (this.m_status.equals("Aborted")) {
/* 260 */           if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4554_ROLLBACK_BEFORE_WAIT(this.m_epr.toString(), this.m_xid)); 
/* 261 */           getWSATHelper().removeDurableParticipant(this);
/* 262 */           this.m_isRemovedFromMap = true;
/*     */           return;
/*     */         } 
/* 265 */         if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4555_ROLLBACK_WAITING_FOR_REPLY(this.m_epr.toString(), this.m_xid)); 
/* 266 */         wait(getWaitForReplyTimeout());
/* 267 */         if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4556_ROLLBACK_FINISHED_WAITING_FOR_REPLY(this.m_epr.toString(), this.m_xid)); 
/*     */       } 
/* 269 */       if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4557_ROLLBACK_RECEIVED_REPLY_STATUS(this.m_status, this.m_epr.toString(), this.m_xid)); 
/* 270 */       if (this.m_status.equals("Aborted"))
/* 271 */       { logSuccess("rollbackaborted");
/* 272 */         getWSATHelper().removeDurableParticipant(this);
/* 273 */         this.m_isRemovedFromMap = true; }
/* 274 */       else { if (this.m_status.equals("Prepared")) {
/* 275 */           LOGGER.severe(LocalizationMessages.WSAT_4558_FAILED_STATE_FOR_ROLLBACK(this.m_status, this.m_epr.toString(), this.m_xid));
/* 276 */           throw newFailedStateXAExceptionForMethodNameAndErrorcode("rollback", -7);
/*     */         } 
/* 278 */         LOGGER.severe(LocalizationMessages.WSAT_4558_FAILED_STATE_FOR_ROLLBACK(this.m_status, this.m_epr.toString(), this.m_xid));
/* 279 */         throw newFailedStateXAExceptionForMethodNameAndErrorcode("rollback", -7); }
/*     */     
/* 281 */     } catch (InterruptedException e) {
/* 282 */       LOGGER.severe(LocalizationMessages.WSAT_4559_INTERRUPTED_EXCEPTION_DURING_ROLLBACK(this.m_epr.toString(), this.m_xid), e);
/* 283 */       XAException xaException = new XAException("InterruptedException during WS-AT XAResource rollback");
/* 284 */       xaException.errorCode = -7;
/* 285 */       xaException.initCause(e);
/* 286 */       throw xaException;
/*     */     } finally {
/* 288 */       getWSATHelper().removeDurableParticipant(this);
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
/*     */   public void forget(Xid xid) throws XAException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setTransactionTimeout(int i) throws XAException {
/* 311 */     return true;
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
/*     */   public void start(Xid xid, int i) throws XAException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void end(Xid xid, int i) throws XAException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTransactionTimeout() throws XAException {
/* 343 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSameRM(XAResource xaResource) throws XAException {
/* 354 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Xid[] recover(int i) throws XAException {
/* 365 */     return new Xid[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Xid getXid() {
/* 375 */     return this.m_xid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBranchQualifier(byte[] bqual) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXid(Xid xid) {
/* 386 */     this.m_xid = xid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 396 */     return (obj instanceof WSATXAResource && ((WSATXAResource)obj).getXid().equals(this.m_xid) && ((WSATXAResource)obj).m_epr.toString().equals(this.m_epr.toString()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream oos) throws IOException {
/* 403 */     oos.defaultWriteObject();
/* 404 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 405 */     this.m_epr.writeTo(new StreamResult(bos));
/* 406 */     byte[] eprBytes = bos.toByteArray();
/* 407 */     oos.writeInt(eprBytes.length);
/* 408 */     oos.write(eprBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
/* 413 */     ois.defaultReadObject();
/* 414 */     int len = ois.readInt();
/* 415 */     byte[] eprBytes = new byte[len];
/* 416 */     ois.readFully(eprBytes);
/* 417 */     this.m_epr = EndpointReference.readFrom(new StreamSource(new ByteArrayInputStream(eprBytes)));
/* 418 */     this.m_status = "Prepared";
/*     */   }
/*     */   
/*     */   private void log(String message, XAException xaex) {
/* 422 */     LOGGER.warning(message + " XAException.errorcode:" + xaex.errorCode, xaex);
/*     */   }
/*     */   
/*     */   private void logSuccess(String method) {
/* 426 */     LOGGER.info("success state during " + method + " of WS-AT XAResource:" + this);
/*     */   }
/*     */   
/*     */   private void debug(String msg) {
/* 430 */     LOGGER.info(msg);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 434 */     return "WSATXAResource: xid" + this.m_xid + " status:" + this.m_status + " epr:" + this.m_epr + " isRemovedFromMap:" + this.m_isRemovedFromMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\WSATXAResource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */