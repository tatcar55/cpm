/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.Assertion;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AssertionImpl extends AssertionTypeImpl implements Assertion, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return Assertion.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "urn:oasis:names:tc:SAML:1.0:assertion";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "Assertion";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
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
/*  58 */     return Assertion.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsq\000~\000\022sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\000q\000~\000\026p\000sq\000~\000\022ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004q\000~\000\026psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\026psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\025\001q\000~\000\037sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000 q\000~\000%sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000'xq\000~\000\"t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.Conditionst\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000q\000~\000\026p\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\022ppsq\000~\000\031q\000~\000\026psq\000~\000\034q\000~\000\026pq\000~\000\037q\000~\000#q\000~\000%sq\000~\000&t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.ConditionsTypeq\000~\000*sq\000~\000\022ppsq\000~\000\034q\000~\000\026psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000'L\000\btypeNameq\000~\000'L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\026psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000'L\000\fnamespaceURIq\000~\000'xpq\000~\000@q\000~\000?sq\000~\000&t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000%sq\000~\000&t\000\nConditionst\000%urn:oasis:names:tc:SAML:1.0:assertionq\000~\000%sq\000~\000\022ppsq\000~\000\022q\000~\000\026psq\000~\000\000q\000~\000\026p\000sq\000~\000\022ppsq\000~\000\031q\000~\000\026psq\000~\000\034q\000~\000\026pq\000~\000\037q\000~\000#q\000~\000%sq\000~\000&t\0002com.sun.xml.wss.saml.internal.saml11.jaxb10.Adviceq\000~\000*sq\000~\000\000q\000~\000\026p\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\022ppsq\000~\000\031q\000~\000\026psq\000~\000\034q\000~\000\026pq\000~\000\037q\000~\000#q\000~\000%sq\000~\000&t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.AdviceTypeq\000~\000*sq\000~\000\022ppsq\000~\000\034q\000~\000\026pq\000~\0008q\000~\000Hq\000~\000%sq\000~\000&t\000\006Adviceq\000~\000Mq\000~\000%sq\000~\000\031ppsq\000~\000\022ppsq\000~\000\022ppsq\000~\000\022ppsq\000~\000\022ppsq\000~\000\000pp\000sq\000~\000\022ppsq\000~\000\031q\000~\000\026psq\000~\000\034q\000~\000\026pq\000~\000\037q\000~\000#q\000~\000%sq\000~\000&t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Statementq\000~\000*sq\000~\000\000pp\000sq\000~\000\022ppsq\000~\000\031q\000~\000\026psq\000~\000\034q\000~\000\026pq\000~\000\037q\000~\000#q\000~\000%sq\000~\000&t\000<com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectStatementq\000~\000*sq\000~\000\000pp\000sq\000~\000\022ppsq\000~\000\031q\000~\000\026psq\000~\000\034q\000~\000\026pq\000~\000\037q\000~\000#q\000~\000%sq\000~\000&t\000Ccom.sun.xml.wss.saml.internal.saml11.jaxb10.AuthenticationStatementq\000~\000*sq\000~\000\000pp\000sq\000~\000\022ppsq\000~\000\031q\000~\000\026psq\000~\000\034q\000~\000\026pq\000~\000\037q\000~\000#q\000~\000%sq\000~\000&t\000Jcom.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorizationDecisionStatementq\000~\000*sq\000~\000\000pp\000sq\000~\000\022ppsq\000~\000\031q\000~\000\026psq\000~\000\034q\000~\000\026pq\000~\000\037q\000~\000#q\000~\000%sq\000~\000&t\000>com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeStatementq\000~\000*sq\000~\000\022ppsq\000~\000\022q\000~\000\026psq\000~\000\000q\000~\000\026p\000sq\000~\000\022ppsq\000~\000\031q\000~\000\026psq\000~\000\034q\000~\000\026pq\000~\000\037q\000~\000#q\000~\000%sq\000~\000&t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Signatureq\000~\000*sq\000~\000\000q\000~\000\026p\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\022ppsq\000~\000\031q\000~\000\026psq\000~\000\034q\000~\000\026pq\000~\000\037q\000~\000#q\000~\000%sq\000~\000&t\0009com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureTypeq\000~\000*sq\000~\000\022ppsq\000~\000\034q\000~\000\026pq\000~\0008q\000~\000Hq\000~\000%sq\000~\000&t\000\tSignaturet\000\"http://www.w3.org/2000/09/xmldsig#q\000~\000%sq\000~\000\034ppsq\000~\0005ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\000:q\000~\000?t\000\002IDq\000~\000C\000q\000~\000Esq\000~\000Fq\000~\000¡q\000~\000?sq\000~\000&t\000\013AssertionIDt\000\000sq\000~\000\034ppsq\000~\0005ppsr\000%com.sun.msv.datatype.xsd.DateTimeType\000\000\000\000\000\000\000\001\002\000\000xr\000)com.sun.msv.datatype.xsd.DateTimeBaseType\024W\032@3¥´å\002\000\000xq\000~\000:q\000~\000?t\000\bdateTimeq\000~\000Cq\000~\000Esq\000~\000Fq\000~\000«q\000~\000?sq\000~\000&t\000\fIssueInstantq\000~\000¥sq\000~\000\034ppsq\000~\0005q\000~\000\026psq\000~\000q\000~\000?t\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xq\000~\000B\001q\000~\000Esq\000~\000Fq\000~\000²q\000~\000?sq\000~\000&t\000\006Issuerq\000~\000¥sq\000~\000\034ppsq\000~\0005ppsr\000$com.sun.msv.datatype.xsd.IntegerType\000\000\000\000\000\000\000\001\002\000\000xr\000+com.sun.msv.datatype.xsd.IntegerDerivedTypeñ]&6k¾\002\000\001L\000\nbaseFacetst\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xq\000~\000:q\000~\000?t\000\007integerq\000~\000Csr\000,com.sun.msv.datatype.xsd.FractionDigitsFacet\000\000\000\000\000\000\000\001\002\000\001I\000\005scalexr\000;com.sun.msv.datatype.xsd.DataTypeWithLexicalConstraintFacetT\034>\032zbê\002\000\000xr\000*com.sun.msv.datatype.xsd.DataTypeWithFacet\000\000\000\000\000\000\000\001\002\000\005Z\000\fisFacetFixedZ\000\022needValueCheckFlagL\000\bbaseTypeq\000~\000¼L\000\fconcreteTypet\000'Lcom/sun/msv/datatype/xsd/ConcreteType;L\000\tfacetNameq\000~\000'xq\000~\000<ppq\000~\000C\001\000sr\000#com.sun.msv.datatype.xsd.NumberType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000:q\000~\000?t\000\007decimalq\000~\000Cq\000~\000Åt\000\016fractionDigits\000\000\000\000q\000~\000Esq\000~\000Fq\000~\000¾q\000~\000?sq\000~\000&t\000\fMajorVersionq\000~\000¥sq\000~\000\034ppq\000~\000¹sq\000~\000&t\000\fMinorVersionq\000~\000¥sq\000~\000\022ppsq\000~\000\034q\000~\000\026pq\000~\0008q\000~\000Hq\000~\000%sq\000~\000&t\000\tAssertionq\000~\000Msr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\0001\001pq\000~\000bq\000~\000\fq\000~\000\020q\000~\000\016q\000~\000,q\000~\000Wq\000~\000q\000~\000\030q\000~\000.q\000~\000Qq\000~\000Yq\000~\000\021q\000~\000hq\000~\000nq\000~\000\033q\000~\000/q\000~\000Rq\000~\000Zq\000~\000iq\000~\000oq\000~\000uq\000~\000tq\000~\000eq\000~\000{q\000~\000zq\000~\000q\000~\000q\000~\000q\000~\000q\000~\000q\000~\000q\000~\000\017q\000~\000cq\000~\000\rq\000~\0003q\000~\000^q\000~\000q\000~\000Îq\000~\000\tq\000~\000fq\000~\000\023q\000~\000Nq\000~\000q\000~\000dq\000~\000\nq\000~\000\024q\000~\000Oq\000~\000q\000~\000\013x");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AssertionImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 171 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 175 */       this(context);
/* 176 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 180 */       return AssertionImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 189 */       switch (this.state) {
/*     */         case 3:
/* 191 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 1:
/* 194 */           attIdx = this.context.getAttribute("", "AssertionID");
/* 195 */           if (attIdx >= 0) {
/* 196 */             this.context.consumeAttribute(attIdx);
/* 197 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 202 */           if ("Assertion" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 203 */             this.context.pushAttributes(__atts, false);
/* 204 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 209 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       int attIdx;
/* 220 */       switch (this.state) {
/*     */         case 3:
/* 222 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 2:
/* 225 */           if ("Assertion" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 226 */             this.context.popAttributes();
/* 227 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 232 */           attIdx = this.context.getAttribute("", "AssertionID");
/* 233 */           if (attIdx >= 0) {
/* 234 */             this.context.consumeAttribute(attIdx);
/* 235 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 240 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 251 */       switch (this.state) {
/*     */         case 3:
/* 253 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 256 */           if ("AssertionID" == ___local && "" == ___uri) {
/* 257 */             AssertionImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new AssertionTypeImpl.Unmarshaller(AssertionImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 262 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       int attIdx;
/* 273 */       switch (this.state) {
/*     */         case 3:
/* 275 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 278 */           attIdx = this.context.getAttribute("", "AssertionID");
/* 279 */           if (attIdx >= 0) {
/* 280 */             this.context.consumeAttribute(attIdx);
/* 281 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 286 */       super.leaveAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleText(String value) throws SAXException {
/*     */       try {
/*     */         int attIdx;
/* 298 */         switch (this.state) {
/*     */           case 3:
/* 300 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/* 303 */             attIdx = this.context.getAttribute("", "AssertionID");
/* 304 */             if (attIdx >= 0) {
/* 305 */               this.context.consumeAttribute(attIdx);
/* 306 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */         } 
/* 311 */       } catch (RuntimeException e) {
/* 312 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AssertionImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */