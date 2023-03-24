/*     */ package com.sun.xml.ws.rx.testing;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
/*     */ import com.sun.xml.ws.rx.rm.runtime.JaxwsApplicationMessage;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RmRuntimeVersion;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RuntimeContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PacketFilter
/*     */ {
/*     */   protected static final long UNSPECIFIED = -1L;
/*  56 */   private static final Logger LOGGER = Logger.getLogger(PacketFilter.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RuntimeContext rc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Packet filterClientRequest(Packet paramPacket) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Packet filterServerResponse(Packet paramPacket) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String getSequenceId(Packet packet) {
/*     */     try {
/*  97 */       if (notInitialized(packet) || isRmProtocolMessage(packet)) {
/*  98 */         return null;
/*     */       }
/*     */       
/* 101 */       JaxwsApplicationMessage message = new JaxwsApplicationMessage(packet, packet.getMessage().getID(this.rc.addressingVersion, this.rc.soapVersion));
/* 102 */       this.rc.protocolHandler.loadSequenceHeaderData((ApplicationMessage)message, message.getJaxwsMessage());
/* 103 */       return message.getSequenceId();
/* 104 */     } catch (Exception ex) {
/* 105 */       LOGGER.warning("Unexpected exception occured", ex);
/* 106 */       return null;
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
/*     */   protected final long getMessageId(Packet packet) {
/*     */     try {
/* 120 */       if (notInitialized(packet) || isRmProtocolMessage(packet)) {
/* 121 */         return -1L;
/*     */       }
/*     */       
/* 124 */       JaxwsApplicationMessage message = new JaxwsApplicationMessage(packet, packet.getMessage().getID(this.rc.addressingVersion, this.rc.soapVersion));
/* 125 */       this.rc.protocolHandler.loadSequenceHeaderData((ApplicationMessage)message, message.getJaxwsMessage());
/* 126 */       return message.getMessageNumber();
/* 127 */     } catch (Exception ex) {
/* 128 */       LOGGER.warning("Unexpected exception occured", ex);
/* 129 */       return -1L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final RmRuntimeVersion getRmVersion() {
/* 140 */     return this.rc.rmVersion;
/*     */   }
/*     */   
/*     */   protected final boolean isRmProtocolMessage(Packet packet) {
/* 144 */     return this.rc.rmVersion.protocolVersion.isProtocolAction(this.rc.communicator.getWsaAction(packet));
/*     */   }
/*     */   
/*     */   final void configure(RuntimeContext context) {
/* 148 */     this.rc = context;
/*     */   }
/*     */   
/*     */   private boolean notInitialized(Packet packet) {
/* 152 */     return (this.rc == null || packet == null || packet.getMessage() == null);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\testing\PacketFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */