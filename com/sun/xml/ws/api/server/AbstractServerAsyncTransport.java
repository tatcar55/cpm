/*     */ package com.sun.xml.ws.api.server;
/*     */ 
/*     */ import com.oracle.webservices.api.message.PropertySet;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.util.Pool;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractServerAsyncTransport<T>
/*     */ {
/*     */   private final WSEndpoint endpoint;
/*     */   private final CodecPool codecPool;
/*     */   
/*     */   public AbstractServerAsyncTransport(WSEndpoint endpoint) {
/*  72 */     this.endpoint = endpoint;
/*  73 */     this.codecPool = new CodecPool(endpoint);
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
/*     */   protected Packet decodePacket(T connection, @NotNull Codec codec) throws IOException {
/*  85 */     Packet packet = new Packet();
/*  86 */     packet.acceptableMimeTypes = getAcceptableMimeTypes(connection);
/*  87 */     packet.addSatellite(getPropertySet(connection));
/*  88 */     packet.transportBackChannel = getTransportBackChannel(connection);
/*  89 */     return packet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handle(final T connection) throws IOException {
/* 146 */     final Codec codec = (Codec)this.codecPool.take();
/* 147 */     Packet request = decodePacket(connection, codec);
/* 148 */     if (!request.getMessage().isFault())
/* 149 */       this.endpoint.schedule(request, new WSEndpoint.CompletionCallback() {
/*     */             public void onCompletion(@NotNull Packet response) {
/*     */               try {
/* 152 */                 AbstractServerAsyncTransport.this.encodePacket(connection, response, codec);
/* 153 */               } catch (IOException ioe) {
/* 154 */                 ioe.printStackTrace();
/*     */               } 
/* 156 */               AbstractServerAsyncTransport.this.codecPool.recycle(codec);
/*     */             }
/*     */           }); 
/*     */   } protected abstract void encodePacket(T paramT, @NotNull Packet paramPacket, @NotNull Codec paramCodec) throws IOException; @Nullable
/*     */   protected abstract String getAcceptableMimeTypes(T paramT); @Nullable
/*     */   protected abstract TransportBackChannel getTransportBackChannel(T paramT); @NotNull
/*     */   protected abstract PropertySet getPropertySet(T paramT);
/*     */   @NotNull
/*     */   protected abstract WebServiceContextDelegate getWebServiceContextDelegate(T paramT);
/*     */   private static final class CodecPool extends Pool<Codec> { CodecPool(WSEndpoint endpoint) {
/* 166 */       this.endpoint = endpoint;
/*     */     }
/*     */     WSEndpoint endpoint;
/*     */     protected Codec create() {
/* 170 */       return this.endpoint.createCodec();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\AbstractServerAsyncTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */