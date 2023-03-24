/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SPKIDataType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SPKIDataTypeImpl implements SPKIDataType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   public static final Class version = JAXBVersion.class; protected ListImpl _SPKISexpAndAny; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SPKIDataType$SPKISexp;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SPKIDataTypeImpl$SPKISexpImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  19 */     return SPKIDataType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getSPKISexpAndAny() {
/*  23 */     if (this._SPKISexpAndAny == null) {
/*  24 */       this._SPKISexpAndAny = new ListImpl(new ArrayList());
/*     */     }
/*  26 */     return this._SPKISexpAndAny;
/*     */   }
/*     */   
/*     */   public List getSPKISexpAndAny() {
/*  30 */     return (List)_getSPKISexpAndAny();
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  34 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  40 */     int idx1 = 0;
/*  41 */     int len1 = (this._SPKISexpAndAny == null) ? 0 : this._SPKISexpAndAny.size();
/*  42 */     while (idx1 != len1) {
/*     */       
/*  44 */       Object o = this._SPKISexpAndAny.get(idx1);
/*  45 */       if (o instanceof JAXBObject) {
/*  46 */         context.childAsBody((JAXBObject)this._SPKISexpAndAny.get(idx1++), "SPKISexpAndAny");
/*     */       }
/*  48 */       else if (o instanceof Object) {
/*  49 */         context.childAsBody((JAXBObject)this._SPKISexpAndAny.get(idx1++), "SPKISexpAndAny");
/*     */       } else {
/*  51 */         Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", o);
/*     */       } 
/*     */ 
/*     */       
/*  55 */       for (int _0 = 1; _0 > 0 && idx1 != len1; _0--) {
/*     */         
/*  57 */         Object object = this._SPKISexpAndAny.get(idx1);
/*  58 */         if (object instanceof JAXBObject) {
/*  59 */           context.childAsBody((JAXBObject)this._SPKISexpAndAny.get(idx1++), "SPKISexpAndAny");
/*     */         }
/*  61 */         else if (object instanceof Object) {
/*  62 */           context.childAsBody((JAXBObject)this._SPKISexpAndAny.get(idx1++), "SPKISexpAndAny");
/*     */         } else {
/*  64 */           Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", object);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  75 */     int idx1 = 0;
/*  76 */     int len1 = (this._SPKISexpAndAny == null) ? 0 : this._SPKISexpAndAny.size();
/*  77 */     while (idx1 != len1) {
/*     */       
/*  79 */       Object o = this._SPKISexpAndAny.get(idx1);
/*  80 */       if (o instanceof JAXBObject) {
/*  81 */         context.childAsAttributes((JAXBObject)this._SPKISexpAndAny.get(idx1++), "SPKISexpAndAny");
/*     */       }
/*  83 */       else if (o instanceof Object) {
/*  84 */         idx1++;
/*     */       } else {
/*  86 */         Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", o);
/*     */       } 
/*     */ 
/*     */       
/*  90 */       for (int _0 = 1; _0 > 0 && idx1 != len1; _0--) {
/*     */         
/*  92 */         Object object = this._SPKISexpAndAny.get(idx1);
/*  93 */         if (object instanceof JAXBObject) {
/*  94 */           context.childAsAttributes((JAXBObject)this._SPKISexpAndAny.get(idx1++), "SPKISexpAndAny");
/*     */         }
/*  96 */         else if (object instanceof Object) {
/*  97 */           idx1++;
/*     */         } else {
/*  99 */           Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", object);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 110 */     int idx1 = 0;
/* 111 */     int len1 = (this._SPKISexpAndAny == null) ? 0 : this._SPKISexpAndAny.size();
/* 112 */     while (idx1 != len1) {
/*     */       
/* 114 */       Object o = this._SPKISexpAndAny.get(idx1);
/* 115 */       if (o instanceof JAXBObject) {
/* 116 */         context.childAsURIs((JAXBObject)this._SPKISexpAndAny.get(idx1++), "SPKISexpAndAny");
/*     */       }
/* 118 */       else if (o instanceof Object) {
/* 119 */         idx1++;
/*     */       } else {
/* 121 */         Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", o);
/*     */       } 
/*     */ 
/*     */       
/* 125 */       for (int _0 = 1; _0 > 0 && idx1 != len1; _0--) {
/*     */         
/* 127 */         Object object = this._SPKISexpAndAny.get(idx1);
/* 128 */         if (object instanceof JAXBObject) {
/* 129 */           context.childAsURIs((JAXBObject)this._SPKISexpAndAny.get(idx1++), "SPKISexpAndAny");
/*     */         }
/* 131 */         else if (object instanceof Object) {
/* 132 */           idx1++;
/*     */         } else {
/* 134 */           Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", object);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 143 */     return SPKIDataType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 147 */     if (schemaFragment == null) {
/* 148 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\002L\000\004exp2q\000~\000\002xq\000~\000\003ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\007ppsq\000~\000\000sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\nxq\000~\000\003q\000~\000\021psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\020\001q\000~\000\025sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\026q\000~\000\033sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\035xq\000~\000\030t\000Acom.sun.xml.wss.saml.internal.saml11.jaxb10.SPKIDataType.SPKISexpt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\rppsq\000~\000\tq\000~\000\021p\000sq\000~\000\022ppq\000~\000\025sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\nL\000\003nc2q\000~\000\nxq\000~\000\030q\000~\000\031sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\nL\000\003nc2q\000~\000\nxq\000~\000\030sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIq\000~\000\035xq\000~\000\030t\000\000sq\000~\000(t\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000(q\000~\000 q\000~\000\033sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\005\001pq\000~\000!q\000~\000\016q\000~\000\bq\000~\000\017q\000~\000\005x");
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
/* 181 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public static class SPKISexpImpl
/*     */     implements SPKIDataType.SPKISexp, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject
/*     */   {
/*     */     protected byte[] _Value;
/* 188 */     public static final Class version = (SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion == null) ? (SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion = SPKIDataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.JAXBVersion")) : SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion;
/*     */     
/*     */     private static Grammar schemaFragment;
/*     */     
/*     */     public SPKISexpImpl() {}
/*     */     
/*     */     public SPKISexpImpl(byte[] value) {
/* 195 */       this._Value = value;
/*     */     }
/*     */     
/*     */     private static final Class PRIMARY_INTERFACE_CLASS() {
/* 199 */       return (SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SPKIDataType$SPKISexp == null) ? (SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SPKIDataType$SPKISexp = SPKIDataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.SPKIDataType$SPKISexp")) : SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SPKIDataType$SPKISexp;
/*     */     }
/*     */     
/*     */     public String ____jaxb_ri____getNamespaceURI() {
/* 203 */       return "http://www.w3.org/2000/09/xmldsig#";
/*     */     }
/*     */     
/*     */     public String ____jaxb_ri____getLocalName() {
/* 207 */       return "SPKISexp";
/*     */     }
/*     */     
/*     */     public byte[] getValue() {
/* 211 */       return this._Value;
/*     */     }
/*     */     
/*     */     public void setValue(byte[] value) {
/* 215 */       this._Value = value;
/*     */     }
/*     */     
/*     */     public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/* 219 */       return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeBody(XMLSerializer context) throws SAXException {
/* 225 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "SPKISexp");
/* 226 */       context.endNamespaceDecls();
/* 227 */       context.endAttributes();
/*     */       try {
/* 229 */         context.text(Base64BinaryType.save(this._Value), "Value");
/* 230 */       } catch (Exception e) {
/* 231 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 233 */       context.endElement();
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
/* 247 */       return (SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SPKIDataType$SPKISexp == null) ? (SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SPKIDataType$SPKISexp = SPKIDataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.SPKIDataType$SPKISexp")) : SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SPKIDataType$SPKISexp;
/*     */     }
/*     */     
/*     */     public DocumentDeclaration createRawValidator() {
/* 251 */       if (schemaFragment == null) {
/* 252 */         schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000)com.sun.msv.datatype.xsd.Base64BinaryType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.BinaryBaseType§Î\016^¯W\021\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\023L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\fbase64Binarysr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xpq\000~\000\027q\000~\000\026sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\036psq\000~\000\nppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\020q\000~\000\026t\000\005QNameq\000~\000\032q\000~\000\034sq\000~\000\037q\000~\000(q\000~\000\026sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\035\001q\000~\0000sq\000~\000*t\000\bSPKISexpt\000\"http://www.w3.org/2000/09/xmldsig#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\002\001pq\000~\000\"q\000~\000\tx");
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
/* 291 */       return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */     }
/*     */     
/*     */     public class Unmarshaller
/*     */       extends AbstractUnmarshallingEventHandlerImpl
/*     */     {
/*     */       private final SPKIDataTypeImpl.SPKISexpImpl this$0;
/*     */       
/*     */       public Unmarshaller(UnmarshallingContext context) {
/* 300 */         super(context, "----");
/*     */       }
/*     */       
/*     */       protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 304 */         this(context);
/* 305 */         this.state = startState;
/*     */       }
/*     */       
/*     */       public Object owner() {
/* 309 */         return SPKIDataTypeImpl.SPKISexpImpl.this;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 318 */         switch (this.state) {
/*     */           case 0:
/* 320 */             if ("SPKISexp" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 321 */               this.context.pushAttributes(__atts, true);
/* 322 */               this.state = 1;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 327 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */         } 
/* 330 */         super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 341 */         switch (this.state) {
/*     */           case 2:
/* 343 */             if ("SPKISexp" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 344 */               this.context.popAttributes();
/* 345 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 350 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */         } 
/* 353 */         super.leaveElement(___uri, ___local, ___qname);
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
/* 364 */         switch (this.state) {
/*     */           case 3:
/* 366 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */         } 
/* 369 */         super.enterAttribute(___uri, ___local, ___qname);
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
/* 380 */         switch (this.state) {
/*     */           case 3:
/* 382 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */         } 
/* 385 */         super.leaveAttribute(___uri, ___local, ___qname);
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
/* 397 */           switch (this.state) {
/*     */             case 1:
/* 399 */               this.state = 2;
/* 400 */               eatText1(value);
/*     */               return;
/*     */             case 3:
/* 403 */               revertToParentFromText(value);
/*     */               return;
/*     */           } 
/* 406 */         } catch (RuntimeException e) {
/* 407 */           handleUnexpectedTextException(value, e);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private void eatText1(String value) throws SAXException {
/*     */         try {
/* 417 */           SPKIDataTypeImpl.SPKISexpImpl.this._Value = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 418 */         } catch (Exception e) {
/* 419 */           handleParseConversionException(e);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SPKIDataTypeImpl this$0;
/*     */ 
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 433 */       super(context, "---");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 437 */       this(context);
/* 438 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 442 */       return SPKIDataTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/* 451 */         switch (this.state) {
/*     */           case 0:
/* 453 */             if ("SPKISexp" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 454 */               SPKIDataTypeImpl.this._getSPKISexpAndAny().add(spawnChildFromEnterElement((SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SPKIDataTypeImpl$SPKISexpImpl == null) ? (SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SPKIDataTypeImpl$SPKISexpImpl = SPKIDataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SPKIDataTypeImpl$SPKISexpImpl")) : SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SPKIDataTypeImpl$SPKISexpImpl, 1, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 459 */             if ("SPKISexp" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 460 */               SPKIDataTypeImpl.this._getSPKISexpAndAny().add(spawnChildFromEnterElement((SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SPKIDataTypeImpl$SPKISexpImpl == null) ? (SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SPKIDataTypeImpl$SPKISexpImpl = SPKIDataTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SPKIDataTypeImpl$SPKISexpImpl")) : SPKIDataTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SPKIDataTypeImpl$SPKISexpImpl, 1, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 463 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 1:
/* 466 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 467 */               Object co = spawnWildcard(2, ___uri, ___local, ___qname, __atts);
/* 468 */               if (co != null) {
/* 469 */                 SPKIDataTypeImpl.this._getSPKISexpAndAny().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 473 */             this.state = 2; continue;
/*     */         }  break;
/*     */       } 
/* 476 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 487 */         switch (this.state) {
/*     */           case 2:
/* 489 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 1:
/* 492 */             this.state = 2; continue;
/*     */         }  break;
/*     */       } 
/* 495 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 506 */         switch (this.state) {
/*     */           case 2:
/* 508 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 1:
/* 511 */             this.state = 2; continue;
/*     */         }  break;
/*     */       } 
/* 514 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 525 */         switch (this.state) {
/*     */           case 2:
/* 527 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 1:
/* 530 */             this.state = 2; continue;
/*     */         }  break;
/*     */       } 
/* 533 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 545 */           switch (this.state) {
/*     */             case 2:
/* 547 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 1:
/* 550 */               this.state = 2; continue;
/*     */           }  break;
/*     */         } 
/* 553 */       } catch (RuntimeException e) {
/* 554 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SPKIDataTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */