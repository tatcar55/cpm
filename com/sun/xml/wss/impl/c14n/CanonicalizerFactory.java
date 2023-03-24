/*     */ package com.sun.xml.wss.impl.c14n;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import javax.mail.internet.ContentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CanonicalizerFactory
/*     */ {
/*  61 */   static MimeHeaderCanonicalizer _mhCanonicalizer = null;
/*     */   
/*  63 */   static HashMap<String, Object> _canonicalizers = new HashMap<String, Object>(10);
/*     */   
/*     */   public static final Canonicalizer getCanonicalizer(String mimeType) throws Exception {
/*  66 */     ContentType contentType = new ContentType(mimeType);
/*  67 */     String baseMimeType = contentType.getBaseType();
/*     */     
/*  69 */     if (baseMimeType.equalsIgnoreCase("text/plain")) {
/*  70 */       ensureRegisteredCharset(contentType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  75 */     String primaryMimeType = contentType.getPrimaryType();
/*  76 */     Canonicalizer _canonicalizer = (Canonicalizer)_canonicalizers.get(primaryMimeType);
/*     */ 
/*     */     
/*  79 */     if (_canonicalizer == null) {
/*  80 */       _canonicalizer = newCanonicalizer(primaryMimeType);
/*     */     }
/*     */ 
/*     */     
/*  84 */     String charset = contentType.getParameter("charset");
/*  85 */     if (charset != null) _canonicalizer.setCharset(charset);
/*     */     
/*  87 */     return _canonicalizer;
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
/*     */   public static final Canonicalizer newCanonicalizer(String primaryMimeType) {
/*  99 */     Canonicalizer canonicalizer = null;
/*     */     
/* 101 */     if (primaryMimeType.equalsIgnoreCase("text")) {
/* 102 */       canonicalizer = new TextPlainCanonicalizer();
/*     */     }
/* 104 */     else if (primaryMimeType.equalsIgnoreCase("image")) {
/* 105 */       canonicalizer = new ImageCanonicalizer();
/*     */     }
/* 107 */     else if (primaryMimeType.equalsIgnoreCase("application")) {
/* 108 */       canonicalizer = new ApplicationCanonicalizer();
/*     */     } 
/*     */ 
/*     */     
/* 112 */     _canonicalizers.put(primaryMimeType, canonicalizer);
/*     */     
/* 114 */     return canonicalizer;
/*     */   }
/*     */   
/*     */   public static final MimeHeaderCanonicalizer getMimeHeaderCanonicalizer(String charset) {
/* 118 */     if (_mhCanonicalizer == null)
/* 119 */       _mhCanonicalizer = new MimeHeaderCanonicalizer(); 
/* 120 */     _mhCanonicalizer.setCharset(charset);
/* 121 */     return _mhCanonicalizer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerCanonicalizer(String baseMimeType, Canonicalizer implementingClass) {
/* 126 */     _canonicalizers.put(baseMimeType, implementingClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerCanonicalizer(String baseMimeType, String implementingClass) throws XWSSecurityException {
/*     */     try {
/* 132 */       Class<?> _class = Class.forName(implementingClass);
/* 133 */       Canonicalizer canonicalizer = (Canonicalizer)_class.newInstance();
/* 134 */       _canonicalizers.put(baseMimeType, canonicalizer);
/* 135 */     } catch (Exception e) {
/*     */       
/* 137 */       throw new XWSSecurityException(e);
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
/*     */   
/*     */   public static boolean ensureRegisteredCharset(ContentType contentType) throws XWSSecurityException {
/* 152 */     String charsetName = contentType.getParameter("charset");
/* 153 */     if (charsetName != null) {
/* 154 */       return Charset.forName(charsetName).isRegistered();
/*     */     }
/* 156 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\CanonicalizerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */