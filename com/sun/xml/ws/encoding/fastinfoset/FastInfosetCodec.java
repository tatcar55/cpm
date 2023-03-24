/*     */ package com.sun.xml.ws.encoding.fastinfoset;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;
/*     */ import com.sun.xml.fastinfoset.vocab.ParserVocabulary;
/*     */ import com.sun.xml.fastinfoset.vocab.SerializerVocabulary;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.encoding.ContentTypeImpl;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.jvnet.fastinfoset.FastInfosetSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastInfosetCodec
/*     */   implements Codec
/*     */ {
/*     */   private static final int DEFAULT_INDEXED_STRING_SIZE_LIMIT = 32;
/*     */   private static final int DEFAULT_INDEXED_STRING_MEMORY_LIMIT = 4194304;
/*     */   private StAXDocumentParser _parser;
/*     */   private StAXDocumentSerializer _serializer;
/*     */   private final boolean _retainState;
/*     */   private final ContentType _contentType;
/*     */   
/*     */   FastInfosetCodec(boolean retainState) {
/*  85 */     this._retainState = retainState;
/*  86 */     this._contentType = retainState ? (ContentType)new ContentTypeImpl("application/vnd.sun.stateful.fastinfoset") : (ContentType)new ContentTypeImpl("application/fastinfoset");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/*  91 */     return this._contentType.getContentType();
/*     */   }
/*     */   
/*     */   public Codec copy() {
/*  95 */     return new FastInfosetCodec(this._retainState);
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/*  99 */     return this._contentType;
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) {
/* 103 */     Message message = packet.getMessage();
/* 104 */     if (message != null && message.hasPayload()) {
/* 105 */       XMLStreamWriter writer = getXMLStreamWriter(out);
/*     */       try {
/* 107 */         writer.writeStartDocument();
/* 108 */         packet.getMessage().writePayloadTo(writer);
/* 109 */         writer.writeEndDocument();
/* 110 */         writer.flush();
/* 111 */       } catch (XMLStreamException e) {
/* 112 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     return this._contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 121 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/*     */     Message message;
/* 128 */     in = hasSomeData(in);
/* 129 */     if (in != null) {
/* 130 */       message = Messages.createUsingPayload((Source)new FastInfosetSource(in), SOAPVersion.SOAP_11);
/*     */     } else {
/*     */       
/* 133 */       message = Messages.createEmpty(SOAPVersion.SOAP_11);
/*     */     } 
/*     */     
/* 136 */     packet.setMessage(message);
/*     */   }
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet response) {
/* 140 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private XMLStreamWriter getXMLStreamWriter(OutputStream out) {
/* 144 */     if (this._serializer != null) {
/* 145 */       this._serializer.setOutputStream(out);
/* 146 */       return (XMLStreamWriter)this._serializer;
/*     */     } 
/* 148 */     return (XMLStreamWriter)(this._serializer = createNewStreamWriter(out, this._retainState));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FastInfosetCodec create() {
/* 158 */     return create(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FastInfosetCodec create(boolean retainState) {
/* 169 */     return new FastInfosetCodec(retainState);
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
/*     */   static StAXDocumentSerializer createNewStreamWriter(OutputStream out, boolean retainState) {
/* 181 */     return createNewStreamWriter(out, retainState, 32, 4194304);
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
/*     */   static StAXDocumentSerializer createNewStreamWriter(OutputStream out, boolean retainState, int indexedStringSizeLimit, int stringsMemoryLimit) {
/* 194 */     StAXDocumentSerializer serializer = new StAXDocumentSerializer(out);
/* 195 */     if (retainState) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       SerializerVocabulary vocabulary = new SerializerVocabulary();
/* 203 */       serializer.setVocabulary(vocabulary);
/* 204 */       serializer.setMinAttributeValueSize(0);
/* 205 */       serializer.setMaxAttributeValueSize(indexedStringSizeLimit);
/* 206 */       serializer.setMinCharacterContentChunkSize(0);
/* 207 */       serializer.setMaxCharacterContentChunkSize(indexedStringSizeLimit);
/* 208 */       serializer.setAttributeValueMapMemoryLimit(stringsMemoryLimit);
/* 209 */       serializer.setCharacterContentChunkMapMemoryLimit(stringsMemoryLimit);
/*     */     } 
/* 211 */     return serializer;
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
/*     */   static StAXDocumentParser createNewStreamReader(InputStream in, boolean retainState) {
/* 223 */     StAXDocumentParser parser = new StAXDocumentParser(in);
/* 224 */     parser.setStringInterning(true);
/* 225 */     if (retainState) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 232 */       ParserVocabulary vocabulary = new ParserVocabulary();
/* 233 */       parser.setVocabulary(vocabulary);
/*     */     } 
/* 235 */     return parser;
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
/*     */   static StAXDocumentParser createNewStreamReaderRecyclable(InputStream in, boolean retainState) {
/* 247 */     StAXDocumentParser parser = new FastInfosetStreamReaderRecyclable(in);
/* 248 */     parser.setStringInterning(true);
/* 249 */     parser.setForceStreamClose(true);
/* 250 */     if (retainState) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       ParserVocabulary vocabulary = new ParserVocabulary();
/* 258 */       parser.setVocabulary(vocabulary);
/*     */     } 
/* 260 */     return parser;
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
/*     */   private static InputStream hasSomeData(InputStream in) throws IOException {
/* 273 */     if (in != null && 
/* 274 */       in.available() < 1) {
/* 275 */       if (!in.markSupported()) {
/* 276 */         in = new BufferedInputStream(in);
/*     */       }
/* 278 */       in.mark(1);
/* 279 */       if (in.read() != -1) {
/* 280 */         in.reset();
/*     */       } else {
/* 282 */         in = null;
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     return in;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\fastinfoset\FastInfosetCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */