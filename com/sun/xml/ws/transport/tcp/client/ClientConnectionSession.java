/*     */ package com.sun.xml.ws.transport.tcp.client;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.transport.tcp.io.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.ConnectionSession;
/*     */ import com.sun.xml.ws.transport.tcp.util.SessionCloseListener;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPURI;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class ClientConnectionSession
/*     */   extends ConnectionSession
/*     */ {
/*  58 */   private Map<String, Object> attributes = new HashMap<String, Object>(2);
/*  59 */   private Map<String, ChannelContext> url2ChannelMap = new HashMap<String, ChannelContext>();
/*     */   
/*     */   public ClientConnectionSession(Connection connection, SessionCloseListener sessionCloseListener) {
/*  62 */     super(connection, sessionCloseListener);
/*  63 */     init();
/*     */   }
/*     */   
/*     */   public void registerChannel(@NotNull ChannelContext context) {
/*  67 */     this.url2ChannelMap.put(context.getTargetWSURI().toString(), context);
/*     */   }
/*     */   
/*     */   public void deregisterChannel(@NotNull ChannelContext context) {
/*  71 */     String wsTCPURLString = context.getTargetWSURI().toString();
/*  72 */     ChannelContext channelToRemove = this.url2ChannelMap.get(wsTCPURLString);
/*  73 */     if (channelToRemove.getChannelId() == context.getChannelId())
/*  74 */       this.url2ChannelMap.remove(wsTCPURLString); 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ChannelContext findWSServiceContextByURI(@NotNull WSTCPURI wsTCPURI) {
/*  79 */     return this.url2ChannelMap.get(wsTCPURI.toString());
/*     */   }
/*     */   
/*     */   public void onReadCompleted() {
/*  83 */     WSConnectionManager.getInstance().freeConnection(this);
/*     */   }
/*     */   
/*     */   public void close() {
/*  87 */     super.close();
/*  88 */     this.attributes = null;
/*     */   }
/*     */   
/*     */   public void setAttribute(@NotNull String name, Object value) {
/*  92 */     this.attributes.put(name, value);
/*     */   }
/*     */   @Nullable
/*     */   public Object getAttribute(@NotNull String name) {
/*  96 */     return this.attributes.get(name);
/*     */   }
/*     */   
/*     */   public int getChannelsAmount() {
/* 100 */     return this.url2ChannelMap.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\client\ClientConnectionSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */