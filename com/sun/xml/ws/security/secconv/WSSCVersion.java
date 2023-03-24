/*    */ package com.sun.xml.ws.security.secconv;
/*    */ 
/*    */ import com.sun.xml.ws.security.secconv.impl.WSSCVersion10;
/*    */ import com.sun.xml.ws.security.secconv.impl.wssx.WSSCVersion13;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WSSCVersion
/*    */ {
/* 52 */   public static final WSSCVersion WSSC_10 = (WSSCVersion)new WSSCVersion10();
/*    */   
/* 54 */   public static final WSSCVersion WSSC_13 = (WSSCVersion)new WSSCVersion13();
/*    */   
/*    */   public static final String WSSC_10_NS_URI = "http://schemas.xmlsoap.org/ws/2005/02/sc";
/*    */   public static final String WSSC_13_NS_URI = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512";
/*    */   
/*    */   public static WSSCVersion getInstance(String nsURI) {
/* 60 */     if (nsURI.equals(WSSC_13.getNamespaceURI())) {
/* 61 */       return WSSC_13;
/*    */     }
/* 63 */     return WSSC_10;
/*    */   }
/*    */   
/*    */   public abstract String getNamespaceURI();
/*    */   
/*    */   public abstract String getSCTTokenTypeURI();
/*    */   
/*    */   public abstract String getDKTokenTypeURI();
/*    */   
/*    */   public abstract String getSCTRequestAction();
/*    */   
/*    */   public abstract String getSCTResponseAction();
/*    */   
/*    */   public abstract String getSCTRenewRequestAction();
/*    */   
/*    */   public abstract String getSCTRenewResponseAction();
/*    */   
/*    */   public abstract String getSCTCancelRequestAction();
/*    */   
/*    */   public abstract String getSCTCancelResponseAction();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\WSSCVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */