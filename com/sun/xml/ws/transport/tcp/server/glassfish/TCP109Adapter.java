/*     */ package com.sun.xml.ws.transport.tcp.server.glassfish;
/*     */ 
/*     */ import com.oracle.webservices.api.message.PropertySet;
/*     */ import com.sun.enterprise.webservice.EjbRuntimeEndpointInfo;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.server.Adapter;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.transport.tcp.server.TCPAdapter;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPException;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TCP109Adapter
/*     */   extends TCPAdapter
/*     */ {
/*     */   private final ServletFakeArtifactSet servletFakeArtifactSet;
/*     */   private final boolean isEJB;
/*     */   
/*     */   public TCP109Adapter(@NotNull String name, @NotNull String urlPattern, @NotNull WSEndpoint endpoint, @NotNull ServletFakeArtifactSet servletFakeArtifactSet, boolean isEJB) {
/*  70 */     super(name, urlPattern, endpoint);
/*  71 */     this.servletFakeArtifactSet = servletFakeArtifactSet;
/*  72 */     this.isEJB = isEJB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@NotNull ChannelContext channelContext) throws IOException, WSTCPException {
/*  78 */     EjbRuntimeEndpointInfo ejbRuntimeEndpointInfo = null;
/*     */     
/*  80 */     if (this.isEJB) {
/*  81 */       ejbRuntimeEndpointInfo = AppServWSRegistry.getInstance().getEjbRuntimeEndpointInfo(getValidPath());
/*     */       
/*     */       try {
/*  84 */         ejbRuntimeEndpointInfo.prepareInvocation(true);
/*  85 */       } catch (Exception e) {
/*  86 */         throw new IOException(e.getClass().getName());
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/*  91 */       super.handle(channelContext);
/*     */     } finally {
/*  93 */       if (this.isEJB && ejbRuntimeEndpointInfo != null) {
/*  94 */         ejbRuntimeEndpointInfo.releaseImplementor();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected TCPAdapter.TCPToolkit createToolkit() {
/* 101 */     return new TCP109Toolkit();
/*     */   }
/*     */   final class TCP109Toolkit extends TCPAdapter.TCPToolkit { TCP109Toolkit() {
/* 104 */       super(TCP109Adapter.this);
/*     */     }
/*     */     
/*     */     public void addCustomPacketSattellites(@NotNull Packet packet) {
/* 108 */       super.addCustomPacketSattellites(packet);
/* 109 */       packet.addSatellite((PropertySet)TCP109Adapter.this.servletFakeArtifactSet);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\glassfish\TCP109Adapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */