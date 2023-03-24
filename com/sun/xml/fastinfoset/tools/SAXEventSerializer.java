/*     */ package com.sun.xml.fastinfoset.tools;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXEventSerializer
/*     */   extends DefaultHandler
/*     */   implements LexicalHandler
/*     */ {
/*     */   private Writer _writer;
/*     */   private boolean _charactersAreCDATA;
/*     */   private StringBuffer _characters;
/*  41 */   private Stack _namespaceStack = new Stack();
/*     */   protected List _namespaceAttributes;
/*     */   
/*     */   public SAXEventSerializer(OutputStream s) throws IOException {
/*  45 */     this._writer = new OutputStreamWriter(s);
/*  46 */     this._charactersAreCDATA = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/*     */     try {
/*  53 */       this._writer.write("<sax xmlns=\"http://www.sun.com/xml/sax-events\">\n");
/*  54 */       this._writer.write("<startDocument/>\n");
/*  55 */       this._writer.flush();
/*     */     }
/*  57 */     catch (IOException e) {
/*  58 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/*     */     try {
/*  64 */       this._writer.write("<endDocument/>\n");
/*  65 */       this._writer.write("</sax>");
/*  66 */       this._writer.flush();
/*  67 */       this._writer.close();
/*     */     }
/*  69 */     catch (IOException e) {
/*  70 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/*  78 */     if (this._namespaceAttributes == null) {
/*  79 */       this._namespaceAttributes = new ArrayList();
/*     */     }
/*     */     
/*  82 */     String qName = (prefix.length() == 0) ? "xmlns" : ("xmlns" + prefix);
/*  83 */     AttributeValueHolder attribute = new AttributeValueHolder(qName, prefix, uri, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     this._namespaceAttributes.add(attribute);
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
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/*     */     try {
/* 114 */       outputCharacters();
/*     */       
/* 116 */       if (this._namespaceAttributes != null) {
/*     */         
/* 118 */         AttributeValueHolder[] arrayOfAttributeValueHolder = new AttributeValueHolder[0];
/* 119 */         arrayOfAttributeValueHolder = (AttributeValueHolder[])this._namespaceAttributes.toArray((Object[])arrayOfAttributeValueHolder);
/*     */ 
/*     */         
/* 122 */         quicksort(arrayOfAttributeValueHolder, 0, arrayOfAttributeValueHolder.length - 1);
/*     */         
/* 124 */         for (int k = 0; k < arrayOfAttributeValueHolder.length; k++) {
/* 125 */           this._writer.write("<startPrefixMapping prefix=\"" + (arrayOfAttributeValueHolder[k]).localName + "\" uri=\"" + (arrayOfAttributeValueHolder[k]).uri + "\"/>\n");
/*     */           
/* 127 */           this._writer.flush();
/*     */         } 
/*     */         
/* 130 */         this._namespaceStack.push(arrayOfAttributeValueHolder);
/* 131 */         this._namespaceAttributes = null;
/*     */       } else {
/* 133 */         this._namespaceStack.push(null);
/*     */       } 
/*     */       
/* 136 */       AttributeValueHolder[] attrsHolder = new AttributeValueHolder[attributes.getLength()];
/*     */       
/* 138 */       for (int i = 0; i < attributes.getLength(); i++) {
/* 139 */         attrsHolder[i] = new AttributeValueHolder(attributes.getQName(i), attributes.getLocalName(i), attributes.getURI(i), attributes.getType(i), attributes.getValue(i));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       quicksort(attrsHolder, 0, attrsHolder.length - 1);
/*     */       
/* 150 */       int attributeCount = 0; int j;
/* 151 */       for (j = 0; j < attrsHolder.length; j++) {
/* 152 */         if (!(attrsHolder[j]).uri.equals("http://www.w3.org/2000/xmlns/"))
/*     */         {
/*     */ 
/*     */           
/* 156 */           attributeCount++;
/*     */         }
/*     */       } 
/* 159 */       if (attributeCount == 0) {
/* 160 */         this._writer.write("<startElement uri=\"" + uri + "\" localName=\"" + localName + "\" qName=\"" + qName + "\"/>\n");
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 166 */       this._writer.write("<startElement uri=\"" + uri + "\" localName=\"" + localName + "\" qName=\"" + qName + "\">\n");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 171 */       for (j = 0; j < attrsHolder.length; j++) {
/* 172 */         if (!(attrsHolder[j]).uri.equals("http://www.w3.org/2000/xmlns/"))
/*     */         {
/*     */ 
/*     */           
/* 176 */           this._writer.write("  <attribute qName=\"" + (attrsHolder[j]).qName + "\" localName=\"" + (attrsHolder[j]).localName + "\" uri=\"" + (attrsHolder[j]).uri + "\" value=\"" + (attrsHolder[j]).value + "\"/>\n");
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 185 */       this._writer.write("</startElement>\n");
/* 186 */       this._writer.flush();
/*     */     }
/* 188 */     catch (IOException e) {
/* 189 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/*     */     try {
/* 197 */       outputCharacters();
/*     */       
/* 199 */       this._writer.write("<endElement uri=\"" + uri + "\" localName=\"" + localName + "\" qName=\"" + qName + "\"/>\n");
/*     */ 
/*     */       
/* 202 */       this._writer.flush();
/*     */ 
/*     */ 
/*     */       
/* 206 */       AttributeValueHolder[] attrsHolder = this._namespaceStack.pop();
/* 207 */       if (attrsHolder != null) {
/* 208 */         for (int i = 0; i < attrsHolder.length; i++) {
/* 209 */           this._writer.write("<endPrefixMapping prefix=\"" + (attrsHolder[i]).localName + "\"/>\n");
/*     */           
/* 211 */           this._writer.flush();
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 216 */     catch (IOException e) {
/* 217 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 224 */     if (length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 228 */     if (this._characters == null) {
/* 229 */       this._characters = new StringBuffer();
/*     */     }
/*     */ 
/*     */     
/* 233 */     this._characters.append(ch, start, length);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void outputCharacters() throws SAXException {
/* 251 */     if (this._characters == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 256 */       this._writer.write("<characters>" + (this._charactersAreCDATA ? "<![CDATA[" : "") + this._characters + (this._charactersAreCDATA ? "]]>" : "") + "</characters>\n");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 261 */       this._writer.flush();
/*     */       
/* 263 */       this._characters = null;
/* 264 */     } catch (IOException e) {
/* 265 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 273 */     characters(ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/*     */     try {
/* 280 */       outputCharacters();
/*     */       
/* 282 */       this._writer.write("<processingInstruction target=\"" + target + "\" data=\"" + data + "\"/>\n");
/*     */       
/* 284 */       this._writer.flush();
/*     */     }
/* 286 */     catch (IOException e) {
/* 287 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDTD() throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startCDATA() throws SAXException {
/* 315 */     this._charactersAreCDATA = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {
/* 320 */     this._charactersAreCDATA = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 327 */       outputCharacters();
/*     */       
/* 329 */       this._writer.write("<comment>" + new String(ch, start, length) + "</comment>\n");
/*     */ 
/*     */       
/* 332 */       this._writer.flush();
/*     */     }
/* 334 */     catch (IOException e) {
/* 335 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void quicksort(AttributeValueHolder[] attrs, int p, int r) {
/* 342 */     while (p < r) {
/* 343 */       int q = partition(attrs, p, r);
/* 344 */       quicksort(attrs, p, q);
/* 345 */       p = q + 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int partition(AttributeValueHolder[] attrs, int p, int r) {
/* 350 */     AttributeValueHolder x = attrs[p + r >>> 1];
/* 351 */     int i = p - 1;
/* 352 */     int j = r + 1;
/*     */     while (true) {
/* 354 */       if (x.compareTo(attrs[--j]) < 0)
/* 355 */         continue;  while (x.compareTo(attrs[++i]) > 0);
/* 356 */       if (i < j) {
/* 357 */         AttributeValueHolder t = attrs[i];
/* 358 */         attrs[i] = attrs[j];
/* 359 */         attrs[j] = t; continue;
/*     */       }  break;
/*     */     } 
/* 362 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class AttributeValueHolder
/*     */     implements Comparable
/*     */   {
/*     */     public final String qName;
/*     */     
/*     */     public final String localName;
/*     */     
/*     */     public final String uri;
/*     */     
/*     */     public final String type;
/*     */     
/*     */     public final String value;
/*     */     
/*     */     public AttributeValueHolder(String qName, String localName, String uri, String type, String value) {
/* 380 */       this.qName = qName;
/* 381 */       this.localName = localName;
/* 382 */       this.uri = uri;
/* 383 */       this.type = type;
/* 384 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int compareTo(Object o) {
/*     */       try {
/* 389 */         return this.qName.compareTo(((AttributeValueHolder)o).qName);
/*     */       }
/* 391 */       catch (Exception e) {
/* 392 */         throw new RuntimeException(CommonResourceBundle.getInstance().getString("message.AttributeValueHolderExpected"));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\SAXEventSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */