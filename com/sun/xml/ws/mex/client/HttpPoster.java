/*     */ package com.sun.xml.ws.mex.client;
/*     */ 
/*     */ import com.sun.xml.ws.mex.MessagesMessages;
/*     */ import com.sun.xml.ws.mex.MetadataConstants;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.logging.Logger;
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
/*     */ public class HttpPoster
/*     */ {
/*  65 */   private static final Logger logger = Logger.getLogger(HttpPoster.class.getName());
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
/*     */   InputStream post(String request, String address, String contentType) throws IOException {
/*  82 */     URL url = new URL(address);
/*  83 */     HttpURLConnection conn = createConnection(url);
/*  84 */     conn.setDoOutput(true);
/*  85 */     conn.setDoInput(true);
/*  86 */     conn.setRequestMethod("POST");
/*  87 */     conn.setRequestProperty("Content-Type", contentType);
/*  88 */     conn.setRequestProperty("SOAPAction", "\"http://schemas.xmlsoap.org/ws/2004/09/transfer/Get\"");
/*     */     
/*  90 */     Writer writer = new OutputStreamWriter(conn.getOutputStream());
/*  91 */     writer.write(request);
/*  92 */     writer.flush();
/*     */     
/*     */     try {
/*  95 */       return conn.getInputStream();
/*  96 */     } catch (IOException ioe) {
/*  97 */       outputErrorStream(conn);
/*     */ 
/*     */       
/* 100 */       throw ioe;
/*     */     } finally {
/* 102 */       writer.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void outputErrorStream(HttpURLConnection conn) {
/* 108 */     InputStream error = conn.getErrorStream();
/* 109 */     if (error != null) {
/* 110 */       BufferedReader reader = new BufferedReader(new InputStreamReader(error));
/*     */       
/*     */       try {
/* 113 */         if (logger.isLoggable(MetadataConstants.ERROR_LOG_LEVEL)) {
/* 114 */           logger.log(MetadataConstants.ERROR_LOG_LEVEL, MessagesMessages.MEX_0010_ERROR_FROM_SERVER());
/*     */           
/* 116 */           String line = reader.readLine();
/* 117 */           while (line != null) {
/* 118 */             logger.log(MetadataConstants.ERROR_LOG_LEVEL, line);
/* 119 */             line = reader.readLine();
/*     */           } 
/* 121 */           logger.log(MetadataConstants.ERROR_LOG_LEVEL, MessagesMessages.MEX_0011_ERROR_FROM_SERVER_END());
/*     */         }
/*     */       
/* 124 */       } catch (IOException ioe) {
/*     */         
/* 126 */         logger.log(MetadataConstants.ERROR_LOG_LEVEL, MessagesMessages.MEX_0012_READING_ERROR_STREAM_FAILURE(), ioe);
/*     */       } finally {
/*     */ 
/*     */         
/*     */         try {
/* 131 */           reader.close();
/* 132 */         } catch (IOException ex) {
/*     */           
/* 134 */           logger.log(MetadataConstants.ERROR_LOG_LEVEL, MessagesMessages.MEX_0013_CLOSING_ERROR_STREAM_FAILURE(), ex);
/*     */         } 
/*     */       } 
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
/*     */ 
/*     */   
/*     */   public InputStream makeGetCall(String address) throws IOException {
/* 153 */     URL url = new URL(address);
/* 154 */     HttpURLConnection conn = createConnection(url);
/* 155 */     conn.setRequestMethod("GET");
/* 156 */     conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*     */     
/*     */     try {
/* 159 */       return conn.getInputStream();
/* 160 */     } catch (IOException ioe) {
/* 161 */       outputErrorStream(conn);
/*     */ 
/*     */       
/* 164 */       throw ioe;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HttpURLConnection createConnection(URL url) throws IOException {
/* 175 */     return (HttpURLConnection)url.openConnection();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\mex\client\HttpPoster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */