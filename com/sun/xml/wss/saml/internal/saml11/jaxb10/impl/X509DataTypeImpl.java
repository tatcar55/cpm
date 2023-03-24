/*      */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*      */ import com.sun.msv.verifier.DocumentDeclaration;
/*      */ import com.sun.xml.bind.JAXBObject;
/*      */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ public class X509DataTypeImpl implements X509DataType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*   15 */   public static final Class version = JAXBVersion.class; protected ListImpl _X509IssuerSerialOrX509SKIOrX509SubjectName; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509IssuerSerialImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SKIImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SubjectNameImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CertificateImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CRLImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509CRL; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509Certificate; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509IssuerSerial; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SKI;
/*      */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SubjectName;
/*      */   
/*      */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*   19 */     return X509DataType.class;
/*      */   }
/*      */   
/*      */   protected ListImpl _getX509IssuerSerialOrX509SKIOrX509SubjectName() {
/*   23 */     if (this._X509IssuerSerialOrX509SKIOrX509SubjectName == null) {
/*   24 */       this._X509IssuerSerialOrX509SKIOrX509SubjectName = new ListImpl(new ArrayList());
/*      */     }
/*   26 */     return this._X509IssuerSerialOrX509SKIOrX509SubjectName;
/*      */   }
/*      */   
/*      */   public List getX509IssuerSerialOrX509SKIOrX509SubjectName() {
/*   30 */     return (List)_getX509IssuerSerialOrX509SKIOrX509SubjectName();
/*      */   }
/*      */   
/*      */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*   34 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeBody(XMLSerializer context) throws SAXException {
/*   40 */     int idx1 = 0;
/*   41 */     int len1 = (this._X509IssuerSerialOrX509SKIOrX509SubjectName == null) ? 0 : this._X509IssuerSerialOrX509SKIOrX509SubjectName.size();
/*   42 */     while (idx1 != len1) {
/*      */       
/*   44 */       Object o = this._X509IssuerSerialOrX509SKIOrX509SubjectName.get(idx1);
/*   45 */       if (o instanceof JAXBObject) {
/*   46 */         context.childAsBody((JAXBObject)this._X509IssuerSerialOrX509SKIOrX509SubjectName.get(idx1++), "X509IssuerSerialOrX509SKIOrX509SubjectName"); continue;
/*      */       } 
/*   48 */       if (o instanceof Object) {
/*   49 */         context.childAsBody((JAXBObject)this._X509IssuerSerialOrX509SKIOrX509SubjectName.get(idx1++), "X509IssuerSerialOrX509SKIOrX509SubjectName"); continue;
/*      */       } 
/*   51 */       Util.handleTypeMismatchError(context, this, "X509IssuerSerialOrX509SKIOrX509SubjectName", o);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*   61 */     int idx1 = 0;
/*   62 */     int len1 = (this._X509IssuerSerialOrX509SKIOrX509SubjectName == null) ? 0 : this._X509IssuerSerialOrX509SKIOrX509SubjectName.size();
/*   63 */     while (idx1 != len1) {
/*      */       
/*   65 */       Object o = this._X509IssuerSerialOrX509SKIOrX509SubjectName.get(idx1);
/*   66 */       if (o instanceof JAXBObject) {
/*   67 */         context.childAsAttributes((JAXBObject)this._X509IssuerSerialOrX509SKIOrX509SubjectName.get(idx1++), "X509IssuerSerialOrX509SKIOrX509SubjectName"); continue;
/*      */       } 
/*   69 */       if (o instanceof Object) {
/*   70 */         idx1++; continue;
/*      */       } 
/*   72 */       Util.handleTypeMismatchError(context, this, "X509IssuerSerialOrX509SKIOrX509SubjectName", o);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*   82 */     int idx1 = 0;
/*   83 */     int len1 = (this._X509IssuerSerialOrX509SKIOrX509SubjectName == null) ? 0 : this._X509IssuerSerialOrX509SKIOrX509SubjectName.size();
/*   84 */     while (idx1 != len1) {
/*      */       
/*   86 */       Object o = this._X509IssuerSerialOrX509SKIOrX509SubjectName.get(idx1);
/*   87 */       if (o instanceof JAXBObject) {
/*   88 */         context.childAsURIs((JAXBObject)this._X509IssuerSerialOrX509SKIOrX509SubjectName.get(idx1++), "X509IssuerSerialOrX509SKIOrX509SubjectName"); continue;
/*      */       } 
/*   90 */       if (o instanceof Object) {
/*   91 */         idx1++; continue;
/*      */       } 
/*   93 */       Util.handleTypeMismatchError(context, this, "X509IssuerSerialOrX509SKIOrX509SubjectName", o);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class getPrimaryInterface() {
/*  101 */     return X509DataType.class;
/*      */   }
/*      */   
/*      */   public DocumentDeclaration createRawValidator() {
/*  105 */     if (schemaFragment == null) {
/*  106 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\002L\000\004exp2q\000~\000\002xq\000~\000\003ppsq\000~\000\006ppsq\000~\000\006ppsq\000~\000\006ppsq\000~\000\006ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\006ppsq\000~\000\000sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\016xq\000~\000\003q\000~\000\024psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\023\001q\000~\000\030sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\031q\000~\000\036sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000 xq\000~\000\033t\000Icom.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType.X509IssuerSerialt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\rpp\000sq\000~\000\006ppsq\000~\000\000q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000@com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType.X509SKIq\000~\000#sq\000~\000\rpp\000sq\000~\000\006ppsq\000~\000\000q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000Hcom.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType.X509SubjectNameq\000~\000#sq\000~\000\rpp\000sq\000~\000\006ppsq\000~\000\000q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000Hcom.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType.X509Certificateq\000~\000#sq\000~\000\rpp\000sq\000~\000\006ppsq\000~\000\000q\000~\000\024psq\000~\000\025q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000@com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType.X509CRLq\000~\000#sq\000~\000\rpp\000sq\000~\000\025ppq\000~\000\030sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\016L\000\003nc2q\000~\000\016xq\000~\000\033q\000~\000\034sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\016L\000\003nc2q\000~\000\016xq\000~\000\033sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIq\000~\000 xq\000~\000\033t\000\000sq\000~\000Bt\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000Bq\000~\000#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\020\001pq\000~\000\021q\000~\000%q\000~\000+q\000~\000\013q\000~\0001q\000~\0007q\000~\000\022q\000~\000&q\000~\000,q\000~\0002q\000~\0008q\000~\000\bq\000~\000\005q\000~\000\tq\000~\000\fq\000~\000\nx");
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
/*  149 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*      */   }
/*      */   
/*      */   public class Unmarshaller
/*      */     extends AbstractUnmarshallingEventHandlerImpl
/*      */   {
/*      */     private final X509DataTypeImpl this$0;
/*      */     
/*      */     public Unmarshaller(UnmarshallingContext context) {
/*  158 */       super(context, "--");
/*      */     }
/*      */     
/*      */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/*  162 */       this(context);
/*  163 */       this.state = startState;
/*      */     }
/*      */     
/*      */     public Object owner() {
/*  167 */       return X509DataTypeImpl.this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*  176 */       switch (this.state) {
/*      */         case 1:
/*  178 */           if ("X509IssuerSerial" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  179 */             X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(spawnChildFromEnterElement((X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509IssuerSerialImpl == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509IssuerSerialImpl = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl$X509IssuerSerialImpl")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509IssuerSerialImpl, 1, ___uri, ___local, ___qname, __atts));
/*      */             return;
/*      */           } 
/*  182 */           if ("X509SKI" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  183 */             X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(spawnChildFromEnterElement((X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SKIImpl == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SKIImpl = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl$X509SKIImpl")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SKIImpl, 1, ___uri, ___local, ___qname, __atts));
/*      */             return;
/*      */           } 
/*  186 */           if ("X509SubjectName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  187 */             X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(spawnChildFromEnterElement((X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SubjectNameImpl == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SubjectNameImpl = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl$X509SubjectNameImpl")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SubjectNameImpl, 1, ___uri, ___local, ___qname, __atts));
/*      */             return;
/*      */           } 
/*  190 */           if ("X509Certificate" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  191 */             X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(spawnChildFromEnterElement((X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CertificateImpl == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CertificateImpl = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl$X509CertificateImpl")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CertificateImpl, 1, ___uri, ___local, ___qname, __atts));
/*      */             return;
/*      */           } 
/*  194 */           if ("X509CRL" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  195 */             X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(spawnChildFromEnterElement((X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CRLImpl == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CRLImpl = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl$X509CRLImpl")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CRLImpl, 1, ___uri, ___local, ___qname, __atts));
/*      */             return;
/*      */           } 
/*  198 */           if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/*  199 */             Object co = spawnWildcard(1, ___uri, ___local, ___qname, __atts);
/*  200 */             if (co != null) {
/*  201 */               X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(co);
/*      */             }
/*      */             return;
/*      */           } 
/*  205 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*      */           return;
/*      */         case 0:
/*  208 */           if ("X509IssuerSerial" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  209 */             X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(spawnChildFromEnterElement((X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509IssuerSerialImpl == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509IssuerSerialImpl = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl$X509IssuerSerialImpl")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509IssuerSerialImpl, 1, ___uri, ___local, ___qname, __atts));
/*      */             return;
/*      */           } 
/*  212 */           if ("X509SKI" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  213 */             X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(spawnChildFromEnterElement((X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SKIImpl == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SKIImpl = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl$X509SKIImpl")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SKIImpl, 1, ___uri, ___local, ___qname, __atts));
/*      */             return;
/*      */           } 
/*  216 */           if ("X509SubjectName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  217 */             X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(spawnChildFromEnterElement((X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SubjectNameImpl == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SubjectNameImpl = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl$X509SubjectNameImpl")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509SubjectNameImpl, 1, ___uri, ___local, ___qname, __atts));
/*      */             return;
/*      */           } 
/*  220 */           if ("X509Certificate" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  221 */             X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(spawnChildFromEnterElement((X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CertificateImpl == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CertificateImpl = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl$X509CertificateImpl")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CertificateImpl, 1, ___uri, ___local, ___qname, __atts));
/*      */             return;
/*      */           } 
/*  224 */           if ("X509CRL" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  225 */             X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(spawnChildFromEnterElement((X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CRLImpl == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CRLImpl = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl$X509CRLImpl")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataTypeImpl$X509CRLImpl, 1, ___uri, ___local, ___qname, __atts));
/*      */             return;
/*      */           } 
/*  228 */           if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/*  229 */             Object co = spawnWildcard(1, ___uri, ___local, ___qname, __atts);
/*  230 */             if (co != null) {
/*  231 */               X509DataTypeImpl.this._getX509IssuerSerialOrX509SKIOrX509SubjectName().add(co);
/*      */             }
/*      */             return;
/*      */           } 
/*      */           break;
/*      */       } 
/*  237 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*  248 */       switch (this.state) {
/*      */         case 1:
/*  250 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*      */           return;
/*      */       } 
/*  253 */       super.leaveElement(___uri, ___local, ___qname);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*  264 */       switch (this.state) {
/*      */         case 1:
/*  266 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*      */           return;
/*      */       } 
/*  269 */       super.enterAttribute(___uri, ___local, ___qname);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*  280 */       switch (this.state) {
/*      */         case 1:
/*  282 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*      */           return;
/*      */       } 
/*  285 */       super.leaveAttribute(___uri, ___local, ___qname);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handleText(String value) throws SAXException {
/*      */       try {
/*  297 */         switch (this.state) {
/*      */           case 1:
/*  299 */             revertToParentFromText(value);
/*      */             return;
/*      */         } 
/*  302 */       } catch (RuntimeException e) {
/*  303 */         handleUnexpectedTextException(value, e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class X509CRLImpl
/*      */     implements X509DataType.X509CRL, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject
/*      */   {
/*      */     protected byte[] _Value;
/*      */     
/*  315 */     public static final Class version = (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.JAXBVersion")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion;
/*      */     
/*      */     private static Grammar schemaFragment;
/*      */     
/*      */     public X509CRLImpl() {}
/*      */     
/*      */     public X509CRLImpl(byte[] value) {
/*  322 */       this._Value = value;
/*      */     }
/*      */     
/*      */     private static final Class PRIMARY_INTERFACE_CLASS() {
/*  326 */       return (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509CRL == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509CRL = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType$X509CRL")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509CRL;
/*      */     }
/*      */     
/*      */     public String ____jaxb_ri____getNamespaceURI() {
/*  330 */       return "http://www.w3.org/2000/09/xmldsig#";
/*      */     }
/*      */     
/*      */     public String ____jaxb_ri____getLocalName() {
/*  334 */       return "X509CRL";
/*      */     }
/*      */     
/*      */     public byte[] getValue() {
/*  338 */       return this._Value;
/*      */     }
/*      */     
/*      */     public void setValue(byte[] value) {
/*  342 */       this._Value = value;
/*      */     }
/*      */     
/*      */     public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  346 */       return (UnmarshallingEventHandler)new Unmarshaller(context);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeBody(XMLSerializer context) throws SAXException {
/*  352 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "X509CRL");
/*  353 */       context.endNamespaceDecls();
/*  354 */       context.endAttributes();
/*      */       try {
/*  356 */         context.text(Base64BinaryType.save(this._Value), "Value");
/*  357 */       } catch (Exception e) {
/*  358 */         Util.handlePrintConversionException(this, e, context);
/*      */       } 
/*  360 */       context.endElement();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeURIs(XMLSerializer context) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public Class getPrimaryInterface() {
/*  374 */       return (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509CRL == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509CRL = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType$X509CRL")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509CRL;
/*      */     }
/*      */     
/*      */     public DocumentDeclaration createRawValidator() {
/*  378 */       if (schemaFragment == null) {
/*  379 */         schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000)com.sun.msv.datatype.xsd.Base64BinaryType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.BinaryBaseType§Î\016^¯W\021\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\023L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\fbase64Binarysr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xpq\000~\000\027q\000~\000\026sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\036psq\000~\000\nppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\020q\000~\000\026t\000\005QNameq\000~\000\032q\000~\000\034sq\000~\000\037q\000~\000(q\000~\000\026sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\035\001q\000~\0000sq\000~\000*t\000\007X509CRLt\000\"http://www.w3.org/2000/09/xmldsig#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\002\001pq\000~\000\"q\000~\000\tx");
/*      */       }
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
/*  418 */       return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*      */     }
/*      */     
/*      */     public class Unmarshaller
/*      */       extends AbstractUnmarshallingEventHandlerImpl
/*      */     {
/*      */       private final X509DataTypeImpl.X509CRLImpl this$0;
/*      */       
/*      */       public Unmarshaller(UnmarshallingContext context) {
/*  427 */         super(context, "----");
/*      */       }
/*      */       
/*      */       protected Unmarshaller(UnmarshallingContext context, int startState) {
/*  431 */         this(context);
/*  432 */         this.state = startState;
/*      */       }
/*      */       
/*      */       public Object owner() {
/*  436 */         return X509DataTypeImpl.X509CRLImpl.this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*  445 */         switch (this.state) {
/*      */           case 0:
/*  447 */             if ("X509CRL" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  448 */               this.context.pushAttributes(__atts, true);
/*  449 */               this.state = 1;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 3:
/*  454 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*      */             return;
/*      */         } 
/*  457 */         super.enterElement(___uri, ___local, ___qname, __atts);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*  468 */         switch (this.state) {
/*      */           case 2:
/*  470 */             if ("X509CRL" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  471 */               this.context.popAttributes();
/*  472 */               this.state = 3;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 3:
/*  477 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/*  480 */         super.leaveElement(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*  491 */         switch (this.state) {
/*      */           case 3:
/*  493 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/*  496 */         super.enterAttribute(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*  507 */         switch (this.state) {
/*      */           case 3:
/*  509 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/*  512 */         super.leaveAttribute(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void handleText(String value) throws SAXException {
/*      */         try {
/*  524 */           switch (this.state) {
/*      */             case 3:
/*  526 */               revertToParentFromText(value);
/*      */               return;
/*      */             case 1:
/*  529 */               this.state = 2;
/*  530 */               eatText1(value);
/*      */               return;
/*      */           } 
/*  533 */         } catch (RuntimeException e) {
/*  534 */           handleUnexpectedTextException(value, e);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private void eatText1(String value) throws SAXException {
/*      */         try {
/*  544 */           X509DataTypeImpl.X509CRLImpl.this._Value = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/*  545 */         } catch (Exception e) {
/*  546 */           handleParseConversionException(e);
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class X509CertificateImpl
/*      */     implements X509DataType.X509Certificate, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject
/*      */   {
/*      */     protected byte[] _Value;
/*      */     
/*  558 */     public static final Class version = (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.JAXBVersion")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion;
/*      */     
/*      */     private static Grammar schemaFragment;
/*      */     
/*      */     public X509CertificateImpl() {}
/*      */     
/*      */     public X509CertificateImpl(byte[] value) {
/*  565 */       this._Value = value;
/*      */     }
/*      */     
/*      */     private static final Class PRIMARY_INTERFACE_CLASS() {
/*  569 */       return (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509Certificate == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509Certificate = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType$X509Certificate")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509Certificate;
/*      */     }
/*      */     
/*      */     public String ____jaxb_ri____getNamespaceURI() {
/*  573 */       return "http://www.w3.org/2000/09/xmldsig#";
/*      */     }
/*      */     
/*      */     public String ____jaxb_ri____getLocalName() {
/*  577 */       return "X509Certificate";
/*      */     }
/*      */     
/*      */     public byte[] getValue() {
/*  581 */       return this._Value;
/*      */     }
/*      */     
/*      */     public void setValue(byte[] value) {
/*  585 */       this._Value = value;
/*      */     }
/*      */     
/*      */     public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  589 */       return (UnmarshallingEventHandler)new Unmarshaller(context);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeBody(XMLSerializer context) throws SAXException {
/*  595 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "X509Certificate");
/*  596 */       context.endNamespaceDecls();
/*  597 */       context.endAttributes();
/*      */       try {
/*  599 */         context.text(Base64BinaryType.save(this._Value), "Value");
/*  600 */       } catch (Exception e) {
/*  601 */         Util.handlePrintConversionException(this, e, context);
/*      */       } 
/*  603 */       context.endElement();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeURIs(XMLSerializer context) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public Class getPrimaryInterface() {
/*  617 */       return (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509Certificate == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509Certificate = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType$X509Certificate")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509Certificate;
/*      */     }
/*      */     
/*      */     public DocumentDeclaration createRawValidator() {
/*  621 */       if (schemaFragment == null) {
/*  622 */         schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000)com.sun.msv.datatype.xsd.Base64BinaryType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.BinaryBaseType§Î\016^¯W\021\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\023L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\fbase64Binarysr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xpq\000~\000\027q\000~\000\026sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\036psq\000~\000\nppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\020q\000~\000\026t\000\005QNameq\000~\000\032q\000~\000\034sq\000~\000\037q\000~\000(q\000~\000\026sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\035\001q\000~\0000sq\000~\000*t\000\017X509Certificatet\000\"http://www.w3.org/2000/09/xmldsig#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\002\001pq\000~\000\"q\000~\000\tx");
/*      */       }
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
/*  661 */       return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*      */     }
/*      */     
/*      */     public class Unmarshaller
/*      */       extends AbstractUnmarshallingEventHandlerImpl
/*      */     {
/*      */       private final X509DataTypeImpl.X509CertificateImpl this$0;
/*      */       
/*      */       public Unmarshaller(UnmarshallingContext context) {
/*  670 */         super(context, "----");
/*      */       }
/*      */       
/*      */       protected Unmarshaller(UnmarshallingContext context, int startState) {
/*  674 */         this(context);
/*  675 */         this.state = startState;
/*      */       }
/*      */       
/*      */       public Object owner() {
/*  679 */         return X509DataTypeImpl.X509CertificateImpl.this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*  688 */         switch (this.state) {
/*      */           case 0:
/*  690 */             if ("X509Certificate" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  691 */               this.context.pushAttributes(__atts, true);
/*  692 */               this.state = 1;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 3:
/*  697 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*      */             return;
/*      */         } 
/*  700 */         super.enterElement(___uri, ___local, ___qname, __atts);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*  711 */         switch (this.state) {
/*      */           case 2:
/*  713 */             if ("X509Certificate" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  714 */               this.context.popAttributes();
/*  715 */               this.state = 3;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 3:
/*  720 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/*  723 */         super.leaveElement(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*  734 */         switch (this.state) {
/*      */           case 3:
/*  736 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/*  739 */         super.enterAttribute(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*  750 */         switch (this.state) {
/*      */           case 3:
/*  752 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/*  755 */         super.leaveAttribute(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void handleText(String value) throws SAXException {
/*      */         try {
/*  767 */           switch (this.state) {
/*      */             case 1:
/*  769 */               this.state = 2;
/*  770 */               eatText1(value);
/*      */               return;
/*      */             case 3:
/*  773 */               revertToParentFromText(value);
/*      */               return;
/*      */           } 
/*  776 */         } catch (RuntimeException e) {
/*  777 */           handleUnexpectedTextException(value, e);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private void eatText1(String value) throws SAXException {
/*      */         try {
/*  787 */           X509DataTypeImpl.X509CertificateImpl.this._Value = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/*  788 */         } catch (Exception e) {
/*  789 */           handleParseConversionException(e);
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class X509IssuerSerialImpl
/*      */     extends X509IssuerSerialTypeImpl
/*      */     implements X509DataType.X509IssuerSerial, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject
/*      */   {
/*  802 */     public static final Class version = (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.JAXBVersion")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion;
/*      */     private static Grammar schemaFragment;
/*      */     
/*      */     private static final Class PRIMARY_INTERFACE_CLASS() {
/*  806 */       return (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509IssuerSerial == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509IssuerSerial = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType$X509IssuerSerial")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509IssuerSerial;
/*      */     }
/*      */     
/*      */     public String ____jaxb_ri____getNamespaceURI() {
/*  810 */       return "http://www.w3.org/2000/09/xmldsig#";
/*      */     }
/*      */     
/*      */     public String ____jaxb_ri____getLocalName() {
/*  814 */       return "X509IssuerSerial";
/*      */     }
/*      */     
/*      */     public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  818 */       return (UnmarshallingEventHandler)new Unmarshaller(context);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeBody(XMLSerializer context) throws SAXException {
/*  824 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial");
/*  825 */       super.serializeURIs(context);
/*  826 */       context.endNamespaceDecls();
/*  827 */       super.serializeAttributes(context);
/*  828 */       context.endAttributes();
/*  829 */       super.serializeBody(context);
/*  830 */       context.endElement();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeURIs(XMLSerializer context) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public Class getPrimaryInterface() {
/*  844 */       return (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509IssuerSerial == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509IssuerSerial = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType$X509IssuerSerial")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509IssuerSerial;
/*      */     }
/*      */     
/*      */     public DocumentDeclaration createRawValidator() {
/*  848 */       if (schemaFragment == null) {
/*  849 */         schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\007ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\027L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\022psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\027L\000\fnamespaceURIq\000~\000\027xpq\000~\000\033q\000~\000\032sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\022psq\000~\000\rppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\024q\000~\000\032t\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\035q\000~\000 sq\000~\000!q\000~\000*q\000~\000\032sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\027L\000\fnamespaceURIq\000~\000\027xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\021\001q\000~\0004sq\000~\000.t\000\016X509IssuerNamet\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\000pp\000sq\000~\000\007ppsq\000~\000\rppsr\000$com.sun.msv.datatype.xsd.IntegerType\000\000\000\000\000\000\000\001\002\000\000xr\000+com.sun.msv.datatype.xsd.IntegerDerivedTypeñ]&6k¾\002\000\001L\000\nbaseFacetst\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xq\000~\000\024q\000~\000\032t\000\007integerq\000~\000,sr\000,com.sun.msv.datatype.xsd.FractionDigitsFacet\000\000\000\000\000\000\000\001\002\000\001I\000\005scalexr\000;com.sun.msv.datatype.xsd.DataTypeWithLexicalConstraintFacetT\034>\032zbê\002\000\000xr\000*com.sun.msv.datatype.xsd.DataTypeWithFacet\000\000\000\000\000\000\000\001\002\000\005Z\000\fisFacetFixedZ\000\022needValueCheckFlagL\000\bbaseTypeq\000~\000>L\000\fconcreteTypet\000'Lcom/sun/msv/datatype/xsd/ConcreteType;L\000\tfacetNameq\000~\000\027xq\000~\000\026ppq\000~\000,\001\000sr\000#com.sun.msv.datatype.xsd.NumberType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\024q\000~\000\032t\000\007decimalq\000~\000,q\000~\000Gt\000\016fractionDigits\000\000\000\000q\000~\000 sq\000~\000!q\000~\000@q\000~\000\032sq\000~\000#ppsq\000~\000%q\000~\000\022pq\000~\000'q\000~\0000q\000~\0004sq\000~\000.t\000\020X509SerialNumberq\000~\0008sq\000~\000#ppsq\000~\000%q\000~\000\022pq\000~\000'q\000~\0000q\000~\0004sq\000~\000.t\000\020X509IssuerSerialq\000~\0008sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\007\001pq\000~\000\tq\000~\000\fq\000~\000:q\000~\000$q\000~\000Kq\000~\000Oq\000~\000\nx");
/*      */       }
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
/*  903 */       return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*      */     }
/*      */     
/*      */     public class Unmarshaller
/*      */       extends AbstractUnmarshallingEventHandlerImpl
/*      */     {
/*      */       private final X509DataTypeImpl.X509IssuerSerialImpl this$0;
/*      */       
/*      */       public Unmarshaller(UnmarshallingContext context) {
/*  912 */         super(context, "----");
/*      */       }
/*      */       
/*      */       protected Unmarshaller(UnmarshallingContext context, int startState) {
/*  916 */         this(context);
/*  917 */         this.state = startState;
/*      */       }
/*      */       
/*      */       public Object owner() {
/*  921 */         return X509DataTypeImpl.X509IssuerSerialImpl.this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*  930 */         switch (this.state) {
/*      */           case 3:
/*  932 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*      */             return;
/*      */           case 0:
/*  935 */             if ("X509IssuerSerial" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  936 */               this.context.pushAttributes(__atts, false);
/*  937 */               this.state = 1;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 1:
/*  942 */             if ("X509IssuerName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  943 */               X509DataTypeImpl.X509IssuerSerialImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new X509IssuerSerialTypeImpl.Unmarshaller(X509DataTypeImpl.X509IssuerSerialImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*      */               return;
/*      */             } 
/*      */             break;
/*      */         } 
/*  948 */         super.enterElement(___uri, ___local, ___qname, __atts);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*  959 */         switch (this.state) {
/*      */           case 3:
/*  961 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*      */             return;
/*      */           case 2:
/*  964 */             if ("X509IssuerSerial" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/*  965 */               this.context.popAttributes();
/*  966 */               this.state = 3;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */         } 
/*  971 */         super.leaveElement(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*  982 */         switch (this.state) {
/*      */           case 3:
/*  984 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/*  987 */         super.enterAttribute(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*  998 */         switch (this.state) {
/*      */           case 3:
/* 1000 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/* 1003 */         super.leaveAttribute(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void handleText(String value) throws SAXException {
/*      */         try {
/* 1015 */           switch (this.state) {
/*      */             case 3:
/* 1017 */               revertToParentFromText(value);
/*      */               return;
/*      */           } 
/* 1020 */         } catch (RuntimeException e) {
/* 1021 */           handleUnexpectedTextException(value, e);
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class X509SKIImpl
/*      */     implements X509DataType.X509SKI, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject
/*      */   {
/*      */     protected byte[] _Value;
/*      */ 
/*      */     
/* 1035 */     public static final Class version = (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.JAXBVersion")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion;
/*      */     
/*      */     private static Grammar schemaFragment;
/*      */     
/*      */     public X509SKIImpl() {}
/*      */     
/*      */     public X509SKIImpl(byte[] value) {
/* 1042 */       this._Value = value;
/*      */     }
/*      */     
/*      */     private static final Class PRIMARY_INTERFACE_CLASS() {
/* 1046 */       return (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SKI == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SKI = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType$X509SKI")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SKI;
/*      */     }
/*      */     
/*      */     public String ____jaxb_ri____getNamespaceURI() {
/* 1050 */       return "http://www.w3.org/2000/09/xmldsig#";
/*      */     }
/*      */     
/*      */     public String ____jaxb_ri____getLocalName() {
/* 1054 */       return "X509SKI";
/*      */     }
/*      */     
/*      */     public byte[] getValue() {
/* 1058 */       return this._Value;
/*      */     }
/*      */     
/*      */     public void setValue(byte[] value) {
/* 1062 */       this._Value = value;
/*      */     }
/*      */     
/*      */     public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/* 1066 */       return (UnmarshallingEventHandler)new Unmarshaller(context);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeBody(XMLSerializer context) throws SAXException {
/* 1072 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "X509SKI");
/* 1073 */       context.endNamespaceDecls();
/* 1074 */       context.endAttributes();
/*      */       try {
/* 1076 */         context.text(Base64BinaryType.save(this._Value), "Value");
/* 1077 */       } catch (Exception e) {
/* 1078 */         Util.handlePrintConversionException(this, e, context);
/*      */       } 
/* 1080 */       context.endElement();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeURIs(XMLSerializer context) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public Class getPrimaryInterface() {
/* 1094 */       return (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SKI == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SKI = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType$X509SKI")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SKI;
/*      */     }
/*      */     
/*      */     public DocumentDeclaration createRawValidator() {
/* 1098 */       if (schemaFragment == null) {
/* 1099 */         schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000)com.sun.msv.datatype.xsd.Base64BinaryType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.BinaryBaseType§Î\016^¯W\021\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\023L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\fbase64Binarysr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xpq\000~\000\027q\000~\000\026sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\036psq\000~\000\nppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\020q\000~\000\026t\000\005QNameq\000~\000\032q\000~\000\034sq\000~\000\037q\000~\000(q\000~\000\026sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\035\001q\000~\0000sq\000~\000*t\000\007X509SKIt\000\"http://www.w3.org/2000/09/xmldsig#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\002\001pq\000~\000\"q\000~\000\tx");
/*      */       }
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
/* 1138 */       return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*      */     }
/*      */     
/*      */     public class Unmarshaller
/*      */       extends AbstractUnmarshallingEventHandlerImpl
/*      */     {
/*      */       private final X509DataTypeImpl.X509SKIImpl this$0;
/*      */       
/*      */       public Unmarshaller(UnmarshallingContext context) {
/* 1147 */         super(context, "----");
/*      */       }
/*      */       
/*      */       protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 1151 */         this(context);
/* 1152 */         this.state = startState;
/*      */       }
/*      */       
/*      */       public Object owner() {
/* 1156 */         return X509DataTypeImpl.X509SKIImpl.this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 1165 */         switch (this.state) {
/*      */           case 0:
/* 1167 */             if ("X509SKI" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 1168 */               this.context.pushAttributes(__atts, true);
/* 1169 */               this.state = 1;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 3:
/* 1174 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*      */             return;
/*      */         } 
/* 1177 */         super.enterElement(___uri, ___local, ___qname, __atts);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/* 1188 */         switch (this.state) {
/*      */           case 3:
/* 1190 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*      */             return;
/*      */           case 2:
/* 1193 */             if ("X509SKI" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 1194 */               this.context.popAttributes();
/* 1195 */               this.state = 3;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */         } 
/* 1200 */         super.leaveElement(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 1211 */         switch (this.state) {
/*      */           case 3:
/* 1213 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/* 1216 */         super.enterAttribute(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 1227 */         switch (this.state) {
/*      */           case 3:
/* 1229 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/* 1232 */         super.leaveAttribute(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void handleText(String value) throws SAXException {
/*      */         try {
/* 1244 */           switch (this.state) {
/*      */             case 1:
/* 1246 */               this.state = 2;
/* 1247 */               eatText1(value);
/*      */               return;
/*      */             case 3:
/* 1250 */               revertToParentFromText(value);
/*      */               return;
/*      */           } 
/* 1253 */         } catch (RuntimeException e) {
/* 1254 */           handleUnexpectedTextException(value, e);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private void eatText1(String value) throws SAXException {
/*      */         try {
/* 1264 */           X509DataTypeImpl.X509SKIImpl.this._Value = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 1265 */         } catch (Exception e) {
/* 1266 */           handleParseConversionException(e);
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class X509SubjectNameImpl
/*      */     implements X509DataType.X509SubjectName, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject
/*      */   {
/*      */     protected String _Value;
/*      */     
/* 1278 */     public static final Class version = (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.JAXBVersion")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion;
/*      */     
/*      */     private static Grammar schemaFragment;
/*      */     
/*      */     public X509SubjectNameImpl() {}
/*      */     
/*      */     public X509SubjectNameImpl(String value) {
/* 1285 */       this._Value = value;
/*      */     }
/*      */     
/*      */     private static final Class PRIMARY_INTERFACE_CLASS() {
/* 1289 */       return (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SubjectName == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SubjectName = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType$X509SubjectName")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SubjectName;
/*      */     }
/*      */     
/*      */     public String ____jaxb_ri____getNamespaceURI() {
/* 1293 */       return "http://www.w3.org/2000/09/xmldsig#";
/*      */     }
/*      */     
/*      */     public String ____jaxb_ri____getLocalName() {
/* 1297 */       return "X509SubjectName";
/*      */     }
/*      */     
/*      */     public String getValue() {
/* 1301 */       return this._Value;
/*      */     }
/*      */     
/*      */     public void setValue(String value) {
/* 1305 */       this._Value = value;
/*      */     }
/*      */     
/*      */     public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/* 1309 */       return (UnmarshallingEventHandler)new Unmarshaller(context);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeBody(XMLSerializer context) throws SAXException {
/* 1315 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "X509SubjectName");
/* 1316 */       context.endNamespaceDecls();
/* 1317 */       context.endAttributes();
/*      */       try {
/* 1319 */         context.text(this._Value, "Value");
/* 1320 */       } catch (Exception e) {
/* 1321 */         Util.handlePrintConversionException(this, e, context);
/*      */       } 
/* 1323 */       context.endElement();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void serializeURIs(XMLSerializer context) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public Class getPrimaryInterface() {
/* 1337 */       return (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SubjectName == null) ? (X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SubjectName = X509DataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.X509DataType$X509SubjectName")) : X509DataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$X509DataType$X509SubjectName;
/*      */     }
/*      */     
/*      */     public DocumentDeclaration createRawValidator() {
/* 1341 */       if (schemaFragment == null) {
/* 1342 */         schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\024L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\017psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\024L\000\fnamespaceURIq\000~\000\024xpq\000~\000\030q\000~\000\027sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\017psq\000~\000\nppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\021q\000~\000\027t\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\032q\000~\000\035sq\000~\000\036q\000~\000'q\000~\000\027sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\024L\000\fnamespaceURIq\000~\000\024xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\016\001q\000~\0001sq\000~\000+t\000\017X509SubjectNamet\000\"http://www.w3.org/2000/09/xmldsig#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\002\001pq\000~\000\tq\000~\000!x");
/*      */       }
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
/* 1382 */       return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*      */     }
/*      */     
/*      */     public class Unmarshaller
/*      */       extends AbstractUnmarshallingEventHandlerImpl
/*      */     {
/*      */       private final X509DataTypeImpl.X509SubjectNameImpl this$0;
/*      */       
/*      */       public Unmarshaller(UnmarshallingContext context) {
/* 1391 */         super(context, "----");
/*      */       }
/*      */       
/*      */       protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 1395 */         this(context);
/* 1396 */         this.state = startState;
/*      */       }
/*      */       
/*      */       public Object owner() {
/* 1400 */         return X509DataTypeImpl.X509SubjectNameImpl.this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 1409 */         switch (this.state) {
/*      */           case 0:
/* 1411 */             if ("X509SubjectName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 1412 */               this.context.pushAttributes(__atts, true);
/* 1413 */               this.state = 1;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 3:
/* 1418 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*      */             return;
/*      */         } 
/* 1421 */         super.enterElement(___uri, ___local, ___qname, __atts);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/* 1432 */         switch (this.state) {
/*      */           case 2:
/* 1434 */             if ("X509SubjectName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 1435 */               this.context.popAttributes();
/* 1436 */               this.state = 3;
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           case 3:
/* 1441 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/* 1444 */         super.leaveElement(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 1455 */         switch (this.state) {
/*      */           case 3:
/* 1457 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/* 1460 */         super.enterAttribute(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 1471 */         switch (this.state) {
/*      */           case 3:
/* 1473 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*      */             return;
/*      */         } 
/* 1476 */         super.leaveAttribute(___uri, ___local, ___qname);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void handleText(String value) throws SAXException {
/*      */         try {
/* 1488 */           switch (this.state) {
/*      */             case 1:
/* 1490 */               this.state = 2;
/* 1491 */               eatText1(value);
/*      */               return;
/*      */             case 3:
/* 1494 */               revertToParentFromText(value);
/*      */               return;
/*      */           } 
/* 1497 */         } catch (RuntimeException e) {
/* 1498 */           handleUnexpectedTextException(value, e);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private void eatText1(String value) throws SAXException {
/*      */         try {
/* 1508 */           X509DataTypeImpl.X509SubjectNameImpl.this._Value = value;
/* 1509 */         } catch (Exception e) {
/* 1510 */           handleParseConversionException(e);
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\X509DataTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */