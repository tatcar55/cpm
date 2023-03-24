/*    */ package com.sun.xml.ws.server;
/*    */ 
/*    */ import com.sun.istack.localization.Localizable;
/*    */ import com.sun.xml.ws.util.exception.JAXWSExceptionBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerRtException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public ServerRtException(String key, Object... args) {
/* 51 */     super(key, args);
/*    */   }
/*    */   
/*    */   public ServerRtException(Throwable throwable) {
/* 55 */     super(throwable);
/*    */   }
/*    */   
/*    */   public ServerRtException(Localizable arg) {
/* 59 */     super("server.rt.err", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 63 */     return "com.sun.xml.ws.resources.server";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\ServerRtException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */