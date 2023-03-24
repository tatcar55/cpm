/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlTreeReader
/*     */   extends XMLReaderBase
/*     */ {
/*     */   protected SOAPElement root;
/*     */   protected SOAPElement currentNode;
/*     */   protected int state;
/*     */   protected QName name;
/*     */   protected ElementIdStack elementIds;
/*     */   protected int elementId;
/*     */   protected String currentValue;
/*  53 */   protected AttributesAdapter attributes = new AttributesAdapter();
/*     */   
/*     */   public XmlTreeReader(SOAPElement root) {
/*  56 */     this.elementIds = new ElementIdStack();
/*  57 */     setRoot(root);
/*     */   }
/*     */   
/*     */   private void setRoot(SOAPElement root) {
/*  61 */     this.root = root;
/*  62 */     this.state = 0;
/*     */   }
/*     */   
/*     */   public void close() {
/*  66 */     this.state = 5;
/*     */   }
/*     */   
/*     */   public Attributes getAttributes() {
/*  70 */     this.attributes.initialize();
/*  71 */     return this.attributes;
/*     */   }
/*     */   
/*     */   public int getElementId() {
/*  75 */     return this.elementId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/*  80 */     return 0;
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/*  84 */     return this.currentNode.getElementName().getLocalName();
/*     */   }
/*     */ 
/*     */   
/*     */   public Element getCurrentNode() {
/*  89 */     return this.currentNode;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  93 */     if (this.name == null) {
/*  94 */       this.name = new QName(getURI(), getLocalName());
/*     */     }
/*  96 */     return this.name;
/*     */   }
/*     */   
/*     */   public Iterator getPrefixes() {
/* 100 */     return this.currentNode.getVisibleNamespacePrefixes();
/*     */   }
/*     */   
/*     */   public int getState() {
/* 104 */     return this.state;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getURI() {
/* 109 */     return this.currentNode.getElementName().getURI();
/*     */   }
/*     */   
/*     */   public String getURI(String prefix) {
/* 113 */     return this.currentNode.getNamespaceURI(prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 118 */     this.currentNode.normalize();
/* 119 */     return this.currentNode.getValue();
/*     */   }
/*     */   
/*     */   public int next() {
/* 123 */     if (this.state == 5) {
/* 124 */       return 5;
/*     */     }
/* 126 */     this.name = null;
/* 127 */     this.attributes.unintialize();
/*     */     
/* 129 */     parse();
/* 130 */     switch (this.state) {
/*     */       case 1:
/* 132 */         this.elementId = this.elementIds.pushNext();
/*     */       
/*     */       case 2:
/* 135 */         this.elementId = this.elementIds.pop();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/* 146 */         return this.state;
/*     */     } 
/*     */     throw new XMLReaderException("xmlreader.illegalStateEncountered", Integer.toString(this.state)); } public void parse() {
/*     */     Node first;
/*     */     Node nextNode;
/* 151 */     switch (this.state) {
/*     */       case 0:
/* 153 */         this.currentNode = this.root;
/* 154 */         this.state = 1;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 159 */         this.currentNode.normalize();
/* 160 */         first = this.currentNode.getFirstChild();
/* 161 */         if (first != null) {
/* 162 */           if (first instanceof javax.xml.soap.Text) {
/* 163 */             Node sec = first.getNextSibling();
/* 164 */             if (sec != null) {
/*     */               
/* 166 */               this.currentNode = (SOAPElement)sec;
/* 167 */               this.state = 1;
/*     */             } else {
/* 169 */               this.state = 3;
/* 170 */               this.currentValue = this.currentNode.getValue();
/* 171 */               if (this.currentValue == null) {
/* 172 */                 this.state = 2;
/*     */               }
/*     */             } 
/* 175 */           } else if (first instanceof SOAPElement) {
/* 176 */             this.state = 1;
/* 177 */             this.currentNode = (SOAPElement)first;
/*     */           } else {
/* 179 */             throw new XMLReaderException("xmlreader.illegalType " + first.getClass());
/*     */           } 
/*     */         } else {
/*     */           
/* 183 */           this.state = 2;
/*     */         } 
/*     */ 
/*     */       
/*     */       case 2:
/* 188 */         nextNode = this.currentNode.getNextSibling();
/* 189 */         if (nextNode != null && nextNode instanceof javax.xml.soap.Text)
/*     */         {
/* 191 */           nextNode = nextNode.getNextSibling();
/*     */         }
/* 193 */         if (nextNode == null) {
/*     */           
/* 195 */           if (this.currentNode == this.root) {
/* 196 */             this.state = 5;
/*     */           } else {
/* 198 */             this.state = 2;
/* 199 */             this.currentNode = this.currentNode.getParentElement();
/*     */           } 
/*     */         } else {
/* 202 */           this.state = 1;
/* 203 */           this.currentNode = (SOAPElement)nextNode;
/*     */         } 
/*     */ 
/*     */       
/*     */       case 5:
/*     */       case 4:
/*     */         return;
/*     */       
/*     */       case 3:
/* 212 */         this.state = 2;
/*     */     } 
/*     */     
/* 215 */     throw new XMLReaderException("xmlreader.illegalStateEncountered", Integer.toString(this.state));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLReader recordElement() {
/* 222 */     this.state = 2;
/* 223 */     return new XmlTreeReader(this.currentNode);
/*     */   }
/*     */   
/*     */   public void skipElement(int elementId) {
/* 227 */     if (this.state == 5)
/*     */       return; 
/* 229 */     while (this.elementId != elementId) {
/* 230 */       this.currentNode = this.currentNode.getParentElement();
/* 231 */       this.elementId = this.elementIds.pop();
/*     */     } 
/* 233 */     this.state = 2;
/*     */   }
/*     */   
/*     */   public class AttributesAdapter
/*     */     implements Attributes
/*     */   {
/*     */     static final String XMLNS_NAMESPACE_URI = "http://www.w3.org/2000/xmlns/";
/*     */     private boolean initialized = false;
/* 241 */     ArrayList attributeUris = new ArrayList();
/* 242 */     ArrayList attributePrefixes = new ArrayList();
/* 243 */     ArrayList attributeNames = new ArrayList();
/* 244 */     ArrayList attributeValues = new ArrayList();
/* 245 */     ArrayList attributeQNames = new ArrayList();
/*     */     
/*     */     void unintialize() {
/* 248 */       if (this.initialized) {
/* 249 */         this.attributeUris.clear();
/* 250 */         this.attributePrefixes.clear();
/* 251 */         this.attributeNames.clear();
/* 252 */         this.attributeValues.clear();
/* 253 */         this.attributeQNames.clear();
/* 254 */         this.initialized = false;
/*     */       } 
/*     */     }
/*     */     void initialize() {
/* 258 */       if (!this.initialized) {
/* 259 */         Iterator<Name> eachAttribute = XmlTreeReader.this.currentNode.getAllAttributes();
/* 260 */         while (eachAttribute.hasNext()) {
/* 261 */           Name name = eachAttribute.next();
/* 262 */           this.attributeUris.add(name.getURI());
/* 263 */           this.attributePrefixes.add(name.getPrefix());
/* 264 */           this.attributeNames.add(name.getLocalName());
/* 265 */           this.attributeValues.add(XmlTreeReader.this.currentNode.getAttributeValue(name));
/* 266 */           this.attributeQNames.add(null);
/*     */         } 
/* 268 */         this.initialized = true;
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getLength() {
/* 273 */       initialize();
/* 274 */       return this.attributeValues.size();
/*     */     }
/*     */     
/*     */     public boolean isNamespaceDeclaration(int index) {
/* 278 */       initialize();
/* 279 */       return "http://www.w3.org/2000/xmlns/".equals(getURI(index));
/*     */     }
/*     */     
/*     */     public QName getName(int index) {
/* 283 */       initialize();
/* 284 */       if (this.attributeQNames.get(index) == null) {
/* 285 */         QName qname = new QName(getURI(index), getLocalName(index));
/* 286 */         this.attributeQNames.set(index, qname);
/*     */       } 
/* 288 */       return this.attributeQNames.get(index);
/*     */     }
/*     */     
/*     */     public String getURI(int index) {
/* 292 */       initialize();
/* 293 */       return this.attributeUris.get(index);
/*     */     }
/*     */     
/*     */     public String getLocalName(int index) {
/* 297 */       initialize();
/* 298 */       return this.attributeNames.get(index);
/*     */     }
/*     */     
/*     */     public String getPrefix(int index) {
/* 302 */       initialize();
/* 303 */       String prefix = this.attributePrefixes.get(index);
/*     */       
/* 305 */       if (prefix != null && prefix.equals("")) {
/* 306 */         prefix = null;
/*     */       }
/* 308 */       return prefix;
/*     */     }
/*     */     
/*     */     public String getValue(int index) {
/* 312 */       initialize();
/* 313 */       return this.attributeValues.get(index);
/*     */     }
/*     */     
/*     */     public int getIndex(QName name) {
/* 317 */       return getIndex(name.getNamespaceURI(), name.getLocalPart());
/*     */     }
/*     */     
/*     */     public int getIndex(String uri, String localName) {
/* 321 */       initialize();
/*     */       
/* 323 */       for (int index = 0; index < this.attributeNames.size(); index++) {
/* 324 */         if (this.attributeUris.get(index).equals(uri) && this.attributeNames.get(index).equals(localName))
/*     */         {
/* 326 */           return index;
/*     */         }
/*     */       } 
/*     */       
/* 330 */       return -1;
/*     */     }
/*     */     
/*     */     public int getIndex(String localName) {
/* 334 */       initialize();
/*     */       
/* 336 */       for (int index = 0; index < this.attributeNames.size(); index++) {
/* 337 */         if (this.attributeNames.get(index).equals(localName)) {
/* 338 */           return index;
/*     */         }
/*     */       } 
/*     */       
/* 342 */       return -1;
/*     */     }
/*     */     
/*     */     public String getValue(QName name) {
/* 346 */       int index = getIndex(name);
/* 347 */       if (index != -1) {
/* 348 */         return this.attributeValues.get(index);
/*     */       }
/* 350 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(String uri, String localName) {
/* 354 */       int index = getIndex(uri, localName);
/* 355 */       if (index != -1) {
/* 356 */         return this.attributeValues.get(index);
/*     */       }
/* 358 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(String localName) {
/* 362 */       int index = getIndex(localName);
/* 363 */       if (index != -1) {
/* 364 */         return this.attributeValues.get(index);
/*     */       }
/* 366 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XmlTreeReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */