/*    */ package com.sun.xml.ws.security.opt.crypto.jaxb;
/*    */ 
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.crypto.XMLStructure;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JAXBStructure
/*    */   implements XMLStructure
/*    */ {
/*    */   private final JAXBElement jbElement;
/*    */   
/*    */   public JAXBStructure(JAXBElement jbElement) {
/* 63 */     if (jbElement == null)
/* 64 */       throw new NullPointerException("JAXBElement cannot be null"); 
/* 65 */     this.jbElement = jbElement;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JAXBElement getJAXBElement() {
/* 74 */     return this.jbElement;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFeatureSupported(String feature) {
/* 81 */     if (feature == null) {
/* 82 */       throw new NullPointerException();
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\jaxb\JAXBStructure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */