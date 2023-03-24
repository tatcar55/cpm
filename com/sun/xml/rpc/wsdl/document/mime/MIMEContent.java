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
/*    */ public class MIMEContent
/*    */   extends Extension
/*    */ {
/*    */   private String _part;
/*    */   private String _type;
/*    */   
/*    */   public QName getElementName() {
/* 44 */     return MIMEConstants.QNAME_CONTENT;
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
/*    */   public String getType() {
/* 56 */     return this._type;
/*    */   }
/*    */   
/*    */   public void setType(String s) {
/* 60 */     this._type = s;
/*    */   }
/*    */   
/*    */   public void validateThis() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\mime\MIMEContent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */