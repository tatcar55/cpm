/*     */ package com.sun.xml.messaging.saaj.packaging.mime.internet;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
/*     */ import com.sun.xml.messaging.saaj.soap.AttachmentPartImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import javax.activation.DataSource;
/*     */ import org.jvnet.mimepull.MIMEConfig;
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
/*     */ public class MimePullMultipart
/*     */   extends MimeMultipart
/*     */ {
/*  60 */   private InputStream in = null;
/*  61 */   private String boundary = null;
/*  62 */   private MIMEMessage mm = null;
/*  63 */   private DataSource dataSource = null;
/*  64 */   private ContentType contType = null;
/*  65 */   private String startParam = null;
/*  66 */   private MIMEPart soapPart = null;
/*     */ 
/*     */   
/*     */   public MimePullMultipart(DataSource ds, ContentType ct) throws MessagingException {
/*  70 */     this.parsed = false;
/*  71 */     if (ct == null) {
/*  72 */       this.contType = new ContentType(ds.getContentType());
/*     */     } else {
/*  74 */       this.contType = ct;
/*     */     } 
/*  76 */     this.dataSource = ds;
/*  77 */     this.boundary = this.contType.getParameter("boundary");
/*     */   }
/*     */   
/*     */   public MIMEPart readAndReturnSOAPPart() throws MessagingException {
/*  81 */     if (this.soapPart != null) {
/*  82 */       throw new MessagingException("Inputstream from datasource was already consumed");
/*     */     }
/*  84 */     readSOAPPart();
/*  85 */     return this.soapPart;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSOAPPart() throws MessagingException {
/*     */     try {
/*  91 */       if (this.soapPart != null) {
/*     */         return;
/*     */       }
/*  94 */       this.in = this.dataSource.getInputStream();
/*  95 */       MIMEConfig config = new MIMEConfig();
/*  96 */       this.mm = new MIMEMessage(this.in, this.boundary, config);
/*  97 */       String st = this.contType.getParameter("start");
/*  98 */       if (this.startParam == null) {
/*  99 */         this.soapPart = this.mm.getPart(0);
/*     */       } else {
/*     */         
/* 102 */         if (st != null && st.length() > 2 && st.charAt(0) == '<' && st.charAt(st.length() - 1) == '>') {
/* 103 */           st = st.substring(1, st.length() - 1);
/*     */         }
/* 105 */         this.startParam = st;
/* 106 */         this.soapPart = this.mm.getPart(this.startParam);
/*     */       }
/*     */     
/* 109 */     } catch (IOException ex) {
/* 110 */       throw new MessagingException("No inputstream from datasource", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void parseAll() throws MessagingException {
/* 115 */     if (this.parsed) {
/*     */       return;
/*     */     }
/* 118 */     if (this.soapPart == null) {
/* 119 */       readSOAPPart();
/*     */     }
/*     */     
/* 122 */     List<MIMEPart> prts = this.mm.getAttachments();
/* 123 */     for (MIMEPart part : prts) {
/* 124 */       if (part != this.soapPart) {
/* 125 */         AttachmentPartImpl attachmentPartImpl = new AttachmentPartImpl(part);
/* 126 */         addBodyPart(new MimeBodyPart(part));
/*     */       } 
/*     */     } 
/* 129 */     this.parsed = true;
/*     */   }
/*     */   
/*     */   protected void parse() throws MessagingException {
/* 133 */     parseAll();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mime\internet\MimePullMultipart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */