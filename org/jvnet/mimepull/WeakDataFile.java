/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Executor;
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
/*     */ final class WeakDataFile
/*     */   extends WeakReference<DataFile>
/*     */ {
/*  62 */   private static final Logger LOGGER = Logger.getLogger(WeakDataFile.class.getName());
/*     */   
/*  64 */   private static ReferenceQueue<DataFile> refQueue = new ReferenceQueue<DataFile>();
/*  65 */   private static List<WeakDataFile> refList = new ArrayList<WeakDataFile>();
/*     */   
/*     */   private final File file;
/*     */   
/*     */   static {
/*  70 */     CleanUpExecutorFactory executorFactory = CleanUpExecutorFactory.newInstance();
/*  71 */     if (executorFactory != null) {
/*  72 */       if (LOGGER.isLoggable(Level.FINE)) {
/*  73 */         LOGGER.log(Level.FINE, "Initializing clean up executor for MIMEPULL: {0}", executorFactory.getClass().getName());
/*     */       }
/*  75 */       Executor executor = executorFactory.getExecutor();
/*  76 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*     */               while (true) {
/*     */                 try {
/*  82 */                   WeakDataFile weak = (WeakDataFile)WeakDataFile.refQueue.remove();
/*  83 */                   if (WeakDataFile.LOGGER.isLoggable(Level.FINE)) {
/*  84 */                     WeakDataFile.LOGGER.log(Level.FINE, "Cleaning file = {0} from reference queue.", weak.file);
/*     */                   }
/*  86 */                   weak.close();
/*  87 */                 } catch (InterruptedException e) {}
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/*  92 */       hasCleanUpExecutor = true;
/*     */     } 
/*     */   }
/*     */   private final RandomAccessFile raf; private static boolean hasCleanUpExecutor = false;
/*     */   WeakDataFile(DataFile df, File file) {
/*  97 */     super(df, refQueue);
/*  98 */     refList.add(this);
/*  99 */     this.file = file;
/*     */     try {
/* 101 */       this.raf = new RandomAccessFile(file, "rw");
/* 102 */     } catch (IOException ioe) {
/* 103 */       throw new MIMEParsingException(ioe);
/*     */     } 
/* 105 */     if (!hasCleanUpExecutor) {
/* 106 */       drainRefQueueBounded();
/*     */     }
/*     */   }
/*     */   
/*     */   synchronized void read(long pointer, byte[] buf, int offset, int length) {
/*     */     try {
/* 112 */       this.raf.seek(pointer);
/* 113 */       this.raf.readFully(buf, offset, length);
/* 114 */     } catch (IOException ioe) {
/* 115 */       throw new MIMEParsingException(ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized long writeTo(long pointer, byte[] data, int offset, int length) {
/*     */     try {
/* 121 */       this.raf.seek(pointer);
/* 122 */       this.raf.write(data, offset, length);
/* 123 */       return this.raf.getFilePointer();
/* 124 */     } catch (IOException ioe) {
/* 125 */       throw new MIMEParsingException(ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   void close() {
/* 130 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 131 */       LOGGER.log(Level.FINE, "Deleting file = {0}", this.file.getName());
/*     */     }
/* 133 */     refList.remove(this);
/*     */     try {
/* 135 */       this.raf.close();
/* 136 */       boolean deleted = this.file.delete();
/* 137 */       if (!deleted && 
/* 138 */         LOGGER.isLoggable(Level.INFO)) {
/* 139 */         LOGGER.log(Level.INFO, "File {0} was not deleted", this.file.getAbsolutePath());
/*     */       }
/*     */     }
/* 142 */     catch (IOException ioe) {
/* 143 */       throw new MIMEParsingException(ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   void renameTo(File f) {
/* 148 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 149 */       LOGGER.log(Level.FINE, "Moving file={0} to={1}", new Object[] { this.file, f });
/*     */     }
/* 151 */     refList.remove(this);
/*     */     try {
/* 153 */       this.raf.close();
/* 154 */       boolean renamed = this.file.renameTo(f);
/* 155 */       if (!renamed && 
/* 156 */         LOGGER.isLoggable(Level.INFO)) {
/* 157 */         LOGGER.log(Level.INFO, "File {0} was not moved to {1}", new Object[] { this.file.getAbsolutePath(), f.getAbsolutePath() });
/*     */       }
/*     */     }
/* 160 */     catch (IOException ioe) {
/* 161 */       throw new MIMEParsingException(ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void drainRefQueueBounded() {
/*     */     WeakDataFile weak;
/* 168 */     while ((weak = (WeakDataFile)refQueue.poll()) != null) {
/* 169 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 170 */         LOGGER.log(Level.FINE, "Cleaning file = {0} from reference queue.", weak.file);
/*     */       }
/* 172 */       weak.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\WeakDataFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */