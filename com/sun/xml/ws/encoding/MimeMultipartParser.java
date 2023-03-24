/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentEx;
/*     */ import com.sun.xml.ws.developer.StreamingAttachmentFeature;
/*     */ import com.sun.xml.ws.developer.StreamingDataHandler;
/*     */ import com.sun.xml.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.ws.util.ByteArrayDataSource;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.jvnet.mimepull.Header;
/*     */ import org.jvnet.mimepull.MIMEMessage;
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
/*     */ public final class MimeMultipartParser
/*     */ {
/*     */   private final String start;
/*     */   private final MIMEMessage message;
/*     */   private Attachment root;
/*     */   private ContentTypeImpl contentType;
/*  88 */   private final Map<String, Attachment> attachments = new HashMap<String, Attachment>();
/*     */   
/*     */   private boolean gotAll;
/*     */   
/*     */   public MimeMultipartParser(InputStream in, String cType, StreamingAttachmentFeature feature) {
/*  93 */     this.contentType = new ContentTypeImpl(cType);
/*     */ 
/*     */     
/*  96 */     String boundary = this.contentType.getBoundary();
/*  97 */     if (boundary == null || boundary.equals("")) {
/*  98 */       throw new WebServiceException("MIME boundary parameter not found" + this.contentType);
/*     */     }
/* 100 */     this.message = (feature != null) ? new MIMEMessage(in, boundary, feature.getConfig()) : new MIMEMessage(in, boundary);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     String st = this.contentType.getRootId();
/* 106 */     if (st != null && st.length() > 2 && st.charAt(0) == '<' && st.charAt(st.length() - 1) == '>') {
/* 107 */       st = st.substring(1, st.length() - 1);
/*     */     }
/* 109 */     this.start = st;
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
/*     */   @Nullable
/*     */   public Attachment getRootPart() {
/* 122 */     if (this.root == null) {
/* 123 */       this.root = (Attachment)new PartAttachment((this.start != null) ? this.message.getPart(this.start) : this.message.getPart(0));
/*     */     }
/* 125 */     return this.root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Map<String, Attachment> getAttachmentParts() {
/* 134 */     if (!this.gotAll) {
/* 135 */       MIMEPart rootPart = (this.start != null) ? this.message.getPart(this.start) : this.message.getPart(0);
/* 136 */       List<MIMEPart> parts = this.message.getAttachments();
/* 137 */       for (MIMEPart part : parts) {
/* 138 */         if (part != rootPart) {
/* 139 */           String cid = part.getContentId();
/* 140 */           if (!this.attachments.containsKey(cid)) {
/* 141 */             PartAttachment attach = new PartAttachment(part);
/* 142 */             this.attachments.put(attach.getContentId(), attach);
/*     */           } 
/*     */         } 
/*     */       } 
/* 146 */       this.gotAll = true;
/*     */     } 
/* 148 */     return this.attachments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Attachment getAttachmentPart(String contentId) throws IOException {
/*     */     PartAttachment partAttachment;
/* 160 */     Attachment attach = this.attachments.get(contentId);
/* 161 */     if (attach == null) {
/* 162 */       MIMEPart part = this.message.getPart(contentId);
/* 163 */       partAttachment = new PartAttachment(part);
/* 164 */       this.attachments.put(contentId, partAttachment);
/*     */     } 
/* 166 */     return (Attachment)partAttachment;
/*     */   }
/*     */   
/*     */   static class PartAttachment
/*     */     implements AttachmentEx {
/*     */     final MIMEPart part;
/*     */     byte[] buf;
/*     */     private StreamingDataHandler streamingDataHandler;
/*     */     
/*     */     PartAttachment(MIMEPart part) {
/* 176 */       this.part = part;
/*     */     }
/*     */     @NotNull
/*     */     public String getContentId() {
/* 180 */       return this.part.getContentId();
/*     */     }
/*     */     @NotNull
/*     */     public String getContentType() {
/* 184 */       return this.part.getContentType();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] asByteArray() {
/* 189 */       if (this.buf == null) {
/* 190 */         ByteArrayBuffer baf = new ByteArrayBuffer();
/*     */         try {
/* 192 */           baf.write(this.part.readOnce());
/* 193 */         } catch (IOException ioe) {
/* 194 */           throw new WebServiceException(ioe);
/*     */         } finally {
/* 196 */           if (baf != null) {
/*     */             try {
/* 198 */               baf.close();
/* 199 */             } catch (IOException ex) {
/* 200 */               Logger.getLogger(MimeMultipartParser.class.getName()).log(Level.FINE, (String)null, ex);
/*     */             } 
/*     */           }
/*     */         } 
/* 204 */         this.buf = baf.toByteArray();
/*     */       } 
/* 206 */       return this.buf;
/*     */     }
/*     */ 
/*     */     
/*     */     public DataHandler asDataHandler() {
/* 211 */       if (this.streamingDataHandler == null) {
/* 212 */         this.streamingDataHandler = (this.buf != null) ? new DataSourceStreamingDataHandler((DataSource)new ByteArrayDataSource(this.buf, getContentType())) : new MIMEPartStreamingDataHandler(this.part);
/*     */       }
/*     */ 
/*     */       
/* 216 */       return (DataHandler)this.streamingDataHandler;
/*     */     }
/*     */ 
/*     */     
/*     */     public Source asSource() {
/* 221 */       return (this.buf != null) ? new StreamSource(new ByteArrayInputStream(this.buf)) : new StreamSource(this.part.read());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream asInputStream() {
/* 228 */       return (this.buf != null) ? new ByteArrayInputStream(this.buf) : this.part.read();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeTo(OutputStream os) throws IOException {
/* 234 */       if (this.buf != null) {
/* 235 */         os.write(this.buf);
/*     */       } else {
/* 237 */         InputStream in = this.part.read();
/* 238 */         byte[] temp = new byte[8192];
/*     */         int len;
/* 240 */         while ((len = in.read(temp)) != -1) {
/* 241 */           os.write(temp, 0, len);
/*     */         }
/* 243 */         in.close();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 249 */       saaj.createAttachmentPart().setDataHandler(asDataHandler());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Iterator<AttachmentEx.MimeHeader> getMimeHeaders() {
/* 255 */       final Iterator<? extends Header> ih = this.part.getAllHeaders().iterator();
/*     */       
/* 257 */       return new Iterator<AttachmentEx.MimeHeader>()
/*     */         {
/*     */           public boolean hasNext() {
/* 260 */             return ih.hasNext();
/*     */           }
/*     */ 
/*     */           
/*     */           public AttachmentEx.MimeHeader next() {
/* 265 */             final Header hdr = ih.next();
/* 266 */             return new AttachmentEx.MimeHeader()
/*     */               {
/*     */                 public String getValue() {
/* 269 */                   return hdr.getValue();
/*     */                 }
/*     */                 
/*     */                 public String getName() {
/* 273 */                   return hdr.getName();
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 280 */             throw new UnsupportedOperationException();
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */   
/*     */   public ContentTypeImpl getContentType() {
/* 287 */     return this.contentType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\MimeMultipartParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */