/*     */ package com.sun.xml.ws.transport.tcp.server;
/*     */ 
/*     */ import com.oracle.webservices.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.api.message.PropertySet;
/*     */ import com.oracle.webservices.api.message.PropertySet.Property;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.DistributedPropertySet;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.server.Adapter;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPException;
/*     */ import java.io.IOException;
/*     */ import javax.xml.soap.SOAPException;
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
/*     */ public final class TCPServiceChannelWSAdapter
/*     */   extends TCPAdapter
/*     */ {
/*     */   private final WSTCPAdapterRegistry adapterRegistry;
/*     */   
/*     */   public TCPServiceChannelWSAdapter(@NotNull String name, @NotNull String urlPattern, @NotNull WSEndpoint endpoint, @NotNull WSTCPAdapterRegistry adapterRegistry) {
/*  66 */     super(name, urlPattern, endpoint);
/*  67 */     this.adapterRegistry = adapterRegistry;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TCPAdapter.TCPToolkit createToolkit() {
/*  72 */     return new ServiceChannelTCPToolkit();
/*     */   }
/*     */   
/*     */   class ServiceChannelTCPToolkit extends TCPAdapter.TCPToolkit {
/*     */     private final TCPServiceChannelWSAdapter.ServiceChannelWSSatellite serviceChannelWSSatellite;
/*     */     
/*     */     public ServiceChannelTCPToolkit() {
/*  79 */       this.serviceChannelWSSatellite = new TCPServiceChannelWSAdapter.ServiceChannelWSSatellite(TCPServiceChannelWSAdapter.this);
/*     */     }
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     protected Codec getCodec(@NotNull ChannelContext context) {
/*  85 */       return this.codec;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void handle(@NotNull TCPConnectionImpl con) throws IOException, WSTCPException {
/*  90 */       this.serviceChannelWSSatellite.setConnectionContext(con.getChannelContext());
/*  91 */       super.handle(con);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addCustomPacketSattellites(@NotNull Packet packet) {
/*  96 */       super.addCustomPacketSattellites(packet);
/*  97 */       packet.addSatellite((PropertySet)this.serviceChannelWSSatellite);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class ServiceChannelWSSatellite
/*     */     extends DistributedPropertySet {
/*     */     private final TCPServiceChannelWSAdapter serviceChannelWSAdapter;
/*     */     private ChannelContext channelContext;
/*     */     
/*     */     ServiceChannelWSSatellite(@NotNull TCPServiceChannelWSAdapter serviceChannelWSAdapter) {
/* 107 */       this.serviceChannelWSAdapter = serviceChannelWSAdapter;
/*     */     }
/*     */     
/*     */     protected void setConnectionContext(ChannelContext channelContext) {
/* 111 */       this.channelContext = channelContext;
/*     */     }
/*     */     @Property({"AdapterRegistry"})
/*     */     @NotNull
/*     */     public WSTCPAdapterRegistry getAdapterRegistry() {
/* 116 */       return this.serviceChannelWSAdapter.adapterRegistry;
/*     */     }
/*     */     
/*     */     @Property({"channelContext"})
/*     */     public ChannelContext getChannelContext() {
/* 121 */       return this.channelContext;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 126 */     private static final BasePropertySet.PropertyMap model = parse(ServiceChannelWSSatellite.class);
/*     */ 
/*     */     
/*     */     public BasePropertySet.PropertyMap getPropertyMap() {
/* 130 */       return model;
/*     */     }
/*     */ 
/*     */     
/*     */     public SOAPMessage getSOAPMessage() throws SOAPException {
/* 135 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setSOAPMessage(SOAPMessage soap) {
/* 139 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\TCPServiceChannelWSAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */