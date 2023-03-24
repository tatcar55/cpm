/*    */ package com.sun.xml.ws.tx.at.v10.client;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.common.ParticipantIF;
/*    */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*    */ import com.sun.xml.ws.tx.at.common.client.ParticipantProxyBuilder;
/*    */ import com.sun.xml.ws.tx.at.v10.types.Notification;
/*    */ import com.sun.xml.ws.tx.at.v10.types.ParticipantPortType;
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
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
/*    */ public class ParticipantProxyBuilderImpl
/*    */   extends ParticipantProxyBuilder<Notification>
/*    */ {
/* 56 */   static final WSAT10Service service = new WSAT10Service();
/*    */   
/*    */   public ParticipantProxyBuilderImpl() {
/* 59 */     super(WSATVersion.v10);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticipantIF<Notification> build() {
/* 64 */     return new ParticipantProxyImpl();
/*    */   }
/*    */ 
/*    */   
/*    */   class ParticipantProxyImpl
/*    */     implements ParticipantIF<Notification>
/*    */   {
/* 71 */     ParticipantPortType port = ParticipantProxyBuilderImpl.service.getParticipantPortTypePort(ParticipantProxyBuilderImpl.this.to, ParticipantProxyBuilderImpl.this.getEnabledFeatures());
/*    */ 
/*    */ 
/*    */     
/*    */     public String toString() {
/* 76 */       return getClass().getName() + " hashcode:" + hashCode() + " to(EndpointReference):" + ParticipantProxyBuilderImpl.this.to + " port:" + this.port;
/*    */     }
/*    */     
/*    */     public void prepare(Notification parameters) {
/* 80 */       this.port.prepare(parameters);
/*    */     }
/*    */ 
/*    */     
/*    */     public void commit(Notification parameters) {
/* 85 */       this.port.commit(parameters);
/* 86 */       closePort();
/*    */     }
/*    */     
/*    */     public void rollback(Notification parameters) {
/* 90 */       this.port.rollback(parameters);
/* 91 */       closePort();
/*    */     }
/*    */     
/*    */     private void closePort() {
/*    */       try {
/* 96 */         ((Closeable)this.port).close();
/* 97 */       } catch (IOException e) {
/* 98 */         e.printStackTrace();
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v10\client\ParticipantProxyBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */