/*    */ package com.sun.xml.ws.transport.tcp.server;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.istack.Nullable;
/*    */ import com.sun.xml.ws.transport.tcp.io.Connection;
/*    */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*    */ import com.sun.xml.ws.transport.tcp.util.ConnectionSession;
/*    */ import com.sun.xml.ws.transport.tcp.util.SessionCloseListener;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public final class ServerConnectionSession
/*    */   extends ConnectionSession
/*    */ {
/* 56 */   private Map<Integer, ChannelContext> channelId2context = new HashMap<Integer, ChannelContext>();
/*    */   
/*    */   private int channelCounter;
/*    */ 
/*    */   
/*    */   public ServerConnectionSession(Connection connection, SessionCloseListener<ServerConnectionSession> sessionCloseListener) {
/* 62 */     super(connection, sessionCloseListener);
/* 63 */     this.channelCounter = 1;
/* 64 */     init();
/*    */   }
/*    */   
/*    */   public void registerChannel(@NotNull ChannelContext context) {
/* 68 */     this.channelId2context.put(Integer.valueOf(context.getChannelId()), context);
/*    */   }
/*    */   @Nullable
/*    */   public ChannelContext findWSServiceContextByChannelId(int channelId) {
/* 72 */     return this.channelId2context.get(Integer.valueOf(channelId));
/*    */   }
/*    */   
/*    */   public void deregisterChannel(int channelId) {
/* 76 */     this.channelId2context.remove(Integer.valueOf(channelId));
/*    */   }
/*    */   
/*    */   public void deregisterChannel(@NotNull ChannelContext context) {
/* 80 */     deregisterChannel(context.getChannelId());
/*    */   }
/*    */   
/*    */   public void close() {
/* 84 */     super.close();
/*    */     
/* 86 */     this.channelId2context = null;
/*    */   }
/*    */   
/*    */   public int getChannelsAmount() {
/* 90 */     return this.channelId2context.size();
/*    */   }
/*    */   
/*    */   public synchronized int getNextAvailChannelId() {
/* 94 */     return this.channelCounter++;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\ServerConnectionSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */