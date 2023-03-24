/*     */ package com.sun.xml.fastinfoset.stax.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.Namespace;
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
/*     */ public class EndElementEvent
/*     */   extends EventBase
/*     */   implements EndElement
/*     */ {
/*  33 */   List _namespaces = null;
/*     */   QName _qname;
/*     */   
/*     */   public void reset() {
/*  37 */     if (this._namespaces != null) this._namespaces.clear(); 
/*     */   }
/*     */   
/*     */   public EndElementEvent() {
/*  41 */     setEventType(2);
/*     */   }
/*     */   
/*     */   public EndElementEvent(String prefix, String namespaceURI, String localpart) {
/*  45 */     this._qname = getQName(namespaceURI, localpart, prefix);
/*  46 */     setEventType(2);
/*     */   }
/*     */   
/*     */   public EndElementEvent(QName qname) {
/*  50 */     this._qname = qname;
/*  51 */     setEventType(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/*  59 */     return this._qname;
/*     */   }
/*     */   
/*     */   public void setName(QName qname) {
/*  63 */     this._qname = qname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getNamespaces() {
/*  74 */     if (this._namespaces != null)
/*  75 */       return this._namespaces.iterator(); 
/*  76 */     return EmptyIterator.getInstance();
/*     */   }
/*     */   
/*     */   public void addNamespace(Namespace namespace) {
/*  80 */     if (this._namespaces == null) {
/*  81 */       this._namespaces = new ArrayList();
/*     */     }
/*  83 */     this._namespaces.add(namespace);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  87 */     StringBuffer sb = new StringBuffer();
/*  88 */     sb.append("</").append(nameAsString());
/*  89 */     Iterator<E> namespaces = getNamespaces();
/*  90 */     while (namespaces.hasNext()) {
/*  91 */       sb.append(" ").append(namespaces.next().toString());
/*     */     }
/*  93 */     sb.append(">");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private String nameAsString() {
/*  99 */     if ("".equals(this._qname.getNamespaceURI()))
/* 100 */       return this._qname.getLocalPart(); 
/* 101 */     if (this._qname.getPrefix() != null) {
/* 102 */       return "['" + this._qname.getNamespaceURI() + "']:" + this._qname.getPrefix() + ":" + this._qname.getLocalPart();
/*     */     }
/* 104 */     return "['" + this._qname.getNamespaceURI() + "']:" + this._qname.getLocalPart();
/*     */   }
/*     */   private QName getQName(String uri, String localPart, String prefix) {
/* 107 */     QName qn = null;
/* 108 */     if (prefix != null && uri != null) {
/* 109 */       qn = new QName(uri, localPart, prefix);
/* 110 */     } else if (prefix == null && uri != null) {
/* 111 */       qn = new QName(uri, localPart);
/* 112 */     } else if (prefix == null && uri == null) {
/* 113 */       qn = new QName(localPart);
/* 114 */     }  return qn;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\EndElementEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */