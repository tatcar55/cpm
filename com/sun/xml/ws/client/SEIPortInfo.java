/*    */ package com.sun.xml.ws.client;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.ws.binding.BindingImpl;
/*    */ import com.sun.xml.ws.binding.SOAPBindingImpl;
/*    */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*    */ import com.sun.xml.ws.model.SOAPSEIModel;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SEIPortInfo
/*    */   extends PortInfo
/*    */ {
/*    */   public final Class sei;
/*    */   public final SOAPSEIModel model;
/*    */   
/*    */   public SEIPortInfo(WSServiceDelegate owner, Class sei, SOAPSEIModel model, @NotNull WSDLPort portModel) {
/* 71 */     super(owner, portModel);
/* 72 */     this.sei = sei;
/* 73 */     this.model = model;
/* 74 */     assert sei != null && model != null;
/*    */   }
/*    */ 
/*    */   
/*    */   public BindingImpl createBinding(WebServiceFeatureList webServiceFeatures, Class<?> portInterface) {
/* 79 */     BindingImpl bindingImpl = createBinding(webServiceFeatures, portInterface, null);
/* 80 */     if (bindingImpl instanceof SOAPBindingImpl) {
/* 81 */       ((SOAPBindingImpl)bindingImpl).setPortKnownHeaders(this.model.getKnownHeaders());
/*    */     }
/*    */ 
/*    */     
/* 85 */     return bindingImpl;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\SEIPortInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */