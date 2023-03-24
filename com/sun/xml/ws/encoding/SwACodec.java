/*    */ package com.sun.xml.ws.encoding;
/*    */ 
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.WSFeatureList;
/*    */ import com.sun.xml.ws.api.message.Attachment;
/*    */ import com.sun.xml.ws.api.message.AttachmentSet;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.pipe.Codec;
/*    */ import com.sun.xml.ws.api.pipe.ContentType;
/*    */ import com.sun.xml.ws.message.MimeAttachmentSet;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.channels.ReadableByteChannel;
/*    */ import java.nio.channels.WritableByteChannel;
/*    */ import java.util.Map;
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
/*    */ public final class SwACodec
/*    */   extends MimeCodec
/*    */ {
/*    */   public SwACodec(SOAPVersion version, WSFeatureList f, Codec rootCodec) {
/* 65 */     super(version, f);
/* 66 */     this.mimeRootCodec = rootCodec;
/*    */   }
/*    */   
/*    */   private SwACodec(SwACodec that) {
/* 70 */     super(that);
/* 71 */     this.mimeRootCodec = that.mimeRootCodec.copy();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void decode(MimeMultipartParser mpp, Packet packet) throws IOException {
/* 77 */     Attachment root = mpp.getRootPart();
/* 78 */     Codec rootCodec = getMimeRootCodec(packet);
/* 79 */     if (rootCodec instanceof RootOnlyCodec) {
/* 80 */       ((RootOnlyCodec)rootCodec).decode(root.asInputStream(), root.getContentType(), packet, (AttachmentSet)new MimeAttachmentSet(mpp));
/*    */     } else {
/* 82 */       rootCodec.decode(root.asInputStream(), root.getContentType(), packet);
/* 83 */       Map<String, Attachment> atts = mpp.getAttachmentParts();
/* 84 */       for (Map.Entry<String, Attachment> att : atts.entrySet()) {
/* 85 */         packet.getMessage().getAttachments().add(att.getValue());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 92 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public SwACodec copy() {
/* 96 */     return new SwACodec(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\SwACodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */