/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ import com.sun.xml.rpc.sp.NamespaceSupport;
/*     */ import com.sun.xml.rpc.util.StructMap;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecordedXMLReader
/*     */   extends XMLReaderBase
/*     */ {
/*  45 */   protected static final QName EMPTY_QNAME = new QName("fooqname");
/*     */   int frameIndex;
/*     */   List frames;
/*     */   ReaderFrame currentFrame;
/*     */   NamespaceSupport originalNamespaces;
/*     */   NamespaceSupport namespaceSupport;
/*     */   boolean lastRetWasEnd;
/*     */   
/*     */   public RecordedXMLReader(XMLReader reader, NamespaceSupport namespaces) {
/*  54 */     this.frameIndex = 0;
/*  55 */     this.frames = new ArrayList();
/*     */     
/*  57 */     this.originalNamespaces = new NamespaceSupport(namespaces);
/*  58 */     this.namespaceSupport = new NamespaceSupport(namespaces);
/*  59 */     this.lastRetWasEnd = false;
/*     */     
/*  61 */     int targetElementId = reader.getElementId();
/*     */ 
/*     */     
/*  64 */     while (reader.getState() != 2 || reader.getElementId() != targetElementId) {
/*  65 */       recordFrame(reader);
/*  66 */       reader.next();
/*     */     } 
/*  68 */     recordFrame(reader);
/*     */     
/*  70 */     setFrame(0);
/*     */   }
/*     */   
/*     */   protected void recordFrame(XMLReader reader) {
/*  74 */     Attributes attributeFrame = null;
/*  75 */     switch (reader.getState()) {
/*     */       case 1:
/*  77 */         attributeFrame = new AttributeFrame(reader.getAttributes());
/*     */       case 2:
/*  79 */         addFrame(new ReaderFrame(reader.getState(), reader.getElementId(), reader.getLineNumber(), reader.getName(), attributeFrame));
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/*  91 */         addFrame(new ReaderFrame(reader.getState(), reader.getElementId(), reader.getLineNumber(), reader.getValue()));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addFrame(ReaderFrame frame) {
/* 103 */     this.frames.add(frame);
/*     */   }
/*     */   protected ReaderFrame getFrame(int index) {
/* 106 */     return this.frames.get(index);
/*     */   }
/*     */   protected void setFrame(int index) {
/* 109 */     this.currentFrame = getFrame(index);
/* 110 */     this.frameIndex = index;
/*     */   }
/*     */   protected void nextFrame() {
/* 113 */     setFrame(this.frameIndex + 1);
/*     */   }
/*     */   public void reset() {
/* 116 */     this.frameIndex = 0;
/* 117 */     this.lastRetWasEnd = false;
/*     */   }
/*     */   
/*     */   static class ReaderFrame {
/*     */     QName name;
/*     */     int state;
/*     */     Attributes attributes;
/*     */     String value;
/*     */     int elementId;
/*     */     int lineNumber;
/*     */     
/*     */     ReaderFrame(int state) {
/* 129 */       this.state = state;
/* 130 */       this.name = RecordedXMLReader.EMPTY_QNAME;
/* 131 */       this.attributes = null;
/* 132 */       this.value = null;
/* 133 */       this.elementId = -1;
/* 134 */       this.lineNumber = 0;
/*     */     }
/*     */     ReaderFrame(int state, int elementId, int lineNumber) {
/* 137 */       this(state);
/* 138 */       this.elementId = elementId;
/* 139 */       this.lineNumber = lineNumber;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ReaderFrame(int state, int elementId, int lineNumber, QName name, Attributes attributes) {
/* 147 */       this(state, elementId, lineNumber);
/* 148 */       this.name = name;
/* 149 */       this.attributes = attributes;
/*     */     }
/*     */     ReaderFrame(int state, int elementId, int lineNumber, String value) {
/* 152 */       this(state, elementId, lineNumber);
/* 153 */       this.value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   static class AttributeFrame
/*     */     implements Attributes
/*     */   {
/*     */     private static final String XMLNS_NAMESPACE_URI = "http://www.w3.org/2000/xmlns/";
/* 161 */     StructMap recordedAttributes = new StructMap();
/* 162 */     List qnames = null;
/* 163 */     List qnameLocalParts = null;
/* 164 */     List values = null;
/*     */     
/*     */     AttributeFrame(Attributes attributes) {
/* 167 */       for (int i = 0; i < attributes.getLength(); i++) {
/* 168 */         this.recordedAttributes.put(attributes.getName(i), attributes.getValue(i));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     List getQNames() {
/* 175 */       if (this.qnames == null) {
/* 176 */         this.qnames = (List)this.recordedAttributes.keys();
/*     */       }
/*     */       
/* 179 */       return this.qnames;
/*     */     }
/*     */ 
/*     */     
/*     */     List getQNameLocalParts() {
/* 184 */       List tempQNames = new ArrayList();
/*     */       
/* 186 */       if (this.qnames == null) {
/* 187 */         this.qnames = (List)this.recordedAttributes.keys();
/*     */       }
/*     */       
/* 190 */       if (this.qnameLocalParts == null) {
/* 191 */         this.qnameLocalParts = new ArrayList();
/*     */       }
/* 193 */       for (int i = 0; i < this.qnames.size(); i++) {
/* 194 */         QName qname = this.qnames.get(i);
/* 195 */         this.qnameLocalParts.add(qname.getLocalPart());
/*     */       } 
/* 197 */       return this.qnameLocalParts;
/*     */     }
/*     */     
/*     */     List getValues() {
/* 201 */       if (this.values == null) {
/* 202 */         this.values = (List)this.recordedAttributes.values();
/*     */       }
/*     */       
/* 205 */       return this.values;
/*     */     }
/*     */     public int getIndex(QName name) {
/* 208 */       List<E> qnames = getQNames();
/*     */       
/* 210 */       for (int i = 0; i < qnames.size(); i++) {
/* 211 */         if (qnames.get(i).equals(name)) {
/* 212 */           return i;
/*     */         }
/*     */       } 
/*     */       
/* 216 */       return -1;
/*     */     }
/*     */     
/*     */     public int getIndex(String uri, String localName) {
/* 220 */       List<QName> qnames = getQNames();
/*     */       
/* 222 */       for (int i = 0; i < qnames.size(); i++) {
/* 223 */         QName qname = qnames.get(i);
/* 224 */         if (qname.getNamespaceURI().equals(uri) && qname.getLocalPart().equals(localName))
/*     */         {
/* 226 */           return i;
/*     */         }
/*     */       } 
/*     */       
/* 230 */       return -1;
/*     */     }
/*     */     
/*     */     public int getIndex(String localName) {
/* 234 */       List<QName> qnames = getQNames();
/*     */       
/* 236 */       for (int i = 0; i < qnames.size(); i++) {
/* 237 */         QName qname = qnames.get(i);
/* 238 */         if (qname.getLocalPart().equals(localName)) {
/* 239 */           return i;
/*     */         }
/*     */       } 
/*     */       
/* 243 */       return -1;
/*     */     }
/*     */     
/*     */     public int getLength() {
/* 247 */       return this.recordedAttributes.size();
/*     */     }
/*     */     
/*     */     public String getLocalName(int index) {
/* 251 */       return getName(index).getLocalPart();
/*     */     }
/*     */     
/*     */     public QName getName(int index) {
/* 255 */       List<QName> qnames = getQNames();
/*     */       
/* 257 */       return qnames.get(index);
/*     */     }
/*     */     
/*     */     public String getPrefix(int index) {
/* 261 */       QName qname = getName(index);
/*     */       
/* 263 */       return XmlUtil.getPrefix(qname.getNamespaceURI());
/*     */     }
/*     */     
/*     */     public String getURI(int index) {
/* 267 */       return getName(index).getNamespaceURI();
/*     */     }
/*     */     
/*     */     public String getValue(int index) {
/* 271 */       if (index == -1) {
/* 272 */         return null;
/*     */       }
/*     */       
/* 275 */       List<String> values = getValues();
/*     */       
/* 277 */       return values.get(index);
/*     */     }
/*     */     
/*     */     public String getValue(QName name) {
/* 281 */       return getValue(getIndex(name));
/*     */     }
/*     */     
/*     */     public String getValue(String uri, String localName) {
/* 285 */       return getValue(getIndex(uri, localName));
/*     */     }
/*     */     
/*     */     public String getValue(String localName) {
/* 289 */       return getValue(getIndex(localName));
/*     */     }
/*     */     
/*     */     public boolean isNamespaceDeclaration(int index) {
/* 293 */       return (getURI(index) == "http://www.w3.org/2000/xmlns/");
/*     */     }
/*     */   }
/*     */   
/*     */   public void close() {
/* 298 */     reset();
/*     */   }
/*     */   
/*     */   public int getState() {
/* 302 */     return this.currentFrame.state;
/*     */   }
/*     */   
/*     */   public QName getName() {
/* 306 */     return this.currentFrame.name;
/*     */   }
/*     */   
/*     */   public String getURI() {
/* 310 */     return getName().getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 314 */     return getName().getLocalPart();
/*     */   }
/*     */   
/*     */   public Attributes getAttributes() {
/* 318 */     return this.currentFrame.attributes;
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 322 */     return this.currentFrame.value;
/*     */   }
/*     */   
/*     */   public int getElementId() {
/* 326 */     return this.currentFrame.elementId;
/*     */   }
/*     */   
/*     */   public int getLineNumber() {
/* 330 */     return this.currentFrame.lineNumber;
/*     */   }
/*     */   
/*     */   public String getURI(String prefix) {
/* 334 */     return this.namespaceSupport.getURI(prefix);
/*     */   }
/*     */   
/*     */   public Iterator getPrefixes() {
/* 338 */     return this.namespaceSupport.getPrefixes();
/*     */   }
/*     */   
/*     */   public int next() {
/* 342 */     if (this.frameIndex + 1 >= this.frames.size() - 1)
/*     */     {
/* 344 */       return 5;
/*     */     }
/* 346 */     nextFrame();
/* 347 */     int ret = getState();
/*     */     
/* 349 */     if (this.lastRetWasEnd) {
/* 350 */       this.namespaceSupport.popContext();
/* 351 */       this.lastRetWasEnd = false;
/*     */     } 
/*     */     
/* 354 */     if (ret == 1) {
/* 355 */       this.namespaceSupport.pushContext();
/* 356 */       Attributes attributes = getAttributes();
/* 357 */       for (int i = 0; i < attributes.getLength(); i++) {
/* 358 */         if (attributes.isNamespaceDeclaration(i)) {
/* 359 */           String prefix = attributes.getLocalName(i);
/* 360 */           String value = attributes.getValue(i);
/* 361 */           this.namespaceSupport.declarePrefix(prefix, value);
/*     */         } 
/*     */       } 
/* 364 */     } else if (ret == 2) {
/* 365 */       this.lastRetWasEnd = true;
/*     */     } 
/*     */     
/* 368 */     return ret;
/*     */   }
/*     */   
/*     */   public XMLReader recordElement() {
/* 372 */     return new RecordedXMLReader(this, this.namespaceSupport);
/*     */   }
/*     */   
/*     */   public void skipElement(int elementId) {
/* 376 */     while (this.currentFrame.state != 5 && (this.currentFrame.state != 2 || this.currentFrame.elementId != elementId)) {
/*     */ 
/*     */       
/* 379 */       if (next() == 5)
/*     */         return; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\RecordedXMLReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */