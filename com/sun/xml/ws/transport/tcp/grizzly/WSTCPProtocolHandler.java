/*    */ package com.sun.xml.ws.transport.tcp.grizzly;
/*    */ 
/*    */ import com.sun.enterprise.web.portunif.ProtocolHandler;
/*    */ import com.sun.enterprise.web.portunif.util.ProtocolInfo;
/*    */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*    */ import com.sun.xml.ws.transport.tcp.server.IncomeMessageProcessor;
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.SelectionKey;
/*    */ import java.nio.channels.SocketChannel;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ public final class WSTCPProtocolHandler
/*    */   implements ProtocolHandler
/*    */ {
/* 58 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*    */   
/*    */   private static IncomeMessageProcessor processor;
/*    */ 
/*    */   
/*    */   public static void setIncomingMessageProcessor(IncomeMessageProcessor processor) {
/* 64 */     WSTCPProtocolHandler.processor = processor;
/*    */   }
/*    */   
/*    */   public String[] getProtocols() {
/* 68 */     return new String[] { "vnd.sun.ws.tcp" };
/*    */   }
/*    */   
/*    */   public void handle(ProtocolInfo tupple) throws IOException {
/* 72 */     if (processor != null) {
/* 73 */       tupple.byteBuffer.flip();
/* 74 */       processor.process(tupple.byteBuffer, (SocketChannel)tupple.key.channel());
/*    */     } else {
/* 76 */       logger.log(Level.WARNING, MessagesMessages.WSTCP_0013_TCP_PROCESSOR_NOT_REGISTERED());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean expireKey(SelectionKey key) {
/* 86 */     if (processor != null) {
/* 87 */       processor.notifyClosed((SocketChannel)key.channel());
/*    */     }
/*    */     
/* 90 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\grizzly\WSTCPProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */