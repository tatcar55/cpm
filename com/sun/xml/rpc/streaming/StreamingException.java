/*    */ package com.sun.xml.rpc.streaming;
/*    */ 
/*    */ import com.sun.xml.rpc.sp.ParseException;
/*    */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*    */ import com.sun.xml.rpc.util.localization.Localizable;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StreamingException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public StreamingException(IOException e) {
/* 43 */     this("streaming.ioException", e.toString());
/*    */   }
/*    */   
/*    */   public StreamingException(ParseException e) {
/* 47 */     this("streaming.parseException", e.toString());
/*    */   }
/*    */   
/*    */   public StreamingException(String key) {
/* 51 */     super(key);
/*    */   }
/*    */   
/*    */   public StreamingException(String key, String arg) {
/* 55 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public StreamingException(String key, Localizable localizable) {
/* 59 */     super(key, localizable);
/*    */   }
/*    */   
/*    */   public StreamingException(String key, Object[] args) {
/* 63 */     super(key, args);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 67 */     return "com.sun.xml.rpc.resources.streaming";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\StreamingException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */