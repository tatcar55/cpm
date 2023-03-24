/*     */ package com.sun.xml.messaging.saaj.soap.ver1_2;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocument;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.DetailImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.DetailEntry;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Detail1_2Impl
/*     */   extends DetailImpl
/*     */ {
/*  60 */   protected static final Logger log = Logger.getLogger(Detail1_2Impl.class.getName(), "com.sun.xml.messaging.saaj.soap.ver1_2.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public Detail1_2Impl(SOAPDocumentImpl ownerDocument, String prefix) {
/*  65 */     super(ownerDocument, NameImpl.createSOAP12Name("Detail", prefix));
/*     */   }
/*     */   
/*     */   public Detail1_2Impl(SOAPDocumentImpl ownerDocument) {
/*  69 */     super(ownerDocument, NameImpl.createSOAP12Name("Detail"));
/*     */   }
/*     */   
/*     */   protected DetailEntry createDetailEntry(Name name) {
/*  73 */     return (DetailEntry)new DetailEntry1_2Impl(((SOAPDocument)getOwnerDocument()).getDocument(), name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected DetailEntry createDetailEntry(QName name) {
/*  79 */     return (DetailEntry)new DetailEntry1_2Impl(((SOAPDocument)getOwnerDocument()).getDocument(), name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncodingStyle(String encodingStyle) throws SOAPException {
/*  89 */     log.severe("SAAJ0403.ver1_2.no.encodingStyle.in.detail");
/*  90 */     throw new SOAPExceptionImpl("EncodingStyle attribute cannot appear in Detail");
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
/*  95 */     if (name.getLocalName().equals("encodingStyle") && name.getURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/*  97 */       setEncodingStyle(value);
/*     */     }
/*  99 */     return super.addAttribute(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(QName name, String value) throws SOAPException {
/* 104 */     if (name.getLocalPart().equals("encodingStyle") && name.getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/* 106 */       setEncodingStyle(value);
/*     */     }
/* 108 */     return super.addAttribute(name, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_2\Detail1_2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */