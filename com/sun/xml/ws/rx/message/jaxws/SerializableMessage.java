/*     */ package com.sun.xml.ws.rx.message.jaxws;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.commons.xmlutil.Converter;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.localization.LocalizationMessages;
/*     */ import java.io.InputStream;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ public final class SerializableMessage
/*     */ {
/*  59 */   private static final Logger LOGGER = Logger.getLogger(SerializableMessage.class);
/*     */   
/*     */   @Nullable
/*     */   private Packet packet;
/*     */ 
/*     */   
/*     */   public SerializableMessage(Packet packet, String wsaAction) {
/*  66 */     this.packet = packet;
/*  67 */     this.message = packet.getMessage();
/*  68 */     this.wsaAction = wsaAction;
/*     */   } @NotNull
/*     */   private final Message message; @Nullable
/*     */   private final String wsaAction; public SerializableMessage(Message message, String wsaAction) {
/*  72 */     assert message != null;
/*     */     
/*  74 */     this.packet = null;
/*  75 */     this.message = message;
/*  76 */     this.wsaAction = wsaAction;
/*     */   }
/*     */   
/*     */   public Message getMessage() {
/*  80 */     return this.message;
/*     */   }
/*     */   
/*     */   public Packet getPacket() {
/*  84 */     return this.packet;
/*     */   }
/*     */   
/*     */   public void setPacket(Packet newPacket) {
/*  88 */     newPacket.setMessage(this.message);
/*  89 */     this.packet = newPacket;
/*     */   }
/*     */   
/*     */   public String getWsaAction() {
/*  93 */     return this.wsaAction;
/*     */   }
/*     */   
/*     */   public byte[] toBytes() {
/*     */     try {
/*  98 */       return Converter.toBytes(this.message.copy(), "UTF-8");
/*  99 */     } catch (XMLStreamException ex) {
/* 100 */       throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRX_1001_UNABLE_TO_SERIALIZE_MSG_TO_XML_STREAM(), ex));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static SerializableMessage newInstance(@NotNull InputStream dataStream, String wsaAction) {
/*     */     Message m;
/*     */     try {
/* 107 */       m = Converter.toMessage(dataStream, "UTF-8");
/* 108 */     } catch (XMLStreamException ex) {
/* 109 */       throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRX_1002_UNABLE_TO_DESERIALIZE_MSG_FROM_XML_STREAM(), ex));
/*     */     } 
/* 111 */     return new SerializableMessage(m, wsaAction);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\message\jaxws\SerializableMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */