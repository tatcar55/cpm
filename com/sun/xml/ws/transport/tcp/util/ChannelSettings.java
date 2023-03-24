/*     */ package com.sun.xml.ws.transport.tcp.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ChannelSettings
/*     */ {
/*     */   private List<String> negotiatedMimeTypes;
/*     */   private List<String> negotiatedParams;
/*     */   private int channelId;
/*     */   private QName wsServiceName;
/*     */   private WSTCPURI targetWSURI;
/*     */   
/*     */   public ChannelSettings() {}
/*     */   
/*     */   public ChannelSettings(@NotNull List<String> negotiatedMimeTypes, @NotNull List<String> negotiatedParams, int channelId, QName wsServiceName, WSTCPURI targetWSURI) {
/*  70 */     this.negotiatedMimeTypes = negotiatedMimeTypes;
/*  71 */     this.negotiatedParams = negotiatedParams;
/*  72 */     this.channelId = channelId;
/*  73 */     this.wsServiceName = wsServiceName;
/*  74 */     this.targetWSURI = targetWSURI;
/*     */   }
/*     */   @NotNull
/*     */   public List<String> getNegotiatedMimeTypes() {
/*  78 */     return this.negotiatedMimeTypes;
/*     */   }
/*     */   
/*     */   public void setNegotiatedMimeTypes(@NotNull List<String> negotiatedMimeTypes) {
/*  82 */     this.negotiatedMimeTypes = negotiatedMimeTypes;
/*     */   }
/*     */   @NotNull
/*     */   public List<String> getNegotiatedParams() {
/*  86 */     return this.negotiatedParams;
/*     */   }
/*     */   
/*     */   public void setNegotiatedParams(@NotNull List<String> negotiatedParams) {
/*  90 */     this.negotiatedParams = negotiatedParams;
/*     */   }
/*     */   @NotNull
/*     */   public WSTCPURI getTargetWSURI() {
/*  94 */     return this.targetWSURI;
/*     */   }
/*     */   
/*     */   public void setTargetWSURI(@NotNull WSTCPURI targetWSURI) {
/*  98 */     this.targetWSURI = targetWSURI;
/*     */   }
/*     */   
/*     */   public int getChannelId() {
/* 102 */     return this.channelId;
/*     */   }
/*     */   
/*     */   public void setChannelId(int channelId) {
/* 106 */     this.channelId = channelId;
/*     */   }
/*     */   @NotNull
/*     */   public QName getWSServiceName() {
/* 110 */     return this.wsServiceName;
/*     */   }
/*     */   
/*     */   public void setWSServiceName(@NotNull QName wsServiceName) {
/* 114 */     this.wsServiceName = wsServiceName;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 118 */     StringBuffer sb = new StringBuffer(200);
/* 119 */     sb.append("TargetURI: ");
/* 120 */     sb.append(this.targetWSURI);
/* 121 */     sb.append(" wsServiceName: ");
/* 122 */     sb.append(this.wsServiceName);
/* 123 */     sb.append(" channelId: ");
/* 124 */     sb.append(this.channelId);
/* 125 */     sb.append(" negotiatedParams: ");
/* 126 */     sb.append(this.negotiatedParams);
/* 127 */     sb.append(" negotiatedMimeTypes: ");
/* 128 */     sb.append(this.negotiatedMimeTypes);
/*     */     
/* 130 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\ChannelSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */