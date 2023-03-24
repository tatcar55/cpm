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
/*     */ 
/*     */ class WrapToken
/*     */   extends MessageToken
/*     */ {
/*     */   static final int CONFOUNDER_SIZE = 8;
/*  48 */   static final byte[][] pads = new byte[][] { null, { 1 }, { 2, 2 }, { 3, 3, 3 }, { 4, 4, 4, 4 }, { 5, 5, 5, 5, 5 }, { 6, 6, 6, 6, 6, 6 }, { 7, 7, 7, 7, 7, 7, 7 }, { 8, 8, 8, 8, 8, 8, 8, 8 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean readTokenFromInputStream = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private InputStream is = null;
/*  69 */   private byte[] tokenBytes = null;
/*  70 */   private int tokenOffset = 0;
/*  71 */   private int tokenLen = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private byte[] dataBytes = null;
/*  81 */   private int dataOffset = 0;
/*  82 */   private int dataLen = 0;
/*     */ 
/*     */   
/*  85 */   private int dataSize = 0;
/*     */ 
/*     */   
/*  88 */   byte[] confounder = null;
/*  89 */   byte[] padding = null;
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public WrapToken(Krb5Context context, byte[] tokenBytes, int tokenOffset, int tokenLen, MessageProp prop) throws GSSException {
/* 110 */     super(513, context, tokenBytes, tokenOffset, tokenLen, prop);
/*     */ 
/*     */     
/* 113 */     this.readTokenFromInputStream = false;
/*     */ 
/*     */     
/* 116 */     this.tokenBytes = tokenBytes;
/* 117 */     this.tokenOffset = tokenOffset;
/* 118 */     this.tokenLen = tokenLen;
/* 119 */     this.privacy = prop.getPrivacy();
/* 120 */     this.dataSize = getGSSHeader().getMechTokenLength() - getKrb5TokenSize();
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
/*     */   public WrapToken(Krb5Context context, InputStream is, MessageProp prop) throws GSSException {
/* 140 */     super(513, context, is, prop);
/*     */ 
/*     */     
/* 143 */     this.is = is;
/* 144 */     this.privacy = prop.getPrivacy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     this.dataSize = getGSSHeader().getMechTokenLength() - getTokenSize();
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
/*     */   public byte[] getData() throws GSSException {
/* 167 */     byte[] temp = new byte[this.dataSize];
/* 168 */     getData(temp, 0);
/*     */ 
/*     */     
/* 171 */     byte[] retVal = new byte[this.dataSize - this.confounder.length - this.padding.length];
/*     */     
/* 173 */     System.arraycopy(temp, 0, retVal, 0, retVal.length);
/*     */     
/* 175 */     return retVal;
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
/* 192 */     if (this.readTokenFromInputStream) {
/* 193 */       getDataFromStream(dataBuf, dataBufOffset);
/*     */     } else {
/* 195 */       getDataFromBuffer(dataBuf, dataBufOffset);
/*     */     } 
/* 197 */     return this.dataSize - this.confounder.length - this.padding.length;
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
/* 213 */     GSSHeader gssHeader = getGSSHeader();
/* 214 */     int dataPos = this.tokenOffset + gssHeader.getLength() + getTokenSize();
/*     */ 
/*     */     
/* 217 */     if (dataPos + this.dataSize > this.tokenOffset + this.tokenLen) {
/* 218 */       throw new GSSException(10, -1, "Insufficient data in " + getTokenName(getTokenId()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     this.confounder = new byte[8];
/*     */ 
/*     */ 
/*     */     
/* 229 */     if (this.privacy) {
/* 230 */       this.cipherHelper.decryptData(this, this.tokenBytes, dataPos, this.dataSize, dataBuf, dataBufOffset);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       System.arraycopy(this.tokenBytes, dataPos, this.confounder, 0, 8);
/*     */       
/* 247 */       int padSize = this.tokenBytes[dataPos + this.dataSize - 1];
/* 248 */       if (padSize < 0)
/* 249 */         padSize = 0; 
/* 250 */       if (padSize > 8) {
/* 251 */         padSize %= 8;
/*     */       }
/* 253 */       this.padding = pads[padSize];
/*     */ 
/*     */       
/* 256 */       System.arraycopy(this.tokenBytes, dataPos + 8, dataBuf, dataBufOffset, this.dataSize - 8 - padSize);
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
/* 270 */     if (!verifySignAndSeqNumber(this.confounder, dataBuf, dataBufOffset, this.dataSize - 8 - this.padding.length, this.padding))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 275 */       throw new GSSException(6, -1, "Corrupt checksum or sequence number in Wrap token");
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
/*     */   private void getDataFromStream(byte[] dataBuf, int dataBufOffset) throws GSSException {
/* 292 */     GSSHeader gssHeader = getGSSHeader();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     this.confounder = new byte[8];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 305 */       if (this.privacy) {
/* 306 */         this.cipherHelper.decryptData(this, this.is, this.dataSize, dataBuf, dataBufOffset);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 320 */         readFully(this.is, this.confounder);
/*     */ 
/*     */ 
/*     */         
/* 324 */         int numBlocks = (this.dataSize - 8) / 8 - 1;
/* 325 */         int offset = dataBufOffset;
/* 326 */         for (int i = 0; i < numBlocks; i++) {
/* 327 */           readFully(this.is, dataBuf, offset, 8);
/* 328 */           offset += 8;
/*     */         } 
/*     */         
/* 331 */         byte[] finalBlock = new byte[8];
/* 332 */         readFully(this.is, finalBlock);
/*     */         
/* 334 */         int padSize = finalBlock[7];
/* 335 */         this.padding = pads[padSize];
/*     */ 
/*     */         
/* 338 */         System.arraycopy(finalBlock, 0, dataBuf, offset, finalBlock.length - padSize);
/*     */       }
/*     */     
/* 341 */     } catch (IOException e) {
/* 342 */       throw new GSSException(10, -1, getTokenName(getTokenId()) + ": " + e.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 351 */     if (!verifySignAndSeqNumber(this.confounder, dataBuf, dataBufOffset, this.dataSize - 8 - this.padding.length, this.padding))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 356 */       throw new GSSException(6, -1, "Corrupt checksum or sequence number in Wrap token");
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
/*     */   private byte[] getPadding(int len) {
/* 369 */     int padSize = 0;
/*     */ 
/*     */     
/* 372 */     if (this.cipherHelper.isArcFour()) {
/* 373 */       padSize = 1;
/*     */     } else {
/* 375 */       padSize = len % 8;
/* 376 */       padSize = 8 - padSize;
/*     */     } 
/* 378 */     return pads[padSize];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WrapToken(Krb5Context context, MessageProp prop, byte[] dataBytes, int dataOffset, int dataLen) throws GSSException {
/* 385 */     super(513, context);
/*     */     
/*     */     try {
/* 388 */       new Confounder(); this.confounder = Confounder.bytes(8);
/* 389 */     } catch (KrbException e) {
/* 390 */       throw new GSSException(11, -1, e.getMessage());
/*     */     } 
/* 392 */     this.padding = getPadding(dataLen);
/* 393 */     this.dataSize = this.confounder.length + dataLen + this.padding.length;
/* 394 */     this.dataBytes = dataBytes;
/* 395 */     this.dataOffset = dataOffset;
/* 396 */     this.dataLen = dataLen;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 406 */     genSignAndSeqNumber(prop, this.confounder, dataBytes, dataOffset, dataLen, this.padding);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 417 */     if (!context.getConfState()) {
/* 418 */       prop.setPrivacy(false);
/*     */     }
/* 420 */     this.privacy = prop.getPrivacy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void encode(OutputStream os) throws IOException, GSSException {
/* 425 */     super.encode(os);
/*     */ 
/*     */     
/* 428 */     if (!this.privacy) {
/*     */ 
/*     */       
/* 431 */       os.write(this.confounder);
/*     */ 
/*     */       
/* 434 */       os.write(this.dataBytes, this.dataOffset, this.dataLen);
/*     */ 
/*     */       
/* 437 */       os.write(this.padding);
/*     */     }
/*     */     else {
/*     */       
/* 441 */       this.cipherHelper.encryptData(this, this.confounder, this.dataBytes, this.dataOffset, this.dataLen, this.padding, os);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() throws IOException, GSSException {
/* 449 */     ByteArrayOutputStream bos = new ByteArrayOutputStream(this.dataSize + 50);
/* 450 */     encode(bos);
/* 451 */     return bos.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int encode(byte[] outToken, int offset) throws IOException, GSSException {
/* 458 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 459 */     super.encode(bos);
/* 460 */     byte[] header = bos.toByteArray();
/* 461 */     System.arraycopy(header, 0, outToken, offset, header.length);
/* 462 */     offset += header.length;
/*     */ 
/*     */     
/* 465 */     if (!this.privacy) {
/*     */ 
/*     */       
/* 468 */       System.arraycopy(this.confounder, 0, outToken, offset, this.confounder.length);
/*     */       
/* 470 */       offset += this.confounder.length;
/*     */ 
/*     */       
/* 473 */       System.arraycopy(this.dataBytes, this.dataOffset, outToken, offset, this.dataLen);
/*     */       
/* 475 */       offset += this.dataLen;
/*     */ 
/*     */       
/* 478 */       System.arraycopy(this.padding, 0, outToken, offset, this.padding.length);
/*     */     }
/*     */     else {
/*     */       
/* 482 */       this.cipherHelper.encryptData(this, this.confounder, this.dataBytes, this.dataOffset, this.dataLen, this.padding, outToken, offset);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 491 */     return header.length + this.confounder.length + this.dataLen + this.padding.length;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getKrb5TokenSize() throws GSSException {
/* 496 */     return getTokenSize() + this.dataSize;
/*     */   }
/*     */   
/*     */   protected int getSealAlg(boolean conf, int qop) throws GSSException {
/* 500 */     if (!conf) {
/* 501 */       return 65535;
/*     */     }
/*     */ 
/*     */     
/* 505 */     return this.cipherHelper.getSealAlg();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getSizeLimit(int qop, boolean confReq, int maxTokenSize, CipherHelper ch) throws GSSException {
/* 512 */     return GSSHeader.getMaxMechTokenSize(OID, maxTokenSize) - getTokenSize(ch) + 8 - 8;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\WrapToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */