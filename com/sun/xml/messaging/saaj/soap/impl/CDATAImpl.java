/*     */ package com.sun.xml.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.dom.CDATASectionImpl;
/*     */ import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.Text;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class CDATAImpl
/*     */   extends CDATASectionImpl
/*     */   implements Text
/*     */ {
/*  55 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.impl", "com.sun.xml.messaging.saaj.soap.impl.LocalStrings");
/*     */   
/*     */   static final String cdataUC = "<![CDATA[";
/*     */   
/*     */   static final String cdataLC = "<![cdata[";
/*     */ 
/*     */   
/*     */   public CDATAImpl(SOAPDocumentImpl ownerDoc, String text) {
/*  63 */     super((CoreDocumentImpl)ownerDoc, text);
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  67 */     String nodeValue = getNodeValue();
/*  68 */     return nodeValue.equals("") ? null : nodeValue;
/*     */   }
/*     */   
/*     */   public void setValue(String text) {
/*  72 */     setNodeValue(text);
/*     */   }
/*     */   
/*     */   public void setParentElement(SOAPElement parent) throws SOAPException {
/*  76 */     if (parent == null) {
/*  77 */       log.severe("SAAJ0145.impl.no.null.to.parent.elem");
/*  78 */       throw new SOAPException("Cannot pass NULL to setParentElement");
/*     */     } 
/*  80 */     ((ElementImpl)parent).addNode(this);
/*     */   }
/*     */   
/*     */   public SOAPElement getParentElement() {
/*  84 */     return (SOAPElement)getParentNode();
/*     */   }
/*     */ 
/*     */   
/*     */   public void detachNode() {
/*  89 */     Node parent = getParentNode();
/*  90 */     if (parent != null) {
/*  91 */       parent.removeChild(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void recycleNode() {
/*  96 */     detachNode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComment() {
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\CDATAImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */