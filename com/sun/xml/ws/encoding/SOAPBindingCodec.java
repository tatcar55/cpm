/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.oracle.webservices.api.message.ContentType;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.client.SelectOptimalEncodingFeature;
/*     */ import com.sun.xml.ws.api.fastinfoset.FastInfosetFeature;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.Codecs;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.pipe.SOAPBindingCodec;
/*     */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.ws.client.ContentNegotiation;
/*     */ import com.sun.xml.ws.protocol.soap.MessageCreationException;
/*     */ import com.sun.xml.ws.resources.StreamingMessages;
/*     */ import com.sun.xml.ws.server.UnsupportedMediaException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.MTOMFeature;
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
/*     */ 
/*     */ 
/*     */ public class SOAPBindingCodec
/*     */   extends MimeCodec
/*     */   implements SOAPBindingCodec
/*     */ {
/*     */   public static final String UTF8_ENCODING = "utf-8";
/*     */   public static final String DEFAULT_ENCODING = "utf-8";
/*     */   private boolean isFastInfosetDisabled;
/*     */   private boolean useFastInfosetForEncoding;
/*     */   private boolean ignoreContentNegotiationProperty;
/*     */   private final StreamSOAPCodec xmlSoapCodec;
/*     */   private final Codec fiSoapCodec;
/*     */   private final MimeCodec xmlMtomCodec;
/*     */   private final MimeCodec xmlSwaCodec;
/*     */   private final MimeCodec fiSwaCodec;
/*     */   private final String xmlMimeType;
/*     */   private final String fiMimeType;
/*     */   private final String xmlAccept;
/*     */   private final String connegXmlAccept;
/*     */   
/*     */   public StreamSOAPCodec getXMLCodec() {
/* 149 */     return this.xmlSoapCodec;
/*     */   }
/*     */   
/*     */   private ContentTypeImpl setAcceptHeader(Packet p, ContentTypeImpl c) {
/*     */     String _accept;
/* 154 */     if (!this.ignoreContentNegotiationProperty && p.contentNegotiation != ContentNegotiation.none) {
/* 155 */       _accept = this.connegXmlAccept;
/*     */     } else {
/* 157 */       _accept = this.xmlAccept;
/*     */     } 
/* 159 */     c.setAcceptHeader(_accept);
/* 160 */     return c;
/*     */   }
/*     */   
/*     */   public SOAPBindingCodec(WSFeatureList features) {
/* 164 */     this(features, Codecs.createSOAPEnvelopeXmlCodec(features));
/*     */   }
/*     */   
/*     */   public SOAPBindingCodec(WSFeatureList features, StreamSOAPCodec xmlSoapCodec) {
/* 168 */     super(WebServiceFeatureList.getSoapVersion(features), features);
/*     */     
/* 170 */     this.xmlSoapCodec = xmlSoapCodec;
/* 171 */     this.xmlMimeType = xmlSoapCodec.getMimeType();
/*     */     
/* 173 */     this.xmlMtomCodec = new MtomCodec(this.version, xmlSoapCodec, features);
/*     */     
/* 175 */     this.xmlSwaCodec = new SwACodec(this.version, features, (Codec)xmlSoapCodec);
/*     */     
/* 177 */     String clientAcceptedContentTypes = xmlSoapCodec.getMimeType() + ", " + this.xmlMtomCodec.getMimeType();
/*     */ 
/*     */     
/* 180 */     WebServiceFeature fi = features.get(FastInfosetFeature.class);
/* 181 */     this.isFastInfosetDisabled = (fi != null && !fi.isEnabled());
/* 182 */     if (!this.isFastInfosetDisabled) {
/* 183 */       this.fiSoapCodec = getFICodec(xmlSoapCodec, this.version);
/* 184 */       if (this.fiSoapCodec != null) {
/* 185 */         this.fiMimeType = this.fiSoapCodec.getMimeType();
/* 186 */         this.fiSwaCodec = new SwACodec(this.version, features, this.fiSoapCodec);
/* 187 */         this.connegXmlAccept = this.fiMimeType + ", " + clientAcceptedContentTypes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 195 */         WebServiceFeature select = features.get(SelectOptimalEncodingFeature.class);
/* 196 */         if (select != null) {
/* 197 */           this.ignoreContentNegotiationProperty = true;
/* 198 */           if (select.isEnabled()) {
/*     */             
/* 200 */             if (fi != null) {
/* 201 */               this.useFastInfosetForEncoding = true;
/*     */             }
/*     */             
/* 204 */             clientAcceptedContentTypes = this.connegXmlAccept;
/*     */           } else {
/* 206 */             this.isFastInfosetDisabled = true;
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 211 */         this.isFastInfosetDisabled = true;
/* 212 */         this.fiSwaCodec = null;
/* 213 */         this.fiMimeType = "";
/* 214 */         this.connegXmlAccept = clientAcceptedContentTypes;
/* 215 */         this.ignoreContentNegotiationProperty = true;
/*     */       } 
/*     */     } else {
/*     */       
/* 219 */       this.fiSoapCodec = this.fiSwaCodec = null;
/* 220 */       this.fiMimeType = "";
/* 221 */       this.connegXmlAccept = clientAcceptedContentTypes;
/* 222 */       this.ignoreContentNegotiationProperty = true;
/*     */     } 
/*     */     
/* 225 */     this.xmlAccept = clientAcceptedContentTypes;
/*     */     
/* 227 */     if (WebServiceFeatureList.getSoapVersion(features) == null)
/* 228 */       throw new WebServiceException("Expecting a SOAP binding but found "); 
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/* 232 */     return null;
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 236 */     ContentType toAdapt = getEncoder(packet).getStaticContentType(packet);
/* 237 */     return setAcceptHeader(packet, (ContentTypeImpl)toAdapt);
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) throws IOException {
/* 241 */     preEncode(packet);
/* 242 */     ContentType ct = getEncoder(packet).encode(packet, out);
/* 243 */     ct = setAcceptHeader(packet, (ContentTypeImpl)ct);
/* 244 */     postEncode();
/* 245 */     return ct;
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 249 */     preEncode(packet);
/* 250 */     ContentType ct = getEncoder(packet).encode(packet, buffer);
/* 251 */     ct = setAcceptHeader(packet, (ContentTypeImpl)ct);
/* 252 */     postEncode();
/* 253 */     return ct;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void preEncode(Packet p) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void postEncode() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void preDecode(Packet p) {
/* 275 */     if (p.contentNegotiation == null) {
/* 276 */       this.useFastInfosetForEncoding = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void postDecode(Packet p) {
/* 284 */     p.setFastInfosetDisabled(this.isFastInfosetDisabled);
/* 285 */     if (this.features.isEnabled(MTOMFeature.class)) p.checkMtomAcceptable();
/*     */     
/* 287 */     MTOMFeature mtomFeature = (MTOMFeature)this.features.get(MTOMFeature.class);
/* 288 */     if (mtomFeature != null) {
/* 289 */       p.setMtomFeature(mtomFeature);
/*     */     }
/* 291 */     if (!this.useFastInfosetForEncoding) {
/* 292 */       this.useFastInfosetForEncoding = p.getFastInfosetAcceptable(this.fiMimeType).booleanValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/* 298 */     if (contentType == null) {
/* 299 */       contentType = this.xmlMimeType;
/*     */     }
/* 301 */     packet.setContentType((ContentType)new ContentTypeImpl(contentType));
/* 302 */     preDecode(packet);
/*     */     try {
/* 304 */       if (isMultipartRelated(contentType))
/*     */       
/* 306 */       { super.decode(in, contentType, packet); }
/* 307 */       else if (isFastInfoset(contentType))
/* 308 */       { if (!this.ignoreContentNegotiationProperty && packet.contentNegotiation == ContentNegotiation.none) {
/* 309 */           throw noFastInfosetForDecoding();
/*     */         }
/* 311 */         this.useFastInfosetForEncoding = true;
/* 312 */         this.fiSoapCodec.decode(in, contentType, packet); }
/*     */       else
/* 314 */       { this.xmlSoapCodec.decode(in, contentType, packet); } 
/* 315 */     } catch (RuntimeException we) {
/* 316 */       if (we instanceof com.sun.xml.ws.api.message.ExceptionHasMessage || we instanceof UnsupportedMediaException) {
/* 317 */         throw we;
/*     */       }
/* 319 */       throw new MessageCreationException(this.version, new Object[] { we });
/*     */     } 
/*     */     
/* 322 */     postDecode(packet);
/*     */   }
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet packet) {
/* 326 */     if (contentType == null) {
/* 327 */       throw new UnsupportedMediaException();
/*     */     }
/*     */     
/* 330 */     preDecode(packet);
/*     */     try {
/* 332 */       if (isMultipartRelated(contentType))
/* 333 */       { super.decode(in, contentType, packet); }
/* 334 */       else if (isFastInfoset(contentType))
/* 335 */       { if (packet.contentNegotiation == ContentNegotiation.none) {
/* 336 */           throw noFastInfosetForDecoding();
/*     */         }
/* 338 */         this.useFastInfosetForEncoding = true;
/* 339 */         this.fiSoapCodec.decode(in, contentType, packet); }
/*     */       else
/* 341 */       { this.xmlSoapCodec.decode(in, contentType, packet); } 
/* 342 */     } catch (RuntimeException we) {
/* 343 */       if (we instanceof com.sun.xml.ws.api.message.ExceptionHasMessage || we instanceof UnsupportedMediaException) {
/* 344 */         throw we;
/*     */       }
/* 346 */       throw new MessageCreationException(this.version, new Object[] { we });
/*     */     } 
/*     */     
/* 349 */     postDecode(packet);
/*     */   }
/*     */   
/*     */   public SOAPBindingCodec copy() {
/* 353 */     return new SOAPBindingCodec(this.features, (StreamSOAPCodec)this.xmlSoapCodec.copy());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(MimeMultipartParser mpp, Packet packet) throws IOException {
/* 359 */     String rootContentType = mpp.getRootPart().getContentType();
/* 360 */     boolean isMTOM = isApplicationXopXml(rootContentType);
/* 361 */     packet.setMtomRequest(Boolean.valueOf(isMTOM));
/* 362 */     if (isMTOM) {
/* 363 */       this.xmlMtomCodec.decode(mpp, packet);
/* 364 */     } else if (isFastInfoset(rootContentType)) {
/* 365 */       if (packet.contentNegotiation == ContentNegotiation.none) {
/* 366 */         throw noFastInfosetForDecoding();
/*     */       }
/* 368 */       this.useFastInfosetForEncoding = true;
/* 369 */       this.fiSwaCodec.decode(mpp, packet);
/* 370 */     } else if (isXml(rootContentType)) {
/* 371 */       this.xmlSwaCodec.decode(mpp, packet);
/*     */     } else {
/*     */       
/* 374 */       throw new IOException("");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isMultipartRelated(String contentType) {
/* 380 */     return compareStrings(contentType, "multipart/related");
/*     */   }
/*     */   
/*     */   private boolean isApplicationXopXml(String contentType) {
/* 384 */     return compareStrings(contentType, "application/xop+xml");
/*     */   }
/*     */   
/*     */   private boolean isXml(String contentType) {
/* 388 */     return compareStrings(contentType, this.xmlMimeType);
/*     */   }
/*     */   
/*     */   private boolean isFastInfoset(String contentType) {
/* 392 */     if (this.isFastInfosetDisabled) return false;
/*     */     
/* 394 */     return compareStrings(contentType, this.fiMimeType);
/*     */   }
/*     */   
/*     */   private boolean compareStrings(String a, String b) {
/* 398 */     return (a.length() >= b.length() && b.equalsIgnoreCase(a.substring(0, b.length())));
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
/*     */   private Codec getEncoder(Packet p) {
/* 442 */     if (!this.ignoreContentNegotiationProperty) {
/* 443 */       if (p.contentNegotiation == ContentNegotiation.none) {
/*     */ 
/*     */         
/* 446 */         this.useFastInfosetForEncoding = false;
/* 447 */       } else if (p.contentNegotiation == ContentNegotiation.optimistic) {
/*     */         
/* 449 */         this.useFastInfosetForEncoding = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 455 */     if (this.useFastInfosetForEncoding) {
/* 456 */       Message message = p.getMessage();
/* 457 */       if (message == null || message.getAttachments().isEmpty() || this.features.isEnabled(MTOMFeature.class)) {
/* 458 */         return this.fiSoapCodec;
/*     */       }
/* 460 */       return this.fiSwaCodec;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 465 */     if (p.getBinding() == null && 
/* 466 */       this.features != null) {
/* 467 */       p.setMtomFeature((MTOMFeature)this.features.get(MTOMFeature.class));
/*     */     }
/*     */ 
/*     */     
/* 471 */     if (p.shouldUseMtom()) {
/* 472 */       return this.xmlMtomCodec;
/*     */     }
/*     */     
/* 475 */     Message m = p.getMessage();
/* 476 */     if (m == null || m.getAttachments().isEmpty()) {
/* 477 */       return (Codec)this.xmlSoapCodec;
/*     */     }
/* 479 */     return this.xmlSwaCodec;
/*     */   }
/*     */   
/*     */   private RuntimeException noFastInfosetForDecoding() {
/* 483 */     return new RuntimeException(StreamingMessages.FASTINFOSET_DECODING_NOT_ACCEPTED());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Codec getFICodec(StreamSOAPCodec soapCodec, SOAPVersion version) {
/*     */     try {
/* 491 */       Class<?> c = Class.forName("com.sun.xml.ws.encoding.fastinfoset.FastInfosetStreamSOAPCodec");
/* 492 */       Method m = c.getMethod("create", new Class[] { StreamSOAPCodec.class, SOAPVersion.class });
/* 493 */       return (Codec)m.invoke(null, new Object[] { soapCodec, version });
/* 494 */     } catch (Exception e) {
/*     */       
/* 496 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\SOAPBindingCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */