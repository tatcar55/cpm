/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SignaturePropertiesType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SignaturePropertiesTypeImpl implements SignaturePropertiesType, JAXBObject, UnmarshallableObject, XMLSerializable, IdentifiableObject, ValidatableObject {
/*     */   protected ListImpl _SignatureProperty;
/*  16 */   public static final Class version = JAXBVersion.class; protected String _Id; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyTypeImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return SignaturePropertiesType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getSignatureProperty() {
/*  24 */     if (this._SignatureProperty == null) {
/*  25 */       this._SignatureProperty = new ListImpl(new ArrayList());
/*     */     }
/*  27 */     return this._SignatureProperty;
/*     */   }
/*     */   
/*     */   public List getSignatureProperty() {
/*  31 */     return (List)_getSignatureProperty();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  35 */     return this._Id;
/*     */   }
/*     */   
/*     */   public void setId(String value) {
/*  39 */     this._Id = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  43 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  49 */     int idx1 = 0;
/*  50 */     int len1 = (this._SignatureProperty == null) ? 0 : this._SignatureProperty.size();
/*  51 */     while (idx1 != len1) {
/*  52 */       if (this._SignatureProperty.get(idx1) instanceof javax.xml.bind.Element) {
/*  53 */         context.childAsBody((JAXBObject)this._SignatureProperty.get(idx1++), "SignatureProperty"); continue;
/*     */       } 
/*  55 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "SignatureProperty");
/*  56 */       int idx_0 = idx1;
/*  57 */       context.childAsURIs((JAXBObject)this._SignatureProperty.get(idx_0++), "SignatureProperty");
/*  58 */       context.endNamespaceDecls();
/*  59 */       int idx_1 = idx1;
/*  60 */       context.childAsAttributes((JAXBObject)this._SignatureProperty.get(idx_1++), "SignatureProperty");
/*  61 */       context.endAttributes();
/*  62 */       context.childAsBody((JAXBObject)this._SignatureProperty.get(idx1++), "SignatureProperty");
/*  63 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  71 */     int idx1 = 0;
/*  72 */     int len1 = (this._SignatureProperty == null) ? 0 : this._SignatureProperty.size();
/*  73 */     if (this._Id != null) {
/*  74 */       context.startAttribute("", "Id");
/*     */       try {
/*  76 */         context.text(context.onID(this, this._Id), "Id");
/*  77 */       } catch (Exception e) {
/*  78 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  80 */       context.endAttribute();
/*     */     } 
/*  82 */     while (idx1 != len1) {
/*  83 */       if (this._SignatureProperty.get(idx1) instanceof javax.xml.bind.Element) {
/*  84 */         context.childAsAttributes((JAXBObject)this._SignatureProperty.get(idx1++), "SignatureProperty"); continue;
/*     */       } 
/*  86 */       idx1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*  94 */     int idx1 = 0;
/*  95 */     int len1 = (this._SignatureProperty == null) ? 0 : this._SignatureProperty.size();
/*  96 */     while (idx1 != len1) {
/*  97 */       if (this._SignatureProperty.get(idx1) instanceof javax.xml.bind.Element) {
/*  98 */         context.childAsURIs((JAXBObject)this._SignatureProperty.get(idx1++), "SignatureProperty"); continue;
/*     */       } 
/* 100 */       idx1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String ____jaxb____getId() {
/* 106 */     return this._Id;
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 110 */     return SignaturePropertiesType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 114 */     if (schemaFragment == null) {
/* 115 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\tppsq\000~\000\006sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\fxq\000~\000\003q\000~\000\022psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\021\001q\000~\000\026sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\027q\000~\000\034sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\036xq\000~\000\031t\000=com.sun.xml.wss.saml.internal.saml11.jaxb10.SignaturePropertyt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\013pp\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\006q\000~\000\022psq\000~\000\023q\000~\000\022pq\000~\000\026q\000~\000\032q\000~\000\034sq\000~\000\035t\000Acom.sun.xml.wss.saml.internal.saml11.jaxb10.SignaturePropertyTypeq\000~\000!sq\000~\000\tppsq\000~\000\023q\000~\000\022psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\036L\000\btypeNameq\000~\000\036L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\022psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\036L\000\fnamespaceURIq\000~\000\036xpq\000~\0007q\000~\0006sq\000~\000\035t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\034sq\000~\000\035t\000\021SignaturePropertyt\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\tppsq\000~\000\023q\000~\000\022psq\000~\000,ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\0001q\000~\0006t\000\002IDq\000~\000:\000q\000~\000<sq\000~\000=q\000~\000Mq\000~\0006sq\000~\000\035t\000\002Idt\000\000q\000~\000\034sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\n\001pq\000~\000Eq\000~\000#q\000~\000\017q\000~\000%q\000~\000\020q\000~\000&q\000~\000\bq\000~\000*q\000~\000\005q\000~\000\nx");
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
/* 166 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SignaturePropertiesTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 175 */       super(context, "-------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 179 */       this(context);
/* 180 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 184 */       return SignaturePropertiesTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true)
/*     */       { int attIdx;
/* 193 */         switch (this.state) {
/*     */           case 0:
/* 195 */             attIdx = this.context.getAttribute("", "Id");
/* 196 */             if (attIdx >= 0) {
/* 197 */               String v = this.context.eatAttribute(attIdx);
/* 198 */               this.state = 3;
/* 199 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 202 */             this.state = 3;
/*     */             continue;
/*     */           case 3:
/* 205 */             if ("SignatureProperty" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 206 */               SignaturePropertiesTypeImpl.this._getSignatureProperty().add(spawnChildFromEnterElement((SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyImpl == null) ? (SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyImpl = SignaturePropertiesTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignaturePropertyImpl")) : SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyImpl, 4, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 209 */             if ("SignatureProperty" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 210 */               this.context.pushAttributes(__atts, true);
/* 211 */               this.state = 5;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 216 */             attIdx = this.context.getAttribute("", "Id");
/* 217 */             if (attIdx >= 0) {
/* 218 */               this.context.consumeAttribute(attIdx);
/* 219 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 222 */             attIdx = this.context.getAttribute("", "Target");
/* 223 */             if (attIdx >= 0) {
/* 224 */               this.context.consumeAttribute(attIdx);
/* 225 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 230 */             if ("SignatureProperty" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 231 */               SignaturePropertiesTypeImpl.this._getSignatureProperty().add(spawnChildFromEnterElement((SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyImpl == null) ? (SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyImpl = SignaturePropertiesTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignaturePropertyImpl")) : SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyImpl, 4, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 234 */             if ("SignatureProperty" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 235 */               this.context.pushAttributes(__atts, true);
/* 236 */               this.state = 5;
/*     */               return;
/*     */             } 
/* 239 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts); return;
/*     */           default:
/*     */             break;
/* 242 */         }  super.enterElement(___uri, ___local, ___qname, __atts); return; }  super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 251 */         SignaturePropertiesTypeImpl.this._Id = this.context.addToIdTable(WhiteSpaceProcessor.collapse(value));
/* 252 */       } catch (Exception e) {
/* 253 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true)
/*     */       { int attIdx;
/* 263 */         switch (this.state) {
/*     */           case 6:
/* 265 */             if ("SignatureProperty" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 266 */               this.context.popAttributes();
/* 267 */               this.state = 4;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 272 */             attIdx = this.context.getAttribute("", "Id");
/* 273 */             if (attIdx >= 0) {
/* 274 */               String v = this.context.eatAttribute(attIdx);
/* 275 */               this.state = 3;
/* 276 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 279 */             this.state = 3;
/*     */             continue;
/*     */           case 5:
/* 282 */             attIdx = this.context.getAttribute("", "Id");
/* 283 */             if (attIdx >= 0) {
/* 284 */               this.context.consumeAttribute(attIdx);
/* 285 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 288 */             attIdx = this.context.getAttribute("", "Target");
/* 289 */             if (attIdx >= 0) {
/* 290 */               this.context.consumeAttribute(attIdx);
/* 291 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 296 */             revertToParentFromLeaveElement(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 299 */         }  super.leaveElement(___uri, ___local, ___qname); return; }  super.leaveElement(___uri, ___local, ___qname);
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
/* 310 */       { switch (this.state) {
/*     */           case 0:
/* 312 */             if ("Id" == ___local && "" == ___uri) {
/* 313 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 316 */             this.state = 3;
/*     */             continue;
/*     */           case 5:
/* 319 */             if ("Id" == ___local && "" == ___uri) {
/* 320 */               SignaturePropertiesTypeImpl.this._getSignatureProperty().add(spawnChildFromEnterAttribute((SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyTypeImpl == null) ? (SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyTypeImpl = SignaturePropertiesTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignaturePropertyTypeImpl")) : SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyTypeImpl, 6, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/* 323 */             if ("Target" == ___local && "" == ___uri) {
/* 324 */               SignaturePropertiesTypeImpl.this._getSignatureProperty().add(spawnChildFromEnterAttribute((SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyTypeImpl == null) ? (SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyTypeImpl = SignaturePropertiesTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignaturePropertyTypeImpl")) : SignaturePropertiesTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignaturePropertyTypeImpl, 6, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 329 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 332 */         }  super.enterAttribute(___uri, ___local, ___qname); return; }  super.enterAttribute(___uri, ___local, ___qname);
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
/* 343 */         switch (this.state) {
/*     */           case 0:
/* 345 */             attIdx = this.context.getAttribute("", "Id");
/* 346 */             if (attIdx >= 0) {
/* 347 */               String v = this.context.eatAttribute(attIdx);
/* 348 */               this.state = 3;
/* 349 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 352 */             this.state = 3;
/*     */             continue;
/*     */           case 2:
/* 355 */             if ("Id" == ___local && "" == ___uri) {
/* 356 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 361 */             attIdx = this.context.getAttribute("", "Id");
/* 362 */             if (attIdx >= 0) {
/* 363 */               this.context.consumeAttribute(attIdx);
/* 364 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 367 */             attIdx = this.context.getAttribute("", "Target");
/* 368 */             if (attIdx >= 0) {
/* 369 */               this.context.consumeAttribute(attIdx);
/* 370 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 375 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 378 */         }  super.leaveAttribute(___uri, ___local, ___qname); return; }  super.leaveAttribute(___uri, ___local, ___qname);
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
/* 390 */           switch (this.state) {
/*     */             case 0:
/* 392 */               attIdx = this.context.getAttribute("", "Id");
/* 393 */               if (attIdx >= 0) {
/* 394 */                 String v = this.context.eatAttribute(attIdx);
/* 395 */                 this.state = 3;
/* 396 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 399 */               this.state = 3;
/*     */               continue;
/*     */             case 5:
/* 402 */               attIdx = this.context.getAttribute("", "Id");
/* 403 */               if (attIdx >= 0) {
/* 404 */                 this.context.consumeAttribute(attIdx);
/* 405 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 408 */               attIdx = this.context.getAttribute("", "Target");
/* 409 */               if (attIdx >= 0) {
/* 410 */                 this.context.consumeAttribute(attIdx);
/* 411 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/*     */               return;
/*     */             case 1:
/* 416 */               this.state = 2;
/* 417 */               eatText1(value);
/*     */               return;
/*     */             case 4:
/* 420 */               revertToParentFromText(value); return;
/*     */           }  break;
/*     */         } 
/* 423 */       } catch (RuntimeException e) {
/* 424 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SignaturePropertiesTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */