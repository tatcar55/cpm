/*    */ package com.sun.xml.messaging.saaj.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import javax.xml.soap.MimeHeader;
/*    */ import javax.xml.soap.MimeHeaders;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MimeHeadersUtil
/*    */ {
/*    */   public static MimeHeaders copy(MimeHeaders headers) {
/* 54 */     MimeHeaders newHeaders = new MimeHeaders();
/* 55 */     Iterator<MimeHeader> eachHeader = headers.getAllHeaders();
/* 56 */     while (eachHeader.hasNext()) {
/* 57 */       MimeHeader currentHeader = eachHeader.next();
/*    */       
/* 59 */       newHeaders.addHeader(currentHeader.getName(), currentHeader.getValue());
/*    */     } 
/*    */ 
/*    */     
/* 63 */     return newHeaders;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\MimeHeadersUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */