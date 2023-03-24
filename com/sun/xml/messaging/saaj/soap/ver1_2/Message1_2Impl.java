/*    */ package com.sun.xml.messaging.saaj.soap.ver1_2;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*    */ import com.sun.xml.messaging.saaj.packaging.mime.internet.ContentType;
/*    */ import com.sun.xml.messaging.saaj.soap.MessageImpl;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import javax.xml.soap.MimeHeaders;
/*    */ import javax.xml.soap.SOAPConstants;
/*    */ import javax.xml.soap.SOAPMessage;
/*    */ import javax.xml.soap.SOAPPart;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Message1_2Impl
/*    */   extends MessageImpl
/*    */   implements SOAPConstants
/*    */ {
/*    */   public Message1_2Impl() {}
/*    */   
/*    */   public Message1_2Impl(SOAPMessage msg) {
/* 63 */     super(msg);
/*    */   }
/*    */   
/*    */   public Message1_2Impl(boolean isFastInfoset, boolean acceptFastInfoset) {
/* 67 */     super(isFastInfoset, acceptFastInfoset);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Message1_2Impl(MimeHeaders headers, InputStream in) throws IOException, SOAPExceptionImpl {
/* 73 */     super(headers, in);
/*    */   }
/*    */ 
/*    */   
/*    */   public Message1_2Impl(MimeHeaders headers, ContentType ct, int stat, InputStream in) throws SOAPExceptionImpl {
/* 78 */     super(headers, ct, stat, in);
/*    */   }
/*    */   
/*    */   public SOAPPart getSOAPPart() {
/* 82 */     if (this.soapPartImpl == null) {
/* 83 */       this.soapPartImpl = new SOAPPart1_2Impl(this);
/*    */     }
/* 85 */     return (SOAPPart)this.soapPartImpl;
/*    */   }
/*    */   
/*    */   protected boolean isCorrectSoapVersion(int contentTypeId) {
/* 89 */     return ((contentTypeId & 0x8) != 0);
/*    */   }
/*    */   
/*    */   protected String getExpectedContentType() {
/* 93 */     return this.isFastInfoset ? "application/soap+fastinfoset" : "application/soap+xml";
/*    */   }
/*    */   
/*    */   protected String getExpectedAcceptHeader() {
/* 97 */     String accept = "application/soap+xml, text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2";
/* 98 */     return this.acceptFastInfoset ? ("application/soap+fastinfoset, " + accept) : accept;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_2\Message1_2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */