/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.oracle.webservices.api.message.ContentType;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentEx;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.developer.StreamingAttachmentFeature;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.util.Iterator;
/*     */ import java.util.UUID;
/*     */ import javax.activation.CommandMap;
/*     */ import javax.activation.MailcapCommandMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MimeCodec
/*     */   implements Codec
/*     */ {
/*     */   public static final String MULTIPART_RELATED_MIME_TYPE = "multipart/related";
/*     */   protected Codec mimeRootCodec;
/*     */   protected final SOAPVersion version;
/*     */   protected final WSFeatureList features;
/*     */   
/*     */   static {
/*     */     try {
/*  84 */       CommandMap map = CommandMap.getDefaultCommandMap();
/*  85 */       if (map instanceof MailcapCommandMap) {
/*  86 */         MailcapCommandMap mailMap = (MailcapCommandMap)map;
/*  87 */         String hndlrStr = ";;x-java-content-handler=";
/*     */ 
/*     */         
/*  90 */         mailMap.addMailcap("text/xml" + hndlrStr + XmlDataContentHandler.class.getName());
/*     */         
/*  92 */         mailMap.addMailcap("application/xml" + hndlrStr + XmlDataContentHandler.class.getName());
/*     */         
/*  94 */         if (map.createDataContentHandler("image/*") == null) {
/*  95 */           mailMap.addMailcap("image/*" + hndlrStr + ImageDataContentHandler.class.getName());
/*     */         }
/*     */         
/*  98 */         if (map.createDataContentHandler("text/plain") == null) {
/*  99 */           mailMap.addMailcap("text/plain" + hndlrStr + StringDataContentHandler.class.getName());
/*     */         }
/*     */       }
/*     */     
/* 103 */     } catch (Throwable t) {}
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
/*     */   protected MimeCodec(SOAPVersion version, WSFeatureList f) {
/* 115 */     this.version = version;
/* 116 */     this.features = f;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/* 120 */     return "multipart/related";
/*     */   }
/*     */   
/*     */   protected Codec getMimeRootCodec(Packet packet) {
/* 124 */     return this.mimeRootCodec;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) throws IOException {
/* 130 */     Message msg = packet.getMessage();
/* 131 */     if (msg == null) {
/* 132 */       return null;
/*     */     }
/* 134 */     ContentTypeImpl ctImpl = (ContentTypeImpl)getStaticContentType(packet);
/* 135 */     String boundary = ctImpl.getBoundary();
/* 136 */     boolean hasAttachments = (boundary != null);
/* 137 */     Codec rootCodec = getMimeRootCodec(packet);
/* 138 */     if (hasAttachments) {
/* 139 */       writeln("--" + boundary, out);
/* 140 */       ContentType ct = rootCodec.getStaticContentType(packet);
/* 141 */       String ctStr = (ct != null) ? ct.getContentType() : rootCodec.getMimeType();
/* 142 */       writeln("Content-Type: " + ctStr, out);
/* 143 */       writeln(out);
/*     */     } 
/* 145 */     ContentType primaryCt = rootCodec.encode(packet, out);
/*     */     
/* 147 */     if (hasAttachments) {
/* 148 */       writeln(out);
/*     */       
/* 150 */       for (Attachment att : msg.getAttachments()) {
/* 151 */         writeln("--" + boundary, out);
/*     */ 
/*     */         
/* 154 */         String cid = att.getContentId();
/* 155 */         if (cid != null && cid.length() > 0 && cid.charAt(0) != '<')
/* 156 */           cid = '<' + cid + '>'; 
/* 157 */         writeln("Content-Id:" + cid, out);
/* 158 */         writeln("Content-Type: " + att.getContentType(), out);
/* 159 */         writeCustomMimeHeaders(att, out);
/* 160 */         writeln("Content-Transfer-Encoding: binary", out);
/* 161 */         writeln(out);
/* 162 */         att.writeTo(out);
/* 163 */         writeln(out);
/*     */       } 
/* 165 */       writeAsAscii("--" + boundary, out);
/* 166 */       writeAsAscii("--", out);
/*     */     } 
/*     */     
/* 169 */     return hasAttachments ? ctImpl : primaryCt;
/*     */   }
/*     */   
/*     */   private void writeCustomMimeHeaders(Attachment att, OutputStream out) throws IOException {
/* 173 */     if (att instanceof AttachmentEx) {
/* 174 */       Iterator<AttachmentEx.MimeHeader> allMimeHeaders = ((AttachmentEx)att).getMimeHeaders();
/* 175 */       while (allMimeHeaders.hasNext()) {
/* 176 */         AttachmentEx.MimeHeader mh = allMimeHeaders.next();
/* 177 */         String name = mh.getName();
/*     */         
/* 179 */         if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Id".equalsIgnoreCase(name)) {
/* 180 */           writeln(name + ": " + mh.getValue(), out);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 187 */     ContentType ct = (ContentType)packet.getInternalContentType();
/* 188 */     if (ct != null) return ct; 
/* 189 */     Message msg = packet.getMessage();
/* 190 */     boolean hasAttachments = !msg.getAttachments().isEmpty();
/* 191 */     Codec rootCodec = getMimeRootCodec(packet);
/*     */     
/* 193 */     if (hasAttachments) {
/* 194 */       String boundary = "uuid:" + UUID.randomUUID().toString();
/* 195 */       String boundaryParameter = "boundary=\"" + boundary + "\"";
/*     */       
/* 197 */       String messageContentType = "multipart/related; type=\"" + rootCodec.getMimeType() + "\"; " + boundaryParameter;
/*     */ 
/*     */       
/* 200 */       ContentTypeImpl impl = new ContentTypeImpl(messageContentType, packet.soapAction, null);
/* 201 */       impl.setBoundary(boundary);
/* 202 */       impl.setBoundaryParameter(boundaryParameter);
/* 203 */       packet.setContentType((ContentType)impl);
/* 204 */       return impl;
/*     */     } 
/* 206 */     ct = rootCodec.getStaticContentType(packet);
/* 207 */     packet.setContentType((ContentType)ct);
/* 208 */     return ct;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MimeCodec(MimeCodec that) {
/* 216 */     this.version = that.version;
/* 217 */     this.features = that.features;
/*     */   }
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/* 221 */     MimeMultipartParser parser = new MimeMultipartParser(in, contentType, (StreamingAttachmentFeature)this.features.get(StreamingAttachmentFeature.class));
/* 222 */     decode(parser, packet);
/*     */   }
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet packet) {
/* 226 */     throw new UnsupportedOperationException();
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
/*     */   public static void writeln(String s, OutputStream out) throws IOException {
/* 238 */     writeAsAscii(s, out);
/* 239 */     writeln(out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeAsAscii(String s, OutputStream out) throws IOException {
/* 246 */     int len = s.length();
/* 247 */     for (int i = 0; i < len; i++)
/* 248 */       out.write((byte)s.charAt(i)); 
/*     */   }
/*     */   
/*     */   public static void writeln(OutputStream out) throws IOException {
/* 252 */     out.write(13);
/* 253 */     out.write(10);
/*     */   }
/*     */   
/*     */   protected abstract void decode(MimeMultipartParser paramMimeMultipartParser, Packet paramPacket) throws IOException;
/*     */   
/*     */   public abstract MimeCodec copy();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\MimeCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */