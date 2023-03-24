/*     */ package com.sun.xml.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import java.io.IOException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XmlOutputAbstractImpl
/*     */   implements XmlOutput
/*     */ {
/*     */   protected int[] nsUriIndex2prefixIndex;
/*     */   protected NamespaceContextImpl nsContext;
/*     */   protected XMLSerializer serializer;
/*     */   
/*     */   public void startDocument(XMLSerializer serializer, boolean fragment, int[] nsUriIndex2prefixIndex, NamespaceContextImpl nsContext) throws IOException, SAXException, XMLStreamException {
/*  76 */     this.nsUriIndex2prefixIndex = nsUriIndex2prefixIndex;
/*  77 */     this.nsContext = nsContext;
/*  78 */     this.serializer = serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument(boolean fragment) throws IOException, SAXException, XMLStreamException {
/*  88 */     this.serializer = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginStartTag(Name name) throws IOException, XMLStreamException {
/* 102 */     beginStartTag(this.nsUriIndex2prefixIndex[name.nsUriIndex], name.localName);
/*     */   }
/*     */   
/*     */   public abstract void beginStartTag(int paramInt, String paramString) throws IOException, XMLStreamException;
/*     */   
/*     */   public void attribute(Name name, String value) throws IOException, XMLStreamException {
/* 108 */     short idx = name.nsUriIndex;
/* 109 */     if (idx == -1) {
/* 110 */       attribute(-1, name.localName, value);
/*     */     } else {
/* 112 */       attribute(this.nsUriIndex2prefixIndex[idx], name.localName, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void attribute(int paramInt, String paramString1, String paramString2) throws IOException, XMLStreamException;
/*     */ 
/*     */   
/*     */   public abstract void endStartTag() throws IOException, SAXException;
/*     */ 
/*     */   
/*     */   public void endTag(Name name) throws IOException, SAXException, XMLStreamException {
/* 124 */     endTag(this.nsUriIndex2prefixIndex[name.nsUriIndex], name.localName);
/*     */   }
/*     */   
/*     */   public abstract void endTag(int paramInt, String paramString) throws IOException, SAXException, XMLStreamException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\XmlOutputAbstractImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */