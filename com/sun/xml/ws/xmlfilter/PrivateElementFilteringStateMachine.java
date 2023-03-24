/*     */ package com.sun.xml.ws.xmlfilter;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrivateElementFilteringStateMachine
/*     */   implements FilteringStateMachine
/*     */ {
/*  55 */   private static final Logger LOGGER = Logger.getLogger(PrivateElementFilteringStateMachine.class);
/*     */   
/*     */   private int depth;
/*     */   
/*     */   private boolean filteringOn;
/*     */   
/*     */   private final QName[] filteredElements;
/*     */   
/*     */   public PrivateElementFilteringStateMachine(QName... filteredElements) {
/*  64 */     if (filteredElements == null) {
/*  65 */       this.filteredElements = new QName[0];
/*     */     } else {
/*  67 */       this.filteredElements = new QName[filteredElements.length];
/*  68 */       System.arraycopy(filteredElements, 0, this.filteredElements, 0, filteredElements.length);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ProcessingStateChange getStateChange(Invocation invocation, XMLStreamWriter writer) {
/*  73 */     LOGGER.entering(new Object[] { invocation });
/*  74 */     ProcessingStateChange resultingState = ProcessingStateChange.NO_CHANGE;
/*     */     try {
/*  76 */       switch (invocation.getMethodType()) {
/*     */         case WRITE_START_ELEMENT:
/*  78 */           if (this.filteringOn) {
/*  79 */             this.depth++; break;
/*     */           } 
/*  81 */           this.filteringOn = startFiltering(invocation, writer);
/*  82 */           if (this.filteringOn) {
/*  83 */             resultingState = ProcessingStateChange.START_FILTERING;
/*     */           }
/*     */           break;
/*     */         
/*     */         case WRITE_END_ELEMENT:
/*  88 */           if (this.filteringOn) {
/*  89 */             if (this.depth == 0) {
/*  90 */               this.filteringOn = false;
/*  91 */               resultingState = ProcessingStateChange.STOP_FILTERING;
/*     */               break;
/*     */             } 
/*  94 */             this.depth--;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case CLOSE:
/*  99 */           if (this.filteringOn) {
/* 100 */             this.filteringOn = false;
/* 101 */             resultingState = ProcessingStateChange.STOP_FILTERING;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 107 */       return resultingState;
/*     */     } finally {
/*     */       
/* 110 */       LOGGER.exiting(resultingState);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean startFiltering(Invocation invocation, XMLStreamWriter writer) {
/* 115 */     QName elementName = XmlFilteringUtils.getElementNameToWrite(invocation, XmlFilteringUtils.getDefaultNamespaceURI(writer));
/*     */     
/* 117 */     for (QName filteredElement : this.filteredElements) {
/* 118 */       if (filteredElement.equals(elementName)) {
/* 119 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 123 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\xmlfilter\PrivateElementFilteringStateMachine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */