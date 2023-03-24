/*    */ package com.sun.xml.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.istack.Nullable;
/*    */ import com.sun.xml.ws.api.model.SEIModel;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public final class WSDLPortProperties
/*    */   extends WSDLProperties
/*    */ {
/*    */   @NotNull
/*    */   private final WSDLPort port;
/*    */   
/*    */   public WSDLPortProperties(@NotNull WSDLPort port) {
/* 62 */     this(port, null);
/*    */   }
/*    */   
/*    */   public WSDLPortProperties(@NotNull WSDLPort port, @Nullable SEIModel seiModel) {
/* 66 */     super(seiModel);
/* 67 */     this.port = port;
/*    */   }
/*    */   
/*    */   public QName getWSDLService() {
/* 71 */     return this.port.getOwner().getName();
/*    */   }
/*    */   
/*    */   public QName getWSDLPort() {
/* 75 */     return this.port.getName();
/*    */   }
/*    */   
/*    */   public QName getWSDLPortType() {
/* 79 */     return this.port.getBinding().getPortTypeName();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLPortProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */