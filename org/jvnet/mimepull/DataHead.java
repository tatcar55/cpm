/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DataHead
/*     */ {
/*     */   volatile Chunk head;
/*     */   volatile Chunk tail;
/*     */   DataFile dataFile;
/*     */   private final MIMEPart part;
/*     */   boolean readOnce;
/*     */   volatile long inMemory;
/*     */   private Throwable consumedAt;
/*     */   
/*     */   DataHead(MIMEPart part) {
/*  79 */     this.part = part;
/*     */   }
/*     */   
/*     */   void addBody(ByteBuffer buf) {
/*  83 */     synchronized (this) {
/*  84 */       this.inMemory += buf.limit();
/*     */     } 
/*  86 */     if (this.tail != null) {
/*  87 */       this.tail = this.tail.createNext(this, buf);
/*     */     } else {
/*  89 */       this.head = this.tail = new Chunk(new MemoryData(buf, this.part.msg.config));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void doneParsing() {}
/*     */   
/*     */   void moveTo(File f) {
/*  97 */     if (this.dataFile != null) {
/*  98 */       this.dataFile.renameTo(f);
/*     */     } else {
/*     */       try {
/* 101 */         OutputStream os = new FileOutputStream(f);
/*     */         try {
/* 103 */           InputStream in = readOnce();
/* 104 */           byte[] buf = new byte[8192];
/*     */           int len;
/* 106 */           while ((len = in.read(buf)) != -1) {
/* 107 */             os.write(buf, 0, len);
/*     */           }
/*     */         } finally {
/* 110 */           if (os != null) {
/* 111 */             os.close();
/*     */           }
/*     */         } 
/* 114 */       } catch (IOException ioe) {
/* 115 */         throw new MIMEParsingException(ioe);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void close() {
/* 121 */     if (this.dataFile != null) {
/* 122 */       this.head = this.tail = null;
/* 123 */       this.dataFile.close();
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
/*     */   public InputStream read() {
/* 138 */     if (this.readOnce) {
/* 139 */       throw new IllegalStateException("readOnce() is called before, read() cannot be called later.");
/*     */     }
/*     */ 
/*     */     
/* 143 */     while (this.tail == null) {
/* 144 */       if (!this.part.msg.makeProgress()) {
/* 145 */         throw new IllegalStateException("No such MIME Part: " + this.part);
/*     */       }
/*     */     } 
/*     */     
/* 149 */     if (this.head == null) {
/* 150 */       throw new IllegalStateException("Already read. Probably readOnce() is called before.");
/*     */     }
/* 152 */     return new ReadMultiStream();
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
/*     */   private boolean unconsumed() {
/* 166 */     if (this.consumedAt != null) {
/* 167 */       AssertionError error = new AssertionError("readOnce() is already called before. See the nested exception from where it's called.");
/* 168 */       error.initCause(this.consumedAt);
/* 169 */       throw error;
/*     */     } 
/* 171 */     this.consumedAt = (new Exception()).fillInStackTrace();
/* 172 */     return true;
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
/*     */   public InputStream readOnce() {
/* 188 */     assert unconsumed();
/* 189 */     if (this.readOnce) {
/* 190 */       throw new IllegalStateException("readOnce() is called before. It can only be called once.");
/*     */     }
/* 192 */     this.readOnce = true;
/*     */     
/* 194 */     while (this.tail == null) {
/* 195 */       if (!this.part.msg.makeProgress() && this.tail == null) {
/* 196 */         throw new IllegalStateException("No such Part: " + this.part);
/*     */       }
/*     */     } 
/* 199 */     InputStream in = new ReadOnceStream();
/* 200 */     this.head = null;
/* 201 */     return in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class ReadMultiStream
/*     */     extends InputStream
/*     */   {
/* 212 */     Chunk current = DataHead.this.head;
/* 213 */     int len = this.current.data.size(); int offset;
/* 214 */     byte[] buf = this.current.data.read();
/*     */     
/*     */     boolean closed;
/*     */     
/*     */     public int read(byte[] b, int off, int sz) throws IOException {
/* 219 */       if (!fetch()) {
/* 220 */         return -1;
/*     */       }
/*     */       
/* 223 */       sz = Math.min(sz, this.len - this.offset);
/* 224 */       System.arraycopy(this.buf, this.offset, b, off, sz);
/* 225 */       this.offset += sz;
/* 226 */       return sz;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 231 */       if (!fetch()) {
/* 232 */         return -1;
/*     */       }
/* 234 */       return this.buf[this.offset++] & 0xFF;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void adjustInMemoryUsage() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean fetch() throws IOException {
/* 247 */       if (this.closed) {
/* 248 */         throw new IOException("Stream already closed");
/*     */       }
/* 250 */       if (this.current == null) {
/* 251 */         return false;
/*     */       }
/*     */       
/* 254 */       while (this.offset == this.len) {
/* 255 */         while (!DataHead.this.part.parsed && this.current.next == null) {
/* 256 */           DataHead.this.part.msg.makeProgress();
/*     */         }
/* 258 */         this.current = this.current.next;
/*     */         
/* 260 */         if (this.current == null) {
/* 261 */           return false;
/*     */         }
/* 263 */         adjustInMemoryUsage();
/* 264 */         this.offset = 0;
/* 265 */         this.buf = this.current.data.read();
/* 266 */         this.len = this.current.data.size();
/*     */       } 
/* 268 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 273 */       super.close();
/* 274 */       this.current = null;
/* 275 */       this.closed = true;
/*     */     }
/*     */   }
/*     */   
/*     */   final class ReadOnceStream
/*     */     extends ReadMultiStream
/*     */   {
/*     */     void adjustInMemoryUsage() {
/* 283 */       synchronized (DataHead.this) {
/* 284 */         DataHead.this.inMemory -= this.current.data.size();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\DataHead.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */