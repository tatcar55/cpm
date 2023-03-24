/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorizationDecisionStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.EvidenceType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AuthorizationDecisionStatementTypeImpl extends SubjectStatementAbstractTypeImpl implements AuthorizationDecisionStatementType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _Resource;
/*     */   protected String _Decision;
/*  20 */   public static final Class version = JAXBVersion.class; protected ListImpl _Action; protected EvidenceType _Evidence; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$EvidenceTypeImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$EvidenceImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionTypeImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  24 */     return AuthorizationDecisionStatementType.class;
/*     */   }
/*     */   
/*     */   public String getResource() {
/*  28 */     return this._Resource;
/*     */   }
/*     */   
/*     */   public void setResource(String value) {
/*  32 */     this._Resource = value;
/*     */   }
/*     */   
/*     */   public String getDecision() {
/*  36 */     return this._Decision;
/*     */   }
/*     */   
/*     */   public void setDecision(String value) {
/*  40 */     this._Decision = value;
/*     */   }
/*     */   
/*     */   protected ListImpl _getAction() {
/*  44 */     if (this._Action == null) {
/*  45 */       this._Action = new ListImpl(new ArrayList());
/*     */     }
/*  47 */     return this._Action;
/*     */   }
/*     */   
/*     */   public List getAction() {
/*  51 */     return (List)_getAction();
/*     */   }
/*     */   
/*     */   public EvidenceType getEvidence() {
/*  55 */     return this._Evidence;
/*     */   }
/*     */   
/*     */   public void setEvidence(EvidenceType value) {
/*  59 */     this._Evidence = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  63 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  69 */     int idx3 = 0;
/*  70 */     int len3 = (this._Action == null) ? 0 : this._Action.size();
/*  71 */     super.serializeBody(context);
/*  72 */     while (idx3 != len3) {
/*  73 */       if (this._Action.get(idx3) instanceof javax.xml.bind.Element) {
/*  74 */         context.childAsBody((JAXBObject)this._Action.get(idx3++), "Action"); continue;
/*     */       } 
/*  76 */       context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "Action");
/*  77 */       int idx_0 = idx3;
/*  78 */       context.childAsURIs((JAXBObject)this._Action.get(idx_0++), "Action");
/*  79 */       context.endNamespaceDecls();
/*  80 */       int idx_1 = idx3;
/*  81 */       context.childAsAttributes((JAXBObject)this._Action.get(idx_1++), "Action");
/*  82 */       context.endAttributes();
/*  83 */       context.childAsBody((JAXBObject)this._Action.get(idx3++), "Action");
/*  84 */       context.endElement();
/*     */     } 
/*     */     
/*  87 */     if (this._Evidence != null) {
/*  88 */       if (this._Evidence instanceof javax.xml.bind.Element) {
/*  89 */         context.childAsBody((JAXBObject)this._Evidence, "Evidence");
/*     */       } else {
/*  91 */         context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "Evidence");
/*  92 */         context.childAsURIs((JAXBObject)this._Evidence, "Evidence");
/*  93 */         context.endNamespaceDecls();
/*  94 */         context.childAsAttributes((JAXBObject)this._Evidence, "Evidence");
/*  95 */         context.endAttributes();
/*  96 */         context.childAsBody((JAXBObject)this._Evidence, "Evidence");
/*  97 */         context.endElement();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 105 */     int idx3 = 0;
/* 106 */     int len3 = (this._Action == null) ? 0 : this._Action.size();
/* 107 */     context.startAttribute("", "Decision");
/*     */     try {
/* 109 */       context.text(this._Decision, "Decision");
/* 110 */     } catch (Exception e) {
/* 111 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 113 */     context.endAttribute();
/* 114 */     context.startAttribute("", "Resource");
/*     */     try {
/* 116 */       context.text(this._Resource, "Resource");
/* 117 */     } catch (Exception e) {
/* 118 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 120 */     context.endAttribute();
/* 121 */     super.serializeAttributes(context);
/* 122 */     while (idx3 != len3) {
/* 123 */       if (this._Action.get(idx3) instanceof javax.xml.bind.Element) {
/* 124 */         context.childAsAttributes((JAXBObject)this._Action.get(idx3++), "Action"); continue;
/*     */       } 
/* 126 */       idx3++;
/*     */     } 
/*     */     
/* 129 */     if (this._Evidence != null && 
/* 130 */       this._Evidence instanceof javax.xml.bind.Element) {
/* 131 */       context.childAsAttributes((JAXBObject)this._Evidence, "Evidence");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 139 */     int idx3 = 0;
/* 140 */     int len3 = (this._Action == null) ? 0 : this._Action.size();
/* 141 */     super.serializeURIs(context);
/* 142 */     while (idx3 != len3) {
/* 143 */       if (this._Action.get(idx3) instanceof javax.xml.bind.Element) {
/* 144 */         context.childAsURIs((JAXBObject)this._Action.get(idx3++), "Action"); continue;
/*     */       } 
/* 146 */       idx3++;
/*     */     } 
/*     */     
/* 149 */     if (this._Evidence != null && 
/* 150 */       this._Evidence instanceof javax.xml.bind.Element) {
/* 151 */       context.childAsURIs((JAXBObject)this._Evidence, "Evidence");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 157 */     return AuthorizationDecisionStatementType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 161 */     if (schemaFragment == null) {
/* 162 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsq\000~\000\000ppsq\000~\000\000ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\tppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\fxq\000~\000\003q\000~\000\024psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\023\001q\000~\000\030sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\031q\000~\000\036sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000 xq\000~\000\033t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.Subjectt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\013pp\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0007com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectTypeq\000~\000#sq\000~\000\tppsq\000~\000\025q\000~\000\024psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000 L\000\btypeNameq\000~\000 L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\024psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xpq\000~\0009q\000~\0008sq\000~\000\037t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\036sq\000~\000\037t\000\007Subjectt\000%urn:oasis:names:tc:SAML:1.0:assertionsq\000~\000\020ppsq\000~\000\tppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0002com.sun.xml.wss.saml.internal.saml11.jaxb10.Actionq\000~\000#sq\000~\000\013pp\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.ActionTypeq\000~\000#sq\000~\000\tppsq\000~\000\025q\000~\000\024pq\000~\0001q\000~\000Aq\000~\000\036sq\000~\000\037t\000\006Actionq\000~\000Fsq\000~\000\tppsq\000~\000\tq\000~\000\024psq\000~\000\013q\000~\000\024p\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0004com.sun.xml.wss.saml.internal.saml11.jaxb10.Evidenceq\000~\000#sq\000~\000\013q\000~\000\024p\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\020q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0008com.sun.xml.wss.saml.internal.saml11.jaxb10.EvidenceTypeq\000~\000#sq\000~\000\tppsq\000~\000\025q\000~\000\024pq\000~\0001q\000~\000Aq\000~\000\036sq\000~\000\037t\000\bEvidenceq\000~\000Fq\000~\000\036sq\000~\000\025ppsq\000~\000.ppsr\000)com.sun.msv.datatype.xsd.EnumerationFacet\000\000\000\000\000\000\000\001\002\000\001L\000\006valuest\000\017Ljava/util/Set;xr\0009com.sun.msv.datatype.xsd.DataTypeWithValueConstraintFacet\"§RoÊÇT\002\000\000xr\000*com.sun.msv.datatype.xsd.DataTypeWithFacet\000\000\000\000\000\000\000\001\002\000\005Z\000\fisFacetFixedZ\000\022needValueCheckFlagL\000\bbaseTypet\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;L\000\fconcreteTypet\000'Lcom/sun/msv/datatype/xsd/ConcreteType;L\000\tfacetNameq\000~\000 xq\000~\0005q\000~\000Ft\000\fDecisionTypesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xq\000~\000;\000\000sr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\0003q\000~\0008t\000\006stringq\000~\000z\001q\000~\000|t\000\013enumerationsr\000\021java.util.HashSetºD¸·4\003\000\000xpw\f\000\000\000\020?@\000\000\000\000\000\003t\000\004Denyt\000\rIndeterminatet\000\006Permitxq\000~\000>sq\000~\000?q\000~\000xq\000~\000Fsq\000~\000\037t\000\bDecisiont\000\000sq\000~\000\025ppsq\000~\000.ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0003q\000~\0008t\000\006anyURIq\000~\000<q\000~\000>sq\000~\000?q\000~\000q\000~\0008sq\000~\000\037t\000\bResourceq\000~\000sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\033\001pq\000~\000\006q\000~\000%q\000~\000Pq\000~\000dq\000~\000\007q\000~\000\017q\000~\000'q\000~\000Jq\000~\000Rq\000~\000^q\000~\000fq\000~\000\022q\000~\000(q\000~\000Kq\000~\000Sq\000~\000_q\000~\000gq\000~\000Gq\000~\000,q\000~\000Wq\000~\000kq\000~\000\005q\000~\000[q\000~\000\nq\000~\000Hq\000~\000\\q\000~\000\bx");
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
/*     */ 
/*     */     
/* 235 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AuthorizationDecisionStatementTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 244 */       super(context, "--------------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 248 */       this(context);
/* 249 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 253 */       return AuthorizationDecisionStatementTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 262 */         switch (this.state) {
/*     */           case 8:
/* 264 */             attIdx = this.context.getAttribute("", "Namespace");
/* 265 */             if (attIdx >= 0) {
/* 266 */               this.context.consumeAttribute(attIdx);
/* 267 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 272 */             if ("Subject" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 273 */               AuthorizationDecisionStatementTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthorizationDecisionStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 276 */             if ("Subject" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 277 */               AuthorizationDecisionStatementTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthorizationDecisionStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 280 */             AuthorizationDecisionStatementTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthorizationDecisionStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 7:
/* 283 */             if ("Action" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 284 */               AuthorizationDecisionStatementTypeImpl.this._getAction().add(spawnChildFromEnterElement((AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionImpl == null) ? (AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionImpl = AuthorizationDecisionStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ActionImpl")) : AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionImpl, 10, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 287 */             if ("Action" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 288 */               this.context.pushAttributes(__atts, true);
/* 289 */               this.state = 8;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 11:
/* 294 */             if ("AssertionIDReference" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 295 */               AuthorizationDecisionStatementTypeImpl.this._Evidence = (EvidenceTypeImpl)spawnChildFromEnterElement((AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$EvidenceTypeImpl == null) ? (AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$EvidenceTypeImpl = AuthorizationDecisionStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.EvidenceTypeImpl")) : AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$EvidenceTypeImpl, 12, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 298 */             if ("Assertion" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 299 */               AuthorizationDecisionStatementTypeImpl.this._Evidence = (EvidenceTypeImpl)spawnChildFromEnterElement((AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$EvidenceTypeImpl == null) ? (AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$EvidenceTypeImpl = AuthorizationDecisionStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.EvidenceTypeImpl")) : AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$EvidenceTypeImpl, 12, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 304 */             attIdx = this.context.getAttribute("", "Resource");
/* 305 */             if (attIdx >= 0) {
/* 306 */               String v = this.context.eatAttribute(attIdx);
/* 307 */               this.state = 6;
/* 308 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 13:
/* 313 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 10:
/* 316 */             if ("Action" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 317 */               AuthorizationDecisionStatementTypeImpl.this._getAction().add(spawnChildFromEnterElement((AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionImpl == null) ? (AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionImpl = AuthorizationDecisionStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ActionImpl")) : AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionImpl, 10, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 320 */             if ("Action" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 321 */               this.context.pushAttributes(__atts, true);
/* 322 */               this.state = 8;
/*     */               return;
/*     */             } 
/* 325 */             if ("Evidence" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 326 */               AuthorizationDecisionStatementTypeImpl.this._Evidence = (EvidenceImpl)spawnChildFromEnterElement((AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$EvidenceImpl == null) ? (AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$EvidenceImpl = AuthorizationDecisionStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.EvidenceImpl")) : AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$EvidenceImpl, 13, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 329 */             if ("Evidence" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 330 */               this.context.pushAttributes(__atts, false);
/* 331 */               this.state = 11;
/*     */               return;
/*     */             } 
/* 334 */             this.state = 13;
/*     */             continue;
/*     */           case 0:
/* 337 */             attIdx = this.context.getAttribute("", "Decision");
/* 338 */             if (attIdx >= 0) {
/* 339 */               String v = this.context.eatAttribute(attIdx);
/* 340 */               this.state = 3;
/* 341 */               eatText2(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 346 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 355 */         AuthorizationDecisionStatementTypeImpl.this._Resource = WhiteSpaceProcessor.collapse(value);
/* 356 */       } catch (Exception e) {
/* 357 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 365 */         AuthorizationDecisionStatementTypeImpl.this._Decision = value;
/* 366 */       } catch (Exception e) {
/* 367 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 377 */         switch (this.state) {
/*     */           case 8:
/* 379 */             attIdx = this.context.getAttribute("", "Namespace");
/* 380 */             if (attIdx >= 0) {
/* 381 */               this.context.consumeAttribute(attIdx);
/* 382 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 387 */             AuthorizationDecisionStatementTypeImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthorizationDecisionStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 12:
/* 390 */             if ("Evidence" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 391 */               this.context.popAttributes();
/* 392 */               this.state = 13;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 397 */             attIdx = this.context.getAttribute("", "Resource");
/* 398 */             if (attIdx >= 0) {
/* 399 */               String v = this.context.eatAttribute(attIdx);
/* 400 */               this.state = 6;
/* 401 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 9:
/* 406 */             if ("Action" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 407 */               this.context.popAttributes();
/* 408 */               this.state = 10;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 13:
/* 413 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 10:
/* 416 */             this.state = 13;
/*     */             continue;
/*     */           case 0:
/* 419 */             attIdx = this.context.getAttribute("", "Decision");
/* 420 */             if (attIdx >= 0) {
/* 421 */               String v = this.context.eatAttribute(attIdx);
/* 422 */               this.state = 3;
/* 423 */               eatText2(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 428 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 439 */       { switch (this.state) {
/*     */           case 8:
/* 441 */             if ("Namespace" == ___local && "" == ___uri) {
/* 442 */               AuthorizationDecisionStatementTypeImpl.this._getAction().add(spawnChildFromEnterAttribute((AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionTypeImpl == null) ? (AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionTypeImpl = AuthorizationDecisionStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ActionTypeImpl")) : AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionTypeImpl, 9, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 447 */             AuthorizationDecisionStatementTypeImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthorizationDecisionStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 450 */             if ("Resource" == ___local && "" == ___uri) {
/* 451 */               this.state = 4;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 13:
/* 456 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 10:
/* 459 */             this.state = 13;
/*     */             continue;
/*     */           case 0:
/* 462 */             if ("Decision" == ___local && "" == ___uri) {
/* 463 */               this.state = 1; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 468 */         super.enterAttribute(___uri, ___local, ___qname); return; }  super.enterAttribute(___uri, ___local, ___qname);
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
/* 479 */         switch (this.state) {
/*     */           case 8:
/* 481 */             attIdx = this.context.getAttribute("", "Namespace");
/* 482 */             if (attIdx >= 0) {
/* 483 */               this.context.consumeAttribute(attIdx);
/* 484 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 489 */             AuthorizationDecisionStatementTypeImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthorizationDecisionStatementTypeImpl.this, this.context), 7, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 5:
/* 492 */             if ("Resource" == ___local && "" == ___uri) {
/* 493 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 498 */             attIdx = this.context.getAttribute("", "Resource");
/* 499 */             if (attIdx >= 0) {
/* 500 */               String v = this.context.eatAttribute(attIdx);
/* 501 */               this.state = 6;
/* 502 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 507 */             if ("Decision" == ___local && "" == ___uri) {
/* 508 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 13:
/* 513 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 10:
/* 516 */             this.state = 13;
/*     */             continue;
/*     */           case 0:
/* 519 */             attIdx = this.context.getAttribute("", "Decision");
/* 520 */             if (attIdx >= 0) {
/* 521 */               String v = this.context.eatAttribute(attIdx);
/* 522 */               this.state = 3;
/* 523 */               eatText2(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 528 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 540 */           switch (this.state) {
/*     */             case 8:
/* 542 */               attIdx = this.context.getAttribute("", "Namespace");
/* 543 */               if (attIdx >= 0) {
/* 544 */                 this.context.consumeAttribute(attIdx);
/* 545 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 548 */               AuthorizationDecisionStatementTypeImpl.this._getAction().add(spawnChildFromText((AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionTypeImpl == null) ? (AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionTypeImpl = AuthorizationDecisionStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ActionTypeImpl")) : AuthorizationDecisionStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ActionTypeImpl, 9, value));
/*     */               return;
/*     */             case 6:
/* 551 */               AuthorizationDecisionStatementTypeImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AuthorizationDecisionStatementTypeImpl.this, this.context), 7, value);
/*     */               return;
/*     */             case 1:
/* 554 */               this.state = 2;
/* 555 */               eatText2(value);
/*     */               return;
/*     */             case 3:
/* 558 */               attIdx = this.context.getAttribute("", "Resource");
/* 559 */               if (attIdx >= 0) {
/* 560 */                 String v = this.context.eatAttribute(attIdx);
/* 561 */                 this.state = 6;
/* 562 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */             case 4:
/* 567 */               this.state = 5;
/* 568 */               eatText1(value);
/*     */               return;
/*     */             case 13:
/* 571 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 10:
/* 574 */               this.state = 13;
/*     */               continue;
/*     */             case 0:
/* 577 */               attIdx = this.context.getAttribute("", "Decision");
/* 578 */               if (attIdx >= 0) {
/* 579 */                 String v = this.context.eatAttribute(attIdx);
/* 580 */                 this.state = 3;
/* 581 */                 eatText2(v); continue;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         } 
/* 586 */       } catch (RuntimeException e) {
/* 587 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AuthorizationDecisionStatementTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */