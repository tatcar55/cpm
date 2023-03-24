/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketMapMessageInfo
/*     */   implements PacketMessageInfo
/*     */ {
/*     */   private SOAPAuthParam soapAuthParam;
/*     */   private Map<Object, Object> infoMap;
/*     */   
/*     */   public PacketMapMessageInfo(Packet reqPacket, Packet resPacket) {
/*  63 */     this.soapAuthParam = new SOAPAuthParam(reqPacket, resPacket, 0);
/*     */   }
/*     */   
/*     */   public Map getMap() {
/*  67 */     if (this.infoMap == null) {
/*  68 */       this.infoMap = this.soapAuthParam.getMap();
/*     */     }
/*  70 */     return this.infoMap;
/*     */   }
/*     */   
/*     */   public Object getRequestMessage() {
/*  74 */     return this.soapAuthParam.getRequest();
/*     */   }
/*     */   
/*     */   public Object getResponseMessage() {
/*  78 */     return this.soapAuthParam.getResponse();
/*     */   }
/*     */   
/*     */   public void setRequestMessage(Object request) {
/*  82 */     this.soapAuthParam.setRequest((SOAPMessage)request);
/*     */   }
/*     */   
/*     */   public void setResponseMessage(Object response) {
/*  86 */     this.soapAuthParam.setResponse((SOAPMessage)response);
/*     */   }
/*     */   
/*     */   public SOAPAuthParam getSOAPAuthParam() {
/*  90 */     return this.soapAuthParam;
/*     */   }
/*     */   
/*     */   public Packet getRequestPacket() {
/*  94 */     return (Packet)this.soapAuthParam.getRequestPacket();
/*     */   }
/*     */   
/*     */   public Packet getResponsePacket() {
/*  98 */     return (Packet)this.soapAuthParam.getResponsePacket();
/*     */   }
/*     */   
/*     */   public void setRequestPacket(Packet p) {
/* 102 */     this.soapAuthParam.setRequestPacket(p);
/*     */   }
/*     */   
/*     */   public void setResponsePacket(Packet p) {
/* 106 */     this.soapAuthParam.setResponsePacket(p);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\PacketMapMessageInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */