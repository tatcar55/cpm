/*    */ package com.sun.xml.ws.addressing;
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
/*    */ 
/*    */ @XmlRootElement(name = "ProblemAction", namespace = "http://www.w3.org/2005/08/addressing")
/*    */ public class ProblemAction
/*    */ {
/*    */   @XmlElement(name = "Action", namespace = "http://www.w3.org/2005/08/addressing")
/*    */   private String action;
/*    */   @XmlElement(name = "SoapAction", namespace = "http://www.w3.org/2005/08/addressing")
/*    */   private String soapAction;
/*    */   
/*    */   public ProblemAction() {}
/*    */   
/*    */   public ProblemAction(String action) {
/* 65 */     this.action = action;
/*    */   }
/*    */   
/*    */   public ProblemAction(String action, String soapAction) {
/* 69 */     this.action = action;
/* 70 */     this.soapAction = soapAction;
/*    */   }
/*    */   
/*    */   public String getAction() {
/* 74 */     return this.action;
/*    */   }
/*    */   
/*    */   public String getSoapAction() {
/* 78 */     return this.soapAction;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\ProblemAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */