/*    */ package org.jvnet.mimepull;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class FileData
/*    */   implements Data
/*    */ {
/*    */   private final DataFile file;
/*    */   private final long pointer;
/*    */   private final int length;
/*    */   
/*    */   FileData(DataFile file, ByteBuffer buf) {
/* 57 */     this(file, file.writeTo(buf.array(), 0, buf.limit()), buf.limit());
/*    */   }
/*    */   
/*    */   FileData(DataFile file, long pointer, int length) {
/* 61 */     this.file = file;
/* 62 */     this.pointer = pointer;
/* 63 */     this.length = length;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] read() {
/* 68 */     byte[] buf = new byte[this.length];
/* 69 */     this.file.read(this.pointer, buf, 0, this.length);
/* 70 */     return buf;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long writeTo(DataFile file) {
/* 78 */     throw new IllegalStateException();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 83 */     return this.length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Data createNext(DataHead dataHead, ByteBuffer buf) {
/* 91 */     return new FileData(this.file, buf);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\FileData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */