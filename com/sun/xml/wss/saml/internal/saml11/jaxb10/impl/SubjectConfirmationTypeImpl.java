/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AnyType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyInfoType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectConfirmationType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SubjectConfirmationTypeImpl implements SubjectConfirmationType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected KeyInfoType _KeyInfo;
/*  17 */   public static final Class version = JAXBVersion.class; protected AnyType _SubjectConfirmationData; protected ListImpl _ConfirmationMethod; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationDataImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return SubjectConfirmationType.class;
/*     */   }
/*     */   
/*     */   public KeyInfoType getKeyInfo() {
/*  25 */     return this._KeyInfo;
/*     */   }
/*     */   
/*     */   public void setKeyInfo(KeyInfoType value) {
/*  29 */     this._KeyInfo = value;
/*     */   }
/*     */   
/*     */   public AnyType getSubjectConfirmationData() {
/*  33 */     return this._SubjectConfirmationData;
/*     */   }
/*     */   
/*     */   public void setSubjectConfirmationData(AnyType value) {
/*  37 */     this._SubjectConfirmationData = value;
/*     */   }
/*     */   
/*     */   protected ListImpl _getConfirmationMethod() {
/*  41 */     if (this._ConfirmationMethod == null) {
/*  42 */       this._ConfirmationMethod = new ListImpl(new ArrayList());
/*     */     }
/*  44 */     return this._ConfirmationMethod;
/*     */   }
/*     */   
/*     */   public List getConfirmationMethod() {
/*  48 */     return (List)_getConfirmationMethod();
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  52 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  58 */     int idx3 = 0;
/*  59 */     int len3 = (this._ConfirmationMethod == null) ? 0 : this._ConfirmationMethod.size();
/*  60 */     while (idx3 != len3) {
/*  61 */       context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod");
/*  62 */       int idx_0 = idx3;
/*     */       try {
/*  64 */         idx_0++;
/*  65 */       } catch (Exception e) {
/*  66 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  68 */       context.endNamespaceDecls();
/*  69 */       int idx_1 = idx3;
/*     */       try {
/*  71 */         idx_1++;
/*  72 */       } catch (Exception e) {
/*  73 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  75 */       context.endAttributes();
/*     */       try {
/*  77 */         context.text((String)this._ConfirmationMethod.get(idx3++), "ConfirmationMethod");
/*  78 */       } catch (Exception e) {
/*  79 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  81 */       context.endElement();
/*     */     } 
/*  83 */     if (this._SubjectConfirmationData != null) {
/*  84 */       if (this._SubjectConfirmationData instanceof javax.xml.bind.Element) {
/*  85 */         context.childAsBody((JAXBObject)this._SubjectConfirmationData, "SubjectConfirmationData");
/*     */       } else {
/*  87 */         context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmationData");
/*  88 */         context.childAsURIs((JAXBObject)this._SubjectConfirmationData, "SubjectConfirmationData");
/*  89 */         context.endNamespaceDecls();
/*  90 */         context.childAsAttributes((JAXBObject)this._SubjectConfirmationData, "SubjectConfirmationData");
/*  91 */         context.endAttributes();
/*  92 */         context.childAsBody((JAXBObject)this._SubjectConfirmationData, "SubjectConfirmationData");
/*  93 */         context.endElement();
/*     */       } 
/*     */     }
/*  96 */     if (this._KeyInfo != null) {
/*  97 */       if (this._KeyInfo instanceof javax.xml.bind.Element) {
/*  98 */         context.childAsBody((JAXBObject)this._KeyInfo, "KeyInfo");
/*     */       } else {
/* 100 */         context.startElement("http://www.w3.org/2000/09/xmldsig#", "KeyInfo");
/* 101 */         context.childAsURIs((JAXBObject)this._KeyInfo, "KeyInfo");
/* 102 */         context.endNamespaceDecls();
/* 103 */         context.childAsAttributes((JAXBObject)this._KeyInfo, "KeyInfo");
/* 104 */         context.endAttributes();
/* 105 */         context.childAsBody((JAXBObject)this._KeyInfo, "KeyInfo");
/* 106 */         context.endElement();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 114 */     int idx3 = 0;
/* 115 */     int len3 = (this._ConfirmationMethod == null) ? 0 : this._ConfirmationMethod.size();
/* 116 */     while (idx3 != len3) {
/*     */       try {
/* 118 */         idx3++;
/* 119 */       } catch (Exception e) {
/* 120 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*     */     } 
/* 123 */     if (this._SubjectConfirmationData != null && 
/* 124 */       this._SubjectConfirmationData instanceof javax.xml.bind.Element) {
/* 125 */       context.childAsAttributes((JAXBObject)this._SubjectConfirmationData, "SubjectConfirmationData");
/*     */     }
/*     */     
/* 128 */     if (this._KeyInfo != null && 
/* 129 */       this._KeyInfo instanceof javax.xml.bind.Element) {
/* 130 */       context.childAsAttributes((JAXBObject)this._KeyInfo, "KeyInfo");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 138 */     int idx3 = 0;
/* 139 */     int len3 = (this._ConfirmationMethod == null) ? 0 : this._ConfirmationMethod.size();
/* 140 */     while (idx3 != len3) {
/*     */       try {
/* 142 */         idx3++;
/* 143 */       } catch (Exception e) {
/* 144 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*     */     } 
/* 147 */     if (this._SubjectConfirmationData != null && 
/* 148 */       this._SubjectConfirmationData instanceof javax.xml.bind.Element) {
/* 149 */       context.childAsURIs((JAXBObject)this._SubjectConfirmationData, "SubjectConfirmationData");
/*     */     }
/*     */     
/* 152 */     if (this._KeyInfo != null && 
/* 153 */       this._KeyInfo instanceof javax.xml.bind.Element) {
/* 154 */       context.childAsURIs((JAXBObject)this._KeyInfo, "KeyInfo");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 160 */     return SubjectConfirmationType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 164 */     if (schemaFragment == null) {
/* 165 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\000ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\027L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\027L\000\fnamespaceURIq\000~\000\027xpq\000~\000\033q\000~\000\032sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\013xq\000~\000\003q\000~\000\"psq\000~\000\017ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\024q\000~\000\032t\000\005QNameq\000~\000\036q\000~\000 sq\000~\000#q\000~\000,q\000~\000\032sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\027L\000\fnamespaceURIq\000~\000\027xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000!\001q\000~\0004sq\000~\000.t\000\022ConfirmationMethodt\000%urn:oasis:names:tc:SAML:1.0:assertionsq\000~\000%ppsq\000~\000%q\000~\000\"psq\000~\000\nq\000~\000\"p\000sq\000~\000%ppsq\000~\000\007q\000~\000\"psq\000~\000'q\000~\000\"psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\0005q\000~\000@sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000/q\000~\0004sq\000~\000.t\000Ccom.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectConfirmationDatat\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\nq\000~\000\"p\000sq\000~\000\000ppsq\000~\000\npp\000sq\000~\000%ppsq\000~\000\007q\000~\000\"psq\000~\000'q\000~\000\"pq\000~\000@q\000~\000Bq\000~\0004sq\000~\000.t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.AnyTypeq\000~\000Esq\000~\000%ppsq\000~\000'q\000~\000\"pq\000~\000)q\000~\0000q\000~\0004sq\000~\000.t\000\027SubjectConfirmationDataq\000~\0008q\000~\0004sq\000~\000%ppsq\000~\000%q\000~\000\"psq\000~\000\nq\000~\000\"p\000sq\000~\000%ppsq\000~\000\007q\000~\000\"psq\000~\000'q\000~\000\"pq\000~\000@q\000~\000Bq\000~\0004sq\000~\000.t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyInfoq\000~\000Esq\000~\000\nq\000~\000\"p\000sq\000~\000\000ppsq\000~\000\npp\000sq\000~\000%ppsq\000~\000\007q\000~\000\"psq\000~\000'q\000~\000\"pq\000~\000@q\000~\000Bq\000~\0004sq\000~\000.t\0007com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyInfoTypeq\000~\000Esq\000~\000%ppsq\000~\000'q\000~\000\"pq\000~\000)q\000~\0000q\000~\0004sq\000~\000.t\000\007KeyInfot\000\"http://www.w3.org/2000/09/xmldsig#q\000~\0004sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\025\001pq\000~\000Gq\000~\000[q\000~\000<q\000~\000Iq\000~\000Uq\000~\000]q\000~\000=q\000~\000Jq\000~\000Vq\000~\000^q\000~\000\tq\000~\000\016q\000~\000\006q\000~\000\005q\000~\000&q\000~\000Nq\000~\000bq\000~\0009q\000~\000Rq\000~\000:q\000~\000Sx");
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
/* 222 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SubjectConfirmationTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 231 */       super(context, "----------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 235 */       this(context);
/* 236 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 240 */       return SubjectConfirmationTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true)
/*     */       { int attIdx;
/* 249 */         switch (this.state) {
/*     */           case 4:
/* 251 */             if ("KeyInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 252 */               SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoImpl, 7, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 255 */             if ("KeyInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 256 */               this.context.pushAttributes(__atts, true);
/* 257 */               this.state = 5;
/*     */               return;
/*     */             } 
/* 260 */             this.state = 7;
/*     */             continue;
/*     */           case 5:
/* 263 */             attIdx = this.context.getAttribute("", "Id");
/* 264 */             if (attIdx >= 0) {
/* 265 */               this.context.consumeAttribute(attIdx);
/* 266 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 269 */             if ("KeyName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 270 */               SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 273 */             if ("KeyValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 274 */               SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 277 */             if ("RetrievalMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 278 */               SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 281 */             if ("X509Data" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 282 */               SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 285 */             if ("PGPData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 286 */               SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 289 */             if ("SPKIData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 290 */               SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 293 */             if ("MgmtData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 294 */               SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 297 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 298 */               SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 301 */             SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           
/*     */           case 8:
/* 305 */             SubjectConfirmationTypeImpl.this._SubjectConfirmationData = (AnyTypeImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */ 
/*     */ 
/*     */           
/*     */           case 7:
/* 311 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 3:
/* 314 */             if ("ConfirmationMethod" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 315 */               this.context.pushAttributes(__atts, true);
/* 316 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 319 */             if ("SubjectConfirmationData" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 320 */               SubjectConfirmationTypeImpl.this._SubjectConfirmationData = (SubjectConfirmationDataImpl)spawnChildFromEnterElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationDataImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationDataImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectConfirmationDataImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationDataImpl, 4, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 323 */             if ("SubjectConfirmationData" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 324 */               this.context.pushAttributes(__atts, true);
/* 325 */               this.state = 8;
/*     */               return;
/*     */             } 
/* 328 */             this.state = 4;
/*     */             continue;
/*     */           case 0:
/* 331 */             if ("ConfirmationMethod" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 332 */               this.context.pushAttributes(__atts, true);
/* 333 */               this.state = 1; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 338 */         super.enterElement(___uri, ___local, ___qname, __atts); return; }  super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 349 */         switch (this.state) {
/*     */           case 9:
/* 351 */             if ("SubjectConfirmationData" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 352 */               this.context.popAttributes();
/* 353 */               this.state = 4;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 358 */             this.state = 7;
/*     */             continue;
/*     */           case 6:
/* 361 */             if ("KeyInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 362 */               this.context.popAttributes();
/* 363 */               this.state = 7;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 368 */             if ("ConfirmationMethod" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 369 */               this.context.popAttributes();
/* 370 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 375 */             attIdx = this.context.getAttribute("", "Id");
/* 376 */             if (attIdx >= 0) {
/* 377 */               this.context.consumeAttribute(attIdx);
/* 378 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 381 */             SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromLeaveElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 8:
/* 384 */             SubjectConfirmationTypeImpl.this._SubjectConfirmationData = (AnyTypeImpl)spawnChildFromLeaveElement((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl, 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 7:
/* 387 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 390 */             this.state = 4; continue;
/*     */         }  break;
/*     */       } 
/* 393 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 404 */         switch (this.state) {
/*     */           case 4:
/* 406 */             this.state = 7;
/*     */             continue;
/*     */           case 5:
/* 409 */             if ("Id" == ___local && "" == ___uri) {
/* 410 */               SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterAttribute((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 413 */             SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterAttribute((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 8:
/* 416 */             SubjectConfirmationTypeImpl.this._SubjectConfirmationData = (AnyTypeImpl)spawnChildFromEnterAttribute((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl, 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 7:
/* 419 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 422 */             this.state = 4; continue;
/*     */         }  break;
/*     */       } 
/* 425 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 436 */         switch (this.state) {
/*     */           case 4:
/* 438 */             this.state = 7;
/*     */             continue;
/*     */           case 5:
/* 441 */             attIdx = this.context.getAttribute("", "Id");
/* 442 */             if (attIdx >= 0) {
/* 443 */               this.context.consumeAttribute(attIdx);
/* 444 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 447 */             SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromLeaveAttribute((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 8:
/* 450 */             SubjectConfirmationTypeImpl.this._SubjectConfirmationData = (AnyTypeImpl)spawnChildFromLeaveAttribute((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl, 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 7:
/* 453 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 456 */             this.state = 4; continue;
/*     */         }  break;
/*     */       } 
/* 459 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 471 */           switch (this.state) {
/*     */             case 4:
/* 473 */               this.state = 7;
/*     */               continue;
/*     */             case 1:
/* 476 */               this.state = 2;
/* 477 */               eatText1(value);
/*     */               return;
/*     */             case 5:
/* 480 */               attIdx = this.context.getAttribute("", "Id");
/* 481 */               if (attIdx >= 0) {
/* 482 */                 this.context.consumeAttribute(attIdx);
/* 483 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 486 */               SubjectConfirmationTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromText((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 6, value);
/*     */               return;
/*     */             case 8:
/* 489 */               SubjectConfirmationTypeImpl.this._SubjectConfirmationData = (AnyTypeImpl)spawnChildFromText((SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl == null) ? (SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl = SubjectConfirmationTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl")) : SubjectConfirmationTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl, 9, value);
/*     */               return;
/*     */             case 7:
/* 492 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 3:
/* 495 */               this.state = 4; continue;
/*     */           }  break;
/*     */         } 
/* 498 */       } catch (RuntimeException e) {
/* 499 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 509 */         SubjectConfirmationTypeImpl.this._getConfirmationMethod().add(WhiteSpaceProcessor.collapse(value));
/* 510 */       } catch (Exception e) {
/* 511 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SubjectConfirmationTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */