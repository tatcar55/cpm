/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.message.stream.StreamHeader;
/*     */ import com.sun.xml.ws.message.stream.StreamHeader12;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ final class StreamSOAP12Codec
/*     */   extends StreamSOAPCodec
/*     */ {
/*     */   public static final String SOAP12_MIME_TYPE = "application/soap+xml";
/*     */   public static final String DEFAULT_SOAP12_CONTENT_TYPE = "application/soap+xml; charset=utf-8";
/*  68 */   private static final List<String> EXPECTED_CONTENT_TYPES = Collections.singletonList("application/soap+xml");
/*     */   
/*     */   StreamSOAP12Codec() {
/*  71 */     super(SOAPVersion.SOAP_12);
/*     */   }
/*     */   
/*     */   StreamSOAP12Codec(WSBinding binding) {
/*  75 */     super(binding);
/*     */   }
/*     */   
/*     */   StreamSOAP12Codec(WSFeatureList features) {
/*  79 */     super(features);
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/*  83 */     return "application/soap+xml";
/*     */   }
/*     */ 
/*     */   
/*     */   protected final StreamHeader createHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/*  88 */     return (StreamHeader)new StreamHeader12(reader, mark);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ContentType getContentType(Packet packet) {
/*  93 */     ContentTypeImpl.Builder b = getContenTypeBuilder(packet);
/*     */     
/*  95 */     if (packet.soapAction == null) {
/*  96 */       return b.build();
/*     */     }
/*  98 */     b.contentType += ";action=" + fixQuotesAroundSoapAction(packet.soapAction);
/*  99 */     return b.build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet, AttachmentSet att) throws IOException {
/* 105 */     ContentType ct = new ContentType(contentType);
/* 106 */     packet.soapAction = fixQuotesAroundSoapAction(ct.getParameter("action"));
/* 107 */     super.decode(in, contentType, packet, att);
/*     */   }
/*     */   
/*     */   private String fixQuotesAroundSoapAction(String soapAction) {
/* 111 */     if (soapAction != null && (!soapAction.startsWith("\"") || !soapAction.endsWith("\""))) {
/* 112 */       String fixedSoapAction = soapAction;
/* 113 */       if (!soapAction.startsWith("\""))
/* 114 */         fixedSoapAction = "\"" + fixedSoapAction; 
/* 115 */       if (!soapAction.endsWith("\""))
/* 116 */         fixedSoapAction = fixedSoapAction + "\""; 
/* 117 */       return fixedSoapAction;
/*     */     } 
/* 119 */     return soapAction;
/*     */   }
/*     */   
/*     */   protected List<String> getExpectedContentTypes() {
/* 123 */     return EXPECTED_CONTENT_TYPES;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultContentType() {
/* 128 */     return "application/soap+xml; charset=utf-8";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\StreamSOAP12Codec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */