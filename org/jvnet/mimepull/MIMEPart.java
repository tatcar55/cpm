/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
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
/*     */ 
/*     */ public class MIMEPart
/*     */ {
/*  61 */   private static final Logger LOGGER = Logger.getLogger(MIMEPart.class.getName());
/*     */   
/*     */   private volatile InternetHeaders headers;
/*     */   
/*     */   private volatile String contentId;
/*     */   private String contentType;
/*     */   private String contentTransferEncoding;
/*     */   volatile boolean parsed;
/*     */   final MIMEMessage msg;
/*     */   private final DataHead dataHead;
/*     */   
/*     */   MIMEPart(MIMEMessage msg) {
/*  73 */     this.msg = msg;
/*  74 */     this.dataHead = new DataHead(this);
/*     */   }
/*     */   
/*     */   MIMEPart(MIMEMessage msg, String contentId) {
/*  78 */     this(msg);
/*  79 */     this.contentId = contentId;
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
/*     */   public InputStream read() {
/*  92 */     InputStream is = null;
/*     */     try {
/*  94 */       is = MimeUtility.decode(this.dataHead.read(), this.contentTransferEncoding);
/*  95 */     } catch (DecodingException ex) {
/*  96 */       if (LOGGER.isLoggable(Level.WARNING)) {
/*  97 */         LOGGER.log(Level.WARNING, (String)null, ex);
/*     */       }
/*     */     } 
/* 100 */     return is;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 109 */     this.dataHead.close();
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
/*     */   public InputStream readOnce() {
/* 125 */     InputStream is = null;
/*     */     try {
/* 127 */       is = MimeUtility.decode(this.dataHead.readOnce(), this.contentTransferEncoding);
/* 128 */     } catch (DecodingException ex) {
/* 129 */       if (LOGGER.isLoggable(Level.WARNING)) {
/* 130 */         LOGGER.log(Level.WARNING, (String)null, ex);
/*     */       }
/*     */     } 
/* 133 */     return is;
/*     */   }
/*     */   
/*     */   public void moveTo(File f) {
/* 137 */     this.dataHead.moveTo(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentId() {
/* 146 */     if (this.contentId == null) {
/* 147 */       getHeaders();
/*     */     }
/* 149 */     return this.contentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentTransferEncoding() {
/* 158 */     if (this.contentTransferEncoding == null) {
/* 159 */       getHeaders();
/*     */     }
/* 161 */     return this.contentTransferEncoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 170 */     if (this.contentType == null) {
/* 171 */       getHeaders();
/*     */     }
/* 173 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   private void getHeaders() {
/* 178 */     while (this.headers == null) {
/* 179 */       if (!this.msg.makeProgress() && 
/* 180 */         this.headers == null) {
/* 181 */         throw new IllegalStateException("Internal Error. Didn't get Headers even after complete parsing.");
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
/*     */   public List<String> getHeader(String name) {
/* 196 */     getHeaders();
/* 197 */     assert this.headers != null;
/* 198 */     return this.headers.getHeader(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<? extends Header> getAllHeaders() {
/* 207 */     getHeaders();
/* 208 */     assert this.headers != null;
/* 209 */     return this.headers.getAllHeaders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setHeaders(InternetHeaders headers) {
/* 218 */     this.headers = headers;
/* 219 */     List<String> ct = getHeader("Content-Type");
/* 220 */     this.contentType = (ct == null) ? "application/octet-stream" : ct.get(0);
/* 221 */     List<String> cte = getHeader("Content-Transfer-Encoding");
/* 222 */     this.contentTransferEncoding = (cte == null) ? "binary" : cte.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addBody(ByteBuffer buf) {
/* 231 */     this.dataHead.addBody(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void doneParsing() {
/* 239 */     this.parsed = true;
/* 240 */     this.dataHead.doneParsing();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setContentId(String cid) {
/* 248 */     this.contentId = cid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setContentTransferEncoding(String cte) {
/* 256 */     this.contentTransferEncoding = cte;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 261 */     return "Part=" + this.contentId + ":" + this.contentTransferEncoding;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\MIMEPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */