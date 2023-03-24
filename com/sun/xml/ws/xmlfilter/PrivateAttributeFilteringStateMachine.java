/*     */ package com.sun.xml.ws.xmlfilter;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.policy.PolicyConstants;
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
/*     */ 
/*     */ public class PrivateAttributeFilteringStateMachine
/*     */   implements FilteringStateMachine
/*     */ {
/*  56 */   private static final Logger LOGGER = Logger.getLogger(PrivateAttributeFilteringStateMachine.class);
/*     */ 
/*     */   
/*     */   private int depth;
/*     */ 
/*     */   
/*     */   private boolean filteringOn;
/*     */ 
/*     */   
/*     */   private boolean cmdBufferingOn;
/*     */ 
/*     */   
/*     */   public ProcessingStateChange getStateChange(Invocation invocation, XMLStreamWriter writer) {
/*  69 */     LOGGER.entering(new Object[] { invocation });
/*  70 */     ProcessingStateChange resultingState = ProcessingStateChange.NO_CHANGE;
/*     */     try {
/*  72 */       switch (invocation.getMethodType()) {
/*     */         case WRITE_START_ELEMENT:
/*  74 */           if (this.filteringOn) {
/*  75 */             this.depth++; break;
/*  76 */           }  if (this.cmdBufferingOn) {
/*  77 */             resultingState = ProcessingStateChange.RESTART_BUFFERING; break;
/*     */           } 
/*  79 */           this.cmdBufferingOn = true;
/*  80 */           resultingState = ProcessingStateChange.START_BUFFERING;
/*     */           break;
/*     */         
/*     */         case WRITE_END_ELEMENT:
/*  84 */           if (this.filteringOn) {
/*  85 */             if (this.depth == 0) {
/*  86 */               this.filteringOn = false;
/*  87 */               resultingState = ProcessingStateChange.STOP_FILTERING; break;
/*     */             } 
/*  89 */             this.depth--; break;
/*     */           } 
/*  91 */           if (this.cmdBufferingOn) {
/*  92 */             this.cmdBufferingOn = false;
/*  93 */             resultingState = ProcessingStateChange.STOP_BUFFERING;
/*     */           } 
/*     */           break;
/*     */         case WRITE_ATTRIBUTE:
/*  97 */           if (!this.filteringOn && this.cmdBufferingOn && startFiltering(invocation, writer)) {
/*  98 */             this.filteringOn = true;
/*  99 */             this.cmdBufferingOn = false;
/* 100 */             resultingState = ProcessingStateChange.START_FILTERING;
/*     */           } 
/*     */           break;
/*     */         case CLOSE:
/* 104 */           if (this.filteringOn) {
/* 105 */             this.filteringOn = false;
/* 106 */             resultingState = ProcessingStateChange.STOP_FILTERING; break;
/* 107 */           }  if (this.cmdBufferingOn) {
/* 108 */             this.cmdBufferingOn = false;
/* 109 */             resultingState = ProcessingStateChange.STOP_BUFFERING;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 116 */       return resultingState;
/*     */     } finally {
/* 118 */       LOGGER.exiting(resultingState);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean startFiltering(Invocation invocation, XMLStreamWriter writer) {
/* 123 */     XmlFilteringUtils.AttributeInfo attributeInfo = XmlFilteringUtils.getAttributeNameToWrite(invocation, XmlFilteringUtils.getDefaultNamespaceURI(writer));
/* 124 */     return (PolicyConstants.VISIBILITY_ATTRIBUTE.equals(attributeInfo.getName()) && "private".equals(attributeInfo.getValue()));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\xmlfilter\PrivateAttributeFilteringStateMachine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */