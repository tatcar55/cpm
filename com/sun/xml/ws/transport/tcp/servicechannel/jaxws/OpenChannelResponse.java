/*     */ package com.sun.xml.ws.transport.tcp.servicechannel.jaxws;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "openChannelResponse", namespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "openChannelResponse", namespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", propOrder = {"channelId", "negotiatedMimeTypes", "negotiatedParams"})
/*     */ public class OpenChannelResponse
/*     */ {
/*     */   @XmlElement(name = "channelId", namespace = "")
/*     */   private int channelId;
/*     */   @XmlElement(name = "negotiatedMimeTypes", namespace = "", required = true)
/*     */   private List<String> negotiatedMimeTypes;
/*     */   @XmlElement(name = "negotiatedParams", namespace = "")
/*     */   private List<String> negotiatedParams;
/*     */   
/*     */   public int getChannelId() {
/*  72 */     return this.channelId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChannelId(int channelId) {
/*  81 */     this.channelId = channelId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getNegotiatedMimeTypes() {
/*  90 */     return this.negotiatedMimeTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNegotiatedMimeTypes(List<String> negotiatedMimeTypes) {
/*  99 */     this.negotiatedMimeTypes = negotiatedMimeTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getNegotiatedParams() {
/* 108 */     return this.negotiatedParams;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNegotiatedParams(List<String> negotiatedParams) {
/* 117 */     this.negotiatedParams = negotiatedParams;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\servicechannel\jaxws\OpenChannelResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */