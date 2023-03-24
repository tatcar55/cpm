/*    */ package com.sun.xml.ws.encoding.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.pipe.Codec;
/*    */ import com.sun.xml.ws.api.pipe.ContentType;
/*    */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*    */ import com.sun.xml.ws.encoding.ContentTypeImpl;
/*    */ import com.sun.xml.ws.message.stream.StreamHeader;
/*    */ import com.sun.xml.ws.message.stream.StreamHeader12;
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
/*    */ final class FastInfosetStreamSOAP12Codec
/*    */   extends FastInfosetStreamSOAPCodec
/*    */ {
/*    */   FastInfosetStreamSOAP12Codec(StreamSOAPCodec soapCodec, boolean retainState) {
/* 62 */     super(soapCodec, SOAPVersion.SOAP_12, retainState, retainState ? "application/vnd.sun.stateful.soap+fastinfoset" : "application/soap+fastinfoset");
/*    */   }
/*    */ 
/*    */   
/*    */   private FastInfosetStreamSOAP12Codec(FastInfosetStreamSOAPCodec that) {
/* 67 */     super(that);
/*    */   }
/*    */   
/*    */   public Codec copy() {
/* 71 */     return new FastInfosetStreamSOAP12Codec(this);
/*    */   }
/*    */   
/*    */   protected final StreamHeader createHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/* 75 */     return (StreamHeader)new StreamHeader12(reader, mark);
/*    */   }
/*    */   
/*    */   protected ContentType getContentType(String soapAction) {
/* 79 */     if (soapAction == null) {
/* 80 */       return this._defaultContentType;
/*    */     }
/* 82 */     return (ContentType)new ContentTypeImpl(this._defaultContentType.getContentType() + ";action=\"" + soapAction + "\"");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\fastinfoset\FastInfosetStreamSOAP12Codec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */