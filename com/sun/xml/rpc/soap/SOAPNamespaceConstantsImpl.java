/*     */ package com.sun.xml.rpc.soap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SOAPNamespaceConstantsImpl
/*     */   implements SOAPNamespaceConstants
/*     */ {
/*     */   private String ENVELOPE;
/*     */   private String ENCODING;
/*     */   private String SOAP_RPC;
/*     */   private String XSD;
/*     */   private String XSI;
/*     */   private String TRANSPORT_HTTP;
/*     */   private String ACTOR_NEXT;
/*     */   private String TAG_ENVELOPE;
/*     */   private String TAG_HEADER;
/*     */   private String TAG_BODY;
/*     */   private String ATTR_ACTOR;
/*     */   private String TAG_RESULT;
/*     */   private String ATTR_MISUNDERSTOOD;
/*     */   private String ATTR_MUST_UNDERSTAND;
/*     */   private String ATTR_ENCODING_STYLE;
/*     */   private String SOAP_UPGRADE;
/*     */   private SOAPVersion ver;
/*     */   
/*     */   SOAPNamespaceConstantsImpl(SOAPVersion ver) {
/*  36 */     this.ver = ver;
/*  37 */     if (ver == SOAPVersion.SOAP_11) {
/*  38 */       initSOAP11();
/*  39 */     } else if (ver == SOAPVersion.SOAP_12) {
/*  40 */       initSOAP12();
/*     */     } 
/*     */   }
/*     */   public SOAPVersion getSOAPVersion() {
/*  44 */     return this.ver;
/*     */   }
/*     */   
/*     */   public String getEnvelope() {
/*  48 */     return this.ENVELOPE;
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/*  52 */     return this.ENCODING;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSOAPRpc() {
/*  57 */     return this.SOAP_RPC;
/*     */   }
/*     */   
/*     */   public String getXSD() {
/*  61 */     return this.XSD;
/*     */   }
/*     */   
/*     */   public String getXSI() {
/*  65 */     return this.XSI;
/*     */   }
/*     */   
/*     */   public String getTransportHTTP() {
/*  69 */     return this.TRANSPORT_HTTP;
/*     */   }
/*     */   
/*     */   public String getActorNext() {
/*  73 */     return this.ACTOR_NEXT;
/*     */   }
/*     */   
/*     */   public String getTagEnvelope() {
/*  77 */     return this.TAG_ENVELOPE;
/*     */   }
/*     */   
/*     */   public String getTagHeader() {
/*  81 */     return this.TAG_HEADER;
/*     */   }
/*     */   
/*     */   public String getTagBody() {
/*  85 */     return this.TAG_BODY;
/*     */   }
/*     */   
/*     */   public String getAttrActor() {
/*  89 */     return this.ATTR_ACTOR;
/*     */   }
/*     */   
/*     */   public String getTagResult() {
/*  93 */     return this.TAG_RESULT;
/*     */   }
/*     */   
/*     */   public String getAttrMustUnderstand() {
/*  97 */     return this.ATTR_MUST_UNDERSTAND;
/*     */   }
/*     */   
/*     */   public String getAttrMisunderstood() {
/* 101 */     return this.ATTR_MISUNDERSTOOD;
/*     */   }
/*     */   
/*     */   public String getSOAPUpgrade() {
/* 105 */     return this.SOAP_UPGRADE;
/*     */   }
/*     */   
/*     */   public String getAttrEncodingStyle() {
/* 109 */     return this.ATTR_ENCODING_STYLE;
/*     */   }
/*     */   
/*     */   private void initSOAP11() {
/* 113 */     this.ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope/";
/* 114 */     this.ENCODING = "http://schemas.xmlsoap.org/soap/encoding/";
/* 115 */     this.XSD = "http://www.w3.org/2001/XMLSchema";
/* 116 */     this.XSI = "http://www.w3.org/2001/XMLSchema-instance";
/* 117 */     this.TRANSPORT_HTTP = "http://schemas.xmlsoap.org/soap/http/";
/* 118 */     this.ACTOR_NEXT = "http://schemas.xmlsoap.org/soap/actor/next";
/* 119 */     this.TAG_ENVELOPE = "Envelope";
/* 120 */     this.TAG_HEADER = "Header";
/* 121 */     this.TAG_BODY = "Body";
/* 122 */     this.ATTR_ACTOR = "actor";
/* 123 */     this.ATTR_MUST_UNDERSTAND = "mustUnderstand";
/* 124 */     this.ATTR_ENCODING_STYLE = "encodingStyle";
/*     */ 
/*     */     
/* 127 */     this.SOAP_RPC = null;
/* 128 */     this.TAG_RESULT = null;
/* 129 */     this.ATTR_MISUNDERSTOOD = null;
/* 130 */     this.SOAP_UPGRADE = null;
/*     */   }
/*     */   
/*     */   private void initSOAP12() {
/* 134 */     this.ENVELOPE = "http://www.w3.org/2002/06/soap-envelope";
/* 135 */     this.ENCODING = "http://www.w3.org/2002/06/soap-encoding";
/* 136 */     this.XSD = "http://www.w3.org/2001/XMLSchema";
/* 137 */     this.XSI = "http://www.w3.org/2001/XMLSchema-instance";
/* 138 */     this.TRANSPORT_HTTP = "http://www.w3.org/2002/06/soap/bindings/HTTP/";
/* 139 */     this.ACTOR_NEXT = "http://www.w3.org/2002/06/soap-envelope/role/next";
/* 140 */     this.TAG_ENVELOPE = "Envelope";
/* 141 */     this.TAG_HEADER = "Header";
/* 142 */     this.TAG_BODY = "Body";
/* 143 */     this.ATTR_ACTOR = "role";
/* 144 */     this.ATTR_MUST_UNDERSTAND = "mustUnderstand";
/* 145 */     this.ATTR_ENCODING_STYLE = "encodingStyle";
/*     */ 
/*     */     
/* 148 */     this.SOAP_RPC = "http://www.w3.org/2002/06/soap-rpc";
/* 149 */     this.TAG_RESULT = "result";
/* 150 */     this.ATTR_MISUNDERSTOOD = "missUnderstood";
/* 151 */     this.SOAP_UPGRADE = "http://www.w3.org/2002/06/soap-upgrade";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\SOAPNamespaceConstantsImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */