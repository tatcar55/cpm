/*    */ package com.sun.xml.ws.security.opt.impl.crypto;
/*    */ 
/*    */ import com.sun.xml.ws.security.opt.crypto.StreamWriterData;
/*    */ import com.sun.xml.ws.security.opt.impl.message.SOAPBody;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamWriter;
/*    */ import org.jvnet.staxex.NamespaceContextEx;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SSBData
/*    */   implements StreamWriterData
/*    */ {
/*    */   private NamespaceContextEx nsContext;
/*    */   private boolean contentOnly;
/*    */   private SOAPBody body;
/*    */   
/*    */   public SSBData(SOAPBody body, boolean contentOnly, NamespaceContextEx nsContext) {
/* 60 */     this.body = body;
/* 61 */     this.contentOnly = contentOnly;
/* 62 */     this.nsContext = nsContext;
/*    */   }
/*    */   
/*    */   public NamespaceContextEx getNamespaceContext() {
/* 66 */     return this.nsContext;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(XMLStreamWriter writer) throws XMLStreamException {
/* 71 */     if (this.contentOnly) {
/* 72 */       this.body.writePayload(writer);
/*    */     } else {
/* 74 */       this.body.writeTo(writer);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\crypto\SSBData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */