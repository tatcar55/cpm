/*    */ package com.sun.xml.ws.transport.tcp.server.tomcat;
/*    */ 
/*    */ import com.sun.xml.ws.transport.tcp.server.TCPAdapter;
/*    */ import com.sun.xml.ws.transport.tcp.server.TCPMessageListener;
/*    */ import com.sun.xml.ws.transport.tcp.server.WSTCPDelegate;
/*    */ import com.sun.xml.ws.transport.tcp.server.WSTCPModule;
/*    */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*    */ import com.sun.xml.ws.transport.tcp.util.WSTCPError;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
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
/*    */ public class WSTCPTomcatRegistry
/*    */   extends WSTCPModule
/*    */   implements TCPMessageListener
/*    */ {
/*    */   private WSTCPDelegate delegate;
/* 58 */   private int listeningPort = -1;
/*    */   
/*    */   protected static void setInstance(WSTCPModule instance) {
/* 61 */     WSTCPModule.setInstance(instance);
/*    */   }
/*    */   
/*    */   WSTCPTomcatRegistry(int port) {
/* 65 */     this.listeningPort = port;
/* 66 */     this.delegate = new WSTCPDelegate();
/*    */   }
/*    */   
/*    */   public int getPort() {
/* 70 */     return this.listeningPort;
/*    */   }
/*    */   
/*    */   public void onMessage(ChannelContext channelContext) throws IOException {
/* 74 */     this.delegate.onMessage(channelContext);
/*    */   }
/*    */   
/*    */   public void onError(ChannelContext channelContext, WSTCPError error) throws IOException {
/* 78 */     this.delegate.onError(channelContext, error);
/*    */   }
/*    */ 
/*    */   
/*    */   public void register(String contextPath, List<TCPAdapter> adapters) {
/* 83 */     this.delegate.registerAdapters(contextPath, adapters);
/*    */   }
/*    */ 
/*    */   
/*    */   public void free(String contextPath, List<TCPAdapter> adapters) {
/* 88 */     this.delegate.freeAdapters(contextPath, adapters);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\tomcat\WSTCPTomcatRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */