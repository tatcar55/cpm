/*    */ package com.sun.xml.wss.impl.c14n;
/*    */ 
/*    */ import com.sun.xml.wss.XWSSecurityException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.logging.Level;
/*    */ import javax.xml.crypto.dsig.TransformException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ApplicationCanonicalizer
/*    */   extends Canonicalizer
/*    */ {
/*    */   public ApplicationCanonicalizer() {}
/*    */   
/*    */   public ApplicationCanonicalizer(String charset) {
/* 66 */     super(charset);
/*    */   }
/*    */   
/*    */   public byte[] canonicalize(byte[] input) throws XWSSecurityException {
/* 70 */     return input;
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream canonicalize(InputStream input, OutputStream outputStream) throws TransformException {
/*    */     try {
/* 76 */       if (outputStream == null) {
/* 77 */         return input;
/*    */       }
/* 79 */       byte[] data = new byte[128];
/*    */       while (true) {
/* 81 */         int len = input.read(data);
/* 82 */         if (len <= 0)
/*    */           break; 
/* 84 */         outputStream.write(data, 0, len);
/*    */       } 
/*    */       
/* 87 */       return null;
/* 88 */     } catch (Exception ex) {
/* 89 */       log.log(Level.SEVERE, "WSS1000.error.canonicalizing", new Object[] { ex.getMessage() });
/*    */       
/* 91 */       throw new TransformException(ex.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\ApplicationCanonicalizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */