/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.NameIdentifierType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectConfirmationType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SubjectTypeImpl implements SubjectType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected SubjectConfirmationType _SubjectConfirmation;
/*  16 */   public static final Class version = JAXBVersion.class; protected NameIdentifierType _NameIdentifier; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationTypeImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierTypeImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return SubjectType.class;
/*     */   }
/*     */   
/*     */   public SubjectConfirmationType getSubjectConfirmation() {
/*  24 */     return this._SubjectConfirmation;
/*     */   }
/*     */   
/*     */   public void setSubjectConfirmation(SubjectConfirmationType value) {
/*  28 */     this._SubjectConfirmation = value;
/*     */   }
/*     */   
/*     */   public NameIdentifierType getNameIdentifier() {
/*  32 */     return this._NameIdentifier;
/*     */   }
/*     */   
/*     */   public void setNameIdentifier(NameIdentifierType value) {
/*  36 */     this._NameIdentifier = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  40 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  46 */     if (this._NameIdentifier != null) {
/*  47 */       if (this._NameIdentifier instanceof javax.xml.bind.Element) {
/*  48 */         context.childAsBody((JAXBObject)this._NameIdentifier, "NameIdentifier");
/*     */       } else {
/*  50 */         context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier");
/*  51 */         context.childAsURIs((JAXBObject)this._NameIdentifier, "NameIdentifier");
/*  52 */         context.endNamespaceDecls();
/*  53 */         context.childAsAttributes((JAXBObject)this._NameIdentifier, "NameIdentifier");
/*  54 */         context.endAttributes();
/*  55 */         context.childAsBody((JAXBObject)this._NameIdentifier, "NameIdentifier");
/*  56 */         context.endElement();
/*     */       } 
/*  58 */       if (this._SubjectConfirmation != null) {
/*  59 */         if (this._SubjectConfirmation instanceof javax.xml.bind.Element) {
/*  60 */           context.childAsBody((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*     */         } else {
/*  62 */           context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation");
/*  63 */           context.childAsURIs((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*  64 */           context.endNamespaceDecls();
/*  65 */           context.childAsAttributes((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*  66 */           context.endAttributes();
/*  67 */           context.childAsBody((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*  68 */           context.endElement();
/*     */         }
/*     */       
/*     */       }
/*  72 */     } else if (this._NameIdentifier == null && this._SubjectConfirmation != null) {
/*  73 */       if (this._SubjectConfirmation instanceof javax.xml.bind.Element) {
/*  74 */         context.childAsBody((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*     */       } else {
/*  76 */         context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation");
/*  77 */         context.childAsURIs((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*  78 */         context.endNamespaceDecls();
/*  79 */         context.childAsAttributes((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*  80 */         context.endAttributes();
/*  81 */         context.childAsBody((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*  82 */         context.endElement();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  91 */     if (this._NameIdentifier != null) {
/*  92 */       if (this._NameIdentifier instanceof javax.xml.bind.Element) {
/*  93 */         context.childAsAttributes((JAXBObject)this._NameIdentifier, "NameIdentifier");
/*     */       }
/*  95 */       if (this._SubjectConfirmation != null && 
/*  96 */         this._SubjectConfirmation instanceof javax.xml.bind.Element) {
/*  97 */         context.childAsAttributes((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*     */       
/*     */       }
/*     */     }
/* 101 */     else if (this._NameIdentifier == null && this._SubjectConfirmation != null && 
/* 102 */       this._SubjectConfirmation instanceof javax.xml.bind.Element) {
/* 103 */       context.childAsAttributes((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 112 */     if (this._NameIdentifier != null) {
/* 113 */       if (this._NameIdentifier instanceof javax.xml.bind.Element) {
/* 114 */         context.childAsURIs((JAXBObject)this._NameIdentifier, "NameIdentifier");
/*     */       }
/* 116 */       if (this._SubjectConfirmation != null && 
/* 117 */         this._SubjectConfirmation instanceof javax.xml.bind.Element) {
/* 118 */         context.childAsURIs((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*     */       
/*     */       }
/*     */     }
/* 122 */     else if (this._NameIdentifier == null && this._SubjectConfirmation != null && 
/* 123 */       this._SubjectConfirmation instanceof javax.xml.bind.Element) {
/* 124 */       context.childAsURIs((JAXBObject)this._SubjectConfirmation, "SubjectConfirmation");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 131 */     return SubjectType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 135 */     if (schemaFragment == null) {
/* 136 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsq\000~\000\000ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\000ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\013xq\000~\000\003q\000~\000\023psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\022\001q\000~\000\027sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\030q\000~\000\035sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\037xq\000~\000\032t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.NameIdentifiert\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\npp\000sq\000~\000\007ppsq\000~\000\npp\000sq\000~\000\000ppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\000>com.sun.xml.wss.saml.internal.saml11.jaxb10.NameIdentifierTypeq\000~\000\"sq\000~\000\000ppsq\000~\000\024q\000~\000\023psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\037L\000\btypeNameq\000~\000\037L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\023psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\037L\000\fnamespaceURIq\000~\000\037xpq\000~\0008q\000~\0007sq\000~\000\036t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\035sq\000~\000\036t\000\016NameIdentifiert\000%urn:oasis:names:tc:SAML:1.0:assertionsq\000~\000\000ppsq\000~\000\000q\000~\000\023psq\000~\000\nq\000~\000\023p\000sq\000~\000\000ppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\000?com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectConfirmationq\000~\000\"sq\000~\000\nq\000~\000\023p\000sq\000~\000\007ppsq\000~\000\npp\000sq\000~\000\000ppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\000Ccom.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectConfirmationTypeq\000~\000\"sq\000~\000\000ppsq\000~\000\024q\000~\000\023pq\000~\0000q\000~\000@q\000~\000\035sq\000~\000\036t\000\023SubjectConfirmationq\000~\000Eq\000~\000\035sq\000~\000\npp\000sq\000~\000\000ppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000Mq\000~\000\"sq\000~\000\npp\000sq\000~\000\007ppsq\000~\000\npp\000sq\000~\000\000ppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000Uq\000~\000\"sq\000~\000\000ppsq\000~\000\024q\000~\000\023pq\000~\0000q\000~\000@q\000~\000\035sq\000~\000\036q\000~\000Yq\000~\000Esr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\030\001pq\000~\000\006q\000~\000\005q\000~\000$q\000~\000Oq\000~\000`q\000~\000\bq\000~\000\016q\000~\000&q\000~\000Iq\000~\000Qq\000~\000[q\000~\000bq\000~\000\021q\000~\000'q\000~\000Jq\000~\000Rq\000~\000\\q\000~\000cq\000~\000+q\000~\000Vq\000~\000fq\000~\000Fq\000~\000\tq\000~\000Gx");
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
/* 194 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SubjectTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 203 */       super(context, "---------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 207 */       this(context);
/* 208 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 212 */       return SubjectTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true)
/*     */       { int attIdx;
/* 221 */         switch (this.state) {
/*     */           case 1:
/* 223 */             attIdx = this.context.getAttribute("", "Format");
/* 224 */             if (attIdx >= 0) {
/* 225 */               this.context.consumeAttribute(attIdx);
/* 226 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 229 */             attIdx = this.context.getAttribute("", "NameQualifier");
/* 230 */             if (attIdx >= 0) {
/* 231 */               this.context.consumeAttribute(attIdx);
/* 232 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 7:
/* 237 */             if ("ConfirmationMethod" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 238 */               SubjectTypeImpl.this._SubjectConfirmation = (SubjectConfirmationTypeImpl)spawnChildFromEnterElement((SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationTypeImpl == null) ? (SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationTypeImpl = SubjectTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectConfirmationTypeImpl")) : SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationTypeImpl, 8, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 243 */             if ("SubjectConfirmation" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 244 */               SubjectTypeImpl.this._SubjectConfirmation = (SubjectConfirmationImpl)spawnChildFromEnterElement((SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationImpl == null) ? (SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationImpl = SubjectTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectConfirmationImpl")) : SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 247 */             if ("SubjectConfirmation" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 248 */               this.context.pushAttributes(__atts, false);
/* 249 */               this.state = 4;
/*     */               return;
/*     */             } 
/* 252 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 255 */             if ("NameIdentifier" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 256 */               SubjectTypeImpl.this._NameIdentifier = (NameIdentifierImpl)spawnChildFromEnterElement((SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierImpl == null) ? (SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierImpl = SubjectTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.NameIdentifierImpl")) : SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierImpl, 3, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 259 */             if ("NameIdentifier" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 260 */               this.context.pushAttributes(__atts, true);
/* 261 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 264 */             if ("SubjectConfirmation" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 265 */               SubjectTypeImpl.this._SubjectConfirmation = (SubjectConfirmationImpl)spawnChildFromEnterElement((SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationImpl == null) ? (SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationImpl = SubjectTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectConfirmationImpl")) : SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationImpl, 6, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 268 */             if ("SubjectConfirmation" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 269 */               this.context.pushAttributes(__atts, false);
/* 270 */               this.state = 7;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 275 */             if ("ConfirmationMethod" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 276 */               SubjectTypeImpl.this._SubjectConfirmation = (SubjectConfirmationTypeImpl)spawnChildFromEnterElement((SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationTypeImpl == null) ? (SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationTypeImpl = SubjectTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectConfirmationTypeImpl")) : SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectConfirmationTypeImpl, 5, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 281 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts); return;
/*     */           default:
/*     */             break;
/* 284 */         }  super.enterElement(___uri, ___local, ___qname, __atts); return; }  super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true)
/*     */       { int attIdx;
/* 295 */         switch (this.state) {
/*     */           case 1:
/* 297 */             attIdx = this.context.getAttribute("", "Format");
/* 298 */             if (attIdx >= 0) {
/* 299 */               this.context.consumeAttribute(attIdx);
/* 300 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 303 */             attIdx = this.context.getAttribute("", "NameQualifier");
/* 304 */             if (attIdx >= 0) {
/* 305 */               this.context.consumeAttribute(attIdx);
/* 306 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 311 */             if ("SubjectConfirmation" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 312 */               this.context.popAttributes();
/* 313 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 318 */             this.state = 6;
/*     */             continue;
/*     */           case 2:
/* 321 */             if ("NameIdentifier" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 322 */               this.context.popAttributes();
/* 323 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 328 */             if ("SubjectConfirmation" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 329 */               this.context.popAttributes();
/* 330 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 335 */             revertToParentFromLeaveElement(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 338 */         }  super.leaveElement(___uri, ___local, ___qname); return; }  super.leaveElement(___uri, ___local, ___qname);
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
/* 349 */         switch (this.state) {
/*     */           case 1:
/* 351 */             if ("Format" == ___local && "" == ___uri) {
/* 352 */               SubjectTypeImpl.this._NameIdentifier = (NameIdentifierTypeImpl)spawnChildFromEnterAttribute((SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierTypeImpl == null) ? (SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierTypeImpl = SubjectTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.NameIdentifierTypeImpl")) : SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierTypeImpl, 2, ___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 355 */             if ("NameQualifier" == ___local && "" == ___uri) {
/* 356 */               SubjectTypeImpl.this._NameIdentifier = (NameIdentifierTypeImpl)spawnChildFromEnterAttribute((SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierTypeImpl == null) ? (SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierTypeImpl = SubjectTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.NameIdentifierTypeImpl")) : SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierTypeImpl, 2, ___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 361 */             this.state = 6;
/*     */             continue;
/*     */           case 6:
/* 364 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 367 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 378 */         switch (this.state) {
/*     */           case 1:
/* 380 */             attIdx = this.context.getAttribute("", "Format");
/* 381 */             if (attIdx >= 0) {
/* 382 */               this.context.consumeAttribute(attIdx);
/* 383 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 386 */             attIdx = this.context.getAttribute("", "NameQualifier");
/* 387 */             if (attIdx >= 0) {
/* 388 */               this.context.consumeAttribute(attIdx);
/* 389 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 394 */             this.state = 6;
/*     */             continue;
/*     */           case 6:
/* 397 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 400 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 412 */           switch (this.state) {
/*     */             case 1:
/* 414 */               attIdx = this.context.getAttribute("", "Format");
/* 415 */               if (attIdx >= 0) {
/* 416 */                 this.context.consumeAttribute(attIdx);
/* 417 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 420 */               attIdx = this.context.getAttribute("", "NameQualifier");
/* 421 */               if (attIdx >= 0) {
/* 422 */                 this.context.consumeAttribute(attIdx);
/* 423 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 426 */               SubjectTypeImpl.this._NameIdentifier = (NameIdentifierTypeImpl)spawnChildFromText((SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierTypeImpl == null) ? (SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierTypeImpl = SubjectTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.NameIdentifierTypeImpl")) : SubjectTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$NameIdentifierTypeImpl, 2, value);
/*     */               return;
/*     */             case 3:
/* 429 */               this.state = 6;
/*     */               continue;
/*     */             case 6:
/* 432 */               revertToParentFromText(value); return;
/*     */           }  break;
/*     */         } 
/* 435 */       } catch (RuntimeException e) {
/* 436 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SubjectTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */