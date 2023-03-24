/*    */ package com.sun.xml.ws.client.dispatch;
/*    */ 
/*    */ import com.sun.xml.ws.api.WSFeatureList;
/*    */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*    */ import com.sun.xml.ws.api.client.WSPortInfo;
/*    */ import com.sun.xml.ws.api.message.Message;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.binding.BindingImpl;
/*    */ import com.sun.xml.ws.client.WSServiceDelegate;
/*    */ import com.sun.xml.ws.encoding.xml.XMLMessage;
/*    */ import javax.activation.DataSource;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.Service;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ 
/*    */ public class DataSourceDispatch
/*    */   extends DispatchImpl<DataSource>
/*    */ {
/*    */   @Deprecated
/*    */   public DataSourceDispatch(QName port, Service.Mode mode, WSServiceDelegate service, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/* 68 */     super(port, mode, service, pipe, binding, epr);
/*    */   }
/*    */   
/*    */   public DataSourceDispatch(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/* 72 */     super(portInfo, mode, binding, epr);
/*    */   }
/*    */ 
/*    */   
/*    */   Packet createPacket(DataSource arg) {
/* 77 */     switch (this.mode) {
/*    */       case PAYLOAD:
/* 79 */         throw new IllegalArgumentException("DataSource use is not allowed in Service.Mode.PAYLOAD\n");
/*    */       case MESSAGE:
/* 81 */         return new Packet(XMLMessage.create(arg, (WSFeatureList)this.binding.getFeatures()));
/*    */     } 
/* 83 */     throw new WebServiceException("Unrecognized message mode");
/*    */   }
/*    */ 
/*    */   
/*    */   DataSource toReturnValue(Packet response) {
/* 88 */     Message message = response.getInternalMessage();
/* 89 */     return (message instanceof XMLMessage.MessageDataSource) ? ((XMLMessage.MessageDataSource)message).getDataSource() : XMLMessage.getDataSource(message, (WSFeatureList)this.binding.getFeatures());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\dispatch\DataSourceDispatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */