/*     */ package com.sun.xml.ws.transport.tcp.io;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.pool.LifeCycle;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.util.FrameType;
/*     */ import com.sun.xml.ws.transport.tcp.util.SelectorFactory;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FramedMessageInputStream
/*     */   extends InputStream
/*     */   implements LifeCycle
/*     */ {
/*  64 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.streams");
/*     */ 
/*     */ 
/*     */   
/*     */   private ByteBuffer byteBuffer;
/*     */ 
/*     */ 
/*     */   
/*     */   private SocketChannel socketChannel;
/*     */ 
/*     */   
/*     */   private static final int READ_TIMEOUT = 30000;
/*     */ 
/*     */   
/*     */   static final int READ_TRY = 10;
/*     */ 
/*     */   
/*  81 */   private final int[] headerTmpArray = new int[2];
/*     */   
/*     */   private boolean isDirectMode;
/*     */   
/*     */   private int frameSize;
/*     */   
/*     */   private int frameBytesRead;
/*     */   
/*     */   private boolean isLastFrame;
/*     */   private int currentFrameDataSize;
/*     */   private int channelId;
/*     */   private int contentId;
/*     */   private int messageId;
/*  94 */   private final Map<Integer, String> contentProps = new HashMap<Integer, String>(8);
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isReadingHeader;
/*     */ 
/*     */ 
/*     */   
/*     */   private long receivedMessageLength;
/*     */ 
/*     */ 
/*     */   
/*     */   public FramedMessageInputStream() {
/* 107 */     this(4096);
/*     */   }
/*     */   
/*     */   public FramedMessageInputStream(int frameSize) {
/* 111 */     setFrameSize(frameSize);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSocketChannel(SocketChannel socketChannel) {
/* 117 */     this.socketChannel = socketChannel;
/*     */   }
/*     */   
/*     */   public int getChannelId() {
/* 121 */     return this.channelId;
/*     */   }
/*     */   
/*     */   public int getMessageId() {
/* 125 */     return this.messageId;
/*     */   }
/*     */   
/*     */   public int getContentId() {
/* 129 */     return this.contentId;
/*     */   }
/*     */   
/*     */   public Map<Integer, String> getContentProperties() {
/* 133 */     return this.contentProps;
/*     */   }
/*     */   
/*     */   public boolean isDirectMode() {
/* 137 */     return this.isDirectMode;
/*     */   }
/*     */   
/*     */   public void setDirectMode(boolean isDirectMode) {
/* 141 */     reset();
/* 142 */     this.isDirectMode = isDirectMode;
/*     */   }
/*     */   
/*     */   public void setFrameSize(int frameSize) {
/* 146 */     this.frameSize = frameSize;
/*     */   }
/*     */   
/*     */   public void setByteBuffer(ByteBuffer byteBuffer) {
/* 150 */     this.byteBuffer = byteBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int available() {
/* 159 */     return remaining();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() {
/* 182 */     if (!this.isDirectMode) {
/* 183 */       if (this.isLastFrame && this.frameBytesRead >= this.currentFrameDataSize)
/*     */       {
/* 185 */         return -1;
/*     */       }
/*     */       
/* 188 */       int eof = 0;
/* 189 */       if (!this.byteBuffer.hasRemaining()) {
/* 190 */         eof = readFromChannel();
/*     */       }
/*     */       
/* 193 */       if (eof == -1 || readFrameHeaderIfRequired() == -1) {
/* 194 */         return -1;
/*     */       }
/*     */       
/* 197 */       if (this.byteBuffer.hasRemaining()) {
/* 198 */         this.frameBytesRead++;
/* 199 */         this.receivedMessageLength++;
/* 200 */         return this.byteBuffer.get() & 0xFF;
/*     */       } 
/*     */       
/* 203 */       return read();
/*     */     } 
/* 205 */     if (!this.byteBuffer.hasRemaining()) {
/* 206 */       int eof = readFromChannel();
/* 207 */       if (eof == -1) {
/* 208 */         return -1;
/*     */       }
/*     */     } 
/*     */     
/* 212 */     return this.byteBuffer.get() & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b) {
/* 222 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int offset, int length) {
/* 231 */     if (!this.isDirectMode) {
/* 232 */       if (this.isLastFrame && this.frameBytesRead >= this.currentFrameDataSize)
/*     */       {
/* 234 */         return -1;
/*     */       }
/*     */       
/* 237 */       int eof = 0;
/* 238 */       if (!this.byteBuffer.hasRemaining()) {
/* 239 */         eof = readFromChannel();
/*     */       }
/*     */       
/* 242 */       if (eof == -1 || readFrameHeaderIfRequired() == -1) {
/* 243 */         return -1;
/*     */       }
/*     */ 
/*     */       
/* 247 */       int i = remaining();
/* 248 */       if (i == 0)
/*     */       {
/* 250 */         return read(b, offset, length);
/*     */       }
/*     */       
/* 253 */       if (length > i) {
/* 254 */         length = i;
/*     */       }
/*     */       
/* 257 */       this.byteBuffer.get(b, offset, length);
/* 258 */       this.frameBytesRead += length;
/* 259 */       this.receivedMessageLength += length;
/*     */       
/* 261 */       return length;
/*     */     } 
/* 263 */     if (!this.byteBuffer.hasRemaining()) {
/* 264 */       int eof = readFromChannel();
/* 265 */       if (eof == -1) {
/* 266 */         return -1;
/*     */       }
/*     */     } 
/* 269 */     int remaining = remaining();
/* 270 */     if (length > remaining) {
/* 271 */       length = remaining;
/*     */     }
/* 273 */     this.byteBuffer.get(b, offset, length);
/* 274 */     return length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void forceHeaderRead() throws IOException {
/* 280 */     readHeader();
/*     */   }
/*     */   
/*     */   private int readFrameHeaderIfRequired() {
/* 284 */     if (!this.isDirectMode && !this.isLastFrame && !this.isReadingHeader && (this.frameBytesRead == 0 || this.frameBytesRead == this.currentFrameDataSize)) {
/*     */       try {
/* 286 */         readHeader();
/* 287 */       } catch (IOException ex) {
/* 288 */         return -1;
/*     */       } 
/*     */     }
/*     */     
/* 292 */     return 0;
/*     */   }
/*     */   
/*     */   private void readHeader() throws IOException {
/* 296 */     if (logger.isLoggable(Level.FINEST)) {
/* 297 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1060_FRAMED_MESSAGE_IS_READ_HEADER_ENTER());
/*     */     }
/* 299 */     this.frameBytesRead = 0;
/* 300 */     this.isReadingHeader = true;
/*     */     
/* 302 */     int lowNeebleValue = DataInOutUtils.readInts4(this, this.headerTmpArray, 2, 0);
/* 303 */     this.channelId = this.headerTmpArray[0];
/* 304 */     this.messageId = this.headerTmpArray[1];
/*     */     
/* 306 */     if (FrameType.isFrameContainsParams(this.messageId)) {
/*     */       
/* 308 */       lowNeebleValue = DataInOutUtils.readInts4(this, this.headerTmpArray, 2, lowNeebleValue);
/* 309 */       this.contentId = this.headerTmpArray[0];
/* 310 */       int paramNumber = this.headerTmpArray[1];
/* 311 */       for (int i = 0; i < paramNumber; i++) {
/*     */         
/* 313 */         DataInOutUtils.readInts4(this, this.headerTmpArray, 2, lowNeebleValue);
/* 314 */         int paramId = this.headerTmpArray[0];
/* 315 */         int paramValueLen = this.headerTmpArray[1];
/* 316 */         byte[] paramValueBytes = new byte[paramValueLen];
/*     */         
/* 318 */         DataInOutUtils.readFully(this, paramValueBytes);
/* 319 */         String paramValue = new String(paramValueBytes, "UTF-8");
/* 320 */         this.contentProps.put(Integer.valueOf(paramId), paramValue);
/* 321 */         lowNeebleValue = 0;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 326 */     this.currentFrameDataSize = DataInOutUtils.readInt8(this);
/* 327 */     this.isLastFrame = FrameType.isLastFrame(this.messageId);
/* 328 */     this.currentFrameDataSize += this.frameBytesRead;
/* 329 */     this.isReadingHeader = false;
/*     */     
/* 331 */     if (logger.isLoggable(Level.FINEST)) {
/* 332 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1061_FRAMED_MESSAGE_IS_READ_HEADER_DONE(Integer.valueOf(this.channelId), Integer.valueOf(this.messageId), Integer.valueOf(this.contentId), this.contentProps, Integer.valueOf(this.currentFrameDataSize), Boolean.valueOf(this.isLastFrame)));
/*     */     }
/*     */   }
/*     */   
/*     */   private int readFromChannel() {
/* 337 */     int eof = 0;
/* 338 */     for (int i = 0; i < 10; i++) {
/* 339 */       eof = doRead();
/*     */       
/* 341 */       if (eof != 0) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 346 */     return eof;
/*     */   }
/*     */   
/*     */   public void skipToEndOfMessage() throws EOFException {
/*     */     do {
/* 351 */       readFrameHeaderIfRequired();
/* 352 */       skipToEndOfFrame();
/* 353 */       this.frameBytesRead = 0;
/* 354 */     } while (!this.isLastFrame);
/*     */   }
/*     */   
/*     */   private void skipToEndOfFrame() throws EOFException {
/* 358 */     if (this.currentFrameDataSize > 0) {
/* 359 */       if (this.byteBuffer.hasRemaining()) {
/* 360 */         int remainFrameBytes = this.currentFrameDataSize - this.frameBytesRead;
/* 361 */         if (remainFrameBytes <= this.byteBuffer.remaining()) {
/* 362 */           this.byteBuffer.position(this.byteBuffer.position() + remainFrameBytes);
/*     */           
/*     */           return;
/*     */         } 
/* 366 */         this.frameBytesRead += this.byteBuffer.remaining();
/* 367 */         this.byteBuffer.position(this.byteBuffer.limit());
/*     */       } 
/*     */       
/* 370 */       while (this.frameBytesRead < this.currentFrameDataSize) {
/* 371 */         int eof = readFromChannel();
/* 372 */         if (eof == -1) {
/* 373 */           String errorMessage = MessagesMessages.WSTCP_1062_FRAMED_MESSAGE_IS_READ_UNEXPECTED_EOF(Boolean.valueOf(this.isLastFrame), Integer.valueOf(this.frameBytesRead), Integer.valueOf(this.frameSize), Integer.valueOf(this.currentFrameDataSize));
/* 374 */           logger.log(Level.SEVERE, errorMessage);
/* 375 */           throw new EOFException(errorMessage);
/*     */         } 
/* 377 */         this.frameBytesRead += eof;
/* 378 */         this.byteBuffer.position(this.byteBuffer.position() + eof);
/*     */       } 
/*     */ 
/*     */       
/* 382 */       this.byteBuffer.position(this.byteBuffer.position() - this.frameBytesRead - this.currentFrameDataSize);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int doRead() {
/* 390 */     if (this.socketChannel == null) return -1; 
/* 391 */     if (isEOF()) {
/* 392 */       return -1;
/*     */     }
/*     */     
/* 395 */     this.byteBuffer.clear();
/*     */ 
/*     */     
/* 398 */     int byteRead = 0;
/* 399 */     Selector readSelector = null;
/* 400 */     SelectionKey tmpKey = null;
/*     */     try {
/*     */       int count;
/*     */       do {
/* 404 */         count = this.socketChannel.read(this.byteBuffer);
/* 405 */         byteRead += count;
/* 406 */       } while (count > 0);
/*     */       
/* 408 */       if (count == -1 && byteRead >= 0) byteRead++;
/*     */       
/* 410 */       if (byteRead == 0) {
/* 411 */         readSelector = SelectorFactory.getSelector();
/*     */         
/* 413 */         if (readSelector == null) {
/* 414 */           return 0;
/*     */         }
/* 416 */         tmpKey = this.socketChannel.register(readSelector, 1);
/*     */         
/* 418 */         tmpKey.interestOps(tmpKey.interestOps() | 0x1);
/* 419 */         int code = readSelector.select(30000L);
/*     */ 
/*     */         
/* 422 */         tmpKey.interestOps(tmpKey.interestOps() & 0xFFFFFFFE);
/* 423 */         if (code == 0) {
/* 424 */           return 0;
/*     */         }
/*     */         
/*     */         while (true)
/* 428 */         { count = this.socketChannel.read(this.byteBuffer);
/* 429 */           byteRead += count;
/* 430 */           if (count <= 0)
/* 431 */           { if (count == -1 && byteRead >= 0) byteRead++;  break; }  } 
/*     */       } 
/* 433 */     } catch (Exception e) {
/* 434 */       logger.log(Level.SEVERE, MessagesMessages.WSTCP_0018_ERROR_READING_FROM_SOCKET(), e);
/* 435 */       return -1;
/*     */     } finally {
/* 437 */       if (tmpKey != null) {
/* 438 */         tmpKey.cancel();
/*     */       }
/* 440 */       if (readSelector != null) {
/*     */         try {
/* 442 */           readSelector.selectNow();
/* 443 */         } catch (IOException ex) {}
/*     */         
/* 445 */         SelectorFactory.returnSelector(readSelector);
/*     */       } 
/*     */       
/* 448 */       this.byteBuffer.flip();
/*     */     } 
/*     */     
/* 451 */     return byteRead;
/*     */   }
/*     */   
/*     */   public boolean isMessageInProcess() {
/* 455 */     if (this.currentFrameDataSize == 0 || isEOF()) return false;
/*     */     
/* 457 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isEOF() {
/* 461 */     return (this.isLastFrame && this.frameBytesRead >= this.currentFrameDataSize);
/*     */   }
/*     */   
/*     */   private int remaining() {
/* 465 */     if (this.isReadingHeader || this.isDirectMode) {
/* 466 */       return this.byteBuffer.remaining();
/*     */     }
/*     */     
/* 469 */     return Math.min(this.currentFrameDataSize - this.frameBytesRead, this.byteBuffer.remaining());
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 474 */     this.frameBytesRead = 0;
/* 475 */     this.currentFrameDataSize = 0;
/* 476 */     this.isLastFrame = false;
/* 477 */     this.isReadingHeader = false;
/* 478 */     this.contentId = -1;
/* 479 */     this.messageId = -1;
/* 480 */     this.contentProps.clear();
/* 481 */     this.receivedMessageLength = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate() {}
/*     */   
/*     */   public void passivate() {
/* 488 */     reset();
/* 489 */     setSocketChannel(null);
/* 490 */     setByteBuffer(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 495 */     StringBuffer buffer = new StringBuffer(100);
/* 496 */     buffer.append("ByteBuffer: ");
/* 497 */     buffer.append(this.byteBuffer);
/* 498 */     buffer.append(" FrameBytesRead: ");
/* 499 */     buffer.append(this.frameBytesRead);
/* 500 */     buffer.append(" CurrentFrameDataSize: ");
/* 501 */     buffer.append(this.currentFrameDataSize);
/* 502 */     buffer.append(" isLastFrame: ");
/* 503 */     buffer.append(this.isLastFrame);
/* 504 */     buffer.append(" isDirectMode: ");
/* 505 */     buffer.append(this.isDirectMode);
/* 506 */     buffer.append(" isReadingHeader: ");
/* 507 */     buffer.append(this.isReadingHeader);
/*     */     
/* 509 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\io\FramedMessageInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */