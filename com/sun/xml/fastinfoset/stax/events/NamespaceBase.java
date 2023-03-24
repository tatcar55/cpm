/*    */ package com.sun.xml.fastinfoset.stax.events;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.events.Namespace;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NamespaceBase
/*    */   extends AttributeBase
/*    */   implements Namespace
/*    */ {
/*    */   static final String DEFAULT_NS_PREFIX = "";
/*    */   static final String XML_NS_URI = "http://www.w3.org/XML/1998/namespace";
/*    */   static final String XML_NS_PREFIX = "xml";
/*    */   static final String XMLNS_ATTRIBUTE_NS_URI = "http://www.w3.org/2000/xmlns/";
/*    */   static final String XMLNS_ATTRIBUTE = "xmlns";
/*    */   static final String W3C_XML_SCHEMA_NS_URI = "http://www.w3.org/2001/XMLSchema";
/*    */   static final String W3C_XML_SCHEMA_INSTANCE_NS_URI = "http://www.w3.org/2001/XMLSchema-instance";
/*    */   private boolean defaultDeclaration = false;
/*    */   
/*    */   public NamespaceBase(String namespaceURI) {
/* 39 */     super("xmlns", "", namespaceURI);
/* 40 */     setEventType(13);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NamespaceBase(String prefix, String namespaceURI) {
/* 49 */     super("xmlns", prefix, namespaceURI);
/* 50 */     setEventType(13);
/* 51 */     if (Util.isEmptyString(prefix)) {
/* 52 */       this.defaultDeclaration = true;
/*    */     }
/*    */   }
/*    */   
/*    */   void setPrefix(String prefix) {
/* 57 */     if (prefix == null) {
/* 58 */       setName(new QName("http://www.w3.org/2000/xmlns/", "", "xmlns"));
/*    */     } else {
/* 60 */       setName(new QName("http://www.w3.org/2000/xmlns/", prefix, "xmlns"));
/*    */     } 
/*    */   }
/*    */   public String getPrefix() {
/* 64 */     if (this.defaultDeclaration) return ""; 
/* 65 */     return getLocalName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void setNamespaceURI(String uri) {
/* 74 */     setValue(uri);
/*    */   }
/*    */   public String getNamespaceURI() {
/* 77 */     return getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isNamespace() {
/* 82 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isDefaultNamespaceDeclaration() {
/* 86 */     return this.defaultDeclaration;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\NamespaceBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */