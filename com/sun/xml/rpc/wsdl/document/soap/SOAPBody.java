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
/*    */ public class SOAPBody
/*    */   extends Extension
/*    */ {
/*    */   private String _encodingStyle;
/*    */   private String _namespace;
/*    */   private String _parts;
/*    */   private SOAPUse _use;
/*    */   
/*    */   public QName getElementName() {
/* 44 */     return SOAPConstants.QNAME_BODY;
/*    */   }
/*    */   
/*    */   public String getNamespace() {
/* 48 */     return this._namespace;
/*    */   }
/*    */   
/*    */   public void setNamespace(String s) {
/* 52 */     this._namespace = s;
/*    */   }
/*    */   
/*    */   public SOAPUse getUse() {
/* 56 */     return this._use;
/*    */   }
/*    */   
/*    */   public void setUse(SOAPUse u) {
/* 60 */     this._use = u;
/*    */   }
/*    */   
/*    */   public boolean isEncoded() {
/* 64 */     return (this._use == SOAPUse.ENCODED);
/*    */   }
/*    */   
/*    */   public boolean isLiteral() {
/* 68 */     return (this._use == SOAPUse.LITERAL);
/*    */   }
/*    */   
/*    */   public String getEncodingStyle() {
/* 72 */     return this._encodingStyle;
/*    */   }
/*    */   
/*    */   public void setEncodingStyle(String s) {
/* 76 */     this._encodingStyle = s;
/*    */   }
/*    */   
/*    */   public String getParts() {
/* 80 */     return this._parts;
/*    */   }
/*    */   
/*    */   public void setParts(String s) {
/* 84 */     this._parts = s;
/*    */   }
/*    */   
/*    */   public void validateThis() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\soap\SOAPBody.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */