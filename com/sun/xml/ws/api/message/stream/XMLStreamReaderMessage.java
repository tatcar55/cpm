/*    */ package com.sun.xml.ws.api.message.stream;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.AttachmentSet;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLStreamReaderMessage
/*    */   extends StreamBasedMessage
/*    */ {
/*    */   public final XMLStreamReader msg;
/*    */   
/*    */   public XMLStreamReaderMessage(Packet properties, XMLStreamReader msg) {
/* 68 */     super(properties);
/* 69 */     this.msg = msg;
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLStreamReaderMessage(Packet properties, AttachmentSet attachments, XMLStreamReader msg) {
/* 86 */     super(properties, attachments);
/* 87 */     this.msg = msg;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\stream\XMLStreamReaderMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */