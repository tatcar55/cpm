/*     */ package com.sun.xml.ws.tx.at.internal;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.Arrays;
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
/*     */ public class BranchXidImpl
/*     */   implements Xid, Externalizable
/*     */ {
/*     */   private Xid delegate;
/*     */   
/*     */   public BranchXidImpl() {}
/*     */   
/*     */   public BranchXidImpl(Xid xid) {
/*  63 */     this.delegate = xid;
/*     */   }
/*     */   
/*     */   public byte[] getBranchQualifier() {
/*  67 */     return this.delegate.getBranchQualifier();
/*     */   }
/*     */   
/*     */   public int getFormatId() {
/*  71 */     return this.delegate.getFormatId();
/*     */   }
/*     */   
/*     */   public byte[] getGlobalTransactionId() {
/*  75 */     return this.delegate.getGlobalTransactionId();
/*     */   }
/*     */   
/*     */   public Xid getDelegate() {
/*  79 */     return this.delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  87 */     if (!(o instanceof Xid)) return false; 
/*  88 */     Xid that = (Xid)o;
/*  89 */     boolean formatId = (getFormatId() == that.getFormatId());
/*  90 */     boolean txid = Arrays.equals(getGlobalTransactionId(), that.getGlobalTransactionId());
/*  91 */     boolean bqual = Arrays.equals(getBranchQualifier(), that.getBranchQualifier());
/*  92 */     return (formatId && txid && bqual);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  98 */     return this.delegate.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 102 */     return "BranchXidImpl:" + this.delegate.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 110 */     this.delegate = (Xid)in.readObject();
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 114 */     out.writeObject(this.delegate);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\internal\BranchXidImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */