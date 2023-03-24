/*     */ package com.sun.xml.ws.transport.tcp.client;
/*     */ 
/*     */ import com.oracle.webservices.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.api.message.PropertySet.Property;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.DistributedPropertySet;
/*     */ import com.sun.xml.ws.transport.tcp.io.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.io.DataInOutUtils;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.FrameType;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPError;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
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
/*     */ 
/*     */ public class TCPClientTransport
/*     */   extends DistributedPropertySet
/*     */ {
/*     */   private ChannelContext channelContext;
/*     */   private Connection connection;
/*     */   private InputStream inputStream;
/*     */   private OutputStream outputStream;
/*     */   private int status;
/*     */   private String contentType;
/*     */   private WSTCPError error;
/*     */   
/*     */   public TCPClientTransport() {}
/*     */   
/*     */   public TCPClientTransport(@NotNull ChannelContext channelContext) {
/*  80 */     setup(channelContext);
/*     */   }
/*     */   
/*     */   public void setup(@Nullable ChannelContext channelContext) {
/*  84 */     this.channelContext = channelContext;
/*  85 */     if (channelContext != null) {
/*  86 */       this.connection = channelContext.getConnection();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getStatus() {
/*  91 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(int status) {
/*  95 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public OutputStream openOutputStream() throws IOException, WSTCPException {
/* 103 */     this.connection.setChannelId(this.channelContext.getChannelId());
/* 104 */     this.connection.setMessageId(0);
/* 105 */     this.channelContext.setContentType(this.contentType);
/*     */     
/* 107 */     this.outputStream = this.connection.openOutputStream();
/* 108 */     return this.outputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public InputStream openInputStream() throws IOException, WSTCPException {
/* 116 */     this.connection.prepareForReading();
/* 117 */     this.inputStream = this.connection.openInputStream();
/* 118 */     int messageId = this.connection.getMessageId();
/* 119 */     this.status = convertToReplyStatus(messageId);
/* 120 */     if (FrameType.isFrameContainsParams(messageId)) {
/* 121 */       this.contentType = this.channelContext.getContentType();
/*     */     }
/*     */     
/* 124 */     if (this.status == 2) {
/* 125 */       this.error = parseErrorMessagePayload();
/*     */     }
/*     */     
/* 128 */     return this.inputStream;
/*     */   }
/*     */   
/*     */   public void send() throws IOException {
/* 132 */     this.connection.flush();
/*     */   }
/*     */   
/*     */   public void close() {
/* 136 */     this.error = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentType(@NotNull String contentType) {
/* 141 */     this.contentType = contentType;
/*     */   }
/*     */   @Nullable
/*     */   public String getContentType() {
/* 145 */     return this.contentType;
/*     */   }
/*     */   @Nullable
/*     */   public WSTCPError getError() {
/* 149 */     return this.error;
/*     */   }
/*     */   @Nullable
/*     */   private WSTCPError parseErrorMessagePayload() throws IOException {
/* 153 */     int[] params = new int[3];
/* 154 */     DataInOutUtils.readInts4(this.inputStream, params, 3);
/* 155 */     int errorCode = params[0];
/* 156 */     int errorSubCode = params[1];
/* 157 */     int errorDescriptionBufferLength = params[2];
/*     */     
/* 159 */     byte[] errorDescriptionBuffer = new byte[errorDescriptionBufferLength];
/* 160 */     DataInOutUtils.readFully(this.inputStream, errorDescriptionBuffer);
/*     */     
/* 162 */     String errorDescription = new String(errorDescriptionBuffer, "UTF-8");
/* 163 */     return WSTCPError.createError(errorCode, errorSubCode, errorDescription);
/*     */   }
/*     */   
/*     */   private int convertToReplyStatus(int messageId) {
/* 167 */     if (messageId == 5)
/* 168 */       return 1; 
/* 169 */     if (messageId == 4) {
/* 170 */       return 2;
/*     */     }
/*     */     
/* 173 */     return 0;
/*     */   }
/*     */   
/*     */   @Property({"channelContext"})
/*     */   public ChannelContext getConnectionContext() {
/* 178 */     return this.channelContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 183 */   private static final BasePropertySet.PropertyMap model = parse(TCPClientTransport.class);
/*     */ 
/*     */   
/*     */   public BasePropertySet.PropertyMap getPropertyMap() {
/* 187 */     return model;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPMessage getSOAPMessage() throws SOAPException {
/* 192 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setSOAPMessage(SOAPMessage soap) {
/* 196 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\client\TCPClientTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */