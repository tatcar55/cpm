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
/*     */ public final class MexImportFilteringStateMachine
/*     */   implements FilteringStateMachine
/*     */ {
/*     */   private enum StateMachineMode
/*     */   {
/*  56 */     INACTIVE,
/*  57 */     BUFFERING,
/*  58 */     FILTERING;
/*     */   }
/*     */   
/*  61 */   private static final Logger LOGGER = Logger.getLogger(MexImportFilteringStateMachine.class);
/*     */   
/*     */   private static final String MEX_NAMESPACE = "http://schemas.xmlsoap.org/ws/2004/09/mex";
/*     */   private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
/*  65 */   private static final QName WSDL_IMPORT_ELEMENT = new QName("http://schemas.xmlsoap.org/wsdl/", "import");
/*  66 */   private static final QName IMPORT_NAMESPACE_ATTIBUTE = new QName("http://schemas.xmlsoap.org/wsdl/", "namespace");
/*     */   
/*     */   private int depth;
/*  69 */   private StateMachineMode currentMode = StateMachineMode.INACTIVE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingStateChange getStateChange(Invocation invocation, XMLStreamWriter writer) {
/*  77 */     LOGGER.entering(new Object[] { invocation });
/*  78 */     ProcessingStateChange resultingState = ProcessingStateChange.NO_CHANGE;
/*     */     try {
/*  80 */       switch (invocation.getMethodType()) {
/*     */         case WRITE_START_ELEMENT:
/*  82 */           if (this.currentMode == StateMachineMode.INACTIVE) {
/*  83 */             if (startBuffering(invocation, writer)) {
/*  84 */               resultingState = ProcessingStateChange.START_BUFFERING;
/*  85 */               this.currentMode = StateMachineMode.BUFFERING;
/*     */             }  break;
/*     */           } 
/*  88 */           this.depth++;
/*     */           break;
/*     */         
/*     */         case WRITE_END_ELEMENT:
/*  92 */           if (this.currentMode != StateMachineMode.INACTIVE) {
/*  93 */             if (this.depth == 0) {
/*  94 */               resultingState = (this.currentMode == StateMachineMode.BUFFERING) ? ProcessingStateChange.STOP_BUFFERING : ProcessingStateChange.STOP_FILTERING;
/*  95 */               this.currentMode = StateMachineMode.INACTIVE; break;
/*     */             } 
/*  97 */             this.depth--;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case WRITE_ATTRIBUTE:
/* 102 */           if (this.currentMode == StateMachineMode.BUFFERING && startFiltering(invocation, writer)) {
/* 103 */             resultingState = ProcessingStateChange.START_FILTERING;
/* 104 */             this.currentMode = StateMachineMode.FILTERING;
/*     */           } 
/*     */           break;
/*     */         case CLOSE:
/* 108 */           switch (this.currentMode) {
/*     */             case WRITE_START_ELEMENT:
/* 110 */               resultingState = ProcessingStateChange.STOP_BUFFERING; break;
/*     */             case WRITE_END_ELEMENT:
/* 112 */               resultingState = ProcessingStateChange.STOP_FILTERING; break;
/*     */           } 
/* 114 */           this.currentMode = StateMachineMode.INACTIVE;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 120 */       return resultingState;
/*     */     } finally {
/*     */       
/* 123 */       LOGGER.exiting(resultingState);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean startFiltering(Invocation invocation, XMLStreamWriter writer) {
/* 128 */     XmlFilteringUtils.AttributeInfo attributeInfo = XmlFilteringUtils.getAttributeNameToWrite(invocation, XmlFilteringUtils.getDefaultNamespaceURI(writer));
/* 129 */     return (IMPORT_NAMESPACE_ATTIBUTE.equals(attributeInfo.getName()) && "http://schemas.xmlsoap.org/ws/2004/09/mex".equals(attributeInfo.getValue()));
/*     */   }
/*     */   
/*     */   private boolean startBuffering(Invocation invocation, XMLStreamWriter writer) {
/* 133 */     QName elementName = XmlFilteringUtils.getElementNameToWrite(invocation, XmlFilteringUtils.getDefaultNamespaceURI(writer));
/* 134 */     return WSDL_IMPORT_ELEMENT.equals(elementName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\xmlfilter\MexImportFilteringStateMachine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */