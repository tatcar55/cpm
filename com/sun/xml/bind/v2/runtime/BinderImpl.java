/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.unmarshaller.InfosetScanner;
/*     */ import com.sun.xml.bind.v2.runtime.output.DOMOutput;
/*     */ import com.sun.xml.bind.v2.runtime.output.XmlOutput;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.InterningXmlVisitor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.SAXConnector;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.XmlVisitor;
/*     */ import javax.xml.bind.Binder;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.validation.Schema;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinderImpl<XmlNode>
/*     */   extends Binder<XmlNode>
/*     */ {
/*     */   private final JAXBContextImpl context;
/*     */   private UnmarshallerImpl unmarshaller;
/*     */   private MarshallerImpl marshaller;
/*     */   private final InfosetScanner<XmlNode> scanner;
/*  96 */   private final AssociationMap<XmlNode> assoc = new AssociationMap<XmlNode>();
/*     */   
/*     */   BinderImpl(JAXBContextImpl _context, InfosetScanner<XmlNode> scanner) {
/*  99 */     this.context = _context;
/* 100 */     this.scanner = scanner;
/*     */   }
/*     */   
/*     */   private UnmarshallerImpl getUnmarshaller() {
/* 104 */     if (this.unmarshaller == null)
/* 105 */       this.unmarshaller = new UnmarshallerImpl(this.context, this.assoc); 
/* 106 */     return this.unmarshaller;
/*     */   }
/*     */   
/*     */   private MarshallerImpl getMarshaller() {
/* 110 */     if (this.marshaller == null)
/* 111 */       this.marshaller = new MarshallerImpl(this.context, this.assoc); 
/* 112 */     return this.marshaller;
/*     */   }
/*     */   
/*     */   public void marshal(Object jaxbObject, XmlNode xmlNode) throws JAXBException {
/* 116 */     if (xmlNode == null || jaxbObject == null)
/* 117 */       throw new IllegalArgumentException(); 
/* 118 */     getMarshaller().marshal(jaxbObject, (XmlOutput)createOutput(xmlNode));
/*     */   }
/*     */ 
/*     */   
/*     */   private DOMOutput createOutput(XmlNode xmlNode) {
/* 123 */     return new DOMOutput((Node)xmlNode, this.assoc);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object updateJAXB(XmlNode xmlNode) throws JAXBException {
/* 128 */     return associativeUnmarshal(xmlNode, true, null);
/*     */   }
/*     */   
/*     */   public Object unmarshal(XmlNode xmlNode) throws JAXBException {
/* 132 */     return associativeUnmarshal(xmlNode, false, null);
/*     */   }
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(XmlNode xmlNode, Class<T> expectedType) throws JAXBException {
/* 136 */     if (expectedType == null) throw new IllegalArgumentException(); 
/* 137 */     return (JAXBElement<T>)associativeUnmarshal(xmlNode, true, expectedType);
/*     */   }
/*     */   
/*     */   public void setSchema(Schema schema) {
/* 141 */     getMarshaller().setSchema(schema);
/* 142 */     getUnmarshaller().setSchema(schema);
/*     */   }
/*     */   
/*     */   public Schema getSchema() {
/* 146 */     return getUnmarshaller().getSchema();
/*     */   }
/*     */   
/*     */   private Object associativeUnmarshal(XmlNode xmlNode, boolean inplace, Class<?> expectedType) throws JAXBException {
/* 150 */     if (xmlNode == null) {
/* 151 */       throw new IllegalArgumentException();
/*     */     }
/* 153 */     JaxBeanInfo<?> bi = null;
/* 154 */     if (expectedType != null) {
/* 155 */       bi = this.context.getBeanInfo(expectedType, true);
/*     */     }
/* 157 */     InterningXmlVisitor handler = new InterningXmlVisitor(getUnmarshaller().createUnmarshallerHandler(this.scanner, inplace, bi));
/*     */     
/* 159 */     this.scanner.setContentHandler((ContentHandler)new SAXConnector((XmlVisitor)handler, this.scanner.getLocator()));
/*     */     try {
/* 161 */       this.scanner.scan(xmlNode);
/* 162 */     } catch (SAXException e) {
/* 163 */       throw this.unmarshaller.createUnmarshalException(e);
/*     */     } 
/*     */     
/* 166 */     return handler.getContext().getResult();
/*     */   }
/*     */   
/*     */   public XmlNode getXMLNode(Object jaxbObject) {
/* 170 */     if (jaxbObject == null)
/* 171 */       throw new IllegalArgumentException(); 
/* 172 */     AssociationMap.Entry<XmlNode> e = this.assoc.byPeer(jaxbObject);
/* 173 */     if (e == null) return null; 
/* 174 */     return e.element();
/*     */   }
/*     */   
/*     */   public Object getJAXBNode(XmlNode xmlNode) {
/* 178 */     if (xmlNode == null)
/* 179 */       throw new IllegalArgumentException(); 
/* 180 */     AssociationMap.Entry<XmlNode> e = this.assoc.byElement(xmlNode);
/* 181 */     if (e == null) return null; 
/* 182 */     if (e.outer() != null) return e.outer(); 
/* 183 */     return e.inner();
/*     */   }
/*     */   
/*     */   public XmlNode updateXML(Object jaxbObject) throws JAXBException {
/* 187 */     return updateXML(jaxbObject, getXMLNode(jaxbObject));
/*     */   }
/*     */   
/*     */   public XmlNode updateXML(Object jaxbObject, XmlNode xmlNode) throws JAXBException {
/* 191 */     if (jaxbObject == null || xmlNode == null) throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     Element e = (Element)xmlNode;
/* 197 */     Node ns = e.getNextSibling();
/* 198 */     Node p = e.getParentNode();
/* 199 */     p.removeChild(e);
/*     */ 
/*     */ 
/*     */     
/* 203 */     JaxBeanInfo bi = this.context.getBeanInfo(jaxbObject, true);
/* 204 */     if (!bi.isElement()) {
/* 205 */       jaxbObject = new JAXBElement(new QName(e.getNamespaceURI(), e.getLocalName()), bi.jaxbType, jaxbObject);
/*     */     }
/*     */     
/* 208 */     getMarshaller().marshal(jaxbObject, p);
/* 209 */     Node newNode = p.getLastChild();
/* 210 */     p.removeChild(newNode);
/* 211 */     p.insertBefore(newNode, ns);
/*     */     
/* 213 */     return (XmlNode)newNode;
/*     */   }
/*     */   
/*     */   public void setEventHandler(ValidationEventHandler handler) throws JAXBException {
/* 217 */     getUnmarshaller().setEventHandler(handler);
/* 218 */     getMarshaller().setEventHandler(handler);
/*     */   }
/*     */   
/*     */   public ValidationEventHandler getEventHandler() {
/* 222 */     return getUnmarshaller().getEventHandler();
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) throws PropertyException {
/* 226 */     if (name == null) {
/* 227 */       throw new IllegalArgumentException(Messages.NULL_PROPERTY_NAME.format(new Object[0]));
/*     */     }
/*     */     
/* 230 */     if (excludeProperty(name)) {
/* 231 */       throw new PropertyException(name);
/*     */     }
/*     */     
/* 234 */     Object prop = null;
/* 235 */     PropertyException pe = null;
/*     */     
/*     */     try {
/* 238 */       prop = getMarshaller().getProperty(name);
/* 239 */       return prop;
/* 240 */     } catch (PropertyException p) {
/* 241 */       pe = p;
/*     */ 
/*     */       
/*     */       try {
/* 245 */         prop = getUnmarshaller().getProperty(name);
/* 246 */         return prop;
/* 247 */       } catch (PropertyException propertyException) {
/* 248 */         pe = propertyException;
/*     */ 
/*     */         
/* 251 */         pe.setStackTrace(Thread.currentThread().getStackTrace());
/* 252 */         throw pe;
/*     */       } 
/*     */     } 
/*     */   } public void setProperty(String name, Object value) throws PropertyException {
/* 256 */     if (name == null) {
/* 257 */       throw new IllegalArgumentException(Messages.NULL_PROPERTY_NAME.format(new Object[0]));
/*     */     }
/*     */     
/* 260 */     if (excludeProperty(name)) {
/* 261 */       throw new PropertyException(name, value);
/*     */     }
/*     */     
/* 264 */     PropertyException pe = null;
/*     */     
/*     */     try {
/* 267 */       getMarshaller().setProperty(name, value);
/*     */       return;
/* 269 */     } catch (PropertyException p) {
/* 270 */       pe = p;
/*     */ 
/*     */       
/*     */       try {
/* 274 */         getUnmarshaller().setProperty(name, value);
/*     */         return;
/* 276 */       } catch (PropertyException propertyException) {
/* 277 */         pe = propertyException;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 282 */         pe.setStackTrace(Thread.currentThread().getStackTrace());
/* 283 */         throw pe;
/*     */       } 
/*     */     } 
/*     */   } private boolean excludeProperty(String name) {
/* 287 */     return (name.equals("com.sun.xml.bind.characterEscapeHandler") || name.equals("com.sun.xml.bind.xmlDeclaration") || name.equals("com.sun.xml.bind.xmlHeaders"));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\BinderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */