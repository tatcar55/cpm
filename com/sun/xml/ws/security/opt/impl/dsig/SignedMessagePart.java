/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SignedData;
/*     */ import com.sun.xml.ws.security.opt.impl.message.SOAPBody;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignedMessagePart
/*     */   implements SecurityElement, SignedData, SecurityElementWriter
/*     */ {
/*     */   protected boolean isCanonicalized = false;
/*  66 */   private SecurityElement se = null;
/*  67 */   private SOAPBody body = null;
/*     */   private boolean contentOnly = false;
/*  69 */   private List attributeValuePrefixes = null;
/*     */   
/*  71 */   private ByteArrayOutputStream storedStream = new ByteArrayOutputStream();
/*     */   
/*  73 */   protected byte[] digestValue = null;
/*     */ 
/*     */   
/*     */   public SignedMessagePart() {}
/*     */ 
/*     */   
/*     */   public SignedMessagePart(SecurityElement se) {
/*  80 */     this.se = se;
/*     */   }
/*     */   
/*     */   public SignedMessagePart(SOAPBody body, boolean contentOnly) {
/*  84 */     this.body = body;
/*  85 */     this.contentOnly = contentOnly;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  89 */     if (this.body != null) {
/*  90 */       if (!this.contentOnly) {
/*  91 */         return this.body.getId();
/*     */       }
/*  93 */       return this.body.getBodyContentId();
/*     */     } 
/*     */     
/*  96 */     return this.se.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/* 101 */     if (this.body != null) {
/* 102 */       if (!this.contentOnly) {
/* 103 */         this.body.setId(id);
/*     */       } else {
/* 105 */         this.body.setBodyContentId(id);
/*     */       } 
/*     */     } else {
/* 108 */       this.se.setId(id);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 113 */     if (this.body != null) {
/* 114 */       if (!this.contentOnly) {
/* 115 */         return (this.body.getSOAPVersion()).nsUri;
/*     */       }
/* 117 */       return this.body.getPayloadNamespaceURI();
/*     */     } 
/*     */     
/* 120 */     return this.se.getNamespaceURI();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalPart() {
/* 125 */     if (this.body != null) {
/* 126 */       if (!this.contentOnly) {
/* 127 */         return "Body";
/*     */       }
/* 129 */       return this.body.getPayloadLocalPart();
/*     */     } 
/*     */     
/* 132 */     return this.se.getLocalPart();
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 137 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/*     */     try {
/* 142 */       if (this.isCanonicalized)
/* 143 */         writeCanonicalized(os); 
/* 144 */     } catch (IOException ioe) {
/* 145 */       throw new XWSSecurityRuntimeException(ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 150 */     if (this.body != null) {
/* 151 */       this.body.cachePayLoad();
/* 152 */       this.attributeValuePrefixes = this.body.getAttributeValuePrefixes();
/* 153 */       if (!this.contentOnly) {
/* 154 */         this.body.writeTo(streamWriter);
/*     */       } else {
/* 156 */         this.body.writePayload(streamWriter);
/*     */       } 
/*     */     } else {
/* 159 */       ((SecurityElementWriter)this.se).writeTo(streamWriter);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 165 */     writeTo(streamWriter);
/*     */   }
/*     */   
/*     */   public void writeCanonicalized(OutputStream os) throws IOException {
/* 169 */     if (this.storedStream == null)
/*     */       return; 
/* 171 */     this.storedStream.writeTo(os);
/*     */   }
/*     */   
/*     */   public void setDigestValue(byte[] digestValue) {
/* 175 */     this.digestValue = digestValue;
/*     */   }
/*     */   
/*     */   public byte[] getDigestValue() {
/* 179 */     return this.digestValue;
/*     */   }
/*     */   
/*     */   public List getAttributeValuePrefixes() {
/* 183 */     return this.attributeValuePrefixes;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\SignedMessagePart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */