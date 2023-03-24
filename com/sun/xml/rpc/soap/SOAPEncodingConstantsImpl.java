/*     */ package com.sun.xml.rpc.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPConstants;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SOAPEncodingConstantsImpl
/*     */   extends SOAPWSDLConstantsImpl
/*     */   implements SOAPEncodingConstants
/*     */ {
/*     */   private SOAPVersion ver;
/*     */   private String URI_ENVELOPE;
/*     */   private String URI_ENCODING;
/*     */   private String URI_HTTP;
/*     */   private QName QNAME_ENCODING_ARRAY;
/*     */   private QName QNAME_ENCODING_ARRAYTYPE;
/*     */   private QName QNAME_ENCODING_ITEMTYPE;
/*     */   private QName QNAME_ENCODING_ARRAYSIZE;
/*     */   private QName QNAME_ENCODING_BASE64;
/*     */   private QName QNAME_ENVELOPE_ENCODINGSTYLE;
/*     */   private QName QNAME_SOAP_FAULT;
/*     */   private QName FAULT_CODE_CLIENT;
/*     */   private QName FAULT_CODE_MUST_UNDERSTAND;
/*     */   private QName FAULT_CODE_SERVER;
/*     */   private QName FAULT_CODE_VERSION_MISMATCH;
/*     */   private QName FAULT_CODE_DATA_ENCODING_UNKNOWN;
/*     */   private QName FAULT_CODE_PROCEDURE_NOT_PRESENT;
/*     */   private QName FAULT_CODE_BAD_ARGUMENTS;
/*     */   private QName QNAME_SOAP_RPC;
/*     */   private QName QNAME_SOAP_RESULT;
/*     */   private QName FAULT_CODE_MISUNDERSTOOD;
/*     */   
/*     */   SOAPEncodingConstantsImpl(SOAPVersion ver) {
/*  43 */     super(ver);
/*  44 */     this.ver = ver;
/*  45 */     if (ver == SOAPVersion.SOAP_11) {
/*  46 */       initSOAP11();
/*  47 */     } else if (ver == SOAPVersion.SOAP_12) {
/*  48 */       initSOAP12();
/*     */     } 
/*     */   }
/*     */   public String getURIEnvelope() {
/*  52 */     return this.URI_ENVELOPE;
/*     */   }
/*     */   
/*     */   public String getURIEncoding() {
/*  56 */     return this.URI_ENCODING;
/*     */   }
/*     */   
/*     */   public String getURIHttp() {
/*  60 */     return this.URI_HTTP;
/*     */   }
/*     */   
/*     */   public QName getQNameEncodingArray() {
/*  64 */     return this.QNAME_ENCODING_ARRAY;
/*     */   }
/*     */   
/*     */   public QName getQNameEncodingArraytype() {
/*  68 */     return this.QNAME_ENCODING_ARRAYTYPE;
/*     */   }
/*     */   
/*     */   public QName getQNameEncodingItemtype() {
/*  72 */     return this.QNAME_ENCODING_ITEMTYPE;
/*     */   }
/*     */   
/*     */   public QName getQNameEncodingArraysize() {
/*  76 */     return this.QNAME_ENCODING_ARRAYSIZE;
/*     */   }
/*     */   
/*     */   public QName getQNameEncodingBase64() {
/*  80 */     return this.QNAME_ENCODING_BASE64;
/*     */   }
/*     */   
/*     */   public QName getQNameEnvelopeEncodingStyle() {
/*  84 */     return this.QNAME_ENVELOPE_ENCODINGSTYLE;
/*     */   }
/*     */   
/*     */   public QName getQNameSOAPFault() {
/*  88 */     return this.QNAME_SOAP_FAULT;
/*     */   }
/*     */   
/*     */   public QName getFaultCodeClient() {
/*  92 */     return this.FAULT_CODE_CLIENT;
/*     */   }
/*     */   
/*     */   public QName getFaultCodeMustUnderstand() {
/*  96 */     return this.FAULT_CODE_MUST_UNDERSTAND;
/*     */   }
/*     */   
/*     */   public QName getFaultCodeServer() {
/* 100 */     return this.FAULT_CODE_SERVER;
/*     */   }
/*     */   
/*     */   public QName getFaultCodeVersionMismatch() {
/* 104 */     return this.FAULT_CODE_VERSION_MISMATCH;
/*     */   }
/*     */   
/*     */   public QName getFaultCodeDataEncodingUnknown() {
/* 108 */     return this.FAULT_CODE_DATA_ENCODING_UNKNOWN;
/*     */   }
/*     */   
/*     */   public QName getFaultCodeProcedureNotPresent() {
/* 112 */     return this.FAULT_CODE_PROCEDURE_NOT_PRESENT;
/*     */   }
/*     */   
/*     */   public QName getFaultCodeBadArguments() {
/* 116 */     return this.FAULT_CODE_BAD_ARGUMENTS;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getQNameSOAPRpc() {
/* 121 */     return this.QNAME_SOAP_RPC;
/*     */   }
/*     */   
/*     */   public QName getQNameSOAPResult() {
/* 125 */     return this.QNAME_SOAP_RESULT;
/*     */   }
/*     */   
/*     */   public QName getFaultCodeMisunderstood() {
/* 129 */     return this.FAULT_CODE_MISUNDERSTOOD;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 134 */     return this.ver;
/*     */   }
/*     */   
/*     */   private void initSOAP11() {
/* 138 */     this.URI_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope/";
/* 139 */     this.URI_ENCODING = "http://schemas.xmlsoap.org/soap/encoding/";
/* 140 */     this.URI_HTTP = "http://schemas.xmlsoap.org/soap/http/";
/*     */     
/* 142 */     this.QNAME_ENCODING_ARRAY = SOAPConstants.QNAME_ENCODING_ARRAY;
/* 143 */     this.QNAME_ENCODING_ARRAYTYPE = SOAPConstants.QNAME_ENCODING_ARRAYTYPE;
/* 144 */     this.QNAME_ENCODING_BASE64 = SOAPConstants.QNAME_ENCODING_BASE64;
/* 145 */     this.QNAME_ENVELOPE_ENCODINGSTYLE = SOAPConstants.QNAME_ENVELOPE_ENCODINGSTYLE;
/*     */ 
/*     */     
/* 148 */     this.QNAME_SOAP_FAULT = SOAPConstants.QNAME_SOAP_FAULT;
/* 149 */     this.FAULT_CODE_CLIENT = SOAPConstants.FAULT_CODE_CLIENT;
/* 150 */     this.FAULT_CODE_MUST_UNDERSTAND = SOAPConstants.FAULT_CODE_MUST_UNDERSTAND;
/* 151 */     this.FAULT_CODE_SERVER = SOAPConstants.FAULT_CODE_SERVER;
/* 152 */     this.FAULT_CODE_VERSION_MISMATCH = SOAPConstants.FAULT_CODE_VERSION_MISMATCH;
/* 153 */     this.FAULT_CODE_DATA_ENCODING_UNKNOWN = SOAPConstants.FAULT_CODE_DATA_ENCODING_UNKNOWN;
/*     */     
/* 155 */     this.FAULT_CODE_PROCEDURE_NOT_PRESENT = SOAPConstants.FAULT_CODE_PROCEDURE_NOT_PRESENT;
/*     */     
/* 157 */     this.FAULT_CODE_BAD_ARGUMENTS = SOAPConstants.FAULT_CODE_BAD_ARGUMENTS;
/*     */     
/* 159 */     this.QNAME_SOAP_RPC = null;
/* 160 */     this.QNAME_SOAP_RESULT = null;
/* 161 */     this.FAULT_CODE_MISUNDERSTOOD = null;
/*     */   }
/*     */   
/*     */   private void initSOAP12() {
/* 165 */     this.URI_ENVELOPE = "http://www.w3.org/2002/06/soap-envelope";
/* 166 */     this.URI_ENCODING = "http://www.w3.org/2002/06/soap-encoding";
/* 167 */     this.URI_HTTP = "http://www.w3.org/2002/06/soap/bindings/HTTP/";
/*     */     
/* 169 */     this.QNAME_ENCODING_ARRAY = SOAP12Constants.QNAME_ENCODING_ARRAY;
/* 170 */     this.QNAME_ENCODING_ARRAYTYPE = SOAP12Constants.QNAME_ENCODING_ARRAYTYPE;
/* 171 */     this.QNAME_ENCODING_ITEMTYPE = SOAP12Constants.QNAME_ENCODING_ITEMTYPE;
/* 172 */     this.QNAME_ENCODING_ARRAYSIZE = SOAP12Constants.QNAME_ENCODING_ARRAYSIZE;
/* 173 */     this.QNAME_ENCODING_BASE64 = SOAP12Constants.QNAME_ENCODING_BASE64;
/* 174 */     this.QNAME_ENVELOPE_ENCODINGSTYLE = SOAP12Constants.QNAME_ENVELOPE_ENCODINGSTYLE;
/*     */ 
/*     */     
/* 177 */     this.QNAME_SOAP_FAULT = SOAP12Constants.QNAME_SOAP_FAULT;
/* 178 */     this.FAULT_CODE_CLIENT = SOAP12Constants.FAULT_CODE_CLIENT;
/* 179 */     this.FAULT_CODE_MUST_UNDERSTAND = SOAP12Constants.FAULT_CODE_MUST_UNDERSTAND;
/* 180 */     this.FAULT_CODE_SERVER = SOAP12Constants.FAULT_CODE_SERVER;
/* 181 */     this.FAULT_CODE_VERSION_MISMATCH = SOAP12Constants.FAULT_CODE_VERSION_MISMATCH;
/*     */     
/* 183 */     this.FAULT_CODE_DATA_ENCODING_UNKNOWN = SOAP12Constants.FAULT_CODE_DATA_ENCODING_UNKNOWN;
/*     */     
/* 185 */     this.FAULT_CODE_PROCEDURE_NOT_PRESENT = SOAP12Constants.FAULT_CODE_PROCEDURE_NOT_PRESENT;
/*     */     
/* 187 */     this.FAULT_CODE_BAD_ARGUMENTS = SOAP12Constants.FAULT_CODE_BAD_ARGUMENTS;
/*     */ 
/*     */     
/* 190 */     this.QNAME_SOAP_RPC = SOAP12Constants.QNAME_SOAP_RPC;
/* 191 */     this.QNAME_SOAP_RESULT = SOAP12Constants.QNAME_SOAP_RESULT;
/* 192 */     this.FAULT_CODE_MISUNDERSTOOD = SOAP12Constants.FAULT_CODE_MISUNDERSTOOD;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\SOAPEncodingConstantsImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */