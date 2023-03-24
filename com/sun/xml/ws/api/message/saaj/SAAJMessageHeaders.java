/*     */ package com.sun.xml.ws.api.message.saaj;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.ws.binding.SOAPBindingImpl;
/*     */ import com.sun.xml.ws.message.saaj.SAAJHeader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPHeaderElement;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAAJMessageHeaders
/*     */   implements MessageHeaders
/*     */ {
/*     */   SOAPMessage sm;
/*     */   Map<SOAPHeaderElement, Header> nonSAAJHeaders;
/*     */   Map<QName, Integer> notUnderstoodCount;
/*     */   SOAPVersion soapVersion;
/*     */   private Set<QName> understoodHeaders;
/*     */   
/*     */   public SAAJMessageHeaders(SOAPMessage sm, SOAPVersion version) {
/*  72 */     this.sm = sm;
/*  73 */     this.soapVersion = version;
/*  74 */     initHeaderUnderstanding();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initHeaderUnderstanding() {
/*  81 */     SOAPHeader soapHeader = ensureSOAPHeader();
/*  82 */     if (soapHeader == null) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     Iterator<SOAPHeaderElement> allHeaders = soapHeader.examineAllHeaderElements();
/*  87 */     while (allHeaders.hasNext()) {
/*  88 */       SOAPHeaderElement nextHdrElem = allHeaders.next();
/*  89 */       if (nextHdrElem == null) {
/*     */         continue;
/*     */       }
/*  92 */       if (nextHdrElem.getMustUnderstand()) {
/*  93 */         notUnderstood(nextHdrElem.getElementQName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void understood(Header header) {
/* 104 */     understood(header.getNamespaceURI(), header.getLocalPart());
/*     */   }
/*     */ 
/*     */   
/*     */   public void understood(String nsUri, String localName) {
/* 109 */     understood(new QName(nsUri, localName));
/*     */   }
/*     */ 
/*     */   
/*     */   public void understood(QName qName) {
/* 114 */     if (this.notUnderstoodCount == null) {
/* 115 */       this.notUnderstoodCount = new HashMap<QName, Integer>();
/*     */     }
/*     */     
/* 118 */     Integer count = this.notUnderstoodCount.get(qName);
/* 119 */     if (count != null && count.intValue() > 0) {
/*     */       
/* 121 */       count = Integer.valueOf(count.intValue() - 1);
/* 122 */       if (count.intValue() <= 0) {
/*     */ 
/*     */         
/* 125 */         this.notUnderstoodCount.remove(qName);
/*     */       } else {
/* 127 */         this.notUnderstoodCount.put(qName, count);
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     if (this.understoodHeaders == null) {
/* 132 */       this.understoodHeaders = new HashSet<QName>();
/*     */     }
/*     */     
/* 135 */     this.understoodHeaders.add(qName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(Header header) {
/* 141 */     return isUnderstood(header.getNamespaceURI(), header.getLocalPart());
/*     */   }
/*     */   
/*     */   public boolean isUnderstood(String nsUri, String localName) {
/* 145 */     return isUnderstood(new QName(nsUri, localName));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(QName name) {
/* 150 */     if (this.understoodHeaders == null) {
/* 151 */       return false;
/*     */     }
/* 153 */     return this.understoodHeaders.contains(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(int index) {
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Header get(String nsUri, String localName, boolean markAsUnderstood) {
/* 163 */     SOAPHeaderElement h = find(nsUri, localName);
/* 164 */     if (h != null) {
/* 165 */       if (markAsUnderstood) {
/* 166 */         understood(nsUri, localName);
/*     */       }
/* 168 */       return (Header)new SAAJHeader(h);
/*     */     } 
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Header get(QName name, boolean markAsUnderstood) {
/* 175 */     return get(name.getNamespaceURI(), name.getLocalPart(), markAsUnderstood);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders(QName headerName, boolean markAsUnderstood) {
/* 181 */     return getHeaders(headerName.getNamespaceURI(), headerName.getLocalPart(), markAsUnderstood);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders(String nsUri, String localName, boolean markAsUnderstood) {
/* 187 */     SOAPHeader soapHeader = ensureSOAPHeader();
/* 188 */     if (soapHeader == null) {
/* 189 */       return null;
/*     */     }
/* 191 */     Iterator<SOAPHeaderElement> allHeaders = soapHeader.examineAllHeaderElements();
/* 192 */     if (markAsUnderstood) {
/*     */ 
/*     */       
/* 195 */       List<Header> headers = new ArrayList<Header>();
/* 196 */       while (allHeaders.hasNext()) {
/* 197 */         SOAPHeaderElement nextHdr = allHeaders.next();
/* 198 */         if (nextHdr != null && nextHdr.getNamespaceURI().equals(nsUri))
/*     */         {
/* 200 */           if (localName == null || nextHdr.getLocalName().equals(localName)) {
/*     */             
/* 202 */             understood(nextHdr.getNamespaceURI(), nextHdr.getLocalName());
/* 203 */             headers.add(new SAAJHeader(nextHdr));
/*     */           } 
/*     */         }
/*     */       } 
/* 207 */       return headers.iterator();
/*     */     } 
/*     */ 
/*     */     
/* 211 */     return new HeaderReadIterator(allHeaders, nsUri, localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders(String nsUri, boolean markAsUnderstood) {
/* 216 */     return getHeaders(nsUri, null, markAsUnderstood);
/*     */   }
/*     */   
/*     */   public boolean add(Header header) {
/*     */     try {
/* 221 */       header.writeTo(this.sm);
/* 222 */     } catch (SOAPException e) {
/*     */       
/* 224 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 228 */     notUnderstood(new QName(header.getNamespaceURI(), header.getLocalPart()));
/*     */ 
/*     */     
/* 231 */     if (isNonSAAJHeader(header))
/*     */     {
/* 233 */       addNonSAAJHeader(find(header.getNamespaceURI(), header.getLocalPart()), header);
/*     */     }
/*     */ 
/*     */     
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Header remove(QName name) {
/* 242 */     return remove(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */ 
/*     */   
/*     */   public Header remove(String nsUri, String localName) {
/* 247 */     SOAPHeader soapHeader = ensureSOAPHeader();
/* 248 */     if (soapHeader == null) {
/* 249 */       return null;
/*     */     }
/* 251 */     SOAPHeaderElement headerElem = find(nsUri, localName);
/* 252 */     if (headerElem == null) {
/* 253 */       return null;
/*     */     }
/* 255 */     headerElem = (SOAPHeaderElement)soapHeader.removeChild(headerElem);
/*     */ 
/*     */     
/* 258 */     removeNonSAAJHeader(headerElem);
/*     */ 
/*     */     
/* 261 */     QName hdrName = (nsUri == null) ? new QName(localName) : new QName(nsUri, localName);
/* 262 */     if (this.understoodHeaders != null) {
/* 263 */       this.understoodHeaders.remove(hdrName);
/*     */     }
/* 265 */     removeNotUnderstood(hdrName);
/*     */     
/* 267 */     return (Header)new SAAJHeader(headerElem);
/*     */   }
/*     */   
/*     */   private void removeNotUnderstood(QName hdrName) {
/* 271 */     if (this.notUnderstoodCount == null) {
/*     */       return;
/*     */     }
/* 274 */     Integer notUnderstood = this.notUnderstoodCount.get(hdrName);
/* 275 */     if (notUnderstood != null) {
/* 276 */       int intNotUnderstood = notUnderstood.intValue();
/* 277 */       intNotUnderstood--;
/* 278 */       if (intNotUnderstood <= 0) {
/* 279 */         this.notUnderstoodCount.remove(hdrName);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private SOAPHeaderElement find(QName qName) {
/* 286 */     return find(qName.getNamespaceURI(), qName.getLocalPart());
/*     */   }
/*     */   
/*     */   private SOAPHeaderElement find(String nsUri, String localName) {
/* 290 */     SOAPHeader soapHeader = ensureSOAPHeader();
/* 291 */     if (soapHeader == null) {
/* 292 */       return null;
/*     */     }
/* 294 */     Iterator<SOAPHeaderElement> allHeaders = soapHeader.examineAllHeaderElements();
/* 295 */     while (allHeaders.hasNext()) {
/* 296 */       SOAPHeaderElement nextHdrElem = allHeaders.next();
/* 297 */       if (nextHdrElem.getNamespaceURI().equals(nsUri) && nextHdrElem.getLocalName().equals(localName))
/*     */       {
/* 299 */         return nextHdrElem;
/*     */       }
/*     */     } 
/* 302 */     return null;
/*     */   }
/*     */   
/*     */   private void notUnderstood(QName qName) {
/* 306 */     if (this.notUnderstoodCount == null) {
/* 307 */       this.notUnderstoodCount = new HashMap<QName, Integer>();
/*     */     }
/* 309 */     Integer count = this.notUnderstoodCount.get(qName);
/* 310 */     if (count == null) {
/* 311 */       this.notUnderstoodCount.put(qName, Integer.valueOf(1));
/*     */     } else {
/* 313 */       this.notUnderstoodCount.put(qName, Integer.valueOf(count.intValue() + 1));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 318 */     if (this.understoodHeaders != null) {
/* 319 */       this.understoodHeaders.remove(qName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPHeader ensureSOAPHeader() {
/*     */     try {
/* 330 */       SOAPHeader header = this.sm.getSOAPPart().getEnvelope().getHeader();
/* 331 */       if (header != null) {
/* 332 */         return header;
/*     */       }
/* 334 */       return this.sm.getSOAPPart().getEnvelope().addHeader();
/*     */     }
/* 336 */     catch (Exception e) {
/* 337 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isNonSAAJHeader(Header header) {
/* 342 */     return !(header instanceof SAAJHeader);
/*     */   }
/*     */   
/*     */   private void addNonSAAJHeader(SOAPHeaderElement headerElem, Header header) {
/* 346 */     if (this.nonSAAJHeaders == null) {
/* 347 */       this.nonSAAJHeaders = new HashMap<SOAPHeaderElement, Header>();
/*     */     }
/* 349 */     this.nonSAAJHeaders.put(headerElem, header);
/*     */   }
/*     */   
/*     */   private void removeNonSAAJHeader(SOAPHeaderElement headerElem) {
/* 353 */     if (this.nonSAAJHeaders != null) {
/* 354 */       this.nonSAAJHeaders.remove(headerElem);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addOrReplace(Header header) {
/* 360 */     remove(header.getNamespaceURI(), header.getLocalPart());
/* 361 */     return add(header);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<QName> getUnderstoodHeaders() {
/* 366 */     return this.understoodHeaders;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<QName> getNotUnderstoodHeaders(Set<String> roles, Set<QName> knownHeaders, WSBinding binding) {
/* 372 */     Set<QName> notUnderstoodHeaderNames = new HashSet<QName>();
/* 373 */     if (this.notUnderstoodCount == null) {
/* 374 */       return notUnderstoodHeaderNames;
/*     */     }
/* 376 */     for (QName headerName : this.notUnderstoodCount.keySet()) {
/* 377 */       int count = ((Integer)this.notUnderstoodCount.get(headerName)).intValue();
/* 378 */       if (count <= 0) {
/*     */         continue;
/*     */       }
/* 381 */       SOAPHeaderElement hdrElem = find(headerName);
/* 382 */       if (!hdrElem.getMustUnderstand()) {
/*     */         continue;
/*     */       }
/* 385 */       SAAJHeader hdr = new SAAJHeader(hdrElem);
/*     */ 
/*     */       
/* 388 */       boolean understood = false;
/* 389 */       if (roles != null) {
/* 390 */         understood = !roles.contains(hdr.getRole(this.soapVersion));
/*     */       }
/* 392 */       if (understood) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 397 */       if (binding != null && binding instanceof SOAPBindingImpl) {
/* 398 */         understood = ((SOAPBindingImpl)binding).understandsHeader(headerName);
/* 399 */         if (!understood && 
/* 400 */           knownHeaders != null && knownHeaders.contains(headerName)) {
/* 401 */           understood = true;
/*     */         }
/*     */       } 
/*     */       
/* 405 */       if (!understood) {
/* 406 */         notUnderstoodHeaderNames.add(headerName);
/*     */       }
/*     */     } 
/* 409 */     return notUnderstoodHeaderNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders() {
/* 414 */     SOAPHeader soapHeader = ensureSOAPHeader();
/* 415 */     if (soapHeader == null) {
/* 416 */       return null;
/*     */     }
/* 418 */     Iterator allHeaders = soapHeader.examineAllHeaderElements();
/* 419 */     return new HeaderReadIterator(allHeaders, null, null);
/*     */   }
/*     */   
/*     */   private static class HeaderReadIterator
/*     */     implements Iterator<Header> {
/*     */     SOAPHeaderElement current;
/*     */     Iterator soapHeaders;
/*     */     String myNsUri;
/*     */     String myLocalName;
/*     */     
/*     */     public HeaderReadIterator(Iterator allHeaders, String nsUri, String localName) {
/* 430 */       this.soapHeaders = allHeaders;
/* 431 */       this.myNsUri = nsUri;
/* 432 */       this.myLocalName = localName;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 437 */       if (this.current == null) {
/* 438 */         advance();
/*     */       }
/* 440 */       return (this.current != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public Header next() {
/* 445 */       if (!hasNext()) {
/* 446 */         return null;
/*     */       }
/* 448 */       if (this.current == null) {
/* 449 */         return null;
/*     */       }
/*     */       
/* 452 */       SAAJHeader ret = new SAAJHeader(this.current);
/* 453 */       this.current = null;
/* 454 */       return (Header)ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 459 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     private void advance() {
/* 463 */       while (this.soapHeaders.hasNext()) {
/* 464 */         SOAPHeaderElement nextHdr = this.soapHeaders.next();
/* 465 */         if (nextHdr != null && (this.myNsUri == null || nextHdr.getNamespaceURI().equals(this.myNsUri)) && (this.myLocalName == null || nextHdr.getLocalName().equals(this.myLocalName))) {
/*     */ 
/*     */           
/* 468 */           this.current = nextHdr;
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 474 */       this.current = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\saaj\SAAJMessageHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */