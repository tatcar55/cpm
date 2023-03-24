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
/*    */ public class SOAPFault
/*    */   extends Extension
/*    */ {
/*    */   private String _name;
/*    */   private String _encodingStyle;
/*    */   private String _namespace;
/* 41 */   private SOAPUse _use = SOAPUse.LITERAL;
/*    */ 
/*    */   
/*    */   public QName getElementName() {
/* 45 */     return SOAPConstants.QNAME_FAULT;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 49 */     return this._name;
/*    */   }
/*    */   
/*    */   public void setName(String s) {
/* 53 */     this._name = s;
/*    */   }
/*    */   
/*    */   public String getNamespace() {
/* 57 */     return this._namespace;
/*    */   }
/*    */   
/*    */   public void setNamespace(String s) {
/* 61 */     this._namespace = s;
/*    */   }
/*    */   
/*    */   public SOAPUse getUse() {
/* 65 */     return this._use;
/*    */   }
/*    */   
/*    */   public void setUse(SOAPUse u) {
/* 69 */     this._use = u;
/*    */   }
/*    */   
/*    */   public boolean isEncoded() {
/* 73 */     return (this._use == SOAPUse.ENCODED);
/*    */   }
/*    */   
/*    */   public boolean isLiteral() {
/* 77 */     return (this._use == SOAPUse.LITERAL);
/*    */   }
/*    */   
/*    */   public String getEncodingStyle() {
/* 81 */     return this._encodingStyle;
/*    */   }
/*    */   
/*    */   public void setEncodingStyle(String s) {
/* 85 */     this._encodingStyle = s;
/*    */   }
/*    */   
/*    */   public void validateThis() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\soap\SOAPFault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */