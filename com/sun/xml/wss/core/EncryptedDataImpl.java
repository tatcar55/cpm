/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedDataImpl
/*     */   extends ByteArrayOutputStream
/*     */ {
/*  67 */   private byte[] iv = null;
/*  68 */   private byte[] encryptedData = null;
/*  69 */   private String id = null;
/*  70 */   private String mimeType = null;
/*  71 */   private String encoding = null;
/*  72 */   private String type = null;
/*  73 */   private KeyInfoHeaderBlock keyInfo = null;
/*  74 */   private String encAlgo = null;
/*     */   
/*  76 */   private static final byte[] ENCRYPTED_DATA = "EncryptedData".getBytes();
/*  77 */   private static final byte[] ENC_PREFIX = "xenc".getBytes();
/*  78 */   private static final byte[] ENC_NS = "http://www.w3.org/2001/04/xmlenc#".getBytes();
/*  79 */   private static byte[] OPENTAG = "<".getBytes();
/*  80 */   private static byte[] CLOSETAG = ">".getBytes();
/*  81 */   private static byte[] ENDTAG = "</".getBytes();
/*  82 */   private static byte[] CLOSEELEMENT = "/>".getBytes();
/*  83 */   private static byte[] ENCRYPTION_METHOD = "EncryptionMethod ".getBytes();
/*  84 */   private static byte[] ALGORITHM = "Algorithm ".getBytes();
/*  85 */   private static byte[] XMLNS = "xmlns".getBytes();
/*  86 */   private static byte[] ID = "Id".getBytes();
/*     */   
/*  88 */   private static byte[] CIPHER_DATA = "CipherData".getBytes();
/*  89 */   private static byte[] CIPHER_VALUE = "CipherValue".getBytes();
/*  90 */   private static byte[] TYPE = "Type".getBytes();
/*  91 */   private static byte[] CONTENT_ONLY = "http://www.w3.org/2001/04/xmlenc#Content".getBytes();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getIv() {
/*  97 */     return this.iv;
/*     */   }
/*     */   
/*     */   public void setIv(byte[] iv) {
/* 101 */     this.iv = iv;
/*     */   }
/*     */   
/*     */   public byte[] getEncryptedData() {
/* 105 */     return this.encryptedData;
/*     */   }
/*     */   
/*     */   public void setEncryptedData(byte[] encryptedData) {
/* 109 */     this.encryptedData = encryptedData;
/*     */   }
/*     */   
/*     */   public KeyInfoHeaderBlock getKeyInfo() {
/* 113 */     return this.keyInfo;
/*     */   }
/*     */   
/*     */   public void setKeyInfo(KeyInfoHeaderBlock keyInfo) {
/* 117 */     this.keyInfo = keyInfo;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 121 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 125 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/* 129 */     return this.mimeType;
/*     */   }
/*     */   
/*     */   public void setMimeType(String mimeType) {
/* 133 */     this.mimeType = mimeType;
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/* 137 */     return this.encoding;
/*     */   }
/*     */   
/*     */   public void setEncoding(String encoding) {
/* 141 */     this.encoding = encoding;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 145 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/* 149 */     this.type = type;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncAlgo() {
/* 241 */     return this.encAlgo;
/*     */   }
/*     */   
/*     */   public void setEncAlgo(String encAlgo) {
/* 245 */     this.encAlgo = encAlgo;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\EncryptedDataImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */