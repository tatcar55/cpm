/*    */ package com.sun.xml.ws.server;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.resources.ServerMessages;
/*    */ import com.sun.xml.ws.util.exception.JAXWSExceptionBase;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class UnsupportedMediaException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public UnsupportedMediaException(@NotNull String contentType, List<String> expectedContentTypes) {
/* 58 */     super(ServerMessages.localizableUNSUPPORTED_CONTENT_TYPE(contentType, expectedContentTypes));
/*    */   }
/*    */   
/*    */   public UnsupportedMediaException() {
/* 62 */     super(ServerMessages.localizableNO_CONTENT_TYPE());
/*    */   }
/*    */   
/*    */   public UnsupportedMediaException(String charset) {
/* 66 */     super(ServerMessages.localizableUNSUPPORTED_CHARSET(charset));
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 70 */     return "com.sun.xml.ws.resources.server";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\UnsupportedMediaException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */