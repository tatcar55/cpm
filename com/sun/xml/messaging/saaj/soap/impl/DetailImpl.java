/*     */ package com.sun.xml.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.DetailEntry;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.w3c.dom.Element;
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
/*     */ public abstract class DetailImpl
/*     */   extends FaultElementImpl
/*     */   implements Detail
/*     */ {
/*     */   public DetailImpl(SOAPDocumentImpl ownerDoc, NameImpl detailName) {
/*  56 */     super(ownerDoc, detailName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract DetailEntry createDetailEntry(Name paramName);
/*     */   
/*     */   public DetailEntry addDetailEntry(Name name) throws SOAPException {
/*  63 */     DetailEntry entry = createDetailEntry(name);
/*  64 */     addNode(entry);
/*  65 */     return (DetailEntry)circumventBug5034339(entry);
/*     */   }
/*     */   protected abstract DetailEntry createDetailEntry(QName paramQName);
/*     */   public DetailEntry addDetailEntry(QName qname) throws SOAPException {
/*  69 */     DetailEntry entry = createDetailEntry(qname);
/*  70 */     addNode(entry);
/*  71 */     return (DetailEntry)circumventBug5034339(entry);
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(Name name) throws SOAPException {
/*  75 */     return addDetailEntry(name);
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(QName name) throws SOAPException {
/*  79 */     return addDetailEntry(name);
/*     */   }
/*     */   
/*     */   protected SOAPElement convertToSoapElement(Element element) {
/*  83 */     if (element instanceof DetailEntry) {
/*  84 */       return (SOAPElement)element;
/*     */     }
/*  86 */     DetailEntry detailEntry = createDetailEntry(NameImpl.copyElementName(element));
/*     */     
/*  88 */     return replaceElementWithSOAPElement(element, (ElementImpl)detailEntry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getDetailEntries() {
/*  95 */     return new Iterator() {
/*  96 */         Iterator eachNode = DetailImpl.this.getChildElementNodes();
/*  97 */         SOAPElement next = null;
/*  98 */         SOAPElement last = null;
/*     */         
/*     */         public boolean hasNext() {
/* 101 */           if (this.next == null) {
/* 102 */             while (this.eachNode.hasNext()) {
/* 103 */               this.next = this.eachNode.next();
/* 104 */               if (this.next instanceof DetailEntry) {
/*     */                 break;
/*     */               }
/* 107 */               this.next = null;
/*     */             } 
/*     */           }
/* 110 */           return (this.next != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 114 */           if (!hasNext()) {
/* 115 */             throw new NoSuchElementException();
/*     */           }
/* 117 */           this.last = this.next;
/* 118 */           this.next = null;
/* 119 */           return this.last;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 123 */           if (this.last == null) {
/* 124 */             throw new IllegalStateException();
/*     */           }
/* 126 */           SOAPElement target = this.last;
/* 127 */           DetailImpl.this.removeChild(target);
/* 128 */           this.last = null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected boolean isStandardFaultElement() {
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SOAPElement circumventBug5034339(SOAPElement element) {
/* 143 */     Name elementName = element.getElementName();
/* 144 */     if (!isNamespaceQualified(elementName)) {
/* 145 */       String prefix = elementName.getPrefix();
/* 146 */       String defaultNamespace = getNamespaceURI(prefix);
/* 147 */       if (defaultNamespace != null) {
/* 148 */         NameImpl nameImpl = NameImpl.create(elementName.getLocalName(), elementName.getPrefix(), defaultNamespace);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         SOAPElement newElement = createDetailEntry((Name)nameImpl);
/* 154 */         replaceChild(newElement, element);
/* 155 */         return newElement;
/*     */       } 
/*     */     } 
/* 158 */     return element;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\DetailImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */