/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Target
/*     */ {
/*     */   protected static final String TARGET_VALUE_SOAP_BODY = "SOAP-BODY";
/*     */   public static final String TARGET_TYPE_VALUE_QNAME = "qname";
/*     */   public static final String TARGET_TYPE_VALUE_XPATH = "xpath";
/*     */   public static final String TARGET_TYPE_VALUE_URI = "uri";
/*     */   public static final String ALL_MESSAGE_HEADERS = "ALL_MESSAGE_HEADERS";
/*     */   public static final String BODY = "{http://schemas.xmlsoap.org/soap/envelope/}Body";
/*     */   public static final String BODY1_2 = "{http://www.w3.org/2003/05/soap-envelope}Body";
/*  77 */   public static final QName BODY_QNAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Body");
/*  78 */   public static final QName SIGNATURE_CONFIRMATION = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "SignatureConfirmation");
/*     */   
/*  80 */   private String type = "qname";
/*     */   
/*  82 */   private String value = "{http://schemas.xmlsoap.org/soap/envelope/}Body";
/*     */   private boolean contentOnly = true;
/*     */   private boolean enforce = true;
/*  85 */   private String xpathExpr = null;
/*  86 */   private QName qname = null;
/*     */   private boolean attachment = false;
/*     */   boolean bsp = false;
/*     */   boolean headersOnly = false;
/*     */   private String xpathVersion;
/*  91 */   private QName policyQName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Target() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Target(String type, String value) {
/* 107 */     this.type = type;
/* 108 */     this.value = value;
/* 109 */     if ("qname".equals(type) && "SOAP-BODY".equals(value)) {
/* 110 */       this.value = "{http://schemas.xmlsoap.org/soap/envelope/}Body";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Target(String type, String value, boolean contentOnly) {
/* 122 */     this.type = type;
/* 123 */     this.value = value;
/* 124 */     this.contentOnly = contentOnly;
/* 125 */     if ("qname".equals(type) && "SOAP-BODY".equals(value)) {
/* 126 */       this.value = "{http://schemas.xmlsoap.org/soap/envelope/}Body";
/*     */     }
/*     */   }
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
/*     */   public Target(String type, String value, boolean contentOnly, boolean enforce) {
/* 141 */     this.type = type;
/* 142 */     this.value = value;
/* 143 */     this.contentOnly = contentOnly;
/* 144 */     this.enforce = enforce;
/* 145 */     if ("qname".equals(type) && "SOAP-BODY".equals(value)) {
/* 146 */       this.value = "{http://schemas.xmlsoap.org/soap/envelope/}Body";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnforce(boolean enforce) {
/* 156 */     this.enforce = enforce;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getEnforce() {
/* 164 */     return this.enforce;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isSOAPHeadersOnly(boolean headersOnly) {
/* 173 */     this.headersOnly = headersOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSOAPHeadersOnly() {
/* 181 */     return this.headersOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isBSP(boolean flag) {
/* 188 */     this.bsp = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBSP() {
/* 194 */     return this.bsp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 202 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(String type) {
/* 210 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 217 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(String value) {
/* 225 */     this.value = value;
/* 226 */     this.xpathExpr = null;
/*     */ 
/*     */     
/* 229 */     if ("{http://www.w3.org/2003/05/soap-envelope}Body".equals(value) || "{http://schemas.xmlsoap.org/soap/envelope/}Body".equals(value)) {
/* 230 */       this.value = "{http://schemas.xmlsoap.org/soap/envelope/}Body";
/*     */     }
/*     */     
/* 233 */     if ("qname".equals(this.type) && "SOAP-BODY".equals(value)) {
/* 234 */       this.value = "{http://schemas.xmlsoap.org/soap/envelope/}Body";
/*     */     }
/*     */     
/* 237 */     if (value != null && (value.startsWith("cid:") || value.startsWith("attachmentRef:"))) {
/* 238 */       this.attachment = true;
/* 239 */       if (value.equals("cid:*")) {
/* 240 */         this.value = "cid:*";
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentOnly(boolean contentOnly) {
/* 250 */     this.contentOnly = contentOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getContentOnly() {
/* 257 */     return this.contentOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String convertToXPATH() {
/* 264 */     if (this.xpathExpr == null) {
/* 265 */       this.xpathExpr = convertToXpath(this.value);
/*     */     }
/* 267 */     return this.xpathExpr;
/*     */   }
/*     */   
/*     */   private String convertToXpath(String qname) {
/* 271 */     QName name = QName.valueOf(qname);
/* 272 */     if ("".equals(name.getNamespaceURI())) {
/* 273 */       return "//" + name.getLocalPart();
/*     */     }
/* 275 */     return "//*[local-name()='" + name.getLocalPart() + "' and namespace-uri()='" + name.getNamespaceURI() + "']";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQName(QName qname) {
/* 287 */     this.type = "qname";
/* 288 */     this.value = qname.toString();
/* 289 */     this.qname = qname;
/* 290 */     this.value = qname.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getQName() {
/* 297 */     if (this.type != "qname") {
/* 298 */       return null;
/*     */     }
/* 300 */     if (this.qname == null && this.value != null) {
/* 301 */       this.qname = QName.valueOf(this.value);
/*     */     }
/* 303 */     return this.qname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAttachment() {
/* 310 */     return this.attachment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXPathVersion() {
/* 317 */     return this.xpathVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXPathVersion(String version) {
/* 326 */     this.xpathVersion = version;
/*     */   }
/*     */   
/*     */   public void setPolicyQName(QName policyQName) {
/* 330 */     this.policyQName = policyQName;
/*     */   }
/*     */   
/*     */   public QName getPolicyQName() {
/* 334 */     return this.policyQName;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\Target.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */