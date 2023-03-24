/*    */ package com.sun.xml.ws.encoding.xml;
/*    */ 
/*    */ import com.oracle.webservices.api.message.BasePropertySet;
/*    */ import com.oracle.webservices.api.message.PropertySet.Property;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLPropertyBag
/*    */   extends BasePropertySet
/*    */ {
/*    */   private String contentType;
/*    */   
/*    */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 50 */     return model;
/*    */   }
/*    */   
/*    */   @Property({"com.sun.jaxws.rest.contenttype"})
/*    */   public String getXMLContentType() {
/* 55 */     return this.contentType;
/*    */   }
/*    */   
/*    */   public void setXMLContentType(String content) {
/* 59 */     this.contentType = content;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   private static final BasePropertySet.PropertyMap model = parse(XMLPropertyBag.class);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\xml\XMLPropertyBag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */