/*     */ package com.sun.xml.ws.tx.at.internal;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class XidImpl
/*     */   implements Xid, Serializable
/*     */ {
/*     */   int formatId;
/*     */   byte[] globalTransactionId;
/*     */   byte[] branchQual;
/*     */   
/*     */   public XidImpl(Xid xid) {
/*  58 */     this.formatId = xid.getFormatId();
/*  59 */     this.globalTransactionId = xid.getGlobalTransactionId();
/*  60 */     this.branchQual = xid.getBranchQualifier();
/*     */   }
/*     */   
/*     */   public XidImpl(int formatId, byte[] globalTransactionId, byte[] branchQual) {
/*  64 */     this.formatId = formatId;
/*  65 */     this.globalTransactionId = globalTransactionId;
/*  66 */     this.branchQual = branchQual;
/*     */   }
/*     */   
/*     */   public XidImpl(byte[] globalTransactionId) {
/*  70 */     this(1234, globalTransactionId, new byte[0]);
/*     */   }
/*     */   
/*     */   public XidImpl(byte[] globalTransactionId, int formatId) {
/*  74 */     this(formatId, globalTransactionId, new byte[0]);
/*     */   }
/*     */   
/*     */   public int getFormatId() {
/*  78 */     return this.formatId;
/*     */   }
/*     */   
/*     */   public byte[] getGlobalTransactionId() {
/*  82 */     return this.globalTransactionId;
/*     */   }
/*     */   
/*     */   public byte[] getBranchQualifier() {
/*  86 */     return this.branchQual;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  91 */     int pos = 0;
/*  92 */     byte[] array = this.globalTransactionId;
/*  93 */     return (short)((array[pos++] & 0xFF) << 8 | array[pos] & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  98 */     if (obj == this) return true; 
/*  99 */     if (obj == null || !(obj instanceof Xid)) return false; 
/* 100 */     Xid thatXid = (Xid)obj;
/* 101 */     boolean formatId = (getFormatId() == thatXid.getFormatId());
/* 102 */     boolean gtrid = Arrays.equals(getGlobalTransactionId(), thatXid.getGlobalTransactionId());
/* 103 */     boolean branchqual = Arrays.equals(getBranchQualifier(), thatXid.getBranchQualifier());
/* 104 */     return (formatId && gtrid && branchqual);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\internal\XidImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */