/*    */ package com.sun.xml.ws.transport.tcp.grizzly;
/*    */ 
/*    */ import com.sun.enterprise.web.connector.grizzly.Handler;
/*    */ import com.sun.enterprise.web.connector.grizzly.algorithms.StreamAlgorithmBase;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.SocketChannel;
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
/*    */ public final class WSTCPStreamAlgorithm
/*    */   extends StreamAlgorithmBase
/*    */ {
/*    */   private ByteBuffer resultByteBuffer;
/*    */   
/*    */   public Handler getHandler() {
/* 56 */     return this.handler;
/*    */   }
/*    */   
/*    */   public boolean parse(ByteBuffer byteBuffer) {
/* 60 */     byteBuffer.flip();
/* 61 */     this.resultByteBuffer = byteBuffer;
/* 62 */     return true;
/*    */   }
/*    */   
/*    */   public SocketChannel getSocketChannel() {
/* 66 */     return this.socketChannel;
/*    */   }
/*    */   
/*    */   public ByteBuffer getByteBuffer() {
/* 70 */     return this.resultByteBuffer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPort(int port) {
/* 79 */     super.setPort(port);
/* 80 */     this.handler = new WSTCPFramedConnectionHandler(this);
/*    */   }
/*    */   
/*    */   public void recycle() {
/* 84 */     this.resultByteBuffer = null;
/* 85 */     this.socketChannel = null;
/*    */     
/* 87 */     super.recycle();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\grizzly\WSTCPStreamAlgorithm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */