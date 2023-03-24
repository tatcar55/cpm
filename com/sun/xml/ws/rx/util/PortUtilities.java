/*    */ package com.sun.xml.ws.rx.util;
/*    */ 
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PortUtilities
/*    */ {
/*    */   public static boolean checkForRequestResponseOperations(WSDLPort port) {
/*    */     WSDLBoundPortType portType;
/* 65 */     if (port == null || null == (portType = port.getBinding()))
/*    */     {
/* 67 */       return false;
/*    */     }
/*    */     
/* 70 */     for (WSDLBoundOperation boundOperation : portType.getBindingOperations()) {
/* 71 */       if (!boundOperation.getOperation().isOneWay()) {
/* 72 */         return true;
/*    */       }
/*    */     } 
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\r\\util\PortUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */