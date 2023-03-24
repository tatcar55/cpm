/*     */ package com.sun.xml.wss.impl.c14n;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
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
/*     */ public class ImageCanonicalizer
/*     */   extends Canonicalizer
/*     */ {
/*     */   public ImageCanonicalizer() {}
/*     */   
/*     */   public ImageCanonicalizer(String charset) {
/*  68 */     super(charset);
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
/*     */   public byte[] canonicalize(byte[] input) throws XWSSecurityException {
/*  81 */     return input;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream canonicalize(InputStream input, OutputStream outputStream) throws TransformException {
/*     */     try {
/*  87 */       if (outputStream == null) {
/*  88 */         return input;
/*     */       }
/*  90 */       byte[] data = new byte[128];
/*     */       while (true) {
/*  92 */         int len = input.read(data);
/*  93 */         if (len <= 0)
/*     */           break; 
/*  95 */         outputStream.write(data, 0, len);
/*     */       }
/*     */     
/*  98 */     } catch (Exception ex) {
/*  99 */       log.log(Level.SEVERE, "WSS1001.error.canonicalizing.image", new Object[] { ex.getMessage() });
/*     */       
/* 101 */       throw new TransformException(ex.getMessage());
/*     */     } 
/* 103 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\ImageCanonicalizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */