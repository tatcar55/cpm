/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import sun.security.krb5.Checksum;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbApReq;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ class InitSecContextToken
/*     */   extends InitialToken
/*     */ {
/*  18 */   private KrbApReq apReq = null;
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
/*     */   InitSecContextToken(Krb5Context context, Credentials tgt, Credentials serviceTicket) throws KrbException, IOException, GSSException {
/*  34 */     boolean mutualRequired = context.getMutualAuthState();
/*  35 */     boolean useSubkey = true;
/*  36 */     boolean useSequenceNumber = true;
/*     */     
/*  38 */     InitialToken.OverloadedChecksum gssChecksum = new InitialToken.OverloadedChecksum(this, context, tgt, serviceTicket);
/*     */ 
/*     */     
/*  41 */     Checksum checksum = gssChecksum.getChecksum();
/*     */     
/*  43 */     this.apReq = new KrbApReq(serviceTicket, mutualRequired, useSubkey, useSequenceNumber, checksum);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  49 */     context.resetMySequenceNumber(this.apReq.getSeqNumber().intValue());
/*     */     
/*  51 */     EncryptionKey subKey = this.apReq.getSubKey();
/*  52 */     if (subKey != null) {
/*  53 */       context.setKey(subKey);
/*     */     } else {
/*  55 */       context.setKey(serviceTicket.getSessionKey());
/*     */     } 
/*  57 */     if (!mutualRequired) {
/*  58 */       context.resetPeerSequenceNumber(0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InitSecContextToken(Krb5Context context, EncryptionKey[] keys, InputStream is) throws IOException, GSSException, KrbException {
/*  69 */     int tokenId = is.read() << 8 | is.read();
/*     */     
/*  71 */     if (tokenId != 256) {
/*  72 */       throw new GSSException(10, -1, "AP_REQ token id does not match!");
/*     */     }
/*     */ 
/*     */     
/*  76 */     byte[] apReqBytes = (new DerValue(is)).toByteArray();
/*     */ 
/*     */     
/*  79 */     this.apReq = new KrbApReq(apReqBytes, keys);
/*     */ 
/*     */     
/*  82 */     EncryptionKey sessionKey = this.apReq.getCreds().getSessionKey();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     EncryptionKey subKey = this.apReq.getSubKey();
/*  91 */     if (subKey != null) {
/*  92 */       context.setKey(subKey);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  98 */       context.setKey(sessionKey);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     InitialToken.OverloadedChecksum gssChecksum = new InitialToken.OverloadedChecksum(this, context, this.apReq.getChecksum(), sessionKey);
/*     */     
/* 104 */     gssChecksum.setContextFlags(context);
/* 105 */     Credentials delegCred = gssChecksum.getDelegatedCreds();
/* 106 */     if (delegCred != null) {
/* 107 */       Krb5CredElement credElement = Krb5InitCredential.getInstance((Krb5NameElement)context.getSrcName(), delegCred);
/*     */ 
/*     */ 
/*     */       
/* 111 */       context.setDelegCred(credElement);
/*     */     } 
/*     */     
/* 114 */     Integer apReqSeqNumber = this.apReq.getSeqNumber();
/* 115 */     int peerSeqNumber = (apReqSeqNumber != null) ? apReqSeqNumber.intValue() : 0;
/*     */ 
/*     */     
/* 118 */     context.resetPeerSequenceNumber(peerSeqNumber);
/* 119 */     if (!context.getMutualAuthState())
/*     */     {
/*     */       
/* 122 */       context.resetMySequenceNumber(peerSeqNumber); } 
/*     */   }
/*     */   
/*     */   public final KrbApReq getKrbApReq() {
/* 126 */     return this.apReq;
/*     */   }
/*     */   
/*     */   public final byte[] encode() throws IOException {
/* 130 */     byte[] apReqBytes = this.apReq.getMessage();
/* 131 */     byte[] retVal = new byte[2 + apReqBytes.length];
/* 132 */     writeInt(256, retVal, 0);
/* 133 */     System.arraycopy(apReqBytes, 0, retVal, 2, apReqBytes.length);
/*     */ 
/*     */     
/* 136 */     return retVal;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\InitSecContextToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */