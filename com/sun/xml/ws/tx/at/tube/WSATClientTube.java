/*     */ package com.sun.xml.ws.tx.at.tube;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.tx.at.TransactionalFeature;
/*     */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.tx.at.WSATConstants;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class WSATClientTube
/*     */   extends AbstractFilterTubeImpl
/*     */   implements WSATConstants
/*     */ {
/*     */   WSBinding m_wsbinding;
/*  63 */   WSATClient m_wsatClientHelper = new WSATClientHelper();
/*     */   private TransactionalFeature m_transactionalFeature;
/*     */   private WSDLPort m_port;
/*     */   
/*     */   public WSATClientTube(Tube next, ClientTubelineAssemblyContext context, TransactionalFeature feature) {
/*  68 */     super(next);
/*  69 */     this.m_wsbinding = context.getBinding();
/*  70 */     this.m_transactionalFeature = feature;
/*  71 */     this.m_port = context.getWsdlPort();
/*     */   }
/*     */   
/*     */   private WSATClientTube(WSATClientTube that, TubeCloner cloner) {
/*  75 */     super(that, cloner);
/*  76 */     this.m_wsbinding = that.m_wsbinding;
/*  77 */     this.m_transactionalFeature = that.m_transactionalFeature;
/*  78 */     this.m_port = that.m_port;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<QName> getHeaders() {
/*  83 */     return new HashSet<QName>();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public NextAction processRequest(@NotNull Packet request) {
/*     */     try {
/*  89 */       doProcessRequest(request);
/*  90 */     } catch (Exception e) {
/*  91 */       e.printStackTrace();
/*     */     } 
/*  93 */     return super.processRequest(request);
/*     */   }
/*     */   
/*     */   private void doProcessRequest(Packet request) {
/*  97 */     TransactionalAttribute transactionalAttribute = WSATTubeHelper.getTransactionalAttribute(this.m_transactionalFeature, request, this.m_port);
/*     */     
/*  99 */     transactionalAttribute.setSoapVersion(this.m_wsbinding.getSOAPVersion());
/* 100 */     List<Header> addedHeaders = this.m_wsatClientHelper.doHandleRequest(transactionalAttribute, request.invocationProperties);
/* 101 */     if (addedHeaders != null) {
/* 102 */       for (Header header : addedHeaders) {
/* 103 */         request.getMessage().getHeaders().add(header);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processResponse(@NotNull Packet response) {
/* 111 */     this.m_wsatClientHelper.doHandleResponse(response.invocationProperties);
/* 112 */     return super.processResponse(response);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processException(Throwable t) {
/* 118 */     Map<String, Object> map = (Fiber.current().getPacket()).invocationProperties;
/* 119 */     this.m_wsatClientHelper.doHandleResponse(map);
/* 120 */     return super.processException(t);
/*     */   }
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 124 */     return (AbstractTubeImpl)new WSATClientTube(this, cloner);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\tube\WSATClientTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */