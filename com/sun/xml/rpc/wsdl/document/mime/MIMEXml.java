/*    */ package com.sun.xml.rpc.wsdl.document.mime;
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
/*    */ public class MIMEXml
/*    */   extends Extension
/*    */ {
/*    */   private String _part;
/*    */   
/*    */   public QName getElementName() {
/* 44 */     return MIMEConstants.QNAME_MIME_XML;
/*    */   }
/*    */   
/*    */   public String getPart() {
/* 48 */     return this._part;
/*    */   }
/*    */   
/*    */   public void setPart(String s) {
/* 52 */     this._part = s;
/*    */   }
/*    */   
/*    */   public void validateThis() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\mime\MIMEXml.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */