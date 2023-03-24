/*     */ package com.sun.xml.wss.impl;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.DSAKeyValue;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.RSAKeyValue;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.security.spec.DSAPublicKeySpec;
/*     */ import java.security.spec.RSAPublicKeySpec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLUtil
/*     */ {
/* 106 */   protected static final Logger logger = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*     */   protected static SOAPFactory soapFactory;
/*     */ 
/*     */   
/*     */   static class XMLHandler
/*     */     extends DefaultHandler
/*     */   {
/*     */     String read(Reader aReader) {
/* 116 */       StringBuffer sb = new StringBuffer();
/*     */       
/*     */       try {
/* 119 */         BufferedReader bReader = new BufferedReader(aReader);
/* 120 */         char[] data = new char[2048];
/* 121 */         int count = 0;
/*     */         
/* 123 */         while ((count = bReader.read(data)) != -1) {
/* 124 */           sb.append(data, 0, count);
/*     */         }
/*     */ 
/*     */         
/* 128 */         bReader.close();
/* 129 */         aReader.close();
/* 130 */       } catch (IOException e) {}
/*     */ 
/*     */ 
/*     */       
/* 134 */       return sb.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String read(String fileName) {
/* 141 */       return read(fileName, XMLUtil.class);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String read(String fileName, Class cl) {
/* 152 */       String data = "";
/*     */       
/*     */       try {
/* 155 */         InputStream in = cl.getResourceAsStream(fileName);
/*     */ 
/*     */         
/* 158 */         if (in == null) {
/*     */           
/*     */           try {
/* 161 */             in = new FileInputStream(fileName);
/* 162 */           } catch (FileNotFoundException e) {
/*     */ 
/*     */             
/* 165 */             String directoryURL = cl.getProtectionDomain().getCodeSource().getLocation().toString();
/*     */ 
/*     */             
/* 168 */             String fileURL = directoryURL + fileName;
/* 169 */             URL url = new URL(fileURL);
/* 170 */             in = url.openStream();
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 175 */         data = read(new InputStreamReader(in));
/* 176 */         in.close();
/* 177 */       } catch (MalformedURLException e) {
/*     */       
/* 179 */       } catch (IOException e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 184 */       return data;
/*     */     }
/*     */     public InputSource resolveEntity(String aPublicID, String aSystemID) {
/* 187 */       String sysid = aSystemID.trim();
/*     */       
/* 189 */       if (sysid.toLowerCase().startsWith("jar://")) {
/* 190 */         String dtdname = sysid.substring(5);
/* 191 */         String dtdValue = read(dtdname).trim();
/*     */         
/* 193 */         return new InputSource(new StringReader(dtdValue));
/*     */       } 
/*     */       
/* 196 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 204 */       soapFactory = SOAPFactory.newInstance();
/* 205 */     } catch (SOAPException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean validating = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SOAPElement convertToSoapElement(Document doc, Element elem) throws DOMException, ClassCastException {
/* 224 */     if (elem instanceof SOAPElement)
/* 225 */       return (SOAPElement)elem; 
/* 226 */     return (SOAPElement)doc.importNode(elem, true);
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
/*     */   public static List getElementsByTagNameNS1(Element element, String nsName, String tagName) {
/* 241 */     List<Node> list = new ArrayList();
/* 242 */     if (element != null) {
/* 243 */       NodeList nl = element.getChildNodes();
/* 244 */       int length = nl.getLength();
/* 245 */       Node child = null;
/*     */ 
/*     */ 
/*     */       
/* 249 */       for (int i = 0; i < length; i++) {
/* 250 */         child = nl.item(i);
/* 251 */         String childName = child.getLocalName();
/* 252 */         String childNS = child.getNamespaceURI();
/*     */         
/* 254 */         if (childName != null && childName.equals(tagName) && childNS != null && childNS.equals(nsName))
/*     */         {
/* 256 */           list.add(child);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     return list;
/*     */   }
/*     */   
/*     */   public static String getFullTextFromChildren(Element element) {
/* 265 */     if (element == null) {
/* 266 */       return null;
/*     */     }
/*     */     
/* 269 */     StringBuffer sb = new StringBuffer(1000);
/* 270 */     NodeList nl = element.getChildNodes();
/* 271 */     Node child = null;
/* 272 */     int length = nl.getLength();
/*     */     
/* 274 */     for (int i = 0; i < length; i++) {
/* 275 */       child = nl.item(i);
/*     */       
/* 277 */       if (child.getNodeType() == 3) {
/* 278 */         sb.append(child.getNodeValue());
/*     */       }
/*     */     } 
/*     */     
/* 282 */     return sb.toString().trim();
/*     */   }
/*     */   
/*     */   public static boolean inEncryptionNS(SOAPElement element) {
/* 286 */     return element.getNamespaceURI().equals("http://www.w3.org/2001/04/xmlenc#");
/*     */   }
/*     */   
/*     */   public static boolean inSamlNSv1_0(SOAPElement element) {
/* 290 */     return element.getNamespaceURI().equals("urn:oasis:names:tc:SAML:1.0:assertion");
/*     */   }
/*     */   
/*     */   public static boolean inSamlNSv2_0(SOAPElement element) {
/* 294 */     return element.getNamespaceURI().equals("urn:oasis:names:tc:SAML:2.0:assertion");
/*     */   }
/*     */   
/*     */   public static boolean inSamlNSv1_1(SOAPElement element) {
/* 298 */     return element.getNamespaceURI().equals("urn:oasis:names:tc:SAML:1.0:assertion");
/*     */   }
/*     */   
/*     */   public static boolean inSignatureNS(SOAPElement element) {
/* 302 */     return element.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#");
/*     */   }
/*     */   
/*     */   public static boolean inWsseNS(SOAPElement element) {
/* 306 */     return element.getNamespaceURI().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
/*     */   }
/*     */   
/*     */   public static boolean inWsscNS(SOAPElement element) {
/* 310 */     return element.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2005/02/sc");
/*     */   }
/*     */   
/*     */   public static boolean inWsse11NS(SOAPElement element) {
/* 314 */     return element.getNamespaceURI().equals("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd");
/*     */   }
/*     */   
/*     */   public static boolean inWSS11_NS(SOAPElement element) {
/* 318 */     return element.getNamespaceURI().equals("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean inWsuNS(SOAPElement element) {
/* 323 */     return element.getNamespaceURI().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */   }
/*     */   
/*     */   public static String resolveXPath(Node element) throws Exception {
/* 327 */     if (element.getOwnerDocument() == null) {
/* 328 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_0424_NULL_OWNER_DOCUMENT_ELEMENT());
/* 329 */       throw new Exception("Element does not have an owner document");
/*     */     } 
/*     */     
/* 332 */     StringBuffer xpath = new StringBuffer();
/* 333 */     String prefix = element.getPrefix();
/* 334 */     String lcname = element.getLocalName();
/* 335 */     String lxpath = prefix + ":" + lcname;
/* 336 */     xpath.append(lxpath);
/* 337 */     Node parentNode = element.getParentNode();
/* 338 */     while (parentNode != null && parentNode.getNodeType() != 9) {
/* 339 */       prefix = parentNode.getPrefix();
/* 340 */       lcname = parentNode.getLocalName();
/* 341 */       lxpath = prefix + ":" + lcname + "/";
/* 342 */       xpath.insert(0, lxpath);
/* 343 */       parentNode = parentNode.getParentNode();
/*     */     } 
/* 345 */     xpath.insert(0, "./");
/* 346 */     return xpath.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element prependChildElement(Element parent, Element child, boolean addWhitespace, Document doc) {
/* 355 */     Node firstChild = parent.getFirstChild();
/* 356 */     if (firstChild == null) {
/* 357 */       parent.appendChild(child);
/*     */     } else {
/* 359 */       parent.insertBefore(child, firstChild);
/*     */     } 
/*     */     
/* 362 */     if (addWhitespace) {
/* 363 */       Node whitespaceText = doc.createTextNode("\n");
/* 364 */       parent.insertBefore(whitespaceText, child);
/*     */     } 
/* 366 */     return child;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Element prependChildElement(Element parent, Element child, Document doc) {
/* 371 */     return prependChildElement(parent, child, true, doc);
/*     */   }
/*     */   
/*     */   public static String print(Node node) {
/*     */     NamedNodeMap attrs;
/*     */     NodeList children;
/*     */     String data;
/*     */     int length, i;
/*     */     NodeList nodeList1;
/* 380 */     if (node == null) {
/* 381 */       return null;
/*     */     }
/*     */     
/* 384 */     StringBuffer xml = new StringBuffer(100);
/* 385 */     int type = node.getNodeType();
/*     */     
/* 387 */     switch (type) {
/*     */       
/*     */       case 1:
/* 390 */         xml.append('<');
/* 391 */         xml.append(node.getNodeName());
/*     */         
/* 393 */         attrs = node.getAttributes();
/* 394 */         length = attrs.getLength();
/*     */ 
/*     */         
/* 397 */         for (i = 0; i < length; i++) {
/* 398 */           Attr attr = (Attr)attrs.item(i);
/* 399 */           xml.append(' ');
/* 400 */           xml.append(attr.getNodeName());
/* 401 */           xml.append("=\"");
/*     */ 
/*     */           
/* 404 */           xml.append(attr.getNodeValue());
/* 405 */           xml.append('"');
/*     */         } 
/*     */         
/* 408 */         xml.append('>');
/*     */         
/* 410 */         nodeList1 = node.getChildNodes();
/*     */         
/* 412 */         if (nodeList1 != null) {
/* 413 */           int len = nodeList1.getLength();
/*     */           
/* 415 */           for (int j = 0; j < len; j++) {
/* 416 */             xml.append(print(nodeList1.item(j)));
/*     */           }
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 425 */         children = node.getChildNodes();
/*     */         
/* 427 */         if (children != null) {
/* 428 */           int len = children.getLength();
/*     */           
/* 430 */           for (int j = 0; j < len; j++) {
/* 431 */             xml.append(print(children.item(j)));
/*     */           }
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 440 */         xml.append("<![CDATA[");
/* 441 */         xml.append(node.getNodeValue());
/* 442 */         xml.append("]]>");
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 450 */         xml.append(node.getNodeValue());
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 7:
/* 457 */         xml.append("<?");
/* 458 */         xml.append(node.getNodeName());
/*     */         
/* 460 */         data = node.getNodeValue();
/*     */         
/* 462 */         if (data != null && data.length() > 0) {
/* 463 */           xml.append(' ');
/* 464 */           xml.append(data);
/*     */         } 
/*     */         
/* 467 */         xml.append("?>");
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 473 */     if (type == 1) {
/* 474 */       xml.append("</");
/* 475 */       xml.append(node.getNodeName());
/* 476 */       xml.append('>');
/*     */     } 
/*     */     
/* 479 */     return xml.toString();
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
/*     */   public static void setWsuIdAttr(Element element, String wsuId) {
/* 497 */     element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */ 
/*     */ 
/*     */     
/* 501 */     element.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", wsuId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setIdAttr(Element element, String Id) {
/* 508 */     element.setAttribute("Id", Id);
/* 509 */     element.setIdAttribute("Id", true);
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
/*     */   public static Document toDOMDocument(InputStream is) {
/* 523 */     DocumentBuilderFactory dbFactory = null;
/*     */ 
/*     */     
/*     */     try {
/* 527 */       dbFactory = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/*     */       
/* 529 */       dbFactory.setValidating(validating);
/* 530 */       dbFactory.setNamespaceAware(true);
/* 531 */     } catch (Exception e) {}
/*     */ 
/*     */     
/*     */     try {
/* 535 */       DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
/*     */       
/* 537 */       if (documentBuilder == null) {
/* 538 */         return null;
/*     */       }
/*     */       
/* 541 */       documentBuilder.setEntityResolver(new XMLHandler());
/*     */ 
/*     */       
/* 544 */       return documentBuilder.parse(is);
/* 545 */     } catch (Exception e) {
/*     */ 
/*     */ 
/*     */       
/* 549 */       return null;
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
/*     */   public static Document toDOMDocument(String xmlString) {
/* 562 */     if (xmlString == null) {
/* 563 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 567 */       ByteArrayInputStream is = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
/*     */ 
/*     */       
/* 570 */       return toDOMDocument(is);
/* 571 */     } catch (UnsupportedEncodingException uee) {
/* 572 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document newDocument() throws ParserConfigurationException {
/* 583 */     DocumentBuilderFactory dbFactory = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 584 */     dbFactory.setNamespaceAware(true);
/* 585 */     dbFactory.setValidating(validating);
/*     */     
/* 587 */     return dbFactory.newDocumentBuilder().newDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasElementChild(Node node) {
/* 596 */     NodeList nl = node.getChildNodes();
/* 597 */     Node child = null;
/* 598 */     int length = nl.getLength();
/*     */     
/* 600 */     for (int i = 0; i < length; i++) {
/* 601 */       child = nl.item(i);
/*     */       
/* 603 */       if (child.getNodeType() == 1) {
/* 604 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 608 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static DSAKeyValue getDSAKeyValue(Document doc, X509Certificate cert) throws XWSSecurityException {
/*     */     try {
/* 614 */       KeyFactory keyFactory = KeyFactory.getInstance("DSA");
/* 615 */       DSAPublicKeySpec dsaPkSpec = keyFactory.<DSAPublicKeySpec>getKeySpec(cert.getPublicKey(), DSAPublicKeySpec.class);
/*     */ 
/*     */       
/* 618 */       return new DSAKeyValue(doc, dsaPkSpec.getP(), dsaPkSpec.getQ(), dsaPkSpec.getG(), dsaPkSpec.getY());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 626 */     catch (Exception e) {
/* 627 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_0426_FAILED_DSA_KEY_VALUE(), e);
/* 628 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static RSAKeyValue getRSAKeyValue(Document doc, X509Certificate cert) throws XWSSecurityException {
/*     */     try {
/* 635 */       KeyFactory keyFactory = KeyFactory.getInstance("RSA");
/* 636 */       RSAPublicKeySpec rsaPkSpec = keyFactory.<RSAPublicKeySpec>getKeySpec(cert.getPublicKey(), RSAPublicKeySpec.class);
/*     */ 
/*     */       
/* 639 */       return new RSAKeyValue(doc, rsaPkSpec.getModulus(), rsaPkSpec.getPublicExponent());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 646 */     catch (Exception e) {
/* 647 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_0293_FAILED_RSA_KEY_VALUE(), e);
/* 648 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static X509Data getX509Data(Document doc, X509Certificate cert) throws XWSSecurityException {
/*     */     try {
/* 657 */       X509Data x509Data = new X509Data(doc);
/* 658 */       x509Data.addCertificate(cert);
/* 659 */       return x509Data;
/* 660 */     } catch (Exception e) {
/* 661 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_0294_FAILED_X_509_DATA(), e);
/* 662 */       throw new XWSSecurityException(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String convertToXpath(String qname) {
/* 744 */     QName name = QName.valueOf(qname);
/* 745 */     if ("".equals(name.getNamespaceURI())) {
/* 746 */       return "//" + name.getLocalPart();
/*     */     }
/* 748 */     return "//*[local-name()='" + name.getLocalPart() + "' and namespace-uri()='" + name.getNamespaceURI() + "']";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getDecodedBase64EncodedData(String encodedData) throws XWSSecurityException {
/*     */     try {
/* 758 */       return Base64.decode(encodedData);
/* 759 */     } catch (Base64DecodingException e) {
/* 760 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_0427_UNABLETO_DECODE_BASE_64(), e);
/* 761 */       throw new XWSSecurityException("Unable to decode Base64 encoded data", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document getOwnerDocument(Node node) {
/* 768 */     if (node.getNodeType() == 9) {
/* 769 */       return (Document)node;
/*     */     }
/* 771 */     return node.getOwnerDocument();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Element getFirstChildElement(Node node) {
/* 776 */     Node child = node.getFirstChild();
/* 777 */     while (child != null && child.getNodeType() != 1) {
/* 778 */       child = child.getNextSibling();
/*     */     }
/* 780 */     return (Element)child;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Element createElement(Document doc, String tag, String nsURI, String prefix) {
/* 785 */     String qName = (prefix == null) ? tag : (prefix + ":" + tag);
/* 786 */     return doc.createElementNS(nsURI, qName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\XMLUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */