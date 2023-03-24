/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import javax.security.auth.kerberos.DelegationPermission;
/*     */ import org.ietf.jgss.ChannelBinding;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import sun.security.jgss.GSSToken;
/*     */ import sun.security.krb5.Checksum;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbCred;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class InitialToken
/*     */   extends Krb5Token
/*     */ {
/*     */   private static final int CHECKSUM_TYPE = 32771;
/*     */   private static final int CHECKSUM_LENGTH_SIZE = 4;
/*     */   private static final int CHECKSUM_BINDINGS_SIZE = 16;
/*     */   private static final int CHECKSUM_FLAGS_SIZE = 4;
/*     */   private static final int CHECKSUM_DELEG_OPT_SIZE = 2;
/*     */   private static final int CHECKSUM_DELEG_LGTH_SIZE = 2;
/*     */   private static final int CHECKSUM_DELEG_FLAG = 1;
/*     */   private static final int CHECKSUM_MUTUAL_FLAG = 2;
/*     */   private static final int CHECKSUM_REPLAY_FLAG = 4;
/*     */   private static final int CHECKSUM_SEQUENCE_FLAG = 8;
/*     */   private static final int CHECKSUM_CONF_FLAG = 16;
/*     */   private static final int CHECKSUM_INTEG_FLAG = 32;
/*  37 */   private final byte[] CHECKSUM_FIRST_BYTES = new byte[] { 16, 0, 0, 0 };
/*     */   
/*     */   private static final int CHANNEL_BINDING_AF_INET = 2;
/*     */   
/*     */   private static final int CHANNEL_BINDING_AF_INET6 = 24;
/*     */   
/*     */   private static final int CHANNEL_BINDING_AF_NULL_ADDR = 255;
/*     */   private static final int Inet4_ADDRSZ = 4;
/*     */   private static final int Inet6_ADDRSZ = 16;
/*     */   
/*     */   protected class OverloadedChecksum
/*     */   {
/*  49 */     private byte[] checksumBytes = null;
/*  50 */     private Credentials delegCreds = null;
/*  51 */     private int flags = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OverloadedChecksum(Krb5Context context, Credentials tgt, Credentials serviceTicket) throws KrbException, IOException, GSSException {
/*  62 */       byte[] krbCredMessage = null;
/*  63 */       int pos = 0;
/*  64 */       int size = 24;
/*     */ 
/*     */       
/*  67 */       if (context.getCredDelegState())
/*     */       {
/*  69 */         if (!tgt.isForwardable()) {
/*     */           
/*  71 */           context.setCredDelegState(false);
/*     */         } else {
/*  73 */           KrbCred krbCred = null;
/*  74 */           CipherHelper cipherHelper = context.getCipherHelper(serviceTicket.getSessionKey());
/*     */           
/*  76 */           if (useNullKey(cipherHelper)) {
/*  77 */             krbCred = new KrbCred(tgt, serviceTicket, EncryptionKey.NULL_KEY);
/*     */           } else {
/*     */             
/*  80 */             krbCred = new KrbCred(tgt, serviceTicket, serviceTicket.getSessionKey());
/*     */           } 
/*     */           
/*  83 */           krbCredMessage = krbCred.getMessage();
/*  84 */           size += 4 + krbCredMessage.length;
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  90 */       this.checksumBytes = new byte[size];
/*     */       
/*  92 */       this.checksumBytes[pos++] = InitialToken.this.CHECKSUM_FIRST_BYTES[0];
/*  93 */       this.checksumBytes[pos++] = InitialToken.this.CHECKSUM_FIRST_BYTES[1];
/*  94 */       this.checksumBytes[pos++] = InitialToken.this.CHECKSUM_FIRST_BYTES[2];
/*  95 */       this.checksumBytes[pos++] = InitialToken.this.CHECKSUM_FIRST_BYTES[3];
/*     */       
/*  97 */       ChannelBinding localBindings = context.getChannelBinding();
/*  98 */       if (localBindings != null) {
/*  99 */         byte[] localBindingsBytes = InitialToken.this.computeChannelBinding(context.getChannelBinding());
/*     */         
/* 101 */         System.arraycopy(localBindingsBytes, 0, this.checksumBytes, pos, localBindingsBytes.length);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       pos += 16;
/*     */       
/* 109 */       if (context.getCredDelegState())
/* 110 */         this.flags |= 0x1; 
/* 111 */       if (context.getMutualAuthState())
/* 112 */         this.flags |= 0x2; 
/* 113 */       if (context.getReplayDetState())
/* 114 */         this.flags |= 0x4; 
/* 115 */       if (context.getSequenceDetState())
/* 116 */         this.flags |= 0x8; 
/* 117 */       if (context.getIntegState())
/* 118 */         this.flags |= 0x20; 
/* 119 */       if (context.getConfState()) {
/* 120 */         this.flags |= 0x10;
/*     */       }
/* 122 */       byte[] temp = new byte[4];
/* 123 */       GSSToken.writeLittleEndian(this.flags, temp);
/* 124 */       this.checksumBytes[pos++] = temp[0];
/* 125 */       this.checksumBytes[pos++] = temp[1];
/* 126 */       this.checksumBytes[pos++] = temp[2];
/* 127 */       this.checksumBytes[pos++] = temp[3];
/*     */       
/* 129 */       if (context.getCredDelegState()) {
/*     */         
/* 131 */         PrincipalName delegateTo = serviceTicket.getServer();
/*     */ 
/*     */ 
/*     */         
/* 135 */         StringBuffer buf = new StringBuffer("\"");
/* 136 */         buf.append(delegateTo.getName()).append('"');
/* 137 */         String realm = delegateTo.getRealmAsString();
/* 138 */         buf.append(" \"krbtgt/").append(realm).append('@');
/* 139 */         buf.append(realm).append('"');
/* 140 */         SecurityManager sm = System.getSecurityManager();
/* 141 */         if (sm != null) {
/* 142 */           DelegationPermission perm = new DelegationPermission(buf.toString());
/*     */           
/* 144 */           sm.checkPermission(perm);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         this.checksumBytes[pos++] = 1;
/* 154 */         this.checksumBytes[pos++] = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 161 */         if (krbCredMessage.length > 65535) {
/* 162 */           throw new GSSException(11, -1, "Incorrect messsage length");
/*     */         }
/*     */         
/* 165 */         GSSToken.writeLittleEndian(krbCredMessage.length, temp);
/* 166 */         this.checksumBytes[pos++] = temp[0];
/* 167 */         this.checksumBytes[pos++] = temp[1];
/* 168 */         System.arraycopy(krbCredMessage, 0, this.checksumBytes, pos, krbCredMessage.length);
/*     */       } 
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
/*     */     public OverloadedChecksum(Krb5Context context, Checksum checksum, EncryptionKey key) throws GSSException, KrbException, IOException {
/* 185 */       int pos = 0;
/*     */       
/* 187 */       this.checksumBytes = checksum.getBytes();
/*     */       
/* 189 */       if (this.checksumBytes[0] != InitialToken.this.CHECKSUM_FIRST_BYTES[0] || this.checksumBytes[1] != InitialToken.this.CHECKSUM_FIRST_BYTES[1] || this.checksumBytes[2] != InitialToken.this.CHECKSUM_FIRST_BYTES[2] || this.checksumBytes[3] != InitialToken.this.CHECKSUM_FIRST_BYTES[3])
/*     */       {
/*     */ 
/*     */         
/* 193 */         throw new GSSException(11, -1, "Incorrect checksum");
/*     */       }
/*     */ 
/*     */       
/* 197 */       byte[] remoteBindingBytes = new byte[16];
/* 198 */       System.arraycopy(this.checksumBytes, 4, remoteBindingBytes, 0, 16);
/*     */ 
/*     */       
/* 201 */       byte[] noBindings = new byte[16];
/* 202 */       boolean tokenContainsBindings = !Arrays.equals(noBindings, remoteBindingBytes);
/*     */ 
/*     */       
/* 205 */       ChannelBinding localBindings = context.getChannelBinding();
/*     */       
/* 207 */       if (tokenContainsBindings || localBindings != null) {
/*     */ 
/*     */         
/* 210 */         boolean badBindings = false;
/* 211 */         String errorMessage = null;
/*     */         
/* 213 */         if (tokenContainsBindings && localBindings != null) {
/*     */           
/* 215 */           byte[] localBindingsBytes = InitialToken.this.computeChannelBinding(localBindings);
/*     */ 
/*     */ 
/*     */           
/* 219 */           badBindings = !Arrays.equals(localBindingsBytes, remoteBindingBytes);
/*     */ 
/*     */           
/* 222 */           errorMessage = "Bytes mismatch!";
/* 223 */         } else if (localBindings == null) {
/* 224 */           errorMessage = "ChannelBinding not provided!";
/* 225 */           badBindings = true;
/*     */         } else {
/* 227 */           errorMessage = "Token missing ChannelBinding!";
/* 228 */           badBindings = true;
/*     */         } 
/*     */         
/* 231 */         if (badBindings) {
/* 232 */           throw new GSSException(1, -1, errorMessage);
/*     */         }
/*     */       } 
/*     */       
/* 236 */       this.flags = GSSToken.readLittleEndian(this.checksumBytes, 20, 4);
/*     */       
/* 238 */       if ((this.flags & 0x1) > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 246 */         int credLen = GSSToken.readLittleEndian(this.checksumBytes, 26, 2);
/* 247 */         byte[] credBytes = new byte[credLen];
/* 248 */         System.arraycopy(this.checksumBytes, 28, credBytes, 0, credLen);
/*     */         
/* 250 */         CipherHelper cipherHelper = context.getCipherHelper(key);
/* 251 */         if (useNullKey(cipherHelper)) {
/* 252 */           this.delegCreds = (new KrbCred(credBytes, EncryptionKey.NULL_KEY)).getDelegatedCreds()[0];
/*     */         }
/*     */         else {
/*     */           
/* 256 */           this.delegCreds = (new KrbCred(credBytes, key)).getDelegatedCreds()[0];
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean useNullKey(CipherHelper ch) {
/* 265 */       boolean flag = true;
/*     */       
/* 267 */       if (ch.getProto() == 1 || ch.isArcFour()) {
/* 268 */         flag = false;
/*     */       }
/* 270 */       return flag;
/*     */     }
/*     */     
/*     */     public Checksum getChecksum() throws KrbException {
/* 274 */       return new Checksum(this.checksumBytes, 32771);
/*     */     }
/*     */     
/*     */     public Credentials getDelegatedCreds() {
/* 278 */       return this.delegCreds;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setContextFlags(Krb5Context context) {
/* 283 */       if ((this.flags & 0x1) > 0) {
/* 284 */         context.setCredDelegState(true);
/*     */       }
/* 286 */       if ((this.flags & 0x2) == 0) {
/* 287 */         context.setMutualAuthState(false);
/*     */       }
/* 289 */       if ((this.flags & 0x4) == 0) {
/* 290 */         context.setReplayDetState(false);
/*     */       }
/* 292 */       if ((this.flags & 0x8) == 0) {
/* 293 */         context.setSequenceDetState(false);
/*     */       }
/* 295 */       if ((this.flags & 0x10) == 0) {
/* 296 */         context.setConfState(false);
/*     */       }
/* 298 */       if ((this.flags & 0x20) == 0) {
/* 299 */         context.setIntegState(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private int getAddrType(InetAddress addr) {
/* 305 */     int addressType = 255;
/*     */     
/* 307 */     if (addr instanceof java.net.Inet4Address) {
/* 308 */       addressType = 2;
/* 309 */     } else if (addr instanceof java.net.Inet6Address) {
/* 310 */       addressType = 24;
/* 311 */     }  return addressType;
/*     */   }
/*     */   
/*     */   private byte[] getAddrBytes(InetAddress addr) throws GSSException {
/* 315 */     int addressType = getAddrType(addr);
/* 316 */     byte[] addressBytes = addr.getAddress();
/* 317 */     if (addressBytes != null) {
/* 318 */       switch (addressType) {
/*     */         case 2:
/* 320 */           if (addressBytes.length != 4) {
/* 321 */             throw new GSSException(11, -1, "Incorrect AF-INET address length in ChannelBinding.");
/*     */           }
/*     */           
/* 324 */           return addressBytes;
/*     */         case 24:
/* 326 */           if (addressBytes.length != 16) {
/* 327 */             throw new GSSException(11, -1, "Incorrect AF-INET6 address length in ChannelBinding.");
/*     */           }
/*     */           
/* 330 */           return addressBytes;
/*     */       } 
/* 332 */       throw new GSSException(11, -1, "Cannot handle non AF-INET addresses in ChannelBinding.");
/*     */     } 
/*     */ 
/*     */     
/* 336 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] computeChannelBinding(ChannelBinding channelBinding) throws GSSException {
/* 342 */     InetAddress initiatorAddress = channelBinding.getInitiatorAddress();
/* 343 */     InetAddress acceptorAddress = channelBinding.getAcceptorAddress();
/* 344 */     int size = 20;
/*     */     
/* 346 */     int initiatorAddressType = getAddrType(initiatorAddress);
/* 347 */     int acceptorAddressType = getAddrType(acceptorAddress);
/*     */     
/* 349 */     byte[] initiatorAddressBytes = null;
/* 350 */     if (initiatorAddress != null) {
/* 351 */       initiatorAddressBytes = getAddrBytes(initiatorAddress);
/* 352 */       size += initiatorAddressBytes.length;
/*     */     } 
/*     */     
/* 355 */     byte[] acceptorAddressBytes = null;
/* 356 */     if (acceptorAddress != null) {
/* 357 */       acceptorAddressBytes = getAddrBytes(acceptorAddress);
/* 358 */       size += acceptorAddressBytes.length;
/*     */     } 
/*     */     
/* 361 */     byte[] appDataBytes = channelBinding.getApplicationData();
/* 362 */     if (appDataBytes != null) {
/* 363 */       size += appDataBytes.length;
/*     */     }
/*     */     
/* 366 */     byte[] data = new byte[size];
/*     */     
/* 368 */     int pos = 0;
/*     */     
/* 370 */     writeLittleEndian(initiatorAddressType, data, pos);
/* 371 */     pos += 4;
/*     */     
/* 373 */     if (initiatorAddressBytes != null) {
/* 374 */       writeLittleEndian(initiatorAddressBytes.length, data, pos);
/* 375 */       pos += 4;
/* 376 */       System.arraycopy(initiatorAddressBytes, 0, data, pos, initiatorAddressBytes.length);
/*     */       
/* 378 */       pos += initiatorAddressBytes.length;
/*     */     } else {
/*     */       
/* 381 */       pos += 4;
/*     */     } 
/*     */     
/* 384 */     writeLittleEndian(acceptorAddressType, data, pos);
/* 385 */     pos += 4;
/*     */     
/* 387 */     if (acceptorAddressBytes != null) {
/* 388 */       writeLittleEndian(acceptorAddressBytes.length, data, pos);
/* 389 */       pos += 4;
/* 390 */       System.arraycopy(acceptorAddressBytes, 0, data, pos, acceptorAddressBytes.length);
/*     */       
/* 392 */       pos += acceptorAddressBytes.length;
/*     */     } else {
/*     */       
/* 395 */       pos += 4;
/*     */     } 
/*     */     
/* 398 */     if (appDataBytes != null) {
/* 399 */       writeLittleEndian(appDataBytes.length, data, pos);
/* 400 */       pos += 4;
/* 401 */       System.arraycopy(appDataBytes, 0, data, pos, appDataBytes.length);
/*     */       
/* 403 */       pos += appDataBytes.length;
/*     */     } else {
/*     */       
/* 406 */       pos += 4;
/*     */     } 
/*     */     
/*     */     try {
/* 410 */       MessageDigest md5 = MessageDigest.getInstance("MD5");
/* 411 */       return md5.digest(data);
/* 412 */     } catch (NoSuchAlgorithmException e) {
/* 413 */       throw new GSSException(11, -1, "Could not get MD5 Message Digest - " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract byte[] encode() throws IOException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\InitialToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */