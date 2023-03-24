/*    */ package com.sun.xml.ws.security.kerb;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import sun.security.jgss.GSSToken;
/*    */ import sun.security.util.ObjectIdentifier;
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
/*    */ abstract class Krb5Token
/*    */   extends GSSToken
/*    */ {
/*    */   public static final int AP_REQ_ID = 256;
/*    */   public static final int AP_REP_ID = 512;
/*    */   public static final int ERR_ID = 768;
/*    */   public static final int MIC_ID = 257;
/*    */   public static final int WRAP_ID = 513;
/*    */   public static final int MIC_ID_v2 = 1028;
/*    */   public static final int WRAP_ID_v2 = 1284;
/*    */   public static ObjectIdentifier OID;
/*    */   
/*    */   static {
/*    */     try {
/* 63 */       OID = new ObjectIdentifier(Krb5MechFactory.GSS_KRB5_MECH_OID.toString());
/*    */     }
/* 65 */     catch (IOException ioe) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getTokenName(int tokenId) {
/* 77 */     String retVal = null;
/* 78 */     switch (tokenId)
/*    */     { case 256:
/*    */       case 512:
/* 81 */         retVal = "Context Establishment Token";
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
/* 99 */         return retVal;case 257: retVal = "MIC Token"; return retVal;case 1028: retVal = "MIC Token (new format)"; return retVal;case 513: retVal = "Wrap Token"; return retVal;case 1284: retVal = "Wrap Token (new format)"; return retVal; }  retVal = "Kerberos GSS-API Mechanism Token"; return retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\Krb5Token.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */