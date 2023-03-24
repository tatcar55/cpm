/*    */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*    */ 
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.SAXException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Discarder
/*    */   implements UnmarshallingEventHandler
/*    */ {
/*    */   private final UnmarshallingContext context;
/* 24 */   private int depth = 0;
/*    */ 
/*    */   
/*    */   public Discarder(UnmarshallingContext _ctxt) {
/* 28 */     this.context = _ctxt;
/*    */   }
/*    */ 
/*    */   
/*    */   public void enterAttribute(String uri, String local, String qname) throws SAXException {}
/*    */   
/*    */   public void enterElement(String uri, String local, String qname, Attributes atts) throws SAXException {
/* 35 */     this.depth++;
/*    */   }
/*    */ 
/*    */   
/*    */   public void leaveAttribute(String uri, String local, String qname) throws SAXException {}
/*    */   
/*    */   public void leaveElement(String uri, String local, String qname) throws SAXException {
/* 42 */     this.depth--;
/* 43 */     if (this.depth == 0)
/* 44 */       this.context.popContentHandler(); 
/*    */   }
/*    */   
/*    */   public Object owner() {
/* 48 */     return null;
/*    */   }
/*    */   
/*    */   public void text(String s) throws SAXException {}
/*    */   
/*    */   public void leaveChild(int nextState) throws SAXException {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\Discarder.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */