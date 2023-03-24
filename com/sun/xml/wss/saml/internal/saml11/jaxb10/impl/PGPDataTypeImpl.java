/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.datatype.xsd.Base64BinaryType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.PGPDataType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class PGPDataTypeImpl implements PGPDataType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected ListImpl _Any;
/*  17 */   public static final Class version = JAXBVersion.class; protected byte[] _PGPKeyPacket; protected byte[] _PGPKeyID;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return PGPDataType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getAny() {
/*  25 */     if (this._Any == null) {
/*  26 */       this._Any = new ListImpl(new ArrayList());
/*     */     }
/*  28 */     return this._Any;
/*     */   }
/*     */   
/*     */   public List getAny() {
/*  32 */     return (List)_getAny();
/*     */   }
/*     */   
/*     */   public byte[] getPGPKeyPacket() {
/*  36 */     return this._PGPKeyPacket;
/*     */   }
/*     */   
/*     */   public void setPGPKeyPacket(byte[] value) {
/*  40 */     this._PGPKeyPacket = value;
/*     */   }
/*     */   
/*     */   public byte[] getPGPKeyID() {
/*  44 */     return this._PGPKeyID;
/*     */   }
/*     */   
/*     */   public void setPGPKeyID(byte[] value) {
/*  48 */     this._PGPKeyID = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  52 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  58 */     int idx1 = 0;
/*  59 */     int len1 = (this._Any == null) ? 0 : this._Any.size();
/*  60 */     if (this._PGPKeyID != null) {
/*  61 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "PGPKeyID");
/*  62 */       context.endNamespaceDecls();
/*  63 */       context.endAttributes();
/*     */       try {
/*  65 */         context.text(Base64BinaryType.save(this._PGPKeyID), "PGPKeyID");
/*  66 */       } catch (Exception e) {
/*  67 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  69 */       context.endElement();
/*  70 */       if (this._PGPKeyPacket != null) {
/*  71 */         context.startElement("http://www.w3.org/2000/09/xmldsig#", "PGPKeyPacket");
/*  72 */         context.endNamespaceDecls();
/*  73 */         context.endAttributes();
/*     */         try {
/*  75 */           context.text(Base64BinaryType.save(this._PGPKeyPacket), "PGPKeyPacket");
/*  76 */         } catch (Exception e) {
/*  77 */           Util.handlePrintConversionException(this, e, context);
/*     */         } 
/*  79 */         context.endElement();
/*     */       } 
/*  81 */       while (idx1 != len1) {
/*  82 */         context.childAsBody((JAXBObject)this._Any.get(idx1++), "Any");
/*     */       }
/*     */     }
/*  85 */     else if (this._PGPKeyPacket != null && this._PGPKeyID == null) {
/*  86 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "PGPKeyPacket");
/*  87 */       context.endNamespaceDecls();
/*  88 */       context.endAttributes();
/*     */       try {
/*  90 */         context.text(Base64BinaryType.save(this._PGPKeyPacket), "PGPKeyPacket");
/*  91 */       } catch (Exception e) {
/*  92 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  94 */       context.endElement();
/*  95 */       while (idx1 != len1) {
/*  96 */         context.childAsBody((JAXBObject)this._Any.get(idx1++), "Any");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 105 */     int idx1 = 0;
/* 106 */     int len1 = (this._Any == null) ? 0 : this._Any.size();
/* 107 */     if (this._PGPKeyID != null) {
/* 108 */       while (idx1 != len1) {
/* 109 */         idx1++;
/*     */       }
/*     */     }
/* 112 */     else if (this._PGPKeyPacket != null && this._PGPKeyID == null) {
/* 113 */       while (idx1 != len1) {
/* 114 */         idx1++;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 123 */     int idx1 = 0;
/* 124 */     int len1 = (this._Any == null) ? 0 : this._Any.size();
/* 125 */     if (this._PGPKeyID != null) {
/* 126 */       while (idx1 != len1) {
/* 127 */         idx1++;
/*     */       }
/*     */     }
/* 130 */     else if (this._PGPKeyPacket != null && this._PGPKeyID == null) {
/* 131 */       while (idx1 != len1) {
/* 132 */         idx1++;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 139 */     return PGPDataType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 143 */     if (schemaFragment == null) {
/* 144 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsq\000~\000\006ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\006ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000)com.sun.msv.datatype.xsd.Base64BinaryType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.BinaryBaseType§Î\016^¯W\021\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\027L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\fbase64Binarysr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\027L\000\fnamespaceURIq\000~\000\027xpq\000~\000\033q\000~\000\032sq\000~\000\000ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\nxq\000~\000\003q\000~\000\"psq\000~\000\016ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\024q\000~\000\032t\000\005QNameq\000~\000\036q\000~\000 sq\000~\000#q\000~\000+q\000~\000\032sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\027L\000\fnamespaceURIq\000~\000\027xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000!\001q\000~\0003sq\000~\000-t\000\bPGPKeyIDt\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\000ppsq\000~\000\tq\000~\000\"p\000sq\000~\000\006ppq\000~\000\021sq\000~\000\000ppsq\000~\000&q\000~\000\"pq\000~\000(q\000~\000/q\000~\0003sq\000~\000-t\000\fPGPKeyPacketq\000~\0007q\000~\0003sq\000~\000\000ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003q\000~\000\"psq\000~\000\tq\000~\000\"p\000sq\000~\000&ppsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\0004q\000~\000Fsr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\nL\000\003nc2q\000~\000\nxq\000~\000.sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000.sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\nL\000\003nc2q\000~\000\nxq\000~\000.sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIq\000~\000\027xq\000~\000.t\000\000sq\000~\000Mq\000~\0007sq\000~\000Mt\000+http://java.sun.com/jaxb/xjc/dummy-elementsq\000~\0003sq\000~\000\006ppsq\000~\000\tpp\000sq\000~\000\006ppq\000~\000\021sq\000~\000\000ppsq\000~\000&q\000~\000\"pq\000~\000(q\000~\000/q\000~\0003sq\000~\000-t\000\fPGPKeyPacketq\000~\0007sq\000~\000\000ppsq\000~\000@q\000~\000\"psq\000~\000\tq\000~\000\"p\000sq\000~\000&ppq\000~\000Fsq\000~\000Gq\000~\000Jsq\000~\000Ksq\000~\000Mq\000~\000Osq\000~\000Mq\000~\0007sq\000~\000Mq\000~\000Rq\000~\0003sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\017\001pq\000~\000Zq\000~\000?q\000~\000\007q\000~\000\005q\000~\000Bq\000~\000[q\000~\0008q\000~\000%q\000~\000;q\000~\000Vq\000~\000Sq\000~\000\rq\000~\000:q\000~\000Uq\000~\000\bx");
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
/* 199 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final PGPDataTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 208 */       super(context, "-----------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 212 */       this(context);
/* 213 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 217 */       return PGPDataTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/* 226 */         switch (this.state) {
/*     */           case 0:
/* 228 */             if ("PGPKeyID" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 229 */               this.context.pushAttributes(__atts, true);
/* 230 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 233 */             if ("PGPKeyPacket" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 234 */               this.context.pushAttributes(__atts, true);
/* 235 */               this.state = 8;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 7:
/* 240 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 241 */               Object co = spawnWildcard(7, ___uri, ___local, ___qname, __atts);
/* 242 */               if (co != null) {
/* 243 */                 PGPDataTypeImpl.this._getAny().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 247 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 248 */               Object co = spawnWildcard(7, ___uri, ___local, ___qname, __atts);
/* 249 */               if (co != null) {
/* 250 */                 PGPDataTypeImpl.this._getAny().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 254 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 10:
/* 257 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 258 */               Object co = spawnWildcard(7, ___uri, ___local, ___qname, __atts);
/* 259 */               if (co != null) {
/* 260 */                 PGPDataTypeImpl.this._getAny().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 264 */             this.state = 7;
/*     */             continue;
/*     */           case 6:
/* 267 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 268 */               Object co = spawnWildcard(7, ___uri, ___local, ___qname, __atts);
/* 269 */               if (co != null) {
/* 270 */                 PGPDataTypeImpl.this._getAny().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 274 */             this.state = 7;
/*     */             continue;
/*     */           case 3:
/* 277 */             if ("PGPKeyPacket" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 278 */               this.context.pushAttributes(__atts, true);
/* 279 */               this.state = 4;
/*     */               return;
/*     */             } 
/* 282 */             this.state = 6; continue;
/*     */         }  break;
/*     */       } 
/* 285 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/* 296 */         switch (this.state) {
/*     */           case 2:
/* 298 */             if ("PGPKeyID" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 299 */               this.context.popAttributes();
/* 300 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 305 */             if ("PGPKeyPacket" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 306 */               this.context.popAttributes();
/* 307 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 9:
/* 312 */             if ("PGPKeyPacket" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 313 */               this.context.popAttributes();
/* 314 */               this.state = 10;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 7:
/* 319 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 10:
/* 322 */             this.state = 7;
/*     */             continue;
/*     */           case 6:
/* 325 */             this.state = 7;
/*     */             continue;
/*     */           case 3:
/* 328 */             this.state = 6; continue;
/*     */         }  break;
/*     */       } 
/* 331 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 342 */         switch (this.state) {
/*     */           case 7:
/* 344 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 10:
/* 347 */             this.state = 7;
/*     */             continue;
/*     */           case 6:
/* 350 */             this.state = 7;
/*     */             continue;
/*     */           case 3:
/* 353 */             this.state = 6; continue;
/*     */         }  break;
/*     */       } 
/* 356 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/* 367 */         switch (this.state) {
/*     */           case 7:
/* 369 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 10:
/* 372 */             this.state = 7;
/*     */             continue;
/*     */           case 6:
/* 375 */             this.state = 7;
/*     */             continue;
/*     */           case 3:
/* 378 */             this.state = 6; continue;
/*     */         }  break;
/*     */       } 
/* 381 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/*     */         while (true) {
/* 393 */           switch (this.state) {
/*     */             case 8:
/* 395 */               this.state = 9;
/* 396 */               eatText1(value);
/*     */               return;
/*     */             case 7:
/* 399 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 10:
/* 402 */               this.state = 7;
/*     */               continue;
/*     */             case 4:
/* 405 */               this.state = 5;
/* 406 */               eatText2(value);
/*     */               return;
/*     */             case 6:
/* 409 */               this.state = 7;
/*     */               continue;
/*     */             case 3:
/* 412 */               this.state = 6;
/*     */               continue;
/*     */             case 1:
/* 415 */               this.state = 2;
/* 416 */               eatText3(value); return;
/*     */           }  break;
/*     */         } 
/* 419 */       } catch (RuntimeException e) {
/* 420 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 430 */         PGPDataTypeImpl.this._PGPKeyPacket = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 431 */       } catch (Exception e) {
/* 432 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 440 */         PGPDataTypeImpl.this._PGPKeyPacket = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 441 */       } catch (Exception e) {
/* 442 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText3(String value) throws SAXException {
/*     */       try {
/* 450 */         PGPDataTypeImpl.this._PGPKeyID = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 451 */       } catch (Exception e) {
/* 452 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\PGPDataTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */