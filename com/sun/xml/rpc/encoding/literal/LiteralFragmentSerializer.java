/*     */ package com.sun.xml.rpc.encoding.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializationException;
/*     */ import com.sun.xml.rpc.encoding.xsd.XSDConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.Node;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LiteralFragmentSerializer
/*     */   extends LiteralObjectSerializerBase
/*     */ {
/*     */   protected SOAPFactory soapFactory;
/*  47 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  50 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */     
/*     */     try {
/*  53 */       this.soapFactory = SOAPFactory.newInstance();
/*  54 */     } catch (SOAPException e) {}
/*     */   }
/*     */   
/*     */   private static final String FIRST_PREFIX = "ns";
/*     */   
/*     */   public LiteralFragmentSerializer(QName type, boolean isNullable, String encodingStyle) {
/*  60 */     this(type, isNullable, encodingStyle, SOAPVersion.SOAP_11);
/*     */   }
/*     */   
/*     */   public LiteralFragmentSerializer(QName type, boolean isNullable, String encodingStyle, SOAPVersion ver) {
/*  64 */     this(type, isNullable, encodingStyle, false, ver);
/*     */   }
/*     */   
/*     */   public LiteralFragmentSerializer(QName type, boolean isNullable, String encodingStyle, boolean encodeType, SOAPVersion ver) {
/*  68 */     super(type, isNullable, encodingStyle, encodeType);
/*  69 */     init(ver);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeAdditionalNamespaceDeclarations(Object obj, XMLWriter writer) throws Exception {}
/*     */ 
/*     */   
/*     */   protected boolean hasDefaultNamespace(SOAPElement element) {
/*  77 */     for (Iterator<Name> iter = element.getAllAttributes(); iter.hasNext(); ) {
/*  78 */       Name aname = iter.next();
/*  79 */       if (aname.getLocalName().equals("xmlns"))
/*  80 */         return true; 
/*     */     } 
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   protected void internalSerialize(Object obj, QName name, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/*  86 */     boolean pushedEncodingStyle = false;
/*  87 */     if (obj == null) {
/*  88 */       if (!this.isNullable) {
/*  89 */         throw new SerializationException("literal.unexpectedNull");
/*     */       }
/*     */       
/*  92 */       writer.startElement((name != null) ? name : this.type);
/*  93 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
/*  94 */       writer.endElement();
/*     */     } else {
/*     */       
/*  97 */       SOAPElement element = (SOAPElement)obj;
/*     */       
/*  99 */       Name elementName = element.getElementName();
/* 100 */       if (hasDefaultNamespace(element)) {
/* 101 */         writer.startElement(elementName.getLocalName(), elementName.getURI());
/*     */       } else {
/* 103 */         writer.startElement(elementName.getLocalName(), elementName.getURI(), elementName.getPrefix());
/*     */       } 
/* 105 */       for (Iterator<String> iterator1 = element.getNamespacePrefixes(); iterator1.hasNext(); ) {
/* 106 */         String prefix = iterator1.next();
/* 107 */         String uri = element.getNamespaceURI(prefix);
/* 108 */         String existingURI = writer.getURI(prefix);
/* 109 */         if (existingURI == null || !existingURI.equals(uri)) {
/* 110 */           writer.writeNamespaceDeclaration(prefix, uri);
/*     */         }
/*     */       } 
/*     */       
/* 114 */       if (this.encodingStyle != null) {
/* 115 */         pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */       }
/* 117 */       for (Iterator<Name> iterator = element.getAllAttributes(); iterator.hasNext(); ) {
/* 118 */         Name aname = iterator.next();
/* 119 */         String value = element.getAttributeValue(aname);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 130 */         writer.writeAttribute(aname.getLocalName(), aname.getURI(), value);
/*     */       } 
/*     */       
/* 133 */       for (Iterator<Node> iter = element.getChildElements(); iter.hasNext(); ) {
/* 134 */         Node node = iter.next();
/* 135 */         if (node instanceof Text) {
/* 136 */           Text text = (Text)node;
/* 137 */           if (text.isComment()) {
/*     */             continue;
/*     */           }
/* 140 */           if (text.getValue() != null)
/* 141 */             writer.writeChars(text.getValue()); 
/*     */           continue;
/*     */         } 
/* 144 */         if (node instanceof SOAPElement) {
/* 145 */           serialize(node, null, null, writer, context);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 152 */       writer.endElement();
/* 153 */       if (pushedEncodingStyle) {
/* 154 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Object doDeserialize(XMLReader reader, SOAPDeserializationContext context) throws Exception {
/*     */     SOAPElement element;
/* 161 */     String elementURI = reader.getURI();
/*     */ 
/*     */ 
/*     */     
/* 165 */     if (elementURI == null || elementURI.equals("") || elementURI.equals("http://schemas.xmlsoap.org/wsdl/")) {
/* 166 */       element = this.soapFactory.createElement(reader.getLocalName());
/*     */     } else {
/*     */       
/* 169 */       element = this.soapFactory.createElement(reader.getLocalName(), "ns", reader.getURI());
/*     */     } 
/*     */     
/* 172 */     String defaultURI = reader.getURI("");
/*     */ 
/*     */ 
/*     */     
/* 176 */     if (defaultURI != null) {
/* 177 */       element.addNamespaceDeclaration("", defaultURI);
/*     */     }
/*     */     
/* 180 */     for (Iterator<String> iter = reader.getPrefixes(); iter.hasNext(); ) {
/* 181 */       String prefix = iter.next();
/* 182 */       String uri = reader.getURI(prefix);
/* 183 */       element.addNamespaceDeclaration(prefix, uri);
/*     */     } 
/*     */     
/* 186 */     Attributes attributes = reader.getAttributes();
/* 187 */     for (int i = 0; i < attributes.getLength(); i++) {
/* 188 */       if (!attributes.isNamespaceDeclaration(i)) {
/*     */         Name name;
/*     */ 
/*     */ 
/*     */         
/* 193 */         String uri = attributes.getURI(i);
/* 194 */         if (uri == null) {
/*     */           
/* 196 */           name = this.soapFactory.createName(attributes.getLocalName(i));
/*     */         }
/*     */         else {
/*     */           
/* 200 */           String prefix = attributes.getPrefix(i);
/* 201 */           name = this.soapFactory.createName(attributes.getLocalName(i), prefix, uri);
/*     */         } 
/* 203 */         element.addAttribute(name, attributes.getValue(i));
/*     */       } 
/*     */     } 
/* 206 */     reader.next();
/* 207 */     while (reader.getState() != 2) {
/* 208 */       int state = reader.getState();
/* 209 */       if (state == 1) {
/* 210 */         SOAPElement child = (SOAPElement)deserialize(null, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 216 */         if (child != null) {
/* 217 */           element.addChildElement(child);
/*     */         } else {
/* 219 */           child = this.soapFactory.createElement(reader.getLocalName(), "ns", reader.getURI());
/* 220 */           element.addChildElement(child);
/*     */         }
/*     */       
/*     */       }
/* 224 */       else if (state == 3) {
/* 225 */         element.addTextNode(reader.getValue());
/*     */       } 
/*     */       
/* 228 */       reader.next();
/*     */     } 
/*     */     
/* 231 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAcceptableType(QName actualType) {
/* 246 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\literal\LiteralFragmentSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */