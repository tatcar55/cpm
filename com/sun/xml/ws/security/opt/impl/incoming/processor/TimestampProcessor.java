/*     */ package com.sun.xml.ws.security.opt.impl.incoming.processor;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.wss.BasicSecurityProfile;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.StreamFilter;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimestampProcessor
/*     */   implements StreamFilter
/*     */ {
/*  55 */   private String created = null;
/*  56 */   private String expires = null;
/*  57 */   private String currentElement = "";
/*  58 */   private JAXBFilterProcessingContext context = null;
/*  59 */   private static String EXPIRES = "Expires".intern();
/*  60 */   private static String CREATED = "Created".intern();
/*     */   
/*     */   public TimestampProcessor(JAXBFilterProcessingContext ctx) {
/*  63 */     this.context = ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(XMLStreamReader reader) {
/*  71 */     if (reader.getEventType() == 1) {
/*  72 */       if ("Created".equals(reader.getLocalName())) {
/*  73 */         this.currentElement = CREATED;
/*  74 */         if (this.context.isBSP() && this.created != null) {
/*  75 */           BasicSecurityProfile.log_bsp_3203();
/*     */         }
/*  77 */         if (this.context.isBSP() && hasValueType(reader)) {
/*  78 */           BasicSecurityProfile.log_bsp_3225();
/*     */         }
/*     */       }
/*  81 */       else if ("Expires".equals(reader.getLocalName())) {
/*  82 */         if (this.context.isBSP() && this.expires != null) {
/*  83 */           BasicSecurityProfile.log_bsp_3224();
/*     */         }
/*  85 */         if (this.context.isBSP() && this.created == null) {
/*  86 */           BasicSecurityProfile.log_bsp_3221();
/*     */         }
/*     */         
/*  89 */         if (this.context.isBSP() && hasValueType(reader)) {
/*  90 */           BasicSecurityProfile.log_bsp_3226();
/*     */         }
/*  92 */         this.currentElement = EXPIRES;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     if (reader.getEventType() == 4) {
/* 102 */       if (this.currentElement == CREATED) {
/* 103 */         this.created = reader.getText();
/* 104 */         this.currentElement = "";
/* 105 */       } else if (this.currentElement == EXPIRES) {
/* 106 */         this.expires = reader.getText();
/* 107 */         this.currentElement = "";
/*     */       } 
/*     */     }
/* 110 */     return true;
/*     */   }
/*     */   
/*     */   public String getCreated() {
/* 114 */     return this.created;
/*     */   }
/*     */   
/*     */   public String getExpires() {
/* 118 */     return this.expires;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasValueType(XMLStreamReader reader) {
/* 123 */     for (int i = 0; i < reader.getAttributeCount(); i++) {
/* 124 */       QName name = reader.getAttributeName(i);
/* 125 */       if (name != null && "ValueType".equals(name.getLocalPart())) {
/* 126 */         return true;
/*     */       }
/*     */     } 
/* 129 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\processor\TimestampProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */