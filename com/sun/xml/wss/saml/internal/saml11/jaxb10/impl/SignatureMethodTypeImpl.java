/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureMethodType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.math.BigInteger;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SignatureMethodTypeImpl implements SignatureMethodType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _Algorithm;
/*  16 */   public static final Class version = JAXBVersion.class; protected ListImpl _Content; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SignatureMethodType$HMACOutputLength;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodTypeImpl$HMACOutputLengthImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return SignatureMethodType.class;
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
/* 141 */     return SignatureMethodType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 145 */     if (schemaFragment == null) {
/* 146 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003ppsq\000~\000\000ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000p\000sq\000~\000\nppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\007q\000~\000\021psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\rxq\000~\000\003q\000~\000\021psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\020\001q\000~\000\030sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\031q\000~\000\036sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000 xq\000~\000\033t\000Pcom.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureMethodType.HMACOutputLengtht\000+http://java.sun.com/jaxb/xjc/dummy-elementsq\000~\000\036sq\000~\000\nppsq\000~\000\023q\000~\000\021psq\000~\000\fq\000~\000\021p\000sq\000~\000\025ppq\000~\000\030sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\rL\000\003nc2q\000~\000\rxq\000~\000\033q\000~\000\034sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\rL\000\003nc2q\000~\000\rxq\000~\000\033sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIq\000~\000 xq\000~\000\033t\000\000sq\000~\000,t\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000,q\000~\000#q\000~\000\036sq\000~\000\025ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000 L\000\btypeNameq\000~\000 L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\021psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xpq\000~\000>q\000~\000=sq\000~\000\037t\000\tAlgorithmq\000~\000.sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\b\001pq\000~\000%q\000~\000\022q\000~\000\024q\000~\000\tq\000~\000$q\000~\000\005q\000~\000\013q\000~\000\bx");
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
/*     */   public static class HMACOutputLengthImpl
/*     */     implements SignatureMethodType.HMACOutputLength, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject
/*     */   {
/*     */     protected BigInteger _Value;
/* 202 */     public static final Class version = (SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion == null) ? (SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion = SignatureMethodTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.JAXBVersion")) : SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$JAXBVersion;
/*     */     
/*     */     private static Grammar schemaFragment;
/*     */     
/*     */     public HMACOutputLengthImpl() {}
/*     */     
/*     */     public HMACOutputLengthImpl(BigInteger value) {
/* 209 */       this._Value = value;
/*     */     }
/*     */     
/*     */     private static final Class PRIMARY_INTERFACE_CLASS() {
/* 213 */       return (SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SignatureMethodType$HMACOutputLength == null) ? (SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SignatureMethodType$HMACOutputLength = SignatureMethodTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureMethodType$HMACOutputLength")) : SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SignatureMethodType$HMACOutputLength;
/*     */     }
/*     */     
/*     */     public String ____jaxb_ri____getNamespaceURI() {
/* 217 */       return "http://www.w3.org/2000/09/xmldsig#";
/*     */     }
/*     */     
/*     */     public String ____jaxb_ri____getLocalName() {
/* 221 */       return "HMACOutputLength";
/*     */     }
/*     */     
/*     */     public BigInteger getValue() {
/* 225 */       return this._Value;
/*     */     }
/*     */     
/*     */     public void setValue(BigInteger value) {
/* 229 */       this._Value = value;
/*     */     }
/*     */     
/*     */     public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/* 233 */       return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeBody(XMLSerializer context) throws SAXException {
/* 239 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "HMACOutputLength");
/* 240 */       context.endNamespaceDecls();
/* 241 */       context.endAttributes();
/*     */       try {
/* 243 */         context.text(DatatypeConverter.printInteger(this._Value), "Value");
/* 244 */       } catch (Exception e) {
/* 245 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 247 */       context.endElement();
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
/* 261 */       return (SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SignatureMethodType$HMACOutputLength == null) ? (SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SignatureMethodType$HMACOutputLength = SignatureMethodTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureMethodType$HMACOutputLength")) : SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$SignatureMethodType$HMACOutputLength;
/*     */     }
/*     */     
/*     */     public DocumentDeclaration createRawValidator() {
/* 265 */       if (schemaFragment == null) {
/* 266 */         schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000'com.sun.msv.datatype.xsd.FinalComponent\000\000\000\000\000\000\000\001\002\000\001I\000\nfinalValuexr\000\036com.sun.msv.datatype.xsd.Proxy\000\000\000\000\000\000\000\001\002\000\001L\000\bbaseTypet\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\022L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000\"http://www.w3.org/2000/09/xmldsig#t\000\024HMACOutputLengthTypesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\000$com.sun.msv.datatype.xsd.IntegerType\000\000\000\000\000\000\000\001\002\000\000xr\000+com.sun.msv.datatype.xsd.IntegerDerivedTypeñ]&6k¾\002\000\001L\000\nbaseFacetsq\000~\000\020xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\021t\000 http://www.w3.org/2001/XMLSchemat\000\007integerq\000~\000\031sr\000,com.sun.msv.datatype.xsd.FractionDigitsFacet\000\000\000\000\000\000\000\001\002\000\001I\000\005scalexr\000;com.sun.msv.datatype.xsd.DataTypeWithLexicalConstraintFacetT\034>\032zbê\002\000\000xr\000*com.sun.msv.datatype.xsd.DataTypeWithFacet\000\000\000\000\000\000\000\001\002\000\005Z\000\fisFacetFixedZ\000\022needValueCheckFlagL\000\bbaseTypeq\000~\000\020L\000\fconcreteTypet\000'Lcom/sun/msv/datatype/xsd/ConcreteType;L\000\tfacetNameq\000~\000\022xq\000~\000\021ppq\000~\000\031\001\000sr\000#com.sun.msv.datatype.xsd.NumberType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\034q\000~\000\037t\000\007decimalq\000~\000\031q\000~\000't\000\016fractionDigits\000\000\000\000\000\000\000\000sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\022L\000\fnamespaceURIq\000~\000\022xpq\000~\000 q\000~\000\025sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000-psq\000~\000\nppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\034q\000~\000\037t\000\005QNameq\000~\000\031q\000~\000+sq\000~\000.q\000~\0007q\000~\000\037sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\022L\000\fnamespaceURIq\000~\000\022xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000,\001q\000~\000?sq\000~\0009t\000\020HMACOutputLengthq\000~\000\025sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\002\001pq\000~\000\tq\000~\0001x");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 316 */       return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */     }
/*     */     
/*     */     public class Unmarshaller
/*     */       extends AbstractUnmarshallingEventHandlerImpl
/*     */     {
/*     */       private final SignatureMethodTypeImpl.HMACOutputLengthImpl this$0;
/*     */       
/*     */       public Unmarshaller(UnmarshallingContext context) {
/* 325 */         super(context, "----");
/*     */       }
/*     */       
/*     */       protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 329 */         this(context);
/* 330 */         this.state = startState;
/*     */       }
/*     */       
/*     */       public Object owner() {
/* 334 */         return SignatureMethodTypeImpl.HMACOutputLengthImpl.this;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 343 */         switch (this.state) {
/*     */           case 3:
/* 345 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 0:
/* 348 */             if ("HMACOutputLength" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 349 */               this.context.pushAttributes(__atts, true);
/* 350 */               this.state = 1;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */         } 
/* 355 */         super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 366 */         switch (this.state) {
/*     */           case 3:
/* 368 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 2:
/* 371 */             if ("HMACOutputLength" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 372 */               this.context.popAttributes();
/* 373 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */         } 
/* 378 */         super.leaveElement(___uri, ___local, ___qname);
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
/* 389 */         switch (this.state) {
/*     */           case 3:
/* 391 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */         } 
/* 394 */         super.enterAttribute(___uri, ___local, ___qname);
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
/* 405 */         switch (this.state) {
/*     */           case 3:
/* 407 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */         } 
/* 410 */         super.leaveAttribute(___uri, ___local, ___qname);
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
/* 422 */           switch (this.state) {
/*     */             case 3:
/* 424 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 1:
/* 427 */               this.state = 2;
/* 428 */               eatText1(value);
/*     */               return;
/*     */           } 
/* 431 */         } catch (RuntimeException e) {
/* 432 */           handleUnexpectedTextException(value, e);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private void eatText1(String value) throws SAXException {
/*     */         try {
/* 442 */           SignatureMethodTypeImpl.HMACOutputLengthImpl.this._Value = DatatypeConverter.parseInteger(WhiteSpaceProcessor.collapse(value));
/* 443 */         } catch (Exception e) {
/* 444 */           handleParseConversionException(e);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SignatureMethodTypeImpl this$0;
/*     */ 
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 458 */       super(context, "----------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 462 */       this(context);
/* 463 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 467 */       return SignatureMethodTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 476 */         switch (this.state) {
/*     */           case 6:
/* 478 */             if ("HMACOutputLength" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 479 */               SignatureMethodTypeImpl.this._getContent().add(spawnChildFromEnterElement((SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodTypeImpl$HMACOutputLengthImpl == null) ? (SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodTypeImpl$HMACOutputLengthImpl = SignatureMethodTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureMethodTypeImpl$HMACOutputLengthImpl")) : SignatureMethodTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SignatureMethodTypeImpl$HMACOutputLengthImpl, 7, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 482 */             this.state = 7;
/*     */             continue;
/*     */           case 5:
/* 485 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 3:
/* 488 */             if ("HMACOutputLength" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 489 */               spawnHandlerFromEnterElement((UnmarshallingEventHandler)new Interleave1(), 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 492 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 493 */               spawnHandlerFromEnterElement((UnmarshallingEventHandler)new Interleave1(), 9, ___uri, ___local, ___qname, __atts);
/*     */               return;
/*     */             } 
/* 496 */             spawnHandlerFromEnterElement((UnmarshallingEventHandler)new Interleave1(), 9, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 7:
/* 499 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 500 */               Object co = spawnWildcard(8, ___uri, ___local, ___qname, __atts);
/* 501 */               if (co != null) {
/* 502 */                 SignatureMethodTypeImpl.this._getContent().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 506 */             this.state = 8;
/*     */             continue;
/*     */           case 0:
/* 509 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 510 */             if (attIdx >= 0) {
/* 511 */               String v = this.context.eatAttribute(attIdx);
/* 512 */               this.state = 3;
/* 513 */               eatText2(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 518 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 519 */               Object co = spawnWildcard(8, ___uri, ___local, ___qname, __atts);
/* 520 */               if (co != null) {
/* 521 */                 SignatureMethodTypeImpl.this._getContent().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 525 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 9:
/* 528 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 4:
/* 531 */             this.state = 5; continue;
/*     */         }  break;
/*     */       } 
/* 534 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 543 */         SignatureMethodTypeImpl.this._Algorithm = WhiteSpaceProcessor.collapse(value);
/* 544 */       } catch (Exception e) {
/* 545 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 555 */         switch (this.state) {
/*     */           case 6:
/* 557 */             this.state = 7;
/*     */             continue;
/*     */           case 5:
/* 560 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 563 */             spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new Interleave1(), 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 7:
/* 566 */             this.state = 8;
/*     */             continue;
/*     */           case 0:
/* 569 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 570 */             if (attIdx >= 0) {
/* 571 */               String v = this.context.eatAttribute(attIdx);
/* 572 */               this.state = 3;
/* 573 */               eatText2(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 578 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 9:
/* 581 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 4:
/* 584 */             this.state = 5; continue;
/*     */         }  break;
/*     */       } 
/* 587 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 598 */         switch (this.state) {
/*     */           case 6:
/* 600 */             this.state = 7;
/*     */             continue;
/*     */           case 5:
/* 603 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 606 */             spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new Interleave1(), 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 7:
/* 609 */             this.state = 8;
/*     */             continue;
/*     */           case 0:
/* 612 */             if ("Algorithm" == ___local && "" == ___uri) {
/* 613 */               this.state = 1;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 618 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 9:
/* 621 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 4:
/* 624 */             this.state = 5; continue;
/*     */         }  break;
/*     */       } 
/* 627 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 638 */         switch (this.state) {
/*     */           case 6:
/* 640 */             this.state = 7;
/*     */             continue;
/*     */           case 5:
/* 643 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 646 */             spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new Interleave1(), 9, ___uri, ___local, ___qname);
/*     */             return;
/*     */           case 7:
/* 649 */             this.state = 8;
/*     */             continue;
/*     */           case 2:
/* 652 */             if ("Algorithm" == ___local && "" == ___uri) {
/* 653 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 658 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 659 */             if (attIdx >= 0) {
/* 660 */               String v = this.context.eatAttribute(attIdx);
/* 661 */               this.state = 3;
/* 662 */               eatText2(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 667 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 9:
/* 670 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 4:
/* 673 */             this.state = 5; continue;
/*     */         }  break;
/*     */       } 
/* 676 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 688 */           switch (this.state) {
/*     */             case 6:
/* 690 */               this.state = 7;
/*     */               continue;
/*     */             case 5:
/* 693 */               this.state = 5;
/* 694 */               eatText3(value);
/*     */               return;
/*     */             case 3:
/* 697 */               spawnHandlerFromText((UnmarshallingEventHandler)new Interleave1(), 9, value);
/*     */               return;
/*     */             case 7:
/* 700 */               this.state = 8;
/*     */               continue;
/*     */             case 1:
/* 703 */               this.state = 2;
/* 704 */               eatText2(value);
/*     */               return;
/*     */             case 0:
/* 707 */               attIdx = this.context.getAttribute("", "Algorithm");
/* 708 */               if (attIdx >= 0) {
/* 709 */                 String v = this.context.eatAttribute(attIdx);
/* 710 */                 this.state = 3;
/* 711 */                 eatText2(v);
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */             case 8:
/* 716 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 9:
/* 719 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 4:
/* 722 */               this.state = 5;
/* 723 */               eatText3(value); return;
/*     */           }  break;
/*     */         } 
/* 726 */       } catch (RuntimeException e) {
/* 727 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText3(String value) throws SAXException {
/*     */       try {
/* 737 */         SignatureMethodTypeImpl.this._getContent().add(value);
/* 738 */       } catch (Exception e) {
/* 739 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     private class Interleave1
/*     */       extends InterleaveDispatcher
/*     */     {
/*     */       private final SignatureMethodTypeImpl.Unmarshaller this$1;
/*     */       
/*     */       private Interleave1() {
/* 749 */         super(SignatureMethodTypeImpl.Unmarshaller.this.context, 2);
/* 750 */         init(new UnmarshallingEventHandler[] { (UnmarshallingEventHandler)new SignatureMethodTypeImpl.Unmarshaller((UnmarshallingContext)this.sites[0], 4), (UnmarshallingEventHandler)new SignatureMethodTypeImpl.Unmarshaller((UnmarshallingContext)this.sites[1], 6) });
/*     */       }
/*     */ 
/*     */       
/*     */       protected int getBranchForElement(String uri, String local) {
/* 755 */         return 1;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected int getBranchForAttribute(String uri, String local) {
/* 761 */         return -1;
/*     */       }
/*     */       
/*     */       protected int getBranchForText() {
/* 765 */         return 0;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SignatureMethodTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */