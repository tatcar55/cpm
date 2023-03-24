/*     */ package com.sun.xml.ws.security.opt.impl.outgoing;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.JAXBEncryptedData;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.JAXBEncryptedKey;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
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
/*     */ public class SecurityHeader
/*     */ {
/*     */   public static final int LAYOUT_LAX = 0;
/*     */   public static final int LAYOUT_STRICT = 1;
/*     */   public static final int LAYOUT_LAX_TS_FIRST = 2;
/*     */   public static final int LAYOUT_LAX_TS_LAST = 3;
/*  74 */   protected ArrayList<SecurityHeaderElement> secHeaderContent = new ArrayList<SecurityHeaderElement>();
/*  75 */   protected int headerLayout = 1;
/*  76 */   protected String soapVersion = "http://schemas.xmlsoap.org/soap/envelope/";
/*     */ 
/*     */   
/*     */   private boolean debug = false;
/*     */   
/*     */   private boolean mustUnderstandValue = true;
/*     */ 
/*     */   
/*     */   public SecurityHeader() {}
/*     */ 
/*     */   
/*     */   public SecurityHeader(int layout, String soapVersion, boolean muValue) {
/*  88 */     this.headerLayout = layout;
/*  89 */     this.soapVersion = soapVersion;
/*  90 */     this.mustUnderstandValue = muValue;
/*     */   }
/*     */   
/*     */   public int getHeaderLayout() {
/*  94 */     return this.headerLayout;
/*     */   }
/*     */   
/*     */   public void setHeaderLayout(int headerLayout) {
/*  98 */     this.headerLayout = headerLayout;
/*     */   }
/*     */   
/*     */   public String getSOAPVersion() {
/* 102 */     return this.soapVersion;
/*     */   }
/*     */   
/*     */   public void setSOAPVersion(String soapVersion) {
/* 106 */     this.soapVersion = soapVersion;
/*     */   }
/*     */   
/*     */   public SecurityHeaderElement getChildElement(String localName, String uri) {
/* 110 */     for (SecurityHeaderElement she : this.secHeaderContent) {
/* 111 */       if (localName.equals(she.getLocalPart()) && uri.equals(she.getNamespaceURI())) {
/* 112 */         return she;
/*     */       }
/*     */     } 
/* 115 */     return null;
/*     */   }
/*     */   
/*     */   public Iterator getHeaders(final String localName, final String uri) {
/* 119 */     return new Iterator() {
/* 120 */         int idx = 0; Object next;
/*     */         
/*     */         public boolean hasNext() {
/* 123 */           if (this.next == null)
/* 124 */             fetch(); 
/* 125 */           return (this.next != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 129 */           if (this.next == null) {
/* 130 */             fetch();
/* 131 */             if (this.next == null) {
/* 132 */               throw new NoSuchElementException();
/*     */             }
/*     */           } 
/*     */           
/* 136 */           Object r = this.next;
/* 137 */           this.next = null;
/* 138 */           return r;
/*     */         }
/*     */         
/*     */         private void fetch() {
/* 142 */           while (this.idx < SecurityHeader.this.secHeaderContent.size()) {
/* 143 */             SecurityHeaderElement she = SecurityHeader.this.secHeaderContent.get(this.idx++);
/* 144 */             if ((uri == null && localName.equals(she.getLocalPart())) || (localName.equals(she.getLocalPart()) && uri.equals(she.getNamespaceURI()))) {
/*     */               
/* 146 */               this.next = she;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/*     */         public void remove() {
/* 153 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityHeaderElement getChildElement(String id) {
/* 160 */     for (SecurityHeaderElement she : this.secHeaderContent) {
/* 161 */       if (id.equals(she.getId()))
/* 162 */         return she; 
/*     */     } 
/* 164 */     return null;
/*     */   }
/*     */   
/*     */   public void add(SecurityHeaderElement header) {
/* 168 */     prepend(header);
/*     */   }
/*     */   
/*     */   public boolean replace(SecurityHeaderElement replaceThis, SecurityHeaderElement withThis) {
/* 172 */     int index = this.secHeaderContent.indexOf(replaceThis);
/* 173 */     if (index != -1) {
/* 174 */       this.secHeaderContent.set(index, withThis);
/* 175 */       return true;
/*     */     } 
/* 177 */     return false;
/*     */   }
/*     */   
/*     */   public void prepend(SecurityHeaderElement element) {
/* 181 */     this.secHeaderContent.add(0, element);
/*     */   }
/*     */   
/*     */   public void append(SecurityHeaderElement element) {
/* 185 */     this.secHeaderContent.add(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/* 195 */     return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getLocalPart() {
/* 205 */     return "Security";
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
/*     */   @Nullable
/*     */   public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
/* 224 */     throw new UnsupportedOperationException();
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
/*     */   @Nullable
/*     */   public String getAttribute(@NotNull QName name) {
/* 239 */     throw new UnsupportedOperationException();
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
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 251 */     orderHeaders();
/* 252 */     if (this.secHeaderContent.size() > 0) {
/* 253 */       streamWriter.writeStartElement("wsse", "Security", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
/* 254 */       writeMustunderstand(streamWriter);
/* 255 */       for (SecurityHeaderElement el : this.secHeaderContent) {
/* 256 */         ((SecurityElementWriter)el).writeTo(streamWriter);
/*     */       }
/*     */       
/* 259 */       streamWriter.writeEndElement();
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
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 276 */     throw new UnsupportedOperationException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 305 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private void writeMustunderstand(XMLStreamWriter writer) throws XMLStreamException {
/* 309 */     if (this.mustUnderstandValue) {
/* 310 */       if (this.soapVersion == "http://schemas.xmlsoap.org/soap/envelope/") {
/* 311 */         writer.writeAttribute("S", "http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand", "1");
/* 312 */       } else if (this.soapVersion == "http://www.w3.org/2003/05/soap-envelope") {
/* 313 */         writer.writeAttribute("S", "http://www.w3.org/2003/05/soap-envelope", "mustUnderstand", "true");
/*     */       }
/*     */     
/* 316 */     } else if (this.soapVersion == "http://schemas.xmlsoap.org/soap/envelope/") {
/* 317 */       writer.writeAttribute("S", "http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand", "0");
/* 318 */     } else if (this.soapVersion == "http://www.w3.org/2003/05/soap-envelope") {
/* 319 */       writer.writeAttribute("S", "http://www.w3.org/2003/05/soap-envelope", "mustUnderstand", "false");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void orderHeaders() {
/* 325 */     if (this.headerLayout == 3) {
/* 326 */       laxTimestampLast();
/* 327 */     } else if (this.headerLayout == 2) {
/* 328 */       laxTimestampFirst();
/*     */     } else {
/* 330 */       strict();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void laxTimestampLast() {
/* 335 */     strict();
/* 336 */     SecurityHeaderElement timestamp = this.secHeaderContent.get(0);
/* 337 */     if (timestamp != null && timestamp instanceof com.sun.xml.ws.security.opt.impl.tokens.Timestamp) {
/* 338 */       this.secHeaderContent.remove(0);
/* 339 */       this.secHeaderContent.add(timestamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void laxTimestampFirst() {
/* 345 */     strict();
/*     */   }
/*     */   
/*     */   private void print(ArrayList<SecurityHeaderElement> list) {
/* 349 */     if (!this.debug) {
/*     */       return;
/*     */     }
/* 352 */     System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
/* 353 */     for (int j = 0; j < list.size(); j++) {
/* 354 */       System.out.println(list.get(j));
/*     */     }
/* 356 */     System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
/*     */   }
/*     */   
/*     */   private void strict() {
/* 360 */     ArrayList<SecurityHeaderElement> primaryElementList = new ArrayList<SecurityHeaderElement>();
/* 361 */     ArrayList<SecurityHeaderElement> topElementList = new ArrayList<SecurityHeaderElement>();
/* 362 */     int len = this.secHeaderContent.size();
/* 363 */     print(this.secHeaderContent);
/*     */     
/* 365 */     SecurityHeaderElement timeStamp = null;
/* 366 */     for (int i = 0; i < len; i++) {
/* 367 */       SecurityHeaderElement she = this.secHeaderContent.get(i);
/* 368 */       if (she.getLocalPart() == "Timestamp") {
/* 369 */         timeStamp = she;
/*     */       
/*     */       }
/* 372 */       else if (isTopLevelElement(she)) {
/* 373 */         topElementList.add(she);
/*     */       } else {
/* 375 */         primaryElementList.add(0, she);
/*     */       } 
/*     */     } 
/*     */     
/* 379 */     print(topElementList);
/*     */ 
/*     */     
/* 382 */     print(primaryElementList);
/* 383 */     primaryElementList = orderList(primaryElementList);
/*     */     
/* 385 */     ArrayList<SecurityHeaderElement> tmpList = new ArrayList<SecurityHeaderElement>(); int j;
/* 386 */     for (j = 0; j < primaryElementList.size(); j++) {
/* 387 */       SecurityHeaderElement she = primaryElementList.get(j);
/* 388 */       if (she.getLocalPart() == "ReferenceList" || she.getLocalPart() == "EncryptedKey") {
/*     */         
/* 390 */         int tLen = topElementList.size();
/* 391 */         for (int k = tLen - 1; k >= 0; k--) {
/* 392 */           SecurityHeaderElement tk = topElementList.get(k);
/* 393 */           if (she.refersToSecHdrWithId(tk.getId())) {
/* 394 */             topElementList.add(k + 1, she);
/*     */             
/* 396 */             tmpList.add(she);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 402 */     primaryElementList.removeAll(tmpList);
/*     */     
/* 404 */     topElementList = orderList(topElementList);
/*     */     
/* 406 */     this.secHeaderContent.clear();
/* 407 */     for (j = topElementList.size() - 1; j >= 0; j--) {
/* 408 */       this.secHeaderContent.add(topElementList.get(j));
/*     */     }
/*     */     
/* 411 */     for (j = primaryElementList.size() - 1; j >= 0; j--) {
/* 412 */       this.secHeaderContent.add(primaryElementList.get(j));
/*     */     }
/*     */     
/* 415 */     if (timeStamp != null) {
/* 416 */       this.secHeaderContent.add(0, timeStamp);
/*     */     }
/*     */   }
/*     */   
/*     */   private ArrayList<SecurityHeaderElement> orderList(ArrayList<SecurityHeaderElement> list) {
/* 421 */     ArrayList<SecurityHeaderElement> tmp = new ArrayList<SecurityHeaderElement>();
/* 422 */     for (int i = 0; i < list.size(); i++) {
/* 423 */       SecurityHeaderElement securityElementOne = list.get(i);
/*     */       
/* 425 */       int wLen = tmp.size();
/* 426 */       int index = 0;
/* 427 */       if (wLen == 0) {
/* 428 */         tmp.add(securityElementOne);
/*     */       }
/*     */       else {
/*     */         
/* 432 */         int setIndex = -1;
/* 433 */         for (int j = 0; j < wLen; j++) {
/* 434 */           SecurityHeaderElement securityElementTwo = tmp.get(j);
/* 435 */           if (securityElementOne.refersToSecHdrWithId(securityElementTwo.getId())) {
/* 436 */             if (securityElementTwo instanceof JAXBEncryptedData) {
/* 437 */               if (securityElementOne instanceof JAXBEncryptedKey || securityElementOne.getLocalPart() == "ReferenceList") {
/* 438 */                 setIndex = j + 1;
/*     */               } else {
/* 440 */                 setIndex = j;
/*     */               } 
/*     */             } else {
/* 443 */               setIndex = j;
/*     */             } 
/* 445 */           } else if (securityElementTwo instanceof JAXBEncryptedData && refersToEncryptedElement(securityElementOne, securityElementTwo)) {
/* 446 */             setIndex = j;
/* 447 */           } else if (securityElementTwo.refersToSecHdrWithId(securityElementOne.getId())) {
/* 448 */             if (securityElementTwo instanceof JAXBEncryptedKey && securityElementOne instanceof JAXBEncryptedData) {
/* 449 */               setIndex = j;
/*     */             } else {
/* 451 */               setIndex = j + 1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 456 */         if (!tmp.contains(securityElementOne))
/*     */         
/*     */         { 
/* 459 */           if (setIndex == -1) {
/* 460 */             tmp.add(securityElementOne);
/*     */           } else {
/* 462 */             tmp.add(setIndex, securityElementOne);
/*     */           } 
/* 464 */           print(tmp); } 
/*     */       } 
/* 466 */     }  return tmp;
/*     */   }
/*     */   
/*     */   private boolean refersToEncryptedElement(SecurityHeaderElement securityElementOne, SecurityHeaderElement securityElementTwo) {
/* 470 */     if (securityElementOne.refersToSecHdrWithId(((JAXBEncryptedData)securityElementTwo).getEncryptedId())) {
/* 471 */       return true;
/*     */     }
/* 473 */     return false;
/*     */   }
/*     */   private void movePrevHeader(SecurityHeaderElement toBeMoved, int index) {
/* 476 */     int prevIndex = this.secHeaderContent.indexOf(toBeMoved);
/* 477 */     SecurityHeaderElement prev = this.secHeaderContent.get(prevIndex - 1);
/* 478 */     String prevId = prev.getId();
/* 479 */     this.secHeaderContent.remove(toBeMoved);
/* 480 */     this.secHeaderContent.add(index, toBeMoved);
/* 481 */     if (toBeMoved.refersToSecHdrWithId(prevId)) {
/* 482 */       movePrevHeader(prev, index);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isTopLevelElement(SecurityHeaderElement she) {
/* 487 */     String localPart = she.getLocalPart();
/*     */     
/* 489 */     if (localPart.equals("EncryptedData")) {
/* 490 */       if (she instanceof com.sun.xml.ws.security.opt.impl.message.GSHeaderElement) {
/* 491 */         return true;
/*     */       }
/* 493 */       localPart = ((JAXBEncryptedData)she).getEncryptedLocalName();
/*     */     } 
/*     */     
/* 496 */     if (localPart == "BinarySecurityToken") {
/* 497 */       return true;
/*     */     }
/* 499 */     if (localPart == "SecurityContextToken") {
/* 500 */       return true;
/*     */     }
/* 502 */     if (localPart == "EncryptedKey") {
/* 503 */       if (((JAXBEncryptedKey)she).hasReferenceList()) {
/* 504 */         return false;
/*     */       }
/* 506 */       return true;
/*     */     } 
/* 508 */     if (localPart == "DerivedKeyToken") {
/* 509 */       return true;
/*     */     }
/* 511 */     if (localPart == "SignatureConfirmation") {
/* 512 */       return true;
/*     */     }
/*     */     
/* 515 */     if (localPart == "Timestamp") {
/* 516 */       return true;
/*     */     }
/* 518 */     if (localPart.equals("Assertion")) {
/* 519 */       return true;
/*     */     }
/* 521 */     if (localPart == "UsernameToken") {
/* 522 */       return true;
/*     */     }
/* 524 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\outgoing\SecurityHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */