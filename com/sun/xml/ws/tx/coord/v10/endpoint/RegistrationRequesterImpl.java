/*    */ package com.sun.xml.ws.tx.coord.v10.endpoint;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.coord.common.endpoint.RegistrationRequester;
/*    */ import javax.xml.ws.WebServiceContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistrationRequesterImpl
/*    */   extends RegistrationRequester
/*    */ {
/*    */   public RegistrationRequesterImpl(WebServiceContext m_context) {
/* 50 */     super(m_context);
/*    */   }
/*    */   
/*    */   protected WSATHelper getWSATHelper() {
/* 54 */     return WSATHelper.V10;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\endpoint\RegistrationRequesterImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */