/*     */ package com.sun.xml.ws.transport.tcp.client;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.client.ClientTransportException;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelException;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.VersionMismatchException;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPException;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPURI;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class TCPTransportPipe
/*     */   extends AbstractTubeImpl
/*     */ {
/*  74 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.client");
/*     */ 
/*     */   
/*  77 */   protected TCPClientTransport clientTransport = new TCPClientTransport();
/*     */   
/*     */   protected final Codec defaultCodec;
/*     */   protected final WSBinding wsBinding;
/*     */   protected final WSService wsService;
/*     */   protected final int customTCPPort;
/*     */   
/*     */   public TCPTransportPipe(ClientTubeAssemblerContext context) {
/*  85 */     this(context, -1);
/*     */   }
/*     */   
/*     */   public TCPTransportPipe(ClientTubeAssemblerContext context, int customTCPPort) {
/*  89 */     this(context.getService(), context.getBinding(), context.getCodec(), customTCPPort);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TCPTransportPipe(WSService wsService, WSBinding wsBinding, Codec defaultCodec, int customTCPPort) {
/*  94 */     this.wsService = wsService;
/*  95 */     this.wsBinding = wsBinding;
/*  96 */     this.defaultCodec = defaultCodec;
/*  97 */     this.customTCPPort = customTCPPort;
/*     */   }
/*     */   
/*     */   protected TCPTransportPipe(TCPTransportPipe that, TubeCloner cloner) {
/* 101 */     this(that.wsService, that.wsBinding, that.defaultCodec.copy(), that.customTCPPort);
/* 102 */     cloner.add((Tube)that, (Tube)this);
/*     */   }
/*     */   
/*     */   public void preDestroy() {
/* 106 */     if (this.clientTransport != null && this.clientTransport.getConnectionContext() != null) {
/* 107 */       WSConnectionManager.getInstance().closeChannel(this.clientTransport.getConnectionContext());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 113 */     return new TCPTransportPipe(this, cloner);
/*     */   }
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 117 */     return doReturnWith(process(request));
/*     */   }
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 121 */     throw new IllegalStateException("TCPTransportPipe's processResponse shouldn't be called.");
/*     */   }
/*     */   
/*     */   public NextAction processException(Throwable t) {
/* 125 */     throw new IllegalStateException("TCPTransportPipe's processException shouldn't be called.");
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet process(Packet packet) {
/* 130 */     if (logger.isLoggable(Level.FINE)) {
/* 131 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1010_TCP_TP_PROCESS_ENTER(packet.endpointAddress));
/*     */     }
/* 133 */     ChannelContext channelContext = null;
/* 134 */     WebServiceException failure = null;
/* 135 */     WSConnectionManager wsConnectionManager = WSConnectionManager.getInstance();
/*     */     
/* 137 */     int retryNum = 0;
/*     */     do {
/*     */       try {
/* 140 */         setupClientTransport(wsConnectionManager, packet.endpointAddress.getURI());
/* 141 */         channelContext = this.clientTransport.getConnectionContext();
/*     */         
/* 143 */         wsConnectionManager.lockConnection(channelContext.getConnectionSession());
/*     */ 
/*     */         
/* 146 */         Codec codec = channelContext.getCodec();
/* 147 */         ContentType ct = codec.getStaticContentType(packet);
/* 148 */         this.clientTransport.setContentType(ct.getContentType());
/*     */ 
/*     */ 
/*     */         
/* 152 */         writeTransportSOAPActionHeaderIfRequired(channelContext, ct, packet);
/*     */         
/* 154 */         if (logger.isLoggable(Level.FINE)) {
/* 155 */           logger.log(Level.FINE, MessagesMessages.WSTCP_1013_TCP_TP_PROCESS_ENCODE(ct.getContentType()));
/*     */         }
/* 157 */         codec.encode(packet, this.clientTransport.openOutputStream());
/*     */         
/* 159 */         if (logger.isLoggable(Level.FINE)) {
/* 160 */           logger.log(Level.FINE, MessagesMessages.WSTCP_1014_TCP_TP_PROCESS_SEND());
/*     */         }
/* 162 */         this.clientTransport.send();
/*     */         
/* 164 */         if (logger.isLoggable(Level.FINE)) {
/* 165 */           logger.log(Level.FINE, MessagesMessages.WSTCP_1015_TCP_TP_PROCESS_OPEN_PREPARE_READING());
/*     */         }
/* 167 */         InputStream replyInputStream = this.clientTransport.openInputStream();
/*     */         
/* 169 */         if (logger.isLoggable(Level.FINE)) {
/* 170 */           logger.log(Level.FINE, MessagesMessages.WSTCP_1016_TCP_TP_PROCESS_OPEN_PROCESS_READING(Integer.valueOf(this.clientTransport.getStatus()), this.clientTransport.getContentType()));
/*     */         }
/* 172 */         if (this.clientTransport.getStatus() != 2) {
/* 173 */           Packet reply = packet.createClientResponse(null);
/* 174 */           if (this.clientTransport.getStatus() != 1) {
/* 175 */             String contentTypeStr = this.clientTransport.getContentType();
/* 176 */             codec.decode(replyInputStream, contentTypeStr, reply);
/*     */           } else {
/* 178 */             releaseSession(channelContext);
/*     */           } 
/* 180 */           return reply;
/*     */         } 
/* 182 */         logger.log(Level.SEVERE, MessagesMessages.WSTCP_0016_ERROR_WS_EXECUTION_ON_SERVER(this.clientTransport.getError()));
/* 183 */         throw new WSTCPException(this.clientTransport.getError());
/*     */       }
/* 185 */       catch (ClientTransportException e) {
/* 186 */         abortSession(channelContext);
/* 187 */         ClientTransportException clientTransportException1 = e;
/* 188 */       } catch (WSTCPException e) {
/* 189 */         if (e.getError().isCritical()) {
/* 190 */           abortSession(channelContext);
/*     */         } else {
/* 192 */           releaseSession(channelContext);
/*     */         } 
/* 194 */         failure = new WebServiceException(MessagesMessages.WSTCP_0016_ERROR_WS_EXECUTION_ON_SERVER(e.getError()), (Throwable)e);
/* 195 */       } catch (IOException e) {
/* 196 */         abortSession(channelContext);
/* 197 */         failure = new WebServiceException(MessagesMessages.WSTCP_0017_ERROR_WS_EXECUTION_ON_CLIENT(), e);
/* 198 */       } catch (ServiceChannelException e) {
/* 199 */         releaseSession(channelContext);
/* 200 */         retryNum = 6;
/* 201 */         failure = new WebServiceException(MessagesMessages.WSTCP_0016_ERROR_WS_EXECUTION_ON_SERVER(e.getFaultInfo().getErrorCode() + ":" + e.getMessage()), (Throwable)e);
/* 202 */       } catch (Exception e) {
/* 203 */         abortSession(channelContext);
/* 204 */         retryNum = 6;
/* 205 */         failure = new WebServiceException(MessagesMessages.WSTCP_0017_ERROR_WS_EXECUTION_ON_CLIENT(), e);
/*     */       } 
/*     */       
/* 208 */       if (!logger.isLoggable(Level.FINE) || !canRetry(retryNum + 1))
/* 209 */         continue;  logger.log(Level.FINE, MessagesMessages.WSTCP_0012_SEND_RETRY(Integer.valueOf(retryNum)), failure);
/*     */     }
/* 211 */     while (canRetry(++retryNum));
/*     */     
/* 213 */     assert failure != null;
/* 214 */     logger.log(Level.SEVERE, MessagesMessages.WSTCP_0001_MESSAGE_PROCESS_FAILED(), failure);
/* 215 */     throw failure;
/*     */   }
/*     */   
/*     */   protected void writeTransportSOAPActionHeaderIfRequired(ChannelContext channelContext, ContentType ct, Packet packet) {
/* 219 */     String soapActionTransportHeader = getSOAPAction(ct.getSOAPActionHeader(), packet);
/* 220 */     if (soapActionTransportHeader != null) {
/*     */       try {
/* 222 */         int transportSoapActionParamId = channelContext.encodeParam("SOAPAction");
/* 223 */         channelContext.getConnection().setContentProperty(transportSoapActionParamId, soapActionTransportHeader);
/* 224 */       } catch (WSTCPException ex) {
/* 225 */         logger.log(Level.WARNING, MessagesMessages.WSTCP_0032_UNEXPECTED_TRANSPORT_SOAP_ACTION(), (Throwable)ex);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void abortSession(ChannelContext channelContext) {
/* 231 */     if (channelContext != null) {
/* 232 */       WSConnectionManager.getInstance().abortConnection(channelContext.getConnectionSession());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void releaseSession(ChannelContext channelContext) {
/* 237 */     if (channelContext != null) {
/* 238 */       WSConnectionManager.getInstance().freeConnection(channelContext.getConnectionSession());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private void setupClientTransport(@NotNull WSConnectionManager wsConnectionManager, @NotNull URI uri) throws InterruptedException, IOException, ServiceChannelException, VersionMismatchException {
/* 245 */     WSTCPURI tcpURI = WSTCPURI.parse(uri);
/* 246 */     if (tcpURI == null) throw new WebServiceException(MessagesMessages.WSTCP_0005_INVALID_EP_URL(uri.toString())); 
/* 247 */     tcpURI.setCustomPort(this.customTCPPort);
/* 248 */     ChannelContext channelContext = wsConnectionManager.openChannel(tcpURI, this.wsService, this.wsBinding, this.defaultCodec);
/* 249 */     this.clientTransport.setup(channelContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String getSOAPAction(String soapAction, Packet packet) {
/* 257 */     Boolean useAction = (Boolean)packet.invocationProperties.get("javax.xml.ws.soap.http.soapaction.use");
/* 258 */     String sAction = null;
/* 259 */     boolean use = (useAction != null) ? useAction.booleanValue() : false;
/*     */     
/* 261 */     if (use)
/*     */     {
/* 263 */       sAction = packet.soapAction;
/*     */     }
/*     */     
/* 266 */     if (sAction != null) {
/* 267 */       return sAction;
/*     */     }
/* 269 */     return soapAction;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean canRetry(int retryNum) {
/* 274 */     return (retryNum <= 5);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\client\TCPTransportPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */