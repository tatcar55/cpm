/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.oracle.webservices.api.message.ContentType;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.stream.buffer.AbstractCreatorProcessor;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferMark;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.ws.developer.SerializationFeature;
/*     */ import com.sun.xml.ws.message.AttachmentSetImpl;
/*     */ import com.sun.xml.ws.message.stream.StreamHeader;
/*     */ import com.sun.xml.ws.message.stream.StreamMessage;
/*     */ import com.sun.xml.ws.protocol.soap.VersionMismatchException;
/*     */ import com.sun.xml.ws.server.UnsupportedMediaException;
/*     */ import com.sun.xml.ws.streaming.TidyXMLStreamReader;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ 
/*     */ 
/*     */ public abstract class StreamSOAPCodec
/*     */   implements StreamSOAPCodec, RootOnlyCodec
/*     */ {
/*     */   private static final String SOAP_ENVELOPE = "Envelope";
/*     */   private static final String SOAP_HEADER = "Header";
/*     */   private static final String SOAP_BODY = "Body";
/*     */   private final String SOAP_NAMESPACE_URI;
/*     */   private final SOAPVersion soapVersion;
/*     */   protected final SerializationFeature serializationFeature;
/*     */   private static final String DECODED_MESSAGE_CHARSET = "decodedMessageCharset";
/*     */   
/*     */   StreamSOAPCodec(SOAPVersion soapVersion) {
/* 107 */     this(soapVersion, null);
/*     */   }
/*     */   
/*     */   StreamSOAPCodec(WSBinding binding) {
/* 111 */     this(binding.getSOAPVersion(), (SerializationFeature)binding.getFeature(SerializationFeature.class));
/*     */   }
/*     */   
/*     */   StreamSOAPCodec(WSFeatureList features) {
/* 115 */     this(WebServiceFeatureList.getSoapVersion(features), (SerializationFeature)features.get(SerializationFeature.class));
/*     */   }
/*     */   
/*     */   private StreamSOAPCodec(SOAPVersion soapVersion, @Nullable SerializationFeature sf) {
/* 119 */     this.soapVersion = soapVersion;
/* 120 */     this.SOAP_NAMESPACE_URI = soapVersion.nsUri;
/* 121 */     this.serializationFeature = sf;
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 125 */     return getContentType(packet);
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) {
/* 129 */     if (packet.getMessage() != null) {
/* 130 */       String encoding = getPacketEncoding(packet);
/* 131 */       packet.invocationProperties.remove("decodedMessageCharset");
/* 132 */       XMLStreamWriter writer = XMLStreamWriterFactory.create(out, encoding);
/*     */       try {
/* 134 */         packet.getMessage().writeTo(writer);
/* 135 */         writer.flush();
/* 136 */       } catch (XMLStreamException e) {
/* 137 */         throw new WebServiceException(e);
/*     */       } 
/* 139 */       XMLStreamWriterFactory.recycle(writer);
/*     */     } 
/* 141 */     return getContentType(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 150 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/* 156 */     decode(in, contentType, packet, (AttachmentSet)new AttachmentSetImpl());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isContentTypeSupported(String ct, List<String> expected) {
/* 168 */     for (String contentType : expected) {
/* 169 */       if (ct.contains(contentType)) {
/* 170 */         return true;
/*     */       }
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final Message decode(@NotNull XMLStreamReader reader) {
/* 184 */     return decode(reader, (AttachmentSet)new AttachmentSetImpl());
/*     */   }
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
/*     */   public final Message decode(XMLStreamReader reader, @NotNull AttachmentSet attachmentSet) {
/* 201 */     if (reader.getEventType() != 1)
/* 202 */       XMLStreamReaderUtil.nextElementContent(reader); 
/* 203 */     XMLStreamReaderUtil.verifyReaderState(reader, 1);
/* 204 */     if ("Envelope".equals(reader.getLocalName()) && !this.SOAP_NAMESPACE_URI.equals(reader.getNamespaceURI())) {
/* 205 */       throw new VersionMismatchException(this.soapVersion, new Object[] { this.SOAP_NAMESPACE_URI, reader.getNamespaceURI() });
/*     */     }
/* 207 */     XMLStreamReaderUtil.verifyTag(reader, this.SOAP_NAMESPACE_URI, "Envelope");
/*     */     
/* 209 */     TagInfoset envelopeTag = new TagInfoset(reader);
/*     */ 
/*     */     
/* 212 */     Map<String, String> namespaces = new HashMap<String, String>();
/* 213 */     for (int i = 0; i < reader.getNamespaceCount(); i++) {
/* 214 */       namespaces.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
/*     */     }
/*     */ 
/*     */     
/* 218 */     XMLStreamReaderUtil.nextElementContent(reader);
/* 219 */     XMLStreamReaderUtil.verifyReaderState(reader, 1);
/*     */ 
/*     */     
/* 222 */     HeaderList headers = null;
/* 223 */     TagInfoset headerTag = null;
/*     */     
/* 225 */     if (reader.getLocalName().equals("Header") && reader.getNamespaceURI().equals(this.SOAP_NAMESPACE_URI)) {
/*     */       
/* 227 */       headerTag = new TagInfoset(reader);
/*     */ 
/*     */       
/* 230 */       for (int j = 0; j < reader.getNamespaceCount(); j++) {
/* 231 */         namespaces.put(reader.getNamespacePrefix(j), reader.getNamespaceURI(j));
/*     */       }
/*     */       
/* 234 */       XMLStreamReaderUtil.nextElementContent(reader);
/*     */ 
/*     */       
/* 237 */       if (reader.getEventType() == 1) {
/* 238 */         headers = new HeaderList(this.soapVersion);
/*     */ 
/*     */         
/*     */         try {
/* 242 */           cacheHeaders(reader, namespaces, headers);
/* 243 */         } catch (XMLStreamException e) {
/*     */           
/* 245 */           throw new WebServiceException(e);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 250 */       XMLStreamReaderUtil.nextElementContent(reader);
/*     */     } 
/*     */ 
/*     */     
/* 254 */     XMLStreamReaderUtil.verifyTag(reader, this.SOAP_NAMESPACE_URI, "Body");
/* 255 */     TagInfoset bodyTag = new TagInfoset(reader);
/*     */     
/* 257 */     String bodyPrologue = XMLStreamReaderUtil.nextWhiteSpaceContent(reader);
/* 258 */     return (Message)new StreamMessage(envelopeTag, headerTag, attachmentSet, headers, bodyPrologue, bodyTag, null, reader, this.soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet packet) {
/* 265 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public final StreamSOAPCodec copy() {
/* 269 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private XMLStreamBuffer cacheHeaders(XMLStreamReader reader, Map<String, String> namespaces, HeaderList headers) throws XMLStreamException {
/* 274 */     MutableXMLStreamBuffer buffer = createXMLStreamBuffer();
/* 275 */     StreamReaderBufferCreator creator = new StreamReaderBufferCreator();
/* 276 */     creator.setXMLStreamBuffer(buffer);
/*     */ 
/*     */     
/* 279 */     while (reader.getEventType() == 1) {
/* 280 */       Map<String, String> headerBlockNamespaces = namespaces;
/*     */ 
/*     */       
/* 283 */       if (reader.getNamespaceCount() > 0) {
/* 284 */         headerBlockNamespaces = new HashMap<String, String>(namespaces);
/* 285 */         for (int i = 0; i < reader.getNamespaceCount(); i++) {
/* 286 */           headerBlockNamespaces.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 291 */       XMLStreamBufferMark xMLStreamBufferMark = new XMLStreamBufferMark(headerBlockNamespaces, (AbstractCreatorProcessor)creator);
/*     */       
/* 293 */       headers.add((Header)createHeader(reader, (XMLStreamBuffer)xMLStreamBufferMark));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       creator.createElementFragment(reader, false);
/* 300 */       if (reader.getEventType() != 1 && reader.getEventType() != 2)
/*     */       {
/* 302 */         XMLStreamReaderUtil.nextElementContent(reader);
/*     */       }
/*     */     } 
/*     */     
/* 306 */     return (XMLStreamBuffer)buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MutableXMLStreamBuffer createXMLStreamBuffer() {
/* 316 */     return new MutableXMLStreamBuffer();
/*     */   }
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet, AttachmentSet att) throws IOException {
/* 320 */     List<String> expectedContentTypes = getExpectedContentTypes();
/* 321 */     if (contentType != null && !isContentTypeSupported(contentType, expectedContentTypes)) {
/* 322 */       throw new UnsupportedMediaException(contentType, expectedContentTypes);
/*     */     }
/* 324 */     ContentType pct = packet.getInternalContentType();
/* 325 */     ContentTypeImpl cti = (pct != null && pct instanceof ContentTypeImpl) ? (ContentTypeImpl)pct : new ContentTypeImpl(contentType);
/*     */     
/* 327 */     String charset = cti.getCharSet();
/* 328 */     if (charset != null && !Charset.isSupported(charset)) {
/* 329 */       throw new UnsupportedMediaException(charset);
/*     */     }
/* 331 */     if (charset != null) {
/* 332 */       packet.invocationProperties.put("decodedMessageCharset", charset);
/*     */     } else {
/* 334 */       packet.invocationProperties.remove("decodedMessageCharset");
/*     */     } 
/* 336 */     XMLStreamReader reader = XMLStreamReaderFactory.create(null, in, charset, true);
/* 337 */     TidyXMLStreamReader tidyXMLStreamReader = new TidyXMLStreamReader(reader, in);
/* 338 */     packet.setMessage(decode((XMLStreamReader)tidyXMLStreamReader, att));
/*     */   }
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet response, AttachmentSet att) {
/* 342 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StreamSOAPCodec create(SOAPVersion version) {
/* 349 */     if (version == null)
/*     */     {
/* 351 */       throw new IllegalArgumentException(); } 
/* 352 */     switch (version) {
/*     */       case SOAP_11:
/* 354 */         return new StreamSOAP11Codec();
/*     */       case SOAP_12:
/* 356 */         return new StreamSOAP12Codec();
/*     */     } 
/* 358 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StreamSOAPCodec create(WSFeatureList features) {
/* 366 */     SOAPVersion version = WebServiceFeatureList.getSoapVersion(features);
/* 367 */     if (version == null)
/*     */     {
/* 369 */       throw new IllegalArgumentException(); } 
/* 370 */     switch (version) {
/*     */       case SOAP_11:
/* 372 */         return new StreamSOAP11Codec(features);
/*     */       case SOAP_12:
/* 374 */         return new StreamSOAP12Codec(features);
/*     */     } 
/* 376 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StreamSOAPCodec create(WSBinding binding) {
/* 386 */     SOAPVersion version = binding.getSOAPVersion();
/* 387 */     if (version == null)
/*     */     {
/* 389 */       throw new IllegalArgumentException(); } 
/* 390 */     switch (version) {
/*     */       case SOAP_11:
/* 392 */         return new StreamSOAP11Codec(binding);
/*     */       case SOAP_12:
/* 394 */         return new StreamSOAP12Codec(binding);
/*     */     } 
/* 396 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getPacketEncoding(Packet packet) {
/* 402 */     if (this.serializationFeature != null && this.serializationFeature.getEncoding() != null) {
/* 403 */       return this.serializationFeature.getEncoding().equals("") ? "utf-8" : this.serializationFeature.getEncoding();
/*     */     }
/*     */ 
/*     */     
/* 407 */     if (packet != null && packet.endpoint != null) {
/*     */       
/* 409 */       String charset = (String)packet.invocationProperties.get("decodedMessageCharset");
/* 410 */       return (charset == null) ? "utf-8" : charset;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 415 */     return "utf-8";
/*     */   }
/*     */   
/*     */   protected ContentTypeImpl.Builder getContenTypeBuilder(Packet packet) {
/* 419 */     ContentTypeImpl.Builder b = new ContentTypeImpl.Builder();
/* 420 */     String encoding = getPacketEncoding(packet);
/* 421 */     if ("utf-8".equalsIgnoreCase(encoding)) {
/* 422 */       b.contentType = getDefaultContentType();
/* 423 */       b.charset = "utf-8";
/* 424 */       return b;
/*     */     } 
/* 426 */     b.contentType = getMimeType() + " ;charset=" + encoding;
/* 427 */     b.charset = encoding;
/* 428 */     return b;
/*     */   }
/*     */   
/*     */   protected abstract ContentType getContentType(Packet paramPacket);
/*     */   
/*     */   protected abstract String getDefaultContentType();
/*     */   
/*     */   protected abstract List<String> getExpectedContentTypes();
/*     */   
/*     */   protected abstract StreamHeader createHeader(XMLStreamReader paramXMLStreamReader, XMLStreamBuffer paramXMLStreamBuffer);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\StreamSOAPCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */