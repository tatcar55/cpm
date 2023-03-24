/*    */ package com.sun.xml.ws.tx.at.tube;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.ws.api.tx.at.Transactional;
/*    */ import com.sun.xml.ws.api.tx.at.TransactionalFeature;
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
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
/*    */ public class WSATTubeHelper
/*    */ {
/* 51 */   private static final Logger LOGGER = Logger.getLogger(WSATTubeHelper.class);
/*    */   
/*    */   public static TransactionalAttribute getTransactionalAttribute(TransactionalFeature feature, Packet packet, WSDLPort port) {
/* 54 */     if (feature == null) {
/* 55 */       feature = new TransactionalFeature(true, Transactional.TransactionFlowType.SUPPORTS, Transactional.Version.DEFAULT);
/*    */     }
/*    */ 
/*    */     
/* 59 */     if (port == null) {
/* 60 */       boolean isEnabled = (feature.isEnabled() && Transactional.TransactionFlowType.NEVER != feature.getFlowType());
/* 61 */       boolean isRequired = (Transactional.TransactionFlowType.MANDATORY == feature.getFlowType());
/* 62 */       if (WSATHelper.isDebugEnabled()) {
/* 63 */         debug("no wsdl port found, the effective transaction attribute is: enabled(" + isEnabled + "),required(" + isRequired + "), version(" + feature.getVersion() + ").");
/*    */       }
/* 65 */       return new TransactionalAttribute(isEnabled, isRequired, feature.getVersion());
/*    */     } 
/* 67 */     WSDLBoundOperation wsdlBoundOperation = packet.getMessage().getOperation(port);
/* 68 */     if (wsdlBoundOperation != null && wsdlBoundOperation.getOperation() != null && !wsdlBoundOperation.getOperation().isOneWay()) {
/*    */ 
/*    */       
/* 71 */       String opName = wsdlBoundOperation.getName().getLocalPart();
/* 72 */       boolean isEnabled = (feature.isEnabled(opName) && Transactional.TransactionFlowType.NEVER != feature.getFlowType(opName));
/*    */       
/* 74 */       boolean isRequired = (Transactional.TransactionFlowType.MANDATORY == feature.getFlowType(opName));
/*    */       
/* 76 */       if (WSATHelper.isDebugEnabled()) {
/* 77 */         debug("the effective transaction attribute for operation' " + opName + "' is : enabled(" + isEnabled + "),required(" + isRequired + "), version(" + feature.getVersion() + ").");
/*    */       }
/* 79 */       return new TransactionalAttribute(isEnabled, isRequired, feature.getVersion());
/*    */     } 
/* 81 */     if (WSATHelper.isDebugEnabled()) {
/* 82 */       debug("no twoway operation found for this request, the effective transaction attribute is disabled.");
/*    */     }
/* 84 */     return new TransactionalAttribute(false, false, Transactional.Version.DEFAULT);
/*    */   }
/*    */   
/*    */   private static void debug(String message) {
/* 88 */     LOGGER.info(message);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\tube\WSATTubeHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */