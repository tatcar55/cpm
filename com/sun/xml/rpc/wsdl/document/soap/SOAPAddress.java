/*    */ package com.sun.xml.rpc.wsdl.document.soap;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class SOAPAddress
/*    */   extends Extension
/*    */ {
/*    */   private String _location;
/*    */   
/*    */   public QName getElementName() {
/* 44 */     return SOAPConstants.QNAME_ADDRESS;
/*    */   }
/*    */   
/*    */   public String getLocation() {
/* 48 */     return this._location;
/*    */   }
/*    */   
/*    */   public void setLocation(String s) {
/* 52 */     this._location = s;
/*    */   }
/*    */   
/*    */   public void validateThis() {
/* 56 */     if (this._location == null)
/* 57 */       failValidation("validation.missingRequiredAttribute", "location"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\soap\SOAPAddress.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */