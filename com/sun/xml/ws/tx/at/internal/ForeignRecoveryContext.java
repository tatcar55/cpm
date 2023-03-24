/*     */ package com.sun.xml.ws.tx.at.internal;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import javax.transaction.Transaction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ForeignRecoveryContext
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = -3257083889097518770L;
/*  59 */   private static int klassVersion = 1032;
/*     */   private Xid fxid;
/*     */   private Transactional.Version version;
/*     */   private EndpointReference epr;
/*     */   private String txLogLocation;
/*     */   private boolean recovered;
/*  65 */   private static transient Logger LOGGER = Logger.getLogger(ForeignRecoveryContext.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ForeignRecoveryContext() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ForeignRecoveryContext(Xid xid) {
/*  78 */     this.fxid = xid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEndpointReference(EndpointReference epr, Transactional.Version version) {
/*  87 */     this.epr = epr;
/*  88 */     this.version = version;
/*     */   }
/*     */   
/*     */   public Xid getXid() {
/*  92 */     return this.fxid;
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
/*     */   public EndpointReference getEndpointReference() {
/* 108 */     return this.epr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Transactional.Version getVersion() {
/* 116 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Transaction getTransaction() {
/* 125 */     if (this.fxid == null) {
/* 126 */       throw new AssertionError("No Tid to Xid mapping for " + this);
/*     */     }
/* 128 */     return null;
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
/*     */   public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
/* 141 */     klassVersion = in.readInt();
/* 142 */     this.fxid = (Xid)in.readObject();
/* 143 */     debug("ForeignRecoveryContext.readExternal tid:" + this.fxid);
/* 144 */     this.version = (Transactional.Version)in.readObject();
/* 145 */     int len = in.readInt();
/* 146 */     byte[] eprBytes = new byte[len];
/* 147 */     in.readFully(eprBytes);
/* 148 */     this.epr = EndpointReference.readFrom(new StreamSource(new ByteArrayInputStream(eprBytes)));
/* 149 */     debug("ForeignRecoveryContext.readExternal EndpointReference:" + this.epr);
/* 150 */     ForeignRecoveryContextManager.getInstance().add(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 159 */     out.writeInt(klassVersion);
/* 160 */     out.writeObject(this.fxid);
/* 161 */     out.writeObject(this.version);
/* 162 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 163 */     this.epr.writeTo(new StreamResult(bos));
/* 164 */     byte[] eprBytes = bos.toByteArray();
/* 165 */     out.writeInt(eprBytes.length);
/* 166 */     out.write(eprBytes);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 170 */     return "ForeignRecoveryContext[tid=" + this.fxid + ", endPointreference=" + this.epr + ", version = " + this.version + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void debug(String msg) {
/* 176 */     LOGGER.info(msg);
/*     */   }
/*     */   
/*     */   public void setTxLogLocation(String logLocation) {
/* 180 */     this.txLogLocation = logLocation;
/*     */   }
/*     */   
/*     */   public String getTxLogLocation() {
/* 184 */     return this.txLogLocation;
/*     */   }
/*     */   
/*     */   public void setRecovered() {
/* 188 */     this.recovered = true;
/*     */   }
/*     */   
/*     */   public boolean isRecovered() {
/* 192 */     return this.recovered;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\internal\ForeignRecoveryContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */