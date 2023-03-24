/*     */ package com.sun.xml.ws.tx.at.runtime;
/*     */ 
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.at.internal.XidImpl;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
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
/*     */ class TransactionIdHelperImpl
/*     */   extends TransactionIdHelper
/*     */ {
/*     */   private static final int FFID = 65309;
/*  65 */   private Map<String, Xid> tids2xids = new HashMap<String, Xid>();
/*  66 */   private Map<Xid, String> xids2tids = new HashMap<Xid, String>();
/*     */ 
/*     */   
/*     */   public String xid2wsatid(Xid xid) {
/*  70 */     return xidToString(xid, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String xidToString(Xid xid, boolean includeBranchQualifier) {
/*  76 */     if (xid == null) return ""; 
/*  77 */     StringBuffer sb = (new StringBuffer()).append(Integer.toHexString(xid.getFormatId()).toUpperCase(Locale.ENGLISH)).append("-").append(byteArrayToString(xid.getGlobalTransactionId()));
/*     */ 
/*     */     
/*  80 */     if (includeBranchQualifier) {
/*  81 */       String bqual = byteArrayToString(xid.getBranchQualifier());
/*  82 */       if (!bqual.equals("")) {
/*  83 */         sb.append("-").append(byteArrayToString(xid.getBranchQualifier()));
/*     */       }
/*     */     } 
/*  86 */     return sb.toString();
/*     */   }
/*     */   
/*  89 */   private static final char[] DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ 
/*     */   
/*     */   private static String byteArrayToString(byte[] barray) {
/*  93 */     if (barray == null) return ""; 
/*  94 */     char[] res = new char[barray.length * 2];
/*  95 */     int j = 0;
/*  96 */     for (int i = 0; i < barray.length; i++) {
/*  97 */       res[j++] = DIGITS[(barray[i] & 0xF0) >>> 4];
/*  98 */       res[j++] = DIGITS[barray[i] & 0xF];
/*     */     } 
/* 100 */     return new String(res);
/*     */   }
/*     */ 
/*     */   
/*     */   public Xid wsatid2xid(String wsatid) {
/* 105 */     return (Xid)create(wsatid);
/*     */   }
/*     */ 
/*     */   
/*     */   public static XidImpl create(String xid) {
/* 110 */     StringTokenizer tok = new StringTokenizer(xid, "-");
/* 111 */     if (tok.countTokens() < 2) return null;
/*     */     
/* 113 */     String formatIdString = tok.nextToken();
/* 114 */     String gtridString = tok.nextToken();
/* 115 */     String bqualString = null;
/* 116 */     if (tok.hasMoreElements()) {
/* 117 */       bqualString = tok.nextToken();
/*     */     }
/* 119 */     return new XidImpl(Integer.parseInt(formatIdString, 16), stringToByteArray(gtridString), (bqualString != null) ? stringToByteArray(bqualString) : new byte[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] stringToByteArray(String str) {
/* 126 */     if (str == null) return new byte[0]; 
/* 127 */     byte[] bytes = new byte[str.length() / 2];
/* 128 */     for (int i = 0, j = 0; i < str.length(); i++, j++) {
/* 129 */       bytes[j] = (byte)(Byte.parseByte(str.substring(i++, i), 16) << 4 | Byte.parseByte(str.substring(i, i + 1), 16));
/*     */     }
/*     */     
/* 132 */     return bytes;
/*     */   }
/*     */   
/*     */   public synchronized Xid getOrCreateXid(byte[] tid) {
/* 136 */     Xid xid = getXid(tid);
/* 137 */     if (xid != null) return xid; 
/* 138 */     byte[] gtrid = WSATHelper.assignUUID().getBytes();
/*     */ 
/*     */     
/* 141 */     XidImpl xidImpl = new XidImpl(65309, gtrid, null);
/* 142 */     String stid = new String(tid);
/* 143 */     this.tids2xids.put(stid, xidImpl);
/* 144 */     this.xids2tids.put(xidImpl, stid);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     return (Xid)xidImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized byte[] getTid(Xid xid) {
/* 154 */     String stid = this.xids2tids.get(xid);
/* 155 */     if (stid == null) return null; 
/* 156 */     return stid.getBytes();
/*     */   }
/*     */   
/*     */   public synchronized Xid getXid(byte[] tid) {
/* 160 */     return this.tids2xids.get(new String(tid));
/*     */   }
/*     */   
/*     */   public synchronized Xid remove(byte[] tid) {
/* 164 */     if (getXid(tid) == null)
/* 165 */       return null; 
/* 166 */     Xid xid = this.tids2xids.remove(tid);
/* 167 */     this.xids2tids.remove(xid);
/* 168 */     return xid;
/*     */   }
/*     */   
/*     */   public synchronized byte[] remove(Xid xid) {
/* 172 */     if (getTid(xid) == null)
/* 173 */       return null; 
/* 174 */     String stid = this.xids2tids.remove(xid);
/* 175 */     this.tids2xids.remove(stid);
/* 176 */     return stid.getBytes();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 180 */     return this.tids2xids.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\runtime\TransactionIdHelperImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */