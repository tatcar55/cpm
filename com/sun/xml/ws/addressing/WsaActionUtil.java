/*    */ package com.sun.xml.ws.addressing;
/*    */ 
/*    */ import com.sun.xml.ws.api.model.CheckedException;
/*    */ import com.sun.xml.ws.api.model.JavaMethod;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WsaActionUtil
/*    */ {
/*    */   public static final String getDefaultFaultAction(JavaMethod method, CheckedException ce) {
/* 57 */     String tns = method.getOwner().getTargetNamespace();
/* 58 */     String delim = getDelimiter(tns);
/* 59 */     if (tns.endsWith(delim)) {
/* 60 */       tns = tns.substring(0, tns.length() - 1);
/*    */     }
/*    */     
/* 63 */     return tns + delim + method.getOwner().getPortTypeName().getLocalPart() + delim + method.getOperationName() + delim + "Fault" + delim + ce.getExceptionClass().getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static String getDelimiter(String tns) {
/* 69 */     String delim = "/";
/*    */     
/*    */     try {
/* 72 */       URI uri = new URI(tns);
/* 73 */       if (uri.getScheme() != null && uri.getScheme().equalsIgnoreCase("urn")) {
/* 74 */         delim = ":";
/*    */       }
/* 76 */     } catch (URISyntaxException e) {
/* 77 */       LOGGER.warning("TargetNamespace of WebService is not a valid URI");
/*    */     } 
/* 79 */     return delim;
/*    */   }
/* 81 */   private static final Logger LOGGER = Logger.getLogger(WsaActionUtil.class.getName());
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\WsaActionUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */