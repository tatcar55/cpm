/*    */ package com.sun.xml.rpc.wsdl.document.http;
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
/*    */ public class HTTPBinding
/*    */   extends Extension
/*    */ {
/*    */   private String _verb;
/*    */   
/*    */   public QName getElementName() {
/* 44 */     return HTTPConstants.QNAME_BINDING;
/*    */   }
/*    */   
/*    */   public String getVerb() {
/* 48 */     return this._verb;
/*    */   }
/*    */   
/*    */   public void setVerb(String s) {
/* 52 */     this._verb = s;
/*    */   }
/*    */   
/*    */   public void validateThis() {
/* 56 */     if (this._verb == null)
/* 57 */       failValidation("validation.missingRequiredAttribute", "verb"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\http\HTTPBinding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */