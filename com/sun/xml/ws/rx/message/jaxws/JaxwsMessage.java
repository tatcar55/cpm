/*     */ package com.sun.xml.ws.rx.message.jaxws;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.commons.xmlutil.Converter;
/*     */ import com.sun.xml.ws.rx.message.RxMessage;
/*     */ import com.sun.xml.ws.rx.message.RxMessageBase;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
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
/*     */ public class JaxwsMessage
/*     */   extends RxMessageBase
/*     */ {
/*     */   private final SerializableMessage jaxwsMessage;
/*     */   
/*     */   public static class JaxwsMessageState
/*     */     implements RxMessage.State
/*     */   {
/*     */     private final byte[] data;
/*     */     private final String wsaAction;
/*     */     private final String correlationId;
/*     */     
/*     */     private JaxwsMessageState(JaxwsMessage message) {
/*  65 */       this.data = message.toBytes();
/*  66 */       this.wsaAction = message.getWsaAction();
/*  67 */       this.correlationId = message.getCorrelationId();
/*     */     }
/*     */     
/*     */     public JaxwsMessage toMessage() {
/*  71 */       ByteArrayInputStream bais = new ByteArrayInputStream(this.data);
/*  72 */       return JaxwsMessage.newInstance(bais, this.correlationId, this.wsaAction);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  77 */       return "JaxwsMessageState{\n\twsaAction=" + this.wsaAction + ",\n\tcorrelationId=" + this.correlationId + ",\n\tmessage data=\n" + Converter.messageDataToString(this.data, "UTF-8") + "\n}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JaxwsMessage(@NotNull Packet packet, @NotNull String correlationId) {
/*  88 */     super(correlationId);
/*     */     
/*  90 */     assert packet != null;
/*  91 */     assert packet.getMessage() != null;
/*     */     
/*  93 */     this.jaxwsMessage = new SerializableMessage(packet, null);
/*     */   }
/*     */   
/*     */   private JaxwsMessage(@NotNull SerializableMessage jaxwsMessage, @NotNull String correlationId) {
/*  97 */     super(correlationId);
/*     */     
/*  99 */     this.jaxwsMessage = jaxwsMessage;
/*     */   }
/*     */   @NotNull
/*     */   public Message getJaxwsMessage() {
/* 103 */     return this.jaxwsMessage.getMessage();
/*     */   }
/*     */   @NotNull
/*     */   public Packet getPacket() {
/* 107 */     return this.jaxwsMessage.getPacket();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPacket(Packet newPacket) {
/* 112 */     this.jaxwsMessage.setPacket(newPacket);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] toBytes() {
/* 117 */     return this.jaxwsMessage.toBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWsaAction() {
/* 128 */     return this.jaxwsMessage.getWsaAction();
/*     */   }
/*     */   
/*     */   public JaxwsMessageState getState() {
/* 132 */     return new JaxwsMessageState(this);
/*     */   }
/*     */   
/*     */   public static JaxwsMessage newInstance(@NotNull InputStream dataStream, @NotNull String correlationId, @NotNull String wsaAction) {
/* 136 */     SerializableMessage jaxwsMessage = SerializableMessage.newInstance(dataStream, wsaAction);
/* 137 */     return new JaxwsMessage(jaxwsMessage, correlationId);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 142 */     StringBuilder sb = new StringBuilder("JAX-WS Message { ");
/* 143 */     sb.append("correlationId=[ ").append(getCorrelationId()).append(" ], ");
/* 144 */     sb.append("wsaAction=[ ").append(this.jaxwsMessage.getWsaAction());
/* 145 */     sb.append(" ] }");
/* 146 */     return super.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\message\jaxws\JaxwsMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */