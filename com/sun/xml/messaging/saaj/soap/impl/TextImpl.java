/*     */ package com.sun.xml.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
/*     */ import com.sun.org.apache.xerces.internal.dom.TextImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.Text;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.Text;
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
/*     */ public class TextImpl
/*     */   extends TextImpl
/*     */   implements Text, Text
/*     */ {
/*  55 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.impl", "com.sun.xml.messaging.saaj.soap.impl.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public TextImpl(SOAPDocumentImpl ownerDoc, String text) {
/*  60 */     super((CoreDocumentImpl)ownerDoc, text);
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  64 */     String nodeValue = getNodeValue();
/*  65 */     return nodeValue.equals("") ? null : nodeValue;
/*     */   }
/*     */   
/*     */   public void setValue(String text) {
/*  69 */     setNodeValue(text);
/*     */   }
/*     */   
/*     */   public void setParentElement(SOAPElement parent) throws SOAPException {
/*  73 */     if (parent == null) {
/*  74 */       log.severe("SAAJ0126.impl.cannot.locate.ns");
/*  75 */       throw new SOAPException("Cannot pass NULL to setParentElement");
/*     */     } 
/*  77 */     ((ElementImpl)parent).addNode(this);
/*     */   }
/*     */   
/*     */   public SOAPElement getParentElement() {
/*  81 */     return (SOAPElement)getParentNode();
/*     */   }
/*     */ 
/*     */   
/*     */   public void detachNode() {
/*  86 */     Node parent = getParentNode();
/*  87 */     if (parent != null) {
/*  88 */       parent.removeChild(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void recycleNode() {
/*  93 */     detachNode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComment() {
/* 100 */     String txt = getNodeValue();
/* 101 */     if (txt == null) {
/* 102 */       return false;
/*     */     }
/* 104 */     return (txt.startsWith("<!--") && txt.endsWith("-->"));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\TextImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */