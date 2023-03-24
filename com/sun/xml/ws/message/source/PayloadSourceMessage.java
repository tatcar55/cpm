/*    */ package com.sun.xml.ws.message.source;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.istack.Nullable;
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.message.AttachmentSet;
/*    */ import com.sun.xml.ws.api.message.HeaderList;
/*    */ import com.sun.xml.ws.message.AttachmentSetImpl;
/*    */ import com.sun.xml.ws.message.stream.PayloadStreamReaderMessage;
/*    */ import com.sun.xml.ws.streaming.SourceReaderFactory;
/*    */ import javax.xml.transform.Source;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PayloadSourceMessage
/*    */   extends PayloadStreamReaderMessage
/*    */ {
/*    */   public PayloadSourceMessage(@Nullable HeaderList headers, @NotNull Source payload, @NotNull AttachmentSet attSet, @NotNull SOAPVersion soapVersion) {
/* 66 */     super(headers, SourceReaderFactory.createSourceReader(payload, true), attSet, soapVersion);
/*    */   }
/*    */ 
/*    */   
/*    */   public PayloadSourceMessage(Source s, SOAPVersion soapVer) {
/* 71 */     this(null, s, (AttachmentSet)new AttachmentSetImpl(), soapVer);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\source\PayloadSourceMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */