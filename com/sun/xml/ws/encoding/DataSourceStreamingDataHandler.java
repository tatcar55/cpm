/*    */ package com.sun.xml.ws.encoding;
/*    */ 
/*    */ import com.sun.xml.ws.developer.StreamingDataHandler;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.activation.DataSource;
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
/*    */ public class DataSourceStreamingDataHandler
/*    */   extends StreamingDataHandler
/*    */ {
/*    */   public DataSourceStreamingDataHandler(DataSource ds) {
/* 54 */     super(ds);
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream readOnce() throws IOException {
/* 59 */     return getInputStream();
/*    */   }
/*    */ 
/*    */   
/*    */   public void moveTo(File file) throws IOException {
/* 64 */     InputStream in = getInputStream();
/* 65 */     OutputStream os = new FileOutputStream(file);
/*    */     try {
/* 67 */       byte[] temp = new byte[8192];
/*    */       int len;
/* 69 */       while ((len = in.read(temp)) != -1) {
/* 70 */         os.write(temp, 0, len);
/*    */       }
/* 72 */       in.close();
/*    */     } finally {
/* 74 */       if (os != null)
/* 75 */         os.close(); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void close() throws IOException {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\DataSourceStreamingDataHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */