/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.oracle.webservices.api.message.ContentType;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.client.ContentNegotiation;
/*     */ import com.sun.xml.ws.encoding.xml.XMLCodec;
/*     */ import com.sun.xml.ws.encoding.xml.XMLMessage;
/*     */ import com.sun.xml.ws.resources.StreamingMessages;
/*     */ import com.sun.xml.ws.util.ByteArrayBuffer;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.activation.DataSource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMLHTTPBindingCodec
/*     */   extends MimeCodec
/*     */ {
/*     */   private static final String BASE_ACCEPT_VALUE = "*";
/*     */   private static final String APPLICATION_FAST_INFOSET_MIME_TYPE = "application/fastinfoset";
/*     */   private boolean useFastInfosetForEncoding;
/*     */   private final Codec xmlCodec;
/*     */   private final Codec fiCodec;
/* 106 */   private static final String xmlAccept = null;
/*     */ 
/*     */   
/*     */   private static final String fiXmlAccept = "application/fastinfoset, *";
/*     */ 
/*     */ 
/*     */   
/*     */   private ContentTypeImpl setAcceptHeader(Packet p, ContentType c) {
/* 114 */     ContentTypeImpl ctImpl = (ContentTypeImpl)c;
/* 115 */     if (p.contentNegotiation == ContentNegotiation.optimistic || p.contentNegotiation == ContentNegotiation.pessimistic) {
/*     */       
/* 117 */       ctImpl.setAcceptHeader("application/fastinfoset, *");
/*     */     } else {
/* 119 */       ctImpl.setAcceptHeader(xmlAccept);
/*     */     } 
/* 121 */     p.setContentType((ContentType)ctImpl);
/* 122 */     return ctImpl;
/*     */   }
/*     */   
/*     */   public XMLHTTPBindingCodec(WSFeatureList f) {
/* 126 */     super(SOAPVersion.SOAP_11, f);
/*     */     
/* 128 */     this.xmlCodec = (Codec)new XMLCodec(f);
/*     */     
/* 130 */     this.fiCodec = getFICodec();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 141 */     if (packet.getInternalMessage() instanceof XMLMessage.MessageDataSource) {
/* 142 */       XMLMessage.MessageDataSource mds = (XMLMessage.MessageDataSource)packet.getInternalMessage();
/* 143 */       if (mds.hasUnconsumedDataSource()) {
/* 144 */         ContentType contentType = getStaticContentType(mds);
/* 145 */         return (contentType != null) ? setAcceptHeader(packet, contentType) : null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 151 */     ContentType ct = super.getStaticContentType(packet);
/* 152 */     return (ct != null) ? setAcceptHeader(packet, ct) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) throws IOException {
/* 159 */     if (packet.getInternalMessage() instanceof XMLMessage.MessageDataSource) {
/* 160 */       XMLMessage.MessageDataSource mds = (XMLMessage.MessageDataSource)packet.getInternalMessage();
/* 161 */       if (mds.hasUnconsumedDataSource()) {
/* 162 */         return setAcceptHeader(packet, encode(mds, out));
/*     */       }
/*     */     } 
/* 165 */     return setAcceptHeader(packet, super.encode(packet, out));
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 170 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/* 179 */     if (packet.contentNegotiation == null) {
/* 180 */       this.useFastInfosetForEncoding = false;
/*     */     }
/* 182 */     if (contentType == null) {
/* 183 */       this.xmlCodec.decode(in, contentType, packet);
/* 184 */     } else if (isMultipartRelated(contentType)) {
/* 185 */       packet.setMessage((Message)new XMLMessage.XMLMultiPart(contentType, in, this.features));
/* 186 */     } else if (isFastInfoset(contentType)) {
/* 187 */       if (this.fiCodec == null) {
/* 188 */         throw new RuntimeException(StreamingMessages.FASTINFOSET_NO_IMPLEMENTATION());
/*     */       }
/*     */       
/* 191 */       this.useFastInfosetForEncoding = true;
/* 192 */       this.fiCodec.decode(in, contentType, packet);
/* 193 */     } else if (isXml(contentType)) {
/* 194 */       this.xmlCodec.decode(in, contentType, packet);
/*     */     } else {
/* 196 */       packet.setMessage((Message)new XMLMessage.UnknownContent(contentType, in));
/*     */     } 
/*     */     
/* 199 */     if (!this.useFastInfosetForEncoding) {
/* 200 */       this.useFastInfosetForEncoding = isFastInfosetAcceptable(packet.acceptableMimeTypes);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(MimeMultipartParser mpp, Packet packet) throws IOException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public MimeCodec copy() {
/* 211 */     return new XMLHTTPBindingCodec(this.features);
/*     */   }
/*     */   
/*     */   private boolean isMultipartRelated(String contentType) {
/* 215 */     return compareStrings(contentType, "multipart/related");
/*     */   }
/*     */   
/*     */   private boolean isXml(String contentType) {
/* 219 */     return (compareStrings(contentType, "application/xml") || compareStrings(contentType, "text/xml") || (compareStrings(contentType, "application/") && contentType.toLowerCase().indexOf("+xml") != -1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isFastInfoset(String contentType) {
/* 225 */     return compareStrings(contentType, "application/fastinfoset");
/*     */   }
/*     */   
/*     */   private boolean compareStrings(String a, String b) {
/* 229 */     return (a.length() >= b.length() && b.equalsIgnoreCase(a.substring(0, b.length())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isFastInfosetAcceptable(String accept) {
/* 236 */     if (accept == null) return false;
/*     */     
/* 238 */     StringTokenizer st = new StringTokenizer(accept, ",");
/* 239 */     while (st.hasMoreTokens()) {
/* 240 */       String token = st.nextToken().trim();
/* 241 */       if (token.equalsIgnoreCase("application/fastinfoset")) {
/* 242 */         return true;
/*     */       }
/*     */     } 
/* 245 */     return false;
/*     */   }
/*     */   
/*     */   private ContentType getStaticContentType(XMLMessage.MessageDataSource mds) {
/* 249 */     String contentType = mds.getDataSource().getContentType();
/* 250 */     boolean isFastInfoset = XMLMessage.isFastInfoset(contentType);
/*     */     
/* 252 */     if (!requiresTransformationOfDataSource(isFastInfoset, this.useFastInfosetForEncoding))
/*     */     {
/* 254 */       return new ContentTypeImpl(contentType);
/*     */     }
/* 256 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private ContentType encode(XMLMessage.MessageDataSource mds, OutputStream out) {
/*     */     try {
/* 262 */       boolean isFastInfoset = XMLMessage.isFastInfoset(mds.getDataSource().getContentType());
/*     */       
/* 264 */       DataSource ds = transformDataSource(mds.getDataSource(), isFastInfoset, this.useFastInfosetForEncoding, this.features);
/*     */ 
/*     */       
/* 267 */       InputStream is = ds.getInputStream();
/* 268 */       byte[] buf = new byte[1024];
/*     */       int count;
/* 270 */       while ((count = is.read(buf)) != -1) {
/* 271 */         out.write(buf, 0, count);
/*     */       }
/* 273 */       return new ContentTypeImpl(ds.getContentType());
/* 274 */     } catch (IOException ioe) {
/* 275 */       throw new WebServiceException(ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Codec getMimeRootCodec(Packet p) {
/* 286 */     if (p.contentNegotiation == ContentNegotiation.none) {
/*     */ 
/*     */       
/* 289 */       this.useFastInfosetForEncoding = false;
/* 290 */     } else if (p.contentNegotiation == ContentNegotiation.optimistic) {
/*     */       
/* 292 */       this.useFastInfosetForEncoding = true;
/*     */     } 
/*     */     
/* 295 */     return (this.useFastInfosetForEncoding && this.fiCodec != null) ? this.fiCodec : this.xmlCodec;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean requiresTransformationOfDataSource(boolean isFastInfoset, boolean useFastInfoset) {
/* 300 */     return ((isFastInfoset && !useFastInfoset) || (!isFastInfoset && useFastInfoset));
/*     */   }
/*     */ 
/*     */   
/*     */   public static DataSource transformDataSource(DataSource in, boolean isFastInfoset, boolean useFastInfoset, WSFeatureList f) {
/*     */     try {
/* 306 */       if (isFastInfoset && !useFastInfoset) {
/*     */         
/* 308 */         Codec codec = new XMLHTTPBindingCodec(f);
/* 309 */         Packet p = new Packet();
/* 310 */         codec.decode(in.getInputStream(), in.getContentType(), p);
/*     */         
/* 312 */         p.getMessage().getAttachments();
/* 313 */         codec.getStaticContentType(p);
/*     */         
/* 315 */         ByteArrayBuffer bos = new ByteArrayBuffer();
/* 316 */         ContentType ct = codec.encode(p, (OutputStream)bos);
/* 317 */         return XMLMessage.createDataSource(ct.getContentType(), bos.newInputStream());
/* 318 */       }  if (!isFastInfoset && useFastInfoset) {
/*     */         
/* 320 */         Codec codec = new XMLHTTPBindingCodec(f);
/* 321 */         Packet p = new Packet();
/* 322 */         codec.decode(in.getInputStream(), in.getContentType(), p);
/*     */         
/* 324 */         p.contentNegotiation = ContentNegotiation.optimistic;
/* 325 */         p.getMessage().getAttachments();
/* 326 */         codec.getStaticContentType(p);
/*     */         
/* 328 */         ByteArrayBuffer bos = new ByteArrayBuffer();
/* 329 */         ContentType ct = codec.encode(p, (OutputStream)bos);
/* 330 */         return XMLMessage.createDataSource(ct.getContentType(), bos.newInputStream());
/*     */       } 
/* 332 */     } catch (Exception ex) {
/* 333 */       throw new WebServiceException(ex);
/*     */     } 
/*     */     
/* 336 */     return in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Codec getFICodec() {
/*     */     try {
/* 344 */       Class<?> c = Class.forName("com.sun.xml.ws.encoding.fastinfoset.FastInfosetCodec");
/* 345 */       Method m = c.getMethod("create", new Class[0]);
/* 346 */       return (Codec)m.invoke(null, new Object[0]);
/* 347 */     } catch (Exception e) {
/* 348 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\XMLHTTPBindingCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */