/*    */ package com.sun.xml.rpc.soap.message;
/*    */ 
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
/*    */ public class SOAPFaultInfo
/*    */ {
/*    */   private QName code;
/*    */   private String string;
/*    */   private String actor;
/*    */   private Object detail;
/*    */   
/*    */   public SOAPFaultInfo(QName code, String string, String actor) {
/* 38 */     this(code, string, actor, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPFaultInfo(QName code, String string, String actor, Object detail) {
/* 46 */     this.code = code;
/* 47 */     this.string = string;
/* 48 */     this.actor = actor;
/* 49 */     this.detail = detail;
/*    */   }
/*    */   
/*    */   public QName getCode() {
/* 53 */     return this.code;
/*    */   }
/*    */   
/*    */   public void setCode(QName code) {
/* 57 */     this.code = code;
/*    */   }
/*    */   
/*    */   public String getString() {
/* 61 */     return this.string;
/*    */   }
/*    */   
/*    */   public void setString(String string) {
/* 65 */     this.string = string;
/*    */   }
/*    */   
/*    */   public String getActor() {
/* 69 */     return this.actor;
/*    */   }
/*    */   
/*    */   public void setActor(String actor) {
/* 73 */     this.actor = actor;
/*    */   }
/*    */   
/*    */   public Object getDetail() {
/* 77 */     return this.detail;
/*    */   }
/*    */   
/*    */   public void setDetail(Object detail) {
/* 81 */     this.detail = detail;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\message\SOAPFaultInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */