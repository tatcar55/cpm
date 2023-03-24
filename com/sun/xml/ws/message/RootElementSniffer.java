/*    */ package com.sun.xml.ws.message;
/*    */ 
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.helpers.AttributesImpl;
/*    */ import org.xml.sax.helpers.DefaultHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RootElementSniffer
/*    */   extends DefaultHandler
/*    */ {
/* 54 */   private String nsUri = "##error";
/* 55 */   private String localName = "##error";
/*    */   
/*    */   private Attributes atts;
/*    */   private final boolean parseAttributes;
/*    */   
/*    */   public RootElementSniffer(boolean parseAttributes) {
/* 61 */     this.parseAttributes = parseAttributes;
/*    */   }
/*    */   
/*    */   public RootElementSniffer() {
/* 65 */     this(true);
/*    */   }
/*    */   
/*    */   public void startElement(String uri, String localName, String qName, Attributes a) throws SAXException {
/* 69 */     this.nsUri = uri;
/* 70 */     this.localName = localName;
/*    */     
/* 72 */     if (this.parseAttributes) {
/* 73 */       if (a.getLength() == 0) {
/* 74 */         this.atts = EMPTY_ATTRIBUTES;
/*    */       } else {
/* 76 */         this.atts = new AttributesImpl(a);
/*    */       } 
/*    */     }
/*    */     
/* 80 */     throw aSAXException;
/*    */   }
/*    */   
/*    */   public String getNsUri() {
/* 84 */     return this.nsUri;
/*    */   }
/*    */   
/*    */   public String getLocalName() {
/* 88 */     return this.localName;
/*    */   }
/*    */   
/*    */   public Attributes getAttributes() {
/* 92 */     return this.atts;
/*    */   }
/*    */   
/* 95 */   private static final SAXException aSAXException = new SAXException();
/* 96 */   private static final Attributes EMPTY_ATTRIBUTES = new AttributesImpl();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\RootElementSniffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */