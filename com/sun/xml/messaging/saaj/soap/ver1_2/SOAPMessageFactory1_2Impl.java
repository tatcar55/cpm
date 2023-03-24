/*    */ package com.sun.xml.messaging.saaj.soap.ver1_2;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.MessageFactoryImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.MessageImpl;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import javax.xml.soap.MimeHeaders;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.SOAPMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SOAPMessageFactory1_2Impl
/*    */   extends MessageFactoryImpl
/*    */ {
/*    */   public SOAPMessage createMessage() throws SOAPException {
/* 59 */     return (SOAPMessage)new Message1_2Impl();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPMessage createMessage(boolean isFastInfoset, boolean acceptFastInfoset) throws SOAPException {
/* 65 */     return (SOAPMessage)new Message1_2Impl(isFastInfoset, acceptFastInfoset);
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPMessage createMessage(MimeHeaders headers, InputStream in) throws IOException, SOAPExceptionImpl {
/* 70 */     if (headers == null) {
/* 71 */       headers = new MimeHeaders();
/*    */     }
/*    */     
/* 74 */     if (getContentType(headers) == null) {
/* 75 */       headers.setHeader("Content-Type", "application/soap+xml");
/*    */     }
/*    */     
/* 78 */     MessageImpl msg = new Message1_2Impl(headers, in);
/* 79 */     msg.setLazyAttachments(this.lazyAttachments);
/* 80 */     return (SOAPMessage)msg;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_2\SOAPMessageFactory1_2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */