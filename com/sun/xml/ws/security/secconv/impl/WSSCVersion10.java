/*    */ package com.sun.xml.ws.security.secconv.impl;
/*    */ 
/*    */ import com.sun.xml.ws.security.secconv.WSSCVersion;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSSCVersion10
/*    */   extends WSSCVersion
/*    */ {
/* 55 */   private String nsURI = "http://schemas.xmlsoap.org/ws/2005/02/sc";
/* 56 */   private String trustNSURI = "http://schemas.xmlsoap.org/ws/2005/02/trust";
/*    */   
/*    */   public String getNamespaceURI() {
/* 59 */     return this.nsURI;
/*    */   }
/*    */   
/*    */   public String getSCTTokenTypeURI() {
/* 63 */     return this.nsURI + "/sct";
/*    */   }
/*    */   
/*    */   public String getDKTokenTypeURI() {
/* 67 */     return this.nsURI + "/dk";
/*    */   }
/*    */   
/*    */   public String getSCTRequestAction() {
/* 71 */     return this.trustNSURI + "/RST/SCT";
/*    */   }
/*    */   
/*    */   public String getSCTResponseAction() {
/* 75 */     return this.trustNSURI + "/RSTR/SCT";
/*    */   }
/*    */   
/*    */   public String getSCTRenewRequestAction() {
/* 79 */     return this.trustNSURI + "/RST/SCT/Renew";
/*    */   }
/*    */   
/*    */   public String getSCTRenewResponseAction() {
/* 83 */     return this.trustNSURI + "/RSTR/SCT/Renew";
/*    */   }
/*    */   
/*    */   public String getSCTCancelRequestAction() {
/* 87 */     return this.trustNSURI + "/RST/SCT/Cancel";
/*    */   }
/*    */   
/*    */   public String getSCTCancelResponseAction() {
/* 91 */     return this.trustNSURI + "/RSTR/SCT/Cancel";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\impl\WSSCVersion10.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */