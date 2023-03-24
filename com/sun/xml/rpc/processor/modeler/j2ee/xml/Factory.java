/*     */ package com.sun.xml.rpc.processor.modeler.j2ee.xml;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
/*     */ import com.sun.org.apache.xerces.internal.parsers.DOMParser;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Factory
/*     */   implements Serializable
/*     */ {
/*     */   protected String xmlFile;
/*     */   protected String packageName;
/*     */   protected Document document;
/*     */   protected String encoding;
/*     */   protected String encodingTag;
/*     */   protected String dtdFileName;
/*     */   protected String dtdPublicId;
/*     */   protected String xsdFileName;
/*     */   protected String xsdNamespaceURI;
/*  79 */   protected Hashtable importedFileHashtable = new Hashtable<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String rootElementName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPackageName(String packageName) {
/*  90 */     this.packageName = packageName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPackageName() {
/*  97 */     return this.packageName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLFilename(String filename) {
/* 104 */     this.xmlFile = filename;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXMLFilename() {
/* 111 */     return this.xmlFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncoding(String encoding) {
/* 119 */     this.encoding = encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 126 */     return this.encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncodingTag(String encodingTag) {
/* 134 */     this.encodingTag = encodingTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncodingTag(String encodingTag) {
/* 141 */     return encodingTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDTDFileName(String dtdFilename) {
/* 149 */     this.dtdFileName = dtdFilename;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDTDFileName() {
/* 156 */     return this.dtdFileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPublicId(String publicId) {
/* 163 */     this.dtdPublicId = publicId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/* 170 */     return this.dtdPublicId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXSDFileName(String xsdFilename) {
/* 178 */     this.xsdFileName = xsdFilename;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXSDFileName() {
/* 185 */     return this.xsdFileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNamespaceURI(String namespaceURI) {
/* 192 */     this.xsdNamespaceURI = namespaceURI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/* 199 */     return this.xsdNamespaceURI;
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
/*     */   public void addImportedFileInfo(String filename, String nsprefix, String nsURI) {
/* 212 */     this.importedFileHashtable.put(nsprefix, nsURI + " " + filename);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseType loadDocument(String classname, String xmlFile) {
/* 219 */     return loadDocument(classname, xmlFile, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseType loadDocument(String classname, String xmlFile, boolean validate) {
/* 227 */     System.out.print("[Factory] ==> classname = " + classname);
/* 228 */     System.out.print("[Factory] ==> xmlFile = " + xmlFile);
/* 229 */     System.out.print("[Factory] ==> validate = " + validate);
/*     */     
/* 231 */     this.xmlFile = xmlFile;
/* 232 */     DOMParser parser = new DOMParser();
/*     */     try {
/* 234 */       if (validate) {
/* 235 */         parser.setEntityResolver(new EntityResolverRI());
/* 236 */         parser.setFeature("http://xml.org/sax/features/validation", true);
/*     */       }
/*     */       else {
/*     */         
/* 240 */         parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
/*     */       } 
/*     */       
/* 243 */       parser.setFeature("http://apache.org/xml/features/validation/schema", true);
/*     */ 
/*     */ 
/*     */       
/* 247 */       parser.setErrorHandler(new ErrorHandlerRI());
/* 248 */       parser.parse(xmlFile);
/* 249 */       this.document = parser.getDocument();
/* 250 */       Element rootElement = this.document.getDocumentElement();
/*     */       
/* 252 */       if (_trace) {
/* 253 */         printElement(rootElement);
/*     */       }
/* 255 */       return newInstance(rootElement, classname);
/* 256 */     } catch (Exception e) {
/* 257 */       System.out.println("Exception: Factory::loadDocument() " + e);
/* 258 */       e.printStackTrace();
/* 259 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseType loadDocument(String classname, InputSource source) {
/* 267 */     return loadDocument(classname, source, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseType loadDocument(String classname, InputSource source, boolean validate) {
/* 275 */     DOMParser parser = new DOMParser();
/*     */     try {
/* 277 */       if (validate) {
/* 278 */         parser.setEntityResolver(new EntityResolverRI());
/* 279 */         parser.setFeature("http://xml.org/sax/features/validation", true);
/*     */       }
/*     */       else {
/*     */         
/* 283 */         parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
/*     */       } 
/*     */       
/* 286 */       parser.setFeature("http://apache.org/xml/features/validation/schema", true);
/*     */ 
/*     */ 
/*     */       
/* 290 */       parser.setErrorHandler(new ErrorHandlerRI());
/* 291 */       parser.parse(source);
/* 292 */       this.document = parser.getDocument();
/* 293 */       Element rootElement = this.document.getDocumentElement();
/*     */ 
/*     */       
/* 296 */       if (_trace) {
/* 297 */         printElement(rootElement);
/*     */       }
/* 299 */       return newInstance(rootElement, classname);
/* 300 */     } catch (Exception e) {
/* 301 */       System.out.println("Exception: Factory::loadDocument() " + e);
/* 302 */       e.printStackTrace();
/* 303 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseType loadDocument(String classname, Element element) {
/*     */     try {
/* 312 */       ComplexType domElement = (ComplexType)newInstance(element, classname);
/*     */       
/* 314 */       return domElement;
/* 315 */     } catch (Exception e) {
/* 316 */       System.out.println("Exception: Factory::loadDocument() " + e);
/* 317 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(String filename) {
/* 325 */     String outputEncoding = (this.encoding != null && !this.encoding.equals("")) ? this.encoding : "UTF8";
/*     */     
/* 327 */     new DOMWriter(this.document, filename, outputEncoding, this.encodingTag, getDocTypeString());
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
/*     */   public void print(PrintWriter writer, String prefix) {
/* 340 */     new DOMWriter(this.document, writer, this.encodingTag, getDocTypeString(), prefix);
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
/*     */   public void save() {
/* 352 */     if (this.xmlFile != null) {
/* 353 */       String outputEncoding = (this.encoding != null && !this.encoding.equals("")) ? this.encoding : "UTF8";
/*     */       
/* 355 */       new DOMWriter(this.document, this.xmlFile, outputEncoding, this.encodingTag, getDocTypeString());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComplexType createRootDOMFromComplexType(String classname, String rootElementName) {
/* 374 */     return (ComplexType)createRootDOMHelper(classname, rootElementName);
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
/*     */   public SimpleType createRootDOMFromSimpleType(String classname, String rootElementName) {
/* 387 */     return (SimpleType)createRootDOMHelper(classname, rootElementName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object createRootDOMHelper(String classname, String rootElementName) {
/* 393 */     this.document = new DocumentImpl();
/* 394 */     this.rootElementName = rootElementName;
/*     */     
/* 396 */     Element childElement = this.document.createElement(rootElementName);
/* 397 */     this.document.appendChild(childElement);
/*     */     
/* 399 */     if (this.xsdFileName != null && !this.xsdFileName.equals("")) {
/*     */       
/* 401 */       childElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 406 */       if (this.xsdNamespaceURI != null && !this.xsdNamespaceURI.equals("")) {
/* 407 */         int index = rootElementName.indexOf(":");
/* 408 */         if (index > 0) {
/*     */           
/* 410 */           String prefix = ":" + rootElementName.substring(0, index);
/* 411 */           childElement.setAttribute("xmlns" + prefix, this.xsdNamespaceURI);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 416 */           childElement.setAttribute("xmlns", this.xsdNamespaceURI);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 423 */         Enumeration enums = this.importedFileHashtable.keys();
/* 424 */         String importedFileValidation = "";
/* 425 */         while (enums.hasMoreElements()) {
/* 426 */           Object nsprefix = enums.nextElement();
/* 427 */           String nsURI = (String)this.importedFileHashtable.get(nsprefix);
/* 428 */           int blank = nsURI.indexOf(' ');
/* 429 */           childElement.setAttribute("xmlns:" + nsprefix, nsURI.substring(0, blank));
/*     */ 
/*     */           
/* 432 */           importedFileValidation = importedFileValidation + " " + nsURI;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 438 */         childElement.setAttribute("xsi:schemaLocation", this.xsdNamespaceURI + " " + this.xsdFileName + importedFileValidation);
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 445 */         childElement.setAttribute("xsi:noNamespaceSchemaLocation", this.xsdFileName);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 451 */     return newInstance(childElement, classname);
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
/*     */   public ComplexType createDOMElementFromComplexType(String classname, String elementName) {
/* 464 */     Element childElement = this.document.createElement(elementName);
/* 465 */     return (ComplexType)newInstance(childElement, classname);
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
/*     */   public SimpleType createDOMElementFromSimpleType(String classname, String elementName) {
/* 478 */     Element childElement = this.document.createElement(elementName);
/* 479 */     return (SimpleType)newInstance(childElement, classname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BaseType newInstance(Node node, String className) {
/*     */     try {
/* 489 */       String fullname = className;
/* 490 */       if (this.packageName != null && !this.packageName.equals("")) {
/* 491 */         fullname = this.packageName + "." + className;
/*     */       }
/*     */ 
/*     */       
/* 495 */       Class<?> javaClass = Class.forName(fullname);
/* 496 */       BaseType object = (BaseType)javaClass.newInstance();
/*     */ 
/*     */ 
/*     */       
/* 500 */       if (node instanceof Element) {
/* 501 */         object.setXMLElement((Element)node);
/*     */       } else {
/* 503 */         object.setXMLAttribute((Attr)node);
/*     */       } 
/*     */       
/* 506 */       object.setFactory(this);
/*     */       
/* 508 */       return object;
/* 509 */     } catch (Exception e) {
/* 510 */       System.out.println("Factory::newInstance() error ***" + e);
/* 511 */       return null;
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
/*     */   protected Element createXMLElementAndText(String elementName, String elementValue) {
/* 523 */     Element item = this.document.createElement(elementName);
/* 524 */     Text text = this.document.createTextNode(elementValue);
/* 525 */     item.appendChild(text);
/* 526 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Attr createAttribute(String name, Element parentElement) {
/* 533 */     Attr attrNode = this.document.createAttribute(name);
/* 534 */     parentElement.setAttributeNode(attrNode);
/* 535 */     return attrNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Text createText(Element parentElement, String value) {
/* 542 */     Text textNode = this.document.createTextNode(value);
/* 543 */     parentElement.appendChild(textNode);
/* 544 */     return textNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getDocTypeString() {
/* 551 */     String docType = "";
/* 552 */     if (this.dtdFileName != null && !this.dtdFileName.equals("")) {
/* 553 */       if (this.dtdPublicId == null || this.dtdPublicId.equals(""))
/*     */       {
/* 555 */         this.dtdPublicId = this.rootElementName + "Id";
/*     */       }
/*     */       
/* 558 */       docType = "<!DOCTYPE " + this.rootElementName + " PUBLIC \"" + this.dtdPublicId + "\"" + " \"" + this.dtdFileName + "\"" + ">";
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 569 */     return docType;
/*     */   }
/*     */   
/*     */   private static void printElement(Element elem) {
/* 573 */     printElement("", elem);
/*     */   }
/*     */   
/*     */   private static void printElement(String prefix, Element elem) {
/* 577 */     String newPrefix = prefix + "    ";
/* 578 */     System.out.println(prefix + " tagName: " + elem.getTagName() + " namespaceURI: " + elem.getNamespaceURI() + " localName: " + elem.getLocalName() + " node name " + elem.getNodeName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 588 */     NamedNodeMap attrs = elem.getAttributes();
/* 589 */     if (attrs != null) {
/* 590 */       System.out.println("  There are " + attrs.getLength() + " attributes");
/*     */       
/* 592 */       for (int j = 0; j < attrs.getLength(); j++) {
/* 593 */         Attr attr = (Attr)attrs.item(j);
/* 594 */         System.out.println(prefix + "  " + attr.getName() + "=" + attr.getValue());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 599 */     NodeList children = elem.getChildNodes();
/* 600 */     int len = children.getLength();
/* 601 */     for (int i = 0; i < len; i++) {
/* 602 */       Node child = children.item(i);
/* 603 */       if (child instanceof Element) {
/* 604 */         printElement(newPrefix, (Element)child);
/*     */       } else {
/* 606 */         System.out.println(newPrefix + "node: name " + child.getNodeName() + " namespaceURI: " + child.getNamespaceURI() + " localName: " + child.getLocalName() + " nodeName: " + child.getNodeName());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean _trace = false;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\Factory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */