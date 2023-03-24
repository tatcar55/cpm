/*    */ package com.sun.xml.ws.api.message.stream;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.AttachmentSet;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.message.AttachmentSetImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class StreamBasedMessage
/*    */ {
/*    */   public final Packet properties;
/*    */   public final AttachmentSet attachments;
/*    */   
/*    */   protected StreamBasedMessage(Packet properties) {
/* 71 */     this.properties = properties;
/* 72 */     this.attachments = (AttachmentSet)new AttachmentSetImpl();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected StreamBasedMessage(Packet properties, AttachmentSet attachments) {
/* 85 */     this.properties = properties;
/* 86 */     this.attachments = attachments;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\stream\StreamBasedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */