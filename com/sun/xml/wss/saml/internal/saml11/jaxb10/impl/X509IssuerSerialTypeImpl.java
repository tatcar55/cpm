/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.X509IssuerSerialType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.math.BigInteger;
/*     */ import javax.xml.bind.DatatypeConverter;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class X509IssuerSerialTypeImpl implements X509IssuerSerialType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected BigInteger _X509SerialNumber;
/*  16 */   public static final Class version = JAXBVersion.class; protected String _X509IssuerName;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return X509IssuerSerialType.class;
/*     */   }
/*     */   
/*     */   public BigInteger getX509SerialNumber() {
/*  24 */     return this._X509SerialNumber;
/*     */   }
/*     */   
/*     */   public void setX509SerialNumber(BigInteger value) {
/*  28 */     this._X509SerialNumber = value;
/*     */   }
/*     */   
/*     */   public String getX509IssuerName() {
/*  32 */     return this._X509IssuerName;
/*     */   }
/*     */   
/*     */   public void setX509IssuerName(String value) {
/*  36 */     this._X509IssuerName = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  40 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  46 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "X509IssuerName");
/*  47 */     context.endNamespaceDecls();
/*  48 */     context.endAttributes();
/*     */     try {
/*  50 */       context.text(this._X509IssuerName, "X509IssuerName");
/*  51 */     } catch (Exception e) {
/*  52 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  54 */     context.endElement();
/*  55 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "X509SerialNumber");
/*  56 */     context.endNamespaceDecls();
/*  57 */     context.endAttributes();
/*     */     try {
/*  59 */       context.text(DatatypeConverter.printInteger(this._X509SerialNumber), "X509SerialNumber");
/*  60 */     } catch (Exception e) {
/*  61 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  63 */     context.endElement();
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
/*  77 */     return X509IssuerSerialType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  81 */     if (schemaFragment == null) {
/*  82 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\000ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\025L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\020psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\025L\000\fnamespaceURIq\000~\000\025xpq\000~\000\031q\000~\000\030sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\007xq\000~\000\003q\000~\000\020psq\000~\000\013ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\022q\000~\000\030t\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\033q\000~\000\036sq\000~\000\037q\000~\000(q\000~\000\030sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\025L\000\fnamespaceURIq\000~\000\025xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\017\001q\000~\0002sq\000~\000,t\000\016X509IssuerNamet\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\006pp\000sq\000~\000\000ppsq\000~\000\013ppsr\000$com.sun.msv.datatype.xsd.IntegerType\000\000\000\000\000\000\000\001\002\000\000xr\000+com.sun.msv.datatype.xsd.IntegerDerivedTypeñ]&6k¾\002\000\001L\000\nbaseFacetst\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xq\000~\000\022q\000~\000\030t\000\007integerq\000~\000*sr\000,com.sun.msv.datatype.xsd.FractionDigitsFacet\000\000\000\000\000\000\000\001\002\000\001I\000\005scalexr\000;com.sun.msv.datatype.xsd.DataTypeWithLexicalConstraintFacetT\034>\032zbê\002\000\000xr\000*com.sun.msv.datatype.xsd.DataTypeWithFacet\000\000\000\000\000\000\000\001\002\000\005Z\000\fisFacetFixedZ\000\022needValueCheckFlagL\000\bbaseTypeq\000~\000<L\000\fconcreteTypet\000'Lcom/sun/msv/datatype/xsd/ConcreteType;L\000\tfacetNameq\000~\000\025xq\000~\000\024ppq\000~\000*\001\000sr\000#com.sun.msv.datatype.xsd.NumberType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\022q\000~\000\030t\000\007decimalq\000~\000*q\000~\000Et\000\016fractionDigits\000\000\000\000q\000~\000\036sq\000~\000\037q\000~\000>q\000~\000\030sq\000~\000!ppsq\000~\000#q\000~\000\020pq\000~\000%q\000~\000.q\000~\0002sq\000~\000,t\000\020X509SerialNumberq\000~\0006sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\005\001pq\000~\000\nq\000~\0008q\000~\000\"q\000~\000Iq\000~\000\005x");
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
/* 134 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final X509IssuerSerialTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 143 */       super(context, "-------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 147 */       this(context);
/* 148 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 152 */       return X509IssuerSerialTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 161 */       switch (this.state) {
/*     */         case 0:
/* 163 */           if ("X509IssuerName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 164 */             this.context.pushAttributes(__atts, true);
/* 165 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 170 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 3:
/* 173 */           if ("X509SerialNumber" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 174 */             this.context.pushAttributes(__atts, true);
/* 175 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 180 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/* 191 */       switch (this.state) {
/*     */         case 2:
/* 193 */           if ("X509IssuerName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 194 */             this.context.popAttributes();
/* 195 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 200 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 5:
/* 203 */           if ("X509SerialNumber" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 204 */             this.context.popAttributes();
/* 205 */             this.state = 6;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 210 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 221 */       switch (this.state) {
/*     */         case 6:
/* 223 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 226 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 237 */       switch (this.state) {
/*     */         case 6:
/* 239 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 242 */       super.leaveAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleText(String value) throws SAXException {
/*     */       try {
/* 254 */         switch (this.state) {
/*     */           case 4:
/* 256 */             this.state = 5;
/* 257 */             eatText1(value);
/*     */             return;
/*     */           case 1:
/* 260 */             this.state = 2;
/* 261 */             eatText2(value);
/*     */             return;
/*     */           case 6:
/* 264 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 267 */       } catch (RuntimeException e) {
/* 268 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 278 */         X509IssuerSerialTypeImpl.this._X509SerialNumber = DatatypeConverter.parseInteger(WhiteSpaceProcessor.collapse(value));
/* 279 */       } catch (Exception e) {
/* 280 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 288 */         X509IssuerSerialTypeImpl.this._X509IssuerName = value;
/* 289 */       } catch (Exception e) {
/* 290 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\X509IssuerSerialTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */