/*     */ package com.sun.xml.ws.transport.tcp.encoding;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;
/*     */ import com.sun.xml.fastinfoset.vocab.ParserVocabulary;
/*     */ import com.sun.xml.fastinfoset.vocab.SerializerVocabulary;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.Codecs;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.ws.encoding.ContentTypeImpl;
/*     */ import com.sun.xml.ws.message.stream.StreamHeader;
/*     */ import com.sun.xml.ws.transport.tcp.encoding.configurator.WSTCPCodecConfigurator;
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
/*     */ public abstract class WSTCPFastInfosetStreamCodec
/*     */   implements Codec
/*     */ {
/*     */   private StAXDocumentParser _statefulParser;
/*     */   private StAXDocumentSerializer _serializer;
/*     */   private final StreamSOAPCodec _soapCodec;
/*     */   private final boolean _retainState;
/*     */   protected final ContentType _defaultContentType;
/*     */   private final WSTCPFastInfosetStreamReaderRecyclable.RecycleAwareListener _readerRecycleListener;
/*     */   
/*     */   WSTCPFastInfosetStreamCodec(@Nullable StreamSOAPCodec soapCodec, @NotNull SOAPVersion soapVersion, @NotNull WSTCPFastInfosetStreamReaderRecyclable.RecycleAwareListener readerRecycleListener, boolean retainState, String mimeType) {
/*  87 */     this._soapCodec = (soapCodec != null) ? soapCodec : Codecs.createSOAPEnvelopeXmlCodec(soapVersion);
/*  88 */     this._readerRecycleListener = readerRecycleListener;
/*  89 */     this._retainState = retainState;
/*  90 */     this._defaultContentType = (ContentType)new ContentTypeImpl(mimeType);
/*     */   }
/*     */   
/*     */   WSTCPFastInfosetStreamCodec(WSTCPFastInfosetStreamCodec that) {
/*  94 */     this._soapCodec = (StreamSOAPCodec)that._soapCodec.copy();
/*  95 */     this._readerRecycleListener = that._readerRecycleListener;
/*  96 */     this._retainState = that._retainState;
/*  97 */     this._defaultContentType = that._defaultContentType;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/* 101 */     return this._defaultContentType.getContentType();
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 105 */     return getContentType(packet.soapAction);
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) {
/* 109 */     if (packet.getMessage() != null) {
/* 110 */       XMLStreamWriter writer = getXMLStreamWriter(out);
/*     */       try {
/* 112 */         packet.getMessage().writeTo(writer);
/* 113 */         writer.flush();
/* 114 */       } catch (XMLStreamException e) {
/* 115 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } 
/* 118 */     return getContentType(packet.soapAction);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 123 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet response) throws IOException {
/* 127 */     response.setMessage(this._soapCodec.decode(getXMLStreamReader(in)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet response) {
/* 132 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected abstract StreamHeader createHeader(XMLStreamReader paramXMLStreamReader, XMLStreamBuffer paramXMLStreamBuffer);
/*     */   
/*     */   protected abstract ContentType getContentType(String paramString);
/*     */   
/*     */   private XMLStreamWriter getXMLStreamWriter(OutputStream out) {
/* 140 */     if (this._serializer != null) {
/* 141 */       this._serializer.setOutputStream(out);
/* 142 */       return (XMLStreamWriter)this._serializer;
/*     */     } 
/* 144 */     WSTCPCodecConfigurator configurator = WSTCPCodecConfigurator.INSTANCE;
/* 145 */     StAXDocumentSerializer serializer = configurator.getDocumentSerializerFactory().newInstance();
/* 146 */     serializer.setOutputStream(out);
/*     */     
/* 148 */     if (this._retainState) {
/* 149 */       SerializerVocabulary vocabulary = configurator.getSerializerVocabularyFactory().newInstance();
/* 150 */       serializer.setVocabulary(vocabulary);
/* 151 */       serializer.setMinAttributeValueSize(configurator.getMinAttributeValueSize());
/*     */       
/* 153 */       serializer.setMaxAttributeValueSize(configurator.getMaxAttributeValueSize());
/*     */       
/* 155 */       serializer.setMinCharacterContentChunkSize(configurator.getMinCharacterContentChunkSize());
/*     */       
/* 157 */       serializer.setMaxCharacterContentChunkSize(configurator.getMaxCharacterContentChunkSize());
/*     */       
/* 159 */       serializer.setAttributeValueMapMemoryLimit(configurator.getAttributeValueMapMemoryLimit());
/*     */       
/* 161 */       serializer.setCharacterContentChunkMapMemoryLimit(configurator.getCharacterContentChunkMapMemoryLimit());
/*     */     } 
/*     */     
/* 164 */     this._serializer = serializer;
/* 165 */     return (XMLStreamWriter)serializer;
/*     */   }
/*     */ 
/*     */   
/*     */   private XMLStreamReader getXMLStreamReader(InputStream in) {
/* 170 */     if (this._statefulParser != null) {
/* 171 */       this._statefulParser.setInputStream(in);
/* 172 */       return (XMLStreamReader)this._statefulParser;
/*     */     } 
/* 174 */     WSTCPCodecConfigurator configurator = WSTCPCodecConfigurator.INSTANCE;
/* 175 */     StAXDocumentParser parser = configurator.getDocumentParserFactory().newInstance();
/* 176 */     parser.setInputStream(in);
/* 177 */     if (parser instanceof WSTCPFastInfosetStreamReaderRecyclable) {
/* 178 */       ((WSTCPFastInfosetStreamReaderRecyclable)parser).setListener(this._readerRecycleListener);
/*     */     }
/*     */ 
/*     */     
/* 182 */     parser.setStringInterning(true);
/* 183 */     if (this._retainState) {
/* 184 */       ParserVocabulary vocabulary = configurator.getParserVocabularyFactory().newInstance();
/*     */       
/* 186 */       parser.setVocabulary(vocabulary);
/*     */     } 
/* 188 */     this._statefulParser = parser;
/* 189 */     return (XMLStreamReader)this._statefulParser;
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
/*     */   public static WSTCPFastInfosetStreamCodec create(StreamSOAPCodec soapCodec, SOAPVersion version, WSTCPFastInfosetStreamReaderRecyclable.RecycleAwareListener readerRecycleListener, boolean retainState) {
/* 201 */     if (version == null)
/*     */     {
/* 203 */       throw new IllegalArgumentException(); } 
/* 204 */     switch (version) {
/*     */       case SOAP_11:
/* 206 */         return new WSTCPFastInfosetStreamSOAP11Codec(soapCodec, readerRecycleListener, retainState);
/*     */       case SOAP_12:
/* 208 */         return new WSTCPFastInfosetStreamSOAP12Codec(soapCodec, readerRecycleListener, retainState);
/*     */     } 
/* 210 */     throw new AssertionError();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\encoding\WSTCPFastInfosetStreamCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */