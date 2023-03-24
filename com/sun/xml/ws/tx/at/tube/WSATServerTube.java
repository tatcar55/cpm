/*     */ package com.sun.xml.ws.tx.at.tube;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.tx.at.TransactionalFeature;
/*     */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.tx.at.WSATConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSATServerTube
/*     */   extends AbstractFilterTubeImpl
/*     */   implements WSATConstants
/*     */ {
/*     */   private static final String WSATATTRIBUTE = ".wsee.wsat.attribute";
/*     */   ServerTubelineAssemblyContext m_context;
/*     */   private WSDLPort m_port;
/*     */   private TransactionalFeature m_transactionalFeature;
/*  99 */   WSATServer m_wsatServerHelper = new WSATServerHelper();
/*     */   
/*     */   public WSATServerTube(Tube next, ServerTubelineAssemblyContext context, TransactionalFeature feature) {
/* 102 */     super(next);
/* 103 */     this.m_context = context;
/* 104 */     this.m_port = context.getWsdlPort();
/* 105 */     this.m_transactionalFeature = feature;
/*     */   }
/*     */   
/*     */   public WSATServerTube(WSATServerTube that, TubeCloner cloner) {
/* 109 */     super(that, cloner);
/* 110 */     this.m_context = that.m_context;
/* 111 */     this.m_port = that.m_port;
/* 112 */     this.m_transactionalFeature = that.m_transactionalFeature;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processRequest(Packet request) {
/* 119 */     TransactionalAttribute tx = WSATTubeHelper.getTransactionalAttribute(this.m_transactionalFeature, request, this.m_port);
/* 120 */     tx.setSoapVersion(this.m_context.getEndpoint().getBinding().getSOAPVersion());
/* 121 */     request.invocationProperties.put(".wsee.wsat.attribute", tx);
/* 122 */     HeaderList headers = request.getMessage().getHeaders();
/* 123 */     this.m_wsatServerHelper.doHandleRequest(headers, tx);
/* 124 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processResponse(Packet response) {
/* 130 */     TransactionalAttribute tx = (TransactionalAttribute)response.invocationProperties.get(".wsee.wsat.attribute");
/* 131 */     this.m_wsatServerHelper.doHandleResponse(tx);
/* 132 */     return super.processResponse(response);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processException(Throwable t) {
/* 138 */     this.m_wsatServerHelper.doHandleException(t);
/* 139 */     return super.processException(t);
/*     */   }
/*     */   
/*     */   public void preDestroy() {
/* 143 */     super.preDestroy();
/*     */   }
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 147 */     return (AbstractTubeImpl)new WSATServerTube(this, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   NextAction doProcessResponse(Packet request) {
/* 152 */     return super.processResponse(request);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\tube\WSATServerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */