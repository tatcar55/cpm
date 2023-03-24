/*     */ package com.sun.xml.wss.impl.c14n;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.util.CRLFOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextPlainCanonicalizer
/*     */   extends Canonicalizer
/*     */ {
/*     */   public TextPlainCanonicalizer() {}
/*     */   
/*     */   public TextPlainCanonicalizer(String charset) {
/*  74 */     super(charset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream canonicalize(InputStream input, OutputStream outputStream) throws TransformException {
/*  80 */     int len = 0;
/*  81 */     byte[] data = null;
/*     */     try {
/*  83 */       data = new byte[128];
/*  84 */       len = input.read(data);
/*  85 */     } catch (IOException e) {
/*  86 */       log.log(Level.SEVERE, "WSS1002.error.canonicalizing.textplain", new Object[] { e.getMessage() });
/*     */       
/*  88 */       throw new TransformException(e);
/*     */     } 
/*  90 */     CRLFOutputStream crlfOutStream = null;
/*  91 */     ByteArrayOutputStream bout = null;
/*  92 */     if (outputStream == null) {
/*  93 */       bout = new ByteArrayOutputStream();
/*  94 */       crlfOutStream = new CRLFOutputStream(bout);
/*     */     } else {
/*  96 */       crlfOutStream = new CRLFOutputStream(outputStream);
/*     */     } 
/*     */     
/*  99 */     while (len > 0) {
/*     */       try {
/* 101 */         crlfOutStream.write(data, 0, len);
/* 102 */         len = input.read(data);
/* 103 */       } catch (IOException e) {
/* 104 */         log.log(Level.SEVERE, "WSS1002.error.canonicalizing.textplain", new Object[] { e.getMessage() });
/*     */         
/* 106 */         throw new TransformException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     if (outputStream == null) {
/* 111 */       byte[] inputData = bout.toByteArray();
/* 112 */       return new ByteArrayInputStream(inputData);
/*     */     } 
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] canonicalize(byte[] inputBytes) throws XWSSecurityException {
/* 123 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 124 */     CRLFOutputStream crlfOutStream = new CRLFOutputStream(bout);
/*     */     try {
/* 126 */       crlfOutStream.write(inputBytes);
/* 127 */     } catch (IOException e) {
/* 128 */       log.log(Level.SEVERE, "WSS1002.error.canonicalizing.textplain", new Object[] { e.getMessage() });
/*     */       
/* 130 */       throw new XWSSecurityException(e);
/*     */     } 
/* 132 */     return bout.toByteArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\TextPlainCanonicalizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */