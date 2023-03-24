/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ContentTypeImpl
/*     */   implements ContentType
/*     */ {
/*     */   @NotNull
/*     */   private final String contentType;
/*     */   @NotNull
/*     */   private final String soapAction;
/*     */   private String accept;
/*     */   @Nullable
/*     */   private final String charset;
/*     */   private String boundary;
/*     */   private String boundaryParameter;
/*     */   private String rootId;
/*     */   private ContentType internalContentType;
/*     */   
/*     */   public ContentTypeImpl(String contentType) {
/*  60 */     this(contentType, null, null);
/*     */   }
/*     */   
/*     */   public ContentTypeImpl(String contentType, @Nullable String soapAction) {
/*  64 */     this(contentType, soapAction, null);
/*     */   }
/*     */   
/*     */   public ContentTypeImpl(String contentType, @Nullable String soapAction, @Nullable String accept) {
/*  68 */     this(contentType, soapAction, accept, null);
/*     */   }
/*     */   
/*     */   public ContentTypeImpl(String contentType, @Nullable String soapAction, @Nullable String accept, String charsetParam) {
/*  72 */     this.contentType = contentType;
/*  73 */     this.accept = accept;
/*  74 */     this.soapAction = getQuotedSOAPAction(soapAction);
/*  75 */     if (charsetParam == null) {
/*  76 */       String tmpCharset = null;
/*     */       try {
/*  78 */         this.internalContentType = new ContentType(contentType);
/*  79 */         tmpCharset = this.internalContentType.getParameter("charset");
/*  80 */       } catch (Exception e) {}
/*     */ 
/*     */       
/*  83 */       this.charset = tmpCharset;
/*     */     } else {
/*  85 */       this.charset = charsetParam;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getCharSet() {
/*  95 */     return this.charset;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getQuotedSOAPAction(String soapAction) {
/* 100 */     if (soapAction == null || soapAction.length() == 0)
/* 101 */       return "\"\""; 
/* 102 */     if (soapAction.charAt(0) != '"' && soapAction.charAt(soapAction.length() - 1) != '"')
/*     */     {
/* 104 */       return "\"" + soapAction + "\"";
/*     */     }
/* 106 */     return soapAction;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 112 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSOAPActionHeader() {
/* 117 */     return this.soapAction;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAcceptHeader() {
/* 122 */     return this.accept;
/*     */   }
/*     */   
/*     */   public void setAcceptHeader(String accept) {
/* 126 */     this.accept = accept;
/*     */   }
/*     */   
/*     */   public String getBoundary() {
/* 130 */     if (this.boundary == null) {
/* 131 */       if (this.internalContentType == null) this.internalContentType = new ContentType(this.contentType); 
/* 132 */       this.boundary = this.internalContentType.getParameter("boundary");
/*     */     } 
/* 134 */     return this.boundary;
/*     */   }
/*     */   
/*     */   public void setBoundary(String boundary) {
/* 138 */     this.boundary = boundary;
/*     */   }
/*     */   
/*     */   public String getBoundaryParameter() {
/* 142 */     return this.boundaryParameter;
/*     */   }
/*     */   
/*     */   public void setBoundaryParameter(String boundaryParameter) {
/* 146 */     this.boundaryParameter = boundaryParameter;
/*     */   }
/*     */   
/*     */   public String getRootId() {
/* 150 */     if (this.rootId == null) {
/* 151 */       if (this.internalContentType == null) this.internalContentType = new ContentType(this.contentType); 
/* 152 */       this.rootId = this.internalContentType.getParameter("start");
/*     */     } 
/* 154 */     return this.rootId;
/*     */   }
/*     */   
/*     */   public void setRootId(String rootId) {
/* 158 */     this.rootId = rootId;
/*     */   }
/*     */   
/*     */   public static class Builder { public String contentType;
/*     */     public String soapAction;
/*     */     public String accept;
/*     */     public String charset;
/*     */     
/*     */     public ContentTypeImpl build() {
/* 167 */       return new ContentTypeImpl(this.contentType, this.soapAction, this.accept, this.charset);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\ContentTypeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */