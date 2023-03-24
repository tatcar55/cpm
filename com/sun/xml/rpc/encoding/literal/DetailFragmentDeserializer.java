/*     */ package com.sun.xml.rpc.encoding.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializerBase;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.streaming.XmlTreeReader;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.DetailEntry;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DetailFragmentDeserializer
/*     */   extends LiteralObjectSerializerBase
/*     */ {
/*     */   protected SOAPFactory soapFactory;
/*     */   private static final String FIRST_PREFIX = "ns";
/*     */   
/*     */   public DetailFragmentDeserializer(QName type, String encodingStyle) {
/*  59 */     this(type, false, encodingStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DetailFragmentDeserializer(QName type, boolean isNillable, String encodingStyle) {
/*  66 */     super(type, isNillable, encodingStyle);
/*     */ 
/*     */     
/*     */     try {
/*  70 */       this.soapFactory = SOAPFactory.newInstance();
/*     */     }
/*  72 */     catch (SOAPException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeAdditionalNamespaceDeclarations(Object obj, XMLWriter writer) throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object internalDeserialize(QName name, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/*  90 */     boolean pushedEncodingStyle = context.processEncodingStyle(reader);
/*     */     try {
/*  92 */       context.verifyEncodingStyle(this.encodingStyle);
/*     */       
/*  94 */       if (name != null) {
/*  95 */         QName actualName = reader.getName();
/*  96 */         if (!actualName.equals(name)) {
/*  97 */           throw new DeserializationException("xsd.unexpectedElementName", new Object[] { name.toString(), actualName.toString() });
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 103 */       Attributes attrs = reader.getAttributes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       String nullVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
/* 118 */       boolean isNull = (nullVal != null && SerializerBase.decodeBoolean(nullVal));
/*     */       
/* 120 */       Object obj = null;
/*     */       
/* 122 */       if (isNull) {
/* 123 */         if (!this.isNullable) {
/* 124 */           throw new DeserializationException("xsd.unexpectedNull");
/*     */         }
/* 126 */         reader.next();
/*     */       } else {
/* 128 */         obj = doDeserialize(reader, context);
/*     */       } 
/*     */       
/* 131 */       XMLReaderUtil.verifyReaderState(reader, 2);
/* 132 */       return obj;
/*     */     } finally {
/* 134 */       if (pushedEncodingStyle) {
/* 135 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object deserializeElement(QName name, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 146 */     boolean pushedEncodingStyle = context.processEncodingStyle(reader);
/*     */     try {
/* 148 */       context.verifyEncodingStyle(this.encodingStyle);
/*     */       
/* 150 */       if (name != null) {
/* 151 */         QName actualName = reader.getName();
/* 152 */         if (!actualName.equals(name)) {
/* 153 */           throw new DeserializationException("xsd.unexpectedElementName", new Object[] { name.toString(), actualName.toString() });
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 159 */       Attributes attrs = reader.getAttributes();
/*     */       
/* 161 */       String nullVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
/* 162 */       boolean isNull = (nullVal != null && SerializerBase.decodeBoolean(nullVal));
/*     */       
/* 164 */       Object obj = null;
/*     */       
/* 166 */       if (isNull) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 171 */         reader.next();
/*     */       } else {
/* 173 */         obj = doDeserializeElement(reader, context);
/*     */       } 
/*     */       
/* 176 */       XMLReaderUtil.verifyReaderState(reader, 2);
/* 177 */       return obj;
/*     */     } finally {
/* 179 */       if (pushedEncodingStyle) {
/* 180 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object doDeserialize(XMLReader reader, SOAPDeserializationContext context) throws Exception {
/*     */     Name name;
/* 191 */     Detail detail = this.soapFactory.createDetail();
/* 192 */     String elementURI = reader.getURI();
/*     */     
/* 194 */     if (elementURI == null || elementURI.equals("")) {
/* 195 */       name = this.soapFactory.createName(reader.getLocalName());
/*     */     } else {
/* 197 */       name = this.soapFactory.createName(reader.getLocalName(), "ns", elementURI);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     DetailEntry entry = detail.addDetailEntry(name);
/* 204 */     doDeserializeElement(entry, reader, context);
/* 205 */     return detail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object doDeserializeElement(XMLReader reader, SOAPDeserializationContext context) throws Exception {
/*     */     SOAPElement element;
/* 214 */     String elementURI = reader.getURI();
/* 215 */     if (elementURI == null || elementURI.equals("")) {
/* 216 */       element = this.soapFactory.createElement(reader.getLocalName());
/*     */     }
/* 218 */     else if (reader instanceof XmlTreeReader) {
/* 219 */       SOAPElement soapElement = (SOAPElement)((XmlTreeReader)reader).getCurrentNode();
/*     */       
/* 221 */       if (soapElement != null && soapElement.getPrefix() == null) {
/* 222 */         element = this.soapFactory.createElement(reader.getLocalName(), null, null);
/*     */       } else {
/*     */         
/* 225 */         element = this.soapFactory.createElement(reader.getLocalName(), "ns", reader.getURI());
/*     */       } 
/*     */     } else {
/*     */       
/* 229 */       element = this.soapFactory.createElement(reader.getLocalName(), "ns", reader.getURI());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     doDeserializeElement(element, reader, context);
/* 236 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDeserializeElement(SOAPElement element, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 245 */     String defaultURI = reader.getURI("");
/* 246 */     if (defaultURI != null) {
/* 247 */       element.addNamespaceDeclaration("", defaultURI);
/*     */     }
/*     */     
/* 250 */     for (Iterator<String> iter = reader.getPrefixes(); iter.hasNext(); ) {
/* 251 */       String prefix = iter.next();
/* 252 */       String uri = reader.getURI(prefix);
/* 253 */       element.addNamespaceDeclaration(prefix, uri);
/*     */     } 
/*     */     
/* 256 */     Attributes attributes = reader.getAttributes();
/* 257 */     for (int i = 0; i < attributes.getLength(); i++) {
/* 258 */       if (!attributes.isNamespaceDeclaration(i)) {
/*     */         Name name;
/*     */ 
/*     */ 
/*     */         
/* 263 */         String uri = attributes.getURI(i);
/* 264 */         if (uri == null) {
/*     */           
/* 266 */           name = this.soapFactory.createName(attributes.getLocalName(i));
/*     */         } else {
/*     */           
/* 269 */           String prefix = attributes.getPrefix(i);
/* 270 */           name = this.soapFactory.createName(attributes.getLocalName(i), prefix, uri);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 276 */         element.addAttribute(name, attributes.getValue(i));
/*     */       } 
/*     */     } 
/* 279 */     reader.next();
/* 280 */     while (reader.getState() != 2) {
/* 281 */       int state = reader.getState();
/* 282 */       if (state == 1) {
/* 283 */         SOAPElement child = (SOAPElement)deserializeElement((QName)null, reader, context);
/*     */         
/* 285 */         element.addChildElement(child);
/* 286 */       } else if (state == 3) {
/* 287 */         element.addTextNode(reader.getValue());
/*     */       } 
/*     */       
/* 290 */       reader.next();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {}
/*     */   
/*     */   protected void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\literal\DetailFragmentDeserializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */