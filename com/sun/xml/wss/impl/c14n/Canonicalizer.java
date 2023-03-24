/*    */ package com.sun.xml.wss.impl.c14n;
/*    */ 
/*    */ import com.sun.xml.wss.XWSSecurityException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.logging.Logger;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Canonicalizer
/*    */ {
/* 66 */   String _charset = "US-ASCII";
/*    */   
/* 68 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.c14n", "com.sun.xml.wss.logging.impl.c14n.LogStrings");
/*    */ 
/*    */   
/*    */   public Canonicalizer() {}
/*    */ 
/*    */   
/*    */   Canonicalizer(String charset) {
/* 75 */     this._charset = charset;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract byte[] canonicalize(byte[] paramArrayOfbyte) throws XWSSecurityException;
/*    */ 
/*    */   
/*    */   public abstract InputStream canonicalize(InputStream paramInputStream, OutputStream paramOutputStream) throws TransformException;
/*    */   
/*    */   void setCharset(String charset) {
/* 85 */     this._charset = charset;
/*    */   }
/*    */   
/*    */   public String getCharset() {
/* 89 */     return this._charset;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\Canonicalizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */