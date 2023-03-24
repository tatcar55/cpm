/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.datatype.xsd.Base64BinaryType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.RSAKeyValueType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class RSAKeyValueTypeImpl implements RSAKeyValueType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected byte[] _Exponent;
/*  16 */   public static final Class version = JAXBVersion.class; protected byte[] _Modulus;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return RSAKeyValueType.class;
/*     */   }
/*     */   
/*     */   public byte[] getExponent() {
/*  24 */     return this._Exponent;
/*     */   }
/*     */   
/*     */   public void setExponent(byte[] value) {
/*  28 */     this._Exponent = value;
/*     */   }
/*     */   
/*     */   public byte[] getModulus() {
/*  32 */     return this._Modulus;
/*     */   }
/*     */   
/*     */   public void setModulus(byte[] value) {
/*  36 */     this._Modulus = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  40 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  46 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "Modulus");
/*  47 */     context.endNamespaceDecls();
/*  48 */     context.endAttributes();
/*     */     try {
/*  50 */       context.text(Base64BinaryType.save(this._Modulus), "Modulus");
/*  51 */     } catch (Exception e) {
/*  52 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  54 */     context.endElement();
/*  55 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "Exponent");
/*  56 */     context.endNamespaceDecls();
/*  57 */     context.endAttributes();
/*     */     try {
/*  59 */       context.text(Base64BinaryType.save(this._Exponent), "Exponent");
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
/*  77 */     return RSAKeyValueType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  81 */     if (schemaFragment == null) {
/*  82 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\000ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000'com.sun.msv.datatype.xsd.FinalComponent\000\000\000\000\000\000\000\001\002\000\001I\000\nfinalValuexr\000\036com.sun.msv.datatype.xsd.Proxy\000\000\000\000\000\000\000\001\002\000\001L\000\bbaseTypet\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\023L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000\"http://www.w3.org/2000/09/xmldsig#t\000\fCryptoBinarysr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\000)com.sun.msv.datatype.xsd.Base64BinaryType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.BinaryBaseType§Î\016^¯W\021\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\022t\000 http://www.w3.org/2001/XMLSchemat\000\fbase64Binaryq\000~\000\032\000\000\000\000sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xpq\000~\000!q\000~\000\026sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\007xq\000~\000\003q\000~\000%psq\000~\000\013ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\035q\000~\000 t\000\005QNameq\000~\000\032q\000~\000#sq\000~\000&q\000~\000/q\000~\000 sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000$\001q\000~\0007sq\000~\0001t\000\007Modulusq\000~\000\026sq\000~\000\006pp\000sq\000~\000\000ppq\000~\000\016sq\000~\000(ppsq\000~\000*q\000~\000%pq\000~\000,q\000~\0003q\000~\0007sq\000~\0001t\000\bExponentq\000~\000\026sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\005\001pq\000~\000\nq\000~\000<q\000~\000\005q\000~\000)q\000~\000=x");
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
/* 126 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final RSAKeyValueTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 135 */       super(context, "-------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 139 */       this(context);
/* 140 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 144 */       return RSAKeyValueTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 153 */       switch (this.state) {
/*     */         case 0:
/* 155 */           if ("Modulus" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 156 */             this.context.pushAttributes(__atts, true);
/* 157 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 162 */           if ("Exponent" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 163 */             this.context.pushAttributes(__atts, true);
/* 164 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 169 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/* 172 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 183 */       switch (this.state) {
/*     */         case 2:
/* 185 */           if ("Modulus" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 186 */             this.context.popAttributes();
/* 187 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 192 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 5:
/* 195 */           if ("Exponent" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 196 */             this.context.popAttributes();
/* 197 */             this.state = 6;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 202 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 213 */       switch (this.state) {
/*     */         case 6:
/* 215 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 218 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 229 */       switch (this.state) {
/*     */         case 6:
/* 231 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 234 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 246 */         switch (this.state) {
/*     */           case 6:
/* 248 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/* 251 */             this.state = 2;
/* 252 */             eatText1(value);
/*     */             return;
/*     */           case 4:
/* 255 */             this.state = 5;
/* 256 */             eatText2(value);
/*     */             return;
/*     */         } 
/* 259 */       } catch (RuntimeException e) {
/* 260 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 270 */         RSAKeyValueTypeImpl.this._Modulus = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 271 */       } catch (Exception e) {
/* 272 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 280 */         RSAKeyValueTypeImpl.this._Exponent = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 281 */       } catch (Exception e) {
/* 282 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\RSAKeyValueTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */