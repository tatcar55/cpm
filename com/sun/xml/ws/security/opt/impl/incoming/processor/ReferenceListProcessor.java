/*     */ package com.sun.xml.ws.security.opt.impl.incoming.processor;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import java.util.ArrayList;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceListProcessor
/*     */ {
/*  58 */   ArrayList<String> refList = null;
/*     */   
/*  60 */   EncryptionPolicy.FeatureBinding fb = null;
/*     */ 
/*     */   
/*     */   public ReferenceListProcessor(EncryptionPolicy encPolicy) {
/*  64 */     this.fb = (EncryptionPolicy.FeatureBinding)encPolicy.getFeatureBinding();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(XMLStreamReader reader) throws XMLStreamException {
/*  72 */     this.refList = new ArrayList<String>(2);
/*  73 */     if (StreamUtil.moveToNextStartOREndElement(reader)) {
/*  74 */       while (reader.getEventType() != 8) {
/*  75 */         if (reader.getEventType() == 1 && 
/*  76 */           reader.getLocalName() == "DataReference" && reader.getNamespaceURI() == "http://www.w3.org/2001/04/xmlenc#") {
/*  77 */           String uri = reader.getAttributeValue(null, "URI");
/*  78 */           if (uri.startsWith("#")) {
/*  79 */             this.refList.add(uri.substring(1));
/*     */           } else {
/*  81 */             this.refList.add(uri);
/*     */           } 
/*     */           
/*  84 */           Target target = new Target("uri", uri);
/*  85 */           EncryptionTarget encTarget = new EncryptionTarget(target);
/*  86 */           this.fb.addTargetBinding(encTarget);
/*     */         } 
/*     */         
/*  89 */         if (_exit(reader)) {
/*     */           break;
/*     */         }
/*  92 */         reader.next();
/*     */         
/*  94 */         if (_exit(reader)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public ArrayList<String> getReferences() {
/* 102 */     return this.refList;
/*     */   }
/*     */   
/*     */   public boolean _exit(XMLStreamReader reader) {
/* 106 */     if (reader.getEventType() == 2 && 
/* 107 */       reader.getLocalName() == "ReferenceList" && reader.getNamespaceURI() == "http://www.w3.org/2001/04/xmlenc#") {
/* 108 */       return true;
/*     */     }
/*     */     
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\processor\ReferenceListProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */