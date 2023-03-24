/*    */ package com.sun.xml.ws.tx.at.v11.client;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.common.CoordinatorIF;
/*    */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*    */ import com.sun.xml.ws.tx.at.common.client.CoordinatorProxyBuilder;
/*    */ import com.sun.xml.ws.tx.at.v11.types.CoordinatorPortType;
/*    */ import com.sun.xml.ws.tx.at.v11.types.Notification;
/*    */ import javax.xml.ws.EndpointReference;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CoordinatorProxyBuilderImpl
/*    */   extends CoordinatorProxyBuilder<Notification>
/*    */ {
/*    */   public CoordinatorProxyBuilderImpl() {
/* 61 */     super(WSATVersion.v11);
/*    */   }
/*    */ 
/*    */   
/*    */   public CoordinatorIF<Notification> build() {
/* 66 */     return new CoordinatorProxyImpl();
/*    */   }
/*    */   
/*    */   class CoordinatorProxyImpl
/*    */     implements CoordinatorIF<Notification> {
/*    */     CoordinatorPortType port;
/* 72 */     WSAT11Service service = new WSAT11Service();
/*    */     
/*    */     CoordinatorProxyImpl() {
/* 75 */       this.port = this.service.getCoordinatorPort(CoordinatorProxyBuilderImpl.this.to, CoordinatorProxyBuilderImpl.this.getEnabledFeatures());
/*    */     }
/*    */     
/*    */     public void preparedOperation(Notification parameters) {
/* 79 */       this.port.preparedOperation(parameters);
/*    */     }
/*    */     
/*    */     public void abortedOperation(Notification parameters) {
/* 83 */       this.port.abortedOperation(parameters);
/*    */     }
/*    */     
/*    */     public void readOnlyOperation(Notification parameters) {
/* 87 */       this.port.readOnlyOperation(parameters);
/*    */     }
/*    */     
/*    */     public void committedOperation(Notification parameters) {
/* 91 */       this.port.committedOperation(parameters);
/*    */     }
/*    */     
/*    */     public void replayOperation(Notification parameters) {
/* 95 */       throw new WebServiceException("replayOperation is not supported by WS-AT 1.1 and 1.2");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v11\client\CoordinatorProxyBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */