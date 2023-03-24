/*    */ package com.sun.xml.ws.tx.coord.v10;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.coord.common.CoordinationContextBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.WSATCoordinationContextBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSATCoordinationContextBuilderImpl
/*    */   extends WSATCoordinationContextBuilder
/*    */ {
/*    */   protected CoordinationContextBuilderImpl newCoordinationContextBuilder() {
/* 53 */     return new CoordinationContextBuilderImpl();
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getCoordinationType() {
/* 58 */     return "http://schemas.xmlsoap.org/ws/2004/10/wsat";
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDefaultRegistrationCoordinatorAddress() {
/* 63 */     return WSATHelper.V10.getRegistrationCoordinatorAddress();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\WSATCoordinationContextBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */