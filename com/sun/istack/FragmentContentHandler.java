/*    */ package com.sun.istack;
/*    */ 
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.XMLReader;
/*    */ import org.xml.sax.helpers.XMLFilterImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FragmentContentHandler
/*    */   extends XMLFilterImpl
/*    */ {
/*    */   public FragmentContentHandler() {}
/*    */   
/*    */   public FragmentContentHandler(XMLReader parent) {
/* 57 */     super(parent);
/*    */   }
/*    */ 
/*    */   
/*    */   public FragmentContentHandler(ContentHandler handler) {
/* 62 */     setContentHandler(handler);
/*    */   }
/*    */   
/*    */   public void startDocument() throws SAXException {}
/*    */   
/*    */   public void endDocument() throws SAXException {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\FragmentContentHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */