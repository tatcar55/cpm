/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class TransformTypeImpl implements TransformType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _Algorithm;
/*  16 */   public static final Class version = JAXBVersion.class; protected ListImpl _Content; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformTypeImpl$XPathImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$TransformType$XPath;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return TransformType.class;
/*     */   }
/*     */   
/*     */   public String getAlgorithm() {
/*  24 */     return this._Algorithm;
/*     */   }
/*     */   
/*     */   public void setAlgorithm(String value) {
/*  28 */     this._Algorithm = value;
/*     */   }
/*     */   
/*     */   protected ListImpl _getContent() {
/*  32 */     if (this._Content == null) {
/*  33 */       this._Content = new ListImpl(new ArrayList());
/*     */     }
/*  35 */     return this._Content;
/*     */   }
/*     */   
/*     */   public List getContent() {
/*  39 */     return (List)_getContent();
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  43 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  49 */     int idx2 = 0;
/*  50 */     int len2 = (this._Content == null) ? 0 : this._Content.size();
/*  51 */     while (idx2 != len2) {
/*     */       
/*  53 */       Object o = this._Content.get(idx2);
/*  54 */       if (o instanceof JAXBObject) {
/*  55 */         context.childAsBody((JAXBObject)this._Content.get(idx2++), "Content"); continue;
/*     */       } 
/*  57 */       if (o instanceof String) {
/*     */         try {
/*  59 */           context.text((String)this._Content.get(idx2++), "Content");
/*  60 */         } catch (Exception e) {
/*  61 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/*  64 */       if (o instanceof Object) {
/*  65 */         context.childAsBody((JAXBObject)this._Content.get(idx2++), "Content"); continue;
/*     */       } 
/*  67 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  78 */     int idx2 = 0;
/*  79 */     int len2 = (this._Content == null) ? 0 : this._Content.size();
/*  80 */     context.startAttribute("", "Algorithm");
/*     */     try {
/*  82 */       context.text(this._Algorithm, "Algorithm");
/*  83 */     } catch (Exception e) {
/*  84 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  86 */     context.endAttribute();
/*  87 */     while (idx2 != len2) {
/*     */       
/*  89 */       Object o = this._Content.get(idx2);
/*  90 */       if (o instanceof JAXBObject) {
/*  91 */         context.childAsAttributes((JAXBObject)this._Content.get(idx2++), "Content"); continue;
/*     */       } 
/*  93 */       if (o instanceof String) {
/*     */         try {
/*  95 */           idx2++;
/*  96 */         } catch (Exception e) {
/*  97 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/* 100 */       if (o instanceof Object) {
/* 101 */         idx2++; continue;
/*     */       } 
/* 103 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 114 */     int idx2 = 0;
/* 115 */     int len2 = (this._Content == null) ? 0 : this._Content.size();
/* 116 */     while (idx2 != len2) {
/*     */       
/* 118 */       Object o = this._Content.get(idx2);
/* 119 */       if (o instanceof JAXBObject) {
/* 120 */         context.childAsURIs((JAXBObject)this._Content.get(idx2++), "Content"); continue;
/*     */       } 
/* 122 */       if (o instanceof String) {
/*     */         try {
/* 124 */           idx2++;
/* 125 */         } catch (Exception e) {
/* 126 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/* 129 */       if (o instanceof Object) {
/* 130 */         idx2++; continue;
/*     */       } 
/* 132 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 141 */     return TransformType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 145 */     if (schemaFragment == null) {
/* 146 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\007sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\tq\000~\000\016psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003q\000~\000\016p\000sr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\021xq\000~\000\003ppsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\r\001q\000~\000\027sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\021L\000\003nc2q\000~\000\021xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\032sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\021L\000\003nc2q\000~\000\021xq\000~\000\032sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIt\000\022Ljava/lang/String;xq\000~\000\032t\000\000sq\000~\000 t\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000 t\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\020q\000~\000\016p\000sq\000~\000\tppsq\000~\000\013q\000~\000\016psq\000~\000\024q\000~\000\016pq\000~\000\027q\000~\000\035sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\030q\000~\000-sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000!L\000\fnamespaceURIq\000~\000!xq\000~\000\032t\000?com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformType.XPathq\000~\000'q\000~\000-sq\000~\000\024ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000!L\000\btypeNameq\000~\000!L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\016psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000!L\000\fnamespaceURIq\000~\000!xpq\000~\000=q\000~\000<sq\000~\000.t\000\tAlgorithmq\000~\000#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\007\001pq\000~\000\nq\000~\000)q\000~\000\017q\000~\000*q\000~\000\bq\000~\000\005q\000~\000\fx");
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
/* 195 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final TransformTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 204 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 208 */       this(context);
/* 209 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 213 */       return TransformTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 222 */         switch (this.state) {
/*     */           case 0:
/* 224 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 225 */             if (attIdx >= 0) {
/* 226 */               String v = this.context.eatAttribute(attIdx);
/* 227 */               this.state = 3;
/* 228 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 233 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 234 */               Object co = spawnWildcard(3, ___uri, ___local, ___qname, __atts);
/* 235 */               if (co != null) {
/* 236 */                 TransformTypeImpl.this._getContent().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 240 */             if ("XPath" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 241 */               TransformTypeImpl.this._getContent().add(spawnChildFromEnterElement((TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformTypeImpl$XPathImpl == null) ? (TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformTypeImpl$XPathImpl = TransformTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformTypeImpl$XPathImpl")) : TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformTypeImpl$XPathImpl, 3, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 244 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts); return;
/*     */         }  break;
/*     */       } 
/* 247 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 256 */         TransformTypeImpl.this._Algorithm = WhiteSpaceProcessor.collapse(value);
/* 257 */       } catch (Exception e) {
/* 258 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 268 */         switch (this.state) {
/*     */           case 0:
/* 270 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 271 */             if (attIdx >= 0) {
/* 272 */               String v = this.context.eatAttribute(attIdx);
/* 273 */               this.state = 3;
/* 274 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 279 */             revertToParentFromLeaveElement(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 282 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 293 */       switch (this.state) {
/*     */         case 0:
/* 295 */           if ("Algorithm" == ___local && "" == ___uri) {
/* 296 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 301 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 304 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 315 */         switch (this.state) {
/*     */           case 0:
/* 317 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 318 */             if (attIdx >= 0) {
/* 319 */               String v = this.context.eatAttribute(attIdx);
/* 320 */               this.state = 3;
/* 321 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 326 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 2:
/* 329 */             if ("Algorithm" == ___local && "" == ___uri) {
/* 330 */               this.state = 3; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 335 */         super.leaveAttribute(___uri, ___local, ___qname); return; }  super.leaveAttribute(___uri, ___local, ___qname);
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
/* 347 */           switch (this.state) {
/*     */             case 0:
/* 349 */               attIdx = this.context.getAttribute("", "Algorithm");
/* 350 */               if (attIdx >= 0) {
/* 351 */                 String v = this.context.eatAttribute(attIdx);
/* 352 */                 this.state = 3;
/* 353 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */             case 3:
/* 358 */               this.state = 3;
/* 359 */               eatText2(value);
/*     */               return;
/*     */             case 1:
/* 362 */               this.state = 2;
/* 363 */               eatText1(value); return;
/*     */           }  break;
/*     */         } 
/* 366 */       } catch (RuntimeException e) {
/* 367 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 377 */         TransformTypeImpl.this._getContent().add(value);
/* 378 */       } catch (Exception e) {
/* 379 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class XPathImpl
/*     */     implements TransformType.XPath, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject
/*     */   {
/*     */     protected String _Value;
/* 389 */     public static final Class version = (TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion == null) ? (TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion = TransformTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.JAXBVersion")) : TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion;
/*     */     
/*     */     private static Grammar schemaFragment;
/*     */     
/*     */     public XPathImpl() {}
/*     */     
/*     */     public XPathImpl(String value) {
/* 396 */       this._Value = value;
/*     */     }
/*     */     
/*     */     private static final Class PRIMARY_INTERFACE_CLASS() {
/* 400 */       return (TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$TransformType$XPath == null) ? (TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$TransformType$XPath = TransformTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformType$XPath")) : TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$TransformType$XPath;
/*     */     }
/*     */     
/*     */     public String ____jaxb_ri____getNamespaceURI() {
/* 404 */       return "http://www.w3.org/2000/09/xmldsig#";
/*     */     }
/*     */     
/*     */     public String ____jaxb_ri____getLocalName() {
/* 408 */       return "XPath";
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 412 */       return this._Value;
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 416 */       this._Value = value;
/*     */     }
/*     */     
/*     */     public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/* 420 */       return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeBody(XMLSerializer context) throws SAXException {
/* 426 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "XPath");
/* 427 */       context.endNamespaceDecls();
/* 428 */       context.endAttributes();
/*     */       try {
/* 430 */         context.text(this._Value, "Value");
/* 431 */       } catch (Exception e) {
/* 432 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 434 */       context.endElement();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeURIs(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Class getPrimaryInterface() {
/* 448 */       return (TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$TransformType$XPath == null) ? (TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$TransformType$XPath = TransformTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformType$XPath")) : TransformTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$TransformType$XPath;
/*     */     }
/*     */     
/*     */     public DocumentDeclaration createRawValidator() {
/* 452 */       if (schemaFragment == null) {
/* 453 */         schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\024L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\017psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\024L\000\fnamespaceURIq\000~\000\024xpq\000~\000\030q\000~\000\027sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\017psq\000~\000\nppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\021q\000~\000\027t\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\032q\000~\000\035sq\000~\000\036q\000~\000'q\000~\000\027sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\024L\000\fnamespaceURIq\000~\000\024xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\016\001q\000~\0001sq\000~\000+t\000\005XPatht\000\"http://www.w3.org/2000/09/xmldsig#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\002\001pq\000~\000\tq\000~\000!x");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 493 */       return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */     }
/*     */     
/*     */     public class Unmarshaller
/*     */       extends AbstractUnmarshallingEventHandlerImpl
/*     */     {
/*     */       private final TransformTypeImpl.XPathImpl this$0;
/*     */       
/*     */       public Unmarshaller(UnmarshallingContext context) {
/* 502 */         super(context, "----");
/*     */       }
/*     */       
/*     */       protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 506 */         this(context);
/* 507 */         this.state = startState;
/*     */       }
/*     */       
/*     */       public Object owner() {
/* 511 */         return TransformTypeImpl.XPathImpl.this;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 520 */         switch (this.state) {
/*     */           case 3:
/* 522 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 0:
/* 525 */             if ("XPath" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 526 */               this.context.pushAttributes(__atts, true);
/* 527 */               this.state = 1;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */         } 
/* 532 */         super.enterElement(___uri, ___local, ___qname, __atts);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/* 543 */         switch (this.state) {
/*     */           case 3:
/* 545 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 2:
/* 548 */             if ("XPath" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 549 */               this.context.popAttributes();
/* 550 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */         } 
/* 555 */         super.leaveElement(___uri, ___local, ___qname);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 566 */         switch (this.state) {
/*     */           case 3:
/* 568 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */         } 
/* 571 */         super.enterAttribute(___uri, ___local, ___qname);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 582 */         switch (this.state) {
/*     */           case 3:
/* 584 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */         } 
/* 587 */         super.leaveAttribute(___uri, ___local, ___qname);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void handleText(String value) throws SAXException {
/*     */         try {
/* 599 */           switch (this.state) {
/*     */             case 3:
/* 601 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 1:
/* 604 */               this.state = 2;
/* 605 */               eatText1(value);
/*     */               return;
/*     */           } 
/* 608 */         } catch (RuntimeException e) {
/* 609 */           handleUnexpectedTextException(value, e);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private void eatText1(String value) throws SAXException {
/*     */         try {
/* 619 */           TransformTypeImpl.XPathImpl.this._Value = value;
/* 620 */         } catch (Exception e) {
/* 621 */           handleParseConversionException(e);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\TransformTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */