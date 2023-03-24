/*     */ package com.sun.xml.ws.tx.coord.common;
/*     */ 
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import com.sun.xml.ws.tx.coord.common.types.CoordinationContextIF;
/*     */ import com.sun.xml.ws.tx.coord.v10.CoordinationContextBuilderImpl;
/*     */ import com.sun.xml.ws.tx.coord.v11.CoordinationContextBuilderImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CoordinationContextBuilder
/*     */ {
/*     */   protected String coordinationType;
/*     */   protected String identifier;
/*     */   protected long expires;
/*     */   protected String address;
/*     */   protected String txId;
/*     */   protected boolean mustUnderstand;
/*     */   protected SOAPVersion soapVersion;
/*     */   protected Header coordinationHeader;
/*     */   Transactional.Version version;
/*     */   
/*     */   public static CoordinationContextBuilder newInstance(Transactional.Version version) {
/*  66 */     if (Transactional.Version.WSAT10 == version)
/*  67 */       return (CoordinationContextBuilder)new CoordinationContextBuilderImpl(); 
/*  68 */     if (Transactional.Version.WSAT11 == version || Transactional.Version.WSAT12 == version) {
/*  69 */       return (CoordinationContextBuilder)new CoordinationContextBuilderImpl();
/*     */     }
/*  71 */     throw new IllegalArgumentException(version + "is not a supported ws-at version");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static CoordinationContextBuilder headers(HeaderList headers, Transactional.Version version) {
/*  77 */     CoordinationContextBuilder builder = null;
/*  78 */     for (int i = 0; i < headers.size(); i++) {
/*  79 */       Header header = headers.get(i);
/*  80 */       if (header.getLocalPart().equals("CoordinationContext")) {
/*  81 */         CoordinationContextBuilderImpl coordinationContextBuilderImpl; if ("http://schemas.xmlsoap.org/ws/2004/10/wscoor".equals(header.getNamespaceURI())) {
/*  82 */           if (version == Transactional.Version.WSAT10 || version == Transactional.Version.DEFAULT) {
/*  83 */             CoordinationContextBuilderImpl coordinationContextBuilderImpl1 = new CoordinationContextBuilderImpl();
/*  84 */             ((CoordinationContextBuilder)coordinationContextBuilderImpl1).version = Transactional.Version.WSAT10;
/*     */           } 
/*  86 */         } else if ("http://docs.oasis-open.org/ws-tx/wscoor/2006/06".equals(header.getNamespaceURI()) && 
/*  87 */           version != Transactional.Version.WSAT10) {
/*  88 */           coordinationContextBuilderImpl = new CoordinationContextBuilderImpl();
/*  89 */           ((CoordinationContextBuilder)coordinationContextBuilderImpl).version = Transactional.Version.WSAT11;
/*     */         } 
/*     */         
/*  92 */         if (coordinationContextBuilderImpl != null) {
/*  93 */           headers.understood(i);
/*  94 */           return coordinationContextBuilderImpl.header(header);
/*     */         } 
/*     */       } 
/*     */     } 
/*  98 */     return null;
/*     */   }
/*     */   
/*     */   public Transactional.Version getVersion() {
/* 102 */     return this.version;
/*     */   }
/*     */   
/*     */   public CoordinationContextBuilder address(String address) {
/* 106 */     this.address = address;
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   public CoordinationContextBuilder txId(String txId) {
/* 111 */     this.txId = txId;
/* 112 */     return this;
/*     */   }
/*     */   
/*     */   public CoordinationContextBuilder identifier(String identifier) {
/* 116 */     this.identifier = identifier;
/* 117 */     return this;
/*     */   }
/*     */   
/*     */   public CoordinationContextBuilder expires(long expires) {
/* 121 */     this.expires = expires;
/* 122 */     return this;
/*     */   }
/*     */   
/*     */   public CoordinationContextBuilder mustUnderstand(boolean mustUnderstand) {
/* 126 */     this.mustUnderstand = mustUnderstand;
/* 127 */     return this;
/*     */   }
/*     */   
/*     */   public CoordinationContextBuilder soapVersion(SOAPVersion soapVersion) {
/* 131 */     this.soapVersion = soapVersion;
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   public CoordinationContextBuilder coordinationType(String coordinationType) {
/* 136 */     this.coordinationType = coordinationType;
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   CoordinationContextBuilder header(Header coordinationHeader) {
/* 141 */     this.coordinationHeader = coordinationHeader;
/* 142 */     return this;
/*     */   }
/*     */   
/*     */   public CoordinationContextIF buildFromHeader() {
/* 146 */     return _fromHeader(this.coordinationHeader);
/*     */   }
/*     */   
/*     */   protected abstract CoordinationContextIF _fromHeader(Header paramHeader);
/*     */   
/*     */   public abstract CoordinationContextIF build();
/*     */   
/*     */   public abstract JAXBRIContext getJAXBRIContext();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\CoordinationContextBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */