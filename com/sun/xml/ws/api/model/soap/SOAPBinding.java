/*     */ package com.sun.xml.ws.api.model.soap;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SOAPBinding
/*     */ {
/*  57 */   protected javax.jws.soap.SOAPBinding.Use use = javax.jws.soap.SOAPBinding.Use.LITERAL;
/*  58 */   protected javax.jws.soap.SOAPBinding.Style style = javax.jws.soap.SOAPBinding.Style.DOCUMENT;
/*  59 */   protected SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*  60 */   protected String soapAction = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public javax.jws.soap.SOAPBinding.Use getUse() {
/*  66 */     return this.use;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public javax.jws.soap.SOAPBinding.Style getStyle() {
/*  73 */     return this.style;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/*  80 */     return this.soapVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDocLit() {
/*  87 */     return (this.style == javax.jws.soap.SOAPBinding.Style.DOCUMENT && this.use == javax.jws.soap.SOAPBinding.Use.LITERAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRpcLit() {
/*  94 */     return (this.style == javax.jws.soap.SOAPBinding.Style.RPC && this.use == javax.jws.soap.SOAPBinding.Use.LITERAL);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSOAPAction() {
/* 114 */     return this.soapAction;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\soap\SOAPBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */