/*     */ package com.sun.xml.ws.tx.at.internal;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import javax.transaction.xa.XAException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JTAHelper
/*     */ {
/*     */   static void throwXAException(int errCode, String errMsg) throws XAException {
/*  54 */     XAException ex = new XAException(xaErrorCodeToString(errCode) + ".  " + errMsg);
/*  55 */     ex.errorCode = errCode;
/*  56 */     throw ex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void throwXAException(int errCode, String errMsg, Throwable t) throws XAException {
/*  62 */     XAException ex = new XAException(xaErrorCodeToString(errCode) + ".  " + errMsg);
/*  63 */     ex.errorCode = errCode;
/*  64 */     ex.initCause(t);
/*  65 */     throw ex;
/*     */   }
/*     */   
/*     */   static String xaErrorCodeToString(int err) {
/*  69 */     return xaErrorCodeToString(err, true);
/*     */   }
/*     */   
/*     */   static String xaErrorCodeToString(int err, boolean detail) {
/*  73 */     StringBuffer msg = new StringBuffer(10);
/*  74 */     switch (err) {
/*     */       case 0:
/*  76 */         return "XA_OK";
/*     */       case 3:
/*  78 */         return "XA_RDONLY";
/*     */       case 7:
/*  80 */         msg.append("XA_HEURCOM");
/*  81 */         if (detail) msg.append(" : The transaction branch has been heuristically committed"); 
/*  82 */         return msg.toString();
/*     */       case 8:
/*  84 */         msg.append("XA_HEURHAZ");
/*  85 */         if (detail) msg.append(" : The transaction branch may have been heuristically completed"); 
/*  86 */         return msg.toString();
/*     */       case 5:
/*  88 */         msg.append("XA_HEURMIX");
/*  89 */         if (detail) msg.append(" : The transaction branch has been heuristically committed and rolled back"); 
/*  90 */         return msg.toString();
/*     */       case 6:
/*  92 */         msg.append("XA_HEURRB");
/*  93 */         if (detail) msg.append(" : The transaction branch has been heuristically rolled back"); 
/*  94 */         return msg.toString();
/*     */       case 101:
/*  96 */         msg.append("XA_RBCOMMFAIL");
/*  97 */         if (detail) msg.append(" : Rollback was caused by communication failure"); 
/*  98 */         return msg.toString();
/*     */       case 102:
/* 100 */         msg.append("XA_RBDEADLOCK");
/* 101 */         if (detail) msg.append(" : A deadlock was detected"); 
/* 102 */         return msg.toString();
/*     */       case 103:
/* 104 */         msg.append("XA_RBINTEGRITY");
/* 105 */         if (detail) msg.append(" : A condition that violates the integrity of the resource was detected"); 
/* 106 */         return msg.toString();
/*     */       case 104:
/* 108 */         msg.append("XA_RBOTHER");
/* 109 */         if (detail) msg.append(" : The resource manager rolled back the transaction branch for a reason not on this list"); 
/* 110 */         return msg.toString();
/*     */       case 105:
/* 112 */         msg.append("XA_RBPROTO");
/* 113 */         if (detail) msg.append(" : A protocol error occured in the resource manager"); 
/* 114 */         return msg.toString();
/*     */       case 100:
/* 116 */         msg.append("XA_RBROLLBACK");
/* 117 */         if (detail) msg.append(" : Rollback was caused by unspecified reason"); 
/* 118 */         return msg.toString();
/*     */       case 106:
/* 120 */         msg.append("XA_RBTIMEOUT");
/* 121 */         if (detail) msg.append(" : A transaction branch took too long"); 
/* 122 */         return msg.toString();
/*     */       case 107:
/* 124 */         msg.append("XA_RBTRANSIENT");
/* 125 */         if (detail) msg.append(" : May retry the transaction branch"); 
/* 126 */         return msg.toString();
/*     */       case -2:
/* 128 */         msg.append("XAER_ASYNC");
/* 129 */         if (detail) msg.append(" : Asynchronous operation already outstanding"); 
/* 130 */         return msg.toString();
/*     */       case -8:
/* 132 */         msg.append("XAER_DUPID");
/* 133 */         if (detail) msg.append(" : The XID already exists"); 
/* 134 */         return msg.toString();
/*     */       case -5:
/* 136 */         msg.append("XAER_INVAL");
/* 137 */         if (detail) msg.append(" : Invalid arguments were given"); 
/* 138 */         return msg.toString();
/*     */       case -4:
/* 140 */         msg.append("XAER_NOTA");
/* 141 */         if (detail) msg.append(" : The XID is not valid"); 
/* 142 */         return msg.toString();
/*     */       case -9:
/* 144 */         msg.append("XAER_OUTSIDE");
/* 145 */         if (detail) msg.append(" : The resource manager is doing work outside global transaction"); 
/* 146 */         return msg.toString();
/*     */       case -6:
/* 148 */         msg.append("XAER_PROTO");
/* 149 */         if (detail) msg.append(" : Routine was invoked in an inproper context"); 
/* 150 */         return msg.toString();
/*     */       case -3:
/* 152 */         msg.append("XAER_RMERR");
/* 153 */         if (detail) msg.append(" : A resource manager error has occured in the transaction branch"); 
/* 154 */         return msg.toString();
/*     */       case -7:
/* 156 */         msg.append("XAER_RMFAIL");
/* 157 */         if (detail) msg.append(" : Resource manager is unavailable"); 
/* 158 */         return msg.toString();
/*     */     } 
/* 160 */     return Integer.toHexString(err).toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\internal\JTAHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */