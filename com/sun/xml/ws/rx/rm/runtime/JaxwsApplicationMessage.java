/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.commons.xmlutil.Converter;
/*     */ import com.sun.xml.ws.rx.message.RxMessage;
/*     */ import com.sun.xml.ws.rx.message.jaxws.SerializableMessage;
/*     */ import com.sun.xml.ws.rx.rm.protocol.AcknowledgementData;
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
/*     */ public class JaxwsApplicationMessage
/*     */   extends ApplicationMessageBase
/*     */ {
/*     */   private final SerializableMessage jaxwsMessage;
/*     */   
/*     */   public static class JaxwsApplicationMessageState
/*     */     implements RxMessage.State
/*     */   {
/*     */     private final String sequenceId;
/*     */     private final long messageNumber;
/*     */     private final int nextResendCount;
/*     */     private final String correlationId;
/*     */     private final String wsaAction;
/*     */     private final byte[] data;
/*     */     
/*     */     private JaxwsApplicationMessageState(JaxwsApplicationMessage message) {
/*  68 */       this.data = message.toBytes();
/*  69 */       this.nextResendCount = message.getNextResendCount();
/*  70 */       this.correlationId = message.getCorrelationId();
/*  71 */       this.wsaAction = message.getWsaAction();
/*  72 */       this.sequenceId = message.getSequenceId();
/*  73 */       this.messageNumber = message.getMessageNumber();
/*     */     }
/*     */     
/*     */     public JaxwsApplicationMessage toMessage() {
/*  77 */       ByteArrayInputStream bais = new ByteArrayInputStream(this.data);
/*  78 */       return JaxwsApplicationMessage.newInstance(bais, this.nextResendCount, this.correlationId, this.wsaAction, this.sequenceId, this.messageNumber);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*  84 */       return "JaxwsApplicationMessageState{\n\tsequenceId=" + this.sequenceId + ",\n\tmessageNumber=" + this.messageNumber + ",\n\tnextResendCount=" + this.nextResendCount + ",\n\tcorrelationId=" + this.correlationId + ",\n\twsaAction=" + this.wsaAction + ",\n\tmessage data=\n" + Converter.messageDataToString(this.data, "UTF-8") + "\n}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JaxwsApplicationMessage(@NotNull Packet packet, @NotNull String correlationId) {
/*  98 */     super(correlationId);
/*     */     
/* 100 */     assert packet != null;
/* 101 */     assert packet.getMessage() != null;
/*     */     
/* 103 */     this.jaxwsMessage = new SerializableMessage(packet, null);
/*     */   }
/*     */   
/*     */   private JaxwsApplicationMessage(@NotNull SerializableMessage jaxwsMessage, int initialResendCounterValue, @NotNull String correlationId, @NotNull String sequenceId, long messageNumber) {
/* 107 */     super(initialResendCounterValue, correlationId, sequenceId, messageNumber, (AcknowledgementData)null);
/*     */     
/* 109 */     this.jaxwsMessage = jaxwsMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Message getJaxwsMessage() {
/* 115 */     return this.jaxwsMessage.getMessage();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Packet getPacket() {
/* 121 */     return this.jaxwsMessage.getPacket();
/*     */   }
/*     */ 
/*     */   
/*     */   void setPacket(Packet newPacket) {
/* 126 */     this.jaxwsMessage.setPacket(newPacket);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] toBytes() {
/* 131 */     return this.jaxwsMessage.toBytes();
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
/* 142 */     return this.jaxwsMessage.getWsaAction();
/*     */   }
/*     */   
/*     */   public JaxwsApplicationMessageState getState() {
/* 146 */     return new JaxwsApplicationMessageState(this);
/*     */   }
/*     */   
/*     */   public static JaxwsApplicationMessage newInstance(@NotNull InputStream dataStream, int initialResendCounterValue, @NotNull String correlationId, @NotNull String wsaAction, @NotNull String sequenceId, long messageNumber) {
/* 150 */     SerializableMessage jaxwsMessage = SerializableMessage.newInstance(dataStream, wsaAction);
/* 151 */     return new JaxwsApplicationMessage(jaxwsMessage, initialResendCounterValue, correlationId, sequenceId, messageNumber);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 156 */     StringBuilder sb = new StringBuilder("JAX-WS Application Message { ");
/* 157 */     sb.append("sequenceId=[ ").append(getSequenceId()).append(" ], ");
/* 158 */     sb.append("messageNumber=[ ").append(getMessageNumber()).append(" ], ");
/* 159 */     sb.append("correlationId=[ ").append(getCorrelationId()).append(" ], ");
/* 160 */     sb.append("nextResendCount=[ ").append(getNextResendCount()).append(" ], ");
/* 161 */     sb.append("wsaAction=[ ").append(this.jaxwsMessage.getWsaAction());
/* 162 */     sb.append(" ] }");
/* 163 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\JaxwsApplicationMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */