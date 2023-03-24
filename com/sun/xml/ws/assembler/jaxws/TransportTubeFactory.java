/*     */ package com.sun.xml.ws.assembler.jaxws;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.transport.tcp.SelectOptimalTransportFeature;
/*     */ import com.sun.xml.ws.api.transport.tcp.TcpTransportFeature;
/*     */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.TubeFactory;
/*     */ import com.sun.xml.ws.transport.tcp.wsit.TCPTransportPipeFactory;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TransportTubeFactory
/*     */   implements TubeFactory
/*     */ {
/*     */   public Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException {
/*  65 */     if (isOptimizedTransportEnabled(context.getWsdlPort(), context.getPortInfo(), context.getBinding())) {
/*  66 */       return TCPTransportPipeFactory.doCreate(context.getWrappedContext(), false);
/*     */     }
/*  68 */     return context.getWrappedContext().createTransportTube();
/*     */   }
/*     */ 
/*     */   
/*     */   public Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException {
/*  73 */     return context.getTubelineHead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isOptimizedTransportEnabled(WSDLPort port, WSPortInfo portInfo, WSBinding binding) {
/*     */     String schema;
/*  84 */     if (port == null && portInfo == null) {
/*  85 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  89 */     if (port != null) {
/*  90 */       schema = port.getAddress().getURI().getScheme();
/*     */     } else {
/*  92 */       schema = portInfo.getEndpointAddress().getURI().getScheme();
/*     */     } 
/*     */     
/*  95 */     if ("vnd.sun.ws.tcp".equals(schema))
/*     */     {
/*  97 */       return true; } 
/*  98 */     if (binding == null) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     TcpTransportFeature tcpTransportFeature = (TcpTransportFeature)binding.getFeature(TcpTransportFeature.class);
/* 103 */     SelectOptimalTransportFeature optimalTransportFeature = (SelectOptimalTransportFeature)binding.getFeature(SelectOptimalTransportFeature.class);
/*     */     
/* 105 */     return (tcpTransportFeature != null && tcpTransportFeature.isEnabled() && optimalTransportFeature != null && optimalTransportFeature.isEnabled());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\jaxws\TransportTubeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */