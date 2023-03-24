/*    */ package com.sun.xml.ws.security.trust.impl.elements;
/*    */ 
/*    */ import com.sun.xml.ws.api.security.trust.Status;
/*    */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*    */ import com.sun.xml.ws.security.trust.impl.bindings.StatusType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatusImpl
/*    */   extends StatusType
/*    */   implements Status
/*    */ {
/*    */   public StatusImpl(String code, String reason) {
/* 57 */     setCode(code);
/* 58 */     setReason(reason);
/*    */   }
/*    */   
/*    */   public StatusImpl(StatusType statusType) {
/* 62 */     setCode(statusType.getCode());
/* 63 */     setReason(statusType.getReason());
/*    */   }
/*    */   
/*    */   public boolean isValid() {
/* 67 */     WSTrustVersion wstVer = WSTrustVersion.WS_TRUST_10;
/* 68 */     return wstVer.getValidStatusCodeURI().equals(getCode());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\StatusImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */