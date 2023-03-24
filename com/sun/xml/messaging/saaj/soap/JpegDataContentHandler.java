/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.MediaTracker;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.ActivationDataFlavor;
/*     */ import javax.activation.DataContentHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JpegDataContentHandler
/*     */   extends Component
/*     */   implements DataContentHandler
/*     */ {
/*     */   public static final String STR_SRC = "java.awt.Image";
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  69 */     DataFlavor[] flavors = new DataFlavor[1];
/*     */     
/*     */     try {
/*  72 */       flavors[0] = new ActivationDataFlavor(Class.forName("java.awt.Image"), "image/jpeg", "JPEG");
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  77 */     catch (Exception e) {
/*  78 */       System.out.println(e);
/*     */     } 
/*     */     
/*  81 */     return flavors;
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
/*     */   public Object getTransferData(DataFlavor df, DataSource ds) {
/*  94 */     if (df.getMimeType().startsWith("image/jpeg") && 
/*  95 */       df.getRepresentationClass().getName().equals("java.awt.Image")) {
/*  96 */       InputStream inputStream = null;
/*  97 */       BufferedImage jpegLoadImage = null;
/*     */       
/*     */       try {
/* 100 */         inputStream = ds.getInputStream();
/* 101 */         jpegLoadImage = ImageIO.read(inputStream);
/*     */       }
/* 103 */       catch (Exception e) {
/* 104 */         System.out.println(e);
/*     */       } 
/*     */       
/* 107 */       return jpegLoadImage;
/*     */     } 
/*     */     
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getContent(DataSource ds) {
/* 117 */     InputStream inputStream = null;
/* 118 */     BufferedImage jpegLoadImage = null;
/*     */     
/*     */     try {
/* 121 */       inputStream = ds.getInputStream();
/* 122 */       jpegLoadImage = ImageIO.read(inputStream);
/*     */     }
/* 124 */     catch (Exception e) {}
/*     */ 
/*     */     
/* 127 */     return jpegLoadImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
/* 137 */     if (!mimeType.equals("image/jpeg")) {
/* 138 */       throw new IOException("Invalid content type \"" + mimeType + "\" for ImageContentHandler");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (obj == null) {
/* 144 */       throw new IOException("Null object for ImageContentHandler");
/*     */     }
/*     */     
/*     */     try {
/* 148 */       BufferedImage bufImage = null;
/* 149 */       if (obj instanceof BufferedImage) {
/* 150 */         bufImage = (BufferedImage)obj;
/*     */       } else {
/*     */         
/* 153 */         Image img = (Image)obj;
/* 154 */         MediaTracker tracker = new MediaTracker(this);
/* 155 */         tracker.addImage(img, 0);
/* 156 */         tracker.waitForAll();
/* 157 */         if (tracker.isErrorAny()) {
/* 158 */           throw new IOException("Error while loading image");
/*     */         }
/* 160 */         bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 166 */         Graphics g = bufImage.createGraphics();
/* 167 */         g.drawImage(img, 0, 0, null);
/*     */       } 
/* 169 */       ImageIO.write(bufImage, "jpeg", os);
/*     */     }
/* 171 */     catch (Exception ex) {
/* 172 */       throw new IOException("Unable to run the JPEG Encoder on a stream " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\JpegDataContentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */