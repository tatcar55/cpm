/*    */ package com.sun.xml.ws.transport.tcp.encoding;
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
/*    */ public class WSTCPFastInfosetStreamSOAP12Codec
/*    */   extends WSTCPFastInfosetStreamCodec
/*    */ {
/*    */   WSTCPFastInfosetStreamSOAP12Codec(StreamSOAPCodec soapCodec, WSTCPFastInfosetStreamReaderRecyclable.RecycleAwareListener readerRecycleListener, boolean retainState) {
/* 61 */     super(soapCodec, SOAPVersion.SOAP_12, readerRecycleListener, retainState, retainState ? "application/vnd.sun.stateful.soap+fastinfoset" : "application/soap+fastinfoset");
/*    */   }
/*    */ 
/*    */   
/*    */   private WSTCPFastInfosetStreamSOAP12Codec(WSTCPFastInfosetStreamSOAP12Codec that) {
/* 66 */     super(that);
/*    */   }
/*    */   
/*    */   public Codec copy() {
/* 70 */     return new WSTCPFastInfosetStreamSOAP12Codec(this);
/*    */   }
/*    */   
/*    */   protected final StreamHeader createHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/* 74 */     return (StreamHeader)new StreamHeader12(reader, mark);
/*    */   }
/*    */   
/*    */   protected ContentType getContentType(String soapAction) {
/* 78 */     if (soapAction == null) {
/* 79 */       return this._defaultContentType;
/*    */     }
/* 81 */     return (ContentType)new ContentTypeImpl(this._defaultContentType.getContentType() + ";action=\"" + soapAction + "\"");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\encoding\WSTCPFastInfosetStreamSOAP12Codec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */