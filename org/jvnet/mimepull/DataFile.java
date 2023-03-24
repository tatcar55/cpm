/*    */ package org.jvnet.mimepull;
/*    */ 
/*    */ import java.io.File;
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
/*    */ 
/*    */ 
/*    */ final class DataFile
/*    */ {
/*    */   private WeakDataFile weak;
/*    */   private long writePointer;
/*    */   
/*    */   DataFile(File file) {
/* 57 */     this.writePointer = 0L;
/* 58 */     this.weak = new WeakDataFile(this, file);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void close() {
/* 65 */     this.weak.close();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   synchronized void read(long pointer, byte[] buf, int offset, int length) {
/* 77 */     this.weak.read(pointer, buf, offset, length);
/*    */   }
/*    */   
/*    */   void renameTo(File f) {
/* 81 */     this.weak.renameTo(f);
/*    */   }
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
/*    */   synchronized long writeTo(byte[] data, int offset, int length) {
/* 94 */     long temp = this.writePointer;
/* 95 */     this.writePointer = this.weak.writeTo(this.writePointer, data, offset, length);
/* 96 */     return temp;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\DataFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */