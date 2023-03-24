/*     */ package com.sun.xml.security.core.dsig;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.xml.bind.v2.util.ByteArrayOutputStreamEx;
/*     */ import com.sun.xml.ws.streaming.MtomStreamWriter;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamWriterFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
/*     */ import org.jvnet.staxex.XMLStreamWriterEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomStreamWriterImpl
/*     */   extends XMLStreamWriterFilter
/*     */   implements XMLStreamWriterEx, MtomStreamWriter
/*     */ {
/*  70 */   protected XMLStreamWriterEx sw = null;
/*  71 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */ 
/*     */   
/*     */   public CustomStreamWriterImpl(XMLStreamWriter sw) {
/*  75 */     super(sw);
/*  76 */     this.sw = (XMLStreamWriterEx)sw;
/*     */   }
/*     */   
/*     */   public void writeBinary(byte[] arg0, int arg1, int arg2, String arg3) throws XMLStreamException {
/*  80 */     this.sw.writeBinary(arg0, arg1, arg2, arg3);
/*     */   }
/*     */   
/*     */   public void writeBinary(DataHandler dh) throws XMLStreamException {
/*  84 */     int len = 0;
/*  85 */     byte[] data = null;
/*  86 */     InputStream is = null;
/*  87 */     ByteArrayOutputStreamEx baos = null;
/*     */     try {
/*  89 */       baos = new ByteArrayOutputStreamEx();
/*  90 */       is = dh.getDataSource().getInputStream();
/*  91 */       baos.readFrom(is);
/*  92 */       data = baos.toByteArray();
/*  93 */       len = data.length;
/*  94 */       baos.close();
/*  95 */       is.close();
/*  96 */     } catch (IOException ioe) {
/*  97 */       logger.log(Level.SEVERE, "could not get the inputstream from the data handler", ioe);
/*     */     } 
/*  99 */     if (len > 1000) {
/* 100 */       this.sw.writeBinary(dh);
/*     */     } else {
/* 102 */       this.sw.writePCDATA(Base64.encode(data));
/*     */     } 
/*     */   }
/*     */   
/*     */   public OutputStream writeBinary(String arg0) throws XMLStreamException {
/* 107 */     return this.sw.writeBinary(arg0);
/*     */   }
/*     */   
/*     */   public void writePCDATA(CharSequence data) throws XMLStreamException {
/* 111 */     this.sw.writePCDATA(data);
/*     */   }
/*     */   
/*     */   public NamespaceContextEx getNamespaceContext() {
/* 115 */     return this.sw.getNamespaceContext();
/*     */   }
/*     */   
/*     */   public AttachmentMarshaller getAttachmentMarshaller() {
/* 119 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\security\core\dsig\CustomStreamWriterImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */