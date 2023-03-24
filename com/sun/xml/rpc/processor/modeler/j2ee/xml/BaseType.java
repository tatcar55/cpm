/*     */ package com.sun.xml.rpc.processor.modeler.j2ee.xml;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BaseType
/*     */   implements Serializable
/*     */ {
/*     */   protected Factory factory;
/*     */   protected Element xmlElement;
/*     */   protected Attr xmlAttr;
/*  57 */   protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFactory(Factory factory) {
/*  64 */     this.factory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setXMLElement(Element element) {
/*  71 */     this.xmlElement = element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getXMLElement() {
/*  78 */     return this.xmlElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setXMLAttribute(Attr xmlAttr) {
/*  85 */     this.xmlAttr = xmlAttr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attr getXMLAttribute() {
/*  92 */     return this.xmlAttr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementValue(String elementName, String elementValue) {
/* 102 */     NodeList nodes = this.xmlElement.getElementsByTagName(elementName);
/*     */     
/* 104 */     if (nodes.getLength() == 0) {
/*     */ 
/*     */       
/* 107 */       Element elementNode = this.factory.createXMLElementAndText(elementName, elementValue);
/*     */       
/* 109 */       this.xmlElement.appendChild(elementNode);
/*     */     } else {
/* 111 */       for (int i = 0; i < nodes.getLength(); i++) {
/* 112 */         Node node = nodes.item(i);
/* 113 */         String item = node.getNodeName().trim();
/*     */         
/* 115 */         if (item.equals(elementName)) {
/*     */           
/* 117 */           Node textNode = node.getFirstChild();
/* 118 */           if (textNode instanceof Text) {
/* 119 */             ((Text)textNode).setNodeValue(elementValue);
/*     */           }
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementValue(String elementName, boolean value) {
/* 131 */     setElementValue(elementName, (new Boolean(value)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementValue(String elementName, int value) {
/* 138 */     setElementValue(elementName, (new Integer(value)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementValue(String elementName, float value) {
/* 145 */     setElementValue(elementName, (new Float(value)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementValue(String elementName, double value) {
/* 152 */     setElementValue(elementName, (new Double(value)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementValue(String elementName, long value) {
/* 159 */     setElementValue(elementName, (new Long(value)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementValue(String elementName, short value) {
/* 166 */     setElementValue(elementName, (new Short(value)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementValue(String elementName, byte value) {
/* 173 */     setElementValue(elementName, (new Byte(value)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementValue(String elementName, Date value) {
/* 181 */     setElementValue(elementName, this.simpleDateFormat.format(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementValue(String elementName, BaseType baseType) {
/* 188 */     Element childXml = baseType.getXMLElement();
/* 189 */     this.xmlElement.appendChild(childXml);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementValue() {
/* 197 */     if (this.xmlAttr != null) {
/* 198 */       return this.xmlAttr.getValue();
/*     */     }
/*     */     
/* 201 */     String value = this.xmlElement.getNodeValue();
/* 202 */     if (value != null) {
/* 203 */       return value;
/*     */     }
/*     */ 
/*     */     
/* 207 */     NodeList nList = this.xmlElement.getChildNodes();
/*     */     
/* 209 */     for (int i = 0; i < nList.getLength(); i++) {
/* 210 */       if (nList.item(i) instanceof Text)
/* 211 */         return ((Text)nList.item(i)).getData(); 
/*     */     } 
/* 213 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementValue(String property, int index) {
/* 221 */     NodeList nodes = this.xmlElement.getChildNodes();
/* 222 */     int idx = 0;
/* 223 */     for (int i = 0; i < nodes.getLength(); i++) {
/* 224 */       Node node = nodes.item(i);
/* 225 */       if (node instanceof Element) {
/*     */         
/* 227 */         String localName = node.getLocalName();
/* 228 */         if (localName != null)
/*     */         {
/* 230 */           if (localName.equals(property)) {
/*     */             
/* 232 */             idx++;
/*     */             
/* 234 */             if (idx == index)
/* 235 */             { Element element = (Element)node;
/* 236 */               Node child = element.getFirstChild();
/* 237 */               if (child instanceof Text) {
/* 238 */                 return ((Text)child).getData();
/*     */               }
/* 240 */               return null; } 
/*     */           }  } 
/*     */       } 
/* 243 */     }  return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementValue(String elementName) {
/* 252 */     NodeList nList = this.xmlElement.getChildNodes();
/* 253 */     for (int i = 0; i < nList.getLength(); i++) {
/* 254 */       Node node = nList.item(i);
/*     */ 
/*     */       
/* 257 */       if (node.getNodeType() == 1) {
/* 258 */         Element anElement = (Element)node;
/* 259 */         if (anElement.getLocalName().equals(elementName)) {
/* 260 */           Node child = anElement.getFirstChild();
/* 261 */           if (child instanceof Text) {
/* 262 */             return ((Text)child).getData();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 267 */     return null;
/*     */   }
/*     */   
/*     */   public boolean getElementBooleanValue(String elementName) {
/* 271 */     return (new Boolean(getElementValue(elementName))).booleanValue();
/*     */   }
/*     */   
/*     */   public int getElementIntegerValue(String elementName) {
/* 275 */     return (new Integer(getElementValue(elementName))).intValue();
/*     */   }
/*     */   
/*     */   public float getElementFloatValue(String elementName) {
/* 279 */     return (new Float(getElementValue(elementName))).floatValue();
/*     */   }
/*     */   
/*     */   public double getElementDoubleValue(String elementName) {
/* 283 */     return (new Double(getElementValue(elementName))).doubleValue();
/*     */   }
/*     */   
/*     */   public long getElementLongValue(String elementName) {
/* 287 */     return (new Long(getElementValue(elementName))).longValue();
/*     */   }
/*     */   
/*     */   public short getElementShortValue(String elementName) {
/* 291 */     return (new Short(getElementValue(elementName))).shortValue();
/*     */   }
/*     */   
/*     */   public byte getElementByteValue(String elementName) {
/* 295 */     return (new Byte(getElementValue(elementName))).byteValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getElementDateValue(String elementName) {
/*     */     try {
/* 306 */       String result = getElementValue(elementName);
/* 307 */       if (result != null) {
/* 308 */         return this.simpleDateFormat.parse(result);
/*     */       }
/* 310 */     } catch (ParseException ex) {
/* 311 */       System.out.println("getElementDateValue exception.." + ex);
/*     */     } 
/* 313 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseType getElementValue(String property, String className) {
/* 322 */     NodeList nList = this.xmlElement.getChildNodes();
/*     */     
/* 324 */     for (int i = 0; i < nList.getLength(); i++) {
/* 325 */       Node node = nList.item(i);
/*     */       
/* 327 */       if (node instanceof Element && node.getLocalName().equals(property))
/*     */       {
/*     */         
/* 330 */         return this.factory.newInstance(node, className);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 335 */     return null;
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
/*     */   public Object getElementValue(String property, String className, int index) {
/* 349 */     NodeList nList = this.xmlElement.getChildNodes();
/* 350 */     int count = 0;
/*     */     
/* 352 */     for (int i = 0; i < nList.getLength(); i++) {
/* 353 */       Node node = nList.item(i);
/* 354 */       if (node instanceof Element && node.getLocalName().equals(property)) {
/*     */ 
/*     */         
/* 357 */         if (count == index) {
/* 358 */           return this.factory.newInstance(node, className);
/*     */         }
/* 360 */         count++;
/*     */       } 
/*     */     } 
/*     */     
/* 364 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateElementValue(String value) {
/* 373 */     Node textNode = this.xmlElement.getFirstChild();
/* 374 */     if (textNode == null) {
/* 375 */       this.factory.createText(this.xmlElement, value);
/*     */     } else {
/* 377 */       textNode.setNodeValue(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateElementValue(boolean value) {
/* 382 */     updateElementValue((new Boolean(value)).toString());
/*     */   }
/*     */   
/*     */   public void updateElementValue(int value) {
/* 386 */     updateElementValue((new Integer(value)).toString());
/*     */   }
/*     */   
/*     */   public void updateElementValue(float value) {
/* 390 */     updateElementValue((new Float(value)).toString());
/*     */   }
/*     */   
/*     */   public void updateElementValue(double value) {
/* 394 */     updateElementValue((new Double(value)).toString());
/*     */   }
/*     */   
/*     */   public void updateElementValue(long value) {
/* 398 */     updateElementValue((new Long(value)).toString());
/*     */   }
/*     */   
/*     */   public void updateElementValue(short value) {
/* 402 */     updateElementValue((new Short(value)).toString());
/*     */   }
/*     */   
/*     */   public void updateElementValue(byte value) {
/* 406 */     updateElementValue((new Byte(value)).toString());
/*     */   }
/*     */   
/*     */   public void updateElementValue(Date value) {
/* 410 */     updateElementValue(this.simpleDateFormat.format(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeElement(String elementName) {
/* 418 */     NodeList nodes = this.xmlElement.getChildNodes();
/*     */     
/* 420 */     for (int i = 0; i < nodes.getLength(); i++) {
/* 421 */       Node node = nodes.item(i);
/* 422 */       if (node instanceof Element && node.getLocalName().equals(elementName)) {
/*     */         
/*     */         try {
/* 425 */           this.xmlElement.removeChild(node);
/* 426 */           return true;
/* 427 */         } catch (DOMException ex) {}
/*     */       }
/*     */     } 
/*     */     
/* 431 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sizeOfElement(String name) {
/* 439 */     NodeList nodes = this.xmlElement.getChildNodes();
/* 440 */     int len = 0;
/* 441 */     for (int i = 0; i < nodes.getLength(); i++) {
/* 442 */       Node node = nodes.item(i);
/* 443 */       if (node instanceof Element && node.getLocalName().equals(name))
/* 444 */         len++; 
/*     */     } 
/* 446 */     return len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementName() {
/* 454 */     return this.xmlElement.getLocalName();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\BaseType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */