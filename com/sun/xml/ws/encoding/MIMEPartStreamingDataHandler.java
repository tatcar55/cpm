/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.sun.xml.ws.developer.StreamingDataHandler;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataSource;
/*     */ import org.jvnet.mimepull.MIMEPart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MIMEPartStreamingDataHandler
/*     */   extends StreamingDataHandler
/*     */ {
/*     */   private final StreamingDataSource ds;
/*     */   
/*     */   public MIMEPartStreamingDataHandler(MIMEPart part) {
/*  76 */     super(new StreamingDataSource(part));
/*  77 */     this.ds = (StreamingDataSource)getDataSource();
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream readOnce() throws IOException {
/*  82 */     return this.ds.readOnce();
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveTo(File file) throws IOException {
/*  87 */     this.ds.moveTo(file);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  92 */     this.ds.close();
/*     */   }
/*     */   
/*     */   private static final class StreamingDataSource implements DataSource {
/*     */     private final MIMEPart part;
/*     */     
/*     */     StreamingDataSource(MIMEPart part) {
/*  99 */       this.part = part;
/*     */     }
/*     */ 
/*     */     
/*     */     public InputStream getInputStream() throws IOException {
/* 104 */       return this.part.read();
/*     */     }
/*     */     
/*     */     InputStream readOnce() throws IOException {
/*     */       try {
/* 109 */         return this.part.readOnce();
/* 110 */       } catch (Exception e) {
/* 111 */         throw new MIMEPartStreamingDataHandler.MyIOException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     void moveTo(File file) throws IOException {
/* 116 */       this.part.moveTo(file);
/*     */     }
/*     */ 
/*     */     
/*     */     public OutputStream getOutputStream() throws IOException {
/* 121 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getContentType() {
/* 126 */       return this.part.getContentType();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 131 */       return "";
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 135 */       this.part.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class MyIOException extends IOException {
/*     */     private final Exception linkedException;
/*     */     
/*     */     MyIOException(Exception linkedException) {
/* 143 */       this.linkedException = linkedException;
/*     */     }
/*     */ 
/*     */     
/*     */     public Throwable getCause() {
/* 148 */       return this.linkedException;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\MIMEPartStreamingDataHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */