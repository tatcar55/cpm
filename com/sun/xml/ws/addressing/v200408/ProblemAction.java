/*    */ package com.sun.xml.ws.addressing.v200408;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "ProblemAction", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*    */ public class ProblemAction
/*    */ {
/*    */   @XmlElement(name = "Action", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*    */   private String action;
/*    */   @XmlElement(name = "SoapAction", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*    */   private String soapAction;
/*    */   
/*    */   public ProblemAction() {}
/*    */   
/*    */   public ProblemAction(String action) {
/* 64 */     this.action = action;
/*    */   }
/*    */   
/*    */   public ProblemAction(String action, String soapAction) {
/* 68 */     this.action = action;
/* 69 */     this.soapAction = soapAction;
/*    */   }
/*    */   
/*    */   public String getAction() {
/* 73 */     return this.action;
/*    */   }
/*    */   
/*    */   public String getSoapAction() {
/* 77 */     return this.soapAction;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\v200408\ProblemAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */