/*     */ package com.sun.xml.messaging.saaj.soap;
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
/*     */ import java.util.logging.Level;
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
/*     */ public class ImageDataContentHandler
/*     */   extends Component
/*     */   implements DataContentHandler
/*     */ {
/*  62 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap", "com.sun.xml.messaging.saaj.soap.LocalStrings");
/*     */ 
/*     */   
/*     */   private DataFlavor[] flavor;
/*     */ 
/*     */   
/*     */   public ImageDataContentHandler() {
/*  69 */     String[] mimeTypes = ImageIO.getReaderMIMETypes();
/*  70 */     this.flavor = new DataFlavor[mimeTypes.length];
/*  71 */     for (int i = 0; i < mimeTypes.length; i++) {
/*  72 */       this.flavor[i] = new ActivationDataFlavor(Image.class, mimeTypes[i], "Image");
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
/* 100 */     for (int i = 0; i < this.flavor.length; i++) {
/* 101 */       if (this.flavor[i].equals(df)) {
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
/* 134 */       BufferedImage bufImage = null;
/* 135 */       if (obj instanceof BufferedImage) {
/* 136 */         bufImage = (BufferedImage)obj;
/* 137 */       } else if (obj instanceof Image) {
/* 138 */         bufImage = render((Image)obj);
/*     */       } else {
/* 140 */         log.log(Level.SEVERE, "SAAJ0520.soap.invalid.obj.type", (Object[])new String[] { obj.getClass().toString() });
/*     */ 
/*     */         
/* 143 */         throw new IOException("ImageDataContentHandler requires Image object, was given object of type " + obj.getClass().toString());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 148 */       ImageWriter writer = null;
/* 149 */       Iterator<ImageWriter> i = ImageIO.getImageWritersByMIMEType(type);
/* 150 */       if (i.hasNext()) {
/* 151 */         writer = i.next();
/*     */       }
/* 153 */       if (writer != null) {
/* 154 */         ImageOutputStream stream = null;
/* 155 */         stream = ImageIO.createImageOutputStream(os);
/* 156 */         writer.setOutput(stream);
/* 157 */         writer.write(bufImage);
/* 158 */         writer.dispose();
/* 159 */         stream.close();
/*     */       } else {
/* 161 */         log.log(Level.SEVERE, "SAAJ0526.soap.unsupported.mime.type", (Object[])new String[] { type });
/*     */         
/* 163 */         throw new IOException("Unsupported mime type:" + type);
/*     */       } 
/* 165 */     } catch (Exception e) {
/* 166 */       log.severe("SAAJ0525.soap.cannot.encode.img");
/* 167 */       throw new IOException("Unable to encode the image to a stream " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferedImage render(Image img) throws InterruptedException {
/* 175 */     MediaTracker tracker = new MediaTracker(this);
/* 176 */     tracker.addImage(img, 0);
/* 177 */     tracker.waitForAll();
/* 178 */     BufferedImage bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), 1);
/*     */     
/* 180 */     Graphics g = bufImage.createGraphics();
/* 181 */     g.drawImage(img, 0, 0, null);
/* 182 */     g.dispose();
/* 183 */     return bufImage;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ImageDataContentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */