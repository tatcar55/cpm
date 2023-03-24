/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MicToken_v2
/*     */   extends MessageToken_v2
/*     */ {
/*     */   public MicToken_v2(Krb5Context context, byte[] tokenBytes, int tokenOffset, int tokenLen, MessageProp prop) throws GSSException {
/*  33 */     super(1028, context, tokenBytes, tokenOffset, tokenLen, prop);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MicToken_v2(Krb5Context context, InputStream is, MessageProp prop) throws GSSException {
/*  40 */     super(1028, context, is, prop);
/*     */   }
/*     */   
/*     */   public void verify(byte[] data, int offset, int len) throws GSSException {
/*  44 */     if (!verifySign(data, offset, len)) {
/*  45 */       throw new GSSException(6, -1, "Corrupt checksum or sequence number in MIC token");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void verify(InputStream data) throws GSSException {
/*  51 */     byte[] dataBytes = null;
/*     */     try {
/*  53 */       dataBytes = new byte[data.available()];
/*  54 */       data.read(dataBytes);
/*  55 */     } catch (IOException e) {
/*     */       
/*  57 */       throw new GSSException(6, -1, "Corrupt checksum or sequence number in MIC token");
/*     */     } 
/*     */     
/*  60 */     verify(dataBytes, 0, dataBytes.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MicToken_v2(Krb5Context context, MessageProp prop, byte[] data, int pos, int len) throws GSSException {
/*  66 */     super(1028, context);
/*     */ 
/*     */ 
/*     */     
/*  70 */     if (prop == null) prop = new MessageProp(0, false); 
/*  71 */     genSignAndSeqNumber(prop, data, pos, len);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MicToken_v2(Krb5Context context, MessageProp prop, InputStream data) throws GSSException, IOException {
/*  77 */     super(1028, context);
/*  78 */     byte[] dataBytes = new byte[data.available()];
/*  79 */     data.read(dataBytes);
/*     */ 
/*     */ 
/*     */     
/*  83 */     if (prop == null) prop = new MessageProp(0, false); 
/*  84 */     genSignAndSeqNumber(prop, dataBytes, 0, dataBytes.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int encode(byte[] outToken, int offset) throws IOException, GSSException {
/*  91 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  92 */     encode(bos);
/*  93 */     byte[] token = bos.toByteArray();
/*  94 */     System.arraycopy(token, 0, outToken, offset, token.length);
/*  95 */     return token.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() throws IOException, GSSException {
/* 101 */     ByteArrayOutputStream bos = new ByteArrayOutputStream(50);
/* 102 */     encode(bos);
/* 103 */     return bos.toByteArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\MicToken_v2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */