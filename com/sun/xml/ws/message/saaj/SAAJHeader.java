/*    */ package com.sun.xml.ws.message.saaj;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.message.DOMHeader;
/*    */ import javax.xml.soap.SOAPHeaderElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SAAJHeader
/*    */   extends DOMHeader<SOAPHeaderElement>
/*    */ {
/*    */   public SAAJHeader(SOAPHeaderElement header) {
/* 59 */     super(header);
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String getRole(@NotNull SOAPVersion soapVersion) {
/* 65 */     String v = getAttribute(soapVersion.nsUri, soapVersion.roleAttributeName);
/* 66 */     if (v == null || v.equals(""))
/* 67 */       v = soapVersion.implicitRole; 
/* 68 */     return v;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\saaj\SAAJHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */