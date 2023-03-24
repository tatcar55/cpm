/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.ManifestType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ManifestTypeImpl implements ManifestType, JAXBObject, UnmarshallableObject, XMLSerializable, IdentifiableObject, ValidatableObject {
/*     */   protected ListImpl _Reference;
/*  16 */   public static final Class version = JAXBVersion.class; protected String _Id; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return ManifestType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getReference() {
/*  24 */     if (this._Reference == null) {
/*  25 */       this._Reference = new ListImpl(new ArrayList());
/*     */     }
/*  27 */     return this._Reference;
/*     */   }
/*     */   
/*     */   public List getReference() {
/*  31 */     return (List)_getReference();
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
/*  50 */     int len1 = (this._Reference == null) ? 0 : this._Reference.size();
/*  51 */     while (idx1 != len1) {
/*  52 */       if (this._Reference.get(idx1) instanceof javax.xml.bind.Element) {
/*  53 */         context.childAsBody((JAXBObject)this._Reference.get(idx1++), "Reference"); continue;
/*     */       } 
/*  55 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "Reference");
/*  56 */       int idx_0 = idx1;
/*  57 */       context.childAsURIs((JAXBObject)this._Reference.get(idx_0++), "Reference");
/*  58 */       context.endNamespaceDecls();
/*  59 */       int idx_1 = idx1;
/*  60 */       context.childAsAttributes((JAXBObject)this._Reference.get(idx_1++), "Reference");
/*  61 */       context.endAttributes();
/*  62 */       context.childAsBody((JAXBObject)this._Reference.get(idx1++), "Reference");
/*  63 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  71 */     int idx1 = 0;
/*  72 */     int len1 = (this._Reference == null) ? 0 : this._Reference.size();
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
/*  83 */       if (this._Reference.get(idx1) instanceof javax.xml.bind.Element) {
/*  84 */         context.childAsAttributes((JAXBObject)this._Reference.get(idx1++), "Reference"); continue;
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
/*  95 */     int len1 = (this._Reference == null) ? 0 : this._Reference.size();
/*  96 */     while (idx1 != len1) {
/*  97 */       if (this._Reference.get(idx1) instanceof javax.xml.bind.Element) {
/*  98 */         context.childAsURIs((JAXBObject)this._Reference.get(idx1++), "Reference"); continue;
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
/* 110 */     return ManifestType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 114 */     if (schemaFragment == null) {
/* 115 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\tppsq\000~\000\006sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\fxq\000~\000\003q\000~\000\022psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\021\001q\000~\000\026sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\027q\000~\000\034sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\036xq\000~\000\031t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Referencet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\013pp\000sq\000~\000\000ppsq\000~\000\013pp\000sq\000~\000\tppsq\000~\000\006q\000~\000\022psq\000~\000\023q\000~\000\022pq\000~\000\026q\000~\000\032q\000~\000\034sq\000~\000\035t\0009com.sun.xml.wss.saml.internal.saml11.jaxb10.ReferenceTypeq\000~\000!sq\000~\000\tppsq\000~\000\023q\000~\000\022psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\036L\000\btypeNameq\000~\000\036L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\022psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\036L\000\fnamespaceURIq\000~\000\036xpq\000~\0007q\000~\0006sq\000~\000\035t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\034sq\000~\000\035t\000\tReferencet\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\tppsq\000~\000\023q\000~\000\022psq\000~\000,ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\0001q\000~\0006t\000\002IDq\000~\000:\000q\000~\000<sq\000~\000=q\000~\000Mq\000~\0006sq\000~\000\035t\000\002Idt\000\000q\000~\000\034sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\n\001pq\000~\000#q\000~\000Eq\000~\000\017q\000~\000%q\000~\000\005q\000~\000\020q\000~\000&q\000~\000\bq\000~\000*q\000~\000\nx");
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
/*     */     private final ManifestTypeImpl this$0;
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
/* 184 */       return ManifestTypeImpl.this;
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
/*     */           case 4:
/* 205 */             attIdx = this.context.getAttribute("", "Id");
/* 206 */             if (attIdx >= 0) {
/* 207 */               this.context.consumeAttribute(attIdx);
/* 208 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 211 */             attIdx = this.context.getAttribute("", "Type");
/* 212 */             if (attIdx >= 0) {
/* 213 */               this.context.consumeAttribute(attIdx);
/* 214 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 217 */             attIdx = this.context.getAttribute("", "URI");
/* 218 */             if (attIdx >= 0) {
/* 219 */               this.context.consumeAttribute(attIdx);
/* 220 */               this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 223 */             if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 224 */               ManifestTypeImpl.this._getReference().add(spawnChildFromEnterElement((ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = ManifestTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 5, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 227 */             if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 228 */               ManifestTypeImpl.this._getReference().add(spawnChildFromEnterElement((ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = ManifestTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 5, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 231 */             if ("DigestMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 232 */               ManifestTypeImpl.this._getReference().add(spawnChildFromEnterElement((ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = ManifestTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 5, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 235 */             if ("DigestMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 236 */               ManifestTypeImpl.this._getReference().add(spawnChildFromEnterElement((ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = ManifestTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 5, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 241 */             if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 242 */               ManifestTypeImpl.this._getReference().add(spawnChildFromEnterElement((ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl == null) ? (ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl = ManifestTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceImpl")) : ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl, 6, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 245 */             if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 246 */               this.context.pushAttributes(__atts, false);
/* 247 */               this.state = 4;
/*     */               return;
/*     */             } 
/* 250 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 3:
/* 253 */             if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 254 */               ManifestTypeImpl.this._getReference().add(spawnChildFromEnterElement((ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl == null) ? (ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl = ManifestTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceImpl")) : ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceImpl, 6, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 257 */             if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 258 */               this.context.pushAttributes(__atts, false);
/* 259 */               this.state = 4; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 264 */         super.enterElement(___uri, ___local, ___qname, __atts); return; }  super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 273 */         ManifestTypeImpl.this._Id = this.context.addToIdTable(WhiteSpaceProcessor.collapse(value));
/* 274 */       } catch (Exception e) {
/* 275 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true)
/*     */       { int attIdx;
/* 285 */         switch (this.state) {
/*     */           case 0:
/* 287 */             attIdx = this.context.getAttribute("", "Id");
/* 288 */             if (attIdx >= 0) {
/* 289 */               String v = this.context.eatAttribute(attIdx);
/* 290 */               this.state = 3;
/* 291 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 294 */             this.state = 3;
/*     */             continue;
/*     */           case 4:
/* 297 */             attIdx = this.context.getAttribute("", "Id");
/* 298 */             if (attIdx >= 0) {
/* 299 */               this.context.consumeAttribute(attIdx);
/* 300 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 303 */             attIdx = this.context.getAttribute("", "Type");
/* 304 */             if (attIdx >= 0) {
/* 305 */               this.context.consumeAttribute(attIdx);
/* 306 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 309 */             attIdx = this.context.getAttribute("", "URI");
/* 310 */             if (attIdx >= 0) {
/* 311 */               this.context.consumeAttribute(attIdx);
/* 312 */               this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 317 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 5:
/* 320 */             if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 321 */               this.context.popAttributes();
/* 322 */               this.state = 6; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 327 */         super.leaveElement(___uri, ___local, ___qname); return; }  super.leaveElement(___uri, ___local, ___qname);
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
/* 338 */       { switch (this.state) {
/*     */           case 0:
/* 340 */             if ("Id" == ___local && "" == ___uri) {
/* 341 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 344 */             this.state = 3;
/*     */             continue;
/*     */           case 4:
/* 347 */             if ("Id" == ___local && "" == ___uri) {
/* 348 */               ManifestTypeImpl.this._getReference().add(spawnChildFromEnterAttribute((ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = ManifestTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 5, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/* 351 */             if ("Type" == ___local && "" == ___uri) {
/* 352 */               ManifestTypeImpl.this._getReference().add(spawnChildFromEnterAttribute((ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = ManifestTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 5, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/* 355 */             if ("URI" == ___local && "" == ___uri) {
/* 356 */               ManifestTypeImpl.this._getReference().add(spawnChildFromEnterAttribute((ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl == null) ? (ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl = ManifestTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl")) : ManifestTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ReferenceTypeImpl, 5, ___uri, ___local, ___qname));
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 361 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 364 */         }  super.enterAttribute(___uri, ___local, ___qname); return; }  super.enterAttribute(___uri, ___local, ___qname);
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
/* 375 */         switch (this.state) {
/*     */           case 0:
/* 377 */             attIdx = this.context.getAttribute("", "Id");
/* 378 */             if (attIdx >= 0) {
/* 379 */               String v = this.context.eatAttribute(attIdx);
/* 380 */               this.state = 3;
/* 381 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 384 */             this.state = 3;
/*     */             continue;
/*     */           case 4:
/* 387 */             attIdx = this.context.getAttribute("", "Id");
/* 388 */             if (attIdx >= 0) {
/* 389 */               this.context.consumeAttribute(attIdx);
/* 390 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 393 */             attIdx = this.context.getAttribute("", "Type");
/* 394 */             if (attIdx >= 0) {
/* 395 */               this.context.consumeAttribute(attIdx);
/* 396 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/* 399 */             attIdx = this.context.getAttribute("", "URI");
/* 400 */             if (attIdx >= 0) {
/* 401 */               this.context.consumeAttribute(attIdx);
/* 402 */               this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 407 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 2:
/* 410 */             if ("Id" == ___local && "" == ___uri) {
/* 411 */               this.state = 3; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 416 */         super.leaveAttribute(___uri, ___local, ___qname); return; }  super.leaveAttribute(___uri, ___local, ___qname);
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
/* 428 */           switch (this.state) {
/*     */             case 0:
/* 430 */               attIdx = this.context.getAttribute("", "Id");
/* 431 */               if (attIdx >= 0) {
/* 432 */                 String v = this.context.eatAttribute(attIdx);
/* 433 */                 this.state = 3;
/* 434 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 437 */               this.state = 3;
/*     */               continue;
/*     */             case 4:
/* 440 */               attIdx = this.context.getAttribute("", "Id");
/* 441 */               if (attIdx >= 0) {
/* 442 */                 this.context.consumeAttribute(attIdx);
/* 443 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 446 */               attIdx = this.context.getAttribute("", "Type");
/* 447 */               if (attIdx >= 0) {
/* 448 */                 this.context.consumeAttribute(attIdx);
/* 449 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/* 452 */               attIdx = this.context.getAttribute("", "URI");
/* 453 */               if (attIdx >= 0) {
/* 454 */                 this.context.consumeAttribute(attIdx);
/* 455 */                 this.context.getCurrentHandler().text(value);
/*     */                 return;
/*     */               } 
/*     */               return;
/*     */             case 6:
/* 460 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 1:
/* 463 */               this.state = 2;
/* 464 */               eatText1(value); return;
/*     */           }  break;
/*     */         } 
/* 467 */       } catch (RuntimeException e) {
/* 468 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\ManifestTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */