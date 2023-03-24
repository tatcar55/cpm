/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.File;
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
/*     */ public class MIMEConfig
/*     */ {
/*     */   private static final int DEFAULT_CHUNK_SIZE = 8192;
/*     */   private static final long DEFAULT_MEMORY_THRESHOLD = 1048576L;
/*     */   private static final String DEFAULT_FILE_PREFIX = "MIME";
/*  58 */   private static final Logger LOGGER = Logger.getLogger(MIMEConfig.class.getName());
/*     */ 
/*     */   
/*     */   boolean parseEagerly;
/*     */ 
/*     */   
/*     */   int chunkSize;
/*     */   
/*     */   long memoryThreshold;
/*     */   
/*     */   File tempDir;
/*     */   
/*     */   String prefix;
/*     */   
/*     */   String suffix;
/*     */ 
/*     */   
/*     */   private MIMEConfig(boolean parseEagerly, int chunkSize, long inMemoryThreshold, String dir, String prefix, String suffix) {
/*  76 */     this.parseEagerly = parseEagerly;
/*  77 */     this.chunkSize = chunkSize;
/*  78 */     this.memoryThreshold = inMemoryThreshold;
/*  79 */     this.prefix = prefix;
/*  80 */     this.suffix = suffix;
/*  81 */     setDir(dir);
/*     */   }
/*     */   
/*     */   public MIMEConfig() {
/*  85 */     this(false, 8192, 1048576L, null, "MIME", null);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isParseEagerly() {
/*  90 */     return this.parseEagerly;
/*     */   }
/*     */   
/*     */   public void setParseEagerly(boolean parseEagerly) {
/*  94 */     this.parseEagerly = parseEagerly;
/*     */   }
/*     */   
/*     */   int getChunkSize() {
/*  98 */     return this.chunkSize;
/*     */   }
/*     */   
/*     */   void setChunkSize(int chunkSize) {
/* 102 */     this.chunkSize = chunkSize;
/*     */   }
/*     */   
/*     */   long getMemoryThreshold() {
/* 106 */     return this.memoryThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMemoryThreshold(long memoryThreshold) {
/* 117 */     this.memoryThreshold = memoryThreshold;
/*     */   }
/*     */   
/*     */   boolean isOnlyMemory() {
/* 121 */     return (this.memoryThreshold == -1L);
/*     */   }
/*     */   
/*     */   File getTempDir() {
/* 125 */     return this.tempDir;
/*     */   }
/*     */   
/*     */   String getTempFilePrefix() {
/* 129 */     return this.prefix;
/*     */   }
/*     */   
/*     */   String getTempFileSuffix() {
/* 133 */     return this.suffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setDir(String dir) {
/* 140 */     if (this.tempDir == null && dir != null && !dir.equals("")) {
/* 141 */       this.tempDir = new File(dir);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/* 150 */     if (!isOnlyMemory())
/*     */       try {
/* 152 */         File tempFile = (this.tempDir == null) ? File.createTempFile(this.prefix, this.suffix) : File.createTempFile(this.prefix, this.suffix, this.tempDir);
/*     */ 
/*     */         
/* 155 */         boolean deleted = tempFile.delete();
/* 156 */         if (!deleted && 
/* 157 */           LOGGER.isLoggable(Level.INFO)) {
/* 158 */           LOGGER.log(Level.INFO, "File {0} was not deleted", tempFile.getAbsolutePath());
/*     */         }
/*     */       }
/* 161 */       catch (Exception ioe) {
/* 162 */         this.memoryThreshold = -1L;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\MIMEConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */