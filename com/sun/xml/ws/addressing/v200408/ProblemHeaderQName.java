/*    */ package com.sun.xml.ws.addressing.v200408;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlValue;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "ProblemHeaderQName", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*    */ public class ProblemHeaderQName
/*    */ {
/*    */   @XmlValue
/*    */   private QName value;
/*    */   
/*    */   public ProblemHeaderQName() {}
/*    */   
/*    */   public ProblemHeaderQName(QName name) {
/* 62 */     this.value = name;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\v200408\ProblemHeaderQName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */