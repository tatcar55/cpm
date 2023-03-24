/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DecryptedInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private static final int SKIP_BUFFER_SIZE = 2048;
/*     */   private static byte[] skipBuffer;
/*  62 */   private StringBuilder startElement = new StringBuilder("<StartElement");
/*     */   private static final String endElement = "</StartElement>";
/*  64 */   private InputStream startIS = null;
/*  65 */   private InputStream endIS = new ByteArrayInputStream("</StartElement>".getBytes());
/*     */ 
/*     */   
/*     */   public DecryptedInputStream(InputStream is, HashMap<String, String> parentNS) {
/*  69 */     super(is);
/*  70 */     Set<Map.Entry<String, String>> set = parentNS.entrySet();
/*  71 */     Iterator<Map.Entry<String, String>> iter = set.iterator();
/*  72 */     while (iter.hasNext()) {
/*  73 */       Map.Entry<String, String> entry = iter.next();
/*  74 */       if (!"".equals(entry.getKey())) {
/*  75 */         this.startElement.append(" xmlns:" + (String)entry.getKey() + "=\"" + (String)entry.getValue() + "\""); continue;
/*     */       } 
/*  77 */       this.startElement.append(" xmlns=\"" + (String)entry.getValue() + "\"");
/*     */     } 
/*     */     
/*  80 */     this.startElement.append(" >");
/*  81 */     String startElem = this.startElement.toString();
/*  82 */     this.startIS = new ByteArrayInputStream(startElem.getBytes());
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/*  86 */     int readVal = this.startIS.read();
/*  87 */     if (readVal != -1) {
/*  88 */       return readVal;
/*     */     }
/*  90 */     readVal = this.in.read();
/*  91 */     if (readVal != -1) {
/*  92 */       return readVal;
/*     */     }
/*  94 */     return this.endIS.read();
/*     */   }
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/*  98 */     return read(b, 0, b.length - 1);
/*     */   }
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 102 */     if (b == null)
/* 103 */       throw new NullPointerException(); 
/* 104 */     if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0)
/*     */     {
/* 106 */       throw new IndexOutOfBoundsException(); } 
/* 107 */     if (len == 0) {
/* 108 */       return 0;
/*     */     }
/* 110 */     int readVal = read();
/* 111 */     if (readVal == -1) {
/* 112 */       return -1;
/*     */     }
/* 114 */     b[off] = (byte)readVal;
/* 115 */     int i = 1;
/* 116 */     for (; i < len; i++) {
/* 117 */       readVal = read();
/* 118 */       if (readVal == -1) {
/*     */         break;
/*     */       }
/* 121 */       if (b != null) {
/* 122 */         b[off + i] = (byte)readVal;
/*     */       }
/*     */     } 
/* 125 */     return i;
/*     */   }
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 129 */     long remaining = n;
/*     */     
/* 131 */     if (skipBuffer == null) {
/* 132 */       skipBuffer = new byte[2048];
/*     */     }
/* 134 */     byte[] localSkipBuffer = skipBuffer;
/*     */     
/* 136 */     if (n <= 0L) {
/* 137 */       return 0L;
/*     */     }
/*     */     
/* 140 */     while (remaining > 0L) {
/* 141 */       int nr = read(localSkipBuffer, 0, (int)Math.min(2048L, remaining));
/*     */       
/* 143 */       if (nr < 0) {
/*     */         break;
/*     */       }
/* 146 */       remaining -= nr;
/*     */     } 
/*     */     
/* 149 */     return n - remaining;
/*     */   }
/*     */   
/*     */   public boolean markSupported() {
/* 153 */     return false;
/*     */   }
/*     */   
/*     */   public synchronized void reset() throws IOException {
/* 157 */     throw new IOException("mark/reset not supported");
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 161 */     this.startIS.close();
/* 162 */     this.in.close();
/* 163 */     this.endIS.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\DecryptedInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */