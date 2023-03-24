/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.X509Data;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class X509DataImpl extends X509DataTypeImpl implements X509Data, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return X509Data.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "X509Data";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "X509Data");
/*  39 */     super.serializeURIs(context);
/*  40 */     context.endNamespaceDecls();
/*  41 */     super.serializeAttributes(context);
/*  42 */     context.endAttributes();
/*  43 */     super.serializeBody(context);
/*  44 */     context.endElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  58 */     return X509Data.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsq\000~\000\rppsq\000~\000\rppsq\000~\000\rppsq\000~\000\rppsq\000~\000\000pp\000sq\000~\000\rppsq\000~\000\nsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\027psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\026\001q\000~\000\033sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\034q\000~\000!sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000#xq\000~\000\036t\000Icom.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType.X509IssuerSerialt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000pp\000sq\000~\000\rppsq\000~\000\nq\000~\000\027psq\000~\000\030q\000~\000\027pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\000@com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType.X509SKIq\000~\000&sq\000~\000\000pp\000sq\000~\000\rppsq\000~\000\nq\000~\000\027psq\000~\000\030q\000~\000\027pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\000Hcom.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType.X509SubjectNameq\000~\000&sq\000~\000\000pp\000sq\000~\000\rppsq\000~\000\nq\000~\000\027psq\000~\000\030q\000~\000\027pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\000Hcom.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType.X509Certificateq\000~\000&sq\000~\000\000pp\000sq\000~\000\rppsq\000~\000\nq\000~\000\027psq\000~\000\030q\000~\000\027pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\000@com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType.X509CRLq\000~\000&sq\000~\000\000pp\000sq\000~\000\030ppq\000~\000\033sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\001L\000\003nc2q\000~\000\001xq\000~\000\036q\000~\000\037sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\001L\000\003nc2q\000~\000\001xq\000~\000\036sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIq\000~\000#xq\000~\000\036t\000\000sq\000~\000Et\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000Eq\000~\000&sq\000~\000\rppsq\000~\000\030q\000~\000\027psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000#L\000\btypeNameq\000~\000#L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\027psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000#L\000\fnamespaceURIq\000~\000#xpq\000~\000Xq\000~\000Wsq\000~\000\"t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000!sq\000~\000\"t\000\bX509Dataq\000~\000Isr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\022\001pq\000~\000\024q\000~\000(q\000~\000.q\000~\000\021q\000~\0004q\000~\000:q\000~\000\tq\000~\000\025q\000~\000)q\000~\000/q\000~\0005q\000~\000;q\000~\000\016q\000~\000\fq\000~\000\017q\000~\000Kq\000~\000\022q\000~\000\020x");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final X509DataImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 131 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 135 */       this(context);
/* 136 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 140 */       return X509DataImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 149 */       switch (this.state) {
/*     */         case 1:
/* 151 */           if ("X509IssuerSerial" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 152 */             X509DataImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new X509DataTypeImpl.Unmarshaller(X509DataImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 155 */           if ("X509SKI" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 156 */             X509DataImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new X509DataTypeImpl.Unmarshaller(X509DataImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 159 */           if ("X509SubjectName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 160 */             X509DataImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new X509DataTypeImpl.Unmarshaller(X509DataImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 163 */           if ("X509Certificate" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 164 */             X509DataImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new X509DataTypeImpl.Unmarshaller(X509DataImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 167 */           if ("X509CRL" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 168 */             X509DataImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new X509DataTypeImpl.Unmarshaller(X509DataImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 171 */           if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 172 */             X509DataImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new X509DataTypeImpl.Unmarshaller(X509DataImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 177 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 180 */           if ("X509Data" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 181 */             this.context.pushAttributes(__atts, false);
/* 182 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 187 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/* 198 */       switch (this.state) {
/*     */         case 2:
/* 200 */           if ("X509Data" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 201 */             this.context.popAttributes();
/* 202 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 207 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 210 */       super.leaveElement(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 221 */       switch (this.state) {
/*     */         case 3:
/* 223 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 226 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 237 */       switch (this.state) {
/*     */         case 3:
/* 239 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 242 */       super.leaveAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleText(String value) throws SAXException {
/*     */       try {
/* 254 */         switch (this.state) {
/*     */           case 3:
/* 256 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 259 */       } catch (RuntimeException e) {
/* 260 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\X509DataImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */