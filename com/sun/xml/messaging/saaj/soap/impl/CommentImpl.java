/*     */ package com.sun.xml.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.dom.CommentImpl;
/*     */ import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.Text;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.DOMException;
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
/*     */ 
/*     */ public class CommentImpl
/*     */   extends CommentImpl
/*     */   implements Text, Comment
/*     */ {
/*  59 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.impl", "com.sun.xml.messaging.saaj.soap.impl.LocalStrings");
/*     */ 
/*     */   
/*  62 */   protected static ResourceBundle rb = log.getResourceBundle();
/*     */ 
/*     */   
/*     */   public CommentImpl(SOAPDocumentImpl ownerDoc, String text) {
/*  66 */     super((CoreDocumentImpl)ownerDoc, text);
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  70 */     String nodeValue = getNodeValue();
/*  71 */     return nodeValue.equals("") ? null : nodeValue;
/*     */   }
/*     */   
/*     */   public void setValue(String text) {
/*  75 */     setNodeValue(text);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParentElement(SOAPElement element) throws SOAPException {
/*  80 */     if (element == null) {
/*  81 */       log.severe("SAAJ0112.impl.no.null.to.parent.elem");
/*  82 */       throw new SOAPException("Cannot pass NULL to setParentElement");
/*     */     } 
/*  84 */     ((ElementImpl)element).addNode(this);
/*     */   }
/*     */   
/*     */   public SOAPElement getParentElement() {
/*  88 */     return (SOAPElement)getParentNode();
/*     */   }
/*     */   
/*     */   public void detachNode() {
/*  92 */     Node parent = getParentNode();
/*  93 */     if (parent != null) {
/*  94 */       parent.removeChild(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void recycleNode() {
/*  99 */     detachNode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComment() {
/* 106 */     return true;
/*     */   }
/*     */   
/*     */   public Text splitText(int offset) throws DOMException {
/* 110 */     log.severe("SAAJ0113.impl.cannot.split.text.from.comment");
/* 111 */     throw new UnsupportedOperationException("Cannot split text from a Comment Node.");
/*     */   }
/*     */   
/*     */   public Text replaceWholeText(String content) throws DOMException {
/* 115 */     log.severe("SAAJ0114.impl.cannot.replace.wholetext.from.comment");
/* 116 */     throw new UnsupportedOperationException("Cannot replace Whole Text from a Comment Node.");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWholeText() {
/* 121 */     throw new UnsupportedOperationException("Not Supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isElementContentWhitespace() {
/* 126 */     throw new UnsupportedOperationException("Not Supported");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\CommentImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */