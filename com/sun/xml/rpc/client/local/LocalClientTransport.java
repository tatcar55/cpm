/*     */ package com.sun.xml.rpc.client.local;
/*     */ 
/*     */ import com.sun.xml.rpc.client.ClientTransport;
/*     */ import com.sun.xml.rpc.client.ClientTransportException;
/*     */ import com.sun.xml.rpc.soap.message.Handler;
/*     */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.spi.runtime.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalClientTransport
/*     */   implements ClientTransport
/*     */ {
/*     */   private Handler _handler;
/*     */   private OutputStream _logStream;
/*     */   
/*     */   public LocalClientTransport(Handler handler) {
/*  50 */     this._handler = handler;
/*     */   }
/*     */   
/*     */   public LocalClientTransport(Handler handler, OutputStream logStream) {
/*  54 */     this._handler = handler;
/*  55 */     this._logStream = logStream;
/*     */   }
/*     */   
/*     */   public void invoke(String endpoint, SOAPMessageContext context) {
/*     */     try {
/*  60 */       if (this._logStream != null) {
/*  61 */         String s = "\n******************\nRequest\n";
/*  62 */         this._logStream.write(s.getBytes());
/*  63 */         context.getMessage().writeTo(this._logStream);
/*     */       } 
/*     */       
/*  66 */       setSOAPMessageFromSAAJ(context);
/*     */       
/*  68 */       this._handler.handle((SOAPMessageContext)context);
/*     */       
/*  70 */       setSOAPMessageFromSAAJ(context);
/*     */ 
/*     */ 
/*     */       
/*  74 */       context.setFailure(false);
/*     */       
/*  76 */       if (this._logStream != null) {
/*  77 */         String s = "\nResponse\n";
/*  78 */         this._logStream.write(s.getBytes());
/*  79 */         context.getMessage().writeTo(this._logStream);
/*  80 */         s = "\n******************\n\n";
/*  81 */         this._logStream.write(s.getBytes());
/*     */       } 
/*  83 */     } catch (Exception e) {
/*  84 */       if (e instanceof Localizable) {
/*  85 */         throw new ClientTransportException("local.client.failed", (Localizable)e);
/*     */       }
/*     */ 
/*     */       
/*  89 */       throw new ClientTransportException("local.client.failed", new LocalizableExceptionAdapter(e));
/*     */     } 
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
/*     */   private void setSOAPMessageFromSAAJ(SOAPMessageContext context) throws Exception {
/* 103 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 104 */     context.getMessage().writeTo(os);
/*     */     
/* 106 */     ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
/*     */     
/* 108 */     SOAPMessage message = MessageFactory.newInstance().createMessage(context.getMessage().getMimeHeaders(), is);
/*     */ 
/*     */ 
/*     */     
/* 112 */     context.setMessage(message);
/*     */   }
/*     */   
/*     */   public void invokeOneWay(String endpoint, SOAPMessageContext context) {
/*     */     try {
/* 117 */       if (this._logStream != null) {
/* 118 */         String s = "\n******************\nRequest\n";
/* 119 */         this._logStream.write(s.getBytes());
/* 120 */         context.getMessage().writeTo(this._logStream);
/*     */       } 
/*     */       
/* 123 */       this._handler.handle((SOAPMessageContext)context);
/*     */ 
/*     */ 
/*     */       
/* 127 */       context.setFailure(false);
/*     */     }
/* 129 */     catch (Exception e) {
/* 130 */       if (e instanceof Localizable) {
/* 131 */         throw new ClientTransportException("local.client.failed", (Localizable)e);
/*     */       }
/*     */ 
/*     */       
/* 135 */       throw new ClientTransportException("local.client.failed", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\local\LocalClientTransport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */