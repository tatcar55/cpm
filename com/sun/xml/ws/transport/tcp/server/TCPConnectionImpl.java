/*     */ package com.sun.xml.ws.transport.tcp.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WebServiceContextDelegate;
/*     */ import com.sun.xml.ws.transport.tcp.io.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.io.DataInOutUtils;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.FrameType;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPError;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Principal;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCPConnectionImpl
/*     */   implements WebServiceContextDelegate
/*     */ {
/*     */   private final ChannelContext channelContext;
/*     */   private final Connection connection;
/*     */   private String contentType;
/*     */   private int replyStatus;
/*     */   private InputStream inputStream;
/*     */   private OutputStream outputStream;
/*     */   private boolean isHeaderSerialized;
/*     */   
/*     */   public TCPConnectionImpl(ChannelContext channelContext) {
/*  75 */     this.channelContext = channelContext;
/*  76 */     this.connection = channelContext.getConnection();
/*     */   }
/*     */   
/*     */   public InputStream openInput() throws IOException, WSTCPException {
/*  80 */     this.inputStream = this.connection.openInputStream();
/*  81 */     this.contentType = this.channelContext.getContentType();
/*  82 */     return this.inputStream;
/*     */   }
/*     */   
/*     */   public OutputStream openOutput() throws IOException, WSTCPException {
/*     */     try {
/*  87 */       setMessageHeaders();
/*  88 */     } catch (IOException ex) {}
/*     */ 
/*     */     
/*  91 */     this.outputStream = this.connection.openOutputStream();
/*  92 */     return this.outputStream;
/*     */   }
/*     */   
/*     */   public int getStatus() {
/*  96 */     return this.replyStatus;
/*     */   }
/*     */   
/*     */   public void setStatus(int statusCode) {
/* 100 */     this.replyStatus = statusCode;
/*     */   }
/*     */   
/*     */   public String getContentType() {
/* 104 */     return this.contentType;
/*     */   }
/*     */   
/*     */   public void setContentType(String contentType) {
/* 108 */     this.contentType = contentType;
/*     */   }
/*     */   
/*     */   public void flush() throws IOException, WSTCPException {
/* 112 */     if (this.outputStream == null) {
/* 113 */       setMessageHeaders();
/* 114 */       this.outputStream = this.connection.openOutputStream();
/*     */     } 
/*     */     
/* 117 */     this.connection.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */   
/*     */   public Principal getUserPrincipal(Packet request) {
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUserInRole(Packet request, String role) {
/* 130 */     return false;
/*     */   }
/*     */   @NotNull
/*     */   public String getEPRAddress(@NotNull Packet request, @NotNull WSEndpoint endpoint) {
/* 134 */     return this.channelContext.getTargetWSURI().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWSDLAddress(@NotNull Packet request, @NotNull WSEndpoint endpoint) {
/* 139 */     return null;
/*     */   }
/*     */   
/*     */   public void sendErrorMessage(WSTCPError message) throws IOException, WSTCPException {
/* 143 */     setStatus(2);
/* 144 */     OutputStream output = openOutput();
/* 145 */     String description = message.getDescription();
/* 146 */     DataInOutUtils.writeInts4(output, new int[] { message.getCode(), message.getSubCode(), description.length() });
/* 147 */     output.write(description.getBytes("UTF-8"));
/* 148 */     flush();
/*     */   }
/*     */   
/*     */   private void setMessageHeaders() throws IOException, WSTCPException {
/* 152 */     if (!this.isHeaderSerialized) {
/* 153 */       this.isHeaderSerialized = true;
/*     */       
/* 155 */       int messageId = getMessageId();
/* 156 */       this.connection.setMessageId(messageId);
/* 157 */       if (FrameType.isFrameContainsParams(messageId)) {
/* 158 */         this.channelContext.setContentType(this.contentType);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getMessageId() {
/* 164 */     if (getStatus() == 1)
/* 165 */       return 5; 
/* 166 */     if (getStatus() != 0) {
/* 167 */       return 4;
/*     */     }
/*     */     
/* 170 */     return 0;
/*     */   }
/*     */   
/*     */   public ChannelContext getChannelContext() {
/* 174 */     return this.channelContext;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\TCPConnectionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */