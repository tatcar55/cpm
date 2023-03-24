/*    */ package com.sun.xml.ws.config.metro.util;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParserUtil
/*    */ {
/* 53 */   private static final Logger LOGGER = Logger.getLogger(ParserUtil.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean parseBooleanValue(String value) throws WebServiceException {
/* 68 */     if ("true".equals(value) || "1".equals(value)) {
/* 69 */       return true;
/*    */     }
/* 71 */     if ("false".equals(value) || "0".equals(value)) {
/* 72 */       return false;
/*    */     }
/*    */     
/* 75 */     throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("invalid boolean value"));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\metr\\util\ParserUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */