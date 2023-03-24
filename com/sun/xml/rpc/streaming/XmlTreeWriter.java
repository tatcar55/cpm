/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ import com.sun.xml.rpc.sp.NamespaceSupport;
/*     */ import com.sun.xml.rpc.util.xml.CDATA;
/*     */ import java.util.Stack;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlTreeWriter
/*     */   extends XMLWriterBase
/*     */   implements XMLWriter
/*     */ {
/*     */   protected Document document;
/*     */   protected SOAPElement currentNode;
/*     */   protected SOAPElement parentNode;
/*     */   protected PrefixFactory pfactory;
/*  45 */   protected NamespaceSupport ns = new NamespaceSupport();
/*  46 */   protected Stack elementStack = new Stack();
/*     */   private static SOAPFactory soapFactory;
/*     */   
/*     */   public XmlTreeWriter(Document document) {
/*  50 */     this.document = document;
/*     */   }
/*     */   
/*     */   protected static SOAPFactory getSoapFactory() throws SOAPException {
/*  54 */     if (soapFactory == null) {
/*  55 */       soapFactory = SOAPFactory.newInstance();
/*     */     }
/*  57 */     return soapFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String localName, String uri) {
/*     */     try {
/*  65 */       this.ns.pushContext();
/*     */       
/*  67 */       boolean mustDeclarePrefix = false;
/*  68 */       String aPrefix = null;
/*  69 */       if (!uri.equals("")) {
/*     */         
/*  71 */         aPrefix = getKnownPrefix(uri);
/*     */         
/*  73 */         if (aPrefix == null) {
/*  74 */           mustDeclarePrefix = true;
/*  75 */           aPrefix = createPrefix(uri);
/*     */         } 
/*     */       } 
/*     */       
/*  79 */       addNewNode(localName, aPrefix, uri);
/*     */       
/*  81 */       if (mustDeclarePrefix) {
/*  82 */         writeNamespaceDeclaration(aPrefix, uri);
/*     */       }
/*  84 */       this.elementStack.push(this.currentNode.getElementName().getLocalName());
/*  85 */     } catch (Exception e) {
/*  86 */       throw new XmlTreeWriterException(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String createPrefix(String uri) {
/*  91 */     String aPrefix = null;
/*  92 */     if (this.pfactory != null) {
/*  93 */       aPrefix = this.pfactory.getPrefix(uri);
/*     */     }
/*     */     
/*  96 */     if (aPrefix == null) {
/*  97 */       throw new XmlTreeWriterException("xmlwriter.noPrefixForURI " + uri);
/*     */     }
/*  99 */     return aPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getKnownPrefix(String uri) {
/* 104 */     String aPrefix, defaultNamespaceURI = this.ns.getPrefix("");
/* 105 */     if (defaultNamespaceURI != null && uri.equals(defaultNamespaceURI)) {
/* 106 */       aPrefix = "";
/*     */     } else {
/* 108 */       aPrefix = this.ns.getPrefix(uri);
/*     */     } 
/*     */     
/* 111 */     return aPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SOAPElement addNewNode(String localName, String prefix, String uri) throws SOAPException {
/* 119 */     if (this.currentNode != null) {
/* 120 */       this.parentNode = this.currentNode;
/* 121 */       this.currentNode = this.parentNode.addChildElement(localName, prefix, uri);
/*     */     } else {
/* 123 */       this.currentNode = getSoapFactory().createElement(localName, prefix, uri);
/*     */       
/* 125 */       this.currentNode = (SOAPElement)this.document.importNode(this.currentNode, true);
/* 126 */       this.document.insertBefore(this.currentNode, null);
/*     */     } 
/*     */     
/* 129 */     return this.currentNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String localName, String uri, String prefix) {
/*     */     try {
/* 137 */       this.ns.pushContext();
/* 138 */       if (!uri.equals("")) {
/* 139 */         String aPrefix = null;
/* 140 */         boolean mustDeclarePrefix = false;
/*     */         
/* 142 */         String defaultNamespaceURI = this.ns.getPrefix("");
/* 143 */         if (defaultNamespaceURI != null && 
/* 144 */           uri.equals(defaultNamespaceURI)) {
/* 145 */           aPrefix = "";
/*     */         }
/*     */ 
/*     */         
/* 149 */         aPrefix = this.ns.getPrefix(uri);
/* 150 */         if (aPrefix == null) {
/* 151 */           mustDeclarePrefix = true;
/*     */           
/* 153 */           aPrefix = prefix;
/*     */           
/* 155 */           if (aPrefix == null) {
/* 156 */             throw new XmlTreeWriterException("xmlwriter.noPrefixForURI");
/*     */           }
/*     */         } 
/* 159 */         addNewNode(localName, aPrefix, uri);
/* 160 */         if (mustDeclarePrefix) {
/* 161 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } else {
/*     */         
/* 165 */         addNewNode(localName, prefix, uri);
/*     */       } 
/* 167 */       this.elementStack.push(this.currentNode.getElementName().getLocalName());
/* 168 */     } catch (SOAPException se) {
/* 169 */       throw new XmlTreeWriterException(se.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttribute(String localName, String uri, String value) {
/* 179 */     value = quote(value);
/* 180 */     writeAttributeUnquoted(localName, uri, value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String quote(String value) {
/* 185 */     boolean didReplacement = false;
/* 186 */     StringBuffer replacementString = null;
/*     */     int i;
/* 188 */     for (i = 0; i < value.length(); i++) {
/* 189 */       switch (value.charAt(i)) {
/*     */         
/*     */         case '"':
/* 192 */           replacementString = new StringBuffer(value.length() + 20);
/* 193 */           replacementString.append(value.substring(0, i));
/* 194 */           replacementString.append("&quot;");
/* 195 */           didReplacement = true;
/*     */           break;
/*     */         case '&':
/* 198 */           replacementString = new StringBuffer(value.length() + 20);
/* 199 */           replacementString.append(value.substring(0, i));
/* 200 */           replacementString.append("&amp;");
/* 201 */           didReplacement = true;
/*     */           break;
/*     */         case '<':
/* 204 */           replacementString = new StringBuffer(value.length() + 20);
/* 205 */           replacementString.append(value.substring(0, i));
/* 206 */           replacementString.append("&lt;");
/* 207 */           didReplacement = true;
/*     */           break;
/*     */         case '>':
/* 210 */           replacementString = new StringBuffer(value.length() + 20);
/* 211 */           replacementString.append(value.substring(0, i));
/* 212 */           replacementString.append("&gt;");
/* 213 */           didReplacement = true;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 218 */     if (!didReplacement) {
/* 219 */       return value;
/*     */     }
/*     */ 
/*     */     
/* 223 */     i++;
/*     */     
/* 225 */     for (; i < value.length(); i++) {
/* 226 */       char nextCharacter = value.charAt(i);
/* 227 */       switch (nextCharacter) {
/*     */         
/*     */         case '"':
/* 230 */           replacementString.append("&quot;");
/*     */           break;
/*     */         case '&':
/* 233 */           replacementString.append("&amp;");
/*     */           break;
/*     */         case '<':
/* 236 */           replacementString.append("&lt;");
/*     */           break;
/*     */         case '>':
/* 239 */           replacementString.append("&gt;");
/*     */           break;
/*     */         default:
/* 242 */           replacementString.append(nextCharacter);
/*     */           break;
/*     */       } 
/*     */     } 
/* 246 */     return replacementString.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttributeUnquoted(String localName, String uri, String value) {
/*     */     try {
/* 258 */       if (!uri.equals("")) {
/*     */         
/* 260 */         String aPrefix = null;
/* 261 */         boolean mustDeclarePrefix = false;
/*     */         
/* 263 */         String defaultNamespaceURI = this.ns.getPrefix("");
/* 264 */         if (defaultNamespaceURI != null && 
/* 265 */           uri.equals(defaultNamespaceURI)) {
/* 266 */           aPrefix = "";
/*     */         }
/*     */ 
/*     */         
/* 270 */         aPrefix = this.ns.getPrefix(uri);
/*     */         
/* 272 */         if (aPrefix == null) {
/* 273 */           mustDeclarePrefix = true;
/*     */           
/* 275 */           if (this.pfactory != null) {
/* 276 */             aPrefix = this.pfactory.getPrefix(uri);
/*     */           }
/*     */           
/* 279 */           if (aPrefix == null) {
/* 280 */             throw new XmlTreeWriterException("No Prefix For URI " + uri);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 285 */         this.currentNode.addAttribute(getSoapFactory().createName(localName, aPrefix, uri), value);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 292 */         if (mustDeclarePrefix) {
/* 293 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } else {
/*     */         
/* 297 */         this.currentNode.addAttribute(getSoapFactory().createName(localName), value);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 303 */     catch (SOAPException se) {
/* 304 */       throw new XmlTreeWriterException(se.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNamespaceDeclaration(String prefix, String uri) {
/* 314 */     this.ns.declarePrefix(prefix, uri);
/*     */     
/*     */     try {
/* 317 */       this.currentNode.addNamespaceDeclaration(prefix, uri);
/*     */     }
/* 319 */     catch (SOAPException se) {
/* 320 */       throw new XmlTreeWriterException(se.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNamespaceDeclaration(String uri) {
/* 329 */     if (this.pfactory == null) {
/* 330 */       throw new XmlTreeWriterException("No Prefix set for the XmlTreeWriter");
/*     */     }
/* 332 */     writeNamespaceDeclaration(this.pfactory.getPrefix(uri), uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(String chars) {
/* 342 */     writeCharsUnquoted(chars);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeCharsUnquoted(String chars) {
/*     */     try {
/* 350 */       this.currentNode.addTextNode(chars);
/*     */     }
/* 352 */     catch (SOAPException se) {
/* 353 */       throw new XmlTreeWriterException(se.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeCharsUnquoted(char[] buf, int offset, int len) {
/* 361 */     writeCharsUnquoted(new String(buf, offset, len));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement() {
/*     */     try {
/* 370 */       if (this.elementStack.size() >= 1) {
/* 371 */         this.elementStack.pop();
/* 372 */         this.currentNode = this.currentNode.getParentElement();
/* 373 */         this.ns.popContext();
/*     */       } else {
/* 375 */         throw new XmlTreeWriterException("All Elements have already closed");
/*     */       } 
/* 377 */     } catch (Exception e) {
/* 378 */       throw new XmlTreeWriterException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefixFactory getPrefixFactory() {
/* 386 */     return this.pfactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrefixFactory(PrefixFactory factory) {
/* 393 */     this.pfactory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI(String prefix) {
/* 402 */     return this.ns.getURI(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix(String uri) {
/* 412 */     return this.ns.getPrefix(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(CDATA chars) {
/*     */     try {
/* 429 */       this.currentNode.addTextNode(chars.getText());
/*     */     }
/* 431 */     catch (SOAPException se) {
/* 432 */       throw new XmlTreeWriterException(se.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XmlTreeWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */