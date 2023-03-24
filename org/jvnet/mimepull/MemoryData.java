/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
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
/*     */ final class MemoryData
/*     */   implements Data
/*     */ {
/*  56 */   private static final Logger LOGGER = Logger.getLogger(MemoryData.class.getName());
/*     */   
/*     */   private final byte[] data;
/*     */   private final int len;
/*     */   private final MIMEConfig config;
/*     */   
/*     */   MemoryData(ByteBuffer buf, MIMEConfig config) {
/*  63 */     this.data = buf.array();
/*  64 */     this.len = buf.limit();
/*  65 */     this.config = config;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  71 */     return this.len;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] read() {
/*  76 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public long writeTo(DataFile file) {
/*  81 */     return file.writeTo(this.data, 0, this.len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Data createNext(DataHead dataHead, ByteBuffer buf) {
/*  92 */     if (!this.config.isOnlyMemory() && dataHead.inMemory >= this.config.memoryThreshold) {
/*     */       try {
/*  94 */         String prefix = this.config.getTempFilePrefix();
/*  95 */         String suffix = this.config.getTempFileSuffix();
/*  96 */         File dir = this.config.getTempDir();
/*  97 */         File tempFile = (dir == null) ? File.createTempFile(prefix, suffix) : File.createTempFile(prefix, suffix, dir);
/*     */ 
/*     */ 
/*     */         
/* 101 */         tempFile.deleteOnExit();
/* 102 */         if (LOGGER.isLoggable(Level.FINE)) LOGGER.log(Level.FINE, "Created temp file = {0}", tempFile); 
/* 103 */         dataHead.dataFile = new DataFile(tempFile);
/* 104 */       } catch (IOException ioe) {
/* 105 */         throw new MIMEParsingException(ioe);
/*     */       } 
/*     */       
/* 108 */       if (dataHead.head != null) {
/* 109 */         for (Chunk c = dataHead.head; c != null; c = c.next) {
/* 110 */           long pointer = c.data.writeTo(dataHead.dataFile);
/* 111 */           c.data = new FileData(dataHead.dataFile, pointer, this.len);
/*     */         } 
/*     */       }
/* 114 */       return new FileData(dataHead.dataFile, buf);
/*     */     } 
/* 116 */     return new MemoryData(buf, this.config);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\MemoryData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */