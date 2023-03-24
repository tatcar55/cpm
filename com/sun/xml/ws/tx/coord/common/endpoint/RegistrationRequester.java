/*    */ package com.sun.xml.ws.tx.coord.common.endpoint;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.coord.common.PendingRequestManager;
/*    */ import com.sun.xml.ws.tx.coord.common.RegistrationRequesterIF;
/*    */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class RegistrationRequester
/*    */   implements RegistrationRequesterIF
/*    */ {
/*    */   private WebServiceContext m_context;
/*    */   
/*    */   public RegistrationRequester(WebServiceContext m_context) {
/* 57 */     this.m_context = m_context;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void registerResponse(BaseRegisterResponseType parameters) {
/* 65 */     String referenceID = getWSATHelper().getWSATTidFromWebServiceContextHeaderList(this.m_context);
/* 66 */     PendingRequestManager.registryReponse(referenceID, parameters);
/*    */   }
/*    */   
/*    */   protected abstract WSATHelper getWSATHelper();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\endpoint\RegistrationRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */