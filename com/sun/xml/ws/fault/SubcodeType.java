/*    */ package com.sun.xml.ws.fault;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlElements;
/*    */ import javax.xml.bind.annotation.XmlTransient;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "SubcodeType", namespace = "http://www.w3.org/2003/05/soap-envelope", propOrder = {"Value", "Subcode"})
/*    */ class SubcodeType
/*    */ {
/*    */   @XmlTransient
/*    */   private static final String ns = "http://www.w3.org/2003/05/soap-envelope";
/*    */   @XmlElement(namespace = "http://www.w3.org/2003/05/soap-envelope")
/*    */   private QName Value;
/*    */   @XmlElements({@XmlElement(namespace = "http://www.w3.org/2003/05/soap-envelope")})
/*    */   private SubcodeType Subcode;
/*    */   
/*    */   public SubcodeType(QName value) {
/* 82 */     this.Value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public SubcodeType() {}
/*    */   
/*    */   QName getValue() {
/* 89 */     return this.Value;
/*    */   }
/*    */   
/*    */   SubcodeType getSubcode() {
/* 93 */     return this.Subcode;
/*    */   }
/*    */   
/*    */   void setSubcode(SubcodeType subcode) {
/* 97 */     this.Subcode = subcode;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\fault\SubcodeType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */