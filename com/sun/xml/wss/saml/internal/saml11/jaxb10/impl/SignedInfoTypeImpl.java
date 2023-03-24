/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.CanonicalizationMethodType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureMethodType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SignedInfoType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SignedInfoTypeImpl implements SignedInfoType, JAXBObject, UnmarshallableObject, XMLSerializable, IdentifiableObject, ValidatableObject {
/*     */   protected SignatureMethodType _SignatureMethod;
/*     */   protected CanonicalizationMethodType _CanonicalizationMethod;
/*  18 */   public static final Class version = JAXBVersion.class; protected ListImpl _Reference; protected String _Id; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$CanonicalizationMethodImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodTypeImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$CanonicalizationMethodTypeImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  22 */     return SignedInfoType.class;
/*     */   }
/*     */   
/*     */   public SignatureMethodType getSignatureMethod() {
/*  26 */     return this._SignatureMethod;
/*     */   }
/*     */   
/*     */   public void setSignatureMethod(SignatureMethodType value) {
/*  30 */     this._SignatureMethod = value;
/*     */   }
/*     */   
/*     */   public CanonicalizationMethodType getCanonicalizationMethod() {
/*  34 */     return this._CanonicalizationMethod;
/*     */   }
/*     */   
/*     */   public void setCanonicalizationMethod(CanonicalizationMethodType value) {
/*  38 */     this._CanonicalizationMethod = value;
/*     */   }
/*     */   
/*     */   protected ListImpl _getReference() {
/*  42 */     if (this._Reference == null) {
/*  43 */       this._Reference = new ListImpl(new ArrayList());
/*     */     }
/*  45 */     return this._Reference;
/*     */   }
/*     */   
/*     */   public List getReference() {
/*  49 */     return (List)_getReference();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  53 */     return this._Id;
/*     */   }
/*     */   
/*     */   public void setId(String value) {
/*  57 */     this._Id = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  61 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  67 */     int idx3 = 0;
/*  68 */     int len3 = (this._Reference == null) ? 0 : this._Reference.size();
/*  69 */     if (this._CanonicalizationMethod instanceof javax.xml.bind.Element) {
/*  70 */       context.childAsBody((JAXBObject)this._CanonicalizationMethod, "CanonicalizationMethod");
/*     */     } else {
/*  72 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "CanonicalizationMethod");
/*  73 */       context.childAsURIs((JAXBObject)this._CanonicalizationMethod, "CanonicalizationMethod");
/*  74 */       context.endNamespaceDecls();
/*  75 */       context.childAsAttributes((JAXBObject)this._CanonicalizationMethod, "CanonicalizationMethod");
/*  76 */       context.endAttributes();
/*  77 */       context.childAsBody((JAXBObject)this._CanonicalizationMethod, "CanonicalizationMethod");
/*  78 */       context.endElement();
/*     */     } 
/*  80 */     if (this._SignatureMethod instanceof javax.xml.bind.Element) {
/*  81 */       context.childAsBody((JAXBObject)this._SignatureMethod, "SignatureMethod");
/*     */     } else {
/*  83 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "SignatureMethod");
/*  84 */       context.childAsURIs((JAXBObject)this._SignatureMethod, "SignatureMethod");
/*  85 */       context.endNamespaceDecls();
/*  86 */       context.childAsAttributes((JAXBObject)this._SignatureMethod, "SignatureMethod");
/*  87 */       context.endAttributes();
/*  88 */       context.childAsBody((JAXBObject)this._SignatureMethod, "SignatureMethod");
/*  89 */       context.endElement();
/*     */     } 
/*  91 */     while (idx3 != len3) {
/*  92 */       if (this._Reference.get(idx3) instanceof javax.xml.bind.Element) {
/*  93 */         context.childAsBody((JAXBObject)this._Reference.get(idx3++), "Reference"); continue;
/*     */       } 
/*  95 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "Reference");
/*  96 */       int idx_4 = idx3;
/*  97 */       context.childAsURIs((JAXBObject)this._Reference.get(idx_4++), "Reference");
/*  98 */       context.endNamespaceDecls();
/*  99 */       int idx_5 = idx3;
/* 100 */       context.childAsAttributes((JAXBObject)this._Reference.get(idx_5++), "Reference");
/* 101 */       context.endAttributes();
/* 102 */       context.childAsBody((JAXBObject)this._Reference.get(idx3++), "Reference");
/* 103 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 111 */     int idx3 = 0;
/* 112 */     int len3 = (this._Reference == null) ? 0 : this._Reference.size();
/* 113 */     if (this._Id != null) {
/* 114 */       context.startAttribute("", "Id");
/*     */       try {
/* 116 */         context.text(context.onID(this, this._Id), "Id");
/* 117 */       } catch (Exception e) {
/* 118 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 120 */       context.endAttribute();
/*     */     } 
/* 122 */     if (this._CanonicalizationMethod instanceof javax.xml.bind.Element) {
/* 123 */       context.childAsAttributes((JAXBObject)this._CanonicalizationMethod, "CanonicalizationMethod");
/*     */     }
/* 125 */     if (this._SignatureMethod instanceof javax.xml.bind.Element) {
/* 126 */       context.childAsAttributes((JAXBObject)this._SignatureMethod, "SignatureMethod");
/*     */     }
/* 128 */     while (idx3 != len3) {
/* 129 */       if (this._Reference.get(idx3) instanceof javax.xml.bind.Element) {
/* 130 */         context.childAsAttributes((JAXBObject)this._Reference.get(idx3++), "Reference"); continue;
/*     */       } 
/* 132 */       idx3++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 140 */     int idx3 = 0;
/* 141 */     int len3 = (this._Reference == null) ? 0 : this._Reference.size();
/* 142 */     if (this._CanonicalizationMethod instanceof javax.xml.bind.Element) {
/* 143 */       context.childAsURIs((JAXBObject)this._CanonicalizationMethod, "CanonicalizationMethod");
/*     */     }
/* 145 */     if (this._SignatureMethod instanceof javax.xml.bind.Element) {
/* 146 */       context.childAsURIs((JAXBObject)this._SignatureMethod, "SignatureMethod");
/*     */     }
/* 148 */     while (idx3 != len3) {
/* 149 */       if (this._Reference.get(idx3) instanceof javax.xml.bind.Element) {
/* 150 */         context.childAsURIs((JAXBObject)this._Reference.get(idx3++), "Reference"); continue;
/*     */       } 
/* 152 */       idx3++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String ____jaxb____getId() {
/* 158 */     return this._Id;
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 162 */     return SignedInfoType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 166 */     if (schemaFragment == null) {
/* 167 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsq\000~\000\000ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\bppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\013xq\000~\000\003q\000~\000\023psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\022\001q\000~\000\027sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\030q\000~\000\035sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\037xq\000~\000\032t\000Bcom.sun.xml.wss.saml.internal.saml11.jaxb10.CanonicalizationMethodt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\npp\000sq\000~\000\000ppsq\000~\000\npp\000sq\000~\000\bppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\000Fcom.sun.xml.wss.saml.internal.saml11.jaxb10.CanonicalizationMethodTypeq\000~\000\"sq\000~\000\bppsq\000~\000\024q\000~\000\023psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\037L\000\btypeNameq\000~\000\037L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\023psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\037L\000\fnamespaceURIq\000~\000\037xpq\000~\0008q\000~\0007sq\000~\000\036t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\035sq\000~\000\036t\000\026CanonicalizationMethodt\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\bppsq\000~\000\npp\000sq\000~\000\bppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\000;com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureMethodq\000~\000\"sq\000~\000\npp\000sq\000~\000\000ppsq\000~\000\npp\000sq\000~\000\bppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\000?com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureMethodTypeq\000~\000\"sq\000~\000\bppsq\000~\000\024q\000~\000\023pq\000~\0000q\000~\000@q\000~\000\035sq\000~\000\036t\000\017SignatureMethodq\000~\000Esq\000~\000\017ppsq\000~\000\bppsq\000~\000\npp\000sq\000~\000\bppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Referenceq\000~\000\"sq\000~\000\npp\000sq\000~\000\000ppsq\000~\000\npp\000sq\000~\000\bppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\0009com.sun.xml.wss.saml.internal.saml11.jaxb10.ReferenceTypeq\000~\000\"sq\000~\000\bppsq\000~\000\024q\000~\000\023pq\000~\0000q\000~\000@q\000~\000\035sq\000~\000\036t\000\tReferenceq\000~\000Esq\000~\000\bppsq\000~\000\024q\000~\000\023psq\000~\000-ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\0002q\000~\0007t\000\002IDq\000~\000;\000q\000~\000=sq\000~\000>q\000~\000uq\000~\0007sq\000~\000\036t\000\002Idt\000\000q\000~\000\035sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\032\001pq\000~\000\007q\000~\000$q\000~\000Nq\000~\000bq\000~\000\016q\000~\000&q\000~\000Hq\000~\000Pq\000~\000\\q\000~\000dq\000~\000\005q\000~\000\021q\000~\000'q\000~\000Iq\000~\000Qq\000~\000]q\000~\000eq\000~\000mq\000~\000Yq\000~\000+q\000~\000Uq\000~\000iq\000~\000\006q\000~\000\tq\000~\000Fq\000~\000Zx");
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
/* 232 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SignedInfoTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 241 */       super(context, "-------------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 245 */       this(context);
/* 246 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 250 */       return SignedInfoTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true)
/*     */       { int attIdx;
/* 259 */         switch (this.state) {
/*     */           case 0:
/* 261 */             attIdx = this.context.getAttribute("", "Id");
/* 262 */             if (attIdx >= 0) {
/* 263 */               String v = this.context.eatAttribute(attIdx);
/* 264 */               this.state = 3;
/* 265 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 268 */             this.state = 3;
/*     */             continue;
/*     */           case 7:
/* 271 */             if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 272 */               SignedInfoTypeImpl.this._getReference().add(spawnChildFromEnterElement((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl, 8, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 275 */             if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 276 */               this.context.pushAttributes(__atts, false);
/* 277 */               this.state = 9;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 282 */             if ("CanonicalizationMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 283 */               SignedInfoTypeImpl.this._CanonicalizationMethod = (CanonicalizationMethodImpl)spawnChildFromEnterElement((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$CanonicalizationMethodImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$CanonicalizationMethodImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.CanonicalizationMethodImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$CanonicalizationMethodImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 286 */             if ("CanonicalizationMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 287 */               this.context.pushAttributes(__atts, true);
/* 288 */               this.state = 4;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 9:
/* 293 */             attIdx = this.context.getAttribute("", "Id");
/* 294 */             if (attIdx >= 0) {
/* 295 */               this.context.consumeAttribute(attIdx);
/* 296 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 299 */             attIdx = this.context.getAttribute("", "Type");
/* 300 */             if (attIdx >= 0) {
/* 301 */               this.context.consumeAttribute(attIdx);
/* 302 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 305 */             attIdx = this.context.getAttribute("", "URI");
/* 306 */             if (attIdx >= 0) {
/* 307 */               this.context.consumeAttribute(attIdx);
/* 308 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 311 */             if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 312 */               SignedInfoTypeImpl.this._getReference().add(spawnChildFromEnterElement((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 10, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 315 */             if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 316 */               SignedInfoTypeImpl.this._getReference().add(spawnChildFromEnterElement((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 10, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 319 */             if ("DigestMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 320 */               SignedInfoTypeImpl.this._getReference().add(spawnChildFromEnterElement((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 10, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 323 */             if ("DigestMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 324 */               SignedInfoTypeImpl.this._getReference().add(spawnChildFromEnterElement((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 10, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 329 */             if ("SignatureMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 330 */               SignedInfoTypeImpl.this._SignatureMethod = (SignatureMethodImpl)spawnChildFromEnterElement((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureMethodImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodImpl, 7, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 333 */             if ("SignatureMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 334 */               this.context.pushAttributes(__atts, true);
/* 335 */               this.state = 11;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 11:
/* 340 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 341 */             if (attIdx >= 0) {
/* 342 */               this.context.consumeAttribute(attIdx);
/* 343 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 348 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 349 */             if (attIdx >= 0) {
/* 350 */               this.context.consumeAttribute(attIdx);
/* 351 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 356 */             if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 357 */               SignedInfoTypeImpl.this._getReference().add(spawnChildFromEnterElement((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl, 8, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 360 */             if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 361 */               this.context.pushAttributes(__atts, false);
/* 362 */               this.state = 9;
/*     */               return;
/*     */             } 
/* 365 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts); return;
/*     */           default:
/*     */             break;
/* 368 */         }  super.enterElement(___uri, ___local, ___qname, __atts); return; }  super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 377 */         SignedInfoTypeImpl.this._Id = this.context.addToIdTable(WhiteSpaceProcessor.collapse(value));
/* 378 */       } catch (Exception e) {
/* 379 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true)
/*     */       { int attIdx;
/* 389 */         switch (this.state) {
/*     */           case 0:
/* 391 */             attIdx = this.context.getAttribute("", "Id");
/* 392 */             if (attIdx >= 0) {
/* 393 */               String v = this.context.eatAttribute(attIdx);
/* 394 */               this.state = 3;
/* 395 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 398 */             this.state = 3;
/*     */             continue;
/*     */           case 5:
/* 401 */             if ("CanonicalizationMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 402 */               this.context.popAttributes();
/* 403 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 9:
/* 408 */             attIdx = this.context.getAttribute("", "Id");
/* 409 */             if (attIdx >= 0) {
/* 410 */               this.context.consumeAttribute(attIdx);
/* 411 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 414 */             attIdx = this.context.getAttribute("", "Type");
/* 415 */             if (attIdx >= 0) {
/* 416 */               this.context.consumeAttribute(attIdx);
/* 417 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 420 */             attIdx = this.context.getAttribute("", "URI");
/* 421 */             if (attIdx >= 0) {
/* 422 */               this.context.consumeAttribute(attIdx);
/* 423 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 10:
/* 428 */             if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 429 */               this.context.popAttributes();
/* 430 */               this.state = 8;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 11:
/* 435 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 436 */             if (attIdx >= 0) {
/* 437 */               this.context.consumeAttribute(attIdx);
/* 438 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 443 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 444 */             if (attIdx >= 0) {
/* 445 */               this.context.consumeAttribute(attIdx);
/* 446 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 12:
/* 451 */             if ("SignatureMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 452 */               this.context.popAttributes();
/* 453 */               this.state = 7;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 458 */             revertToParentFromLeaveElement(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 461 */         }  super.leaveElement(___uri, ___local, ___qname); return; }  super.leaveElement(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true)
/* 472 */       { switch (this.state) {
/*     */           case 0:
/* 474 */             if ("Id" == ___local && "" == ___uri) {
/* 475 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 478 */             this.state = 3;
/*     */             continue;
/*     */           case 9:
/* 481 */             if ("Id" == ___local && "" == ___uri) {
/* 482 */               SignedInfoTypeImpl.this._getReference().add(spawnChildFromEnterAttribute((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 10, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/* 485 */             if ("Type" == ___local && "" == ___uri) {
/* 486 */               SignedInfoTypeImpl.this._getReference().add(spawnChildFromEnterAttribute((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 10, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/* 489 */             if ("URI" == ___local && "" == ___uri) {
/* 490 */               SignedInfoTypeImpl.this._getReference().add(spawnChildFromEnterAttribute((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 10, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 11:
/* 495 */             if ("Algorithm" == ___local && "" == ___uri) {
/* 496 */               SignedInfoTypeImpl.this._SignatureMethod = (SignatureMethodTypeImpl)spawnChildFromEnterAttribute((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodTypeImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodTypeImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureMethodTypeImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodTypeImpl, 12, ___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 501 */             if ("Algorithm" == ___local && "" == ___uri) {
/* 502 */               SignedInfoTypeImpl.this._CanonicalizationMethod = (CanonicalizationMethodTypeImpl)spawnChildFromEnterAttribute((SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$CanonicalizationMethodTypeImpl == null) ? (SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$CanonicalizationMethodTypeImpl = SignedInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.CanonicalizationMethodTypeImpl")) : SignedInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$CanonicalizationMethodTypeImpl, 5, ___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 507 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 510 */         }  super.enterAttribute(___uri, ___local, ___qname); return; }  super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true)
/*     */       { int attIdx;
/* 521 */         switch (this.state) {
/*     */           case 0:
/* 523 */             attIdx = this.context.getAttribute("", "Id");
/* 524 */             if (attIdx >= 0) {
/* 525 */               String v = this.context.eatAttribute(attIdx);
/* 526 */               this.state = 3;
/* 527 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 530 */             this.state = 3;
/*     */             continue;
/*     */           case 9:
/* 533 */             attIdx = this.context.getAttribute("", "Id");
/* 534 */             if (attIdx >= 0) {
/* 535 */               this.context.consumeAttribute(attIdx);
/* 536 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 539 */             attIdx = this.context.getAttribute("", "Type");
/* 540 */             if (attIdx >= 0) {
/* 541 */               this.context.consumeAttribute(attIdx);
/* 542 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 545 */             attIdx = this.context.getAttribute("", "URI");
/* 546 */             if (attIdx >= 0) {
/* 547 */               this.context.consumeAttribute(attIdx);
/* 548 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 11:
/* 553 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 554 */             if (attIdx >= 0) {
/* 555 */               this.context.consumeAttribute(attIdx);
/* 556 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 561 */             if ("Id" == ___local && "" == ___uri) {
/* 562 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 567 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 568 */             if (attIdx >= 0) {
/* 569 */               this.context.consumeAttribute(attIdx);
/* 570 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 575 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 578 */         }  super.leaveAttribute(___uri, ___local, ___qname); return; }  super.leaveAttribute(___uri, ___local, ___qname);
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
/* 590 */           switch (this.state) {
/*     */             case 0:
/* 592 */               attIdx = this.context.getAttribute("", "Id");
/* 593 */               if (attIdx >= 0) {
/* 594 */                 String v = this.context.eatAttribute(attIdx);
/* 595 */                 this.state = 3;
/* 596 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 599 */               this.state = 3;
/*     */               continue;
/*     */             case 9:
/* 602 */               attIdx = this.context.getAttribute("", "Id");
/* 603 */               if (attIdx >= 0) {
/* 604 */                 this.context.consumeAttribute(attIdx);
/* 605 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 608 */               attIdx = this.context.getAttribute("", "Type");
/* 609 */               if (attIdx >= 0) {
/* 610 */                 this.context.consumeAttribute(attIdx);
/* 611 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 614 */               attIdx = this.context.getAttribute("", "URI");
/* 615 */               if (attIdx >= 0) {
/* 616 */                 this.context.consumeAttribute(attIdx);
/* 617 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/*     */               return;
/*     */             case 1:
/* 622 */               this.state = 2;
/* 623 */               eatText1(value);
/*     */               return;
/*     */             case 11:
/* 626 */               attIdx = this.context.getAttribute("", "Algorithm");
/* 627 */               if (attIdx >= 0) {
/* 628 */                 this.context.consumeAttribute(attIdx);
/* 629 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/*     */               return;
/*     */             case 4:
/* 634 */               attIdx = this.context.getAttribute("", "Algorithm");
/* 635 */               if (attIdx >= 0) {
/* 636 */                 this.context.consumeAttribute(attIdx);
/* 637 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/*     */               return;
/*     */             case 8:
/* 642 */               revertToParentFromText(value); return;
/*     */           }  break;
/*     */         } 
/* 645 */       } catch (RuntimeException e) {
/* 646 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SignedInfoTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */