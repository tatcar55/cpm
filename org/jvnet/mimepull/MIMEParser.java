/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MIMEParser
/*     */   implements Iterable<MIMEEvent>
/*     */ {
/*  70 */   private static final Logger LOGGER = Logger.getLogger(MIMEParser.class.getName());
/*     */   
/*     */   private static final String HEADER_ENCODING = "ISO8859-1";
/*     */   
/*     */   private static final int NO_LWSP = 1000;
/*     */   
/*     */   private enum STATE
/*     */   {
/*  78 */     START_MESSAGE, SKIP_PREAMBLE, START_PART, HEADERS, BODY, END_PART, END_MESSAGE; }
/*  79 */   private STATE state = STATE.START_MESSAGE;
/*     */   
/*     */   private final InputStream in;
/*     */   private final byte[] bndbytes;
/*     */   private final int bl;
/*     */   private final MIMEConfig config;
/*  85 */   private final int[] bcs = new int[128];
/*     */ 
/*     */   
/*     */   private final int[] gss;
/*     */ 
/*     */   
/*     */   private boolean parsed;
/*     */ 
/*     */   
/*     */   private boolean done = false;
/*     */ 
/*     */   
/*     */   private boolean eof;
/*     */   
/*     */   private final int capacity;
/*     */   
/*     */   private byte[] buf;
/*     */   
/*     */   private int len;
/*     */   
/*     */   private boolean bol;
/*     */ 
/*     */   
/*     */   MIMEParser(InputStream in, String boundary, MIMEConfig config) {
/* 109 */     this.in = in;
/* 110 */     this.bndbytes = getBytes("--" + boundary);
/* 111 */     this.bl = this.bndbytes.length;
/* 112 */     this.config = config;
/* 113 */     this.gss = new int[this.bl];
/* 114 */     compileBoundaryPattern();
/*     */ 
/*     */     
/* 117 */     this.capacity = config.chunkSize + 2 + this.bl + 4 + 1000;
/* 118 */     createBuf(this.capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<MIMEEvent> iterator() {
/* 129 */     return new MIMEEventIterator();
/*     */   }
/*     */   
/*     */   class MIMEEventIterator
/*     */     implements Iterator<MIMEEvent>
/*     */   {
/*     */     public boolean hasNext() {
/* 136 */       return !MIMEParser.this.parsed;
/*     */     }
/*     */     public MIMEEvent next() {
/*     */       InternetHeaders ih;
/*     */       ByteBuffer buf;
/* 141 */       switch (MIMEParser.this.state) {
/*     */         case START_MESSAGE:
/* 143 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.START_MESSAGE); 
/* 144 */           MIMEParser.this.state = MIMEParser.STATE.SKIP_PREAMBLE;
/* 145 */           return MIMEEvent.START_MESSAGE;
/*     */         
/*     */         case SKIP_PREAMBLE:
/* 148 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.SKIP_PREAMBLE); 
/* 149 */           MIMEParser.this.skipPreamble();
/*     */         
/*     */         case START_PART:
/* 152 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.START_PART); 
/* 153 */           MIMEParser.this.state = MIMEParser.STATE.HEADERS;
/* 154 */           return MIMEEvent.START_PART;
/*     */         
/*     */         case HEADERS:
/* 157 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.HEADERS); 
/* 158 */           ih = MIMEParser.this.readHeaders();
/* 159 */           MIMEParser.this.state = MIMEParser.STATE.BODY;
/* 160 */           MIMEParser.this.bol = true;
/* 161 */           return new MIMEEvent.Headers(ih);
/*     */         
/*     */         case BODY:
/* 164 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.BODY); 
/* 165 */           buf = MIMEParser.this.readBody();
/* 166 */           MIMEParser.this.bol = false;
/* 167 */           return new MIMEEvent.Content(buf);
/*     */         
/*     */         case END_PART:
/* 170 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.END_PART); 
/* 171 */           if (MIMEParser.this.done) {
/* 172 */             MIMEParser.this.state = MIMEParser.STATE.END_MESSAGE;
/*     */           } else {
/* 174 */             MIMEParser.this.state = MIMEParser.STATE.START_PART;
/*     */           } 
/* 176 */           return MIMEEvent.END_PART;
/*     */         
/*     */         case END_MESSAGE:
/* 179 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.END_MESSAGE); 
/* 180 */           MIMEParser.this.parsed = true;
/* 181 */           return MIMEEvent.END_MESSAGE;
/*     */       } 
/*     */       
/* 184 */       throw new MIMEParsingException("Unknown Parser state = " + MIMEParser.this.state);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 190 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InternetHeaders readHeaders() {
/* 200 */     if (!this.eof) {
/* 201 */       fillBuf();
/*     */     }
/* 203 */     return new InternetHeaders(new LineInputStream());
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
/*     */   private ByteBuffer readBody() {
/* 215 */     if (!this.eof) {
/* 216 */       fillBuf();
/*     */     }
/* 218 */     int start = match(this.buf, 0, this.len);
/* 219 */     if (start == -1) {
/*     */       
/* 221 */       assert this.eof || this.len >= this.config.chunkSize;
/* 222 */       int chunkSize = this.eof ? this.len : this.config.chunkSize;
/* 223 */       if (this.eof) {
/* 224 */         this.done = true;
/* 225 */         throw new MIMEParsingException("Reached EOF, but there is no closing MIME boundary.");
/*     */       } 
/* 227 */       return adjustBuf(chunkSize, this.len - chunkSize);
/*     */     } 
/*     */ 
/*     */     
/* 231 */     int chunkLen = start;
/* 232 */     if (!this.bol || start != 0)
/*     */     {
/* 234 */       if (start > 0 && (this.buf[start - 1] == 10 || this.buf[start - 1] == 13)) {
/* 235 */         chunkLen--;
/* 236 */         if (this.buf[start - 1] == 10 && start > 1 && this.buf[start - 2] == 13) {
/* 237 */           chunkLen--;
/*     */         }
/*     */       } else {
/* 240 */         return adjustBuf(start + 1, this.len - start - 1);
/*     */       } 
/*     */     }
/* 243 */     if (start + this.bl + 1 < this.len && this.buf[start + this.bl] == 45 && this.buf[start + this.bl + 1] == 45) {
/* 244 */       this.state = STATE.END_PART;
/* 245 */       this.done = true;
/* 246 */       return adjustBuf(chunkLen, 0);
/*     */     } 
/*     */ 
/*     */     
/* 250 */     int lwsp = 0;
/* 251 */     for (int i = start + this.bl; i < this.len && (this.buf[i] == 32 || this.buf[i] == 9); i++) {
/* 252 */       lwsp++;
/*     */     }
/*     */ 
/*     */     
/* 256 */     if (start + this.bl + lwsp < this.len && this.buf[start + this.bl + lwsp] == 10) {
/* 257 */       this.state = STATE.END_PART;
/* 258 */       return adjustBuf(chunkLen, this.len - start - this.bl - lwsp - 1);
/* 259 */     }  if (start + this.bl + lwsp + 1 < this.len && this.buf[start + this.bl + lwsp] == 13 && this.buf[start + this.bl + lwsp + 1] == 10) {
/* 260 */       this.state = STATE.END_PART;
/* 261 */       return adjustBuf(chunkLen, this.len - start - this.bl - lwsp - 2);
/* 262 */     }  if (start + this.bl + lwsp + 1 < this.len)
/* 263 */       return adjustBuf(chunkLen + 1, this.len - chunkLen - 1); 
/* 264 */     if (this.eof) {
/* 265 */       this.done = true;
/* 266 */       throw new MIMEParsingException("Reached EOF, but there is no closing MIME boundary.");
/*     */     } 
/*     */ 
/*     */     
/* 270 */     return adjustBuf(chunkLen, this.len - chunkLen);
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
/*     */   private ByteBuffer adjustBuf(int chunkSize, int remaining) {
/* 283 */     assert this.buf != null;
/* 284 */     assert chunkSize >= 0;
/* 285 */     assert remaining >= 0;
/*     */     
/* 287 */     byte[] temp = this.buf;
/*     */     
/* 289 */     createBuf(remaining);
/* 290 */     System.arraycopy(temp, this.len - remaining, this.buf, 0, remaining);
/* 291 */     this.len = remaining;
/*     */     
/* 293 */     return ByteBuffer.wrap(temp, 0, chunkSize);
/*     */   }
/*     */   
/*     */   private void createBuf(int min) {
/* 297 */     this.buf = new byte[(min < this.capacity) ? this.capacity : min];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void skipPreamble() {
/*     */     while (true) {
/* 306 */       if (!this.eof) {
/* 307 */         fillBuf();
/*     */       }
/* 309 */       int start = match(this.buf, 0, this.len);
/* 310 */       if (start == -1) {
/*     */         
/* 312 */         if (this.eof) {
/* 313 */           throw new MIMEParsingException("Missing start boundary");
/*     */         }
/* 315 */         adjustBuf(this.len - this.bl + 1, this.bl - 1);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 320 */       if (start > this.config.chunkSize) {
/* 321 */         adjustBuf(start, this.len - start);
/*     */         
/*     */         continue;
/*     */       } 
/* 325 */       int lwsp = 0;
/* 326 */       for (int i = start + this.bl; i < this.len && (this.buf[i] == 32 || this.buf[i] == 9); i++) {
/* 327 */         lwsp++;
/*     */       }
/*     */       
/* 330 */       if (start + this.bl + lwsp < this.len && (this.buf[start + this.bl + lwsp] == 10 || this.buf[start + this.bl + lwsp] == 13)) {
/* 331 */         if (this.buf[start + this.bl + lwsp] == 10) {
/* 332 */           adjustBuf(start + this.bl + lwsp + 1, this.len - start - this.bl - lwsp - 1); break;
/*     */         } 
/* 334 */         if (start + this.bl + lwsp + 1 < this.len && this.buf[start + this.bl + lwsp + 1] == 10) {
/* 335 */           adjustBuf(start + this.bl + lwsp + 2, this.len - start - this.bl - lwsp - 2);
/*     */           break;
/*     */         } 
/*     */       } 
/* 339 */       adjustBuf(start + 1, this.len - start - 1);
/*     */     } 
/* 341 */     if (LOGGER.isLoggable(Level.FINE)) LOGGER.log(Level.FINE, "Skipped the preamble. buffer len={0}", Integer.valueOf(this.len)); 
/*     */   }
/*     */   
/*     */   private static byte[] getBytes(String s) {
/* 345 */     char[] chars = s.toCharArray();
/* 346 */     int size = chars.length;
/* 347 */     byte[] bytes = new byte[size];
/*     */     
/* 349 */     for (int i = 0; i < size;) {
/* 350 */       bytes[i] = (byte)chars[i++];
/*     */     }
/* 352 */     return bytes;
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
/*     */   private void compileBoundaryPattern() {
/*     */     int i;
/* 370 */     for (i = 0; i < this.bndbytes.length; i++) {
/* 371 */       this.bcs[this.bndbytes[i] & Byte.MAX_VALUE] = i + 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 376 */     for (i = this.bndbytes.length; i > 0; i--) {
/*     */       
/* 378 */       int j = this.bndbytes.length - 1; while (true) { if (j >= i) {
/*     */           
/* 380 */           if (this.bndbytes[j] == this.bndbytes[j - i]) {
/*     */             
/* 382 */             this.gss[j - 1] = i;
/*     */ 
/*     */             
/*     */             j--;
/*     */           } 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 392 */         while (j > 0)
/* 393 */           this.gss[--j] = i; 
/*     */         break; }
/*     */     
/*     */     } 
/* 397 */     this.gss[this.bndbytes.length - 1] = 1;
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
/*     */   private int match(byte[] mybuf, int off, int len) {
/* 411 */     int last = len - this.bndbytes.length;
/*     */ 
/*     */     
/* 414 */     label15: while (off <= last) {
/*     */       
/* 416 */       for (int j = this.bndbytes.length - 1; j >= 0; j--) {
/* 417 */         byte ch = mybuf[off + j];
/* 418 */         if (ch != this.bndbytes[j]) {
/*     */ 
/*     */           
/* 421 */           off += Math.max(j + 1 - this.bcs[ch & Byte.MAX_VALUE], this.gss[j]);
/*     */           
/*     */           continue label15;
/*     */         } 
/*     */       } 
/* 426 */       return off;
/*     */     } 
/* 428 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillBuf() {
/* 435 */     if (LOGGER.isLoggable(Level.FINER)) LOGGER.log(Level.FINER, "Before fillBuf() buffer len={0}", Integer.valueOf(this.len)); 
/* 436 */     assert !this.eof;
/* 437 */     while (this.len < this.buf.length) {
/*     */       int read;
/*     */       try {
/* 440 */         read = this.in.read(this.buf, this.len, this.buf.length - this.len);
/* 441 */       } catch (IOException ioe) {
/* 442 */         throw new MIMEParsingException(ioe);
/*     */       } 
/* 444 */       if (read == -1) {
/* 445 */         this.eof = true;
/*     */         try {
/* 447 */           if (LOGGER.isLoggable(Level.FINE)) LOGGER.fine("Closing the input stream."); 
/* 448 */           this.in.close();
/* 449 */         } catch (IOException ioe) {
/* 450 */           throw new MIMEParsingException(ioe);
/*     */         } 
/*     */         break;
/*     */       } 
/* 454 */       this.len += read;
/*     */     } 
/*     */     
/* 457 */     if (LOGGER.isLoggable(Level.FINER)) LOGGER.log(Level.FINER, "After fillBuf() buffer len={0}", Integer.valueOf(this.len)); 
/*     */   }
/*     */   
/*     */   private void doubleBuf() {
/* 461 */     byte[] temp = new byte[2 * this.len];
/* 462 */     System.arraycopy(this.buf, 0, temp, 0, this.len);
/* 463 */     this.buf = temp;
/* 464 */     if (!this.eof) {
/* 465 */       fillBuf();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class LineInputStream
/*     */   {
/*     */     private int offset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String readLine() throws IOException {
/* 485 */       int hdrLen = 0;
/* 486 */       int lwsp = 0;
/* 487 */       while (this.offset + hdrLen < MIMEParser.this.len) {
/* 488 */         if (MIMEParser.this.buf[this.offset + hdrLen] == 10) {
/* 489 */           lwsp = 1;
/*     */           break;
/*     */         } 
/* 492 */         if (this.offset + hdrLen + 1 == MIMEParser.this.len) {
/* 493 */           MIMEParser.this.doubleBuf();
/*     */         }
/* 495 */         if (this.offset + hdrLen + 1 >= MIMEParser.this.len) {
/* 496 */           assert MIMEParser.this.eof;
/* 497 */           return null;
/*     */         } 
/* 499 */         if (MIMEParser.this.buf[this.offset + hdrLen] == 13 && MIMEParser.this.buf[this.offset + hdrLen + 1] == 10) {
/* 500 */           lwsp = 2;
/*     */           break;
/*     */         } 
/* 503 */         hdrLen++;
/*     */       } 
/* 505 */       if (hdrLen == 0) {
/* 506 */         MIMEParser.this.adjustBuf(this.offset + lwsp, MIMEParser.this.len - this.offset - lwsp);
/* 507 */         return null;
/*     */       } 
/*     */       
/* 510 */       String hdr = new String(MIMEParser.this.buf, this.offset, hdrLen, "ISO8859-1");
/* 511 */       this.offset += hdrLen + lwsp;
/* 512 */       return hdr;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\MIMEParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */