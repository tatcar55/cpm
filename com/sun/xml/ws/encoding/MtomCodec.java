/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.oracle.webservices.api.message.ContentType;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import com.sun.xml.ws.developer.SerializationFeature;
/*     */ import com.sun.xml.ws.developer.StreamingDataHandler;
/*     */ import com.sun.xml.ws.message.MimeAttachmentSet;
/*     */ import com.sun.xml.ws.server.UnsupportedMediaException;
/*     */ import com.sun.xml.ws.streaming.MtomStreamWriter;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.ws.streaming.XMLStreamWriterUtil;
/*     */ import com.sun.xml.ws.util.ByteArrayDataSource;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamReaderFilter;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamWriterFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ import org.jvnet.staxex.Base64Data;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
/*     */ import org.jvnet.staxex.XMLStreamReaderEx;
/*     */ import org.jvnet.staxex.XMLStreamWriterEx;
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
/*     */ public class MtomCodec
/*     */   extends MimeCodec
/*     */ {
/*     */   public static final String XOP_XML_MIME_TYPE = "application/xop+xml";
/*     */   private static final String XOP_LOCALNAME = "Include";
/*     */   private static final String XOP_NAMESPACEURI = "http://www.w3.org/2004/08/xop/include";
/*     */   private final StreamSOAPCodec codec;
/*     */   private final MTOMFeature mtomFeature;
/*     */   private final SerializationFeature sf;
/*     */   private static final String DECODED_MESSAGE_CHARSET = "decodedMessageCharset";
/*     */   
/*     */   MtomCodec(SOAPVersion version, StreamSOAPCodec codec, WSFeatureList features) {
/* 108 */     super(version, features);
/* 109 */     this.codec = codec;
/* 110 */     this.sf = (SerializationFeature)features.get(SerializationFeature.class);
/* 111 */     MTOMFeature mtom = (MTOMFeature)features.get(MTOMFeature.class);
/* 112 */     if (mtom == null) {
/* 113 */       this.mtomFeature = new MTOMFeature();
/*     */     } else {
/* 115 */       this.mtomFeature = mtom;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 125 */     return getStaticContentTypeStatic(packet, this.version);
/*     */   }
/*     */   
/*     */   public static ContentType getStaticContentTypeStatic(Packet packet, SOAPVersion version) {
/* 129 */     ContentType ct = (ContentType)packet.getInternalContentType();
/* 130 */     if (ct != null) return ct;
/*     */     
/* 132 */     String uuid = UUID.randomUUID().toString();
/* 133 */     String boundary = "uuid:" + uuid;
/* 134 */     String rootId = "<rootpart*" + uuid + "@example.jaxws.sun.com>";
/* 135 */     String soapActionParameter = SOAPVersion.SOAP_11.equals(version) ? null : createActionParameter(packet);
/*     */     
/* 137 */     String boundaryParameter = "boundary=\"" + boundary + "\"";
/* 138 */     String messageContentType = "multipart/related;start=\"" + rootId + "\"" + ";type=\"" + "application/xop+xml" + "\";" + boundaryParameter + ";start-info=\"" + version.contentType + ((soapActionParameter == null) ? "" : soapActionParameter) + "\"";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     if (SOAPVersion.SOAP_11.equals(version)) {  } else {  }  ContentTypeImpl ctImpl = new ContentTypeImpl(messageContentType, null, null);
/*     */ 
/*     */     
/* 149 */     ctImpl.setBoundary(boundary);
/* 150 */     ctImpl.setRootId(rootId);
/* 151 */     packet.setContentType((ContentType)ctImpl);
/* 152 */     return ctImpl;
/*     */   }
/*     */   
/*     */   private static String createActionParameter(Packet packet) {
/* 156 */     return (packet.soapAction != null) ? (";action=\\\"" + packet.soapAction + "\\\"") : "";
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) throws IOException {
/* 161 */     ContentTypeImpl ctImpl = (ContentTypeImpl)getStaticContentType(packet);
/* 162 */     String boundary = ctImpl.getBoundary();
/* 163 */     String rootId = ctImpl.getRootId();
/*     */     
/* 165 */     if (packet.getMessage() != null) {
/*     */       try {
/* 167 */         String encoding = getPacketEncoding(packet);
/* 168 */         packet.invocationProperties.remove("decodedMessageCharset");
/*     */         
/* 170 */         String actionParameter = getActionParameter(packet, this.version);
/* 171 */         String soapXopContentType = getSOAPXopContentType(encoding, this.version, actionParameter);
/*     */         
/* 173 */         writeln("--" + boundary, out);
/* 174 */         writeMimeHeaders(soapXopContentType, rootId, out);
/*     */ 
/*     */         
/* 177 */         List<ByteArrayBuffer> mtomAttachments = new ArrayList<ByteArrayBuffer>();
/* 178 */         MtomStreamWriterImpl writer = new MtomStreamWriterImpl(XMLStreamWriterFactory.create(out, encoding), mtomAttachments, boundary, this.mtomFeature);
/*     */ 
/*     */         
/* 181 */         packet.getMessage().writeTo((XMLStreamWriter)writer);
/* 182 */         XMLStreamWriterFactory.recycle((XMLStreamWriter)writer);
/* 183 */         writeln(out);
/*     */         
/* 185 */         for (ByteArrayBuffer bos : mtomAttachments) {
/* 186 */           bos.write(out);
/*     */         }
/*     */ 
/*     */         
/* 190 */         writeAttachments(packet.getMessage().getAttachments(), out, boundary);
/*     */ 
/*     */         
/* 193 */         writeAsAscii("--" + boundary, out);
/* 194 */         writeAsAscii("--", out);
/*     */       }
/* 196 */       catch (XMLStreamException e) {
/* 197 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 202 */     return ctImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSOAPXopContentType(String encoding, SOAPVersion version, String actionParameter) {
/* 207 */     return "application/xop+xml;charset=" + encoding + ";type=\"" + version.contentType + actionParameter + "\"";
/*     */   }
/*     */   
/*     */   public static String getActionParameter(Packet packet, SOAPVersion version) {
/* 211 */     return (version == SOAPVersion.SOAP_11) ? "" : createActionParameter(packet);
/*     */   }
/*     */   
/*     */   public static class ByteArrayBuffer
/*     */   {
/*     */     final String contentId;
/*     */     private final DataHandler dh;
/*     */     private final String boundary;
/*     */     
/*     */     ByteArrayBuffer(@NotNull DataHandler dh, String b) {
/* 221 */       this.dh = dh;
/* 222 */       this.contentId = MtomCodec.encodeCid();
/* 223 */       this.boundary = b;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(OutputStream os) throws IOException {
/* 228 */       MimeCodec.writeln("--" + this.boundary, os);
/* 229 */       MtomCodec.writeMimeHeaders(this.dh.getContentType(), this.contentId, os);
/* 230 */       this.dh.writeTo(os);
/* 231 */       MimeCodec.writeln(os);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void writeMimeHeaders(String contentType, String contentId, OutputStream out) throws IOException {
/* 236 */     String cid = contentId;
/* 237 */     if (cid != null && cid.length() > 0 && cid.charAt(0) != '<')
/* 238 */       cid = '<' + cid + '>'; 
/* 239 */     writeln("Content-Id: " + cid, out);
/* 240 */     writeln("Content-Type: " + contentType, out);
/* 241 */     writeln("Content-Transfer-Encoding: binary", out);
/* 242 */     writeln(out);
/*     */   }
/*     */   
/*     */   private void writeAttachments(AttachmentSet attachments, OutputStream out, String boundary) throws IOException {
/* 246 */     for (Attachment att : attachments) {
/*     */       
/* 248 */       writeln("--" + boundary, out);
/* 249 */       writeMimeHeaders(att.getContentType(), att.getContentId(), out);
/* 250 */       att.writeTo(out);
/* 251 */       writeln(out);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 257 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public MtomCodec copy() {
/* 262 */     return new MtomCodec(this.version, (StreamSOAPCodec)this.codec.copy(), this.features);
/*     */   }
/*     */   
/*     */   private static String encodeCid() {
/* 266 */     String cid = "example.jaxws.sun.com";
/* 267 */     String name = UUID.randomUUID() + "@";
/* 268 */     return name + cid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(MimeMultipartParser mpp, Packet packet) throws IOException {
/* 275 */     String charset = null;
/* 276 */     String ct = mpp.getRootPart().getContentType();
/* 277 */     if (ct != null) {
/* 278 */       charset = (new ContentTypeImpl(ct)).getCharSet();
/*     */     }
/* 280 */     if (charset != null && !Charset.isSupported(charset)) {
/* 281 */       throw new UnsupportedMediaException(charset);
/*     */     }
/*     */     
/* 284 */     if (charset != null) {
/* 285 */       packet.invocationProperties.put("decodedMessageCharset", charset);
/*     */     } else {
/* 287 */       packet.invocationProperties.remove("decodedMessageCharset");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 292 */     MtomXMLStreamReaderEx mtomXMLStreamReaderEx = new MtomXMLStreamReaderEx(mpp, XMLStreamReaderFactory.create(null, mpp.getRootPart().asInputStream(), charset, true));
/*     */ 
/*     */ 
/*     */     
/* 296 */     packet.setMessage(this.codec.decode((XMLStreamReader)mtomXMLStreamReaderEx, (AttachmentSet)new MimeAttachmentSet(mpp)));
/* 297 */     packet.setMtomFeature(this.mtomFeature);
/* 298 */     packet.setContentType((ContentType)mpp.getContentType());
/*     */   }
/*     */ 
/*     */   
/*     */   private String getPacketEncoding(Packet packet) {
/* 303 */     if (this.sf != null && this.sf.getEncoding() != null) {
/* 304 */       return this.sf.getEncoding().equals("") ? "utf-8" : this.sf.getEncoding();
/*     */     }
/* 306 */     return determinePacketEncoding(packet);
/*     */   }
/*     */   
/*     */   public static String determinePacketEncoding(Packet packet) {
/* 310 */     if (packet != null && packet.endpoint != null) {
/*     */       
/* 312 */       String charset = (String)packet.invocationProperties.get("decodedMessageCharset");
/* 313 */       return (charset == null) ? "utf-8" : charset;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 318 */     return "utf-8";
/*     */   }
/*     */   
/*     */   public static class MtomStreamWriterImpl extends XMLStreamWriterFilter implements XMLStreamWriterEx, MtomStreamWriter, HasEncoding {
/*     */     private final List<MtomCodec.ByteArrayBuffer> mtomAttachments;
/*     */     private final String boundary;
/*     */     private final MTOMFeature myMtomFeature;
/*     */     
/*     */     public MtomStreamWriterImpl(XMLStreamWriter w, List<MtomCodec.ByteArrayBuffer> mtomAttachments, String b, MTOMFeature myMtomFeature) {
/* 327 */       super(w);
/* 328 */       this.mtomAttachments = mtomAttachments;
/* 329 */       this.boundary = b;
/* 330 */       this.myMtomFeature = myMtomFeature;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeBinary(byte[] data, int start, int len, String contentType) throws XMLStreamException {
/* 336 */       if (this.myMtomFeature.getThreshold() > len) {
/* 337 */         writeCharacters(DatatypeConverterImpl._printBase64Binary(data, start, len));
/*     */         return;
/*     */       } 
/* 340 */       MtomCodec.ByteArrayBuffer bab = new MtomCodec.ByteArrayBuffer(new DataHandler((DataSource)new ByteArrayDataSource(data, start, len, contentType)), this.boundary);
/* 341 */       writeBinary(bab);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeBinary(DataHandler dataHandler) throws XMLStreamException {
/* 347 */       writeBinary(new MtomCodec.ByteArrayBuffer(dataHandler, this.boundary));
/*     */     }
/*     */ 
/*     */     
/*     */     public OutputStream writeBinary(String contentType) throws XMLStreamException {
/* 352 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePCDATA(CharSequence data) throws XMLStreamException {
/* 357 */       if (data == null)
/*     */         return; 
/* 359 */       if (data instanceof Base64Data) {
/* 360 */         Base64Data binaryData = (Base64Data)data;
/* 361 */         writeBinary(binaryData.getDataHandler());
/*     */         return;
/*     */       } 
/* 364 */       writeCharacters(data.toString());
/*     */     }
/*     */     
/*     */     private void writeBinary(MtomCodec.ByteArrayBuffer bab) {
/*     */       try {
/* 369 */         this.mtomAttachments.add(bab);
/* 370 */         this.writer.setPrefix("xop", "http://www.w3.org/2004/08/xop/include");
/* 371 */         this.writer.writeNamespace("xop", "http://www.w3.org/2004/08/xop/include");
/* 372 */         this.writer.writeStartElement("http://www.w3.org/2004/08/xop/include", "Include");
/* 373 */         this.writer.writeAttribute("href", "cid:" + bab.contentId);
/* 374 */         this.writer.writeEndElement();
/* 375 */         this.writer.flush();
/* 376 */       } catch (XMLStreamException e) {
/* 377 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getProperty(String name) throws IllegalArgumentException {
/* 384 */       if (name.equals("sjsxp-outputstream") && this.writer instanceof Map) {
/* 385 */         Object obj = ((Map)this.writer).get("sjsxp-outputstream");
/* 386 */         if (obj != null) {
/* 387 */           return obj;
/*     */         }
/*     */       } 
/* 390 */       return super.getProperty(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AttachmentMarshaller getAttachmentMarshaller() {
/* 400 */       return new AttachmentMarshaller()
/*     */         {
/*     */ 
/*     */           
/*     */           public String addMtomAttachment(DataHandler data, String elementNamespace, String elementLocalName)
/*     */           {
/* 406 */             MtomCodec.ByteArrayBuffer bab = new MtomCodec.ByteArrayBuffer(data, MtomCodec.MtomStreamWriterImpl.this.boundary);
/* 407 */             MtomCodec.MtomStreamWriterImpl.this.mtomAttachments.add(bab);
/* 408 */             return "cid:" + bab.contentId;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public String addMtomAttachment(byte[] data, int offset, int length, String mimeType, String elementNamespace, String elementLocalName) {
/* 414 */             if (MtomCodec.MtomStreamWriterImpl.this.myMtomFeature.getThreshold() > length) {
/* 415 */               return null;
/*     */             }
/* 417 */             MtomCodec.ByteArrayBuffer bab = new MtomCodec.ByteArrayBuffer(new DataHandler((DataSource)new ByteArrayDataSource(data, offset, length, mimeType)), MtomCodec.MtomStreamWriterImpl.this.boundary);
/* 418 */             MtomCodec.MtomStreamWriterImpl.this.mtomAttachments.add(bab);
/* 419 */             return "cid:" + bab.contentId;
/*     */           }
/*     */ 
/*     */           
/*     */           public String addSwaRefAttachment(DataHandler data) {
/* 424 */             MtomCodec.ByteArrayBuffer bab = new MtomCodec.ByteArrayBuffer(data, MtomCodec.MtomStreamWriterImpl.this.boundary);
/* 425 */             MtomCodec.MtomStreamWriterImpl.this.mtomAttachments.add(bab);
/* 426 */             return "cid:" + bab.contentId;
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean isXOPPackage() {
/* 431 */             return true;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public List<MtomCodec.ByteArrayBuffer> getMtomAttachments() {
/* 437 */       return this.mtomAttachments;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getEncoding() {
/* 442 */       return XMLStreamWriterUtil.getEncoding(this.writer);
/*     */     }
/*     */     
/*     */     private static class MtomNamespaceContextEx implements NamespaceContextEx {
/*     */       private final NamespaceContext nsContext;
/*     */       
/*     */       public MtomNamespaceContextEx(NamespaceContext nsContext) {
/* 449 */         this.nsContext = nsContext;
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator<NamespaceContextEx.Binding> iterator() {
/* 454 */         throw new UnsupportedOperationException();
/*     */       }
/*     */ 
/*     */       
/*     */       public String getNamespaceURI(String prefix) {
/* 459 */         return this.nsContext.getNamespaceURI(prefix);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrefix(String namespaceURI) {
/* 464 */         return this.nsContext.getPrefix(namespaceURI);
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator getPrefixes(String namespaceURI) {
/* 469 */         return this.nsContext.getPrefixes(namespaceURI);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public NamespaceContextEx getNamespaceContext() {
/* 475 */       NamespaceContext nsContext = this.writer.getNamespaceContext();
/* 476 */       return new MtomNamespaceContextEx(nsContext);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MtomXMLStreamReaderEx
/*     */     extends XMLStreamReaderFilter
/*     */     implements XMLStreamReaderEx
/*     */   {
/*     */     private final MimeMultipartParser mimeMP;
/*     */     
/*     */     private boolean xopReferencePresent = false;
/*     */     
/*     */     private Base64Data base64AttData;
/*     */     
/*     */     private char[] base64EncodedText;
/*     */     private String xopHref;
/*     */     
/*     */     public MtomXMLStreamReaderEx(MimeMultipartParser mimeMP, XMLStreamReader reader) {
/* 495 */       super(reader);
/* 496 */       this.mimeMP = mimeMP;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSequence getPCDATA() throws XMLStreamException {
/* 501 */       if (this.xopReferencePresent) {
/* 502 */         return (CharSequence)this.base64AttData;
/*     */       }
/* 504 */       return this.reader.getText();
/*     */     }
/*     */ 
/*     */     
/*     */     public NamespaceContextEx getNamespaceContext() {
/* 509 */       NamespaceContext nsContext = this.reader.getNamespaceContext();
/* 510 */       return new MtomNamespaceContextEx(nsContext);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getElementTextTrim() throws XMLStreamException {
/* 515 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     private static class MtomNamespaceContextEx implements NamespaceContextEx {
/*     */       private final NamespaceContext nsContext;
/*     */       
/*     */       public MtomNamespaceContextEx(NamespaceContext nsContext) {
/* 522 */         this.nsContext = nsContext;
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator<NamespaceContextEx.Binding> iterator() {
/* 527 */         throw new UnsupportedOperationException();
/*     */       }
/*     */ 
/*     */       
/*     */       public String getNamespaceURI(String prefix) {
/* 532 */         return this.nsContext.getNamespaceURI(prefix);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrefix(String namespaceURI) {
/* 537 */         return this.nsContext.getPrefix(namespaceURI);
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator getPrefixes(String namespaceURI) {
/* 542 */         return this.nsContext.getPrefixes(namespaceURI);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getTextLength() {
/* 549 */       if (this.xopReferencePresent) {
/* 550 */         return this.base64AttData.length();
/*     */       }
/* 552 */       return this.reader.getTextLength();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTextStart() {
/* 557 */       if (this.xopReferencePresent) {
/* 558 */         return 0;
/*     */       }
/* 560 */       return this.reader.getTextStart();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getEventType() {
/* 565 */       if (this.xopReferencePresent)
/* 566 */         return 4; 
/* 567 */       return super.getEventType();
/*     */     }
/*     */ 
/*     */     
/*     */     public int next() throws XMLStreamException {
/* 572 */       int event = this.reader.next();
/* 573 */       if (event == 1 && this.reader.getLocalName().equals("Include") && this.reader.getNamespaceURI().equals("http://www.w3.org/2004/08/xop/include")) {
/*     */         
/* 575 */         String href = this.reader.getAttributeValue(null, "href");
/*     */         try {
/* 577 */           this.xopHref = href;
/* 578 */           Attachment att = getAttachment(href);
/* 579 */           if (att != null) {
/* 580 */             DataHandler dh = att.asDataHandler();
/* 581 */             if (dh instanceof StreamingDataHandler) {
/* 582 */               ((StreamingDataHandler)dh).setHrefCid(att.getContentId());
/*     */             }
/* 584 */             this.base64AttData = new Base64Data();
/* 585 */             this.base64AttData.set(dh);
/*     */           } 
/* 587 */           this.xopReferencePresent = true;
/* 588 */         } catch (IOException e) {
/* 589 */           throw new WebServiceException(e);
/*     */         } 
/*     */         
/* 592 */         XMLStreamReaderUtil.nextElementContent(this.reader);
/* 593 */         return 4;
/*     */       } 
/* 595 */       if (this.xopReferencePresent) {
/* 596 */         this.xopReferencePresent = false;
/* 597 */         this.base64EncodedText = null;
/* 598 */         this.xopHref = null;
/*     */       } 
/* 600 */       return event;
/*     */     }
/*     */     
/*     */     private String decodeCid(String cid) {
/*     */       try {
/* 605 */         cid = URLDecoder.decode(cid, "utf-8");
/* 606 */       } catch (UnsupportedEncodingException e) {}
/*     */ 
/*     */       
/* 609 */       return cid;
/*     */     }
/*     */     
/*     */     private Attachment getAttachment(String cid) throws IOException {
/* 613 */       if (cid.startsWith("cid:"))
/* 614 */         cid = cid.substring(4, cid.length()); 
/* 615 */       if (cid.indexOf('%') != -1) {
/* 616 */         cid = decodeCid(cid);
/* 617 */         return this.mimeMP.getAttachmentPart(cid);
/*     */       } 
/* 619 */       return this.mimeMP.getAttachmentPart(cid);
/*     */     }
/*     */ 
/*     */     
/*     */     public char[] getTextCharacters() {
/* 624 */       if (this.xopReferencePresent) {
/* 625 */         char[] chars = new char[this.base64AttData.length()];
/* 626 */         this.base64AttData.writeTo(chars, 0);
/* 627 */         return chars;
/*     */       } 
/* 629 */       return this.reader.getTextCharacters();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
/* 634 */       if (this.xopReferencePresent) {
/* 635 */         if (target == null) {
/* 636 */           throw new NullPointerException("target char array can't be null");
/*     */         }
/*     */         
/* 639 */         if (targetStart < 0 || length < 0 || sourceStart < 0 || targetStart >= target.length || targetStart + length > target.length)
/*     */         {
/* 641 */           throw new IndexOutOfBoundsException();
/*     */         }
/*     */         
/* 644 */         int textLength = this.base64AttData.length();
/* 645 */         if (sourceStart > textLength) {
/* 646 */           throw new IndexOutOfBoundsException();
/*     */         }
/* 648 */         if (this.base64EncodedText == null) {
/* 649 */           this.base64EncodedText = new char[this.base64AttData.length()];
/* 650 */           this.base64AttData.writeTo(this.base64EncodedText, 0);
/*     */         } 
/*     */         
/* 653 */         int copiedLength = Math.min(textLength - sourceStart, length);
/* 654 */         System.arraycopy(this.base64EncodedText, sourceStart, target, targetStart, copiedLength);
/* 655 */         return copiedLength;
/*     */       } 
/* 657 */       return this.reader.getTextCharacters(sourceStart, target, targetStart, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getText() {
/* 662 */       if (this.xopReferencePresent) {
/* 663 */         return this.base64AttData.toString();
/*     */       }
/* 665 */       return this.reader.getText();
/*     */     }
/*     */     
/*     */     protected boolean isXopReference() throws XMLStreamException {
/* 669 */       return this.xopReferencePresent;
/*     */     }
/*     */     
/*     */     protected String getXopHref() {
/* 673 */       return this.xopHref;
/*     */     }
/*     */     
/*     */     public MimeMultipartParser getMimeMultipartParser() {
/* 677 */       return this.mimeMP;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\MtomCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */