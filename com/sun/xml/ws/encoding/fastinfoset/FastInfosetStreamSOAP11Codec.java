/*    */ package com.sun.xml.ws.encoding.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.pipe.Codec;
/*    */ import com.sun.xml.ws.api.pipe.ContentType;
/*    */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*    */ import com.sun.xml.ws.encoding.ContentTypeImpl;
/*    */ import com.sun.xml.ws.message.stream.StreamHeader;
/*    */ import com.sun.xml.ws.message.stream.StreamHeader11;
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
/*    */ final class FastInfosetStreamSOAP11Codec
/*    */   extends FastInfosetStreamSOAPCodec
/*    */ {
/*    */   FastInfosetStreamSOAP11Codec(StreamSOAPCodec soapCodec, boolean retainState) {
/* 62 */     super(soapCodec, SOAPVersion.SOAP_11, retainState, retainState ? "application/vnd.sun.stateful.fastinfoset" : "application/fastinfoset");
/*    */   }
/*    */ 
/*    */   
/*    */   private FastInfosetStreamSOAP11Codec(FastInfosetStreamSOAP11Codec that) {
/* 67 */     super(that);
/*    */   }
/*    */   
/*    */   public Codec copy() {
/* 71 */     return new FastInfosetStreamSOAP11Codec(this);
/*    */   }
/*    */   
/*    */   protected final StreamHeader createHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/* 75 */     return (StreamHeader)new StreamHeader11(reader, mark);
/*    */   }
/*    */   
/*    */   protected ContentType getContentType(String soapAction) {
/* 79 */     if (soapAction == null || soapAction.length() == 0) {
/* 80 */       return this._defaultContentType;
/*    */     }
/* 82 */     return (ContentType)new ContentTypeImpl(this._defaultContentType.getContentType(), soapAction);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\fastinfoset\FastInfosetStreamSOAP11Codec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */