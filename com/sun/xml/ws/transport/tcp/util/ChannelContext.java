/*     */ package com.sun.xml.ws.transport.tcp.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.SOAPBindingCodec;
/*     */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.ws.transport.tcp.encoding.WSTCPFastInfosetStreamCodec;
/*     */ import com.sun.xml.ws.transport.tcp.encoding.WSTCPFastInfosetStreamReaderRecyclable;
/*     */ import com.sun.xml.ws.transport.tcp.io.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class ChannelContext
/*     */   implements WSTCPFastInfosetStreamReaderRecyclable.RecycleAwareListener
/*     */ {
/*  61 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ConnectionSession connectionSession;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ChannelSettings channelSettings;
/*     */ 
/*     */ 
/*     */   
/*     */   private Codec codec;
/*     */ 
/*     */ 
/*     */   
/*  78 */   private final ContentType contentType = new ContentType();
/*     */ 
/*     */   
/*     */   public ChannelContext(@NotNull ConnectionSession connectionSession, @NotNull ChannelSettings channelSettings) {
/*  82 */     this.connectionSession = connectionSession;
/*  83 */     this.channelSettings = channelSettings;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ConnectionSession getConnectionSession() {
/*  90 */     return this.connectionSession;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ChannelSettings getChannelSettings() {
/*  97 */     return this.channelSettings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Codec getCodec() {
/* 105 */     return this.codec;
/*     */   }
/*     */   
/*     */   private void setCodec(@NotNull Codec codec) {
/* 109 */     this.codec = codec;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Connection getConnection() {
/* 116 */     return this.connectionSession.getConnection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChannelId() {
/* 123 */     return this.channelSettings.getChannelId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public QName getWSServiceName() {
/* 130 */     return this.channelSettings.getWSServiceName();
/*     */   }
/*     */   
/*     */   public void setWSServiceName(@NotNull QName wsServiceName) {
/* 134 */     this.channelSettings.setWSServiceName(wsServiceName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSTCPURI getTargetWSURI() {
/* 141 */     return this.channelSettings.getTargetWSURI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentType(@NotNull String contentTypeS) throws WSTCPException {
/* 148 */     Connection connection = this.connectionSession.getConnection();
/* 149 */     if (logger.isLoggable(Level.FINEST)) {
/* 150 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1120_CHANNEL_CONTEXT_ENCODE_CT(contentTypeS));
/*     */     }
/* 152 */     this.contentType.parse(contentTypeS);
/*     */     
/* 154 */     int mt = encodeMimeType(this.contentType.getMimeType());
/*     */     
/* 156 */     connection.setContentId(mt);
/* 157 */     Map<String, String> parameters = this.contentType.getParameters();
/* 158 */     for (Map.Entry<String, String> parameter : parameters.entrySet()) {
/* 159 */       int paramId = encodeParam(parameter.getKey());
/* 160 */       connection.setContentProperty(paramId, parameter.getValue());
/*     */     } 
/*     */     
/* 163 */     if (logger.isLoggable(Level.FINEST)) {
/* 164 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1121_CHANNEL_CONTEXT_ENCODED_CT(Integer.valueOf(mt), parameters));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getContentType() throws WSTCPException {
/* 172 */     Connection connection = this.connectionSession.getConnection();
/* 173 */     int mimeId = connection.getContentId();
/* 174 */     Map<Integer, String> params = connection.getContentProperties();
/*     */     
/* 176 */     if (logger.isLoggable(Level.FINEST)) {
/* 177 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1122_CHANNEL_CONTEXT_DECODE_CT(Integer.valueOf(mimeId), params));
/*     */     }
/*     */     
/* 180 */     String mimeType = decodeMimeType(mimeId);
/*     */     
/* 182 */     String contentTypeStr = mimeType;
/* 183 */     if (params.size() > 0) {
/* 184 */       StringBuffer ctBuf = new StringBuffer(contentTypeStr);
/* 185 */       for (Map.Entry<Integer, String> parameter : params.entrySet()) {
/* 186 */         ctBuf.append(';');
/* 187 */         String paramKey = decodeParam(((Integer)parameter.getKey()).intValue());
/* 188 */         String paramValue = parameter.getValue();
/* 189 */         ctBuf.append(paramKey);
/* 190 */         ctBuf.append('=');
/* 191 */         ctBuf.append(paramValue);
/*     */       } 
/* 193 */       contentTypeStr = ctBuf.toString();
/*     */     } 
/*     */     
/* 196 */     if (logger.isLoggable(Level.FINEST)) {
/* 197 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1123_CHANNEL_CONTEXT_DECODED_CT(contentTypeStr));
/*     */     }
/* 199 */     return contentTypeStr;
/*     */   }
/*     */   
/*     */   public int encodeMimeType(@NotNull String mimeType) throws WSTCPException {
/* 203 */     int contentId = this.channelSettings.getNegotiatedMimeTypes().indexOf(mimeType);
/* 204 */     if (contentId != -1) {
/* 205 */       return contentId;
/*     */     }
/*     */     
/* 208 */     throw new WSTCPException(WSTCPError.createNonCriticalError(2, MessagesMessages.WSTCP_0011_UNKNOWN_CONTENT_TYPE(mimeType)));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String decodeMimeType(int contentId) throws WSTCPException {
/* 213 */     String mimeType = this.channelSettings.getNegotiatedMimeTypes().get(contentId);
/*     */     
/* 215 */     if (mimeType != null) {
/* 216 */       return mimeType;
/*     */     }
/* 218 */     throw new WSTCPException(WSTCPError.createNonCriticalError(2, MessagesMessages.WSTCP_0011_UNKNOWN_CONTENT_TYPE(Integer.valueOf(contentId))));
/*     */   }
/*     */ 
/*     */   
/*     */   public int encodeParam(@NotNull String paramStr) throws WSTCPException {
/* 223 */     int paramId = this.channelSettings.getNegotiatedParams().indexOf(paramStr);
/* 224 */     if (paramId != -1) {
/* 225 */       return paramId;
/*     */     }
/*     */     
/* 228 */     throw new WSTCPException(WSTCPError.createNonCriticalError(3, MessagesMessages.WSTCP_0010_UNKNOWN_PARAMETER(paramStr)));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String decodeParam(int paramId) throws WSTCPException {
/* 233 */     String paramStr = this.channelSettings.getNegotiatedParams().get(paramId);
/*     */     
/* 235 */     if (paramStr != null) {
/* 236 */       return paramStr;
/*     */     }
/* 238 */     throw new WSTCPException(WSTCPError.createNonCriticalError(3, MessagesMessages.WSTCP_0010_UNKNOWN_PARAMETER(Integer.valueOf(paramId))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void configureCodec(@NotNull ChannelContext channelContext, @NotNull SOAPVersion soapVersion, @NotNull Codec defaultCodec) {
/* 248 */     List<String> supportedMimeTypes = channelContext.getChannelSettings().getNegotiatedMimeTypes();
/* 249 */     if (supportedMimeTypes != null) {
/* 250 */       if (supportedMimeTypes.contains("application/vnd.sun.stateful.fastinfoset") || supportedMimeTypes.contains("application/vnd.sun.stateful.soap+fastinfoset")) {
/*     */         
/* 252 */         logger.log(Level.FINEST, "ChannelContext.configureCodec: FI Stateful");
/* 253 */         StreamSOAPCodec streamSoapCodec = (defaultCodec instanceof SOAPBindingCodec) ? ((SOAPBindingCodec)defaultCodec).getXMLCodec() : null;
/*     */         
/* 255 */         channelContext.setCodec((Codec)WSTCPFastInfosetStreamCodec.create(streamSoapCodec, soapVersion, channelContext, true)); return;
/*     */       } 
/* 257 */       if (supportedMimeTypes.contains("application/fastinfoset") || supportedMimeTypes.contains("application/soap+fastinfoset")) {
/*     */         
/* 259 */         logger.log(Level.FINEST, "ChannelContext.configureCodec: FI Stateless");
/* 260 */         StreamSOAPCodec streamSoapCodec = (defaultCodec instanceof SOAPBindingCodec) ? ((SOAPBindingCodec)defaultCodec).getXMLCodec() : null;
/*     */         
/* 262 */         channelContext.setCodec((Codec)WSTCPFastInfosetStreamCodec.create(streamSoapCodec, soapVersion, channelContext, false));
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 267 */     logger.log(Level.FINEST, "ChannelContext.configureCodec: default");
/* 268 */     channelContext.setCodec(defaultCodec);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 273 */     return String.format("ID: %d\nURI: %s\nCodec:%s", new Object[] { Integer.valueOf(getChannelId()), getTargetWSURI(), getCodec() });
/*     */   }
/*     */   
/*     */   public void onRecycled() {
/* 277 */     this.connectionSession.onReadCompleted();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\ChannelContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */