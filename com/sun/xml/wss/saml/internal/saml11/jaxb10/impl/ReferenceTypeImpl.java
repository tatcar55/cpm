/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.datatype.xsd.Base64BinaryType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.DigestMethodType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.ReferenceType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformsType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ReferenceTypeImpl implements ReferenceType, JAXBObject, UnmarshallableObject, XMLSerializable, IdentifiableObject, ValidatableObject {
/*     */   protected String _Type;
/*     */   protected DigestMethodType _DigestMethod;
/*     */   protected byte[] _DigestValue;
/*  20 */   public static final Class version = JAXBVersion.class; protected String _URI; protected TransformsType _Transforms; protected String _Id; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DigestMethodImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DigestMethodTypeImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  24 */     return ReferenceType.class;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  28 */     return this._Type;
/*     */   }
/*     */   
/*     */   public void setType(String value) {
/*  32 */     this._Type = value;
/*     */   }
/*     */   
/*     */   public DigestMethodType getDigestMethod() {
/*  36 */     return this._DigestMethod;
/*     */   }
/*     */   
/*     */   public void setDigestMethod(DigestMethodType value) {
/*  40 */     this._DigestMethod = value;
/*     */   }
/*     */   
/*     */   public byte[] getDigestValue() {
/*  44 */     return this._DigestValue;
/*     */   }
/*     */   
/*     */   public void setDigestValue(byte[] value) {
/*  48 */     this._DigestValue = value;
/*     */   }
/*     */   
/*     */   public String getURI() {
/*  52 */     return this._URI;
/*     */   }
/*     */   
/*     */   public void setURI(String value) {
/*  56 */     this._URI = value;
/*     */   }
/*     */   
/*     */   public TransformsType getTransforms() {
/*  60 */     return this._Transforms;
/*     */   }
/*     */   
/*     */   public void setTransforms(TransformsType value) {
/*  64 */     this._Transforms = value;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  68 */     return this._Id;
/*     */   }
/*     */   
/*     */   public void setId(String value) {
/*  72 */     this._Id = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  76 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  82 */     if (this._Transforms != null) {
/*  83 */       if (this._Transforms instanceof javax.xml.bind.Element) {
/*  84 */         context.childAsBody((JAXBObject)this._Transforms, "Transforms");
/*     */       } else {
/*  86 */         context.startElement("http://www.w3.org/2000/09/xmldsig#", "Transforms");
/*  87 */         context.childAsURIs((JAXBObject)this._Transforms, "Transforms");
/*  88 */         context.endNamespaceDecls();
/*  89 */         context.childAsAttributes((JAXBObject)this._Transforms, "Transforms");
/*  90 */         context.endAttributes();
/*  91 */         context.childAsBody((JAXBObject)this._Transforms, "Transforms");
/*  92 */         context.endElement();
/*     */       } 
/*     */     }
/*  95 */     if (this._DigestMethod instanceof javax.xml.bind.Element) {
/*  96 */       context.childAsBody((JAXBObject)this._DigestMethod, "DigestMethod");
/*     */     } else {
/*  98 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "DigestMethod");
/*  99 */       context.childAsURIs((JAXBObject)this._DigestMethod, "DigestMethod");
/* 100 */       context.endNamespaceDecls();
/* 101 */       context.childAsAttributes((JAXBObject)this._DigestMethod, "DigestMethod");
/* 102 */       context.endAttributes();
/* 103 */       context.childAsBody((JAXBObject)this._DigestMethod, "DigestMethod");
/* 104 */       context.endElement();
/*     */     } 
/* 106 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "DigestValue");
/* 107 */     context.endNamespaceDecls();
/* 108 */     context.endAttributes();
/*     */     try {
/* 110 */       context.text(Base64BinaryType.save(this._DigestValue), "DigestValue");
/* 111 */     } catch (Exception e) {
/* 112 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 114 */     context.endElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 120 */     if (this._Id != null) {
/* 121 */       context.startAttribute("", "Id");
/*     */       try {
/* 123 */         context.text(context.onID(this, this._Id), "Id");
/* 124 */       } catch (Exception e) {
/* 125 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 127 */       context.endAttribute();
/*     */     } 
/* 129 */     if (this._Type != null) {
/* 130 */       context.startAttribute("", "Type");
/*     */       try {
/* 132 */         context.text(this._Type, "Type");
/* 133 */       } catch (Exception e) {
/* 134 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 136 */       context.endAttribute();
/*     */     } 
/* 138 */     if (this._URI != null) {
/* 139 */       context.startAttribute("", "URI");
/*     */       try {
/* 141 */         context.text(this._URI, "URI");
/* 142 */       } catch (Exception e) {
/* 143 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 145 */       context.endAttribute();
/*     */     } 
/* 147 */     if (this._Transforms != null && 
/* 148 */       this._Transforms instanceof javax.xml.bind.Element) {
/* 149 */       context.childAsAttributes((JAXBObject)this._Transforms, "Transforms");
/*     */     }
/*     */     
/* 152 */     if (this._DigestMethod instanceof javax.xml.bind.Element) {
/* 153 */       context.childAsAttributes((JAXBObject)this._DigestMethod, "DigestMethod");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 160 */     if (this._Transforms != null && 
/* 161 */       this._Transforms instanceof javax.xml.bind.Element) {
/* 162 */       context.childAsURIs((JAXBObject)this._Transforms, "Transforms");
/*     */     }
/*     */     
/* 165 */     if (this._DigestMethod instanceof javax.xml.bind.Element) {
/* 166 */       context.childAsURIs((JAXBObject)this._DigestMethod, "DigestMethod");
/*     */     }
/*     */   }
/*     */   
/*     */   public String ____jaxb____getId() {
/* 171 */     return this._Id;
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 175 */     return ReferenceType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 179 */     if (schemaFragment == null) {
/* 180 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsq\000~\000\000ppsq\000~\000\000ppsq\000~\000\000ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsq\000~\000\nsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003q\000~\000\016p\000sq\000~\000\nppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003q\000~\000\016psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\020xq\000~\000\003q\000~\000\016psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\r\001q\000~\000\032sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\033q\000~\000 sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\"xq\000~\000\035t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.Transformst\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\017q\000~\000\016p\000sq\000~\000\000ppsq\000~\000\017pp\000sq\000~\000\nppsq\000~\000\024q\000~\000\016psq\000~\000\027q\000~\000\016pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformsTypeq\000~\000%sq\000~\000\nppsq\000~\000\027q\000~\000\016psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\"L\000\btypeNameq\000~\000\"L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\016psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\"L\000\fnamespaceURIq\000~\000\"xpq\000~\000;q\000~\000:sq\000~\000!t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000 sq\000~\000!t\000\nTransformst\000\"http://www.w3.org/2000/09/xmldsig#q\000~\000 sq\000~\000\nppsq\000~\000\017pp\000sq\000~\000\nppsq\000~\000\024q\000~\000\016psq\000~\000\027q\000~\000\016pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\0008com.sun.xml.wss.saml.internal.saml11.jaxb10.DigestMethodq\000~\000%sq\000~\000\017pp\000sq\000~\000\000ppsq\000~\000\017pp\000sq\000~\000\nppsq\000~\000\024q\000~\000\016psq\000~\000\027q\000~\000\016pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000<com.sun.xml.wss.saml.internal.saml11.jaxb10.DigestMethodTypeq\000~\000%sq\000~\000\nppsq\000~\000\027q\000~\000\016pq\000~\0003q\000~\000Cq\000~\000 sq\000~\000!t\000\fDigestMethodq\000~\000Hsq\000~\000\017pp\000sq\000~\000\000ppsq\000~\0000ppsr\000'com.sun.msv.datatype.xsd.FinalComponent\000\000\000\000\000\000\000\001\002\000\001I\000\nfinalValuexr\000\036com.sun.msv.datatype.xsd.Proxy\000\000\000\000\000\000\000\001\002\000\001L\000\bbaseTypet\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xq\000~\0007q\000~\000Ht\000\017DigestValueTypeq\000~\000>sr\000)com.sun.msv.datatype.xsd.Base64BinaryType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.BinaryBaseType§Î\016^¯W\021\002\000\000xq\000~\0005q\000~\000:t\000\fbase64Binaryq\000~\000>\000\000\000\000q\000~\000@sq\000~\000Aq\000~\000gq\000~\000Hsq\000~\000\nppsq\000~\000\027q\000~\000\016pq\000~\0003q\000~\000Cq\000~\000 sq\000~\000!t\000\013DigestValueq\000~\000Hsq\000~\000\nppsq\000~\000\027q\000~\000\016psq\000~\0000ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\0005q\000~\000:t\000\002IDq\000~\000>\000q\000~\000@sq\000~\000Aq\000~\000uq\000~\000:sq\000~\000!t\000\002Idt\000\000q\000~\000 sq\000~\000\nppsq\000~\000\027q\000~\000\016psq\000~\0000ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0005q\000~\000:t\000\006anyURIq\000~\000>q\000~\000@sq\000~\000Aq\000~\000q\000~\000:sq\000~\000!t\000\004Typeq\000~\000yq\000~\000 sq\000~\000\nppsq\000~\000\027q\000~\000\016pq\000~\000|sq\000~\000!t\000\003URIq\000~\000yq\000~\000 sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\031\001pq\000~\000'q\000~\000Qq\000~\000\tq\000~\000\023q\000~\000)q\000~\000Kq\000~\000Sq\000~\000\026q\000~\000*q\000~\000Lq\000~\000Tq\000~\000mq\000~\000\bq\000~\000zq\000~\000]q\000~\000.q\000~\000Xq\000~\000iq\000~\000\005q\000~\000\013q\000~\000\fq\000~\000Iq\000~\000q\000~\000\006q\000~\000\007x");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final ReferenceTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 259 */       super(context, "-------------------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 263 */       this(context);
/* 264 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 268 */       return ReferenceTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 277 */         switch (this.state) {
/*     */           case 17:
/* 279 */             if ("Transform" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 280 */               ReferenceTypeImpl.this._Transforms = (TransformsTypeImpl)spawnChildFromEnterElement((ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl == null) ? (ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl = ReferenceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformsTypeImpl")) : ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl, 18, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 283 */             if ("Transform" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 284 */               ReferenceTypeImpl.this._Transforms = (TransformsTypeImpl)spawnChildFromEnterElement((ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl == null) ? (ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl = ReferenceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformsTypeImpl")) : ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl, 18, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 10:
/* 289 */             if ("DigestMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 290 */               ReferenceTypeImpl.this._DigestMethod = (DigestMethodImpl)spawnChildFromEnterElement((ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DigestMethodImpl == null) ? (ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DigestMethodImpl = ReferenceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DigestMethodImpl")) : ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DigestMethodImpl, 11, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 293 */             if ("DigestMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 294 */               this.context.pushAttributes(__atts, true);
/* 295 */               this.state = 15;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 14:
/* 300 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 15:
/* 303 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 304 */             if (attIdx >= 0) {
/* 305 */               this.context.consumeAttribute(attIdx);
/* 306 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 11:
/* 311 */             if ("DigestValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 312 */               this.context.pushAttributes(__atts, true);
/* 313 */               this.state = 12;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 318 */             attIdx = this.context.getAttribute("", "Type");
/* 319 */             if (attIdx >= 0) {
/* 320 */               String v = this.context.eatAttribute(attIdx);
/* 321 */               this.state = 6;
/* 322 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 325 */             this.state = 6;
/*     */             continue;
/*     */           case 6:
/* 328 */             attIdx = this.context.getAttribute("", "URI");
/* 329 */             if (attIdx >= 0) {
/* 330 */               String v = this.context.eatAttribute(attIdx);
/* 331 */               this.state = 9;
/* 332 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 335 */             this.state = 9;
/*     */             continue;
/*     */           case 9:
/* 338 */             if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 339 */               ReferenceTypeImpl.this._Transforms = (TransformsImpl)spawnChildFromEnterElement((ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsImpl == null) ? (ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsImpl = ReferenceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformsImpl")) : ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsImpl, 10, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 342 */             if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 343 */               this.context.pushAttributes(__atts, false);
/* 344 */               this.state = 17;
/*     */               return;
/*     */             } 
/* 347 */             this.state = 10;
/*     */             continue;
/*     */           case 0:
/* 350 */             attIdx = this.context.getAttribute("", "Id");
/* 351 */             if (attIdx >= 0) {
/* 352 */               String v = this.context.eatAttribute(attIdx);
/* 353 */               this.state = 3;
/* 354 */               eatText3(v);
/*     */               continue;
/*     */             } 
/* 357 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 360 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 369 */         ReferenceTypeImpl.this._Type = WhiteSpaceProcessor.collapse(value);
/* 370 */       } catch (Exception e) {
/* 371 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 379 */         ReferenceTypeImpl.this._URI = WhiteSpaceProcessor.collapse(value);
/* 380 */       } catch (Exception e) {
/* 381 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText3(String value) throws SAXException {
/*     */       try {
/* 389 */         ReferenceTypeImpl.this._Id = this.context.addToIdTable(WhiteSpaceProcessor.collapse(value));
/* 390 */       } catch (Exception e) {
/* 391 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 401 */         switch (this.state) {
/*     */           case 16:
/* 403 */             if ("DigestMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 404 */               this.context.popAttributes();
/* 405 */               this.state = 11;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 14:
/* 410 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 15:
/* 413 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 414 */             if (attIdx >= 0) {
/* 415 */               this.context.consumeAttribute(attIdx);
/* 416 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 18:
/* 421 */             if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 422 */               this.context.popAttributes();
/* 423 */               this.state = 10;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 428 */             attIdx = this.context.getAttribute("", "Type");
/* 429 */             if (attIdx >= 0) {
/* 430 */               String v = this.context.eatAttribute(attIdx);
/* 431 */               this.state = 6;
/* 432 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 435 */             this.state = 6;
/*     */             continue;
/*     */           case 13:
/* 438 */             if ("DigestValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 439 */               this.context.popAttributes();
/* 440 */               this.state = 14;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 445 */             attIdx = this.context.getAttribute("", "URI");
/* 446 */             if (attIdx >= 0) {
/* 447 */               String v = this.context.eatAttribute(attIdx);
/* 448 */               this.state = 9;
/* 449 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 452 */             this.state = 9;
/*     */             continue;
/*     */           case 9:
/* 455 */             this.state = 10;
/*     */             continue;
/*     */           case 0:
/* 458 */             attIdx = this.context.getAttribute("", "Id");
/* 459 */             if (attIdx >= 0) {
/* 460 */               String v = this.context.eatAttribute(attIdx);
/* 461 */               this.state = 3;
/* 462 */               eatText3(v);
/*     */               continue;
/*     */             } 
/* 465 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 468 */       super.leaveElement(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/* 479 */         switch (this.state) {
/*     */           case 14:
/* 481 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 15:
/* 484 */             if ("Algorithm" == ___local && "" == ___uri) {
/* 485 */               ReferenceTypeImpl.this._DigestMethod = (DigestMethodTypeImpl)spawnChildFromEnterAttribute((ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DigestMethodTypeImpl == null) ? (ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DigestMethodTypeImpl = ReferenceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DigestMethodTypeImpl")) : ReferenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DigestMethodTypeImpl, 16, ___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 490 */             if ("Type" == ___local && "" == ___uri) {
/* 491 */               this.state = 4;
/*     */               return;
/*     */             } 
/* 494 */             this.state = 6;
/*     */             continue;
/*     */           case 6:
/* 497 */             if ("URI" == ___local && "" == ___uri) {
/* 498 */               this.state = 7;
/*     */               return;
/*     */             } 
/* 501 */             this.state = 9;
/*     */             continue;
/*     */           case 9:
/* 504 */             this.state = 10;
/*     */             continue;
/*     */           case 0:
/* 507 */             if ("Id" == ___local && "" == ___uri) {
/* 508 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 511 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 514 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 525 */         switch (this.state) {
/*     */           case 14:
/* 527 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 2:
/* 530 */             if ("Id" == ___local && "" == ___uri) {
/* 531 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 15:
/* 536 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 537 */             if (attIdx >= 0) {
/* 538 */               this.context.consumeAttribute(attIdx);
/* 539 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 544 */             if ("URI" == ___local && "" == ___uri) {
/* 545 */               this.state = 9;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 550 */             if ("Type" == ___local && "" == ___uri) {
/* 551 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 556 */             attIdx = this.context.getAttribute("", "Type");
/* 557 */             if (attIdx >= 0) {
/* 558 */               String v = this.context.eatAttribute(attIdx);
/* 559 */               this.state = 6;
/* 560 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 563 */             this.state = 6;
/*     */             continue;
/*     */           case 6:
/* 566 */             attIdx = this.context.getAttribute("", "URI");
/* 567 */             if (attIdx >= 0) {
/* 568 */               String v = this.context.eatAttribute(attIdx);
/* 569 */               this.state = 9;
/* 570 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 573 */             this.state = 9;
/*     */             continue;
/*     */           case 9:
/* 576 */             this.state = 10;
/*     */             continue;
/*     */           case 0:
/* 579 */             attIdx = this.context.getAttribute("", "Id");
/* 580 */             if (attIdx >= 0) {
/* 581 */               String v = this.context.eatAttribute(attIdx);
/* 582 */               this.state = 3;
/* 583 */               eatText3(v);
/*     */               continue;
/*     */             } 
/* 586 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 589 */       super.leaveAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleText(String value) throws SAXException {
/*     */       try {
/*     */         while (true) {
/*     */           int attIdx;
/* 601 */           switch (this.state) {
/*     */             case 14:
/* 603 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 12:
/* 606 */               this.state = 13;
/* 607 */               eatText4(value);
/*     */               return;
/*     */             case 15:
/* 610 */               attIdx = this.context.getAttribute("", "Algorithm");
/* 611 */               if (attIdx >= 0) {
/* 612 */                 this.context.consumeAttribute(attIdx);
/* 613 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/*     */               break;
/*     */             case 1:
/* 618 */               this.state = 2;
/* 619 */               eatText3(value);
/*     */               return;
/*     */             case 7:
/* 622 */               this.state = 8;
/* 623 */               eatText2(value);
/*     */               return;
/*     */             case 3:
/* 626 */               attIdx = this.context.getAttribute("", "Type");
/* 627 */               if (attIdx >= 0) {
/* 628 */                 String v = this.context.eatAttribute(attIdx);
/* 629 */                 this.state = 6;
/* 630 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 633 */               this.state = 6;
/*     */               continue;
/*     */             case 4:
/* 636 */               this.state = 5;
/* 637 */               eatText1(value);
/*     */               return;
/*     */             case 6:
/* 640 */               attIdx = this.context.getAttribute("", "URI");
/* 641 */               if (attIdx >= 0) {
/* 642 */                 String v = this.context.eatAttribute(attIdx);
/* 643 */                 this.state = 9;
/* 644 */                 eatText2(v);
/*     */                 continue;
/*     */               } 
/* 647 */               this.state = 9;
/*     */               continue;
/*     */             case 9:
/* 650 */               this.state = 10;
/*     */               continue;
/*     */             case 0:
/* 653 */               attIdx = this.context.getAttribute("", "Id");
/* 654 */               if (attIdx >= 0) {
/* 655 */                 String v = this.context.eatAttribute(attIdx);
/* 656 */                 this.state = 3;
/* 657 */                 eatText3(v);
/*     */                 continue;
/*     */               } 
/* 660 */               this.state = 3; continue;
/*     */           }  break;
/*     */         } 
/* 663 */       } catch (RuntimeException e) {
/* 664 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText4(String value) throws SAXException {
/*     */       try {
/* 674 */         ReferenceTypeImpl.this._DigestValue = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 675 */       } catch (Exception e) {
/* 676 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\ReferenceTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */