/*    */ package com.sun.xml.ws.transport.tcp.io;
/*    */ 
/*    */ import com.sun.xml.ws.transport.tcp.util.ByteBufferFactory;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ public class ByteBufferOutputStream
/*    */   extends OutputStream
/*    */ {
/*    */   private static final boolean USE_DIRECT_BUFFER = false;
/*    */   private ByteBuffer outputBuffer;
/*    */   
/*    */   public ByteBufferOutputStream() {
/* 57 */     this.outputBuffer = ByteBufferFactory.allocateView(false);
/*    */   }
/*    */   
/*    */   public ByteBufferOutputStream(int initSize) {
/* 61 */     this.outputBuffer = ByteBufferFactory.allocateView(initSize, false);
/*    */   }
/*    */   
/*    */   public ByteBufferOutputStream(ByteBuffer outputBuffer) {
/* 65 */     this.outputBuffer = outputBuffer;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 69 */     this.outputBuffer.clear();
/*    */   }
/*    */   
/*    */   public ByteBuffer getByteBuffer() {
/* 73 */     this.outputBuffer.flip();
/* 74 */     return this.outputBuffer;
/*    */   }
/*    */   
/*    */   public void write(int data) throws IOException {
/* 78 */     if (this.outputBuffer.position() == this.outputBuffer.capacity() - 1) {
/* 79 */       ByteBuffer tmpBuffer = ByteBufferFactory.allocateView(this.outputBuffer.capacity() * 2, false);
/* 80 */       tmpBuffer.put(this.outputBuffer);
/* 81 */       this.outputBuffer = tmpBuffer;
/*    */     } 
/*    */     
/* 84 */     this.outputBuffer.put((byte)data);
/*    */   }
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\io\ByteBufferOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */