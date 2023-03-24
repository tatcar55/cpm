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
/*    */ public class SOAPBinding
/*    */   extends Extension
/*    */ {
/*    */   private String _transport;
/* 41 */   private SOAPStyle _style = SOAPStyle.DOCUMENT;
/*    */ 
/*    */   
/*    */   public QName getElementName() {
/* 45 */     return SOAPConstants.QNAME_BINDING;
/*    */   }
/*    */   
/*    */   public String getTransport() {
/* 49 */     return this._transport;
/*    */   }
/*    */   
/*    */   public void setTransport(String s) {
/* 53 */     this._transport = s;
/*    */   }
/*    */   
/*    */   public SOAPStyle getStyle() {
/* 57 */     return this._style;
/*    */   }
/*    */   
/*    */   public void setStyle(SOAPStyle s) {
/* 61 */     this._style = s;
/*    */   }
/*    */   
/*    */   public boolean isDocument() {
/* 65 */     return (this._style == SOAPStyle.DOCUMENT);
/*    */   }
/*    */   
/*    */   public boolean isRPC() {
/* 69 */     return (this._style == SOAPStyle.RPC);
/*    */   }
/*    */   
/*    */   public void validateThis() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\soap\SOAPBinding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */