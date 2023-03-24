/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.datatype.xsd.Base64BinaryType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.DSAKeyValueType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class DSAKeyValueTypeImpl implements DSAKeyValueType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected byte[] _Seed;
/*     */   protected byte[] _J;
/*     */   protected byte[] _Y;
/*  21 */   public static final Class version = JAXBVersion.class; protected byte[] _P; protected byte[] _Q; protected byte[] _PgenCounter; protected byte[] _G;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  25 */     return DSAKeyValueType.class;
/*     */   }
/*     */   
/*     */   public byte[] getSeed() {
/*  29 */     return this._Seed;
/*     */   }
/*     */   
/*     */   public void setSeed(byte[] value) {
/*  33 */     this._Seed = value;
/*     */   }
/*     */   
/*     */   public byte[] getJ() {
/*  37 */     return this._J;
/*     */   }
/*     */   
/*     */   public void setJ(byte[] value) {
/*  41 */     this._J = value;
/*     */   }
/*     */   
/*     */   public byte[] getY() {
/*  45 */     return this._Y;
/*     */   }
/*     */   
/*     */   public void setY(byte[] value) {
/*  49 */     this._Y = value;
/*     */   }
/*     */   
/*     */   public byte[] getP() {
/*  53 */     return this._P;
/*     */   }
/*     */   
/*     */   public void setP(byte[] value) {
/*  57 */     this._P = value;
/*     */   }
/*     */   
/*     */   public byte[] getQ() {
/*  61 */     return this._Q;
/*     */   }
/*     */   
/*     */   public void setQ(byte[] value) {
/*  65 */     this._Q = value;
/*     */   }
/*     */   
/*     */   public byte[] getPgenCounter() {
/*  69 */     return this._PgenCounter;
/*     */   }
/*     */   
/*     */   public void setPgenCounter(byte[] value) {
/*  73 */     this._PgenCounter = value;
/*     */   }
/*     */   
/*     */   public byte[] getG() {
/*  77 */     return this._G;
/*     */   }
/*     */   
/*     */   public void setG(byte[] value) {
/*  81 */     this._G = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  85 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  91 */     if (this._Q != null && this._P != null) {
/*  92 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "P");
/*  93 */       context.endNamespaceDecls();
/*  94 */       context.endAttributes();
/*     */       try {
/*  96 */         context.text(Base64BinaryType.save(this._P), "P");
/*  97 */       } catch (Exception e) {
/*  98 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 100 */       context.endElement();
/* 101 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "Q");
/* 102 */       context.endNamespaceDecls();
/* 103 */       context.endAttributes();
/*     */       try {
/* 105 */         context.text(Base64BinaryType.save(this._Q), "Q");
/* 106 */       } catch (Exception e) {
/* 107 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 109 */       context.endElement();
/*     */     } 
/* 111 */     if (this._G != null) {
/* 112 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "G");
/* 113 */       context.endNamespaceDecls();
/* 114 */       context.endAttributes();
/*     */       try {
/* 116 */         context.text(Base64BinaryType.save(this._G), "G");
/* 117 */       } catch (Exception e) {
/* 118 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 120 */       context.endElement();
/*     */     } 
/* 122 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "Y");
/* 123 */     context.endNamespaceDecls();
/* 124 */     context.endAttributes();
/*     */     try {
/* 126 */       context.text(Base64BinaryType.save(this._Y), "Y");
/* 127 */     } catch (Exception e) {
/* 128 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 130 */     context.endElement();
/* 131 */     if (this._J != null) {
/* 132 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "J");
/* 133 */       context.endNamespaceDecls();
/* 134 */       context.endAttributes();
/*     */       try {
/* 136 */         context.text(Base64BinaryType.save(this._J), "J");
/* 137 */       } catch (Exception e) {
/* 138 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 140 */       context.endElement();
/*     */     } 
/* 142 */     if (this._PgenCounter != null && this._Seed != null) {
/* 143 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "Seed");
/* 144 */       context.endNamespaceDecls();
/* 145 */       context.endAttributes();
/*     */       try {
/* 147 */         context.text(Base64BinaryType.save(this._Seed), "Seed");
/* 148 */       } catch (Exception e) {
/* 149 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 151 */       context.endElement();
/* 152 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "PgenCounter");
/* 153 */       context.endNamespaceDecls();
/* 154 */       context.endAttributes();
/*     */       try {
/* 156 */         context.text(Base64BinaryType.save(this._PgenCounter), "PgenCounter");
/* 157 */       } catch (Exception e) {
/* 158 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 160 */       context.endElement();
/*     */     } 
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
/* 175 */     return DSAKeyValueType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 179 */     if (schemaFragment == null) {
/* 180 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsq\000~\000\000ppsq\000~\000\000ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsq\000~\000\000sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003q\000~\000\rp\000sq\000~\000\000ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000'com.sun.msv.datatype.xsd.FinalComponent\000\000\000\000\000\000\000\001\002\000\001I\000\nfinalValuexr\000\036com.sun.msv.datatype.xsd.Proxy\000\000\000\000\000\000\000\001\002\000\001L\000\bbaseTypet\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\033L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000\"http://www.w3.org/2000/09/xmldsig#t\000\fCryptoBinarysr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\000)com.sun.msv.datatype.xsd.Base64BinaryType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.BinaryBaseType§Î\016^¯W\021\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\032t\000 http://www.w3.org/2001/XMLSchemat\000\fbase64Binaryq\000~\000\"\000\000\000\000sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\rpsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\033L\000\fnamespaceURIq\000~\000\033xpq\000~\000)q\000~\000\036sq\000~\000\tppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\017xq\000~\000\003q\000~\000\rpsq\000~\000\023ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000%q\000~\000(t\000\005QNameq\000~\000\"q\000~\000+sq\000~\000,q\000~\0004q\000~\000(sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\033L\000\fnamespaceURIq\000~\000\033xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\f\001q\000~\000<sq\000~\0006t\000\001Pq\000~\000\036sq\000~\000\016pp\000sq\000~\000\000ppq\000~\000\026sq\000~\000\tppsq\000~\000/q\000~\000\rpq\000~\0001q\000~\0008q\000~\000<sq\000~\0006t\000\001Qq\000~\000\036q\000~\000<sq\000~\000\tppsq\000~\000\016q\000~\000\rp\000sq\000~\000\000ppq\000~\000\026sq\000~\000\tppsq\000~\000/q\000~\000\rpq\000~\0001q\000~\0008q\000~\000<sq\000~\0006t\000\001Gq\000~\000\036q\000~\000<sq\000~\000\016pp\000sq\000~\000\000ppq\000~\000\026sq\000~\000\tppsq\000~\000/q\000~\000\rpq\000~\0001q\000~\0008q\000~\000<sq\000~\0006t\000\001Yq\000~\000\036sq\000~\000\tppsq\000~\000\016q\000~\000\rp\000sq\000~\000\000ppq\000~\000\026sq\000~\000\tppsq\000~\000/q\000~\000\rpq\000~\0001q\000~\0008q\000~\000<sq\000~\0006t\000\001Jq\000~\000\036q\000~\000<sq\000~\000\tppsq\000~\000\000q\000~\000\rpsq\000~\000\016q\000~\000\rp\000sq\000~\000\000ppq\000~\000\026sq\000~\000\tppsq\000~\000/q\000~\000\rpq\000~\0001q\000~\0008q\000~\000<sq\000~\0006t\000\004Seedq\000~\000\036sq\000~\000\016pp\000sq\000~\000\000ppq\000~\000\026sq\000~\000\tppsq\000~\000/q\000~\000\rpq\000~\0001q\000~\0008q\000~\000<sq\000~\0006t\000\013PgenCounterq\000~\000\036q\000~\000<sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\030\001pq\000~\000\006q\000~\000\005q\000~\000\022q\000~\000Aq\000~\000Hq\000~\000\bq\000~\000Nq\000~\000Uq\000~\000]q\000~\000\nq\000~\000cq\000~\000Zq\000~\000\007q\000~\000\013q\000~\000[q\000~\000.q\000~\000Bq\000~\000Iq\000~\000Oq\000~\000Vq\000~\000^q\000~\000dq\000~\000Fq\000~\000Sx");
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
/* 234 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final DSAKeyValueTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 243 */       super(context, "----------------------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 247 */       this(context);
/* 248 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 252 */       return DSAKeyValueTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true)
/* 261 */       { switch (this.state) {
/*     */           case 15:
/* 263 */             if ("Seed" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 264 */               this.context.pushAttributes(__atts, true);
/* 265 */               this.state = 16;
/*     */               return;
/*     */             } 
/* 268 */             this.state = 21;
/*     */             continue;
/*     */           case 21:
/* 271 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 18:
/* 274 */             if ("PgenCounter" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 275 */               this.context.pushAttributes(__atts, true);
/* 276 */               this.state = 19;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 12:
/* 281 */             if ("J" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 282 */               this.context.pushAttributes(__atts, true);
/* 283 */               this.state = 13;
/*     */               return;
/*     */             } 
/* 286 */             this.state = 15;
/*     */             continue;
/*     */           case 3:
/* 289 */             if ("Q" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 290 */               this.context.pushAttributes(__atts, true);
/* 291 */               this.state = 4;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 296 */             if ("G" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 297 */               this.context.pushAttributes(__atts, true);
/* 298 */               this.state = 7;
/*     */               return;
/*     */             } 
/* 301 */             this.state = 9;
/*     */             continue;
/*     */           case 0:
/* 304 */             if ("P" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 305 */               this.context.pushAttributes(__atts, true);
/* 306 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 309 */             this.state = 6;
/*     */             continue;
/*     */           case 9:
/* 312 */             if ("Y" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 313 */               this.context.pushAttributes(__atts, true);
/* 314 */               this.state = 10; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 319 */         super.enterElement(___uri, ___local, ___qname, __atts); return; }  super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true)
/* 330 */       { switch (this.state) {
/*     */           case 15:
/* 332 */             this.state = 21;
/*     */             continue;
/*     */           case 21:
/* 335 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 12:
/* 338 */             this.state = 15;
/*     */             continue;
/*     */           case 17:
/* 341 */             if ("Seed" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 342 */               this.context.popAttributes();
/* 343 */               this.state = 18;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 11:
/* 348 */             if ("Y" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 349 */               this.context.popAttributes();
/* 350 */               this.state = 12;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 355 */             this.state = 9;
/*     */             continue;
/*     */           case 0:
/* 358 */             this.state = 6;
/*     */             continue;
/*     */           case 2:
/* 361 */             if ("P" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 362 */               this.context.popAttributes();
/* 363 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 20:
/* 368 */             if ("PgenCounter" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 369 */               this.context.popAttributes();
/* 370 */               this.state = 21;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 375 */             if ("G" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 376 */               this.context.popAttributes();
/* 377 */               this.state = 9;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 14:
/* 382 */             if ("J" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 383 */               this.context.popAttributes();
/* 384 */               this.state = 15;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 389 */             if ("Q" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 390 */               this.context.popAttributes();
/* 391 */               this.state = 6; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 396 */         super.leaveElement(___uri, ___local, ___qname); return; }  super.leaveElement(___uri, ___local, ___qname);
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
/* 407 */         switch (this.state) {
/*     */           case 15:
/* 409 */             this.state = 21;
/*     */             continue;
/*     */           case 21:
/* 412 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 12:
/* 415 */             this.state = 15;
/*     */             continue;
/*     */           case 6:
/* 418 */             this.state = 9;
/*     */             continue;
/*     */           case 0:
/* 421 */             this.state = 6; continue;
/*     */         }  break;
/*     */       } 
/* 424 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 435 */         switch (this.state) {
/*     */           case 15:
/* 437 */             this.state = 21;
/*     */             continue;
/*     */           case 21:
/* 440 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 12:
/* 443 */             this.state = 15;
/*     */             continue;
/*     */           case 6:
/* 446 */             this.state = 9;
/*     */             continue;
/*     */           case 0:
/* 449 */             this.state = 6; continue;
/*     */         }  break;
/*     */       } 
/* 452 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 464 */           switch (this.state) {
/*     */             case 15:
/* 466 */               this.state = 21;
/*     */               continue;
/*     */             case 21:
/* 469 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 12:
/* 472 */               this.state = 15;
/*     */               continue;
/*     */             case 4:
/* 475 */               this.state = 5;
/* 476 */               eatText1(value);
/*     */               return;
/*     */             case 6:
/* 479 */               this.state = 9;
/*     */               continue;
/*     */             case 0:
/* 482 */               this.state = 6;
/*     */               continue;
/*     */             case 13:
/* 485 */               this.state = 14;
/* 486 */               eatText2(value);
/*     */               return;
/*     */             case 1:
/* 489 */               this.state = 2;
/* 490 */               eatText3(value);
/*     */               return;
/*     */             case 7:
/* 493 */               this.state = 8;
/* 494 */               eatText4(value);
/*     */               return;
/*     */             case 10:
/* 497 */               this.state = 11;
/* 498 */               eatText5(value);
/*     */               return;
/*     */             case 19:
/* 501 */               this.state = 20;
/* 502 */               eatText6(value);
/*     */               return;
/*     */             case 16:
/* 505 */               this.state = 17;
/* 506 */               eatText7(value); return;
/*     */           }  break;
/*     */         } 
/* 509 */       } catch (RuntimeException e) {
/* 510 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 520 */         DSAKeyValueTypeImpl.this._Q = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 521 */       } catch (Exception e) {
/* 522 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 530 */         DSAKeyValueTypeImpl.this._J = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 531 */       } catch (Exception e) {
/* 532 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText3(String value) throws SAXException {
/*     */       try {
/* 540 */         DSAKeyValueTypeImpl.this._P = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 541 */       } catch (Exception e) {
/* 542 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText4(String value) throws SAXException {
/*     */       try {
/* 550 */         DSAKeyValueTypeImpl.this._G = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 551 */       } catch (Exception e) {
/* 552 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText5(String value) throws SAXException {
/*     */       try {
/* 560 */         DSAKeyValueTypeImpl.this._Y = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 561 */       } catch (Exception e) {
/* 562 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText6(String value) throws SAXException {
/*     */       try {
/* 570 */         DSAKeyValueTypeImpl.this._PgenCounter = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 571 */       } catch (Exception e) {
/* 572 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText7(String value) throws SAXException {
/*     */       try {
/* 580 */         DSAKeyValueTypeImpl.this._Seed = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 581 */       } catch (Exception e) {
/* 582 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\DSAKeyValueTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */