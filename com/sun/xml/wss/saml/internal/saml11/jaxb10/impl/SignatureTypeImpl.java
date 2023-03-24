/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyInfoType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureValueType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SignedInfoType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SignatureTypeImpl implements SignatureType, JAXBObject, UnmarshallableObject, XMLSerializable, IdentifiableObject, ValidatableObject {
/*     */   protected SignedInfoType _SignedInfo;
/*     */   protected KeyInfoType _KeyInfo;
/*  19 */   public static final Class version = JAXBVersion.class; protected SignatureValueType _SignatureValue; protected ListImpl _Object; protected String _Id; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureValueImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoTypeImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureValueTypeImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  23 */     return SignatureType.class;
/*     */   }
/*     */   
/*     */   public SignedInfoType getSignedInfo() {
/*  27 */     return this._SignedInfo;
/*     */   }
/*     */   
/*     */   public void setSignedInfo(SignedInfoType value) {
/*  31 */     this._SignedInfo = value;
/*     */   }
/*     */   
/*     */   public KeyInfoType getKeyInfo() {
/*  35 */     return this._KeyInfo;
/*     */   }
/*     */   
/*     */   public void setKeyInfo(KeyInfoType value) {
/*  39 */     this._KeyInfo = value;
/*     */   }
/*     */   
/*     */   public SignatureValueType getSignatureValue() {
/*  43 */     return this._SignatureValue;
/*     */   }
/*     */   
/*     */   public void setSignatureValue(SignatureValueType value) {
/*  47 */     this._SignatureValue = value;
/*     */   }
/*     */   
/*     */   protected ListImpl _getObject() {
/*  51 */     if (this._Object == null) {
/*  52 */       this._Object = new ListImpl(new ArrayList());
/*     */     }
/*  54 */     return this._Object;
/*     */   }
/*     */   
/*     */   public List getObject() {
/*  58 */     return (List)_getObject();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  62 */     return this._Id;
/*     */   }
/*     */   
/*     */   public void setId(String value) {
/*  66 */     this._Id = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  70 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  76 */     int idx4 = 0;
/*  77 */     int len4 = (this._Object == null) ? 0 : this._Object.size();
/*  78 */     if (this._SignedInfo instanceof javax.xml.bind.Element) {
/*  79 */       context.childAsBody((JAXBObject)this._SignedInfo, "SignedInfo");
/*     */     } else {
/*  81 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "SignedInfo");
/*  82 */       context.childAsURIs((JAXBObject)this._SignedInfo, "SignedInfo");
/*  83 */       context.endNamespaceDecls();
/*  84 */       context.childAsAttributes((JAXBObject)this._SignedInfo, "SignedInfo");
/*  85 */       context.endAttributes();
/*  86 */       context.childAsBody((JAXBObject)this._SignedInfo, "SignedInfo");
/*  87 */       context.endElement();
/*     */     } 
/*  89 */     if (this._SignatureValue instanceof javax.xml.bind.Element) {
/*  90 */       context.childAsBody((JAXBObject)this._SignatureValue, "SignatureValue");
/*     */     } else {
/*  92 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "SignatureValue");
/*  93 */       context.childAsURIs((JAXBObject)this._SignatureValue, "SignatureValue");
/*  94 */       context.endNamespaceDecls();
/*  95 */       context.childAsAttributes((JAXBObject)this._SignatureValue, "SignatureValue");
/*  96 */       context.endAttributes();
/*  97 */       context.childAsBody((JAXBObject)this._SignatureValue, "SignatureValue");
/*  98 */       context.endElement();
/*     */     } 
/* 100 */     if (this._KeyInfo != null) {
/* 101 */       if (this._KeyInfo instanceof javax.xml.bind.Element) {
/* 102 */         context.childAsBody((JAXBObject)this._KeyInfo, "KeyInfo");
/*     */       } else {
/* 104 */         context.startElement("http://www.w3.org/2000/09/xmldsig#", "KeyInfo");
/* 105 */         context.childAsURIs((JAXBObject)this._KeyInfo, "KeyInfo");
/* 106 */         context.endNamespaceDecls();
/* 107 */         context.childAsAttributes((JAXBObject)this._KeyInfo, "KeyInfo");
/* 108 */         context.endAttributes();
/* 109 */         context.childAsBody((JAXBObject)this._KeyInfo, "KeyInfo");
/* 110 */         context.endElement();
/*     */       } 
/*     */     }
/* 113 */     while (idx4 != len4) {
/* 114 */       if (this._Object.get(idx4) instanceof javax.xml.bind.Element) {
/* 115 */         context.childAsBody((JAXBObject)this._Object.get(idx4++), "Object"); continue;
/*     */       } 
/* 117 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "Object");
/* 118 */       int idx_6 = idx4;
/* 119 */       context.childAsURIs((JAXBObject)this._Object.get(idx_6++), "Object");
/* 120 */       context.endNamespaceDecls();
/* 121 */       int idx_7 = idx4;
/* 122 */       context.childAsAttributes((JAXBObject)this._Object.get(idx_7++), "Object");
/* 123 */       context.endAttributes();
/* 124 */       context.childAsBody((JAXBObject)this._Object.get(idx4++), "Object");
/* 125 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 133 */     int idx4 = 0;
/* 134 */     int len4 = (this._Object == null) ? 0 : this._Object.size();
/* 135 */     if (this._Id != null) {
/* 136 */       context.startAttribute("", "Id");
/*     */       try {
/* 138 */         context.text(context.onID(this, this._Id), "Id");
/* 139 */       } catch (Exception e) {
/* 140 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 142 */       context.endAttribute();
/*     */     } 
/* 144 */     if (this._SignedInfo instanceof javax.xml.bind.Element) {
/* 145 */       context.childAsAttributes((JAXBObject)this._SignedInfo, "SignedInfo");
/*     */     }
/* 147 */     if (this._SignatureValue instanceof javax.xml.bind.Element) {
/* 148 */       context.childAsAttributes((JAXBObject)this._SignatureValue, "SignatureValue");
/*     */     }
/* 150 */     if (this._KeyInfo != null && 
/* 151 */       this._KeyInfo instanceof javax.xml.bind.Element) {
/* 152 */       context.childAsAttributes((JAXBObject)this._KeyInfo, "KeyInfo");
/*     */     }
/*     */     
/* 155 */     while (idx4 != len4) {
/* 156 */       if (this._Object.get(idx4) instanceof javax.xml.bind.Element) {
/* 157 */         context.childAsAttributes((JAXBObject)this._Object.get(idx4++), "Object"); continue;
/*     */       } 
/* 159 */       idx4++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 167 */     int idx4 = 0;
/* 168 */     int len4 = (this._Object == null) ? 0 : this._Object.size();
/* 169 */     if (this._SignedInfo instanceof javax.xml.bind.Element) {
/* 170 */       context.childAsURIs((JAXBObject)this._SignedInfo, "SignedInfo");
/*     */     }
/* 172 */     if (this._SignatureValue instanceof javax.xml.bind.Element) {
/* 173 */       context.childAsURIs((JAXBObject)this._SignatureValue, "SignatureValue");
/*     */     }
/* 175 */     if (this._KeyInfo != null && 
/* 176 */       this._KeyInfo instanceof javax.xml.bind.Element) {
/* 177 */       context.childAsURIs((JAXBObject)this._KeyInfo, "KeyInfo");
/*     */     }
/*     */     
/* 180 */     while (idx4 != len4) {
/* 181 */       if (this._Object.get(idx4) instanceof javax.xml.bind.Element) {
/* 182 */         context.childAsURIs((JAXBObject)this._Object.get(idx4++), "Object"); continue;
/*     */       } 
/* 184 */       idx4++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String ____jaxb____getId() {
/* 190 */     return this._Id;
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 194 */     return SignatureType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 198 */     if (schemaFragment == null) {
/* 199 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsq\000~\000\000ppsq\000~\000\000ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\tppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\fxq\000~\000\003q\000~\000\024psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\023\001q\000~\000\030sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\031q\000~\000\036sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000 xq\000~\000\033t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.SignedInfot\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\013pp\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.SignedInfoTypeq\000~\000#sq\000~\000\tppsq\000~\000\025q\000~\000\024psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000 L\000\btypeNameq\000~\000 L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\024psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xpq\000~\0009q\000~\0008sq\000~\000\037t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\036sq\000~\000\037t\000\nSignedInfot\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\tppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureValueq\000~\000#sq\000~\000\013pp\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000>com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureValueTypeq\000~\000#sq\000~\000\tppsq\000~\000\025q\000~\000\024pq\000~\0001q\000~\000Aq\000~\000\036sq\000~\000\037t\000\016SignatureValueq\000~\000Fsq\000~\000\tppsq\000~\000\tq\000~\000\024psq\000~\000\013q\000~\000\024p\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyInfoq\000~\000#sq\000~\000\013q\000~\000\024p\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0007com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyInfoTypeq\000~\000#sq\000~\000\tppsq\000~\000\025q\000~\000\024pq\000~\0001q\000~\000Aq\000~\000\036sq\000~\000\037t\000\007KeyInfoq\000~\000Fq\000~\000\036sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\tq\000~\000\024psq\000~\000\013q\000~\000\024p\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0002com.sun.xml.wss.saml.internal.saml11.jaxb10.Objectq\000~\000#sq\000~\000\013q\000~\000\024p\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.ObjectTypeq\000~\000#sq\000~\000\tppsq\000~\000\025q\000~\000\024pq\000~\0001q\000~\000Aq\000~\000\036sq\000~\000\037t\000\006Objectq\000~\000Fq\000~\000\036sq\000~\000\tppsq\000~\000\025q\000~\000\024psq\000~\000.ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\0003q\000~\0008t\000\002IDq\000~\000<\000q\000~\000>sq\000~\000?q\000~\000q\000~\0008sq\000~\000\037t\000\002Idt\000\000q\000~\000\036sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000$\001pq\000~\000\007q\000~\000nq\000~\000\bq\000~\000%q\000~\000Oq\000~\000cq\000~\000xq\000~\000\017q\000~\000'q\000~\000Iq\000~\000Qq\000~\000]q\000~\000eq\000~\000rq\000~\000\022q\000~\000(q\000~\000Jq\000~\000Rq\000~\000^q\000~\000fq\000~\000sq\000~\000{q\000~\000zq\000~\000oq\000~\000,q\000~\000Vq\000~\000jq\000~\000q\000~\000\005q\000~\000q\000~\000Zq\000~\000\nq\000~\000Gq\000~\000[q\000~\000pq\000~\000\006x");
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
/*     */     
/* 270 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SignatureTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 279 */       super(context, "----------------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 283 */       this(context);
/* 284 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 288 */       return SignatureTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 297 */         switch (this.state) {
/*     */           case 10:
/* 299 */             if ("Object" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 300 */               SignatureTypeImpl.this._getObject().add(spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectImpl, 11, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 303 */             if ("Object" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 304 */               this.context.pushAttributes(__atts, true);
/* 305 */               this.state = 12;
/*     */               return;
/*     */             } 
/* 308 */             this.state = 11;
/*     */             continue;
/*     */           case 4:
/* 311 */             if ("SignatureValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 312 */               SignatureTypeImpl.this._SignatureValue = (SignatureValueImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureValueImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureValueImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureValueImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureValueImpl, 7, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 315 */             if ("SignatureValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 316 */               this.context.pushAttributes(__atts, true);
/* 317 */               this.state = 5;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 322 */             attIdx = this.context.getAttribute("", "Id");
/* 323 */             if (attIdx >= 0) {
/* 324 */               this.context.consumeAttribute(attIdx);
/* 325 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 14:
/* 330 */             attIdx = this.context.getAttribute("", "Id");
/* 331 */             if (attIdx >= 0) {
/* 332 */               this.context.consumeAttribute(attIdx);
/* 333 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 336 */             if ("CanonicalizationMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 337 */               SignatureTypeImpl.this._SignedInfo = (SignedInfoTypeImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignedInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoTypeImpl, 15, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 340 */             if ("CanonicalizationMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 341 */               SignatureTypeImpl.this._SignedInfo = (SignedInfoTypeImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignedInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoTypeImpl, 15, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 346 */             if ("SignedInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 347 */               SignatureTypeImpl.this._SignedInfo = (SignedInfoImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignedInfoImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoImpl, 4, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 350 */             if ("SignedInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 351 */               this.context.pushAttributes(__atts, false);
/* 352 */               this.state = 14;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 357 */             attIdx = this.context.getAttribute("", "Id");
/* 358 */             if (attIdx >= 0) {
/* 359 */               this.context.consumeAttribute(attIdx);
/* 360 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 363 */             if ("KeyName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 364 */               SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 367 */             if ("KeyValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 368 */               SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 371 */             if ("RetrievalMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 372 */               SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 375 */             if ("X509Data" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 376 */               SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 379 */             if ("PGPData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 380 */               SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 383 */             if ("SPKIData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 384 */               SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 387 */             if ("MgmtData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 388 */               SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 391 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 392 */               SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 395 */             SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 7:
/* 398 */             if ("KeyInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 399 */               SignatureTypeImpl.this._KeyInfo = (KeyInfoImpl)spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoImpl, 10, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 402 */             if ("KeyInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 403 */               this.context.pushAttributes(__atts, true);
/* 404 */               this.state = 8;
/*     */               return;
/*     */             } 
/* 407 */             this.state = 10;
/*     */             continue;
/*     */           case 12:
/* 410 */             attIdx = this.context.getAttribute("", "Encoding");
/* 411 */             if (attIdx >= 0) {
/* 412 */               this.context.consumeAttribute(attIdx);
/* 413 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 416 */             attIdx = this.context.getAttribute("", "Id");
/* 417 */             if (attIdx >= 0) {
/* 418 */               this.context.consumeAttribute(attIdx);
/* 419 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 422 */             attIdx = this.context.getAttribute("", "MimeType");
/* 423 */             if (attIdx >= 0) {
/* 424 */               this.context.consumeAttribute(attIdx);
/* 425 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               
/*     */               return;
/*     */             } 
/* 429 */             SignatureTypeImpl.this._getObject().add(spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl, 13, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */ 
/*     */ 
/*     */           
/*     */           case 11:
/* 435 */             if ("Object" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 436 */               SignatureTypeImpl.this._getObject().add(spawnChildFromEnterElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectImpl, 11, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 439 */             if ("Object" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 440 */               this.context.pushAttributes(__atts, true);
/* 441 */               this.state = 12;
/*     */               return;
/*     */             } 
/* 444 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 0:
/* 447 */             attIdx = this.context.getAttribute("", "Id");
/* 448 */             if (attIdx >= 0) {
/* 449 */               String v = this.context.eatAttribute(attIdx);
/* 450 */               this.state = 3;
/* 451 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 454 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 457 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 466 */         SignatureTypeImpl.this._Id = this.context.addToIdTable(WhiteSpaceProcessor.collapse(value));
/* 467 */       } catch (Exception e) {
/* 468 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 478 */         switch (this.state) {
/*     */           case 10:
/* 480 */             this.state = 11;
/*     */             continue;
/*     */           case 15:
/* 483 */             if ("SignedInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 484 */               this.context.popAttributes();
/* 485 */               this.state = 4;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 490 */             attIdx = this.context.getAttribute("", "Id");
/* 491 */             if (attIdx >= 0) {
/* 492 */               this.context.consumeAttribute(attIdx);
/* 493 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 14:
/* 498 */             attIdx = this.context.getAttribute("", "Id");
/* 499 */             if (attIdx >= 0) {
/* 500 */               this.context.consumeAttribute(attIdx);
/* 501 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 13:
/* 506 */             if ("Object" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 507 */               this.context.popAttributes();
/* 508 */               this.state = 11;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 513 */             attIdx = this.context.getAttribute("", "Id");
/* 514 */             if (attIdx >= 0) {
/* 515 */               this.context.consumeAttribute(attIdx);
/* 516 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 519 */             SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromLeaveElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 6:
/* 522 */             if ("SignatureValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 523 */               this.context.popAttributes();
/* 524 */               this.state = 7;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 7:
/* 529 */             this.state = 10;
/*     */             continue;
/*     */           case 9:
/* 532 */             if ("KeyInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 533 */               this.context.popAttributes();
/* 534 */               this.state = 10;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 12:
/* 539 */             attIdx = this.context.getAttribute("", "Encoding");
/* 540 */             if (attIdx >= 0) {
/* 541 */               this.context.consumeAttribute(attIdx);
/* 542 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 545 */             attIdx = this.context.getAttribute("", "Id");
/* 546 */             if (attIdx >= 0) {
/* 547 */               this.context.consumeAttribute(attIdx);
/* 548 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 551 */             attIdx = this.context.getAttribute("", "MimeType");
/* 552 */             if (attIdx >= 0) {
/* 553 */               this.context.consumeAttribute(attIdx);
/* 554 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 557 */             SignatureTypeImpl.this._getObject().add(spawnChildFromLeaveElement((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl, 13, ___uri, ___local, ___qname));
/*     */             return;
/*     */           case 11:
/* 560 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 563 */             attIdx = this.context.getAttribute("", "Id");
/* 564 */             if (attIdx >= 0) {
/* 565 */               String v = this.context.eatAttribute(attIdx);
/* 566 */               this.state = 3;
/* 567 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 570 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 573 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 584 */         switch (this.state) {
/*     */           case 10:
/* 586 */             this.state = 11;
/*     */             continue;
/*     */           case 5:
/* 589 */             if ("Id" == ___local && "" == ___uri) {
/* 590 */               SignatureTypeImpl.this._SignatureValue = (SignatureValueTypeImpl)spawnChildFromEnterAttribute((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureValueTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureValueTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureValueTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureValueTypeImpl, 6, ___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 14:
/* 595 */             if ("Id" == ___local && "" == ___uri) {
/* 596 */               SignatureTypeImpl.this._SignedInfo = (SignedInfoTypeImpl)spawnChildFromEnterAttribute((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignedInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignedInfoTypeImpl, 15, ___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 601 */             if ("Id" == ___local && "" == ___uri) {
/* 602 */               SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterAttribute((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 605 */             SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromEnterAttribute((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 7:
/* 608 */             this.state = 10;
/*     */             continue;
/*     */           case 12:
/* 611 */             if ("Encoding" == ___local && "" == ___uri) {
/* 612 */               SignatureTypeImpl.this._getObject().add(spawnChildFromEnterAttribute((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl, 13, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/* 615 */             if ("Id" == ___local && "" == ___uri) {
/* 616 */               SignatureTypeImpl.this._getObject().add(spawnChildFromEnterAttribute((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl, 13, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/* 619 */             if ("MimeType" == ___local && "" == ___uri) {
/* 620 */               SignatureTypeImpl.this._getObject().add(spawnChildFromEnterAttribute((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl, 13, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/* 623 */             SignatureTypeImpl.this._getObject().add(spawnChildFromEnterAttribute((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl, 13, ___uri, ___local, ___qname));
/*     */             return;
/*     */           case 11:
/* 626 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 629 */             if ("Id" == ___local && "" == ___uri) {
/* 630 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 633 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 636 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 647 */         switch (this.state) {
/*     */           case 10:
/* 649 */             this.state = 11;
/*     */             continue;
/*     */           case 2:
/* 652 */             if ("Id" == ___local && "" == ___uri) {
/* 653 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 658 */             attIdx = this.context.getAttribute("", "Id");
/* 659 */             if (attIdx >= 0) {
/* 660 */               this.context.consumeAttribute(attIdx);
/* 661 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 14:
/* 666 */             attIdx = this.context.getAttribute("", "Id");
/* 667 */             if (attIdx >= 0) {
/* 668 */               this.context.consumeAttribute(attIdx);
/* 669 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 674 */             attIdx = this.context.getAttribute("", "Id");
/* 675 */             if (attIdx >= 0) {
/* 676 */               this.context.consumeAttribute(attIdx);
/* 677 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 680 */             SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromLeaveAttribute((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 7:
/* 683 */             this.state = 10;
/*     */             continue;
/*     */           case 12:
/* 686 */             attIdx = this.context.getAttribute("", "Encoding");
/* 687 */             if (attIdx >= 0) {
/* 688 */               this.context.consumeAttribute(attIdx);
/* 689 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 692 */             attIdx = this.context.getAttribute("", "Id");
/* 693 */             if (attIdx >= 0) {
/* 694 */               this.context.consumeAttribute(attIdx);
/* 695 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 698 */             attIdx = this.context.getAttribute("", "MimeType");
/* 699 */             if (attIdx >= 0) {
/* 700 */               this.context.consumeAttribute(attIdx);
/* 701 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 704 */             SignatureTypeImpl.this._getObject().add(spawnChildFromLeaveAttribute((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl, 13, ___uri, ___local, ___qname));
/*     */             return;
/*     */           case 11:
/* 707 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 710 */             attIdx = this.context.getAttribute("", "Id");
/* 711 */             if (attIdx >= 0) {
/* 712 */               String v = this.context.eatAttribute(attIdx);
/* 713 */               this.state = 3;
/* 714 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 717 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 720 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 732 */           switch (this.state) {
/*     */             case 1:
/* 734 */               this.state = 2;
/* 735 */               eatText1(value);
/*     */               return;
/*     */             case 10:
/* 738 */               this.state = 11;
/*     */               continue;
/*     */             case 5:
/* 741 */               attIdx = this.context.getAttribute("", "Id");
/* 742 */               if (attIdx >= 0) {
/* 743 */                 this.context.consumeAttribute(attIdx);
/* 744 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 747 */               SignatureTypeImpl.this._SignatureValue = (SignatureValueTypeImpl)spawnChildFromText((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureValueTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureValueTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureValueTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureValueTypeImpl, 6, value);
/*     */               return;
/*     */             case 14:
/* 750 */               attIdx = this.context.getAttribute("", "Id");
/* 751 */               if (attIdx >= 0) {
/* 752 */                 this.context.consumeAttribute(attIdx);
/* 753 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/*     */               break;
/*     */             case 8:
/* 758 */               attIdx = this.context.getAttribute("", "Id");
/* 759 */               if (attIdx >= 0) {
/* 760 */                 this.context.consumeAttribute(attIdx);
/* 761 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 764 */               SignatureTypeImpl.this._KeyInfo = (KeyInfoTypeImpl)spawnChildFromText((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyInfoTypeImpl, 9, value);
/*     */               return;
/*     */             case 7:
/* 767 */               this.state = 10;
/*     */               continue;
/*     */             case 12:
/* 770 */               attIdx = this.context.getAttribute("", "Encoding");
/* 771 */               if (attIdx >= 0) {
/* 772 */                 this.context.consumeAttribute(attIdx);
/* 773 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 776 */               attIdx = this.context.getAttribute("", "Id");
/* 777 */               if (attIdx >= 0) {
/* 778 */                 this.context.consumeAttribute(attIdx);
/* 779 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 782 */               attIdx = this.context.getAttribute("", "MimeType");
/* 783 */               if (attIdx >= 0) {
/* 784 */                 this.context.consumeAttribute(attIdx);
/* 785 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 788 */               SignatureTypeImpl.this._getObject().add(spawnChildFromText((SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl == null) ? (SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl = SignatureTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectTypeImpl")) : SignatureTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ObjectTypeImpl, 13, value));
/*     */               return;
/*     */             case 11:
/* 791 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 0:
/* 794 */               attIdx = this.context.getAttribute("", "Id");
/* 795 */               if (attIdx >= 0) {
/* 796 */                 String v = this.context.eatAttribute(attIdx);
/* 797 */                 this.state = 3;
/* 798 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 801 */               this.state = 3; continue;
/*     */           }  break;
/*     */         } 
/* 804 */       } catch (RuntimeException e) {
/* 805 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SignatureTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */