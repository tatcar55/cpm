/*     */ package com.sun.xml.ws.security.opt.impl.crypto;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.message.jaxb.JAXBHeader;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.crypto.JAXBData;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.logging.impl.opt.LogStringsMessages;
/*     */ import java.io.OutputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JAXBDataImpl
/*     */   implements JAXBData
/*     */ {
/*  68 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt", "com.sun.xml.wss.logging.impl.opt.LogStrings");
/*     */   
/*     */   private JAXBElement jb;
/*     */   
/*  72 */   private JAXBContext jc = null;
/*  73 */   private Header header = null;
/*  74 */   private SecurityElement securityElement = null;
/*     */   private boolean contentOnly = false;
/*  76 */   private NamespaceContextEx nsContext = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBDataImpl(JAXBElement jb, JAXBContext jc, boolean contentOnly, NamespaceContextEx nsContext) {
/*  81 */     this.jb = jb;
/*  82 */     this.jc = jc;
/*  83 */     this.contentOnly = contentOnly;
/*  84 */     this.nsContext = nsContext;
/*     */   }
/*     */   
/*     */   public JAXBDataImpl(Header header, boolean contentOnly, NamespaceContextEx nsContext, JAXBContext jcc) {
/*  88 */     this.header = header;
/*  89 */     this.contentOnly = contentOnly;
/*  90 */     this.nsContext = nsContext;
/*  91 */     this.jc = jcc;
/*     */   }
/*     */   
/*     */   public JAXBDataImpl(SecurityElement se, NamespaceContextEx nsContext, boolean contentOnly) {
/*  95 */     this.securityElement = se;
/*  96 */     this.contentOnly = contentOnly;
/*  97 */     this.nsContext = nsContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public JAXBDataImpl(JAXBElement jb, JAXBContext jc, NamespaceContextEx nsContext) {
/* 102 */     this.jb = jb;
/* 103 */     this.jc = jc;
/* 104 */     this.nsContext = nsContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public JAXBDataImpl(Header header) {
/* 109 */     this.header = header;
/*     */   }
/*     */   
/*     */   public JAXBDataImpl(SecurityElement se) {
/* 113 */     this.securityElement = se;
/*     */   }
/*     */   
/*     */   public JAXBElement getJAXBElement() {
/* 117 */     return this.jb;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter writer) throws XWSSecurityException {
/* 121 */     if (this.securityElement != null) {
/*     */       try {
/* 123 */         ((SecurityElementWriter)this.securityElement).writeTo(writer);
/* 124 */       } catch (XMLStreamException ex) {
/* 125 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1609_ERROR_SERIALIZING_ELEMENT(this.securityElement.getLocalPart()));
/* 126 */         throw new XWSSecurityException(LogStringsMessages.WSS_1609_ERROR_SERIALIZING_ELEMENT(this.securityElement.getLocalPart()), ex);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 131 */     if (this.header != null) {
/*     */       try {
/* 133 */         this.header.writeTo(writer);
/* 134 */       } catch (XMLStreamException ex) {
/* 135 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1609_ERROR_SERIALIZING_ELEMENT(this.header.getLocalPart()));
/* 136 */         throw new XWSSecurityException(LogStringsMessages.WSS_1609_ERROR_SERIALIZING_ELEMENT(this.header.getLocalPart()), ex);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/* 143 */       Marshaller mh = this.jc.createMarshaller();
/* 144 */       mh.marshal(this.jb, writer);
/* 145 */     } catch (JAXBException ex) {
/* 146 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1610_ERROR_MARSHALLING_JBOBJECT(this.jb.getName()));
/* 147 */       throw new XWSSecurityException(LogStringsMessages.WSS_1610_ERROR_MARSHALLING_JBOBJECT(this.jb.getName()), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) throws XWSSecurityException {
/*     */     try {
/* 155 */       if (this.header != null && this.header instanceof JAXBHeader) {
/* 156 */         JAXBHeader hdr = (JAXBHeader)this.header;
/* 157 */         Object obj = this.header.readAsJAXB(this.jc.createUnmarshaller());
/* 158 */         Marshaller mh = this.jc.createMarshaller();
/*     */         
/* 160 */         mh.setProperty("jaxb.fragment", Boolean.TRUE);
/* 161 */         mh.marshal(obj, os);
/*     */       } else {
/* 163 */         Marshaller mh = this.jc.createMarshaller();
/* 164 */         mh.setProperty("com.sun.xml.bind.c14n", Boolean.valueOf(true));
/* 165 */         mh.marshal(this.jb, os);
/*     */       }
/*     */     
/* 168 */     } catch (JAXBException ex) {
/* 169 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1610_ERROR_MARSHALLING_JBOBJECT(this.jb.getName()));
/* 170 */       throw new XWSSecurityException(LogStringsMessages.WSS_1610_ERROR_MARSHALLING_JBOBJECT(this.jb.getName()), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public NamespaceContextEx getNamespaceContext() {
/* 175 */     return this.nsContext;
/*     */   }
/*     */   
/*     */   public SecurityElement getSecurityElement() {
/* 179 */     return this.securityElement;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\crypto\JAXBDataImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */