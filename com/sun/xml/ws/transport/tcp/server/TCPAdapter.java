/*     */ package com.sun.xml.ws.transport.tcp.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.server.Adapter;
/*     */ import com.sun.xml.ws.api.server.TransportBackChannel;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.transport.http.DeploymentDescriptorParser;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPError;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPException;
/*     */ import com.sun.xml.ws.util.Pool;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class TCPAdapter
/*     */   extends Adapter<TCPAdapter.TCPToolkit>
/*     */ {
/*  65 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*     */   
/*     */   final String name;
/*     */   
/*     */   final String urlPattern;
/*     */   
/*     */   public TCPAdapter(@NotNull String name, @NotNull String urlPattern, @NotNull WSEndpoint endpoint) {
/*  72 */     super(endpoint);
/*  73 */     this.name = name;
/*  74 */     this.urlPattern = urlPattern;
/*     */   }
/*     */   
/*     */   public void handle(@NotNull ChannelContext channelContext) throws IOException, WSTCPException {
/*  78 */     TCPConnectionImpl connection = new TCPConnectionImpl(channelContext);
/*     */     
/*  80 */     Pool<TCPToolkit> currentPool = getPool();
/*  81 */     TCPToolkit tk = (TCPToolkit)currentPool.take();
/*     */     try {
/*  83 */       tk.handle(connection);
/*  84 */       connection.flush();
/*     */     } finally {
/*  86 */       currentPool.recycle(tk);
/*  87 */       connection.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected TCPToolkit createToolkit() {
/*  92 */     return new TCPToolkit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValidPath() {
/* 100 */     if (this.urlPattern.endsWith("/*")) {
/* 101 */       return this.urlPattern.substring(0, this.urlPattern.length() - 2);
/*     */     }
/* 103 */     return this.urlPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendErrorResponse(@NotNull ChannelContext channelContext, WSTCPError message) throws IOException, WSTCPException {
/* 109 */     TCPConnectionImpl connection = new TCPConnectionImpl(channelContext);
/* 110 */     connection.sendErrorMessage(message);
/*     */   } public class TCPToolkit extends Adapter.Toolkit implements TransportBackChannel { protected TCPConnectionImpl connection;
/*     */     public TCPToolkit() {
/* 113 */       super(TCPAdapter.this);
/*     */     }
/*     */     private boolean isClosed;
/*     */     
/*     */     protected void handle(@NotNull TCPConnectionImpl con) throws IOException, WSTCPException {
/* 118 */       this.connection = con;
/* 119 */       this.isClosed = false;
/*     */       
/* 121 */       InputStream in = this.connection.openInput();
/* 122 */       Codec currentCodec = getCodec(this.connection.getChannelContext());
/*     */       
/* 124 */       String ct = this.connection.getContentType();
/* 125 */       if (TCPAdapter.logger.isLoggable(Level.FINE)) {
/* 126 */         TCPAdapter.logger.log(Level.FINE, MessagesMessages.WSTCP_1090_TCP_ADAPTER_REQ_CONTENT_TYPE(ct));
/*     */       }
/*     */       
/* 129 */       Packet packet = new Packet();
/* 130 */       currentCodec.decode(in, ct, packet);
/* 131 */       if (TCPAdapter.logger.isLoggable(Level.FINE)) {
/* 132 */         TCPAdapter.logger.log(Level.FINE, MessagesMessages.WSTCP_1091_TCP_ADAPTER_DECODED());
/*     */       }
/* 134 */       addCustomPacketSattellites(packet);
/* 135 */       packet = this.head.process(packet, this.connection, this);
/*     */       
/* 137 */       if (this.isClosed) {
/*     */         return;
/*     */       }
/*     */       
/* 141 */       ct = currentCodec.getStaticContentType(packet).getContentType();
/* 142 */       if (TCPAdapter.logger.isLoggable(Level.FINE)) {
/* 143 */         TCPAdapter.logger.log(Level.FINE, MessagesMessages.WSTCP_1092_TCP_ADAPTER_RPL_CONTENT_TYPE(ct));
/*     */       }
/* 145 */       if (ct == null) {
/* 146 */         throw new UnsupportedOperationException(MessagesMessages.WSTCP_0021_TCP_ADAPTER_UNSUPPORTER_OPERATION());
/*     */       }
/* 148 */       this.connection.setContentType(ct);
/* 149 */       if (packet.getMessage() == null) {
/* 150 */         if (TCPAdapter.logger.isLoggable(Level.FINE)) {
/* 151 */           TCPAdapter.logger.log(Level.FINE, MessagesMessages.WSTCP_1093_TCP_ADAPTER_ONE_WAY());
/*     */         }
/* 153 */         this.connection.setStatus(1);
/*     */       } else {
/* 155 */         currentCodec.encode(packet, this.connection.openOutput());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     protected Codec getCodec(@NotNull ChannelContext context) {
/* 162 */       return context.getCodec();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void addCustomPacketSattellites(@NotNull Packet packet) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() {
/* 172 */       if (TCPAdapter.logger.isLoggable(Level.FINE)) {
/* 173 */         TCPAdapter.logger.log(Level.FINE, MessagesMessages.WSTCP_1094_TCP_ADAPTER_CLOSE());
/*     */       }
/* 175 */       this.connection.setStatus(1);
/* 176 */       this.isClosed = true;
/*     */     } }
/*     */ 
/*     */   
/* 180 */   public static final DeploymentDescriptorParser.AdapterFactory<TCPAdapter> FACTORY = new TCPAdapterList();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\TCPAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */