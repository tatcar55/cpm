/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.PolicyBuilder;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.ReferenceListProcessor;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ public class ReferenceListHeader
/*     */   implements SecurityHeaderElement, SecurityElementWriter, PolicyBuilder
/*     */ {
/*     */   private static final int DATA_REFERENCE_ELEMENT = 1;
/*  67 */   private String id = "";
/*  68 */   private String namespaceURI = "";
/*  69 */   private String localName = "";
/*  70 */   private JAXBFilterProcessingContext pc = null;
/*  71 */   private ArrayList<String> referenceList = null;
/*  72 */   private ArrayList<String> pendingRefList = null;
/*     */   
/*  74 */   private EncryptionPolicy encPolicy = null;
/*     */ 
/*     */   
/*     */   public ReferenceListHeader(XMLStreamReader reader, JAXBFilterProcessingContext pc) throws XMLStreamException {
/*  78 */     this.pc = pc;
/*  79 */     this.encPolicy = new EncryptionPolicy();
/*  80 */     this.encPolicy.setFeatureBinding((MLSPolicy)new EncryptionPolicy.FeatureBinding());
/*  81 */     process(reader);
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/*  85 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  89 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  93 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/*  97 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 101 */     return this.localName;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 105 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 109 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 113 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List<String> getReferenceList() {
/* 117 */     return this.referenceList;
/*     */   }
/*     */   
/*     */   public List<String> getPendingReferenceList() {
/* 121 */     return this.pendingRefList;
/*     */   }
/*     */   
/*     */   private void process(XMLStreamReader reader) throws XMLStreamException {
/* 125 */     this.id = reader.getAttributeValue(null, "Id");
/* 126 */     this.namespaceURI = reader.getNamespaceURI();
/* 127 */     this.localName = reader.getLocalName();
/*     */     
/* 129 */     ReferenceListProcessor rlp = new ReferenceListProcessor(this.encPolicy);
/* 130 */     rlp.process(reader);
/* 131 */     this.referenceList = rlp.getReferences();
/* 132 */     this.pendingRefList = (ArrayList<String>)this.referenceList.clone();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 136 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public WSSPolicy getPolicy() {
/* 140 */     return (WSSPolicy)this.encPolicy;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\ReferenceListHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */