/*     */ package com.sun.xml.rpc.wsdl.document.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.QNameAction;
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
/*     */ public class SOAPHeaderFault
/*     */   extends Extension
/*     */ {
/*     */   private String _encodingStyle;
/*     */   private String _namespace;
/*     */   private String _part;
/*     */   private QName _message;
/*     */   private SOAPUse _use;
/*     */   
/*     */   public QName getElementName() {
/*  45 */     return SOAPConstants.QNAME_HEADERFAULT;
/*     */   }
/*     */   
/*     */   public String getNamespace() {
/*  49 */     return this._namespace;
/*     */   }
/*     */   
/*     */   public void setNamespace(String s) {
/*  53 */     this._namespace = s;
/*     */   }
/*     */   
/*     */   public SOAPUse getUse() {
/*  57 */     return this._use;
/*     */   }
/*     */   
/*     */   public void setUse(SOAPUse u) {
/*  61 */     this._use = u;
/*     */   }
/*     */   
/*     */   public boolean isEncoded() {
/*  65 */     return (this._use == SOAPUse.ENCODED);
/*     */   }
/*     */   
/*     */   public boolean isLiteral() {
/*  69 */     return (this._use == SOAPUse.LITERAL);
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/*  73 */     return this._encodingStyle;
/*     */   }
/*     */   
/*     */   public void setEncodingStyle(String s) {
/*  77 */     this._encodingStyle = s;
/*     */   }
/*     */   
/*     */   public String getPart() {
/*  81 */     return this._part;
/*     */   }
/*     */   
/*     */   public void setMessage(QName message) {
/*  85 */     this._message = message;
/*     */   }
/*     */   
/*     */   public QName getMessage() {
/*  89 */     return this._message;
/*     */   }
/*     */   
/*     */   public void setPart(String s) {
/*  93 */     this._part = s;
/*     */   }
/*     */   
/*     */   public void withAllQNamesDo(QNameAction action) {
/*  97 */     super.withAllQNamesDo(action);
/*     */     
/*  99 */     if (this._message != null) {
/* 100 */       action.perform(this._message);
/*     */     }
/*     */   }
/*     */   
/*     */   public void validateThis() {
/* 105 */     if (this._message == null) {
/* 106 */       failValidation("validation.missingRequiredAttribute", "message");
/*     */     }
/* 108 */     if (this._part == null) {
/* 109 */       failValidation("validation.missingRequiredAttribute", "part");
/*     */     }
/* 111 */     if (this._use == null)
/* 112 */       failValidation("validation.missingRequiredAttribute", "use"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\soap\SOAPHeaderFault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */