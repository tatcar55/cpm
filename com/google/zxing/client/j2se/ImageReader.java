/*    */ package com.google.zxing.client.j2se;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
/*    */ import java.net.URLDecoder;
/*    */ import javax.imageio.ImageIO;
/*    */ import javax.xml.bind.DatatypeConverter;
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
/*    */ public final class ImageReader
/*    */ {
/*    */   private static final String BASE64TOKEN = "base64,";
/*    */   
/*    */   public static BufferedImage readImage(URI uri) throws IOException {
/*    */     BufferedImage result;
/* 40 */     if ("data".equals(uri.getScheme())) {
/* 41 */       return readDataURIImage(uri);
/*    */     }
/*    */     
/*    */     try {
/* 45 */       result = ImageIO.read(uri.toURL());
/* 46 */     } catch (IllegalArgumentException iae) {
/* 47 */       throw new IOException("Resource not found: " + uri, iae);
/*    */     } 
/* 49 */     if (result == null) {
/* 50 */       throw new IOException("Could not load " + uri);
/*    */     }
/* 52 */     return result;
/*    */   }
/*    */   
/*    */   public static BufferedImage readDataURIImage(URI uri) throws IOException {
/* 56 */     String uriString = uri.getSchemeSpecificPart();
/* 57 */     if (!uriString.startsWith("image/")) {
/* 58 */       throw new IOException("Unsupported data URI MIME type");
/*    */     }
/* 60 */     int base64Start = uriString.indexOf("base64,");
/* 61 */     if (base64Start < 0) {
/* 62 */       throw new IOException("Unsupported data URI encoding");
/*    */     }
/* 64 */     String base64DataEncoded = uriString.substring(base64Start + "base64,".length());
/* 65 */     String base64Data = URLDecoder.decode(base64DataEncoded, "UTF-8");
/* 66 */     byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Data);
/* 67 */     return ImageIO.read(new ByteArrayInputStream(imageBytes));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\j2se\ImageReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */