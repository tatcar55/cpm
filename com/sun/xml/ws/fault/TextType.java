/*    */ package com.sun.xml.ws.fault;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import javax.xml.bind.annotation.XmlValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlType(name = "TextType", namespace = "http://www.w3.org/2003/05/soap-envelope")
/*    */ class TextType
/*    */ {
/*    */   @XmlValue
/*    */   private String text;
/*    */   @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace", required = true)
/*    */   private String lang;
/*    */   
/*    */   TextType() {}
/*    */   
/*    */   TextType(String text) {
/* 67 */     this.text = text;
/* 68 */     this.lang = Locale.getDefault().getLanguage();
/*    */   }
/*    */   
/*    */   String getText() {
/* 72 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\fault\TextType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */