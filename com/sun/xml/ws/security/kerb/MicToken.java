/*    */ package com.sun.xml.ws.security.kerb;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.ietf.jgss.GSSException;
/*    */ import org.ietf.jgss.MessageProp;
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
/*    */ class MicToken
/*    */   extends MessageToken
/*    */ {
/*    */   public MicToken(Krb5Context context, byte[] tokenBytes, int tokenOffset, int tokenLen, MessageProp prop) throws GSSException {
/* 22 */     super(257, context, tokenBytes, tokenOffset, tokenLen, prop);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MicToken(Krb5Context context, InputStream is, MessageProp prop) throws GSSException {
/* 29 */     super(257, context, is, prop);
/*    */   }
/*    */   
/*    */   public void verify(byte[] data, int offset, int len) throws GSSException {
/* 33 */     if (!verifySignAndSeqNumber(null, data, offset, len, null)) {
/* 34 */       throw new GSSException(6, -1, "Corrupt checksum or sequence number in MIC token");
/*    */     }
/*    */   }
/*    */   
/*    */   public void verify(InputStream data) throws GSSException {
/* 39 */     byte[] dataBytes = null;
/*    */     try {
/* 41 */       dataBytes = new byte[data.available()];
/* 42 */       data.read(dataBytes);
/* 43 */     } catch (IOException e) {
/*    */       
/* 45 */       throw new GSSException(6, -1, "Corrupt checksum or sequence number in MIC token");
/*    */     } 
/*    */     
/* 48 */     verify(dataBytes, 0, dataBytes.length);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MicToken(Krb5Context context, MessageProp prop, byte[] data, int pos, int len) throws GSSException {
/* 54 */     super(257, context);
/*    */ 
/*    */ 
/*    */     
/* 58 */     if (prop == null) prop = new MessageProp(0, false); 
/* 59 */     genSignAndSeqNumber(prop, null, data, pos, len, null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MicToken(Krb5Context context, MessageProp prop, InputStream data) throws GSSException, IOException {
/* 65 */     super(257, context);
/* 66 */     byte[] dataBytes = new byte[data.available()];
/* 67 */     data.read(dataBytes);
/*    */ 
/*    */ 
/*    */     
/* 71 */     if (prop == null) prop = new MessageProp(0, false); 
/* 72 */     genSignAndSeqNumber(prop, null, dataBytes, 0, dataBytes.length, null);
/*    */   }
/*    */   
/*    */   protected int getSealAlg(boolean confRequested, int qop) {
/* 76 */     return 65535;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int encode(byte[] outToken, int offset) throws IOException, GSSException {
/* 82 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 83 */     encode(bos);
/* 84 */     byte[] token = bos.toByteArray();
/* 85 */     System.arraycopy(token, 0, outToken, offset, token.length);
/* 86 */     return token.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] encode() throws IOException, GSSException {
/* 91 */     ByteArrayOutputStream bos = new ByteArrayOutputStream(50);
/* 92 */     encode(bos);
/* 93 */     return bos.toByteArray();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\MicToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */