/*    */ package com.sun.xml.ws.security.opt.crypto.dsig.keyinfo;
/*    */ 
/*    */ import com.sun.xml.security.core.dsig.X509DataType;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.crypto.dsig.keyinfo.X509Data;
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
/*    */ @XmlRootElement(name = "X509Data", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*    */ public class X509Data
/*    */   extends X509DataType
/*    */   implements X509Data
/*    */ {
/*    */   public List getContent() {
/* 64 */     return this.x509IssuerSerialOrX509SKIOrX509SubjectName;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFeatureSupported(String string) {
/* 69 */     return false;
/*    */   }
/*    */   
/*    */   public void setX509IssuerSerialOrX509SKIOrX509SubjectName(List<Object> x509IssuerSerialOrX509SKIOrX509SubjectName) {
/* 73 */     this.x509IssuerSerialOrX509SKIOrX509SubjectName = x509IssuerSerialOrX509SKIOrX509SubjectName;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\keyinfo\X509Data.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */