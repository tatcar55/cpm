/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.util.ByteInputStream;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import com.sun.xml.rpc.sp.NamespaceSupport;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXReader
/*     */   extends XMLReaderBase
/*     */ {
/*     */   static final String XMLNS_NAMESPACE_URI = "http://www.w3.org/2000/xmlns/";
/*     */   private XMLStreamReader reader;
/*     */   private int state;
/*     */   private static final int CONTINUE = -10;
/*  73 */   private static final int[] stax2XMLReader = new int[16];
/*     */   static {
/*  75 */     for (int i = 0; i < 16; i++) {
/*  76 */       stax2XMLReader[i] = -10;
/*     */     }
/*  78 */     stax2XMLReader[7] = 0;
/*  79 */     stax2XMLReader[1] = 1;
/*  80 */     stax2XMLReader[2] = 2;
/*  81 */     stax2XMLReader[4] = 3;
/*  82 */     stax2XMLReader[3] = 4;
/*  83 */     stax2XMLReader[8] = 5;
/*     */   }
/*     */ 
/*     */   
/*     */   private QName currentName;
/*     */   private AttributesImpl currentAttributes;
/*     */   private ArrayList allPrefixes;
/*     */   private ElementIdStack elementIds;
/*     */   private int elementId;
/*     */   
/*     */   public StAXReader(InputSource source, boolean rejectDTDs) {
/*     */     try {
/*  95 */       this.reader = getInputFactory().createXMLStreamReader(source.getByteStream(), source.getEncoding());
/*     */       
/*  97 */       finishSetup();
/*  98 */     } catch (XMLStreamException e) {
/*  99 */       throw new XMLReaderException("staxreader.xmlstreamexception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StAXReader(InputSource source, boolean rejectDTDs, XMLStreamReader reader) {
/* 107 */     this.reader = reader;
/* 108 */     finishSetup();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StAXReader(Source source, boolean rejectDTDs) {
/*     */     try {
/* 116 */       boolean workingYet = false;
/* 117 */       if (workingYet) {
/* 118 */         this.reader = getInputFactory().createXMLStreamReader(source);
/*     */       } else {
/* 120 */         Transformer transformer = XmlUtil.newTransformer();
/* 121 */         ByteOutputStream bos = new ByteOutputStream();
/* 122 */         transformer.transform(source, new StreamResult((OutputStream)bos));
/* 123 */         ByteInputStream byteInputStream = new ByteInputStream(bos.getBytes(), bos.getCount());
/*     */         
/* 125 */         InputSource iSource = new InputSource((InputStream)byteInputStream);
/* 126 */         this.reader = getInputFactory().createXMLStreamReader(iSource.getByteStream(), iSource.getEncoding());
/*     */       } 
/*     */       
/* 129 */       finishSetup();
/* 130 */     } catch (XMLStreamException e) {
/* 131 */       throw new XMLReaderException("staxreader.xmlstreamexception", new LocalizableExceptionAdapter(e));
/*     */     }
/* 133 */     catch (TransformerException te) {
/*     */       
/* 135 */       throw new XMLReaderException("staxreader.xmlstreamexception", new LocalizableExceptionAdapter(te));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public StAXReader(StringReader stringReader, boolean rejectDTDs) {
/*     */     try {
/* 142 */       this.reader = getInputFactory().createXMLStreamReader(stringReader);
/* 143 */       finishSetup();
/* 144 */     } catch (XMLStreamException e) {
/* 145 */       throw new XMLReaderException("staxreader.xmlstreamexception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLInputFactory getInputFactory() throws XMLStreamException {
/* 156 */     XMLInputFactory inputFactory = XMLInputFactory.newInstance();
/*     */     
/* 158 */     inputFactory.setProperty("javax.xml.stream.isNamespaceAware", Boolean.TRUE);
/*     */     
/* 160 */     inputFactory.setProperty("javax.xml.stream.isCoalescing", Boolean.TRUE);
/*     */ 
/*     */     
/* 163 */     return inputFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void finishSetup() {
/* 171 */     this.state = stax2XMLReader[this.reader.getEventType()];
/*     */ 
/*     */     
/* 174 */     this.allPrefixes = new ArrayList();
/*     */     
/* 176 */     this.elementIds = new ElementIdStack();
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
/*     */   public XMLStreamReader getXMLStreamReader() {
/* 192 */     return this.reader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void synchronizeReader() {
/* 202 */     this.currentName = null;
/* 203 */     this.currentAttributes = null;
/* 204 */     this.state = stax2XMLReader[this.reader.getEventType()];
/*     */   }
/*     */   
/*     */   public int next() {
/* 208 */     if (this.state == 5) {
/* 209 */       return 5;
/*     */     }
/*     */     
/* 212 */     this.currentName = null;
/* 213 */     this.currentAttributes = null;
/*     */     
/*     */     try {
/*     */       do {
/* 217 */         this.state = stax2XMLReader[this.reader.next()];
/* 218 */       } while (this.state == -10);
/*     */       
/* 220 */       if (this.state == 1) {
/* 221 */         collectPrefixes();
/* 222 */         this.elementId = this.elementIds.pushNext();
/* 223 */       } else if (this.state == 2) {
/* 224 */         this.elementId = this.elementIds.pop();
/*     */       }
/*     */     
/* 227 */     } catch (XMLStreamException e) {
/* 228 */       throw new XMLReaderException("staxreader.xmlstreamexception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 233 */     return this.state;
/*     */   }
/*     */   
/*     */   public int getState() {
/* 237 */     return this.state;
/*     */   }
/*     */   
/*     */   public QName getName() {
/* 241 */     if (this.currentName == null) {
/* 242 */       this.currentName = this.reader.getName();
/*     */     }
/* 244 */     return this.currentName;
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 248 */     return this.reader.getLocalName();
/*     */   }
/*     */   
/*     */   public String getURI() {
/* 252 */     return this.reader.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public Attributes getAttributes() {
/* 256 */     if (this.currentAttributes == null) {
/* 257 */       if (this.reader.getEventType() == 1 || this.reader.getEventType() == 10) {
/*     */ 
/*     */         
/* 260 */         this.currentAttributes = new AttributesImpl(this.reader);
/*     */       } else {
/*     */         
/* 263 */         this.currentAttributes = new AttributesImpl(null);
/*     */       } 
/*     */     }
/* 266 */     return this.currentAttributes;
/*     */   }
/*     */   
/*     */   public String getURI(String prefix) {
/* 270 */     return this.reader.getNamespaceURI(prefix);
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 274 */     return this.reader.getText();
/*     */   }
/*     */   
/*     */   public int getLineNumber() {
/* 278 */     return this.reader.getLocation().getLineNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void collectPrefixes() {
/* 286 */     for (int i = 0; i < this.reader.getNamespaceCount(); i++) {
/* 287 */       String prefix = this.reader.getNamespacePrefix(i);
/* 288 */       if (prefix != null) {
/* 289 */         this.allPrefixes.add(prefix);
/*     */       }
/*     */     } 
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
/*     */   public Iterator getPrefixes() {
/* 304 */     Iterator<String> iter = this.allPrefixes.iterator();
/* 305 */     HashSet<String> set = new HashSet(this.allPrefixes.size());
/* 306 */     while (iter.hasNext()) {
/* 307 */       String prefix = iter.next();
/* 308 */       if (this.reader.getNamespaceURI(prefix) != null) {
/* 309 */         set.add(prefix);
/*     */       }
/*     */     } 
/* 312 */     return set.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getElementId() {
/* 317 */     return this.elementId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void skipElement(int id) {
/* 322 */     while (this.state != 5 && (this.state != 2 || this.elementId != id)) {
/* 323 */       next();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLReader recordElement() {
/* 332 */     return new RecordedXMLReader(this, new NamespaceContextWrapper(this.reader.getNamespaceContext()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 337 */     this.state = 5;
/*     */     try {
/* 339 */       this.reader.close();
/* 340 */     } catch (XMLStreamException e) {
/* 341 */       throw new XMLReaderException("staxreader.xmlstreamexception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printStreamConstants() {
/* 349 */     System.out.println(" ");
/* 350 */     System.out.println("XMLStreamConstants.START_ELEMENT: 1");
/*     */     
/* 352 */     System.out.println("XMLStreamConstants.END_ELEMENT: 2");
/*     */     
/* 354 */     System.out.println("XMLStreamConstants.PROCESSING_INSTRUCTION: 3");
/*     */     
/* 356 */     System.out.println("XMLStreamConstants.CHARACTERS: 4");
/*     */     
/* 358 */     System.out.println("XMLStreamConstants.COMMENT: 5");
/*     */     
/* 360 */     System.out.println("XMLStreamConstants.SPACE: 6");
/*     */     
/* 362 */     System.out.println("XMLStreamConstants.START_DOCUMENT: 7");
/*     */     
/* 364 */     System.out.println("XMLStreamConstants.END_DOCUMENT: 8");
/*     */     
/* 366 */     System.out.println("XMLStreamConstants.ENTITY_REFERENCE: 9");
/*     */     
/* 368 */     System.out.println("XMLStreamConstants.ATTRIBUTE: 10");
/*     */     
/* 370 */     System.out.println("XMLStreamConstants.DTD: 11");
/*     */     
/* 372 */     System.out.println("XMLStreamConstants.CDATA: 12");
/*     */     
/* 374 */     System.out.println("XMLStreamConstants.NAMESPACE: 13");
/*     */     
/* 376 */     System.out.println("XMLStreamConstants.NOTATION_DECLARATION: 14");
/*     */     
/* 378 */     System.out.println("XMLStreamConstants.ENTITY_DECLARATION: 15");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AttributesImpl
/*     */     implements Attributes
/*     */   {
/*     */     StAXReader.AttributeInfo[] atInfos;
/*     */ 
/*     */ 
/*     */     
/*     */     public AttributesImpl(XMLStreamReader reader) {
/* 392 */       if (reader == null) {
/*     */ 
/*     */ 
/*     */         
/* 396 */         this.atInfos = new StAXReader.AttributeInfo[0];
/*     */       }
/*     */       else {
/*     */         
/* 400 */         int index = 0;
/* 401 */         String namespacePrefix = null;
/* 402 */         int namespaceCount = reader.getNamespaceCount();
/* 403 */         int attributeCount = reader.getAttributeCount();
/* 404 */         this.atInfos = new StAXReader.AttributeInfo[namespaceCount + attributeCount]; int i;
/* 405 */         for (i = 0; i < namespaceCount; i++) {
/* 406 */           namespacePrefix = reader.getNamespacePrefix(i);
/*     */ 
/*     */           
/* 409 */           if (namespacePrefix == null) {
/* 410 */             namespacePrefix = "";
/*     */           }
/* 412 */           this.atInfos[index++] = new StAXReader.AttributeInfo(new QName("http://www.w3.org/2000/xmlns/", namespacePrefix, "xmlns"), reader.getNamespaceURI(i));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 418 */         for (i = 0; i < attributeCount; i++) {
/* 419 */           this.atInfos[index++] = new StAXReader.AttributeInfo(reader.getAttributeName(i), reader.getAttributeValue(i));
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getLength() {
/* 427 */       return this.atInfos.length;
/*     */     }
/*     */     
/*     */     public String getLocalName(int index) {
/* 431 */       if (index >= 0 && index < this.atInfos.length) {
/* 432 */         return this.atInfos[index].getLocalName();
/*     */       }
/* 434 */       return null;
/*     */     }
/*     */     
/*     */     public QName getName(int index) {
/* 438 */       if (index >= 0 && index < this.atInfos.length) {
/* 439 */         return this.atInfos[index].getName();
/*     */       }
/* 441 */       return null;
/*     */     }
/*     */     
/*     */     public String getPrefix(int index) {
/* 445 */       if (index >= 0 && index < this.atInfos.length) {
/* 446 */         return this.atInfos[index].getName().getPrefix();
/*     */       }
/* 448 */       return null;
/*     */     }
/*     */     
/*     */     public String getURI(int index) {
/* 452 */       if (index >= 0 && index < this.atInfos.length) {
/* 453 */         return this.atInfos[index].getName().getNamespaceURI();
/*     */       }
/* 455 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(int index) {
/* 459 */       if (index >= 0 && index < this.atInfos.length) {
/* 460 */         return this.atInfos[index].getValue();
/*     */       }
/* 462 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(QName name) {
/* 466 */       int index = getIndex(name);
/* 467 */       if (index != -1) {
/* 468 */         return this.atInfos[index].getValue();
/*     */       }
/* 470 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(String localName) {
/* 474 */       int index = getIndex(localName);
/* 475 */       if (index != -1) {
/* 476 */         return this.atInfos[index].getValue();
/*     */       }
/* 478 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(String uri, String localName) {
/* 482 */       int index = getIndex(uri, localName);
/* 483 */       if (index != -1) {
/* 484 */         return this.atInfos[index].getValue();
/*     */       }
/* 486 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isNamespaceDeclaration(int index) {
/* 490 */       if (index >= 0 && index < this.atInfos.length) {
/* 491 */         return this.atInfos[index].isNamespaceDeclaration();
/*     */       }
/* 493 */       return false;
/*     */     }
/*     */     
/*     */     public int getIndex(QName name) {
/* 497 */       for (int i = 0; i < this.atInfos.length; i++) {
/* 498 */         if (this.atInfos[i].getName().equals(name)) {
/* 499 */           return i;
/*     */         }
/*     */       } 
/* 502 */       return -1;
/*     */     }
/*     */     
/*     */     public int getIndex(String localName) {
/* 506 */       for (int i = 0; i < this.atInfos.length; i++) {
/* 507 */         if (this.atInfos[i].getName().getLocalPart().equals(localName)) {
/* 508 */           return i;
/*     */         }
/*     */       } 
/* 511 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIndex(String uri, String localName) {
/* 516 */       for (int i = 0; i < this.atInfos.length; i++) {
/* 517 */         QName qName = this.atInfos[i].getName();
/* 518 */         if (qName.getNamespaceURI().equals(uri) && qName.getLocalPart().equals(localName))
/*     */         {
/*     */           
/* 521 */           return i;
/*     */         }
/*     */       } 
/* 524 */       return -1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class AttributeInfo
/*     */   {
/*     */     private QName name;
/*     */     
/*     */     private String value;
/*     */     
/*     */     public AttributeInfo(QName name, String value) {
/* 536 */       this.name = name;
/* 537 */       if (value == null) {
/*     */         
/* 539 */         this.value = "";
/*     */       } else {
/* 541 */         this.value = value;
/*     */       } 
/*     */     }
/*     */     
/*     */     QName getName() {
/* 546 */       return this.name;
/*     */     }
/*     */     
/*     */     String getValue() {
/* 550 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String getLocalName() {
/* 557 */       if (isNamespaceDeclaration()) {
/* 558 */         if (this.name.getLocalPart().equals("")) {
/* 559 */           return "xmlns";
/*     */         }
/* 561 */         return "xmlns:" + this.name.getLocalPart();
/*     */       } 
/* 563 */       return this.name.getLocalPart();
/*     */     }
/*     */     
/*     */     boolean isNamespaceDeclaration() {
/* 567 */       return (this.name.getNamespaceURI() == "http://www.w3.org/2000/xmlns/");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class NamespaceContextWrapper
/*     */     extends NamespaceSupport
/*     */   {
/*     */     private NamespaceContext context;
/*     */ 
/*     */     
/*     */     public NamespaceContextWrapper(NamespaceContext context) {
/* 580 */       this.context = context;
/*     */     }
/*     */     
/*     */     public String getPrefix(String uri) {
/* 584 */       return this.context.getPrefix(uri);
/*     */     }
/*     */     
/*     */     public String getURI(String prefix) {
/* 588 */       return this.context.getNamespaceURI(prefix);
/*     */     }
/*     */     
/*     */     public Iterator getPrefixes(String uri) {
/* 592 */       return this.context.getPrefixes(uri);
/*     */     }
/*     */     
/*     */     public Iterator getPrefixes() {
/* 596 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\StAXReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */