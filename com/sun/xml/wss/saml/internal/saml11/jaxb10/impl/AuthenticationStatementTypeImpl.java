/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.datatype.xsd.DateTimeType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthenticationStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectLocalityType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.Calendar;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AuthenticationStatementTypeImpl extends SubjectStatementAbstractTypeImpl implements AuthenticationStatementType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected ListImpl _AuthorityBinding;
/*     */   protected Calendar _AuthenticationInstant;
/*  20 */   public static final Class version = JAXBVersion.class; protected SubjectLocalityType _SubjectLocality; protected String _AuthenticationMethod; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorityBindingImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorityBindingTypeImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  24 */     return AuthenticationStatementType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getAuthorityBinding() {
/*  28 */     if (this._AuthorityBinding == null) {
/*  29 */       this._AuthorityBinding = new ListImpl(new ArrayList());
/*     */     }
/*  31 */     return this._AuthorityBinding;
/*     */   }
/*     */   
/*     */   public List getAuthorityBinding() {
/*  35 */     return (List)_getAuthorityBinding();
/*     */   }
/*     */   
/*     */   public Calendar getAuthenticationInstant() {
/*  39 */     return this._AuthenticationInstant;
/*     */   }
/*     */   
/*     */   public void setAuthenticationInstant(Calendar value) {
/*  43 */     this._AuthenticationInstant = value;
/*     */   }
/*     */   
/*     */   public SubjectLocalityType getSubjectLocality() {
/*  47 */     return this._SubjectLocality;
/*     */   }
/*     */   
/*     */   public void setSubjectLocality(SubjectLocalityType value) {
/*  51 */     this._SubjectLocality = value;
/*     */   }
/*     */   
/*     */   public String getAuthenticationMethod() {
/*  55 */     return this._AuthenticationMethod;
/*     */   }
/*     */   
/*     */   public void setAuthenticationMethod(String value) {
/*  59 */     this._AuthenticationMethod = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  63 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  69 */     int idx1 = 0;
/*  70 */     int len1 = (this._AuthorityBinding == null) ? 0 : this._AuthorityBinding.size();
/*  71 */     super.serializeBody(context);
/*  72 */     if (this._SubjectLocality != null) {
/*  73 */       if (this._SubjectLocality instanceof javax.xml.bind.Element) {
/*  74 */         context.childAsBody((JAXBObject)this._SubjectLocality, "SubjectLocality");
/*     */       } else {
/*  76 */         context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality");
/*  77 */         context.childAsURIs((JAXBObject)this._SubjectLocality, "SubjectLocality");
/*  78 */         context.endNamespaceDecls();
/*  79 */         context.childAsAttributes((JAXBObject)this._SubjectLocality, "SubjectLocality");
/*  80 */         context.endAttributes();
/*  81 */         context.childAsBody((JAXBObject)this._SubjectLocality, "SubjectLocality");
/*  82 */         context.endElement();
/*     */       } 
/*     */     }
/*  85 */     while (idx1 != len1) {
/*  86 */       if (this._AuthorityBinding.get(idx1) instanceof javax.xml.bind.Element) {
/*  87 */         context.childAsBody((JAXBObject)this._AuthorityBinding.get(idx1++), "AuthorityBinding"); continue;
/*     */       } 
/*  89 */       context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding");
/*  90 */       int idx_2 = idx1;
/*  91 */       context.childAsURIs((JAXBObject)this._AuthorityBinding.get(idx_2++), "AuthorityBinding");
/*  92 */       context.endNamespaceDecls();
/*  93 */       int idx_3 = idx1;
/*  94 */       context.childAsAttributes((JAXBObject)this._AuthorityBinding.get(idx_3++), "AuthorityBinding");
/*  95 */       context.endAttributes();
/*  96 */       context.childAsBody((JAXBObject)this._AuthorityBinding.get(idx1++), "AuthorityBinding");
/*  97 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 105 */     int idx1 = 0;
/* 106 */     int len1 = (this._AuthorityBinding == null) ? 0 : this._AuthorityBinding.size();
/* 107 */     context.startAttribute("", "AuthenticationInstant");
/*     */     try {
/* 109 */       context.text(DateTimeType.theInstance.serializeJavaObject(this._AuthenticationInstant, null), "AuthenticationInstant");
/* 110 */     } catch (Exception e) {
/* 111 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 113 */     context.endAttribute();
/* 114 */     context.startAttribute("", "AuthenticationMethod");
/*     */     try {
/* 116 */       context.text(this._AuthenticationMethod, "AuthenticationMethod");
/* 117 */     } catch (Exception e) {
/* 118 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 120 */     context.endAttribute();
/* 121 */     super.serializeAttributes(context);
/* 122 */     if (this._SubjectLocality != null && 
/* 123 */       this._SubjectLocality instanceof javax.xml.bind.Element) {
/* 124 */       context.childAsAttributes((JAXBObject)this._SubjectLocality, "SubjectLocality");
/*     */     }
/*     */     
/* 127 */     while (idx1 != len1) {
/* 128 */       if (this._AuthorityBinding.get(idx1) instanceof javax.xml.bind.Element) {
/* 129 */         context.childAsAttributes((JAXBObject)this._AuthorityBinding.get(idx1++), "AuthorityBinding"); continue;
/*     */       } 
/* 131 */       idx1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 139 */     int idx1 = 0;
/* 140 */     int len1 = (this._AuthorityBinding == null) ? 0 : this._AuthorityBinding.size();
/* 141 */     super.serializeURIs(context);
/* 142 */     if (this._SubjectLocality != null && 
/* 143 */       this._SubjectLocality instanceof javax.xml.bind.Element) {
/* 144 */       context.childAsURIs((JAXBObject)this._SubjectLocality, "SubjectLocality");
/*     */     }
/*     */     
/* 147 */     while (idx1 != len1) {
/* 148 */       if (this._AuthorityBinding.get(idx1) instanceof javax.xml.bind.Element) {
/* 149 */         context.childAsURIs((JAXBObject)this._AuthorityBinding.get(idx1++), "AuthorityBinding"); continue;
/*     */       } 
/* 151 */       idx1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 157 */     return AuthenticationStatementType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 161 */     if (schemaFragment == null) {
/* 162 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsq\000~\000\000ppsq\000~\000\000ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\tppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\fxq\000~\000\003q\000~\000\024psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\023\001q\000~\000\030sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\031q\000~\000\036sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000 xq\000~\000\033t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.Subjectt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\013pp\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0007com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectTypeq\000~\000#sq\000~\000\tppsq\000~\000\025q\000~\000\024psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000 L\000\btypeNameq\000~\000 L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\024psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xpq\000~\0009q\000~\0008sq\000~\000\037t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\036sq\000~\000\037t\000\007Subjectt\000%urn:oasis:names:tc:SAML:1.0:assertionsq\000~\000\tppsq\000~\000\tq\000~\000\024psq\000~\000\013q\000~\000\024p\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000;com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectLocalityq\000~\000#sq\000~\000\013q\000~\000\024p\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000?com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectLocalityTypeq\000~\000#sq\000~\000\tppsq\000~\000\025q\000~\000\024pq\000~\0001q\000~\000Aq\000~\000\036sq\000~\000\037t\000\017SubjectLocalityq\000~\000Fq\000~\000\036sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\tq\000~\000\024psq\000~\000\013q\000~\000\024p\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000<com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorityBindingq\000~\000#sq\000~\000\013q\000~\000\024p\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000@com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorityBindingTypeq\000~\000#sq\000~\000\tppsq\000~\000\025q\000~\000\024pq\000~\0001q\000~\000Aq\000~\000\036sq\000~\000\037t\000\020AuthorityBindingq\000~\000Fq\000~\000\036sq\000~\000\025ppsq\000~\000.ppsr\000%com.sun.msv.datatype.xsd.DateTimeType\000\000\000\000\000\000\000\001\002\000\000xr\000)com.sun.msv.datatype.xsd.DateTimeBaseType\024W\032@3¥´å\002\000\000xq\000~\0003q\000~\0008t\000\bdateTimeq\000~\000<q\000~\000>sq\000~\000?q\000~\000uq\000~\0008sq\000~\000\037t\000\025AuthenticationInstantt\000\000sq\000~\000\025ppsq\000~\000.ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0003q\000~\0008t\000\006anyURIq\000~\000<q\000~\000>sq\000~\000?q\000~\000~q\000~\0008sq\000~\000\037t\000\024AuthenticationMethodq\000~\000ysr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\034\001pq\000~\000\007q\000~\000[q\000~\000\005q\000~\000%q\000~\000Pq\000~\000eq\000~\000\bq\000~\000\017q\000~\000'q\000~\000Jq\000~\000Rq\000~\000_q\000~\000gq\000~\000\022q\000~\000(q\000~\000Kq\000~\000Sq\000~\000`q\000~\000hq\000~\000\\q\000~\000\006q\000~\000,q\000~\000Wq\000~\000lq\000~\000Gq\000~\000\nq\000~\000Hq\000~\000]x");
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
/* 228 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AuthenticationStatementTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 237 */       super(context, "--------------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 241 */       this(context);
/* 242 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 246 */       return AuthenticationStatementTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 255 */         switch (this.state) {
/*     */           case 3:
/* 257 */             attIdx = this.context.getAttribute("", "AuthenticationMethod");
/* 258 */             if (attIdx >= 0) {
/* 259 */               String v = this.context.eatAttribute(attIdx);
/* 260 */               this.state = 6;
/* 261 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 7:
/* 266 */             if ("SubjectLocality" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 267 */               AuthenticationStatementTypeImpl.this._SubjectLocality = (SubjectLocalityImpl)spawnChildFromEnterElement((AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityImpl == null) ? (AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityImpl = AuthenticationStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectLocalityImpl")) : AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityImpl, 10, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 270 */             if ("SubjectLocality" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 271 */               this.context.pushAttributes(__atts, false);
/* 272 */               this.state = 8;
/*     */               return;
/*     */             } 
/* 275 */             this.state = 10;
/*     */             continue;
/*     */           case 6:
/* 278 */             if ("Subject" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 279 */               AuthenticationStatementTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthenticationStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 282 */             if ("Subject" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 283 */               AuthenticationStatementTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthenticationStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 286 */             AuthenticationStatementTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthenticationStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 8:
/* 289 */             attIdx = this.context.getAttribute("", "DNSAddress");
/* 290 */             if (attIdx >= 0) {
/* 291 */               this.context.consumeAttribute(attIdx);
/* 292 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 295 */             attIdx = this.context.getAttribute("", "IPAddress");
/* 296 */             if (attIdx >= 0) {
/* 297 */               this.context.consumeAttribute(attIdx);
/* 298 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 301 */             AuthenticationStatementTypeImpl.this._SubjectLocality = (SubjectLocalityTypeImpl)spawnChildFromEnterElement((AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl == null) ? (AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl = AuthenticationStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectLocalityTypeImpl")) : AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 11:
/* 304 */             attIdx = this.context.getAttribute("", "AuthorityKind");
/* 305 */             if (attIdx >= 0) {
/* 306 */               this.context.consumeAttribute(attIdx);
/* 307 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 10:
/* 312 */             if ("AuthorityBinding" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 313 */               AuthenticationStatementTypeImpl.this._getAuthorityBinding().add(spawnChildFromEnterElement((AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorityBindingImpl == null) ? (AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorityBindingImpl = AuthenticationStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorityBindingImpl")) : AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorityBindingImpl, 13, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 316 */             if ("AuthorityBinding" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 317 */               this.context.pushAttributes(__atts, false);
/* 318 */               this.state = 11;
/*     */               return;
/*     */             } 
/* 321 */             this.state = 13;
/*     */             continue;
/*     */           case 0:
/* 324 */             attIdx = this.context.getAttribute("", "AuthenticationInstant");
/* 325 */             if (attIdx >= 0) {
/* 326 */               String v = this.context.eatAttribute(attIdx);
/* 327 */               this.state = 3;
/* 328 */               eatText2(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 13:
/* 333 */             if ("AuthorityBinding" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 334 */               AuthenticationStatementTypeImpl.this._getAuthorityBinding().add(spawnChildFromEnterElement((AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorityBindingImpl == null) ? (AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorityBindingImpl = AuthenticationStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorityBindingImpl")) : AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorityBindingImpl, 13, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 337 */             if ("AuthorityBinding" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 338 */               this.context.pushAttributes(__atts, false);
/* 339 */               this.state = 11;
/*     */               return;
/*     */             } 
/* 342 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts); return;
/*     */         }  break;
/*     */       } 
/* 345 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 354 */         AuthenticationStatementTypeImpl.this._AuthenticationMethod = WhiteSpaceProcessor.collapse(value);
/* 355 */       } catch (Exception e) {
/* 356 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 364 */         AuthenticationStatementTypeImpl.this._AuthenticationInstant = (Calendar)DateTimeType.theInstance.createJavaObject(WhiteSpaceProcessor.collapse(value), null);
/* 365 */       } catch (Exception e) {
/* 366 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true)
/*     */       { int attIdx;
/* 376 */         switch (this.state) {
/*     */           case 3:
/* 378 */             attIdx = this.context.getAttribute("", "AuthenticationMethod");
/* 379 */             if (attIdx >= 0) {
/* 380 */               String v = this.context.eatAttribute(attIdx);
/* 381 */               this.state = 6;
/* 382 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 7:
/* 387 */             this.state = 10;
/*     */             continue;
/*     */           case 6:
/* 390 */             AuthenticationStatementTypeImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthenticationStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 8:
/* 393 */             attIdx = this.context.getAttribute("", "DNSAddress");
/* 394 */             if (attIdx >= 0) {
/* 395 */               this.context.consumeAttribute(attIdx);
/* 396 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 399 */             attIdx = this.context.getAttribute("", "IPAddress");
/* 400 */             if (attIdx >= 0) {
/* 401 */               this.context.consumeAttribute(attIdx);
/* 402 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 405 */             AuthenticationStatementTypeImpl.this._SubjectLocality = (SubjectLocalityTypeImpl)spawnChildFromLeaveElement((AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl == null) ? (AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl = AuthenticationStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectLocalityTypeImpl")) : AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl, 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 9:
/* 408 */             if ("SubjectLocality" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 409 */               this.context.popAttributes();
/* 410 */               this.state = 10;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 11:
/* 415 */             attIdx = this.context.getAttribute("", "AuthorityKind");
/* 416 */             if (attIdx >= 0) {
/* 417 */               this.context.consumeAttribute(attIdx);
/* 418 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 10:
/* 423 */             this.state = 13;
/*     */             continue;
/*     */           case 0:
/* 426 */             attIdx = this.context.getAttribute("", "AuthenticationInstant");
/* 427 */             if (attIdx >= 0) {
/* 428 */               String v = this.context.eatAttribute(attIdx);
/* 429 */               this.state = 3;
/* 430 */               eatText2(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 12:
/* 435 */             if ("AuthorityBinding" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 436 */               this.context.popAttributes();
/* 437 */               this.state = 13;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 13:
/* 442 */             revertToParentFromLeaveElement(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 445 */         }  super.leaveElement(___uri, ___local, ___qname); return; }  super.leaveElement(___uri, ___local, ___qname);
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
/* 456 */       { switch (this.state) {
/*     */           case 3:
/* 458 */             if ("AuthenticationMethod" == ___local && "" == ___uri) {
/* 459 */               this.state = 4;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 7:
/* 464 */             this.state = 10;
/*     */             continue;
/*     */           case 6:
/* 467 */             AuthenticationStatementTypeImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthenticationStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 8:
/* 470 */             if ("DNSAddress" == ___local && "" == ___uri) {
/* 471 */               AuthenticationStatementTypeImpl.this._SubjectLocality = (SubjectLocalityTypeImpl)spawnChildFromEnterAttribute((AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl == null) ? (AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl = AuthenticationStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectLocalityTypeImpl")) : AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl, 9, ___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 474 */             if ("IPAddress" == ___local && "" == ___uri) {
/* 475 */               AuthenticationStatementTypeImpl.this._SubjectLocality = (SubjectLocalityTypeImpl)spawnChildFromEnterAttribute((AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl == null) ? (AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl = AuthenticationStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectLocalityTypeImpl")) : AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl, 9, ___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 478 */             AuthenticationStatementTypeImpl.this._SubjectLocality = (SubjectLocalityTypeImpl)spawnChildFromEnterAttribute((AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl == null) ? (AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl = AuthenticationStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectLocalityTypeImpl")) : AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl, 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 11:
/* 481 */             if ("AuthorityKind" == ___local && "" == ___uri) {
/* 482 */               AuthenticationStatementTypeImpl.this._getAuthorityBinding().add(spawnChildFromEnterAttribute((AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorityBindingTypeImpl == null) ? (AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorityBindingTypeImpl = AuthenticationStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorityBindingTypeImpl")) : AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorityBindingTypeImpl, 12, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 10:
/* 487 */             this.state = 13;
/*     */             continue;
/*     */           case 0:
/* 490 */             if ("AuthenticationInstant" == ___local && "" == ___uri) {
/* 491 */               this.state = 1;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 13:
/* 496 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 499 */         }  super.enterAttribute(___uri, ___local, ___qname); return; }  super.enterAttribute(___uri, ___local, ___qname);
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
/* 510 */         switch (this.state) {
/*     */           case 3:
/* 512 */             attIdx = this.context.getAttribute("", "AuthenticationMethod");
/* 513 */             if (attIdx >= 0) {
/* 514 */               String v = this.context.eatAttribute(attIdx);
/* 515 */               this.state = 6;
/* 516 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 7:
/* 521 */             this.state = 10;
/*     */             continue;
/*     */           case 5:
/* 524 */             if ("AuthenticationMethod" == ___local && "" == ___uri) {
/* 525 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 530 */             if ("AuthenticationInstant" == ___local && "" == ___uri) {
/* 531 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 536 */             AuthenticationStatementTypeImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthenticationStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 8:
/* 539 */             attIdx = this.context.getAttribute("", "DNSAddress");
/* 540 */             if (attIdx >= 0) {
/* 541 */               this.context.consumeAttribute(attIdx);
/* 542 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 545 */             attIdx = this.context.getAttribute("", "IPAddress");
/* 546 */             if (attIdx >= 0) {
/* 547 */               this.context.consumeAttribute(attIdx);
/* 548 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 551 */             AuthenticationStatementTypeImpl.this._SubjectLocality = (SubjectLocalityTypeImpl)spawnChildFromLeaveAttribute((AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl == null) ? (AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl = AuthenticationStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectLocalityTypeImpl")) : AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl, 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 11:
/* 554 */             attIdx = this.context.getAttribute("", "AuthorityKind");
/* 555 */             if (attIdx >= 0) {
/* 556 */               this.context.consumeAttribute(attIdx);
/* 557 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 10:
/* 562 */             this.state = 13;
/*     */             continue;
/*     */           case 0:
/* 565 */             attIdx = this.context.getAttribute("", "AuthenticationInstant");
/* 566 */             if (attIdx >= 0) {
/* 567 */               String v = this.context.eatAttribute(attIdx);
/* 568 */               this.state = 3;
/* 569 */               eatText2(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 13:
/* 574 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 577 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 589 */           switch (this.state) {
/*     */             case 3:
/* 591 */               attIdx = this.context.getAttribute("", "AuthenticationMethod");
/* 592 */               if (attIdx >= 0) {
/* 593 */                 String v = this.context.eatAttribute(attIdx);
/* 594 */                 this.state = 6;
/* 595 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */             case 7:
/* 600 */               this.state = 10;
/*     */               continue;
/*     */             case 1:
/* 603 */               this.state = 2;
/* 604 */               eatText2(value);
/*     */               return;
/*     */             case 6:
/* 607 */               AuthenticationStatementTypeImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthenticationStatementTypeImpl.this, this.context), 7, value);
/*     */               return;
/*     */             case 8:
/* 610 */               attIdx = this.context.getAttribute("", "DNSAddress");
/* 611 */               if (attIdx >= 0) {
/* 612 */                 this.context.consumeAttribute(attIdx);
/* 613 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 616 */               attIdx = this.context.getAttribute("", "IPAddress");
/* 617 */               if (attIdx >= 0) {
/* 618 */                 this.context.consumeAttribute(attIdx);
/* 619 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 622 */               AuthenticationStatementTypeImpl.this._SubjectLocality = (SubjectLocalityTypeImpl)spawnChildFromText((AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl == null) ? (AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl = AuthenticationStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectLocalityTypeImpl")) : AuthenticationStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectLocalityTypeImpl, 9, value);
/*     */               return;
/*     */             case 11:
/* 625 */               attIdx = this.context.getAttribute("", "AuthorityKind");
/* 626 */               if (attIdx >= 0) {
/* 627 */                 this.context.consumeAttribute(attIdx);
/* 628 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/*     */               break;
/*     */             case 10:
/* 633 */               this.state = 13;
/*     */               continue;
/*     */             case 0:
/* 636 */               attIdx = this.context.getAttribute("", "AuthenticationInstant");
/* 637 */               if (attIdx >= 0) {
/* 638 */                 String v = this.context.eatAttribute(attIdx);
/* 639 */                 this.state = 3;
/* 640 */                 eatText2(v);
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */             case 4:
/* 645 */               this.state = 5;
/* 646 */               eatText1(value);
/*     */               return;
/*     */             case 13:
/* 649 */               revertToParentFromText(value); return;
/*     */           }  break;
/*     */         } 
/* 652 */       } catch (RuntimeException e) {
/* 653 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AuthenticationStatementTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */