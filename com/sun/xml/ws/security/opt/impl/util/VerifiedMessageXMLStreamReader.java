/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
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
/*     */ public class VerifiedMessageXMLStreamReader
/*     */   implements XMLStreamReader
/*     */ {
/*  56 */   private XMLStreamReader reader = null;
/*  57 */   private NamespaceContext _nsCtx = null;
/*     */ 
/*     */   
/*     */   public VerifiedMessageXMLStreamReader(XMLStreamReader reader, Map<String, String> bodyENVNS) {
/*  61 */     this.reader = reader;
/*  62 */     this._nsCtx = new InternalNamespaceContext(reader.getNamespaceContext(), bodyENVNS);
/*     */   }
/*     */   
/*     */   public int getAttributeCount() {
/*  66 */     return this.reader.getAttributeCount();
/*     */   }
/*     */   
/*     */   public int getEventType() {
/*  70 */     return this.reader.getEventType();
/*     */   }
/*     */   
/*     */   public int getNamespaceCount() {
/*  74 */     return this.reader.getNamespaceCount();
/*     */   }
/*     */   
/*     */   public int getTextLength() {
/*  78 */     return this.reader.getTextLength();
/*     */   }
/*     */   
/*     */   public int getTextStart() {
/*  82 */     return this.reader.getTextStart();
/*     */   }
/*     */   
/*     */   public int next() throws XMLStreamException {
/*  86 */     return this.reader.next();
/*     */   }
/*     */   
/*     */   public int nextTag() throws XMLStreamException {
/*  90 */     return this.reader.nextTag();
/*     */   }
/*     */   
/*     */   public void close() throws XMLStreamException {
/*  94 */     this.reader.close();
/*     */   }
/*     */   
/*     */   public boolean hasName() {
/*  98 */     return this.reader.hasName();
/*     */   }
/*     */   
/*     */   public boolean hasNext() throws XMLStreamException {
/* 102 */     return this.reader.hasNext();
/*     */   }
/*     */   
/*     */   public boolean hasText() {
/* 106 */     return this.reader.hasText();
/*     */   }
/*     */   
/*     */   public boolean isCharacters() {
/* 110 */     return this.reader.isCharacters();
/*     */   }
/*     */   
/*     */   public boolean isEndElement() {
/* 114 */     return this.reader.isEndElement();
/*     */   }
/*     */   
/*     */   public boolean isStandalone() {
/* 118 */     return this.reader.isStandalone();
/*     */   }
/*     */   
/*     */   public boolean isStartElement() {
/* 122 */     return this.reader.isStartElement();
/*     */   }
/*     */   
/*     */   public boolean isWhiteSpace() {
/* 126 */     return this.reader.isWhiteSpace();
/*     */   }
/*     */   
/*     */   public boolean standaloneSet() {
/* 130 */     return this.reader.standaloneSet();
/*     */   }
/*     */   
/*     */   public char[] getTextCharacters() {
/* 134 */     return this.reader.getTextCharacters();
/*     */   }
/*     */   
/*     */   public boolean isAttributeSpecified(int i) {
/* 138 */     return this.reader.isAttributeSpecified(i);
/*     */   }
/*     */   
/*     */   public int getTextCharacters(int i, char[] c, int i0, int i1) throws XMLStreamException {
/* 142 */     return this.reader.getTextCharacters(i, c, i0, i1);
/*     */   }
/*     */   
/*     */   public String getCharacterEncodingScheme() {
/* 146 */     return this.reader.getCharacterEncodingScheme();
/*     */   }
/*     */   
/*     */   public String getElementText() throws XMLStreamException {
/* 150 */     return this.reader.getElementText();
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/* 154 */     return this.reader.getEncoding();
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 158 */     return this.reader.getLocalName();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 162 */     return this.reader.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getPIData() {
/* 166 */     return this.reader.getPIData();
/*     */   }
/*     */   
/*     */   public String getPITarget() {
/* 170 */     return this.reader.getPITarget();
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 174 */     return this.reader.getPrefix();
/*     */   }
/*     */   
/*     */   public String getText() {
/* 178 */     return this.reader.getText();
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 182 */     return this.reader.getVersion();
/*     */   }
/*     */   
/*     */   public String getAttributeLocalName(int i) {
/* 186 */     return this.reader.getAttributeLocalName(i);
/*     */   }
/*     */   
/*     */   public String getAttributeNamespace(int i) {
/* 190 */     return this.reader.getAttributeNamespace(i);
/*     */   }
/*     */   
/*     */   public String getAttributePrefix(int i) {
/* 194 */     return this.reader.getAttributePrefix(i);
/*     */   }
/*     */   
/*     */   public String getAttributeType(int i) {
/* 198 */     return this.reader.getAttributeType(i);
/*     */   }
/*     */   
/*     */   public String getAttributeValue(int i) {
/* 202 */     return this.reader.getAttributeValue(i);
/*     */   }
/*     */   
/*     */   public String getNamespacePrefix(int i) {
/* 206 */     return this.reader.getNamespacePrefix(i);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(int i) {
/* 210 */     return this.reader.getNamespaceURI(i);
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 214 */     return this._nsCtx;
/*     */   }
/*     */   
/*     */   public QName getName() {
/* 218 */     return this.reader.getName();
/*     */   }
/*     */   
/*     */   public QName getAttributeName(int i) {
/* 222 */     return this.reader.getAttributeName(i);
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 226 */     return this.reader.getLocation();
/*     */   }
/*     */   
/*     */   public Object getProperty(String string) throws IllegalArgumentException {
/* 230 */     return this.reader.getProperty(string);
/*     */   }
/*     */   
/*     */   public void require(int i, String string, String string0) throws XMLStreamException {
/* 234 */     this.reader.require(i, string, string0);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(String string) {
/* 238 */     return this.reader.getNamespaceURI(string);
/*     */   }
/*     */   
/*     */   public String getAttributeValue(String string, String string0) {
/* 242 */     return this.reader.getAttributeValue(string, string0);
/*     */   }
/*     */   
/*     */   private static final class InternalNamespaceContext
/*     */     implements NamespaceContext {
/*     */     private NamespaceContext parent;
/*     */     private Map<String, String> bodyEnvNs;
/*     */     
/*     */     public InternalNamespaceContext(NamespaceContext parent, Map<String, String> bodyEnvNs) {
/* 251 */       this.parent = parent;
/* 252 */       this.bodyEnvNs = bodyEnvNs;
/*     */     }
/*     */     
/*     */     public String getNamespaceURI(String prefix) {
/* 256 */       String nsUri = this.parent.getNamespaceURI(prefix);
/* 257 */       if (nsUri == null || nsUri == "") {
/* 258 */         nsUri = this.bodyEnvNs.get(prefix);
/*     */       }
/* 260 */       return nsUri;
/*     */     }
/*     */     
/*     */     public String getPrefix(String namespaceURI) {
/* 264 */       if (namespaceURI == null) {
/* 265 */         return null;
/*     */       }
/* 267 */       String prefix = this.parent.getPrefix(namespaceURI);
/* 268 */       if (prefix == null) {
/* 269 */         Iterator<String> it = this.bodyEnvNs.keySet().iterator();
/* 270 */         while (it.hasNext()) {
/* 271 */           String nextKey = it.next();
/* 272 */           if (namespaceURI.equals(this.bodyEnvNs.get(nextKey))) {
/* 273 */             return nextKey;
/*     */           }
/*     */         } 
/*     */       } 
/* 277 */       return prefix;
/*     */     }
/*     */     
/*     */     public Iterator getPrefixes(String namespaceURI) {
/* 281 */       return new VerifiedMessageXMLStreamReader.InternalIterator(this.parent.getPrefixes(namespaceURI), this.bodyEnvNs, namespaceURI);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class InternalIterator implements Iterator {
/* 286 */     ArrayList<String> arr = new ArrayList<String>();
/* 287 */     Iterator internal = null;
/*     */     public InternalIterator(Iterator<String> parent, Map<String, String> bodyEnvNs, String namespaceURI) {
/* 289 */       while (parent.hasNext()) {
/* 290 */         this.arr.add(parent.next());
/*     */       }
/* 292 */       if (namespaceURI != null) {
/* 293 */         Iterator<Map.Entry<String, String>> it = bodyEnvNs.entrySet().iterator();
/* 294 */         while (it.hasNext()) {
/* 295 */           Map.Entry<String, String> entry = it.next();
/* 296 */           if (namespaceURI.equals(entry.getValue())) {
/* 297 */             this.arr.add(entry.getKey());
/*     */           }
/*     */         } 
/*     */       } 
/* 301 */       this.internal = this.arr.iterator();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 305 */       return this.internal.hasNext();
/*     */     }
/*     */     
/*     */     public Object next() {
/* 309 */       return this.internal.next();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 313 */       throw new UnsupportedOperationException("Remove Not Supported");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\VerifiedMessageXMLStreamReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */