/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.RetrievalMethodType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformsType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class RetrievalMethodTypeImpl implements RetrievalMethodType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _Type;
/*  17 */   public static final Class version = JAXBVersion.class; protected String _URI; protected TransformsType _Transforms; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return RetrievalMethodType.class;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  25 */     return this._Type;
/*     */   }
/*     */   
/*     */   public void setType(String value) {
/*  29 */     this._Type = value;
/*     */   }
/*     */   
/*     */   public String getURI() {
/*  33 */     return this._URI;
/*     */   }
/*     */   
/*     */   public void setURI(String value) {
/*  37 */     this._URI = value;
/*     */   }
/*     */   
/*     */   public TransformsType getTransforms() {
/*  41 */     return this._Transforms;
/*     */   }
/*     */   
/*     */   public void setTransforms(TransformsType value) {
/*  45 */     this._Transforms = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  49 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  55 */     if (this._Transforms != null) {
/*  56 */       if (this._Transforms instanceof javax.xml.bind.Element) {
/*  57 */         context.childAsBody((JAXBObject)this._Transforms, "Transforms");
/*     */       } else {
/*  59 */         context.startElement("http://www.w3.org/2000/09/xmldsig#", "Transforms");
/*  60 */         context.childAsURIs((JAXBObject)this._Transforms, "Transforms");
/*  61 */         context.endNamespaceDecls();
/*  62 */         context.childAsAttributes((JAXBObject)this._Transforms, "Transforms");
/*  63 */         context.endAttributes();
/*  64 */         context.childAsBody((JAXBObject)this._Transforms, "Transforms");
/*  65 */         context.endElement();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  73 */     if (this._Type != null) {
/*  74 */       context.startAttribute("", "Type");
/*     */       try {
/*  76 */         context.text(this._Type, "Type");
/*  77 */       } catch (Exception e) {
/*  78 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  80 */       context.endAttribute();
/*     */     } 
/*  82 */     if (this._URI != null) {
/*  83 */       context.startAttribute("", "URI");
/*     */       try {
/*  85 */         context.text(this._URI, "URI");
/*  86 */       } catch (Exception e) {
/*  87 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  89 */       context.endAttribute();
/*     */     } 
/*  91 */     if (this._Transforms != null && 
/*  92 */       this._Transforms instanceof javax.xml.bind.Element) {
/*  93 */       context.childAsAttributes((JAXBObject)this._Transforms, "Transforms");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 101 */     if (this._Transforms != null && 
/* 102 */       this._Transforms instanceof javax.xml.bind.Element) {
/* 103 */       context.childAsURIs((JAXBObject)this._Transforms, "Transforms");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 109 */     return RetrievalMethodType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 113 */     if (schemaFragment == null) {
/* 114 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsq\000~\000\007sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003q\000~\000\013p\000sq\000~\000\007ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003q\000~\000\013psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\rxq\000~\000\003q\000~\000\013psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\n\001q\000~\000\027sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\030q\000~\000\035sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\037xq\000~\000\032t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.Transformst\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\fq\000~\000\013p\000sq\000~\000\000ppsq\000~\000\fpp\000sq\000~\000\007ppsq\000~\000\021q\000~\000\013psq\000~\000\024q\000~\000\013pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformsTypeq\000~\000\"sq\000~\000\007ppsq\000~\000\024q\000~\000\013psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\037L\000\btypeNameq\000~\000\037L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\013psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\037L\000\fnamespaceURIq\000~\000\037xpq\000~\0008q\000~\0007sq\000~\000\036t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\035sq\000~\000\036t\000\nTransformst\000\"http://www.w3.org/2000/09/xmldsig#q\000~\000\035sq\000~\000\007ppsq\000~\000\024q\000~\000\013psq\000~\000-ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0002q\000~\0007t\000\006anyURIq\000~\000;q\000~\000=sq\000~\000>q\000~\000Kq\000~\0007sq\000~\000\036t\000\004Typet\000\000q\000~\000\035sq\000~\000\007ppsq\000~\000\024q\000~\000\013pq\000~\000Hsq\000~\000\036t\000\003URIq\000~\000Oq\000~\000\035sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\f\001pq\000~\000\005q\000~\000$q\000~\000\020q\000~\000&q\000~\000\023q\000~\000'q\000~\000Pq\000~\000\006q\000~\000+q\000~\000Fq\000~\000\bq\000~\000\tx");
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
/* 164 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final RetrievalMethodTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 173 */       super(context, "----------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 177 */       this(context);
/* 178 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 182 */       return RetrievalMethodTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 191 */         switch (this.state) {
/*     */           case 7:
/* 193 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 8:
/* 196 */             if ("Transform" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 197 */               RetrievalMethodTypeImpl.this._Transforms = (TransformsTypeImpl)spawnChildFromEnterElement((RetrievalMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl == null) ? (RetrievalMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl = RetrievalMethodTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformsTypeImpl")) : RetrievalMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 200 */             if ("Transform" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 201 */               RetrievalMethodTypeImpl.this._Transforms = (TransformsTypeImpl)spawnChildFromEnterElement((RetrievalMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl == null) ? (RetrievalMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl = RetrievalMethodTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformsTypeImpl")) : RetrievalMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsTypeImpl, 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 206 */             attIdx = this.context.getAttribute("", "URI");
/* 207 */             if (attIdx >= 0) {
/* 208 */               String v = this.context.eatAttribute(attIdx);
/* 209 */               this.state = 6;
/* 210 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 213 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 216 */             attIdx = this.context.getAttribute("", "Type");
/* 217 */             if (attIdx >= 0) {
/* 218 */               String v = this.context.eatAttribute(attIdx);
/* 219 */               this.state = 3;
/* 220 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 223 */             this.state = 3;
/*     */             continue;
/*     */           case 6:
/* 226 */             if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 227 */               RetrievalMethodTypeImpl.this._Transforms = (TransformsImpl)spawnChildFromEnterElement((RetrievalMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsImpl == null) ? (RetrievalMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsImpl = RetrievalMethodTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformsImpl")) : RetrievalMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformsImpl, 7, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 230 */             if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 231 */               this.context.pushAttributes(__atts, false);
/* 232 */               this.state = 8;
/*     */               return;
/*     */             } 
/* 235 */             this.state = 7; continue;
/*     */         }  break;
/*     */       } 
/* 238 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 247 */         RetrievalMethodTypeImpl.this._URI = WhiteSpaceProcessor.collapse(value);
/* 248 */       } catch (Exception e) {
/* 249 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 257 */         RetrievalMethodTypeImpl.this._Type = WhiteSpaceProcessor.collapse(value);
/* 258 */       } catch (Exception e) {
/* 259 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 269 */         switch (this.state) {
/*     */           case 9:
/* 271 */             if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 272 */               this.context.popAttributes();
/* 273 */               this.state = 7;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 7:
/* 278 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 281 */             attIdx = this.context.getAttribute("", "URI");
/* 282 */             if (attIdx >= 0) {
/* 283 */               String v = this.context.eatAttribute(attIdx);
/* 284 */               this.state = 6;
/* 285 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 288 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 291 */             attIdx = this.context.getAttribute("", "Type");
/* 292 */             if (attIdx >= 0) {
/* 293 */               String v = this.context.eatAttribute(attIdx);
/* 294 */               this.state = 3;
/* 295 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 298 */             this.state = 3;
/*     */             continue;
/*     */           case 6:
/* 301 */             this.state = 7; continue;
/*     */         }  break;
/*     */       } 
/* 304 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 315 */         switch (this.state) {
/*     */           case 7:
/* 317 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 320 */             if ("URI" == ___local && "" == ___uri) {
/* 321 */               this.state = 4;
/*     */               return;
/*     */             } 
/* 324 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 327 */             if ("Type" == ___local && "" == ___uri) {
/* 328 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 331 */             this.state = 3;
/*     */             continue;
/*     */           case 6:
/* 334 */             this.state = 7; continue;
/*     */         }  break;
/*     */       } 
/* 337 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 348 */         switch (this.state) {
/*     */           case 5:
/* 350 */             if ("URI" == ___local && "" == ___uri) {
/* 351 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 356 */             if ("Type" == ___local && "" == ___uri) {
/* 357 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 7:
/* 362 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 365 */             attIdx = this.context.getAttribute("", "URI");
/* 366 */             if (attIdx >= 0) {
/* 367 */               String v = this.context.eatAttribute(attIdx);
/* 368 */               this.state = 6;
/* 369 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 372 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 375 */             attIdx = this.context.getAttribute("", "Type");
/* 376 */             if (attIdx >= 0) {
/* 377 */               String v = this.context.eatAttribute(attIdx);
/* 378 */               this.state = 3;
/* 379 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 382 */             this.state = 3;
/*     */             continue;
/*     */           case 6:
/* 385 */             this.state = 7; continue;
/*     */         }  break;
/*     */       } 
/* 388 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 400 */           switch (this.state) {
/*     */             case 7:
/* 402 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 4:
/* 405 */               this.state = 5;
/* 406 */               eatText1(value);
/*     */               return;
/*     */             case 3:
/* 409 */               attIdx = this.context.getAttribute("", "URI");
/* 410 */               if (attIdx >= 0) {
/* 411 */                 String v = this.context.eatAttribute(attIdx);
/* 412 */                 this.state = 6;
/* 413 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 416 */               this.state = 6;
/*     */               continue;
/*     */             case 0:
/* 419 */               attIdx = this.context.getAttribute("", "Type");
/* 420 */               if (attIdx >= 0) {
/* 421 */                 String v = this.context.eatAttribute(attIdx);
/* 422 */                 this.state = 3;
/* 423 */                 eatText2(v);
/*     */                 continue;
/*     */               } 
/* 426 */               this.state = 3;
/*     */               continue;
/*     */             case 1:
/* 429 */               this.state = 2;
/* 430 */               eatText2(value);
/*     */               return;
/*     */             case 6:
/* 433 */               this.state = 7; continue;
/*     */           }  break;
/*     */         } 
/* 436 */       } catch (RuntimeException e) {
/* 437 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\RetrievalMethodTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */