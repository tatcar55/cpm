/*    */ package com.sun.xml.ws.model.soap;
/*    */ 
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.model.soap.SOAPBinding;
/*    */ import javax.jws.soap.SOAPBinding;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SOAPBindingImpl
/*    */   extends SOAPBinding
/*    */ {
/*    */   public SOAPBindingImpl() {}
/*    */   
/*    */   public SOAPBindingImpl(SOAPBinding sb) {
/* 60 */     this.use = sb.getUse();
/* 61 */     this.style = sb.getStyle();
/* 62 */     this.soapVersion = sb.getSOAPVersion();
/* 63 */     this.soapAction = sb.getSOAPAction();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setStyle(SOAPBinding.Style style) {
/* 70 */     this.style = style;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSOAPVersion(SOAPVersion version) {
/* 77 */     this.soapVersion = version;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSOAPAction(String soapAction) {
/* 84 */     this.soapAction = soapAction;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\soap\SOAPBindingImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */