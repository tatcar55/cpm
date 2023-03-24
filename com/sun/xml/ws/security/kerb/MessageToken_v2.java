/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.MessageProp;
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
/*     */ abstract class MessageToken_v2
/*     */   extends Krb5Token
/*     */ {
/*     */   private static final int TOKEN_ID_POS = 0;
/*     */   private static final int TOKEN_FLAG_POS = 2;
/*     */   private static final int TOKEN_EC_POS = 4;
/*     */   private static final int TOKEN_RRC_POS = 6;
/*     */   static final int TOKEN_HEADER_SIZE = 16;
/*  84 */   private int tokenId = 0;
/*     */   
/*     */   private int seqNumber;
/*     */   
/*  88 */   private int ec = 0;
/*  89 */   private int rrc = 0;
/*     */   
/*     */   private boolean confState = true;
/*     */   
/*     */   private boolean initiator = true;
/*  94 */   byte[] confounder = null;
/*  95 */   byte[] checksum = null;
/*     */   
/*  97 */   private int key_usage = 0;
/*  98 */   private byte[] seqNumberData = null;
/*     */   
/* 100 */   private MessageTokenHeader tokenHeader = null;
/*     */ 
/*     */   
/* 103 */   CipherHelper cipherHelper = null;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int KG_USAGE_ACCEPTOR_SEAL = 22;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int KG_USAGE_ACCEPTOR_SIGN = 23;
/*     */ 
/*     */   
/*     */   static final int KG_USAGE_INITIATOR_SEAL = 24;
/*     */ 
/*     */   
/*     */   static final int KG_USAGE_INITIATOR_SIGN = 25;
/*     */ 
/*     */   
/*     */   private static final int FLAG_SENDER_IS_ACCEPTOR = 1;
/*     */ 
/*     */   
/*     */   private static final int FLAG_WRAP_CONFIDENTIAL = 2;
/*     */ 
/*     */   
/*     */   private static final int FLAG_ACCEPTOR_SUBKEY = 4;
/*     */ 
/*     */   
/*     */   private static final int FILLER = 255;
/*     */ 
/*     */ 
/*     */   
/*     */   MessageToken_v2(int tokenId, Krb5Context context, byte[] tokenBytes, int tokenOffset, int tokenLen, MessageProp prop) throws GSSException {
/* 134 */     this(tokenId, context, new ByteArrayInputStream(tokenBytes, tokenOffset, tokenLen), prop);
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
/*     */   MessageToken_v2(int tokenId, Krb5Context context, InputStream is, MessageProp prop) throws GSSException {
/* 155 */     init(tokenId, context);
/*     */     
/*     */     try {
/* 158 */       if (!this.confState) {
/* 159 */         prop.setPrivacy(false);
/*     */       }
/* 161 */       this.tokenHeader = new MessageTokenHeader(is, prop, tokenId);
/*     */ 
/*     */       
/* 164 */       if (tokenId == 1284) {
/* 165 */         this.key_usage = !this.initiator ? 24 : 22;
/*     */       }
/* 167 */       else if (tokenId == 1028) {
/* 168 */         this.key_usage = !this.initiator ? 25 : 23;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 173 */       int tokenLen = is.available();
/* 174 */       byte[] data = new byte[tokenLen];
/* 175 */       readFully(is, data);
/* 176 */       this.checksum = new byte[this.cipherHelper.getChecksumLength()];
/* 177 */       System.arraycopy(data, tokenLen - this.cipherHelper.getChecksumLength(), this.checksum, 0, this.cipherHelper.getChecksumLength());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       if (!prop.getPrivacy() && tokenId == 1284)
/*     */       {
/* 184 */         if (this.checksum.length != this.ec) {
/* 185 */           throw new GSSException(10, -1, getTokenName(tokenId) + ":" + "EC incorrect!");
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 191 */     catch (IOException e) {
/* 192 */       throw new GSSException(10, -1, getTokenName(tokenId) + ":" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getTokenId() {
/* 202 */     return this.tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getKeyUsage() {
/* 210 */     return this.key_usage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getConfState() {
/* 219 */     return this.confState;
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
/*     */   public void genSignAndSeqNumber(MessageProp prop, byte[] data, int offset, int len) throws GSSException {
/* 239 */     int qop = prop.getQOP();
/* 240 */     if (qop != 0) {
/* 241 */       qop = 0;
/* 242 */       prop.setQOP(qop);
/*     */     } 
/*     */     
/* 245 */     if (!this.confState) {
/* 246 */       prop.setPrivacy(false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 251 */     this.tokenHeader = new MessageTokenHeader(this.tokenId, prop.getPrivacy(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     if (this.tokenId == 1284) {
/* 258 */       this.key_usage = this.initiator ? 24 : 22;
/*     */     }
/* 260 */     else if (this.tokenId == 1028) {
/* 261 */       this.key_usage = this.initiator ? 25 : 23;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 266 */     if (this.tokenId == 1028 || (!prop.getPrivacy() && this.tokenId == 1284))
/*     */     {
/* 268 */       this.checksum = getChecksum(data, offset, len);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     if (!prop.getPrivacy() && this.tokenId == 1284) {
/* 276 */       byte[] tok_header = this.tokenHeader.getBytes();
/* 277 */       tok_header[4] = (byte)(this.checksum.length >>> 8);
/* 278 */       tok_header[5] = (byte)this.checksum.length;
/*     */     } 
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
/*     */   public final boolean verifySign(byte[] data, int offset, int len) throws GSSException {
/* 298 */     byte[] myChecksum = getChecksum(data, offset, len);
/*     */ 
/*     */     
/* 301 */     if (MessageDigest.isEqual(this.checksum, myChecksum))
/*     */     {
/* 303 */       return true;
/*     */     }
/* 305 */     return false;
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
/*     */   public boolean rotate_left(byte[] in_bytes, int tokenOffset, byte[] out_bytes, int bufsize) {
/* 318 */     int offset = 0;
/*     */ 
/*     */     
/* 321 */     if (this.rrc > 0) {
/* 322 */       if (bufsize == 0) {
/* 323 */         return false;
/*     */       }
/* 325 */       this.rrc %= bufsize - 16;
/* 326 */       if (this.rrc == 0) {
/* 327 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 331 */       if (tokenOffset > 0) {
/* 332 */         offset += tokenOffset;
/*     */       }
/*     */ 
/*     */       
/* 336 */       System.arraycopy(in_bytes, offset, out_bytes, 0, 16);
/* 337 */       offset += 16;
/*     */ 
/*     */       
/* 340 */       System.arraycopy(in_bytes, offset + this.rrc, out_bytes, 16, bufsize - 16 - this.rrc);
/*     */ 
/*     */ 
/*     */       
/* 344 */       System.arraycopy(in_bytes, offset, out_bytes, bufsize - 16 - this.rrc, this.rrc);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 349 */       return true;
/*     */     } 
/* 351 */     return false;
/*     */   }
/*     */   
/*     */   public final int getSequenceNumber() {
/* 355 */     return readBigEndian(this.seqNumberData, 0, 4);
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
/*     */   byte[] getChecksum(byte[] data, int offset, int len) throws GSSException {
/* 383 */     byte[] tokenHeaderBytes = this.tokenHeader.getBytes();
/*     */ 
/*     */     
/* 386 */     int conf_flag = tokenHeaderBytes[2] & 0x2;
/*     */ 
/*     */ 
/*     */     
/* 390 */     if (conf_flag == 0 && this.tokenId == 1284) {
/* 391 */       tokenHeaderBytes[4] = 0;
/* 392 */       tokenHeaderBytes[5] = 0;
/*     */     } 
/* 394 */     return this.cipherHelper.calculateChecksum(tokenHeaderBytes, data, offset, len, this.key_usage);
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
/*     */   MessageToken_v2(int tokenId, Krb5Context context) throws GSSException {
/* 417 */     init(tokenId, context);
/* 418 */     this.seqNumber = context.incrementMySequenceNumber();
/*     */   }
/*     */   
/*     */   private void init(int tokenId, Krb5Context context) throws GSSException {
/* 422 */     this.tokenId = tokenId;
/*     */     
/* 424 */     this.confState = context.getConfState();
/*     */     
/* 426 */     this.initiator = context.isInitiator();
/*     */     
/* 428 */     this.cipherHelper = context.getCipherHelper(null);
/*     */ 
/*     */ 
/*     */     
/* 432 */     this.tokenId = tokenId;
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
/*     */   public void encode(OutputStream os) throws IOException, GSSException {
/* 444 */     this.tokenHeader.encode(os);
/*     */     
/* 446 */     if (this.tokenId == 1028) {
/* 447 */       os.write(this.checksum);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getKrb5TokenSize() throws GSSException {
/* 457 */     return getTokenSize();
/*     */   }
/*     */   
/*     */   protected final int getTokenSize() throws GSSException {
/* 461 */     return 16 + this.cipherHelper.getChecksumLength();
/*     */   }
/*     */ 
/*     */   
/*     */   protected static final int getTokenSize(CipherHelper ch) throws GSSException {
/* 466 */     return 16 + ch.getChecksumLength();
/*     */   }
/*     */   
/*     */   protected final byte[] getTokenHeader() {
/* 470 */     return this.tokenHeader.getBytes();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 515 */     private byte[] bytes = new byte[16];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MessageTokenHeader(int tokenId, boolean conf, boolean have_acceptor_subkey) throws GSSException {
/* 521 */       this.tokenId = tokenId;
/*     */       
/* 523 */       this.bytes[0] = (byte)(tokenId >>> 8);
/* 524 */       this.bytes[1] = (byte)tokenId;
/*     */ 
/*     */       
/* 527 */       int flags = 0;
/* 528 */       flags = (MessageToken_v2.this.initiator ? 0 : 1) | ((conf && tokenId != 1028) ? 2 : 0) | (have_acceptor_subkey ? 4 : 0);
/*     */ 
/*     */ 
/*     */       
/* 532 */       this.bytes[2] = (byte)flags;
/*     */ 
/*     */       
/* 535 */       this.bytes[3] = -1;
/*     */ 
/*     */       
/* 538 */       if (tokenId == 1284) {
/*     */         
/* 540 */         this.bytes[4] = 0;
/* 541 */         this.bytes[5] = 0;
/*     */         
/* 543 */         this.bytes[6] = 0;
/* 544 */         this.bytes[7] = 0;
/* 545 */       } else if (tokenId == 1028) {
/*     */         
/* 547 */         for (int i = 4; i < 8; i++) {
/* 548 */           this.bytes[i] = -1;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 553 */       MessageToken_v2.this.seqNumberData = new byte[8];
/* 554 */       GSSToken.writeBigEndian(MessageToken_v2.this.seqNumber, MessageToken_v2.this.seqNumberData, 4);
/* 555 */       System.arraycopy(MessageToken_v2.this.seqNumberData, 0, this.bytes, 8, 8);
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
/*     */     
/*     */     public MessageTokenHeader(InputStream is, MessageProp prop, int tokId) throws IOException, GSSException {
/* 571 */       GSSToken.readFully(is, this.bytes, 0, 16);
/* 572 */       this.tokenId = GSSToken.readInt(this.bytes, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 578 */       int acceptor_flag = MessageToken_v2.this.initiator ? 1 : 0;
/* 579 */       int flag = this.bytes[2] & 0x1;
/* 580 */       if (flag != acceptor_flag) {
/* 581 */         throw new GSSException(10, -1, Krb5Token.getTokenName(this.tokenId) + ":" + "Acceptor Flag Missing!");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 586 */       int conf_flag = this.bytes[2] & 0x2;
/* 587 */       if (conf_flag == 2 && this.tokenId == 1284) {
/*     */         
/* 589 */         prop.setPrivacy(true);
/*     */       } else {
/* 591 */         prop.setPrivacy(false);
/*     */       } 
/*     */ 
/*     */       
/* 595 */       if (this.tokenId != tokId) {
/* 596 */         throw new GSSException(10, -1, Krb5Token.getTokenName(this.tokenId) + ":" + "Defective Token ID!");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 601 */       if ((this.bytes[3] & 0xFF) != 255) {
/* 602 */         throw new GSSException(10, -1, Krb5Token.getTokenName(this.tokenId) + ":" + "Defective Token Filler!");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 607 */       if (this.tokenId == 1028) {
/* 608 */         for (int i = 4; i < 8; i++) {
/* 609 */           if ((this.bytes[i] & 0xFF) != 255) {
/* 610 */             throw new GSSException(10, -1, Krb5Token.getTokenName(this.tokenId) + ":" + "Defective Token Filler!");
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 618 */       MessageToken_v2.this.ec = GSSToken.readBigEndian(this.bytes, 4, 2);
/*     */ 
/*     */       
/* 621 */       MessageToken_v2.this.rrc = GSSToken.readBigEndian(this.bytes, 6, 2);
/*     */ 
/*     */       
/* 624 */       prop.setQOP(0);
/*     */ 
/*     */       
/* 627 */       MessageToken_v2.this.seqNumberData = new byte[8];
/* 628 */       System.arraycopy(this.bytes, 8, MessageToken_v2.this.seqNumberData, 0, 8);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void encode(OutputStream os) throws IOException {
/* 637 */       os.write(this.bytes);
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
/* 648 */       return this.tokenId;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final byte[] getBytes() {
/* 656 */       return this.bytes;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\MessageToken_v2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */