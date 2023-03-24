/*    */ package org.jvnet.staxex;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import javax.activation.DataHandler;
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
/*    */ public abstract class StreamingDataHandler
/*    */   extends DataHandler
/*    */   implements Closeable
/*    */ {
/*    */   public StreamingDataHandler(Object o, String s) {
/* 71 */     super(o, s);
/*    */   }
/*    */   
/*    */   public StreamingDataHandler(URL url) {
/* 75 */     super(url);
/*    */   }
/*    */   
/*    */   public StreamingDataHandler(DataSource dataSource) {
/* 79 */     super(dataSource);
/*    */   }
/*    */   
/*    */   public abstract InputStream readOnce() throws IOException;
/*    */   
/*    */   public abstract void moveTo(File paramFile) throws IOException;
/*    */   
/*    */   public abstract void close() throws IOException;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\staxex\StreamingDataHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */