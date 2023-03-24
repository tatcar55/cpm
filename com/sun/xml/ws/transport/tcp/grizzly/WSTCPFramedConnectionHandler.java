/*    */ package com.sun.xml.ws.transport.tcp.grizzly;
/*    */ 
/*    */ import com.sun.enterprise.web.connector.grizzly.Handler;
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.transport.tcp.server.IncomeMessageProcessor;
/*    */ import java.io.IOException;
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
/*    */ public final class WSTCPFramedConnectionHandler
/*    */   implements Handler
/*    */ {
/*    */   private final WSTCPStreamAlgorithm streamAlgorithm;
/*    */   private final IncomeMessageProcessor messageProcessor;
/*    */   
/*    */   public WSTCPFramedConnectionHandler(@NotNull WSTCPStreamAlgorithm streamAlgorithm) {
/* 59 */     this.streamAlgorithm = streamAlgorithm;
/* 60 */     this.messageProcessor = IncomeMessageProcessor.getMessageProcessorForPort(streamAlgorithm.getPort());
/*    */   }
/*    */   
/*    */   public int handle(Object request, int code) throws IOException {
/* 64 */     if (code == 2) {
/* 65 */       ByteBuffer messageBuffer = this.streamAlgorithm.getByteBuffer();
/* 66 */       SocketChannel socketChannel = this.streamAlgorithm.getSocketChannel();
/* 67 */       this.messageProcessor.process(messageBuffer, socketChannel);
/*    */     } 
/*    */     
/* 70 */     return 1;
/*    */   }
/*    */   
/*    */   public void attachChannel(SocketChannel socketChannel) {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\grizzly\WSTCPFramedConnectionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */