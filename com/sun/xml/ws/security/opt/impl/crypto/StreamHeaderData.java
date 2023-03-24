/*    */ package com.sun.xml.ws.security.opt.impl.crypto;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Header;
/*    */ import com.sun.xml.ws.security.opt.crypto.StreamWriterData;
/*    */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*    */ import com.sun.xml.ws.security.opt.impl.util.XMLStreamFilter;
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
/*    */ public class StreamHeaderData
/*    */   implements StreamWriterData
/*    */ {
/*    */   private NamespaceContextEx nsContext;
/*    */   private boolean contentOnly;
/*    */   private Header data;
/*    */   
/*    */   public StreamHeaderData(Header header, boolean contentOnly, NamespaceContextEx ns) {
/* 58 */     this.data = header;
/* 59 */     this.nsContext = ns;
/* 60 */     this.contentOnly = contentOnly;
/*    */   }
/*    */   
/*    */   public NamespaceContextEx getNamespaceContext() {
/* 64 */     return this.nsContext;
/*    */   }
/*    */   
/*    */   public void write(XMLStreamWriter writer) throws XMLStreamException {
/* 68 */     if (this.contentOnly) {
/*    */       
/* 70 */       XMLStreamFilter xMLStreamFilter = new XMLStreamFilter(writer, (NamespaceContextEx)this.nsContext);
/* 71 */       this.data.writeTo((XMLStreamWriter)xMLStreamFilter);
/*    */     } else {
/* 73 */       this.data.writeTo(writer);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\crypto\StreamHeaderData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */