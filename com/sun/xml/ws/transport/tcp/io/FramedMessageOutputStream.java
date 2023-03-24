/*     */ package com.sun.xml.ws.transport.tcp.io;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.pool.LifeCycle;
/*     */ import com.sun.xml.ws.transport.tcp.util.ByteBufferFactory;
/*     */ import com.sun.xml.ws.transport.tcp.util.FrameType;
/*     */ import com.sun.xml.ws.transport.tcp.util.TCPSettings;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FramedMessageOutputStream
/*     */   extends OutputStream
/*     */   implements LifeCycle
/*     */ {
/*  59 */   private static final int MAX_GROW_SIZE = TCPSettings.getInstance().getOutputBufferGrowLimit();
/*  60 */   private static final int MAX_PAYLOAD_LENGTH_LENTGTH = calculatePayloadLengthLength(MAX_GROW_SIZE);
/*  61 */   private static final boolean IS_GROWABLE = TCPSettings.getInstance().isOutputBufferGrow();
/*     */   
/*     */   private boolean useDirectBuffer;
/*     */   
/*     */   private ByteBuffer outputBuffer;
/*     */   
/*     */   private SocketChannel socketChannel;
/*     */   
/*     */   private int frameNumber;
/*     */   
/*     */   private int frameSize;
/*     */   private boolean isFlushLast;
/*     */   private int channelId;
/*     */   private int messageId;
/*     */   private int contentId;
/*  76 */   private Map<Integer, String> contentProps = new HashMap<Integer, String>(8);
/*     */ 
/*     */   
/*     */   private int payloadlengthLength;
/*     */   
/*     */   private boolean isDirectMode;
/*     */   
/*     */   private final ByteBuffer headerBuffer;
/*     */   
/*     */   private int frameMessageIdHighValue;
/*     */   
/*     */   private int frameMessageIdPosition;
/*     */   
/*     */   private long sentMessageLength;
/*     */ 
/*     */   
/*     */   public FramedMessageOutputStream() {
/*  93 */     this(4096, false);
/*     */   }
/*     */   
/*     */   public FramedMessageOutputStream(int frameSize) {
/*  97 */     this(frameSize, false);
/*     */   }
/*     */   
/*     */   public FramedMessageOutputStream(int frameSize, boolean useDirectBuffer) {
/* 101 */     this.useDirectBuffer = useDirectBuffer;
/* 102 */     this.headerBuffer = ByteBufferFactory.allocateView(frameSize, useDirectBuffer);
/* 103 */     setFrameSize(frameSize);
/*     */   }
/*     */   
/*     */   public void setFrameSize(int frameSize) {
/* 107 */     this.frameSize = frameSize;
/* 108 */     this.payloadlengthLength = calculatePayloadLengthLength(frameSize);
/* 109 */     this.outputBuffer = ByteBufferFactory.allocateView(frameSize, this.useDirectBuffer);
/*     */   }
/*     */   
/*     */   public boolean isDirectMode() {
/* 113 */     return this.isDirectMode;
/*     */   }
/*     */   
/*     */   public void setDirectMode(boolean isDirectMode) {
/* 117 */     reset();
/* 118 */     this.isDirectMode = isDirectMode;
/*     */     try {
/* 120 */       buildHeader();
/* 121 */     } catch (IOException e) {
/* 122 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSocketChannel(SocketChannel socketChannel) {
/* 127 */     this.socketChannel = socketChannel;
/*     */   }
/*     */   
/*     */   public void setChannelId(int channelId) {
/* 131 */     this.channelId = channelId;
/*     */   }
/*     */   
/*     */   public void setMessageId(int messageId) {
/* 135 */     this.messageId = messageId;
/*     */   }
/*     */   
/*     */   public void setContentId(int contentId) {
/* 139 */     this.contentId = contentId;
/*     */   }
/*     */   
/*     */   public void setContentProperty(int key, String value) {
/* 143 */     this.contentProps.put(Integer.valueOf(key), value);
/*     */   }
/*     */   
/*     */   public void addAllContentProperties(Map<Integer, String> properties) {
/* 147 */     this.contentProps.putAll(properties);
/*     */   }
/*     */   
/*     */   public void write(int data) throws IOException {
/* 151 */     if (!this.outputBuffer.hasRemaining()) {
/* 152 */       flushFrame();
/*     */     }
/*     */     
/* 155 */     this.outputBuffer.put((byte)data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] data, int offset, int size) throws IOException {
/* 160 */     while (size > 0) {
/* 161 */       int bytesToWrite = Math.min(size, this.outputBuffer.remaining());
/* 162 */       this.outputBuffer.put(data, offset, bytesToWrite);
/* 163 */       size -= bytesToWrite;
/* 164 */       offset += bytesToWrite;
/* 165 */       if (!this.outputBuffer.hasRemaining() && size > 0) {
/* 166 */         flushFrame();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void flushLast() throws IOException {
/* 172 */     if (!this.isFlushLast) {
/* 173 */       this.outputBuffer.flip();
/* 174 */       this.isFlushLast = true;
/*     */       
/* 176 */       flushBuffer();
/* 177 */       this.outputBuffer.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void buildHeader() throws IOException {
/* 182 */     this.headerBuffer.clear();
/* 183 */     if (!this.isDirectMode) {
/*     */       
/* 185 */       this.frameMessageIdHighValue = DataInOutUtils.writeInt4(this.headerBuffer, this.channelId, 0, false);
/* 186 */       this.frameMessageIdPosition = this.headerBuffer.position();
/* 187 */       boolean isFrameWithParameters = (FrameType.isFrameContainsParams(this.messageId) && this.frameNumber == 0);
/*     */ 
/*     */       
/* 190 */       int highValue = DataInOutUtils.writeInt4(this.headerBuffer, this.messageId, this.frameMessageIdHighValue, !isFrameWithParameters);
/*     */       
/* 192 */       if (isFrameWithParameters) {
/*     */ 
/*     */         
/* 195 */         highValue = DataInOutUtils.writeInt4(this.headerBuffer, this.contentId, highValue, false);
/*     */         
/* 197 */         int propsCount = this.contentProps.size();
/*     */         
/* 199 */         highValue = DataInOutUtils.writeInt4(this.headerBuffer, propsCount, highValue, (propsCount == 0));
/*     */         
/* 201 */         for (Map.Entry<Integer, String> entry : this.contentProps.entrySet()) {
/* 202 */           String value = entry.getValue();
/* 203 */           byte[] valueBytes = value.getBytes("UTF-8");
/*     */           
/* 205 */           highValue = DataInOutUtils.writeInt4(this.headerBuffer, ((Integer)entry.getKey()).intValue(), highValue, false);
/*     */           
/* 207 */           DataInOutUtils.writeInt4(this.headerBuffer, valueBytes.length, highValue, true);
/*     */           
/* 209 */           this.headerBuffer.put(valueBytes);
/* 210 */           highValue = 0;
/*     */         } 
/*     */       } 
/*     */       
/* 214 */       initOutputBuffer();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void flushBuffer() throws IOException {
/* 219 */     if (!this.isDirectMode) {
/* 220 */       int approxHeaderSize = this.headerBuffer.position() + predictPayloadLengthLength();
/* 221 */       int payloadLength = this.outputBuffer.remaining() - approxHeaderSize;
/*     */       
/* 223 */       if (this.messageId == 0)
/*     */       {
/* 225 */         updateMessageIdIfRequired(this.frameMessageIdPosition, this.frameMessageIdHighValue, this.isFlushLast);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 231 */       DataInOutUtils.writeInt8(this.headerBuffer, payloadLength);
/* 232 */       this.headerBuffer.flip();
/*     */       
/* 234 */       int diff = approxHeaderSize - this.headerBuffer.remaining();
/* 235 */       this.outputBuffer.position(diff);
/* 236 */       this.outputBuffer.put(this.headerBuffer);
/* 237 */       this.outputBuffer.position(diff);
/*     */       
/* 239 */       OutputWriter.flushChannel(this.socketChannel, this.outputBuffer);
/* 240 */       this.sentMessageLength += payloadLength;
/* 241 */       this.frameNumber++;
/*     */     } else {
/* 243 */       OutputWriter.flushChannel(this.socketChannel, this.outputBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateMessageIdIfRequired(int frameMessageIdPosition, int frameMessageIdHighValue, boolean isLastFrame) {
/*     */     int frameMessageId;
/* 251 */     if (isLastFrame) {
/* 252 */       if (this.frameNumber != 0) {
/* 253 */         frameMessageId = 3;
/*     */       } else {
/*     */         
/*     */         return;
/*     */       } 
/* 258 */     } else if (this.frameNumber == 0) {
/* 259 */       frameMessageId = 1;
/*     */     } else {
/* 261 */       frameMessageId = 2;
/*     */     } 
/*     */ 
/*     */     
/* 265 */     if (frameMessageIdHighValue != 0) {
/*     */       
/* 267 */       this.headerBuffer.put(frameMessageIdPosition, (byte)(frameMessageIdHighValue & 0x70 | frameMessageId));
/*     */     } else {
/*     */       
/* 270 */       int value = this.headerBuffer.get(frameMessageIdPosition);
/* 271 */       this.headerBuffer.put(frameMessageIdPosition, (byte)(frameMessageId << 4 | value & 0xF));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/* 276 */     this.outputBuffer.clear();
/* 277 */     this.headerBuffer.clear();
/* 278 */     this.messageId = -1;
/* 279 */     this.contentId = -1;
/* 280 */     this.contentProps.clear();
/* 281 */     this.frameNumber = 0;
/* 282 */     this.isFlushLast = false;
/* 283 */     this.sentMessageLength = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate() {}
/*     */   
/*     */   public void passivate() {
/* 290 */     reset();
/* 291 */     this.socketChannel = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */   
/*     */   private void flushFrame() throws IOException {
/* 299 */     this.outputBuffer.flip();
/* 300 */     if (IS_GROWABLE && this.outputBuffer.capacity() < MAX_GROW_SIZE) {
/* 301 */       ByteBuffer newOutputByteBuffer = ByteBufferFactory.allocateView(Math.min(this.outputBuffer.capacity() * 2, MAX_GROW_SIZE), this.useDirectBuffer);
/*     */ 
/*     */       
/* 304 */       newOutputByteBuffer.put(this.outputBuffer);
/* 305 */       this.outputBuffer = newOutputByteBuffer;
/*     */     } else {
/* 307 */       flushBuffer();
/* 308 */       buildHeader();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initOutputBuffer() {
/* 313 */     this.outputBuffer.clear();
/* 314 */     this.outputBuffer.position(this.headerBuffer.position() + predictPayloadLengthLength());
/*     */   }
/*     */   
/*     */   private int predictPayloadLengthLength() {
/* 318 */     return IS_GROWABLE ? MAX_PAYLOAD_LENGTH_LENTGTH : this.payloadlengthLength;
/*     */   }
/*     */   
/*     */   private static int calculatePayloadLengthLength(int frameSize) {
/* 322 */     return (int)Math.ceil(Math.log(frameSize) / Math.log(2.0D) / 7.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\io\FramedMessageOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */