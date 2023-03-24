/*    */ package com.sun.xml.ws.tx.coord.v11;
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
/*    */ public class WSATCoordinationContextBuilderImpl
/*    */   extends WSATCoordinationContextBuilder
/*    */ {
/*    */   protected String getCoordinationType() {
/* 51 */     return "http://docs.oasis-open.org/ws-tx/wsat/2006/06";
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDefaultRegistrationCoordinatorAddress() {
/* 56 */     return WSATHelper.V11.getRegistrationCoordinatorAddress();
/*    */   }
/*    */ 
/*    */   
/*    */   protected CoordinationContextBuilderImpl newCoordinationContextBuilder() {
/* 61 */     CoordinationContextBuilderImpl builder = new CoordinationContextBuilderImpl();
/* 62 */     return builder;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\WSATCoordinationContextBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */