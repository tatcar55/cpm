/*    */ package com.sun.xml.ws.tx.at.runtime;
/*    */ 
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import javax.transaction.xa.Xid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TransactionIdHelper
/*    */ {
/*    */   private static TransactionIdHelper singleton;
/*    */   
/*    */   static {
/*    */     try {
/* 56 */       singleton = new TransactionIdHelperImpl();
/* 57 */     } catch (NoSuchAlgorithmException e) {
/* 58 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract byte[] remove(Xid paramXid);
/*    */ 
/*    */   
/*    */   public static TransactionIdHelper getInstance() {
/* 68 */     return singleton;
/*    */   }
/*    */   
/*    */   public abstract Xid remove(byte[] paramArrayOfbyte);
/*    */   
/*    */   public abstract byte[] getTid(Xid paramXid);
/*    */   
/*    */   public abstract Xid getXid(byte[] paramArrayOfbyte);
/*    */   
/*    */   public abstract Xid getOrCreateXid(byte[] paramArrayOfbyte);
/*    */   
/*    */   public abstract Xid wsatid2xid(String paramString);
/*    */   
/*    */   public abstract String xid2wsatid(Xid paramXid);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\runtime\TransactionIdHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */