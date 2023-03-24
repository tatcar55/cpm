/*      */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*      */ import com.sun.xml.bind.JAXBObject;
/*      */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*      */ import com.sun.xml.bind.util.ListImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AdviceType;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AssertionType;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.ConditionsType;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureType;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Calendar;
/*      */ import javax.xml.bind.DatatypeConverter;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ public class AssertionTypeImpl implements AssertionType, JAXBObject, UnmarshallableObject, XMLSerializable, IdentifiableObject, ValidatableObject {
/*      */   protected AdviceType _Advice;
/*      */   protected SignatureType _Signature;
/*      */   protected ListImpl _StatementOrSubjectStatementOrAuthenticationStatement;
/*      */   protected ConditionsType _Conditions;
/*   23 */   public static final Class version = JAXBVersion.class; protected String _Issuer; protected BigInteger _MajorVersion; protected Calendar _IssueInstant; protected BigInteger _MinorVersion; protected String _AssertionID; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$StatementImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectStatementImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthenticationStatementImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorizationDecisionStatementImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeStatementImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureTypeImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceImpl;
/*      */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl;
/*      */   
/*      */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*   27 */     return AssertionType.class;
/*      */   }
/*      */   
/*      */   public AdviceType getAdvice() {
/*   31 */     return this._Advice;
/*      */   }
/*      */   
/*      */   public void setAdvice(AdviceType value) {
/*   35 */     this._Advice = value;
/*      */   }
/*      */   
/*      */   public SignatureType getSignature() {
/*   39 */     return this._Signature;
/*      */   }
/*      */   
/*      */   public void setSignature(SignatureType value) {
/*   43 */     this._Signature = value;
/*      */   }
/*      */   
/*      */   protected ListImpl _getStatementOrSubjectStatementOrAuthenticationStatement() {
/*   47 */     if (this._StatementOrSubjectStatementOrAuthenticationStatement == null) {
/*   48 */       this._StatementOrSubjectStatementOrAuthenticationStatement = new ListImpl(new ArrayList());
/*      */     }
/*   50 */     return this._StatementOrSubjectStatementOrAuthenticationStatement;
/*      */   }
/*      */   
/*      */   public List getStatementOrSubjectStatementOrAuthenticationStatement() {
/*   54 */     return (List)_getStatementOrSubjectStatementOrAuthenticationStatement();
/*      */   }
/*      */   
/*      */   public ConditionsType getConditions() {
/*   58 */     return this._Conditions;
/*      */   }
/*      */   
/*      */   public void setConditions(ConditionsType value) {
/*   62 */     this._Conditions = value;
/*      */   }
/*      */   
/*      */   public String getIssuer() {
/*   66 */     return this._Issuer;
/*      */   }
/*      */   
/*      */   public void setIssuer(String value) {
/*   70 */     this._Issuer = value;
/*      */   }
/*      */   
/*      */   public BigInteger getMajorVersion() {
/*   74 */     return this._MajorVersion;
/*      */   }
/*      */   
/*      */   public void setMajorVersion(BigInteger value) {
/*   78 */     this._MajorVersion = value;
/*      */   }
/*      */   
/*      */   public Calendar getIssueInstant() {
/*   82 */     return this._IssueInstant;
/*      */   }
/*      */   
/*      */   public void setIssueInstant(Calendar value) {
/*   86 */     this._IssueInstant = value;
/*      */   }
/*      */   
/*      */   public BigInteger getMinorVersion() {
/*   90 */     return this._MinorVersion;
/*      */   }
/*      */   
/*      */   public void setMinorVersion(BigInteger value) {
/*   94 */     this._MinorVersion = value;
/*      */   }
/*      */   
/*      */   public String getAssertionID() {
/*   98 */     return this._AssertionID;
/*      */   }
/*      */   
/*      */   public void setAssertionID(String value) {
/*  102 */     this._AssertionID = value;
/*      */   }
/*      */   
/*      */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  106 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  112 */     int idx3 = 0;
/*  113 */     int len3 = (this._StatementOrSubjectStatementOrAuthenticationStatement == null) ? 0 : this._StatementOrSubjectStatementOrAuthenticationStatement.size();
/*  114 */     if (this._Conditions != null) {
/*  115 */       if (this._Conditions instanceof javax.xml.bind.Element) {
/*  116 */         context.childAsBody((JAXBObject)this._Conditions, "Conditions");
/*      */       } else {
/*  118 */         context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "Conditions");
/*  119 */         context.childAsURIs((JAXBObject)this._Conditions, "Conditions");
/*  120 */         context.endNamespaceDecls();
/*  121 */         context.childAsAttributes((JAXBObject)this._Conditions, "Conditions");
/*  122 */         context.endAttributes();
/*  123 */         context.childAsBody((JAXBObject)this._Conditions, "Conditions");
/*  124 */         context.endElement();
/*      */       } 
/*      */     }
/*  127 */     if (this._Advice != null) {
/*  128 */       if (this._Advice instanceof javax.xml.bind.Element) {
/*  129 */         context.childAsBody((JAXBObject)this._Advice, "Advice");
/*      */       } else {
/*  131 */         context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "Advice");
/*  132 */         context.childAsURIs((JAXBObject)this._Advice, "Advice");
/*  133 */         context.endNamespaceDecls();
/*  134 */         context.childAsAttributes((JAXBObject)this._Advice, "Advice");
/*  135 */         context.endAttributes();
/*  136 */         context.childAsBody((JAXBObject)this._Advice, "Advice");
/*  137 */         context.endElement();
/*      */       } 
/*      */     }
/*  140 */     while (idx3 != len3) {
/*  141 */       context.childAsBody((JAXBObject)this._StatementOrSubjectStatementOrAuthenticationStatement.get(idx3++), "StatementOrSubjectStatementOrAuthenticationStatement");
/*      */     }
/*  143 */     if (this._Signature != null) {
/*  144 */       if (this._Signature instanceof javax.xml.bind.Element) {
/*  145 */         context.childAsBody((JAXBObject)this._Signature, "Signature");
/*      */       } else {
/*  147 */         context.startElement("http://www.w3.org/2000/09/xmldsig#", "Signature");
/*  148 */         context.childAsURIs((JAXBObject)this._Signature, "Signature");
/*  149 */         context.endNamespaceDecls();
/*  150 */         context.childAsAttributes((JAXBObject)this._Signature, "Signature");
/*  151 */         context.endAttributes();
/*  152 */         context.childAsBody((JAXBObject)this._Signature, "Signature");
/*  153 */         context.endElement();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  161 */     int idx3 = 0;
/*  162 */     int len3 = (this._StatementOrSubjectStatementOrAuthenticationStatement == null) ? 0 : this._StatementOrSubjectStatementOrAuthenticationStatement.size();
/*  163 */     context.startAttribute("", "AssertionID");
/*      */     try {
/*  165 */       context.text(context.onID(this, this._AssertionID), "AssertionID");
/*  166 */     } catch (Exception e) {
/*  167 */       Util.handlePrintConversionException(this, e, context);
/*      */     } 
/*  169 */     context.endAttribute();
/*  170 */     context.startAttribute("", "IssueInstant");
/*      */     try {
/*  172 */       context.text(DateTimeType.theInstance.serializeJavaObject(this._IssueInstant, null), "IssueInstant");
/*  173 */     } catch (Exception e) {
/*  174 */       Util.handlePrintConversionException(this, e, context);
/*      */     } 
/*  176 */     context.endAttribute();
/*  177 */     context.startAttribute("", "Issuer");
/*      */     try {
/*  179 */       context.text(this._Issuer, "Issuer");
/*  180 */     } catch (Exception e) {
/*  181 */       Util.handlePrintConversionException(this, e, context);
/*      */     } 
/*  183 */     context.endAttribute();
/*  184 */     context.startAttribute("", "MajorVersion");
/*      */     try {
/*  186 */       context.text(DatatypeConverter.printInteger(this._MajorVersion), "MajorVersion");
/*  187 */     } catch (Exception e) {
/*  188 */       Util.handlePrintConversionException(this, e, context);
/*      */     } 
/*  190 */     context.endAttribute();
/*  191 */     context.startAttribute("", "MinorVersion");
/*      */     try {
/*  193 */       context.text(DatatypeConverter.printInteger(this._MinorVersion), "MinorVersion");
/*  194 */     } catch (Exception e) {
/*  195 */       Util.handlePrintConversionException(this, e, context);
/*      */     } 
/*  197 */     context.endAttribute();
/*  198 */     if (this._Conditions != null && 
/*  199 */       this._Conditions instanceof javax.xml.bind.Element) {
/*  200 */       context.childAsAttributes((JAXBObject)this._Conditions, "Conditions");
/*      */     }
/*      */     
/*  203 */     if (this._Advice != null && 
/*  204 */       this._Advice instanceof javax.xml.bind.Element) {
/*  205 */       context.childAsAttributes((JAXBObject)this._Advice, "Advice");
/*      */     }
/*      */     
/*  208 */     while (idx3 != len3) {
/*  209 */       context.childAsAttributes((JAXBObject)this._StatementOrSubjectStatementOrAuthenticationStatement.get(idx3++), "StatementOrSubjectStatementOrAuthenticationStatement");
/*      */     }
/*  211 */     if (this._Signature != null && 
/*  212 */       this._Signature instanceof javax.xml.bind.Element) {
/*  213 */       context.childAsAttributes((JAXBObject)this._Signature, "Signature");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*  221 */     int idx3 = 0;
/*  222 */     int len3 = (this._StatementOrSubjectStatementOrAuthenticationStatement == null) ? 0 : this._StatementOrSubjectStatementOrAuthenticationStatement.size();
/*  223 */     if (this._Conditions != null && 
/*  224 */       this._Conditions instanceof javax.xml.bind.Element) {
/*  225 */       context.childAsURIs((JAXBObject)this._Conditions, "Conditions");
/*      */     }
/*      */     
/*  228 */     if (this._Advice != null && 
/*  229 */       this._Advice instanceof javax.xml.bind.Element) {
/*  230 */       context.childAsURIs((JAXBObject)this._Advice, "Advice");
/*      */     }
/*      */     
/*  233 */     while (idx3 != len3) {
/*  234 */       context.childAsURIs((JAXBObject)this._StatementOrSubjectStatementOrAuthenticationStatement.get(idx3++), "StatementOrSubjectStatementOrAuthenticationStatement");
/*      */     }
/*  236 */     if (this._Signature != null && 
/*  237 */       this._Signature instanceof javax.xml.bind.Element) {
/*  238 */       context.childAsURIs((JAXBObject)this._Signature, "Signature");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public String ____jaxb____getId() {
/*  244 */     return this._AssertionID;
/*      */   }
/*      */   
/*      */   public Class getPrimaryInterface() {
/*  248 */     return AssertionType.class;
/*      */   }
/*      */   
/*      */   public DocumentDeclaration createRawValidator() {
/*  252 */     if (schemaFragment == null) {
/*  253 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsq\000~\000\000ppsq\000~\000\000ppsq\000~\000\000ppsq\000~\000\000ppsq\000~\000\000ppsq\000~\000\000ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsq\000~\000\rsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003q\000~\000\021p\000sq\000~\000\rppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003q\000~\000\021psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\023xq\000~\000\003q\000~\000\021psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\020\001q\000~\000\035sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\036q\000~\000#sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000%xq\000~\000 t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.Conditionst\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\022q\000~\000\021p\000sq\000~\000\000ppsq\000~\000\022pp\000sq\000~\000\rppsq\000~\000\027q\000~\000\021psq\000~\000\032q\000~\000\021pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.ConditionsTypeq\000~\000(sq\000~\000\rppsq\000~\000\032q\000~\000\021psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000%L\000\btypeNameq\000~\000%L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\021psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000%L\000\fnamespaceURIq\000~\000%xpq\000~\000>q\000~\000=sq\000~\000$t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000#sq\000~\000$t\000\nConditionst\000%urn:oasis:names:tc:SAML:1.0:assertionq\000~\000#sq\000~\000\rppsq\000~\000\rq\000~\000\021psq\000~\000\022q\000~\000\021p\000sq\000~\000\rppsq\000~\000\027q\000~\000\021psq\000~\000\032q\000~\000\021pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\0002com.sun.xml.wss.saml.internal.saml11.jaxb10.Adviceq\000~\000(sq\000~\000\022q\000~\000\021p\000sq\000~\000\000ppsq\000~\000\022pp\000sq\000~\000\rppsq\000~\000\027q\000~\000\021psq\000~\000\032q\000~\000\021pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.AdviceTypeq\000~\000(sq\000~\000\rppsq\000~\000\032q\000~\000\021pq\000~\0006q\000~\000Fq\000~\000#sq\000~\000$t\000\006Adviceq\000~\000Kq\000~\000#sq\000~\000\027ppsq\000~\000\rppsq\000~\000\rppsq\000~\000\rppsq\000~\000\rppsq\000~\000\022pp\000sq\000~\000\rppsq\000~\000\027q\000~\000\021psq\000~\000\032q\000~\000\021pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Statementq\000~\000(sq\000~\000\022pp\000sq\000~\000\rppsq\000~\000\027q\000~\000\021psq\000~\000\032q\000~\000\021pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\000<com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectStatementq\000~\000(sq\000~\000\022pp\000sq\000~\000\rppsq\000~\000\027q\000~\000\021psq\000~\000\032q\000~\000\021pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\000Ccom.sun.xml.wss.saml.internal.saml11.jaxb10.AuthenticationStatementq\000~\000(sq\000~\000\022pp\000sq\000~\000\rppsq\000~\000\027q\000~\000\021psq\000~\000\032q\000~\000\021pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\000Jcom.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorizationDecisionStatementq\000~\000(sq\000~\000\022pp\000sq\000~\000\rppsq\000~\000\027q\000~\000\021psq\000~\000\032q\000~\000\021pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\000>com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeStatementq\000~\000(sq\000~\000\rppsq\000~\000\rq\000~\000\021psq\000~\000\022q\000~\000\021p\000sq\000~\000\rppsq\000~\000\027q\000~\000\021psq\000~\000\032q\000~\000\021pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Signatureq\000~\000(sq\000~\000\022q\000~\000\021p\000sq\000~\000\000ppsq\000~\000\022pp\000sq\000~\000\rppsq\000~\000\027q\000~\000\021psq\000~\000\032q\000~\000\021pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\0009com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureTypeq\000~\000(sq\000~\000\rppsq\000~\000\032q\000~\000\021pq\000~\0006q\000~\000Fq\000~\000#sq\000~\000$t\000\tSignaturet\000\"http://www.w3.org/2000/09/xmldsig#q\000~\000#sq\000~\000\032ppsq\000~\0003ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\0008q\000~\000=t\000\002IDq\000~\000A\000q\000~\000Csq\000~\000Dq\000~\000q\000~\000=sq\000~\000$t\000\013AssertionIDt\000\000sq\000~\000\032ppsq\000~\0003ppsr\000%com.sun.msv.datatype.xsd.DateTimeType\000\000\000\000\000\000\000\001\002\000\000xr\000)com.sun.msv.datatype.xsd.DateTimeBaseType\024W\032@3¥´å\002\000\000xq\000~\0008q\000~\000=t\000\bdateTimeq\000~\000Aq\000~\000Csq\000~\000Dq\000~\000©q\000~\000=sq\000~\000$t\000\fIssueInstantq\000~\000£sq\000~\000\032ppsq\000~\0003q\000~\000\021psq\000~\000q\000~\000=t\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xq\000~\000@\001q\000~\000Csq\000~\000Dq\000~\000°q\000~\000=sq\000~\000$t\000\006Issuerq\000~\000£sq\000~\000\032ppsq\000~\0003ppsr\000$com.sun.msv.datatype.xsd.IntegerType\000\000\000\000\000\000\000\001\002\000\000xr\000+com.sun.msv.datatype.xsd.IntegerDerivedTypeñ]&6k¾\002\000\001L\000\nbaseFacetst\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xq\000~\0008q\000~\000=t\000\007integerq\000~\000Asr\000,com.sun.msv.datatype.xsd.FractionDigitsFacet\000\000\000\000\000\000\000\001\002\000\001I\000\005scalexr\000;com.sun.msv.datatype.xsd.DataTypeWithLexicalConstraintFacetT\034>\032zbê\002\000\000xr\000*com.sun.msv.datatype.xsd.DataTypeWithFacet\000\000\000\000\000\000\000\001\002\000\005Z\000\fisFacetFixedZ\000\022needValueCheckFlagL\000\bbaseTypeq\000~\000ºL\000\fconcreteTypet\000'Lcom/sun/msv/datatype/xsd/ConcreteType;L\000\tfacetNameq\000~\000%xq\000~\000:ppq\000~\000A\001\000sr\000#com.sun.msv.datatype.xsd.NumberType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0008q\000~\000=t\000\007decimalq\000~\000Aq\000~\000Ãt\000\016fractionDigits\000\000\000\000q\000~\000Csq\000~\000Dq\000~\000¼q\000~\000=sq\000~\000$t\000\fMajorVersionq\000~\000£sq\000~\000\032ppq\000~\000·sq\000~\000$t\000\fMinorVersionq\000~\000£sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000/\001pq\000~\000`q\000~\000\007q\000~\000\013q\000~\000\tq\000~\000*q\000~\000Uq\000~\000q\000~\000\026q\000~\000,q\000~\000Oq\000~\000Wq\000~\000\fq\000~\000fq\000~\000lq\000~\000\031q\000~\000-q\000~\000Pq\000~\000Xq\000~\000gq\000~\000mq\000~\000sq\000~\000rq\000~\000cq\000~\000yq\000~\000xq\000~\000q\000~\000~q\000~\000q\000~\000q\000~\000q\000~\000q\000~\000\nq\000~\000aq\000~\000\bq\000~\0001q\000~\000\\q\000~\000q\000~\000dq\000~\000\016q\000~\000Lq\000~\000q\000~\000bq\000~\000\005q\000~\000\017q\000~\000Mq\000~\000q\000~\000\006x");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  351 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*      */   }
/*      */   
/*      */   public class Unmarshaller
/*      */     extends AbstractUnmarshallingEventHandlerImpl
/*      */   {
/*      */     private final AssertionTypeImpl this$0;
/*      */     
/*      */     public Unmarshaller(UnmarshallingContext context) {
/*  360 */       super(context, "--------------------------");
/*      */     }
/*      */     
/*      */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/*  364 */       this(context);
/*  365 */       this.state = startState;
/*      */     }
/*      */     
/*      */     public Object owner() {
/*  369 */       return AssertionTypeImpl.this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*      */       while (true) {
/*      */         int attIdx;
/*  378 */         switch (this.state) {
/*      */           case 12:
/*  380 */             attIdx = this.context.getAttribute("", "MinorVersion");
/*  381 */             if (attIdx >= 0) {
/*  382 */               String v = this.context.eatAttribute(attIdx);
/*  383 */               this.state = 15;
/*  384 */               eatText1(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 15:
/*  389 */             if ("Conditions" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  390 */               AssertionTypeImpl.this._Conditions = (ConditionsImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsImpl, 16, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  393 */             if ("Conditions" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  394 */               this.context.pushAttributes(__atts, false);
/*  395 */               this.state = 24;
/*      */               return;
/*      */             } 
/*  398 */             this.state = 16;
/*      */             continue;
/*      */           case 19:
/*  401 */             if ("Statement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  402 */               AssertionTypeImpl.this._getStatementOrSubjectStatementOrAuthenticationStatement().add(spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$StatementImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$StatementImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.StatementImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$StatementImpl, 20, ___uri, ___local, ___qname, __atts));
/*      */               return;
/*      */             } 
/*  405 */             if ("SubjectStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  406 */               AssertionTypeImpl.this._getStatementOrSubjectStatementOrAuthenticationStatement().add(spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectStatementImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectStatementImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectStatementImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectStatementImpl, 20, ___uri, ___local, ___qname, __atts));
/*      */               return;
/*      */             } 
/*  409 */             if ("AuthenticationStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  410 */               AssertionTypeImpl.this._getStatementOrSubjectStatementOrAuthenticationStatement().add(spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthenticationStatementImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthenticationStatementImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthenticationStatementImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthenticationStatementImpl, 20, ___uri, ___local, ___qname, __atts));
/*      */               return;
/*      */             } 
/*  413 */             if ("AuthorizationDecisionStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  414 */               AssertionTypeImpl.this._getStatementOrSubjectStatementOrAuthenticationStatement().add(spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorizationDecisionStatementImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorizationDecisionStatementImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorizationDecisionStatementImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorizationDecisionStatementImpl, 20, ___uri, ___local, ___qname, __atts));
/*      */               return;
/*      */             } 
/*  417 */             if ("AttributeStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  418 */               AssertionTypeImpl.this._getStatementOrSubjectStatementOrAuthenticationStatement().add(spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeStatementImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeStatementImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeStatementImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeStatementImpl, 20, ___uri, ___local, ___qname, __atts));
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 6:
/*  423 */             attIdx = this.context.getAttribute("", "Issuer");
/*  424 */             if (attIdx >= 0) {
/*  425 */               String v = this.context.eatAttribute(attIdx);
/*  426 */               this.state = 9;
/*  427 */               eatText2(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 24:
/*  432 */             attIdx = this.context.getAttribute("", "NotBefore");
/*  433 */             if (attIdx >= 0) {
/*  434 */               this.context.consumeAttribute(attIdx);
/*  435 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  438 */             attIdx = this.context.getAttribute("", "NotOnOrAfter");
/*  439 */             if (attIdx >= 0) {
/*  440 */               this.context.consumeAttribute(attIdx);
/*  441 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  444 */             if ("AudienceRestrictionCondition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  445 */               AssertionTypeImpl.this._Conditions = (ConditionsTypeImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl, 25, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  448 */             if ("DoNotCacheCondition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  449 */               AssertionTypeImpl.this._Conditions = (ConditionsTypeImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl, 25, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  452 */             if ("Condition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  453 */               AssertionTypeImpl.this._Conditions = (ConditionsTypeImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl, 25, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  456 */             AssertionTypeImpl.this._Conditions = (ConditionsTypeImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl, 25, ___uri, ___local, ___qname, __atts);
/*      */             return;
/*      */           case 9:
/*  459 */             attIdx = this.context.getAttribute("", "MajorVersion");
/*  460 */             if (attIdx >= 0) {
/*  461 */               String v = this.context.eatAttribute(attIdx);
/*  462 */               this.state = 12;
/*  463 */               eatText3(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 0:
/*  468 */             attIdx = this.context.getAttribute("", "AssertionID");
/*  469 */             if (attIdx >= 0) {
/*  470 */               String v = this.context.eatAttribute(attIdx);
/*  471 */               this.state = 3;
/*  472 */               eatText4(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 22:
/*  477 */             attIdx = this.context.getAttribute("", "Id");
/*  478 */             if (attIdx >= 0) {
/*  479 */               this.context.consumeAttribute(attIdx);
/*  480 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  483 */             if ("SignedInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  484 */               AssertionTypeImpl.this._Signature = (SignatureTypeImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureTypeImpl, 23, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  487 */             if ("SignedInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  488 */               AssertionTypeImpl.this._Signature = (SignatureTypeImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureTypeImpl, 23, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 20:
/*  493 */             if ("Statement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  494 */               AssertionTypeImpl.this._getStatementOrSubjectStatementOrAuthenticationStatement().add(spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$StatementImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$StatementImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.StatementImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$StatementImpl, 20, ___uri, ___local, ___qname, __atts));
/*      */               return;
/*      */             } 
/*  497 */             if ("SubjectStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  498 */               AssertionTypeImpl.this._getStatementOrSubjectStatementOrAuthenticationStatement().add(spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectStatementImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectStatementImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectStatementImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectStatementImpl, 20, ___uri, ___local, ___qname, __atts));
/*      */               return;
/*      */             } 
/*  501 */             if ("AuthenticationStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  502 */               AssertionTypeImpl.this._getStatementOrSubjectStatementOrAuthenticationStatement().add(spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthenticationStatementImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthenticationStatementImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthenticationStatementImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthenticationStatementImpl, 20, ___uri, ___local, ___qname, __atts));
/*      */               return;
/*      */             } 
/*  505 */             if ("AuthorizationDecisionStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  506 */               AssertionTypeImpl.this._getStatementOrSubjectStatementOrAuthenticationStatement().add(spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorizationDecisionStatementImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorizationDecisionStatementImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorizationDecisionStatementImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AuthorizationDecisionStatementImpl, 20, ___uri, ___local, ___qname, __atts));
/*      */               return;
/*      */             } 
/*  509 */             if ("AttributeStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  510 */               AssertionTypeImpl.this._getStatementOrSubjectStatementOrAuthenticationStatement().add(spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeStatementImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeStatementImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeStatementImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeStatementImpl, 20, ___uri, ___local, ___qname, __atts));
/*      */               return;
/*      */             } 
/*  513 */             if ("Signature" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  514 */               AssertionTypeImpl.this._Signature = (SignatureImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureImpl, 21, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  517 */             if ("Signature" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  518 */               this.context.pushAttributes(__atts, false);
/*  519 */               this.state = 22;
/*      */               return;
/*      */             } 
/*  522 */             this.state = 21;
/*      */             continue;
/*      */           case 16:
/*  525 */             if ("Advice" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  526 */               AssertionTypeImpl.this._Advice = (AdviceImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceImpl, 19, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  529 */             if ("Advice" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  530 */               this.context.pushAttributes(__atts, false);
/*  531 */               this.state = 17;
/*      */               return;
/*      */             } 
/*  534 */             this.state = 19;
/*      */             continue;
/*      */           case 3:
/*  537 */             attIdx = this.context.getAttribute("", "IssueInstant");
/*  538 */             if (attIdx >= 0) {
/*  539 */               String v = this.context.eatAttribute(attIdx);
/*  540 */               this.state = 6;
/*  541 */               eatText5(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 17:
/*  546 */             if ("AssertionIDReference" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  547 */               AssertionTypeImpl.this._Advice = (AdviceTypeImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl, 18, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  550 */             if ("Assertion" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  551 */               AssertionTypeImpl.this._Advice = (AdviceTypeImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl, 18, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  554 */             if ("" != ___uri && "urn:oasis:names:tc:SAML:1.0:assertion" != ___uri) {
/*  555 */               AssertionTypeImpl.this._Advice = (AdviceTypeImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl, 18, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*  558 */             AssertionTypeImpl.this._Advice = (AdviceTypeImpl)spawnChildFromEnterElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl, 18, ___uri, ___local, ___qname, __atts);
/*      */             return;
/*      */           case 21:
/*  561 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts); return;
/*      */         }  break;
/*      */       } 
/*  564 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void eatText1(String value) throws SAXException {
/*      */       try {
/*  573 */         AssertionTypeImpl.this._MinorVersion = DatatypeConverter.parseInteger(WhiteSpaceProcessor.collapse(value));
/*  574 */       } catch (Exception e) {
/*  575 */         handleParseConversionException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void eatText2(String value) throws SAXException {
/*      */       try {
/*  583 */         AssertionTypeImpl.this._Issuer = value;
/*  584 */       } catch (Exception e) {
/*  585 */         handleParseConversionException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void eatText3(String value) throws SAXException {
/*      */       try {
/*  593 */         AssertionTypeImpl.this._MajorVersion = DatatypeConverter.parseInteger(WhiteSpaceProcessor.collapse(value));
/*  594 */       } catch (Exception e) {
/*  595 */         handleParseConversionException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void eatText4(String value) throws SAXException {
/*      */       try {
/*  603 */         AssertionTypeImpl.this._AssertionID = this.context.addToIdTable(WhiteSpaceProcessor.collapse(value));
/*  604 */       } catch (Exception e) {
/*  605 */         handleParseConversionException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void eatText5(String value) throws SAXException {
/*      */       try {
/*  613 */         AssertionTypeImpl.this._IssueInstant = (Calendar)DateTimeType.theInstance.createJavaObject(WhiteSpaceProcessor.collapse(value), null);
/*  614 */       } catch (Exception e) {
/*  615 */         handleParseConversionException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*      */       while (true)
/*      */       { int attIdx;
/*  625 */         switch (this.state) {
/*      */           case 12:
/*  627 */             attIdx = this.context.getAttribute("", "MinorVersion");
/*  628 */             if (attIdx >= 0) {
/*  629 */               String v = this.context.eatAttribute(attIdx);
/*  630 */               this.state = 15;
/*  631 */               eatText1(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 15:
/*  636 */             this.state = 16;
/*      */             continue;
/*      */           case 6:
/*  639 */             attIdx = this.context.getAttribute("", "Issuer");
/*  640 */             if (attIdx >= 0) {
/*  641 */               String v = this.context.eatAttribute(attIdx);
/*  642 */               this.state = 9;
/*  643 */               eatText2(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 24:
/*  648 */             attIdx = this.context.getAttribute("", "NotBefore");
/*  649 */             if (attIdx >= 0) {
/*  650 */               this.context.consumeAttribute(attIdx);
/*  651 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*      */               return;
/*      */             } 
/*  654 */             attIdx = this.context.getAttribute("", "NotOnOrAfter");
/*  655 */             if (attIdx >= 0) {
/*  656 */               this.context.consumeAttribute(attIdx);
/*  657 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*      */               return;
/*      */             } 
/*  660 */             AssertionTypeImpl.this._Conditions = (ConditionsTypeImpl)spawnChildFromLeaveElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl, 25, ___uri, ___local, ___qname);
/*      */             return;
/*      */           case 9:
/*  663 */             attIdx = this.context.getAttribute("", "MajorVersion");
/*  664 */             if (attIdx >= 0) {
/*  665 */               String v = this.context.eatAttribute(attIdx);
/*  666 */               this.state = 12;
/*  667 */               eatText3(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 0:
/*  672 */             attIdx = this.context.getAttribute("", "AssertionID");
/*  673 */             if (attIdx >= 0) {
/*  674 */               String v = this.context.eatAttribute(attIdx);
/*  675 */               this.state = 3;
/*  676 */               eatText4(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 22:
/*  681 */             attIdx = this.context.getAttribute("", "Id");
/*  682 */             if (attIdx >= 0) {
/*  683 */               this.context.consumeAttribute(attIdx);
/*  684 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 25:
/*  689 */             if ("Conditions" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  690 */               this.context.popAttributes();
/*  691 */               this.state = 16;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 20:
/*  696 */             this.state = 21;
/*      */             continue;
/*      */           case 16:
/*  699 */             this.state = 19;
/*      */             continue;
/*      */           case 18:
/*  702 */             if ("Advice" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/*  703 */               this.context.popAttributes();
/*  704 */               this.state = 19;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 3:
/*  709 */             attIdx = this.context.getAttribute("", "IssueInstant");
/*  710 */             if (attIdx >= 0) {
/*  711 */               String v = this.context.eatAttribute(attIdx);
/*  712 */               this.state = 6;
/*  713 */               eatText5(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 17:
/*  718 */             AssertionTypeImpl.this._Advice = (AdviceTypeImpl)spawnChildFromLeaveElement((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl, 18, ___uri, ___local, ___qname);
/*      */             return;
/*      */           case 21:
/*  721 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*      */             return;
/*      */           case 23:
/*  724 */             if ("Signature" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  725 */               this.context.popAttributes();
/*  726 */               this.state = 21; return;
/*      */             }  break;
/*      */           default:
/*      */             break;
/*      */         } 
/*  731 */         super.leaveElement(___uri, ___local, ___qname); return; }  super.leaveElement(___uri, ___local, ___qname);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*      */       while (true)
/*  742 */       { switch (this.state) {
/*      */           case 12:
/*  744 */             if ("MinorVersion" == ___local && "" == ___uri) {
/*  745 */               this.state = 13;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 15:
/*  750 */             this.state = 16;
/*      */             continue;
/*      */           case 6:
/*  753 */             if ("Issuer" == ___local && "" == ___uri) {
/*  754 */               this.state = 7;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 24:
/*  759 */             if ("NotBefore" == ___local && "" == ___uri) {
/*  760 */               AssertionTypeImpl.this._Conditions = (ConditionsTypeImpl)spawnChildFromEnterAttribute((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl, 25, ___uri, ___local, ___qname);
/*      */               return;
/*      */             } 
/*  763 */             if ("NotOnOrAfter" == ___local && "" == ___uri) {
/*  764 */               AssertionTypeImpl.this._Conditions = (ConditionsTypeImpl)spawnChildFromEnterAttribute((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl, 25, ___uri, ___local, ___qname);
/*      */               return;
/*      */             } 
/*  767 */             AssertionTypeImpl.this._Conditions = (ConditionsTypeImpl)spawnChildFromEnterAttribute((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl, 25, ___uri, ___local, ___qname);
/*      */             return;
/*      */           case 9:
/*  770 */             if ("MajorVersion" == ___local && "" == ___uri) {
/*  771 */               this.state = 10;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 0:
/*  776 */             if ("AssertionID" == ___local && "" == ___uri) {
/*  777 */               this.state = 1;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 22:
/*  782 */             if ("Id" == ___local && "" == ___uri) {
/*  783 */               AssertionTypeImpl.this._Signature = (SignatureTypeImpl)spawnChildFromEnterAttribute((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureTypeImpl, 23, ___uri, ___local, ___qname);
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 20:
/*  788 */             this.state = 21;
/*      */             continue;
/*      */           case 16:
/*  791 */             this.state = 19;
/*      */             continue;
/*      */           case 3:
/*  794 */             if ("IssueInstant" == ___local && "" == ___uri) {
/*  795 */               this.state = 4;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 17:
/*  800 */             AssertionTypeImpl.this._Advice = (AdviceTypeImpl)spawnChildFromEnterAttribute((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl, 18, ___uri, ___local, ___qname);
/*      */             return;
/*      */           case 21:
/*  803 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname); return;
/*      */           default:
/*      */             break;
/*  806 */         }  super.enterAttribute(___uri, ___local, ___qname); return; }  super.enterAttribute(___uri, ___local, ___qname);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*      */       while (true)
/*      */       { int attIdx;
/*  817 */         switch (this.state) {
/*      */           case 12:
/*  819 */             attIdx = this.context.getAttribute("", "MinorVersion");
/*  820 */             if (attIdx >= 0) {
/*  821 */               String v = this.context.eatAttribute(attIdx);
/*  822 */               this.state = 15;
/*  823 */               eatText1(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 15:
/*  828 */             this.state = 16;
/*      */             continue;
/*      */           case 5:
/*  831 */             if ("IssueInstant" == ___local && "" == ___uri) {
/*  832 */               this.state = 6;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 6:
/*  837 */             attIdx = this.context.getAttribute("", "Issuer");
/*  838 */             if (attIdx >= 0) {
/*  839 */               String v = this.context.eatAttribute(attIdx);
/*  840 */               this.state = 9;
/*  841 */               eatText2(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 24:
/*  846 */             attIdx = this.context.getAttribute("", "NotBefore");
/*  847 */             if (attIdx >= 0) {
/*  848 */               this.context.consumeAttribute(attIdx);
/*  849 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*      */               return;
/*      */             } 
/*  852 */             attIdx = this.context.getAttribute("", "NotOnOrAfter");
/*  853 */             if (attIdx >= 0) {
/*  854 */               this.context.consumeAttribute(attIdx);
/*  855 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*      */               return;
/*      */             } 
/*  858 */             AssertionTypeImpl.this._Conditions = (ConditionsTypeImpl)spawnChildFromLeaveAttribute((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl, 25, ___uri, ___local, ___qname);
/*      */             return;
/*      */           case 9:
/*  861 */             attIdx = this.context.getAttribute("", "MajorVersion");
/*  862 */             if (attIdx >= 0) {
/*  863 */               String v = this.context.eatAttribute(attIdx);
/*  864 */               this.state = 12;
/*  865 */               eatText3(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 14:
/*  870 */             if ("MinorVersion" == ___local && "" == ___uri) {
/*  871 */               this.state = 15;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 8:
/*  876 */             if ("Issuer" == ___local && "" == ___uri) {
/*  877 */               this.state = 9;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 0:
/*  882 */             attIdx = this.context.getAttribute("", "AssertionID");
/*  883 */             if (attIdx >= 0) {
/*  884 */               String v = this.context.eatAttribute(attIdx);
/*  885 */               this.state = 3;
/*  886 */               eatText4(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 22:
/*  891 */             attIdx = this.context.getAttribute("", "Id");
/*  892 */             if (attIdx >= 0) {
/*  893 */               this.context.consumeAttribute(attIdx);
/*  894 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 2:
/*  899 */             if ("AssertionID" == ___local && "" == ___uri) {
/*  900 */               this.state = 3;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 20:
/*  905 */             this.state = 21;
/*      */             continue;
/*      */           case 16:
/*  908 */             this.state = 19;
/*      */             continue;
/*      */           case 3:
/*  911 */             attIdx = this.context.getAttribute("", "IssueInstant");
/*  912 */             if (attIdx >= 0) {
/*  913 */               String v = this.context.eatAttribute(attIdx);
/*  914 */               this.state = 6;
/*  915 */               eatText5(v);
/*      */               continue;
/*      */             } 
/*      */             break;
/*      */           case 11:
/*  920 */             if ("MajorVersion" == ___local && "" == ___uri) {
/*  921 */               this.state = 12;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 17:
/*  926 */             AssertionTypeImpl.this._Advice = (AdviceTypeImpl)spawnChildFromLeaveAttribute((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl, 18, ___uri, ___local, ___qname);
/*      */             return;
/*      */           case 21:
/*  929 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname); return;
/*      */           default:
/*      */             break;
/*  932 */         }  super.leaveAttribute(___uri, ___local, ___qname); return; }  super.leaveAttribute(___uri, ___local, ___qname);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handleText(String value) throws SAXException {
/*      */       try {
/*      */         while (true) {
/*      */           int attIdx;
/*  944 */           switch (this.state) {
/*      */             case 12:
/*  946 */               attIdx = this.context.getAttribute("", "MinorVersion");
/*  947 */               if (attIdx >= 0) {
/*  948 */                 String v = this.context.eatAttribute(attIdx);
/*  949 */                 this.state = 15;
/*  950 */                 eatText1(v);
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 10:
/*  955 */               this.state = 11;
/*  956 */               eatText3(value);
/*      */               return;
/*      */             case 15:
/*  959 */               this.state = 16;
/*      */               continue;
/*      */             case 6:
/*  962 */               attIdx = this.context.getAttribute("", "Issuer");
/*  963 */               if (attIdx >= 0) {
/*  964 */                 String v = this.context.eatAttribute(attIdx);
/*  965 */                 this.state = 9;
/*  966 */                 eatText2(v);
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 24:
/*  971 */               attIdx = this.context.getAttribute("", "NotBefore");
/*  972 */               if (attIdx >= 0) {
/*  973 */                 this.context.consumeAttribute(attIdx);
/*  974 */                 this.context.getCurrentHandler().text(value);
/*      */                 return;
/*      */               } 
/*  977 */               attIdx = this.context.getAttribute("", "NotOnOrAfter");
/*  978 */               if (attIdx >= 0) {
/*  979 */                 this.context.consumeAttribute(attIdx);
/*  980 */                 this.context.getCurrentHandler().text(value);
/*      */                 return;
/*      */               } 
/*  983 */               AssertionTypeImpl.this._Conditions = (ConditionsTypeImpl)spawnChildFromText((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionsTypeImpl, 25, value);
/*      */               return;
/*      */             case 9:
/*  986 */               attIdx = this.context.getAttribute("", "MajorVersion");
/*  987 */               if (attIdx >= 0) {
/*  988 */                 String v = this.context.eatAttribute(attIdx);
/*  989 */                 this.state = 12;
/*  990 */                 eatText3(v);
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 7:
/*  995 */               this.state = 8;
/*  996 */               eatText2(value);
/*      */               return;
/*      */             case 1:
/*  999 */               this.state = 2;
/* 1000 */               eatText4(value);
/*      */               return;
/*      */             case 4:
/* 1003 */               this.state = 5;
/* 1004 */               eatText5(value);
/*      */               return;
/*      */             case 0:
/* 1007 */               attIdx = this.context.getAttribute("", "AssertionID");
/* 1008 */               if (attIdx >= 0) {
/* 1009 */                 String v = this.context.eatAttribute(attIdx);
/* 1010 */                 this.state = 3;
/* 1011 */                 eatText4(v);
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 22:
/* 1016 */               attIdx = this.context.getAttribute("", "Id");
/* 1017 */               if (attIdx >= 0) {
/* 1018 */                 this.context.consumeAttribute(attIdx);
/* 1019 */                 this.context.getCurrentHandler().text(value);
/*      */                 return;
/*      */               } 
/*      */               break;
/*      */             case 20:
/* 1024 */               this.state = 21;
/*      */               continue;
/*      */             case 16:
/* 1027 */               this.state = 19;
/*      */               continue;
/*      */             case 13:
/* 1030 */               this.state = 14;
/* 1031 */               eatText1(value);
/*      */               return;
/*      */             case 3:
/* 1034 */               attIdx = this.context.getAttribute("", "IssueInstant");
/* 1035 */               if (attIdx >= 0) {
/* 1036 */                 String v = this.context.eatAttribute(attIdx);
/* 1037 */                 this.state = 6;
/* 1038 */                 eatText5(v);
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 17:
/* 1043 */               AssertionTypeImpl.this._Advice = (AdviceTypeImpl)spawnChildFromText((AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl == null) ? (AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl = AssertionTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceTypeImpl")) : AssertionTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AdviceTypeImpl, 18, value);
/*      */               return;
/*      */             case 21:
/* 1046 */               revertToParentFromText(value); return;
/*      */           }  break;
/*      */         } 
/* 1049 */       } catch (RuntimeException e) {
/* 1050 */         handleUnexpectedTextException(value, e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AssertionTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */