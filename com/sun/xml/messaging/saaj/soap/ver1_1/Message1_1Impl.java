/*     */ package com.sun.xml.messaging.saaj.soap.ver1_1;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.ContentType;
/*     */ import com.sun.xml.messaging.saaj.soap.MessageImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.soap.SOAPPart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Message1_1Impl
/*     */   extends MessageImpl
/*     */   implements SOAPConstants
/*     */ {
/*  61 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.ver1_1", "com.sun.xml.messaging.saaj.soap.ver1_1.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public Message1_1Impl() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Message1_1Impl(boolean isFastInfoset, boolean acceptFastInfoset) {
/*  70 */     super(isFastInfoset, acceptFastInfoset);
/*     */   }
/*     */   
/*     */   public Message1_1Impl(SOAPMessage msg) {
/*  74 */     super(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Message1_1Impl(MimeHeaders headers, InputStream in) throws IOException, SOAPExceptionImpl {
/*  80 */     super(headers, in);
/*     */   }
/*     */ 
/*     */   
/*     */   public Message1_1Impl(MimeHeaders headers, ContentType ct, int stat, InputStream in) throws SOAPExceptionImpl {
/*  85 */     super(headers, ct, stat, in);
/*     */   }
/*     */   
/*     */   public SOAPPart getSOAPPart() {
/*  89 */     if (this.soapPartImpl == null) {
/*  90 */       this.soapPartImpl = new SOAPPart1_1Impl(this);
/*     */     }
/*  92 */     return (SOAPPart)this.soapPartImpl;
/*     */   }
/*     */   
/*     */   protected boolean isCorrectSoapVersion(int contentTypeId) {
/*  96 */     return ((contentTypeId & 0x4) != 0);
/*     */   }
/*     */   
/*     */   public String getAction() {
/* 100 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", (Object[])new String[] { "Action" });
/*     */ 
/*     */ 
/*     */     
/* 104 */     throw new UnsupportedOperationException("Operation not supported by SOAP 1.1");
/*     */   }
/*     */   
/*     */   public void setAction(String type) {
/* 108 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", (Object[])new String[] { "Action" });
/*     */ 
/*     */ 
/*     */     
/* 112 */     throw new UnsupportedOperationException("Operation not supported by SOAP 1.1");
/*     */   }
/*     */   
/*     */   public String getCharset() {
/* 116 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", (Object[])new String[] { "Charset" });
/*     */ 
/*     */ 
/*     */     
/* 120 */     throw new UnsupportedOperationException("Operation not supported by SOAP 1.1");
/*     */   }
/*     */   
/*     */   public void setCharset(String charset) {
/* 124 */     log.log(Level.SEVERE, "SAAJ0303.ver1_1.msg.op.unsupported.in.SOAP1.1", (Object[])new String[] { "Charset" });
/*     */ 
/*     */ 
/*     */     
/* 128 */     throw new UnsupportedOperationException("Operation not supported by SOAP 1.1");
/*     */   }
/*     */   
/*     */   protected String getExpectedContentType() {
/* 132 */     return this.isFastInfoset ? "application/fastinfoset" : "text/xml";
/*     */   }
/*     */   
/*     */   protected String getExpectedAcceptHeader() {
/* 136 */     String accept = "text/xml, text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2";
/* 137 */     return this.acceptFastInfoset ? ("application/fastinfoset, " + accept) : accept;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_1\Message1_1Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */