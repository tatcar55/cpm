/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
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
/*     */ public class EndElementEvent
/*     */   extends AbstractXMLEvent
/*     */   implements EndElement
/*     */ {
/*     */   protected QName name;
/*     */   protected Collection namespaces;
/*     */   
/*     */   public EndElementEvent(QName name, Iterator namespaces) {
/*  63 */     this(name, namespaces, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EndElementEvent(QName name, Iterator namespaces, Location location) {
/*  69 */     this(name, namespaces, location, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndElementEvent(QName name, Iterator namespaces, Location location, QName schemaType) {
/*  76 */     super(location, schemaType);
/*     */     
/*  78 */     this.name = name;
/*  79 */     if (namespaces != null && namespaces.hasNext()) {
/*     */       
/*  81 */       List nsList = new ArrayList();
/*     */       
/*     */       do {
/*  84 */         Namespace ns = namespaces.next();
/*  85 */         nsList.add(ns);
/*     */       }
/*  87 */       while (namespaces.hasNext());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndElementEvent(EndElement that) {
/*  95 */     super(that);
/*  96 */     this.name = that.getName();
/*     */     
/*  98 */     Iterator namespaces = that.getNamespaces();
/*  99 */     if (namespaces != null && namespaces.hasNext()) {
/*     */       
/* 101 */       List nsList = new ArrayList();
/*     */       
/*     */       do {
/* 104 */         Namespace ns = namespaces.next();
/* 105 */         nsList.add(ns);
/*     */       }
/* 107 */       while (namespaces.hasNext());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEventType() {
/* 118 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/* 124 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getNamespaces() {
/* 130 */     if (this.namespaces != null)
/*     */     {
/* 132 */       return this.namespaces.iterator();
/*     */     }
/*     */ 
/*     */     
/* 136 */     return Collections.EMPTY_LIST.iterator();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\EndElementEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */