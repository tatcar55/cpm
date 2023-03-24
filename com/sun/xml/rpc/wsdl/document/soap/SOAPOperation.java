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
/*    */ public class SOAPOperation
/*    */   extends Extension
/*    */ {
/*    */   private String _soapAction;
/*    */   private SOAPStyle _style;
/*    */   
/*    */   public QName getElementName() {
/* 44 */     return SOAPConstants.QNAME_OPERATION;
/*    */   }
/*    */   
/*    */   public String getSOAPAction() {
/* 48 */     return this._soapAction;
/*    */   }
/*    */   
/*    */   public void setSOAPAction(String s) {
/* 52 */     this._soapAction = s;
/*    */   }
/*    */   
/*    */   public SOAPStyle getStyle() {
/* 56 */     return this._style;
/*    */   }
/*    */   
/*    */   public void setStyle(SOAPStyle s) {
/* 60 */     this._style = s;
/*    */   }
/*    */   
/*    */   public boolean isDocument() {
/* 64 */     return (this._style == SOAPStyle.DOCUMENT);
/*    */   }
/*    */   
/*    */   public boolean isRPC() {
/* 68 */     return (this._style == SOAPStyle.RPC);
/*    */   }
/*    */   
/*    */   public void validateThis() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\soap\SOAPOperation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */