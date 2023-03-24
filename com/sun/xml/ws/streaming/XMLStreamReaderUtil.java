/*     */ package com.sun.xml.ws.streaming;
/*     */ 
/*     */ import javax.xml.namespace.QName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamReaderUtil
/*     */ {
/*     */   public static void close(XMLStreamReader reader) {
/*     */     try {
/*  62 */       reader.close();
/*  63 */     } catch (XMLStreamException e) {
/*  64 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void readRest(XMLStreamReader reader) {
/*     */     try {
/*  70 */       while (reader.getEventType() != 8) {
/*  71 */         reader.next();
/*     */       }
/*  73 */     } catch (XMLStreamException e) {
/*  74 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int next(XMLStreamReader reader) {
/*     */     try {
/*  80 */       int readerEvent = reader.next();
/*     */       
/*  82 */       while (readerEvent != 8) {
/*  83 */         switch (readerEvent) {
/*     */           case 1:
/*     */           case 2:
/*     */           case 3:
/*     */           case 4:
/*     */           case 12:
/*  89 */             return readerEvent;
/*     */         } 
/*     */ 
/*     */         
/*  93 */         readerEvent = reader.next();
/*     */       } 
/*     */       
/*  96 */       return readerEvent;
/*     */     }
/*  98 */     catch (XMLStreamException e) {
/*  99 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int nextElementContent(XMLStreamReader reader) {
/* 104 */     int state = nextContent(reader);
/* 105 */     if (state == 4) {
/* 106 */       throw new XMLStreamReaderException("xmlreader.unexpectedCharacterContent", new Object[] { reader.getText() });
/*     */     }
/*     */     
/* 109 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void toNextTag(XMLStreamReader reader, QName name) {
/* 114 */     if (reader.getEventType() != 1 && reader.getEventType() != 2)
/*     */     {
/* 116 */       nextElementContent(reader);
/*     */     }
/* 118 */     if (reader.getEventType() == 2 && name.equals(reader.getName())) {
/* 119 */       nextElementContent(reader);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String nextWhiteSpaceContent(XMLStreamReader reader) {
/* 130 */     next(reader);
/* 131 */     return currentWhiteSpaceContent(reader);
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
/*     */   public static String currentWhiteSpaceContent(XMLStreamReader reader) {
/* 144 */     StringBuilder whiteSpaces = null;
/*     */     
/*     */     while (true) {
/* 147 */       switch (reader.getEventType()) {
/*     */         case 1:
/*     */         case 2:
/*     */         case 8:
/* 151 */           return (whiteSpaces == null) ? null : whiteSpaces.toString();
/*     */         case 4:
/* 153 */           if (reader.isWhiteSpace()) {
/* 154 */             if (whiteSpaces == null) {
/* 155 */               whiteSpaces = new StringBuilder();
/*     */             }
/* 157 */             whiteSpaces.append(reader.getText()); break;
/*     */           } 
/* 159 */           throw new XMLStreamReaderException("xmlreader.unexpectedCharacterContent", new Object[] { reader.getText() });
/*     */       } 
/*     */ 
/*     */       
/* 163 */       next(reader);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int nextContent(XMLStreamReader reader) {
/*     */     while (true) {
/* 169 */       int state = next(reader);
/* 170 */       switch (state) {
/*     */         case 1:
/*     */         case 2:
/*     */         case 8:
/* 174 */           return state;
/*     */         case 4:
/* 176 */           if (!reader.isWhiteSpace()) {
/* 177 */             return 4;
/*     */           }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void skipElement(XMLStreamReader reader) {
/* 188 */     assert reader.getEventType() == 1;
/* 189 */     skipTags(reader, true);
/* 190 */     assert reader.getEventType() == 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void skipSiblings(XMLStreamReader reader, QName parent) {
/* 198 */     skipTags(reader, reader.getName().equals(parent));
/* 199 */     assert reader.getEventType() == 2;
/*     */   }
/*     */   
/*     */   private static void skipTags(XMLStreamReader reader, boolean exitCondition) {
/*     */     try {
/* 204 */       int tags = 0; int state;
/* 205 */       while ((state = reader.next()) != 8) {
/* 206 */         if (state == 1) {
/* 207 */           tags++; continue;
/*     */         } 
/* 209 */         if (state == 2) {
/* 210 */           if (tags == 0 && exitCondition)
/* 211 */             return;  tags--;
/*     */         }
/*     */       
/*     */       } 
/* 215 */     } catch (XMLStreamException e) {
/* 216 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getElementText(XMLStreamReader reader) {
/*     */     try {
/* 225 */       return reader.getElementText();
/* 226 */     } catch (XMLStreamException e) {
/* 227 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QName getElementQName(XMLStreamReader reader) {
/*     */     try {
/* 238 */       String text = reader.getElementText().trim();
/* 239 */       String prefix = text.substring(0, text.indexOf(':'));
/* 240 */       String namespaceURI = reader.getNamespaceContext().getNamespaceURI(prefix);
/* 241 */       if (namespaceURI == null) {
/* 242 */         namespaceURI = "";
/*     */       }
/* 244 */       String localPart = text.substring(text.indexOf(':') + 1, text.length());
/*     */       
/* 246 */       return new QName(namespaceURI, localPart);
/* 247 */     } catch (XMLStreamException e) {
/* 248 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Attributes getAttributes(XMLStreamReader reader) {
/* 257 */     return (reader.getEventType() == 1 || reader.getEventType() == 10) ? new AttributesImpl(reader) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void verifyReaderState(XMLStreamReader reader, int expectedState) {
/* 263 */     int state = reader.getEventType();
/* 264 */     if (state != expectedState) {
/* 265 */       throw new XMLStreamReaderException("xmlreader.unexpectedState", new Object[] { getStateName(expectedState), getStateName(state) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void verifyTag(XMLStreamReader reader, String namespaceURI, String localName) {
/* 272 */     if (!localName.equals(reader.getLocalName()) || !namespaceURI.equals(reader.getNamespaceURI())) {
/* 273 */       throw new XMLStreamReaderException("xmlreader.unexpectedState.tag", new Object[] { "{" + namespaceURI + "}" + localName, "{" + reader.getNamespaceURI() + "}" + reader.getLocalName() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void verifyTag(XMLStreamReader reader, QName name) {
/* 281 */     verifyTag(reader, name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */   
/*     */   public static String getStateName(XMLStreamReader reader) {
/* 285 */     return getStateName(reader.getEventType());
/*     */   }
/*     */   
/*     */   public static String getStateName(int state) {
/* 289 */     switch (state) {
/*     */       case 10:
/* 291 */         return "ATTRIBUTE";
/*     */       case 12:
/* 293 */         return "CDATA";
/*     */       case 4:
/* 295 */         return "CHARACTERS";
/*     */       case 5:
/* 297 */         return "COMMENT";
/*     */       case 11:
/* 299 */         return "DTD";
/*     */       case 8:
/* 301 */         return "END_DOCUMENT";
/*     */       case 2:
/* 303 */         return "END_ELEMENT";
/*     */       case 15:
/* 305 */         return "ENTITY_DECLARATION";
/*     */       case 9:
/* 307 */         return "ENTITY_REFERENCE";
/*     */       case 13:
/* 309 */         return "NAMESPACE";
/*     */       case 14:
/* 311 */         return "NOTATION_DECLARATION";
/*     */       case 3:
/* 313 */         return "PROCESSING_INSTRUCTION";
/*     */       case 6:
/* 315 */         return "SPACE";
/*     */       case 7:
/* 317 */         return "START_DOCUMENT";
/*     */       case 1:
/* 319 */         return "START_ELEMENT";
/*     */     } 
/* 321 */     return "UNKNOWN";
/*     */   }
/*     */ 
/*     */   
/*     */   private static XMLStreamReaderException wrapException(XMLStreamException e) {
/* 326 */     return new XMLStreamReaderException("xmlreader.ioException", new Object[] { e });
/*     */   }
/*     */ 
/*     */   
/*     */   public static class AttributesImpl
/*     */     implements Attributes
/*     */   {
/*     */     static final String XMLNS_NAMESPACE_URI = "http://www.w3.org/2000/xmlns/";
/*     */     
/*     */     AttributeInfo[] atInfos;
/*     */ 
/*     */     
/*     */     static class AttributeInfo
/*     */     {
/*     */       private QName name;
/*     */       
/*     */       private String value;
/*     */       
/*     */       public AttributeInfo(QName name, String value) {
/* 345 */         this.name = name;
/* 346 */         if (value == null) {
/*     */           
/* 348 */           this.value = "";
/*     */         } else {
/* 350 */           this.value = value;
/*     */         } 
/*     */       }
/*     */       
/*     */       QName getName() {
/* 355 */         return this.name;
/*     */       }
/*     */       
/*     */       String getValue() {
/* 359 */         return this.value;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       String getLocalName() {
/* 366 */         if (isNamespaceDeclaration()) {
/* 367 */           if (this.name.getLocalPart().equals("")) {
/* 368 */             return "xmlns";
/*     */           }
/* 370 */           return "xmlns:" + this.name.getLocalPart();
/*     */         } 
/* 372 */         return this.name.getLocalPart();
/*     */       }
/*     */       
/*     */       boolean isNamespaceDeclaration() {
/* 376 */         return (this.name.getNamespaceURI() == "http://www.w3.org/2000/xmlns/");
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AttributesImpl(XMLStreamReader reader) {
/* 388 */       if (reader == null) {
/*     */ 
/*     */ 
/*     */         
/* 392 */         this.atInfos = new AttributeInfo[0];
/*     */       }
/*     */       else {
/*     */         
/* 396 */         int index = 0;
/* 397 */         int namespaceCount = reader.getNamespaceCount();
/* 398 */         int attributeCount = reader.getAttributeCount();
/* 399 */         this.atInfos = new AttributeInfo[namespaceCount + attributeCount]; int i;
/* 400 */         for (i = 0; i < namespaceCount; i++) {
/* 401 */           String namespacePrefix = reader.getNamespacePrefix(i);
/*     */ 
/*     */           
/* 404 */           if (namespacePrefix == null) {
/* 405 */             namespacePrefix = "";
/*     */           }
/* 407 */           this.atInfos[index++] = new AttributeInfo(new QName("http://www.w3.org/2000/xmlns/", namespacePrefix, "xmlns"), reader.getNamespaceURI(i));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 413 */         for (i = 0; i < attributeCount; i++) {
/* 414 */           this.atInfos[index++] = new AttributeInfo(reader.getAttributeName(i), reader.getAttributeValue(i));
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getLength() {
/* 422 */       return this.atInfos.length;
/*     */     }
/*     */     
/*     */     public String getLocalName(int index) {
/* 426 */       if (index >= 0 && index < this.atInfos.length) {
/* 427 */         return this.atInfos[index].getLocalName();
/*     */       }
/* 429 */       return null;
/*     */     }
/*     */     
/*     */     public QName getName(int index) {
/* 433 */       if (index >= 0 && index < this.atInfos.length) {
/* 434 */         return this.atInfos[index].getName();
/*     */       }
/* 436 */       return null;
/*     */     }
/*     */     
/*     */     public String getPrefix(int index) {
/* 440 */       if (index >= 0 && index < this.atInfos.length) {
/* 441 */         return this.atInfos[index].getName().getPrefix();
/*     */       }
/* 443 */       return null;
/*     */     }
/*     */     
/*     */     public String getURI(int index) {
/* 447 */       if (index >= 0 && index < this.atInfos.length) {
/* 448 */         return this.atInfos[index].getName().getNamespaceURI();
/*     */       }
/* 450 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(int index) {
/* 454 */       if (index >= 0 && index < this.atInfos.length) {
/* 455 */         return this.atInfos[index].getValue();
/*     */       }
/* 457 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(QName name) {
/* 461 */       int index = getIndex(name);
/* 462 */       if (index != -1) {
/* 463 */         return this.atInfos[index].getValue();
/*     */       }
/* 465 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(String localName) {
/* 469 */       int index = getIndex(localName);
/* 470 */       if (index != -1) {
/* 471 */         return this.atInfos[index].getValue();
/*     */       }
/* 473 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(String uri, String localName) {
/* 477 */       int index = getIndex(uri, localName);
/* 478 */       if (index != -1) {
/* 479 */         return this.atInfos[index].getValue();
/*     */       }
/* 481 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isNamespaceDeclaration(int index) {
/* 485 */       if (index >= 0 && index < this.atInfos.length) {
/* 486 */         return this.atInfos[index].isNamespaceDeclaration();
/*     */       }
/* 488 */       return false;
/*     */     }
/*     */     
/*     */     public int getIndex(QName name) {
/* 492 */       for (int i = 0; i < this.atInfos.length; i++) {
/* 493 */         if (this.atInfos[i].getName().equals(name)) {
/* 494 */           return i;
/*     */         }
/*     */       } 
/* 497 */       return -1;
/*     */     }
/*     */     
/*     */     public int getIndex(String localName) {
/* 501 */       for (int i = 0; i < this.atInfos.length; i++) {
/* 502 */         if (this.atInfos[i].getName().getLocalPart().equals(localName)) {
/* 503 */           return i;
/*     */         }
/*     */       } 
/* 506 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIndex(String uri, String localName) {
/* 511 */       for (int i = 0; i < this.atInfos.length; i++) {
/* 512 */         QName qName = this.atInfos[i].getName();
/* 513 */         if (qName.getNamespaceURI().equals(uri) && qName.getLocalPart().equals(localName))
/*     */         {
/*     */           
/* 516 */           return i;
/*     */         }
/*     */       } 
/* 519 */       return -1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\streaming\XMLStreamReaderUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */