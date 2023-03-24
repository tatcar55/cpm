/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.MediaTracker;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.ActivationDataFlavor;
/*     */ import javax.activation.DataContentHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageWriter;
/*     */ import javax.imageio.stream.ImageOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageDataContentHandler
/*     */   extends Component
/*     */   implements DataContentHandler
/*     */ {
/*  66 */   private static final Logger log = Logger.getLogger(ImageDataContentHandler.class.getName());
/*     */   private final DataFlavor[] flavor;
/*     */   
/*     */   public ImageDataContentHandler() {
/*  70 */     String[] mimeTypes = ImageIO.getReaderMIMETypes();
/*  71 */     this.flavor = new DataFlavor[mimeTypes.length];
/*  72 */     for (int i = 0; i < mimeTypes.length; i++) {
/*  73 */       this.flavor[i] = new ActivationDataFlavor(Image.class, mimeTypes[i], "Image");
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
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  86 */     return Arrays.<DataFlavor>copyOf(this.flavor, this.flavor.length);
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
/*     */   public Object getTransferData(DataFlavor df, DataSource ds) throws IOException {
/* 100 */     for (DataFlavor aFlavor : this.flavor) {
/* 101 */       if (aFlavor.equals(df)) {
/* 102 */         return getContent(ds);
/*     */       }
/*     */     } 
/* 105 */     return null;
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
/*     */   public Object getContent(DataSource ds) throws IOException {
/* 117 */     return ImageIO.read(new BufferedInputStream(ds.getInputStream()));
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
/*     */   public void writeTo(Object obj, String type, OutputStream os) throws IOException {
/*     */     try {
/*     */       BufferedImage bufImage;
/* 135 */       if (obj instanceof BufferedImage) {
/* 136 */         bufImage = (BufferedImage)obj;
/* 137 */       } else if (obj instanceof Image) {
/* 138 */         bufImage = render((Image)obj);
/*     */       } else {
/* 140 */         throw new IOException("ImageDataContentHandler requires Image object, was given object of type " + obj.getClass().toString());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 145 */       ImageWriter writer = null;
/* 146 */       Iterator<ImageWriter> i = ImageIO.getImageWritersByMIMEType(type);
/* 147 */       if (i.hasNext()) {
/* 148 */         writer = i.next();
/*     */       }
/* 150 */       if (writer != null) {
/* 151 */         ImageOutputStream stream = ImageIO.createImageOutputStream(os);
/* 152 */         writer.setOutput(stream);
/* 153 */         writer.write(bufImage);
/* 154 */         writer.dispose();
/* 155 */         stream.close();
/*     */       } else {
/* 157 */         throw new IOException("Unsupported mime type:" + type);
/*     */       } 
/* 159 */     } catch (Exception e) {
/* 160 */       BufferedImage bufImage; throw new IOException("Unable to encode the image to a stream " + bufImage.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferedImage render(Image img) throws InterruptedException {
/* 168 */     MediaTracker tracker = new MediaTracker(this);
/* 169 */     tracker.addImage(img, 0);
/* 170 */     tracker.waitForAll();
/* 171 */     BufferedImage bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), 1);
/*     */     
/* 173 */     Graphics g = bufImage.createGraphics();
/* 174 */     g.drawImage(img, 0, 0, null);
/* 175 */     g.dispose();
/* 176 */     return bufImage;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\ImageDataContentHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */