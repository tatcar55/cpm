/*     */ package com.sun.xml.rpc.processor.modeler.j2ee.xml;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComplexType
/*     */   extends BaseType
/*     */ {
/*     */   public boolean removeElement(int index, String elementName) {
/*  68 */     NodeList nodes = this.xmlElement.getElementsByTagName(elementName);
/*  69 */     int length = nodes.getLength();
/*     */     
/*  71 */     if (index < length) {
/*  72 */       Node deleteNode = nodes.item(index);
/*  73 */       if (deleteNode != null) {
/*     */         try {
/*  75 */           this.xmlElement.removeChild(deleteNode);
/*  76 */           return true;
/*  77 */         } catch (DOMException ex) {
/*  78 */           System.out.println("DOM Exception from removeElement." + ex);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  83 */     return false;
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
/*     */   public void setElementValue(int index, String elementName, BaseType baseType) {
/*  95 */     Element childXml = baseType.getXMLElement();
/*  96 */     insertXMLElementAtLocation(childXml, index, elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementValue(int index, String elementName, String elementValue) {
/* 104 */     Element newChild = this.factory.createXMLElementAndText(elementName, elementValue);
/*     */     
/* 106 */     insertXMLElementAtLocation(newChild, index, elementName);
/*     */   }
/*     */   
/*     */   public void setElementValue(int index, String elementName, boolean value) {
/* 110 */     setElementValue(index, elementName, (new Boolean(value)).toString());
/*     */   }
/*     */   
/*     */   public void setElementValue(int index, String elementName, int value) {
/* 114 */     setElementValue(index, elementName, (new Integer(value)).toString());
/*     */   }
/*     */   
/*     */   public void setElementValue(int index, String elementName, float value) {
/* 118 */     setElementValue(index, elementName, (new Float(value)).toString());
/*     */   }
/*     */   
/*     */   public void setElementValue(int index, String elementName, double value) {
/* 122 */     setElementValue(index, elementName, (new Double(value)).toString());
/*     */   }
/*     */   
/*     */   public void setElementValue(int index, String elementName, long value) {
/* 126 */     setElementValue(index, elementName, (new Long(value)).toString());
/*     */   }
/*     */   
/*     */   public void setElementValue(int index, String elementName, short value) {
/* 130 */     setElementValue(index, elementName, (new Short(value)).toString());
/*     */   }
/*     */   
/*     */   public void setElementValue(int index, String elementName, byte value) {
/* 134 */     setElementValue(index, elementName, (new Byte(value)).toString());
/*     */   }
/*     */   
/*     */   public void setElementValue(int index, String elementName, Date value) {
/* 138 */     setElementValue(index, elementName, this.simpleDateFormat.format(value));
/*     */   }
/*     */   
/*     */   public boolean getElementBooleanValue(String elementName, int index) {
/* 142 */     return (new Boolean(getElementValue(elementName, index))).booleanValue();
/*     */   }
/*     */   
/*     */   public int getElementIntegerValue(String elementName, int index) {
/* 146 */     return (new Integer(getElementValue(elementName, index))).intValue();
/*     */   }
/*     */   
/*     */   public float getElementFloatValue(String elementName, int index) {
/* 150 */     return (new Float(getElementValue(elementName, index))).floatValue();
/*     */   }
/*     */   
/*     */   public double getElementDoubleValue(String elementName, int index) {
/* 154 */     return (new Double(getElementValue(elementName, index))).doubleValue();
/*     */   }
/*     */   
/*     */   public long getElementLongValue(String elementName, int index) {
/* 158 */     return (new Long(getElementValue(elementName, index))).longValue();
/*     */   }
/*     */   
/*     */   public short getElementShortValue(String elementName, int index) {
/* 162 */     return (new Short(getElementValue(elementName, index))).shortValue();
/*     */   }
/*     */   
/*     */   public byte getElementByteValue(String elementName, int index) {
/* 166 */     return (new Byte(getElementValue(elementName, index))).byteValue();
/*     */   }
/*     */   
/*     */   public Date getElementDateValue(String elementName, int index) {
/*     */     try {
/* 171 */       String result = getElementValue(elementName, index);
/* 172 */       if (result != null) {
/* 173 */         return this.simpleDateFormat.parse(result);
/*     */       }
/* 175 */     } catch (ParseException ex) {
/* 176 */       System.out.println("getElementDateValue exception.." + ex);
/*     */     } 
/* 178 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributeValue(String attrname, String attrvalue) {
/* 187 */     if (this.xmlElement == null) {
/*     */       
/* 189 */       this.xmlAttr.setValue(attrvalue);
/*     */     } else {
/* 191 */       Attr attr = this.xmlElement.getAttributeNode(attrname);
/* 192 */       if (attr == null) {
/* 193 */         attr = this.factory.createAttribute(attrname, this.xmlElement);
/*     */       }
/* 195 */       attr.setValue(attrvalue);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributeValue(String attrname, SimpleType attrObject) {
/* 203 */     setAttributeValue(attrname, attrObject.getElementValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributeValue(String attrname, boolean attrObject) {
/* 210 */     setAttributeValue(attrname, (new Boolean(attrObject)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributeValue(String attrname, int attrObject) {
/* 217 */     setAttributeValue(attrname, (new Integer(attrObject)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributeValue(String attrname, float attrObject) {
/* 224 */     setAttributeValue(attrname, (new Float(attrObject)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributeValue(String attrname, double attrObject) {
/* 231 */     setAttributeValue(attrname, (new Double(attrObject)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributeValue(String attrname, long attrObject) {
/* 238 */     setAttributeValue(attrname, (new Long(attrObject)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributeValue(String attrname, short attrObject) {
/* 245 */     setAttributeValue(attrname, (new Short(attrObject)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributeValue(String attrname, byte attrObject) {
/* 252 */     setAttributeValue(attrname, (new Byte(attrObject)).toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributeValue(String attrname, Date attrObject) {
/* 259 */     setAttributeValue(attrname, this.simpleDateFormat.format(attrObject));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeValue(String attrname) {
/* 266 */     Attr attr = this.xmlElement.getAttributeNode(attrname);
/* 267 */     if (attr != null) {
/* 268 */       return attr.getValue();
/*     */     }
/* 270 */     return null;
/*     */   }
/*     */   
/*     */   public boolean getAttributeBooleanValue(String attrname) {
/* 274 */     return (new Boolean(getAttributeValue(attrname))).booleanValue();
/*     */   }
/*     */   
/*     */   public int getAttributeIntegerValue(String attrname) {
/* 278 */     return (new Integer(getAttributeValue(attrname))).intValue();
/*     */   }
/*     */   
/*     */   public float getAttributeFloatValue(String attrname) {
/* 282 */     return (new Float(getAttributeValue(attrname))).floatValue();
/*     */   }
/*     */   
/*     */   public double getAttributeDoubleValue(String attrname) {
/* 286 */     return (new Double(getAttributeValue(attrname))).doubleValue();
/*     */   }
/*     */   
/*     */   public long getAttributeLongValue(String attrname) {
/* 290 */     return (new Long(getAttributeValue(attrname))).longValue();
/*     */   }
/*     */   
/*     */   public short getAttributeShortValue(String attrname) {
/* 294 */     return (new Short(getAttributeValue(attrname))).shortValue();
/*     */   }
/*     */   
/*     */   public byte getAttributeByteValue(String attrname) {
/* 298 */     return (new Byte(getAttributeValue(attrname))).byteValue();
/*     */   }
/*     */   
/*     */   public Date getAttributeDateValue(String attrname) {
/*     */     try {
/* 303 */       String result = getAttributeValue(attrname);
/* 304 */       if (result != null) {
/* 305 */         return this.simpleDateFormat.parse(result);
/*     */       }
/* 307 */     } catch (ParseException ex) {
/* 308 */       System.out.println("getElementDateValue exception.." + ex);
/*     */     } 
/* 310 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAttributeValue(String property, String className) {
/* 317 */     Attr attr = this.xmlElement.getAttributeNode(property);
/* 318 */     return this.factory.newInstance(attr, className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAttribute(String attrname) {
/*     */     try {
/* 326 */       this.xmlElement.removeAttribute(attrname);
/* 327 */       return true;
/* 328 */     } catch (Exception ex) {
/* 329 */       return false;
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
/*     */   private void insertXMLElementAtLocation(Element newChild, int index, String elementName) {
/* 341 */     NodeList nodes = this.xmlElement.getElementsByTagName(elementName);
/* 342 */     int length = nodes.getLength();
/*     */     
/* 344 */     if (index >= length) {
/* 345 */       if (length > 0) {
/*     */ 
/*     */         
/* 348 */         Node lastContent = nodes.item(length - 1);
/* 349 */         if (lastContent != null) {
/* 350 */           Node nextSibling = lastContent.getNextSibling();
/* 351 */           if (nextSibling != null) {
/* 352 */             this.xmlElement.insertBefore(newChild, nextSibling);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 359 */       this.xmlElement.appendChild(newChild);
/*     */     } else {
/* 361 */       Node refChild = nodes.item(index);
/* 362 */       this.xmlElement.replaceChild(newChild, refChild);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAttributeNames() {
/* 370 */     NamedNodeMap nnm = this.xmlElement.getAttributes();
/* 371 */     int len = 0;
/* 372 */     if (nnm != null)
/* 373 */       len = nnm.getLength(); 
/* 374 */     String[] ret = new String[len];
/* 375 */     for (int i = 0; i < len; i++) {
/* 376 */       Attr attr = (Attr)nnm.item(i);
/* 377 */       ret[i] = attr.getName();
/*     */     } 
/* 379 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\ComplexType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */