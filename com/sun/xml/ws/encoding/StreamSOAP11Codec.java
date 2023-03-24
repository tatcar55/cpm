/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.message.stream.StreamHeader;
/*     */ import com.sun.xml.ws.message.stream.StreamHeader11;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class StreamSOAP11Codec
/*     */   extends StreamSOAPCodec
/*     */ {
/*     */   public static final String SOAP11_MIME_TYPE = "text/xml";
/*     */   public static final String DEFAULT_SOAP11_CONTENT_TYPE = "text/xml; charset=utf-8";
/*  66 */   private static final List<String> EXPECTED_CONTENT_TYPES = Collections.singletonList("text/xml");
/*     */   
/*     */   StreamSOAP11Codec() {
/*  69 */     super(SOAPVersion.SOAP_11);
/*     */   }
/*     */   
/*     */   StreamSOAP11Codec(WSBinding binding) {
/*  73 */     super(binding);
/*     */   }
/*     */   
/*     */   StreamSOAP11Codec(WSFeatureList features) {
/*  77 */     super(features);
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/*  81 */     return "text/xml";
/*     */   }
/*     */ 
/*     */   
/*     */   protected final StreamHeader createHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/*  86 */     return (StreamHeader)new StreamHeader11(reader, mark);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ContentType getContentType(Packet packet) {
/*  91 */     ContentTypeImpl.Builder b = getContenTypeBuilder(packet);
/*  92 */     b.soapAction = packet.soapAction;
/*  93 */     return b.build();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultContentType() {
/*  98 */     return "text/xml; charset=utf-8";
/*     */   }
/*     */   
/*     */   protected List<String> getExpectedContentTypes() {
/* 102 */     return EXPECTED_CONTENT_TYPES;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\StreamSOAP11Codec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */