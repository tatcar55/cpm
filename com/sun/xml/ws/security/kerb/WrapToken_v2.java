/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ import sun.security.jgss.GSSHeader;
/*     */ import sun.security.krb5.Confounder;
/*     */ import sun.security.krb5.KrbException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WrapToken_v2
/*     */   extends MessageToken_v2
/*     */ {
/*     */   static final int CONFOUNDER_SIZE = 16;
/*     */   private boolean readTokenFromInputStream = true;
/*  48 */   private InputStream is = null;
/*  49 */   private byte[] tokenBytes = null;
/*  50 */   private int tokenOffset = 0;
/*  51 */   private int tokenLen = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private byte[] dataBytes = null;
/*  61 */   private int dataOffset = 0;
/*  62 */   private int dataLen = 0;
/*     */ 
/*     */ 
/*     */   
/*  66 */   private int dataSize = 0;
/*     */ 
/*     */   
/*  69 */   byte[] confounder = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean privacy = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean initiator = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WrapToken_v2(Krb5Context context, byte[] tokenBytes, int tokenOffset, int tokenLen, MessageProp prop) throws GSSException {
/*  91 */     super(1284, context, tokenBytes, tokenOffset, tokenLen, prop);
/*     */     
/*  93 */     this.readTokenFromInputStream = false;
/*     */ 
/*     */     
/*  96 */     byte[] new_tokenBytes = new byte[tokenLen];
/*  97 */     if (rotate_left(tokenBytes, tokenOffset, new_tokenBytes, tokenLen)) {
/*  98 */       this.tokenBytes = new_tokenBytes;
/*  99 */       this.tokenOffset = 0;
/*     */     } else {
/* 101 */       this.tokenBytes = tokenBytes;
/* 102 */       this.tokenOffset = tokenOffset;
/*     */     } 
/*     */ 
/*     */     
/* 106 */     this.tokenLen = tokenLen;
/* 107 */     this.privacy = prop.getPrivacy();
/*     */     
/* 109 */     this.dataSize = tokenLen - 16;
/*     */ 
/*     */     
/* 112 */     this.initiator = context.isInitiator();
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
/*     */   public WrapToken_v2(Krb5Context context, InputStream is, MessageProp prop) throws GSSException {
/* 132 */     super(1284, context, is, prop);
/*     */ 
/*     */     
/* 135 */     this.is = is;
/* 136 */     this.privacy = prop.getPrivacy();
/*     */ 
/*     */     
/*     */     try {
/* 140 */       this.tokenLen = is.available();
/* 141 */     } catch (IOException e) {
/* 142 */       throw new GSSException(10, -1, getTokenName(getTokenId()) + ": " + e.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     this.dataSize = this.tokenLen - 16;
/*     */ 
/*     */     
/* 151 */     this.initiator = context.isInitiator();
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
/*     */   public byte[] getData() throws GSSException {
/* 163 */     byte[] temp = new byte[this.dataSize];
/* 164 */     int len = getData(temp, 0);
/*     */ 
/*     */     
/* 167 */     byte[] retVal = new byte[len];
/* 168 */     System.arraycopy(temp, 0, retVal, 0, retVal.length);
/* 169 */     return retVal;
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
/*     */   public int getData(byte[] dataBuf, int dataBufOffset) throws GSSException {
/* 186 */     if (this.readTokenFromInputStream) {
/* 187 */       getDataFromStream(dataBuf, dataBufOffset);
/*     */     } else {
/* 189 */       getDataFromBuffer(dataBuf, dataBufOffset);
/*     */     } 
/* 191 */     int retVal = 0;
/* 192 */     if (this.privacy) {
/* 193 */       retVal = this.dataSize - this.confounder.length - 16 - this.cipherHelper.getChecksumLength();
/*     */     } else {
/*     */       
/* 196 */       retVal = this.dataSize - this.cipherHelper.getChecksumLength();
/*     */     } 
/* 198 */     return retVal;
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
/*     */   private void getDataFromBuffer(byte[] dataBuf, int dataBufOffset) throws GSSException {
/* 214 */     int dataPos = this.tokenOffset + 16;
/* 215 */     int data_length = 0;
/*     */     
/* 217 */     if (dataPos + this.dataSize > this.tokenOffset + this.tokenLen) {
/* 218 */       throw new GSSException(10, -1, "Insufficient data in " + getTokenName(getTokenId()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 223 */     this.confounder = new byte[16];
/*     */ 
/*     */     
/* 226 */     if (this.privacy) {
/*     */ 
/*     */       
/* 229 */       this.cipherHelper.decryptData(this, this.tokenBytes, dataPos, this.dataSize, dataBuf, dataBufOffset, getKeyUsage());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 239 */       data_length = this.dataSize - 16 - 16 - this.cipherHelper.getChecksumLength();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 244 */       debug("\t\tNo encryption was performed by peer.\n");
/*     */ 
/*     */       
/* 247 */       data_length = this.dataSize - this.cipherHelper.getChecksumLength();
/* 248 */       System.arraycopy(this.tokenBytes, dataPos, dataBuf, dataBufOffset, data_length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 256 */       if (!verifySign(dataBuf, dataBufOffset, data_length)) {
/* 257 */         throw new GSSException(6, -1, "Corrupt checksum in Wrap token");
/*     */       }
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
/*     */   private void getDataFromStream(byte[] dataBuf, int dataBufOffset) throws GSSException {
/* 276 */     int data_length = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     this.confounder = new byte[16];
/*     */ 
/*     */     
/*     */     try {
/* 285 */       if (this.privacy)
/*     */       {
/* 287 */         this.cipherHelper.decryptData(this, this.is, this.dataSize, dataBuf, dataBufOffset, getKeyUsage());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 297 */         data_length = this.dataSize - 16 - 16 - this.cipherHelper.getChecksumLength();
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 302 */         debug("\t\tNo encryption was performed by peer.\n");
/* 303 */         readFully(this.is, this.confounder);
/*     */ 
/*     */         
/* 306 */         data_length = this.dataSize - this.cipherHelper.getChecksumLength();
/* 307 */         readFully(this.is, dataBuf, dataBufOffset, data_length);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 312 */         if (!verifySign(dataBuf, dataBufOffset, data_length)) {
/* 313 */           throw new GSSException(6, -1, "Corrupt checksum in Wrap token");
/*     */         }
/*     */       }
/*     */     
/* 317 */     } catch (IOException e) {
/* 318 */       throw new GSSException(10, -1, getTokenName(getTokenId()) + ": " + e.getMessage());
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
/*     */   public WrapToken_v2(Krb5Context context, MessageProp prop, byte[] dataBytes, int dataOffset, int dataLen) throws GSSException {
/* 330 */     super(1284, context);
/*     */     
/*     */     try {
/* 333 */       new Confounder(); this.confounder = Confounder.bytes(16);
/* 334 */     } catch (KrbException e) {
/* 335 */       throw new GSSException(11, -1, e.getMessage());
/*     */     } 
/*     */     
/* 338 */     this.dataSize = this.confounder.length + dataLen + 16 + this.cipherHelper.getChecksumLength();
/*     */     
/* 340 */     this.dataBytes = dataBytes;
/* 341 */     this.dataOffset = dataOffset;
/* 342 */     this.dataLen = dataLen;
/*     */ 
/*     */     
/* 345 */     this.initiator = context.isInitiator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 351 */     genSignAndSeqNumber(prop, dataBytes, dataOffset, dataLen);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 360 */     if (!context.getConfState()) {
/* 361 */       prop.setPrivacy(false);
/*     */     }
/* 363 */     this.privacy = prop.getPrivacy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void encode(OutputStream os) throws IOException, GSSException {
/* 368 */     super.encode(os);
/*     */ 
/*     */     
/* 371 */     if (!this.privacy) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 378 */       byte[] checksum = getChecksum(this.dataBytes, this.dataOffset, this.dataLen);
/*     */ 
/*     */ 
/*     */       
/* 382 */       os.write(this.dataBytes, this.dataOffset, this.dataLen);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 387 */       os.write(checksum);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 396 */       this.cipherHelper.encryptData(this, this.confounder, getTokenHeader(), this.dataBytes, this.dataOffset, this.dataLen, getKeyUsage(), os);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() throws IOException, GSSException {
/* 405 */     ByteArrayOutputStream bos = new ByteArrayOutputStream(this.dataSize + 50);
/* 406 */     encode(bos);
/* 407 */     return bos.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int encode(byte[] outToken, int offset) throws IOException, GSSException {
/* 413 */     int retVal = 0;
/*     */ 
/*     */     
/* 416 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 417 */     super.encode(bos);
/* 418 */     byte[] header = bos.toByteArray();
/* 419 */     System.arraycopy(header, 0, outToken, offset, header.length);
/* 420 */     offset += header.length;
/*     */ 
/*     */     
/* 423 */     if (!this.privacy) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 430 */       byte[] checksum = getChecksum(this.dataBytes, this.dataOffset, this.dataLen);
/*     */ 
/*     */ 
/*     */       
/* 434 */       System.arraycopy(this.dataBytes, this.dataOffset, outToken, offset, this.dataLen);
/*     */       
/* 436 */       offset += this.dataLen;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 441 */       System.arraycopy(checksum, 0, outToken, offset, this.cipherHelper.getChecksumLength());
/*     */ 
/*     */       
/* 444 */       retVal = header.length + this.dataLen + this.cipherHelper.getChecksumLength();
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 451 */       int cLen = this.cipherHelper.encryptData(this, this.confounder, getTokenHeader(), this.dataBytes, this.dataOffset, this.dataLen, outToken, offset, getKeyUsage());
/*     */ 
/*     */ 
/*     */       
/* 455 */       retVal = header.length + cLen;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 462 */     return retVal;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getKrb5TokenSize() throws GSSException {
/* 467 */     return getTokenSize() + this.dataSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getSizeLimit(int qop, boolean confReq, int maxTokenSize, CipherHelper ch) throws GSSException {
/* 474 */     return GSSHeader.getMaxMechTokenSize(OID, maxTokenSize) - getTokenSize(ch) + 16 - 8;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\WrapToken_v2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */