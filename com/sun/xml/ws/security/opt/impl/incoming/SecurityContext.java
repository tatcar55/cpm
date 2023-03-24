/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.security.opt.impl.attachment.AttachmentSetImpl;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityContext
/*     */ {
/*  58 */   private ArrayList processedSecurityHeaders = new ArrayList(2);
/*  59 */   private ArrayList bufferedSecurityHeaders = null;
/*  60 */   private HeaderList nonSecurityHeaders = null;
/*  61 */   private HashMap<String, String> shND = null;
/*  62 */   private HashMap<String, String> envND = null;
/*  63 */   private AttachmentSet attachments = null;
/*  64 */   private AttachmentSet decryptedAttachments = null;
/*     */   
/*  66 */   private MLSPolicy inferredKB = null;
/*     */   
/*  68 */   private ProcessingContext pc = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSAMLKB = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttachmentSet(AttachmentSet attachments) {
/*  78 */     this.attachments = attachments;
/*     */   }
/*     */   
/*     */   public AttachmentSet getAttachmentSet() {
/*  82 */     if (this.attachments == null) {
/*  83 */       this.attachments = (AttachmentSet)new AttachmentSetImpl();
/*     */     }
/*  85 */     return this.attachments;
/*     */   }
/*     */   
/*     */   public AttachmentSet getDecryptedAttachmentSet() {
/*  89 */     if (this.decryptedAttachments == null) {
/*  90 */       this.decryptedAttachments = (AttachmentSet)new AttachmentSetImpl();
/*     */     }
/*  92 */     return this.decryptedAttachments;
/*     */   }
/*     */   
/*     */   public MLSPolicy getInferredKB() {
/*  96 */     return this.inferredKB;
/*     */   }
/*     */   
/*     */   public void setInferredKB(MLSPolicy inferredKB) {
/* 100 */     this.inferredKB = inferredKB;
/*     */   }
/*     */   
/*     */   public void setProcessedSecurityHeaders(ArrayList headers) {
/* 104 */     this.processedSecurityHeaders = headers;
/*     */   }
/*     */   
/*     */   public ArrayList getProcessedSecurityHeaders() {
/* 108 */     return this.processedSecurityHeaders;
/*     */   }
/*     */   
/*     */   public void setBufferedSecurityHeaders(ArrayList headers) {
/* 112 */     this.bufferedSecurityHeaders = headers;
/*     */   }
/*     */   
/*     */   public ArrayList getBufferedSecurityHeaders() {
/* 116 */     return this.bufferedSecurityHeaders;
/*     */   }
/*     */   
/*     */   public HeaderList getNonSecurityHeaders() {
/* 120 */     return this.nonSecurityHeaders;
/*     */   }
/*     */   
/*     */   public void setNonSecurityHeaders(HeaderList list) {
/* 124 */     this.nonSecurityHeaders = list;
/*     */   }
/*     */   
/*     */   public void setSecurityHdrNSDecls(HashMap<String, String> nsDecls) {
/* 128 */     this.shND = nsDecls;
/*     */   }
/*     */   
/*     */   public HashMap<String, String> getSecurityHdrNSDecls() {
/* 132 */     return this.shND;
/*     */   }
/*     */   
/*     */   public void setSOAPEnvelopeNSDecls(HashMap<String, String> nsDecls) {
/* 136 */     this.envND = nsDecls;
/*     */   }
/*     */   
/*     */   public HashMap<String, String> getSOAPEnvelopeNSDecls() {
/* 140 */     return this.envND;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingContext getProcessingContext() {
/* 146 */     return this.pc;
/*     */   }
/*     */   
/*     */   public void setProcessingContext(ProcessingContext pc) {
/* 150 */     this.pc = pc;
/*     */   }
/*     */   
/*     */   public void setIsSAMLKeyBinding(boolean flag) {
/* 154 */     this.isSAMLKB = flag;
/*     */   }
/*     */   
/*     */   public boolean getIsSAMLKeyBinding() {
/* 158 */     return this.isSAMLKB;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\SecurityContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */