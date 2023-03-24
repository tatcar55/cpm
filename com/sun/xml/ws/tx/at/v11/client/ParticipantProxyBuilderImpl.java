/*    */ package com.sun.xml.ws.tx.at.v11.client;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.common.ParticipantIF;
/*    */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*    */ import com.sun.xml.ws.tx.at.common.client.ParticipantProxyBuilder;
/*    */ import com.sun.xml.ws.tx.at.v11.types.Notification;
/*    */ import com.sun.xml.ws.tx.at.v11.types.ParticipantPortType;
/*    */ import javax.xml.ws.EndpointReference;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticipantProxyBuilderImpl
/*    */   extends ParticipantProxyBuilder<Notification>
/*    */ {
/*    */   public ParticipantProxyBuilderImpl() {
/* 57 */     super(WSATVersion.v11);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticipantIF<Notification> build() {
/* 62 */     return new ParticipantProxyImpl();
/*    */   }
/*    */   
/*    */   class ParticipantProxyImpl implements ParticipantIF<Notification> {
/*    */     ParticipantPortType port;
/* 67 */     WSAT11Service service = new WSAT11Service();
/*    */     
/*    */     ParticipantProxyImpl() {
/* 70 */       this.port = this.service.getParticipantPort(ParticipantProxyBuilderImpl.this.to, ParticipantProxyBuilderImpl.this.getEnabledFeatures());
/*    */     }
/*    */     
/*    */     public void prepare(Notification parameters) {
/* 74 */       this.port.prepareOperation(parameters);
/*    */     }
/*    */     
/*    */     public void commit(Notification parameters) {
/* 78 */       this.port.commitOperation(parameters);
/*    */     }
/*    */     
/*    */     public void rollback(Notification parameters) {
/* 82 */       this.port.rollbackOperation(parameters);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v11\client\ParticipantProxyBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */