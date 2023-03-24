/*     */ package org.jvnet.mimepull;
/*     */ 
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
/*     */ final class ChunkInputStream
/*     */   extends InputStream
/*     */ {
/*     */   Chunk current;
/*     */   int offset;
/*     */   int len;
/*     */   final MIMEMessage msg;
/*     */   final MIMEPart part;
/*     */   byte[] buf;
/*     */   
/*     */   public ChunkInputStream(MIMEMessage msg, MIMEPart part, Chunk startPos) {
/*  61 */     this.current = startPos;
/*  62 */     this.len = this.current.data.size();
/*  63 */     this.buf = this.current.data.read();
/*  64 */     this.msg = msg;
/*  65 */     this.part = part;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int sz) throws IOException {
/*  70 */     if (!fetch()) {
/*  71 */       return -1;
/*     */     }
/*     */     
/*  74 */     sz = Math.min(sz, this.len - this.offset);
/*  75 */     System.arraycopy(this.buf, this.offset, b, off, sz);
/*  76 */     return sz;
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/*  80 */     if (!fetch()) {
/*  81 */       return -1;
/*     */     }
/*  83 */     return this.buf[this.offset++] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean fetch() {
/*  91 */     if (this.current == null) {
/*  92 */       throw new IllegalStateException("Stream already closed");
/*     */     }
/*  94 */     while (this.offset == this.len) {
/*  95 */       while (!this.part.parsed && this.current.next == null) {
/*  96 */         this.msg.makeProgress();
/*     */       }
/*  98 */       this.current = this.current.next;
/*     */       
/* 100 */       if (this.current == null) {
/* 101 */         return false;
/*     */       }
/* 103 */       this.offset = 0;
/* 104 */       this.buf = this.current.data.read();
/* 105 */       this.len = this.current.data.size();
/*     */     } 
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 112 */     super.close();
/* 113 */     this.current = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\ChunkInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */