/*    */ package com.sun.xml.wss.impl.c14n;
/*    */ 
/*    */ import org.xml.sax.Attributes;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Attribute
/*    */ {
/* 60 */   int position = 0;
/* 61 */   Attributes attributes = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPosition(int pos) {
/* 68 */     this.position = pos;
/*    */   }
/*    */   public void setAttributes(Attributes attrs) {
/* 71 */     this.attributes = attrs;
/*    */   }
/*    */   public String getLocalName() {
/* 74 */     return this.attributes.getLocalName(this.position);
/*    */   }
/*    */   
/*    */   public String getNamespaceURI() {
/* 78 */     return this.attributes.getURI(this.position);
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 82 */     return this.attributes.getValue(this.position);
/*    */   }
/*    */   public int getPosition() {
/* 85 */     return this.position;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\Attribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */