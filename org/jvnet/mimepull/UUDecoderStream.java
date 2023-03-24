/*     */ package org.jvnet.mimepull;
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
/*     */ final class UUDecoderStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private String name;
/*     */   private int mode;
/*  60 */   private byte[] buffer = new byte[45];
/*  61 */   private int bufsize = 0;
/*  62 */   private int index = 0;
/*     */ 
/*     */   
/*     */   private boolean gotPrefix = false;
/*     */ 
/*     */   
/*     */   private boolean gotEnd = false;
/*     */ 
/*     */   
/*     */   private LineInputStream lin;
/*     */   
/*     */   private boolean ignoreErrors;
/*     */   
/*     */   private boolean ignoreMissingBeginEnd;
/*     */   
/*     */   private String readAhead;
/*     */ 
/*     */   
/*     */   public UUDecoderStream(InputStream in) {
/*  81 */     super(in);
/*  82 */     this.lin = new LineInputStream(in);
/*     */     
/*  84 */     this.ignoreErrors = PropUtil.getBooleanSystemProperty("mail.mime.uudecode.ignoreerrors", false);
/*     */ 
/*     */     
/*  87 */     this.ignoreMissingBeginEnd = PropUtil.getBooleanSystemProperty("mail.mime.uudecode.ignoremissingbeginend", false);
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
/*     */   public UUDecoderStream(InputStream in, boolean ignoreErrors, boolean ignoreMissingBeginEnd) {
/*  99 */     super(in);
/* 100 */     this.lin = new LineInputStream(in);
/* 101 */     this.ignoreErrors = ignoreErrors;
/* 102 */     this.ignoreMissingBeginEnd = ignoreMissingBeginEnd;
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
/* 120 */     if (this.index >= this.bufsize) {
/* 121 */       readPrefix();
/* 122 */       if (!decode()) {
/* 123 */         return -1;
/*     */       }
/* 125 */       this.index = 0;
/*     */     } 
/* 127 */     return this.buffer[this.index++] & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] buf, int off, int len) throws IOException {
/*     */     int i;
/* 133 */     for (i = 0; i < len; i++) {
/* 134 */       int c; if ((c = read()) == -1) {
/* 135 */         if (i == 0) {
/* 136 */           i = -1;
/*     */         }
/*     */         break;
/*     */       } 
/* 140 */       buf[off + i] = (byte)c;
/*     */     } 
/* 142 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 154 */     return this.in.available() * 3 / 4 + this.bufsize - this.index;
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
/* 165 */     readPrefix();
/* 166 */     return this.name;
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
/* 177 */     readPrefix();
/* 178 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readPrefix() throws IOException {
/* 187 */     if (this.gotPrefix) {
/*     */       return;
/*     */     }
/*     */     
/* 191 */     this.mode = 438;
/* 192 */     this.name = "encoder.buf";
/*     */ 
/*     */     
/*     */     while (true) {
/* 196 */       String line = this.lin.readLine();
/* 197 */       if (line == null) {
/* 198 */         if (!this.ignoreMissingBeginEnd) {
/* 199 */           throw new DecodingException("UUDecoder: Missing begin");
/*     */         }
/*     */         
/* 202 */         this.gotPrefix = true;
/* 203 */         this.gotEnd = true;
/*     */         break;
/*     */       } 
/* 206 */       if (line.regionMatches(false, 0, "begin", 0, 5)) {
/*     */         try {
/* 208 */           this.mode = Integer.parseInt(line.substring(6, 9));
/* 209 */         } catch (NumberFormatException ex) {
/* 210 */           if (!this.ignoreErrors) {
/* 211 */             throw new DecodingException("UUDecoder: Error in mode: " + ex.toString());
/*     */           }
/*     */         } 
/*     */         
/* 215 */         if (line.length() > 10) {
/* 216 */           this.name = line.substring(10);
/*     */         }
/* 218 */         else if (!this.ignoreErrors) {
/* 219 */           throw new DecodingException("UUDecoder: Missing name: " + line);
/*     */         } 
/*     */ 
/*     */         
/* 223 */         this.gotPrefix = true; break;
/*     */       } 
/* 225 */       if (this.ignoreMissingBeginEnd && line.length() != 0) {
/* 226 */         int count = line.charAt(0);
/* 227 */         count = count - 32 & 0x3F;
/* 228 */         int need = (count * 8 + 5) / 6;
/* 229 */         if (need == 0 || line.length() >= need + 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 236 */           this.readAhead = line;
/* 237 */           this.gotPrefix = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean decode() throws IOException {
/*     */     String line;
/* 246 */     if (this.gotEnd) {
/* 247 */       return false;
/*     */     }
/* 249 */     this.bufsize = 0;
/* 250 */     int count = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 257 */       if (this.readAhead != null) {
/* 258 */         line = this.readAhead;
/* 259 */         this.readAhead = null;
/*     */       } else {
/* 261 */         line = this.lin.readLine();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 269 */       if (line == null) {
/* 270 */         if (!this.ignoreMissingBeginEnd) {
/* 271 */           throw new DecodingException("UUDecoder: Missing end at EOF");
/*     */         }
/*     */         
/* 274 */         this.gotEnd = true;
/* 275 */         return false;
/*     */       } 
/* 277 */       if (line.equals("end")) {
/* 278 */         this.gotEnd = true;
/* 279 */         return false;
/*     */       } 
/* 281 */       if (line.length() == 0) {
/*     */         continue;
/*     */       }
/* 284 */       count = line.charAt(0);
/* 285 */       if (count < 32) {
/* 286 */         if (!this.ignoreErrors) {
/* 287 */           throw new DecodingException("UUDecoder: Buffer format error");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       count = count - 32 & 0x3F;
/*     */       
/* 301 */       if (count == 0) {
/* 302 */         line = this.lin.readLine();
/* 303 */         if ((line == null || !line.equals("end")) && 
/* 304 */           !this.ignoreMissingBeginEnd) {
/* 305 */           throw new DecodingException("UUDecoder: Missing End after count 0 line");
/*     */         }
/*     */ 
/*     */         
/* 309 */         this.gotEnd = true;
/* 310 */         return false;
/*     */       } 
/*     */       
/* 313 */       int need = (count * 8 + 5) / 6;
/*     */       
/* 315 */       if (line.length() < need + 1) {
/* 316 */         if (!this.ignoreErrors) {
/* 317 */           throw new DecodingException("UUDecoder: Short buffer error");
/*     */         }
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*     */       break;
/*     */     } 
/*     */     
/* 327 */     int i = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 335 */     while (this.bufsize < count) {
/*     */       
/* 337 */       byte a = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 338 */       byte b = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 339 */       this.buffer[this.bufsize++] = (byte)(a << 2 & 0xFC | b >>> 4 & 0x3);
/*     */       
/* 341 */       if (this.bufsize < count) {
/* 342 */         a = b;
/* 343 */         b = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 344 */         this.buffer[this.bufsize++] = (byte)(a << 4 & 0xF0 | b >>> 2 & 0xF);
/*     */       } 
/*     */ 
/*     */       
/* 348 */       if (this.bufsize < count) {
/* 349 */         a = b;
/* 350 */         b = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 351 */         this.buffer[this.bufsize++] = (byte)(a << 6 & 0xC0 | b & 0x3F);
/*     */       } 
/*     */     } 
/* 354 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\UUDecoderStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */