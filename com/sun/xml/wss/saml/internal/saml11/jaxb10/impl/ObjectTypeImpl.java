/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.ObjectType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ObjectTypeImpl implements ObjectType, JAXBObject, UnmarshallableObject, XMLSerializable, IdentifiableObject, ValidatableObject {
/*     */   protected String _MimeType;
/*     */   protected String _Encoding;
/*  18 */   public static final Class version = JAXBVersion.class; protected ListImpl _Content; protected String _Id;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  22 */     return ObjectType.class;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/*  26 */     return this._MimeType;
/*     */   }
/*     */   
/*     */   public void setMimeType(String value) {
/*  30 */     this._MimeType = value;
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/*  34 */     return this._Encoding;
/*     */   }
/*     */   
/*     */   public void setEncoding(String value) {
/*  38 */     this._Encoding = value;
/*     */   }
/*     */   
/*     */   protected ListImpl _getContent() {
/*  42 */     if (this._Content == null) {
/*  43 */       this._Content = new ListImpl(new ArrayList());
/*     */     }
/*  45 */     return this._Content;
/*     */   }
/*     */   
/*     */   public List getContent() {
/*  49 */     return (List)_getContent();
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
/*  68 */     int len3 = (this._Content == null) ? 0 : this._Content.size();
/*  69 */     while (idx3 != len3) {
/*     */       
/*  71 */       Object o = this._Content.get(idx3);
/*  72 */       if (o instanceof String) {
/*     */         try {
/*  74 */           context.text((String)this._Content.get(idx3++), "Content");
/*  75 */         } catch (Exception e) {
/*  76 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/*  79 */       if (o instanceof Object) {
/*  80 */         context.childAsBody((JAXBObject)this._Content.get(idx3++), "Content"); continue;
/*     */       } 
/*  82 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  92 */     int idx3 = 0;
/*  93 */     int len3 = (this._Content == null) ? 0 : this._Content.size();
/*  94 */     if (this._Encoding != null) {
/*  95 */       context.startAttribute("", "Encoding");
/*     */       try {
/*  97 */         context.text(this._Encoding, "Encoding");
/*  98 */       } catch (Exception e) {
/*  99 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 101 */       context.endAttribute();
/*     */     } 
/* 103 */     if (this._Id != null) {
/* 104 */       context.startAttribute("", "Id");
/*     */       try {
/* 106 */         context.text(context.onID(this, this._Id), "Id");
/* 107 */       } catch (Exception e) {
/* 108 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 110 */       context.endAttribute();
/*     */     } 
/* 112 */     if (this._MimeType != null) {
/* 113 */       context.startAttribute("", "MimeType");
/*     */       try {
/* 115 */         context.text(this._MimeType, "MimeType");
/* 116 */       } catch (Exception e) {
/* 117 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 119 */       context.endAttribute();
/*     */     } 
/* 121 */     while (idx3 != len3) {
/*     */       
/* 123 */       Object o = this._Content.get(idx3);
/* 124 */       if (o instanceof String) {
/*     */         try {
/* 126 */           idx3++;
/* 127 */         } catch (Exception e) {
/* 128 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/* 131 */       if (o instanceof Object) {
/* 132 */         idx3++; continue;
/*     */       } 
/* 134 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 144 */     int idx3 = 0;
/* 145 */     int len3 = (this._Content == null) ? 0 : this._Content.size();
/* 146 */     while (idx3 != len3) {
/*     */       
/* 148 */       Object o = this._Content.get(idx3);
/* 149 */       if (o instanceof String) {
/*     */         try {
/* 151 */           idx3++;
/* 152 */         } catch (Exception e) {
/* 153 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/* 156 */       if (o instanceof Object) {
/* 157 */         idx3++; continue;
/*     */       } 
/* 159 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String ____jaxb____getId() {
/* 167 */     return this._Id;
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 171 */     return ObjectType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 175 */     if (schemaFragment == null) {
/* 176 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsq\000~\000\000ppsr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\tsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003q\000~\000\020p\000sr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\022xq\000~\000\003ppsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\017\001q\000~\000\030sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIt\000\022Ljava/lang/String;xq\000~\000\033t\000+http://java.sun.com/jaxb/xjc/dummy-elementssr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\031q\000~\000\"sq\000~\000\013ppsq\000~\000\025q\000~\000\020psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\036L\000\btypeNameq\000~\000\036L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\020psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\036L\000\fnamespaceURIq\000~\000\036xpq\000~\0000q\000~\000/sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\036L\000\fnamespaceURIq\000~\000\036xq\000~\000\033t\000\bEncodingt\000\000q\000~\000\"sq\000~\000\013ppsq\000~\000\025q\000~\000\020psq\000~\000%ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\000*q\000~\000/t\000\002IDq\000~\0003\000q\000~\0005sq\000~\0006q\000~\000Dq\000~\000/sq\000~\0008t\000\002Idq\000~\000;q\000~\000\"sq\000~\000\013ppsq\000~\000\025q\000~\000\020psq\000~\000%q\000~\000\020psq\000~\000Bq\000~\000/t\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xq\000~\0002\001q\000~\0005sq\000~\0006q\000~\000Lq\000~\000/sq\000~\0008t\000\bMimeTypeq\000~\000;q\000~\000\"sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\t\001pq\000~\000Hq\000~\000\016q\000~\000\006q\000~\000\fq\000~\000#q\000~\000<q\000~\000\nq\000~\000\007q\000~\000\005x");
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
/* 227 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final ObjectTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 236 */       super(context, "----------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 240 */       this(context);
/* 241 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 245 */       return ObjectTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/*     */         Object co;
/* 254 */         switch (this.state) {
/*     */           
/*     */           case 9:
/* 257 */             co = spawnWildcard(9, ___uri, ___local, ___qname, __atts);
/* 258 */             if (co != null) {
/* 259 */               ObjectTypeImpl.this._getContent().add(co);
/*     */             }
/*     */             return;
/*     */ 
/*     */ 
/*     */           
/*     */           case 3:
/* 266 */             attIdx = this.context.getAttribute("", "Id");
/* 267 */             if (attIdx >= 0) {
/* 268 */               String v = this.context.eatAttribute(attIdx);
/* 269 */               this.state = 6;
/* 270 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 273 */             this.state = 6;
/*     */             continue;
/*     */           case 6:
/* 276 */             attIdx = this.context.getAttribute("", "MimeType");
/* 277 */             if (attIdx >= 0) {
/* 278 */               String v = this.context.eatAttribute(attIdx);
/* 279 */               this.state = 9;
/* 280 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 283 */             this.state = 9;
/*     */             continue;
/*     */           case 0:
/* 286 */             attIdx = this.context.getAttribute("", "Encoding");
/* 287 */             if (attIdx >= 0) {
/* 288 */               String v = this.context.eatAttribute(attIdx);
/* 289 */               this.state = 3;
/* 290 */               eatText3(v);
/*     */               continue;
/*     */             } 
/* 293 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 296 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 305 */         ObjectTypeImpl.this._Id = this.context.addToIdTable(WhiteSpaceProcessor.collapse(value));
/* 306 */       } catch (Exception e) {
/* 307 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 315 */         ObjectTypeImpl.this._MimeType = value;
/* 316 */       } catch (Exception e) {
/* 317 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText3(String value) throws SAXException {
/*     */       try {
/* 325 */         ObjectTypeImpl.this._Encoding = WhiteSpaceProcessor.collapse(value);
/* 326 */       } catch (Exception e) {
/* 327 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 337 */         switch (this.state) {
/*     */           case 9:
/* 339 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 342 */             attIdx = this.context.getAttribute("", "Id");
/* 343 */             if (attIdx >= 0) {
/* 344 */               String v = this.context.eatAttribute(attIdx);
/* 345 */               this.state = 6;
/* 346 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 349 */             this.state = 6;
/*     */             continue;
/*     */           case 6:
/* 352 */             attIdx = this.context.getAttribute("", "MimeType");
/* 353 */             if (attIdx >= 0) {
/* 354 */               String v = this.context.eatAttribute(attIdx);
/* 355 */               this.state = 9;
/* 356 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 359 */             this.state = 9;
/*     */             continue;
/*     */           case 0:
/* 362 */             attIdx = this.context.getAttribute("", "Encoding");
/* 363 */             if (attIdx >= 0) {
/* 364 */               String v = this.context.eatAttribute(attIdx);
/* 365 */               this.state = 3;
/* 366 */               eatText3(v);
/*     */               continue;
/*     */             } 
/* 369 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 372 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 383 */         switch (this.state) {
/*     */           case 9:
/* 385 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 388 */             if ("Id" == ___local && "" == ___uri) {
/* 389 */               this.state = 4;
/*     */               return;
/*     */             } 
/* 392 */             this.state = 6;
/*     */             continue;
/*     */           case 6:
/* 395 */             if ("MimeType" == ___local && "" == ___uri) {
/* 396 */               this.state = 7;
/*     */               return;
/*     */             } 
/* 399 */             this.state = 9;
/*     */             continue;
/*     */           case 0:
/* 402 */             if ("Encoding" == ___local && "" == ___uri) {
/* 403 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 406 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 409 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 420 */         switch (this.state) {
/*     */           case 9:
/* 422 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 425 */             attIdx = this.context.getAttribute("", "Id");
/* 426 */             if (attIdx >= 0) {
/* 427 */               String v = this.context.eatAttribute(attIdx);
/* 428 */               this.state = 6;
/* 429 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 432 */             this.state = 6;
/*     */             continue;
/*     */           case 8:
/* 435 */             if ("MimeType" == ___local && "" == ___uri) {
/* 436 */               this.state = 9;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 441 */             if ("Id" == ___local && "" == ___uri) {
/* 442 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 447 */             attIdx = this.context.getAttribute("", "MimeType");
/* 448 */             if (attIdx >= 0) {
/* 449 */               String v = this.context.eatAttribute(attIdx);
/* 450 */               this.state = 9;
/* 451 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 454 */             this.state = 9;
/*     */             continue;
/*     */           case 0:
/* 457 */             attIdx = this.context.getAttribute("", "Encoding");
/* 458 */             if (attIdx >= 0) {
/* 459 */               String v = this.context.eatAttribute(attIdx);
/* 460 */               this.state = 3;
/* 461 */               eatText3(v);
/*     */               continue;
/*     */             } 
/* 464 */             this.state = 3;
/*     */             continue;
/*     */           case 2:
/* 467 */             if ("Encoding" == ___local && "" == ___uri) {
/* 468 */               this.state = 3; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 473 */         super.leaveAttribute(___uri, ___local, ___qname); return; }  super.leaveAttribute(___uri, ___local, ___qname);
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
/* 485 */           switch (this.state) {
/*     */             case 9:
/* 487 */               this.state = 9;
/* 488 */               eatText4(value);
/*     */               return;
/*     */             case 7:
/* 491 */               this.state = 8;
/* 492 */               eatText2(value);
/*     */               return;
/*     */             case 4:
/* 495 */               this.state = 5;
/* 496 */               eatText1(value);
/*     */               return;
/*     */             case 3:
/* 499 */               attIdx = this.context.getAttribute("", "Id");
/* 500 */               if (attIdx >= 0) {
/* 501 */                 String v = this.context.eatAttribute(attIdx);
/* 502 */                 this.state = 6;
/* 503 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 506 */               this.state = 6;
/*     */               continue;
/*     */             case 1:
/* 509 */               this.state = 2;
/* 510 */               eatText3(value);
/*     */               return;
/*     */             case 6:
/* 513 */               attIdx = this.context.getAttribute("", "MimeType");
/* 514 */               if (attIdx >= 0) {
/* 515 */                 String v = this.context.eatAttribute(attIdx);
/* 516 */                 this.state = 9;
/* 517 */                 eatText2(v);
/*     */                 continue;
/*     */               } 
/* 520 */               this.state = 9;
/*     */               continue;
/*     */             case 0:
/* 523 */               attIdx = this.context.getAttribute("", "Encoding");
/* 524 */               if (attIdx >= 0) {
/* 525 */                 String v = this.context.eatAttribute(attIdx);
/* 526 */                 this.state = 3;
/* 527 */                 eatText3(v);
/*     */                 continue;
/*     */               } 
/* 530 */               this.state = 3; continue;
/*     */           }  break;
/*     */         } 
/* 533 */       } catch (RuntimeException e) {
/* 534 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText4(String value) throws SAXException {
/*     */       try {
/* 544 */         ObjectTypeImpl.this._getContent().add(value);
/* 545 */       } catch (Exception e) {
/* 546 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\ObjectTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */