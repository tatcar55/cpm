/*     */ package com.sun.xml.ws.security.encoding;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.security.message.stream.LazyStreamBasedMessage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
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
/*     */ public class LazyStreamCodec
/*     */   implements StreamSOAPCodec
/*     */ {
/*  65 */   private StreamSOAPCodec codec = null;
/*     */   
/*     */   public LazyStreamCodec(StreamSOAPCodec codec) {
/*  68 */     this.codec = codec;
/*     */   }
/*     */   
/*     */   public Message decode(XMLStreamReader reader) {
/*  72 */     return (Message)new LazyStreamBasedMessage(reader, this.codec);
/*     */   }
/*     */   @NotNull
/*     */   public Message decode(@NotNull XMLStreamReader reader, AttachmentSet att) {
/*  76 */     return (Message)new LazyStreamBasedMessage(reader, this.codec, att);
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/*  80 */     return this.codec.getMimeType();
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/*  84 */     return this.codec.getStaticContentType(packet);
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream outputStream) throws IOException {
/*  88 */     return this.codec.encode(packet, outputStream);
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel writableByteChannel) {
/*  92 */     return this.codec.encode(packet, writableByteChannel);
/*     */   }
/*     */   
/*     */   public Codec copy() {
/*  96 */     return (Codec)this;
/*     */   }
/*     */   
/*     */   public void decode(InputStream inputStream, String string, Packet packet) throws IOException {
/* 100 */     XMLStreamReader reader = XMLStreamReaderFactory.create(null, inputStream, true);
/* 101 */     packet.setMessage(decode(reader));
/*     */   }
/*     */   
/*     */   public void decode(ReadableByteChannel readableByteChannel, String string, Packet packet) {
/* 105 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\encoding\LazyStreamCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */