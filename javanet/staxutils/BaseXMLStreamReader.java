/*     */ package javanet.staxutils;
/*     */ 
/*     */ import javanet.staxutils.error.IllegalStreamStateException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseXMLStreamReader
/*     */   implements XMLStreamReader
/*     */ {
/*     */   protected String systemId;
/*     */   protected String encoding;
/*     */   
/*     */   public BaseXMLStreamReader() {}
/*     */   
/*     */   public BaseXMLStreamReader(String systemId, String encoding) {
/*  67 */     this.systemId = systemId;
/*  68 */     this.encoding = encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/*  74 */     return this.systemId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/*  80 */     return this.encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEventTypeName() {
/*  91 */     return XMLStreamUtils.getEventTypeName(getEventType());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextTag() throws XMLStreamException {
/*  97 */     for (int eventType = next(); hasNext(); eventType = next()) {
/*     */       
/*  99 */       switch (eventType) {
/*     */         
/*     */         case 1:
/*     */         case 2:
/* 103 */           return eventType;
/*     */         
/*     */         case 4:
/*     */         case 12:
/* 107 */           if (!isWhiteSpace());
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 128 */     throw new XMLStreamException("Encountered " + getEventTypeName() + " when expecting START_ELEMENT or END_ELEMENT", getStableLocation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCharacters() {
/* 136 */     return (getEventType() == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEndElement() {
/* 142 */     return (getEventType() == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStartElement() {
/* 148 */     return (getEventType() == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWhiteSpace() {
/* 154 */     return (getEventType() == 6);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasName() {
/* 160 */     switch (getEventType()) {
/*     */       
/*     */       case 1:
/*     */       case 2:
/* 164 */         return true;
/*     */     } 
/*     */     
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 175 */     switch (getEventType()) {
/*     */       
/*     */       case 1:
/*     */       case 2:
/* 179 */         return getName().getPrefix();
/*     */     } 
/*     */     
/* 182 */     throw new IllegalStreamStateException("Expected START_ELEMENT or END_ELEMENT but was " + getEventTypeName(), getStableLocation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasText() {
/* 192 */     switch (getEventType()) {
/*     */       
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 9:
/*     */       case 12:
/* 199 */         return true;
/*     */     } 
/*     */     
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/* 210 */     if (prefix == null)
/*     */     {
/* 212 */       throw new IllegalArgumentException("Namespace prefix was null");
/*     */     }
/*     */ 
/*     */     
/* 216 */     return getNamespaceContext().getNamespaceURI(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/* 222 */     switch (getEventType()) {
/*     */       
/*     */       case 1:
/*     */       case 2:
/* 226 */         return getName().getNamespaceURI();
/*     */     } 
/*     */     
/* 229 */     throw new IllegalStreamStateException("Expected START_ELEMENT or END_ELEMENT state, but found " + getEventTypeName(), getStableLocation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeLocalName(int index) {
/* 239 */     return getAttributeName(index).getLocalPart();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeNamespace(int index) {
/* 245 */     return getAttributeName(index).getNamespaceURI();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributePrefix(int index) {
/* 251 */     return getAttributeName(index).getPrefix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
/* 258 */     int currType = getEventType();
/* 259 */     if (currType != type)
/*     */     {
/* 261 */       throw new XMLStreamException("Expected " + XMLStreamUtils.getEventTypeName(type) + " but found " + XMLStreamUtils.getEventTypeName(currType), getStableLocation());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementText() throws XMLStreamException {
/* 272 */     if (getEventType() != 1)
/*     */     {
/* 274 */       throw new XMLStreamException("Expected START_ELEMENT but found " + getEventTypeName(), getStableLocation());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     QName elemName = getName();
/* 282 */     Location elemLocation = getStableLocation();
/*     */ 
/*     */     
/* 285 */     StringBuffer content = null; int eventType;
/* 286 */     for (eventType = next(); eventType != 2; eventType = next()) {
/*     */       
/* 288 */       if (hasText()) {
/*     */         
/* 290 */         if (content == null)
/*     */         {
/* 292 */           content = new StringBuffer();
/*     */         }
/*     */         
/* 295 */         content.append(getText());
/*     */       }
/*     */       else {
/*     */         
/* 299 */         throw new XMLStreamException("Encountered " + getEventTypeName() + " event within text-only element " + elemName, elemLocation);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 309 */     return (content == null) ? "" : content.toString();
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
/*     */   public Location getStableLocation() {
/* 324 */     Location location = getLocation();
/* 325 */     if (!(location instanceof StaticLocation))
/*     */     {
/*     */       
/* 328 */       location = new SimpleLocation(location);
/*     */     }
/*     */ 
/*     */     
/* 332 */     return location;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\BaseXMLStreamReader.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */