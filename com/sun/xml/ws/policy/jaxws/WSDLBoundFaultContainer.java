/*    */ package com.sun.xml.ws.policy.jaxws;
/*    */ 
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundFault;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLObject;
/*    */ import org.xml.sax.Locator;
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
/*    */ class WSDLBoundFaultContainer
/*    */   implements WSDLObject
/*    */ {
/*    */   private final WSDLBoundFault boundFault;
/*    */   private final WSDLBoundOperation boundOperation;
/*    */   
/*    */   public WSDLBoundFaultContainer(WSDLBoundFault fault, WSDLBoundOperation operation) {
/* 61 */     this.boundFault = fault;
/* 62 */     this.boundOperation = operation;
/*    */   }
/*    */   
/*    */   public Locator getLocation() {
/* 66 */     return null;
/*    */   }
/*    */   
/*    */   public WSDLBoundFault getBoundFault() {
/* 70 */     return this.boundFault;
/*    */   }
/*    */   
/*    */   public WSDLBoundOperation getBoundOperation() {
/* 74 */     return this.boundOperation;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\WSDLBoundFaultContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */