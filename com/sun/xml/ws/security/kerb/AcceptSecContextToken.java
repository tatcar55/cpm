/*    */ package com.sun.xml.ws.security.kerb;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.ietf.jgss.GSSException;
/*    */ import sun.security.krb5.Credentials;
/*    */ import sun.security.krb5.EncryptionKey;
/*    */ import sun.security.krb5.KrbApRep;
/*    */ import sun.security.krb5.KrbApReq;
/*    */ import sun.security.krb5.KrbException;
/*    */ import sun.security.util.DerValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class AcceptSecContextToken
/*    */   extends InitialToken
/*    */ {
/* 19 */   private KrbApRep apRep = null;
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
/*    */   public AcceptSecContextToken(Krb5Context context, KrbApReq apReq) throws KrbException, IOException {
/* 39 */     boolean useSubkey = false;
/*    */     
/* 41 */     boolean useSequenceNumber = true;
/*    */     
/* 43 */     this.apRep = new KrbApRep(apReq, useSequenceNumber, useSubkey);
/*    */     
/* 45 */     context.resetMySequenceNumber(this.apRep.getSeqNumber().intValue());
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AcceptSecContextToken(Krb5Context context, Credentials serviceCreds, KrbApReq apReq, InputStream is) throws IOException, GSSException, KrbException {
/* 62 */     int tokenId = is.read() << 8 | is.read();
/*    */     
/* 64 */     if (tokenId != 512) {
/* 65 */       throw new GSSException(10, -1, "AP_REP token id does not match!");
/*    */     }
/*    */     
/* 68 */     byte[] apRepBytes = (new DerValue(is)).toByteArray();
/*    */ 
/*    */     
/* 71 */     KrbApRep apRep = new KrbApRep(apRepBytes, serviceCreds, apReq);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     EncryptionKey subKey = apRep.getSubKey();
/* 78 */     if (subKey != null) {
/* 79 */       context.setKey(subKey);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 86 */     Integer apRepSeqNumber = apRep.getSeqNumber();
/* 87 */     int peerSeqNumber = (apRepSeqNumber != null) ? apRepSeqNumber.intValue() : 0;
/*    */ 
/*    */     
/* 90 */     context.resetPeerSequenceNumber(peerSeqNumber);
/*    */   }
/*    */   
/*    */   public final byte[] encode() throws IOException {
/* 94 */     byte[] apRepBytes = this.apRep.getMessage();
/* 95 */     byte[] retVal = new byte[2 + apRepBytes.length];
/* 96 */     writeInt(512, retVal, 0);
/* 97 */     System.arraycopy(apRepBytes, 0, retVal, 2, apRepBytes.length);
/* 98 */     return retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\AcceptSecContextToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */