/*    */ package com.sun.xml.ws.tx.at.tube;
/*    */ 
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.tx.at.Transactional;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransactionalAttribute
/*    */ {
/*    */   private boolean enabled;
/*    */   private boolean required;
/*    */   private Transactional.Version version;
/*    */   private SOAPVersion soapVersion;
/*    */   
/*    */   public TransactionalAttribute(boolean enabled, boolean required, Transactional.Version version) {
/* 54 */     this.enabled = enabled;
/* 55 */     this.required = required;
/* 56 */     this.version = version;
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 60 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 64 */     this.enabled = enabled;
/*    */   }
/*    */   
/*    */   public boolean isRequired() {
/* 68 */     return this.required;
/*    */   }
/*    */   
/*    */   public void setRequired(boolean required) {
/* 72 */     this.required = required;
/*    */   }
/*    */   
/*    */   public Transactional.Version getVersion() {
/* 76 */     return this.version;
/*    */   }
/*    */   
/*    */   public void setVersion(Transactional.Version version) {
/* 80 */     this.version = version;
/*    */   }
/*    */   
/*    */   public SOAPVersion getSoapVersion() {
/* 84 */     if (this.soapVersion == null)
/* 85 */       this.soapVersion = SOAPVersion.SOAP_11; 
/* 86 */     return this.soapVersion;
/*    */   }
/*    */   
/*    */   public void setSoapVersion(SOAPVersion soapVersion) {
/* 90 */     this.soapVersion = soapVersion;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\tube\TransactionalAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */