/*     */ package com.sun.xml.ws.encoding.fastinfoset;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.ws.encoding.ContentTypeImpl;
/*     */ import com.sun.xml.ws.message.stream.StreamHeader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
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
/*     */ 
/*     */ public abstract class FastInfosetStreamSOAPCodec
/*     */   implements Codec
/*     */ {
/*  75 */   private static final FastInfosetStreamReaderFactory READER_FACTORY = FastInfosetStreamReaderFactory.getInstance();
/*     */   
/*     */   private StAXDocumentParser _statefulParser;
/*     */   
/*     */   private StAXDocumentSerializer _serializer;
/*     */   
/*     */   private final StreamSOAPCodec _soapCodec;
/*     */   
/*     */   private final boolean _retainState;
/*     */   
/*     */   protected final ContentType _defaultContentType;
/*     */   
/*     */   FastInfosetStreamSOAPCodec(StreamSOAPCodec soapCodec, SOAPVersion soapVersion, boolean retainState, String mimeType) {
/*  88 */     this._soapCodec = soapCodec;
/*  89 */     this._retainState = retainState;
/*  90 */     this._defaultContentType = (ContentType)new ContentTypeImpl(mimeType);
/*     */   }
/*     */   
/*     */   FastInfosetStreamSOAPCodec(FastInfosetStreamSOAPCodec that) {
/*  94 */     this._soapCodec = (StreamSOAPCodec)that._soapCodec.copy();
/*  95 */     this._retainState = that._retainState;
/*  96 */     this._defaultContentType = that._defaultContentType;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/* 100 */     return this._defaultContentType.getContentType();
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 104 */     return getContentType(packet.soapAction);
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) {
/* 108 */     if (packet.getMessage() != null) {
/* 109 */       XMLStreamWriter writer = getXMLStreamWriter(out);
/*     */       try {
/* 111 */         packet.getMessage().writeTo(writer);
/* 112 */         writer.flush();
/* 113 */       } catch (XMLStreamException e) {
/* 114 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } 
/* 117 */     return getContentType(packet.soapAction);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 122 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet response) throws IOException {
/* 126 */     response.setMessage(this._soapCodec.decode(getXMLStreamReader(in)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet response) {
/* 131 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected abstract StreamHeader createHeader(XMLStreamReader paramXMLStreamReader, XMLStreamBuffer paramXMLStreamBuffer);
/*     */   
/*     */   protected abstract ContentType getContentType(String paramString);
/*     */   
/*     */   private XMLStreamWriter getXMLStreamWriter(OutputStream out) {
/* 139 */     if (this._serializer != null) {
/* 140 */       this._serializer.setOutputStream(out);
/* 141 */       return (XMLStreamWriter)this._serializer;
/*     */     } 
/* 143 */     return (XMLStreamWriter)(this._serializer = FastInfosetCodec.createNewStreamWriter(out, this._retainState));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLStreamReader getXMLStreamReader(InputStream in) {
/* 149 */     if (this._retainState) {
/* 150 */       if (this._statefulParser != null) {
/* 151 */         this._statefulParser.setInputStream(in);
/* 152 */         return (XMLStreamReader)this._statefulParser;
/*     */       } 
/* 154 */       return (XMLStreamReader)(this._statefulParser = FastInfosetCodec.createNewStreamReader(in, this._retainState));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 159 */     return READER_FACTORY.doCreate((String)null, in, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FastInfosetStreamSOAPCodec create(StreamSOAPCodec soapCodec, SOAPVersion version) {
/* 169 */     return create(soapCodec, version, false);
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
/*     */   public static FastInfosetStreamSOAPCodec create(StreamSOAPCodec soapCodec, SOAPVersion version, boolean retainState) {
/* 182 */     if (version == null)
/*     */     {
/* 184 */       throw new IllegalArgumentException(); } 
/* 185 */     switch (version) {
/*     */       case SOAP_11:
/* 187 */         return new FastInfosetStreamSOAP11Codec(soapCodec, retainState);
/*     */       case SOAP_12:
/* 189 */         return new FastInfosetStreamSOAP12Codec(soapCodec, retainState);
/*     */     } 
/* 191 */     throw new AssertionError();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\fastinfoset\FastInfosetStreamSOAPCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */