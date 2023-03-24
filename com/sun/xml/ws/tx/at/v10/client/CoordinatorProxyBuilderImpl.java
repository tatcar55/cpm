/*     */ package com.sun.xml.ws.tx.at.v10.client;
/*     */ 
/*     */ import com.sun.xml.ws.tx.at.common.CoordinatorIF;
/*     */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*     */ import com.sun.xml.ws.tx.at.common.client.CoordinatorProxyBuilder;
/*     */ import com.sun.xml.ws.tx.at.v10.types.CoordinatorPortType;
/*     */ import com.sun.xml.ws.tx.at.v10.types.Notification;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoordinatorProxyBuilderImpl
/*     */   extends CoordinatorProxyBuilder<Notification>
/*     */ {
/*  60 */   private static final WSAT10Service service = new WSAT10Service();
/*     */   
/*     */   public CoordinatorProxyBuilderImpl() {
/*  63 */     super(WSATVersion.v10);
/*     */   }
/*     */ 
/*     */   
/*     */   public CoordinatorIF<Notification> build() {
/*  68 */     return new CoordinatorProxyImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class CoordinatorProxyImpl
/*     */     implements CoordinatorIF<Notification>
/*     */   {
/*  76 */     CoordinatorPortType port = CoordinatorProxyBuilderImpl.service.getCoordinatorPortTypePort(CoordinatorProxyBuilderImpl.this.to, CoordinatorProxyBuilderImpl.this.getEnabledFeatures());
/*     */ 
/*     */     
/*     */     public void preparedOperation(Notification parameters) {
/*  80 */       this.port.preparedOperation(parameters);
/*  81 */       closePort();
/*     */     }
/*     */     
/*     */     public void abortedOperation(Notification parameters) {
/*  85 */       this.port.abortedOperation(parameters);
/*  86 */       closePort();
/*     */     }
/*     */     
/*     */     public void readOnlyOperation(Notification parameters) {
/*  90 */       this.port.readOnlyOperation(parameters);
/*  91 */       closePort();
/*     */     }
/*     */     
/*     */     public void committedOperation(Notification parameters) {
/*  95 */       this.port.committedOperation(parameters);
/*  96 */       closePort();
/*     */     }
/*     */     
/*     */     public void replayOperation(Notification parameters) {
/* 100 */       this.port.replayOperation(parameters);
/* 101 */       closePort();
/*     */     }
/*     */     
/*     */     private void closePort() {
/*     */       try {
/* 106 */         ((Closeable)this.port).close();
/* 107 */       } catch (IOException e) {
/* 108 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v10\client\CoordinatorProxyBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */