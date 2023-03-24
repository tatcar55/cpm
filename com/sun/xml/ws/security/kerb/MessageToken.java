/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ import sun.security.jgss.GSSHeader;
/*     */ import sun.security.jgss.GSSToken;
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
/*     */ abstract class MessageToken
/*     */   extends Krb5Token
/*     */ {
/*     */   private static final int TOKEN_NO_CKSUM_SIZE = 16;
/*     */   private static final int FILLER = 65535;
/*     */   static final int SGN_ALG_DES_MAC_MD5 = 0;
/*     */   static final int SGN_ALG_DES_MAC = 512;
/*     */   static final int SGN_ALG_HMAC_SHA1_DES3_KD = 1024;
/*     */   static final int SEAL_ALG_NONE = 65535;
/*     */   static final int SEAL_ALG_DES = 0;
/*     */   static final int SEAL_ALG_DES3_KD = 512;
/*     */   static final int SEAL_ALG_ARCFOUR_HMAC = 4096;
/*     */   static final int SGN_ALG_HMAC_MD5_ARCFOUR = 4352;
/*     */   private static final int TOKEN_ID_POS = 0;
/*     */   private static final int SIGN_ALG_POS = 2;
/*     */   private static final int SEAL_ALG_POS = 4;
/*     */   private int seqNumber;
/*     */   private boolean confState = true;
/*     */   private boolean initiator = true;
/* 116 */   private int tokenId = 0;
/* 117 */   private GSSHeader gssHeader = null;
/* 118 */   private MessageTokenHeader tokenHeader = null;
/* 119 */   private byte[] checksum = null;
/* 120 */   private byte[] encSeqNumber = null;
/* 121 */   private byte[] seqNumberData = null;
/*     */ 
/*     */   
/* 124 */   CipherHelper cipherHelper = null;
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
/*     */   MessageToken(int tokenId, Krb5Context context, byte[] tokenBytes, int tokenOffset, int tokenLen, MessageProp prop) throws GSSException {
/* 144 */     this(tokenId, context, new ByteArrayInputStream(tokenBytes, tokenOffset, tokenLen), prop);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MessageToken(int tokenId, Krb5Context context, InputStream is, MessageProp prop) throws GSSException {
/* 165 */     init(tokenId, context);
/*     */     
/*     */     try {
/* 168 */       this.gssHeader = new GSSHeader(is);
/*     */       
/* 170 */       if (!this.gssHeader.getOid().equals(OID)) {
/* 171 */         throw new GSSException(10, -1, getTokenName(tokenId));
/*     */       }
/*     */       
/* 174 */       if (!this.confState) {
/* 175 */         prop.setPrivacy(false);
/*     */       }
/*     */       
/* 178 */       this.tokenHeader = new MessageTokenHeader(is, prop);
/*     */       
/* 180 */       this.encSeqNumber = new byte[8];
/* 181 */       readFully(is, this.encSeqNumber);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 186 */       this.checksum = new byte[this.cipherHelper.getChecksumLength()];
/* 187 */       readFully(is, this.checksum);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 193 */     catch (IOException e) {
/* 194 */       throw new GSSException(10, -1, getTokenName(tokenId) + ":" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final GSSHeader getGSSHeader() {
/* 204 */     return this.gssHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getTokenId() {
/* 212 */     return this.tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getEncSeqNumber() {
/* 220 */     return this.encSeqNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getChecksum() {
/* 228 */     return this.checksum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getConfState() {
/* 237 */     return this.confState;
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
/*     */   public void genSignAndSeqNumber(MessageProp prop, byte[] optionalHeader, byte[] data, int offset, int len, byte[] optionalTrailer) throws GSSException {
/* 282 */     int qop = prop.getQOP();
/* 283 */     if (qop != 0) {
/* 284 */       qop = 0;
/* 285 */       prop.setQOP(qop);
/*     */     } 
/*     */     
/* 288 */     if (!this.confState) {
/* 289 */       prop.setPrivacy(false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 294 */     this.tokenHeader = new MessageTokenHeader(this.tokenId, prop.getPrivacy(), qop);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     this.checksum = getChecksum(optionalHeader, data, offset, len, optionalTrailer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     this.seqNumberData = new byte[8];
/*     */ 
/*     */ 
/*     */     
/* 311 */     if (this.cipherHelper.isArcFour()) {
/* 312 */       writeBigEndian(this.seqNumber, this.seqNumberData);
/*     */     } else {
/*     */       
/* 315 */       writeLittleEndian(this.seqNumber, this.seqNumberData);
/*     */     } 
/* 317 */     if (!this.initiator) {
/* 318 */       this.seqNumberData[4] = -1;
/* 319 */       this.seqNumberData[5] = -1;
/* 320 */       this.seqNumberData[6] = -1;
/* 321 */       this.seqNumberData[7] = -1;
/*     */     } 
/*     */     
/* 324 */     this.encSeqNumber = this.cipherHelper.encryptSeq(this.checksum, this.seqNumberData, 0, 8);
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
/*     */   public final boolean verifySignAndSeqNumber(byte[] optionalHeader, byte[] data, int offset, int len, byte[] optionalTrailer) throws GSSException {
/* 358 */     byte[] myChecksum = getChecksum(optionalHeader, data, offset, len, optionalTrailer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 364 */     if (MessageDigest.isEqual(this.checksum, myChecksum)) {
/*     */       
/* 366 */       this.seqNumberData = this.cipherHelper.decryptSeq(this.checksum, this.encSeqNumber, 0, 8);
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
/* 378 */       byte directionByte = 0;
/* 379 */       if (this.initiator) {
/* 380 */         directionByte = -1;
/*     */       }
/* 382 */       if (this.seqNumberData[4] == directionByte && this.seqNumberData[5] == directionByte && this.seqNumberData[6] == directionByte && this.seqNumberData[7] == directionByte)
/*     */       {
/*     */ 
/*     */         
/* 386 */         return true;
/*     */       }
/*     */     } 
/* 389 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getSequenceNumber() {
/* 394 */     int sequenceNum = 0;
/* 395 */     if (this.cipherHelper.isArcFour()) {
/* 396 */       sequenceNum = readBigEndian(this.seqNumberData, 0, 4);
/*     */     } else {
/* 398 */       sequenceNum = readLittleEndian(this.seqNumberData, 0, 4);
/*     */     } 
/* 400 */     return sequenceNum;
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
/*     */   private byte[] getChecksum(byte[] optionalHeader, byte[] data, int offset, int len, byte[] optionalTrailer) throws GSSException {
/* 437 */     byte[] tokenHeaderBytes = this.tokenHeader.getBytes();
/* 438 */     byte[] existingHeader = optionalHeader;
/* 439 */     byte[] checksumDataHeader = tokenHeaderBytes;
/*     */     
/* 441 */     if (existingHeader != null) {
/* 442 */       checksumDataHeader = new byte[tokenHeaderBytes.length + existingHeader.length];
/*     */       
/* 444 */       System.arraycopy(tokenHeaderBytes, 0, checksumDataHeader, 0, tokenHeaderBytes.length);
/*     */       
/* 446 */       System.arraycopy(existingHeader, 0, checksumDataHeader, tokenHeaderBytes.length, existingHeader.length);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 451 */     return this.cipherHelper.calculateChecksum(this.tokenHeader.getSignAlg(), checksumDataHeader, optionalTrailer, data, offset, len, this.tokenId);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MessageToken(int tokenId, Krb5Context context) throws GSSException {
/* 474 */     init(tokenId, context);
/* 475 */     this.seqNumber = context.incrementMySequenceNumber();
/*     */   }
/*     */   
/*     */   private void init(int tokenId, Krb5Context context) throws GSSException {
/* 479 */     this.tokenId = tokenId;
/*     */     
/* 481 */     this.confState = context.getConfState();
/*     */     
/* 483 */     this.initiator = context.isInitiator();
/*     */     
/* 485 */     this.cipherHelper = context.getCipherHelper(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream os) throws IOException, GSSException {
/* 496 */     this.gssHeader = new GSSHeader(OID, getKrb5TokenSize());
/* 497 */     this.gssHeader.encode(os);
/* 498 */     this.tokenHeader.encode(os);
/*     */     
/* 500 */     os.write(this.encSeqNumber);
/*     */     
/* 502 */     os.write(this.checksum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getKrb5TokenSize() throws GSSException {
/* 511 */     return getTokenSize();
/*     */   }
/*     */   
/*     */   protected final int getTokenSize() throws GSSException {
/* 515 */     return 16 + this.cipherHelper.getChecksumLength();
/*     */   }
/*     */ 
/*     */   
/*     */   protected static final int getTokenSize(CipherHelper ch) throws GSSException {
/* 520 */     return 16 + ch.getChecksumLength();
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
/*     */   protected abstract int getSealAlg(boolean paramBoolean, int paramInt) throws GSSException;
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
/*     */   class MessageTokenHeader
/*     */   {
/*     */     private int tokenId;
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
/*     */     private int signAlg;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int sealAlg;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 578 */     private byte[] bytes = new byte[8];
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
/*     */     public MessageTokenHeader(int tokenId, boolean conf, int qop) throws GSSException {
/* 593 */       this.tokenId = tokenId;
/*     */       
/* 595 */       this.signAlg = MessageToken.this.getSgnAlg(qop);
/*     */       
/* 597 */       this.sealAlg = MessageToken.this.getSealAlg(conf, qop);
/*     */       
/* 599 */       this.bytes[0] = (byte)(tokenId >>> 8);
/* 600 */       this.bytes[1] = (byte)tokenId;
/*     */       
/* 602 */       this.bytes[2] = (byte)(this.signAlg >>> 8);
/* 603 */       this.bytes[3] = (byte)this.signAlg;
/*     */       
/* 605 */       this.bytes[4] = (byte)(this.sealAlg >>> 8);
/* 606 */       this.bytes[5] = (byte)this.sealAlg;
/*     */       
/* 608 */       this.bytes[6] = -1;
/* 609 */       this.bytes[7] = -1;
/*     */     }
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
/*     */     public MessageTokenHeader(InputStream is, MessageProp prop) throws IOException {
/* 624 */       GSSToken.readFully(is, this.bytes);
/* 625 */       this.tokenId = GSSToken.readInt(this.bytes, 0);
/* 626 */       this.signAlg = GSSToken.readInt(this.bytes, 2);
/* 627 */       this.sealAlg = GSSToken.readInt(this.bytes, 4);
/*     */ 
/*     */ 
/*     */       
/* 631 */       int temp = GSSToken.readInt(this.bytes, 6);
/*     */ 
/*     */ 
/*     */       
/* 635 */       switch (this.sealAlg) {
/*     */         case 0:
/*     */         case 512:
/*     */         case 4096:
/* 639 */           prop.setPrivacy(true);
/*     */           break;
/*     */         
/*     */         default:
/* 643 */           prop.setPrivacy(false);
/*     */           break;
/*     */       } 
/* 646 */       prop.setQOP(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void encode(OutputStream os) throws IOException {
/* 655 */       os.write(this.bytes);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int getTokenId() {
/* 666 */       return this.tokenId;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int getSignAlg() {
/* 676 */       return this.signAlg;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int getSealAlg() {
/* 686 */       return this.sealAlg;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final byte[] getBytes() {
/* 694 */       return this.bytes;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getSgnAlg(int qop) throws GSSException {
/* 704 */     return this.cipherHelper.getSgnAlg();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\MessageToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */