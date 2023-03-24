/*     */ package com.sun.xml.messaging.saaj.packaging.mime.util;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UUDecoderStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private String name;
/*     */   private int mode;
/*     */   private byte[] buffer;
/*  66 */   private int bufsize = 0;
/*  67 */   private int index = 0;
/*     */   
/*     */   private boolean gotPrefix = false;
/*     */   
/*     */   private boolean gotEnd = false;
/*     */   
/*     */   private LineInputStream lin;
/*     */ 
/*     */   
/*     */   public UUDecoderStream(InputStream in) {
/*  77 */     super(in);
/*  78 */     this.lin = new LineInputStream(in);
/*  79 */     this.buffer = new byte[45];
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
/*     */   public int read() throws IOException {
/*  97 */     if (this.index >= this.bufsize) {
/*  98 */       readPrefix();
/*  99 */       if (!decode())
/* 100 */         return -1; 
/* 101 */       this.index = 0;
/*     */     } 
/* 103 */     return this.buffer[this.index++] & 0xFF;
/*     */   }
/*     */   
/*     */   public int read(byte[] buf, int off, int len) throws IOException {
/*     */     int i;
/* 108 */     for (i = 0; i < len; i++) {
/* 109 */       int c; if ((c = read()) == -1) {
/* 110 */         if (i == 0)
/* 111 */           i = -1; 
/*     */         break;
/*     */       } 
/* 114 */       buf[off + i] = (byte)c;
/*     */     } 
/* 116 */     return i;
/*     */   }
/*     */   
/*     */   public boolean markSupported() {
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 126 */     return this.in.available() * 3 / 4 + this.bufsize - this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() throws IOException {
/* 137 */     readPrefix();
/* 138 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMode() throws IOException {
/* 149 */     readPrefix();
/* 150 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readPrefix() throws IOException {
/*     */     String s;
/* 159 */     if (this.gotPrefix) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     do {
/* 165 */       s = this.lin.readLine();
/* 166 */       if (s == null)
/* 167 */         throw new IOException("UUDecoder error: No Begin"); 
/* 168 */     } while (!s.regionMatches(true, 0, "begin", 0, 5));
/*     */     try {
/* 170 */       this.mode = Integer.parseInt(s.substring(6, 9));
/* 171 */     } catch (NumberFormatException ex) {
/* 172 */       throw new IOException("UUDecoder error: " + ex.toString());
/*     */     } 
/* 174 */     this.name = s.substring(10);
/* 175 */     this.gotPrefix = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean decode() throws IOException {
/* 183 */     if (this.gotEnd)
/* 184 */       return false; 
/* 185 */     this.bufsize = 0;
/*     */     
/*     */     while (true) {
/* 188 */       String line = this.lin.readLine();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 195 */       if (line == null)
/* 196 */         throw new IOException("Missing End"); 
/* 197 */       if (line.regionMatches(true, 0, "end", 0, 3)) {
/* 198 */         this.gotEnd = true;
/* 199 */         return false;
/*     */       } 
/* 201 */       if (line.length() != 0) {
/* 202 */         int count = line.charAt(0);
/* 203 */         if (count < 32) {
/* 204 */           throw new IOException("Buffer format error");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 212 */         count = count - 32 & 0x3F;
/*     */         
/* 214 */         if (count == 0) {
/* 215 */           line = this.lin.readLine();
/* 216 */           if (line == null || !line.regionMatches(true, 0, "end", 0, 3))
/* 217 */             throw new IOException("Missing End"); 
/* 218 */           this.gotEnd = true;
/* 219 */           return false;
/*     */         } 
/*     */         
/* 222 */         int need = (count * 8 + 5) / 6;
/*     */         
/* 224 */         if (line.length() < need + 1) {
/* 225 */           throw new IOException("Short buffer error");
/*     */         }
/* 227 */         int i = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 235 */         while (this.bufsize < count) {
/*     */           
/* 237 */           byte a = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 238 */           byte b = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 239 */           this.buffer[this.bufsize++] = (byte)(a << 2 & 0xFC | b >>> 4 & 0x3);
/*     */           
/* 241 */           if (this.bufsize < count) {
/* 242 */             a = b;
/* 243 */             b = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 244 */             this.buffer[this.bufsize++] = (byte)(a << 4 & 0xF0 | b >>> 2 & 0xF);
/*     */           } 
/*     */ 
/*     */           
/* 248 */           if (this.bufsize < count) {
/* 249 */             a = b;
/* 250 */             b = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 251 */             this.buffer[this.bufsize++] = (byte)(a << 6 & 0xC0 | b & 0x3F);
/*     */           } 
/*     */         } 
/* 254 */         return true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mim\\util\UUDecoderStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */