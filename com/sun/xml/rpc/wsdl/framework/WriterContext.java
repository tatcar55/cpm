/*     */ package com.sun.xml.rpc.wsdl.framework;
/*     */ 
/*     */ import com.sun.xml.rpc.sp.NamespaceSupport;
/*     */ import com.sun.xml.rpc.util.xml.PrettyPrintingXmlWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WriterContext
/*     */ {
/*     */   private PrettyPrintingXmlWriter _writer;
/*     */   private NamespaceSupport _nsSupport;
/*     */   private String _targetNamespaceURI;
/*     */   private int _newPrefixCount;
/*     */   private List _pendingNamespaceDeclarations;
/*     */   
/*     */   public WriterContext(OutputStream os) throws IOException {
/*  49 */     this._writer = new PrettyPrintingXmlWriter(os);
/*  50 */     this._nsSupport = new NamespaceSupport();
/*  51 */     this._newPrefixCount = 2;
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/*  55 */     this._writer.flush();
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/*  59 */     this._writer.close();
/*     */   }
/*     */   
/*     */   public void push() {
/*  63 */     if (this._pendingNamespaceDeclarations != null) {
/*  64 */       throw new IllegalStateException("prefix declarations are pending");
/*     */     }
/*  66 */     this._nsSupport.pushContext();
/*     */   }
/*     */   
/*     */   public void pop() {
/*  70 */     this._nsSupport.popContext();
/*  71 */     this._pendingNamespaceDeclarations = null;
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/*  75 */     return this._nsSupport.getURI(prefix);
/*     */   }
/*     */   
/*     */   public Iterator getPrefixes() {
/*  79 */     return this._nsSupport.getPrefixes();
/*     */   }
/*     */   
/*     */   public String getDefaultNamespaceURI() {
/*  83 */     return getNamespaceURI("");
/*     */   }
/*     */   
/*     */   public void declarePrefix(String prefix, String uri) {
/*  87 */     this._nsSupport.declarePrefix(prefix, uri);
/*  88 */     if (this._pendingNamespaceDeclarations == null) {
/*  89 */       this._pendingNamespaceDeclarations = new ArrayList();
/*     */     }
/*  91 */     this._pendingNamespaceDeclarations.add(new String[] { prefix, uri });
/*     */   }
/*     */   
/*     */   public String getPrefixFor(String uri) {
/*  95 */     if (getDefaultNamespaceURI().equals(uri)) {
/*  96 */       return "";
/*     */     }
/*  98 */     return this._nsSupport.getPrefix(uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public String findNewPrefix(String base) {
/* 103 */     return base + Integer.toString(this._newPrefixCount++);
/*     */   }
/*     */   
/*     */   public String getTargetNamespaceURI() {
/* 107 */     return this._targetNamespaceURI;
/*     */   }
/*     */   
/*     */   public void setTargetNamespaceURI(String uri) {
/* 111 */     this._targetNamespaceURI = uri;
/*     */   }
/*     */   
/*     */   public void writeStartTag(QName name) throws IOException {
/* 115 */     this._writer.start(getQNameString(name));
/*     */   }
/*     */   
/*     */   public void writeEndTag(QName name) throws IOException {
/* 119 */     this._writer.end(getQNameString(name));
/*     */   }
/*     */   
/*     */   public void writeAttribute(String name, String value) throws IOException {
/* 123 */     if (value != null) {
/* 124 */       this._writer.attribute(name, value);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeAttribute(String name, QName value) throws IOException {
/* 129 */     if (value != null) {
/* 130 */       this._writer.attribute(name, getQNameString(value));
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeAttribute(String name, boolean value) throws IOException {
/* 135 */     writeAttribute(name, value ? "true" : "false");
/*     */   }
/*     */   
/*     */   public void writeAttribute(String name, Boolean value) throws IOException {
/* 139 */     if (value != null) {
/* 140 */       writeAttribute(name, value.booleanValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeAttribute(String name, int value) throws IOException {
/* 145 */     writeAttribute(name, Integer.toString(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String name, Object value, Map valueToXmlMap) throws IOException {
/* 150 */     String actualValue = (String)valueToXmlMap.get(value);
/* 151 */     writeAttribute(name, actualValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeNamespaceDeclaration(String prefix, String uri) throws IOException {
/* 156 */     this._writer.attribute(getNamespaceDeclarationAttributeName(prefix), uri);
/*     */   }
/*     */   
/*     */   public void writeAllPendingNamespaceDeclarations() throws IOException {
/* 160 */     if (this._pendingNamespaceDeclarations != null) {
/* 161 */       Iterator<String[]> iter = this._pendingNamespaceDeclarations.iterator();
/* 162 */       while (iter.hasNext()) {
/*     */         
/* 164 */         String[] pair = iter.next();
/* 165 */         writeNamespaceDeclaration(pair[0], pair[1]);
/*     */       } 
/*     */     } 
/* 168 */     this._pendingNamespaceDeclarations = null;
/*     */   }
/*     */   
/*     */   private String getNamespaceDeclarationAttributeName(String prefix) {
/* 172 */     if (prefix.equals("")) {
/* 173 */       return "xmlns";
/*     */     }
/* 175 */     return "xmlns:" + prefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTag(QName name, String value) throws IOException {
/* 180 */     this._writer.leaf(getQNameString(name), value);
/*     */   }
/*     */   
/*     */   public String getQNameString(QName name) {
/* 184 */     String prefix = getPrefixFor(name.getNamespaceURI());
/* 185 */     if (prefix == null)
/* 186 */       throw new IllegalArgumentException(); 
/* 187 */     if (prefix.equals("")) {
/* 188 */       return name.getLocalPart();
/*     */     }
/* 190 */     return prefix + ":" + name.getLocalPart();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getQNameStringWithTargetNamespaceCheck(QName name) {
/* 195 */     if (name.getNamespaceURI().equals(this._targetNamespaceURI)) {
/* 196 */       return name.getLocalPart();
/*     */     }
/* 198 */     return getQNameString(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\WriterContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */