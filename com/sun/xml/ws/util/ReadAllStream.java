/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class ReadAllStream
/*     */   extends InputStream
/*     */ {
/*  68 */   private static final Logger LOGGER = Logger.getLogger(ReadAllStream.class.getName());
/*     */   
/*     */   @NotNull
/*  71 */   private final MemoryStream memStream = new MemoryStream(); @NotNull
/*  72 */   private final FileStream fileStream = new FileStream();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean readAll;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean closed;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readAll(InputStream in, long inMemory) throws IOException {
/*  88 */     assert !this.readAll;
/*  89 */     this.readAll = true;
/*     */     
/*  91 */     boolean eof = this.memStream.readAll(in, inMemory);
/*  92 */     if (!eof) {
/*  93 */       this.fileStream.readAll(in);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*  99 */     int ch = this.memStream.read();
/* 100 */     if (ch == -1) {
/* 101 */       ch = this.fileStream.read();
/*     */     }
/* 103 */     return ch;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int sz) throws IOException {
/* 108 */     int len = this.memStream.read(b, off, sz);
/* 109 */     if (len == -1) {
/* 110 */       len = this.fileStream.read(b, off, sz);
/*     */     }
/* 112 */     return len;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 117 */     if (!this.closed) {
/* 118 */       this.memStream.close();
/* 119 */       this.fileStream.close();
/* 120 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class FileStream
/*     */     extends InputStream {
/*     */     @Nullable
/*     */     private File tempFile;
/*     */     
/*     */     void readAll(InputStream in) throws IOException {
/* 130 */       this.tempFile = File.createTempFile("jaxws", ".bin");
/* 131 */       FileOutputStream fileOut = new FileOutputStream(this.tempFile);
/*     */       try {
/* 133 */         byte[] buf = new byte[8192];
/*     */         int len;
/* 135 */         while ((len = in.read(buf)) != -1) {
/* 136 */           fileOut.write(buf, 0, len);
/*     */         }
/*     */       } finally {
/* 139 */         fileOut.close();
/*     */       } 
/* 141 */       this.fin = new FileInputStream(this.tempFile);
/*     */     } @Nullable
/*     */     private FileInputStream fin;
/*     */     private FileStream() {}
/*     */     public int read() throws IOException {
/* 146 */       return (this.fin != null) ? this.fin.read() : -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] b, int off, int sz) throws IOException {
/* 151 */       return (this.fin != null) ? this.fin.read(b, off, sz) : -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 156 */       if (this.fin != null) {
/* 157 */         this.fin.close();
/*     */       }
/* 159 */       if (this.tempFile != null) {
/* 160 */         boolean success = this.tempFile.delete();
/* 161 */         if (!success)
/* 162 */           ReadAllStream.LOGGER.log(Level.INFO, "File {0} could not be deleted", this.tempFile); 
/*     */       } 
/*     */     } }
/*     */   
/*     */   private static class MemoryStream extends InputStream {
/*     */     private Chunk head;
/*     */     private Chunk tail;
/*     */     private int curOff;
/*     */     
/*     */     private MemoryStream() {}
/*     */     
/*     */     private void add(byte[] buf, int len) {
/* 174 */       if (this.tail != null) {
/* 175 */         this.tail = this.tail.createNext(buf, 0, len);
/*     */       } else {
/* 177 */         this.head = this.tail = new Chunk(buf, 0, len);
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
/*     */     boolean readAll(InputStream in, long inMemory) throws IOException {
/* 191 */       long total = 0L;
/*     */       while (true) {
/* 193 */         byte[] buf = new byte[8192];
/* 194 */         int read = fill(in, buf);
/* 195 */         total += read;
/* 196 */         if (read != 0) {
/* 197 */           add(buf, read);
/*     */         }
/* 199 */         if (read != buf.length) {
/* 200 */           return true;
/*     */         }
/* 202 */         if (total > inMemory) {
/* 203 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private int fill(InputStream in, byte[] buf) throws IOException {
/* 210 */       int total = 0; int read;
/* 211 */       while (total < buf.length && (read = in.read(buf, total, buf.length - total)) != -1) {
/* 212 */         total += read;
/*     */       }
/* 214 */       return total;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 219 */       if (!fetch()) {
/* 220 */         return -1;
/*     */       }
/* 222 */       return this.head.buf[this.curOff++] & 0xFF;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] b, int off, int sz) throws IOException {
/* 227 */       if (!fetch()) {
/* 228 */         return -1;
/*     */       }
/* 230 */       sz = Math.min(sz, this.head.len - this.curOff - this.head.off);
/* 231 */       System.arraycopy(this.head.buf, this.curOff, b, off, sz);
/* 232 */       this.curOff += sz;
/* 233 */       return sz;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean fetch() {
/* 238 */       if (this.head == null) {
/* 239 */         return false;
/*     */       }
/* 241 */       if (this.curOff == this.head.off + this.head.len) {
/* 242 */         this.head = this.head.next;
/* 243 */         if (this.head == null) {
/* 244 */           return false;
/*     */         }
/* 246 */         this.curOff = this.head.off;
/*     */       } 
/* 248 */       return true;
/*     */     }
/*     */     
/*     */     private static final class Chunk {
/*     */       Chunk next;
/*     */       final byte[] buf;
/*     */       final int off;
/*     */       final int len;
/*     */       
/*     */       public Chunk(byte[] buf, int off, int len) {
/* 258 */         this.buf = buf;
/* 259 */         this.off = off;
/* 260 */         this.len = len;
/*     */       }
/*     */       
/*     */       public Chunk createNext(byte[] buf, int off, int len) {
/* 264 */         return this.next = new Chunk(buf, off, len);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\ReadAllStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */